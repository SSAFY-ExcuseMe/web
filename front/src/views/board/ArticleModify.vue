<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useMemberStore } from '@/stores/jwt_token';
import { stationAxios } from "@/util/http-commons";

const title = ref("")
const contents = ref("")

const router = useRouter();
const route = useRoute();
const articleno = route.params.articleno
const memberStore = useMemberStore();
const { confirmLogin } = memberStore;

onMounted(async () => {
    const isLogin = await confirmLogin();
    if (isLogin) {
        getArticle();
    } else {
        alert("잘못된 접근입니다.")
        router.push('/');
    }
})

const getArticle = () => {
    const axiosInstance = stationAxios();
    axiosInstance.get("/board/" + articleno)
        .then((response) => {
            title.value = response.data.title;
            contents.value = response.data.contents;
        })
        .catch((error) => {
            console.error(error);
        })
}

const modify = () => {
    const axiosInstance = stationAxios();
    const data = {
        title: title.value,
        contents: contents.value
    }
    axiosInstance.put("/board/" + articleno, data)
        .then((response) => {
            alert("후기 수정이 완료 되었습니다")
            router.push(`/aritcle/view/${articleno}`)

        })
        .catch((error) => {
            console.error(error);
        })
}
const cancel = () => {
    const confirmOut = confirm("이 페이지에서 나가시겠습니까? \n 지금까지 작성된 글은 저장되지 않습니다.")
    if (confirmOut) {
        // router.push(`/aritcle/view/${articleno}`)
        router.go(-1)
    }
}
</script>

<template>
    <main>
        <h3 style="margin-bottom: 2rem; font-weight: 600;">여행 후기 수정</h3>
        <form>
            <div class="mb-3">
                <input type="email" class="form-control" id="exampleFormControlInput1" placeholder="제목" v-model="title">
            </div>
            <div class="mb-3">
                <textarea class="form-control" id="exampleFormControlTextarea1" rows="10" v-model="contents"></textarea>
            </div>
        </form>
        <div style="display:flex; gap:0.5rem;">
            <button type="button" class="btn btn-primary" @click="modify">수정</button>
            <button type="button" class="btn btn-outline-primary" @click="cancel">취소</button>
        </div>

    </main>
</template>

<style scoped>
main {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 4rem;
}

form {
    width: 80vh;
    margin: 2.5rem 0rem 4rem 0rem;
}
</style>