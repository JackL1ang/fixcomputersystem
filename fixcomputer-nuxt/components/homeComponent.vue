<template>
<el-row :gutter="200" class="fdr">
    <el-col :span="2">
      <el-timeline>
        <el-timeline-item center placement="top">
          <el-card class="c4" style="width: 280px; height: 230px" shadow="hover">
            <h3>通知：</h3>
            <el-divider />
            <el-scrollbar height="100px">
              
            <div v-for="(item,index) in notice">
              
              <div><el-tag>标题:

              </el-tag><el-button link @click="dialogopen(item)"><h3>{{ item.title }}</h3></el-button>
              </div>
              <el-tag>时间:</el-tag>{{ item.createTime }}
              <el-divider v-if="notice.length !=index+1 "/>
            </div>
           
          </el-scrollbar>
          </el-card>
        </el-timeline-item>


        <el-timeline-item placement="top">
          <el-card class="c4" style="width: 280px; height: 300px" shadow="hover">
            <h3>工作代办：</h3>
            
            <el-scrollbar height="200px">
              <div v-for="items in item" >
                <el-tag>{{items.orderId}}</el-tag> 
                <el-tag v-show="items.status==3" type="warning" style="margin-left: 20px;">{{items.statusName}}</el-tag>
                <el-tag v-show="items.status==2" type="danger" style="margin-left: 20px;">{{items.statusName}}</el-tag>
                <div style="padding: 8px 0"></div>
               
              </div>
          </el-scrollbar>
          </el-card>
        
        </el-timeline-item>


        <el-timeline-item center placement="top">
  
                <el-card class="c4" style="width: 280px; height: 200px;" shadow="hover">
                  <h3>备注</h3>
                  <div >
                    <el-scrollbar height="80px">
                      <div style="padding: 8px 0" v-if="showRemark">{{ remark }}</div>
                    </el-scrollbar>
                  </div>
                  <el-input
                    v-model="textarea2"
                    style="width: 240px"
                    :autosize="{ minRows: 2, maxRows: 4 }"
                    type="textarea"
                    placeholder="Please input"
                    v-if="!showRemark"
                  />
                  <el-button @click="handleRemark">修改</el-button>
                  <el-button v-if="!showRemark" @click="cancelhRemark">取消</el-button>
                </el-card>
  
        </el-timeline-item>
        
      </el-timeline>
    </el-col>
    <el-col :span="3">

    </el-col>
    <el-col :span="15" style="width: 100%;">
      <el-row>
        <el-container class="fdc" style="margin-top: 10px">
          <el-row :gutter="100" class="fdr">
            <el-col :span="6">
              <el-card shadow="hover" style="width:350px; height: 270px;text-align: center;">
                <template #header>
                  <h3 class="t2">总订单量</h3>
                </template>
                <el-statistic :value="outputValue" style="margin-top: 50px"/>
               </el-card>
            </el-col>
            <el-col :span="3">

            </el-col>

            <el-col :span="6">
              <el-card shadow="hover" style="width:350px; text-align: center;">
                <template #header>
                  <h3 class="t1">已完成</h3>
                </template>
                <el-progress type="dashboard" :percentage="completePercent" status="success">
                  <template #default="{ percentage }">
                    <span class="percentage-value t1">{{ percentage }}%</span>
                  </template>
                </el-progress>
              </el-card>
            </el-col>
            <el-col :span="3">

            </el-col>
            <el-col :span="6" >
              <el-card shadow="hover"  style="width:350px; text-align: center;">
                <template #header>
                  <h3 class="t3">未完成</h3>
                </template>
                <el-progress type="dashboard" :percentage="incompletePercent" status="warning">
                  <template #default="{ percentage }">
                    <span class="percentage-value t3">{{ percentage }}%</span>
                  </template>
                </el-progress>
              </el-card>
            </el-col>
          </el-row>
        </el-container>
      </el-row>
      <el-row style="margin-top: 60px;" :gutter="60">
        <el-col :span="10">
            <el-card shadow="hover" style="width: 500px">
              <div style="text-align-last:right">
                <el-radio-group v-model="radioButton" style="">
                  <el-radio-button label="月统计" value="month" />
                  <el-radio-button label="日统计" value="day" />
                </el-radio-group>
              </div>
              <div ref="lineChartRef" class="chart" style="width: 100%; height: 400px; text-align: -webkit-center; margin-left: -13px" v-show="radioButton=='day'"/>
              <div ref="chartRef" v-show="radioButton=='month'" style="width: 500px; height: 400px; margin-left: -13px"/>
              
              
            </el-card>
        </el-col>
        <el-col :span="5">

        </el-col>
        <el-col :span="9">
          <el-card shadow="hover" style="width: 500px;">
            <div ref="pieChartRef" style="width: 100%; height: 430px; text-align: -webkit-center;">
            </div>
          </el-card>
        
        </el-col>

      </el-row>
    </el-col>


  </el-row>
<child-page-dialog :msg="true">
 
  <dialog-notice-info :msg="noticeContext">

  </dialog-notice-info>
</child-page-dialog>
</template>

<script setup lang="ts">
import * as echarts from 'echarts';
//订单量
const source  = ref(0)

//选择按钮
const radioButton  = ref('day')

//完成数值
const completePercent = ref(0)

//未完成数值
const incompletePercent = ref(0)

const outputValue = useTransition(source, {
  duration: 1000,
})

//折线插件dom
const lineChartRef = ref<HTMLElement | null>(null);
//饼图插件dom
const pieChartRef = ref<HTMLElement | null>(null);
//柱形插件dom
const chartRef = ref<HTMLElement | null>(null);
//备注按钮
const showRemark = ref(true)

//备注
const remark = ref("123")
//周报
const weekDay: any[] = []
const weekDayDate: any[] = []
//年报
const month: any[] = []
const yearOrder: any[] = []
//获取统计数据
$fetch(useRuntimeConfig().public.statisticUrl,{
  method:'get',
  headers:{'token':useCookie('token').value}
}).then(res=>{
  completePercent.value = res.complete
  incompletePercent.value = res.incomplete
  source.value = res.allOrder
  item.value = res.statusorder
  PieChart.value = res.category
  res.weekOrder.forEach((element: { orderDate: any; orderCount: any; }) => {
      weekDay.push(element.orderDate)
      weekDayDate.push(element.orderCount)
  });
  res.yearOrder.forEach((element: { orderMonth: any; orderCount: any; }) => {
    month.push(element.orderMonth)
    yearOrder.push(element.orderCount)
  });
  
  initPieChart()
  initLineChart()
  initChart()
})

//柱形图初始化
const initChart = ()=>{
    if(chartRef.value){
      const chartRefDom = chartRef.value
      const chartInstance = echarts.init(chartRefDom)

      const chartOption = {
          title:{
            text:'每月统计量',
            
          },
          tooltip: {
              trigger: 'axis',
              axisPointer: {
                type: 'shadow'
              }
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis:{
            data:month,
            axisTick: {
                  alignWithLabel: true
            }
          },
          yAxis:[{
            type:'value'
          }],
          series: [
            {
              type: 'bar',
              barWidth: '60%',
              data: yearOrder
            }
          ]
      }
      chartInstance.setOption(chartOption)
    }
}


//折线图初始化
const initLineChart = () => {
    if (lineChartRef.value) {
      const lineChartDom = lineChartRef.value;
      const lineChartInstance = echarts.init(lineChartDom);

      const lineChartOption = {
        title: {
          text: '每日订单量'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        
        xAxis: {
          data: weekDay
        },
        yAxis: {},
        series: [{
          name: '销量',
          type: 'line',
          data: weekDayDate
        }]
      };

      lineChartInstance.setOption(lineChartOption);
    }
  };
  const PieChart = ref()

  

  //初始化饼图
  const initPieChart = () => {
    if (pieChartRef.value) {
      const pieChartDom = pieChartRef.value;
      const pieChartInstance = echarts.init(pieChartDom);

      const pieChartOption = {
        title: {
          text: '维修种类'
        },
        tooltip: {},
        series: [{
          name: '种类',
          type: 'pie',
          radius: '55%',
          data:PieChart.value
        }]
      };

      pieChartInstance.setOption(pieChartOption);
    }
  };
//输入备注信息
const textarea2 =ref()

//代办公告栏
const item = ref()

//通知
const notice = ref()

//获取通知
$fetch(useRuntimeConfig().public.notice,{
  method:'GET',
}).then(res=>{
  notice.value = res.reverse()

})


//备注按钮
const handleRemark = ()=>{
showRemark.value = !showRemark.value

}
//取消备注
const cancelhRemark = ()=>{
showRemark.value = !showRemark.value

}

const noticeContext = ref()
//打开弹窗
const dialogopen = (item: any)=>{
  usedrawer().value=!usedrawer().value
  noticeContext.value = item
}
  
</script>