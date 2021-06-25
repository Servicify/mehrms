<!-- 员工培训 -->
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
        @click="addItem"
        icon="el-icon-edit"
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
    <!-- 数据显示区域 -->
    <div>
      <el-table :data="PerTrains" size="large" border style="width: 81%">
        <el-table-column type="index" label="序号" fixed width="50">
        </el-table-column>
        <el-table-column
          prop="eid"
          :formatter="formatterColumn"
          label="员工工号"
          width="150"
        >
        </el-table-column>
        <el-table-column
          prop="employee.name"
          label="员工名字"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="trainBeginDate"
          label="培训开始日期"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="trainEndDate"
          label="培训结束日期"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="trainContent"
          label="培训内容"
          width="100"
        ></el-table-column>
        <el-table-column
          prop="remark"
          label="备注"
          width="100"
        ></el-table-column>
        <el-table-column label="操作" width="220">
          <template slot-scope="scope">
            <el-button
              type="text"
              size="small"
              @click="editItem(scope.row)"
              icon="el-icon-edit"
              >修改</el-button
            >
            <el-button
              type="text"
              size="small"
              @click="deleteItem(scope.row.id)"
              icon="el-icon-delete"
              >删除</el-button
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
        <el-form :model="PerTrain">
          <!-- 使用自定义模板员工信息输入框 -->
          <!-- 员工信息输入框 -->
          <el-form-item>
            <el-tag type="success" size="large" v-if="inputName"
              ><span style="color:red" v-if="this.validateIt != 0"
                >&nbsp;*&nbsp;</span
              >选则员工信息</el-tag
            >
            <!-- 参考ElementUI中的Input输入框中的自定义模板 -->
            <el-autocomplete
              popper-class="my-autocomplete"
              v-model="PerTrain.name"
              :fetch-suggestions="querySearchEmp"
              placeholder="请输入员工信息内容"
              @select="handleSelectEmp"
              clearable
              v-if="inputName"
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
            <el-tag type="success" size="large" v-if="inputId"
              ><span style="color:red" v-if="this.validateIt != 0"
                >&nbsp;*&nbsp;</span
              >员工工号</el-tag
            >
            <el-input
              v-model="PerTrain.workID"
              disabled
              style="width:100px"
              v-if="inputId"
            ></el-input>
          </el-form-item>
          <!-- 日期范围输入框 -->
          <el-form-item>
            <el-tag type="success" size="large" v-if="inputDate"
              ><span style="color:red" v-if="this.validateIt != 0"
                >&nbsp;*&nbsp;</span
              >{{ DateScope }}</el-tag
            >
            <el-date-picker
              v-model="PerTrain.beginDateScope"
              type="daterange"
              size="larger"
              unlink-panels
              value-format="yyyy-MM-dd"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              v-if="inputDate1"
            >
            </el-date-picker>
            <el-date-picker
              v-model="PerTrain.trainBeginDate"
              type="date"
              placeholder="请选则起始日期"
              value-format="yyyy-MM-dd"
              v-if="inputDate2"
            >
            </el-date-picker>
            <el-date-picker
              v-model="PerTrain.trainEndDate"
              value-format="yyyy-MM-dd"
              type="date"
              placeholder="请选则结束日期"
              v-if="inputDate2"
            >
            </el-date-picker>
          </el-form-item>
          <!-- 培训内容 -->
          <el-form-item>
            <el-tag type="success" size="large" v-if="inputContent"
              ><span style="color:red" v-if="this.validateIt != 0"
                >&nbsp;*&nbsp;</span
              >培训内容</el-tag
            >
            <el-input
              v-model="PerTrain.trainContent"
              style="width:100px"
              v-if="inputContent"
            ></el-input>
          </el-form-item>
          <!-- 备注 -->
          <el-form-item>
            <el-tag type="success" size="large" v-if="inputRemark">备注</el-tag>
            <el-input
              v-model="PerTrain.remark"
              style="width:100px"
              v-if="inputRemark"
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
</template>

<script>
export default {
  name: "PerTrain",
  data() {
    return {
      // 员工培训接收数据
      PerTrains: [],
      // 员工查改添基础数据对象
      PerTrain: {
        id: null,
        eid: null,
        name: null,
        workID: null,
        trainBeginDate: null,
        trainEndDate: null,
        trainContent: null,
        remark: null,
        beginDateScope: null
      },
      //页数
      page: 1,
      size: 10,
      total: 0,
      // 员工信息自定义模板
      emps: [],
      // 弹出面板
      dialogFormVisible: false,
      // 弹出面板显示输入框类型值
      validateIt: 0,
      // 修改数据发送对象
      edit: {
        id: null,
        eid: null,
        name: null,
        trainEndDate: null,
        trainBeginDate: null,
        trainContent: null,
        remark: null
      },
      // 查看数据发送对象
      search: {
        eid: null,
        trainContent: null,
        remark: null,
        beginDateScope: null
      },
      // 添加数据发送对象
      add: {
        eid: null,
        trainEndDate: null,
        trainBeginDate: null,
        trainContent: null,
        remark: null
      },
      DateScope: "查询日期",
      // 确定显示的弹出框的内容
      inputName: false,
      inputId: false,
      inputDate: false,
      inputDate1: false,
      inputDate2: false,
      inputContent: false,
      inputRemark: false,
      Title: "查询员工培训记录"
    };
  },
  methods: {
    // ***************************
    // *******初始化数据***********
    // *******具体查询数据*********
    // ***************************
    initPerTrain() {
      let url = "/per/train/?page=" + this.page + "&size=" + this.size;
      if (this.search.eid) {
        url += "&eid=" + this.search.eid;
      }
      if (this.search.trainContent) {
        url += "&trainContent=" + this.search.trainContent;
      }
      if (this.search.remark) {
        url += "&remark=" + this.search.remark;
      }
      if (this.search.beginDateScope) {
        url += "&beginDateScope=" + this.search.beginDateScope;
      }
      this.getRequest(url).then(resp => {
        if (resp) {
          this.PerTrains = resp.data;
          this.total = resp.total;
          // 示范
          //
          // var keys = Object.keys(person).forEach(function (e) {
          // });
        }
      });
    },
    // ***************************
    // *******分页显示*************
    // ***************************
    // 调整页面显示数目
    sizeChange(currentSize) {
      //
      this.size = currentSize;
      this.initPerTrain();
    },
    // 调整页面显示第几页
    currentChange(currentPage) {
      this.page = currentPage;
      this.initPerTrain();
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
      this.PerTrain.name = item.value;
      this.PerTrain.eid = item.id;
      this.PerTrain.workID = item.workID;
    },
    //
    searchItem() {
      this.PerTrain = JSON.parse(JSON.stringify(this.search));
      this.validateIt = 0;
      this.showView();
    },
    addItem() {
      this.validateIt = 1;
      this.DateScope = "培训日期";
      this.Title = "添加员工培训记录";
      this.showView();
    },
    // 刷新
    refresh() {
      Object.keys(this.PerTrain).forEach(key => (this.PerTrain[key] = null));
      Object.keys(this.edit).forEach(key => (this.edit[key] = null));
      Object.keys(this.search).forEach(key => (this.search[key] = null));
      Object.keys(this.add).forEach(key => (this.add[key] = null));
      this.initPerTrain();
      this.$message({
        showClose: true,
        message: "刷新成功",
        type: "success"
      });
    },
    editItem(row) {
      this.validateIt = 2;
      this.Title = "修改员工培训记录";
      this.showView();
      this.PerTrain.id = row.id;
      this.PerTrain.eid = row.eid;
      this.PerTrain.workID = this.PrefixInteger(row.eid, 8);
      this.PerTrain.name = row.employee.name;
      this.PerTrain.trainBeginDate = row.trainBeginDate;
      this.PerTrain.trainEndDate = row.trainEndDate;
      this.PerTrain.trainContent = row.trainContent;
      this.PerTrain.remark = row.remark;
    },
    onSubmit() {
      if (this.validateIt == 0) {
        this.search.name = this.PerTrain.name;
        this.search.eid = this.PerTrain.eid;
        this.search.trainContent = this.PerTrain.trainContent;
        this.search.remark = this.PerTrain.remark;
        this.search.beginDateScope = this.PerTrain.beginDateScope;
        this.initPerTrain();
      } else if (this.validateIt == 1) {
        this.add.eid = this.PerTrain.eid;
        this.add.trainBeginDate = this.PerTrain.trainBeginDate;
        this.add.trainEndDate = this.PerTrain.trainEndDate;
        this.add.trainContent = this.PerTrain.trainContent;
        this.add.remark = this.PerTrain.remark;
        if (this.add) {
          this.postRequest("/per/train/", this.add).then(resp => {
            if (resp) {
              this.initPerTrain();
            }
          });
        } else {
          this.$message.error("添加字段不可以为空!");
        }
      } else if (this.validateIt == 2) {
        this.edit.id = this.PerTrain.id;
        this.edit.eid = this.PerTrain.eid;
        this.edit.trainBeginDate = this.PerTrain.trainBeginDate;
        this.edit.trainEndDate = this.PerTrain.trainEndDate;
        this.edit.trainContent = this.PerTrain.trainContent;
        this.edit.remark = this.PerTrain.remark;
        this.putRequest("/per/train/", this.edit).then(resp => {
          if (resp) {
            this.initPerTrain();
          }
        });
      }
      // 根据this.validateIt的值，判断传输哪些属性
      this.closed();
    },
    closed() {
      Object.keys(this.PerTrain).forEach(key => (this.PerTrain[key] = null));
      Object.keys(this.edit).forEach(key => (this.edit[key] = null));
      Object.keys(this.add).forEach(key => (this.add[key] = null));
      this.validateIt = 0;
      this.dialogFormVisible = false;
      this.inputName = false;
      this.inputId = false;
      this.inputDate = false;
      this.inputDate1 = false;
      this.inputDate2 = false;
      this.inputContent = false;
      this.inputRemark = false;
    },
    deleteItem(id) {
      this.$confirm("此操作将永久删除此员工培训记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.deleteRequest("/per/train/" + id).then(resp => {
            if (resp) {
              this.initPerTrain();
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
    // 提供打开并控制弹出框输入内容
    showView() {
      this.dialogFormVisible = true;
      if (this.validateIt == 0) {
        // 如果this.validateIt的属性值为0，则为查询类型面板的输入框进行赋值，使其激活
        this.inputName = true;
        this.inputId = true;
        this.inputDate = true;
        this.inputDate1 = true;
        this.inputContent = true;
        this.inputRemark = true;
      } else if (this.validateIt == 1) {
        // 如果this.validateIt的属性值为1，则为添加类型面板的输入框进行赋值，使其激活
        this.DateScope = "培训日期";
        // 确定显示的弹出框的内容
        this.inputName = true;
        this.inputId = true;
        this.inputDate = true;
        this.inputDate2 = true;
        this.inputContent = true;
        this.inputRemark = true;
      } else {
        // 如果this.validateIt的属性值为2，则为修改类型面板的输入框进行赋值，使其激活
        this.DateScope = "培训日期";
        // 确定显示的弹出框的内容
        this.inputName = true;
        this.inputId = true;
        this.inputDate = true;
        this.inputDate2 = true;
        this.inputContent = true;
        this.inputRemark = true;
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
    this.initPerTrain();
    // 员工信息
    this.emps = this.loadAllEmp();
  }
};
</script>
<style scoped>
/* @import url(); 引入css类 */
</style>