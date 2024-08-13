<template>

<el-form :model="store_Address" label-position="top" style="width: 100%;">
        
        <el-form-item label="城市">
            <el-input v-model="store_Address.name" style="width: 100%;"/>
        </el-form-item>
        <el-form-item label="地址">
            <el-input v-model="store_Address.address" style="width: 100%;"/>
        </el-form-item>
        <el-form-item label="坐标">
            <el-input v-model="store_Address.coordinate"/>
        </el-form-item>
        <div style="margin-top: 30px;">
        <el-button type="primary" @click="addAddess">确认</el-button>
        <el-button @click="cleanAll">重置</el-button>
        </div>
</el-form>

</template>


<script setup lang="ts">

const store_Address = reactive({
    name:'',
    address:'',
    coordinate:''
})

const addAddess = ()=>{
    $fetch(useRuntimeConfig().public.storeAddressUrl,{
        method:'post',
        body:store_Address
    }).then(res=>{
        if(res){

            ElMessage({
                showClose: true,
                message: '成功添加',
                type: 'success',
            })

           

            usedrawer1().value = !usedrawer1().value


            reloadNuxtApp({
                ttl:2000
            })
        }else{
            ElMessage({
                showClose: true,
                message: '有误，请重试',
                type: 'error',
            })
        }
    })
}

const cleanAll = ()=>{
    store_Address.name =''
    store_Address.address=''
    store_Address.coordinate =''
}

</script>