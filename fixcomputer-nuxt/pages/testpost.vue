<template>

</template>

<script setup lang="ts">
$fetch('/pay',{
  baseURL:useRuntimeConfig().public.apiUrl,
  method:'get'
}).then(res=>{
  console.log(res)
 const divForm = document.getElementsByTagName("div")
 if(divForm.length){
  document.body.removeChild(divForm[0])
 }
 const div = document.createElement("div");
        div.innerHTML = res; // data就是接口返回的form 表单字符串
        document.body.appendChild(div);
        //document.forms[0].setAttribute("target", "_blank"); // 新开窗口跳转
        document.forms[0].submit();

})



</script>
