<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router'
import { stationAxios } from "@/util/http-commons";

const router = useRouter();

const pageNation = ref(5);
const boardList = ref([]);
const nowPage = ref(1);

onMounted(() => {
    getList();
})
const getList = () => {
    const axiosInstance = stationAxios();
    axiosInstance.get("/board?page=" + nowPage.value)
        .then((response) => {
            boardList.value = response.data.tourList.articles
            pageNation.value = response.data.tourList.totalPageCount;
        })
        .catch((error) => {
            console.error(error)
        })
}
const movePage = (index) => {
    nowPage.value = index;
    getList();
}
const moveToWrite = () => {
    router.push("/article/register")
}
const moveArticle = (board) => {
    router.push("/aritcle/view/" + board.id)
}
</script>

<template>
    <main>
        <h3 style="font-weight: 600;">여행지 후기 게시판</h3>
        <div class=" upper-container">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th scope="col">제목</th>
                        <th scope="col">작성일</th>
                        <th scope="col">좋아요</th>
                        <th scope="col">조회수</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="board in boardList" :key="board.id" @click="moveArticle(board)">
                        <td>{{ board.title }}</td>
                        <td>{{ board.created_date }}</td>
                        <td>{{ board.like }}</td>
                        <td>{{ board.view }}</td>
                    </tr>

                </tbody>
            </table>
            <button type="button" class="btn btn-outline-primary" @click="moveToWrite">글쓰기</button>
        </div>
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <li class="page-item" v-for="i in pageNation"><a class="page-link" @click="movePage(i)">{{ i }}</a>
                </li>
                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </main>
</template>

<style scoped>
* {
    font-family: 'Pretendard';
    margin: 0;
    padding: 0;
}

main {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 4rem;
}

table {
    text-align: center;
}

.upper-container {
    width: 75vw;
    max-width: 1080px;
    display: flex;
    flex-direction: column;
    align-items: flex-end
}

.th {
    text-align: center;
}

.table {
    margin-top: 4rem !important;
}
</style>