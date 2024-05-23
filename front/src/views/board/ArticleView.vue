<script setup>
import { ref, onMounted, nextTick, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { storeToRefs } from 'pinia';
import { stationAxios } from '@/util/http-commons';
import { useMemberStore } from '@/stores/jwt_token';

const router = useRouter();
const route = useRoute();
const article = ref(null);
const comments = ref([]);
const myArticle = ref(false);
const writeComment = ref('');
const memberStore = useMemberStore();
const articleno = route.params.articleno;
const likeBtnClass = ref("btn like-btn btn-outline-danger");
const { getUserInfo } = memberStore;
const { userInfo } = storeToRefs(memberStore);

onMounted(async () => {
    userInfo.value = userInfo.value || { userId: '' };
    window.addEventListener('resize', handleResize);
    await getPost();
    await nextTick();
});
const getPost = async () => {
    const axiosInstance = stationAxios();
    try {
        const response = await axiosInstance.get('/board/' + articleno);
        article.value = response.data;
        comments.value = response.data.comments;
        await getUserInfo(sessionStorage.getItem('accessToken'));
        if (userInfo.value.userId === article.value.user_id) {
            myArticle.value = true;
        }
        await nextTick();
        handleResize();
    } catch (error) {
        console.error(error);
    }
};

const handleResize = () => {
    const header = document.querySelector('#header');
    const main = document.querySelector('.main');

    if (header && main) {
        const headerHeight = header.offsetHeight;
        main.style.height = `calc(100vh - ${headerHeight}px)`;
    }
};

const registerComment = async () => {
    try {
        const axiosInstance = stationAxios();
        const params = route.params.articleno;
        const data = {
            contents: writeComment.value,
        };
        await axiosInstance.post(`/board/${articleno}/comment`, data);
        writeComment.value = "";
        await getPost(); // 댓글 등록 후 getPost 함수 호출
    } catch (error) {
        console.error(error);
    }
};

const like = async () => {
    const axiosInstance = stationAxios();
    await axiosInstance.post(`/board/${articleno}/like`)
        .then((response) => {
            const btn = document.querySelector(".like-btn");
            article.value.like++;
            likeBtnClass.value = "btn like-btn btn-danger"
            btn.disabled = true;
        })
        .catch((error) => {
            console.error(error)
        })
}
const commentDelete = (id) => {
    const axiosInstance = stationAxios();
    axiosInstance.delete(`/board/comment/${id}`)
        .then(async (response) => {
            alert("댓글 삭제 완료")
            await getPost();
        })
        .catch((error) => {
            console.error(error)
        })
}
const articleDelete = () => {
    const check = confirm("삭제 후 복구가 불가능합니다. 글 삭제를 진행하시겠습니까?")
    if (check) {
        const axiosInstance = stationAxios();
        axiosInstance.delete(`/board/${articleno}`)
            .then((response) => {
                alert("글 삭제 완료")
                router.push("/board/list")
            })
            .catch((error) => {
                console.error(error)
            })
    }

}
const moveToModify = () => {
    router.push(`/article/modify/${articleno}`)
}
</script>

<template>
    <main v-if="article" class="main">
        <div class="container">
            <div class="article-title">
                <div class="article-title-left">
                    <h3>{{ article.title }}</h3>
                    <p class="light-font">{{ article.created_date }}</p>
                </div>
                <div class="article-title-right">
                    <p class="light-font">조회수 : {{ article.view }}</p>
                    <div style="display:flex; gap:0.4rem;" v-show="myArticle">
                        <a class="light-font" style="text-decoration: underline;" @click="moveToModify">수정</a>
                        <a class="light-font" @click="articleDelete" style="text-decoration: underline;">삭제</a>
                    </div>
                </div>
            </div>

            <p>{{ article.contents }}</p>
            <div class="button-container">
                <button type="button " :class="likeBtnClass" @click="like">❤ {{ article.like }}</button>
            </div>
        </div>
        <div class="container">
            <h5>댓글</h5>
            <div class="comments-list" v-if="comments.length" style="width:100% !important;">
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr class="comment" v-for="c in comments" :key="c.created_date">
                            <td>
                                <div class="comment-upper">
                                    <p style="font-weight: 600;">{{ c.user_id }}</p>
                                    <p class="light-font">{{ c.created_date }}</p>
                                </div>
                                <p>{{ c.contents }}</p>
                                <div style="display:flex; flex-direction:column; align-items: flex-end; gap:0.4rem;"
                                    v-show="userInfo.userId === c.user_id">
                                    <a class="light-font" style="text-decoration: underline;"
                                        @click="commentDelete(c.id)">삭제</a>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="write-container">
                <textarea class="form-control" id="exampleFormControlTextarea1" rows="2"
                    v-model="writeComment"></textarea>
                <button @click="registerComment" class="btn btn-primary submit-btn">등록</button>
            </div>
        </div>
    </main>
</template>


<style scoped>
main {
    display: flex;
    flex-direction: column;
    gap: 2rem;
    margin: 4rem;
}

.container {
    width: 65vw;
    max-width: 650px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    border: 1px solid #dadada;
    border-radius: 20px;
    padding: 4rem;
    gap: 1.4rem;

}

.comment-upper {
    font-size: 1rem;
}

.article-title-right {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
}

.button-container {
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.comment-upper {
    display: flex;
    justify-content: space-between;
}

.light-font {
    color: #777777;
    font-weight: 200;

}

.article-title {
    width: 100%;
    display: flex;
    justify-content: space-between;
    border-bottom: 1px solid #dadada;
}

.write-container {
    display: flex;
    width: 100%;
    align-items: center;
    gap: 1rem;
    padding-top: 2rem;
}

.write-container textarea {
    flex: 1;
}

.submit-btn {
    flex-shrink: 0;
    height: 100%;
}
</style>