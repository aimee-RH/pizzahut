<script setup>
import {useRouter} from 'vue-router'

const imageUrl = [
  new URL("@/assets/images/swiper1.png", import.meta.url),
  new URL("@/assets/images/swiper2.png", import.meta.url),
  new URL("@/assets/images/swiper3.png", import.meta.url),
  new URL("@/assets/images/swiper4.png", import.meta.url),
]

const router = useRouter()

function customer() {
  router.push('/customer/login')
}

function admin() {
  router.push('/admin/login')
}

function shop() {
  router.push('/shop/login')
}

function deliver() {
  router.push('/deliver/login')
}
</script>

<template>
  <el-carousel :interval="2000" type="card" height="400px">
    <el-carousel-item v-for="item in imageUrl" :key="item">
      <img class="large" :src='item'>
    </el-carousel-item>
  </el-carousel>
  <div class="buttonBox">
      <button class="customer-button" @click="customer">我是顾客</button>
      <button class="deliver-button" @click="deliver">我是配送员</button>
      <button class="shop-button" @click="shop">我是门店</button>
      <button class="admin-button" @click="admin">我是管理员</button>
  </div>
</template>

<style scoped>
.buttonBox {
  display: flex;
  width: 80%;
  margin: 2% auto;
  justify-content: space-between;
}

.el-carousel__item img {
  color: #475669;
  height: 400px;
  width: 800px;
  margin: 0;
}


@keyframes flow {
  0% {
    left: -100%;
  }

  100% {
    left: 100%;
  }
}

.customer-button {
  display: inline-block;
  width: 12rem;
  height: 4rem;
  margin-top: 1.5rem;
  padding: 10px 20px;
  font-size: 20px;
  font-weight: bold;
  color: rgb(135, 124, 1);
  background-color: #ffcc00;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.customer-button::before {
  content: "";
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(to right, transparent, rgba(255, 255, 255, 0.4), transparent);
  animation: flow 2s linear infinite;
}

.customer-button:hover {
  background-color: #ffdb4d;
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.2);
}

.customer-button:hover::before {
  opacity: 1;
  animation: flow 2s linear infinite;
}


.deliver-button {
  display: inline-block;
  width: 12rem;
  height: 4rem;
  margin-top: 1.5rem;
  margin-right: 1rem;
  padding: 10px 20px;
  font-size: 20px;
  font-weight: bold;
  color: #faa5a5;
  background-color: #ff0000;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.deliver-button::before {
  content: "";
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(to right, transparent, rgba(255, 255, 255, 0.4), transparent);
  animation: flow 2s linear infinite;
}

.deliver-button:hover {
  background-color: #940000;
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.2);
}

.deliver-button:hover::before {
  opacity: 1;
  animation: flow 2s linear infinite;
}


.shop-button {
  display: inline-block;
  width: 12rem;
  height: 4rem;
  margin-top: 1.5rem;
  padding: 10px 20px;
  font-size: 20px;
  font-weight: bold;
  color: rgb(10, 135, 1);
  background-color: #54ff00;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.shop-button::before {
  content: "";
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(to right, transparent, rgba(255, 255, 255, 0.4), transparent);
  animation: flow 2s linear infinite;
}

.shop-button:hover {
  background-color: #7aff4d;
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.2);
}

.shop-button:hover::before {
  opacity: 1;
  animation: flow 2s linear infinite;
}


.admin-button {
  display: inline-block;
  width: 12rem;
  height: 4rem;
  margin-top: 1.5rem;
  margin-right: 1rem;
  padding: 10px 20px;
  font-size: 20px;
  font-weight: bold;
  color: #00f7ff;
  background-color: #007BFF;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.admin-button::before {
  content: "";
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(to right, transparent, rgba(255, 255, 255, 0.4), transparent);
  animation: flow 2s linear infinite;
}

.admin-button:hover {
  background-color: #0056b3;
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.2);
}

.admin-button:hover::before {
  opacity: 1;
  animation: flow 2s linear infinite;
}

</style>