import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex);


const store = new Vuex.Store({
    state: {
        // 从服务器里加载的路由数组
        routes: [],
        sessions: {},
        currentHr: JSON.parse(window.sessionStorage.getItem("user")),
    },
    mutations: {
        initRoutes(state, data) {
            // 传递上方的state参数的routes和后端传递的数据data
            state.routes = data;
        },
        INIT_CURRENTHR(state, hr) {
            state.currentHr = hr;
        },
    },
    actions: {}
})

store.watch(function (state) {
    return state.sessions
}, {
    deep: true/*这个貌似是开启watch监测的判断,官方说明也比较模糊*/
})

export default store;
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/