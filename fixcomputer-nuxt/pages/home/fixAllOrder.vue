<template>

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
          :data="order"
          style="width: 100%"
          >     
              <el-table-column type="selection" width="55" />
              <el-table-column property="orderId" label="订单号" width="200"/>
              <el-table-column property="category" label="维修商品名" width="200" align="center"/>
              <el-table-column property="fixer_id" label="维修员id" width="200"/>
              <el-table-column property="userName" label="用户名" width="200"/>
              <el-table-column property="status" label="状态" width="100">
                <template #default="scope">
                  <el-tag v-if="scope.row.status===0" type="success">已完成</el-tag>
                  <el-tag v-if="scope.row.status===1" type="primary">已下单</el-tag>
                  <el-tag v-if="scope.row.status===2" type="warning">已接单</el-tag>
                  <el-tag v-if="scope.row.status===3" type="danger">维修中</el-tag>
                  <el-tag v-if="scope.row.status===4" type="danger">已取消</el-tag>
                </template>
                
              </el-table-column>
              <el-table-column property="create_date" label="创建日期" width="220"/>
              <el-table-column property="accept_date" label="接单日期" width="220"/>
              <el-table-column property="finish_date" label="完成日期" width="220"/>
            <el-table-column 
              label="操作" 
              fixed="right">
              <template #default="scope">
                  <nuxt-link
                    :to="{
                      name:'home-fixAllOrder-uid',
                      params: {
                        uid:scope.row.orderId,
                      },
                    }"
                    custom
                    v-slot="{href,navigate}">
                    <el-button
                      text
                      type="primary"
                      :href="href"
                      size="default"
                      @click="drawerFuction(scope.row.orderId,scope.row.status)"
                      @click.stop="navigate">
                      编辑 
                    </el-button>
                  </nuxt-link>
                
                  <el-button
                  type="danger"
                  @click="delectOrder(scope.row.orderId,scope.$index)"
                  link>
                        删除 
                  </el-button>
              </template>       
            </el-table-column>
      </el-table>
    </div>
  
    <!--抽屉-->
    
    <child-page-dialog >
        <template v-slot:header>
            订单详情
        </template>
        <template v-slot:button>
            <el-button type="warning" @click="QRCode(order_id)" v-if="order_status==0">查看保修信息</el-button>
        </template>
        <dialog-admin-order-info :msg = order_id v-if="usedrawer().value">
          <DialogQRCode :msg="QRCodeUrl"></DialogQRCode>
        </dialog-admin-order-info>
    </child-page-dialog>
  
    <div>

    </div>
    <!--分页-->
    <div>
      <el-pagination
        v-model:current-page="currentPage4"
        v-model:page-size="pageSize4"
        layout="total, prev, pager, next, jumper"
        :total="allOrderLength"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="justify-content: center;"
      />
  </div>
  

</template>
<script setup lang="ts">


definePageMeta({
    layout:"home",
    meta:{
        id:3,
        auth:"admin",
        title:"维修订单管理"
    }
})
const form = reactive({
    orderNo: '',
    category:''
})

const order = ref()


//搜索
const filterTableData = ()=>{
  if(form.orderNo=='' && form.category==null || form.orderNo=='' && form.category==''){
    order.value = backupOrder.value
    console.log(backupOrder.value)
    allOrderLength.value = allOrder.value.length
  }else if(form.category!=null && form.category !='' && form.category != 'undefined'){
    order.value = []
    allOrder.value.forEach((element: any) => {
      if(element.category === form.category){
            order.value.push(element)
            
      }
      if(form.category == "其他"){
        if(element.category != "显卡" && element.category!="主板"){
            order.value.push(element)
        }
      }

    });
    order.value = order.value.filter((orders: { orderId: string; })=>orders.orderId.includes(form.orderNo))
    allOrderLength.value = order.value.length
    
  }else{

    order.value = allOrder.value.filter((order: { orderId: string; })=>order.orderId.includes(form.orderNo))
    allOrderLength.value = order.value.length
  }
  
  
}


const category = [
    {key:"主板",value:"主板"},
    {key:"显卡",value:"显卡"},
    {key:"其他",value:"其他"}

]
const allOrder = ref()
const allOrderLength = ref()
const backupOrder = ref()

//请求后端数据
const test = async(key='')=>{
  await nextTick()
  $fetch('/all',{
    method:'get',
    baseURL:useRuntimeConfig().public.orderUrl,
    headers:{'token':useCookie('token').value}
    }).then((res)=>{

      allOrder.value = res.orderall
      order.value = res.order
      allOrderLength.value = res.orderall.length
      backupOrder.value = order.value
      
    
    })
}

test()

const order_id = ref()
const order_status=ref()
//控制弹框
const drawerFuction = (uid: any,status: any)=>{


  usedrawer().value = !usedrawer().value
  order_id.value =uid
  order_status.value = status

}

const currentPage4 = ref(1)
const pageSize4 = ref(9)



const handleSizeChange = (val: number) => {
  console.log(`${val} items per page`)
}
const handleCurrentChange = (val: number) => {
  $fetch(useRuntimeConfig().public.pageUrl+"/"+val,{
    method:'GET',
    headers:{"token":useCookie('token').value},

  }).then(res=>{
    if(res.status){
      order.value = res.order
    }else{
      
    }
    
  })
}

//删除订单
const delectOrder = (orderid: any,index: any)=>{

  order.value.splice(index,1)
  allOrderLength.value=allOrderLength.value - 1
}

const QRCodeUrl = ref()
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
</script>
<style scoped>

.button-order{
  display: contents;
}
.el-form--inline .el-form-item{
  margin-top: 25px;
}
</style>