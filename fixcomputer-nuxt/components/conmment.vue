<template>
    <ClientOnly>
    <div style="width: 800px;">
      <div style="border: 1px solid #ccc" class="text">
        <Toolbar
          style="border-bottom: 1px solid #ccc"
          :editor="editorRef"
          :defaultConfig="toolbarConfig"
          :mode="mode"
        />
        <Editor
          style="height: 300px; overflow-y: hidden;"
          v-model="form.content"
          :defaultConfig="editorConfig"
          :mode="mode"
          :config="editorConfig"
          @onCreated="handleCreated"
        />
      </div>
      <div class="sumbit-button">
      <el-button type="primary" @click="sumbit">发布贴子</el-button>
      </div>
    </div>
</ClientOnly>
</template>

<script setup lang="ts">
import '@wangeditor/editor/dist/css/style.css' // 引入 css
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'

const emit = defineEmits(['newValue'])
const editorRef = shallowRef()

const sendId = defineProps({
    msg:{

    }
})
const form = reactive({
  content:'',
  sendId:sendId.msg
})
//最后提交时 遍历两个src缺的哪个
function findMissingUrl(arr1: any,arr2: any){
  
  const valueToCompare = arr2.map((item: any)=>item['src'])

  const missingValues = arr1.filter((value: any) => !valueToCompare.some((val: any) => val === value))


  return missingValues

  
}

const toolbarConfig = {}
const editorConfig = { 
  
  placeholder: '请输入内容...',
  MENU_CONF: {},


}

const images1: any[] = []

editorConfig.MENU_CONF["uploadImage"] = {
  fieldName:'images',
  server: "http://www.localhost:8080/image",
};

editorConfig.MENU_CONF["insertImage"] = {
  onInsertedImage(imageNode: ImageElement | null) {
    if (imageNode == null) return
    const { src, alt, url, href } = imageNode
    images1.push(src)
    console.log(images1)
  }
};
// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
    const editor = editorRef.value
    if (editor == null) return
    editor.destroy()
})

const handleCreated = (editor: any) => {

  editorRef.value = editor // 记录 editor 实例，重要！
  
  
}


const sumbit = ()=>{
  //对比两个数组 看提交前差哪个图片 从minio删除
   const findDelImages =  findMissingUrl(images1,editorRef.value.getElemsByType('image'))
   if(findDelImages.length!=0){
      //删除提交过的图片
    $fetch(useRuntimeConfig().public.imageUrl,{
        method:'DELETE',
        body:findDelImages
    })
   }
   $fetch('/comment',{
        baseURL:useRuntimeConfig().public.forumUrl,
        method:'POST',
        headers:{'token':useCookie('token').value},
        body:form
   }).then(res=>{
      if(res !=null){
        form.content=''
        ElMessage({
          message: '发送成功',
          type: 'success',
        })
        emit('newValue',res)
        
      }else{
        ElMessage.error('出现了错误！')
      }
   })

}

</script>

<style>
.title{
    margin-top: 30px;
}
.text{
    margin-top: 30px;
}
.sumbit-button{
    width: 100%;
    text-align: -webkit-center;
    margin-top: 20px;
}
</style>