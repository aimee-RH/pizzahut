<script setup>
import {ref, onBeforeMount} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import adminAxios from '@/axios/adminAxios.js';

const orders = ref([])
const deliveries = ref([])

onBeforeMount(() => {
  adminAxios.get("/order")
      .then((res) => {
        orders.value = res.data.orders
      })
      .catch(() => {
        ElMessage.error('订单请求异常！')
      });
  adminAxios.get("/deliveries")
      .then((res) => {
        deliveries.value = res.data.deliveries
      })
      .catch(() => {
        ElMessage.error('快递员请求异常！')
      });
})

function filterDelivery(value, row) {
  return row.配送员名 === value;
}

function filterState(value, row) {
  return row.配送状态 === value;
}

function remove(id) {
  ElMessageBox.confirm('您即将删除该订单，是否确认?', '删除订单', {
    confirmButtonText: '删除',
    cancelButtonText: '取消',
    type: 'warning',
  })
      .then(() => {
        adminAxios.get(`/deleteOrder/${id}`)
            .then((res) => {
              if (res.data.code === 1) {
                ElMessage.success('删除成功')
                setTimeout(() => {
                  window.location.reload()
                }, 1000)
              } else {
                ElMessage.error(res.data.msg)
                console.log(res.data.msg)
              }
            })
            .catch(() => {
              ElMessage.error('删除请求失败')
            })
      })
}

const Visible = ref(false)

const Form = ref({
  delivery: '',
  state: ''
})

const target = ref('')

function open(id, oldDelivery, oldState) {
  target.value = id
  Form.value.delivery = oldDelivery
  Form.value.state = oldState
  Visible.value = true
}

function submit() {
  adminAxios.post('/updateOrder', {
    编号: target.value,
    配送员: Form.value.delivery,
    配送状态: Form.value.state
  })
      .then((res) => {
        if (res.data.code === 1) {
          ElMessage.success('订单更新成功！')
          setTimeout(() => {
            window.location.reload();
          }, 1000);
        } else {
          ElMessage.error(res.data.msg)
        }
      })
      .catch(() => {
        ElMessage.error('订单更新请求失败！')
      })
  Visible.value = false
}
</script>

<template>
  <div class="orderBox">
    <h3>我的订单</h3>
    <el-table :data="orders" style="width: 90%" max-height="470">
      <el-table-column prop="编号" label="编号"/>
      <el-table-column prop="时间" label="时间" width="110px" />
      <el-table-column prop="用户名" label="用户"/>
      <el-table-column prop="地址" label="地址"/>
      <el-table-column prop="门店名" label="门店"/>
      <el-table-column prop="配送员名" label="配送员" :filters="[{ text: '暂未分配', value: '暂未分配' }]"
                       :filter-method="filterDelivery" filter-placement="bottom-end"/>
      <el-table-column prop="配送状态" label="配送状态" :filters="[{ text: '未配送', value: '未配送' },
                { text: '配送中', value: '配送中' }, { text: '已配送', value: '已配送' }]" :filter-method="filterState"
                       filter-placement="bottom-end"/>
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
          <el-button type="primary" @click="open(scope.row.编号, scope.row.配送员, scope.row.配送状态)" round>更新
          </el-button>
        </template>
      </el-table-column>

      <el-table-column>
        <template #default="scope">
          <el-button type="danger" @click="remove(scope.row.编号)" round>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <el-dialog title="更新订单" v-model="Visible" center>
    <el-form>
      <el-form-item label="配送员" label-width="250px">
        <el-select placeholder="请指定配送员" v-model="Form.delivery">
          <el-option v-for="(item, index) in deliveries" :key="index" :label=item.姓名 :value=item.编号></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="配送状态" label-width="250px">
        <el-select placeholder="请选择配送状态" v-model="Form.state">
          <el-option label="未配送" value="未配送"></el-option>
          <el-option label="配送中" value="配送中"></el-option>
          <el-option label="已配送" value="已配送"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="Visible = false" type="warning" round>返回</el-button>
      <el-button @click="submit" type="primary" round>更新</el-button>
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

