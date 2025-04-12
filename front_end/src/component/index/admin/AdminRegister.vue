<script setup>
import { adminStore } from '@/store/adminStore.js'
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import adminAxios from '@/axios/adminAxios.js';
import { useRouter } from 'vue-router'

const router = useRouter()
const store = adminStore()
const form = ref({
    name: '',
    password: ''
})
const confirm = ref('')

const loading = ref(false)

function check() {
    if (form.value.name === '') {
        ElMessage.warning('请输入姓名')
        return false
    }
    if (form.value.name.length > 10) {
        ElMessage.warning('姓名长度不能超过10个字')
        return false
    }
    if (form.value.password === '') {
        ElMessage.warning('请输入密码')
        return false
    }
    if (confirm.value === '') {
        ElMessage.warning('请确认密码')
        return false
    }
    if (confirm.value != form.value.password) {
        ElMessage.warning('两次密码不一致')
        return false
    }
    return true
}

function open(id) {
    ElMessageBox.alert('恭喜您已成功注册账号，请牢记您的账号：' + id, '注册成功', {
        confirmButtonText: '去登录',
        callback: () => {
            router.push('/admin/login')
        },
    })
}

function register() {
    if (!check()) {
        return
    }
    loading.value = true
    adminAxios.post("/register",
        {
            姓名: form.value.name,
            密码: form.value.password
        })
        .then((res) => {
            if (res.data.code === 1) {
                store.alterAdminID(res.data.msg)
                open(res.data.msg)
                loading.value = false
            }
            else {
                ElMessage.error(res.data.msg)
                loading.value = false
            }
        })
        .catch(() => {
            ElMessage.error('注册请求异常')
            loading.value = false
        })
}

</script>


<template>
    <div class="box">
        <div class="left"></div>
        <div class="right">
            <h4>注 册</h4>
            <form @submit.prevent>
                <input type="text" class="acc" placeholder="姓名" v-model="form.name" />
                <input type="password" class="acc" placeholder="密码" v-model="form.password" />
                <input type="password" class="acc" placeholder="确认密码" v-model="confirm" />
            </form>
            <button class="submit" type="primary" @click="register">
                <span v-if="!loading">确认</span>
                <span v-else>注册中...</span>
            </button>
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
    background-image: url("@/assets/images/adminPoster.png");
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
    color: rgb(31, 0, 92);
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
    margin-top: 2rem;
    padding: 1rem 0 0 1.6rem;
    border: none;
    border-bottom: 2px solid rgb(72, 0, 255);
    color: rgb(8, 0, 255);
    background-color: rgba(0, 0, 0, 0);
}

.right el-form .acc:focus {
    outline: none;
    color: rgb(248, 74, 74);
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