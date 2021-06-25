<!-- 员工当前工资信息 -->
<!-- 
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
-->
<template>
  <div>
    <div>
      <!-- 添加，查询，刷新按钮 -->
      <div>
        <el-button
          type="success"
          size="large"
          @click="validateItem"
          icon="el-icon-refresh"
          >校准员工信息</el-button
        >
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
        <el-button
          type="success"
          size="large"
          @click="resetRecordPer"
          icon="el-icon-refresh"
          >一键重设绩效值</el-button
        >
      </div>
      <!-- 数据显示区域 -->
      <div>
        <el-table
          :data="SalInfos"
          size="large"
          border
          style="width: 100%;margin-top:10px"
        >
          <el-table-column type="index" label="序号" fixed width="50">
          </el-table-column>
          <el-table-column
            width="100"
            prop="employee.workID"
            fixed
            label="员工号"
          ></el-table-column>
          <el-table-column
            width="90"
            prop="employee.name"
            fixed
            label="员工姓名"
          ></el-table-column>
          <el-table-column
            width="120"
            prop="department.name"
            fixed
            label="所属部门"
          ></el-table-column>
          <el-table-column label="账套类别" align="center" width="300">
            <template slot-scope="scope">
              <!-- 参考ElementUI的文字提示右侧提示 -->
              <!-- 由于新入职员工的账套并未设置，所以此处使用v-if="scope.row.salary"
                 来显示已经设置好工资账套的员工，使用v-else设置显示尚未设置账套信息的
                 员工信息 -->
              <el-tooltip placement="right" v-if="scope.row">
                <div slot="content">
                  <table>
                    <tr>
                      <td>基本工资</td>
                      <td>{{ scope.row.salary.basicSalary }}</td>
                    </tr>
                    <tr>
                      <td>交通补助</td>
                      <td>{{ scope.row.salary.trafficSalary }}</td>
                    </tr>
                    <tr>
                      <td>午餐补助</td>
                      <td>{{ scope.row.salary.lunchSalary }}</td>
                    </tr>
                    <tr>
                      <td>奖金</td>
                      <td>{{ scope.row.salary.bonus }}</td>
                    </tr>
                    <tr>
                      <td>养老金比率</td>
                      <td>{{ scope.row.salary.pensionPer }}</td>
                    </tr>
                    <tr>
                      <td>养老金基数</td>
                      <td>{{ scope.row.salary.pensionBase }}</td>
                    </tr>
                    <tr>
                      <td>医疗保险比率</td>
                      <td>{{ scope.row.salary.medicalPer }}</td>
                    </tr>
                    <tr>
                      <td>医疗保险基数</td>
                      <td>{{ scope.row.salary.medicalBase }}</td>
                    </tr>
                    <tr>
                      <td>公积金比率</td>
                      <td>{{ scope.row.salary.accumulationFundPer }}</td>
                    </tr>
                    <tr>
                      <td>公积金基数</td>
                      <td>{{ scope.row.salary.accumulationFundBase }}</td>
                    </tr>
                    <tr>
                      <td>启用时间</td>
                      <td>{{ scope.row.salary.createDate }}</td>
                    </tr>
                    <tr>
                      <td>小计</td>
                      <td>{{ scope.row.salary.allSalary }}</td>
                    </tr>
                  </table>
                </div>
                <el-tag>{{ scope.row.salary.name }}</el-tag>
              </el-tooltip>
              <el-tag v-else>暂未设置</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            width="150"
            prop="times"
            label="倍数"
          ></el-table-column>
          <el-table-column
            width="150"
            prop="others"
            label="其他补助"
          ></el-table-column>
          <el-table-column
            width="150"
            prop="deduct"
            label="减薪（罚款）"
          ></el-table-column>
          <el-table-column
            width="170"
            prop="recordPer"
            label="绩效分值（元）"
          ></el-table-column>
          <el-table-column
            width="150"
            prop="count"
            label="目前工资合计"
          ></el-table-column>
          <el-table-column label="操作" width="120" fixed="right">
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
      <div
        style="display: flex;justify-content: flex-end;position:relative;right:-40%"
      >
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
          <el-form :model="SalInfo">
            <el-form-item v-if="InputEmp">
              <!-- 使用自定义模板员工信息输入框 -->
              <!-- 员工信息输入框 -->
              <!-- 查询员工信息需要选则员工的名子 -->
              <el-tag type="success" size="large">选则员工信息</el-tag>
              <!-- 参考ElementUI中的Input输入框中的自定义模板 -->
              <el-autocomplete
                popper-class="my-autocomplete"
                v-model="SalInfo.ename"
                :fetch-suggestions="querySearchEmp"
                placeholder="请输入员工信息内容"
                @select="handleSelectEmp"
                :disabled="this.validateIt != 0"
                clearable
              >
                <i class="el-icon-edit el-input__icon" slot="suffix"> </i>
                <template slot-scope="{ item }">
                  <div class="name">{{ item.value }}</div>
                  <span class="addr">{{ item.workID }}</span>
                </template>
              </el-autocomplete>
              <!-- 员工号输入框 -->
              <el-tag type="success" size="large">员工号</el-tag>
              <el-input
                v-model="SalInfo.workID"
                disabled
                style="width:100px"
              ></el-input>
            </el-form-item>
            <el-form-item v-if="InputDep">
              <el-tag type="success" size="large">所属部门：</el-tag>
              <el-input
                v-model="SalInfo.dname"
                disabled
                style="width:100px"
              ></el-input>
            </el-form-item>
            <el-form-item v-if="InputSalary">
              <el-tag type="success" size="large">账套类别：</el-tag>
              <el-input
                v-model="SalInfo.sname"
                disabled
                style="width:100px"
              ></el-input>
            </el-form-item>
            <el-form-item v-if="InputTimesAndOthers">
              <el-tag type="success" size="large"
                ><span style="color:red" v-if="this.validateIt != 0"
                  >&nbsp;*&nbsp;</span
                >倍数：</el-tag
              >
              <el-input v-model="SalInfo.times" style="width:100px"></el-input>

              <el-tag type="success" size="large"
                ><span style="color:red" v-if="this.validateIt != 0"
                  >&nbsp;*&nbsp;</span
                >其他补助：</el-tag
              >
              <el-input v-model="SalInfo.others" style="width:100px"></el-input>
            </el-form-item>
            <el-form-item>
              <el-tag type="success" size="large" v-if="InputDeduct"
                ><span style="color:red" v-if="this.validateIt != 0"
                  >&nbsp;*&nbsp;</span
                >减薪（罚款）：</el-tag
              >
              <el-input
                v-model="SalInfo.deduct"
                style="width:100px"
                v-if="InputDeduct"
              ></el-input>
              <el-tag type="success" size="large"
                ><span style="color:red" v-if="this.validateIt != 0"
                  >&nbsp;*&nbsp;</span
                >绩效分值：</el-tag
              >
              <el-input
                v-model="SalInfo.recordPer"
                style="width:100px"
              ></el-input>
            </el-form-item>
            <el-form-item v-if="InputCount">
              <el-tag type="success" size="large">合计：</el-tag>
              <el-input
                v-model="SalInfo.count"
                style="width:100px"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">确认</el-button>
              <el-button @click="closed">取消</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SalInfo",
  data() {
    return {
      SalInfos: [],
      SalInfo: {
        // args_role对象参数
        id: null,
        eid: null,
        times: null,
        others: null,
        deduct: null,
        recordPer: null,
        count: null,
        // 员工对象参数
        workID: null,
        ename: null,
        // 账套名称
        sname: null,
        // 部门名称
        dname: null
      },
      // 员工信息自定义模板
      emps: [],
      // 页数
      // 总计多少页
      total: 0,
      // 默认显示第一页
      page: 1,
      size: 10,
      // 查询修改弹窗的标题
      Title: "调整员工当前薪资信息",
      // 弹出面板
      dialogFormVisible: false,
      nameDisabled: true,
      // 弹出面板显示输入框类型值
      validateIt: 0,
      // 控制弹出框显示项
      InputEmp: false,
      InputDep: false,
      InputSalary: false,
      InputTimesAndOthers: false,
      InputDeduct: false,
      InputCount: false,
      // 修改数据发送对象
      edit: {
        id: null,
        eid: null,
        times: null,
        others: null,
        deduct: null,
        recordPer: null,
        count: null
      },
      // 查看数据发送对象
      search: {
        workID: null,
        ename: null,
        eid: null,
        times: null,
        others: null,
        deduct: null,
        recordPer: null,
        count: null,
        sname: null
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
      this.initSalInfo();
    },
    // 调整页面显示第几页
    currentChange(currentPage) {
      this.page = currentPage;
      this.initSalInfo();
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
      this.SalInfo.ename = item.value;
      this.SalInfo.eid = item.id;
      this.SalInfo.workID = item.workID;
    },
    // 初始化页面数据
    initSalInfo() {
      let url = "/per/salary/emp/?page=" + this.page + "&size=" + this.size;
      if (this.search.eid != null) {
        url += "&eid=" + this.search.eid;
      }
      if (this.search.times != null) {
        url += "&times=" + this.search.times;
      }
      if (this.search.others != null) {
        url += "&others=" + this.search.others;
      }
      if (this.search.deduct != null) {
        url += "&deduct=" + this.search.deduct;
      }
      if (this.search.recordPer != null) {
        url += "&recordPer=" + this.search.recordPer;
      }
      if (this.search.count != null) {
        url += "&count=" + this.search.count;
      }
      if (this.search.sname != null) {
        url += "&sname=" + this.search.sname;
      }
      this.getRequest(url).then(resp => {
        if (resp) {
          this.SalInfos = resp.data;
          this.total = resp.total;
        }
      });
    },
    refresh() {
      Object.keys(this.SalInfo).forEach(key => (this.SalInfo[key] = null));
      Object.keys(this.SalInfos).forEach(key => (this.SalInfos[key] = null));
      Object.keys(this.search).forEach(key => (this.search[key] = null));
      Object.keys(this.edit).forEach(key => (this.edit[key] = null));
      this.initSalInfo();
      this.$message({
        showClose: true,
        message: "刷新成功",
        type: "success"
      });
    },
    //
    editItem(row) {
      this.validateIt = 1;
      this.Title = "修改该员工目前工资信息";
      this.SalInfo.id = row.id;
      this.SalInfo.eid = row.eid;
      this.SalInfo.times = row.times;
      this.SalInfo.others = row.others;
      this.SalInfo.deduct = row.deduct;
      this.SalInfo.recordPer = row.recordPer;
      this.SalInfo.count = row.count;
      this.SalInfo.workID = row.employee.workID;
      this.SalInfo.ename = row.employee.name;
      this.SalInfo.sname = row.salary.name;
      this.SalInfo.dname = row.department.name;
      this.showView();
    },
    searchItem() {
      this.validateIt = 0;
      this.Title = "查找员工目前薪资信息";
      this.SalInfo = JSON.parse(JSON.stringify(this.search));
      this.showView();
    },
    resetRecordPer() {
      this.validateIt = 2;
      this.Title = "统一更改所有员工的绩效分值信息";
      this.showView();
    },
    validateItem() {
      this.getRequest("/per/salary/emp/validate").then(resp => {
        if (resp) {
          this.initSalInfo();
          // 示范
          Object.keys(this.SalInfo).forEach(key => (this.SalInfo[key] = null));
        }
      });
    },
    onSubmit() {
      if (this.validateIt == 0) {
        this.search.workID = this.SalInfo.workID;
        this.search.ename = this.SalInfo.ename;
        this.search.id = this.SalInfo.id;
        this.search.eid = this.SalInfo.eid;
        this.search.times = this.SalInfo.times;
        this.search.others = this.SalInfo.others;
        this.search.deduct = this.SalInfo.deduct;
        this.search.recordPer = this.SalInfo.recordPer;
        this.search.count = this.SalInfo.count;
        this.initSalInfo();
      } else if (this.validateIt == 1) {
        this.edit.id = this.SalInfo.id;
        this.edit.eid = this.SalInfo.eid;
        this.edit.times = this.SalInfo.times;
        this.edit.others = this.SalInfo.others;
        this.edit.deduct = this.SalInfo.deduct;
        this.edit.recordPer = this.SalInfo.recordPer;
        this.edit.count = this.SalInfo.count;
        this.putRequest("/per/salary/emp/", this.edit).then(resp => {
          if (resp) {
            this.initSalInfo();
            Object.keys(this.SalInfo).forEach(
              key => (this.SalInfo[key] = null)
            );
            Object.keys(this.edit).forEach(key => (this.edit[key] = null));
          }
        });
      } else {
        this.edit.recordPer = this.SalInfo.recordPer;
        this.putRequest("/per/salary/emp/recordper", this.edit).then(resp => {
          if (resp) {
            this.initSalInfo();
            Object.keys(this.SalInfo).forEach(
              key => (this.SalInfo[key] = null)
            );
            Object.keys(this.edit).forEach(key => (this.edit[key] = null));
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
    // 提供打开并控制弹出框输入内容
    showView() {
      this.dialogFormVisible = true;
      if (this.validateIt == 0) {
        // 查询
        // 名字选则
        this.nameDisabled = false;
        this.InputEmp = true;
        this.InputDep = false;
        this.InputSalary = false;
        this.InputTimesAndOthers = true;
        this.InputDeduct = true;
        this.InputCount = false;
      } else if (this.validateIt == 1) {
        // 修改
        // 名字选则
        this.nameDisabled = true;
        this.InputEmp = true;
        this.InputDep = true;
        this.InputSalary = true;
        this.InputTimesAndOthers = true;
        this.InputDeduct = true;
        this.InputCount = true;
      } else {
        // 绩效分值更改
        // 名字选则
        this.nameDisabled = true;
        this.InputEmp = false;
        this.InputDep = false;
        this.InputSalary = false;
        this.InputTimesAndOthers = false;
        this.InputDeduct = false;
        this.InputCount = false;
      }
    }
  },
  //生命周期 - 创建完成（访问当前this实例）
  created() {},
  //生命周期 - 挂载完成（访问DOM元素）
  mounted() {
    this.initSalInfo();
    this.loadAllEmp();
  }
};
</script>
<style>
/* @import url(); 引入css类 */
</style>