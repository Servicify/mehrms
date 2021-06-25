<!-- 员工账套设置 -->
<template>
  <div>
    <div>
      <!-- 表格数据显示 -->
      <el-table :data="emps" border stripe size="mini">
        <el-table-column type="index" label="序号" fixed width="50">
        </el-table-column>
        <el-table-column
          prop="name"
          label="姓名"
          fixed
          width="120"
          align="left"
        ></el-table-column>
        <el-table-column
          prop="workID"
          label="工号"
          width="120"
          align="left"
        ></el-table-column>
        <el-table-column
          prop="email"
          label="电子邮件"
          width="200"
          align="left"
        ></el-table-column>
        <el-table-column
          prop="phone"
          label="电话号码"
          width="120"
          align="left"
        ></el-table-column>
        <el-table-column
          prop="department.name"
          label="所属部门"
          width="120"
          align="left"
        ></el-table-column>
        <el-table-column label="账套类别" align="center">
          <template slot-scope="scope">
            <!-- 参考ElementUI的文字提示右侧提示 -->
            <!-- 由于新入职员工的账套并未设置，所以此处使用v-if="scope.row.salary"
                 来显示已经设置好工资账套的员工，使用v-else设置显示尚未设置账套信息的
                 员工信息 -->
            <el-tooltip placement="right" v-if="scope.row.salary">
              <div slot="content">
                <table>
                  <tr>
                    <td>基本工资</td>
                    <td>{{ scope.row.salary.basicSalary }}</td>
                  </tr>
                  <tr>
                    <td>交通补助</td>
                    <td>{{ scope.row.salary.trafficSalary }}</td>
                  </tr>
                  <tr>
                    <td>午餐补助</td>
                    <td>{{ scope.row.salary.lunchSalary }}</td>
                  </tr>
                  <tr>
                    <td>奖金</td>
                    <td>{{ scope.row.salary.bonus }}</td>
                  </tr>
                  <tr>
                    <td>养老金比率</td>
                    <td>{{ scope.row.salary.pensionPer }}</td>
                  </tr>
                  <tr>
                    <td>养老金基数</td>
                    <td>{{ scope.row.salary.pensionBase }}</td>
                  </tr>
                  <tr>
                    <td>医疗保险比率</td>
                    <td>{{ scope.row.salary.medicalPer }}</td>
                  </tr>
                  <tr>
                    <td>医疗保险基数</td>
                    <td>{{ scope.row.salary.medicalBase }}</td>
                  </tr>
                  <tr>
                    <td>公积金比率</td>
                    <td>{{ scope.row.salary.accumulationFundPer }}</td>
                  </tr>
                  <tr>
                    <td>公积金基数</td>
                    <td>{{ scope.row.salary.accumulationFundBase }}</td>
                  </tr>
                  <tr>
                    <td>启用时间</td>
                    <td>{{ scope.row.salary.createDate }}</td>
                  </tr>
                  <tr>
                    <td>小计</td>
                    <td>{{ scope.row.salary.allSalary }}</td>
                  </tr>
                </table>
              </div>
              <el-tag>{{ scope.row.salary.name }}</el-tag>
            </el-tooltip>
            <el-tag v-else>暂未设置</el-tag>
          </template>
        </el-table-column>
        <!-- 修改操作列 -->
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <!-- 参考ElementUI的弹出框popover、下拉框Select
            ，@hide="hidePop(scope.row)关闭时激活的参数" -->
            <!-- 修改工资账套弹出框 -->
            <el-popover
              placement="left"
              title="修改工资账套"
              @show="showPop(scope.row.salary)"
              @hide="hidePop(scope.row)"
              width="200"
              trigger="click"
            >
              <!-- 从以上的 @show="showPop(scope.row.salary)"传递salary中的id，
              以便获得账套名称 -->
              <div>
                <el-select
                  v-model="currentSalary"
                  placeholder="请选择"
                  size="mini"
                >
                  <el-option
                    v-for="item in salaries"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </div>
              <el-button slot="reference" type="danger" icon="el-icon-edit"
                >修改工资账套</el-button
              >
            </el-popover>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页操作 -->
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
  </div>
</template>

<script>
export default {
  name: "SalSobCfg",
  data() {
    return {
      emps: [],
      total: 0,
      currentPage: 1,
      currentSize: 10,
      currentSalary: null,
      salaries: []
    };
  },
  mounted() {
    this.initEmps();
    this.initSalaries();
  },
  methods: {
    sizeChange(size) {
      this.currentSize = size;
      this.initEmps();
    },
    currentChange(page) {
      this.currentPage = page;
      this.initEmps();
    },
    hidePop(data) {
      // 如果当前的currentSalary操作不为null时，才操作发送更新请求
      // 否则不设置此更新请求
      if (this.currentSalary) {
        this.putRequest(
          "/salary/sobcfg/?eid=" + data.id + "&sid=" + this.currentSalary
        ).then(resp => {
          if (resp) {
            this.initEmps();
          }
        });
      }
    },
    showPop(data) {
      // 设置过滤器，添加一个id的过滤器，
      // 如果尚未设置账套的员工，在未设置账套的时候，点击修改弹出框，尚未操作，
      // 关闭弹出框后，会自动进行一次更新请求操作。
      // 故在此处设置一个对data的过滤值，以便过滤掉此种请求
      // 此处设置this.currentSalary = null;可以将未设置账套的员工信息，通过v-else标签显示
      // 暂未设置提示
      if (data) {
        this.currentSalary = data.id;
      } else {
        this.currentSalary = null;
      }
    },
    initSalaries() {
      this.getRequest("/salary/sobcfg/salaries/").then(resp => {
        if (resp) {
          this.salaries = resp;
        }
      });
    },
    initEmps() {
      this.getRequest(
        "/salary/sobcfg/?page=" + this.currentPage + "&size=" + this.currentSize
      ).then(resp => {
        if (resp) {
          this.emps = resp.data;
          this.total = resp.total;
        }
      });
    }
  }
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