<template>
  <Header></Header>
    <el-container class="post-list-container">
      <div style="margin-top: 50px; text-align: -webkit-center;">论坛中心</div>
      <el-main>
        <div class="post-list">
          <el-card class="post-card" v-for="post in posts" :key="post.id">
            <div slot="header" class="clearfix">
              <span>{{ post.title }}</span>
              <el-button style="float: right; padding: 3px 0" type="text" @click="viewPost(post)">查看详情</el-button>
            </div>
            <div v-html="post.content"></div>
            <div class="post-meta">
              <span>作者：{{ post.author }}</span>
              <span>&nbsp;&nbsp;发布时间：{{ post.createdAt }}</span>
            </div>
          </el-card>
        </div>
      </el-main>
    </el-container>
  </template>
  
  <script lang="ts" setup>
  const postAll = defineProps({
    posts:{
      type:Object,
      default:{}
    }
  })

function removeHTMLTags(str) {
return str.replace(/<[^>]*>/g, '');
}

const postMessage = ()=>{
  postAll.posts.forEach(element => {
    console.log(element)
  });
}


  </script>
  
  <style scoped>
  .post-list-container {
    display: flex;
    flex-direction: column;
    height: 100vh;
  }
  
  .post-list {
    padding: 20px;
  }
  
  .post-card {
    margin-bottom: 20px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    border-radius: 8px;
    transition: all 0.3s ease;
  }
  
  .post-card:hover {
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
  }
  
  .post-meta {
    color: #888;
    font-size: 0.9em;
    margin-top: 10px;
  }
  </style>