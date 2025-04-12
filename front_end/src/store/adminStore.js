import { defineStore } from 'pinia'
import { ref } from 'vue'

export const adminStore = defineStore('adminStore',() => {
    const adminID = ref('')
        
    function alterAdminID(newID) {
        adminID.value = newID
    }
    
    return {
        adminID,alterAdminID
    }
    },
    {
        persist: {
            enabled:true
        }
    }
)