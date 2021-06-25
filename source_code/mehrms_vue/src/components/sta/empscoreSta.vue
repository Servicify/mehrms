<!-- 员工积分统计折柱混合图表显示组件 -->
<template>
  <!-- <div class="hello">
    <div ref="mapBox"></div>
  </div> -->
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
          v-model="empscoreSta.month"
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
          v-model="empscoreSta.num"
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
  name: "empscoreSta",
  data() {
    return {
      empscoreStas: [],
      dialogFormVisible: false,
      empscoreSta: {
        num: null,
        month: null
      },
      option: {},
      myChart: null,
      colors: ["#5470C6", "#91CC75", "#EE6666"],
      legendData: [
        "[0,60)",
        "[60,80)",
        "[80,90)",
        "[90,100]",
        "受惩罚人次",
        "受奖励人次",
        "月平均积分"
      ],
      xAxisData: undefined,
      series: [
        {
          name: "[0,60)",
          stack: "人数",
          type: "bar",
          data: undefined
        },
        {
          name: "[60,80)",
          stack: "人数",
          type: "bar",
          data: undefined
        },
        {
          name: "[80,90)",
          stack: "人数",
          type: "bar",
          data: undefined
        },
        {
          name: "[90,100]",
          type: "bar",
          data: undefined
        },
        {
          name: "受惩罚人次",
          stack: "人次",
          type: "bar",
          data: undefined
        },
        {
          name: "受奖励人次",
          stack: "人次",
          type: "bar",
          data: undefined
        },
        {
          name: "月平均积分",
          type: "line",
          yAxisIndex: 1,
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
      let url = "/sta/score?";
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
          this.empscoreStas = resp;
          this.xAxisData = this.empscoreStas.yearsAndMonths;
          this.series[0].data = new Array();
          this.series[0].data = this.empscoreStas.lessThan60;
          this.series[1].data = new Array();
          this.series[1].data = this.empscoreStas.lessThan80;
          this.series[2].data = new Array();
          this.series[2].data = this.empscoreStas.lessThan90;
          this.series[3].data = new Array();
          this.series[3].data = this.empscoreStas.lessThan100;
          this.series[4].data = new Array();
          this.series[4].data = this.empscoreStas.bonusList;
          this.series[5].data = new Array();
          this.series[5].data = this.empscoreStas.punishmentsList;
          this.series[6].data = new Array();
          this.series[6].data = this.empscoreStas.avgScore;
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
            name: "月统计(人次)",
            interval: 50,
            axisLabel: {
              formatter: "{value} 人次"
            }
          },
          {
            type: "value",
            name: "月平均分统计(分)",
            interval: 10,
            axisLabel: {
              formatter: "{value} 分"
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
      this.Search.month = this.empscoreSta.month;
      this.Search.num = this.empscoreSta.num;
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
    },
    // 为刚进入页面后的初始化数据所需要的时间赋值
    getTime() {
      var date = new Date(+new Date() + 8 * 3600 * 1000)
        .toISOString()
        .replace(/T/g, " ")
        .replace(/\.[\d]{3}Z/, "");
      return date;
    }
  },
  //生命周期 - 创建完成（访问当前this实例）
  created() {},
  //生命周期 - 挂载完成（访问DOM元素）
  mounted() {
    this.initEmpScoreSta();
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