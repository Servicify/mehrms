<!--  -->
<template>
  <div>
    <div>
      <!-- 添加职位输入框 -->
      <el-tag type="success" size="large" style="margin:0px 6px"
        ><span style="color:red">&nbsp;*&nbsp;</span>职位名称：</el-tag
      >
      <el-input
        size="small"
        class="addPosInput"
        placeholder="添加职位..."
        prefix-icon="el-icon-plus"
        @keydown.enter.native="addPositon"
        v-model="pos.name"
      >
      </el-input>
      <!-- 添加职位按钮 -->
      <el-button
        icon="el-icon-plus"
        size="small"
        type="success"
        @click="addPositon"
        >添加</el-button
      >
    </div>
    <div class="posManaMain">
      <!-- 职位管理数据展示表格 -->
      <el-table
        :data="positions"
        border
        stripe
        style="width: 70%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="index" label="序号" fixed width="50">
        </el-table-column>
        <el-table-column prop="name" label="职位名称" width="180">
        </el-table-column>
        <el-table-column prop="createDate" label="创建时间" width="150">
        </el-table-column>
        <el-table-column label="是否启用">
          <template slot-scope="scope">
            <el-tag size="small" type="success" v-if="scope.row.enabled">
              已启用
            </el-tag>
            <el-tag size="small" type="danger" v-else>
              未启用
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="showEditView(scope.$index, scope.row)"
              icon="el-icon-edit"
              >修改</el-button
            >
            <el-button
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)"
              icon="el-icon-delete"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <el-button
        type="danger"
        size="small"
        style="margin-top:8px"
        :disabled="multipleSelection.length == 0"
        @click="deleteMany"
        >批量删除</el-button
      >
    </div>
    <!-- 修改弹出提示框 -->
    <el-dialog title="修改职位" :visible.sync="dialogVisible" width="30%">
      <div>
        <div>
          <el-tag>职位名称</el-tag>
          <el-input
            class="updatePosInput"
            size="small"
            v-model="updatePos.name"
          ></el-input>
        </div>
        <div>
          <el-tag>是否启用</el-tag>
          <el-switch
            v-model="updatePos.enabled"
            active-text="启用"
            inactive-text="禁用"
          >
          </el-switch>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="doUpdate"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "PosMana",
  data() {
    return {
      pos: {
        name: ""
      },
      dialogVisible: false,
      updatePos: {
        name: "",
        enabled: false
      },
      positions: [],
      // 批量删除的数组
      multipleSelection: []
    };
  },
  methods: {
    deleteMany() {
      this.$confirm(
        "此操作将永久删除【" +
          this.multipleSelection.length +
          "】条记录, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          // 通过遍历生成请求地址参数
          let ids = "?";
          this.multipleSelection.forEach(item => {
            ids += "ids=" + item.id + "&";
          });
          // 发送请求数组
          this.deleteRequest("/system/basic/pos/" + ids).then(resp => {
            if (resp) {
              this.initPositions();
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 发送查询职位管理Position表中的全部数据,由于该查询请求需要系统自动的去执行，
    // 故必须使用vue生命周期的mounted()钩子函数来自动执行请求。
    initPositions() {
      this.getRequest("/system/basic/pos/").then(resp => {
        if (resp) {
          this.positions = resp;
        }
      });
    },
    doUpdate() {
      this.putRequest("/system/basic/pos/", this.updatePos).then(resp => {
        if (resp) {
          // 如果请求成功，刷新页面的数据，并清空避免脏读所设置的中间存储数据对象updatePos的数据对象的值
          this.initPositions();
          this.updatePos.name = "";
          this.dialogVisible = false;
        }
      });
    },
    // 打开修改的弹出对话框
    showEditView(index, data) {
      // Object.assign方法用于对象的合并，将源对象【即第一个参数之后的所有参数】（source）的所有可枚举属性，复制到目标对象
      //（target）。
      // this.updatePos=data,其本质上，updatePos的引用地址和data的引用地址实际上为同一个。
      // 而Object.assign则不会。
      Object.assign(this.updatePos, data);
      this.dialogVisible = true;
    },
    handleDelete(index, data) {
      // 职位删除需要确认。当职位在其他表中引用时则会报错。
      this.$confirm(
        "此操作将永久删除【" + data.name + "】职位, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          this.deleteRequest("system/basic/pos/" + data.id).then(resp => {
            if (resp) {
              // 删除成功，刷新数据
              this.initPositions();
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    addPositon() {
      if (this.pos.name) {
        this.postRequest("/system/basic/pos/", this.pos).then(resp => {
          if (resp) {
            // 如果数据添加成功，刷新表格
            this.initPositions();
            // 添加完成后清空输入框
            this.pos = "";
          }
        });
      } else {
        this.$message.error("输入栏数据不能为空！");
      }
    }
  },
  //生命周期 - 创建完成（访问当前this实例）
  created() {},
  //生命周期 - 挂载完成（访问DOM元素）
  mounted() {
    // 当组件注册使用完成后，系统自动发送职位的查询请求
    this.initPositions();
  }
};
</script>
<style scoped>
/* @import url(); 引入css类 */
.addPosInput {
  width: 300px;
  margin-right: 8px;
}
.posManaMain {
  margin-top: 10px;
}
/* 对弹出对话的输入框进行样式的调整 */
.updatePosInput {
  width: 200px;
  margin-left: 8px;
}
</style>
<!-- 
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
-->