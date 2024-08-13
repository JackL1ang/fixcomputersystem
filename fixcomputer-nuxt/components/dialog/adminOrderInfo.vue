<template>
    <el-row>
        
        
        <el-steps style="width: 100%;" :active="useFixOrderStatus().value" simple>
            <el-step title="已下单" icon="Edit" />
            <el-step title="已接单" icon="Edit" />
            <el-step title="维修中" icon="Upload" />
            <el-step title="已完成" icon="Picture" />
        </el-steps>
        
        
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
            <template #extra>
                <el-button type="primary" round @click="userWayBill(formOrder.orderDetailInfo.waybill)" v-if="formOrder.orderDetailInfo.waybill!=''">用户物流信息</el-button>
                <el-button type="warning" round 
                v-if="useFixOrderStatus().value==4 && formOrder.orderDetailInfo.complete_waybill!=null" 
                @click="userWayBill(formOrder?.orderDetailInfo.complete_waybill)">维修员物流信息</el-button>
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
                <el-input v-model="formOrder.orderDetailInfo.orderId" :disabled="true"></el-input>
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
                <el-input v-model="formOrder.orderDetailInfo.userName" :disabled="true"/>
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
                <el-input :disabled="true" v-model="formOrder.orderDetailInfo.create_date"/>
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
                <el-input v-model="formOrder.orderDetailInfo.fixerId" :disabled="true"/>
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
                <el-input v-model="formOrder.orderDetailInfo.category"/>
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
                <el-input v-model="formOrder.orderDetailInfo.accept_date"  :disabled="true"/>
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
                <el-input v-model="formOrder.orderDetailInfo.name" />
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
                <el-input v-model="formOrder.orderDetailInfo.phone"/>
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                <div class="cell-item" >
                    <el-icon>
                        <Clock />
                    </el-icon>
                    完成时间
                </div>
                </template>
                <el-input v-model="formOrder.orderDetailInfo.finish_date" :disabled="true"/>
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
                <el-input v-model="formOrder.orderDetailInfo.waybill" />
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
            <el-input v-model="formOrder.orderDetailInfo.address" />
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
            <el-input v-model="formOrder.orderDetailInfo.notes" />
            </el-descriptions-item>
    </el-descriptions>
        <el-divider v-if="formOrder.repairInfo != null "/>
        <el-descriptions
        title="维修信息"
        :column="3"
        border
        v-if="formOrder.repairInfo!= null"
        >
            <el-descriptions-item label="故障原因"><el-input v-model="formOrder.repairInfo.fixSource"/></el-descriptions-item>
            <el-descriptions-item label="是否成功维修">
                <el-select v-model="formOrder.repairInfo.whetherFix" placeholder="Select" style="width: 240px">
                <el-option
                    v-for="item in options"
                    
                    :label="item.label"
                    :value="item.value"
                />
                </el-select>

            </el-descriptions-item>
            <el-descriptions-item label="维修内容"><el-input v-model="formOrder.repairInfo.fixText"/></el-descriptions-item>
        </el-descriptions>
        <el-divider v-if="formOrder.usePartsInfo.length != 0"/>
        <el-descriptions
        title="使用零件"
        :column="3"
        border
        v-if="formOrder.usePartsInfo.length != 0"
        >   <div v-for="item in formOrder.usePartsInfo">
                <el-descriptions-item label="零件名"><el-input v-model="item.parts_name"/></el-descriptions-item>
                <el-descriptions-item label="厂商"><el-input v-model="item.manufacturer"/></el-descriptions-item>
                <el-descriptions-item label="数量"><el-input v-model="item.amount"/></el-descriptions-item>
            </div>
        </el-descriptions>
        <div  style="margin-top: 20px; text-align:-webkit-center;">
            <el-button type="primary" @click="updateFixStatus">完成</el-button>
            <el-button @click="closeDialog">关闭</el-button> 
        </div>
    </el-form>
    <slot></slot>
    <dialog-waybill :msg="waybillInfo">
    
    </dialog-waybill>
</template>


<script lang="ts" setup>
const order_id = defineProps({
    msg:{
        type:String,
        default:''
    }
})



const formOrder = ref({
    orderDetailInfo:'',
    usePartsInfo:'',
    repairInfo:''

})
$fetch(useRuntimeConfig().public.fixinfoUrl,{
    method:'GET',
    query:{orderId:order_id.msg}
}).then(res =>{
    formOrder.value =res
    console.log(res)
    if(res.orderDetailInfo.statusNum == 0){
        useFixOrderStatus().value=4
    }else if (res.orderDetailInfo.statusNum == 4){
        useFixOrderStatus().value= -1
    }else{
        useFixOrderStatus().value = res.orderDetailInfo.statusNum
    }
    
})

//修改信息
const updateFixStatus = ()=>{
  
    $fetch(useRuntimeConfig().public.fixinfoUrl,{
        method:'PUT',
        body:formOrder.value
    }).then(res =>{
        if(res){
            ElMessage({
                message: '成功修改，即将关闭窗口',
                type: 'success',
            })
            usedrawer().value=!usedrawer().value

        }else{
            ElMessage.error('修改失败')
        }
    })
}
//选择框值
const options = [
  {
    value: true,
    label: '是',
  },
  {
    value: false,
    label: '否',
  }]
//关闭按钮
const closeDialog = ()=>{
    usedrawer().value=!usedrawer().value
    useRouter().back();
}

const waybillInfo = ref()
const userWayBill = (val: any)=>{
    useWayBill().value=!useWayBill().value
    $fetch('/waybill/'+val,{
        baseURL:useRuntimeConfig().public.apiUrl,
        method:'GET'
    }).then(res=>{

        waybillInfo.value = res
    })

}
</script>