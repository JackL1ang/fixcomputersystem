<template>
<el-container>
  <el-header>
    <Header>

    </Header>
  </el-header>
  <el-main>
    <div>
      <div style="text-align: center;">
        <h1>维修员综合评分</h1>
      </div>
      <el-row>
        <el-col :span="6" v-for="test in value1">
          <el-card style="width: 300px; height: 300px; margin-left: 50px;margin-top: 30px" shadow="hover">
              <div style="width: 100%; text-align: center" >
              <el-avatar :size="100" :src="circleUrl" />
              </div>
              <div style="width: 100%; text-align: center;">
                <h1>{{123}}</h1>
              </div>
              <div style="width: 100%; text-align: center;">
                
                <el-rate
                v-model="test.rate"
                disabled
                show-score
                text-color="#ff9900"
                score-template="{value} points"
              />
              
              </div>
              <div style="width: 100%; text-align: center; margin-top: 30px; color: gainsboro">
                点击查看评价
              </div>
          </el-card>
        </el-col>
        
      </el-row>

  


    </div>  
</el-main>
</el-container>
</template>

<script setup lang="ts">
const value = ref(5)

const value1 = ref([
    {rate:3},
    {rate:5},
    {rate:5},
    {rate:2},
    {rate:1},
    {rate:0},
    {rate:5}



])

</script>
<style scoped>
.el-header{
  --el-header-padding : 0 0px
}
</style>