import Vue from 'vue'
import Router from 'vue-router'
import Join from "./views/Join";
import Login from "./views/Login";
import Test from "./views/Test";

Vue.use(Router);

export default new Router({
    // mode: 'history',
    routes: [
        {path: '/join', name: 'join', component: Join},
        {path: '/login', name: 'login', component: Login},
        {path: '/request', name: 'request', component: Test},
    ]
})
