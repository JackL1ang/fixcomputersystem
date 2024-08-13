<template>
    <el-form>
        <el-descriptions
            class="margin-top"
            title="零件详情"
            :column="4"
            border
        >
        <template #extra>
        <el-button type="primary" @click="updateInfo">确 认</el-button>
        </template>
        <el-descriptions-item>
        <template #label>
            <div class="cell-item">
            <el-icon>
                <user />
            </el-icon>
            零件编号
            </div>
        </template>
        {{parts.msg.partsId}}
        </el-descriptions-item>
        <el-descriptions-item>
        <template #label>
            <div class="cell-item">
            <el-icon>
                <iphone />
            </el-icon>
            厂商
            </div>
        </template>
        <el-input v-model="parts.msg.manufacturer"/>
        </el-descriptions-item>
        <el-descriptions-item>
        <template #label>
            <div class="cell-item">
            <el-icon>
                <location />
            </el-icon>
            型号
            </div>
        </template>
        <el-input v-model="parts.msg.name"/>
        </el-descriptions-item>
        <el-descriptions-item>
        <template #label>
            <div class="cell-item">
            <el-icon >
                <tickets />
            </el-icon>
            库存
            </div>
        </template>
        <el-input-number v-model="parts.msg.inventory"/>
        </el-descriptions-item>
        <el-descriptions-item>
        <template #label>
            <div class="cell-item">
            <el-icon >
                <office-building />
            </el-icon>
            价格
            </div>
        </template>
        <el-input-number v-model="parts.msg.price"/>
        </el-descriptions-item>
        <el-descriptions-item>
        <template #label>
            <div class="cell-item">
            <el-icon >
                <office-building />
            </el-icon>
            入库时间
            </div>
        </template>
        {{ parts.msg.createDate }}
        </el-descriptions-item>
        <el-descriptions-item>
        <template #label>
            <div class="cell-item">
            <el-icon >
                <office-building />
            </el-icon>
            操作人
            </div>
        </template>
        {{ parts.msg.operator }}
        </el-descriptions-item>
    </el-descriptions>
    </el-form>


</template>

<script setup lang="ts">
const emit = defineEmits(['updateInfo'])
const parts = defineProps({
    msg:{
        
        type:Object,
        default:''
    
    
    }
})

const updateInfo = ()=>{
    parts.msg.operator = useUsername().value
    console.log(parts.msg)
    $fetch(useRuntimeConfig().public.partsUrl,{
        method:'post',
        body:parts.msg
    }).then((res)=>{
        if(res){
            ElMessage({
                type:'success',
                message:'修改成功'
            })
            setTimeout(() => {
                usedrawer().value = !usedrawer().value
            }, 1000);
            emit('updateInfo',res)
            
        }else{
            ElMessage({
                type:'warning',
                message:'修改失败，请重试'
            })
            emit('updateInfo',res)
        }
    })
}
</script>