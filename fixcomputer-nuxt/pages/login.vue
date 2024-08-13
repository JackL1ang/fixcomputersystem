<script setup lang="ts">

const loginForm = ref({
  username:'',
  password:''
})

const registerForm = ref({
  username:'',
  password:''
})

const loginStates = useLoginStates()

//成功弹出
const alertSuccess = ref(false)


//子组件传过来的值
const transValue = (value: any)=>{
  
  LoginButtonDisabled.value = value
  console.log(LoginButtonDisabled.value)

}

//注册
const onClick = () =>{
  
    loginStates.value = !loginStates.value
    LoginButtonDisabled.value = true
  
    
    
   
}

const LoginButtonDisabled = ref(true)

const alertDescription:globalThis.Ref<string> = ref('')
const alertError:globalThis.Ref<boolean> = ref(false)
//登录
const LoginButton = ()=>{
  if(LoginButtonDisabled.value==false){
      useFetch('/login',{
      
      baseURL: useRuntimeConfig().public.baseUrl,
      method: 'post',
      body:{username:loginForm.value.username,password:loginForm.value.password},
      onResponse({request , response,options}){
        if(response._data.state){
          alertSuccess.value = true
          setTimeout(() => {
            alertSuccess.value = false
          }, 1000);
          useUsername().value = response._data.username
          useshowUserInfo().value = true
          useshowComponentLoginButtonComponent().value = false
          useAuth().value = response._data.role
          //将后端返回信息接收
          const token = useCookie('token')
          token.value = response._data.token
          return navigateTo('/')
        }else{
          //错误将后端报错信息弹出
          useshowComponentLoginButtonComponent().value = true
          useshowUserInfo().value = false
          alertDescription.value = response._data.info
          alertError.value = true
          setTimeout(()=>
            alertError.value = false,
            2000
          )
          
        }
      },

      onRequestError({options,request,error}){
        if(error.name === "TypeError"){
          //错误将后端报错信息弹出
          alertDescription.value = "出现了异常，请重试"
          alertError.value = true
          setTimeout(()=>
            alertError.value = false,
            2000
          )
        }
      }

    })
  }else{
    //错误将后端报错信息弹出
    alertDescription.value = "请滑动验证块，先验证"
          alertError.value = true
          setTimeout(()=>
            alertError.value = false,
            2000
          )
  }
  
}

const registerButton = ()=>{
  $fetch('http://localhost:8080/register',{
    method: 'post',
    body:{username:registerForm.value.username,password:registerForm.value.password}
    

  }).then(res=>{
    
    if(res.state){
      alertSuccess.value = true
      console.log(useLoginStates().value)
      setTimeout(() => {
        alertSuccess.value = false
        useLoginStates().value = !useLoginStates().value
      }, 1000);
      
    }else{
      
      alertDescription.value = res.message
      alertError.value = true
      setTimeout(() => {
        alertError.value = false
      }, 2000);
    }
  })



}
    

</script>


<template>
  <ClientOnly>
    <div class="common-layout">
    <el-container>
        <el-header>
            <Header/>
        </el-header>
      <el-main>
        
        <div :class="{container:true,'sign-up-mode':loginStates}">
            <div class="form-warp">
              <form class="sign-in-form">
                <el-form class="sign-in-form" :model="loginForm" style="width: 290px;" >
                  <h2 class="form-title">登录</h2>
                  <el-alert
                    title="成功"
                    type="success"
                    description="成功注册"
                    show-icon
                    v-if="alertSuccess"
                  />
                  <el-alert
                    title="错误信息"
                    type="error"
                    :description=alertDescription
                    v-if="alertError"
                    show-icon
                  />
                  <el-input 
                  v-model="loginForm.username" 
                  placeholder="请输入账号" 
                  clearable 
                  />

                  <el-input
                    v-model="loginForm.password"
                    type="password"
                    placeholder="请输入密码"
                    show-password
                    @keyup.enter="LoginButton"
              
                    />

                </el-form>
                <slidebar @transemit="transValue" v-if="loginStates==false"/>
                <el-button type="primary" round @click="LoginButton" :disabled="LoginButtonDisabled">立即登录</el-button>
                  
              </form>
              <form class="sign-up-form" >
                <el-form class="sign-up-form" :model="registerForm" style="width: 290px;" >
                  <h2 class="form-title">注册</h2>
                  <el-alert
                    title="成功"
                    type="success"
                    description="成功注册"
                    show-icon
                    v-if="alertSuccess"
                  />
                  <el-alert
                    title="错误信息"
                    type="error"
                    :description=alertDescription
                    v-if="alertError"
                    show-icon
                  />
                  <el-input 
                  v-model="registerForm.username" 
                  placeholder="请输入账号" 
                  clearable 
                  />

                  <el-input
                    v-model="registerForm.password"
                    type="password"
                    placeholder="请输入密码"
                    show-password
                    />
                  <slidebar @transemit="transValue" v-if="loginStates"/>
                </el-form>
                <el-button type="primary" @click="registerButton" round :disabled="LoginButtonDisabled">立即注册</el-button>
              </form>
            </div>
            <div class="desc-warp">
            <div class="desc-warp-item sign-up-desc">
                <div class="content">
                <el-button type="default" @click="onClick">去注册</el-button>
                

                </div>
                
            </div>
            <div class="desc-warp-item sign-in-desc">
                <div class="content">
                  <el-button type="default" @click="onClick">去登录</el-button>
                </div>   
            </div>
            </div>
        </div>
      </el-main>
    </el-container>

  </div>
</ClientOnly>
</template>

<style scoped>

.el-header{
  --el-header-padding:0!important;
  --el-header-height:0px!important
  
}
.el-main{
  --el-main-padding:0!important;
  margin-top: 39px!important;
  height: 80%;
}


.container {
  position: relative;
  min-height: 95vh;
  width: 100%;
  overflow: hidden;
}
.container::before {
  content: " ";
  position: absolute;
  width: 2000px;
  height: 2000px;
  border-radius: 50%;
  background-image: linear-gradient(-45deg, #6266f5 0%, #04befe 100%);
  transition: 1.8s ease-in-out;
  z-index: 6;
  top: -10%;
  right: 48%;
  transform: translateY(-50%);
}
.container.sign-up-mode::before {
  transform: translate(100%, -50%);
}

.form-warp {
  width: 50%;
  position: absolute;
  z-index: 5;
  left: 75%;
  top: 50%;
  z-index: 5;
  transform: translate(-50%, -50%);
  display: grid;
  grid-template-columns: 1fr;
  transition: 1s 0.7s ease-in-out; 
}
.form-warp form {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  gap: 20px;
  /* 将两个 form 布局在 grid 同一位置 */
  grid-row: 1 / 2;
  grid-column: 1 / 2;
  transition: all 0.2s 0.7s;
  opacity: 1;
  z-index: 4;
}
.form-title {
  color: #6266f5;
}
.form-warp .sign-up-form {
  opacity: 0;
  z-index: 3;
}
.container.sign-up-mode .form-warp {
  left: 25%;
}

.container.sign-up-mode .sign-up-form {
  opacity: 1;
  z-index: 4;
}
input,

input::placeholder {
  color: #cccc;
}
.container.sign-up-mode .sign-in-form {
  opacity: 0;
  z-index: 3;
}
.desc-warp {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0;
  left: 0;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
}
.desc-warp-item {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  justify-content: space-around;
  text-align: center;
  text-align: center;
  padding: 3rem 17% 2rem 12%;
  z-index: 6;
}
/* 事件穿透 BEGIN */
.sign-in-desc {
  pointer-events: none;
}
.sign-up-mode .sign-in-desc {
  pointer-events: all;
}
.sign-up-mode .sign-up-desc {
  pointer-events: none;
}
/* 事件穿透 END */
.content {
  width: 100%;
  transition: transform 0.9s ease-in-out;
  transition-delay: .6s;
}
.sign-in-desc img,
.sign-in-desc .content {
  transform: translateX(800px);
}
.sign-up-mode .sign-in-desc img,
.sign-up-mode .sign-in-desc .content {
  transform: translateX(0);
}

.sign-up-mode .sign-up-desc img,
.sign-up-mode .sign-up-desc .content {
  transform: translateX(-800px);
}

img {
  width: 100%;
  display: block;
  transition: transform 0.9s ease-in-out;
  transition-delay: .5s;
}

/* 响应式 */
@media screen and (max-width: 870px) {
  .container::before {
    width: 1500px;
    height: 1500px;
    transform: translateX(-50%);
    left: 30%;
    bottom: 68%;
    right: initial;
    top: initial;
    transition: 2s ease-in-out;
  }
  .container.sign-up-mode::before {
    transform: translate(-50%, 100%);
    bottom: 32%;
    right: initial;
  }
  .form-warp {
    width: 100%;
    top: 75%;
    left: 50%;
    transform: translate(-50%, -100%);
    transition: 1s 0.8s ease-in-out;
  }
  .container.sign-up-mode .form-warp {
    top: 25%;
    left: 50%;
    transform: translate(-50%, 0);
  }
  img {
    width: 200px;
    transition: transform 0.9s ease-in-out;
    transition-delay: 0.7s;
  }
  .desc-warp {
    grid-template-columns: 1fr;
    grid-template-rows: 1fr 2fr 1fr;
  }
  .desc-warp-item {
    flex-direction: row;
    justify-content: space-around;
    align-items: center;
    padding: 2.5rem 8%;
    grid-column: 1 / 2;
  }
  .sign-in-desc {
    grid-row: 3 / 4;
  }

  .sign-in-desc img,
  .sign-in-desc .content {
    transform: translateY(800px);
  }

  .sign-up-mode .sign-in-desc img,
  .sign-up-mode .sign-in-desc .content {
    transform: translateY(0);
  }

  .sign-up-mode .sign-up-desc img,
  .sign-up-mode .sign-up-desc .content {
    transform: translateY(-800px);
  }
}

@media screen and (max-width: 570px) {
  .container::before {
    bottom: 72%;
    left: 50%;
  }
  img {
    display: none;
  }
}
</style>