<template>
  <el-button @click="createNewAddress">新建</el-button>
  <div v-if="addressList.length">
    <el-card
    v-for="(address,index) in addressList"
    style="margin-top: 30px;"
    shadow="hover"
    >
      <el-descriptions
        :column="2"
        border
      >
      
        <template #extra>
          <el-button v-show="address.defaultAddress==false" @click="defaultButton(index,address.id)">默认地址</el-button>
          <nuxt-link
          :to="{
            name:'home-userCenter-userInfo-id',
            params:{
              id:index+1
            },
          }"
          custom
          v-slot="{href,navigate}"
          >
          <el-button type="primary" 
          :href="href"
          @click="drawerFunction(address)"
          @click.stop="navigate"
          >修改</el-button>
          </nuxt-link>
          <el-button type="danger" @click="deleteUserInfo(index)">删除</el-button>
        </template>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon >
                <user />
              </el-icon>
              姓名
            </div>
          </template>
          {{address.name}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon >
                <iphone />
              </el-icon>
              联系电话
            </div>
          </template>
          {{ address.phone }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template #label>
            <div class="cell-item">
              <el-icon >
                <office-building />
              </el-icon>
              Address
            </div>
          </template>
          {{codeToText[address.province]+codeToText[address.city]+codeToText[address.area]+address.address}}
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
  <div style="text-align: -webkit-center; margin-top: 200px;" v-else>
    <span class="el-table__empty-text">No Data</span>
  </div>

  <child-page-dialog :msg="true">
    <dialog-address-info>

    </dialog-address-info>
  </child-page-dialog>
  <child-page-drawer>
    <template v-slot:header>
        地址信息
    </template>
    <template v-slot:body>
      <drawer-addressInfo :msg="drawerAddress " v-if="usedrawer1().value">

      </drawer-addressInfo>
    </template>
  </child-page-drawer>
</template>

<script setup lang="ts">
import {codeToText} from "element-china-area-data";

//控制弹窗
const createNewAddress = ()=>{
  
  usedrawer().value = !usedrawer().value
}
//后端接收的地址数组
const addressList = ref([])
//发送给抽屉的值
const drawerAddress = ref()
const drawerFunction = (address: any)=>{
  drawerAddress.value = address

  usedrawer1().value = !usedrawer1().value
}

//信息查询
const test = async(key='')=>{
  await nextTick()
  useFetch(useRuntimeConfig().public.userAddressUrl,{
    method:'get',
    headers:{"token":useCookie('token').value},

  }).then((res)=>{
    addressList.value = res.data._rawValue
    console.log(addressList.value)
  })
}
test()

definePageMeta({
  title:"个人信息"
})

//删除信息
const deleteUserInfo = (index: any)=>{
  addressList.value.splice(index,1)
}

//设置默认地址
const defaultButton = (index: any,id: any)=>{
  let old_id = ''
  addressList.value.forEach((element,index) => {
    if(element.defaultAddress){
      addressList.value[index].defaultAddress = false
      old_id = addressList.value[index].id
    }
    
  });
  addressList.value[index].defaultAddress = true
  $fetch(useRuntimeConfig().public.userAddressUrl+"/"+id+"/"+old_id,{
    method:'get',
    headers:{'token':useCookie('token').value}

  }).then(res=>{
    if(res){
      ElMessage({
        message: '修改成功',
        type: 'success',
      })
    }else{
      ElMessage.error('修改失败')
    }
  })
 
}
</script>