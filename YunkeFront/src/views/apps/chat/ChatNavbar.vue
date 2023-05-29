<!--
  聊天面板上面的用户信息以及收藏图标
-->
<template>
    <div v-if="friend != null" class="chat__header">
        <vs-navbar class="p-4 flex navbar-custom" color="white" type="flat">
            <div class="relative flex mr-4">
                <feather-icon icon="MenuIcon" class="mr-4 cursor-pointer" v-if="isSidebarCollapsed" @click.stop="$emit('openContactsSidebar')"></feather-icon>
                <vs-avatar class="m-0 border-2 border-solid border-white" size="40px" :src="friend.headImage" />
            </div>
            <h6>{{ friend.userName }}</h6>
            <vs-spacer></vs-spacer>
        </vs-navbar>
    </div>
</template>

<script>

export default{
    props: {
        friend: {
            type: Object,
            required: true,
        },
        isSidebarCollapsed: {
            type: Boolean,
            required: true,
        }
    }
}
</script>
