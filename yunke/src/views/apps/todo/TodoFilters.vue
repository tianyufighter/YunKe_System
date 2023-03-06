<!--
  制定任务界面的左侧边栏
-->


<template>
    <div class="todo__filters-container">

        <!-- all -->
        <div class="px-6 py-4">
            <div class="flex cursor-pointer" :class="{'text-primary': todoFilter == '所有'}" @click="applyTodoFilter('所有')">
                <feather-icon icon="MailIcon" :svgClasses="[{'text-primary stroke-current': todoFilter == '所有'}, 'h-6 w-6']"></feather-icon>
                <span class="text-lg ml-3">所有</span>
            </div>
        </div>

        <vs-divider></vs-divider>

        <!-- starred -->
        <div class="px-6 py-4">
            <h5>过滤</h5>

            <template v-for="filter in todoFilters">
                <div class="flex mt-6 cursor-pointer" :class="{'text-primary': todoFilter == filter.filter}" @click="applyTodoFilter(filter.filter)" :key="filter.filter">
                    <feather-icon :icon="filter.icon" :svgClasses="[{'text-primary stroke-current': todoFilter == filter.filter}, 'h-6 w-6']"></feather-icon>
                    <span class="text-lg ml-3">{{ filter.filterName }}</span>
                </div>
            </template>

        </div>

        <vs-divider></vs-divider>

        <div class="px-6 py-4">
            <h5>标签</h5>
            <div class="todo__lables-list">
                <div class="todo__label flex items-center mt-6 cursor-pointer" v-for="(tag, index) in todoTags" :key="index" @click="applyTodoFilter(tag.value)">
                    <div class="h-4 w-4 rounded-full mr-4" :class="'bg-' + tag.color"></div>
                    <span class="text-lg" :class="{'text-primary': todoFilter == tag.value}">{{ tag.text }}</span>
                </div>
            </div>
        </div>

    </div>
</template>

<script>

export default{
    data() {
        return {
          todoFilters: [
              { filterName: '收藏', filter: '收藏', icon: 'StarIcon' },
              { filterName: '重要', filter: '重要', icon: 'InfoIcon' },
              { filterName: '完成', filter: '完成', icon: 'CheckIcon' },
              { filterName: '删除', filter: '删除', icon: 'TrashIcon' },
          ],
          todoTags: [
            { text: '学习' ,value : '学习', color: 'primary' },
            { text: '生活', value: '生活', color: 'warning'},
            { text: '工作', value: '工作', color: 'success'},
            { text: '其它', value: '其它', color: 'danger' },
          ],
        }
    },
    computed: {
      todoFilter() {
        return this.$store.state.todoFilter;
      }
    },
    methods: {
        applyTodoFilter(filterName) {
            this.$store.commit('updateTodoFilter', filterName);
        },
    },
}
</script>
