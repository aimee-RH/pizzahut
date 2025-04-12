import { defineStore } from 'pinia'
import { ref } from 'vue'

export const customerStore = defineStore('customerStore',() => {
    const customerID = ref('')
    const level = ref(0)
        
    function alterCustomerID(newID) {
        customerID.value = newID
    }
    function setLevel(newLevel) {
        level.value = newLevel
    }
    
    return {
        customerID,alterCustomerID,level,setLevel
    }
    },
    {
        persist: {
            enabled:true
        }
    }
)