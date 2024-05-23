<script setup>
import { ref } from 'vue';
import MyRouteList from "@/views/myRoute/MyRouteList.vue"
import MyRouteView from "@/views/myRoute/MyRouteView.vue"

const select = ref()
const selectTour = (tour) => {
    select.value = tour;
}
</script>

<template>
    <div class="row">
        <div class="col-4 border-end">
            <MyRouteList @select="selectTour" />
        </div>
        <div class="col-8">
            <MyRouteView :select="select" />
        </div>
    </div>
</template>

<style scoped>
* {
    font-family: 'Pretendard';
    padding: 0;
    margin: 0;
}
</style>