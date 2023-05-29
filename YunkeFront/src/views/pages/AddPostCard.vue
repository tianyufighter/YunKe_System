<!--
  发布帖子信息
 -->
<template>
  <vs-sidebar click-not-close position-right parent="body" default-index="1" color="primary" class="add-new-data-sidebar items-no-padding" spacer v-model="isSidebarActiveLocal">
    <div class="mt-6 flex items-center justify-between px-6">
      <h4>发布帖子</h4>
      <feather-icon icon="XIcon" @click.stop="isSidebarActiveLocal = false" class="cursor-pointer"></feather-icon>
    </div>
    <vs-divider class="mb-0"></vs-divider>

    <VuePerfectScrollbar class="scroll-area--data-list-add-new pt-4 pb-6" :settings="settings">
      <div class="p-6">
        <!-- 标题 -->
        <vs-input label="标题" name="name" v-model="title" class="mt-5 w-full" />
        <!-- 描述信息 -->
        <vs-textarea label="描述信息" name="price" v-model="describe" class="mt-5 w-full" />

        <!--图片上传-->
        <el-upload
            class="upload-demo"
            drag
            ref="imgUpload"
            action="/api/common/file/upload"
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
      <vs-button class="mr-6" @click="releasePost">发布</vs-button>
      <vs-button type="border" color="danger" @click="isSidebarActiveLocal = false">取消</vs-button>
    </div>
  </vs-sidebar>
</template>

<script>
import VuePerfectScrollbar from 'vue-perfect-scrollbar'
import {doReleasePost} from "../../network";
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
      describe: '',
      imgUrl: '',
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
      this.title = ''
      this.describe = ''
      this.imgUrl = ''
    },
    // 发布帖子
    releasePost() {
      doReleasePost({
        title: this.title,
        content: this.describe,
        postCover: this.imgUrl,
        createTime: this.dateFormat(new Date())
      }).then(res => {
        if (res.data.code === 200) {
          this.$refs.imgUpload.clearFiles();
          this.$emit('closeSidebar')
          this.title = "";
          this.describe = "";
          this.imgUrl = "";
          this.$vs.notify({
            title:'提示',
            text: "帖子发布成功",
            color:'success',
            position:'top-center'})
        }
      }).catch(err => {
        console.log("err = ", err)
      })
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
