<template>
    <div style="text-align: left;margin-top: -75px;">
        <el-avatar style="margin-left: 30px;"> {{ posts.msg.author }} </el-avatar>
        <div style="font-size: 10px;">{{posts.msg.createTime}}</div>
    </div>
    <el-card style="margin: 20px;">
        <div style="text-align: center;"><h1>{{ posts.msg.title }}</h1></div>
        <el-divider />
        <div style="text-align: left;font-size: 18px;margin-left: 20px;">{{ posts.msg.content }}</div>
    </el-card>
    <el-divider />
    <div style="text-align: left;"><h2>评论区：</h2></div>
    <el-scrollbar height="500px">
          <div v-for="(item,index) in commentList">
           
                <div style="text-align: left;">
               <el-avatar > {{ item.author }}</el-avatar>
               <div style="font-size: 10px;">{{item.createTime}}</div>
               <el-card >
                    <div v-html="item.content" style="font-size: 18px"></div>
                </el-card>
                
                </div>  
                <el-divider v-if="index+1!=commentList.length"/>
          </div>
    </el-scrollbar>
    <el-divider />
    <Conmment  :msg="msg.sendId" @newValue="newValue">

    </Conmment>
</template>

<script setup lang="ts">

const commentList = ref()
const posts = defineProps({
    msg:{

    }
})

const test = async(key='')=>{
  await nextTick()
    $fetch('/comment/'+posts.msg.sendId,{
        baseURL:useRuntimeConfig().public.forumUrl,
        method:'GET',

        }).then(res=>{
        commentList.value = res
    })
}
test()

const newValue = (value: any)=>{
    console.log(value)
    commentList.value.push(value)
    conosole.log(commentList.value)
}


watch(posts,(newValue,OldValue)=>{
    test()
})
</script>

<style>
img{
    
    width: 200px;
}

</style>