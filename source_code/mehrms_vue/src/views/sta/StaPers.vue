<!-- 人事信息统计 -->
<template>
  <div>
    <!-- 数据显示区域 -->
    <!-- 刷新按钮 -->
    <div>
      <el-button
        type="success"
        size="large"
        @click="refresh"
        icon="el-icon-refresh"
        >刷新</el-button
      >
      <el-select
        v-model="integer"
        placeholder="请选择"
        @change="this.initEmpScoreSta"
      >
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
    </div>
    <div ref="mapBox" style="height:600px;width:1300px"></div>
  </div>
</template>

<script>
import * as echarts from "echarts";
export default {
  name: "StaPers",
  data() {
    return {
      StaPers: [],
      integer: null,
      title: "员工所在部门信息统计",
      option: {},
      myChart: null,
      // [
      //       { value: 1048, name: "搜索引擎" },
      //       { value: 735, name: "直接访问" },
      //       { value: 580, name: "邮件营销" },
      //       { value: 484, name: "联盟广告" },
      //       { value: 300, name: "视频广告" },
      //       { value: 0, name: "视频广告1" }
      //     ]
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
      options: [
        {
          value: "0",
          label: "查询员工所在部门信息"
        },
        {
          value: "1",
          label: "查询员工职称信息"
        },
        {
          value: "2",
          label: "查询员工职位信息"
        }
      ]
    };
  },
  methods: {
    initEmpScoreSta() {
      let url = "/sta/pers/?integer=";
      if (this.integer != null) {
        url += this.integer;
        if (this.integer == 0) {
          this.title = "员工所在部门信息统计";
        }
        if (this.integer == 1) {
          this.title = "员工职称信息统计";
        }
        if (this.integer == 2) {
          this.title = "员工职位信息统计";
        }
      } else {
        url = "/sta/pers/?integer=0";
      }
      // 初始化图标，由于没有数据，所以使用一个默认值进行初始化两个请求参数
      // 如果搜索数据数据，并点击查询按钮后，会传递一个search的字符串
      // 使得两个请求参数直接被赋予查询框中的数据值，而不会被初始化
      // date参数代表请求截止日期，integer参数代表查询截止日期前的多少个参数
      this.getRequest(url).then(resp => {
        if (resp) {
          this.StaPers = resp.data;
          this.series[0].data = new Array();
          // n 是对象中属性的字符串形式，有几个属性就会循环几次
          for (let n in this.StaPers) {
            this.series[0].data.push(this.StaPers[n]);
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