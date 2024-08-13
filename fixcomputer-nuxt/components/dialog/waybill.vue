<template>
<el-dialog
:model-value="useWayBill().value"
title="物流信息"
append-to-body
@closed="changeClose()"
:draggable="true"
>
运单号：{{ msg?.LogisticCode }}
<el-tag v-if="msg?.State==='3'" type="success">已签收</el-tag>
<el-tag v-else type="danger">未签收</el-tag>
<el-timeline v-if="msg?.Success" style="margin-top: 10px">
    <el-timeline-item
    v-for="(item,index) in msg.Traces"
    :key="index"
    :timestamp="item.AcceptTime"
    >
    {{ item.AcceptStation }}
    </el-timeline-item>
</el-timeline>
<div v-else>

    无物流信息

</div>
</el-dialog>

</template>

<script setup lang="ts">

defineProps({
    msg:{

    }
})

const changeClose = ()=>{
    useWayBill().value = !useWayBill().value
}


</script>