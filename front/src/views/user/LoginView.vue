<script setup>
import VueCookies from 'vue-cookies';
import { ref, onMounted } from 'vue'
import { storeToRefs } from "pinia"
import { useRouter } from 'vue-router'
import { useMemberStore } from '@/stores/jwt_token'


const router = useRouter();
const memberStore = useMemberStore()

const { isLogin, isLoginError } = storeToRefs(memberStore)
const { userLogin, getUserInfo } = memberStore
const id = ref("");
const pw = ref("");
const saveId = ref(false);
const submit = () => {
    if (id.value == "") {
        alert("아이디를 입력해주세요")
    } else if (pw.value == "") {
        alert("비밀번호를 입력해주세요")
    } else {
        login()
    }
}
const login = async () => {
    const data = {
        userId: id.value,
        userPwd: pw.value
    }
    await userLogin(data)
    let token = sessionStorage.getItem("accessToken")
    if (isLogin.value) {
        getUserInfo(token)
        router.replace("/")
    }
}
const moveToSignup = () => {
    router.push("/signup")
}
onMounted(() => {
    const cookie = VueCookies.get("saveId");
    if (cookie != null) {
        id.value = cookie;
    }
})
</script>

<template>
    <div class="container centered">
        <div class="row justify-content-center">
            <div class="col-sm-10 col-md-8 col-lg-6 col-xl-5">
                <a href="">
                    <div class="row justify-content-center mt-5">
                        <img src="@/assets/logo.png" class="img-thumnail" alt="로고" style="width: 200px; height: auto;">
                    </div>
                </a>
                <div class="card shadow">
                    <div class="card-body">
                        <form>

                            <div class="form-group">
                                <label for="username"></label>
                                <input type="text" class="form-control " id="username" placeholder="🙎‍♂️ 아이디 입력"
                                    v-model="id" required>
                            </div>

                            <div class="form-group mb-3">
                                <label for="password"></label>
                                <input type="password" class="form-control " id="password" placeholder="🔑 비밀번호 입력"
                                    v-model="pw" required>
                            </div>

                            <div class="form-check mb-3">
                                <label class="form-check-label">
                                    <input class="form-check-input" type="checkbox" name="remember" v-model="saveId">
                                    아이디 저장
                                </label>
                            </div>

                            <div class="row justify-content-center ms-auto me-auto">
                                <button type="button" class="btn btn-primary" @click="submit">여행 떠나기!</button>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="row justify-content-center mt-4">
                    <ul class="list-group list-group-horizontal justify-content-end">
                        <a @click="moveToSignup" class="text-decoration-none text-muted small">회원가입</a>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped></style>