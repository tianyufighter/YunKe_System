import Vue from 'vue'
import App from './App.vue'

// Vuesax Component Framework
import Vuesax from 'vuesax'
import 'material-icons/iconfont/material-icons.css' //Material Icons
import 'vuesax/dist/vuesax.css'; // Vuesax
Vue.use(Vuesax)


// axios
import axios from 'axios'
Vue.prototype.$http = axios

// Theme Configurations
import '../themeConfig.js'


// Globally Registered Components
import './globalComponents.js'


// Styles: SCSS
import './assets/scss/main.scss'


import '@/assets/css/main.css';


// Vue Router
import router from './router'


// Vuex Store
import store from './store/store'


// i18n
import i18n from './i18n/i18n'


// Vuesax Admin Filters
import './filters/filters'


// Clipboard
import VueClipboard from 'vue-clipboard2'
Vue.use(VueClipboard);

// 引入element ui
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
Vue.use(ElementUI)

// Tour
import VueTour from 'vue-tour'
Vue.use(VueTour)
require('vue-tour/dist/vue-tour.css')


// VeeValidate
import VeeValidate from 'vee-validate';
Vue.use(VeeValidate);


// Vuejs - Vue wrapper for hammerjs
import { VueHammer } from 'vue2-hammer'
Vue.use(VueHammer)

import VueCookie from 'vue-cookies';
Vue.use(VueCookie);

// markdown
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
Vue.use(mavonEditor)

// PrismJS
import 'prismjs'
import 'prismjs/themes/prism-tomorrow.css'


// Feather font icon
require('./assets/css/iconfont.css')


Vue.config.productionTip = false


new Vue({
    router,
    store,
    i18n,
    render: h => h(App)
}).$mount('#app')
