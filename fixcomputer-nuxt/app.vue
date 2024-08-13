<template>
  <NuxtLayout>
    <NuxtPage/>
  </NuxtLayout>
  
  
</template>


<script setup>
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

//nust 对 vueAPP进行封装
const nustApp = useNuxtApp()
for(const [key,component] of Object.entries(ElementPlusIconsVue)){
  nustApp.vueApp.component(key,component)
}
</script>
