<script setup>
import { computed, ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import { storeToRefs } from "pinia"
import { useMapStore } from '@/stores/map'
import { stationAxios } from "@/util/http-commons";
import { useMemberStore } from '@/stores/jwt_token';
import { Modal } from 'bootstrap';

const mapStore = useMapStore()
const memberStore = useMemberStore();
const { route } = storeToRefs(mapStore)
const { routeRemove } = mapStore
const { confirmLogin } = memberStore;
const router = useRouter();
const dist = ref();
const diffDist = ref([]);
const title = ref("");
const routeLen = computed(() => {
    return route.value.length;
})

watch(routeLen, () => {
    if (routeLen.value > 1) {
        const { pathRoute: pathRoute, pathDist: pathDist } = findShortestPath(route.value);
        route.value = pathRoute;
        diffDist.value = pathDist;
        dist.value = computed(() => {
            let sum = 0; // 여기를 `let`으로 변경
            pathDist.forEach(p => {
                sum += Number(p);
            })
            return Math.round(sum, -1);
        })
    }

})

const calculateDistance = (pos1x, pos1y, pos2x, pos2y) => {
    const R = 6371e3; // 지구의 반지름 (미터)
    const φ1 = pos1x * Math.PI / 180; // 위도를 라디안으로 변환
    const φ2 = pos2x * Math.PI / 180;
    const Δφ = (pos2x - pos1x) * Math.PI / 180; // 위도 간의 차이를 라디안으로 변환
    const Δλ = (pos2y - pos1y) * Math.PI / 180; // 경도 간의 차이를 라디안으로 변환

    const a = Math.sin(Δφ / 2) * Math.sin(Δφ / 2) +
        Math.cos(φ1) * Math.cos(φ2) *
        Math.sin(Δλ / 2) * Math.sin(Δλ / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    const d = R * c; // 두 지점 간의 거리 (미터)
    return d;
}

const findShortestPath = (stores) => {
    const n = stores.length;
    const memo = new Array(1 << n).fill(null).map(() => new Array(n).fill(null));

    function tsp(mask, current) {
        if (mask === (1 << n) - 1) {
            return calculateDistance(stores[current].map_x, stores[current].map_y, stores[0].map_x, stores[0].map_y);
        }
        if (memo[mask][current] !== null) {
            return memo[mask][current];
        }

        let minDistance = Number.MAX_SAFE_INTEGER;
        for (let next = 0; next < n; next++) {
            if ((mask & (1 << next)) === 0) {
                const distance = calculateDistance(stores[current].map_x, stores[current].map_y, stores[next].map_x, stores[next].map_y) +
                    tsp(mask | (1 << next), next);
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }

        memo[mask][current] = minDistance;
        return minDistance;
    }
    const shortestDistance = (tsp(1, 0) / 1000).toFixed(2) / 2; // 0번째 지점부터 시작

    // 최단 경로 추적
    let current = 0;
    let mask = 1;
    const pathRoute = [stores[0]];
    const pathDist = [0];
    while (pathRoute.length < n) {
        let nextStoreIndex = -1;
        let minDistance = Number.MAX_SAFE_INTEGER;
        for (let next = 0; next < n; next++) {
            if ((mask & (1 << next)) === 0) {
                const distance = calculateDistance(stores[current].map_x, stores[current].map_y, stores[next].map_x, stores[next].map_y);
                if (distance < minDistance) {
                    minDistance = distance;
                    nextStoreIndex = next;
                }
            }
        }
        pathDist.push((minDistance / 1000).toFixed(2));
        pathRoute.push(stores[nextStoreIndex]);
        current = nextStoreIndex;
        mask |= (1 << nextStoreIndex);
    }

    return { pathRoute: pathRoute, pathDist: pathDist };
}

const openModal = async () => {
    const isLogin = confirmLogin();
    if (route.value.length === 0) {
        alert("일정이 비어 있습니다. 먼저 여행지를 추가해주세요.");
    } else if (!isLogin) {
        alert("로그인이 필요합니다.")
        router.push("/login");

    } else {
        const modalElement = document.getElementById('exampleModal');
        const exampleModal = new Modal(modalElement);
        exampleModal.show();
    }
}

const save = async () => {
    const list = [];
    route.value.forEach(r => {
        list.push(r.id)
    })
    const data = {
        title: title.value,
        len: routeLen.value,
        list: list
    }

    const axiosInstance = stationAxios();
    await axiosInstance.post("/route", data)
        .then((response) => {
            alert("여행지 등록이 완료되었습니다!")
            route.value = [];
            router.go(0)
        })
        .catch((error) => {
            console.error(error)
        })
}
</script>
<template>
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5>원하는 여행지 경로 이름을 입력해주세요.</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">

                    <input type="text" class="form-control" v-model="title">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                    <button type="button" class="btn btn-primary" @click="save">저장하기</button>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div>
            <h5 class="offcanvas-title" id="offcanvasScrollingLabel">일정</h5>
            <div class="list" style="overflow-y: auto; max-height: 650px; overflow-x: hidden;" id="result">

                <div class="items" v-for="(r, index) in route" :key="r.id">
                    <span class="badge rounded-pill text-bg-primary">{{ index + 1 }}</span>
                    <div>
                        <p class="title-font">{{ r.name }}</p>
                        <p class="small-font" v-show="diffDist[index] != 0">{{ diffDist[index] }}km</p>
                    </div>

                    <button id="deleteBtn" class="btn btn-light btn-sm" @click="routeRemove(r)">-</button>

                </div>

            </div>
        </div>
        <div>
            <p style="font-size: 1rem; margin-bottom: 0.5rem; font-weight: 600;">총 거리 : {{ dist }} km</p>
            <button type="button" class="btn btn-primary" style="width:100% !important" @click="openModal">
                일정 저장
            </button>
        </div>


        <!-- Modal -->

    </div>


</template>

<style scoped>
* {
    font-family: 'Pretendard';
    padding: 0;
    margin: 0;
}

.offcanvas-title {
    margin-bottom: 1rem;
}


.container {
    display: flex;
    flex-direction: column;
    height: 100%;
    width: 100%;
    justify-content: space-between;
}

.list {
    display: flex;
    flex-direction: column;
}

.items {
    display: flex;
    align-items: baseline;
    padding: 0.5rem;
    gap: 1rem;
}

.title-font {
    font-size: 100%;
}

.small-font {
    font-size: 70%;
    color: #D4D4D4;
}
</style>
<style>
.modal-backdrop {

    z-index: -1 !important;

}
</style>
