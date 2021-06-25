// 登录
<template>
  <div>
    <el-container>
      <!-- 顶部导航区域 -->
      <el-header class="homeHeader" v-bind:style="{ backgroundColor: bgc }">
        <div class="title" style="font-family:华文中宋">MEHRMS</div>
        <!--下拉菜单
        Dropdown Events  
            command	点击菜单项触发的事件回调	dropdown-item 的指令
            commandHandler处理的参数为下方选项中的值
        在下方定义一个commandHandler()方法负责处理下拉选项的列表项点击事件
        -->
        <div style="position:relative;right:-600px">{{ user.name }}</div>
        <el-dropdown class="userInfo" @command="commandHandler">
          <!-- 添加el-dropdown-link的标签样式，对管理员图片<img>标签进行控制 -->
          <span class="el-dropdown-link">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i
              class="el-icon-setting"
              style="font-size:24px;color:#8f8f8f"
            ></i>

            <!-- <img :src="user.userface" alt="" /> -->
          </span>
          <el-dropdown-menu slot="dropdown">
            <!-- command属性参见ElementUI的Dropdown 下拉菜单
            Dropdown Menu Item Attributes
            参数	       说明	   类型	                  
            command	     指令	   string/number/object	-->
            <el-dropdown-item command="userInfo" icon="el-icon-s-custom"
              >个人中心</el-dropdown-item
            >
            <!-- <el-dropdown-item command="setting">设置</el-dropdown-item> -->
            <!-- 分割线下选项 -->
            <el-dropdown-item
              command="logout"
              divided
              icon="el-icon-switch-button"
              >注销登录</el-dropdown-item
            >
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <!-- 除开顶部导航区域以外的部分 -->
      <el-container>
        <el-aside width="200px">
          <!-- 参考ElementUI的NavMenu侧栏导航 
                Menu Events
                事件名称	        说明	          回调参数
                select	         菜单激活回调	    index: 选中菜单项的 index, indexPath: 选中菜单项的 index path
                
                <el-menu @select="menuClick">在el-menu标签中添加select事件，该事件有两个参数，第一个为index参数，第二个为indexPath参数，
                -->
          <el-menu @select="menuClick" unique-opened>
            <!-- 
              :index="index"避免同级菜单在打开一个一级菜单的时候，其他菜单也随之展开
              v-for遍历从服务器请求来的数据，以便实现动态加载菜单
             -->
            <el-submenu
              :index="index + ''"
              v-for="(item, index) in routes"
              v-if="!item.hidden"
              :key="index"
            >
              <template slot="title">
                <i
                  :class="item.iconCls"
                  style="margin-right:5px;"
                  v-bind:style="{ color: Color }"
                ></i>
                <span v-bind:style="{ color: Color }">{{ item.name }}</span>
              </template>
              <el-menu-item
                index="/test1"
                :index="child.path"
                v-for="(child, indexj) in item.children"
                :key="indexj"
                v-bind:style="{ color: Color }"
                >{{ child.name }}</el-menu-item
              >
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>
          <!-- 图标分隔法的面包屑导航
                v-if="this.$router.currentRoute.path != '/home'"
                为当面包屑路径为首页时，不显示导航
                而当面包屑路径不是首页时显示导航
           -->
          <el-breadcrumb
            separator-class="el-icon-arrow-right"
            v-if="this.$router.currentRoute.path != '/home'"
          >
            <el-breadcrumb-item :to="{ path: '/home' }"
              >首页</el-breadcrumb-item
            >
            <!-- this.$router.currentRoute.name获取当前路径的页面名字 -->
            <el-breadcrumb-item>{{
              this.$router.currentRoute.name
            }}</el-breadcrumb-item>
          </el-breadcrumb>
          <!-- 在首页添加一串欢迎来到微人事系统的提醒,且该文字串仅在首页显示 -->
          <div
            class="homeWelcome"
            v-if="this.$router.currentRoute.path == '/home'"
          >
            欢迎来到***公司人事管理系统！
          </div>
          <router-view class="homeRouterView" />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      bgc: null,
      Color: null
      // 由于用户信息已经存入SessionStorage里面了，所以使用window.sessionStorage.getItem
      // 读取用户信息，但考虑到使用的便捷性，使用JSON将其转换为JSON对象。
      // user: JSON.parse(window.sessionStorage.getItem("user"))
    };
  },
  computed: {
    // 添加从服务器请求到的数据routes属性
    routes() {
      return this.$store.state.routes;
    },
    user() {
      return this.$store.state.currentHr;
    }
  },
  methods: {
    commandHandler(cmd) {
      // 如果点击的值logout，则添加一个消息弹框以便确认用户注销的决定，并决定是否进行注销。
      // 参见ElementUI的Message弹框
      if (cmd == "logout") {
        this.$confirm("是否注销?", "提示", {
          confirmButtonText: "注销",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            this.getRequest("/logout");
            // 将下方的消息提示统一封装在api.js中
            //
            // 移除登录用户的信息
            window.sessionStorage.removeItem("user");
            this.$store.commit("initRoutes", []);
            // 将页面跳转至登录页面
            this.$router.replace("/");
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "取消注销"
            });
          });
      } else if (cmd == "userInfo") {
        this.$router.push("/hrinfo");
      }
    },
    menuClick(index) {
      // 测试index和indexPath参数,测试哪个参数为开发中需要的参数
      // indexPath参数显示结果为(2) ["1", "1-1"]
      // index参数显示结果为1-1
      // 所以参数只选用index即可
      // 更改标签<el-menu-item index="1-1">中的index值为路径，以便本方法使用
      this.$router.push(index);
    },
    initColor() {
      this.getRequest("/sys/cfg/").then(resp => {
        if (resp) {
          this.bgc = resp.backgroundColor;
          this.Color = resp.color;
        }
      });
    }
  },
  //生命周期 - 创建完成（访问当前this实例）
  created() {
    this.initColor();
  },
  //生命周期 - 挂载完成（访问DOM元素）
  mounted() {
    this.initColor();
  }
};
</script>

<style scoped>
/* @import url(); 引入css类 */
.homeRouterView {
  margin-top: 17px;
}
.homeWelcome {
  text-align: center;
  font-size: 30px;
  font-family: 华文行楷;
  color: #409dff;
  padding-top: 50px;
}
.homeHeader {
  display: flex;
  align-items: center; /*行内位置*/
  justify-content: space-between;
  padding: 0px 15px;
  box-sizing: border-box;
}
.homeHeader .title {
  font-size: 30px;
  font-family: 华文行楷;
  color: white;
}
/* cursor规则是设定网页浏览时用户鼠标指针的样式，也就是鼠标的图形形状
cursor：pointer设定鼠标的形状为一只伸出食指的手，这也是绝大多数浏览器里面鼠标停留在网页链接上方时候的样式
另外可以选择其他鼠标指针样式，常用的有default 箭头，crosshair 十字，progress 箭头和沙漏等等

此处为设置在Header右上角区域将鼠标指针样式改为手指方式
 */
.homeHeader .userInfo {
  cursor: pointer;
}
/* 控制系统管理员图标 */
.el-dropdown-link img {
  width: 48px;
  height: 48px;
  border-radius: 24px;
  margin-left: 8px;
}
/* 设置系统管理员文本居中 */
.el-dropdown-link {
  display: flex;
  align-items: center;
}
</style>
<!-- 
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
-->