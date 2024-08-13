<template>

    <el-button type="primary" @click="addStore()">添加</el-button>
    <el-row>
        <el-col :span="12">
            <el-table :data="tableData" style="width: auto">
                <el-table-column fixed prop="id" label="id" width="150" />
                <el-table-column prop="name" label="Name" width="120" />
                <el-table-column prop="address" label="Address" width="300" align="center"/>
                <el-table-column prop="coordinate" label="坐标" width="120" />
                <el-table-column fixed="right" label="Operations" width="120">
                    <template #default="scope">
                        <el-button link type="primary" size="small" @click="handleClick(scope.row.id)">
                        查看
                        </el-button>
                        <el-button link type="danger" size="small" @click="delvalue(scope.row.id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-col>
        <el-col :span="12">
            <div style="height: 800px;width: 800px; ">
                <map-baidu></map-baidu>
            </div>
        </el-col>
    </el-row>

    <child-page-drawer :msg="true">
        <template v-slot:header>
            添加门店
        </template>
        <template v-slot:body>
            <AddStoreAddress></AddStoreAddress>
        </template>
        
    </child-page-drawer>

</template>

<script setup lang="ts">
import AddStoreAddress from '../../components/drawer/addStoreAddress.vue';

const tableData = ref()
definePageMeta({
    layout:"home",
    meta:{
        id:10,
        auth:"admin",
        title:"门店地址",
        icon:"maplocaltion"
    }
})

$fetch(useRuntimeConfig().public.storeAddressUrl,{
    method:'GET',

}).then(res=>{
    tableData.value = res
})

const addStore = ()=>{
    usedrawer1().value = !usedrawer1().value
}


const handleClick  = ()=>{

}

const delvalue = (val: any)=>{
    $fetch(useRuntimeConfig().public.storeAddressUrl+'/'+val,{
        method:'DELETE',
    }).then(res=>{
       if(res){
        ElMessage({
                showClose: true,
                message: '成功添加',
                type: 'success',
        })
        reloadNuxtApp({
                ttl:2000
        })
       }else{
        ElMessage({
                showClose: true,
                message: '有误，请重试',
                type: 'error',
            })
       }
    })

}


</script>