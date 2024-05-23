import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from "@/views/user/LoginView.vue"
import SignupView from "@/views/user/SignupView.vue"
import BoardView from "@/views/board/BoardView.vue"
import MapView from "@/views/map/MapView.vue"
import MyTrip from "@/views/user/MyTrip.vue"
import MyPage from "@/views/user/MyPage.vue"
import MyInfo from "@/views/user/MyInfo.vue"
import MyReview from "@/views/user/MyReview.vue"
import MyRouteList from "@/views/myRoute/MyRouteList.vue"
import MyRouteView from "@/views/myRoute/MyRouteView.vue"
import BoardList from "@/views/board/BoardList.vue"
import ArticleRegister from "@/views/board/ArticleRegister.vue"
import ArticleModify from "@/views/board/ArticleModify.vue"
import ArticleView from "@/views/board/ArticleView.vue"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignupView
    },
    {
      path: '/board',
      name: 'board',
      component: BoardView
    },
    {
      path: '/map',
      name: 'map',
      component: MapView,
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: MyPage,
      children: [
        {
          path: '/mypage/myTrip', name: 'myTrip', component: MyTrip,
          children: [
            { path: '/mypage/myTrip/myRouteList', name: 'myRouteList', component: MyRouteList },
            { path: '/mypage/myTrip/myRouteView', name: 'myRouteView', component: MyRouteView }
          ]
        },
        { path: '/mypage/myReview', name: 'myReview', component: MyReview },
        {path:'/mypage/myInfo',name:'myInfo',component:MyInfo}
      ]
    },
    {
      path: '/board',
      name: 'board',
      component: BoardView,
      children: [
        { path: '/board/list', name: 'boardList', component: BoardList },
        { path: '/article/register', name: 'artcleRegister', component: ArticleRegister},
        { path: '/article/modify/:articleno', name: 'artcleModify', component: ArticleModify },
        {path:'/aritcle/view/:articleno',name:'articleView',component:ArticleView}
      ]
    }
  ]
})

export default router
