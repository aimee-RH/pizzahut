<script setup>
import { ref, onBeforeMount } from 'vue'
import { ElMessage } from 'element-plus'
import { Edit, Delete } from '@element-plus/icons-vue'
import deliverAxios from '@/axios/deliverAxios.js'
import { deliverStore } from '@/store/deliverStore.js'

const store = deliverStore()
const id = ref('')
const name = ref('')
const sex = ref('')
const phone = ref('')


onBeforeMount(() => {
    id.value = store.deliverID
    deliverAxios.get("/info/" + store.deliverID)
        .then((res) => {
            name.value = res.data.姓名
            sex.value = res.data.性别
            phone.value = res.data.电话
        })
        .catch(() => {
            ElMessage.error('配送员信息请求异常！')
        })
})

const baseVisible = ref(false)
function updateInfo() {
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

function submitInfo() {
    deliverAxios.post('/alterInfo', {
        账号: id.value,
        姓名: baseForm.value.name,
        性别: baseForm.value.sex,
        电话: baseForm.value.phone
    })
        .then((res) => {
            if (res.data.code === 1) {
                ElMessage.success('配送员信息修改成功！')
                setTimeout(() => {
                    window.location.reload();
                }, 1000);
            }
            else {
                ElMessage.error(res.data.msg)
            }
        })
        .catch(() => {
            ElMessage.error('配送员信息修改请求失败！')
        })
    baseVisible.value = false
}

</script>

<template>
    <div class="infoBox">
        <h1 style="font-size: large;">基本信息</h1>
        <el-button type="success" :icon="Edit" @click="updateInfo" circle/>
        <el-descriptions class="margin-top" :column="2" size="large" border >
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
        </el-descriptions>
    </div>

    <el-dialog v-model="baseVisible" title="配送员信息修改" center>
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
                <el-button type="primary" @click="submitInfo">确认</el-button>
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