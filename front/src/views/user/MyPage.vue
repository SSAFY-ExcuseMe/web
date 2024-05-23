<script setup>
import { onMounted, ref } from 'vue';
import { RouterLink, RouterView, useRouter } from 'vue-router'
import { storeToRefs } from "pinia";
import { useMemberStore } from '@/stores/jwt_token'


const router = useRouter();
const memberStore = useMemberStore()
const { isLogin } = storeToRefs(memberStore)


onMounted(() => {
    if (isLogin == false) {
        alert("잘못된 접근입니다.")
        router.push("/")
    }
    router.push("/mypage/myTrip")
})

</script>

<template>
    <ul class="nav nav-underline justify-content-center border-bottom mypage-nav">
        <li class="nav-item">
            <RouterLink :to="{ name: 'myTrip' }" class="nav-link list" aria-current="page">나의 여행</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink :to="{ name: 'myReview' }" class="nav-link list" aria-current="page">나의 후기</RouterLink>
        </li>
        <li class="nav-item">
            <RouterLink :to="{ name: 'myInfo' }" class="nav-link list" aria-current="page">회원 정보 수정</RouterLink>
        </li>
    </ul>
    <RouterView />
</template>

<style scoped>
.list {
    margin: 2rem;
    font-size: 1.3rem;
}
</style>