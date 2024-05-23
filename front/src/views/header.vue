<script setup>
import { ref, watch } from 'vue';
import { storeToRefs } from "pinia"
import { RouterLink, RouterView, useRouter } from 'vue-router'
import { useMemberStore } from '@/stores/jwt_token'

const router = useRouter();
const memberStore = useMemberStore()
const isToken = ref(sessionStorage.getItem("accessToken") == null ? false : true)
const { isLogin } = storeToRefs(memberStore)
const { userLogout } = memberStore

const logout = async () => {
    await userLogout();
    if (isLogin.value == false) {
        router.replace(router.currentRoute.value.fullPath);
    }
}
const handleToggle = () => {
    const navbarNav = document.getElementById('navbarNav');
    if (navbarNav.classList.contains('show')) {
        navbarNav.classList.remove('show');
    } else {
        navbarNav.classList.add('show');
    }
};
</script>

<template>
    <header id="header"
        class="navbar navbar-expand-lg ps-4 pe-4 shadow-sm p-3 bg-body-tertiary rounded sticky-top header-style">
        <div class="container-fluid">
            <RouterLink :to="{ name: 'home' }" class="nav-link active fs-5" aria-current="page">
                <img class='col-4 ms-4' src='@/assets/nav_logo.png'>
            </RouterLink>
            <button class="navbar-toggler" type="button" @click="handleToggle" aria-controls="navbarNav"
                aria-expanded="false data-bs-toggle=collapse" data-bs-target="#navbarNav"
                aria-label=" Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <nav class="collapse navbar-collapse mr-1" id="navbarNav">
                <ul class="navbar-nav ms-auto me-2 grid gap-4">
                    <li class="navbar-item">
                        <RouterLink :to="{ name: 'map' }" class="nav-link active fs-5" aria-current="page">나만의 여행계획
                        </RouterLink>
                    </li>
                    <li class="navbar-item">
                        <RouterLink :to="{ name: 'boardList' }" class="nav-link active fs-5" aria-current="page">후기 게시판
                        </RouterLink>
                    </li>
                    <li id='nav-login' class="navbar-item" v-show="!isToken">
                        <RouterLink :to="{ name: 'login' }" class="nav-link active fs-5" aria-current="page">로그인
                        </RouterLink>
                    </li>
                    <li id='nav-login' class="navbar-item" v-show="!isToken">
                        <RouterLink :to="{ name: 'signup' }" class="nav-link active fs-5" aria-current="page">회원가입
                        </RouterLink>
                    </li>
                    <li id='nav-logout' class="navbar-item" v-show="isToken">
                        <a class="nav-link active fs-5" aria-current="page" @click="logout">로그아웃</a>
                    </li>
                    <li id='nav-mypage' class="navbar-item" v-show="isToken">
                        <RouterLink :to="{ name: 'myRouteView' }" class="nav-link active fs-5" aria-current="page">마이페이지
                        </RouterLink>
                    </li>
                </ul>
            </nav>
        </div>
    </header>
</template>

<style scoped></style>