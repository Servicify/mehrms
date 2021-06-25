<!-- 操作日志管理 -->
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
      <el-table :data="SysLogs" style="width: 100%" stripe>
        <el-table-column prop="id" label="序号" width="55" fixed>
        </el-table-column>
        <el-table-column prop="requestTime" label="请求时间" fixed width="150">
        </el-table-column>
        <el-table-column prop="requestURL" label="请求地址" fixed width="300">
        </el-table-column>
        <el-table-column prop="requestInfos" label="请求操作信息" width="600">
        </el-table-column>
        <el-table-column prop="hr.name" label="发送请求的管理员" width="150">
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页功能 -->
    <div style="display: flex;justify-content: flex-end">
      <el-pagination
        background
        @size-change="sizeChange"
        @current-change="currentChange"
        layout="sizes, prev, pager, next, jumper, ->, total, slot"
        :total="total"
      >
      </el-pagination>
    </div>
    <!-- 查询显示框 -->
    <div>
      <!-- :model="emp" :rules="rules"添加表单校验规则 
        ref="empForm"为添加员工之前的一个表单校验-->
      <el-dialog title="查询相关操作日志" :visible.sync="dialogFormVisible">
        <el-form :model="SysLog">
          <el-form-item label="查询日期范围:">
            <el-date-picker
              v-model="SysLog.beginDateScope"
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
          <el-form-item label="操作管理员:">
            <el-input
              size="mini"
              style="width: 150px"
              prefix-icon="el-icon-edit"
              v-model="SysLog.manager"
              placeholder="请输入操作管理员信息"
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
  name: "SysLog",
  data() {
    return {
      SysLogs: [],
      SysLog: {
        beginDateScope: null,
        manager: null
      },
      search: {
        beginDateScope: null,
        manager: null
      },
      dialogFormVisible: false,
      total: 0,
      currentPage: 1,
      currentSize: 10
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
    // 分页显示
    currentChange(page) {
      this.currentPage = page;
      this.initSysLog();
    },
    initSysLog() {
      let url =
        "/sys/log/?page=" + this.currentPage + "&size=" + this.currentSize;
      if (this.search.manager != null) {
        url += "&requestManager=" + this.search.manager;
      }
      if (this.search.beginDateScope != null) {
        url += "&beginDateScope=" + this.search.beginDateScope;
      }
      this.getRequest(url).then(resp => {
        if (resp) {
          this.SysLogs = resp.data;
          this.total = resp.total;
        }
      });
    },
    searchItem() {
      this.dialogFormVisible = true;
    },
    onSubmit() {
      this.search.manager = this.SysLog.manager;
      this.search.beginDateScope = this.SysLog.beginDateScope;
      this.initSysLog();
      this.$message({
        showClose: true,
        message: "查询成功",
        type: "success"
      });
      this.closed();
    },
    closed() {
      this.dialogFormVisible = false;
    },
    refresh() {
      Object.keys(this.SysLog).forEach(key => (this.SysLog[key] = null));
      Object.keys(this.search).forEach(key => (this.search[key] = null));
      this.initSysLog();
      this.$message({
        showClose: true,
        message: "刷新成功",
        type: "success"
      });
    }
  },
  //生命周期 - 创建完成（访问当前this实例）
  created() {},
  //生命周期 - 挂载完成（访问DOM元素）
  mounted() {
    this.initSysLog();
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