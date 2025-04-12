<script setup>
import { ref, onBeforeMount } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { customerStore } from '@/store/customerStore.js'
import customerAxios from '@/axios/customerAxios.js'

const store = customerStore()
const imgVIP = new URL("@/assets/images/VIP.png", import.meta.url)
const imgSVIP = new URL("@/assets/images/SVIP.png", import.meta.url)
const VIPPrice = ref(100)
const SVIPPrice = ref(200)
const EXP = ref('')

onBeforeMount(() => {
    if (store.level != '1') {
        customerAxios.get("/EXP/" + store.customerID)
            .then((res) => {
                var date = new Date(res.data)
                EXP.value = date.getFullYear() + '年' + date.getMonth() + '月' + date.getDate() + '日'
            })
            .catch(() => {
                ElMessage.error('会员期限请求异常！')
            });
    } 
})

function buyVIP() {
    if (store.level === '2') {
        ElMessage.warning('VIP有效至'+ EXP.value)
        return
    }
    if (store.level === '3') {
        ElMessage.warning('您已是SVIP用户')
        return
    }
    ElMessageBox.confirm(
        '花费' + VIPPrice.value + '元将您的账号升级为VIP, 确定支付吗?',
        '升级为VIP用户',
        {
            confirmButtonText: '确认支付',
            cancelButtonText: '下次一定',
            type: 'warning',
        }
    )
        .then(() => {
            customerAxios.get(`/upgrade`,
                {
                    账号: store.customerID,
                    会员等级: 2
                })
                .then(() => {
                    store.setLevel(2)
                    ElMessage.success('支付成功,欢迎加入VIP用户')
                    setTimeout(() => {
                        window.location.reload();
                    }, 1000);
                })
                .catch(() => {
                    ElMessage.error('支付失败')
                })
        })
}

function buySVIP() {
    if (store.level === '3') {
        ElMessage.warning('SVIP有效至' + EXP.value)
        return
    }
    ElMessageBox.confirm(
        '花费' + SVIPPrice.value + '元将您的账号升级为SVIP, 确定支付吗?',
        '升级为SVIP用户',
        {
            confirmButtonText: '确认支付',
            cancelButtonText: '下次一定',
            type: 'warning',
        }
    )
        .then(() => {
            customerAxios.get(`/upgrade`,
                {
                    账号: store.customerID,
                    会员等级: 3
                })
                .then((res) => {
                    if (res.data.code === 1) {
                        store.setLevel(3)
                        ElMessage.success('支付成功,欢迎加入SVIP用户') 
                        setTimeout(() => {
                            window.location.reload();
                        }, 1000); 
                    }
                    else {
                        ElMessage.error(res.data.msg)
                    }
                })
                .catch(() => {
                    ElMessage.error('支付失败')
                })
        })
}
</script>

<template>
    <div class="box">
        <div class="left">
            <h1>VIP</h1>
            <img :src="imgVIP" style="height: 100px;">
            <h3>每日一次订单最高价商品打8折优惠</h3>
            <h3>领取VIP专属礼包</h3>
            <h3>优质的配送服务</h3>
            <el-button type="primary" style="width: 20rem;" @click="buyVIP">
                <span v-if="store.level === 1">{{ VIPPrice }}￥-加入VIP</span>
                <span v-else-if="store.level === 2">VIP有效至{{ EXP }}</span>
                <span v-else>您已是SVIP用户</span>
            </el-button>
        </div>
        <div class="right">
            <h2>SVIP</h2>
            <img :src="imgSVIP" style="height: 100px;">
            <h3>每日一次订单最高价商品打7折优惠</h3>
            <h3>领取SVIP专属礼包</h3>
            <h3>极致的配送服务</h3>
            <el-button type="danger" style="width: 20rem;" @click="buySVIP">  
                <span v-if="store.level != 3">{{ SVIPPrice }}￥-加入SVIP</span>
                <span v-else>SVIP有效至{{ EXP }}</span>
            </el-button>
        </div>
    </div>

</template>

<style scoped>
h1{
    margin: 1rem;
    font-size: xxx-large;
    color: #d5d813;
}
h2{
    margin: 1rem;
    font-size: xxx-large;
    color: blueviolet;
}
h3{
    margin: 2rem;
}
.box {
    display: flex;
    width: 80rem;
    height: 35rem;
    justify-content: space-between;
}
.box .left {
    display: flex;
    width: 35%;
    height: 95%;
    flex-direction: column;
    align-items: center;
    background-image: linear-gradient(180deg,#25a8a8,#ffffff);
    margin: 0 2rem 0 8rem;
    border-radius: 5rem;
    box-shadow: 0 0 2rem 2rem rgb(0 0 0 / 10%);
}

.box .right {
    display: flex;
    width: 35%;
    height: 95%;
    flex-direction: column;
    align-items: center;
    background-image: linear-gradient(180deg,#000000,#ff0000,#ffffff);
    margin: 0 8rem 0 2rem;
    border-radius: 5rem;
    box-shadow: 0 0 2rem 2rem rgb(0 0 0 / 10%);
}
</style>