<template>
  <section>
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>云客后台管理系统</h2>
        <p>
          云客后台管理系统主要是对云客在线服务系统的数据进行综合的管理，该系统只能是管理员和超级管理员进入，能够对系统中的数据进行增删改查。
        </p>
        <p>
          <b>当前版本:</b> <span>v 1.0.0</span>
        </p>
        <p>
          <el-button
              type="primary"
              size="mini"
              icon="el-icon-cloudy"
              plain
              @click="goTarget('https://github.com/tianyufighter/GraduationProject')"
          >访问github</el-button>
          <el-button
              size="mini"
              icon="el-icon-s-home"
              plain
              @click="goTarget('http://www.tianyufighter.cn')"
          >访问前台</el-button>
          <el-button
            size="medium"
            type="primary"
            @click="refreshSensitive"
          >
            刷新敏感词库
          </el-button>
        </p>
      </el-col>

      <el-col :sm="24" :lg="12" style="padding-left: 50px">
        <el-row>
          <el-col :span="12">
            <h2>技术选型</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <h4>后端技术</h4>
            <ul>
              <li>SpringBoot</li>
              <li>Spring Security</li>
              <li>JWT</li>
              <li>MyBatis</li>
              <li>Druid</li>
              <li>Fastjson</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>前端技术</h4>
            <ul>
              <li>Vue</li>
              <li>Vuex</li>
              <li>Element-ui</li>
              <li>Axios</li>
              <li>Sass</li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </section>
</template>

<script>
import {refreshSensitiveLibrary} from '@/network'

export default {
  components: {

  },
  data() {
    return {
      data: {},
    }
  },
  methods: {
    refreshSensitive() {
      refreshSensitiveLibrary().then(res => {
        this.$vs.notify({
          title:'消息提示',
          text:'系统已成功更新敏感词库信息',
          color:'success',
          position:'top-center'})
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'系统更新敏感词信息失败',
          color:'danger',
          position:'top-center'})
      })
    },
    goTarget(href) {
      window.open(href, "_blank");
    }
  },
  created() {

  },
}
</script>

<style lang="scss">


</style>
