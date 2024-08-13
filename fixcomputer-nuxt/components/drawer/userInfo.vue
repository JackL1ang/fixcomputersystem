<template>

    <client-only>
            <el-form :model="userInfo" label-position="top" style="width: 100%;">
                <el-form-item label="id">
                    <el-input :disabled=true v-model="userMsg.msg.id" style="width: 100%;"/>
                </el-form-item>
                <el-form-item label="用户名">
                    <el-input v-model="userInfo.username" style="width: 100%;"/>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="userInfo.password"/>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="userInfo.name"/>
                </el-form-item>
                <el-form-item label="电话号码">
                    <el-input v-model="userInfo.phone"/>
                </el-form-item>
                <el-form-item label="地区">
                    <el-cascader
                        size="large"
                        :options ="regionData"
                        v-model="selectedOptions"
                        style="width: 100%"
                    >
                    </el-cascader>

                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="userInfo.address"/>
                </el-form-item>
                <el-form-item label="角色">
                    <el-select
                    v-model="userInfo.role">
                    <el-option
                    v-for="item in role"
                    :key="item.key"
                    :label="item.value"
                    :value="item.key"
                    />
                    </el-select>
                </el-form-item>
                <el-form-item label="状态">
                    <el-select
                    v-model="userInfo.state">
                    <el-option
                    v-for="item in status"
                    :key="item.key"
                    :label="item.value"
                    :value="item.key"
                    />
                    </el-select>
                </el-form-item>
                <div style="margin-top: 30px;">
                <el-button type="primary" @click="modifyInfo(userInfo.id)">修改</el-button>
                <el-button @click="closeWindows">取消</el-button>
                </div>
            </el-form>
    </client-only>

</template>
<script setup lang="ts">
import {regionData} from "element-china-area-data";
//regionData插件中的地区值
const selectedOptions = ref([])

const userInfo = ref({
    username:"",
    password:"",
    name:"",
    phone:"",
    address:"",
    role:"",
    state:"",
    province:"",
    city:"",
    area:"",
})

const status = [
    {
        value:"使用",
        key:1
    },
    {
        value:"注销",
        key:0
    }
]
const role = [
    {
        value:"管理员",
        key:0
    },
    {
        value:"用户",
        key:1
    },
    {
        value:"维修员",
        key:2
    }
]
const userMsg = defineProps({
        msg:{
            type:Object,
            default:{}
        }
})
const emits = defineEmits(['updateStatus'])
//修改信息
const modifyInfo = (id: any)=>{
    userInfo.value.province = selectedOptions.value[0]
    userInfo.value.city = selectedOptions.value[1]
    userInfo.value.area = selectedOptions.value[2]
    console.log(userInfo.value)
    $fetch(useRuntimeConfig().public.userUrl,{
        method:'put',
        body:userInfo.value

    }).then((res)=>{

        if(res){
            emits('updateStatus',res)
            ElMessage({
                message: '修改成功，即将关闭窗口',
                type: 'success',
            })
            setTimeout(() => {
                usedrawer1().value=!usedrawer1().value;
            }, 1000);

        }else{
            emits('updateStatus',res)
            ElMessage.error('信息有误，请重试')
        }
    })
    
}

watch(userMsg,(newValue)=>{
    selectedOptions.value = []
    userInfo.value = userMsg.msg
    selectedOptions.value.push(userMsg.msg.province,userMsg.msg.city,userMsg.msg.area)
})

const closeWindows = ()=>{
    usedrawer1().value=!usedrawer1().value;
    selectedOptions.value = []
}
</script>

