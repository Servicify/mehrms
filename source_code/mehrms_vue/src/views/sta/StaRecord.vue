<!-- 人事记录统计 -->
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
    <div ref="mapBox" style="height:600px;width:1300px"></div>
    <!-- 查询区域 -->
    <div>
      <el-dialog title="查询" :visible.sync="dialogFormVisible">
        <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
          ><span style="color:red">&nbsp;*&nbsp;</span>查询截至日期:</el-tag
        >
        <el-date-picker
          v-model="StaRecord.month"
          type="month"
          value-format="yyyy-MM-01"
          placeholder="选择月"
          style="margin:7px 4px 7px 0px "
        >
        </el-date-picker>
        <br />
        <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
          ><span style="color:red">&nbsp;*&nbsp;</span>查询月份数目:</el-tag
        >
        <el-select
          v-model="StaRecord.num"
          placeholder="请选择"
          style="margin:7px 4px 7px 0px "
        >
          <el-option
            v-for="item in options1"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
        <br />
        <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
          ><span style="color:red">&nbsp;*&nbsp;</span>查询类型:</el-tag
        >
        <el-select
          v-model="StaRecord.integer"
          placeholder="请选择"
          @change="this.initEmpScoreSta"
          style="margin:7px 4px 7px 0px "
        >
          <el-option
            v-for="item in options2"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
        <br />
        <el-button style="margin-top: 12px;" @click="onSubmit" type="primary"
          >确认</el-button
        >
        <el-button style="margin-top: 12px;" @click="dialogFormVisible = false"
          >取消</el-button
        >
      </el-dialog>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
export default {
  name: "StaRecord",
  data() {
    return {
      StaRecords: [],
      title: "部门调入信息统计",
      dialogFormVisible: false,
      StaRecord: {
        // 查询截至日期前的多少个月份
        num: null,
        // 查询截至日期
        month: null,
        // 查询某一类型的数据
        integer: null
      },
      option: {},
      myChart: null,
      series: [
        {
          name: "访问来源",
          type: "pie",
          radius: "50%",
          data: undefined,
          emphasis: {
            itemStyle: {
              shadowBlur: 20,
              shadowOffsetX: 0,
              shadowColor: "rgba(0, 0, 0, 0.5)"
            }
          }
        }
      ],
      Search: {
        num: undefined,
        month: undefined,
        integer: null
      },
      options1: [
        {
          value: "1",
          label: "查询最近1个月"
        },
        {
          value: "3",
          label: "查询最近3个月"
        },
        {
          value: "6",
          label: "查询最近6个月"
        },
        {
          value: "12",
          label: "查询最近12个月"
        }
      ],
      options2: [
        {
          value: "0",
          label: "查询部门调入信息"
        },
        {
          value: "1",
          label: "查询部门调出信息"
        },
        {
          value: "2",
          label: "查询员工培训信息"
        }
      ]
    };
  },
  methods: {
    initEmpScoreSta(type) {
      let url = "/sta/record/?";
      // 初始化图标，由于没有数据，所以使用一个默认值进行初始化两个请求参数
      // 如果搜索数据数据，并点击查询按钮后，会传递一个search的字符串
      // 使得两个请求参数直接被赋予查询框中的数据值，而不会被初始化
      // date参数代表请求截止日期，integer参数代表查询截止日期前的多少个参数
      if (type && type == "search") {
        url += "date=" + this.Search.month + "&";
        url += "months=" + this.Search.num + "&";
        url += "integer=" + this.Search.integer + "&";
      } else {
        url += "date=" + this.getTime() + "&";
        url += "months=" + 3 + "&";
        url += "integer=2" + "&";
      }
      if (this.StaRecord.integer == 0) {
        this.title = "部门调入信息统计";
      }
      if (this.StaRecord.integer == 1) {
        this.title = "部门调出信息统计";
      }
      if (this.StaRecord.integer == 2) {
        this.title = "员工培训信息统计";
      }

      this.getRequest(url).then(resp => {
        if (resp) {
          this.StaRecords = resp.data;
          this.series[0].data = new Array();
          // n 是对象中属性的字符串形式，有几个属性就会循环几次
          for (let n in this.StaRecords) {
            this.series[0].data.push(this.StaRecords[n]);
          }
          this.draw();
        }
      });
    },
    draw() {
      // 绘制图
      this.myChart = echarts.init(this.$refs.mapBox, "dark");
      this.myChart.setOption(this.option);
      // 给option赋值
      this.option = {
        // 设置图标背景色
        backgroundColor: "#15a7b0",
        title: {
          text: this.title,
          subtext: "****公司",
          left: "center"
        },
        tooltip: {
          trigger: "item"
        },
        legend: {
          orient: "vertical",
          left: "left"
        },
        series: this.series
      };
      this.option && this.myChart.setOption(this.option);
    },
    // 为刚进入页面后的初始化数据所需要的时间赋值
    getTime() {
      var date = new Date(+new Date() + 8 * 3600 * 1000)
        .toISOString()
        .replace(/T/g, " ")
        .replace(/\.[\d]{3}Z/, "");
      return date;
    },
    // 搜索
    searchItem() {
      this.dialogFormVisible = true;
    },
    // 提交
    onSubmit() {
      this.Search.month = this.StaRecord.month;
      this.Search.num = this.StaRecord.num;
      this.Search.integer = this.StaRecord.integer;
      this.initEmpScoreSta("search");
      this.dialogFormVisible = false;
    },
    // 刷新并清空查询对象中的值，重置查询框中的值
    refresh() {
      Object.keys(this.Search).forEach(key => (this.Search[key] = null));
      this.initEmpScoreSta();
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
    this.initEmpScoreSta();
    // this.mycharts = echarts.init(this.$refs.mapBox);
    // this.mycharts.setOption(option);
    // this.chartDom = document.getElementById(this.$refs.mapBox);
  }
};
</script>
<style scoped>
/* @import url(); 引入css类 */
/* * {
  color: rgba(16, 12, 42);
} */
</style>
<!-- 
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
-->