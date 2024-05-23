<script setup>
import { ref, watch, onMounted } from 'vue'
import { storeToRefs } from "pinia"
import { useMapStore } from '@/stores/map'
import axios from 'axios'
const regionSelect = ["서울특별시", "부산광역시", "대구광역시", "대전광역시", "인천광역시", "광주광역시", "울산광역시",
    "세종특별자치시", "경기도", "강원특별자치도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주특별자치도"];
const tourType = [
    { text: "관광지", value: "12" },
    { text: "문화시설", value: "14" },
    { text: "축제공연행사", value: "15" },
    { text: "여행코스", value: "25" },
    { text: "레포츠", value: "28" },
    { text: "숙박", value: "32" },
    { text: "쇼핑", value: "38" },
    { text: "음식점", value: "39" }
]
const region = ref("")
const type = ref("")
const keyword = ref("")

const page = ref(1);

const { VITE_URL } = import.meta.env
const mapStore = useMapStore()
const { routePush } = mapStore
const { mapResult } = storeToRefs(mapStore)


watch([region, type, keyword], () => {
    const params = {
        keyword: keyword.value,
        type: type.value,
        region: region.value,
        page: 1, // 페이지 번호 추가
    };

    const url = `${VITE_URL}/tour`;
    axios.get(url, { params })
        .then((response) => {
            mapResult.value = response.data.tourList
        })
        .catch((error) => {
            console.error(error);
        });
});
// Intersection Observer 초기화 및 설정
const initIntersectionObserver = () => {
    const observer = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting) {
            fetchTour(); // 스크롤이 끝에 도달하면 추가 데이터 로드
        }
    }, { threshold: 1.0 });

    const scrollAnchor = document.querySelector('.scroll-anchor');
    if (scrollAnchor) {
        observer.observe(scrollAnchor); // 관찰 대상 설정
    } else {
        console.error("Scroll anchor element not found.");
    }
};
const fetchTour = () => {
    const params = {
        keyword: encodeURIComponent(keyword.value),
        type: type.value,
        region: region.value,
        page: page.value, // 페이지 번호 추가
    };
    const url = `${VITE_URL}/tour`;
    axios.get(url, { params })
        .then((response) => {
            mapResult.value = [...mapResult.value, ...response.data.tourList]; // 결과를 추가
            page.value++; // 페이지 번호 증가
        })
        .catch((error) => {
            console.error(error);
        });
};
onMounted(() => {
    fetchTour(); // 컴포넌트 마운트 시 초기 데이터 로드
    initIntersectionObserver(); // Intersection Observer 설정
});
</script>

<template id="template">
    <div>
        <div class="offcanvas-header">
            <h5 class="offcanvas-title" id="offcanvasScrollingLabel">여행 계획 세우기</h5>
        </div>
        <form class="search" v-on:submit.prevent>
            <input class="search-input" type="text" placeholder="키워드 검색" v-model="keyword">
            <button class="search-btn"><img src="@/assets/search.png"></button>
        </form>
        <div class="offcanvas-select select-container">
            <select class="form-select" aria-label="Default select example" placeholder="지역" v-model="region">
                <option selected value="" disabled>지역</option>
                <option v-for="region in regionSelect" :key="region" :value="region">{{ region }}</option>
            </select>
            <select class="form-select" aria-label="Default select example" v-model="type">
                <option selected value="" disabled>관광타입</option>
                <option v-for="tour in tourType" :key="tour.value" :value="tour.value">{{ tour.text }}</option>
            </select>
        </div>
    </div>
    <div class="result" style="overflow-y: auto; max-height: 600px;" id="result">
        <!-- 여행지 결과를 여기에 표시 -->
        <div class="tourItem-container" v-for="result in mapResult" :key="result.id" @click="$emit('moveTo', result)">
            <img :src="result.img" v-if="result.img != ''">
            <img src="@/assets/alt.png" v-if="result.img == ''">
            <div class="tourItem">
                <p class="tour-title">{{ result.name }}</p>
                <p class="tour-addr">{{ result.addr }}</p>
            </div>
            <button @click=routePush(result) class="tour-btn">+</button>
        </div>
        <!-- 스크롤이 끝에 도달했을 때 감지할 요소 -->
        <div class="scroll-anchor"></div>
    </div>
</template>

<style scoped>
* {
    font-family: 'Pretendard';
    margin: 0;
    padding: 0;
}


.select-container {
    display: flex;
    gap: 0.5rem;
}

select option[value=""][disabled] {
    display: none !important;
}

.search {
    display: flex;
    align-items: center;
    height: 2.5rem;
    padding: 0.5rem;
    border: 1px solid #D4D4D4;
    border-radius: 20px;
    margin-bottom: 1rem;
}

.search-input {
    border: none;
    width: 90%;
}

.search-btn {
    border: none;
    width: 13%;
    background-color: white;
}

.search-btn img {
    width: 70%;
}

.result {
    overflow-y: auto;
    display: flex;
    flex-direction: column;
    gap: 0.8rem;
    margin-top: 1.4rem;
    /* 스크롤 가능하도록 설정 */
    /*max-height: 400px;*/
    /* 최대 높이 설정 */
}

.tourItem-container {
    display: flex;
    align-items: center;
    gap: 0.7rem;
    padding: 0.3rem;
}

.tourItem-container img {
    width: 36%;
    aspect-ratio: 1/1;
    border-radius: 10px;
}

.tour-title {
    font-size: 1rem;
    font-weight: 600;
}

.tour-addr {
    font-size: 0.8rem;
    color: #777777;
}

.tour-btn {
    border: none;
    padding: 0.2rem;
    border-radius: 5px;
}
</style>