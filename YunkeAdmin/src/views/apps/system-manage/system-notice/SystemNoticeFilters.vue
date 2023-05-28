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
          md="4"
          class="mb-md-0 mb-2"
        >
          <b-form-group
              label="公告内容"
              label-for="basicInput"
          >
            <b-form-input
                id="basicInput"
                v-model="noticeContent"
                placeholder="请输入公告内容"
            />
          </b-form-group>
        </b-col>
        <b-col
          cols="12"
          md="3"
          class="mb-md-0 mb-2"
        >
          <label>状态</label>
          <v-select
            :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
            :options="statusOptions"
            class="w-100"
            v-model="status"
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
                @click="clickQuery"
            >
              查询
            </b-button>
          </b-form-group>

        </b-col>
        <b-col
            clos="12"
            md="3"
            class="mb-md-0 mb-2 right"
        >
          <b-form-group
              label-for="basicInput"
          >
            <br/>
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
  },
  data() {
    return {
      noticeContent: null, // 公告内容
      status: null // 公告状态
    }
  },
  methods: {
    resetData() {
      this.noticeContent = null;
      this.status = null;
      this.$emit('reGetData')
    },
    clickQuery() {
      this.$emit('queryNoticeCondition', this.noticeContent, this.status?.value)
    }
  }
}
</script>

<style lang="scss">
@import '@core/scss/vue/libs/vue-select.scss';
</style>
