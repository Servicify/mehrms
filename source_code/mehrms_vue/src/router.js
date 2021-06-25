import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/Login.vue'
import Home from './views/Home.vue'

// 个人中心
import HrInfo from './views/HrInfo.vue'


Vue.use(Router)

export default new Router({
    mode: 'hash',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            name: 'Login',
            component: Login,
            // 避免登录页被动态地渲染到侧边栏菜单的左边去
            hidden: true
        },
        {
            path: '/home',
            name: 'Home',
            component: Home,
            // 避免登录页被动态地渲染到侧边栏菜单的左边去
            hidden: true,
            meta: {
                roles: ['admin', 'user']
            },
            children: [
                {
                    path: '/hrinfo',
                    name: '个人中心',
                    component: HrInfo,
                    hidden: true
                }
            ]
        }, {
            path: '*',
            redirect: '/home'
        }
    ]
})
/**
 * Created by Servicify
 * Date 2021/6/7
 * Time 9:08
 **/