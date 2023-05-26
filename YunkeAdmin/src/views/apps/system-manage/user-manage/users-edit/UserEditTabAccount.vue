<template>
  <div>

    <!-- Media -->
    <b-media class="mb-2">
      <template #aside>
        <b-avatar
          ref="previewEl"
          :src="headImage"
          :text="avatarText(username)"
          :variant="`light-${resolveUserRoleVariant(personType)}`"
          size="90px"
          rounded
        />
      </template>
      <h4 class="mb-1">
        {{ username }}
      </h4>
      <div class="d-flex flex-wrap">
        <b-button
          variant="primary"
        >
          <el-upload
              class="avatar-uploader"
              action="/api/common/file/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
            <span class="d-none d-sm-inline">更换头像</span>
          </el-upload>
        </b-button>
        <b-button
          variant="warning"
          class="ml-1"
        >
          <span class="d-none d-sm-inline"  @click="deleteUserInfo">删除用户</span>
        </b-button>
      </div>
    </b-media>

    <!-- User Info: Input Fields -->
    <b-form>
      <b-row>

        <!-- Field: 用户名 -->
        <b-col
          cols="12"
          md="4"
        >
          <b-form-group
            label="用户名"
            label-for="username"
          >
            <b-form-input
              id="username"
              v-model="username"
            />
          </b-form-group>
        </b-col>

        <!-- Field: 邮箱 -->
        <b-col
            cols="12"
            md="4"
        >
          <b-form-group
              label="邮箱"
              label-for="email"
          >
            <b-form-input
                id="email"
                v-model="email"
                type="email"
            />
          </b-form-group>
        </b-col>

        <!-- Field: 性别 -->
        <b-col
            cols="12"
            md="4"
        >
          <b-form-group
              label="性别"
              label-for="gender"
          >
            <v-select
                v-model="gender"
                :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
                :options="genderOptions"
                :reduce="val => val.value"
                :clearable="false"
                input-id="gender"
            />
          </b-form-group>
        </b-col>

        <!-- Field: 角色 -->
        <b-col
            cols="12"
            md="4"
        >
          <b-form-group
              label="用户角色"
              label-for="user-role"
          >
            <v-select
                v-model="personType"
                :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
                :options="roleOptions"
                :reduce="val => val.value"
                :clearable="false"
                input-id="user-role"
            />
          </b-form-group>
        </b-col>

        <!-- Field: 出生日期 -->
        <b-col
          cols="12"
          md="4"
        >
          <b-form-group
            label="出生日期"
            label-for="full-name"
          >
            <b-form-input
              id="full-name"
              v-model="birthday"
            />
          </b-form-group>
        </b-col>


        <!-- Field: 状态 -->
        <b-col
          cols="12"
          md="4"
        >
          <b-form-group
            label="状态"
            label-for="user-status"
          >
            <v-select
              v-model="isBan"
              :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
              :options="statusOptions"
              :reduce="val => val.value"
              :clearable="false"
              input-id="user-status"
            />
          </b-form-group>
        </b-col>


        <!-- Field: 电话号码 -->
        <b-col
          cols="12"
          md="4"
        >
          <b-form-group
            label="电话号码"
            label-for="phone"
          >
            <b-form-input
              id="phone"
              v-model="phone"
            />
          </b-form-group>
        </b-col>


        <!-- Field: 注册时间 -->
        <b-col
            cols="12"
            md="4"
        >
          <b-form-group
              label="注册时间"
              label-for="registryTime"
          >
            <b-form-input
                id="registryTime"
                v-model="registryTime"
            />
          </b-form-group>
        </b-col>

        <!-- Field: 工作职称 -->
        <b-col
            cols="12"
            md="4"
        >
          <b-form-group
              label="工作职称"
              label-for="jobTitle"
          >
            <b-form-input
                id="jobTitle"
                v-model="jobTitle"
            />
          </b-form-group>
        </b-col>

        <!-- Field: 个人描述 -->
        <b-col
            cols="12"
            md="4"
        >
          <b-form-group
              label="个人描述"
              label-for="description"
          >
            <b-form-textarea
                id="description"
                v-model="description"
            />
          </b-form-group>
        </b-col>

      </b-row>
    </b-form>


    <!-- Action Buttons -->
    <b-button
      variant="primary"
      class="mb-1 mb-sm-0 mr-0 mr-sm-1"
      @click="updateUser"
    >
      保存更改
    </b-button>
    <b-button
      variant="primary"
      @click="$router.back()"
    >
      返回
    </b-button>
  </div>
</template>

<script>
import {
  BButton, BMedia, BAvatar, BRow, BCol, BFormGroup, BFormInput, BForm, BTable, BCard, BCardHeader, BCardTitle, BFormCheckbox,
    BFormTextarea
} from 'bootstrap-vue'
import { avatarText } from '@core/utils/filter'
import vSelect from 'vue-select'
import {updateUserInfoById, deleteUser} from '@/network/usermanage'

export default {
  components: {
    BFormTextarea,
    BButton,
    BMedia,
    BAvatar,
    BRow,
    BCol,
    BFormGroup,
    BFormInput,
    BForm,
    BTable,
    BCard,
    BCardHeader,
    BCardTitle,
    BFormCheckbox,
    vSelect,
  },
  props: {
    userData: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      id: this.userData.id,
      username: this.userData.username,
      gender: this.getGenderValue(this.userData.gender),
      email: this.userData.email,
      personType: this.getPersonValue(this.userData.personType),
      headImage: this.userData.headImage,
      birthday: this.simplyTime(this.userData.birthday),
      phone: this.userData.phone,
      registryTime: this.simplyTime(this.userData.registryTime),
      jobTitle: this.userData.jobTitle,
      description: this.userData.description,
      isBan: this.userData.isBan == true ? 'ban' : 'normal',
      roleOptions: [
        { label: '超级管理员', value: 'superAdmin' },
        { label: '管理员', value: 'admin' },
        { label: '普通用户', value: 'user' },
      ],
      genderOptions: [
        { label: '男', value: 'male' },
        { label: '女', value: 'female' },
        { label: '未知', value: 'unknown' },
      ],
      statusOptions: [
        { label: '正常', value: 'normal' },
        { label: '封禁', value: 'ban' },
      ],
    }
  },
  methods: {
    deleteUserInfo() {
      deleteUser(this.id).then(res => {
        if (res.data.code == 200) {
          this.$router.push({name: 'user-manage'})
          this.$vs.notify({
            title:'消息提示',
            text:'用户信息删除成功',
            color:'success',
            position:'top-center'})
        }
      }).catch(res => {
        this.$vs.notify({
          title:'错误提示',
          text:'用户信息未能成功删除',
          color:'danger',
          position:'top-center'})
      })
    },
    updateUser() {
      let user = {
        id: this.id,
        username: this.username,
        gender: this.getGender(this.gender),
        email: this.email,
        personType: this.getPerson(this.personType),
        headImage: this.headImage,
        birthday: this.dateFormat(this.birthday),
        phone: this.phone,
        registryTime: this.dateFormat(this.registryTime),
        jobTitle: this.jobTitle,
        description: this.description,
        isBan: this.isBan == 'ban' ? true : false
      };
      updateUserInfoById(user).then(res => {
        if (res.data.code == 200) {
          this.$vs.notify({
            title: '消息提示',
            text: '用户信息修改成功',
            color: 'success',
            position: 'top-center'
          })
        } else {
          Promise.reject(res);
        }
      }).catch(err => {
        this.$vs.notify({
          title:'错误提示',
          text:'用户信息修改未能成功',
          color:'danger',
          position:'top-center'})
      })
    },
    handleAvatarSuccess(res, file) {
      console.log("图片上传成功: ", file)
      this.headImage = file.response.data.url;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$vs.notify({
          title:'错误提示',
          text:'上传头像图片只能是 JPG 格式!',
          color:'danger',
          position:'top-right'})
      }
      if (!isLt2M) {
        this.$vs.notify({
          title:'错误提示',
          text:'上传头像图片大小不能超过 2MB!',
          color:'danger',
          position:'top-right'})
      }

      return isJPG && isLt2M;
    },
    // 简化时间
    simplyTime(time) {
      let date=new Date(time);
      let year=date.getFullYear();
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      let month= date.getMonth()+1<10 ? "0"+(date.getMonth()+1) : date.getMonth()+1;
      let day=date.getDate()<10 ? "0"+date.getDate() : date.getDate();
      // 拼接
      return year+"-"+month+"-"+day;
    },
    //时间格式化函数，此处仅针对yyyy-MM-dd hh:mm:ss 的格式进行格式化
    dateFormat(time) {
      let date=new Date(time);
      let year=date.getFullYear();
      /* 在日期格式中，月份是从0开始的，因此要加0
       * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
       * */
      let month= date.getMonth()+1<10 ? "0"+(date.getMonth()+1) : date.getMonth()+1;
      let day=date.getDate()<10 ? "0"+date.getDate() : date.getDate();
      let hours=date.getHours()<10 ? "0"+date.getHours() : date.getHours();
      let minutes=date.getMinutes()<10 ? "0"+date.getMinutes() : date.getMinutes();
      let seconds=date.getSeconds()<10 ? "0"+date.getSeconds() : date.getSeconds();
      // 拼接
      return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
    },
    // 获取性别在数据库中对应的码
    getGender(genderName) {
      if (genderName == 'male') {
        return '男';
      } else if (genderName == 'female') {
        return '女';
      } else {
        return '未知';
      }
    },
    // 获取用户类型在数据库中对应的码
    getPerson(personName) {
      if (personName == 'user') {
        return 1;
      } else if (personName == 'admin') {
        return 2;
      } else if (personName == 'superAdmin') {
        return 3;
      } else {
        return 1;
      }
    },
    getPersonValue(personType) {
      if (personType == 1) {
        return 'user';
      } else if (personType == 2) {
        return 'admin';
      } else if (personType == 3) {
        return 'superAdmin';
      } else {
        return 'user';
      }
    },
    getGenderValue(gender) {
      if (gender == '男') {
        return 'male';
      } else if (gender == '女') {
        return 'female';
      } else {
        return 'unknown';
      }
    },
    inputImageRenderer: function() {

    },
    avatarText,
    resolveUserRoleVariant(roleType) {
      if (roleType == 1) return 'primary'
      if (roleType == 2) return 'warning'
      if (roleType == 3) return 'success'
      return 'primary'
    }
  },
}
</script>

<style lang="scss">
@import '@core/scss/vue/libs/vue-select.scss';
</style>
