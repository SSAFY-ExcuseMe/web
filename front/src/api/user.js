import { localAxios } from "@/util/http-commons";
import axios from 'axios'

const local = localAxios();

async function userConfirm(data, success, fail) {
  await axios.post(`http://localhost:8080/user/login`,data).then(success).catch(fail);
}

async function findById(userId, success, fail) {
  local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await local.get(`http://localhost:8080/user`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`/user/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await local.get(`/user/logout/${userid}`).then(success).catch(fail);
}

export { userConfirm, findById, tokenRegeneration, logout };
