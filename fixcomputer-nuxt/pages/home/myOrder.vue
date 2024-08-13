<template>
   <tableComponent :msg="transMessage">
     <!--nuxtlink预先加载不能接收v-slot值，使用prefetch禁止预先加载-->
     <template v-slot:button="{orderid,status,rate,whetherMail,paystatus}">
      <nuxt-link
        v-if="orderid && status==1"
        :to="{
          name:'home-myOrder-orderid',
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
                编辑</el-button>
        </nuxt-link>
        
        <nuxt-link
        v-if="orderid && status !=1"
        :to="{
          name:'home-myOrder-orderid',
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
                查看</el-button>
        </nuxt-link>
        
        <nuxt-link
        
        v-if="orderid && status==0 && !rate && paystatus"
        :to="{
          name:'home-myOrder-orderid',
          params: {
            orderid:orderid
          },
        }"
        custom
        :prefetch="false"
        v-slot="{href,navigate}">
            <el-button
            style="margin-left: -1px;"
                text
                type="warning"
                :href="href"
                size="default"
                @click="RateHandle(orderid)"
                @click.stop="navigate">
                评价</el-button>
        </nuxt-link>
        <nuxt-link
        to="https://ec.yto.net.cn/home"
        target="_blank"
        no-rel
        v-if="status==1&&whetherMail==0"
        >
          <el-button
          text
          type="warning"
          >
          去邮寄
          </el-button>
          
        </nuxt-link>
        
    </template>
    
    <template v-slot:button1>

      <el-button type="primary" 
        @click="QRCode(orderInfo.orderId)" 
        v-if="orderInfo?.statusNum==0">查看保修
      </el-button>
    </template>
    <template v-slot:dialog>
        <DialogUserOrderInfo :msg="orderInfo">
          <DialogQRCode :msg="QRCodeUrl"></DialogQRCode>
        </DialogUserOrderInfo>
    </template>
   </tableComponent>
   <DialogRate :msg="dialogValue" @handleRate="handleRate" >

   </DialogRate>
    
</template>
<script setup lang="ts">
const dialogValue = reactive({
    dialogControl:false,
    orderid:''
    
})

definePageMeta({
    layout:"home",
    meta:{
        id:2,
        auth:"user",
        title:"我的订单",
        
    }
})

const orderInfo = ref()
//查询当前订单信息
function drawerFuction(orderId: any){
  usedrawer().value = !usedrawer().value
  if(usedrawer().value){
    $fetch(useRuntimeConfig().public.orderUrl,{
      query:{orderId:orderId}
    }).then(res=>{
      
      orderInfo.value = res
      console.log(orderInfo.value)

    })
  }

}

//发送订单号给子组件
const RateHandle = (orderid: any)=>{
    
    dialogValue.dialogControl = true
    dialogValue.orderid = orderid
}

//子主键关闭后 传过来的值
const handleRate = (e:any)=>{
    
    dialogValue.dialogControl  = false
    
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
    if(res.status){
      useQRCode().value = !useQRCode().value
      QRCodeUrl.value = res
    }else{
      ElMessage.error('失败，请重试')
    }
   
  })
}

</script>
