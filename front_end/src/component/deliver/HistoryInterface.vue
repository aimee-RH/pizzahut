<script setup>
import {ref, onBeforeMount} from 'vue'
import {ElMessage} from 'element-plus'
import {deliverStore} from '@/store/deliverStore.js'
import deliverAxios from '@/axios/deliverAxios.js'

const store = deliverStore()
let orders = ref([])

onBeforeMount(() => {
  deliverAxios.get(`/history/${store.deliverID}`)
      .then((res) => {
        orders.value = res.data.orders
      })
      .catch(() => {
        ElMessage.error('订单请求异常！')
      });
})
function done(id){
  deliverAxios.post("/updateOrder",{
    orderID: id
  })
      .then((res) => {
        if (res.data.code === 1) {
          ElMessage.success('更新成功！')
          setTimeout(() => {
            window.location.reload();
          }, 1000);
        }
        else {
          ElMessage.error(res.data.msg)
        }
      })
      .catch(() => {
        ElMessage.error('更新请求失败！')
      })
}
</script>

<template>
  <div v-if="orders.length === 0">
    <el-empty style="margin-top: 12rem;" description="您未拥有任何订单"></el-empty>
  </div>

  <div v-else class="orderBox">
    <h3>我的接单</h3>
    <el-table :data="orders" style="width: 90%" max-height="470">
      <el-table-column prop="编号" label="编号"/>
      <el-table-column prop="时间" label="时间" width="110px">
        <template #default="scope">
          <p>{{ scope.row.时间 }}</p>
        </template>
      </el-table-column>
      <el-table-column prop="用户名" label="用户"/>
      <el-table-column prop="地址" label="地址" width="200px"/>
      <el-table-column prop="门店名" label="门店"/>
      <el-table-column prop="配送状态" label="配送状态"/>
      <el-table-column prop="备注" label="备注"/>
      <el-table-column prop="总金额" label="总金额/￥"/>
      <el-table-column>
        <template #default="scope">
          <el-icon>
            <Ticket v-if="scope.row.用券 === '是'"/>
            <StarFilled v-if="scope.row.特权 === '是'"/>
          </el-icon>
        </template>
      </el-table-column>
      <el-table-column>
        <template #default="scope">
          <el-button v-if="scope.row.配送状态 === '配送中'" type="success" @click="done(scope.row.编号)" round>送达</el-button>
        </template>
      </el-table-column>

    </el-table>
  </div>
</template>

<style scoped>
.orderBox {
  display: flex;
  width: 100%;
  height: 35rem;
  flex-direction: column;
  align-items: center;
  margin-top: 2rem;
}
</style>

