export default [
  // *===============================================---*
  // *--------- USER ---- ---------------------------------------*
  // *===============================================---*
  {
    path: '/apps/user/manage',
    name: 'user-manage',
    component: () => import('@/views/apps/system-manage/user-manage/users-list/UsersList.vue'),
  },
  {
    path: '/apps/user/edit/:id',
    name: 'user-edit',
    component: () => import('@/views/apps/system-manage/user-manage/users-edit/UsersEdit.vue'),
  },
  {
    path: '/apps/param/setting',
    name: 'param-setting',
    component: () => import('@/views/apps/system-manage/param-setting/ParamSetting.vue'),
  },

  // Invoice
  {
    path: '/apps/system/notice',
    name: 'system-notice',
    component: () => import('@/views/apps/system-manage/system-notice/SystemNotice.vue'),
  },
  {
    path: '/apps/system/loginlog/',
    name: 'login-log',
    component: () => import('@/views/apps/system-manage/log-manage/login-log/index.vue'),
  },
]
