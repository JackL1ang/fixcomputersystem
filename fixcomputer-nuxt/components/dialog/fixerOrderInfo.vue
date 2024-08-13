<template>

    

<ClientOnly>


    

    <el-row>
        
       
        <el-steps style="width: 100%;" :active="useFixOrderStatus().value" simple>
            <el-step title="已接单" icon="Edit" />
            <el-step title="维修中" icon="Upload" />
            <el-step title="已完成" icon="Picture" />
            
        </el-steps>
        
        
    </el-row>
    
    
    <StepOne :msg="fixOrder.msg" v-if="useFixOrderStatus().value==1"/>

    
   
    <StepTwo :msg="fixOrder.msg.orderId" v-if="useFixOrderStatus().value==2"/>
     

   

    <StepThree :msg="fixOrder.msg.orderId" v-if="useFixOrderStatus().value==3"/>

    <slot></slot>

</ClientOnly>

</template>

<script setup lang="ts">

const fixOrder = defineProps({
    msg:{
        type:Object,
        default:{}
    }
})


onMounted(()=>{
    console.log(fixOrder.msg)
    //监听传过来的步骤
    watch(fixOrder,(oldvalue,newvalue)=>{
         if(newvalue.msg.statusNum == 2){
            useFixOrderStatus().value = 1
        }else if(newvalue.msg.statusNum == 3){
            useFixOrderStatus().value = 2
        }else if(newvalue.msg.statusNum == 0){
            useFixOrderStatus().value = 3
        }else{
            useFixOrderStatus().value = 0
        }

            
        
   
    })
})




</script>
