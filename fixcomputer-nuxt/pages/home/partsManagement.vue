<template>
    <nuxt-page/>
    <div >
    <el-form
        class="form-body"
        :inline="true"
        label-suffix=":"
        
        >
          <el-form-item label="零件型号">
              <el-input
                  placeholder="请输入型号"
                  clearable
                  v-model="category"
              />
              
          </el-form-item>
         
            <div class="button-order">
                    <el-button type="primary" @click="searchPart">搜索</el-button>
                    <el-button>重置</el-button>
            </div>

      </el-form>
      <el-button
      @click="addParts"
      >新建</el-button>
      </div>
        <div style="margin-top: 15px;">
          <el-table
          ref="multipleTableRef"
          :data="allParts"
          style="width: 100%"
          >     
              <el-table-column type="selection" width="55" />
              <el-table-column property="partsId" label="零件编号" width="200"/>
              <el-table-column property="manufacturer" label="零件名" width="200" />
              <el-table-column property="name" label="零件型号" width="200"/>
              <el-table-column property="price" label="价格" width="200"/>
              <el-table-column property="inventory" label="库存" width="200"/>
              <el-table-column property="createDate" label="创建日期" width="220"/>
              <el-table-column property="operator" label="操作人" width="220"/>
             
    
              <el-table-column 
              label="操作" 
              fixed="right"
              >
              <template #default="scope">
                 
                  <nuxt-link
                    :to="{
                      name:'home-partsManagement-id',
                      params: {
                        id:scope.row.partsId,
                      },
                    }"
                    custom
                    v-slot="{href,navigate}">
                  <el-button
                    text
                    type="primary"
                    :href="href"
                    size="default"
                    @click="drawerFuction(scope.row.partsId)"
                    @click.stop="navigate">
                    编辑 
                  </el-button>
                </nuxt-link>
                            
                <el-button
                type="danger"
                link
                @click.prevent="deletRow(scope.$index)"
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
        :total="totalPage"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="justify-content: center;"
      />
  </div>

  <child-page-drawer :msg="true">
    <template v-slot:header>
      添加零件
    </template>
    <template v-slot:body>
      <drawer-add-parts @updateInfo="updateStatus">

      </drawer-add-parts>
    </template>
    
  </child-page-drawer>
<child-page-dialog>
  <template v-slot:header>
      详情
  </template>
  <dialog-parts-info :msg="partsInfo" @updateInfo="updateStatus">

  </dialog-parts-info>
  
</child-page-dialog>
</template>
<script setup lang="ts">
const totalPage = ref()
const allParts = ref()
const test = async(key='')=>{
  await nextTick()
  $fetch("/page/1",{
    baseURL:useRuntimeConfig().public.partsUrl,
    method:'get'
  }).then((res)=>{
    
    allParts.value =res.records
    totalPage.value = res.total
  })

}
test()
const updateStatus = (res: any)=>{
    if(res){
      test()
    }
}
const category = ref()
definePageMeta({
    layout:"home",
    meta:{
        id:3,
        auth:"hybrid",
        title:"备件管理",
        icon:"setting"
    }
})



const addParts = ()=>{
  usedrawer1().value = !usedrawer1().value

}
const partsInfo = ref()
//控制弹框
function drawerFuction(partsId: any){

  usedrawer().value = !usedrawer().value
  if(usedrawer().value){
    $fetch(useRuntimeConfig().public.partsUrl+'/'+partsId,{
      method:'get',
    }).then((res)=>{
      partsInfo.value=res
      
      
    })
  }
  
}
const currentPage4 = ref(1)
const pageSize4 = ref(9)

const handleSizeChange = (val: number) => {
  console.log(`${val} items per page`)
}
const handleCurrentChange = (val: number) => {
  console.log(category.value)
  if(category.value == '' || category.value==undefined){
      $fetch("/page/"+val,{
      baseURL:useRuntimeConfig().public.partsUrl,
      method:'get'
    }).then((res)=>{
      
      allParts.value =res.records
      totalPage.value = res.total
  })
  }else{
    $fetch(useRuntimeConfig().public.partsUrl+"/search",{
      method:'POST',
      body:{"name":category.value,"page":val}
    }).then((res)=>{
      
      allParts.value =res.records
      totalPage.value = res.total
  })
  }
  
}


const deletRow = (index:number)=>{
  allParts.value.splice(index,1)
}


const searchPart = ()=>{
  $fetch(useRuntimeConfig().public.partsUrl+"/search",{
      method:'POST',
      body:{"name":category.value,"page":1}
    }).then((res)=>{
      
      allParts.value =res.records
      totalPage.value = res.total
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