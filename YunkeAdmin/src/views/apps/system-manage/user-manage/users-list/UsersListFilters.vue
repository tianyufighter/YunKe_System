<template>
  <b-card no-body>
    <b-card-header class="pb-50">
      <h5>
        过滤
      </h5>
    </b-card-header>
    <b-card-body>
      <b-row>
        <b-col
          cols="12"
          md="4"
          class="mb-md-0 mb-2"
        >
          <label>角色</label>
          <v-select
            :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
            v-model="role"
            :options="roleOptions"
            class="w-100"
          />
        </b-col>
        <b-col
          cols="12"
          md="4"
          class="mb-md-0 mb-2"
        >
          <label>性别</label>
          <v-select
            :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
            v-model="gender"
            :options="genderOptions"
            class="w-100"
          />
        </b-col>
        <b-col
          cols="12"
          md="3"
          class="mb-md-0 mb-2"
        >
          <label>状态</label>
          <v-select
            :dir="$store.state.appConfig.isRTL ? 'rtl' : 'ltr'"
            v-model="status"
            :options="statusOptions"
            class="w-100"
          />
        </b-col>
        <b-col
            cols="12"
            md="1"
            class="mb-md-0 mb-2"
        >
          <br/>
          <b-button
              variant="primary"
              @click="submitQuery"
          >
            <span class="text-nowrap">查询</span>
          </b-button>
        </b-col>
      </b-row>
    </b-card-body>
  </b-card>
</template>

<script>
import {
  BCard, BCardHeader, BCardBody, BRow, BCol, BButton
} from 'bootstrap-vue'
import vSelect from 'vue-select'

export default {
  components: {
    BRow,
    BCol,
    BCard,
    BCardHeader,
    BCardBody,
    vSelect,
    BButton
  },
  props: {
    roleOptions: {
      type: Array,
      required: true,
    },
    genderOptions: {
      type: Array,
      required: true,
    },
    statusOptions: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      role: null,
      gender: null,
      status: null
    }
  },
  methods: {
    submitQuery() {
      this.$emit('submitQuery', this.role, this.gender, this.status);
    }
  }
}
</script>

<style lang="scss">
@import '@core/scss/vue/libs/vue-select.scss';
</style>
