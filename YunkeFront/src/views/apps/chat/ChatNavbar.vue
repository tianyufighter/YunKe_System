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
            <feather-icon icon="StarIcon" class="cursor-pointer" :svgClasses="[{'text-warning stroke-current': isPinnedLocal}, 'w-6', 'h-6']" @click.stop="toggleIsPinned"></feather-icon>
        </vs-navbar>
    </div>
</template>

<script>
import contacts from './contacts'

export default{
    props: {
        friend: {
            type: Object,
            required: true,
        },
        isPinnedProp: {
            type: Boolean,
            required: true,
        },
        isSidebarCollapsed: {
            type: Boolean,
            required: true,
        }
    },
    data() {
        return {
            contacts: contacts,
            isPinnedLocal: this.isPinnedProp,
        }
    },
    watch: {
        isPinnedProp(val) {
            this.isPinnedLocal = val;
        }
    },
    computed: {
        contactIndex() {
            return contacts.findIndex(contact => contact.id == this.userId);
        },
        userImg() {
            if(this.contactIndex === -1) {
                return this.$store.state.AppActiveUser.img;
            }else{
                return this.contacts[this.contactIndex].img;
            }
        },
        contactName() {
            if(this.contactIndex === -1) {
                return this.$store.state.AppActiveUser.name;
            }else{
                return this.contacts[this.contactIndex].name;
            }
        },
    },
    methods: {
        getUserStatus(isActiveUser) {
            return (isActiveUser) ? this.$store.state.AppActiveUser.status : this.contacts[this.contactIndex].status;
        },
        toggleIsPinned() {
            this.isPinnedLocal = !this.isPinnedLocal
        }
    }
}
</script>
