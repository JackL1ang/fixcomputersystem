
//登录状态
export const useLoginStates = () => useState<boolean>('loginState',()=>false)
//用户名
export const useUsername = ()=> useState<string>('username',()=>'')
//权限
export const useAuth = ()=>useState<String>('auth',()=>'')
//小窗状态
export const usedrawer = ()=>useState<boolean>('drawer',()=>false)

export const usedrawer1 = ()=>useState<boolean>('drawer1',()=>false)
//显示登录按钮
export const useshowComponentLoginButtonComponent = ()=>useState<boolean>('showComponentLoginButtonComponent',()=>true)
//显示用户信息
export const useshowUserInfo = ()=>useState<boolean>('showUserInfo',()=>false)
//订单状态
export const useFixOrderStatus = ()=>useState<number>('FixOrderStatus',()=>0)
//评分窗口
export const useRate = ()=>useState<boolean>('ratedialog',()=>false)
//QRCode
export const useQRCode = ()=>useState<boolean>('qrcodedialog',()=>false)
//物流信息
export const useWayBill = ()=>useState<boolean>('waybilldialog',()=>false)
//添加物流信息
export const usecompleteWaybill = ()=>useState<boolean>('completewaybilldialog',()=>false)


//date数据
export const usedataInfo = ()=>useState<String>('dataInfo',()=>'')

//geo坐标
export const usegeoMap = ()=>useState<String>('geoMapInfo',()=>'')
