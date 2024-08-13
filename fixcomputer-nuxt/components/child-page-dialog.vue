<template>
  <client-only>
    <el-dialog 
    :model-value="usedrawer().value" 
    :show-close="false"
    :before-close="handleClose"
    style="width: 1200px; text-align: -webkit-center;"
    :draggable="true"
    @closed="closedFunction"
    >
    <template #header="{ close, titleId, titleClass }">
      <div class="my-header">
        <h4 :id="titleId" :class="titleClass"><slot name="header"></slot></h4>
        <div>
          <slot name="button"></slot>
          <el-button type="danger" @click="handleClose">
            <el-icon class="el-icon--left"><CircleCloseFilled /></el-icon>
            关 闭
          </el-button>
        </div>
      </div>
    </template>
   
      <slot></slot>
    </el-dialog>
</client-only>
</template>

<script setup lang="ts">
//是否跳转
const skip =  defineProps({
  msg:{
    default:false,
    type:Boolean
  }
})


const handleClose = ()=>{
  
  //维修订单中 如不重置状态 会使用同一个组件
  useFixOrderStatus().value = ''
  //地址管理中 是否跳转 由于地址管理没有子路由
  
  if(skip.msg){
    usedrawer().value=!usedrawer().value
  }else{
  
    usedrawer().value=!usedrawer().value
    useRouter().back();
  }
  
  
}


const closedFunction = ()=>{
  if(useAuth().value=='fixer'){
    // reloadNuxtApp()
  }else{

  }
  
}


</script>

<style scoped>
.my-header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  gap: 16px;
}
</style>