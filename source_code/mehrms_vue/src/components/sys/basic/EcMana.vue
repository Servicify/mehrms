<!-- 奖惩规则接口 -->
<!-- 批量删除接口完成，前端待完成 -->
<template>
  <div>
    <!-- 头部添加数据区域 -->
    <div>
      <!-- 单记录查询数据输入框 -->
      <el-input
        placeholder="请输入奖惩规则名进行搜索..."
        prefix-icon="el-icon-search"
        clearable
        @clear="initEcMana"
        style="width: 150px;margin-right: 10px"
        v-model="EcMana.ruleName"
        @keydown.enter.native="selectByRuleName(EcMana)"
      ></el-input>
      <!-- 单记录查询按钮 -->
      <el-tooltip
        class="item"
        effect="light"
        content="查询数据仅需输入对应的规则名称"
        placement="left-end"
      >
        <el-button
          icon="el-icon-search"
          type="success"
          size="large"
          @click="selectByRuleName(EcMana)"
        >
          搜索
        </el-button>
      </el-tooltip>
      <!-- 添加奖惩规则名称数据 ，使用clearable属性即可得到一个可清空的输入框-->
      <el-tag type="success" size="large" style="margin:0px 6px"
        ><span style="color:red">&nbsp;*&nbsp;</span>规则名称：</el-tag
      >
      <el-input
        placeholder="请输入奖惩规则名称"
        v-model="newEcMana.ruleName"
        clearable
        style="width:200px"
      >
      </el-input>
      <!-- 奖惩规则的分值 -->
      <el-tag type="success" size="large" style="margin:0px 7px 0px 12px"
        ><span style="color:red">&nbsp;*&nbsp;</span>规则分值：</el-tag
      >
      <el-select
        v-model="newEcMana.ruleValue"
        slot="prepend"
        placeholder="请选择"
        style="width:200px;"
      >
        <el-option label="奖励1分" value="1"></el-option>
        <el-option label="奖励2分" value="2"></el-option>
        <el-option label="奖励3分" value="3"></el-option>
        <el-option label="奖励4分" value="4"></el-option>
        <el-option label="奖励5分" value="5"></el-option>
        <el-option label="奖励6分" value="6"></el-option>
        <el-option label="奖励7分" value="7"></el-option>
        <el-option label="奖励8分" value="8"></el-option>
        <el-option label="奖励9分" value="9"></el-option>
        <el-option label="奖励10分" value="10"></el-option>
        <el-option label="扣除1分" value="-1"></el-option>
        <el-option label="扣除2分" value="-2"></el-option>
        <el-option label="扣除3分" value="-3"></el-option>
        <el-option label="扣除4分" value="-4"></el-option>
        <el-option label="扣除5分" value="-5"></el-option>
        <el-option label="扣除6分" value="-6"></el-option>
        <el-option label="扣除7分" value="-7"></el-option>
        <el-option label="扣除8分" value="-8"></el-option>
        <el-option label="扣除9分" value="-9"></el-option>
        <el-option label="扣除10分" value="-10"></el-option>
      </el-select>
      <!-- 添加按钮 -->
      <el-button
        type="success"
        @click="addRule(newEcMana)"
        size="large"
        style="margin:0px 0px 7px 20px"
        icon="el-icon-plus"
        >添加数据</el-button
      >
    </div>
    <!-- 查询奖惩信息数据表格化 -->
    <div>
      <el-table
        :data="EcManas"
        v-loading="loading"
        element-loading-text="正在加载..."
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0, 0, 0, 0.8)"
        size="small"
        border
        style="width: 81%"
      >
        <el-table-column type="index" label="序号" fixed width="50">
        </el-table-column>
        <el-table-column
          prop="createDate"
          label="创建时间"
          width="200"
        ></el-table-column>
        <el-table-column
          prop="ruleName"
          label="奖惩名称"
          width="200"
        ></el-table-column>
        <el-table-column
          prop="ruleValue"
          label="奖惩分值"
          width="159"
        ></el-table-column>
        <el-table-column prop="enabled" label="是否启用" width="114">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.enabled">已启用</el-tag>
            <el-tag type="danger" v-else>未启用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="220">
          <template slot-scope="scope">
            <el-button
              @click="showEditView(scope.row)"
              type="text"
              size="small"
              icon="el-icon-edit"
              >修改</el-button
            >
            <el-button
              type="text"
              size="small"
              @click="deleteEcMana(scope.row)"
              icon="el-icon-delete"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 修改框 -->
    <el-dialog title="修改奖惩规则" :visible.sync="dialogVisible" width="30%">
      <div>
        <table>
          <tr>
            <td>
              <el-tag>规则名称：</el-tag>
            </td>
            <td>
              <el-input size="small" v-model="EcMana.ruleName"></el-input>
            </td>
          </tr>
          <tr>
            <td>
              <el-tag>奖惩分值：</el-tag>
            </td>
            <td>
              <el-select
                v-model="EcMana.ruleValue"
                slot="prepend"
                placeholder="请选择"
                style="width:200px;"
              >
                <el-option label="奖励1分" value="1"></el-option>
                <el-option label="奖励2分" value="2"></el-option>
                <el-option label="奖励3分" value="3"></el-option>
                <el-option label="奖励4分" value="4"></el-option>
                <el-option label="奖励5分" value="5"></el-option>
                <el-option label="奖励6分" value="6"></el-option>
                <el-option label="奖励7分" value="7"></el-option>
                <el-option label="奖励8分" value="8"></el-option>
                <el-option label="奖励9分" value="9"></el-option>
                <el-option label="奖励10分" value="10"></el-option>
                <el-option label="扣除1分" value="-1"></el-option>
                <el-option label="扣除2分" value="-2"></el-option>
                <el-option label="扣除3分" value="-3"></el-option>
                <el-option label="扣除4分" value="-4"></el-option>
                <el-option label="扣除5分" value="-5"></el-option>
                <el-option label="扣除6分" value="-6"></el-option>
                <el-option label="扣除7分" value="-7"></el-option>
                <el-option label="扣除8分" value="-8"></el-option>
                <el-option label="扣除9分" value="-9"></el-option>
                <el-option label="扣除10分" value="-10"></el-option>
              </el-select>
            </td>
          </tr>
          <tr>
            <td>
              <el-tag>是否启用</el-tag>
            </td>
            <td>
              <el-switch
                v-model="EcMana.enabled"
                active-text="启用"
                inactive-text="禁用"
              >
              </el-switch>
            </td>
          </tr>
        </table>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="editEcMana(EcMana)"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "EcMana",
  data() {
    return {
      // 表格加载状态
      loading: false,
      // 添加数据绑定对象
      newEcMana: {
        // 绑定奖惩规则名称输入框数据
        ruleName: "",
        // 绑定奖惩规则的分值
        ruleValue: "请输入奖励分值"
      },
      //查询信息接受接收对象
      EcManas: [],
      //修改信息接受对象
      EcMana: { id: "", ruleName: "", ruleValue: "", enabled: "" },
      //打开修改面板弹窗
      dialogVisible: false
    };
  },
  methods: {
    // 通过名字查询单条记录
    selectByRuleName(EcMana) {
      this.postRequest("/system/basic/ecmana/rulename", this.EcMana).then(
        resp => {
          if (resp) {
            this.EcManas = resp;
          }
        }
      );
    },
    // 操作删除
    deleteEcMana(row) {
      this.$confirm(
        "此操作将永久删除【" + row.ruleName + "】奖惩规则, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          this.deleteRequest("/system/basic/ecmana/" + row.id).then(resp => {
            if (resp) {
              this.initEcMana();
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
    // 操作修改
    editEcMana(EcMana) {
      // 测试数据
      this.putRequest("/system/basic/ecmana/", this.EcMana).then(resp => {
        if (resp) {
          this.initEcMana();
          this.dialogVisible = false;
        }
      });
    },
    // 打开修改弹出框
    showEditView(row) {
      // this.editEcMana.id = row.id;
      // this.editEcMana.ruleName = row.ruleName;
      // this.editEcMana.ruleValue = row.ruleValue;
      // this.editEcMana.enabled = row.enabled;
      Object.assign(this.EcMana, row);
      this.dialogVisible = true;
    },
    // 添加数据
    addRule(newEcMana) {
      // 测试newEcMana数据
      if (
        this.newEcMana.ruleName &&
        this.newEcMana.ruleValue >= -10 &&
        this.newEcMana.ruleValue <= 10
      ) {
        this.postRequest("/system/basic/ecmana/", this.newEcMana).then(resp => {
          if (resp) {
            this.initEcMana();
          }
        });
      } else {
        this.$message.error("添加字段不可以为空!");
      }
    },
    // 初始化数据
    initEcMana() {
      this.getRequest("/system/basic/ecmana/").then(resp => {
        if (resp) {
          this.EcManas = resp;
          this.EcMana = {
            id: "",
            ruleName: "",
            ruleValue: "",
            enabled: ""
          };
          this.newEcMana = {
            id: "",
            ruleName: "",
            ruleValue: "",
            enabled: ""
          };
        }
      });
    }
  },
  //生命周期 - 创建完成（访问当前this实例）
  created() {},
  //生命周期 - 挂载完成（访问DOM元素）
  mounted() {
    this.initEcMana();
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