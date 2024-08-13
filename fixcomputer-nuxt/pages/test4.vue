<template>
    <div class="chat-room">
      <div class="message-container" ref="messageContainer">
        <div v-for="(message, index) in messages" :key="index" class="message">
          <div :class="{ 'sent': message.sender === 'user', 'received': message.sender === 'bot' }">
            {{ message.content }}
          </div>
        </div>
      </div>
      <div class="input-container">
        <el-input
          placeholder="请输入消息"
          v-model="inputMessage"
          @keyup.enter="sendMessage"
        ></el-input>
        <el-button type="primary" @click="sendMessage">发送</el-button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        messages: [],
        inputMessage: ''
      };
    },
    methods: {
      sendMessage() {
        if (this.inputMessage.trim() === '') return;
        this.messages.push({ content: this.inputMessage, sender: 'user' });
        this.inputMessage = '';
        this.scrollToBottom();
        this.replyMessage();
      },
      replyMessage() {
        // 模拟回复消息，这里可以替换成你的具体逻辑
        setTimeout(() => {
          this.messages.push({ content: '这是机器人的回复', sender: 'bot' });
          this.scrollToBottom();
        }, 500);
      },
      scrollToBottom() {
        this.$nextTick(() => {
          const container = this.$refs.messageContainer;
          container.scrollTop = container.scrollHeight;
        });
      }
    }
  };
  </script>
  
  <style scoped>
  .chat-room {
    width: 400px;
    height: 500px;
    border: 1px solid #ccc;
    overflow-y: auto;
    padding: 10px;
  }
  
  .message-container {
    margin-bottom: 10px;
  }
  
  .message {
    margin-bottom: 5px;
  }
  
  .sent {
    text-align: right;
    color: blue;
  }
  
  .received {
    text-align: left;
    color: green;
  }
  
  .input-container {
    display: flex;
    align-items: center;
  }
  
  .el-input {
    flex: 1;
    margin-right: 10px;
  }
  </style>
  