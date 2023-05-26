/*=========================================================================================
  File Name: state.js
  Description: Vuex Store - state
  ----------------------------------------------------------------------------------------
  Item Name: Vuesax Admin - VueJS Dashboard Admin Template
  Author: Pixinvent
  Author URL: http://www.themeforest.net/user/pixinvent
==========================================================================================*/

import navbarSearchAndPinList from '@/layouts/components/navbarSearchAndPinList'
import themeConfig from '@/../themeConfig.js'
import colors from '@/../themeConfig.js'

const state = {
    isSidebarActive: true,
    breakpoint: null,
    sidebarWidth: "default",
    reduceButton: themeConfig.sidebarCollapsed,
    bodyOverlay: false,
    sidebarItemsMin: false,
    theme: themeConfig.theme || 'light',
    navbarSearchAndPinList: navbarSearchAndPinList,
    newsDetail: {title:'你丫的'},
    blogDetail: {title: '博客标题'},
    avatar: '',
    username: '',
    todoFilter: '所有', // 任务界面的过滤条件
    totalReleaseBlog: JSON.parse(sessionStorage.getItem("totalReleaseBlog")) || 0, // 博客总的发布量
    totalReleasePost: JSON.parse(sessionStorage.getItem("totalReleasePost")) || 0, // 帖子总的发布量
    AppActiveUser: {
        id: 0,
        name: 'John Doe',
        about: 'Dessert chocolate cake lemon drops jujubes. Biscuit cupcake ice cream bear claw brownie brownie marshmallow.',
        img: '',
        status: 'online',
    },

    themePrimaryColor: colors.primary,

    starredPages: navbarSearchAndPinList.data.filter((page) => page.highlightAction),
    userRole: null,

    // Can be used to get current window with
    // Note: Above breakpoint state is for internal use of sidebar component
    windowWidth: null,
}

export default state
