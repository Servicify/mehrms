<!-- 员工积分统计 -->
<template>
  <div>
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="表格统计" name="empscoreTable">
        <empscoreTable></empscoreTable>
      </el-tab-pane>
      <el-tab-pane label="图标统计" name="empscoreSta">
        <empscoreSta></empscoreSta>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import empscoreTable from "../../components/sta/empscoreTable";
import empscoreSta from "../../components/sta/empscoreSta";
export default {
  name: "StaScore",
  data() {
    return {
      activeName: "empscoreTable"
    };
  },
  components: {
    empscoreTable,
    empscoreSta
  },
  //生命周期 - 创建完成（访问当前this实例）
  created() {},
  //生命周期 - 挂载完成（访问DOM元素）
  mounted() {}
};
</script>
<style scoped>
</style>
<!-- 
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
-->