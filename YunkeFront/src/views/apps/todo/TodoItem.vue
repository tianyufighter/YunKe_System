<!--
  任务列表展示界面
-->
<template>
    <div @click="displayPrompt" class="px-4 py-4 list-item-component">
        <div class="vx-row">
            <div class="vx-col w-full sm:w-5/6 flex sm:items-center sm:flex-row flex-col">
                <div class="flex items-center">
                    <vs-checkbox v-show="!todoItem.isTrashed" v-model="todoItem.isDone" class="w-8 m-0 vs-checkbox-small" @click.stop="toggleIsDone"></vs-checkbox>
                    <h6 class="todo-title" :class="{'line-through': todoItem.isDone}">{{ todoItem.taskTitle }}</h6>
                </div>
                <div class="todo-tags sm:ml-2 sm:my-0 my-2 flex">
                    <vs-chip v-for="(tag, index) in todoItem.tagNameList" :key="index">
                        <div class="h-2 w-2 rounded-full mr-1" :class="'bg-' + todoLabelColor(tag)"></div>
                        <span>{{ tag | capitalize }}</span>
                    </vs-chip>
                </div>
            </div>

            <div class="vx-col w-full sm:w-1/6 ml-auto flex sm:justify-end">
                <feather-icon icon="InfoIcon" class="cursor-pointer" :svgClasses="[{'text-success stroke-current': todoItem.isImportant}, 'w-5', 'h-5 mr-4']" @click.stop="toggleIsImportant"></feather-icon>
                <feather-icon icon="StarIcon" class="cursor-pointer" :svgClasses="[{'text-warning stroke-current': todoItem.isStarred}, 'w-5', 'h-5 mr-4']" @click.stop="toggleIsStarred"></feather-icon>
                <feather-icon icon="TrashIcon" class="cursor-pointer" svgClasses="w-5 h-5" @click.stop="moveToTrash" v-if="!todoItem.isTrashed"></feather-icon>
            </div>
        </div>
        <div class="vx-row">
            <div class="vx-col w-full">
                <p class="mt-2 truncate" :class="{'line-through': todoItem.isDone}">{{ todoItem.taskDesc }}</p>
            </div>
        </div>
    </div>
</template>

<script>
import {doAddOrUpdateTask} from "../../../network";

export default{
    props: {
        todoItem: {
            type: Object,
            required: true,
        }
    },
    data() {
        return {
        }
    },
    computed: {
        todoLabelColor() {
            return (label) => {
              let todoTags = [
                { text: '学习' ,value : '学习', color: 'primary' },
                { text: '生活', value: '生活', color: 'warning'},
                { text: '工作', value: '工作', color: 'success'},
                { text: '其它', value: '其它', color: 'danger' },
              ];
              return todoTags.find((tag) => {
                  return tag.value == label
              }).color
            }
        }
    },
    methods: {
        toggleIsImportant() {
          doAddOrUpdateTask({
            id: this.todoItem.id,
            isImportant: !this.todoItem.isImportant
          }).then(res => {
            if (res.data.code == 200) {
              this.$vs.notify({
                title:'提示',
                text: this.todoItem.isImportant == true ? "已取消标记为重要" : "已标记为重要",
                color:'success',
                position:'top-center'})
              this.todoItem.isImportant = !this.todoItem.isImportant;
            }
          }).catch(err => {
            console.log(err)
          })
        },
        toggleIsStarred() {
          doAddOrUpdateTask({
            id: this.todoItem.id,
            isStarred: !this.todoItem.isStarred
          }).then(res => {
            if (res.data.code == 200) {
              this.$vs.notify({
                title:'提示',
                text: this.todoItem.isStarred == true ? "已取消收藏" : "已成功收藏",
                color:'success',
                position:'top-center'})
              this.todoItem.isStarred = !this.todoItem.isStarred;
            }
          }).catch(err => {
            console.log(err)
          })
        },
        toggleIsDone() {
          doAddOrUpdateTask({
            id: this.todoItem.id,
            isDone: !this.todoItem.isDone
          }).then(res => {
            if (res.data.code == 200) {
              this.$vs.notify({
                title:'提示',
                text: this.todoItem.isDone == true ? "已将任务标记为完成" : "已取消标记任务",
                color:'success',
                position:'top-center'})
            }
          }).catch(err => {
            console.log(err)
          })
        },
        moveToTrash() {
          doAddOrUpdateTask({
            id: this.todoItem.id,
            isTrashed: !this.todoItem.isTrashed
          }).then(res => {
            if (res.data.code == 200) {
              this.$vs.notify({
                title:'提示',
                text: "已删除该任务",
                color:'success',
                position:'top-center'})
            }
            this.todoItem.isTrashed = !this.todoItem.isTrashed;
          }).catch(err => {
            console.log(err)
          })
        },
        editTodo() {
            alert();
        },
        displayPrompt() {
            this.$emit('showDisplayPrompt', this.todoItem);
        }
    },
}
</script>
