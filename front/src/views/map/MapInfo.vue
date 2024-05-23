<script setup>
import { defineEmits, defineProps, onMounted, ref, watch } from 'vue';
import OpenAI from 'openai';
const emit = defineEmits(['resignExecute']);
const props = defineProps({
    askTour: {
        type: String,
        required: true,
    },
});
const closeModal = () => {
    emit('closeModal')
}
onMounted(() => {
    getGPTResponse(props.askTour);
    if (props.askTour) {
        getGPTResponse(props.askTour);
    }
})
watch(props.askTour, (newAskTour) => {
    if (newAskTour) {
        getGPTResponse(newAskTour);
    }
})
const getGPTResponse = async (askTour) => {
    try {
        const openai = new OpenAI({
            apiKey: `${import.meta.env.VITE_OPENAI_API_KEY}`,
            dangerouslyAllowBrowser: true,
        })

        const prompt =
            `${askTour}에 대한 사람들의 후기를 

- 전반적인 평가 내용
- 장점
- 단점

을 개조식으로 한국말로 요약해줘
모든 문장의 끝에 꼭 '<br>'를 넣어주고 전반적인 평가 내용, 장점, 단점 글 앞에는 <br> 한 번 더 붙이고, <strong>을 그 이어서 붙여주고 뒤에는 </strong>을 꼭 붙여줘`

        const response = await openai.chat.completions.create({
            messages: [
                {
                    role: 'user',
                    content: prompt,
                },
            ],
            model: 'gpt-4',
        })
        document.querySelector(".card-text").innerHTML = response.choices[0].message.content
    } catch (error) {
        console.log('chatGPT: 🚨 에러가 발생했습니다.')
        console.error(error)
    }
}
</script>

<template>
    <div class="modal-bg">
        <div class="card" style="width:60vw; max-width:600px">
            <div class="card-header" style="font-weight: 600; font-size:1.2rem">
                {{ props.askTour }}
            </div>
            <div class="card-body">
                <p class="card-text"></p>
                <a href="#" class="btn btn-secondary" @click="closeModal">close</a>
            </div>
        </div>
    </div>
</template>

<style scoped>
.modal-bg {
    position: absolute;
    /* display: none; */
    display: flex;
    align-items: center;
    justify-content: center;
    top: 0;
    left: 0;

    width: 100vw;
    height: 100vh;

    z-index: 1050;
    background-color: rgba(0, 0, 0, 0.4);
}

.mo {
    width: 400px;
    height: 600px;

    padding: 40px;

    text-align: center;

    background-color: rgb(255, 255, 255);
    border-radius: 10px;
    box-shadow: 0 2px 3px 0 rgba(34, 36, 38, 0.15);

    transform: translateY(-50%);

    max-height: 80vh;
    overflow-y: auto;

}
</style>