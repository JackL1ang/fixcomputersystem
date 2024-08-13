<template>
  <ClientOnly>
    <el-card style="height: 100%;" shadow="hover">
      <el-table :data="tableData" style="width: 100%;margin-top: 20px;" >
          <el-table-column fixed prop="id" label="id" width="300" />
          <el-table-column fixed prop="username" label="用户名" width="300" />
          <el-table-column prop="password" label="密码" width="300" />
          <el-table-column prop="state" label="状态" width="300">
              <template #default="scope">
                  <el-tag v-if="scope.row.state===0" type="danger">注销</el-tag>
                  <el-tag v-if="scope.row.state===1" type="success">使用</el-tag>
              </template>
          </el-table-column>
          <el-table-column prop="role" label="角色" width="320">
              <template #default="scope">
                  <el-tag v-if="scope.row.role===0" type="danger">管理员</el-tag>
                  <el-tag v-if="scope.row.role===1" type="primary">普通用户</el-tag>
                  <el-tag v-if="scope.row.role===2" type="warning">维修员</el-tag>
                  
              </template>

          </el-table-column>
      
          
          <el-table-column 
            fixed="right" 
            label="操作" 
            width="100"
            >
            <template #default="scope">
              <nuxt-link
              :to="{
                name:'home-userInfoMage-id',
                params:{
                  id:scope.row.id
                }
              }"
              custom
              v-slot="{href,navigate}">
              <el-button link type="primary" size="small" @click="drawerFuction(scope.row.id)"
              @click.stop="navigate">修改</el-button>
              </nuxt-link>
              
              <el-button 
              link 
              size="small" 
              type="danger" 
              @click.prevent =deletRow(scope.$index)
              
              >删除</el-button>
            </template>
          </el-table-column>
      </el-table>
  </el-card>
    <ChildPageDrawer>
      <template v-slot:header>
          用户信息
      </template>
      <template v-slot:body>
        <drawer-userInfo :msg = userDate v-if="usedrawer1().value" @updateStatus="updateInfo">

        </drawer-userInfo>
      </template>
    </ChildPageDrawer>

  </ClientOnly>  
</template>

<script setup lang="ts">
//所有用户
const test = async()=>{
    await nextTick()
    useFetch('/all',{
    method:'get',
    baseURL:useRuntimeConfig().public.userUrl,

}).then((res)=>{

    tableData.value = res.data.value

})
}
test()


definePageMeta({
    layout:"home",
    meta:{
        id:2,
        auth:"admin",
        title:"用户信息管理",
        icon:"user"
    }
})
const userDate = ref()
//控制侧边栏
function drawerFuction(id: any)
{
  usedrawer1().value = !usedrawer1().value
  if(usedrawer1().value){
    $fetch(useRuntimeConfig().public.userUrl,{
      query:{userId:id}
    }).then(res=>{
      
      userDate.value = res
      
    })
  }
  
}
//接受所有用户信息
const tableData = ref()

const deletRow = (index:number)=>{
  
  tableData.value.splice(index,1)
}


//修改信息
const updateInfo = (res: any)=>{
    if(res){
      test()
    }
}
</script>