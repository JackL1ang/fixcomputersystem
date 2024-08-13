<template>
<el-dialog
:model-value="usecompleteWaybill().value"
title="物流信息"
append-to-body
@closed="changeClose()"
>

<div v-if="waybill.msg.complete_waybill==null ">
    <el-form :model="form">
        <el-form-item label="快递单号：">

            <el-input v-model="form.complete_waybill" />
        </el-form-item>
    
    
    </el-form>
    <div  style="text-align: center;">
     <el-button type="primary" @click="commitComplete(waybill.msg.order_id)">提交</el-button>
    </div>
</div>
<div v-else>
    运单号：{{ waybillInfo?.LogisticCode }}
    <el-tag v-if="waybillInfo?.State==='3'" type="success">已签收</el-tag>
    <el-tag v-else type="danger">未签收</el-tag>
    <el-timeline v-if="waybillInfo?.Success" style="margin-top: 10px">
        <el-timeline-item
        v-for="(item,index) in waybillInfo.Traces"
        :key="index"
        :timestamp="item.AcceptTime"
        >
        {{ item.AcceptStation }}
        </el-timeline-item>
    </el-timeline>
    <div v-else>

        无物流信息

    </div>


</div>


</el-dialog>


</template>

<script setup lang="ts">
const changeClose = ()=>{
    usecompleteWaybill().value = !usecompleteWaybill().value
}
const waybill = defineProps({
    msg:{

    }
})
const waybillInfo = ref()
const search_waybillInfo = (val: string)=>{
    $fetch('/waybill/'+val,{
        baseURL:useRuntimeConfig().public.apiUrl,
        method:'GET',
    }).then(res=>{
        waybillInfo.value = res
    })
}



const form = reactive({
    order_id:'',
    complete_waybill:''

})

const commitComplete = (val: string)=>{
    form.order_id = val
    $fetch('/complete_waybill',{
        baseURL:useRuntimeConfig().public.orderUpdataUrl,
        method:'POST',
        body:form
    }).then((res)=>{
        if(res){
            console.log(waybill.msg)
            waybill.msg.complete_waybill = form.complete_waybill
            ElMessage({
                message: '提交成功',
                type: 'success',
            })
        }else{
            ElMessage.error('错误，请重试')
        }
    })
    
}

watch(waybill,(oldvalue,newvalue)=>{
    if(waybill.msg.complete_waybill != null){
    
    search_waybillInfo(waybill.msg.complete_waybill)
}
})
</script>