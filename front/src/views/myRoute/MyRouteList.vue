<script setup>
import { ref, onMounted } from 'vue';
import { stationAxios } from "@/util/http-commons";

const list = ref([])
const click = ref();
const emit = defineEmits(["select"])
const select = (tour, event) => {
    emit('select', tour);
    if (click.value != null) {
        click.value.style.transform = "scale(1.0)"
        click.value.style.color = "black"
    }

    const div = event.currentTarget;
    click.value = div;
    div.style.transform = "scale(1.05)"
    div.style.color = "#5271FF"
}
onMounted(() => {
    const axiosInstance = stationAxios();
    axiosInstance.get('/route')
        .then((response) => {
            list.value = response.data.routeDto;
        })
        .catch((error) => {
            console.error(error); s
        })

})
</script>

<template>
    <main>
        <div v-for="tour in list" :key="tour.id" class="list-container" @click="select(tour, $event)">
            <h5 style="font-size:1.5rem;">{{ tour.title }}</h5>
            <p style="font-size:0.8rem; color:#DADADA">{{ tour.len }}km</p>
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
    width: 100%;
    padding: 2rem;
    overflow-y: auto;
    max-height: 700px;
}


.list-container {
    display: flex;
    padding: 1.5rem;
    justify-content: space-between;
    align-items: center;
}

.list-container:hover {
    transform: scale(1.05);
    color: #5271FF;
    font-weight: 800;
}
</style>