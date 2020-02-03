校友小程序在此文件夹下进行版本管理。

======================================================================我是一条分割线=========================================================================================
#ajax 自定义请求方法
封装此请求是为了方便请求管理端。
1、在页面引入 import request from '@/common/request.js';
2、页面中直接使用
request.ajax({
    url:'',
    data:'',
    header: "",  
    method:'', 
    success:(res) =>{
        
    },
    
    error:(res) =>{
        
    }
})

3、注意：
请求url 只需要传入 app.serverPath 之后的 路径
当header为 Content-Type": "application/json" 时，不需要传入，当为'content-type': 'application/x-www-form-urlencoded',则需要设置
header : 'application/x-www-form-urlencoded'
method 当为post 请求时,不需要传递
其他特殊的上传使用 uni.request 原生进行请求

======================================================================我是一条分割线=========================================================================================
# vueX store 状态管理
	state: {
		hasLogin: false,
		token: {}
		
	}
    state 中为各种全局变量 如 hasLogin 是否登录
    当在登录页面登录成功后
    通过mapMutations修改状态
    import {
        mapMutations
    } from 'vuex';
    在method 中写入 
    //更新登录状态
    ...mapMutations(['login']),
    //并执行更新状态的方法
    //修改vueX state
    this.login(res.data.data.token);    
    
    //通过 mapState 获取vuex store 中的变量
    import {
        mapState
    } from 'vuex';
    
    然后通过计算函数
    computed:{
    	...mapState(['hasLogin'])
    },
    this.hasLogin 可以直接取到是否登录的值 。  详细解说请参考  vueX 官方网站
    
-----------------------------------------------------------------------------------------------------
2019-07-13 v2.0 封版
2.0版本
1、自动登录
2、人脸识别
3、小程序通知消息
4、埋点