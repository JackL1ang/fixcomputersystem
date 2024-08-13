<template>
  <ClientOnly>
  <TableComponent 
  
 
  >
  <!--nuxtlink预先加载不能接收v-slot值，使用prefetch禁止预先加载-->
    <template v-slot:button="{orderid}">
      <nuxt-link
        v-if="orderid"
        :to="{
          name:'home-orderMage-orderid',
          params: {
            orderid:orderid
          },
        }"
        custom
        :prefetch="false"
        v-slot="{href,navigate}">
        <el-button
          text
          type="primary"
          :href="href"
          size="default"
          @click="drawerFuction(orderid)"
          @click.stop="navigate">
          编辑
        </el-button>
    </nuxt-link>

    </template>
    <template v-slot:button1>
      <el-button type="primary" @click="QRCode(orderInfo.orderId)" v-if="useFixOrderStatus().value==3">查看二维码</el-button>
      <el-button type="warning" v-if="(useFixOrderStatus().value==1||useFixOrderStatus().value==2) && useAuth().value=='fixer'" @click="returnOrder(orderInfo.orderId)">转单</el-button>
    </template>
    
    <template v-slot:dialog>
   
      <dialog-fixerOrderInfo :msg="orderInfo">

        <DialogQRCode :msg="QRCodeUrl"></DialogQRCode>
          
      </dialog-fixerOrderInfo>

    </template>
        
      
  </TableComponent>
</ClientOnly>

</template>

<script setup lang="ts">


definePageMeta({
  layout:"home",
    meta:{
      id:3,
      auth:"fixer",
      title:"维修订单管理"
    }
})
const orderInfo = ref()
function drawerFuction(orderId: any){
  usedrawer().value = !usedrawer().value
  if(usedrawer().value){
    $fetch(useRuntimeConfig().public.orderUrl,{
      query:{orderId:orderId}
    }).then(res=>{
      orderInfo.value = res
    })
  }
  console.log(usedrawer().value)
}

const QRCodeUrl = ref()
/***
 * 
 * 生成二维码
 * 
 */
const QRCode =(order_id: any)=>{
  $fetch('/code/'+order_id,{
    baseURL:useRuntimeConfig().public.apiUrl,
    method:'GET'

  }).then(res=>{
    console.log(res)
    if(res.status){
      useQRCode().value = !useQRCode().value
      QRCodeUrl.value = res
    }else{
      ElMessage.error('失败，请重试')
    }
   
  })
}


const returnOrder =(order_id:any)=>{
  $fetch(useRuntimeConfig().public.orderUpdataUrl+'/fallback/'+order_id,{
        method:'GET'
  }).then(res=>{
      if(res){
        ElMessage({
          message: '成功转单',
          type: 'success',
        })
        setTimeout(() => {
          usedrawer().value = !usedrawer().value
        }, 1000);
        reloadNuxtApp({
          ttl:2000
        })
      }else{
        ElMessage.error('失败，请重试')
      }
  })
    
  
}
</script>