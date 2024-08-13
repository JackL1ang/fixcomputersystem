 
<template>
  <input v-model="sendAdmin.message"/>
  <button @click="sendMsg">给服务器发消息</button>
  <div v-for="item in list">
  <el-button @click="selectName(item)">{{item}}</el-button>

  </div>  
  {{ uuid }}

  <div> {{ data }}</div>
</template>
<script setup lang="ts">
import { useWebSocket } from '@vueuse/core'
import {v4 as uuidv4} from 'uuid'

//获取用户token
const token = useCookie('token').value
const role:Ref<string>|any  = ref()

const list = [
  "admin","user"

]


const sendAdmin = ref({
  myName:"",
  toName:"",
  message:"",
  uuid:uuidv4()
})
//链接服务器
const { status, data, send, open, close } = useWebSocket('ws://localhost:8080/chat?'+token)
  //token验证
$fetch('verifyToken',{
  //设置request的头部带的参数
  headers:{"token":token},
  baseURL: useRuntimeConfig().public.apiUrl,
}).then(res=>{
    sendAdmin.value.myName=res.username
})
const uuidList = []

watch(data,(newvalue,oldvalue)=>{
  // uuidList.forEach(val=>{
  //     if(val == data.Message.uuid){

  //     }else{
  //       uuidList.push(data.Message.uuid)
  //     }
  // })
  console.log(data.myName)

  console.log(oldvalue)
  console.log(newvalue)
})

    //消息内容
    const msg=ref('')

    const getMsg:any = ref()
    //创建对象 绑定接口
    // let ws= useWebSocket("ws://localhost:8080/chat",{
    //   onConnected:((ws)=>{
    //       console.log(ws)
    //   })
    // });
     const uuid =  uuidv4()

   console.log(status)


 
    //发送消息
    const sendMsg = ()=>{
  
        send(JSON.stringify(sendAdmin.value))
      
      
    }
    const getmsg = ()=>{
      console.log(data)
    }

    const selectName = (username: any)=>{
      sendAdmin.value.toName = username
    }
 
    //生命周期 挂载好的时候监听信息
   onMounted(()=>{
    
   })
</script>