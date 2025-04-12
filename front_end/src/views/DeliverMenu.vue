<script setup>
import { ref, onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { deliverStore } from '@/store/deliverStore.js'
import deliverAxios from '@/axios/deliverAxios.js';

const item = ['/deliver/menu/order', '/deliver/menu/history','/deliver/menu/info']
const router = useRouter();
const store = deliverStore()

function exit() {
  store.alterDeliverID('')
  router.push(`/index`);
}
function info() {
  router.push(item.value[2]);
}
function welcome() {
  router.push(`/deliver/menu`);
}

const logo = new URL('@/assets/images/title_logo.png', import.meta.url)
const deliverIcon = new URL('@/assets/images/deliver.png', import.meta.url)
const name = ref('')

onBeforeMount(() => {
  deliverAxios.get("/nav/" + store.deliverID)
      .then((res) => {
        name.value = res.data
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
        <img :src="deliverIcon" style="margin-right: 1rem" @click="info">
        <div class="info" @click="info">
          <strong class="name">{{ name }}</strong>
          <strong class="rank">配送员</strong>
        </div>
        <el-popconfirm width="220" confirm-button-text="确定" cancel-button-text="取消" :icon="InfoFilled"
                       title="确定退出登录?" @confirm="exit">
          <template #reference>
            <el-button type="danger" class="exit" round>退出</el-button>
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
              <List />
            </el-icon>
            <span>待接单列表</span>
          </el-menu-item>

          <el-menu-item :index="item[1]">
            <el-icon :size="20">
              <List />
            </el-icon>
            <span>我的接单</span>
          </el-menu-item>

          <el-menu-item :index="item[2]">
            <el-icon :size="20">
              <UserFilled />
            </el-icon>
            <span>配送员信息</span>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.el-container .main {
  height: 100%;
}
.nav_right{
  display: flex;
  flex-direction: row;
  margin-top: 0.5rem;
}

.name {
  font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
  font-size: x-large;
  width: 4rem;
}
.rank {
  width: 4rem;
  color: rgb(154, 24, 209);
  background: linear-gradient(120deg, #f6ff00 0%, #9b9b9b 100%) no-repeat;
  text-align: center;
}


.info {
  display: flex;
  width: 65%;
  flex-direction: column;
  align-items: center;
  margin-top: 0.8rem;
}

.exit {
  margin: 1.3rem 0.5rem;
}

.el-header {
  background: linear-gradient(120deg, #ffd500 0%, #fdf1a2 100%) no-repeat;
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
