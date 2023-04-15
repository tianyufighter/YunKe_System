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
              label="新闻标题"
              label-for="basicInput"
          >
            <b-form-input
                id="basicInput"
                v-model="newsTitle"
                placeholder="请输入新闻标题"
            />
          </b-form-group>
        </b-col>
        <b-col
          cols="12"
          md="4"
          class="mb-md-0 mb-2"
        >
          <b-form-group
              label="新闻内容"
              label-for="basicInput"
          >
            <b-form-input
                id="basicInput"
                v-model="newsContent"
                placeholder="请输入新闻内容"
            />
          </b-form-group>
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
          </b-form-group>

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
  data() {
    return {
      newsTitle: null, // 博客标题
      newsContent: null, // 博客内容
    }
  },
  methods: {
    resetData() {
      this.newsTitle = null;
      this.newsContent = null;
      this.$emit('clickReset')
    },
    submitData() {
      this.$emit('clickSearch',
          {
            newsTitle: this.newsTitle,
            newsContent: this.newsContent,
          });
    }
  }
}
</script>

<style lang="scss">
@import '@core/scss/vue/libs/vue-select.scss';
</style>
