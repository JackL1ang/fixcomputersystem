import {defineStore} from 'pinia';
import { useAuth } from '~/composables/login-states';


export default defineStore('menu',()=>{
   
    const router = useRouter().getRoutes()
     console.log(router)
    const auth = useAuth().value
    console.log(auth)
    const result: any[]= []
    router.forEach((item: {
        children: any; meta: { 
            meta: {
            children: any; 
            auth: any; 
            path: any; 
            icon:any;
        }; }; 
        path: any; 
    }) =>{
        //从meta获取带有标识的meta作为侧边栏
        
        if(item.meta.meta){
           item.meta.meta.path = item.path
           
            //将共有的加入menus中
            if(item.meta.meta.auth === auth || item.meta.meta.auth === "public"){
                console.log(item)
                //meta放入result中
                result.push(item.meta.meta)
                //判断路由中有无子路由
                if(item.children.length != 0){
                    
                    //遍历子路由
                    item.children.forEach((item: any) =>{
                       
                        //将子路由路径'-'改为'/'
                        item.name="/"+item.name.replaceAll("-","/")
                        
                        
                    })
                    if(item.children[0].path === ':uid()' || item.children[0].path === ':id()' || item.children[0].path ===':orderid()'){

                    }else{
                        item.meta.meta.children = item.children
                    }
                    
                    
                
                }
            }
            if(auth==="admin"||auth==="fixer"){
                if(item.meta.meta.auth === "hybrid"){
                    result.push(item.meta.meta)
                }
            }
        }
    
        
        
    })
    //进行排序
    result.sort((a,b)=>a.id-b.id)
    return { result }
})

