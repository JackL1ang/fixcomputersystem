<template>
  <div style="text-align: -webkit-center; margin-top: 100px;">
    <el-card 
    style="width: 600px;"
    >
    <el-form
      ref="ruleFormRef"
      :model="ruleForm"
      status-icon
      :rules="rules"
      label-width="120px"
      class="demo-ruleForm"
    >
      <el-form-item label="旧密码" prop="oldPassword" style="margin-top: 10px;">
        <el-input type="password" v-model="ruleForm.oldPassword"/>
      </el-form-item>
      <el-form-item label="新密码" prop="pass" style="margin-top: 40px;">
        <el-input v-model="ruleForm.password" type="password" autocomplete="off" />
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPass" style="margin-top: 40px;">
        <el-input
          v-model="ruleForm.checkPass"
          type="password"
          autocomplete="off"
        />
      </el-form-item>
      <el-form-item style="margin-top: 30px;">
        <el-button type="primary" @click="submitForm(ruleFormRef)"
          >提交</el-button
        >
        <el-button @click="resetForm(ruleFormRef)">重置</el-button>
      </el-form-item>
    </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import type { FormInstance, FormRules } from 'element-plus'

const ruleForm = reactive({
  oldPassword:'',
  password: '',
  checkPass: '',
  
})

const ruleFormRef = ref<FormInstance>()

const checkOldPassword = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入旧密码'))
  } else {
    if (ruleForm.checkPass !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('checkPass', () => null)
    }
    callback()
  }
}

const validatePass = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请输入密码'))
  } else {
    if (ruleForm.checkPass !== '') {
      if (!ruleFormRef.value) return
      ruleFormRef.value.validateField('checkPass', () => null)
    }
    callback()
  }
}
const validatePass2 = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请再输入一次密码'))
  } else if (value !== ruleForm.password) {
    callback(new Error("两次密码不同"))
  } else {
    callback()
  }
}

const rules = reactive<FormRules<typeof ruleForm>>({
  password: [{ validator: validatePass, trigger: 'blur' }],
  checkPass: [{ validator: validatePass2, trigger: 'blur' }],
  oldPassword: [{ validator: checkOldPassword, trigger: 'blur' }],
})

const submitForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.validate((valid) => {
    if (valid) {
      $fetch('modifypw',{
        baseURL:useRuntimeConfig().public.userUrl,
        headers:{'token':useCookie('token').value},
        method:'post',
        body:ruleForm
      }).then(res=>{
        if(res){
          ElMessage({
            message: '修改成功，准备跳转',
            type: 'success',
            
          })

          setTimeout(() => {
            useCookie('token').value = undefined
            useshowComponentLoginButtonComponent().value = true
            useshowUserInfo().value = false
            reloadNuxtApp({
                path:'/login',
                ttl:1000
              })
          }, 1000);
        }
      })
    } else {
      ElMessage({
        message: '错误请重试.',
        type: 'error',
        
      })
      return false
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
definePageMeta({
    title:"修改密码",
    
})

</script>