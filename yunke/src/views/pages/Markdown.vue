<!--博客编辑界面-->
<template>
  <div id="profile-page">
    <p class="mb-4"><a href="javascript:;" rel="nofollow">云客</a> 个人博客编辑平台</p>
    <mavon-editor
        ref="md"
        placeholder="请输入文章内容..."
        :boxShadow="false"
        style="z-index: 1; border: 1px solid #d9d9d9; height: 70vh;"
        v-model="doc"
        :external-link="externalLink"
        :toolbars="toolbars"
        code-style="atom-one-dark"
        @save="activePrompt = true"
        @imgAdd="uploadImg"
    >
    </mavon-editor>
    <vs-prompt
        @vs-cancel="clearValMultiple"
        @vs-accept="acceptAlert"
        @vs-close="close"
        @
        :vs-is-valid="validName"
        :vs-active.sync="activePrompt"
        vs-title="保存博客"
        vsAcceptText="保存"
        vsCancelText="取消">
      <div class="con-exemple-prompt">
        请你输入标题和标签 <b>继续</b>.
        <vs-input placeholder="标题" v-model="valMultipe.value1" class="mt-4 mb-2 w-full" />
        <vs-input placeholder="标签" v-model="valMultipe.value2" class="w-full" />

        <vs-alert :active="!validName" color="danger" vs-icon="new_releases" class="mt-4" >
          字段不能为空，请输入数据
        </vs-alert>
      </div>
    </vs-prompt>
  </div>
</template>

<script>
import {doReleaseArticle, doUploadImage} from "../../network";

  export default {
    name: "Markdown",
    data() {
      return {
        doc: "",
        activePrompt:false,
        valMultipe:{
          value1:'',
          value2:''
        },
        externalLink: {
          hljs_js: () => '/md/highlightjs/highlight.min.js',
          hljs_css: (css) => '/md/highlightjs/styles/' + css + '.min.css',
          hljs_lang: (lang) => '/md/highlightjs/languages/' + lang + '.min.js',
          katex_css: () => '/md/katex/katex.min.css',
          katex_js: () => '/md/katex/katex.min.js',
          markdown_css: () => '/md/markdown/github-markdown.min.css'
        },
        //参数
        toolbars: {
          bold: true, // 粗体
          italic: true, // 斜体
          header: true, // 标题
          underline: true, // 下划线
          strikethrough: true, // 中划线
          mark: true, // 标记
          superscript: true, // 上角标
          subscript: true, // 下角标
          quote: true, // 引用
          ol: true, // 有序列表
          ul: true, // 无序列表
          link: true, // 链接
          imagelink: true, // 图片链接
          code: true, // code
          table: true, // 表格
          fullscreen: false, // 全屏编辑
          readmodel: true, // 沉浸式阅读
          htmlcode: true, // 展示html源码
          help: true, // 帮助
          /* 1.3.5 */
          undo: true, // 上一步
          redo: true, // 下一步
          trash: true, // 清空
          save: true, // 保存（触发events中的save事件）
          /* 1.4.2 */
          navigation: true, // 导航目录
          /* 2.1.8 */
          alignleft: true, // 左对齐
          aligncenter: true, // 居中
          alignright: true, // 右对齐
          /* 2.2.1 */
          subfield: true, // 单双栏模式
          preview: true // 预览
        }
      }
    },
    computed:{
      validName(){
        return (this.valMultipe.value1.length > 0 && this.valMultipe.value2.length > 0)
      }
    },
    methods:{
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
      acceptAlert(){
        let article = JSON.stringify({
          articleTitle: this.valMultipe.value1,
          articleContent: this.$refs.md.d_render,
          articleText: this.$refs.md.d_value,
          articleCover: '',
          category: this.valMultipe.value2,
          status: 1,
          createTime: this.dateFormat(new Date)
        });
        doReleaseArticle({
          jsonObject: article
        }).then(res => {
          this.clearValMultiple();
          this.$vs.notify({
            color:'success',
            title:'保存成功',
            text:'文章已经成功保存到数据库中'
          })
          this.doc = '';
        }).catch(err => {
          console.log(err)
        })
      },
      close(){
        this.$vs.notify({
          color:'danger',
          title:'关闭',
          text:'你关闭了弹窗'
        })
      },
      clearValMultiple() {
        this.valMultipe.value1 = "";
        this.valMultipe.value2 = "";
      },
      // 上传图片
      uploadImg(pos, file) {
        // 第一步，将图片上传到服务器
        let formData = new FormData();
        formData.append("file", file);
        doUploadImage(formData).then(res => {
          if (res.data.code == 200) {
            console.log(res)
            // 第二步，将返回的url替换到文本原位置![...](0)->![...](url)
            /**
             * $vm 指为mavonEditor实例，可以通过如下两种方式获取
             * 1. 通过引入对象获取: `import {mavonEditor} from ...` 等方式引入后，`$vm`为`mavonEditor`
             * 2. 通过$refs获取: html声明ref : `<mavon-editor ref=md ></mavon-editor>，`$vm`为 `this.$refs.md`
             */
            this.$refs.md.$img2Url(pos, res.data.data.url);
          }
        }).catch(err => {
          console.log(err)
        })
      }
    }
  }
</script>

<style>
  ins {
    text-decoration: underline;
  }
  s {
    text-decoration: line-through;
  }
</style>