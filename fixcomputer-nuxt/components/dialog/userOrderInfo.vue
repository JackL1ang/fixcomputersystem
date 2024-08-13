<template>
<el-row>
        <el-col :span="3"/>
        <el-col :span="18">
        <el-steps style="width: 100%;" :active="orderstatus" simple>
            <el-step title="已下单" icon="Edit" />
            <el-step title="已接单" icon="Edit" />
            <el-step title="维修中" icon="Upload" />
            <el-step title="已完成" icon="Picture" />
            <el-step title="评价" icon="Picture" />
        </el-steps>
        </el-col>
        <el-col :span="3"/>
</el-row>
<el-form 
    :model="formOrder"
    style="margin-top: 20px;"
  >
      <el-descriptions
      class="margin-top"
      :column="3"
      size="default"
      border
    >
        <template #title v-if="orderstatus!=1 && orderstatus!=-1">
            订单已开启不能修改与取消
        </template>
        <template #title v-if="orderstatus==-1">
            订单已取消
        </template>
        <template #title v-if="orderstatus==4 || orderstatus==5">
            <div >
                订单状态: 
                <el-tag v-if="fixOrder.msg.payStatus" type="success">已支付</el-tag>
                <el-tag v-else="fixOrder.msg.payStatus" type="danger">未支付</el-tag>
            </div>
           
           <div >
           维修金额: <el-tag>{{ fixOrder.msg.total_amount }}</el-tag>
            </div>

        </template>
       
        <template #extra>
            <el-button 
            type="primary" round 
            
            @click="watchWayBill(formOrder.waybill)"
            v-if="formOrder.waybill!=''"
            >查看物流信息</el-button>
            <el-button 
            type="warning" round 
            
            @click="watchWayBill(fixOrder.msg.complete_waybill)"
            v-if="fixOrder.msg.complete_waybill!=null &&(orderstatus==4||orderstatus == 5) && fixOrder.msg.payStatus"
            >寄回物流信息 </el-button>
           
            <el-button type="danger" round :disabled="disabledHandle" @click="cancelOrder(fixOrder.msg.orderId)">取消订单</el-button>
        </template>
        <el-descriptions-item>
            <template #label>
            <div class="cell-item">
                <el-icon>
                <Edit/>
                </el-icon>
                订单号
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
            <el-input :disabled="true" v-model="formOrder.create_date"/>
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
            <el-input v-model="formOrder.fixerId" :disabled="true"/>
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
            <el-input v-model="formOrder.category" :disabled="disabledHandle"/>
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
            <el-input v-model="formOrder.accept_date" :disabled="true"/>
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
            <el-input v-model="formOrder.name" :disabled="disabledHandle"/>
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
            <el-input v-model="formOrder.phone" :disabled="disabledHandle"/>
        </el-descriptions-item>
        <el-descriptions-item>
            <template #label>
            <div class="cell-item" >
                <el-icon>
                <Van />
                </el-icon>
                完成时间
            </div>
            </template>
            <el-input v-model="formOrder.finish_date" :disabled="true"/>
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
            <el-input v-model="formOrder.waybill" :disabled="disabledHandle"/>
        </el-descriptions-item>
        <el-descriptions-item>
            <template #label>
            <div class="cell-item">
                <el-icon>
                <office-building />
                </el-icon>
                地址
            </div>
            </template>
        <el-input v-model="formOrder.address" :disabled="disabledHandle"/>
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
        <el-input v-model="formOrder.notes" :disabled="disabledHandle"/>
        </el-descriptions-item>
  </el-descriptions>
  <el-divider v-if="orderstatus==5"/>
    <div v-if="orderstatus==5">
        <el-descriptions title="我的评价">
            <el-descriptions-item label="我的评分">

                <el-rate
                v-model="formOrder.rate_score"
                disabled
                show-score
                text-color="#ff9900"
                score-template="{value} 分"
            />

            </el-descriptions-item>
            <el-descriptions-item label="我的评价">{{formOrder.rate_content}}</el-descriptions-item>

        </el-descriptions>


    </div>


    <div  style="margin-top: 20px; text-align:-webkit-center;" v-if="orderstatus!=5">
        <el-button type="primary" @click="updateFixStatus(fixOrder.msg.orderId)" :disabled="disabledHandle">提交</el-button>
        <el-button @click="closeDialog">关闭</el-button> 
    </div>
</el-form>


<slot></slot>
<dialog-waybill :msg="waybillInfo">
    
</dialog-waybill>    
</template>
<script lang="ts" setup>


const formOrder = ref({
    name:"",
    phone:"",
    address:"",
    category:"",
    waybill:"",
    notes:""

})
//修改订单信息
const updateFixStatus = (id: any)=>{
    $fetch(useRuntimeConfig().public.orderUpdataUrl,{
        method:'PUT',
        body:formOrder.value,
        headers:{'token':useCookie('token').value}

    }).then(res =>{
        if(res){
            
            ElMessage({
                message: '修改成功，即将关闭',
                type: 'success',
            })
            setTimeout(() => {
                usedrawer().value = !usedrawer().value
            }, 1000);
            reloadNuxtApp({
                ttl:1000
            })
            
        }else{
            ElMessage.error('修改失败，请重试')
            emit('updateInfo',res)
        }       
        
    })
}

const closeDialog = ()=>{
    usedrawer().value = !usedrawer().value
}

const disabledHandle = ref(false)

const fixOrder = defineProps({
    msg:{
        type:Object,
        default:{}
    }
})
//取消订单
const cancelOrder = (id:any)=>{
    $fetch(id,{
        method:'GET',
        baseURL:useRuntimeConfig().public.orderUpdataUrl,
    }).then(res =>{
        if(res){
            ElMessage({
                message: '修改成功，即将关闭',
                type: 'success',
            })
            setTimeout(() => {
                usedrawer().value = !usedrawer().value
            }, 1000);
            
            reloadNuxtApp({
                ttl:1000
            })
            
        }else{
            ElMessage.error('修改失败，请重试')
        }       
        
    })
}

const orderstatus = ref()

watch(fixOrder,(oldvalue: { msg: { statusNum: number; }; },newvalue: any)=>{
    console.log(fixOrder)
    if(oldvalue.msg.statusNum==4){
        orderstatus.value =-1
    }else if(oldvalue.msg.statusNum==0){
        orderstatus.value = 4
    }else{
        orderstatus.value = oldvalue.msg.statusNum
    }
    formOrder.value =fixOrder.msg
    //取消按钮
    if(fixOrder.msg.statusNum!=1){
        
        disabledHandle.value = true
    }else{
        disabledHandle.value = false
    }

    if(fixOrder.msg.rate_status && orderstatus.value==4) {
        orderstatus.value = 5
    }

})

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