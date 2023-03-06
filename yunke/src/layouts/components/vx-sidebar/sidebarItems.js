/*
  侧边导航栏的每个菜单数据
  Strucutre:
          url     => router path
          name    => name to display in sidebar
          slug    => router path name
          icon    => Feather Icon component/icon name
          tag     => text to display on badge
          tagColor  => class to apply on badge element
          i18n    => Internationalization
          submenu   => submenu of current item (current item will become dropdown )
                NOTE: Submenu don't have any icon(you can add icon if u want to display)
          isDisabled  => disable sidebar item/group
*/


export default [
  {
    url: null,
    name: "Dashboard",
    slug: "dashboard",
    tag: "3",
    tagColor: "warning",
    icon: "HomeIcon",
    i18n: "Home",
    submenu: [
      // 左侧菜单栏——个人数据
      {
        url: '/home/personalData',
        name: "PersonalData",
        slug: "dashboardAnalytics",
        i18n: "PersonalData",
      },
      // 左侧菜单栏——社区
      {
        url: '/home/community',
        name: "Community",
        slug: "dashboardECommerce",
        i18n: "Community",
      },
      // 左侧菜单栏——我的帖子
      {
        url: '/home/myPost',
        name: "MyPost",
        slug: "dashboardECommerce",
        i18n: "MyPost",
      },
    ]
  },
  {
    header: "Apps",
    i18n: "Apps",
  },
  // 左侧菜单栏——聊天
  {
    url: "/apps/chat",
    name: "Chat",
    slug: "chat",
    icon: "MessageSquareIcon",
    i18n: "Chat",
  },
  // 左侧菜单栏——任务
  {
    url: "/apps/todo",
    name: "Todo",
    slug: "todo",
    icon: "CheckSquareIcon",
    i18n: "Todo",
  },
  // 左侧菜单栏——日历
  {
    url: "/apps/calendar/vue-fullcalendar",
    name: "Calendar",
    slug: "calendar",
    icon: "CalendarIcon",
    tag: "new",
    tagColor: "success",
    i18n: "Calendar",
  },
  // 左侧菜单栏——百事通
  {
    url: null,
    name: "KnowEverything",
    slug: "eCommerce",
    icon: "CloudIcon",
    i18n: "KnowEverything",
    submenu: [
      // 左侧菜单栏——信息平台
      {
        url: '/apps/knowEverything/information',
        name: "InfoPlatform",
        slug: "eCommerceShop",
        i18n: "InfoPlatform",
      },
      // 左侧菜单栏——管理信息
      {
        url: '/apps/knowEverything/informationManage',
        name: "ManageInfo",
        slug: "eCommerceCheckout",
        i18n: "ManageInfo",
      },
    ]
  },
  // 左侧菜单栏——新闻
  {
    url: "/apps/news",
    name: "News",
    slug: "colors",
    icon: "FileTextIcon",
    i18n: "News",
  },
  // 左侧菜单栏——博客面板
  {
    url: null,
    name: 'BlogPanel',
    icon: 'EditIcon',
    i18n: 'BlogPanel',
    submenu: [
      // 左侧菜单栏——博客编辑器
      {
        url: '/apps/blog-editor',
        name: "BlogEditor",
        slug: "extraComponentQuillEditor",
        i18n: "BlogEditor",
      },
      // 左侧菜单栏——博客管理
      {
        url: '/pages/BlogManagement',
        name: "BlogManagement",
        slug: "extraComponentQuillEditor",
        i18n: "BlogManagement",
      }
    ]
  },
  // 左侧菜单栏——支持
  {
    header: "Support",
    i18n: "Support",
  },
  // 左侧菜单栏——关于
  {
    url: '/pages/About',
    name: "About",
    icon: "UsersIcon",
    slug: "todo",
    i18n: "About",
  },
]
