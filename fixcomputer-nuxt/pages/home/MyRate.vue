<template>
    <el-card 
    class="box-card"
    shadow="hover"
    >
        <template #header>
        <div class="card-header">
            <span>客 服</span>
            <el-button class="button" text>Operation button</el-button>
        </div>
        </template>
        <div v-for="o in 4" :key="o" class="text item">{{ 'List item ' + o }}</div>
        <template #footer>
            <el-input
            v-model="textarea"
            :rows="2"
            type="textarea"
            placeholder="Please input"
            />
            <div class="send-message">
            <el-button type="primary">发送</el-button>
            </div>
        </template>
  </el-card>
</template>
<script setup>

    const textarea = ref('')
    definePageMeta({
        layout:"home",
        meta:{
            id:5,
           
            title:"我的评价",
            icon:"star"
        }
    })

</script>

<style>

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.box-card {
  width: 480px;
  margin-top: 10px;
}
.send-message{
    margin-top: 10px;
    text-align: right;
}
.el-textarea__inner{
    height: 165px;
}

</style>