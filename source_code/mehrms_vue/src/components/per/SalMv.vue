<!-- 员工调薪记录 -->
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
        :data="SalMvInfos"
        size="large"
        border
        style="width: 100%;margin-top:10px"
      >
        <el-table-column type="index" label="序号" fixed width="50">
        </el-table-column>
        <el-table-column
          width="130"
          prop="createDate"
          fixed
          label="创建时间"
        ></el-table-column>
        <el-table-column
          width="100"
          prop="employee.name"
          fixed
          label="员工姓名"
        ></el-table-column>
        <el-table-column
          width="120"
          prop="employee.workID"
          label="员工号"
        ></el-table-column>
        <el-table-column
          width="150"
          prop="department.name"
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
                    <td>{{ scope.row.basicSalary }}</td>
                  </tr>
                  <tr>
                    <td>交通补助</td>
                    <td>{{ scope.row.trafficSalary }}</td>
                  </tr>
                  <tr>
                    <td>午餐补助</td>
                    <td>{{ scope.row.lunchSalary }}</td>
                  </tr>
                  <tr>
                    <td>奖金</td>
                    <td>{{ scope.row.bonus }}</td>
                  </tr>
                  <tr>
                    <td>养老金比率</td>
                    <td>{{ scope.row.pensionPer }}</td>
                  </tr>
                  <tr>
                    <td>养老金基数</td>
                    <td>{{ scope.row.pensionBase }}</td>
                  </tr>
                  <tr>
                    <td>医疗保险比率</td>
                    <td>{{ scope.row.medicalPer }}</td>
                  </tr>
                  <tr>
                    <td>医疗保险基数</td>
                    <td>{{ scope.row.medicalBase }}</td>
                  </tr>
                  <tr>
                    <td>公积金比率</td>
                    <td>{{ scope.row.accumulationFundPer }}</td>
                  </tr>
                  <tr>
                    <td>公积金基数</td>
                    <td>{{ scope.row.accumulationFundBase }}</td>
                  </tr>
                  <tr>
                    <td>启用时间</td>
                    <td>{{ scope.row.createDate }}</td>
                  </tr>
                  <tr>
                    <td>小计</td>
                    <td>{{ scope.row.allSalary }}</td>
                  </tr>
                </table>
              </div>
              <el-tag>{{ scope.row.sname }}</el-tag>
            </el-tooltip>
            <el-tag v-else>暂未设置</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          width="100"
          prop="afterTimes"
          label="调整后倍数"
        ></el-table-column>
        <el-table-column
          width="100"
          prop="afterOthers"
          label="调整后其他补助"
        ></el-table-column>
        <el-table-column
          width="100"
          prop="afterDeduct"
          label="调整后减薪（罚款）"
        ></el-table-column>
        <el-table-column
          width="100"
          prop="afterPer"
          label="调整后绩效分值（元）"
        ></el-table-column>
        <el-table-column
          width="100"
          prop="afterCount"
          label="调整后工资合计"
        ></el-table-column>
        <el-table-column
          width="200"
          prop="remark"
          label="备注（原因）"
        ></el-table-column>
        <el-table-column label="操作" width="120" fixed="right">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              @click="editItem(scope.row)"
              icon="el-icon-edit"
              >修改记录</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页功能 -->
    <div
      style="display: flex;justify-content: flex-end;position:relative;right:-20%"
    >
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
        <el-form :model="SalMvInfo">
          <!-- 使用自定义模板员工信息输入框 -->
          <!-- 员工信息输入框 -->
          <!-- 查询员工信息需要选则员工的名子 -->
          <el-form-item>
            <el-tag type="success" size="large">员工信息</el-tag>
            <!-- 参考ElementUI中的Input输入框中的自定义模板 -->
            <el-autocomplete
              popper-class="my-autocomplete"
              v-model="SalMvInfo.ename"
              :fetch-suggestions="querySearchEmp"
              placeholder="请输入员工信息"
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
            <!-- 员工id输入框 -->
            <el-tag type="success" size="large">员工号</el-tag>
            <el-input
              v-model="SalMvInfo.workID"
              disabled
              style="width:100px"
            ></el-input>
            <!-- 部门输入框 -->
            <!-- 查询存在的部门信息名字 -->
            <el-tag type="success" size="large" v-if="this.validateIt != 1"
              >选则部门</el-tag
            >
            <el-popover
              placement="right"
              title="请选择部门"
              width="200"
              trigger="manual"
              v-model="popVisible"
              v-if="this.validateIt != 1"
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
                {{ SalMvInfo.dname }}
              </div>
            </el-popover>
          </el-form-item>
          <!-- 查询日期选则器 -->
          <el-form-item v-if="Input">
            <el-tag type="success" size="large">查询日期</el-tag>
            <el-date-picker
              v-model="SalMvInfo.beginDateScope"
              type="daterange"
              size="mini"
              unlink-panels
              value-format="yyyy-MM-dd"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </el-form-item>
          <!-- 账套名称 -->
          <el-form-item v-if="Input">
            <el-tag type="success" size="large">账套名称:</el-tag>
            <el-input
              type="text"
              :rows="2"
              style="width:200px"
              placeholder="请输入账套名称"
              v-model="SalMvInfo.sname"
            >
            </el-input>
            <!-- 账套小计 -->
            <el-tag type="success" size="large">账套小计:</el-tag>
            <el-input
              type="text"
              :rows="2"
              style="width:200px"
              placeholder="请输入账套小计"
              v-model="SalMvInfo.allSalary"
            >
            </el-input>
          </el-form-item>
          <!-- 调整后倍数 -->
          <el-form-item v-if="Input">
            <el-tag type="success" size="large">调整后倍数:</el-tag>
            <el-input
              type="text"
              :rows="2"
              style="width:200px"
              placeholder="请输入调整后倍数"
              v-model="SalMvInfo.afterTimes"
            >
            </el-input>
            <!-- 调整后其他补助 -->
            <el-tag type="success" size="large">调整后其他补助:</el-tag>
            <el-input
              type="text"
              :rows="2"
              style="width:200px"
              placeholder="请输入调整后其他补助"
              v-model="SalMvInfo.afterOthers"
            >
            </el-input>
          </el-form-item>
          <!-- 调整后减薪（罚款） -->
          <el-form-item v-if="Input">
            <el-tag type="success" size="large">调整后减薪（罚款）:</el-tag>
            <el-input
              type="text"
              :rows="2"
              style="width:200px"
              placeholder="请输入调整后减薪（罚款）数额"
              v-model="SalMvInfo.afterDeduct"
            >
            </el-input>
            <!-- 调整后绩效分值（元） -->
            <el-tag type="success" size="large">调整后绩效分值（元）:</el-tag>
            <el-input
              type="text"
              :rows="2"
              style="width:200px"
              placeholder="请输入调整后绩效分值（元）"
              v-model="SalMvInfo.afterPer"
            >
            </el-input>
          </el-form-item>
          <!-- 调整后合计 -->
          <el-form-item v-if="Input">
            <el-tag type="success" size="large">调整后合计:</el-tag>
            <el-input
              type="text"
              :rows="2"
              style="width:200px"
              placeholder="请输入调整后合计"
              v-model="SalMvInfo.afterCount"
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
              v-model="SalMvInfo.remark"
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
  name: "SalMv",
  data() {
    return {
      SalMvInfos: [],
      SalMvInfo: {
        id: null,
        createDate: null,
        eid: null,
        ename: null,
        did: null,
        dname: null,
        basicSalary: null,
        bonus: null,
        lunchSalary: null,
        trafficSalary: null,
        allSalary: null,
        pensionBase: null,
        pensionPer: null,
        medicalBase: null,
        medicalPer: null,
        accumulationFundBase: null,
        accumulationFundPer: null,
        sname: null,
        afterTimes: null,
        afterOthers: null,
        afterDeduct: null,
        afterPer: null,
        afterCount: null,
        remark: null,
        beginDateScope: null
      },
      // 员工信息自定义模板
      emps: [],
      // 部门信息自定义模板
      deps: [],
      // 页数
      // 总计多少页
      total: 0,
      // 默认显示第一页
      page: 1,
      size: 10,
      // 查询修改弹窗的标题
      Title: "查找员工调薪信息",
      nameDisabled: false,
      // 弹出面板
      dialogFormVisible: false,
      // 部门选则提示框的弹出
      popVisible: false,
      // 弹出框入职日期是否显示
      dateView: false,
      // 弹出面板框修改显示
      Input: false,
      // 弹出面板显示输入框类型值
      validateIt: 0,
      // 修改数据发送对象
      edit: {
        id: null,
        remark: null
      },
      // 查看数据发送对象
      search: {
        id: null,
        ename: null,
        workId: null,
        createDate: null,
        eid: null,
        dname: null,
        did: null,
        allSalary: null,
        sname: null,
        afterTimes: null,
        afterOthers: null,
        afterDeduct: null,
        afterPer: null,
        afterCount: null,
        remark: null,
        beginDateScope: null
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
      this.SalMvInfo.ename = item.value;
      this.SalMvInfo.eid = item.id;
      this.SalMvInfo.workID = item.workID;
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
      this.SalMvInfo.dname = data.name;
      this.SalMvInfo.did = data.id;
      this.popVisible = !this.popVisible;
    },
    refresh() {
      Object.keys(this.SalMvInfos).forEach(
        key => (this.SalMvInfos[key] = null)
      );
      Object.keys(this.edit).forEach(key => (this.edit[key] = null));
      Object.keys(this.SalMvInfo).forEach(key => (this.SalMvInfo[key] = null));
      Object.keys(this.search).forEach(key => (this.search[key] = null));
      this.initMvInfo();
      this.$message({
        showClose: true,
        message: "刷新成功",
        type: "success"
      });
    },
    // 初始化页面数据
    initMvInfo() {
      let url = "/per/salary/?page=" + this.page + "&size=" + this.size;
      if (this.search.sname != null) {
        url += "&sname=" + this.search.sname;
      }
      if (this.search.createDate != null) {
        url += "&createDate=" + this.search.createDate;
      }
      if (this.search.createDate != null) {
        url += "&createDate=" + this.search.createDate;
      }
      if (this.search.eid != null) {
        url += "&eid=" + this.search.eid;
      }
      if (this.search.did != null) {
        url += "&did=" + this.search.did;
      }
      if (this.search.allSalary != null) {
        url += "&allSalary=" + this.search.allSalary;
      }
      if (this.search.afterTimes != null) {
        url += "&afterTimes=" + this.search.afterTimes;
      }
      if (this.search.afterTimes != null) {
        url += "&afterTimes=" + this.search.afterTimes;
      }
      if (this.search.afterTimes != null) {
        url += "&afterTimes=" + this.search.afterTimes;
      }
      if (this.search.afterOthers != null) {
        url += "&afterOthers=" + this.search.afterOthers;
      }
      if (this.search.afterDeduct != null) {
        url += "&afterDeduct=" + this.search.afterDeduct;
      }
      if (this.search.afterPer != null) {
        url += "&afterPer=" + this.search.afterPer;
      }
      if (this.search.afterCount != null) {
        url += "&afterCount=" + this.search.afterCount;
      }
      if (this.search.remark != null) {
        url += "&remark=" + this.search.remark;
      }
      if (this.search.beginDateScope != null) {
        url += "&beginDateScope=" + this.search.beginDateScope;
      }
      this.getRequest(url).then(resp => {
        if (resp) {
          this.SalMvInfos = resp.data;
          this.total = resp.total;
          // 示范
        }
      });
    },
    //
    searchItem() {
      this.SalMvInfo = JSON.parse(JSON.stringify(this.search));
      this.validateIt = 0;
      this.Title = "查找员工薪资调整信息";
      this.showView();
    },
    editItem(row) {
      this.validateIt = 1;
      this.Title = "修改员工薪资调整信息";
      this.SalMvInfo.id = row.id;
      this.SalMvInfo.ename = row.employee.name;
      this.SalMvInfo.workID = row.employee.workID;
      this.SalMvInfo.dname = row.department.name;
      this.SalMvInfo.remark = row.remark;
      this.showView();
    },
    onSubmit() {
      if (this.validateIt == 0) {
        this.search.sname = this.SalMvInfo.sname;
        this.search.beginDateScope = this.SalMvInfo.beginDateScope;
        this.search.ename = this.SalMvInfo.ename;
        this.search.did = this.SalMvInfo.did;
        this.search.eid = this.SalMvInfo.eid;
        this.search.workID = this.SalMvInfo.workID;
        this.search.allSalary = this.SalMvInfo.allSalary;
        this.search.afterTimes = this.SalMvInfo.afterTimes;
        this.search.afterOthers = this.SalMvInfo.afterOthers;
        this.search.afterDeduct = this.SalMvInfo.afterDeduct;
        this.search.afterPer = this.SalMvInfo.afterPer;
        this.search.afterCount = this.SalMvInfo.afterCount;
        this.search.remark = this.SalMvInfo.remark;
        this.initMvInfo();
      }
      if (this.validateIt == 1) {
        // 修改数据
        this.edit.id = this.SalMvInfo.id;
        this.edit.remark = this.SalMvInfo.remark;
        this.putRequest("/per/salary/", this.edit).then(resp => {
          if (resp) {
            this.initMvInfo();
          }
        });
        // 根据this.validateIt的值，判断传输哪些属性
      }
      this.closed();
    },
    closed() {
      Object.keys(this.SalMvInfos).forEach(
        key => (this.SalMvInfos[key] = null)
      );
      Object.keys(this.edit).forEach(key => (this.edit[key] = null));
      Object.keys(this.SalMvInfo).forEach(key => (this.SalMvInfo[key] = null));
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
        this.Input = true;
      }
      if (this.validateIt == 1) {
        this.dateView = true;
        this.nameDisabled = true;
        this.Input = false;
      }
    }
  },
  //生命周期 - 创建完成（访问当前this实例）
  created() {},
  //生命周期 - 挂载完成（访问DOM元素）
  mounted() {
    this.initMvInfo();
    this.emps = this.loadAllEmp();
    this.loadAllDep();
  }
};
</script>
<style>
/* @import url(); 引入css类 */
</style>