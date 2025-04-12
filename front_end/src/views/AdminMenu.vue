<script setup>
import {ref, onBeforeMount} from 'vue'
import {useRouter} from 'vue-router'
import {ElMessage} from 'element-plus'
import {adminStore} from '@/store/adminStore.js'
import adminAxios from '@/axios/adminAxios.js';

const item = ['/admin/menu/dish', '/admin/menu/order', '/admin/menu/backup']
const router = useRouter();
const store = adminStore()

function exit() {
  store.alterAdminID('')
  router.push(`/index`);
}

function welcome() {
  router.push(`/admin/menu`);
}

const logo = new URL('@/assets/images/title_logo.png', import.meta.url)
const adminIcon = new URL('@/assets/images/admin.png', import.meta.url)
const name = ref('')

onBeforeMount(() => {
  adminAxios.get("/nav/" + store.adminID)
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
        <img :src="adminIcon" style="margin-right: 1rem">
        <div class="info">
          <strong class="name">{{ name }}</strong>
          <strong class="rank">管理员</strong>
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
              <DishDot/>
            </el-icon>
            <span>菜品管理</span>
          </el-menu-item>
          <el-menu-item :index="item[1]">
            <el-icon :size="20">
              <List/>
            </el-icon>
            <span>订单管理</span>
          </el-menu-item>
          <el-menu-item :index="item[2]">
            <el-icon :size="20">
              <Management/>
            </el-icon>
            <span>数据管理</span>
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

.name {
  font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
  font-size: xx-large;
}

.rank {
  width: 4rem;
  color: rgb(154, 24, 209);
  background: linear-gradient(120deg, #f6ff00 0%, #9b9b9b 100%) no-repeat;
  text-align: center;
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
  margin-top: 0.4rem;
}

.exit {
  margin: 1.3rem 0.5rem 0 1rem;
}

.el-header {
  background: linear-gradient(120deg, #0084ff 0%, #68f2ff 100%) no-repeat;
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
