<template>
   
<el-descriptions
            class="margin-top"
            :column="3"
            size="default"
            border
            style="margin-top: 50px"
            title="用户信息"
            >
            <template #extra>
                <el-button type="primary" round 
                @click="addWayBill(fix_completeInfo?.orderDetailInfo.orderId,fix_completeInfo?.orderDetailInfo.complete_waybill)"
                v-if="fix_completeInfo?.orderDetailInfo.payStatus"
                
                >
                添加物流信息
                </el-button>
            </template>
            <el-descriptions-item >
                <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <Edit/>
                        </el-icon>
                        订单编号
                    
                    </div>
                </template>
                {{ fix_completeInfo?.orderDetailInfo.orderId }}
            </el-descriptions-item>
            <el-descriptions-item >
                <template #label>
                    <div class="cell-item" style="width: 100px;">
                        <el-icon>
                        <User />
                        </el-icon>
                        用户名
                    </div>
                </template>
                {{ fix_completeInfo?.orderDetailInfo.userName }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item" style="width: 100px;">
                        <el-icon>
                        <Clock />
                        </el-icon>
                        创建时间
                    </div>
                </template>
                {{ fix_completeInfo?.orderDetailInfo.create_date }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <Tools />
                        </el-icon>
                        类别
                    </div>
                </template>
                {{ fix_completeInfo?.orderDetailInfo.category }}
            </el-descriptions-item>
            
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <UserFilled />
                        </el-icon>
                        用户姓名
                    </div>
                </template>
                {{ fix_completeInfo?.orderDetailInfo.name }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item" style="width: 100px;">
                        <el-icon>
                        <tickets />
                        </el-icon>
                        接收时间
                    </div>
                </template>
                {{ fix_completeInfo?.orderDetailInfo.accept_date }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <office-building />
                        </el-icon>
                        手机号码
                    </div>
                </template>
                {{ fix_completeInfo?.orderDetailInfo.phone }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item" >
                        <el-icon>
                        <Van />
                        </el-icon>
                        快递单号
                    </div>
                </template>
                {{ fix_completeInfo?.orderDetailInfo.waybill }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item" >
                        <el-icon>
                            <Clock />
                        </el-icon>
                        完成时间
                    </div>
                </template>
                {{ fix_completeInfo?.orderDetailInfo.finish_date }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <office-building />
                        </el-icon>
                        地址
                    </div>
                </template>
                {{ fix_completeInfo?.orderDetailInfo.address }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template #label>
                    <div class="cell-item">
                        <el-icon>
                        <office-building />
                        </el-icon>
                        备注
                    </div>
                </template>
                {{ fix_completeInfo?.orderDetailInfo.notes }}
            </el-descriptions-item>
    </el-descriptions>
    <el-divider/>
    <el-descriptions
        title="支付信息"
        :column="4"
        :style="blockMargin"
    >
        <el-descriptions-item label="金额" ><el-tag type="warning">{{fix_completeInfo?.orderDetailInfo.total_amount}}</el-tag></el-descriptions-item>
        <el-descriptions-item label="是否成功支付">
            <el-tag v-show="fix_completeInfo?.orderDetailInfo.payStatus" type="success">是</el-tag>
            <el-tag v-show="!fix_completeInfo?.orderDetailInfo.payStatus" type="danger">否</el-tag>
        </el-descriptions-item>

  </el-descriptions>
    <el-divider v-if="fix_completeInfo?.repairInfo!=null"/>
    <el-descriptions
        title="维修信息"
        :column="4"
        v-if="fix_completeInfo?.repairInfo!=null"
        :style="blockMargin"
    >
        <el-descriptions-item label="故障原因"><el-tag>{{fix_completeInfo?.repairInfo.fixSource}}</el-tag></el-descriptions-item>
        <el-descriptions-item label="是否成功维修">
            <el-tag v-show="fix_completeInfo?.repairInfo.whetherFix" type="success">是</el-tag>
            <el-tag v-show="!fix_completeInfo?.repairInfo.whetherFix" type="danger">否</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="维修内容"><el-tag>{{fix_completeInfo?.repairInfo.fixSource}}</el-tag></el-descriptions-item>
        <el-descriptions-item label="维修员工号"><el-tag>{{ fix_completeInfo?.orderDetailInfo.fixerId }}</el-tag> </el-descriptions-item>
  </el-descriptions>
  <el-divider v-if="fix_completeInfo?.usePartsInfo.length!=0"/>
  <el-descriptions
        title="使用零件"
        :column="3"
        :style="blockMargin"
        v-if="fix_completeInfo?.usePartsInfo.length!=0"
    >
        <div v-for="item in fix_completeInfo?.usePartsInfo">
            <el-descriptions-item label="零件名"><el-tag>{{item.parts_name}}</el-tag></el-descriptions-item>
            <el-descriptions-item label="厂商">
                <el-tag type="warning">
                    {{item.manufacturer}}
                </el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="数量" ><el-tag type="success">{{ item.amount }}</el-tag> </el-descriptions-item>
        </div>
        
  </el-descriptions>

  <DialogCompleteWaybill :msg="form"></DialogCompleteWaybill>

</template>

<script setup lang="ts">

const fix_completeInfo = ref()

const order_id = defineProps({
    msg:{
        type:Object,
        default:{}
    }
})
onMounted(()=>{
    $fetch(useRuntimeConfig().public.fixinfoUrl,{
        query:{orderId:order_id.msg}
    }).then(res=>{
        console.log(res)
        fix_completeInfo.value = res
        console.log(fix_completeInfo)
    })
})

const form = reactive({
    order_id:'',
    complete_waybill:''
})
const addWayBill = (val: any,waybill: any)=>{
    usecompleteWaybill().value = !usecompleteWaybill().value
    form.order_id = val
    form.complete_waybill = waybill
    console.log(form)
}

</script>