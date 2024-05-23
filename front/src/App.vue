<script setup>
import { watch, ref } from 'vue'
import { RouterView, useRouter } from 'vue-router'
import { storeToRefs } from "pinia"
import headerNav from '@/views/header.vue'
import { useMemberStore } from '@/stores/jwt_token'

const headerKey = ref(0)
const router = useRouter()
const memberStore = useMemberStore()
const { isLogin } = storeToRefs(memberStore)
watch(isLogin, () => {
  if (isLogin.value == true) {
    router.push("/")
  }
  headerKey.value++;
})
</script>

<template>
  <link rel="stylesheet" type="text/css"
    href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css" />
  <headerNav :key="headerKey"></headerNav>
  <RouterView />

</template>

<style scoped>
* {
  font-family: 'Pretendard';
  margin: 0;
  padding: 0;
}

.header-style {
  background-color: #ffffff !important;
  /* 흰색 배경색 */
}
</style>
