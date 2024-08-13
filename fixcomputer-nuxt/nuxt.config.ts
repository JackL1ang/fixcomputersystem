// https://nuxt.com/docs/api/configuration/nuxt-config

export default defineNuxtConfig({
  modules: [
    '@element-plus/nuxt',
    '@vueuse/nuxt',
    '@pinia/nuxt',
    
  
  ],
  runtimeConfig:{
    public:{
      baseUrl:"http://www.localhost:8080",
      apiUrl:'http://www.localhost:8080/api',
      orderUrl:'http://www.localhost:8080/order',
      userUrl:'http://www.localhost:8080/user',
      orderUpdataUrl:'http://www.localhost:8080/update_order',
      partsUrl:'http://www.localhost:8080/parts',
      userAddressUrl:'http://www.localhost:8080/user_address',
      fixinfoUrl:'http://www.localhost:8080/fix_info',
      statisticUrl:'http://www.localhost:8080/statistic',
      rateUrl:'http://www.localhost:8080/rate',
      notice:'http://www.localhost:8080/notice',
      pageUrl:'http://www.localhost:8080/page',
      imageUrl:'http://www.localhost:8080/image',
      forumUrl:'http://www.localhost:8080/forum',
      storeAddressUrl:'http://www.localhost:8080/map'
    }
  },

  nitro: {
    plugins: ['plugins/nitroPlugin.ts']
  },


})
