<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useMemberStore } from '@/stores/jwt_token';

const route = useRoute();
const router = useRouter();
const memberStore = useMemberStore();
const { confirmLogin } = memberStore;

onMounted(async () => {
    const isLogin = await confirmLogin();
    if (!isLogin) {
        alert("로그인이 필요합니다.")
        router.push('/login');
    } else if (route.query.mypage) {
        router.push("/aritcle/view/" + route.params.articleno)
    } else {
        router.push("/board/list")
    }
})

</script>

<template>
    <div>
        <RouterView></RouterView>
    </div>
</template>

<style scoped>
* {
    font-family: 'Pretendard';
    margin: 0;
    padding: 0;
}
</style>