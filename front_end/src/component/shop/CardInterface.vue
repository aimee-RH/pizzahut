<script setup>
import {ref, onBeforeMount} from 'vue'
import {ElMessage} from 'element-plus'
import shopAxios from '@/axios/shopAxios.js';
import {shopStore} from "@/store/shopStore";

const cards = ref('')
const img1 = ref(new URL("@/assets/images/card1.png", import.meta.url))
const img2 = ref(new URL("@/assets/images/card2.png", import.meta.url))
const img3 = ref(new URL("@/assets/images/card3.png", import.meta.url))
const store = shopStore()

onBeforeMount(() => {
  shopAxios.get("/card")
      .then((res) => {
        cards.value = res.data.cards
      })
      .catch(() => {
        ElMessage.error('优惠券请求异常！')
      });
})

const Visible = ref(false)
const cardID = ref('')
const customers = ref('')
const grant = ref('')

function open(ID) {
  cardID.value = ID
  grant.value = ''
  shopAxios.get(`/noCardCustomer/${cardID.value}/${store.shopID}`)
      .then((res) => {
        customers.value = res.data.customers
        console.log(customers.value)
        Visible.value = true
      })
      .catch(() => {
        ElMessage.error("用户请求失败")
      })
  Visible.value = true
}

function distribute() {
  if (grant.value === '') {
    ElMessage.warning("请选择用户")
    return
  }
  shopAxios.post(`/distributeCard`,
      {
        customerID: grant.value,
        shopID: store.shopID,
        cardID: cardID.value
      })
      .then((res) => {
        if (res.data.code === 1) {
          ElMessage.success('优惠券发放成功！')
        } else {
          ElMessage.error(res.data.msg)
        }
      })
      .catch(() => {
        ElMessage.error('优惠券发放请求失败！')
      })
  Visible.value = false
}
</script>

<template>
  <div class="dishBox">
    <h3>本店优惠券</h3>
    <el-table :data="cards" style="width: 50rem">
      <el-table-column label="图片" label- width="250">
        <template #default="scope">
          <img v-if="scope.row.编号 === '1'" :src="img1">
          <img v-else-if="scope.row.编号 === '2'" :src="img2">
          <img v-else :src="img3">
        </template>
      </el-table-column>
      <el-table-column prop="种类" label="种类" width="180"/>
      <el-table-column prop="金额" label="金额/￥"/>
      <el-table-column>
        <template #default="scope">
          <el-button type="primary" @click="open(scope.row.编号)" round>发放</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <el-dialog v-model="Visible" title="优惠券发放" center>
    <el-form :inline="true" :model="dishForm" class="demo-form-inline">
      <el-form-item label="发放给用户" label-width="280px">
        <el-select v-model="grant" placeholder="请选择" clearable>
          <template v-for="(item, index) in customers" :key="index">
            <el-option :label="item.姓名" :value="item.账号"/>
          </template>
        </el-select>
      </el-form-item>
    </el-form>

    <template #footer>
            <span class="dialog-footer">
                <el-button @click="Visible = false">取消</el-button>
                <el-button type="primary" @click="distribute">发放</el-button>
            </span>
    </template>
  </el-dialog>

</template>

<style scoped>

.dishBox {
  display: flex;
  width: 100%;
  flex-direction: column;
  align-items: center;
}
</style>