<template>
    <el-form  label-position="top" style="width: 100%;" >
                <el-form-item label="姓名"  style="margin-top:10px">
                    <el-input  v-model="addressForm.name" style="width: 100%;" />
                </el-form-item>
                <el-form-item label="联系电话" style="margin-top:30px">
                    <el-input  v-model="addressForm.phone" style="width: 100%;"/>
                </el-form-item>
                <el-form-item label="地区" style="margin-top:30px">
                    <el-cascader
                        size="large"
                        :options ="regionData"
                        v-model="selectedOptions"
                        style="width: 100%"
                    >
                    </el-cascader>
                </el-form-item>
                <el-form-item label="具体地址" style="margin-top:30px">
                    <el-input v-model="addressForm.address"/>
                </el-form-item>
                <el-form-item label="是否默认地址" style="margin-top:30px">
                    <el-switch v-model="addressForm.value" inline-prompt active-text="是" inactive-text="否" />
                </el-form-item>
                
    </el-form>
    <div style="margin-top:50px">
    <el-button type="primary" @click="modifyAddress">
        确认
    </el-button>
    <el-button>
        取消
    </el-button>
    </div>
</template>
<script setup lang="ts">
import {regionData} from "element-china-area-data";
const addressForm= reactive({
    name:'',
    phone:'',
    address:'',
    value:false

})

//regionData插件中的地区值
const selectedOptions = ref([])
const addressInfo = defineProps({
    msg:{
        type:Object,
        default:{}
    }
})
addressForm.name = addressInfo.msg.name
addressForm.phone = addressInfo.msg.phone
addressForm.address =addressInfo.msg.address
addressForm.value =addressInfo.msg.defaultAddress
selectedOptions.value.push(addressInfo.msg.province,addressInfo.msg.city,addressInfo.msg.area)


const modifyAddress = ()=>{
    addressInfo.msg.province = selectedOptions.value[0]
    addressInfo.msg.city = selectedOptions.value[1]
    addressInfo.msg.area = selectedOptions.value[2]
    addressInfo.msg.name = addressForm.name
    addressInfo.msg.phone = addressForm.phone
    addressInfo.msg.default_address=addressForm.value
    addressInfo.msg.address = addressForm.address
    console.log(addressInfo.msg)
    $fetch(useRuntimeConfig().public.userAddressUrl,{
            method:'put',
            body:addressInfo.msg
    }).then(res=>{
        if(res){
            ElMessage({
                message: '修改成功，即将关闭窗口',
                type: 'success',
            })
            setTimeout(() => {
                usedrawer1().value = !usedrawer1().value
            }, 1000);
        }else{
            ElMessage.error('信息有误，请重试')
        }
    })
    
    

}


</script>

