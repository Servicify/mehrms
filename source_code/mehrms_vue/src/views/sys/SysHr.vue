<!-- 权限管理 -->
<template>
  <div>
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="操作员管理" name="Administer">
        <Administer></Administer>
      </el-tab-pane>
      <el-tab-pane label="角色权限管理" name="Permiss">
        <Permiss></Permiss>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import Permiss from "../../components/sys/manager/Permiss.vue";
import Administer from "../../components/sys/manager/Administer.vue";
export default {
  name: "SysHr",
  data() {
    return {
      // 默认为哪个选项
      activeName: "Administer"
    };
  },
  components: {
    Permiss,
    Administer
  }
};
</script>

<style>
</style>
<!-- 
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
-->