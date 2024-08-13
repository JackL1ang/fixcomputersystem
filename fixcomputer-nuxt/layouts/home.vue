<template>

<div class="common-layout">
 
      <el-container style="height: 100vh">
        <!--侧边栏-->
        <div style="height: 100%;background-color: #545c64;">
          <el-menu
            active-text-color="#ffd04b"
            background-color="#545c64"
            class="el-menu-vertical-demo"
            default-active="2"
            text-color="#fff"
            :collapse="isCollapse"
            @open="handleOpen"
            @close="handleClose"
            router
            style="height: 100%"
            
          >
            <div >
              <h3 style="text-align:center;margin-top: 10px; color: white;" v-show="!isCollapse">后台管理系统</h3>
              <img src="http://192.168.146.128:9000/fixcomputer/ico.svg" style="width: 60px" v-show="isCollapse"/>
            </div>
            
            <div style="margin-top: 15px">

            </div>
            
            <SidebarMenus
            v-for="item in menues.result"
            :route = "item"
            >
            </SidebarMenus>
          
          </el-menu>
        </div>
            
            
          
        
        
        <!--主题内容-->
      <el-container>
        <el-header>
          <el-menu
            :default-active="activeIndex"
            class="el-menu-demo"
            mode="horizontal"
            :ellipsis="false"
            @select="handleSelect"
          >
            <el-menu-item index="0">
              
              <el-icon><Expand /></el-icon>
            
            </el-menu-item>
            <div class="flex-grow" />
            <el-dropdown trigger="hover">
              <div class="avatar-wrapper" style="margin-right: 20px;">
                <el-avatar size="small" src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" />
                <div> <h3>{{username}}</h3> </div>
                <el-icon class="el-icon--right">
                  <arrow-down />
                </el-icon>
               </div>
               <template #dropdown>
                  <el-dropdown-menu>
                    <nuxt-link to="/">
                      <el-dropdown-item>首页</el-dropdown-item>
                    </nuxt-link>
                    
                    <el-dropdown-item>修改头像</el-dropdown-item>
                    <el-dropdown-item @click="loginOut">注销</el-dropdown-item>
                  </el-dropdown-menu>
              </template>
            </el-dropdown>
            
          </el-menu>




        </el-header>
        <ClientOnly>
          <el-main>
            <NuxtPage />
          </el-main>
        </ClientOnly>
      </el-container>
    </el-container>
  
  </div>


    

</template>

<script lang="ts" setup>

const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
//侧边栏折叠
const isCollapse = ref(false)

const route = useRoute()
console.log(route.fullpath)
useMenus()
//菜单
const menues = useMenus()
//权限
const auth = useAuth()

console.log(auth.value)
console.log(menues)
const activeIndex = ref('1')
const handleSelect = (key: string, keyPath: string[]) => {
  if(key=='0'){
    isCollapse.value=!isCollapse.value
  }
}

//获取用户名
const username = useUsername().value


async function loginOut(){
    useCookie('token').value = undefined
    
    useshowComponentLoginButtonComponent().value = true
    useshowUserInfo().value = false
    reloadNuxtApp({
      path:'/',
      ttl:1000
    })
    
}


</script>


<style scope>
body{
  overflow: auto;
  margin: 0px
}

.el-menu-vertical-demo{
  height: 100%;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
  height: 100vh
  
}
.el-main{
  overflow: hidden;
}

.flex-grow {
  flex-grow: 1;
}
.avatar-wrapper{
  display: flex;
  align-items: center;
  position: relative;
  margin-top: 5px;
  height: 50px;
  line-height: 50px;
}
.el-header{
  --el-header-padding:0
}
</style>