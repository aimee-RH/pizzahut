<script setup>
import {ref, onBeforeMount} from 'vue'
import {ElMessage} from 'element-plus'
import {Edit, Delete} from '@element-plus/icons-vue'
import shopAxios from '@/axios/shopAxios.js'
import {shopStore} from '@/store/shopStore.js'

const store = shopStore()
const id = ref('')
const infoTable = ref('')


onBeforeMount(() => {
  id.value = store.shopID
  shopAxios.get("/info/" + store.shopID)
      .then((res) => {
        infoTable.value = res.data
      })
      .catch(() => {
        ElMessage.error('门店信息请求异常！')
      })
})

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

function validateTime(rule, value, callback) {
  if (value === '') {
    callback(new Error('请选择时间'))
  }
  else {
    callback()
  }
}

const infoRules = {
  name: [{validator: validateName, trigger: 'blur'}],
  address: [{validator: validateAddress, trigger: 'blur'}],
  phone: [{validator: validatePhone, trigger: 'blur'}],
  start: [{validator: validateTime, trigger: 'blur'}],
  end: [{validator: validateTime, trigger: 'blur'}]
}

const infoForm = ref({
  name: '',
  address: '',
  phone: '',
  start: '',
  end: ''
})
const infoVisible = ref(false)

function updateInfo() {
  infoForm.value.name = infoTable.value.名称
  infoForm.value.address = infoTable.value.地址
  infoForm.value.phone = infoTable.value.电话

  var startTime = new Date(infoTable.value.上班时间)
  infoVisible.value = true
}

function submitInfo() {
  shopAxios.post('/alterInfo', {
    账号: id.value,
    名称: infoForm.value.name,
    地址: infoForm.value.address,
    电话: infoForm.value.phone,
    上班时间: infoForm.value.start,
    下班时间: infoForm.value.end
  })
      .then((res) => {
        if (res.data.code === 1) {
          ElMessage.success('门店信息修改成功！')
          setTimeout(() => {
            window.location.reload();
          }, 1000);
        } else {
          ElMessage.error(res.data.msg)
        }
      })
      .catch(() => {
        ElMessage.error('用户信息修改请求失败！')
      })
  infoVisible.value = false
}
</script>

<template>
  <div class="infoBox">
    <h1 style="font-size: large;">基本信息</h1>
    <el-button type="success" :icon="Edit" @click="updateInfo" circle/>
    <el-descriptions class="margin-top" :column="3" size="large" border>
      <el-descriptions-item>
        <template #label>
          <el-icon>
            <shop/>
          </el-icon>
          账号
        </template>
        {{ id }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template #label>
          <el-icon>
            <EditPen/>
          </el-icon>
          名称
        </template>
        {{ infoTable.名称 }}
      </el-descriptions-item>

      <el-descriptions-item>
        <template #label>
          <el-icon>
            <OfficeBuilding/>
          </el-icon>
          地址
        </template>
        {{ infoTable.地址 }}
      </el-descriptions-item>

      <el-descriptions-item>
        <template #label>
          <el-icon>
            <Iphone/>
          </el-icon>
          电话
        </template>
        {{ infoTable.电话 }}
      </el-descriptions-item>

      <el-descriptions-item>
        <template #label>
          <el-icon>
            <AlarmClock/>
          </el-icon>
          上班时间
        </template>
        {{ infoTable.上班时间 }}
      </el-descriptions-item>

      <el-descriptions-item>
        <template #label>
          <el-icon>
            <AlarmClock/>
          </el-icon>
          下班时间
        </template>
        {{ infoTable.下班时间 }}
      </el-descriptions-item>

    </el-descriptions>
  </div>

  <el-dialog v-model="infoVisible" title="门店信息修改" center>
    <el-form :model="infoForm" :rules="infoRules" style="max-width: 600px">
      <el-form-item label="名称" label-width="200px" prop="name">
        <el-input v-model="infoForm.name"/>
      </el-form-item>

      <el-form-item label="地址" label-width="200px" prop="name">
        <el-input v-model="infoForm.address"/>
      </el-form-item>

      <el-form-item label="电话" label-width="200px" prop="phone">
        <el-input v-model="infoForm.phone"/>
      </el-form-item>

      <el-form-item label="上班时间" label-width="200px">
        <el-time-picker
            v-model="infoForm.start"
            value-format="HH:mm:ss"
            placeholder="上班时间"
            style="width: 100%"/>
      </el-form-item>

      <el-form-item label="下班时间" label-width="200px">
        <el-time-picker
            v-model="infoForm.end"
            value-format="HH:mm:ss"
            placeholder="下班时间"
            style="width: 100%"/>
      </el-form-item>
    </el-form>

    <template #footer>
            <span class="dialog-footer">
                <el-button @click="infoVisible = false">取消</el-button>
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

