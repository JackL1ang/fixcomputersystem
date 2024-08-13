import { useshowComponentLoginButtonComponent, useshowUserInfo } from "~/composables/login-states"

export default defineNuxtRouteMiddleware((to, from) => {
 
  const auth = useAuth()

  const username = useUsername()
    //token验证
        $fetch('verifyToken',{
          //设置request的头部带的参数
          onRequest({request,options}){
            options.headers = options.headers || {}
            options.headers.authorization  = "123"
            options.headers.token = useCookie('token').value
          },
          baseURL: useRuntimeConfig().public.apiUrl,
          onResponse({response}){
            
            const state = response._data.state
            
            if(state){
              useshowUserInfo().value = state
              useshowComponentLoginButtonComponent().value = false
              auth.value = response._data.role
              username.value = response._data.username
            }else{
              //将错误token清空
              useshowComponentLoginButtonComponent().value = true
              useshowUserInfo().value = false
              useCookie('token').value = undefined
            }
            
          }
        })
   
   
    
  
  
  })
  