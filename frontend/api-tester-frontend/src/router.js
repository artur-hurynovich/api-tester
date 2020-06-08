import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/views/Main'
import AccessDenied from '@/views/AccessDenied'
import Registration from '@/views/Registration'
import Login from '@/views/Login'

Vue.use(Router)

export default new Router({
    mode: "history",
    routes: [
        {
            path: "/",
            component: Main
        },
        {
            path: "/access-denied",
            component: AccessDenied
        },
        {
            path: "/registration",
            component: Registration
        },
        {
            path: "/login",
            component: Login
        }
    ]
})
