<template>
    <ClientOnly>
        <el-row :gutter="5">
            <el-col :span="12" v-for="(item,i) in data" style="margin-top: 10px;">
                <el-card shadow="hover">
                    <el-descriptions
                        class="margin-top"
                        title="工 单"
                        :column="3"
                        border>
                        <template #extra>
                        <el-button type="primary" @click="acceptorder(i,item.orderId)">接受</el-button>
                        </template>
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item">
                                <el-icon>
                                    <user />
                                </el-icon>
                                订单号
                                </div>
                            </template>
                            {{ item.orderId }}
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item">
                                <el-icon >
                                    <user />
                                </el-icon>
                                创建时间
                                </div>
                            </template>
                            {{item.create_date}}
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item">
                                <el-icon >
                                    <user />
                                </el-icon>
                                是否邮寄
                                </div>
                            </template>
                            <el-tag type="success" v-if="item.whetherMail==1">是</el-tag>
                            <el-tag type="danger" v-if="item.whetherMail==0">否</el-tag>
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item">
                                <el-icon >
                                    <user />
                                </el-icon>
                                用户名
                                </div>
                            </template>
                            {{item.userName}}
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label >
                                <div class="cell-item">
                                    <el-icon >
                                        <iphone />
                                    </el-icon>
                                联系电话
                                </div>
                            </template>
                            {{item.phone}}
                        </el-descriptions-item>
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item">
                                <el-icon>
                                    <Cpu />
                                </el-icon>
                                设备
                                </div>
                            </template>
                            <el-tag type="primary">{{item.category}}</el-tag>
                        </el-descriptions-item>
                        
                            
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item" style="width: 50px;">
                                <el-icon >
                                    <tickets />
                                </el-icon>
                                备注
                                </div>
                            </template>
                            {{item.notes}}
                        </el-descriptions-item>
                        
                        <el-descriptions-item>
                            <template #label>
                                <div class="cell-item">
                                    <el-icon >
                                        <office-building />
                                    </el-icon>
                                    地址
                                </div>
                            </template>
                               {{ item.address }}
                        </el-descriptions-item>
                      
                    </el-descriptions>

                </el-card>
                
            
            </el-col>
        </el-row>
        <!--分页-->
    <div style="margin-top: 30px">
      <el-pagination
        v-model:current-page="currentPage4"
        v-model:page-size="pageSize4"
        layout="total, prev, pager, next, jumper"
        :total="countOrder"
        @current-change="handleCurrentChange"
        style="justify-content: center;"
      />
    </div>
    </ClientOnly>
</template>

<script setup lang="ts">
const data = ref()
const currentPage4 = ref(1)
const pageSize4 = ref(6)
const countOrder = ref()
definePageMeta({
    layout:"home",
    meta:{
        id:2,
        auth:"fixer",
        title:"接单中心",
        icon:"tickets"

    }
})


const nextTickAccess = async()=>{
    await nextTick()
    $fetch('/fixorder',{
        baseURL:useRuntimeConfig().public.orderUrl,
        method:'get',
    }).then((res)=>{
        data.value = res.jsonObject
        countOrder.value = res.jsonObjectCount

        
    })
} 
nextTickAccess()
const handleCurrentChange = (val: any)=>{
    $fetch('/accept/'+val,{
        baseURL:useRuntimeConfig().public.pageUrl,
        method:'get',
    }).then(res=>{
        data.value = res.jsonObject
        countOrder.value = res.jsonObjectCount
    })
}
const acceptorder = (i: any,item: any)=>{
    $fetch('/accept',{
        baseURL:useRuntimeConfig().public.orderUpdataUrl,
        method:'get',
        headers:{'token':useCookie('token').value},
        query:{"orderId":item}
        
    }).then((res)=>{
        if(res.status){
            ElMessage({
                message: '成功接收订单',
                type: 'success',
            })
        }else{
            ElMessage.error('接单失败请重试.')

        }
    })
    data.value.splice(i,1)

}
</script>