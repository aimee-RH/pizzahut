import { defineStore } from 'pinia'
import { ref } from 'vue'

export const shopStore = defineStore('shopStore',() => {
        const shopID = ref('')

        function alterShopID(newID) {
            shopID.value = newID
        }

        return {
            shopID,alterShopID
        }
    },
    {
        persist: {
            enabled:true
        }
    }
)