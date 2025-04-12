<script setup>
import {shopStore} from '@/store/shopStore.js'
import {ref} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import shopAxios from '@/axios/shopAxios.js';
import {useRouter} from 'vue-router'

const router = useRouter()
const store = shopStore()
const form = ref({
  id: '',
  password: ''
})
const confirm = ref('')

function check() {
  if (form.value.id === '') {
    ElMessage.warning('请输入账号')
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

function open() {
  ElMessageBox.alert('恭喜您已成功修改密码，请牢记您的密码', '密码修改成功', {
    confirmButtonText: '去登录',
    callback: () => {
      router.push('/shop/login')
    },
  })
}

function alterPassword() {
  if (!check()) {
    return
  }
  shopAxios.post("/alterPassword",
      {
        账号: form.value.id,
        密码: form.value.password
      })
      .then((res) => {
        if (res.data.code === 1) {
          store.alterShopID(form.value.id)
          open()
        } else {
          ElMessage.error(res.data.msg)
        }
      })
      .catch(() => {
        ElMessage.error('注册请求异常')
      })
}

</script>


<template>
  <div class="box">
    <div class="left"></div>
    <div class="right">
      <h4>忘记密码</h4>
      <form @submit.prevent>
        <input type="text" class="acc" placeholder="账号" v-model="form.id"/>
        <input type="password" class="acc" placeholder="新密码" v-model="form.password"/>
        <input type="password" class="acc" placeholder="确认密码" v-model="confirm"/>
      </form>
      <button class="submit" type="primary" @click="alterPassword">
        <span>确认</span>
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
  background-image: url("@/assets/images/shopPoster.png");
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
  color: rgb(17, 92, 0);
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
  border-bottom: 2px solid rgb(48, 164, 21);
  color: rgb(67, 173, 51);
  background-color: rgba(0, 0, 0, 0);
}

.right el-form .acc:focus {
  outline: none;
  color: rgb(118, 248, 74);
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
  margin: 2rem 0 0 42%;
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

.right .fn a {
  font-size: 1rem;
  margin-top: 1rem;
  padding: 1rem;
  color: #666;
  align-items: center;
}

.right .fn a:hover {
  color: rgb(166, 241, 129);
}
</style>