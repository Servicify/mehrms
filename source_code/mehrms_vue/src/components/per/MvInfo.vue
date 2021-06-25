<!-- 调岗记录 -->
<!-- 
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
-->
<template>
  <div>
    <!-- 添加，查询，刷新按钮 -->
    <div>
      <el-button
        type="danger"
        size="large"
        @click="searchItem"
        icon="el-icon-search"
        >查询</el-button
      >
      <el-button
        type="success"
        size="large"
        @click="refresh"
        icon="el-icon-refresh"
        >刷新</el-button
      >
    </div>
    <!-- 数据显示区域 -->
    <div>
      <el-table
        :data="MvInfos"
        size="large"
        border
        style="width: 80%;margin-top:10px"
      >
        <el-table-column type="index" label="序号" fixed width="50">
        </el-table-column>
        <el-table-column
          prop="id"
          :formatter="formatterColumn"
          label="员工工号"
          width="150"
        >
        </el-table-column>
        <el-table-column
          prop="removeDate"
          label="调岗时间"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="employee.id"
          label="员工工号"
          width="100"
        ></el-table-column>
        <el-table-column
          fixed
          prop="employee.name"
          label="员工姓名"
          width="100"
        ></el-table-column
        ><el-table-column
          prop="empBeforeRemove.beforeDepName"
          label="调整前所属部门"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="empBeforeRemove.beforePositionName"
          label="调整前职位"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="empBeforeRemove.beforeJobLevelName"
          label="调整前职称"
          width="100"
        >
        </el-table-column>
        <el-table-column
          prop="empBeforeRemove.beforeJobLevelTitleName"
          label="调整前职称级别"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="department.name"
          label="调整后所属部门"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="position.name"
          label="调整后职位"
          width="100"
        ></el-table-column>
        <el-table-column prop="jobLevel.name" label="调整后职称" width="100">
        </el-table-column>
        <el-table-column
          prop="jobLevel.titleLevel"
          label="调整后职称级别"
          width="100"
        >
        </el-table-column>
        <el-table-column prop="reason" label="调岗原因" width="200">
        </el-table-column>
        <el-table-column prop="remark" label="备注" width="200">
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="150">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              @click="editItem(scope.row)"
              icon="el-icon-edit"
              >修改</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页功能 -->
    <div style="display: flex;justify-content: flex-end">
      <el-pagination
        @size-change="sizeChange"
        @current-change="currentChange"
        :current-page="page"
        :page-sizes="[5, 10, 20, 30, 40, 50, 100, 200, 500, 1000]"
        :page-size="size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        style="position:relative;left:-20%"
      >
      </el-pagination>
    </div>
    <!-- 添加、修改、查询操作弹出框 -->
    <div>
      <el-dialog :title="Title" :visible.sync="dialogFormVisible">
        <el-form :model="MvInfo">
          <!-- 使用自定义模板员工信息输入框 -->
          <!-- 员工信息输入框 -->
          <!-- 查询员工信息需要选则员工的名子 -->
          <el-form-item>
            <el-tag type="success" size="large">选则员工信息</el-tag>
            <!-- 参考ElementUI中的Input输入框中的自定义模板 -->
            <el-autocomplete
              popper-class="my-autocomplete"
              v-model="MvInfo.ename"
              :fetch-suggestions="querySearchEmp"
              placeholder="请输入员工信息内容"
              @select="handleSelectEmp"
              :disabled="nameDisabled"
              clearable
            >
              <i class="el-icon-edit el-input__icon" slot="suffix"> </i>
              <template slot-scope="{ item }">
                <div class="name">{{ item.value }}</div>
                <span class="addr">{{ item.workID }}</span>
              </template>
            </el-autocomplete>
          </el-form-item>
          <!-- 员工id输入框 -->
          <el-form-item>
            <el-tag type="success" size="large">员工工号</el-tag>
            <el-input
              v-model="MvInfo.workID"
              disabled
              style="width:100px"
            ></el-input>
          </el-form-item>
          <!-- 部门输入框 -->
          <!-- 查询存在的部门信息名字 -->
          <el-form-item>
            <el-tag type="success" size="large"
              ><span style="color:red" v-if="this.validateIt != 0"
                >&nbsp;*&nbsp;</span
              >选则调整后部门</el-tag
            >
            <el-popover
              placement="right"
              title="请选择调整后部门"
              width="200"
              trigger="manual"
              v-model="popVisible"
            >
              <el-tree
                default-expand-all
                :data="deps"
                :props="defaultProps"
                @node-click="handleNodeClick"
              ></el-tree>
              <div
                slot="reference"
                style="width: 130px;display: inline-flex;font-size: 13px;border: 1px solid #dedede;height: 26px;border-radius: 5px;cursor: pointer;align-items: center;padding-left: 8px;box-sizing: border-box;margin-left: 3px"
                @click="showDepView"
              >
                {{ MvInfo.dname }}
              </div>
            </el-popover>
          </el-form-item>
          <!-- 职位信息输入框 -->
          <!-- 查询职位信息需要选则职位的名字 -->
          <el-form-item>
            <el-tag type="success" size="large"
              ><span style="color:red" v-if="this.validateIt != 0"
                >&nbsp;*&nbsp;</span
              >选则调整后职位信息</el-tag
            >
            <!-- 参考ElementUI中的Input输入框中的自定义模板 -->
            <el-autocomplete
              popper-class="my-autocomplete"
              v-model="MvInfo.pname"
              :fetch-suggestions="querySearchPos"
              placeholder="请输入调整后职位信息内容"
              @select="handleSelectPos"
              clearable
            >
              <i class="el-icon-edit el-input__icon" slot="suffix"> </i>
              <template slot-scope="{ item }">
                <div class="name">{{ item.value }}</div>
                <span class="addr">{{ item.id }}</span>
              </template>
            </el-autocomplete>
          </el-form-item>
          <!-- 职称信息输入框 -->
          <!-- 查询职称信息需要选则职位的名字 -->
          <el-form-item>
            <el-tag type="success" size="large"
              ><span style="color:red" v-if="this.validateIt != 0"
                >&nbsp;*&nbsp;</span
              >选则调整后职称信息</el-tag
            >
            <!-- 参考ElementUI中的Input输入框中的自定义模板 -->
            <el-autocomplete
              popper-class="my-autocomplete"
              v-model="MvInfo.jname"
              :fetch-suggestions="querySearchJls"
              placeholder="请输入调整后职称信息内容"
              @select="handleSelectJls"
              clearable
            >
              <i class="el-icon-edit el-input__icon" slot="suffix"> </i>
              <template slot-scope="{ item }">
                <div class="name">{{ item.value }}</div>
                <span class="addr">{{ item.id }}</span>
              </template>
            </el-autocomplete>
          </el-form-item>
          <!-- 查询日期选则器 -->
          <el-form-item>
            <el-tag type="success" size="large"
              ><span style="color:red" v-if="this.validateIt != 0"
                >&nbsp;*&nbsp;</span
              >入职日期:</el-tag
            >
            <el-date-picker
              v-model="MvInfo.removeDate"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd"
              v-if="dateView"
            >
            </el-date-picker>
            <el-date-picker
              v-model="MvInfo.beginDateScope"
              type="daterange"
              size="mini"
              unlink-panels
              value-format="yyyy-MM-dd"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              v-else
            >
            </el-date-picker>
          </el-form-item>
          <!-- 原因 -->
          <el-form-item>
            <el-tag type="success" size="large">调动原因:</el-tag>
            <el-input
              type="textarea"
              :rows="2"
              placeholder="请输入调动原因"
              v-model="MvInfo.reason"
            >
            </el-input>
          </el-form-item>
          <!-- 备注 -->
          <el-form-item>
            <el-tag type="success" size="large">备注内容:</el-tag>
            <el-input
              type="textarea"
              :rows="2"
              placeholder="请输入备注内容"
              v-model="MvInfo.remark"
            >
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">确认</el-button>
            <el-button @click="closed">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "MvInfo",
  data() {
    return {
      MvInfos: [],
      MvInfo: {
        id: null,
        workID: null,
        eid: null,
        ename: null,
        afterDepId: null,
        dname: null,
        afterJobLevelId: null,
        jname: null,
        afterPositionId: null,
        pname: null,
        removeDate: null,
        reason: null,
        remark: null,
        beginDateScope: null
      },
      // 员工信息自定义模板
      emps: [],
      // 部门信息自定义模板
      deps: [],
      // 职位信息自定义模板
      poss: [],
      // 职称信息自定义模板
      jls: [],
      // 页数
      // 总计多少页
      total: 0,
      // 默认显示第一页
      page: 1,
      size: 10,
      // 查询修改弹窗的标题
      Title: "调整岗位信息",
      nameDisabled: false,
      // 弹出面板
      dialogFormVisible: false,
      // 部门选则提示框的弹出
      popVisible: false,
      // 弹出框入职日期是否显示
      dateView: false,
      // 弹出面板显示输入框类型值
      validateIt: 0,
      // 修改数据发送对象
      edit: {
        id: null,
        eid: null,
        afterDepId: null,
        afterJobLevelId: null,
        afterPositionId: null,
        removeDate: null,
        reason: null,
        remark: null
      },
      editEmp: {
        id: null,
        departmentId: null,
        PosId: null,
        jobLevelId: null
      },
      // 查看数据发送对象
      search: {
        ename: null,
        eid: null,
        afterDepId: null,
        afterJobLevelId: null,
        afterPositionId: null,
        reason: null,
        remark: null,
        beginDateScope: null,
        id: null,
        dname: null,
        jname: null,
        pname: null
      },
      // 部门选则框
      defaultProps: {
        children: "children",
        label: "name"
      }
    };
  },
  methods: {
    // ***************************
    // *******分页显示*************
    // ***************************
    // 调整页面显示数目
    sizeChange(currentSize) {
      //
      this.size = currentSize;
      this.initMvInfo();
    },
    // 调整页面显示第几页
    currentChange(currentPage) {
      this.page = currentPage;
      this.initMvInfo();
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
      this.MvInfo.ename = item.value;
      this.MvInfo.eid = item.id;
      this.MvInfo.workID = item.workID;
    },
    // ***************************
    // *******部门选项加载数据*****
    // ***************************
    // 请求部门信息
    loadAllDep() {
      // 使用网页的存储器进行存储
      if (!window.sessionStorage.getItem("Deps")) {
        this.getRequest("/personnel/emp/deps").then(resp => {
          if (resp) {
            // 为接收数组的每一个对象插入一个value属性
            for (let item of resp) {
              item.value = item.name;
            }
            this.deps = resp;
            window.sessionStorage.setItem("Deps", JSON.stringify(resp));
          }
        });
      } else {
        this.deps = JSON.parse(window.sessionStorage.getItem("Deps"));
      }
    },
    // 控制部门提示信息的选则框的关闭
    showDepView() {
      this.popVisible = !this.popVisible;
    },
    handleNodeClick(data) {
      this.MvInfo.dname = data.name;
      this.MvInfo.afterDepId = data.id;
      this.popVisible = !this.popVisible;
    },
    // ***************************
    // *******职位选项加载数据*****
    // ***************************
    // 提示职位信息
    querySearchPos(queryString, cb) {
      var poss = this.poss;
      var results = queryString
        ? poss.filter(this.createFilterPos(queryString))
        : poss;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    // 对接收到的职位信息进行过滤
    createFilterPos(queryString) {
      return pos => {
        return pos.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0;
      };
    },
    // 请求职位信息
    loadAllPos() {
      this.getRequest("/system/basic/pos/").then(resp => {
        if (resp) {
          // 为接收数组的每一个对象插入一个value属性
          for (let item of resp) {
            item.value = item.name;
          }
          this.poss = resp;
        }
      });
    },
    // 选中职位中的提示信息的操作
    handleSelectPos(item) {
      // 选中后的操作
      this.MvInfo.pname = item.value;
      this.MvInfo.afterPositionId = item.id;
    },
    // ***************************
    // *******职称选项加载数据*****
    // ***************************
    // 提示职称信息
    querySearchJls(queryString, cb) {
      var jls = this.jls;
      var results = queryString
        ? jls.filter(this.createFilterJls(queryString))
        : jls;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    // 对接收到的职称信息进行过滤
    createFilterJls(queryString) {
      return jl => {
        return jl.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0;
      };
    },
    // 请求职称信息
    loadAllJls() {
      this.getRequest("/system/basic/joblevel/").then(resp => {
        if (resp) {
          // 为接收数组的每一个对象插入一个value属性
          for (let item of resp) {
            item.value = item.name;
          }
          this.jls = resp;
        }
      });
    },
    // 选中职称中的提示信息的操作
    handleSelectJls(item) {
      // 选中后的操作
      this.MvInfo.jname = item.value;
      this.MvInfo.afterJobLevelId = item.id;
    },
    refresh() {
      Object.keys(this.MvInfo).forEach(key => (this.MvInfo[key] = null));
      Object.keys(this.MvInfos).forEach(key => (this.MvInfos[key] = null));
      Object.keys(this.search).forEach(key => (this.search[key] = null));
      Object.keys(this.edit).forEach(key => (this.edit[key] = null));
      this.initMvInfo();
      this.$message({
        showClose: true,
        message: "刷新成功",
        type: "success"
      });
    },
    // 初始化页面数据
    initMvInfo() {
      let url = "/per/mv/?page=" + this.page + "&size=" + this.size;
      if (this.search.eid != null) {
        url += "&eid=" + this.search.eid;
      }
      if (this.search.afterDepId != null) {
        url += "&afterDepId=" + this.search.afterDepId;
      }
      if (this.search.afterPositionId != null) {
        url += "&afterPositionId=" + this.search.afterPositionId;
      }
      if (this.search.afterJobLevelId != null) {
        url += "&afterJobLevelId=" + this.search.afterJobLevelId;
      }
      if (this.search.reason != null) {
        url += "&reason=" + this.search.reason;
      }
      if (this.search.remark != null) {
        url += "&remark=" + this.search.remark;
      }
      if (this.search.beginDateScope != null) {
        url += "&beginDateScope=" + this.search.beginDateScope;
      }
      this.getRequest(url).then(resp => {
        if (resp) {
          this.MvInfos = resp.data;
          this.total = resp.total;
          // 示范
        }
      });
    },
    //
    searchItem() {
      // 把this.search的属性值赋给MvInfo
      this.MvInfo = JSON.parse(JSON.stringify(this.search));
      this.validateIt = 0;
      this.Title = "查找员工岗位调动信息";
      this.showView();
    },
    editItem(row) {
      this.validateIt = 2;
      this.Title = "修改员工岗位信息";
      this.MvInfo.id = row.id;
      this.MvInfo.workID = this.PrefixInteger(row.employee.id);
      this.MvInfo.eid = row.employee.id;
      this.MvInfo.ename = row.employee.name;
      this.MvInfo.afterDepId = row.afterDepId;
      this.MvInfo.dname = row.department.name;
      this.MvInfo.afterJobLevelId = row.afterJobLevelId;
      this.MvInfo.jname = row.jobLevel.name;
      this.MvInfo.afterPositionId = row.afterPositionId;
      this.MvInfo.pname = row.position.name;
      this.MvInfo.removeDate = row.removeDate;
      this.MvInfo.reason = row.reason;
      this.MvInfo.remark = row.remark;
      this.showView();
    },
    onSubmit() {
      if (this.validateIt == 0) {
        this.search = JSON.parse(JSON.stringify(this.MvInfo));
        // this.search.ename = this.MvInfo.ename;
        // this.search.id = this.MvInfo.id;
        // this.search.eid = this.MvInfo.eid;
        // this.search.afterDepId = this.MvInfo.afterDepId;
        // this.search.afterJobLevelId = this.MvInfo.afterJobLevelId;
        // this.search.afterPositionId = this.MvInfo.afterPositionId;
        // this.search.reason = this.MvInfo.reason;
        // this.search.remark = this.MvInfo.remark;
        // this.search.beginDateScope = this.MvInfo.beginDateScope;
        this.initMvInfo();
      } else if ((this.validateIt = 2)) {
        // 修改数据
        this.edit = JSON.parse(JSON.stringify(this.MvInfo));
        // this.edit.id = this.MvInfo.id;
        // this.edit.eid = this.MvInfo.eid;
        // this.edit.afterDepId = this.MvInfo.afterDepId;
        // this.edit.afterJobLevelId = this.MvInfo.afterJobLevelId;
        // this.edit.afterPositionId = this.MvInfo.afterPositionId;
        // this.edit.removeDate = this.MvInfo.removeDate;
        // this.edit.reason = this.MvInfo.reason;
        // this.edit.remark = this.MvInfo.remark;
        this.putRequest("/per/mv/", this.edit).then(resp => {
          if (resp) {
            this.initMvInfo();
          }
        });
        // 根据this.validateIt的值，判断传输哪些属性
      }
      this.closed();
    },
    closed() {
      Object.keys(this.MvInfo).forEach(key => (this.MvInfo[key] = null));
      Object.keys(this.edit).forEach(key => (this.edit[key] = null));
      Object.keys(this.MvInfos).forEach(key => (this.MvInfos[key] = null));
      this.validateIt = 0;
      this.dialogFormVisible = false;
      this.nameDisabled = false;
    },
    // 提供打开并控制弹出框输入内容
    showView() {
      this.dialogFormVisible = true;
      if (this.validateIt == 0) {
        // 如果this.validateIt的属性值为0，则为查询类型面板的输入框进行赋值，使其激活
        this.nameDisabled = false;
      } else {
        this.dateView = true;
        this.nameDisabled = true;
      }
    },
    // 表格数据加工处理函数
    formatterColumn(row, column) {
      return this.PrefixInteger(row.employee.id, 8);
    },
    // 不足 位数 前补 0
    PrefixInteger(num, length) {
      return (Array(length).join("0") + num).slice(-length);
    }
  },
  //生命周期 - 创建完成（访问当前this实例）
  created() {},
  //生命周期 - 挂载完成（访问DOM元素）
  mounted() {
    this.initMvInfo();
    this.emps = this.loadAllEmp();
    this.pos = this.loadAllPos();
    this.loadAllDep();
    this.jls = this.loadAllJls();
  }
};
</script>
<style>
/* @import url(); 引入css类 */
</style>