<script setup>
import { ref, watch } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
const router = useRouter();
const url = ""
const email = ref('');
const id = ref('');
const name = ref('');
const phoneNumber = ref('');
const password = ref('');
const checkPW = ref("");
const checkPWClass = ref("form-control mb-2");

watch(phoneNumber, (newVal) => {
    phoneNumber.value = newVal.replace(/\D/g, '').replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
});
watch(checkPW, (newVal) => {
    if (checkPW.value != null && checkPW.value != password.value) {
        checkPWClass.value = "form-control is-invalid mb-2";
    } else {
        checkPWClass.value = "form-control mb-2";
    }
})
const signup = () => {
    const data = {
        userId: id.value,
        userName: name.value,
        userPwd: password.value,
        email: email.value,
        phone: phoneNumber.value,
        role: "user"
    }
    axios.post("http://localhost:8080/user/join", data)
        .then((response) => {
            alert("회원가입이 완료 되었습니다 !");
        })
        .catch((error) => {
            alert("에러 발생 !");
        });
}
const submit = () => {
    if (id.value == "") {
        alert("아이디를 입력해주세요")
    } else if (name.value == "") {
        alert("이름을 입력해주세요")
    } else if (password.value == "") {
        alert("비밀번호를 입력해주세요")
    } else if (email.value == "") {
        alert("이메일을 입력해주세요")
    } else if (phoneNumber.value == "") {
        alert("휴대번호를 입력해주세요")
    } else {
        signup();
        router.push("/")
    }
}

</script>

<template>
    <div class="rounded-top p-0 col-sm-6 col-md-6 col-lg-4 container centered mt-5">
        <a class="text-decoration-none  fw-bolder fs-3 text-start mt-0 row justify-content-center" href="#">회원가입</a>
    </div>
    <form id="userInfo">
        <div class="border shadow rounded p-3 col-sm-6 col-md-6 col-lg-4 container centered mt-3 mb-3">
            <div class="justify-content-center">
                <label for="inputUsername" class="form-label">아이디</label> <span class="text-warning">*</span>
                <input type="id" class="form-control mb-2" placeholder="아이디 입력" v-model=id required>
            </div>
            <div class="justify-content-center">
                <label for="inputPassword" class="form-label">비밀번호</label> <span class="text-warning">*</span>
                <input type="password" class="form-control mb-2" placeholder="비밀번호 입력" v-model=password required>
            </div>
            <div class="justify-content-center">
                <label for="inputPassword2" class="form-label">비밀번호 확인</label> <span class="text-warning">*</span>
                <input type="password" :class="checkPWClass" placeholder="비밀번호 재입력" v-model.lazy="checkPW" required>
            </div>
        </div>
        <div class="border shadow rounded p-3 col-sm-6 col-md-6 col-lg-4 container centered">
            <div class="justify-content-center">
                <label for="name" class="form-label">이름</label> <span class="text-warning">*</span>
                <input type="text" class="form-control mb-2" placeholder="성 이름" v-model=name required>
            </div>
            <div class="justify-content-center">
                <label for="inputEmail" class="form-label">이메일</label> <span class="text-warning">*</span>
                <input type="text" class="form-control mb-2" placeholder="user@domain.com" v-model=email required>
            </div>
            <div class="justify-content-center">
                <label for="inputEmail" class="form-label">휴대번호</label> <span class="text-warning">*</span>
                <input type="tel" class="form-control mb-2" placeholder="010-1234-5678" v-model="phoneNumber" required>
            </div>
        </div>
        <div class="rounded-top p-0 col-sm-6 col-md-6 col-lg-4 container centered mt-5">
            <div class="row justify-content-center ms-auto me-auto">
                <button type="button" class="btn btn-primary" @click="submit">가입하기</button>
            </div>
        </div>
    </form>
</template>

<style scoped></style>