<script setup>
import {ref, onBeforeMount} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import {useRouter} from 'vue-router'
import {shopStore} from '@/store/shopStore.js'
import shopAxios from '@/axios/shopAxios.js'
import {InfoFilled} from '@element-plus/icons-vue'
import customerAxios from "@/axios/customerAxios";

const pizza = ref(new URL('@/assets/images/pizza.png', import.meta.url))
const beverage = ref(new URL('@/assets/images/beverage.png', import.meta.url))
const salad = ref(new URL('@/assets/images/salad.png', import.meta.url))
const Spaghetti = ref(new URL('@/assets/images/Spaghetti.png', import.meta.url))
const steak = ref(new URL('@/assets/images/steak.png', import.meta.url))
const router = useRouter()
const store = shopStore()

const dishes = ref([])
const availableDishes = ref([])

onBeforeMount(() => {
  shopAxios.get("/dish")
      .then((res) => {
        dishes.value = res.data.dishes
      })
      .catch(() => {
        ElMessage.error('菜品请求异常！')
      });
  shopAxios.get(`/availableDishes/${store.shopID}`)
      .then((res) => {
        availableDishes.value = res.data.availableDishes
      })
      .catch(() => {
        ElMessage.error('门店菜品请求异常！')
      });
})

function filterTag(value, row) {
  return row.种类 === value;
}

function isDisable(id) {
  if (availableDishes.value.includes(id)) {
    return false
  }
  return true
}

const rowState = ({row}) => {
  let style = {}
  if (availableDishes.value.includes(row.编号)) {
    style = {
      backgroundColor: 'white'
    }
  } else {
    style = {
      backgroundColor: '#adadad'
    }
  }
  return style;
}

function addDish(id){
  shopAxios.post("/addDish", {
    shopID: store.shopID,
    dishID: id
  })
      .then((res) => {
        if (String(res.data.code) === '1') {
          ElMessage.success('上架成功');
          setTimeout(() => {
            window.location.reload()
          }, 1000);
        }
        else {
          ElMessage.error(res.data.msg)
        }
      })
      .catch(() => {
        ElMessage.error('上架请求失败');
      })
}
function deleteDish(id){
  shopAxios.post("/removeDish", {
    shopID: store.shopID,
    dishID: id
  })
      .then((res) => {
        if (String(res.data.code) === '1') {
          ElMessage.success('下架成功');
          setTimeout(() => {
            window.location.reload()
          }, 1000);
        }
        else {
          ElMessage.error(res.data.msg)
        }
      })
      .catch(() => {
        ElMessage.error('下架请求失败');
      })
}
</script>

<template>
  <div class="dishBox">
    <el-table :data="dishes" overflow:auto style="width: 80%;height: 100%;" max-height="580"
              :row-style="rowState" border>
      <el-table-column label="图片" width="180">
        <template #default="scope">
          <img v-if="scope.row.种类 === '披萨'" :src="pizza">
          <img v-else-if="scope.row.种类 === '饮料'" :src="beverage">
          <img v-else-if="scope.row.种类 === '沙拉'" :src="salad">
          <img v-else-if="scope.row.种类 === '意面'" :src="Spaghetti">
          <img v-else :src="steak">
        </template>
      </el-table-column>
      <el-table-column prop="名称" label="名称" width="180"/>
      <el-table-column prop="种类" label="种类" width="100" :filters="[{ text: '披萨', value: '披萨' }, { text: '饮料', value: '饮料' }, { text: '沙拉', value: '沙拉' },
            { text: '意面', value: '意面' }, { text: '牛排', value: '牛排' }]" :filter-method="filterTag"
                       filter-placement="bottom-end">
      </el-table-column>
      <el-table-column prop="描述" label="描述"/>
      <el-table-column prop="原价" label="原价/￥"/>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button v-if="isDisable(scope.row.编号)" type="success" @click="addDish(scope.row.编号)" style="margin-top: 2rem;" round>上架</el-button>
          <el-button v-else type="danger" @click="deleteDish(scope.row.编号)" style="margin-top: 2rem;" round>下架</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<style scoped>
.dishBox {
  display: flex;
  width: 100%;
  flex-direction: column;
  align-items: center;
  margin-top: 0rem;
}
</style>