<template>
    <el-col :xs="5" :sm="0" :md="12" :lg="16">
        
    </el-col>

    <el-col :xs="5" :sm="9" :md="3" :lg="3">
        <el-dropdown>
            <div class="index-avatar">
            <el-avatar :icon="UserFilled" :size="35"/>
            <div class="index-username">{{ msg }}</div>
            </div>
            <arrow-down/>
            <template #dropdown>
                <el-dropdown-menu>
                    <nuxt-link :to=name1
                    
                    
                    >
                        <el-dropdown-item>个人中心</el-dropdown-item>
                    </nuxt-link>
                    
                    <el-dropdown-item @click="loginOut">注销</el-dropdown-item>
                
                </el-dropdown-menu>
            </template>
        </el-dropdown>
        
        
    </el-col>
</template>


<script setup lang="ts">
import { UserFilled } from '@element-plus/icons-vue'

defineProps({
    msg: String,
})


async function loginOut(){
    useCookie('token').value = undefined
    await reloadNuxtApp()
    useshowComponentLoginButtonComponent().value = true
    useshowUserInfo().value = false
    useAuth().value =''
}

const name1 = "/home"+"/"+useAuth().value
</script>

<style scoped>
.el-avatar{
    margin-top: 3px;
}
.index-avatar{
    display: flex;
    align-items: center;
}
.index-username{
    margin-left: 5px;
    color: white;
}
</style>