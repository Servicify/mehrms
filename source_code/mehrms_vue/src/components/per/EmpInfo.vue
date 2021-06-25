<!-- 员工岗位职称职务信息 -->
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
        :data="EmpInfos"
        size="large"
        border
        style="width: 60%;margin-top:10px"
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
          prop="name"
          label="员工姓名"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="department.name"
          label="所属部门"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="position.name"
          label="职位"
          width="100"
        ></el-table-column>
        <el-table-column prop="jobLevel.name" label="职称" width="100">
        </el-table-column>
        <el-table-column
          prop="jobLevel.titleLevel"
          label="职称级别"
          width="100"
        >
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              @click="editItem(scope.row)"
              icon="el-icon-edit"
              >岗位调整</el-button
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
        style="position:relative;left:-40%"
      >
      </el-pagination>
    </div>
    <!-- 添加、修改、查询操作弹出框 -->
    <div>
      <el-dialog :title="Title" :visible.sync="dialogFormVisible">
        <el-form :model="EmpInfo">
          <!-- 使用自定义模板员工信息输入框 -->
          <!-- 员工信息输入框 -->
          <!-- 查询员工信息需要选则员工的名子 -->
          <el-form-item>
            <el-tag type="success" size="large">选则员工信息</el-tag>
            <!-- 参考ElementUI中的Input输入框中的自定义模板 -->
            <el-autocomplete
              popper-class="my-autocomplete"
              v-model="EmpInfo.ename"
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
              v-model="EmpInfo.workID"
              disabled
              style="width:100px"
            ></el-input>
          </el-form-item>
          <!-- 使用自定义模板部门输入框 -->
          <!-- 部门输入框 -->
          <!-- 查询存在的部门信息名字 -->
          <el-form-item>
            <el-tag type="success" size="large"
              ><span style="color:red" v-if="this.validateIt != 0"
                >&nbsp;*&nbsp;</span
              >选则部门</el-tag
            >
            <el-popover
              placement="right"
              title="请选择部门"
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
                {{ EmpInfo.dname }}
              </div>
            </el-popover>
          </el-form-item>
          <!-- 使用自定义模板职位输入框 -->
          <!-- 职位信息输入框 -->
          <!-- 查询职位信息需要选则职位的名字 -->
          <el-form-item>
            <el-tag type="success" size="large"
              ><span style="color:red" v-if="this.validateIt != 0"
                >&nbsp;*&nbsp;</span
              >选则职位信息</el-tag
            >
            <!-- 参考ElementUI中的Input输入框中的自定义模板 -->
            <el-autocomplete
              popper-class="my-autocomplete"
              v-model="EmpInfo.pname"
              :fetch-suggestions="querySearchPos"
              placeholder="请输入职位信息内容"
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
          <!-- 使用自定义模板职称输入框 -->
          <!-- 职称信息输入框 -->
          <!-- 查询职称信息需要选则职位的名字 -->
          <el-form-item>
            <el-tag type="success" size="large"
              ><span style="color:red" v-if="this.validateIt != 0"
                >&nbsp;*&nbsp;</span
              >选则职称信息</el-tag
            >
            <!-- 参考ElementUI中的Input输入框中的自定义模板 -->
            <el-autocomplete
              popper-class="my-autocomplete"
              v-model="EmpInfo.jname"
              :fetch-suggestions="querySearchJls"
              placeholder="请输入职称信息内容"
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
  name: "EmpInfo",
  data() {
    return {
      EmpInfos: [],
      EmpInfo: {
        eid: null,
        workID: null,
        ename: null,
        did: null,
        dname: null,
        jid: null,
        jname: null,
        pid: null,
        pname: null
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
      // 弹出面板显示输入框类型值
      validateIt: 0,
      // 修改数据发送对象
      edit: {
        eid: null,
        did: null,
        jid: null,
        pid: null
      },
      // 查看数据发送对象
      search: {
        eid: null,
        did: null,
        jid: null,
        pid: null,
        ename: null,
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
      this.initEmpInfo();
    },
    // 调整页面显示第几页
    currentChange(currentPage) {
      this.page = currentPage;
      this.initEmpInfo();
    },
    // 初始化页面数据
    initEmpInfo() {
      let url = "/per/mv/emp/?page=" + this.page + "&size=" + this.size;
      if (this.search.id != null) {
        url += "&id=" + this.search.id;
      }
      if (this.search.departmentId != null) {
        url += "&departmentId=" + this.search.departmentId;
      }
      if (this.search.posId != null) {
        url += "&posId=" + this.search.posId;
      }
      if (this.search.jobLevelId != null) {
        url += "&jobLevelId=" + this.search.jobLevelId;
      }
      this.getRequest(url).then(resp => {
        if (resp) {
          this.EmpInfos = resp.data;
          this.total = resp.total;
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
      this.EmpInfo.ename = item.value;
      this.EmpInfo.eid = item.id;
      this.EmpInfo.workID = item.workID;
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
      this.EmpInfo.dname = data.name;
      this.EmpInfo.did = data.id;
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
      this.EmpInfo.pname = item.value;
      this.EmpInfo.pid = item.id;
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
      this.EmpInfo.jname = item.value;
      this.EmpInfo.jid = item.id;
    },
    editItem(row) {
      this.validateIt = 1;
      this.Title = "修改员工岗位信息";

      this.EmpInfo.eid = row.id;
      this.EmpInfo.ename = row.name;
      this.EmpInfo.workID = this.PrefixInteger(row.id, 8);
      this.EmpInfo.did = row.department.id;
      this.EmpInfo.dname = row.department.name;
      this.EmpInfo.jid = row.jobLevel.id;
      this.EmpInfo.jname = row.jobLevel.name;
      this.EmpInfo.pid = row.position.id;
      this.EmpInfo.pname = row.position.name;
      this.showView();
    },
    searchItem() {
      this.EmpInfo = JSON.parse(JSON.stringify(this.search));
      this.validateIt = 0;
      this.Title = "查找员工岗位信息";
      this.showView();
    },
    onSubmit() {
      if (this.validateIt == 0) {
        this.search.id = this.EmpInfo.eid;
        this.search.ename = this.EmpInfo.ename;
        this.search.pname = this.EmpInfo.pname;
        this.search.jname = this.EmpInfo.jname;
        this.search.dname = this.EmpInfo.dname;
        this.search.departmentId = this.EmpInfo.did;
        this.search.jobLevelId = this.EmpInfo.jid;
        this.search.positionId = this.EmpInfo.pid;
        this.initEmpInfo();
      } else if (this.validateIt == 1) {
        this.edit.id = this.EmpInfo.eid;
        this.edit.departmentId = this.EmpInfo.did;
        this.edit.jobLevelId = this.EmpInfo.jid;
        this.edit.posId = this.EmpInfo.pid;
        this.putRequest("/per/mv/emp", this.edit).then(resp => {
          if (resp) {
            this.initEmpInfo();
          }
        });
      }
      // 根据this.validateIt的值，判断传输哪些属性
      this.closed();
    },
    closed() {
      this.validateIt = 0;
      this.dialogFormVisible = false;
      this.nameDisabled = false;
    },
    // 刷新
    refresh() {
      Object.keys(this.edit).forEach(key => (this.edit[key] = null));
      Object.keys(this.search).forEach(key => (this.search[key] = null));
      Object.keys(this.EmpInfos).forEach(key => (this.EmpInfos[key] = null));
      Object.keys(this.EmpInfo).forEach(key => (this.EmpInfo[key] = null));
      this.initEmpInfo();
      this.$message({
        showClose: true,
        message: "刷新成功",
        type: "success"
      });
    },
    // 提供打开并控制弹出框输入内容
    showView() {
      this.dialogFormVisible = true;
      if (this.validateIt == 0) {
        // 如果this.validateIt的属性值为0，则为查询类型面板的输入框进行赋值，使其激活
        this.nameDisabled = false;
      } else if (this.validateIt == 1) {
        // 如果this.validateIt的属性值为1，则为添加类型面板的输入框进行赋值，使其激活
        this.nameDisabled = true;
      }
    },
    // 表格数据加工处理函数
    formatterColumn(row, column) {
      return this.PrefixInteger(row.id, 8);
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
    this.initEmpInfo();
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