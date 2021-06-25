<!-- 系统设置 -->
<template>
  <div>
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="部门管理" name="depMana">
        <DepMana></DepMana>
      </el-tab-pane>
      <el-tab-pane label="职位管理" name="posMana">
        <PosMana></PosMana>
      </el-tab-pane>
      <el-tab-pane label="职称管理" name="jobLevelMana">
        <JobLevelMana></JobLevelMana>
      </el-tab-pane>
      <el-tab-pane label="奖惩规则" name="ecMana">
        <EcMana></EcMana>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import DepMana from "../../components/sys/basic/DepMana";
import PosMana from "../../components/sys/basic/PosMana";
import JobLevelMana from "../../components/sys/basic/JobLevelMana";
import EcMana from "../../components/sys/basic/EcMana";

export default {
  name: "SysBasic",
  data() {
    return {
      // 默认为哪个选项
      activeName: "depMana"
    };
  },
  components: {
    DepMana,
    PosMana,
    JobLevelMana,
    EcMana
  },
  //生命周期 - 创建完成（访问当前this实例）
  created() {},
  //生命周期 - 挂载完成（访问DOM元素）
  mounted() {}
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