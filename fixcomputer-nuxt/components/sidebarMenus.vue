<template>
<!--有子目录的-->
<el-sub-menu v-if="route?.children?.length > 0" :index="route.path">
    <template #title>
    <el-icon><location /></el-icon>
    <span>{{ route.title }}</span>
    </template>
    
    <el-menu-item v-for="item in route.children" :index="item.name">
        {{ item.meta.title }}
    </el-menu-item>
    <!-- <SidebarMenus v-for="item in route">

    </SidebarMenus> -->
                   

                 
</el-sub-menu>
<!--无子目录-->
<el-menu-item v-else :index="route.path">
    <el-icon v-if="route.icon=='home'"><House /></el-icon>
    <el-icon v-else-if="route.icon=='user'"><User /></el-icon>
    <el-icon v-else-if="route.icon=='setting'"><Setting /></el-icon>
    <el-icon v-else-if="route.icon=='notice'"><Notification /></el-icon>
    <el-icon v-else-if="route.icon=='tickets'"><Tickets /></el-icon>
    <el-icon v-else-if="route.icon=='star'"><Star /></el-icon>
    <el-icon v-else-if="route.icon=='messagebox'"><MessageBox /></el-icon>
    <el-icon v-else-if="route.icon=='maplocaltion'"><MapLocation/></el-icon>
    <el-icon v-else><icon-menu /></el-icon>
    <template #title>{{route.title}}</template>
</el-menu-item>

</template>
<script setup>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
defineProps({
    route:{
        type:Object,
        required:true
    }
})

</script>