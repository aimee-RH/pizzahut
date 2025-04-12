<script setup>
import { ref, onBeforeMount } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { customerStore } from '@/store/customerStore.js'
import customerAxios from '@/axios/customerAxios.js'

const store = customerStore()
const router = useRouter()
const cards = ref('')
const img1 = ref(new URL("@/assets/images/card1.png", import.meta.url))
const img2 = ref(new URL("@/assets/images/card2.png", import.meta.url))
const img3 = ref(new URL("@/assets/images/card3.png", import.meta.url))

onBeforeMount(() => {
    customerAxios.get("/card/" + store.customerID)
        .then((res) => {
            cards.value = res.data.cards
        })
        .catch(() => {
            ElMessage.error('优惠券请求异常！')
        });
})

const FirstVisible = ref(false)
const SecondVisible = ref(false)
const ThirdVisible = ref(false)

const dishes = ref([])

const pizza = ref([])
const beverage = ref([])
const Spaghetti = ref([])
const steak = ref([])

const card = ref('')
const addresses = ref([])

const dishForm = ref({
    firstDish: '',
    secondDish: '',
    address: '',
    shop: '',
    note: ''
})

function use(cardID, shopID) {
    card.value = cardID
    dishForm.value.firstDish = ''
    dishForm.value.secondDish = ''
    dishForm.value.shop = shopID
    switch (cardID) {
        case '1': FirstVisible.value = true; break;
        case '2': SecondVisible.value = true; break;
        case '3': ThirdVisible.value = true;
    }
    customerAxios.get("/addresses/" + store.customerID)
        .then((res) => {
            addresses.value = res.data.addresses;
        })
        .catch(() => {
            ElMessage.error('地址请求失败')
        })
    if (dishes.value.length === 0)
    {
        customerAxios.get("/dish")
            .then((res) => {
                dishes.value = res.data.dishes
                dishes.value.forEach((item) => {
                    switch (item.种类) {
                        case '披萨': pizza.value.push(item); break;
                        case '意面': Spaghetti.value.push(item); break;
                        case '牛排': steak.value.push(item); break;
                        case '饮料': beverage.value.push(item); break;
                    }
                })
            })
            .catch(() => {
                ElMessage.error('菜品请求异常！')
            });
    }
}

function submitForm() {
    if (dishForm.value.firstDish === '') {
        ElMessage.warning('您还有菜品未选择')
        return
    }
    if (dishForm.value.firstDish != '3' && dishForm.value.firstDish === ''){
        ElMessage.warning('您还有菜品未选择')
        return
    }
    if (dishForm.value.address == '') {
        ElMessage.warning('请选择地址')
        return
    }
    if (dishForm.value.note === '') {
        dishForm.value.note = '无'
    }
    customerAxios.post("/useCard", {
        customerID: store.customerID,
        cardID: card.value,
        firstDish: dishForm.value.firstDish,
        secondDish: dishForm.value.secondDish,
        address: dishForm.value.address,
        shop: dishForm.value.shop,
        note: dishForm.value.note
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
            ElMessage.error('优惠券使用请求失败');
        })
}
</script>

<template>
    <div class="dishBox">
        <div v-if="cards.length === 0">
            <el-empty style="margin-top: 12rem;" description="您未拥有任何优惠券"></el-empty>
        </div>
        <div v-else class="dishBox">
            <h3>我的优惠券</h3>
        <el-table :data="cards" style="width: 50rem"  max-height="500">
            <el-table-column label="图片"  width="250">
                <template #default="scope">
                    <img v-if="scope.row.编号 === '1'" :src="img1">
                    <img v-else-if="scope.row.编号 === '2'" :src="img2">
                    <img v-else :src="img3">
                </template>
            </el-table-column>
            <el-table-column prop="种类" label="种类" />
            <el-table-column prop="门店名" label="门店" />
            <el-table-column prop="金额" label="金额/￥" />
            <el-table-column>
                <template #default="scope">
                    <el-button type="primary" @click="use(scope.row.编号, scope.row.门店号)" round>使用</el-button>
                </template>
            </el-table-column>
        </el-table>
        </div> 
    </div>

    <el-dialog v-model="FirstVisible" title="优惠券使用" center>
        <el-form :inline="true" :model="dishForm" class="demo-form-inline">
            <el-form-item label="牛排" label-width="90px">
                <el-select v-model="dishForm.firstDish" placeholder="请选择" clearable>
                    <template v-for="(item, index) in steak" :key="index">
                        <el-option :label="item.名称" :value="item.编号" />
                    </template>
                </el-select>
            </el-form-item>

            <el-form-item label="饮料" label-width="90px">
                <el-select v-model="dishForm.secondDish" placeholder="请选择" clearable>
                    <template v-for="(item, index) in beverage" :key="index">
                        <el-option :label="item.名称" :value="item.编号" />
                    </template>
                </el-select>
            </el-form-item>

            <el-form-item label="地址" label-width="250px">
                <el-select placeholder="请选择您预留的地址" v-model="dishForm.address">
                    <el-option v-for="(item, index) in addresses" :key="index" :label=item :value=item></el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="备注" label-width="250px">
                <el-input v-model="dishForm.note" placeholder="请输入内容" type="textarea" :rows="2" maxlength="25"
                    show-word-limit />
            </el-form-item>
        </el-form>

        <template #footer>
            <span class="dialog-footer">
                <el-button @click="FirstVisible = false">取消订单</el-button>
                <el-button type="primary" @click="submitForm" >立即支付</el-button>
            </span>
        </template>
    </el-dialog>

    <el-dialog v-model="SecondVisible" title="优惠券使用" center>
        <el-form :inline="true" :model="dishForm" class="demo-form-inline">
            <el-form-item label="意面" label-width="90px">
                <el-select v-model="dishForm.firstDish" placeholder="请选择" clearable>
                    <template v-for="(item, index) in Spaghetti" :key="index">
                        <el-option :label="item.名称" :value="item.编号" />
                    </template>
                </el-select>
            </el-form-item>

            <el-form-item label="饮料" label-width="90px">
                <el-select v-model="dishForm.secondDish" placeholder="请选择" clearable>
                    <template v-for="(item, index) in beverage" :key="index">
                        <el-option :label="item.名称" :value="item.编号" />
                    </template>
                </el-select>
            </el-form-item>

            <el-form-item label="地址" label-width="250px">
                <el-select placeholder="请选择您预留的地址" v-model="dishForm.address">
                    <el-option v-for="(item, index) in addresses" :key="index" :label=item :value=item></el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="备注" label-width="250px">
                <el-input v-model="dishForm.note" placeholder="请输入内容" type="textarea" :rows="2" maxlength="25"
                    show-word-limit />
            </el-form-item>
        </el-form>

        <template #footer>
            <span class="dialog-footer">
                <el-button @click="SecondVisible = false">取消订单</el-button>
                <el-button type="primary" @click="submitForm" >立即支付</el-button>
            </span>
        </template>
    </el-dialog>

    <el-dialog v-model="ThirdVisible" title="优惠券使用" center>
            <el-form :inline="true" :model="dishForm" class="demo-form-inline">
                <el-form-item label="披萨" label-width="250px">
                    <el-select v-model="dishForm.firstDish" placeholder="请选择" clearable>
                        <template v-for="(item, index) in pizza" :key="index">
                            <el-option :label="item.名称" :value="item.编号" />
                        </template>
                    </el-select>
                </el-form-item>

                <el-form-item label="地址" label-width="250px">
                    <el-select placeholder="请选择您预留的地址" v-model="dishForm.address">
                        <el-option v-for="(item, index) in addresses" :key="index" :label=item :value=item></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="备注" label-width="250px">
                    <el-input v-model="dishForm.note" placeholder="请输入内容" type="textarea" :rows="2" maxlength="25"
                        show-word-limit />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="ThirdVisible = false">取消订单</el-button>
                    <el-button type="primary" @click="submitForm" >立即支付</el-button>
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
}
</style>