<!-- 员工资料 -->
<!-- 员工信息校正实验页 -->
<!-- 
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/
-->
<template>
  <div>
    <!-- 员工数据操作按钮区域 -->
    <div>
      <!-- 刷新数据 -->
      <el-button icon="el-icon-refresh" type="primary" @click="refresh">
        刷新数据
      </el-button>
      <!-- 搜索按钮 -->
      <el-button icon="el-icon-search" type="primary" @click="searchItem">
        搜索员工
      </el-button>
      <!-- 下载导入模板 -->
      <el-button
        type="success"
        @click="downloadTemplate"
        icon="el-icon-download"
      >
        下载模板
      </el-button>
      <!-- 添加员工按钮 -->
      <el-button
        type="primary"
        icon="el-icon-plus"
        @click="addItem"
        style="margin-right: 10px"
      >
        添加员工
      </el-button>
      <!-- 上传excel表格数据按钮 -->
      <el-upload
        :show-file-list="false"
        :before-upload="beforeUpload"
        :on-success="onSuccess"
        :on-error="onError"
        :disabled="importDataDisabled"
        style="display: inline-flex;margin-right: 8px"
        action="/personnel/emp/import"
      >
        <el-button
          :disabled="importDataDisabled"
          type="success"
          :icon="importDataBtnIcon"
        >
          {{ importDataBtnText }}
        </el-button>
      </el-upload>
      <!-- 导出数据按钮 -->
      <el-button type="success" @click="exportData" icon="el-icon-download">
        导出数据
      </el-button>
    </div>
    <!-- 员工数据显示区域 -->
    <div>
      <el-table
        :data="emps"
        stripe
        border
        v-loading="loading"
        element-loading-text="正在加载..."
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0, 0, 0, 0.8)"
        style="width: 100%;margin-top:10px"
      >
        <el-table-column type="index" label="序号" fixed width="50">
        </el-table-column>
        <!-- 使用fixed属性，姓名列冻结屏幕上，align="left"调整该列在左侧冻结 -->
        <el-table-column prop="name" fixed align="left" label="姓名" width="90">
        </el-table-column>
        <el-table-column prop="workID" label="工号" align="left" width="85">
        </el-table-column>
        <el-table-column prop="gender" label="性别" align="left" width="85">
        </el-table-column>
        <el-table-column
          prop="birthday"
          width="95"
          align="left"
          label="出生日期"
        >
        </el-table-column>
        <el-table-column
          prop="idCard"
          width="150"
          align="left"
          label="身份证号码"
        >
        </el-table-column>
        <el-table-column prop="wedlock" width="70" label="婚姻状况">
        </el-table-column>
        <el-table-column prop="nation.name" width="50" label="民族">
        </el-table-column>
        <el-table-column prop="nativePlace" width="80" label="籍贯">
        </el-table-column>
        <el-table-column prop="politicsstatus.name" label="政治面貌">
        </el-table-column>
        <el-table-column prop="email" width="180" align="left" label="电子邮件">
        </el-table-column>
        <el-table-column prop="phone" width="100" align="left" label="电话号码">
        </el-table-column>
        <el-table-column
          prop="address"
          width="220"
          align="left"
          label="联系地址"
        >
        </el-table-column>
        <el-table-column
          prop="department.name"
          width="100"
          align="left"
          label="所属部门"
        >
        </el-table-column>
        <el-table-column prop="position.name" width="100" label="职位">
        </el-table-column>
        <el-table-column prop="jobLevel.name" width="100" label="职称">
        </el-table-column>
        <el-table-column
          prop="engageForm"
          width="100"
          align="left"
          label="聘用形式"
        >
        </el-table-column>
        <el-table-column
          prop="tiptopDegree"
          width="80"
          align="left"
          label="最高学历"
        >
        </el-table-column>
        <el-table-column prop="specialty" width="150" align="left" label="专业">
        </el-table-column>
        <el-table-column
          prop="school"
          width="150"
          align="left"
          label="毕业院校"
        >
        </el-table-column>
        <el-table-column
          prop="beginDate"
          width="95"
          align="left"
          label="入职日期"
        >
        </el-table-column>
        <el-table-column
          prop="conversionTime"
          width="95"
          align="left"
          label="转正日期"
        >
        </el-table-column>
        <el-table-column
          prop="beginContract"
          width="95"
          align="left"
          label="合同起始日期"
        >
        </el-table-column>
        <el-table-column
          prop="endContract"
          width="95"
          align="left"
          label="合同截止日期"
        >
        </el-table-column>
        <el-table-column
          prop="workState"
          width="95"
          align="left"
          label="在职状态"
        ></el-table-column>
        <el-table-column
          prop="notWorkDate"
          width="95"
          align="left"
          label="离职日期"
        ></el-table-column>
        <el-table-column
          prop="work_age.beforeWorkAge"
          width="95"
          align="left"
          label="入职前工龄"
        ></el-table-column>
        <el-table-column
          prop="work_age.afterWorkAge"
          width="95"
          align="left"
          label="入职后工龄"
        ></el-table-column>
        <el-table-column
          prop="work_age.workAge"
          width="95"
          align="left"
          label="总工龄"
        ></el-table-column>
        <el-table-column width="100" align="left" label="合同期限">
          <template slot-scope="scope">
            <el-tag>{{ scope.row.contractTerm }}</el-tag>
            年
          </template>
        </el-table-column>
        <el-table-column fixed="right" width="300" label="操作">
          <template slot-scope="scope">
            <el-button
              @click="editItem(scope.row)"
              type="primary"
              icon="el-icon-edit"
              >编辑&nbsp;&nbsp;&nbsp;</el-button
            >
            <el-button
              @click="deleteItem(scope.row)"
              type="danger"
              icon="el-icon-delete"
              >删除&nbsp;&nbsp;&nbsp;
            </el-button>

            <el-tooltip
              class="item"
              effect="light"
              content="入职邮件已发送，点此可重新发送"
              placement="bottom"
              v-show="
                scope.row.empInfoMailStatus != null &&
                  scope.row.empInfoMailStatus.status == 1
              "
            >
              <el-button
                type="success"
                icon="el-icon-message"
                v-show="
                  scope.row.empInfoMailStatus != null &&
                    scope.row.empInfoMailStatus.status == 1
                "
                @click="sendMail(scope.row.id)"
                >已发送</el-button
              >
            </el-tooltip>
            <el-tooltip
              class="item"
              effect="light"
              content="入职邮件尚未发送，点此重新发送"
              placement="bottom"
              v-show="
                scope.row.empInfoMailStatus == null ||
                  scope.row.empInfoMailStatus.status == 0
              "
            >
              <el-button
                type="info"
                icon="el-icon-message"
                v-show="
                  scope.row.empInfoMailStatus == null ||
                    scope.row.empInfoMailStatus.status == 0
                "
                @click="sendMail(scope.row.id)"
                >未发送</el-button
              >
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 分页控件区域 -->
    <div>
      <div style="display: flex;justify-content: flex-end">
        <el-pagination
          background
          @size-change="sizeChange"
          @current-change="currentChange"
          layout="sizes, prev, pager, next, jumper, ->, total, slot"
          :total="total"
        >
        </el-pagination>
      </div>
    </div>
    <!-- 添加、修改、查询框 -->
    <div>
      <!-- 添加员工弹出框 -->
      <el-dialog :title="title" :visible.sync="dialogVisible1" width="80%">
        <div>
          <!-- :model="emp" :rules="rules"添加表单校验规则 
        ref="empForm"为添加员工之前的一个表单校验-->
          <el-form :model="emp" :rules="rules" ref="empForm">
            <el-row>
              <el-col :span="6">
                <el-form-item label="姓名:" prop="name">
                  <el-input
                    size="mini"
                    style="width: 150px"
                    prefix-icon="el-icon-edit"
                    v-model="emp.name"
                    placeholder="请输入员工姓名"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="5">
                <el-form-item label="性别:" prop="gender">
                  <el-radio-group v-model="emp.gender">
                    <el-radio label="男">男</el-radio>
                    <el-radio label="女">女</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="出生日期:" prop="birthday">
                  <el-date-picker
                    v-model="emp.birthday"
                    size="mini"
                    type="date"
                    value-format="yyyy-MM-dd"
                    style="width: 150px;"
                    placeholder="出生日期"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="7">
                <el-form-item label="政治面貌:" prop="politicId">
                  <el-select
                    v-model="emp.politicId"
                    placeholder="政治面貌"
                    size="mini"
                    style="width: 200px;"
                  >
                    <el-option
                      v-for="item in politicsstatus"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="民族:" prop="nationId">
                  <el-select
                    v-model="emp.nationId"
                    placeholder="民族"
                    size="mini"
                    style="width: 150px;"
                  >
                    <el-option
                      v-for="item in nations"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="5">
                <el-form-item label="籍贯:" prop="nativePlace">
                  <el-input
                    size="mini"
                    style="width: 120px"
                    prefix-icon="el-icon-edit"
                    v-model="emp.nativePlace"
                    placeholder="请输入籍贯"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="电子邮箱:" prop="email">
                  <el-input
                    size="mini"
                    style="width: 150px"
                    prefix-icon="el-icon-message"
                    v-model="emp.email"
                    placeholder="请输入电子邮箱"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="7">
                <el-form-item label="联系地址:" prop="address">
                  <el-input
                    size="mini"
                    style="width: 200px"
                    prefix-icon="el-icon-edit"
                    v-model="emp.address"
                    placeholder="请输入联系地址"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="职位:" prop="posId">
                  <el-select
                    v-model="emp.posId"
                    placeholder="职位"
                    size="mini"
                    style="width: 150px;"
                  >
                    <el-option
                      v-for="item in positions"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="5">
                <el-form-item label="职称:" prop="jobLevelId">
                  <el-select
                    v-model="emp.jobLevelId"
                    placeholder="职称"
                    size="mini"
                    style="width: 150px;"
                  >
                    <el-option
                      v-for="item in joblevels"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="所属部门:" prop="departmentId">
                  <el-popover
                    placement="right"
                    title="请选择部门"
                    width="200"
                    trigger="manual"
                    v-model="popVisible"
                  >
                    <el-tree
                      default-expand-all
                      :data="allDeps"
                      :props="defaultProps"
                      :expand-on-click-node="false"
                      @node-click="handleNodeClick"
                    ></el-tree>
                    <div
                      slot="reference"
                      style="width: 150px;display: inline-flex;font-size: 13px;border: 1px solid #dedede;height: 26px;border-radius: 5px;cursor: pointer;align-items: center;padding-left: 8px;box-sizing: border-box"
                      @click="showDepView"
                    >
                      {{ inputDepName }}
                    </div>
                  </el-popover>
                </el-form-item>
              </el-col>
              <el-col :span="7">
                <el-form-item label="电话号码:" prop="phone">
                  <el-input
                    size="mini"
                    style="width: 200px"
                    prefix-icon="el-icon-phone"
                    v-model="emp.phone"
                    placeholder="电话号码"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="工号:" prop="workID">
                  <el-input
                    size="mini"
                    style="width: 150px"
                    prefix-icon="el-icon-edit"
                    v-model="emp.workID"
                    placeholder="工号"
                    disabled
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="5">
                <el-form-item label="学历:" prop="tiptopDegree">
                  <el-select
                    v-model="emp.tiptopDegree"
                    placeholder="学历"
                    size="mini"
                    style="width: 150px;"
                  >
                    <el-option
                      v-for="item in tiptopDegrees"
                      :key="item"
                      :label="item"
                      :value="item"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="毕业院校:" prop="school">
                  <el-input
                    size="mini"
                    style="width: 150px"
                    prefix-icon="el-icon-edit"
                    v-model="emp.school"
                    placeholder="毕业院校名称"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="7">
                <el-form-item label="专业名称:" prop="specialty">
                  <el-input
                    size="mini"
                    style="width: 200px"
                    prefix-icon="el-icon-edit"
                    v-model="emp.specialty"
                    placeholder="请输入专业名称"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="入职日期:" prop="beginDate">
                  <el-date-picker
                    v-model="emp.beginDate"
                    size="mini"
                    type="date"
                    value-format="yyyy-MM-dd"
                    style="width: 130px;"
                    placeholder="入职日期"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="转正日期:" prop="conversionTime">
                  <el-date-picker
                    v-model="emp.conversionTime"
                    size="mini"
                    type="date"
                    value-format="yyyy-MM-dd"
                    style="width: 130px;"
                    placeholder="转正日期"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="合同起始日期:" prop="beginContract">
                  <el-date-picker
                    v-model="emp.beginContract"
                    size="mini"
                    type="date"
                    value-format="yyyy-MM-dd"
                    style="width: 130px;"
                    placeholder="合同起始日期"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="合同终止日期:" prop="endContract">
                  <el-date-picker
                    v-model="emp.endContract"
                    size="mini"
                    type="date"
                    value-format="yyyy-MM-dd"
                    style="width: 150px;"
                    placeholder="合同终止日期"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="身份证号码:" prop="idCard">
                  <el-input
                    size="mini"
                    style="width: 180px"
                    prefix-icon="el-icon-edit"
                    v-model="emp.idCard"
                    placeholder="请输入身份证号码"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="聘用形式:" prop="engageForm">
                  <el-radio-group v-model="emp.engageForm">
                    <el-radio label="劳动合同">劳动合同</el-radio>
                    <el-radio label="劳务合同">劳务合同</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="婚姻状况:" prop="wedlock">
                  <el-radio-group v-model="emp.wedlock">
                    <el-radio label="已婚">已婚</el-radio>
                    <el-radio label="未婚">未婚</el-radio>
                    <el-radio label="离异">离异</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="工作状态:" prop="workState">
                  <el-radio-group v-model="emp.workState">
                    <el-radio label="在职">在职</el-radio>
                    <el-radio label="离职">离职</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="离职日期:" prop="notWorkDate">
                  <el-date-picker
                    v-model="emp.notWorkDate"
                    size="mini"
                    type="date"
                    value-format="yyyy-MM-dd"
                    style="width: 130px;"
                    placeholder="离职日期"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="入职前工龄(年):" prop="beforeWorkAge">
                  <!-- v-model.number使得输入框中的值为数字类型，但只能保持输入整数类型
                   -->
                  <el-input
                    size="mini"
                    style="width: 150px"
                    prefix-icon="el-icon-edit"
                    v-model="emp.beforeWorkAge"
                    placeholder="入职前工龄"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="closed">取 消</el-button>
          <el-button type="primary" @click="onSubmit">确 定</el-button>
        </span>
      </el-dialog>
      <!-- 查询员工弹出框 -->
      <el-dialog :title="title" :visible.sync="dialogVisible2" width="80%">
        <div>
          <!-- :model="emp" :rules="rules"添加表单校验规则 
        ref="empForm"为添加员工之前的一个表单校验-->
          <el-form :model="emp" :rules="rules" ref="empForm">
            <el-row>
              <el-col :span="6">
                <el-form-item label="姓名:" prop="name">
                  <el-input
                    size="mini"
                    style="width: 150px"
                    prefix-icon="el-icon-edit"
                    v-model="emp.name"
                    placeholder="请输入员工姓名"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="政治面貌:" prop="politicId">
                  <el-select
                    v-model="emp.politicId"
                    placeholder="政治面貌"
                    size="mini"
                    style="width: 200px;"
                  >
                    <el-option
                      v-for="item in politicsstatus"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="民族:" prop="nationId">
                  <el-select
                    v-model="emp.nationId"
                    placeholder="民族"
                    size="mini"
                    style="width: 150px;"
                  >
                    <el-option
                      v-for="item in nations"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="职位:" prop="posId">
                  <el-select
                    v-model="emp.posId"
                    placeholder="职位"
                    size="mini"
                    style="width: 150px;"
                  >
                    <el-option
                      v-for="item in positions"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="5">
                <el-form-item label="职称:" prop="jobLevelId">
                  <el-select
                    v-model="emp.jobLevelId"
                    placeholder="职称"
                    size="mini"
                    style="width: 150px;"
                  >
                    <el-option
                      v-for="item in joblevels"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="所属部门:" prop="departmentId">
                  <el-popover
                    placement="right"
                    title="请选择部门"
                    width="200"
                    trigger="manual"
                    v-model="popVisible"
                  >
                    <el-tree
                      default-expand-all
                      :data="allDeps"
                      :props="defaultProps"
                      :expand-on-click-node="false"
                      @node-click="handleNodeClick"
                    ></el-tree>
                    <div
                      slot="reference"
                      style="width: 150px;display: inline-flex;font-size: 13px;border: 1px solid #dedede;height: 26px;border-radius: 5px;cursor: pointer;align-items: center;padding-left: 8px;box-sizing: border-box"
                      @click="showDepView"
                    >
                      {{ inputDepName }}
                    </div>
                  </el-popover>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="工作状态:" prop="workState">
                  <el-radio-group v-model="emp.workState">
                    <el-radio label="在职">在职</el-radio>
                    <el-radio label="离职">离职</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="离职日期:" prop="notWorkDate">
                  <el-date-picker
                    v-model="emp.notWorkDate"
                    size="mini"
                    type="date"
                    value-format="yyyy-MM-dd"
                    style="width: 130px;"
                    placeholder="离职日期"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-col :span="8">
              <el-form-item label="聘用形式:" prop="engageForm">
                <el-radio-group v-model="emp.engageForm">
                  <el-radio label="劳动合同">劳动合同</el-radio>
                  <el-radio label="劳务合同">劳务合同</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-form>
          <el-col :span="10">
            入职日期:
            <el-date-picker
              v-model="beginDateScope"
              type="daterange"
              size="mini"
              unlink-panels
              value-format="yyyy-MM-dd"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </el-col>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="closed">取 消</el-button>
          <el-button type="primary" @click="onSubmit">确 定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  name: "EmpAdv",
  data() {
    return {
      emps: [],
      total: 0,
      currentPage: 1,
      currentSize: 10,
      emp: {
        id: null,
        name: null,
        gender: null,
        birthday: null,
        idCard: null,
        wedlock: null,
        nationId: null,
        nativePlace: null,
        politicId: null,
        email: null,
        phone: null,
        address: null,
        departmentId: null,
        jobLevelId: null,
        posId: null,
        engageForm: null,
        tiptopDegree: null,
        specialty: null,
        school: null,
        beginDate: null,
        workState: null,
        workID: null,
        contractTerm: null,
        conversionTime: null,
        notWorkDate: null,
        beginContract: null,
        endContract: null,
        workAge: null,
        beforeWorkAge: null,
        afterWorkAge: null
      },
      search: {
        id: null,
        name: null,
        gender: null,
        birthday: null,
        idCard: null,
        wedlock: null,
        nationId: null,
        nativePlace: null,
        politicId: null,
        email: null,
        phone: null,
        address: null,
        departmentId: null,
        jobLevelId: null,
        posId: null,
        engageForm: null,
        tiptopDegree: null,
        specialty: null,
        school: null,
        beginDate: null,
        workState: null,
        workID: null,
        contractTerm: null,
        conversionTime: null,
        notWorkDate: null,
        beginContract: null,
        endContract: null,
        workAge: null,
        beforeWorkAge: null,
        afterWorkAge: null
      },
      edit: {
        id: null,
        name: null,
        gender: null,
        birthday: null,
        idCard: null,
        wedlock: null,
        nationId: null,
        nativePlace: null,
        politicId: null,
        email: null,
        phone: null,
        address: null,
        departmentId: null,
        jobLevelId: null,
        posId: null,
        engageForm: null,
        tiptopDegree: null,
        specialty: null,
        school: null,
        beginDate: null,
        workState: null,
        workID: null,
        contractTerm: null,
        conversionTime: null,
        notWorkDate: null,
        beginContract: null,
        endContract: null,
        workAge: null,
        beforeWorkAge: null,
        afterWorkAge: null
      },
      add: {
        id: null,
        name: null,
        gender: null,
        birthday: null,
        idCard: null,
        wedlock: null,
        nationId: null,
        nativePlace: null,
        politicId: null,
        email: null,
        phone: null,
        address: null,
        departmentId: null,
        jobLevelId: null,
        posId: null,
        engageForm: null,
        tiptopDegree: null,
        specialty: null,
        school: null,
        beginDate: null,
        workState: null,
        workID: null,
        contractTerm: null,
        conversionTime: null,
        notWorkDate: null,
        beginContract: null,
        endContract: null,
        workAge: null,
        beforeWorkAge: null,
        afterWorkAge: null
      },
      beginDateScope: null,
      // 查询弹出框显示器
      dialogVisible1: false,
      dialogVisible2: false,
      // 表格数据导出化
      importDataBtnText: "导入数据",
      importDataBtnIcon: "el-icon-upload2",
      loading: false,
      // 默认上传时不能再点击按钮进行上传
      importDataDisabled: false,
      // 设置一个全局的index来指示提交表单所需要执行哪一类操作
      index: 0,
      // 弹出框的标题
      title: null,
      // 弹出框的提示数据
      // 部门展示
      defaultProps: {
        children: "children",
        label: "name"
      },
      inputDepName: "所属部门",
      // 部门数据展开关闭指示器
      popVisible: false,
      // 政治面貌下拉框模拟数据
      options: [
        {
          value: "选项1",
          label: "黄金糕"
        },
        {
          value: "选项2",
          label: "双皮奶"
        }
      ],
      // 添加员工的下拉框中的学历选项
      tiptopDegrees: [
        "本科",
        "大专",
        "硕士",
        "博士",
        "高中",
        "初中",
        "小学",
        "其他"
      ], // 添加员工的下拉框中的民族选项，数据接口
      nations: [],
      // 添加员工的下拉框中的职称选项，数据接口
      joblevels: [],
      // 添加员工的下拉框中的政治面貌选项，数据接口
      politicsstatus: [],
      // 添加员工的下拉框中的职位选项，数据接口
      positions: [],
      // 部门信息接受对象
      allDeps: [],
      // 员工表单校验规则
      // 添加员工表单校验规则
      rules: {
        name: [{ required: true, message: "请输入用户名", trigger: "blur" }],
        gender: [{ required: true, message: "请输入性别", trigger: "blur" }],
        birthday: [
          { required: true, message: "请输入出生日期", trigger: "blur" }
        ],
        idCard: [
          { required: true, message: "请输入身份证号码", trigger: "blur" },
          {
            pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
            message: "身份证号码格式不正确",
            trigger: "blur"
          }
        ],
        wedlock: [
          { required: true, message: "请输入婚姻状况", trigger: "blur" }
        ],
        nationId: [{ required: true, message: "请输入您组", trigger: "blur" }],
        nativePlace: [
          { required: true, message: "请输入籍贯", trigger: "blur" }
        ],
        politicId: [
          { required: true, message: "请输入政治面貌", trigger: "blur" }
        ],
        email: [
          { required: true, message: "请输入邮箱地址", trigger: "blur" },
          {
            type: "email",
            message: "邮箱格式不正确",
            trigger: "blur"
          }
        ],
        phone: [{ required: true, message: "请输入电话号码", trigger: "blur" }],
        address: [
          { required: true, message: "请输入员工地址", trigger: "blur" }
        ],
        departmentId: [
          { required: true, message: "请输入部门名称", trigger: "blur" }
        ],
        jobLevelId: [
          { required: true, message: "请输入职称", trigger: "blur" }
        ],
        posId: [{ required: true, message: "请输入职位", trigger: "blur" }],
        engageForm: [
          { required: true, message: "请输入聘用形式", trigger: "blur" }
        ],
        tiptopDegree: [
          { required: true, message: "请输入学历", trigger: "blur" }
        ],
        specialty: [{ required: true, message: "请输入专业", trigger: "blur" }],
        school: [
          { required: true, message: "请输入毕业院校", trigger: "blur" }
        ],
        beginDate: [
          { required: true, message: "请输入入职日期", trigger: "blur" }
        ],
        workState: [
          { required: true, message: "请选则工作状态", trigger: "blur" }
        ],
        workID: [{ required: true, message: "请输入工号", trigger: "blur" }],
        contractTerm: [
          { required: true, message: "请输入合同期限", trigger: "blur" }
        ],
        conversionTime: [
          { required: true, message: "请输入转正日期", trigger: "blur" }
        ],
        beginContract: [
          { required: true, message: "请输入合同起始日期", trigger: "blur" }
        ],
        endContract: [
          { required: true, message: "请输入合同结束日期", trigger: "blur" }
        ],
        beforeWorkAge: [
          { required: true, message: "请输入入职前工龄", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    // ***************************
    // *******分页显示*************
    // ***************************
    // 调整页面显示数目
    sizeChange(currentSize) {
      //
      this.size = currentSize;
      this.initEmps();
    },
    // 分页显示
    currentChange(page) {
      this.currentPage = page;
      this.initEmps();
    },
    // 弹出框提示数据的获取
    // 部门数据选中后操作
    handleNodeClick(data) {
      this.inputDepName = data.name;
      this.emp.departmentId = data.id;
      this.popVisible = !this.popVisible;
    },
    // showDepView展示添加员工页面
    showDepView() {
      this.popVisible = !this.popVisible;
    },
    // 获取职位数据
    initPositions() {
      this.getRequest("/personnel/emp/positions").then(resp => {
        if (resp) {
          this.positions = resp;
        }
      });
    },
    // 获取员工最大ID号+1
    getMaxWordID() {
      this.getRequest("/personnel/emp/maxWorkID").then(resp => {
        if (resp) {
          this.emp.workID = resp.obj;
        }
      });
    },
    // 添加员工中的下拉框中的选项数据的存取
    // 如果本地sessionStorage中不存在，则请求服务端加载
    initData() {
      // 民族
      if (!window.sessionStorage.getItem("nations")) {
        this.getRequest("/personnel/emp/nations").then(resp => {
          if (resp) {
            this.nations = resp;
            // 网页常用代码片段-sessionStorage存储JSON
            // 1、存储
            // window.sessionStorage.setItem("name_info",JSON.stringify(res.data));
            // 2、使用存储
            // JSON.parse(window.sessionStorage.getItem('name_info'))
            window.sessionStorage.setItem("nations", JSON.stringify(resp));
          }
        });
      } else {
        this.nations = JSON.parse(window.sessionStorage.getItem("nations"));
      }
      //
      if (!window.sessionStorage.getItem("joblevels")) {
        this.getRequest("/personnel/emp/joblevels").then(resp => {
          if (resp) {
            this.joblevels = resp;
            window.sessionStorage.setItem("joblevels", JSON.stringify(resp));
          }
        });
      } else {
        this.joblevels = JSON.parse(window.sessionStorage.getItem("joblevels"));
      }
      if (!window.sessionStorage.getItem("politicsstatus")) {
        this.getRequest("/personnel/emp/politicsstatus").then(resp => {
          if (resp) {
            this.politicsstatus = resp;
            window.sessionStorage.setItem(
              "politicsstatus",
              JSON.stringify(resp)
            );
          }
        });
      } else {
        this.politicsstatus = JSON.parse(
          window.sessionStorage.getItem("politicsstatus")
        );
      }
      if (!window.sessionStorage.getItem("deps")) {
        this.getRequest("/personnel/emp/deps").then(resp => {
          if (resp) {
            this.allDeps = resp;
            window.sessionStorage.setItem("deps", JSON.stringify(resp));
          }
        });
      } else {
        this.allDeps = JSON.parse(window.sessionStorage.getItem("deps"));
      }
    },
    // 上传失败时的按钮图标的样式
    onError(err, file, fileList) {
      // 显示操作结果提示
      this.$message({
        showClose: true,
        message: "上传失败",
        type: "success"
      });
      // 更改操作按钮信息
      this.importDataBtnText = "导入数据";
      this.importDataBtnIcon = "el-icon-upload2";
      this.importDataDisabled = false;
    },
    // 上传成功时的按钮图标的样式
    onSuccess(response, file, fileList) {
      // 显示操作结果提示
      this.$message({
        showClose: true,
        message: "上传成功",
        type: "success"
      });
      // 更改按钮字样
      this.importDataBtnText = "导入数据";
      this.importDataBtnIcon = "el-icon-upload2";
      this.importDataDisabled = false;
      this.initEmps();
    },
    // 正在上传时的按钮的图标文字样式
    beforeUpload() {
      this.importDataBtnText = "正在导入";
      this.importDataBtnIcon = "el-icon-loading";
      this.importDataDisabled = true;
    },
    // 导出数据事件
    exportData() {
      // 查看请求地址，在当前窗口导出
      window.open("/personnel/emp/export", "_parent");
    },
    // 下载模板
    downloadTemplate() {
      // 查看请求地址，在当前窗口导出
      window.open("/personnel/emp/download", "_parent");
    },
    // 初始化表格数据
    initEmps() {
      this.loading = true;
      let url =
        "/personnel/emp/?page=" +
        this.currentPage +
        "&size=" +
        this.currentSize;
      if (this.search.name && this.search.name != null) {
        url += "&name=" + this.search.name;
      }
      if (this.search.politicId && this.search.politicId != null) {
        url += "&politicId=" + this.search.politicId;
      }
      if (this.search.nationId && this.search.nationId != null) {
        url += "&nationId=" + this.search.nationId;
      }
      if (this.search.jobLevelId && this.search.jobLevelId != null) {
        url += "&jobLevelId=" + this.search.jobLevelId;
      }
      if (this.search.posId && this.search.posId != null) {
        url += "&posId=" + this.search.posId;
      }
      if (this.search.engageForm && this.search.engageForm != null) {
        url += "&engageForm=" + this.search.engageForm;
      }
      if (this.search.departmentId && this.search.departmentId != null) {
        url += "&departmentId=" + this.search.departmentId;
      }
      if (this.search.workState && this.search.workState != null) {
        url += "&workState=" + this.search.workState;
      }
      if (this.beginDateScope && this.beginDateScope != null) {
        url += "&beginDateScope=" + this.beginDateScope;
      }
      this.getRequest(url).then(resp => {
        this.loading = false;
        if (resp) {
          this.emps = resp.data;
          this.total = resp.total;
        }
      });
    },
    // 刷新
    refresh() {
      Object.keys(this.emp).forEach(key => (this.emp[key] = null));
      Object.keys(this.add).forEach(key => (this.add[key] = undefined));
      Object.keys(this.edit).forEach(key => (this.edit[key] = undefined));
      Object.keys(this.search).forEach(key => (this.search[key] = undefined));
      this.inputDepName = undefined;
      this.initEmps();
      this.$message({
        showClose: true,
        message: "刷新成功",
        type: "success"
      });
    },
    // 查询
    searchItem() {
      this.emp = JSON.parse(JSON.stringify(this.search));
      this.title = "查询员工信息";
      this.index = 0;
      this.showView(0);
    },
    // 添加
    addItem() {
      this.title = "添加员工信息";
      this.index = 1;
      this.emp.workID = this.getMaxWordID();
      this.showView(1);
    },
    // 修改
    editItem(row) {
      this.title = "修改员工信息";
      this.inputDepName = row.department.name;
      this.index = 2;
      this.emp.id = row.id;
      this.emp.address = row.address;
      this.emp.beforeWorkAge = row.beforeWorkAge;
      this.emp.beginContract = row.beginContract;
      this.emp.beginDate = row.beginDate;
      this.emp.birthday = row.birthday;
      this.emp.contractTerm = row.contractTerm;
      this.emp.conversionTime = row.conversionTime;
      this.emp.email = row.email;
      this.emp.departmentId = row.departmentId;
      this.emp.endContract = row.endContract;
      this.emp.engageForm = row.engageForm;
      this.emp.gender = row.gender;
      this.emp.idCard = row.idCard;
      this.emp.jobLevelId = row.jobLevelId;
      this.emp.name = row.name;
      this.emp.nationId = row.nationId;
      this.emp.nativePlace = row.nativePlace;
      this.emp.notWorkDate = row.notWorkDate;
      this.emp.phone = row.phone;
      this.emp.politicId = row.politicId;
      this.emp.politicsstatus = row.politicsstatus;
      this.emp.posId = row.posId;
      this.emp.school = row.school;
      this.emp.specialty = row.specialty;
      this.emp.tiptopDegree = row.tiptopDegree;
      this.emp.wedlock = row.wedlock;
      this.emp.workID = row.workID;
      this.emp.workState = row.workState;
      this.emp.beforeWorkAge = row.work_age.beforeWorkAge;
      this.initPositions();
      this.showView(2);
    },
    // 删除
    deleteItem(row) {
      this.$confirm("此操作将永久删除【" + row.name + "】, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.deleteRequest("/personnel/emp/" + row.id).then(resp => {
            if (resp) {
              this.initEmps();
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
    // 弹出框弹出内容控制
    showView(index) {
      if (index == 0) {
        this.dialogVisible2 = true;
      } else if (index == 1) {
        this.dialogVisible1 = true;
      } else if (index == 2) {
        this.dialogVisible1 = true;
      }
    },
    // 提交
    onSubmit() {
      if (this.index == 0) {
        this.search = JSON.parse(JSON.stringify(this.emp));
        this.initEmps();
      } else if (this.index == 1) {
        // 添加操作
        // 添加员工数据之前，使用validate进行表单校验
        // 校验成功，则发送；校验失败，则不发送
        this.add = JSON.parse(JSON.stringify(this.emp));
        this.$refs["empForm"].validate(valid => {
          if (valid) {
            this.postRequest("/personnel/emp/", this.emp).then(resp => {
              if (resp) {
                this.dialogVisible = false;
                this.initEmps();
              }
            });
          }
        });
      } else if (this.index == 2) {
        // 修改操作
        // this.edit = JSON.parse(JSON.stringify(this.emp));
        (this.edit.id = this.emp.id),
          (this.edit.address = this.emp.address),
          (this.edit.beforeWorkAge = this.emp.beforeWorkAge),
          (this.edit.beginContract = this.emp.beginContract),
          (this.edit.beginDate = this.emp.beginDate),
          (this.edit.birthday = this.emp.birthday),
          (this.edit.conversionTime = this.emp.conversionTime),
          (this.edit.email = this.emp.email),
          (this.edit.departmentId = this.emp.departmentId),
          (this.edit.endContract = this.emp.endContract),
          (this.edit.engageForm = this.emp.engageForm),
          (this.edit.gender = this.emp.gender),
          (this.edit.idCard = this.emp.idCard),
          (this.edit.jobLevelId = this.emp.jobLevelId),
          (this.edit.name = this.emp.name),
          (this.edit.nationId = this.emp.nationId),
          (this.edit.nativePlace = this.emp.nativePlace),
          (this.edit.notWorkDate = this.emp.notWorkDate),
          (this.edit.phone = this.emp.phone),
          (this.edit.politicId = this.emp.politicId),
          (this.edit.posId = this.emp.posId),
          (this.edit.school = this.emp.school),
          (this.edit.specialty = this.emp.specialty),
          (this.edit.tiptopDegree = this.emp.tiptopDegree),
          (this.edit.wedlock = this.emp.wedlock),
          (this.edit.workState = this.emp.workState),
          (this.edit.beforeWorkAge = this.emp.beforeWorkAge);
        if (this.edit.id) {
          this.$refs["empForm"].validate(valid => {
            if (valid) {
              this.putRequest("/personnel/emp/", this.edit).then(resp => {
                if (resp) {
                  this.dialogVisible = false;
                  this.initEmps();
                }
              });
            }
          });
        }
      }
      this.closed();
    },
    // 关闭弹窗
    closed() {
      Object.keys(this.emp).forEach(key => (this.emp[key] = null));
      Object.keys(this.add).forEach(key => (this.add[key] = null));
      Object.keys(this.edit).forEach(key => (this.edit[key] = null));
      this.inputDepName = undefined;
      this.index = 0;
      this.dialogVisible2 = false;
      this.dialogVisible1 = false;
      this.title = undefined;
    },
    // 发送入职邮件
    sendMail(id) {
      this.getRequest("/personnel/emp/mail/send?id=" + id).then(resp => {
        if (resp) {
          this.initEmps();
        }
      });
    }
  },

  //生命周期 - 创建完成（访问当前this实例）
  created() {},
  //生命周期 - 挂载完成（访问DOM元素）
  mounted() {
    this.initEmps();
    this.initData();
    this.initPositions();
  }
};
</script>
<style scoped>
/* @import url(); 引入css类 */
</style>