<template>
  <client-only>
    <div >
    <el-form
        class="form-body"
        :model="form"
        :inline="true"
        label-suffix=":"
        
        >
          <el-form-item label="订单号">
              <el-input
                  placeholder="请输入订单号"
                  clearable
                  v-model="form.orderNo"
              />
              
          </el-form-item>
            
          <el-form-item label="类别">
              <el-select v-model="form.category" clearable placeholder="请选择类别" style="width: 200px;">
                  <el-option v-for="item in category" :label="item.key" :value="item.value"/>
              </el-select>
            
          </el-form-item>
         
            <div class="button-order">
                    <el-button type="primary" @click="filterTableData">搜索</el-button>
                    <el-button>重置</el-button>
            </div>

      </el-form>
      </div>
        <div style="margin-top: 15px;">
          <el-table
          ref="multipleTableRef"
          :data="allOrder"
          style="width: 100%"
          >     
              <el-table-column type="selection" width="55" />
              <el-table-column property="orderId" label="订单号" width="180"/>
              <el-table-column property="category" label="维修商品名" width="200" align="center"/>
              
              <el-table-column property="userName" label="用户名" width="200" v-if="useAuth().value != 'user'"/>
              <el-table-column property="whetherMail" label="是否已邮寄" width="200" round>
                  <template #default="scope">
                    <el-tag v-if="scope.row.whetherMail===1" type="success" effect="plain" round>是</el-tag>
                    <el-tag v-if="scope.row.whetherMail===0" type="danger" effect="plain" round>
                      否
                    </el-tag>
                    <el-text v-if="scope.row.whetherMail===0 && useAuth().value == 'user' " type="danger" effect="plain" round>
                      请尽快进行邮寄
                    </el-text>
                  </template>
              </el-table-column>
              <el-table-column property="statusNum" label="状态" width="200">
                <template #default="scope">
                  <el-tag v-if="scope.row.statusNum===0 && !scope.row.rate_status" type="success">已完成</el-tag>
                  <el-tag v-if="scope.row.statusNum===0 && scope.row.rate_status" type="success">已评价</el-tag>
                  <el-tag v-if="scope.row.statusNum===1" type="primary">已下单</el-tag>
                  <el-tag v-if="scope.row.statusNum===2" type="primary">已接单</el-tag>
                  <el-tag v-if="scope.row.statusNum===3" type="warning">维修中</el-tag>
                  <el-tag v-if="scope.row.statusNum===4" type="danger">已取消</el-tag>
        
                </template>
                
                
              </el-table-column>
              <el-table-column property="create_date" label="创建日期" width="220"/>
              <el-table-column property="accept_date" label="接收日期" width="220"/>
              <el-table-column property="finish_date" label="完成日期" width="220"/>
              <el-table-column 
              label="操作" 
              fixed="right"
              width="200"
              >
              <template #default="scope">
                 <slot name="button" 
                 :orderid="scope.row.orderId" 
                 :status="scope.row.statusNum" 
                 :rate="scope.row.rate_status"
                 :whetherMail="scope.row.whetherMail"
                 :paystatus="scope.row.pay_status"
                 >
                 </slot>
                            
                
                <el-button
                type="danger"
                link
               v-if="(useAuth().value == 'user' && scope.row.statusNum==0) && !scope.row.pay_status"
               @click="payMoney(scope.row.orderId,scope.row.category)"
                >
                     去支付
                </el-button>

                <el-button
                type="danger"
                link
                @click.prevent="deletRow(scope.$index)"
                v-else
                >
                     删除 
                </el-button>
                
              </template>          
          </el-table-column>
      </el-table>
      
    </div>
    
    <!--分页-->
    <div class="pagination-block">
      <el-pagination
        v-model:current-page="currentPage4"
        v-model:page-size="pageSize4"
        layout="total, prev, pager, next, jumper"
        :total="countOrder"
        @current-change="handleCurrentChange"
        style="justify-content: center;"
      />
    </div>


  <child-page-dialog>
    <template v-slot:button>
      <slot name="button1"></slot>
    </template>
    <slot name="dialog"></slot>
  </child-page-dialog>
</client-only>
</template>
<script setup lang="ts">
const allOrder = ref()

const form = reactive({
    orderNo: '',
    category:''
})
//父组件 传过来 是否 修改值
const Info = defineProps({
  msg:{
    type:Boolean,
    default:false
  }
})



const category = [
    {key:"主板",value:"主板"},
    {key:"显卡",value:"显卡"},
    {key:"其他",value:"其他"}

]

const currentPage4 = ref(1)
const pageSize4 = ref(9)
const backupOrder = ref()
//分页
const handleCurrentChange = (val: number) => {
  $fetch(useRuntimeConfig().public.pageUrl+"/"+val,{
      method:'get',
      headers:{'token':useCookie('token').value}
  }).then(res=>{
    if(res.status){
      allOrder.value = res.order
    }
  })
}

const countOrder = ref()
const test = async(key='')=>{
  await nextTick()
  useFetch('/all',{
    method:'get',
    baseURL:useRuntimeConfig().public.orderUrl,
    headers:{'token':useCookie('token').value}
    }).then((res)=>{
      console.log(res)
      allOrder.value = res.data._rawValue.order
      backupOrder.value = res.data._rawValue.order
      countOrder.value = res.data._rawValue.orderCount
    })
}
test()


//删除订单
const deletRow = (index:number)=>{

  allOrder.value.splice(index,1)
}


const payForm = reactive({
  order_id:'',
  category:''
})

//支付
const payMoney = (order_id: any,category:any)=>{

  payForm.order_id = order_id
  payForm.category = category
  console.log(payForm)
  $fetch('/pay',{
    baseURL:useRuntimeConfig().public.apiUrl,
    method:'post',
    body:payForm
  }).then(res=>{
    //将后端对接支付宝的信息跳转为一个新页面
    let newWindows = window.open('','_blank')
    if(newWindows){
      newWindows.document.write(res)
    }

 
  })
}

const filterTableData = ()=>{

  if(form.orderNo=='' && form.category==null || form.orderNo=='' && form.category==''){
    test()
  }else if(form.category!=null && form.category !='' && form.category != 'undefined'){
    allOrder.value = []
    backupOrder.value.forEach((element: any) => {
      console.log(element.category)
      if(element.category === form.category){
        allOrder.value.push(element)
            
      }
      if(form.category == "其他"){
        if(element.category != "显卡" && element.category!="主板"){
          allOrder.value.push(element)
        }
      }

    });
    allOrder.value = allOrder.value.filter((orders: { orderId: string; })=>orders.orderId.includes(form.orderNo))
    
    
  }else{

    allOrder.value = allOrder.value.filter((order: { orderId: string; })=>order.orderId.includes(form.orderNo))

  }
  
  
}
</script>
<style scoped>

.button-order{
  display: contents;
}
.el-form--inline .el-form-item{
  margin-top: 25px;
}
</style>