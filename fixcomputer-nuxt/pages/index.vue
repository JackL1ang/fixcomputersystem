<template>
    
<div class="index-page">
    <!--头部-->
    <Header/>
    
    <!--内容区-->
    <ClientOnly>

    
    <div class="banner">
        <div class="slide1 slide" @mouseenter="mouseenter_left" @mouseleave="mouseleave_left">
            <div class="bg-wrapper">
                <div class="bg">
                    <div class="mask" id="mask1"></div>
                </div>
            </div>
            <div class="content-wrapper">
                <NuxtLink to="/fixPage" class="content-inner">
                    <div class="title">{{ left_Title }}</div>
                    <div class=""></div>
                </NuxtLink>
            </div>
        </div>
        <div class="slide2 slide" @mouseenter="mouseenter_right" @mouseleave="mouseleave_right">
            <div class="bg-wrapper">
                <div class="bg">

                </div>
                <div class="mask" id="mask2">

                </div>
            </div>
            <div class="content-wrapper">
                <NuxtLink to="/allPost" class="content-inner">
                    <div class="title">{{ right_Title }}</div>
                    <div class=""></div>
                </NuxtLink>
            </div>


        </div>
    </div>

    </ClientOnly>
    <div class="footer">

    </div>


</div>
</template>



<script setup lang="ts">

const left_Title:String = "快 速 报 修"
const right_Title:String = "维 修 论 坛"




function mouseenter_left(event:any){
   
    const slide1_Style = event.target as HTMLElement
    slide1_Style.style['animation'] = "sizeup1 2s forwards"
   
    const slide2_Sytle = document.querySelector('.slide2') as HTMLElement
    slide2_Sytle.style['animation'] = "backup 2.5s forwards"

    const mask_Style = document.querySelector('#mask2') as HTMLElement
    mask_Style.style['opacity'] = "0.55"
}


function mouseleave_left(event:any){
    const slide1_Style = event.target as HTMLElement
    slide1_Style.style['animation'] = "sizeup2 2s forwards"

    const slide2_Sytle = document.querySelector('.slide2') as HTMLElement
    slide2_Sytle.style['animation'] = "leaveup 2.5s forwards"

    const mask_Style = document.querySelector('#mask2') as HTMLElement
    mask_Style.style['opacity'] = "0"
}

function mouseenter_right(event:any){
    const slide2_Style = event.target as HTMLElement
    slide2_Style.style['animation'] = "sizeup 2s forwards"

    const slide1_Sytle = document.querySelector('.slide1') as HTMLElement
    slide1_Sytle.style['animation'] = "blue_leave 2.5s forwards"

    const mask_Style = document.querySelector('#mask1') as HTMLElement
    mask_Style.style['opacity'] = "0.55"

}

function mouseleave_right(event:any){
    const slide2_Style = event.target as HTMLElement
    slide2_Style.style['animation'] = "red_back 2.5s forwards"

    const slide1_Sytle = document.querySelector('.slide1') as HTMLElement
    slide1_Sytle.style['animation'] = "red_back 2s forwards"

    const mask_Style = document.querySelector('#mask1') as HTMLElement
    mask_Style.style['opacity'] = "0"
}
//先把地图值获取
const test = async()=>{
  await nextTick()
  $fetch('/map',{
    baseURL:useRuntimeConfig().public.apiUrl,
    method:'GET'
  }).then(res=>{
    usedataInfo().value = res.data
    usegeoMap().value = res.geoCoordMap

  })

}
test()

</script >


<style >

html{
    height: 100%;
}
#__nuxt{
    height: 100%;
    
}

body{
    margin: 0;
    height: 100%;
    
}
.container{
    width: 100%;
    height: 100%;
    margin: 0;
    position: relative;
}
a{
    color: white;
    text-decoration: none !important;
}
.index-page{
    position: relative;
    background: black;
    height: 100%;
    min-height: 750px;
    overflow: hidden;
}

.index-right{
    float: right;
    margin-right: 10px;
}
.iconfont{
    margin-top: 12px;
    font-family: 'iconfont';
    font-size: 16px;
    font-style: normal;
}

.index-page .banner{
    overflow: hidden;
    white-space: nowrap;
    height: 100%;
}
.index-page .banner .slide{
    width: 80%;
    height: 100%;
    display: inline-block;
}
.index-page .banner .slide1{
    margin-left: -30%;
}

.index-page .banner .slide1 .bg-wrapper{
    margin-right: -20px;
}
.index-page .banner .slide1 .bg-wrapper .bg, .index-page .banner .slide1 .bg-wrapper .mask{
    right: -10%;
}
.index-page .banner .slide .bg-wrapper{
    position: relative;
    height: 100%;
    overflow: hidden;
    transform: skew(-5deg) translateZ(0);
}
.index-page .banner .slide .bg-wrapper .bg{
    position: absolute;
    top: 0;
    background-repeat: no-repeat;
    background-size: cover;
    width: 100%;
    height: 100%;
    transition: all .8s ease;
    transform: skew(5deg) translateZ(0);
}
.index-page .banner .slide1 .bg{
    background-image: url("http://192.168.146.128:9000/fixcomputer/index-right.jpg");
    margin-left: 20%;
    height: 100%;
}
.index-page .banner .slide2 .bg-wrapper{
    margin-left: -20px;
}

.index-page .banner .slide2 .bg{
    margin-left: -20%;
    background-image: url("http://192.168.146.128:9000/fixcomputer/index-left.jpg");
}
.index-page .banner .slide .bg-wrapper .mask{
    position: absolute;
    top: 0;
    width: 150%;
    height: 100%;
    background-color: black;
    opacity: 0;
    transition: opacity .8s;
}
.index-page .banner .slide2 .bg-wrapper .bg, .index-page .banner .slide2 .bg-wrapper .mask{
    left: -10%;
}
.index-page .banner .slide1 .content-wrapper{
            right: 0;
            margin-left: 30%\9;
        }
.index-page .banner .slide .content-wrapper{
    position: absolute;
    text-align: center;
    width: 62.5%;
    height: 100%;
    top: 0;
    z-index: 50;
}
.index-page .banner .slide .content-wrapper .content-inner{
    position: absolute;
    top: 50%;
    left: 50%;
    margin-top: -195px;
    margin-left: -235px;
}
.index-page .banner .slide2 .content-wrapper{
    left: 0;
    margin-left: 30%\9;
}
.index-page .banner .slide .title{
            width: 490px;
            height: 160px;
            margin: 0 auto 0;
            background-size:80%;
            background-repeat:no-repeat;
            background-position: center;
            transition:all .8s;
}
.slide.slide1.slide1-sizeup::before{
    animation: sizeup1 2s forwards
}
.slide2.slide2-backup::before{
    animation: backup 2.5s forwards;
}
.mask.mask2::before{
    opacity: "0.55";
}


@keyframes leaveup{
    from{
        transform: translate3d(5%,0,0);
    }to{
            transform: translate3d(0%,0,0);
        }
}
@keyframes backup{
    from{
        transform: translate3d(0%,0,0);
    }to{
            transform: translate3d(5%,0,0);
        }
}
@keyframes sizeup {
    from{
        transform: translate3d(0%,0,0);
    }to{
        transform: translate3d(-5%,0,0);
        }

}
@keyframes sizeup1 {
    from{
        transform: translate3d(0%,0,0);
    }to{
            transform: translate3d(5%,0,0);
        }

}
@keyframes sizeup2 {
    from{
        transform: translate3d(5%,0,0);
    }to{
            transform: translate3d(0%,0,0);
        }

}
@keyframes blue_leave {
    from{
        transform: translate3d(0%,0,0);
    }to{
            transform: translate3d(-5%,0,0);
        }

}
@keyframes blue_back {
    from{
        transform: translate3d(-5%,0,0);
    }to{
            transform: translate3d(0%,0,0);
        }

}
@keyframes red_back {
    from{
        transform: translate3d(-5%,0,0);
    }to{
            transform: translate3d(0%,0,0);
        }

}



</style>