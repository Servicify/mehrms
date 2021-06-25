<!-- 员工调动 -->
<!-- 
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
-->
<template>
  <div>
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="员工岗位信息" name="empInfo">
        <EmpInfo></EmpInfo>
      </el-tab-pane>
      <el-tab-pane label="员工调岗记录" name="mvInfo">
        <MvInfo></MvInfo>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import EmpInfo from "../../components/per/EmpInfo";
import MvInfo from "../../components/per/MvInfo";
export default {
  name: "PerMv",
  data() {
    return {
      // 默认为哪个选项
      activeName: "empInfo"
    };
  },
  components: {
    EmpInfo,
    MvInfo
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