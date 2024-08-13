<script lang="ts" setup>
import {regionData , codeToText } from "element-china-area-data";

//regionData插件中的地区
const selectedOptions = ref([])

const form = reactive({
  name: "",
  phone: "",
  address:"",
  region:"",
  waybill:"",
  category:"",
  other:"",
  notes:"",
})


$fetch('query_defaultAddress',{
    baseURL:useRuntimeConfig().public.userAddressUrl,
    method:'GET',
    headers:{'token':useCookie('token').value}
}).then(res =>{
    form.name = res.name
    form.phone = res.phone
    form.address = res.address
    selectedOptions.value.push(res.province,res.city,res.area)
    form.region = codeToText[selectedOptions.value[0]]+
    codeToText[selectedOptions.value[1]]+
    codeToText[selectedOptions.value[2]]
})



//拦截更改地址放入
const handleChange = ()=>{

    form.region = codeToText[selectedOptions.value[0]]+
    codeToText[selectedOptions.value[1]]+
    codeToText[selectedOptions.value[2]]

    
}
const active = ref(0)
const submitResult = ref()
//是否邮寄按钮
let value4 = ref(true)

//next按钮
const next = () =>{
    active.value++
    console.log(active.value)
    if(active.value >= 4){

        active.value=-1
    }
}



//返回上一个按钮
const back = ()=>{
    // emit('changStr',active.value-1)
    active.value--
    if(active.value<=-1){
        console.log(active.value)
        active.value=-1
    }
}
const showOther = ref(false)


const otherButtonTrue  = ()=>{
    showOther.value = true
}
const otherButtonFalse = ()=>{
    showOther.value = false
}

//是否邮寄
const swMail = ()=>{
    console.log(value4)
   
}

//完成按钮
const completeButtonDisable = ref(false)
const completeButton = ()=>{
    if(useUsername().value != ""){
        //如果类型为其他
        if(form.category === "other"){
            useFetch('/add',{
                method:'post',
                baseURL:useRuntimeConfig().public.orderUrl,
                
                headers:{"token":"123",},
                body:{"name":form.name,
                    "phone":form.phone,
                    "address":form.region+form.address,
                    "category":form.other,
                    "waybill":form.waybill,
                    "note":form.notes
            
            
            },
                
                
            })
            .then(res=>{
                if(res.data.value){
                    next()
                    submitResult.value = true
                    setTimeout(() => {
                        navigateTo('/')
                    }, 3000);
                }else{
                    next()
                    submitResult.value = false
                    
                    
                }
            })
            
            
        }else{
            useFetch('/add',{
                method:'post',
                baseURL:useRuntimeConfig().public.orderUrl,
                headers:{"token":useCookie('token')},
                body:{"name":form.name,
                    "phone":form.phone,
                    "address":form.region+form.address,
                    "category":form.category,
                    "waybill":form.waybill,
                    "note":form.notes
            
            
                },
            }).then(res=>{
                if(res.data.value){
                    next()
                    submitResult.value = true
                    setTimeout(() => {
                        navigateTo('/')
                    }, 3000);
                }else{
                    next()
                    submitResult.value = false
                }
            })

        }
    }else{
        //控制完成按钮能否点击
        completeButtonDisable.value = true
        alert("请先登录")
    }
    
}
const backindex=()=>{
    navigateTo('/')
}



</script>
<template>
    
<el-container>
    <el-header>
        <slot name="header"></slot>
    </el-header>
    <client-only>
        <el-main>
            <el-row style="height: 90vh">
                <el-col :span="7" :xs="0"></el-col>
                <el-col :span="10" :xs="24">
                    <el-card shadow="hover" class="welcome" >
                        <el-steps :active="active" finish-status="success"  align-center>
                            <el-step title="Step 1" />
                            <el-step title="Step 2" />
                            <el-step title="Step 3" />
                        </el-steps>
                        <div v-if="active===0">
                        
                            <el-row>
                                
                            <el-col>
                                <div class="fix-title">
                                    <h1>欢迎来到维修报修系统</h1>
                                </div>
                                
                            </el-col>
                                
                                
                            </el-row>
                            <el-row>
                                <div class="fix-button">
                                    <el-button type="primary" @click="next">开始报修</el-button>
                                </div>
                        
                            </el-row>
                        
                            
                        </div>
                        <div v-if="active===1">
                            <el-form label-width="120px" style="margin-top: 40px;">
                                <el-form-item label="姓 名">
                                <el-input v-model="form.name" />
                                </el-form-item>
                                <el-form-item label="手机号码">
                                <el-input v-model="form.phone" />
                                </el-form-item>
                                <el-form-item label="地 区">
                                    <el-cascader
                                        size="large"
                                        :options ="regionData"
                                        v-model="selectedOptions"
                                        @change="handleChange"
                                    >
                                    </el-cascader>
                                </el-form-item>
                                <el-form-item label="详细地址">
                                    <el-input
                                        v-model="form.address"
                                        :rows="2"
                                        type="textarea"
                                        placeholder="请输入详细地址"
                                    />
                                </el-form-item>
                                
                            </el-form>
                            <!--按钮-->
                            <div style="text-align-last: center;">
                                <el-button style="margin-top: 12px;" @click="back"  :disabled="active>-1?false:true">上一步</el-button>
                                <el-button style="margin-top: 12px;" @click="next"  >下一步</el-button>
                            </div>
                        </div>
                        <div v-if="active===2">
                            <el-form-item label="是否已邮寄" label-width="120px" style="margin-top: 30px;">
                                <el-switch
                                    v-model="value4"
                                    class="ml-2"
                                    inline-prompt
                                    style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                                    active-text="是"
                                    inactive-text="否"
                                    @click="swMail"
                                    
                                />
                                
                            </el-form-item>
                            <!--邮寄框-->
                            
                            <el-form-item label="快递单号" label-width="120px" v-if="value4">
                                <el-input v-model="form.waybill" placeholder="请输入快递单号" />
                            </el-form-item>
                            
                            <el-form-item label="维修类别" label-width="120px" >
                                    <el-select v-model="form.category" placeholder="please select your zone">
                                        <el-option label="显卡" value="显卡" @click="otherButtonFalse"/>
                                        <el-option label="主板" value="主板" @click="otherButtonFalse"/>
                                        <el-option label="其他" value="other" @click="otherButtonTrue"/>
                                        
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="其他" v-show="showOther" label-width="120px" >
                                
                                    <!--其他文本框-->
                                    <el-input
                                            v-model="form.other"
                                            :rows="1"
                                            type="textarea"
                                            placeholder="请输入是什么设备"
                                    />
                                    
                                </el-form-item>
                                <!--备注-->
                                <el-form-item label="备注"  label-width="120px" >
                                
                                
                                <el-input
                                        v-model="form.notes"
                                        :rows="2"
                                        type="textarea"
                                        placeholder=""
                                />
                                
                            </el-form-item>
                            <!--按钮-->
                            <div style="text-align-last: center;">
                                <el-button style="margin-top: 12px;" @click="back"  :disabled="active>-1?false:true">上一步</el-button>
                                <el-button style="margin-top: 12px;" @click="completeButton" :disabled="completeButtonDisable" >完成</el-button>
                            </div>
                        </div>
                       
                        <div v-if="active==3">
                            <el-result
                                icon="success"
                                title="成功"
                                sub-title="成功提交订单"
                                v-if="submitResult"
                            >
                            <template #extra>
                            <el-button type="primary"  @click="backindex">返回首页</el-button>
                            </template>
                            </el-result>
                            <el-result
                                icon="warning"
                                title="错误"
                                sub-title="请重新再试"
                                v-else
                            >
                                <template #extra>
                                
                                    <el-button type="primary" @click="back">返回上一页</el-button>
                                
                                </template>
                            </el-result>
                        </div>
                        
                    </el-card>
                </el-col>
                <el-col :span="7" :xs="0"></el-col>
            </el-row>
        </el-main>
    </client-only>
    
</el-container>
  
</template>

  
<style scoped>
.el-header{
    --el-header-padding:0 0px!important;
}
.el-card{
    margin-top: 100px;
    height: 450px;
}
.fix-title{
    margin-top: 50px;
    text-align: center;
}
.fix-button{
    width: 100%;
    margin-top: 150px;
    text-align: center;
}


</style>

