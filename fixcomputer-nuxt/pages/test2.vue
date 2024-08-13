<template>
    <div class="chat-window">
      <el-card class="chat-header">
        <div class="header-content">
          <span class="chat-title">聊天窗口</span>
          <el-button class="close-button" @click="closeChat">关闭</el-button>
        </div>
      </el-card>
      <el-dialog
      :model-value="true"
      >


      </el-dialog>
  
      <div class="chat-messages">
        <div v-for="(message, index) in form.message" :key="index" class="message-item">
          <div :class="{ 'sent': message.sender === true, 'received': message.received === true }">
            {{ message.content }}
          </div>
        </div>
      </div>
  
      <div class="chat-input">
        <el-input
          v-model="form.inputMessage"
          placeholder="请输入消息..."
          @keyup.enter.native="sendMessage"
          :disabled="disabledButton"
        ></el-input>
        <el-button @click="sendMessage" :disabled=disabledButton>发送</el-button>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { useWebSocket } from '@vueuse/core'
  //获取用户token
  const token = useCookie('token').value
  //获取用户权限
  const role:Ref<string>|any  = ref()

  //获取用户名
  const username:Ref<string>|any = useUsername()

  //是否可用发送按钮
  const disabledButton = ref(true)

  const form =  ref({
    message:[],
    inputMessage:''
  })
  
  const sendUser = ref({
  toName:"user",
  message:""
})

const sendAdmin = ref({
  toName:"admin",
  message:""
})

const { status, data, send, open, close } = useWebSocket('ws://localhost:8080/chat?'+token)
$fetch('verifyToken',{
  //设置request的头部带的参数
  headers:{"token":token},
  baseURL: useRuntimeConfig().public.apiUrl,
  onResponse({request,options,response}){
    console.log('response._data.state'+response._data.state)
    const state = response._data.state.toLowerCase()
    if(state == 'true'){
        disabledButton.value = false
        role.value = response._data.role
    }else{

      close()

    }
    
  },

})
//发送信息
  const sendMessage = ()=>{
    
    if(form.value.inputMessage.trim() === '') return;
    if(role.value != "user"){
      console.log(123)
      sendUser.value.message=form.value.inputMessage
      form.value.message.push({content:form.value.inputMessage,sender:true})
      send(JSON.stringify(sendUser.value))
      form.value.inputMessage = '';
    
    }else{
      sendAdmin.value.message=form.value.inputMessage
      form.value.message.push({content:form.value.inputMessage,sender:true})
      send(JSON.stringify(sendAdmin.value))
      form.value.inputMessage = '';
    }
   
    
  }

  watch(data,async(newData,oldData)=>{
    form.value.message.push({content:newData,received:true})
    console.log(newData)
  })


  
  </script>
  
  <style scoped>
  .chat-window {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
  }
  
  .chat-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    background-color: #f5f7fa;
  }
  
  .header-content {
    display: flex;
    align-items: center;
  }
  
  .chat-title {
    margin-right: 10px;
    font-weight: bold;
  }
  
  .close-button {
    background-color: #f4623a;
    color: #fff;
    border: none;
    outline: none;
    padding: 5px 10px;
    cursor: pointer;
  }
  
  .chat-messages {
    flex: 1;
    padding: 10px;
    overflow-y: auto;
  }
  
  .message-item {
    margin-bottom: 10px;
  }
  

  .chat-input {
    display: flex;
    padding: 10px;
  }
  
  .el-input {
    flex: 1;
    margin-right: 10px;
  }
  .sent {
    text-align: right;
    color: blue;
  }
  
  .received {
    text-align: left;
    color: green;
  }
  
  </style>