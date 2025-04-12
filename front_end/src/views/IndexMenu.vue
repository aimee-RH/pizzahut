<script setup>
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()
function index() {
    if (route.path != '/index')
        router.push('/index')
}
</script>

<template>
    <div class="index">
        <div class="nav">
            <img src="@/assets/images/title_logo.png" @click="index">
        </div>
        <router-view/>
    </div>
</template>

<style scoped>
.nav {
    height: 80px;
    width: 300px;
    text-align: center;
    margin: 2% auto;
  }

.index {
  position: fixed;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #36a52a, #25a8a8, #a9fda2, #C0A3E1, #8B8DA0);
  animation: backgroundAnimation 20s linear infinite;
}

@keyframes backgroundAnimation {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}

</style>