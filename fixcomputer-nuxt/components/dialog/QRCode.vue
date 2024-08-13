<template>
<el-dialog
:model-value="useQRCode().value"
title="保修信息"
append-to-body
@closed = "changeClose()"
>
<div style="text-align: -webkit-center;">
    <div>剩余时间：{{ QRcode.msg?.remainDate }}天</div>
    <el-image :src="QRcode.msg?.qrcode.url"></el-image>
    <div>
        <el-tag type="success" v-if="QRcode.msg?.guarantee==='1'">在保</el-tag>
        <el-tag type="danger" v-if="QRcode.msg?.guarantee==='0'">过保</el-tag>
    </div>
    <div>保修时间：{{ QRcode.msg?.qrcode.guaranteeDate }}</div>
    
</div>
</el-dialog>




</template>

<script setup lang="ts">

const QRcode = defineProps({
    msg:{
        type:Object,
    }
})


const changeClose = ()=>{
    useQRCode().value = !useQRCode().value
}


</script>