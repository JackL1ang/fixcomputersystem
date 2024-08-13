<template>
    <div>
        <div style="font-size: 30px;color: red;margin-top: -50px">
            <h1>
                <p>
                    {{ noticeText.msg.title }}
                </p>
            </h1>
            
        </div>
        <div style="margin-top: -30px;margin-left: 300px;color:silver;">作者：{{ noticeText.msg.author }}  时间：{{noticeText.msg.createTime}} </div>
    </div>
   
<div v-html="noticeText.msg.content"/>


</template>

<script setup lang="ts">

const noticeText =  defineProps({
  msg:{
    default:{},
    type:Object
  }
})

</script>

<style >
img{

    width: 200px;
}
</style>