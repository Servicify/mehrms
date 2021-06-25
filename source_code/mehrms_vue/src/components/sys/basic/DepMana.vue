<!-- 部门管理 -->
<template>
  <div style="width: 500px;">
    <!-- el-icon-search为搜索图标 -->
    <el-input
      placeholder="请输入部门名称进行搜索..."
      prefix-icon="el-icon-search"
      v-model="filterText"
    >
    </el-input>

    <el-tree
      :data="deps"
      :props="defaultProps"
      :expand-on-click-node="false"
      :filter-node-method="filterNode"
      ref="tree"
    >
      <!-- 添加按钮和删除按钮 -->
      <span
        class="custom-tree-node"
        style="display: flex;justify-content: space-between;width: 100%;"
        slot-scope="{ node, data }"
      >
        <span>{{ data.name }}</span>
        <span>
          <el-button
            type="primary"
            size="mini"
            class="depBtn"
            @click="() => showAddDepView(data)"
          >
            添加部门
          </el-button>
          <el-button
            type="danger"
            size="mini"
            class="depBtn"
            @click="() => deleteDep(data)"
          >
            删除部门
          </el-button>
        </span>
      </span>
    </el-tree>
    <el-dialog title="添加部门" :visible.sync="dialogVisible" width="30%">
      <div>
        <table>
          <tr>
            <td>
              <el-tag>上级部门</el-tag>
            </td>
            <td>{{ pname }}</td>
          </tr>
          <tr>
            <td>
              <el-tag>部门名称</el-tag>
            </td>
            <td>
              <el-input
                v-model="dep.name"
                placeholder="请输入部门名称..."
              ></el-input>
            </td>
          </tr>
        </table>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="doAddDep">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "DepMana",
  data() {
    return {
      filterText: "",
      deps: [],
      defaultProps: {
        children: "children",
        label: "name"
      },
      dep: {
        name: "",
        parentId: -1
      },
      pname: "",
      dialogVisible: false
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  //生命周期 - 挂载完成（访问DOM元素）
  mounted() {
    this.initDeps();
  },
  methods: {
    initDeps() {
      this.getRequest("/system/basic/department/").then(resp => {
        if (resp) {
          this.deps = resp;
        }
      });
    },
    showAddDepView(data) {
      this.pname = data.name;
      this.dep.parentId = data.id;
      this.dialogVisible = true;
    },
    // 添加一个刷新方法
    initDep() {
      this.dep = {
        name: "",
        parentId: -1
      };
      this.pname = "";
    },
    // 删除该方法
    deleteDep(data) {
      // 如果父部门存在子部门，则删除失败
      if (data.parent) {
        this.$message.error("父部门删除失败");
      } else {
        this.$confirm(
          "此操作将永久删除【" + data.name + "】部门, 是否继续?",
          "提示",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        )
          .then(() => {
            this.deleteRequest("/system/basic/department/" + data.id).then(
              resp => {
                if (resp) {
                  this.removeDepFromDeps(null, this.deps, data.id);
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
    // 查询输入框的调用方法，data为JSON对象，value为输入框的传入值
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    // 添加操作
    doAddDep() {
      this.postRequest("/system/basic/department/", this.dep).then(resp => {
        if (resp) {
          // resp.obj是一个添加数据后的，返回的数据中的obj对象中的数据
          // {
          //     "status": 200,
          //     "msg": "添加成功",
          //     "obj": {
          //         "id": 105,
          //         "name": "运维5部",
          //         "parentId": 92,
          //         "depPath": null,
          //         "enabled": true,
          //         "children": null,
          //         "parent": null,
          //         "result": 1
          //     }
          // }
          this.addDep2Deps(this.deps, resp.obj);
          this.dialogVisible = false;
          //初始化变量
          this.initDep();
        }
      });
    },
    // 功能为避免添加数据后，收起树枝
    // 【【【BUG】】】在树枝尖部添加数据，树枝既不会收拢，也不会打开子树枝
    addDep2Deps(deps, dep) {
      // 遍历数组
      for (let i = 0; i < deps.length; i++) {
        let d = deps[i];
        if (d.id == dep.parentId) {
          // 在添加数据的父部门处通过在原来的数据集中添加刚插入的部门数据
          d.children = d.children.concat(dep);
          // 添加子部门后，将该项设置为父部门
          if (d.children.length > 0) {
            d.parent = true;
          }
          return;
        } else {
          // 否则递归调用
          this.addDep2Deps(d.children, dep);
        }
      }
    },
    // 删除功能，在该级部门既没有子部门，且父部门存在的情况下，才可删除
    removeDepFromDeps(p, deps, id) {
      // 遍历树
      for (let i = 0; i < deps.length; i++) {
        // 使用递归遍历树枝末梢，对页面显示数据中既不是根父节点的，也没有子节点的元素进行删除
        let d = deps[i];
        if (d.id == id) {
          deps.splice(i, 1);
          // 如果移除子项后，与该项同级的数组元素为0，那么该项不在是父元素
          if (deps.length == 0) {
            p.parent = false;
          }
          return;
        } else {
          // 递归
          this.removeDepFromDeps(d, d.children, id);
        }
      }
    }
  },
  //生命周期 - 创建完成（访问当前this实例）
  created() {}
};
</script>
<style scoped>
/* @import url(); 引入css类 */
.depBtn {
  padding: 2px;
}
</style> 
<!-- 
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
-->