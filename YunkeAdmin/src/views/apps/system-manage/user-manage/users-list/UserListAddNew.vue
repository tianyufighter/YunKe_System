<template>
  <b-sidebar
    id="add-new-user-sidebar"
    :visible="isAddNewUserSidebarActive"
    bg-variant="white"
    sidebar-class="sidebar-lg"
    shadow
    backdrop
    no-header
    right
    @change="(val) => $emit('update:is-add-new-user-sidebar-active', val)"
  >
    <template #default="{ hide }">
      <!-- Header -->
      <div class="d-flex justify-content-between align-items-center content-sidebar-header px-2 py-1">
        <h5 class="mb-0">
          添加新用户
        </h5>

        <feather-icon
          class="ml-1 cursor-pointer"
          icon="XIcon"
          size="16"
          @click="hide"
        />

      </div>

      <!-- BODY -->
      <validation-observer
        #default="{ handleSubmit }"
        ref="refFormObserver"
      >
        <!-- Form -->
        <b-form
          class="p-2"
          @submit.prevent="handleSubmit(submitUser)"
          @reset.prevent="resetForm"
        >

          <!-- 用户名 -->
          <validation-provider
            #default="validationContext"
            name="Username"
            rules="required|alpha-num"
          >
            <b-form-group
              label="用户名"
              label-for="username"
            >
              <b-form-input
                id="username"
                v-model="userData.username"
                :state="getValidationState(validationContext)"
                trim
              />

              <b-form-invalid-feedback>
                用户名要求输入
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <!-- 邮箱 -->
          <validation-provider
            #default="validationContext"
            name="Email"
            rules="required|email"
          >
            <b-form-group
              label="邮箱"
              label-for="email"
            >
              <b-form-input
                id="email"
                v-model="userData.email"
                :state="getValidationState(validationContext)"
                trim
              />

              <b-form-invalid-feedback>
                邮箱要求输入
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <!-- 密码 -->
          <validation-provider
            #default="validationContext"
            name="密码"
            rules="required"
          >
            <b-form-group
              label="密码"
              label-for="contact"
            >
              <b-form-input
                id="contact"
                v-model="userData.password"
                :state="getValidationState(validationContext)"
                type="password"
                trim
              />

              <b-form-invalid-feedback>
                密码要求输入
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <!-- 职位 -->
          <validation-provider
            #default="validationContext"
            name="职位"
            rules="required"
          >
            <b-form-group
              label="职位"
              label-for="company"
            >
              <b-form-input
                id="company"
                v-model="userData.jobTitle"
                trim
              />

            </b-form-group>
          </validation-provider>

          <!-- 用户角色 -->
          <validation-provider
            #default="validationContext"
            name="用户角色"
            rules="required"
          >
            <b-form-group
              label="用户角色"
              label-for="user-role"
            >
              <v-select
                v-model="userData.personType"
                :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
                :options="roleOptions"
                :reduce="val => val.value"
                :clearable="false"
                input-id="user-role"
              />
            </b-form-group>
          </validation-provider>

          <!-- 性别 -->
          <validation-provider
            #default="validationContext"
            name="性别"
            rules="required"
          >
            <b-form-group
              label="性别"
              label-for="plan"
            >
              <v-select
                v-model="userData.gender"
                :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
                :options="genderOptions"
                :reduce="val => val.value"
                :clearable="false"
                input-id="plan"
              />
            </b-form-group>
          </validation-provider>

          <!-- Form Actions -->
          <div class="d-flex mt-2">
            <b-button
              v-ripple.400="'rgba(255, 255, 255, 0.15)'"
              variant="primary"
              class="mr-2"
              type="submit"
            >
              添加
            </b-button>
            <b-button
              v-ripple.400="'rgba(186, 191, 199, 0.15)'"
              type="button"
              variant="outline-secondary"
              @click="hide"
            >
              取消
            </b-button>
          </div>

        </b-form>
      </validation-observer>
    </template>
  </b-sidebar>
</template>

<script>
import {
  BSidebar, BForm, BFormGroup, BFormInput, BFormInvalidFeedback, BButton,
} from 'bootstrap-vue'
import { ValidationProvider, ValidationObserver } from 'vee-validate'
import { required, alphaNum, email } from '@validations'
import Ripple from 'vue-ripple-directive'
import vSelect from 'vue-select'
import {addUser} from '@/network/usermanage'
import ToastificationContent from '@core/components/toastification/ToastificationContent.vue'

export default {
  components: {
    BSidebar,
    BForm,
    BFormGroup,
    BFormInput,
    BFormInvalidFeedback,
    BButton,
    vSelect,

    // Form Validation
    ValidationProvider,
    ValidationObserver,
  },
  directives: {
    Ripple,
  },
  model: {
    prop: 'isAddNewUserSidebarActive',
    event: 'update:is-add-new-user-sidebar-active',
  },
  props: {
    isAddNewUserSidebarActive: {
      type: Boolean,
      required: true,
    },
    roleOptions: {
      type: Array,
      required: true,
    },
    genderOptions: {
      type: Array,
      required: true,
    },
  },
  watch: {
    isAddNewUserSidebarActive() {
      this.userData = {
        username: '',
        email: '',
        password: '',
        jobTitle: '',
        personType: null,
        gender: null
      }
    }
  },
  data() {
    return {
      required,
      alphaNum,
      email,
      userData: {
        username: '',
        email: '',
        password: '',
        jobTitle: '',
        personType: null,
        gender: null
      },
      refFormObserver: null,

    }
  },
  methods: {
    getValidationState({ dirty, validated, required: fieldRequired, changed, valid = null }) {
      const result = dirty || validated ? valid : null
      return !fieldRequired && !changed ? null : result
    },
    // 添加用户
    submitUser() {
      let user = {
        username: this.userData.username,
        email: this.userData.email,
        password: this.userData.password,
        jobTitle: this.userData.jobTitle,
        personType: this.getPersonTypeByName(this.userData.personType),
        gender: this.getGenderByName(this.userData.gender)
      };
      addUser(user).then(res => {
        if (res.data.code == 200) {
          this.$toast({
            component: ToastificationContent,
            props: {
              title: '添加用户成功',
              icon: 'CoffeeIcon',
              variant: 'success',
            },
          })
          this.resetForm();
          this.$emit('updateUserSuccess', false)
        } else {
          this.$toast({
            component: ToastificationContent,
            props: {
              title: '添加用户失败',
              text: res.data.message,
              icon: 'CoffeeIcon',
              variant: 'danger',
            },
          })
        }
      }).catch(err => {
        this.$toast({
          component: ToastificationContent,
          props: {
            title: '添加用户失败',
            text: err.msg,
            icon: 'CoffeeIcon',
            variant: 'danger',
          },
        })
      })
    },
    // 根据评论的名字获取它的码
    getPersonTypeByName(typeName) {
      if (typeName == 'superAdmin') {
        return 3;
      } else if (typeName == 'admin') {
        return 2;
      } else {
        return 1;
      }
    },
    // 根据性别的名字获取它的码
    getGenderByName(genderName) {
      if (genderName == 'male') {
        return '男';
      } else if (genderName == 'female') {
        return '女';
      } else {
        return '未知';
      }
    },
    // 清空数据
    resetForm() {
      this.userData = {
        username: '',
        email: '',
        password: '',
        jobTitle: '',
        personType: null,
        gender: null
      }
    }
  }
}
</script>

<style lang="scss">
@import '@core/scss/vue/libs/vue-select.scss';

#add-new-user-sidebar {
  .vs__dropdown-menu {
    max-height: 200px !important;
  }
}
</style>
