<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter();
const moveToMap = () => {
    router.push("/map")
}
const moveToBoard = () => {
    router.push("/board/list")
}
</script>

<template>
    <link rel="stylesheet" type="text/css"
        href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css" />
    <main>
        <div style="width:100%; display:flex; flex-direction: column; align-items: center;" data-aos="fade">
            <div class='container-1'>
                <div class='container-1-div'>
                    <div class='container-1-left'>
                        <div class='container-1-left-p'>
                            <p class='container-1-left-bold'>대한민국에 숨겨진<br> 여행지를 찾아보세요.</p>
                            <p class='container-1-left-light'>여행지 검색부터 일정 조정까지 <span
                                    style="font-weight: 600;">저기요</span>에서
                                해결하세요</p>
                        </div>
                        <div class='container-1-buttons'>
                            <a @click="moveToMap" class='btn-1' id="mapBtn">관광지 찾으러가기 ></a>
                            <a @click="moveToBoard" class='btn-2'>후기 보러 가기 ></a>
                        </div>
                    </div>
                    <img class="container-1-img" src='@/assets/main1.png'>
                </div>
            </div>
        </div>

        <div class='container-3' data-aos="fade-up" data-aos-anchor-placement="top-bottom">
            <div class='container-3-div'>
                <img src="@/assets/nav_logo.png">
                <p class='explain-light'><span class='explain-bold'>‘저기요 EXCUSE ME’</span>는<br>
                    마치 여행지 현지인들에게 관광지 안내를 받는 것처럼<br>
                    숨겨진 관광지에 대한 정보를 얻을 수 있는 플랫폼<br>
                    이라는 뜻을 가지고 있습니다.</p>
            </div>

        </div>
        <div class='container-2'>
            <div class='explain-container1-div' data-aos="fade-up" data-aos-anchor-placement="top-bottom">
                <div class='explain-container1'>
                    <div data-aos="fade-up" class='explain-text'>
                        <div class='explain-bold'>
                            <p>어느 지역으로 여행을 가시나요?</p>
                            <p>지역 기반으로 관광지를 추천해드려요</p>
                        </div>
                        <div class='explain-light'>
                            <p>여행을 계획 중인 지역을 입력해주세요</p>
                            <p>편하게 이동 시간을 계획할 수 있도록</p>
                            <p>지도와 함께 관광지에 대한 정보를 제공하고 있어요</p>
                        </div>
                    </div>
                    <img class="emo" data-aos="fade-down" src='@/assets/main2.png'>
                </div>
            </div>
            <div class='explain-container1-div' data-aos="fade-up" data-aos-anchor-placement="top-bottom">
                <div class='explain-container1'>
                    <div data-aos="fade-up" data-aos-anchor-placement="top-bottom" class='explain-text'>
                        <div class='explain-bold'>
                            <p>관광지에 대한 후기를 바로 확인할 수 있어요</p>
                        </div>
                        <div class='explain-light'>
                            <p>관광지에 대한 평가가 궁금하신가요?</p>
                            <p>먼저 여행을 다녀온 사람들의 후기를 바로 확인해보세요 !</p>
                        </div>
                    </div>
                    <img class="emo" data-aos="fade-down" src='@/assets/main3.png'>
                </div>

            </div>
        </div>
        <div class='container-4'>
            <div class='where-container'>
                <div class='explain-bold' data-aos="fade-up" data-aos-anchor-placement="top-bottom">
                    <p>어디로 떠나볼까요?</p>
                </div>
                <div class="card-container">
                    <div class="where-card" data-aos="fade-up" data-aos-anchor-placement="top-bottom">
                        <img src="@/assets/seoul.jpg">
                        <div class="card-title">
                            <span class="english">SEOUL</span>
                            <span class="korean">서울</span>
                        </div>
                    </div>
                    <div class="where-card" data-aos="fade-up" data-aos-anchor-placement="top-bottom">
                        <img src="@/assets/busan.jpg">
                        <div class="card-title">
                            <span class="english">BUSAN</span>
                            <span class="korean">부산</span>
                        </div>
                    </div>
                    <div class="where-card" data-aos="fade-up" data-aos-anchor-placement="top-bottom">
                        <img src="@/assets/gwangju.jpg">
                        <div class="card-title">
                            <span class="english">GWANGJU</span>
                            <span class="korean">광주</span>
                        </div>
                    </div>
                    <div class="where-card" data-aos="fade-up" data-aos-anchor-placement="top-bottom">
                        <img src="@/assets/jeju.jpg">
                        <div class="card-title">
                            <span class="english">JEJU</span>
                            <span class="korean">제주</span>
                        </div>
                    </div>
                    <div class="where-card" data-aos="fade-up" data-aos-anchor-placement="top-bottom">
                        <img src="@/assets/gangwon.jpg">
                        <div class="card-title">
                            <span class="english">GANGWON</span>
                            <span class="korean">강원</span>
                        </div>
                    </div>
                    <div class="where-card" data-aos="fade-up" data-aos-anchor-placement="top-bottom">
                        <img src="@/assets/seoul.jpg">
                        <div class="card-title">
                            <span class="english">INCHEON</span>
                            <span class="korean">인천</span>
                        </div>
                    </div>
                    <div class="where-card" data-aos="fade-up" data-aos-anchor-placement="top-bottom">
                        <img src="@/assets/daejeon.jpg">
                        <div class="card-title">
                            <span class="english">DAEJEON</span>
                            <span class="korean">대전</span>
                        </div>
                    </div>
                    <div class="where-card" data-aos="fade-up" data-aos-anchor-placement="top-bottom">
                        <img src="@/assets/geoje.jpg">
                        <div class="card-title">
                            <span class="english">GEOJE</span>
                            <span class="korean">거제</span>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </main>
    <footer>
        <div class="navbar navbar-expand navbar-dark bg-white container-fluid justify-content-center p-3">
            <div class="row">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link text-primary fw-bolder fs-4 text-center mt-0" href="#">Excuse Me</a>
                    </li>
                    <li class="nav-item ">
                        <a class="nav-link text-dark text-center mt-2" href="#">개인정보처리방침</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-dark text-center mt-2" href="#">이용약관</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-muted text-center mt-2" href="#">&copy;SSAFY 광주 4반 신민경 김지원</a>
                    </li>
                </ul>
            </div>
        </div>
    </footer>
</template>
<style scoped>
* {
    font-family: 'Pretendard';
    margin: 0;
    padding: 0;
}

.container-4 {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.where-container {
    display: flex;
    flex-direction: column;
    width: 70vw;
    align-items: center;
    padding: 4rem;
}

.card-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    padding: 4rem;
    gap: 2rem;
}

.card-title {
    display: flex;
    flex-direction: column;
}

.english {
    font-size: 20px;
    font-weight: 600;
}

.korean {
    font-size: 20px;
    font-weight: 200;
    color: #7D7D7D;
}

.where-card img {
    width: 200px;
    height: 240px;
    border-radius: 10px;
}

.container-fluid {
    max-width: 1280px;
    display: flex;

}

.container-1 {
    width: 100vw;
    max-width: 1280px;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.container-1-div {
    display: flex;
    width: 100%;
    justify-content: space-between;
    padding: 100px 48px 100px 48px;
}

.container-1-left {
    display: flex;
    flex-direction: column;
    gap: 25px;
    justify-content: center;
}

.explain-text {
    display: flex;
    flex-direction: column;
    gap: 32px;

}

.container-1-left-bold {
    font-size: min(4vw, 42px);
    word-spacing: -8px;
    font-weight: 600;
}

.container-1-left-light {
    font-size: min(3vw, 28px);
    font-weight: 200;
    color: #575757;

}

.container-1-buttons {
    display: flex;
    margin-top: 24px;
    gap: 12px;
}

.container-1-buttons a {
    outline-style: none;
    padding: 2% 4% 2% 4%;
    font-size: min(2vw, 100%);
    text-decoration-line: none;
}

.btn-1 {
    border: none;
    background-color: black;
    border-color: black;
    color: white;
}

.btn-2 {
    background-color: white;
    border: 1px black solid;
    color: black;
}

.container-1-img {
    max-width: 474px;
    max-height: 400px;
    width: 40vw;
    height: 65%;
}

.emo {
    width: 25vw;
    max-width: 300px;
}

.explain-bold {
    font-size: min(3vw, 32px);
    font-weight: 600;
}

.explain-light {
    font-size: min(3vw, 28px);
    font-weight: 200;
}

.explain-container1-div {
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: white;
}

.explain-container1 {

    display: flex;
    align-items: center;
    justify-content: center;
    gap: 4vw;
    width: 80%;
    margin: 48px 0px 48px 0px;
}

.explain-container1-div {
    background-color: #FAFAFA;
}

.container-3,
.container-2 {
    background-color: #FAFAFA;
    padding-bottom: 2rem;
}

.container-3 {
    margin-bottom: 120px;
}

.container-3-div {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 120px 24px;
    gap: 2rem;
}

.container-3 img {
    width: 40vw;
    max-width: 300px;
}

.container-3-div p {
    text-align: center;
}
</style>