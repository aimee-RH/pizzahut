<script setup>
import {ref, onBeforeMount} from 'vue'
import {ElMessage} from 'element-plus'
import {customerStore} from '@/store/customerStore.js'
import customerAxios from '@/axios/customerAxios.js'

const store = customerStore()
let orders = ref([])

onBeforeMount(() => {
  customerAxios.get("/order/" + store.customerID)
      .then((res) => {
        orders.value = res.data.orders
      })
      .catch(() => {
        ElMessage.error('订单请求异常！')
      });
})

const dialogFormVisible = ref(false)
const list = ref([])
const shop = ref([])
const deliver = ref([])
const hasDeliver = ref(false)

async function getDetail(orderID, shopID, deliverID) {
  await customerAxios.get(`/orderDetail/${orderID}/${shopID}/${deliverID}`)
      .then((res) => {
        list.value = res.data.list

        shop.value = []
        shop.value.push(res.data.shop)

        deliver.value = []
        deliver.value.push(res.data.deliver)

        if (deliverID === '0') {
          hasDeliver.value = false
        } else {
          hasDeliver.value = true

        }
      })
      .catch(() => {
        ElMessage.error('订单详情请求异常！')
      });
  dialogFormVisible.value = true
}

function canDelete(time) {
  const twoMinutesAgo = new Date();
  twoMinutesAgo.setMinutes(twoMinutesAgo.getMinutes() - 2);
  const targetDate = new Date(time)

  if (targetDate >= twoMinutesAgo) {
    return true
  }
  return false;
}

function deleteOrder(id) {
  if(canDelete){
    customerAxios.post("/deleteOrder", {
      data: id
    })
        .then((res) => {
          if (String(res.data.code) === '1') {
            ElMessage.success('订单删除成功');
            setTimeout(() => {
              window.location.reload()
            }, 1000);
          } else {
            ElMessage.error(res.data.msg)
          }
        })
        .catch(() => {
          ElMessage.error('订单删除失败');
        })
  }
  else{
    ElMessage.warning("订单时间超过两分钟，无法撤回")
    setTimeout(() => {
      window.location.reload()
    }, 1000);
  }
}
</script>

<template>
  <div v-if="orders.length === 0">
    <el-empty style="margin-top: 12rem;" description="您未拥有任何订单"></el-empty>
  </div>

  <div v-else class="orderBox">
    <h3>我的订单</h3>
    <el-table :data="orders" style="width: 90%" max-height="470">
      <el-table-column prop="编号" label="编号"/>
      <el-table-column prop="时间" label="时间" width="110px" />
      <el-table-column prop="地址" label="地址" width="200px"/>
      <el-table-column prop="门店名" label="门店"/>
      <el-table-column prop="配送员名" label="配送员"/>
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
          <el-button type="primary" @click="getDetail(scope.row.编号, scope.row.门店, scope.row.配送员)" round>详情
          </el-button>
        </template>
      </el-table-column>

      <el-table-column>
        <template #default="scope">
          <el-button v-if="canDelete(scope.row.时间)" type="danger" @click="deleteOrder(scope.row.编号)" round>删除
          </el-button>
        </template>
      </el-table-column>

    </el-table>
  </div>


  <el-dialog title="订单详情" v-model="dialogFormVisible" center>
    <h1 class="title">菜品明细</h1>
    <el-table :data="list" stripe style="width: 100%">
      <el-table-column prop="名称" label="菜名" width="238"/>
      <el-table-column prop="数量" label="数量" width="238"/>
      <el-table-column prop="金额" label="金额" width="238"/>
    </el-table>

    <h1 class="title">门店信息</h1>
    <el-table :data="shop" stripe style="width: 100%">
      <el-table-column prop="账号" label="门店号"/>
      <el-table-column prop="名称" label="名称"/>
      <el-table-column prop="地址" label="地址"/>
      <el-table-column prop="电话" label="电话"/>
      <el-table-column prop="上班时间" label="上班时间"/>
      <el-table-column prop="下班时间" label="下班时间"/>
    </el-table>

    <div v-if="hasDeliver">
      <h1 class="title">配送员信息</h1>
      <el-table :data="deliver" stripe style="width: 100%">
        <el-table-column prop="账号" label="配送员号"/>
        <el-table-column prop="姓名" label="姓名"/>
        <el-table-column prop="性别" label="性别"/>
        <el-table-column prop="电话" label="电话"/>
      </el-table>
    </div>
    <template #footer>
      <el-button @click="dialogFormVisible = false" type="warning" round>返回</el-button>
    </template>

  </el-dialog>
</template>

<style scoped>
.title {
  font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
  font-size: large;
  text-align: center;
}

.orderBox {
  display: flex;
  width: 100%;
  height: 35rem;
  flex-direction: column;
  align-items: center;
  margin-top: 2rem;
}
</style>

