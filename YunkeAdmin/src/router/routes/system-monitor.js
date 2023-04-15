export default [
  {
    path: '/apps/monitor/datamonitor',
    name: 'data-monitor',
    component: () => import('@/views/apps/system-monitor/data-monitor/index.vue'),
  },
  {
    path: '/apps/monitor/servicemonitor',
    name: 'service-monitor',
    component: () => import('@/views/apps/system-monitor/service-monitor/index.vue'),
  },

  // Invoice
  {
    path: '/apps/monitor/cachemonitor',
    name: 'cache-monitor',
    component: () => import('@/views/apps/system-monitor/cache-monitor/index.vue'),
  },
  {
    path: '/apps/monitor/cachelist',
    name: 'cache-list',
    component: () => import('@/views/apps/system-monitor/cache-list/index.vue'),
  },
]
