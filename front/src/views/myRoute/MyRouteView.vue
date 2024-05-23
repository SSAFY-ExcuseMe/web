<script setup>
import { stationAxios } from "@/util/http-commons";
import { useRouter } from 'vue-router';
import { defineProps, watch, ref, onMounted, nextTick } from 'vue';

const tour = ref();
const myRoute = ref();
const props = defineProps({
    select: {
        type: Object,
        required: true
    }
});
const router = useRouter();
watch(() => props.select, async (newVal) => {
    tour.value = newVal;
    const axiosInstance = stationAxios();
    axiosInstance.get("/route/" + tour.value.id)
        .then(async (response) => {
            myRoute.value = response.data;
            await nextTick();
            handleResize();
        })
        .catch((error) => {
            console.error(error);
        })
});
const deleteRoute = (route) => {
    const check = confirm("경로 삭제를 진행하시겠습니까?")
    if (check) {
        const axiosInstance = stationAxios();
        axiosInstance.delete("/route/" + route.id)
            .then((response) => {
                alert("삭제가 완료 되었습니다.")
                router.go(0);
            })
            .catch((error) => {
                console.error(error)
            })
    }
}
onMounted(() => {
    window.addEventListener('resize', handleResize)
})

const handleResize = () => {
    if (!document.querySelector(".nav") || !document.querySelector("#header")) return;
    const header = document.querySelector("#header").offsetHeight;
    const nav = document.querySelector(".nav").offsetHeight;

    const container = document.querySelector(".container");
    const main = document.querySelector(".main");
    main.style.maxHeight = `calc(100vh - ${header}px - ${nav}px - 2rem)`;
    if (container) {
        const w = container.offsetWidth;
        document.querySelectorAll(".tour-container").forEach(tourContainer => {
            tourContainer.style.width = `calc(${w}px * 0.9)`;
        });
    }
}
</script>

<template>
    <main class="main">
        <div class="container" v-if="myRoute && myRoute.routeDto">
            <div>
                <h3 style="font-weight: 600;">{{ myRoute.routeDto.title }}</h3>
                <span style="font-size:1.2rem; color:#DADADA">{{ myRoute.routeDto.len }} km</span>
                <div>
                    <div v-for="(tour, index) in myRoute.tourList" class="tour-container">
                        <span class="alert alert-light">{{ index + 1 }}</span>
                        <img :src="tour.img" width="73" height="70" v-show="tour.img != ''">
                        <img src="https://i.ibb.co/PTYxX92/alt.png" width="73" height="70" v-show="tour.img == ''">
                        <div>
                            <p style="font-weight: 600; margin-bottom: 0.7rem; font-size: 1.1rem;">{{ tour.name }}</p>
                            <p v-show="tour.tel != ''">📞{{ tour.tel }}</p>
                            <p v-show="tour.addr != ''">🚩{{ tour.addr }}</p>
                        </div>
                    </div>
                </div>
            </div>
            <button type="button" class="btn btn-outline-danger p-8" @click="deleteRoute(myRoute.routeDto)">경로
                삭제</button>
        </div>
    </main>
</template>

<style scoped>
* {
    font-family: 'Pretendard';
    padding: 0;
    margin: 0;
}

main {
    overflow-y: auto;
}

.container {
    padding: 3rem;
    display: flex;
    flex-direction: column;
    align-items: flex-end;
}

.tour-container {
    padding: 1rem 0rem;
    display: flex;
    gap: 1rem;
    align-items: center;
    margin-top: 24px;
}

.button-container {
    display: flex;
    flex-direction: column;
}

.tour-container span {
    width: 40px;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.tour-container img {
    border-radius: 5px;
    width: 100px;
    height: 100px;
}
</style>