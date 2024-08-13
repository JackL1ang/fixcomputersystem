<template>
    
        <div style="text-align: -webkit-center;" v-show="showPage">
            <el-form
            label-position="top"
            :model="form"
            style="width: 600px; margin-top: 20px;"
            >
                <el-form-item label="订单号:">
                    <el-input v-model="form.order_id" :disabled="true"/>
                </el-form-item>
                <el-form-item label="故障源:">
                    <el-input v-model="form.fix_source" type="textarea"/>
                </el-form-item>
                <el-form-item label="维修内容:">
                    <el-input v-model="form.fix_text" type="textarea"/>
                </el-form-item>
                <el-form-item label="是否维修成功:">
                    <el-switch
                    v-model="form.whether_fix"
                    inline-prompt
                    active-text="是"
                    inactive-text="否"
                />
                </el-form-item>
                <el-form-item label="金额:" v-if="form.whether_fix">
                    <el-input v-model="form.amount" style="width: 240px" placeholder="请输入金额" />
                </el-form-item>
            
                <el-form-item label="维修零件:" v-if="form.whether_fix">
                <el-transfer
                    v-model="value"
                    filterable
                    :filter-method="filterMethod"
                    filter-placeholder="请输入需要搜索的零件"
                    :data="data"
                    @change="handleChange"
                />
                </el-form-item>
                
                <div  style="margin-top: 20px; text-align:-webkit-center;">
                    <el-button type="primary" @click="completeButton" v-show="!form.whether_fix">完成</el-button>
                    <el-button type="primary" @click="updateFixStatus" v-show="form.whether_fix">下一步</el-button>
                    <el-button @click="closeDialog">关闭</el-button> 
                </div>
            </el-form>
            
         </div>

        <div v-show="!showPage">
            
                <el-table :data="form.parts">
                    <el-table-column/>
                    <el-table-column label="零件名" >
                            <template #default="scope">
                                    <el-tag size="large">{{scope.row.name}}</el-tag>

                            </template>


                    </el-table-column>

                        
                    
                    <el-table-column label="数量">
                        <template #default="scope">
                            <el-input-number v-model="scope.row.amount" :min="1" :max="10"  />
                        </template>
                        
                    </el-table-column>
                </el-table>
                <div>
                    <el-button @click="completeButton">确认</el-button>
                    <el-button @click="goback">上一步</el-button>
                </div>
           
        </div>

  
</template>

<script setup lang="ts">
const showPage = ref(true)



//选择框右边备件
const states = ref([])

//左边的备件
const data = ref([])

const goback = ()=>{
    showPage.value=true
}

const order = defineProps({
    msg:{
        type:Object,
        default:{}
    }
})

const form = reactive({
    order_id:order.msg,
    fix_source:'',
    fix_text:'',
    whether_fix:true,
    amount:'',
    parts:[]

})


//下一步按钮
const updateFixStatus = ()=>{
        if(form.parts.length == 0){
            
        }else{
            showPage.value=false
        }
        
        
}
//关闭按钮
const closeDialog = ()=>{
    usedrawer().value=!usedrawer().value
    useRouter().back();
    console.log(usedrawer().value)
}


//获取零件名字
$fetch(useRuntimeConfig().public.partsUrl,{
    method:'get',

}).then(res=>{
    //备件
    states.value = res
 
    //遍历备件 加入序号
    generateData()
    
})

//捕获将数据装到右边,并存入数组
const handleChange = (value: string | number,direction: any,movedKeys: any)=>{
    
    for (var value in value){
        if(form.parts.indexOf(states.value[value] === -1)){
            //添加进来的零件值设为1
            states.value[value].amount =1

            form.parts.push(states.value[value])


        }
    }
}


//进行遍历 放入data中
const generateData = () => {
    states.value.forEach((items,index)=>{
        console.log(items.name)
        data.value.push({
            label:items.name,
            key:index
        })
    })
    return data
}



const value = ref([])
//搜索 进行简单筛选
const filterMethod = (query: string | RegExp, item: { label: string; }) => {
    const pattern = new RegExp(query,'i')
    if(pattern.test(item.label)){
        return item
    }
  
    
  
}


//完成按钮
const completeButton = ()=>{
    $fetch(useRuntimeConfig().public.fixinfoUrl,{
        method:'post',
        body:form
    }).then(res=>{
        console.log(res.status)
        if(res.status){
            ElMessage({
                message:"成功完成订单",
                type:'success',
            })
            useFixOrderStatus().value = 3
        }else{
            ElMessage.error('发生了错误，请重新再试')
        }
    })

}
</script>