// 登录
<template>
  <div>
    <div class="bodyClass">
      <p class="titleClass">欢迎来到**公司微型企业人事管理系统</p>
    </div>
    <!-- 使用ElementUI的表单元素<el-form><el-form/>，
        并使用v-bind绑定表单校验规则rules属性，
        :rules="rules" 是动态绑定的表单验证规则rules，
        在下方的Vue实例中设置校验规则。
        :model="loginForm" 是绑定我们form表单中需要提交给后台的一个对象，
        在下方的Vue实例中设置loginForm对象数据。
        ref="ruleForm"  是我们绑定的对象；
        通过此可以查找表单的名字

        ------参见ElementUI的表单验证代码。
        -->
    <el-form
      :rules="rules"
      v-loading="loading"
      element-loading-text="正在登录..."
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0, 0, 0, 0.8)"
      ref="loginForm"
      :model="loginForm"
      class="loginContainer"
    >
      <h3 class="loginTitle">系统登录</h3>
      <!-- <el-form-item>为<el-form>表单元素的子元素，
          prop属性为<el-form>标签中:rules="rules" ，动态绑定的表单验证规则rules的子属性 -->
      <el-form-item prop="username">
        <!-- <el-input>标签为单一的表单输入框，
          type="text"输入类型是文本，
          v-model="loginForm.username"使得该输入框输入数据与表单提交给后台的对象loginForm的子属性username进行双向绑定。
          auto-complete 默认为on，其含义代表是否让浏览器自动记录之前输入的值
          很多时候，需要对客户的资属料进行保密，防止浏览器软件或者恶意插件获取到。
          可以在input中加入
          autocomplete="off" 来关闭浏览器或恶意插件的记录。
          placeholder="请输入用户名"设置该表单输入框的提示信息
          下同（password） -->
        <el-input
          type="text"
          v-model="loginForm.username"
          auto-complete="off"
          placeholder="请输入用户名"
        ></el-input>
      </el-form-item>

      <el-form-item prop="password">
        <el-input
          type="password"
          v-model="loginForm.password"
          auto-complete="off"
          placeholder="请输入密码"
          @keydown.enter.native="submitLogin"
        >
        </el-input>
      </el-form-item>
      <!-- 添加code验证码输入和显示框 -->
      <el-form-item prop="code">
        <el-input
          size="normal"
          type="text"
          v-model="loginForm.code"
          auto-complete="off"
          placeholder="点击图片更换验证码"
          @keydown.enter.native="submitLogin"
          style="width: 250px"
        ></el-input>
        <img
          :src="vcUrl"
          @click="updateVerifyCode"
          alt=""
          style="cursor: pointer"
        />
      </el-form-item>
      <!-- 单选框。checked勾选为true，取消勾选为false，此处默认为true。
      用蝎子钉就可以了，css中的>>>任何地方的样式都能改，
      一个表格内checkbox需要绑定一个值，向后台返回的值默认是0和1。
      原生的checkbox在vue中有true-value和false-value两个属性。
      然后翻阅element文档，el-checkbox也有俩相似的属性。
      true-label和false-label。-->
      <!-- <div class="panel">
        <el-checkbox
          v-model="checked"
          label="记住密码 "
          class="demo loginRemember"
          :true-label="1"
          :false-label="2"
        ></el-checkbox>
      </div> -->
      <!-- 添加登录按钮 -->
      <el-button
        type="success"
        style="width:100%; margin:7px 0px;"
        plain
        @click="submitLogin()"
        >登录</el-button
      >
    </el-form>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      // rules设置表单校验规则属性的值，其中包括用户名校验规则和密码校验规则
      // 其中required为是否为必填项；
      // message为表单提示信息；
      // trigger为触发方式，blur为失去焦点，change为数据改变
      //
      // ------参见ElementUI的表单验证代码
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 10, message: "长度在 3 到 10 个字符", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 3, max: 16, message: "长度在 8 到 16 个字符", trigger: "blur" }
        ],
        code: [{ required: true, message: "请输入验证码", trigger: "blur" }]
      },
      // 设置表单提交给后台的对象loginForm的属性与值
      loginForm: {
        username: undefined,
        password: undefined,
        code: ""
      },
      //
      checked: false,
      loading: false,
      // 验证码属性
      vcUrl: "/verifyCode?time=" + new Date()
    };
  },
  methods: {
    // 此函数的作用为防止前端提交非法（未经验证）数据。
    // ------参考ElementUI开发文档数字验证类型
    submitLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          // 当点击登录按钮时，激活加载状态
          this.loading = true;
          // 由于请求失败的处理业务已经被统一封装，故只书写处理请求成功的业务。
          this.postRequest("/doLogin", this.loginForm).then(resp => {
            // 登录成功后关闭加载状态
            this.loading = false;
            if (resp) {
              this.$store.commit("INIT_CURRENTHR", resp.obj);
              // 打印成功的信息
              // alert(JSON.stringify(resp));
              // localStorage 和 sessionStorage 属性允许在浏览器中存储 key/value 对的数据。
              // sessionStorage 用于临时保存同一窗口(或标签页)的数据，在关闭窗口或标签页之后将
              // 会删除这些数据。
              window.sessionStorage.setItem("user", JSON.stringify(resp.obj));
              // 跳转页面，如果用push跳转页面，可以在浏览器中使用后退按钮，使得页面后退到上一次
              // 的页面（后退按钮可点击）；如果使用replace跳转页面，可以在浏览器中不可以使用后退按钮回跳至上一页
              // （后退按钮不可点击）
              //
              // 如果权限不足的用户访问其他菜单，则自动跳转到home页，若未登录则自动跳转到登录页
              let path = this.$route.query.redirect;
              this.$router.replace(
                path == "/" || path == undefined ? "/home" : path
              );
            } else {
              this.vcUrl = "/verifyCode?time=" + new Date();
            }
          });
          // alert("submit!");
        } else {
          // ------参考ElementUI消息提示
          this.$message.error("登录用户名/密码不正确，请重新输入");
          return false;
        }
      });
    },
    // 添加验证码请求
    updateVerifyCode() {
      this.vcUrl = "/verifyCode?time=" + new Date();
    }
  },
  //生命周期 - 创建完成（访问当前this实例）
  created() {},
  //生命周期 - 挂载完成（访问DOM元素）
  mounted() {}
};
</script>
<style>
.bodyClass {
  position: absolute;
  left: 0px;
  top: 0px;
  z-index: -10;
  width: 100%;
  height: 100%;
  background-color: rgba(19, 215, 230, 0.83);
}
.titleClass {
  text-align: center;
  font-size: 40px;
  font-family: 华文行楷;
  color: #a5f86d;
  padding-top: 50px;
}
/* @import url(); 引入css类 */
.loginContainer {
  border-radius: 15px;
  /* 浮动 */
  background-clip: paddding-box;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
.loginTitle {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #2ae649da;
}
.loginRemember {
  text-align: left;
  margin: 0px 0px 7px 0px;
}
.panel .demo >>> .is-checked + .el-checkbox__label {
  color: #2ae649da;
}
</style>
<!-- 
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
-->