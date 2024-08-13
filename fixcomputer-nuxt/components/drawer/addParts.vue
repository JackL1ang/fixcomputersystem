<template>
<el-form :model="partsInfo" label-position="top" style="width: 100%;">
        
        <el-form-item label="厂商">
            <el-input v-model="partsInfo.manufacturer" style="width: 100%;"/>
        </el-form-item>
        <el-form-item label="零件型号">
            <el-input v-model="partsInfo.name" style="width: 100%;"/>
        </el-form-item>
        <el-form-item label="价格">
            <el-input v-model="partsInfo.price"/>
        </el-form-item>
        <el-form-item label="库存">
            <el-input v-model="partsInfo.inventory"/>
        </el-form-item>
        <el-form-item label="操作人">
            <el-input v-model="partsInfo.operator" disabled/>
        </el-form-item>
        <div style="margin-top: 30px;">
        <el-button type="primary" @click="addParts">确认</el-button>
        <el-button @click="closeWindows">取消</el-button>
        </div>
</el-form>
</template>

<script setup lang="ts">
const emit = defineEmits(['updateInfo'])
const addParts = ()=>{
    $fetch('/add',{
        baseURL:useRuntimeConfig().public.partsUrl,
        method:'post',
        body:partsInfo
    }).then((value)=>{
        if(value){
            emit("updateInfo",value)
            ElMessage({
                type:'success',
                message:'入库成功',
                showClose:true
            })
            setTimeout(() => {
                usedrawer1().value = !usedrawer1().value
                partsInfo.name=''
                partsInfo.price=''
                partsInfo.manufacturer=''
                partsInfo.inventory=''
            }, 1000);
            
           
        }else{
            ElMessage({
                type:'error',
                message:'请重新再试',
                showClose:true
            })
            emit("updateInfo",value)
        }
    })
}
const partsInfo = reactive({
    name:"",
    price:"",
    manufacturer:"",
    inventory:"",
    operator:useUsername().value
})

const closeWindows = ()=>{
    usedrawer().value=!usedrawer().value;
   
}
</script>