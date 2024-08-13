//获取公钥
export default defineEventHandler(async (event) =>{
    
    console.log(event)
    const publicKey = await $fetch('http://localhost:8080/api/getpublickey',{
        method: 'get',
    })

   
    return publicKey
})
