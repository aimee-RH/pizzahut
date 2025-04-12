import { defineStore } from 'pinia'
import { ref } from 'vue'

export const deliverStore = defineStore('deliverStore',() => {
        const deliverID = ref('')

        function alterDeliverID(newID) {
            deliverID.value = newID
        }

        return {
            deliverID,alterDeliverID
        }
    },
    {
        persist: {
            enabled:true
        }
    }
)