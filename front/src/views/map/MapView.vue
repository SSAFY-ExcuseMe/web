<script setup>
import { ref, onMounted, onUnmounted, watch } from 'vue'
import { storeToRefs } from "pinia"
import { useMapStore } from '@/stores/map'
import MapOffcanvasSearch from "./MapOffcanvasSearch.vue";
import MapOffcanvasSelect from "./MapOffcanvasSelect.vue";
import MapInfo from "./MapInfo.vue";

const { VITE_KAKAO_MAP_KEY } = import.meta.env
const mapStore = useMapStore()
const askTour = ref("");
const infoModal = ref(false)

//mapResult : offcanvas에 불리어진 여행 리스트들
//route : 저장하고자 하는 여행지 경로
const { routePush } = mapStore
const { mapResult } = storeToRefs(mapStore)

let mapContainer = ref(null)
let mapInstance = null
let markers = []
var overlay = null;
let selectedMarker = null;

//로딩될 때 맵 사이즈 조절(스크롤 안생기도록)
onMounted(() => {
    loadKaKaoMap(mapContainer.value)
    window.addEventListener('resize', handleResize)
})
onUnmounted(() => {
    window.addEventListener('resize', handleResize)
})


//카카오맵 로딩
const loadKaKaoMap = (container) => {
    const script = document.createElement('script')
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${VITE_KAKAO_MAP_KEY}&autoload=false`
    document.head.appendChild(script)
    script.onload = () => {
        window.kakao.maps.load(() => {

            const options = {
                center: new window.kakao.maps.LatLng(33.450701, 126.570667),
                level: 3,//지도 확대 레벨
                maxLevel: 10 //지도 축소 제한 레벨
            }
            mapInstance = new window.kakao.maps.Map(container, options)//지도생성
            initMarker();
            resizeMapContainer()
        })
    }
}
watch(mapResult, () => {
    initMarker();
});

const initMarker = () => {
    if (!window.kakao || !window.kakao.maps) {
        console.error("카카오맵이 로드되지 않았습니다.");
        return;
    }

    var bounds = new kakao.maps.LatLngBounds();
    removeMarker();

    for (var i = 0; i < mapResult.value.length; i++) {
        var position = new kakao.maps.LatLng(mapResult.value[i].map_y, mapResult.value[i].map_x);
        addMarker(mapResult.value[i], position);
        bounds.extend(position);
    }

    if (mapInstance) {
        mapInstance.setBounds(bounds);
    }
}
const addMarker = (tour, position) => {

    var imageSrc = "https://i.ibb.co/W6zKbd9/marker.png" // 마커 이미지 url, 스프라이트 이미지를 씁니다
    var imageSize = new kakao.maps.Size(22, 24),  // 마커 이미지의 크기
        imgOptions = {
            // spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
            // spriteOrigin: new kakao.maps.Point(0, 46), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage
        });
    kakao.maps.event.addListener(marker, 'click', function () {

        if (selectedMarker != null) {
            closeOverlay();
        }
        selectedMarker = marker;
        displayInfowindow(marker, tour);
    });
    marker.setMap(mapInstance); // 지도 위에 마커를 표출합니다
    markers.push(marker);  // 배열에 생성된 마커를 추가합니다
}
const removeMarker = () => {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
    }
    markers = [];
}
//화면크기에 따른 offcanvas 크기 조절
const handleResize = () => {
    if (mapInstance) {
        resizeMapContainer()
    }
}
const resizeMapContainer = () => {
    // 화면 높이에서 헤더의 높이를 뺀 값으로 지도 컨테이너의 높이를 설정
    const headerHeight = document.querySelector('#header').offsetHeight
    const offcanvas = document.querySelector('#offcanvasScrolling')
    offcanvas.style.top = `${headerHeight}px`;
    mapContainer.value.style.height = `calc(100vh - ${headerHeight}px)`
}
const displayInfowindow = (marker, tour) => {
    var content = `
        <div class="wrap">
            <div class="info">
                <div class="title">${tour.name}
                    <div class="close" title="닫기"></div>
                </div>
                <div class="body">
                    
                    <div class="img">
                        ${tour.img ? `<img src="${tour.img}" width="73" height="70">` : `<img src="https://i.ibb.co/PTYxX92/alt.png" width="73" height="70">`}
                    </div>
                    <div class="desc">
                        <div class = "desc-info">
                            <div class="ellipsis">${tour.addr}</div>
                            <div class="jibun ellipsis">${tour.tel}</div>
                        </div>
                        <div class = "desc-info">
                            <a href='#' id = "showInfo" data-name="${tour.name}">AI 후기보기</a>
                            <button id = "addBtn" class="btn btn-light btn-sm">+</button>
                        </div>
                        
                    </div>
                </div>s
            </div>
        </div>`;
    setTimeout(() => {
        document.querySelector('.wrap .close').addEventListener('click', closeOverlay);
        document.getElementById("addBtn").addEventListener('click', () => addTour(tour));
        document.querySelector('#showInfo').addEventListener('click', (event) => {
            const name = event.target.dataset.name;
            openModal(name);
        });
    }, 0);
    overlay = new kakao.maps.CustomOverlay({
        content: content,
        map: mapInstance,
        position: marker.getPosition()
    });
    overlay.setMap(mapInstance);

}
const closeOverlay = () => {
    overlay.setMap(null);
}

const addTour = (tour) => {
    routePush(tour);
}
const moveMap = (tour) => {
    var pos = new kakao.maps.LatLng(tour.map_y, tour.map_x + 0.001)
    mapInstance.panTo(pos);
    mapInstance.setLevel(4);
}
const openModal = (title) => {
    askTour.value = title

    infoModal.value = true;
}
const closeModal = () => {
    infoModal.value = false;
}
</script>

<template>
    <div>
        <button class="btn btn-primary floatBtn" type="button" data-bs-toggle="offcanvas"
            data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling"><img src="@/assets/map.png"
                class="floatBtnImg"></button>
        <div class="offcanvas offcanvas-start offcanvas" data-bs-scroll="false" data-bs-backdrop="false" tabindex="-1"
            id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel">
            <div class="offcanvas-body row">
                <div class="col-7 border-end">
                    <MapOffcanvasSearch @moveTo="moveMap" />
                </div>
                <div class="col-5">
                    <div class="offcanvas-header">
                        <MapOffcanvasSelect />
                        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div ref="mapContainer" style="width:100%; height:100vh"></div>
    <MapInfo v-show="infoModal" @closeModal="closeModal" :askTour="askTour" :key="askTour"></MapInfo>


</template>


<style scoped>
* {
    font-family: 'Pretendard';
    margin: 0;
    padding: 0;
}

.floatBtn {
    position: absolute;
    margin-top: 50px;
    left: 50px;
    z-index: 10;
    font-weight: 600;
    width: 64px;
    height: 64px;
    border-radius: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.floatBtnImg {
    width: 100%;
}

.offcanvas {
    width: 40vw !important;
    min-width: 200px !important;
    max-width: 680px !important;
    position: absolute;
    z-index: 11;
}

.desc-info {
    display: flex;
    gap: 0.7rem;
}

.offcanvas-header {
    display: flex;
    align-items: flex-start;
    height: 100%;
}
</style>
<style>
/* 전역 스타일 정의 */
.wrap {
    position: absolute;
    left: 0;
    bottom: 40px;
    width: 288px;
    height: 132px;
    margin-left: -144px;
    text-align: left;
    overflow: hidden;
    font-size: 12px;
    font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;
    line-height: 1.5;
}

.wrap * {
    padding: 0;
    margin: 0;
}

.wrap .info {
    width: 286px;
    height: 120px;
    border-radius: 5px;
    border-bottom: 2px solid #ccc;
    border-right: 1px solid #ccc;
    overflow: hidden;
    background: #fff;
}

.wrap .info:nth-child(1) {
    border: 0;
    box-shadow: 0px 1px 2px #888;
}

.info .title {
    padding: 5px 0 0 10px;
    height: 30px;
    background: #eee;
    border-bottom: 1px solid #ddd;
    font-size: 18px;
    font-weight: bold;
}

.info .close {
    position: absolute;
    top: 10px;
    right: 10px;
    color: #888;
    width: 17px;
    height: 17px;
    background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png');
}

.info .close:hover {
    cursor: pointer;
}

.info .body {
    position: relative;
    overflow: hidden;
}

.info .desc {
    position: relative;
    margin: 13px 0 0 90px;
    height: 75px;
}

.desc-info {
    margin-bottom: 0.5rem;
}

.desc .ellipsis {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.desc .jibun {
    font-size: 11px;
    color: #888;
    margin-top: -2px;
}

.info .img {
    position: absolute;
    top: 6px;
    left: 5px;
    width: 73px;
    height: 71px;
    border: 1px solid #ddd;
    color: #888;
    overflow: hidden;
}

.info:after {
    content: '';
    position: absolute;
    margin-left: -12px;
    left: 50%;
    bottom: 0;
    width: 22px;
    height: 12px;
    background: url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')
}

.info .link {
    color: #5085BB;
}
</style>
