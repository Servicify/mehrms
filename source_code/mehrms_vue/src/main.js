import Vue from 'vue'

import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import VCharts from 'v-charts';

import { postKeyValueRequest } from "./utils/api";
import { postRequest } from "./utils/api";
import { getRequest } from "./utils/api";
import { putRequest } from "./utils/api";
import { deleteRequest } from "./utils/api";
import { initMenu } from './utils/menus'

import 'font-awesome/css/font-awesome.min.css'
// 制作为全局插件
Vue.prototype.postKeyValueRequest = postKeyValueRequest;
Vue.prototype.postRequest = postRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.deleteRequest = deleteRequest;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/

Vue.config.productionTip = false
Vue.use(ElementUI, { size: "small" });
Vue.use(VCharts)
// 路由全局前置守卫
router.beforeEach((to, from, next) => {
    // console.log(to);      //to参数为路由器到哪个页面去
    // console.log(from);    //from参数为路由器从哪个页面跳转
    // next();               //若没有next()方法，则不会跳转页面，执行next()方法则跳转页面至to
    // //                      参数中的页面中
    // 如果请求路劲为'/'，则直接跳转，
    // 否则，先重新请求菜单数据，再跳转
    if (to.path == '/') {
        next();
    } else {
        // 确认用户的角色，若用户未登录，则自动重定向到登录页面
        if (window.sessionStorage.getItem("user")) {
            initMenu(router, store);
            next();
        } else {
            next("/?redirect=" + to.path);
        }
    }

})

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
