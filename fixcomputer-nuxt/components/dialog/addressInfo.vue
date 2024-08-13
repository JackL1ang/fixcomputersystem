<template>
<div style="text-align: -webkit-center; width: 600px;">
    
    <el-form
      :model="form"
      label-width="120px"
      
    >
      <el-form-item label="姓名" prop="pass">
        <el-input v-model="form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="电话" prop="checkPass">
        <el-input
          autocomplete="off"
          v-model="form.phone"
        />
      </el-form-item>
      <el-form-item label="地 区">
          <el-cascader
              size="large"
              :options ="regionData"
              v-model="selectedOptions"
              @change="handleChange"
          >
          </el-cascader>
      </el-form-item>
      <el-form-item label="详细地址">
          <el-input
              v-model="form.address"
              :rows="2"
              type="textarea"
              placeholder="请输入详细地址"
          />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="sumbitInfo"
          >提交
        </el-button>
        <el-button >重置</el-button>
      </el-form-item>
    </el-form>
    
  </div>

</template>
<script setup lang="ts">
import {regionData , codeToText} from "element-china-area-data";
//regionData插件中的地区
const selectedOptions = ref([])

const form = ref({
      name:'',
      phone:'',
      address:'',
      province:'',
      city:'',
      area:''


})

//提交信息
const sumbitInfo = ()=>{
  form.value.province = selectedOptions.value[0]
  form.value.city = selectedOptions.value[1]
  form.value.area = selectedOptions.value[2]
  $fetch(useRuntimeConfig().public.userAddressUrl,{
    method:'post',
    headers:{"token":useCookie('token').value},
    body:form.value

  }).then((res)=>{
    if(res){
      ElMessage({
        type:'success',
        message:"提交成功"
      })
      usedrawer().value=!usedrawer().value
      reloadNuxtApp({
        
        ttl:1000
      })
    }else{
      ElMessage.error("提交失败，请稍后重试")
    }
  })
}
</script>