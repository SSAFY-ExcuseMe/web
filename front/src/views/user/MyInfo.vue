<script setup>
import { ref, watch, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { storeToRefs } from "pinia";
import { useMemberStore } from '@/stores/jwt_token'
import { stationAxios } from "@/util/http-commons";
import MyInfoResignModal from './MyInfoResignModal.vue'
import axios from 'axios'

const router = useRouter();
const resignModal = ref(false)
const memberStore = useMemberStore()
const { VITE_URL } = import.meta.env
const resign = () => {
    resignModal.value = true;
}
const resignExecute = () => {
    resignModal.value = false;


    const axiosInstance = stationAxios();
    axiosInstance.delete("/user/delete")
        .then((response) => {
            sessionStorage.removeItem("accessToken")
            sessionStorage.removeItem("refreshToken")
            router.push("/")
        })
        .catch((error) => {
            console.error(error)
        })

}

const email = ref("");
const id = ref("");
const name = ref("");
const phoneNumber = ref("");
const password = ref('');
const checkPW = ref("");
const checkPWClass = ref("form-control mb-2");

onMounted(() => {
    const axiosInstance = stationAxios();
    axiosInstance.get("/user")
        .then((response) => {
            const userInfo = response.data.userInfo;
            email.value = userInfo.email;
            id.value = userInfo.userId;
            name.value = userInfo.userName;
            password.value = userInfo.userPwd;
            phoneNumber.value = userInfo.phone;
        })
        .catch((error) => {
            console.error(error)
        })
})
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
    } else if (password.value != checkPW.value) {
        alert("비밀번호 확인을 확인해주세요")
    } else {
        modify();
    }
}

const modify = () => {
    const token = sessionStorage.getItem("accessToken")
    const url = VITE_URL + "/user/update"

    const data = {
        "userId": id.value,
        "userPwd": password.value,
        "userName": name.value,
        "phone": phoneNumber.value,
        "email": email.value
    }
    axios.post(url, data, {
        headers: { "Authorization": token }
    }).then((response) => {
        alert("회원 정보 수정이 완료되었습니다.")
        router.push("/")
    }).catch((error) => {
        console.error(error)
    })

}
</script>

<template>
    <div class="container">
        <div class="border shadow rounded p-3 col-sm-6 col-md-6 col-lg-4 container centered mt-3 mb-3">
            <div class="justify-content-center">
                <label for="inputUsername" class="form-label">아이디</label> <span class="text-warning">*</span>
                <input type="id" class="form-control mb-2" :placeholder="id" disabled>
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
        <div class="p-3 col-sm-6 col-md-6 col-lg-4 container centered">
            <a @click="resign" href="#" data-bs-toggle="modal" data-bs-target="#exampleModal" class="resignBtn">탈퇴하기
                ></a>
            <!-- 모달 -->
            <MyInfoResignModal @resignExecute="resignExecute" />

        </div>
        <div class="rounded-top p-0 col-sm-6 col-md-6 col-lg-4 container centered mt-5">
            <div class="row justify-content-center ms-auto me-auto">
                <button type="button" class="btn btn-primary" @click="submit">완료</button>
            </div>
        </div>

    </div>
</template>

<style scoped>
.resignBtn {
    text-decoration: none;
    color: #9B9B9B;
}
</style>