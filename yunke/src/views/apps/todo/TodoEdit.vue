<!--
  修改任务的表单
-->
<template>
    <vs-prompt
        vs-title="编辑任务"
        vs-accept-text= "提交"
        vs-cancel-text = "移除"
        vs-button-cancel = "border"
        @vs-cancel="removeTodo"
        @vs-accept="submitTodo"
        @vs-close="init"
        :vs-is-valid="validateForm"
        :vs-active.sync="activePrompt">
        <div>
            <form>
                <div class="vx-row">
                    <div class="vx-col w-1/6 self-center">
                        <vs-checkbox v-model="isDoneLocal" class="w-8"></vs-checkbox>
                    </div>

                    <div class="vx-col ml-auto flex">
                        <feather-icon icon="InfoIcon" class="cursor-pointer" :svgClasses="[{'text-success stroke-current': isImportantLocal}, 'w-5', 'h-5 mr-4']" @click.prevent="toggleIsImportant"></feather-icon>

                        <feather-icon icon="StarIcon" class="cursor-pointer" :svgClasses="[{'text-warning stroke-current': isStarredLocal}, 'w-5', 'h-5 mr-4']" @click.prevent="toggleIsStarred"></feather-icon>

                        <vs-dropdown class="cursor-pointer" vs-custom-content>

                            <feather-icon icon="TagIcon" svgClasses="h-5 w-5"></feather-icon>

                            <vs-dropdown-menu style="z-index: 200001">
                                    <vs-dropdown-item v-for="(tag, index) in todoTags" :key="index">
                                        <vs-checkbox @click.stop :vs-value="tag.value" v-model="tagsLocal">{{ tag.text }}</vs-checkbox>
                                    </vs-dropdown-item>
                            </vs-dropdown-menu>
                        </vs-dropdown>
                    </div>
                </div>

                <div class="vx-row">
                    <div class="vx-col w-full">
                        <vs-input v-validate="'required'" name="title" class="w-full mb-4 mt-5" placeholder="标题" v-model="titleLocal" />
                        <vs-textarea rows="5" label="修改描述" v-model="descLocal" />
                    </div>
                </div>

            </form>
        </div>
    </vs-prompt>
</template>

<script>
import {doDeleteTask, doUpdateOrAddTask} from "../../../network";

export default {
    props: {
        displayPrompt: {
            type: Boolean,
            required: true,
        },
        todoItem: {
            type: Object,
            required: true,
        }
    },
    data() {
        return {
            titleLocal: this.todoItem.taskTitle,
            descLocal: this.todoItem.taskDesc,
            isDoneLocal: this.todoItem.isDone,
            isImportantLocal: this.todoItem.isImportant,
            isStarredLocal: this.todoItem.isStarred,
            tagsLocal: this.todoItem.tagNameList,
            todoTags: [
              { text: '学习' ,value : '学习', color: 'primary' },
              { text: '生活', value: '生活', color: 'warning'},
              { text: '工作', value: '工作', color: 'success'},
              { text: '其它', value: '其它', color: 'danger' },
            ],
        }
    },
    computed: {
        activePrompt: {
            get() {
                return this.displayPrompt;
            },
            set(value) {
                this.$emit('hideDisplayPrompt', value);
            }
        },
        validateForm() {
            return !this.errors.any() && this.titleLocal != '';
        },
    },
    methods: {
        toggleIsImportant() {
            this.isImportantLocal = !this.isImportantLocal;
        },
        toggleIsStarred() {
            this.isStarredLocal = !this.isStarredLocal;
        },
        removeTodo() {
          if (this.$store.state.todoFilter == '删除') {
            let idList = [this.todoItem.id]
            doDeleteTask({
              idList: idList
            }).then(res => {
              if (res.data.code == 200) {
                this.$vs.notify({
                  title:'提示',
                  text: '任务已成功删除',
                  color:'primary',
                  position:'top-center'})
              }
            }).catch(err => {
              console.error(err)
            })
          } else {
            doUpdateOrAddTask({
              id: this.todoItem.id,
              isTrashed: true
            }).then(res => {
              if (res.data.code == 200) {
                this.todoItem.isTrashed = true;
              }
            }).catch(err => {
              console.error("err = ", err)
            })
          }
        },
        init() {

        },
        submitTodo() {
          let taskObj = {};
          taskObj.id = this.todoItem.id;
          taskObj.taskTitle = this.titleLocal;
          taskObj.taskDesc = this.descLocal;
          taskObj.isImportant = this.isImportantLocal;
          taskObj.isDone = this.isDoneLocal;
          taskObj.isStarred = this.isStarredLocal;
          taskObj.tagNameList = this.tagsLocal;
          doUpdateOrAddTask(taskObj).then(res => {
            if (res.data.code == 200) {
              this.todoItem.taskTitle = this.titleLocal;
              this.todoItem.taskDesc = this.descLocal;
              this.todoItem.isImportant = this.isImportantLocal;
              this.todoItem.isDone = this.isDoneLocal;
              this.todoItem.isStarred = this.isStarredLocal;
              this.todoItem.tagNameList = this.tagsLocal;
            }
          }).catch(err => {
            console.error("err = ", err)
          })
        }
    },
}
</script>
