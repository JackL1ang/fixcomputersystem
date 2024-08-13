<template>
  
  <Header></Header>
    <el-container class="post-list-container">
      <div style="margin-top: 50px; text-align: -webkit-center;">论坛中心</div>
      <el-main>
        <div class="post-list">
          <el-card class="post-card" v-for="(post,index) in allPosts" :key="post.sendId">
            <div>
              <div class="clearfix" style="display: flex">
                <div class="post-meta" >
                  标题：
                </div>
                <div style="margin-top: 8px">
                 {{ post.title }}
                </div>
            </div>
              <nuxt-link
              :to="{
                name:'allPost-id',
                params:{
                  id:post.sendId,
                },
              }"
              custom
              v-slot="{href,navigate}"
              >
              <el-button 
              style="float: right; padding: 3px 0" 
              type="primary" 
              @click="viewPost(index)"
              @click.stop="navigate"
              link
              >
              查看详情
            </el-button>
            </nuxt-link>
            <el-button 
              style="float: right; padding: 3px 0;margin-right: 10px;" 
              type="danger" 
              @click="deleteList(index)"
              link
              v-if="useAuth().value === 'admin' || post.author === useUsername().value"
              >
              删除
            </el-button>
            </div>
            <div style="display: flex">
                <div class="post-meta" >
                  内容：
                </div>
                <div v-html="post.content" style="margin-top: 8px;"></div>
            </div>
            <div class="post-meta">
              <span>作者：{{ post.author }}</span>
              <span>&nbsp;&nbsp;发布时间：{{ post.createTime }}</span>
              <span>&nbsp;&nbsp;评论数：{{ post.comment_size }}</span>
            </div>
          </el-card>
        </div>
        <div>
          <el-pagination
            v-model:current-page="currentPage4"
            v-model:page-size="pageSize4"
            layout="total, prev, pager, next, jumper"
            :total="allPost"
            @current-change="handleCurrentChange"
            style="justify-content: center; margin-top: 50px;"
          />
      </div>
      </el-main>
    </el-container>
    <ChildPageDialog >
      <DialogForum :msg="transMessage"></DialogForum>
    </ChildPageDialog>
 
</template>

<script setup lang="ts">
const allPost = ref()
const backup = ref()
const allPosts = ref([])
const currentPage4 = ref(1)
const pageSize4 = ref(5)
$fetch(useRuntimeConfig().public.forumUrl,{
  method:'GET',

}).then(res=>{

  backup.value = res
  console.log(backup.value[0])

  
  res.forEach((element: any) => {

    element.content = removePTags(element.content)
   
    element.content = truncateString(element.content,18)

    element.createTime = formatTimestamp(element.createTime)

    allPosts.value.push(element)

    
    

  });
  allPost.value = allPosts.value.length

  

  
})

//将标签去除   
 function removePTags(str: string) {
  return str.replace(/<p[^>]*>|<\/p>/gi, '');
}

//将字符长度限定为18 后面....表示
function truncateString(str: string, len: number) {
  if (str.length <= len) {
    return str;
  }
  return str.slice(0, len - 3) + '...' + str.slice(-4);
}

//时间戳转换
function formatTimestamp(timestamp: any) {
  let date = new Date(timestamp)

    let year = date.getFullYear();
    let month = String(date.getMonth() + 1).padStart(2, '0'); // 月份从0开始，所以要加1，并且保证是两位数
    let day = String(date.getDate()).padStart(2, '0'); // 保证日期是两位数
    let hours = String(date.getHours()).padStart(2, '0'); // 保证小时是两位数
    let minutes = String(date.getMinutes()).padStart(2, '0'); // 保证分钟是两位数
    let seconds = String(date.getSeconds()).padStart(2, '0'); // 保证秒数是两位数
    let formattedDate = year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds;

    return formattedDate;
}

const transMessage = ref()
const viewPost = (index: any)=>{
  usedrawer().value=!usedrawer().value
  transMessage.value=backup.value[index]
}


const deleteList =(index: any)=>{
  allPosts.value.splice(index,1)
}


const handleCurrentChange = (val: any)=>{

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