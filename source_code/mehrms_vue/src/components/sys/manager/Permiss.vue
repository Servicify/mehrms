<!-- 权限管理 -->
<template>
  <div>
    <!-- 该区域为添加数据区域 -->
    <div class="permissManaTool">
      <!-- 参考ElementUI的Input输入框的复合型输入框 -->
      <el-input
        size="small"
        placeholder="请输入角色英文名（此项为必填项）"
        v-model="role.name"
      >
        <template slot="prepend">ROLE_</template>
      </el-input>
      <el-input
        size="small"
        placeholder="请输入角色中文名（此项为必填项）"
        v-model="role.nameZh"
        @keydown.enter.native="doAddRole"
      ></el-input>
      <el-button
        type="primary"
        size="small"
        icon="el-icon-plus"
        @click="doAddRole"
      >
        添加角色</el-button
      >
    </div>
    <!-- 该区域为显示数据及增删改操作区域 -->
    <div class="permissManaMain">
      <!-- 参考ElementUI的Collapse 折叠面板的手风琴效果 -->
      <el-collapse accordion @change="change" v-model="activeName">
        <el-collapse-item
          :title="r.nameZh"
          :name="r.id"
          v-for="(r, index) in roles"
          :key="index"
        >
          <!-- 参考ElementUI卡片组件 -->
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>可访问的资源</span>
              <!-- 面板一级子菜单删除按钮 -->
              <el-button
                style="float: right; padding: 3px 0;color:red"
                icon="el-icon-delete"
                type="text"
                @click="deleteRole(r)"
              ></el-button>
            </div>
            <div>
              <!-- 面板展示区域 ,show-checkbox为多选框-->
              <!-- 
                node-key="id"
                default-checked-keys="[7,8,9]"
                ref="tree"为该树形控件添加一个引用名称
                默认选中哪项
                参考Tree属性控件的默认展开和默认选中 -->
              <el-tree
                show-checkbox
                :data="allmenus"
                :key="index"
                ref="tree"
                :props="defaultProps"
                node-key="id"
                :default-checked-keys="selectedMenus"
              ></el-tree>
              <!-- 添加修改按钮 -->
              <div style="display:flex;justify-content:flex-end">
                <el-button size="small" @click="cancelUpdate"
                  >取消修改</el-button
                >
                <!--  doUpdate(r.id, index) 
                      index在前端列表中的指数
                      r.id该数据在数据库中的id
                      -->
                <el-button
                  size="small"
                  @click="doUpdate(r.id, index)"
                  type="primary"
                  >确认修改</el-button
                >
              </div>
            </div>
          </el-card>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
export default {
  name: "Permiss",
  data() {
    return {
      role: {
        name: "",
        nameZh: ""
      },
      roles: [],
      // 可访问菜单选项的菜单
      allmenus: [],
      defaultProps: {
        // 返回字符串的名称
        children: "children",
        // 展示的标签名为
        label: "name"
      },
      //
      selectedMenus: [],
      // 将activeName设置为-1，即值默认不展开
      activeName: -1
    };
  },
  methods: {
    // 定义一个initMethod()方法，该方法为初始化表格数据，或者刷新表格数据
    initRoles() {
      this.getRequest("/system/hr/permiss/").then(resp => {
        if (resp) {
          this.roles = resp;
        }
      });
    },
    change(rid) {
      // 实现折叠面板的点击事件
      // 测试
      // alert(name);
      if (rid) {
        this.initAllMenus();
        this.initSelectedMenus(rid);
      }
    },
    initAllMenus() {
      this.getRequest("/system/hr/permiss/menus").then(resp => {
        if (resp) {
          this.allmenus = resp;
        }
      });
    },
    initSelectedMenus(rid) {
      this.getRequest("/system/hr/permiss/mids/" + rid).then(resp => {
        if (resp) {
          this.selectedMenus = resp;
        }
      });
    },
    // 为确认修改添加点击事件，将修改分为删除和插入两个操作
    doUpdate(rid, index) {
      let tree = this.$refs.tree[index];
      // true使得菜单的选中选项只包括二级菜单，不包括一级菜单选项
      let selectedKeys = tree.getCheckedKeys(true);
      // 生成请求链接，并附带参数
      let url = "/system/hr/permiss/?rid=" + rid;
      console.log(url);
      selectedKeys.forEach(key => {
        url += "&mids=" + key;
      });
      // 发送请求
      this.putRequest(url).then(resp => {
        if (resp) {
          this.activeName = -1;
        }
      });
    },
    cancelUpdate() {
      this.activeName = -1;
    },
    doAddRole() {
      // 添加数据非空的过滤器
      if (this.role.name && this.role.nameZh) {
        this.postRequest("/system/hr/permiss/role", this.role).then(resp => {
          if (resp) {
            // 若请求成功，清空输入框的字符串的值
            this.role.name = "";
            this.role.nameZh = "";
            this.initRoles();
          }
        });
      } else {
        this.$message.error("数据不为空");
      }
    },
    deleteRole(role) {
      this.$confirm(
        "此操作将永久删除【" + role.nameZh + "】角色, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          this.deleteRequest("/system/hr/permiss/role/" + role.id).then(
            resp => {
              if (resp) {
                this.initRoles();
              }
            }
          );
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    }
  },
  //生命周期 - 创建完成（访问当前this实例）
  created() {},
  //生命周期 - 挂载完成（访问DOM元素）
  mounted() {
    this.initRoles();
  }
};
</script>
<style>
/* @import url(); 引入css类 */
.permissManaTool {
  display: flex;
  justify-content: flex-start;
}
.permissManaTool .el-input {
  width: 300px;
  margin-right: 7px;
}
.permissManaMain {
  margin-top: 10px;
  width: 700px;
}
</style>
<!-- 
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
-->