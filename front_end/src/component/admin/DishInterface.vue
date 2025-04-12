<script setup>
import { ref, onBeforeMount } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Delete, Edit } from '@element-plus/icons-vue'
import { adminStore } from '@/store/adminStore.js'
import adminAxios from '@/axios/adminAxios.js';

const pizza = ref(new URL('@/assets/images/pizza.png', import.meta.url))
const beverage = ref(new URL('@/assets/images/beverage.png', import.meta.url))
const salad = ref(new URL('@/assets/images/salad.png', import.meta.url))
const Spaghetti = ref(new URL('@/assets/images/Spaghetti.png', import.meta.url))
const steak = ref(new URL('@/assets/images/steak.png', import.meta.url))
const store = adminStore()

const id = ref('')
const dishes = ref([])
const dishList = ref([])

onBeforeMount(() => {
    id.value = store.userID;
    adminAxios.get("/dish")
        .then((res) => {
            dishes.value = res.data.dishes
            dishList.value = dishes.value.map(dish => ({ id: dish.编号, num: 0 }));
        })
        .catch(() => {
            ElMessage.error('菜品请求异常！')
        });
})

function filterTag(value, row) {
    return row.种类 === value;
}

const addVisible = ref(false)
const dishForm = ref({
    名称: '',
    种类: '披萨',
    原价: '',
    描述: ''
})
function validateName(rule, value, callback) {
    if (value === '') {
        callback(new Error('请输入名称'))
    }
    else {
        callback()
    }
}

function validatePrice(rule, value, callback) {
    if (value === '') {
        callback(new Error('请输入原价'))
    }
    let num = Number(value) 
    if (isNaN(num)) {
        callback(new Error('请输入正确格式的价格'))
    }
    let str = value.split('.')
    if(str.length > 1 && str[1].length > 2){
        callback(new Error('小数点后不能超过两位数'))
    }
    if (num < 0 || num > 999) {
        callback(new Error('原价应在0~999元之间'))
    }
    else {
        callback()
    }
}

function validateDescribe(rule, value, callback) {
    if (value === '') {
        callback(new Error('请输入描述'))
    }
    else {
        callback()
    }
}

const rules = {
    名称: [{ validator: validateName, trigger: 'blur' }],
    原价: [{ validator: validatePrice, trigger: 'blur' }],
    描述: [{ validator: validateDescribe, trigger: 'blur' }]
}

function add() {
    dishForm.value = {
        名称: '',
        种类: '披萨',
        原价: '',
        描述: ''
    }
    addVisible.value = true
}

function submitAdd() {
    adminAxios.post("/insertDish", {
        名称: dishForm.value.名称,
        种类: dishForm.value.种类,
        原价: dishForm.value.原价,
        描述: dishForm.value.描述
    })
        .then((res) => {
            if (res.data.code === 1) {
                ElMessage.success('菜品添加成功！')
                setTimeout(() => {
                    window.location.reload();
                }, 1000);
            }
            else {
                ElMessage.error(res.data.msg)
            }
        })
        .catch(() => {
            ElMessage.error('菜品添加请求失败！')
        })
    addVisible.value = false
}

function remove (id){
    ElMessageBox.confirm('您即将删除该菜品，是否确认?', '删除菜品', {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning',
    })
        .then(() => {
            adminAxios.get(`/deleteDish/${id}`)
                .then((res) => {
                    if (res.data.code === 1) {
                        ElMessage.success('删除成功')
                        setTimeout(() => {
                            window.location.reload()
                        }, 1000)
                    }
                    else {
                        ElMessage.error(res.data.msg)
                    }
                })
                .catch(() => {
                    ElMessage.error('删除请求失败')
                })
        })
}

const editVisible = ref(false)
const dishID = ref('')

function edit(dish) {
    dishID.value = dish.编号
    dishForm.value.名称 = dish.名称,
    dishForm.value.种类 = dish.种类,
    dishForm.value.原价 = dish.原价,
    dishForm.value.描述 = dish.描述,
    editVisible.value = true
}

function submitEdit() {
    adminAxios.post("/editDish", {
        编号: dishID.value,
        名称: dishForm.value.名称,
        种类: dishForm.value.种类,
        原价: dishForm.value.原价,
        描述: dishForm.value.描述
    })
        .then((res) => {
            if (res.data.code === 1) {
                ElMessage.success('菜品编辑成功！')
                setTimeout(() => {
                    window.location.reload();
                }, 1000);
            }
            else {
                ElMessage.error(res.data.msg)
            }
        })
        .catch(() => {
            ElMessage.error('菜品编辑请求失败！')
        })
    editVisible.value = false
}
</script>

<template>
    <div class="dishBox">
        <el-table :data="dishes" overflow:auto style="width: 80%;height: 100%;" max-height="470" border>
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
            <el-table-column>
                <template #default="scope">
                    <el-button type="primary" :icon="Edit" @click="edit(scope.row)" circle />
                    <el-button type="danger" :icon="Delete" @click="remove(scope.row.编号)" circle />
                </template>
            </el-table-column>
        </el-table>
        <el-button type="success" @click="add" style="margin-top: 2rem;" round>➕新增菜品</el-button>
    </div>

    <el-dialog v-model="addVisible" title="新增菜品" center>
        <el-form :model="dishForm" :rules="rules" style="max-width: 600px">
            <el-form-item label="名称" label-width="200px" prop="名称">
                <el-input v-model="dishForm.名称" maxlength="10" show-word-limit/>
            </el-form-item>
            <el-form-item label="种类" label-width="200px">
                <el-radio-group v-model="dishForm.种类">
                    <el-radio label="披萨" />
                    <el-radio label="意面" />
                    <el-radio label="牛排" />
                    <el-radio label="饮料" />
                    <el-radio label="沙拉" />
                </el-radio-group>
            </el-form-item>
            <el-form-item label="原价" label-width="200px" prop="原价">
                <el-input v-model="dishForm.原价" />
            </el-form-item>

            <el-form-item label="描述" label-width="200px" prop="描述">
                <el-input v-model="dishForm.描述" type="textarea" :rows="3" maxlength="50" show-word-limit />
            </el-form-item>
        </el-form>

        <template #footer>
            <span class="dialog-footer">
                <el-button @click="addVisible = false">取消</el-button>
                <el-button type="primary" @click="submitAdd">添加</el-button>
            </span>
        </template>
    </el-dialog>

    <el-dialog v-model="editVisible" title="编辑菜品" center>
        <el-form :model="dishForm" :rules="rules" style="max-width: 600px">
            <el-form-item label="名称" label-width="200px" prop="名称">
                <el-input v-model="dishForm.名称" maxlength="10" show-word-limit/>
            </el-form-item>
            <el-form-item label="种类" label-width="200px">
                <el-radio-group v-model="dishForm.种类">
                    <el-radio label="披萨" />
                    <el-radio label="意面" />
                    <el-radio label="牛排" />
                    <el-radio label="饮料" />
                    <el-radio label="沙拉" />
                </el-radio-group>
            </el-form-item>
            <el-form-item label="原价" label-width="200px" prop="原价">
                <el-input v-model="dishForm.原价" />
            </el-form-item>

            <el-form-item label="描述" label-width="200px" prop="描述">
                <el-input v-model="dishForm.描述" type="textarea" :rows="3" maxlength="50" show-word-limit />
            </el-form-item>
        </el-form>

        <template #footer>
            <span class="dialog-footer">
                <el-button @click="editVisible = false">取消</el-button>
                <el-button type="primary" @click="submitEdit">确定</el-button>
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
    margin-top: 2rem;
}
</style>