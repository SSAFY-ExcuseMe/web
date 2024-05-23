import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { LoadingPlugin } from 'vue-loading-overlay';
import App from './App.vue'
import router from './router'
import AOS from 'aos';
import "aos/dist/aos.css";

import "bootstrap"
import "bootstrap/dist/css/bootstrap.min.css"
import 'bootstrap/dist/js/bootstrap.js'
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

// Vue 3 애플리케이션 인스턴스 생성
const app = createApp(App)

// Pinia 상태 관리 라이브러리 사용
const pinia = createPinia()
app.use(pinia)

// Vue Router 사용
app.use(router)

// AOS 초기화 - onMounted 라이프사이클 훅에서 수행
app.mount('#app')

// AOS 초기화를 위해 애플리케이션 인스턴스에 전역 설정 추가
app.config.globalProperties.$aos = AOS.init();
