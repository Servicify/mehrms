<!-- 系统管理 -->
<template>
  <div>
    <div>
      <!-- 前景颜色选则器 -->
      <el-alert title="请选则主题前景色：" type="success" :closable="false">
      </el-alert>
      <el-color-picker
        v-model="SysCfg.color"
        show-alpha
        :predefine="predefineColors"
        @change="_change"
        style="margin-left:20px;margin-top:20px"
      >
      </el-color-picker>
    </div>
    <!-- 背景颜色选则器 -->
    <div>
      <el-alert title="请选则主题背景色：" type="success" :closable="false">
      </el-alert>
      <el-color-picker
        v-model="SysCfg.backgroundColor"
        show-alpha
        @change="_change"
        :predefine="predefineBackgroundColors"
        style="margin-left:20px;margin-top:20px"
      >
      </el-color-picker>
    </div>
    <el-button
      type="primary"
      size="large"
      @click="changeItem"
      icon="el-icon-edit-outline"
      >变更主题色</el-button
    >
  </div>
</template>

<script>
export default {
  name: "SysCfg",
  data() {
    return {
      SysCfgs: [],
      SysCfg: {
        color: null,
        backgroundColor: null
      },
      change: {
        id: null,
        hrid: null,
        color: null,
        backgroundColor: null
      },
      color: "rgba(255, 69, 0, 0.68)",
      backgroundColor: "rgba(255, 69, 0, 0.68)",
      predefineColors: [
        "#ff4500",
        "#ff8c00",
        "#ffd700",
        "#90ee90",
        "#00ced1",
        "#1e90ff",
        "#c71585",
        "rgba(255, 69, 0, 0.68)",
        "rgb(255, 120, 0)",
        "hsv(51, 100, 98)",
        "hsva(120, 40, 94, 0.5)",
        "hsl(181, 100%, 37%)",
        "hsla(209, 100%, 56%, 0.73)",
        "rgba(255, 0, 34, 1)"
      ],
      predefineBackgroundColors: [
        "#ff4500",
        "#ff8c00",
        "#ffd700",
        "#90ee90",
        "#00ced1",
        "#1e90ff",
        "#c71585",
        "rgba(255, 69, 0, 0.68)",
        "rgb(255, 120, 0)",
        "hsv(51, 100, 98)",
        "hsva(120, 40, 94, 0.5)",
        "hsl(181, 100%, 37%)",
        "hsla(209, 100%, 56%, 0.73)",
        "rgba(255, 0, 34, 1)"
      ]
    };
  },
  methods: {
    initInfos() {
      this.getRequest("/sys/cfg/").then(resp => {
        if (resp) {
          this.SysCfgs = resp;
          this.SysCfg.color = resp.color;
          this.SysCfg.backgroundColor = resp.backgroundColor;
          this.SysCfg.id = resp.id;
          this.SysCfg.backgroundColor = resp.backgroundColor;
        }
      });
    },
    changeItem() {
      this.change.color = this.SysCfg.color;
      this.change.backgroundColor = this.SysCfg.backgroundColor;
      this.putRequest("/sys/cfg/", this.SysCfg).then(resp => {
        if (resp) {
          this.initInfos();
        }
      });
    },
    _change(val) {
      if (val) {
        this.$emit("change", val);
      }
    }
  },
  //生命周期 - 创建完成（访问当前this实例）
  created() {},
  //生命周期 - 挂载完成（访问DOM元素）
  mounted() {
    this.initInfos();
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