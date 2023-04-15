<template>
  <b-card>
    <!-- form -->
    <b-form>
      <b-row>
        <!-- old password -->
        <b-col md="6">
          <b-form-group
            label="重置密码"
            label-for="account-old-password"
          >
            <b-input-group class="input-group-merge">
              <b-form-input
                id="account-old-password"
                v-model="passwordValue"
                name="old-password"
                :type="passwordFieldTypeOld"
                placeholder="请输入新的密码..."
              />
              <b-input-group-append is-text>
                <feather-icon
                  :icon="passwordToggleIconOld"
                  class="cursor-pointer"
                  @click="togglePasswordOld"
                />
              </b-input-group-append>
            </b-input-group>
          </b-form-group>
        </b-col>
        <!--/ old password -->
      </b-row>
      <b-row>
        <!-- buttons -->
        <b-col cols="12">
          <b-button
            v-ripple.400="'rgba(255, 255, 255, 0.15)'"
            variant="primary"
            class="mt-1 mr-1"
            @click="resetPassword"
          >
            重置
          </b-button>
          <b-button
            v-ripple.400="'rgba(186, 191, 199, 0.15)'"
            type="reset"
            variant="outline-secondary"
            class="mt-1"
            @click="clearForm"
          >
            清空
          </b-button>
        </b-col>
        <!--/ buttons -->
      </b-row>
    </b-form>
  </b-card>
</template>

<script>
import {
  BButton, BForm, BFormGroup, BFormInput, BRow, BCol, BCard, BInputGroup, BInputGroupAppend,
} from 'bootstrap-vue'
import Ripple from 'vue-ripple-directive'
import {resetUserPassword } from '@/network/usermanage'

export default {
  components: {
    BButton,
    BForm,
    BFormGroup,
    BFormInput,
    BRow,
    BCol,
    BCard,
    BInputGroup,
    BInputGroupAppend,
  },
  props: ['userId'],
  directives: {
    Ripple,
  },
  data() {
    return {
      id: this.userId,
      passwordValue: '',
      passwordFieldTypeOld: 'password',
      passwordFieldTypeNew: 'password',
      passwordFieldTypeRetype: 'password',
    }
  },
  computed: {
    passwordToggleIconOld() {
      return this.passwordFieldTypeOld === 'password' ? 'EyeIcon' : 'EyeOffIcon'
    },
    passwordToggleIconNew() {
      return this.passwordFieldTypeNew === 'password' ? 'EyeIcon' : 'EyeOffIcon'
    },
    passwordToggleIconRetype() {
      return this.passwordFieldTypeRetype === 'password' ? 'EyeIcon' : 'EyeOffIcon'
    },
  },
  methods: {
    clearForm() {
      this.passwordValue = '';
    },
    resetPassword() {
      resetUserPassword(this.id, this.passwordValue).
      then(res => {
        this.passwordValue = '';
          if (res.data.code == 200) {
            this.$vs.notify({
              title:'消息提示',
              text:'重置密码成功',
              color:'success',
              position:'top-center'})
          } else {
            Promise.reject(res);
          }
      }).catch(err=> {
        this.$vs.notify({
          title:'错误提示',
          text:'重置密码错误',
          color:'danger',
          position:'top-center'})
      })
    },
    togglePasswordOld() {
      this.passwordFieldTypeOld = this.passwordFieldTypeOld === 'password' ? 'text' : 'password'
    },
    togglePasswordNew() {
      this.passwordFieldTypeNew = this.passwordFieldTypeNew === 'password' ? 'text' : 'password'
    },
    togglePasswordRetype() {
      this.passwordFieldTypeRetype = this.passwordFieldTypeRetype === 'password' ? 'text' : 'password'
    },
  },
}
</script>
