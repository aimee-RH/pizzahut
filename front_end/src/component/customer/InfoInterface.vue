<script setup>
import { ref, onBeforeMount } from 'vue'
import { ElMessage } from 'element-plus'
import { Edit, Delete } from '@element-plus/icons-vue'
import customerAxios from '@/axios/customerAxios.js'
import { customerStore } from '@/store/customerStore.js'

const store = customerStore()
const id = ref('')
const name = ref('')
const sex = ref('')
const phone = ref('')
const level = ref('')
const address = ref([])

onBeforeMount(() => {
    id.value = store.customerID
    customerAxios.get("/info/" + store.customerID)
        .then((res) => {
            name.value = res.data.name
            sex.value = res.data.sex
            phone.value = res.data.phone
            level.value = res.data.level
            address.value = res.data.address
        })
        .catch(() => {
            ElMessage.error('用户信息请求异常！')
        })
})

const baseVisible = ref(false)
function updateBase() {
    baseForm.value.name = name.value
    baseForm.value.sex = sex.value
    baseForm.value.phone = phone.value
    baseVisible.value = true
}


function validateName(rule, value, callback) {
    if (value === '') {
        callback(new Error('请输入姓名'))
    }
    if (value.length > 10) {
        callback(new Error('名字不能超过10个字'))
    } else {
        callback()
    }
}

function validatePhone(rule, value, callback) {
    if (value === '') {
        callback(new Error('请输入手机号'))
    }
    let newValue = value.replace(/[^0-9]/gi, '')
    if (value !== newValue) {
        callback(new Error('请输入正确的手机号'))
    } else if (newValue.length !== 11) {
        callback(new Error('请输入正确的手机号'))
    } else {
        callback()
    }
}

const baseRules = {
    name: [{ validator: validateName, trigger: 'blur' }],
    phone: [{ validator: validatePhone, trigger: 'blur' }]
}

const baseForm = ref({
    name: '',
    sex: '',
    phone: ''
})

function submitBase() {
    customerAxios.post('/alterBase', {
        账号: id.value,
        姓名: baseForm.value.name,
        性别: baseForm.value.sex,
        电话: baseForm.value.phone
    })
        .then((res) => {
            if (res.data.code === 1) {
                ElMessage.success('用户信息修改成功！')
                setTimeout(() => {
                    window.location.reload();
                }, 1000);
            }
            else {
                ElMessage.error(res.data.msg)
            }
        })
        .catch(() => {
            ElMessage.error('用户信息修改请求失败！')
        })
    baseVisible.value = false
}

const addrVisible = ref(false)
const addrForm = ref({
    addresses: []
})
function updateAddr() {
    addrForm.value.addresses = []
    address.value.forEach((item) => {
        addrForm.value.addresses.push(item)
    })
    addrVisible.value = true
}

function validateAddress(rule, value, callback) {
    if (value === '') {
        callback(new Error('请输入地址'))
    }
    if (value.length > 50) {
        callback(new Error('地址字数不能超过50字'))
    } else {
        callback()
    }
}


function addRow() {
    addrForm.value.addresses.push('');
};

function deleteRow(index) {
    addrForm.value.addresses.splice(index, 1)
}

function submitAddr() {
    customerAxios.post('/alterAddr', {
        id : id.value,
        addresses : addrForm.value.addresses
    })
        .then((res) => {
            if (res.data.code === 1) {
                ElMessage.success('地址信息修改成功！')
                setTimeout(() => {
                    window.location.reload();
                }, 1000);
            }
            else {
                ElMessage.error(res.data.msg)
            }
        })
        .catch(() => {
            ElMessage.error('地址信息修改请求失败！')
        })
    addrVisible.value = false
}
</script>

<template>
    <div class="infoBox">
        <h1 style="font-size: large;">基本信息</h1>
        <el-button type="success" :icon="Edit" @click="updateBase" circle/>
        <el-descriptions class="margin-top" :column="3" size="large" border >
            <el-descriptions-item >
                <template #label>
                    <el-icon><user /></el-icon>
                    账号
                </template>
                {{ id }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <el-icon><EditPen /></el-icon>
                    姓名
                </template>
                {{ name }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <el-icon><Iphone /></el-icon>
                    手机号
                </template>
                {{ phone }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <el-icon v-if="sex == '男'"><Male /></el-icon>
                    <el-icon v-if="sex == '女'"><Female /></el-icon>
                    性別
                </template>
                {{ sex }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <el-icon><TrophyBase /></el-icon>
                    等级
                </template>
                <span v-if="level === '1'">普通</span>
                <span v-else-if="level === '2'">VIP</span>
                <span v-else>SVIP</span>
            </el-descriptions-item>
        </el-descriptions>

        <h1 style="font-size: large;">预留地址</h1>
        <el-button type="primary" :icon="Edit" @click="updateAddr" circle/>
        <el-descriptions class="margin-top" :column="1" size="large" border>
            <template v-for="(item, index) in address" :key="index">
                <el-descriptions-item>
                    <template #label>
                        <el-icon><OfficeBuilding /></el-icon>
                        地址{{ index + 1 }}
                    </template>
                    {{item}}
                    </el-descriptions-item>
            </template>
        </el-descriptions>
    </div>

    <el-dialog v-model="baseVisible" title="用户信息修改" center>
        <el-form :model="baseForm" :rules="baseRules" style="max-width: 600px">
            <el-form-item label="姓名" label-width="200px" prop="name">
                <el-input v-model="baseForm.name" />
            </el-form-item>
            <el-form-item label="性别" label-width="200px">
                <el-radio-group v-model="baseForm.sex">
                    <el-radio label="男" />
                    <el-radio label="女" />
                </el-radio-group>
            </el-form-item>
            <el-form-item label="电话" label-width="200px" prop="phone">
                <el-input v-model="baseForm.phone" />
            </el-form-item>
        </el-form>

        <template #footer>
            <span class="dialog-footer">
                <el-button @click="baseVisible = false">取消</el-button>
                <el-button type="primary" @click="submitBase">确认</el-button>
            </span>
        </template>
    </el-dialog>

    <el-dialog v-model="addrVisible" title="地址信息修改" center>
        <el-form :model="addrForm" style="max-width: 600px">
            <el-form-item v-for="(addrRow, index) in addrForm.addresses" label-width="150px"
                :key="index" :label="'地址' + (index+1)" :prop="'addresses.'+ index"
                 :rules="[{ validator: validateAddress, trigger: 'blur' }]">
                <div style="display: flex;">
                    <el-input v-model="addrForm.addresses[index]" style="width: 30rem;"/>
                    <el-button type="danger" :icon="Delete" @click="deleteRow(index)" circle style="display: flex;margin-left: 2rem;"/>
                </div>
            </el-form-item>

            <el-form-item label-width="150px">
                <el-button type="info" @click="addRow" round>➕新地址</el-button>
            </el-form-item>
        </el-form>

        <template #footer>
            <span class="dialog-footer">
                <el-button @click="addrVisible = false">取消</el-button>
                <el-button type="primary" @click="submitAddr">确认</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<style scoped>
:deep .el-descriptions__body {
  width: 70%;
  font-size: large;
}
.el-descriptions__body {
  width: 70%;
}
.infoBox {
    display: flex;
    width: 100%;
    flex-direction: column;
    align-items: center;
    margin-top: 2rem;
}
.margin-top {
    display: flex;
    width: 100%;
    flex-direction: column;
    align-items: center;
    margin-top: 2rem;
}
</style>