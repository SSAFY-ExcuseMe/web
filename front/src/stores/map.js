import { ref } from "vue"
import { defineStore } from "pinia"

export const useMapStore = defineStore("mapStore", () => {
    const mapResult = ref([])
    const route = ref([])
    const saveRoute = ref(false)

    const routePush = async (tour) => {
        if (route.value.findIndex(r => r.id == tour.id) === -1) {
            route.value.push(tour);
        }
    }
    const routeRemove = async (tour) => {
        route.value.splice(route.value.findIndex(t => t.id === tour.id),1);
    }

    return {
        mapResult,
        route,
        saveRoute,
        routePush,
        routeRemove
    }
})