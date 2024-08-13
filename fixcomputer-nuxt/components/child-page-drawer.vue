<template>
<client-only>
<el-drawer v-model="usedrawer1().value" :before-close="drawerClose" >
    <template #header="{ close, titleClass }">
        <h4 :class="titleClass">
            <slot name="header">

            </slot>
        </h4>
        
        
    </template>
    <slot name="body">
        
    </slot>


</el-drawer>
</client-only>

</template>

<script setup lang="ts">

const skip = defineProps({
    msg:{

    }
})
const drawerClose = ()=>{
    
   if(skip.msg){
    usedrawer1().value = false
   }else{
    useRouter().back();
    usedrawer1().value = false
   }
    
}


</script>