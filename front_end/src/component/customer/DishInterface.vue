<script setup>
import { ref, onBeforeMount } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'
import { customerStore } from '@/store/customerStore.js'
import customerAxios from '@/axios/customerAxios.js'
import { InfoFilled } from '@element-plus/icons-vue'

const pizza = ref(new URL('@/assets/images/pizza.png', import.meta.url))
const beverage = ref(new URL('@/assets/images/beverage.png', import.meta.url))
const salad = ref(new URL('@/assets/images/salad.png', import.meta.url))
const Spaghetti = ref(new URL('@/assets/images/Spaghetti.png', import.meta.url))
const steak = ref(new URL('@/assets/images/steak.png', import.meta.url))
const router = useRouter()
const store = customerStore()

const dishes = ref([])
const dishList = ref([])
const discount = ref('')
const shops = ref([])
const addresses = ref([])
const orderList =ref ({
    用户: '',
    地址: '',
    门店: '',
    总金额: '',
    备注: '',
    用券: '否',
    特权: '否'
})
const canPrivilege = ref(false)
const privilegeTip = ref('')

onBeforeMount(() => {
    orderList.value.用户 = store.customerID;
    customerAxios.get("/dish")
        .then((res) => {
            dishes.value = res.data.dishes
            dishList.value = dishes.value.map(dish => ({ id: dish.编号, num: 0, money: dish.原价 }));
        })
        .catch(() => {
            ElMessage.error('菜品请求异常！')
        });
    customerAxios.get("/buyDishes/" + store.customerID)
        .then((res) => {
            discount.value = res.data.discount
            shops.value = res.data.shops;
            if ((1 - discount.value) > 0.1) {
                canPrivilege.value = res.data.canPrivilege
                privilegeTip.value = `会员特权每日只可使用一次，可将本订单中一个最高单价商品打${discount.value * 10}折`
            }
        })
        .catch(() => {
            ElMessage.error('购买信息请求异常！')
        });

    customerAxios.get("/addresses/" + store.customerID)
        .then((res) => {
            addresses.value = res.data.addresses;
        })
        .catch(() => {
            ElMessage.error('地址请求异常！')
        });
})

function filterTag(value, row) {
    return row.种类 === value;
}
function getListByIndex(index) {
    return dishList.value[index];
}

const availableDishes = ref([])

function alterShop(shopID) {
    customerAxios.get(`/availableDishes/${shopID}`)
        .then((res) => {
            availableDishes.value = res.data.availableDishes
        })
        .catch(() => {
            ElMessage.error("门店商品请求失败")
        })
}

function disabled(id) {
    if (availableDishes.value.includes(id)) {
        return false
    }
    dishList.value.find(item => item.id === id).num = 0
    return true
}

const rowState = ({ row }) => {
    let style = {}
    if (availableDishes.value.includes(row.编号)) {
        style = {
            backgroundColor: 'white'
        }
    }
    else {
        style = {
            backgroundColor: '#adadad'
        }
    }
    return style;
}

function calFloat(num1, num2, operator) {
    switch (operator) {
        case '+':
            return parseFloat((parseInt((num1 + num2) * 100) / 100).toFixed(2))
        case '-': 
            return parseFloat((parseInt((num1 - num2) * 100) / 100).toFixed(2))
        case '*': 
            return parseFloat((parseInt((num1 * num2) * 100) / 100).toFixed(2))
    }
}

const paymentList = ref([])
const list = ref([])
const dialogFormVisible = ref(false)
const rebate = ref({
    dish: '',
    money: 0
})
const usePrivilege = ref(false)

function confirmPayment() {
    paymentList.value = []
    list.value = []
    usePrivilege.value = false
    orderList.value.总金额 = 0
    for (let i = 0, j = 0; i < dishList.value.length; i++) {
        if (dishList.value[i].num > 0) {
            paymentList.value[j] = JSON.parse(JSON.stringify(dishList.value[i]))        
            paymentList.value[j].money = calFloat(dishList.value[i].num, dishList.value[i].money,'*')
            if (dishList.value[i].money > rebate.value.money) {
                rebate.value.dish = j
                rebate.value.money = dishList.value[i].money
            }
            list.value[j] = JSON.parse(JSON.stringify(paymentList.value[j]))
            list.value[j].name = dishes.value[i].名称

            orderList.value.总金额 = calFloat(orderList.value.总金额,paymentList.value[j++].money,'+')
        }
    }
                    console.log(discount.value)
    rebate.value.money = calFloat(rebate.value.money,(1 - discount.value),'*')
    if (paymentList.value.length === 0) {
        ElMessage.warning('您当前未选择任何商品');
        return
    }
    ElMessageBox.confirm('您已选择好了菜品及其数量，是否确认?', '即将生成订单中',{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        })
        .then(() => {
            dialogFormVisible.value = true;
        }).catch(() => {
            ElMessage('订单取消');
        });
}

function privilege() {
    if (usePrivilege.value) { 
        paymentList.value[rebate.value.dish].money = calFloat(paymentList.value[rebate.value.dish].money, rebate.value.money,'-')
        list.value[rebate.value.dish].money = calFloat(list.value[rebate.value.dish].money, rebate.value.money, '-')
        orderList.value.总金额 = calFloat(orderList.value.总金额, rebate.value.money, '-')
        orderList.value.特权 = '是'
    }
    else {
        paymentList.value[rebate.value.dish].money = calFloat(paymentList.value[rebate.value.dish].money, rebate.value.money, '+')
        list.value[rebate.value.dish].money = calFloat(list.value[rebate.value.dish].money, rebate.value.money, '+')
        orderList.value.总金额 = calFloat(orderList.value.总金额, rebate.value.money, '+')
        orderList.value.特权 = '否'
    }
}

function submitForm() {
    if (orderList.value.地址 != '' && orderList.value.门店 != '') {
        dialogFormVisible.value = false
        if (orderList.value.备注 === '') {
            orderList.value.备注 = '无'
        }
        customerAxios.post("/insertOrder", {
            paymentList: paymentList.value,
            orderList: orderList.value
        }) 
            .then((res) => {
                if (String(res.data.code) === '1') {
                    ElMessage.success('支付成功');
                    setTimeout(() => {
                        router.push('/customer/menu/order')
                    }, 1000);
                } else {
                    ElMessage.error(res.data.msg)
                }
            })
            .catch(() => {
                ElMessage.error('订单请求失败');
            })
    }
    else {
        ElMessage.error('请选择地址和门店');
    }
}

function cancelForm() {
    dialogFormVisible.value = false
    ElMessage('订单取消');
}

</script>

<template>
    <div class="dishBox">
        <el-form>
            <el-form-item>
                <el-icon size="30px" style="margin-right: 1rem;"><Shop /></el-icon>
                <el-select placeholder="请选择当前营业中的门店" v-model="orderList.门店">
                    <el-option v-for="(item, index) in shops" :key="index" :label=item.名称
                        :value=item.账号 @click="alterShop(item.账号)" />
                </el-select>
            </el-form-item>
        </el-form>
        <el-table :data="dishes" overflow:auto style="width: 80%;height: 100%;" max-height="470" :row-style="rowState" border>
            <el-table-column label="图片" width="180">
                <template #default="scope">
                    <img v-if="scope.row.种类 === '披萨'" :src="pizza">
                    <img v-else-if="scope.row.种类 === '饮料'" :src="beverage">
                    <img v-else-if="scope.row.种类 === '沙拉'" :src="salad">
                    <img v-else-if="scope.row.种类 === '意面'" :src="Spaghetti">
                    <img v-else :src="steak">
                </template>
            </el-table-column>
            <el-table-column prop="名称" label="名称" width="180" />
            <el-table-column prop="种类" label="种类" width="100" :filters="[{ text: '披萨', value: '披萨' }, { text: '饮料', value: '饮料' }, { text: '沙拉', value: '沙拉' },
            { text: '意面', value: '意面' }, { text: '牛排', value: '牛排' }]" :filter-method="filterTag"
                filter-placement="bottom-end">
            </el-table-column>
            <el-table-column prop="描述" label="描述" />
            <el-table-column prop="原价" label="原价/￥" />
            <el-table-column prop="数量" label="数量/份">
                <template #default="scope">
                    <el-input-number size="mini" :step="1" :min="0" :disabled="disabled(scope.row.编号)"
                    v-model="getListByIndex(scope.$index).num"></el-input-number>
                </template>
            </el-table-column>
        </el-table>
        <el-button type="success" @click="confirmPayment" style="margin-top: 2rem;" round>购买</el-button>
    </div>

    <el-dialog title="请填写订单详细内容" v-model="dialogFormVisible" center>
        <el-form>
            <el-form-item label="会员特权" label-width="200px" v-if="canPrivilege">
                <el-switch v-model="usePrivilege" inline-prompt active-text="使用" inactive-text="不使用" @click="privilege"/>
                <el-popover placement="top-start" title="会员特权使用规则" :width="200" trigger="hover" 
                :content='privilegeTip'>
                    <template #reference>
                        <el-icon style="margin-left: 0.8rem;" size="20px" color="rgb(69, 137, 255)"><InfoFilled /></el-icon>
                    </template>
                </el-popover>
            </el-form-item>
            
            <el-form-item label="地址" label-width="200px">
                <el-select placeholder="请选择您预留的地址" v-model="orderList.地址">
                    <el-option v-for="(item, index) in addresses" :key="index" :label=item :value=item></el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="备注" label-width="200px">
                <el-input v-model="orderList.备注" placeholder="请输入内容" type="textarea" :rows="2" maxlength="25"
                    show-word-limit />
            </el-form-item>
        </el-form>

        <el-table :data="list" stripe style="width: 100%">
            <el-table-column prop="name" label="菜名" width="238" />
            <el-table-column prop="num" label="数量" width="238" />
            <el-table-column prop="money" label="金额" width="238" />
        </el-table>

        <h1 class="money">
            总金额：<span style="font-family: 'Courier New', Courier, monospace;">{{ orderList.总金额 }}</span>
        </h1>

        <template #footer>
            <span class="dialog-footer">
                <el-button @click="cancelForm">取消订单</el-button>
                <el-button type="primary" @click="submitForm">立即支付</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<style scoped>
.money {
    text-align: center;
    font-size: large;
    font-family: Georgia, 'Times New Roman', Times, serif;
    color: crimson;
}

.dishBox {
    display: flex;
    width: 100%;
    flex-direction: column;
    align-items: center;
    margin-top: 0rem;
}
</style>