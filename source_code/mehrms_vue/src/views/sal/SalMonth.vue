<!-- 月末处理 -->
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
      <el-button
        type="danger"
        size="large"
        style="margin-top: 10px"
        :disabled="compute.length == 0"
        @click="SelectMany"
        icon="el-icon-d-arrow-right"
        >月末处理
      </el-button>
    </div>
    <!-- 数据显示区域 -->
    <div>
      <el-table
        :data="SalMonths"
        style="width: 100%"
        ref="Salserch"
        stripe
        @selection-change="handleSelectionIdsChange"
      >
        <el-table-column type="selection" width="55" fixed> </el-table-column>
        <el-table-column type="index" label="序号" fixed width="50">
        </el-table-column>
        <el-table-column prop="createDate" label="创建时间" fixed width="150">
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
                </table>
              </div>
              <el-tag>{{ scope.row.sname }}</el-tag>
            </el-tooltip>
            <el-tag v-else>暂未设置</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="times" label="工资翻倍" width="120">
        </el-table-column>
        <el-table-column prop="others" label="其他补助" width="120">
        </el-table-column>
        <el-table-column prop="deduct" label="减薪（罚款）" width="120">
        </el-table-column>
        <el-table-column prop="prepaid" label="预支工资" width="120">
        </el-table-column>
        <el-table-column label="当月绩效">
          <el-table-column
            prop="accountsAdvInfo.recordScore"
            label="当月绩效总分"
            width="120"
          >
          </el-table-column>
          <el-table-column
            prop="recordPer"
            label="当月绩效分值（元）"
            width="120"
          >
          </el-table-column>
        </el-table-column>
        <el-table-column label="合计">
          <el-table-column
            prop="accountsAdvInfo.recordSalary"
            label="当月绩效奖金"
            width="120"
          >
          </el-table-column>
          <el-table-column
            label="应发"
            prop="accountsAdvInfo.count"
            width="120"
          >
          </el-table-column>
          <el-table-column label="实际发放" prop="realSalary" width="120">
          </el-table-column>
          <el-table-column
            label="赊欠员工"
            prop="accountsAdvInfo.creditEmp"
            width="120"
          >
          </el-table-column>
          <el-table-column
            label="赊欠公司"
            prop="accountsAdvInfo.creditCom"
            width="120"
          >
          </el-table-column>
        </el-table-column>
        <el-table-column label="备注" prop="remark" width="120">
        </el-table-column>
        <el-table-column label="出纳人签名" prop="cashierName" width="120">
        </el-table-column>
        <el-table-column label="签名时间" prop="sendDate" width="120">
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template slot-scope="scope">
            <el-button
              type="danger"
              size="large"
              @click="comItem(scope.row)"
              icon="el-icon-d-arrow-right"
              >月末处理</el-button
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
    <div>
      <el-dialog :title="Title" :visible.sync="dialogFormVisible1">
        <el-steps :active="active" finish-status="success">
          <el-step title="步骤 1"> </el-step>
          <el-step title="步骤 2"> </el-step>
          <el-step title="步骤 3"> </el-step>
          <el-step title="步骤 4"> </el-step>
        </el-steps>
        <!-- 员工姓名输入框 -->
        <div v-if="this.active == 0">
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            >员工信息</el-tag
          >
          <!-- 参考ElementUI中的Input输入框中的自定义模板 -->
          <el-autocomplete
            popper-class="my-autocomplete"
            v-model="SalMonth.ename"
            :fetch-suggestions="querySearchEmp"
            placeholder="请输入员工信息"
            @select="handleSelectEmp"
            style="margin:7px 4px 7px 0px"
            clearable
          >
            <i class="el-icon-edit el-input__icon" slot="suffix"> </i>
            <template slot-scope="{ item }">
              <div class="name">{{ item.value }}</div>
              <span class="addr">{{ item.workID }}</span>
            </template>
          </el-autocomplete>
        </div>
        <!-- 员工号输入框 -->
        <div v-if="this.active == 0">
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            >员工号</el-tag
          >
          <el-input
            v-model="SalMonth.workID"
            disabled
            style="width:100px;margin:7px 4px 7px 0px"
          ></el-input>
        </div>
        <!-- 选则部门输入框 -->
        <div v-if="this.active == 0">
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
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
              style="width: 130px;display: inline-flex;font-size: 13px;border: 1px solid #dedede;height: 32px;border-radius: 5px;cursor: pointer;align-items: center;padding-left: 8px;box-sizing: border-box;margin-left: 3px;position:relative;
              top:10px"
              @click="showDepView"
            >
              {{ SalMonth.dname }}
            </div>
          </el-popover>
        </div>
        <!-- 选则职位输入框 -->
        <div v-if="this.active == 0">
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            >选则职位信息</el-tag
          >
          <!-- 参考ElementUI中的Input输入框中的自定义模板 -->
          <el-autocomplete
            popper-class="my-autocomplete"
            v-model="SalMonth.pname"
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
        <div v-if="this.active == 0">
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            >选则职称信息</el-tag
          >
          <!-- 参考ElementUI中的Input输入框中的自定义模板 -->
          <el-autocomplete
            popper-class="my-autocomplete"
            v-model="SalMonth.jname"
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
        <!-- 账套日期 -->
        <div v-if="this.active == 1">
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            >创建日期:</el-tag
          >
          <el-date-picker
            v-model="SalMonth.beginDateScope"
            type="daterange"
            size="mini"
            unlink-panels
            value-format="yyyy-MM-dd"
            style="margin:7px 4px 7px 0px "
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker>
        </div>
        <!-- 签名 -->
        <div v-if="this.active == 1">
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            >出纳人:</el-tag
          >
          <el-input
            size="mini"
            style="margin:7px 4px 7px 0px;width:40% "
            v-model="SalMonth.cashierName"
          ></el-input>
        </div>
        <!-- 工龄 -->
        <div v-if="this.active == 1">
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            >工龄:</el-tag
          >
          <el-input
            v-model="SalMonth.workAge"
            width="70"
            style="margin:7px 4px 7px 0px;width:40% "
          ></el-input>
        </div>
        <!-- 实发工资范围 -->
        <div v-if="this.active == 2">
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            >实发工资:</el-tag
          >
          <el-input
            v-model="SalMonth.RealSalaryScope1"
            width="70"
            style="margin:7px 4px 7px 0px;width:20%"
          ></el-input>
          至
          <el-input
            v-model="SalMonth.RealSalaryScope2"
            width="70"
            style="margin:7px 4px 7px 0px;width:20%"
          ></el-input>
        </div>
        <!-- 赊欠员工范围 -->
        <div v-if="this.active == 2">
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            >赊欠员工:</el-tag
          >
          <el-input
            v-model="SalMonth.CreditEmpScope1"
            width="70"
            style="margin:7px 4px 7px 0px;width:20% "
          ></el-input>
          至
          <el-input
            v-model="SalMonth.CreditEmpScope2"
            width="70"
            style="margin:7px 4px 7px 0px;width:20% "
          ></el-input>
        </div>
        <!-- 赊欠公司范围 -->
        <div v-if="this.active == 2">
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            >赊欠公司:</el-tag
          >
          <el-input
            v-model="SalMonth.CreditComScope1"
            width="70"
            style="margin:7px 4px 7px 0px;width:20% "
          ></el-input>
          至
          <el-input
            v-model="SalMonth.CreditComScope2"
            width="70"
            style="margin:7px 4px 7px 0px;width:20%"
          ></el-input>
        </div>
        <!-- 备注信息 -->
        <div v-if="this.active == 3">
          <el-tag
            type="success"
            size="large"
            style="margin:7px 4px 7px 0px;position:relative;top:-20px"
            >备注内容:</el-tag
          >
          <el-input
            type="textarea"
            :rows="2"
            placeholder="请输入备注内容"
            v-model="SalMonth.remark"
            style="margin:7px 4px 7px 0px;width:70%; "
          >
          </el-input>
        </div>
        <!-- 上一步按钮 -->
        <el-button style="margin-top: 12px;" @click="last">{{
          buttonTitleCancel
        }}</el-button>
        <!-- 下一步按钮 -->
        <el-button style="margin-top: 12px;" @click="next">{{
          buttonTitleConfirm
        }}</el-button>
      </el-dialog>
      <el-dialog :title="Title" :visible.sync="dialogFormVisible3">
        <el-tag type="success" size="large" style="margin:7px 4px 7px 0px;"
          >出纳人签名:</el-tag
        >
        <el-input
          type="text"
          placeholder="请输入出纳人签名"
          v-model="SalMonth.cashierName"
          style="margin:7px 4px 7px 0px;width:40%"
        >
        </el-input>
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
  name: "SalMonth",
  data() {
    return {
      SalMonths: [],
      SalMonth: {
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
        workAge: null,
        cashierName: null,
        basicSalary: null,
        bonus: null,
        lunchSalary: null,
        trafficSalary: null,
        pensionBase: null,
        pensionPer: null,
        medicalBase: null,
        medicalPer: null,
        accumulationFundBase: null,
        accumulationFundPer: null,
        times: null,
        others: null,
        deduct: null,
        prepaid: null,
        recordPer: null,
        realSalary: null,
        RealSalaryScope1: null,
        RealSalaryScope2: null,
        RealSalaryScope: null,
        CreditEmpScope1: null,
        CreditEmpScope2: null,
        CreditEmpScope: null,
        CreditComScope1: null,
        CreditComScope2: null,
        CreditComScope: null,
        beginDateScope1: null,
        beginDateScope2: null,
        beginDateScope: null,
        remark: null
      },
      CopySearch: {
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
        workAge: null,
        cashierName: null,
        basicSalary: null,
        bonus: null,
        lunchSalary: null,
        trafficSalary: null,
        pensionBase: null,
        pensionPer: null,
        medicalBase: null,
        medicalPer: null,
        accumulationFundBase: null,
        accumulationFundPer: null,
        times: null,
        others: null,
        deduct: null,
        prepaid: null,
        recordPer: null,
        realSalary: null,
        RealSalaryScope1: null,
        RealSalaryScope2: null,
        RealSalaryScope: null,
        CreditEmpScope1: null,
        CreditEmpScope2: null,
        CreditEmpScope: null,
        CreditComScope1: null,
        CreditComScope2: null,
        CreditComScope: null,
        beginDateScope1: null,
        beginDateScope2: null,
        beginDateScope: null,
        remark: null
      },
      selectionIds: [],
      search: {
        workID: null,
        eid: null,
        dname: null,
        pname: null,
        jname: null,
        workAge: null,
        cashierName: null,
        RealSalaryScope: null,
        CreditEmpScope: null,
        CreditComScope: null,
        beginDateScope: null,
        remark: null
      },
      compute: {
        ids: null,
        name: null
      },
      // 员工信息自定义模板
      emps: [],
      // 部门信息自定义模板
      deps: [],
      // 职位信息自定义模板
      poss: [],
      // 职称信息自定义模板
      jls: [],
      active: 0,
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
      dialogFormVisible3: false,
      dialogFormVisible4: false,
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
      this.SalMonth.ename = item.value;
      this.SalMonth.eid = item.id;
      this.SalMonth.workID = item.workID;
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
      this.SalMonth.dname = data.name;
      this.SalMonth.did = data.id;
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
      this.SalMonth.pname = item.value;
      this.SalMonth.pid = item.id;
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
      this.SalMonth.jname = item.value;
      this.SalMonth.jid = item.id;
    },
    // 刷新
    refresh() {
      Object.keys(this.CopySearch).forEach(
        key => (this.CopySearch[key] = null)
      );
      Object.keys(this.search).forEach(key => (this.search[key] = null));
      Object.keys(this.compute).forEach(key => (this.compute[key] = null));
      Object.keys(this.SalMonth).forEach(key => (this.SalMonth[key] = null));
      this.active = 0;
      this.buttonTitleCancel = "取消";
      this.buttonTitleConfirm = "下一步";
      this.initSalSearch();
      this.$message({
        showClose: true,
        message: "刷新成功",
        type: "success"
      });
    },
    // 弹出框下一步
    next() {
      this.active++;
      this.buttonTitleCancel = "上一步";
      if (this.buttonTitleConfirm == "确认") {
        this.onSubmit();
      }
      if (this.active == 3) {
        this.buttonTitleConfirm = "确认";
      }
      if (this.active > 3) {
        this.dialogFormVisible = false;
        this.buttonTitleConfirm = "下一步";
        this.buttonTitleCancel = "取消";
        this.active = 0;
        this.closed();
      }
    },
    // 弹出框上一步
    last() {
      this.active--;

      if (this.active <= 0) {
        this.buttonTitleCancel = "取消";
      }
      if (this.active < 0) {
        this.buttonTitleCancel = "取消";
        this.dialogFormVisible = false;
        this.active = 0;
        this.closed();
      }
      if (this.active > 1) {
        this.buttonTitleCancel = "上一步";
        this.buttonTitleConfirm = "下一步";
      }
    },
    // 初始化页面数据
    // 初始化页面数据
    initSalSearch() {
      let url = "/salary/month/?page=" + this.page + "&size=" + this.size;
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
      if (this.search.workAge != null) {
        url += "&workAge=" + this.search.workAge;
      }
      if (this.search.cashierName != null) {
        url += "&cashierName=" + this.search.cashierName;
      }
      if (this.search.RealSalaryScope != null) {
        url += "&RealSalaryScope=" + this.search.RealSalaryScope;
      }
      if (this.search.CreditEmpScope != null) {
        url += "&CreditEmpScope=" + this.search.CreditEmpScope;
      }
      if (this.search.CreditComScope != null) {
        url += "&CreditComScope=" + this.search.CreditComScope;
      }
      if (this.search.beginDateScope != null) {
        url += "&beginDateScope=" + this.search.beginDateScope;
      }
      this.getRequest(url).then(resp => {
        if (resp) {
          this.SalMonths = resp.data;
          this.total = resp.total;
        }
      });
    },
    searchItem() {
      this.SalMonth = JSON.parse(JSON.stringify(this.CopySearch));
      this.validateIt = 0;
      this.Title = "查找员工工资信息";
      this.showView();
    },
    // 多选
    handleSelectionIdsChange(row) {
      this.selectionIds = row;
    },
    SelectMany() {
      this.validateIt = 2;
      this.Title = "月末处理";
      this.showView();
    },
    comItem(row) {
      this.validateIt = 3;
      this.compute.ids = [];
      this.compute.ids.push(row.id);
      this.showView();
    },
    onSubmit() {
      if (this.validateIt == 0) {
        this.CopySearch = JSON.parse(JSON.stringify(this.SalMonth));
        this.search.eid = this.SalMonth.eid;
        this.search.dname = this.SalMonth.dname;
        this.search.jname = this.SalMonth.jname;
        this.search.pname = this.SalMonth.pname;
        this.search.workAge = this.SalMonth.workAge;
        this.search.cashierName = this.SalMonth.cashierName;
        if (
          this.SalMonth.RealSalaryScope1 != null &&
          this.SalMonth.RealSalaryScope2 != null
        ) {
          this.search.RealSalaryScope = [];
          this.search.RealSalaryScope.push(this.SalMonth.RealSalaryScope1);
          this.search.RealSalaryScope.push(this.SalMonth.RealSalaryScope2);
        }
        if (
          this.SalMonth.CreditEmpScope1 != null &&
          this.SalMonth.CreditEmpScope2 != null
        ) {
          this.search.CreditEmpScope = [];
          this.search.CreditEmpScope.push(this.SalMonth.CreditEmpScope1);
          this.search.CreditEmpScope.push(this.SalMonth.CreditEmpScope2);
        }
        if (
          this.SalMonth.CreditComScope1 != null &&
          this.SalMonth.CreditComScope2 != null
        ) {
          this.search.CreditComScope = [];
          this.search.CreditComScope.push(this.SalMonth.CreditComScope1);
          this.search.CreditComScope.push(this.SalMonth.CreditComScope2);
        }
        this.search.beginDateScope = this.SalMonth.beginDateScope;
        this.search.remark = this.SalMonth.remark;
        this.initSalSearch();
        this.closed();
      } else if (this.validateIt == 2) {
        this.compute.ids = [];
        this.compute.name = this.SalMonth.cashierName;
        this.selectionIds.forEach(item => {
          this.compute.ids.push(item.id);
        });
        this.putRequest("/salary/month/disabled/", this.compute).then(resp => {
          if (resp) {
            this.initSalSearch();
          }
        });
        this.closed();
      } else if (this.validateIt == 3) {
        this.compute.name = this.SalMonth.cashierName;
        this.putRequest("/salary/month/disabled/", this.compute).then(resp => {
          if (resp) {
            this.initSalSearch();
          }
        });
        this.closed();
      }
    },
    closed() {
      this.validateIt = 0;
      this.dialogFormVisible1 = false;
      this.dialogFormVisible3 = false;
      Object.keys(this.SalMonth).forEach(key => (this.SalMonth[key] = null));
      Object.keys(this.compute).forEach(key => (this.compute[key] = null));
      this.selectionIds = [];
    },
    // 提供打开并控制弹出框输入内容
    showView() {
      if (this.validateIt == 0) {
        // 如果this.validateIt的属性值为0，则为查询类型面板的输入框进行赋值，使其激活
        this.dialogFormVisible1 = true;
        this.dialogFormVisible3 = false;
      } else if (this.validateIt == 2 || this.validateIt == 3) {
        this.dialogFormVisible1 = false;
        this.dialogFormVisible3 = true;
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