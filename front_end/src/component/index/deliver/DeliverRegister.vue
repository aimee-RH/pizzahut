<script setup>
import { deliverStore } from '@/store/deliverStore.js'
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import deliverAxios from '@/axios/deliverAxios.js';
import { useRouter } from 'vue-router'

const router = useRouter()
const store = deliverStore()
const form = ref({
  name: '',
  sex: '男',
  phone: '',
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
  if (form.value.phone === '') {
    ElMessage.warning('请输入手机号')
    return false
  }
  if (form.value.phone.length != 11) {
    ElMessage.warning('请输入正确的手机号')
    return false
  }
  let newValue = form.value.phone.replace(/[^0-9]/gi, '')
  if (form.value.phone !== newValue) {
    ElMessage.warning('请输入正确的手机号')
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

function open (id){
  ElMessageBox.alert('恭喜您已成功注册账号，请牢记您的账号：' + id, '注册成功', {
    confirmButtonText: '去登录',
    callback: () => {
      router.push('/deliver/login')
    },
  })
}

function register() {
  if (!check()) {
    return
  }
  loading.value = true
  deliverAxios.post("/register",
      {
        姓名: form.value.name,
        性别: form.value.sex,
        电话: form.value.phone,
        密码: form.value.password
      })
      .then((res) => {
        if (res.data.code === 1) {
          store.alterDeliverID(res.data.msg)
          open(res.data.msg)
          loading.value = false
        }
        else {
          ElMessage.error(res.data.msg)
          loading.value = false
        }
      })
      .catch(() => {
        ElMessage.error('修改密码请求异常')
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
        <div class="sel">
          <span style="margin-right: 2rem;">性别:</span>
          <el-radio-group v-model="form.sex">
            <el-radio label="男" size="large" style="margin-right: 2rem;">男</el-radio>
            <el-radio label="女" size="large" style="margin-right: 2rem;">女</el-radio>
          </el-radio-group>
        </div>
        <input type="text" class="acc" placeholder="姓名" v-model="form.name" />
        <input type="text" class="acc" placeholder="电话" v-model="form.phone" />
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
  margin-top: 2rem;
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
  margin-top: 1rem;
  padding: 1rem 0 0 1.6rem;
  border: none;
  border-bottom: 2px solid rgb(255, 0, 0);
  color: rgb(255, 87, 72);
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
  color: rgb(241, 133, 129);
}
</style>