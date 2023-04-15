<template>
  <b-card no-body>
    <b-card-header class="pb-50">
      <h5>
        查询
      </h5>
    </b-card-header>
    <b-card-body>
      <b-row>
        <b-col
          cols="12"
          md="3"
          class="mb-md-0 mb-2"
        >
          <b-form-group
              label="博客标题"
              label-for="basicInput"
          >
            <b-form-input
                id="basicInput"
                v-model="articleTitle"
                placeholder="请输入博客标题"
            />
          </b-form-group>
        </b-col>
        <b-col
          cols="12"
          md="3"
          class="mb-md-0 mb-2"
        >
          <b-form-group
              label="博客内容"
              label-for="basicInput"
          >
            <b-form-input
                id="basicInput"
                v-model="articleText"
                placeholder="请输入博客内容"
            />
          </b-form-group>
        </b-col>
        <b-col
          cols="12"
          md="2"
          class="mb-md-0 mb-2"
        >
          <label>博客状态</label>
          <v-select
            :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
            :options="statusOptions"
            class="w-100"
            :reduce="val => val.value"
            v-model="status"
          />
        </b-col>
        <b-col
            cols="12"
            md="2"
            class="mb-md-0 mb-2"
        >
          <label>是否合规</label>
          <v-select
              :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
              :options="violationOptions"
              class="w-100"
              :reduce="val => val.value"
              v-model="violation"
          />
        </b-col>
        <b-col
          clos="12"
          md="2"
          class="mb-md-0 mb-2 right"
        >
          <b-form-group
              label-for="basicInput"
          >
            <br/>
            <b-button
                v-ripple.400="'rgba(255, 255, 255, 0.15)'"
                variant="primary"
                @click="submitData"
            >
              查询
            </b-button>
            &nbsp;
            <b-button
                v-ripple.400="'rgba(255, 255, 255, 0.15)'"
                variant="primary"
                @click="resetData"
            >
              重置
            </b-button>
          </b-form-group>

        </b-col>
      </b-row>
    </b-card-body>
  </b-card>
</template>

<script>
import {
  BButton, BCard, BCardHeader, BCardBody, BRow, BCol,BFormInput,BFormGroup
} from 'bootstrap-vue'
import vSelect from 'vue-select'
import Ripple from 'vue-ripple-directive'

export default {
  components: {
    BButton,
    BRow,
    BCol,
    BCard,
    BCardHeader,
    BCardBody,
    vSelect,
    BFormInput,
    BFormGroup
  },
  directives: {
    Ripple,
  },
  props: {
    statusOptions: {
      type: Array,
      required: true,
    },
    violationOptions: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      articleTitle: null, // 博客标题
      articleText: null, // 博客内容
      status: null, // 博客状态
      violation: null, // 是否合规
    }
  },
  methods: {
    resetData() {
      this.articleText = null;
      this.articleTitle = null;
      this.status = null;
      this.violation = null;
      this.$emit('clickReset')
    },
    submitData() {
      this.$emit('clickSearch',
          {
            articleTitle: this.articleTitle,
            articleText: this.articleText,
            status: this.status,
            violation: this.violation
          });
    }
  }
}
</script>

<style lang="scss">
@import '@core/scss/vue/libs/vue-select.scss';
</style>
