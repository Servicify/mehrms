<!-- 操作员管理 -->
<template>
  <div>
    <!-- 为了把搜索框展示出来 -->
    <div style="margin-top=10px;display: flex;justify-content: center">
      <el-button
        type="success"
        size="large"
        @click="addItem"
        style="position:relative;left:-200px"
        icon="el-icon-plus"
        >添加用户</el-button
      >
      <!-- 顶部区域--搜索框 -->
      <el-input
        v-model="keywords"
        placeholder="通过用户名搜索用户..."
        prefix-icon="el-icon-search"
        style="width: 400px;margin-right: 10px"
        @keydown.enter.native="doSearch"
      ></el-input>
      <el-button icon="el-icon-search" type="primary" @click="doSearch"
        >搜索</el-button
      >
      <el-button icon="el-icon-refresh" type="success" @click="refresh"
        >刷新</el-button
      >
    </div>
    <div class="hr-container">
      <el-card class="hr-card" v-for="(hr, index) in hrs" :key="index">
        <div slot="header" class="clearfix">
          <span>{{ hr.name }}</span>
          <el-button
            style="float: right; padding: 3px 0;color: #e30007;"
            type="text"
            icon="el-icon-delete"
            @click="deleteHr(hr)"
          ></el-button>
        </div>
        <div>
          <div class="img-container">
            <!-- <img
              :src="hr.userface"
              :alt="hr.name"
              :title="hr.name"
              class="userface-img"
            /> -->
          </div>
          <div class="userinfo-container">
            <div>账号：{{ hr.username }}</div>
            <div>
              用户名：{{
                hr.name
              }}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <el-button type="text" size="small" @click="editUser(hr.id)"
                ><el-tag type="success"
                  ><i class="el-icon-edit"></i>修改绑定</el-tag
                ></el-button
              >
            </div>
            <div>手机号码：{{ hr.phone }}</div>
            <div>电话号码：{{ hr.telephone }}</div>
            <div>地址：{{ hr.address }}</div>
            <div>
              用户状态：
              <el-switch
                v-model="hr.enabled"
                active-text="启用"
                @change="enabledChange(hr)"
                active-color="#13ce66"
                inactive-color="#ff4949"
                inactive-text="禁用"
              >
              </el-switch>
            </div>
            <div>
              用户角色：
              <el-tag
                type="success"
                style="margin-right: 4px"
                v-for="(role, indexj) in hr.roles"
                :key="indexj"
                >{{ role.nameZh }}
              </el-tag>
              <el-popover
                placement="right"
                title="角色列表"
                @show="showPop(hr)"
                @hide="hidePop(hr)"
                width="200"
                trigger="click"
              >
                <el-select
                  v-model="selectedRoles"
                  multiple
                  placeholder="请选择"
                >
                  <el-option
                    v-for="(r, indexk) in allroles"
                    :key="indexk"
                    :label="r.nameZh"
                    :value="r.id"
                  >
                  </el-option>
                </el-select>
                <el-button
                  slot="reference"
                  icon="el-icon-more"
                  type="text"
                ></el-button>
              </el-popover>
            </div>
            <div>备注：{{ hr.remark }}</div>
          </div>
        </div>
      </el-card>
    </div>
    <!-- 修改区域 -->
    <div>
      <el-dialog title="修改账号绑定" :visible.sync="dialogFormVisible">
        <el-form :model="Admins">
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px ">
            <span style="color:red">&nbsp;*&nbsp;</span>选则员工信息</el-tag
          >
          <!-- 参考ElementUI中的Input输入框中的自定义模板 -->
          <el-autocomplete
            popper-class="my-autocomplete"
            v-model="Admins.empname"
            :fetch-suggestions="querySearchEmp"
            placeholder="请输入员工信息内容"
            @select="handleSelectEmp"
            style="margin:7px 4px 7px 0px "
            clearable
          >
            <i class="el-icon-edit el-input__icon" slot="suffix"> </i>
            <template slot-scope="{ item }">
              <div class="name">{{ item.value }}</div>
              <span class="addr">{{ item.workID }}</span>
            </template>
          </el-autocomplete>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="closed">取 消</el-button>
          <el-button type="primary" @click="onSubmit">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "Administer",
  data() {
    return {
      keywords: "",
      // 服务端响应数据接收器
      hrs: [],
      selectedRoles: [],
      allroles: [],
      // 修改数据弹出框修改
      dialogFormVisible: false,
      // 员工信息自定义模板
      emps: [],
      Admins: {
        eid: null
      },
      edit: {
        eid: null,
        hrid: null
      }
    };
  },
  //生命周期 - 挂载完成（访问DOM元素）
  mounted() {
    this.initHrs();
    // 员工信息
    this.emps = this.loadAllEmp();
  },
  methods: {
    // 初始化数据
    initHrs() {
      this.getRequest("/system/hr/?keywords=" + this.keywords).then(resp => {
        if (resp) {
          this.hrs = resp;
        }
      });
    },
    // 更新当前账户的禁用或启用状态,
    enabledChange(hr) {
      // 去掉服务器更新数据不需要的属性
      delete hr.roles;
      this.putRequest("/system/hr/", hr).then(resp => {
        if (resp) {
          this.initHrs();
        }
      });
    },
    // 查询创建的所有角色
    initAllRoles() {
      this.getRequest("/system/hr/roles").then(resp => {
        if (resp) {
          this.allroles = resp;
        }
      });
    },
    // 在下拉框中显示所有角色，并选中用户所拥有的角色
    showPop(hr) {
      this.initAllRoles();
      let roles = hr.roles;
      this.selectedRoles = [];
      roles.forEach(r => {
        this.selectedRoles.push(r.id);
      });
    },
    // 关闭角色更新框之后，自动生成请求参数，并发送至后端
    hidePop(hr) {
      // 添加一个以flag为指示器的过滤代码。
      // 如果用户点击了更新框，但并没有实质上做更新操作，则不发送更新请求
      // 否则，发送更新请求
      let roles = [];
      Object.assign(roles, hr.roles);
      let flag = false;
      if (roles.length != this.selectedRoles.length) {
        flag = true;
      } else {
        for (let i = 0; i < roles.length; i++) {
          let role = roles[i];
          for (let j = 0; j < this.selectedRoles.length; j++) {
            let sr = this.selectedRoles[j];
            if (role.id == sr) {
              roles.splice(i, 1);
              i--;
              break;
            }
          }
        }
        if (roles.length != 0) {
          flag = true;
        }
      }
      if (flag) {
        // 生成请求参数链接
        let url = "/system/hr/role?hrid=" + hr.id;
        this.selectedRoles.forEach(sr => {
          url += "&rids=" + sr;
        });
        // 发送请求
        this.putRequest(url).then(resp => {
          if (resp) {
            this.initHrs();
          }
        });
      }
    },
    // 对关键字进行搜索
    doSearch() {
      this.initHrs();
    },
    // 删除事件
    deleteHr(hr) {
      this.$confirm("此操作将永久删除【" + hr.name + "】, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.deleteRequest("/system/hr/" + hr.id).then(resp => {
            if (resp) {
              this.initHrs();
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    addItem() {
      this.getRequest("/system/hr/add").then(resp => {
        if (resp) {
          this.initHrs();
        }
      });
    },
    // ***************************
    // *******员工选项加载数据*****
    // ***************************
    // 提示员工信息
    querySearchEmp(queryString, cb) {
      var emps = this.emps;
      var results = queryString
        ? emps.filter(this.createFilterEmp(queryString))
        : emps;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    // 对接收到的员工信息进行过滤
    createFilterEmp(queryString) {
      return emp => {
        return emp.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0;
      };
    },
    // 请求员工信息
    loadAllEmp() {
      this.getRequest("/personnel/emp/info").then(resp => {
        if (resp) {
          // 为接收数组的每一个对象插入一个value属性
          for (let item of resp) {
            item.value = item.name;
          }
          this.emps = resp;
        }
      });
    },
    // 选中员工中的提示信息的操作
    handleSelectEmp(item) {
      // 选中后的操作
      this.Admins.empname = item.value;
      this.Admins.workID = item.workID;
      this.Admins.eid = item.id;
    },
    editUser(hrid) {
      console.log(hrid);
      this.Admins.hrid = hrid;
      this.show();
    },
    show() {
      this.dialogFormVisible = true;
    },
    onSubmit() {
      this.edit.eid = this.Admins.eid;
      this.edit.hrid = this.Admins.hrid;
      console.log(this.edit);
      this.putRequest("/system/hr/emp", this.edit).then(resp => {
        if (resp) {
          this.initHrs();
          this.dialogFormVisible = false;
        }
      });
      this.closed();
    },
    closed() {
      this.dialogFormVisible = false;
      Object.keys(this.Admins).forEach(key => (this.Admins[key] = undefined));
      Object.keys(this.edit).forEach(key => (this.edit[key] = undefined));
      this.initHrs();
    },
    refresh() {
      this.keywords = "";
      this.initHrs();
      this.$message({
        showClose: true,
        message: "刷新成功",
        type: "success"
      });
    }
  },
  //生命周期 - 创建完成（访问当前this实例）
  created() {}
};
</script>
<style>
/* @import url(); 引入css类 */
.userinfo-container div {
  font-size: 12px;
  color: #409eff;
}

.userinfo-container {
  margin-top: 20px;
}

.img-container {
  width: 100%;
  display: flex;
  justify-content: center;
}

.userface-img {
  width: 72px;
  height: 72px;
  border-radius: 72px;
}

.hr-container {
  margin-top: 10px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}

.hr-card {
  width: 350px;
  margin-bottom: 20px;
}
</style>
<!-- 
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
-->