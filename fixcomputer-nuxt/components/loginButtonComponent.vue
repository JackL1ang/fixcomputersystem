<template>

        <el-col :xs="5" :sm="0" :md="12" :lg="12">
        
        </el-col>

        <el-col :xs="5" :sm="9" :md="3" :lg="3">
            <NuxtLink to="login"><el-button @click="onLogin">登 录</el-button></NuxtLink>
            
        </el-col>
        <el-col :xs="5" :sm="2" :md="3" :lg="3">

        </el-col>
        <el-col :xs="9" :sm="2" :md="3" :lg="5">
            <NuxtLink to="login">
                <el-button type="primary" @click="onRegister">注 册</el-button>
            </NuxtLink>
        </el-col> 

  

</template>



<script setup lang="ts">


const loginStates = useLoginStates()

const onRegister = ()=>{
    loginStates.value = true
    
}

const onLogin = ()=>{
    loginStates.value = false
}


</script> 