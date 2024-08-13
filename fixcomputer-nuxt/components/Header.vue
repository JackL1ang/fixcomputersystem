<script setup lang="ts">
import { useshowComponentLoginButtonComponent, useshowUserInfo } from '~/composables/login-states';



//显示登录按钮
const showComponentLoginButtonComponent = useshowComponentLoginButtonComponent().value
//显示用户信息
const showUserInfo = useshowUserInfo().value
//用户名
const username = useUsername().value


</script>

<template>

  <HeaderComponent>
       <LoginButtonComponent v-if=showComponentLoginButtonComponent>
          
        </LoginButtonComponent>
       
        <UserInfo v-if=showUserInfo :msg = username>
         
        </UserInfo>
  </HeaderComponent>


</template>
<style scoped>
.el-header{
  --el-header-padding:0 0px!important;
  --el-header-height:0px!important
  
}
.el-main{
  --el-main-padding:0px;
  margin-top:50px
}

</style>