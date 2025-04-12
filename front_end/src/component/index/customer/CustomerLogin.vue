<script setup>
import { customerStore } from '@/store/customerStore.js'
import { ref,onBeforeMount } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import customerAxios from '@/axios/customerAxios.js';   

const store = customerStore()
const id = ref('')

onBeforeMount(() => {
    id.value = store.userID
})

const router = useRouter()
const password = ref('')
const loading = ref(false)

function login() {
    if (id.value === '') {
        ElMessage.warning('账号不能为空')
    }
    else if (password.value === '') {
        ElMessage.warning('密码不能为空')
    }
    else
    {
        loading.value = true
        customerAxios.post("/login",
            {
                id: id.value,
                password: password.value
            })
            .then((res) => {
                if (String(res.data.code) === '1') {
                    store.alterCustomerID(id.value)
                    router.push(`/customer/menu`)
                } else {
                    ElMessage.warning(res.data.msg)
                    loading.value = false
                }
            })
            .catch(() => {
                ElMessage.error('登录请求异常')
                loading.value = false
            })
    }
}

</script>


<template>
    <div class="box">
        <div class="left"></div>
        <div class="right">
            <h4>登 录</h4>
            <form @submit.prevent>
                <input type="text" class="acc" placeholder="账号" v-model="id" />
                <input type="password" class="acc" placeholder="密码" v-model="password">
                <button class="submit" type="primary" @click="login">
                    <span v-if="!loading">确认</span>
                    <span v-else>登录中...</span>
                </button>
            </form>
            <div class="fn">
                <router-link to="/customer/register" style="color: rgb(197,154,14);">注册账号</router-link>
                <router-link to="/customer/alterPassword" style="color: rgb(197,154,14);">忘记密码</router-link>
            </div>
        </div>
    </div>
</template>

<style scoped>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-size: 15px;
}

::selection {
    color: #fff;
    background-color: rgb(112, 247, 79);

}

.box {
    display: flex;
    overflow: hidden;
    width: 66rem;
    height: 33rem;
    background-color: rgba(255, 255, 255, 60%);
    border-radius: 1.5rem;
    margin: 2% auto;
    box-shadow: 0 0 1rem 0.2rem rgb(0 0 0 / 10%);
}

.box .left {
    position: relative;
    width: 100%;
    height: 100%;
}

.box .left::before {
    content: '';
    position: absolute;
    width: 100%;
    height: 100%;
    background-image: url("@/assets/images/customerPoster.png");
    background-size: cover;
    opacity: 80%;
}

.box .right {
    display: flex;
    width: 65%;
    flex-direction: column;
    align-items: center;
}

.box .right h4 {
    color: rgb(92, 80, 0);
    font-size: 2rem;
    margin-top: 3rem;
}

.box .right form {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
}

.box .right form .acc {
    outline: none;
    width: 80%;
    height: 3.5rem;
    font-size: 1rem;
    margin-top: 3rem;
    padding: 1rem 0 0 1.6rem;
    border: none;
    border-bottom: 2px solid rgb(155, 131, 5);
    color: rgb(150, 121, 5);
    background-color: rgba(0, 0, 0, 0);
}

.right el-form .acc:focus {
    outline: none;
    color: rgb(248, 213, 74);
    padding: 1rem 0 0 1.6rem;
}

.right .submit {
    width: 40%;
    height: 3.5rem;
    color: #f6f6f6;
    background-image: linear-gradient(120deg, #000000 0%, #444444 100%);
    font-size: 1.5rem;
    border: none;
    border-radius: 0.5rem;
    margin: 5rem 0 0 42%;
    transform: translateX(-50%);
}

.right .submit:hover {
    box-shadow: 0 0 2rem -0.5rem rgb(0 0 0 / 15%);
}

.right .fn {
    display: flex;
    margin-top: 1rem;
    width: 70%;
    justify-content: space-between;
}

</style>