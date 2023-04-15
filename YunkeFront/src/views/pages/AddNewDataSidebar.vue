<!--
  发布信息表单
 -->
<template>
  <vs-sidebar click-not-close position-right parent="body" default-index="1" color="primary" class="add-new-data-sidebar items-no-padding" spacer v-model="isSidebarActiveLocal">
    <div class="mt-6 flex items-center justify-between px-6">
        <h4>发布新信息</h4>
        <feather-icon icon="XIcon" @click.stop="isSidebarActiveLocal = false" class="cursor-pointer"></feather-icon>
    </div>
    <vs-divider class="mb-0"></vs-divider>

    <VuePerfectScrollbar class="scroll-area--data-list-add-new pt-4 pb-6" :settings="settings">

      <div class="p-6">
        <!-- 标题 -->
        <vs-input label="标题" name="name" v-model="title" class="mt-5 w-full" />

        <!-- 类型 -->
        <vs-select v-model="category" label="信息类型" class="mt-5 w-full">
          <vs-select-item :key="item.value" :value="item.value" :text="item.text" v-for="item in category_choices" />
        </vs-select>

        <!-- 状态 -->
<!--        <vs-select v-model="order_status" label="发布状态" class="mt-5 w-full">-->
<!--          <vs-select-item :key="item.value" :value="item.value" :text="item.text" v-for="item in order_status_choices" />-->
<!--        </vs-select>-->

        <!-- 描述信息 -->
        <vs-textarea label="描述信息" name="price" v-model="describe" class="mt-5 w-full" />

        <!-- IMG -->
        <!--图片上传-->
        <el-upload
            class="upload-demo"
            drag
            action="/api/file/upload"
            :limit="1"
            :multiple="false"
            :on-exceed="doSuggest"
            :on-success="doSuccess">
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
      </div>
    </VuePerfectScrollbar>

    <div class="flex flex-wrap items-center justify-center p-6" slot="footer">
      <vs-button class="mr-6" @click="releaseInfo">发布</vs-button>
      <vs-button type="border" color="danger" @click="isSidebarActiveLocal = false">取消</vs-button>
    </div>
  </vs-sidebar>
</template>

<script>
import VuePerfectScrollbar from 'vue-perfect-scrollbar'
import {doReleaseInfo} from "../../network";

export default {
  props: {
    isSidebarActive: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      title: '',
      category: '未知',
      // order_status: 'pending',
      describe: '',
      imgUrl: '',

      category_choices: [
        {text:'未知',value:'未知'},
        {text:'二手商品',value:'二手商品'},
        {text:'兼职信息',value:'兼职信息'},
        {text:'失物招领',value:'失物招领'}
      ],
      order_status_choices: [
        {text:'Running',value:'running'},
        {text:'Completed',value:'completed'},
        {text:'Canceled',value:'canceled'},
      ],
      settings: { // perfectscrollbar settings
          maxScrollbarLength: 60,
          wheelSpeed: .60,
      },
    }
  },
  computed: {
    isSidebarActiveLocal: {
      get() {
        return this.isSidebarActive
      },
      set(val) {
        if(!val) {
          this.$emit('closeSidebar');
          this.initValues();
        }
      }
    },
    infoTypeNum() {
      if (this.category == '二手商品') {
        return 1;
      } else if (this.category == '兼职信息') {
        return 2;
      } else if (this.category == '失物招领') {
        return 3;
      } else {
        return 0;
      }
    }
  },
  methods: {
    // 图片上传成功
    doSuccess(response, file, fileList) {
      this.imgUrl = response.data.url;
    },
    doSuggest() {
      this.$vs.notify({
        title:'提示',
        text:'最多只能上传一张图片',
        color:'warning'})
    },
    initValues() {
      this.name = '';
      this.category = 'audio';
      this.order_status = 'pending';
      this.price = '';
      this.$refs.fileUpload.srcs = [];
    },
    releaseInfo() {
      doReleaseInfo({
        infoCover: this.imgUrl,
        infoTitle: this.title,
        infoContent: this.describe,
        type: this.infoTypeNum
      }).then(res => {
        if (res.data.code === 200) {
          this.title = "";
          this.describe = "";
          this.imgUrl = "";
          this.category = '未知',
          this.$vs.notify({
            title:'提示',
            text: "信息发布成功",
            color:'success',
            position:'top-center'})
          this.$emit('closeSidebar')
        }
      }).catch(err => {
        console.log("err = ", err)
      })
    }
  },
  components: {
    VuePerfectScrollbar,
  }
}
</script>

<style lang="scss" scoped>
.add-new-data-sidebar {
  /deep/ .vs-sidebar--background {
    z-index: 52010;
  }

  /deep/ .vs-sidebar {
    z-index: 52010;
    width: 400px;
    max-width: 90vw;

    .img-upload {
      margin-top: 2rem;

      .con-img-upload {
        padding: 0;
      }

      .con-input-upload {
        width: 100%;
        margin: 0;
      }
    }
  }
}

.scroll-area--data-list-add-new {
    height: calc(100% - 4.3rem);
}
</style>
