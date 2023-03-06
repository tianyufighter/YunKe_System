<!--
  制定计划界面
-->
<template>
    <div id="todo-app" class="border border-solid d-theme-border-grey-light rounded relative overflow-hidden">
      <!--界面左侧边栏-->
      <vs-sidebar class="items-no-padding" parent="#todo-app" :click-not-close="clickNotClose" :hidden-background="clickNotClose" v-model="isSidebarActive">
          <todo-add-new @addNewTask="addNewTask"></todo-add-new>
          <VuePerfectScrollbar class="todo-scroll-area" :settings="settings">
            <todo-filters @closeSidebar="toggleTodoSidebar"></todo-filters>
          </VuePerfectScrollbar>
      </vs-sidebar>
      <div :class="{'sidebar-spacer': clickNotClose}" class="app-fixed-height border border-r-0 border-b-0 border-t-0 border-solid d-theme-border-grey-light app-fixed-height">
        <div class="flex items-center app-search-container border border-l-0 border-r-0 border-t-0 border-solid d-theme-border-grey-light">
          <!-- 搜索 -->
          <vs-input size="large" icon-pack="feather" icon="icon-search" placeholder="搜索..." v-model="searchQuery" class="vs-input-no-border vs-input-no-shdow-focus no-icon-border w-full" />
        </div>
        <!-- TODO LIST -->
        <VuePerfectScrollbar class="todo-content-scroll-area" :settings="settings" ref="todoListPS">
            <transition-group class="todo-list" name="list-enter-up" tag="ul" appear>
                <li class="cursor-pointer todo_todo-item" v-for="(todoItem, index) in filterTodo" :key="String(todoFilter) + String(todoItem.id)" :style="[{transitionDelay: (index * 0.1) + 's'}]">
                    <todo-item :todoItem="todoItem" @showDisplayPrompt="showDisplayPrompt($event)"></todo-item>
                </li>
            </transition-group>
        </VuePerfectScrollbar>
          <!-- /TODO LIST -->
      </div>
      <!-- EDIT TODO DIALOG -->
      <todo-edit :displayPrompt="displayPrompt" :todoItem="todoItem" @hideDisplayPrompt="hidePrompt" v-if="displayPrompt"></todo-edit>
    </div>
</template>

<script>
import TodoAddNew from "./TodoAddNew.vue"
import TodoItem from "./TodoItem.vue"
import TodoFilters from "./TodoFilters.vue"
import TodoEdit from "./TodoEdit.vue"
import VuePerfectScrollbar from 'vue-perfect-scrollbar'
import {getUserAllTask} from "../../../network";

export default{
    data() {
        return {
          clickNotClose: true,
          displayPrompt: false,
          todoItem: null, // 当前需要修改的任务
          isSidebarActive: true,
          windowWidth: window.innerWidth,
          settings: {
              maxScrollbarLength: 60,
              wheelSpeed: 0.30,
          },
          todoList: [], // 用户的所有任务
          searchQuery: ''
        }
    },
    watch: {
        todoFilter() {
            this.$refs.todoListPS.$el.scrollTop = 0;
        }
    },
    computed: {
        todo() {
            return this.$store.state.todo.todoArray;
        },
        todoFilter() {
            return this.$store.state.todoFilter;
        },
        // 过滤后的todo
        filterTodo() {
          return this.todoList.filter((todoItem) => {
            if (this.todoFilter == '所有') return !todoItem.isTrashed && (todoItem.taskTitle.includes(this.searchQuery) || todoItem.taskDesc.includes(this.searchQuery));
            if (this.todoFilter == '收藏') return !todoItem.isTrashed && todoItem.isStarred && (todoItem.taskTitle.includes(this.searchQuery) || todoItem.taskDesc.includes(this.searchQuery));
            if (this.todoFilter == '重要') return !todoItem.isTrashed && todoItem.isImportant && (todoItem.taskTitle.includes(this.searchQuery) || todoItem.taskDesc.includes(this.searchQuery));
            if (this.todoFilter == '完成') return !todoItem.isTrashed && todoItem.isDone && (todoItem.taskTitle.includes(this.searchQuery) || todoItem.taskDesc.includes(this.searchQuery));
            if (this.todoFilter == '删除') return todoItem.isTrashed && (todoItem.taskTitle.includes(this.searchQuery) || todoItem.taskDesc.includes(this.searchQuery));
            if (this.todoFilter == '学习') return !todoItem.isTrashed && todoItem.tagNameList.includes("学习") && (todoItem.taskTitle.includes(this.searchQuery) || todoItem.taskDesc.includes(this.searchQuery));
            if (this.todoFilter == '生活') return !todoItem.isTrashed && todoItem.tagNameList.includes("生活") && (todoItem.taskTitle.includes(this.searchQuery) || todoItem.taskDesc.includes(this.searchQuery));
            if (this.todoFilter == '工作') return !todoItem.isTrashed && todoItem.tagNameList.includes("工作") && (todoItem.taskTitle.includes(this.searchQuery) || todoItem.taskDesc.includes(this.searchQuery));
            if (this.todoFilter == '其它') return !todoItem.isTrashed && todoItem.tagNameList.includes("其它") && (todoItem.taskTitle.includes(this.searchQuery) || todoItem.taskDesc.includes(this.searchQuery));
          })
        },
        // todoList() {
        //     return this.$store.getters['todo/todoList'];
        // },
    },
    methods: {
        showDisplayPrompt(item) {
            this.todoItem = item;
            this.displayPrompt = true;
        },
        hidePrompt() {
            this.displayPrompt = false;
        },
        handleWindowResize(event) {
            this.windowWidth = event.currentTarget.innerWidth;
            this.setSidebarWidth();
        },
        setSidebarWidth() {
            if(this.windowWidth < 992) {
                this.isSidebarActive = this.clickNotClose = false;
            }else {
                this.isSidebarActive = this.clickNotClose = true;
            }
        },
        toggleTodoSidebar(value = false) {
            if(!value && this.clickNotClose) return
            this.isSidebarActive = value;
        },
        // 添加新任务后执行的回调函数
        addNewTask() {
          getUserAllTask().then(res => {
            if (res.data.code == 200) {
              this.todoList = res.data.data;
              this.$vs.notify({
                title:'提示',
                text: '添加任务成功',
                color:'primary',
                position:'top-center'})
            }
          }).catch(err => {
            console.error("err = ", err)
          })
        }
    },
    components: {
        TodoAddNew,
        TodoItem,
        TodoFilters,
        TodoEdit,
        VuePerfectScrollbar
    },
    created() {
        this.$nextTick(() => {
            window.addEventListener('resize', this.handleWindowResize);
        })
        this.setSidebarWidth();
    },
    beforeDestroy: function () {
        window.removeEventListener('resize', this.handleWindowResize)
    },
    mounted() {
      getUserAllTask().then(res => {
        console.log("task = ", res)
        if (res.data.code == 200) {
          this.todoList = res.data.data;
        }
      }).catch(err => {
        console.error("err = ", err)
      })
    }
}
</script>

<style lang="scss">
@import "@/assets/scss/vuesax/apps/todo.scss";
</style>
