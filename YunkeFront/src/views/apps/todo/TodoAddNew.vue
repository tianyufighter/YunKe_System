<!--
  添加任务的表单
-->


<template>
    <div class="px-6 pb-2 pt-6">
    <vs-button @click="activePrompt = true" class="w-full">添加任务</vs-button>
    <vs-prompt
        vs-title="添加任务"
        vs-accept-text= "添加"
        vsCancelText="取消"
        vs-button-cancel = "border"
        @vs-cancel="clearFields"
        @vs-accept="submitTodo"
        @vs-close="clearFields"
        :vs-is-valid="validateForm"
        :vs-active.sync="activePrompt">
        <div>
            <form>
                <div class="vx-row">

                    <div class="vx-col ml-auto flex">
                        <feather-icon icon="InfoIcon" class="cursor-pointer" :svgClasses="[{'text-success stroke-current': isImportant}, 'w-5', 'h-5 mr-4']" @click.prevent="isImportant = !isImportant"></feather-icon>

                        <feather-icon icon="StarIcon" class="cursor-pointer" :svgClasses="[{'text-warning stroke-current': isStarred}, 'w-5', 'h-5 mr-4']" @click.prevent="isStarred = !isStarred"></feather-icon>

                        <vs-dropdown class="cursor-pointer" vs-custom-content>

                            <feather-icon icon="TagIcon" svgClasses="h-5 w-5" @click.prevent></feather-icon>
                            <!-- <vs-button radius color="success" type="flat" iconPack="feather" icon="icon-tag" @click.prevent></vs-button> -->

                            <vs-dropdown-menu style="z-index: 200001">
                                    <vs-dropdown-item v-for="(tag, index) in todoTags" :key="index">
                                        <vs-checkbox @click.stop :vs-value="tag.value" v-model="tags">{{ tag.text }}</vs-checkbox>
                                    </vs-dropdown-item>
                            </vs-dropdown-menu>
                        </vs-dropdown>
                    </div>
                </div>

                <div class="vx-row">
                    <div class="vx-col w-full">
                        <vs-input v-validate="'required'" name="title" class="w-full mb-4 mt-5" placeholder="标题" v-model="title" :color="validateForm ? 'success' : 'danger'" />
                        <vs-textarea rows="5" label="添加描述" v-model="desc" />
                    </div>
                </div>

            </form>
        </div>
    </vs-prompt>
    </div>
</template>

<script>
import {doAddOrUpdateTask} from "../../../network";

export default {
    data() {
        return {
            activePrompt: false,

            // task fields
            title: '',
            desc: '',
            isImportant: false,
            isStarred: false,
            tags: [],

            // task obj
            taskObj: {},
            todoTags: [
              { text: '学习' ,value : '学习', color: 'primary' },
              { text: '生活', value: '生活', color: 'warning'},
              { text: '工作', value: '工作', color: 'success'},
              { text: '其它', value: '其它', color: 'danger' },
            ],
        }
    },
    computed: {
        todoArrayLength() {
            return this.$store.getters['todo/todoArrayLength'];
        },
        // todoTags() {
        //     return this.$store.state.todo.todoTags;
        // },
        validateForm() {
            return !this.errors.any() && this.title != '';
        }
    },
    methods: {
        // 添加任务
        addTodo() {
          this.taskObj.taskTitle = this.title;
          this.taskObj.taskDesc = this.desc;
          this.taskObj.isImportant = this.isImportant;
          this.taskObj.isDone = false;
          this.taskObj.isStarred = this.isStarred;
          this.taskObj.tagNameList = this.tags;
          this.taskObj.isTrashed = false;
          this.taskObj.createTime = this.dateFormat(new Date())
          doAddOrUpdateTask(this.taskObj).then(res => {
            this.clearFields()
            if (res.data.code == 200) {
              this.$emit('addNewTask');
            }
          }).catch(err => {
            console.error("err = ", err)
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
        },
        clearFields() {
            // clear all fileds in todo
            this.title = "";
            this.desc = "";
            this.isDone = false;
            this.isImportant = false;
            this.isStarred = false;
            this.tags = [];
        },
        submitTodo() {
            this.$validator.validateAll().then(result => {
                if (result) this.addTodo();
            })
        }
    },
}
</script>
