<template>
    <el-dialog
    :model-value="dialogController"
    :draggable="true"
    :show-close="false"
    :before-close="handleClose"
    style="width: 1000px;"
    >
    <template #header="{ close, titleId, titleClass }">
      <div class="my-header">
        <h4 :id="titleId" :class="titleClass"><slot name="header"></slot></h4>
        <el-button type="danger" @click="handleClose">
          <el-icon class="el-icon--left"><CircleCloseFilled /></el-icon>
          关 闭
        </el-button>
      </div>
    </template>
    <el-card>
        <div>
            <el-row>
                <el-col :span="2"/>
                <el-col :span="10">
                    <div class="demo-rate-block" >
                        <span class="demonstration" >评分</span>
                        <el-rate v-model="form.rateScore"  size="large" />
                  </div>
                </el-col>
                <el-col :span="2">
                    <el-divider direction="vertical"  style="height: 100%;"/>
                </el-col>
                <el-col :span="10">
                    <div class="demo-rate-block">
                        <span class="demonstration" style="width: 300px;">评价</span>
                        <el-input 
                        v-model="form.rateContent" 
                        type="textarea" 
                        style="width: 300px;"
                        maxlength="30"
                        rows="10"
                        />
                    </div>
                </el-col>
                
            </el-row>
            
           
        </div>
        <div style="text-align: center; margin-top: 30px;" >
            <el-button @click="commitRate">提交</el-button>
        </div>
    </el-card>
    </el-dialog>
    
</template>

<script setup lang="ts">
const form = reactive({
  orderId:'',
  rateScore: 0,
  rateContent:''
})

const emit = defineEmits(['handleRate'])
const dialogController = ref()
//控制dialog开关
const dialogValue = defineProps({
    msg:{
        type:Object,
        
    }
})

//拦截窗口关闭动作
const handleClose = ()=>{
    //发送给父组件
    form.orderId=''
    form.rateScore = 0
    form.rateContent = ''
    emit('handleRate',false)
    dialogController.value=false
    useRouter().back();
    
  
}
onMounted(()=>{
    watch(dialogValue,(oldValue,newvalue)=>{
      console.log(dialogValue.msg)
      dialogController.value = newvalue.msg?.dialogControl
      form.orderId = newvalue.msg?.orderid
    
})
})

const commitRate =()=>{
    $fetch(useRuntimeConfig().public.rateUrl,{
      method:'post',
      body:form
    }).then(res=>{
      console.log(res)
      if(res){
        ElMessage({
          message: '提交成功，即将关窗口',
          type: 'success',
        })
        setTimeout(() => {
          handleClose()
          
        }, 1000);
        reloadNuxtApp({
          path:"/home/myOrder",
          ttl:10000
        })
          
      }
    })
  
}
</script>
<style scoped>
.my-header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  gap: 16px;
}
.demo-rate-block {
  padding: 30px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  display: inline-block;
  width: 49%;
  box-sizing: border-box;
}
.demo-rate-block:last-child {
  border-right: none;
}
.demo-rate-block .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 14px;
  margin-bottom: 20px;
}
</style>