<template>
    <div class="drag" ref="dragDiv">
      <div class="drag_bg"></div>
      <div class="drag_text">{{confirmWords}}</div>
      <div ref="moveDiv" @mousedown="mouseDownFn" :class="{'handler_ok_bg':confirmSuccess}" class="handler handler_bg" style="position: absolute;top: 0;left: 0;"></div>
    </div>
  </template>
  
  <script setup lang='ts'>
        const emit = defineEmits(["transemit"])
        const dragDiv = ref()
        const moveDiv = ref()
     
        const beginClientX =  ref(0)        /*距离屏幕左端距离*/
        const mouseMoveState= ref(false)     /*触发拖动状态  判断*/
        const maxWidth =  ref()             /*拖动最大宽度，依据滑块宽度算出来的*/
        const confirmWords= ref('向右拖动滑块验证' )  /*滑块文字*/
        const confirmSuccess= ref(false)            /*验证成功判断*/
      
    
  
      //mousedown 事件
      function mouseDownFn (event: { preventDefault: () => any; clientX: number; }) {
        console.log(event)
        if(!confirmSuccess.value){
          event.preventDefault && event.preventDefault();   //阻止文字选中等 浏览器默认事件
          mouseMoveState.value = true;
          beginClientX.value = event.clientX;
        }
      }
      //验证成功函数
      function successFunction(this: any){
        confirmSuccess.value = true;
        confirmWords.value = '验证通过';
        //验证成功
        if(window.addEventListener){
          //验证后将数据发回父组件
          emit('transemit',false)
          document.getElementsByTagName('html')[0].removeEventListener('mousemove',mouseMoveFn);
          document.getElementsByTagName('html')[0].removeEventListener('mouseup',moseUpFn);
        }else {
          
          document.getElementsByTagName('html')[0].removeEventListener('mouseup',()=>{});
        }
        document.getElementsByClassName('drag_text')[0].style.color = '#fff';
        document.getElementsByClassName('handler')[0].style.left = maxWidth.value + 'px';
        document.getElementsByClassName('drag_bg')[0].style.width = maxWidth.value + 'px';
      }
      //mousemove事件
      function mouseMoveFn(e: { clientX: number; }){
        if(mouseMoveState.value){
          let width = e.clientX - beginClientX.value;
          if(width>0 && width<=maxWidth.value){
            document.getElementsByClassName('handler')[0].style.left = width + 'px';
            document.getElementsByClassName('drag_bg')[0].style.width = width + 'px';
          }else if(width>maxWidth.value){
            successFunction();
          }
        }
      }
      //mouseup事件
      function moseUpFn(e: { clientX: number; }){
        mouseMoveState.value = false;
        console.log(e)
        const width = e.clientX - beginClientX.value;
        if(width<maxWidth.value){
          document.getElementsByClassName('handler')[0].style.left = 0 + 'px';
          document.getElementsByClassName('drag_bg')[0].style.width = 0 + 'px';
        }
      }
  
      
      onMounted(()=>{
        const test = async()=>{
          await nextTick()
          
        maxWidth.value = dragDiv.value.clientWidth - moveDiv.value.clientWidth;
        console.log(maxWidth.value)
        document.getElementsByTagName('html')[0].addEventListener('mousemove',mouseMoveFn);
        document.getElementsByTagName('html')[0].addEventListener('mouseup',moseUpFn)
        }
        test()
      })
     
      
    
  
  </script>
  <style scoped>
  .drag{
    position: relative;
    width: 300px;
    height: 40px;
    line-height: 40px;
    text-align: center;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  .handler{
    width: 40px;
    height: 38px;
    border: 1px solid #ccc;
    border-radius: 4px;
    cursor: move;
  }
  .handler_bg{
    background: #fff url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA3hpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNS1jMDIxIDc5LjE1NTc3MiwgMjAxNC8wMS8xMy0xOTo0NDowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo0ZDhlNWY5My05NmI0LTRlNWQtOGFjYi03ZTY4OGYyMTU2ZTYiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6NTEyNTVEMURGMkVFMTFFNEI5NDBCMjQ2M0ExMDQ1OUYiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NTEyNTVEMUNGMkVFMTFFNEI5NDBCMjQ2M0ExMDQ1OUYiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTQgKE1hY2ludG9zaCkiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo2MTc5NzNmZS02OTQxLTQyOTYtYTIwNi02NDI2YTNkOWU5YmUiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6NGQ4ZTVmOTMtOTZiNC00ZTVkLThhY2ItN2U2ODhmMjE1NmU2Ii8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+YiRG4AAAALFJREFUeNpi/P//PwMlgImBQkA9A+bOnfsIiBOxKcInh+yCaCDuByoswaIOpxwjciACFegBqZ1AvBSIS5OTk/8TkmNEjwWgQiUgtQuIjwAxUF3yX3xyGIEIFLwHpKyAWB+I1xGSwxULIGf9A7mQkBwTlhBXAFLHgPgqEAcTkmNCU6AL9d8WII4HOvk3ITkWJAXWUMlOoGQHmsE45ViQ2KuBuASoYC4Wf+OUYxz6mQkgwAAN9mIrUReCXgAAAABJRU5ErkJggg==") no-repeat center;
  }
  .handler_ok_bg{
    border-top-left-radius: 0;
    border-bottom-left-radius: 0;
    background: #fff url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA3hpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNS1jMDIxIDc5LjE1NTc3MiwgMjAxNC8wMS8xMy0xOTo0NDowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDo0ZDhlNWY5My05NmI0LTRlNWQtOGFjYi03ZTY4OGYyMTU2ZTYiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6NDlBRDI3NjVGMkQ2MTFFNEI5NDBCMjQ2M0ExMDQ1OUYiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6NDlBRDI3NjRGMkQ2MTFFNEI5NDBCMjQ2M0ExMDQ1OUYiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTQgKE1hY2ludG9zaCkiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDphNWEzMWNhMC1hYmViLTQxNWEtYTEwZS04Y2U5NzRlN2Q4YTEiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6NGQ4ZTVmOTMtOTZiNC00ZTVkLThhY2ItN2U2ODhmMjE1NmU2Ii8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+k+sHwwAAASZJREFUeNpi/P//PwMyKD8uZw+kUoDYEYgloMIvgHg/EM/ptHx0EFk9I8wAoEZ+IDUPiIMY8IN1QJwENOgj3ACo5gNAbMBAHLgAxA4gQ5igAnNJ0MwAVTsX7IKyY7L2UNuJAf+AmAmJ78AEDTBiwGYg5gbifCSxFCZoaBMCy4A4GOjnH0D6DpK4IxNSVIHAfSDOAeLraJrjgJp/AwPbHMhejiQnwYRmUzNQ4VQgDQqXK0ia/0I17wJiPmQNTNBEAgMlQIWiQA2vgWw7QppBekGxsAjIiEUSBNnsBDWEAY9mEFgMMgBk00E0iZtA7AHEctDQ58MRuA6wlLgGFMoMpIG1QFeGwAIxGZo8GUhIysmwQGSAZgwHaEZhICIzOaBkJkqyM0CAAQDGx279Jf50AAAAAABJRU5ErkJggg==") no-repeat center;
  }
  .drag_bg{
    background-color: #7ac23c;
    height: 38px;
    width: 0;
    border-radius: 4px 0 0 4px;
  }
  .drag_text{
    position: absolute;
    top: 0;
    width: 100%;text-align: center;
    -moz-user-select: none;
    -webkit-user-select: none;
    user-select: none;
    -o-user-select:none;
    -ms-user-select:none;
  }
  </style>