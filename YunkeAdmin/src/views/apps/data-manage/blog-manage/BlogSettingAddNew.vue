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
    @change="(val) => $emit('updateStatus', val)"
  >
    <template #default="{ hide }">
      <!-- Header -->
      <div class="d-flex justify-content-between align-items-center content-sidebar-header px-2 py-1">
        <h5 class="mb-0">
          帖子信息
        </h5>

        <feather-icon
          class="ml-1 cursor-pointer"
          icon="XIcon"
          size="16"
          @click="hide"
        />
      </div>

        <!-- Form -->
        <b-form
          class="p-2"
          @submit.prevent="handleSubmit"
        >
          <!-- 参数名称 -->

          <b-form-group
              label="帖子标题"
              label-for="title"
          >
            <b-form-input
                id="title"
                v-model="title"
                trim
            />
          </b-form-group>

          <!-- 备注 -->
          <b-form-group
              label="帖子内容"
              label-for="content"
          >
            <b-form-textarea
                id="content"
                v-model="content"
                trim
            />
          </b-form-group>
          <!-- 是否违规 -->
          <b-form-group
              label="是否违规"
              label-for="isViolation"
          >
            <b-form-radio-group
                id="isViolation"
                v-model="isViolation"
                :options="options"
                class="demo-inline-spacing mb-1"
                value-field="value"
                text-field="text"
                disabled-field="disabled"
            />
          </b-form-group>
          <b-form-group
            label="帖子图片"
            label-for="postCover"
          >
            <el-upload
                class="avatar-uploader"
                action="/api/common/file/upload"
                :show-file-list="false"
                :on-success="handlePictureSuccess">
              <img v-if="postCover" :src="postCover" style="max-height: 200px;max-width: 300px">
              <el-button v-else size="small" type="primary">上传图片</el-button>
            </el-upload>
          </b-form-group>
          <br/>
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
    </template>
  </b-sidebar>
</template>

<script>
import {
  BSidebar, BForm, BFormGroup, BFormInput, BFormInvalidFeedback, BButton, BFormTextarea, BFormRadioGroup
} from 'bootstrap-vue'
import { ValidationProvider, ValidationObserver } from 'vee-validate'
import { required, alphaNum, email } from '@validations'
import Ripple from 'vue-ripple-directive'
import vSelect from 'vue-select'

export default {
  components: {
    BFormRadioGroup,
    BSidebar,
    BForm,
    BFormGroup,
    BFormInput,
    BFormInvalidFeedback,
    BButton,
    BFormTextarea,
    vSelect,

    // Form Validation
    ValidationProvider,
    ValidationObserver,
  },
  directives: {
    Ripple,
  },
  props: {
    isAddNewUserSidebarActive: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      required,
      alphaNum,
      email,
      options: [
        { text: '正常', value: false, disabled: false },
        { text: '违规', value: true, disabled: false },
      ],
      // 下面是帖子相关的数据
      id: null,
      content: null,
      title: null,
      postCover: null,
      isViolation: null,
    }
  },
  methods: {
    handleSubmit() {
      this.$emit('onSubmit', {
        id: this.id,
        title: this.title,
        content: this.content,
        postCover: this.postCover,
        isViolation: this.isViolation
      })
    },
    handlePictureSuccess(res, file) {
      this.postCover = file.response.data.url;
    },
  },
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
