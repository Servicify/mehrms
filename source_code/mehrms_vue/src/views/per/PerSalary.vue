<!-- 员工调薪 -->
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
      <el-tab-pane label="员工目前薪资" name="salInfo">
        <SalInfo></SalInfo>
      </el-tab-pane>
      <el-tab-pane label="员工调薪记录" name="salMv">
        <SalMv></SalMv>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import SalInfo from "../../components/per/SalInfo.vue";
import SalMv from "../../components/per/SalMv.vue";
export default {
  name: "PerSalary",
  data() {
    return {
      // 默认为哪个选项
      activeName: "salInfo"
    };
  },
  components: {
    SalInfo,
    SalMv
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