<script setup>
import { ref } from 'vue'
const logo = ref(new URL('@/assets/images/welcome.png', import.meta.url))
</script>

<template>
    <div class="welcome">
        <img :src="logo" style="height: 450px;">
        <h1 class="text1">欢迎进入</h1>
        <h2 class="text2">必胜客管理系统</h2>
    </div>
</template>

<style scoped>
.welcome{
    display: flex;
    width: 100%;
    flex-direction: column;
    align-items: center;
}
.text1{
    margin: 0;
    font-size: 2rem;
    font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
}
.text2{
    margin: 0;
    font-size: 3rem;
    font-style: italic;
    font-family: Arial, Helvetica, sans-serif;
    color: rgb(69, 42, 165);
    transform: translateX(-1rem);
}
</style>