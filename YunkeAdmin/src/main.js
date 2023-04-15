import Vue from 'vue'
import { ToastPlugin, ModalPlugin } from 'bootstrap-vue'
import VueCompositionAPI from '@vue/composition-api'

import Vuesax from 'vuesax'
import 'vuesax/dist/vuesax.css'; // Vuesax
Vue.use(Vuesax)

// markdown
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor)

import i18n from '@/libs/i18n'
import router from './router'
import store from './store'
import App from './App.vue'
import plugins from './plugins' // plugins
Vue.use(plugins)
//ElementUI
import './element-ui'

// 分页组件
import Pagination from "@/components/Pagination";
Vue.component('Pagination', Pagination)
// Global Components
import './global-components'

// 3rd party plugins
import '@axios'
import '@/libs/portal-vue'
import '@/libs/clipboard'
import '@/libs/toastification'
import '@/libs/sweet-alerts'
import '@/libs/vue-select'
import '@/libs/tour'

import VueCookie from 'vue-cookies';
Vue.use(VueCookie);

// BSV Plugin Registration
Vue.use(ToastPlugin)
Vue.use(ModalPlugin)

// Composition API
Vue.use(VueCompositionAPI)

// Feather font icon - For form-wizard
// * Shall remove it if not using font-icons of feather-icons - For form-wizard
require('@core/assets/fonts/feather/iconfont.css') // For form-wizard

// import core styles
require('@core/scss/core.scss')

// import assets styles
require('@/assets/scss/style.scss')

Vue.config.productionTip = false

new Vue({
  router,
  store,
  i18n,
  render: h => h(App),
}).$mount('#app')
