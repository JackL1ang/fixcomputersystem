import {defineStore} from 'pinia'

export const loginState = defineStore("loginState",{
    state:()=>{
        return{loginState:false}
    }
})