<script setup>
import {ref, onBeforeMount} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import adminAxios from '@/axios/adminAxios.js';
const Visible = ref(false)
const backups = ref([])

onBeforeMount(() => {
  adminAxios.get("/backups")
      .then((res) => {
        backups.value = res.data.backups
      })
      .catch(() => {
        ElMessage.error('备份记录请求异常！')
      });
})

function validateName(rule, value, callback) {
  const illegal = '/:\\*?"<>|';
  for (let word of value){
    if(illegal.indexOf(word)!=-1){
      callback(new Error('文件名不能包含:  '+illegal))
    }
  }
  callback()
}

const rules = {
  name: [{ validator: validateName, trigger: 'blur' }]
}

const form = ref({
  name: ''
})

function backup(){
  adminAxios.post("/backup",{
    data: form.value.name
  })
      .then((res) => {
        if (res.data.code === 1) {
          ElMessage.success('备份成功！')
          setTimeout(() => {
            window.location.reload();
          }, 1000);
        } else {
          ElMessage.error(res.data.msg)
        }
      })
      .catch(() => {
        ElMessage.error('备份请求失败！')
      })
}

function restore(id){
  adminAxios.post("/restore",{
    data: id
  })
      .then((res) => {
        if (res.data.code === 1) {
          ElMessage.success('恢复成功！')
          setTimeout(() => {
            window.location.reload();
          }, 1000);
        } else {
          ElMessage.error(res.data.msg)
        }
      })
      .catch(() => {
        ElMessage.error('恢复请求失败！')
      })
}
</script>

<template>
  <div class="orderBox">
    <h3>备份记录</h3>
    <el-table :data="backups" style="width: 90%" max-height="470">
      <el-table-column prop="编号" label="编号"/>
      <el-table-column prop="时间" label="时间"/>
      <el-table-column prop="文件名" label="文件名"/>
      <el-table-column width="110px" >
        <template #default="scope">
          <el-button type="danger" @click="restore(scope.row.编号)" round>恢复</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-button type="success" @click="Visible = true" style="margin-top: 2rem;" round>➕新增备份</el-button>
  </div>

  <el-dialog title="新建备份" v-model="Visible" center>
    <el-form :model="form" :rules="rules" style="max-width: 600px">
      <el-form-item label="名称" label-width="200px" prop="name">
        <el-input v-model="form.name"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="Visible = false" type="warning" round>返回</el-button>
      <el-button @click="backup" type="primary" round>新建</el-button>
    </template>

  </el-dialog>
</template>

<style scoped>
.orderBox {
  display: flex;
  width: 100%;
  height: 35rem;
  flex-direction: column;
  align-items: center;
  margin-top: 2rem;
}
</style>

