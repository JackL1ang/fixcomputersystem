<template>

        <el-form
            :model="fixOrder.msg"
            style="margin-top: 20px;"
        >
            <el-descriptions
            class="margin-top"
            :column="3"
            size="default"
            border
            >
                <template #extra>
                    <el-button type="primary" round @click="watchWayBill(fixOrder.msg.waybill)" v-if="fixOrder.msg.waybill!=''">查看物流信息</el-button>
                </template>
                <el-descriptions-item>
                    <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <Edit/>
                        </el-icon>
                        订单编号
                       
                    </div>
                    </template>
                    <el-input v-model="fixOrder.msg.orderId" :disabled="true"></el-input>
                </el-descriptions-item>
                <el-descriptions-item >
                    <template #label>
                    <div class="cell-item" style="width: 100px;">
                        <el-icon>
                        <User />
                        </el-icon>
                        用户名
                    </div>
                    </template>
                    <el-input v-model="fixOrder.msg.userName" :disabled="true"/>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template #label>
                    <div class="cell-item" style="width: 100px;">
                        <el-icon>
                        <Clock />
                        </el-icon>
                        创建时间
                    </div>
                    </template>
                    <el-input :disabled="true" v-model="fixOrder.msg.create_date"/>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <UserFilled />
                        </el-icon>
                        维修员工号
                    </div>
                    </template>
                    <el-input v-model="fixOrder.msg.fixerId" :disabled="true"/>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <Tools />
                        </el-icon>
                        类别
                    </div>
                    </template>
                    <el-input v-model="fixOrder.msg.category"/>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template #label>
                    <div class="cell-item" style="width: 100px;">
                        <el-icon>
                        <tickets />
                        </el-icon>
                        接收时间
                    </div>
                    </template>
                    <el-input v-model="fixOrder.msg.accept_date" :disabled="true"/>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <UserFilled />
                        </el-icon>
                        姓名
                    </div>
                    </template>
                    <el-input v-model="fixOrder.msg.name" :disabled="true"/>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <office-building />
                        </el-icon>
                        手机号码
                    </div>
                    </template>
                    <el-input v-model="fixOrder.msg.phone" :disabled="true"/>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template #label>
                    <div class="cell-item" >
                        <el-icon>
                        <Van />
                        </el-icon>
                        快递单号
                    </div>
                    </template>
                    <el-input v-model="fixOrder.msg.waybill" :disabled="true"/>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <office-building />
                        </el-icon>
                        Address
                    </div>
                    </template>
                <el-input v-model="fixOrder.msg.address" :disabled="true"/>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <office-building />
                        </el-icon>
                        备注
                    </div>
                    </template>
                <el-input v-model="fixOrder.msg.notes" :disabled="true"/>
                </el-descriptions-item>
        </el-descriptions>
            <div  style="margin-top: 20px; text-align:-webkit-center;">
                <el-button type="primary" @click="updateFixStatus" :disabled="disabledButton">下一步</el-button>
                <el-button @click="closeDialog">关闭</el-button> 
            </div>
        </el-form>
        <dialog-waybill :msg="waybillInfo">
    
        </dialog-waybill>
</template>

<script setup lang="ts">
import type User from '~/pages/home/user.vue';



const disabledButton = ref(false)
//fixOrderInfo传来的消息
const fixOrder = defineProps({
    msg:{
        type:Object,
        default:{}
    }
})

//修改信息
const updateFixStatus = ()=>{
    
    if(fixOrder.msg.category==""){
        disabledButton.value = true
        ElMessage.error("类别不能为空，请输入类别")
        setTimeout(() => {
            disabledButton.value = false
        }, 1000);
        
        
    }else{
        console.log(fixOrder.msg.category)
        useFixOrderStatus().value = 2
        $fetch(useRuntimeConfig().public.orderUpdataUrl,{
        method:'post',
        body:{"category":fixOrder.msg.category,"order_id":fixOrder.msg.orderId}
        })
    }
    
}


//关闭按钮
const closeDialog = ()=>{
    usedrawer().value=!usedrawer().value
    useRouter().back();
    console.log(usedrawer().value)
}

const waybillInfo = ref()
const watchWayBill = (waybill: any)=>{
    useWayBill().value=!useWayBill().value
    $fetch('/waybill/'+waybill,{
        baseURL:useRuntimeConfig().public.apiUrl,
        method:'GET'
    }).then(res=>{
        console.log(res)
        waybillInfo.value = res
    })
}
</script>

