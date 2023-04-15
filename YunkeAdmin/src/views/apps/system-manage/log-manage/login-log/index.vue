<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="用户名称" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名称"
          clearable
          style="width: 240px;"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="登录状态"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in operateStatus"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          @click="handleClean"
        >清空</el-button>
      </el-col>
    </el-row>
    <br>
    <el-table ref="tables" v-loading="loading" :data="list" @selection-change="handleSelectionChange" :default-sort="defaultSort" @sort-change="handleSortChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="访问编号" align="center" prop="id" />
      <el-table-column label="用户名称" align="center" prop="username" :show-overflow-tooltip="true" sortable="custom" :sort-orders="['descending', 'ascending']" />
      <el-table-column label="浏览器" align="center" prop="browser" :show-overflow-tooltip="true" />
      <el-table-column label="操作系统" align="center" prop="os" />
      <el-table-column label="登录状态" align="center" prop="status">
        <template slot-scope="scope">
          <b-badge
              pill
              :variant="`light-${resolveStatusVariant(scope.row.status)}`"
              class="text-capitalize"
          >
            {{ scope.row.status == 0 ? '成功' : '失败' }}
          </b-badge>
        </template>
      </el-table-column>
      <el-table-column label="操作信息" align="center" prop="msg" :show-overflow-tooltip="true" />
      <el-table-column label="登录日期" align="center" prop="loginTime" sortable="custom" :sort-orders="['descending', 'ascending']" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.loginTime) }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {BBadge} from 'bootstrap-vue'
import {parseTime} from '@/utils/ruoyi'
import {list, delLogininfor, cleanLogininfor} from '@/network/loginlog/index'

export default {
  name: "Logininfor",
  dicts: ['sys_common_status'],
  components: {
    BBadge
  },
  data() {
    return {
      operateStatus: [
        {label: '成功', value: '0'},
        {label: '失败', value: '1'},
      ],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 选择用户名
      selectName: "",
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 表格数据
      list: [],
      // 日期范围
      dateRange: [],
      // 默认排序
      defaultSort: {prop: 'loginTime', order: 'descending'},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        status: undefined
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    parseTime,
    resolveStatusVariant(status) {
      if (status == 0) return 'success'
      if (status == 1) return 'secondary'
      return 'success';
    },
    /** 查询登录日志列表 */
    getList() {
      this.loading = true;
      list(this.queryParams)
          .then(res => {
            console.log("登录日志: ", res)
            this.list = res.data.data.list;
            this.total = res.data.data.total;
            this.loading = false;
          })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm();
      this.queryParams.pageNum = 1;
      this.$refs.tables.sort(this.defaultSort.prop, this.defaultSort.order)
    },
    resetForm() {
      this.$refs.queryForm.resetFields();
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
      this.selectName = selection.map(item => item.userId);
    },
    /** 排序触发事件 */
    handleSortChange(column, prop, order) {
      this.queryParams.orderByColumn = column.prop;
      this.queryParams.isAsc = column.order;
      this.getList();
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const infoIds = row.id || this.ids;
      this.$modal.confirm('是否确认删除访问编号为"' + infoIds + '"的数据项？')
          .then(function () {
            return delLogininfor(infoIds);
          })
          .then(() => {
            this.getList();
            this.$modal.msgSuccess("删除成功");
          })
          .catch(() => {
          });
    },
    /** 清空按钮操作 */
    handleClean() {
      this.$modal.confirm('是否确认清空所有登录日志数据项？')
          .then(function () {
            return cleanLogininfor();
          })
          .then(() => {
            this.getList();
            this.$modal.msgSuccess("清空成功");
          })
          .catch(() => {
          });
    },
  }
}
</script>

