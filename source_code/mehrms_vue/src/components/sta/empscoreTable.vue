<!-- 员工积分统计信息表格显示组件 -->
<template>
  <div>
    <!-- 数据显示区域 -->
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
      <el-table :data="SalSearchs" style="width: 100%" stripe>
        <el-table-column prop="id" label="序号" width="55" fixed>
        </el-table-column>
        <el-table-column
          prop="employee.name"
          label="员工名字"
          fixed
          width="120"
        >
        </el-table-column>
        <el-table-column prop="employee.workID" label="员工号" width="150">
        </el-table-column>
        <el-table-column prop="startDate" label="开始时间" width="150">
        </el-table-column>
        <el-table-column prop="endDate" label="截止时间" width="150">
        </el-table-column>
        <el-table-column prop="dname" label="所属部门" width="150">
        </el-table-column>
        <el-table-column prop="pname" label="职位" width="150">
        </el-table-column>
        <el-table-column prop="jname" label="职称" width="150">
        </el-table-column>
        <el-table-column prop="workAge" label="工龄" width="150">
        </el-table-column>
        <el-table-column
          prop="accountsAdvInfo.recordScore"
          label="当月绩效总分"
          width="120"
          fixed="right"
        >
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
    <div>
      <el-dialog :title="Title" :visible.sync="dialogFormVisible1">
        <!-- 员工姓名输入框 -->
        <div>
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            >员工信息</el-tag
          >
          <!-- 参考ElementUI中的Input输入框中的自定义模板 -->
          <el-autocomplete
            popper-class="my-autocomplete"
            v-model="SalSearch.ename"
            :fetch-suggestions="querySearchEmp"
            placeholder="请输入员工信息"
            @select="handleSelectEmp"
            clearable
            style="margin:7px 4px 7px 0px "
          >
            <i class="el-icon-edit el-input__icon" slot="suffix"> </i>
            <template slot-scope="{ item }">
              <div class="name">{{ item.value }}</div>
              <span class="addr">{{ item.workID }}</span>
            </template>
          </el-autocomplete>
        </div>
        <!-- 员工号输入框 -->
        <div>
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            >员工号</el-tag
          >
          <el-input
            v-model="SalSearch.workID"
            disabled
            style="margin:7px 4px 7px 0px ;width:100px"
          ></el-input>
        </div>
        <!-- 选则部门输入框 -->
        <div>
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            >选则部门</el-tag
          >
          <el-popover
            placement="right"
            title="请选择部门"
            width="200"
            style="margin:7px 4px 7px 0px "
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
              style="width: 130px;display: inline-flex;font-size: 13px;border: 1px solid #dedede;height:32px;border-radius: 5px;cursor: pointer;align-items: center;padding-left: 8px;box-sizing: border-box;margin-left: 3px;position:relative;
              top:10px"
              @click="showDepView"
            >
              {{ SalSearch.dname }}
            </div>
          </el-popover>
        </div>
        <!-- 选则职位输入框 -->
        <div>
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            >选则职位信息</el-tag
          >
          <!-- 参考ElementUI中的Input输入框中的自定义模板 -->
          <el-autocomplete
            popper-class="my-autocomplete"
            v-model="SalSearch.pname"
            :fetch-suggestions="querySearchPos"
            placeholder="请输入职位信息内容"
            @select="handleSelectPos"
            style="margin:7px 4px 7px 0px "
            clearable
          >
            <i class="el-icon-edit el-input__icon" slot="suffix"> </i>
            <template slot-scope="{ item }">
              <div class="name">{{ item.value }}</div>
              <span class="addr">{{ item.id }}</span>
            </template>
          </el-autocomplete>
        </div>
        <!-- 选则职称输入框 -->
        <div>
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            >选则职称信息</el-tag
          >
          <!-- 参考ElementUI中的Input输入框中的自定义模板 -->
          <el-autocomplete
            popper-class="my-autocomplete"
            v-model="SalSearch.jname"
            :fetch-suggestions="querySearchJls"
            placeholder="请输入职称信息内容"
            @select="handleSelectJls"
            style="margin:7px 4px 7px 0px "
            clearable
          >
            <i class="el-icon-edit el-input__icon" slot="suffix"> </i>
            <template slot-scope="{ item }">
              <div class="name">{{ item.value }}</div>
              <span class="addr">{{ item.id }}</span>
            </template>
          </el-autocomplete>
        </div>
        <!-- 积分创建时间 -->
        <div>
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            >积分创建时间:</el-tag
          >
          <el-date-picker
            v-model="SalSearch.beginDateScope"
            type="daterange"
            size="mini"
            style="margin:7px 4px 7px 0px "
            unlink-panels
            value-format="yyyy-MM-dd"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </div>
        <br />
        <el-button style="margin-top: 12px;" @click="onSubmit" type="primary"
          >确认</el-button
        >
        <el-button style="margin-top: 12px;" @click="closed">取消</el-button>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "SalSearch",
  data() {
    return {
      SalSearchs: [],
      SalSearch: {
        id: null,
        workID: null,
        ename: null,
        eid: null,
        did: null,
        dname: null,
        pid: null,
        pname: null,
        jid: null,
        jname: null,
        beginDateScope: null
      },
      search: {
        workID: null,
        eid: null,
        dname: null,
        pname: null,
        jname: null,
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
      Title: "查询员工工资记录",
      buttonTitleConfirm: "下一步",
      buttonTitleCancel: "取消",
      // 弹出面板
      dialogFormVisible1: false,
      // 部门选则提示框的弹出
      popVisible: false,
      // 弹出面板显示输入框类型值
      validateIt: 0,
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
      this.initSalSearch();
    },
    // 调整页面显示第几页
    currentChange(currentPage) {
      this.page = currentPage;
      this.initSalSearch();
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
      this.SalSearch.ename = item.value;
      this.SalSearch.eid = item.id;
      this.SalSearch.workID = item.workID;
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
      this.SalSearch.dname = data.name;
      this.SalSearch.did = data.id;
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
      this.SalSearch.pname = item.value;
      this.SalSearch.pid = item.id;
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
      this.SalSearch.jname = item.value;
      this.SalSearch.jid = item.id;
    },
    // 刷新
    refresh() {
      Object.keys(this.SalSearch).forEach(key => (this.SalSearch[key] = null));
      Object.keys(this.search).forEach(key => (this.search[key] = null));
      this.initSalSearch();
      this.$message({
        showClose: true,
        message: "刷新成功",
        type: "success"
      });
    },
    // 初始化页面数据
    // 初始化页面数据
    initSalSearch() {
      let url = "/sal/search/?page=" + this.page + "&size=" + this.size;
      if (this.search.dname != null) {
        url += "&dname=" + this.search.dname;
      }
      if (this.search.pname != null) {
        url += "&pname=" + this.search.pname;
      }
      if (this.search.jname != null) {
        url += "&jname=" + this.search.jname;
      }
      if (this.search.eid != null) {
        url += "&eid=" + this.search.eid;
      }
      if (this.search.beginDateScope != null) {
        url += "&beginDateScope=" + this.search.beginDateScope;
      }
      this.getRequest(url).then(resp => {
        if (resp) {
          this.SalSearchs = resp.data;
          this.total = resp.total;
        }
      });
    },
    searchItem() {
      this.validateIt = 0;
      this.Title = "查找员工积分信息";
      this.showView();
    },
    onSubmit() {
      if (this.validateIt == 0) {
        this.search.eid = this.SalSearch.eid;
        this.search.dname = this.SalSearch.dname;
        this.search.jname = this.SalSearch.jname;
        this.search.pname = this.SalSearch.pname;
        this.search.beginDateScope = this.SalSearch.beginDateScope;
        this.initSalSearch();
        this.closed();
      }
    },
    closed() {
      this.validateIt = 0;
      this.dialogFormVisible1 = false;
    },
    // 提供打开并控制弹出框输入内容
    showView() {
      if (this.validateIt == 0) {
        // 如果this.validateIt的属性值为0，则为查询类型面板的输入框进行赋值，使其激活
        this.dialogFormVisible1 = true;
      }
    }
  },
  //生命周期 - 创建完成（访问当前this实例）
  created() {},
  //生命周期 - 挂载完成（访问DOM元素）
  mounted() {
    this.initSalSearch();
    this.emps = this.loadAllEmp();
    this.pos = this.loadAllPos();
    this.loadAllDep();
    this.jls = this.loadAllJls();
  }
};
</script>
<style scoped>
/* @import url(); 引入css类 */
</style>
<!-- 
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
-->