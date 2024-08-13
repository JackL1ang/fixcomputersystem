<template>
  <client-only>
  <el-row style="margin-top: 20px;">
    <el-col :span="8" style="text-align: -webkit-center;">
      <el-card shadow="hover" style="height: 130px;">
        <el-statistic title="全部订单" :value="outputValue2" />
      </el-card>
      
    </el-col>
    <el-col :span="8" style="text-align: -webkit-center;">
      <el-card shadow="hover" style="height: 130px;">
        <el-statistic title="完成" :value="outputValue"/>
       
      
      </el-card>
    </el-col>
    <el-col :span="8" style="text-align: -webkit-center;">
      <el-card shadow="hover" style="height: 130px;">
      <el-statistic title="未完成" :value="outputValue1"/>
      </el-card>
    </el-col>
  </el-row>
  <el-row style="margin-top: 40px;">
    <el-col :span="12">
      <el-card shadow="hover" style="height: 100%;">
        <el-timeline style="max-width: 600px">
          <el-timeline-item timestamp="通知" placement="top" style="font-size: 20px;color:black">
            
            <el-card style="height: 230px;">
              <el-scrollbar style="height: 200px;">
              <div v-for="(item,index) in notice">
                <div>
                  <el-tag>标题:</el-tag>
                <el-button link @click="openDialog(item)"><h3>{{item.title}}</h3></el-button>
                </div>
                <div>
                  <el-tag>
                    发布时间:
                  </el-tag>
                  <el-button link>{{ item.createTime }}</el-button>
                </div>
              
                <el-divider v-if="notice.length!=index+1"/>
              </div>
            </el-scrollbar>
            </el-card>
          </el-timeline-item>
          <el-timeline-item timestamp="订单状态" placement="top">
            <el-card style="height: 230px;">
              <el-scrollbar style="height: 190px;">
                <div v-for="(item,index) in order">
                  <div style="width: 100%; margin-top: 10px">
                    <el-tag>订单号:</el-tag> {{ item.orderId }}
                  </div>
                  <div><el-tag type="info">状态:</el-tag>   
                    <el-tag v-if="item.status==0" type="success">
                        {{ item.statusName }}
                    </el-tag>
                    <el-tag v-if="item.status==1" type="primary">
                      {{ item.statusName }}
                    </el-tag>
                    <el-tag v-if="item.status==2" type="success">
                      {{ item.statusName }}
                    </el-tag>
                    <el-tag v-if="item.status==3" type="warning">
                      {{ item.statusName }}
                    </el-tag>
                    <el-tag v-if="item.status==4" type="danger">
                      {{ item.statusName }}
                    </el-tag>
                  </div>
                  <el-divider v-if="order.length!=index+1"/>
                 
                </div>
              </el-scrollbar>
            </el-card>
          </el-timeline-item>
          </el-timeline>



      </el-card>
      
    </el-col>
    
    <el-col :span="12" style="text-align: -webkit-center; height:600px;">
      
        <map-baidu></map-baidu>
      
    </el-col>
  </el-row>



  <child-page-dialog :msg="true">
 
  <dialog-notice-info :msg="noticeContext">

  </dialog-notice-info>
  </child-page-dialog>
</client-only>
</template>

<script setup lang="ts">
//完成
const completeOrder= ref(0)
//未完成
const incompleteOrder = ref(0)
//所有订单
const allOrder = ref(0)
//通知所有
const noticeContext = ref()
const outputValue = useTransition(completeOrder, {
  duration: 1000,
})
const outputValue1 = useTransition(incompleteOrder, {
  duration: 1000,
})
const outputValue2 = useTransition(allOrder, {
  duration: 1000,
})

$fetch(useRuntimeConfig().public.statisticUrl,{
  method:'GET',
  headers:{'token':useCookie('token').value}
}).then(res =>{
  completeOrder.value = res.completeOrder
  incompleteOrder.value = res.inCompleteOrder
  allOrder.value = res.allOrder

})
const notice = ref()
//获取通知
$fetch(useRuntimeConfig().public.notice,{
  method:'GET',
}).then(res=>{
  notice.value = res
})
const order =ref()
$fetch('status',{
  baseURL:useRuntimeConfig().public.orderUrl,
  method:'GET',
  headers:{'token':useCookie('token').value}
}).then(res=>{
  order.value = res
})

//打开通知
const openDialog = (item: any)=>{
  usedrawer().value=!usedrawer().value
  noticeContext.value = item
}

definePageMeta({
  layout:"home",
    meta:{
        id:0,
        auth:"user",
        title:"首页",
        icon:"home"
      
    }
})

</script>

