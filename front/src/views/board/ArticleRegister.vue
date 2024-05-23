<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { stationAxios } from "@/util/http-commons";

const title = ref("")
const contents = ref("")
const router = useRouter();

const register = () => {
    const data = {
        title: title.value,
        contents: contents.value
    }
    const axiosInstance = stationAxios();
    axiosInstance.post("/board", data)
        .then((response) => {
            alert("글 등록이 완료 되었습니다.")
            router.push("/board/list")
        })
}
</script>

<template>
    <main>
        <h3 style="margin-bottom: 2rem; font-weight: 600;">여행 후기 작성</h3>
        <form>
            <div class="mb-3">
                <input type="email" class="form-control" id="exampleFormControlInput1" placeholder="제목" v-model="title">
            </div>
            <div class="mb-3">
                <textarea class="form-control" id="exampleFormControlTextarea1" rows="10" v-model="contents"></textarea>
            </div>
        </form>
        <div style="display:flex; gap:0.5rem;">
            <button type="button" class="btn btn-primary" @click="register">등록</button>
            <button type="button" class="btn btn-outline-primary">취소</button>
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