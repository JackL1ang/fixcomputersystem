<template>
    
    <div id="container"></div>
    
  </template>
  
<script lang="ts" setup>
import * as echarts from 'echarts'
import 'echarts/extension-src/bmap/bmap';

const data = ref()
data.value = usedataInfo().value

const geoCoordMap = ref()
geoCoordMap.value = usegeoMap().value


const newvalueInfo = defineProps({
  msg:{
    default:false
  }
})

const test = async()=>{
  await nextTick()
  $fetch('/map',{
    baseURL:useRuntimeConfig().public.apiUrl,
    method:'GET'
  }).then(res=>{
    data.value = res.data
    geoCoordMap.value = res.geoCoordMap

    usedataInfo().value = res.data

    usegeoMap().value = res.geoCoordMap

  })

}
test()


const LoadBaiduMapScript = () => {

  const AK = 'Ab6qZsKACYsX7M97AKBARmfSAl7CYLW9'
  const BMap_URL = 'https://api.map.baidu.com/api?v=3.0&ak=' + AK + '&s=1&callback=onBMapCallback'
  return new Promise((resolve, reject) => {
    // 如果已加载直接返回
    if (typeof BMap !== "undefined") {
        resolve(BMap);
        return true;
    }
    
    // 百度地图异步加载回调处理
    window.onBMapCallback = function () {
        console.log("百度地图脚本初始化成功...");
        resolve(BMap);
    };
    // 插入script脚本
    let scriptNode = document.createElement("script");
    scriptNode.setAttribute("type", "text/javascript");
    scriptNode.setAttribute("src", BMap_URL);
    document.body.appendChild(scriptNode);
  })
}
let myChart: echarts.ECharts | null = null
onMounted(async () => {

  // 注意加入异步
  await LoadBaiduMapScript()
  // 创建地图实例，初始化地图等操作
  //挂载完成后 获取dom
    var chartDom = document.getElementById('container')!;
    myChart = echarts.init(chartDom);
    var option: EChartsOption;

interface DataItem {
  name: string;
  address: string;
  value: number;
}



const convertData = function (data: DataItem[]) {

  var res = [];
  for (var i = 0; i < data?.length; i++) {
    var geoCoord = geoCoordMap.value[data[i].name];
   
    if (geoCoord) {
      
      res.push({
        //市名
        name: data[i].name,
        //具体地址
        address:data[i].address,
        //坐标
        value: geoCoord.concat(data[i].address),
        
        
      });
    }
  }
  return res;
};

option = {
  title: {
    text: '门店地址-点击可复制地址',
    left: 'center'
  },
  tooltip: {
    trigger: 'item',
    formatter:function(params: { data: { name1: any; address: any; }; }){
       
        return params.data.address
       
    }
    
  },
  bmap: {
    center: [160, 37.550339],
    zoom: 4,
    roam: true,
    //地图样式
    mapStyle: {
      styleJson: [
        {
          featureType: 'water',
          elementType: 'all',
          stylers: {
            color: '#d1d1d1'
          }
        },
        {
          featureType: 'land',
          elementType: 'all',
          stylers: {
            color: '#f3f3f3'
          }
        },
        {
          featureType: 'railway',
          elementType: 'all',
          stylers: {
            visibility: 'off'
          }
        },
        {
          featureType: 'highway',
          elementType: 'all',
          stylers: {
            color: '#fdfdfd'
          }
        },
        {
          featureType: 'highway',
          elementType: 'labels',
          stylers: {
            visibility: 'off'
          }
        },
        {
          featureType: 'arterial',
          elementType: 'geometry',
          stylers: {
            color: '#fefefe'
          }
        },
        {
          featureType: 'arterial',
          elementType: 'geometry.fill',
          stylers: {
            color: '#fefefe'
          }
        },
        {
          featureType: 'poi',
          elementType: 'all',
          stylers: {
            visibility: 'off'
          }
        },
        {
          featureType: 'green',
          elementType: 'all',
          stylers: {
            visibility: 'off'
          }
        },
        {
          featureType: 'subway',
          elementType: 'all',
          stylers: {
            visibility: 'off'
          }
        },
        {
          featureType: 'manmade',
          elementType: 'all',
          stylers: {
            color: '#d1d1d1'
          }
        },
        {
          featureType: 'local',
          elementType: 'all',
          stylers: {
            color: '#d1d1d1'
          }
        },
        {
          featureType: 'arterial',
          elementType: 'labels',
          stylers: {
            visibility: 'off'
          }
        },
        {
          featureType: 'boundary',
          elementType: 'all',
          stylers: {
            color: '#fefefe'
          }
        },
        {
          featureType: 'building',
          elementType: 'all',
          stylers: {
            color: '#d1d1d1'
          }
        },
        {
          featureType: 'label',
          elementType: 'labels.text.fill',
          stylers: {
            color: '#999999'
          }
        }
      ]
    }
  },
  series: [
    {
      type: 'scatter',
      coordinateSystem: 'bmap',
    },
    {
      
      type: 'effectScatter',
      coordinateSystem: 'bmap',
      data: convertData(data.value),
      symbolSize: function () {
        return 20;
      },
      encode:{
        value: 2
      },
      rippleEffect: {
        brushType: 'stroke'
      },
      label: {
        formatter: '{b}',
        position: 'right',
        show: true
      },
      itemStyle: {
        shadowBlur: 10,
        shadowColor: '#333'
      },
     
      
    }
  ]
};

option && myChart.setOption(option);
myChart.on('click',function(params){
    try {
        navigator.clipboard.writeText(params.data.address);
        ElNotification({
            title: 'Success',
            message: '成功复制地址',
            type: 'success',
        })
    } catch (error) {
        ElNotification({
            title: 'Error',
            message: '错误',
            type: 'error',
        })
    }
    
});

})




</script>
  
<style scoped>
  #container {
    width: 100%;
    height: 100%;
  }
 </style>