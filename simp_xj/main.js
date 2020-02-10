import Vue from 'vue'
import App from './App'

import cuCustom from './components/cu-custom/cu-custom.vue'
Vue.component('cu-custom',cuCustom)

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()