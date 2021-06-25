<!-- 员工奖惩 -->
<!-- 
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
-->
<template>
  <div>
    <!-- 添加、查询、刷新操作区域 -->
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
        @click="addItem"
        icon="el-icon-circle-plus-outline"
        >添加记录</el-button
      >
      <el-button
        type="success"
        size="large"
        @click="refresh"
        icon="el-icon-refresh"
        >刷新</el-button
      >
    </div>
    <!-- 数据具体展示区域 -->
    <div>
      <el-table :data="PerEcs" border style="width: 85%">
        <el-table-column type="index" label="序号" fixed width="50">
        </el-table-column
        ><el-table-column fixed prop="createDate" label="创建日期" width="150">
        </el-table-column>
        <!-- 由于员工号为8位数的员工id，左边用零补齐，故需对本列进行数据加工，使用添加:formatter="formatterColumn(row,column)"属性或者使用template进行处理 -->
        <!-- <el-table-column
          prop="employee.id"
          :formatter="formatterColumn"
          label="员工工号"
          width="150"
        >
        </el-table-column> -->
        <el-table-column label="员工工号" width="150">
          <template slot-scope="scope">
            <span>{{ formatterColumn(scope.row, scope.column) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="employee.name" label="员工姓名" width="150">
        </el-table-column>
        <el-table-column prop="rule.ruleName" label="规则名称" width="150">
        </el-table-column>
        <el-table-column prop="rule.ruleValue" label="规则分值" width="80">
        </el-table-column>
        <el-table-column prop="reasonRecord" label="备注原因" width="270">
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <el-button @click="editItem(scope.row)" type="text" size="small"
              >修改</el-button
            >
            <el-button type="text" size="small" @click="deleteItem(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页功能 -->
    <div
      style="display: flex;justify-content: flex-end;position:relative;left:-15%;"
    >
      <el-pagination
        @size-change="sizeChange"
        @current-change="currentChange"
        :current-page="page"
        :page-sizes="[5, 10, 20, 30, 40, 50, 100, 200, 500, 1000]"
        :page-size="size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>
    <!-- 修改弹出框 -->
    <div>
      <el-dialog :title="Title" :visible.sync="dialogFormVisible">
        <el-form :model="PerEc">
          <!-- 使用自定义模板员工信息输入框 -->
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px ">
            <span
              style="color:red"
              v-if="this.validateIt == 1 || this.validateIt == 2"
              >&nbsp;*&nbsp;</span
            >选则员工信息</el-tag
          >
          <!-- 参考ElementUI中的Input输入框中的自定义模板 -->
          <el-autocomplete
            popper-class="my-autocomplete"
            v-model="PerEc.empname"
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
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            ><span
              style="color:red"
              v-if="this.validateIt == 1 || this.validateIt == 2"
              >&nbsp;*&nbsp;</span
            >员工工号</el-tag
          >
          <el-input
            v-model="PerEc.workID"
            disabled
            style="width:100px;margin:7px 4px 7px 0px"
          ></el-input>
          <!-- 使用自定义模板规则对象输入框 -->
          <br />
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            ><span
              style="color:red"
              v-if="this.validateIt == 1 || this.validateIt == 2"
              >&nbsp;*&nbsp;</span
            >选则规则信息</el-tag
          >
          <!-- 参考ElementUI中的Input输入框中的自定义模板 -->
          <el-autocomplete
            popper-class="my-autocomplete"
            v-model="PerEc.ruleName"
            :fetch-suggestions="querySearchRule"
            placeholder="请输入奖惩规则"
            @select="handleSelectRule"
            style="margin:7px 4px 7px 0px "
            clearable
          >
            <i class="el-icon-edit el-input__icon" slot="suffix"> </i>
            <template slot-scope="{ item }">
              <div class="name">{{ item.value }}</div>
              <span class="addr">{{ item.ruleValue }}</span>
            </template>
          </el-autocomplete>
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            ><span
              style="color:red"
              v-if="this.validateIt == 1 || this.validateIt == 2"
              >&nbsp;*&nbsp;</span
            >规则分值</el-tag
          >
          <el-input
            v-model="PerEc.ruleValue"
            disabled
            style="width:100px;margin:7px 4px 7px 0px"
          ></el-input>
          <!-- 创建日期 -->
          <br />
          <el-tag
            type="success"
            size="large"
            v-if="showDate"
            style="margin:7px 4px 7px 0px "
            ><span
              style="color:red"
              v-if="this.validateIt == 1 || this.validateIt == 2"
              >&nbsp;*&nbsp;</span
            >创建日期</el-tag
          >
          <el-input
            v-model="PerEc.createDate"
            style="width:30%;margin:7px 4px 7px 0px"
            disabled
            v-if="showDate"
          ></el-input>
          <el-tag
            type="success"
            size="large"
            v-if="!showDate"
            style="margin:7px 4px 7px 0px "
            ><span
              style="color:red"
              v-if="this.validateIt == 1 || this.validateIt == 2"
              >&nbsp;*&nbsp;</span
            >
            添加日期
          </el-tag>
          <el-date-picker
            v-model="PerEc.beginDateScope"
            type="daterange"
            size="larger"
            unlink-panels
            value-format="yyyy-MM-dd"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            v-if="!showDate"
            style="margin:7px 4px 7px 0px "
          >
          </el-date-picker>
          <!-- 参考可变自适应输入框 -->
          <br />
          <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
            >备注原因</el-tag
          >
          <el-input
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 6 }"
            placeholder="请输入备注原因"
            v-model="PerEc.reasonRecord"
            style="width:30%;margin:7px 4px 7px 0px"
          >
          </el-input>
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
  name: "PerEc",
  data() {
    return {
      // 员工信息自定义模板
      emps: [],
      // 规则信息自定义模板
      rules: [],
      // 日期范围输入框是否启用
      dateBoolean: true,
      // 添加数据输入框处理对象
      add: {
        eid: null,
        rid: null,
        reasonRecord: null
      },
      // 查询数据输入框处理对象
      search: {
        empname: null,
        ruleName: null,
        ruleValue: null,
        eid: null,
        rid: null,
        reasonRecord: null,
        beginDateScope: null
      },
      // Rule_Role数据接收对象
      PerEcs: [],
      // Rule_Role数据基础修改对象
      PerEc: {
        workID: null,
        empname: null,
        ruleValue: null,
        ruleName: null,
        eid: null,
        reasonRecord: null,
        rid: null,
        id: null,
        createDate: null,
        beginDateScope: null
      },
      edit: {
        eid: null,
        reasonRecord: null,
        rid: null,
        id: null,
        createDate: null
      },
      // 页数
      // 总计多少页
      total: 0,
      // 默认显示第一页
      page: 1,
      size: 10,
      // 传入数据
      type: "",
      // 修改数据弹出框修改
      dialogFormVisible: false,
      // 修改数据弹出框宽度
      formLabelWidth: "120px",
      // 弹出框标题
      Title: null,
      // 操作类型指示器
      validateIt: null,
      // 日期输入框选则
      showDate: false,
      // 输入日期处分割符显示
      showBr: false
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
      this.initRuleRole();
    },
    // 调整页面显示第几页
    currentChange(currentPage) {
      this.page = currentPage;
      this.initRuleRole("search");
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
      this.PerEc.empname = item.value;
      this.PerEc.workID = item.workID;
      this.PerEc.eid = item.id;
    },
    // ***************************
    // *******规则选项加载数据*****
    // ***************************
    // 提示规则信息
    querySearchRule(queryString, cb) {
      var rules = this.rules;
      var results = queryString
        ? rules.filter(this.createFilterEmp(queryString))
        : rules;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    // 对接收到的规则信息进行过滤
    createFilterRule(queryString) {
      return rule => {
        return (
          rule.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        );
      };
    },
    // 请求规则信息
    loadAllRule() {
      this.getRequest("/system/basic/ecmana/enabled").then(resp => {
        if (resp) {
          // 为接收数组的每一个对象插入一个value属性
          for (let item of resp) {
            item.value = item.ruleName;
          }
          this.rules = resp;
        }
      });
    },
    // 选中规则中的提示信息的操作
    handleSelectRule(item) {
      // 选中后的操作
      this.PerEc.ruleName = item.value;
      this.PerEc.rid = item.id;
      this.PerEc.ruleValue = item.ruleValue;
    },
    // ***************************
    // *******初始化数据***********
    // *******具体查询数据*********
    // ***************************
    initRuleRole() {
      let url = "/per/ec/?page=" + this.page + "&size=" + this.size;
      if (this.search.empname && this.search.empname != null) {
        url += "&name=" + this.search.empname;
      }
      if (this.search.workID && this.search.workID != null) {
        url += "&workID=" + this.search.workID;
      }
      if (this.search.rid && this.search.rid != null) {
        url += "&rid=" + this.search.rid;
      }
      if (this.search.reasonRecord && this.search.ereasonRecordid != null) {
        url += "&reasonRecord=" + this.search.reasonRecord;
      }
      if (this.search.beginDateScope && this.search.beginDateScope != null) {
        url += "&beginDateScope=" + this.search.beginDateScope;
      }
      this.getRequest(url).then(resp => {
        if (resp) {
          this.PerEcs = resp.data;
          this.total = resp.total;
        }
      });
    },
    //刷新数据
    refresh() {
      Object.keys(this.add).forEach(key => (this.add[key] = undefined));
      Object.keys(this.search).forEach(key => (this.search[key] = undefined));
      Object.keys(this.PerEc).forEach(key => (this.PerEc[key] = undefined));
      Object.keys(this.edit).forEach(key => (this.edit[key] = undefined));
      this.dialogFormVisible = false;
      this.validateIt = 0;
      this.Title = "";
      this.showBr = false;
      this.showDate = false;
      this.initRuleRole();
      this.$message({
        showClose: true,
        message: "刷新成功",
        type: "success"
      });
    },
    // ***************************
    // *******查询数据*************
    // ***************************
    searchItem() {
      this.PerEc = JSON.parse(JSON.stringify(this.search));
      this.Title = "查询员工奖惩记录";
      this.validateIt = 0;
      this.showView();
    },
    // ***************************
    // *******添加数据*************
    // ***************************
    addItem() {
      // 测试add数据
      this.Title = "添加员工奖惩规则";
      this.validateIt = 1;
      this.showView();
    },
    //删除奖惩记录
    deleteItem(row) {
      this.$confirm("此操作将永久删除该奖惩记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.deleteRequest("/per/ec/" + row.id).then(resp => {
            if (resp) {
              this.initRuleRole();
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
    //修改奖惩记录
    editItem(row) {
      this.Title = "修改员工奖惩规则";
      this.validateIt = 2;
      this.showView(row);
    },
    //打开弹出框
    showView(row) {
      if (this.validateIt == 0) {
        // 查询
        this.showDate = false;
        this.showBr = true;
      } else if (this.validateIt == 1) {
        // 添加
        this.showDate = false;
      } else if (this.validateIt == 2) {
        // 修改
        //    修改赋值
        this.PerEc.id = row.id;
        this.PerEc.eid = row.employee.id;
        this.PerEc.workID = row.employee.workID;
        this.PerEc.empname = row.employee.name;
        this.PerEc.rid = row.rule.id;
        this.PerEc.ruleName = row.rule.ruleName;
        this.PerEc.ruleValue = row.rule.ruleValue;
        this.PerEc.reasonRecord = row.reasonRecord;
        this.PerEc.createDate = row.createDate;
        this.showDate = true;
        this.showBr = true;
      }
      this.dialogFormVisible = true;
    },
    onSubmit() {
      if (this.validateIt == 0) {
        // 查询
        this.search.workID = this.PerEc.workID;
        this.search.empname = this.PerEc.empname;
        this.search.ruleName = this.PerEc.ruleName;
        this.search.ruleValue = this.PerEc.ruleValue;
        this.search.eid = this.PerEc.empid;
        this.search.rid = this.PerEc.ruleId;
        this.search.reasonRecord = this.PerEc.reasonRecord;
        this.search.beginDateScope = this.PerEc.beginDateScope;
        this.initRuleRole();
      } else if (this.validateIt == 1) {
        // 添加
        this.add.eid = this.PerEc.eid;
        this.add.rid = this.PerEc.rid;
        this.add.reasonRecord = this.PerEc.reasonRecord;

        this.postRequest("/per/ec/", this.add).then(resp => {
          if (resp) {
            this.initRuleRole();
          }
        });
      } else if (this.validateIt == 2) {
        // 修改
        this.edit.rid = this.PerEc.rid;
        this.edit.eid = this.PerEc.eid;
        this.edit.id = this.PerEc.id;
        this.edit.reasonRecord = this.PerEc.reasonRecord;
        this.edit.createDate = this.PerEc.createDate;
        this.putRequest("/per/ec/", this.edit).then(resp => {
          if (resp) {
            this.initRuleRole();
            this.dialogFormVisible = false;
          }
        });
      }
      this.closed();
    },
    closed() {
      // 由于关闭查询弹出框后系统自动清空search对象后，出现只能查看当前页码中所的查询数据，
      // 而查询其他页码对应的查询数据无法查询（需重新输入查询条件进行查询）的现象。
      // 为避免此类现象出现，关闭查询框后清除除search对象以外的所有对象数据
      // 将查询到的search对象在重新打开查询弹出框后，将search对象重新赋值给PerEc对象
      Object.keys(this.add).forEach(key => (this.add[key] = undefined));
      Object.keys(this.PerEc).forEach(key => (this.PerEc[key] = undefined));
      Object.keys(this.edit).forEach(key => (this.edit[key] = undefined));
      this.dialogFormVisible = false;
      this.validateIt = 0;
      this.Title = "";
      this.showDate = false;
      this.showBr = false;
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
    // 员工信息
    this.emps = this.loadAllEmp();
    // 规则信息
    this.rules = this.loadAllRule();
    // 初始化
    this.initRuleRole();
  }
};
</script>
<style >
/* @import url(); 引入css类 */
/* @import url(); 引入css类 */
.my-autocomplete li {
  line-height: normal;
  padding: 7px;
}
.my-autocomplete .name {
  text-overflow: ellipsis;
  overflow: hidden;
}
.my-autocomplete .addr {
  font-size: 12px;
  color: #b4b4b4;
}
.my-autocomplete .highlighted .addr {
  color: #ddd;
}
</style>