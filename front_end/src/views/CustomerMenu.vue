<script setup>
import {customerStore} from '@/store/customerStore.js'
import {ref, onBeforeMount} from 'vue'
import {useRouter} from 'vue-router'
import customerAxios from '@/axios/customerAxios.js'
import {ElMessage} from 'element-plus'

const item = ['/customer/menu/dish', '/customer/menu/card', '/customer/menu/order', '/customer/menu/info', '/customer/menu/vip']
const router = useRouter();
const store = customerStore()

function exit() {
  store.alterCustomerID('')
  router.replace(`/index`);
}

function info() {
  router.push(item.value[3]);
}

function welcome() {
  router.push(`/customer/menu`);
}

const logo = new URL('@/assets/images/title_logo.png', import.meta.url)
const customerIcon = ref('')
const name = ref('')
const level = ref('')

onBeforeMount(() => {
  customerAxios.get("/nav/" + store.customerID)
      .then((res) => {
        if (String(res.data.sex) === '女') {
          customerIcon.value = new URL('@/assets/images/female.png', import.meta.url)
        } else {
          customerIcon.value = new URL('@/assets/images/male.png', import.meta.url)
        }
        name.value = res.data.name
        level.value = res.data.level
        store.setLevel(res.data.levelID)
      })
      .catch(() => {
        ElMessage.error('主菜单请求异常！')
      })
});
</script>

<template>
  <el-container class="main">
    <el-header style="height: 5rem; border-radius: 0.5rem;">
      <img :src="logo" @click="welcome">
      <div class="nav_right">
        <img :src="customerIcon" style="margin-right: 1rem" @click="info">
        <div class="info" @click="info">
          <strong class="name">{{ name }}</strong>
          <strong :class="{ aqua: level === 'VIP', orange: level === 'SVIP' }">{{ level }}</strong>
        </div>
        <el-popconfirm width="220" confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled"
                       title="确定退出登录?" @confirm="exit">
          <template #reference>
            <el-button type="danger" round class="exit">退出</el-button>
          </template>
        </el-popconfirm>
      </div>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <el-menu class="el-menu-vertical-demo" :router=true background-color="#545c64" text-color="#fff"
                 active-text-color="#ffd04b" style="height: 100%; border-radius: 0.5rem;">
          <el-menu-item :index="item[0]">
            <el-icon :size="20">
              <DishDot/>
            </el-icon>
            <span>点餐</span>
          </el-menu-item>
          <el-menu-item :index="item[1]">
            <el-icon :size="20">
              <Ticket/>
            </el-icon>
            <span>优惠券</span>
          </el-menu-item>
          <el-menu-item :index="item[2]">
            <el-icon :size="20">
              <List/>
            </el-icon>
            <span>订单</span>
          </el-menu-item>
          <el-menu-item :index="item[3]">
            <el-icon :size="20">
              <UserFilled/>
            </el-icon>
            <span>用户信息</span>
          </el-menu-item>
          <el-menu-item :index="item[4]">
            <el-icon :size="20">
              <StarFilled/>
            </el-icon>
            <span>会员中心</span>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.el-container .main {
  height: 100%;
}

.nav_right {
  display: flex;
  flex-direction: row;
  margin-top: 0.5rem;
}

.info {
  display: flex;
  width: 65%;
  flex-direction: column;
  align-items: center;
  margin-top: 0.8rem;
}

.exit {
  margin: 1.3rem 0.5rem 0 1rem;
}


.name {
  font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
  font-size: x-large;
}

.aqua {
  width: 4rem;
  color: aqua;
  background: linear-gradient(120deg, #ff0000 0%, #960000 100%) no-repeat;
  text-align: center;
}

.orange {
  width: 4rem;
  color: rgb(255, 166, 0);
  background: linear-gradient(120deg, #000000 0%, #c5c5c5 100%) no-repeat;
  text-align: center;
}


.menu {
  height: 100%;
}

.el-header {
  background: linear-gradient(120deg, #fbff00 0%, #fff568 100%) no-repeat;
  display: flex;
  justify-content: space-between;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  height: 38rem;
}

.el-main {
  color: #333;
  text-align: center;
  height: 38rem;
}
</style>
