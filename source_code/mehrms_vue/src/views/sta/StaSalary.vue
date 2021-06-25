<!-- 综合信息统计 StaAll-->
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
          v-model="StaSalary.month"
          type="month"
          style="margin:7px 4px 7px 0px "
          value-format="yyyy-MM-01"
          placeholder="选择月"
        >
        </el-date-picker>
        <br />
        <el-tag type="success" size="large" style="margin:7px 4px 7px 0px "
          ><span style="color:red">&nbsp;*&nbsp;</span>查询月份数目:</el-tag
        >
        <el-select
          v-model="StaSalary.num"
          placeholder="请选择"
          style="margin:7px 4px 7px 0px "
        >
          <el-option
            v-for="item in options"
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
  name: "StaSalary",
  data() {
    return {
      StaSalarys: [],
      dialogFormVisible: false,
      StaSalary: {
        num: null,
        month: null
      },
      option: {},
      myChart: null,
      legendData: [
        "4000以下",
        "4000至6000",
        "6000至8000",
        "8000至10000",
        "10000以上",
        "拖欠员工工资",
        "拖欠公司工资",
        "实发工资总额",
        "应发工资总额",
        "月平均工资"
      ],
      xAxisData: undefined,
      series: [
        {
          name: "4000以下",
          stack: "人数",
          type: "bar",
          data: undefined
        },
        {
          name: "4000至6000",
          stack: "人数",
          type: "bar",
          data: undefined
        },
        {
          name: "6000至8000",
          stack: "人数",
          type: "bar",
          data: undefined
        },
        {
          name: "8000至10000",
          stack: "人数",
          type: "bar",
          data: undefined
        },
        {
          name: "10000以上",
          stack: "人数",
          type: "bar",
          data: undefined
        },
        {
          name: "实发工资总额",
          yAxisIndex: 1,
          type: "bar",
          data: undefined
        },
        {
          name: "应发工资总额",
          yAxisIndex: 1,
          type: "bar",
          data: undefined
        },
        {
          name: "月平均工资",
          type: "line",
          yAxisIndex: 1,
          data: undefined
        },
        {
          name: "拖欠员工工资",
          yAxisIndex: 1,
          type: "bar",
          data: undefined
        },
        {
          name: "拖欠公司工资",
          yAxisIndex: 1,
          type: "bar",
          data: undefined
        }
      ],
      Search: {
        num: undefined,
        month: undefined
      },
      options: [
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
      ]
    };
  },
  methods: {
    initEmpScoreSta(type) {
      let url = "/sta/salary?";
      // 初始化图标，由于没有数据，所以使用一个默认值进行初始化两个请求参数
      // 如果搜索数据数据，并点击查询按钮后，会传递一个search的字符串
      // 使得两个请求参数直接被赋予查询框中的数据值，而不会被初始化
      // date参数代表请求截止日期，integer参数代表查询截止日期前的多少个参数
      if (type && type == "search") {
        url += "date=" + this.Search.month + "&";
        url += "integer=" + this.Search.num + "&";
      } else {
        url += "date=" + this.getTime() + "&";
        url += "integer=" + 3 + "&";
      }
      this.getRequest(url).then(resp => {
        if (resp) {
          this.StaSalarys = resp;
          this.xAxisData = this.StaSalarys.months;
          this.series[0].data = new Array();
          this.series[0].data = this.StaSalarys.lessThan4000;
          this.series[1].data = new Array();
          this.series[1].data = this.StaSalarys.lessThan6000;
          this.series[2].data = new Array();
          this.series[2].data = this.StaSalarys.lessThan8000;
          this.series[3].data = new Array();
          this.series[3].data = this.StaSalarys.lessThan10000;
          this.series[4].data = new Array();
          this.series[4].data = this.StaSalarys.moreThan10000;
          this.series[5].data = new Array();
          this.series[5].data = this.StaSalarys.realSalary;
          this.series[6].data = new Array();
          this.series[6].data = this.StaSalarys.counts;
          this.series[7].data = new Array();
          this.series[7].data = this.StaSalarys.avgSalary;
          this.series[8].data = new Array();
          this.series[8].data = this.StaSalarys.creditEmps;
          this.series[9].data = new Array();
          this.series[9].data = this.StaSalarys.creditCom;
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
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
            crossStyle: {
              color: "#999"
            }
          }
        },
        toolbox: {
          feature: {
            dataView: { show: true, readOnly: false },
            magicType: { show: true, type: ["line", "bar"] },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        legend: {
          data: this.legendData
        },
        xAxis: [
          {
            type: "category",
            data: this.xAxisData,
            axisPointer: {
              type: "shadow"
            }
          }
        ],
        yAxis: [
          {
            type: "value",
            name: "人次",
            interval: 10,
            axisLabel: {
              formatter: "{value} 人次"
            }
          },
          {
            type: "value",
            name: "工资(元)",
            interval: 200000,
            axisLabel: {
              formatter: "{value} 元"
            }
          }
        ],
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
      this.Search.month = this.StaSalary.month;
      this.Search.num = this.StaSalary.num;
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