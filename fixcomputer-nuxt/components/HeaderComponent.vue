<script setup lang="ts">
const title = '欢迎来到计算机维修系统'
//头中间打印组件
let index = 0 
let text = ref('')

//nuxt限制setInterval 需要在Mount内使用
onBeforeMount(() => {
  function autoTyping(){
    index++
    text.value=title.slice(0,index)+'_'
    if(text.value.length>title.length){
        text.value = title 
        clearInterval(late_Timer)
      }
}
  const late_Timer = setInterval(()=>{
  autoTyping()
},300)
})

</script>


<template>
<div class="index-header">
        <div class="bg"></div>
  <el-row>
    <el-col :xs="0" :sm="1">
          
    </el-col>     
    <el-col  :xs="2" :sm="9">
      
      <NuxtLink to="/">
        <el-row :gutter="10">
          <el-col :xs="24" :sm="3" :md="2">
            <img class="index-icon"  src="http://192.168.146.128:9000/fixcomputer/ico.svg" />
          </el-col>
          <el-col :xs="0" :sm="18" :md="20" style="margin-top: 5px">
            <span class="index-text">Fix</span>   
          </el-col>
        </el-row>
      </NuxtLink>
    </el-col>
    <el-col  :xs="12" :sm="9" style="margin-top: 2px;">
        {{text}}
    </el-col>
    <el-col  :xs="10" :sm="5" >
      <el-row>
        <slot>
          
        </slot>
       
        
      </el-row>
      
    </el-col>
        
    
    
  </el-row>
</div>
</template>

<style scoped>
.index-header{
    height: 40px;
    position: absolute;
    width: 100%;
    z-index: 20;
    line-height: 40px;
    color: white;
}
.el-header{
  --el-header-padding:0!important;
  --el-header-height:0px!important
  
}
.el-main{
  --el-main-padding:0!important;
  margin-top: 60px!important;
}
.el-footer{
  --el-footer-padding:0!important;
  --el-footer-height:0px!important
}
.index-header .bg{
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0,0,0,0.75) none;

}
.index-icon{
  width: 50px;
  margin-top: 6px;
}
.index-text{
  font-size: 20px;
  font-family:'Courier New', Courier, monospace 
}
.el-row{
  height: 40px;
}
.el-col .el-col-24{
  height: 40px;
}

</style>