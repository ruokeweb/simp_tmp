/**
 * @autor syp
 * @content 登录页面js
 * @returns
 * @Time 2018-08-03
 */
layui.config({
	base : "static/js/"
}).extend({
	"application" : "application"
})


layui.use(['form','layer','application'],function(){
    var form = layui.form,
		application = layui.application,
        layer = parent.layer === undefined ? layui.layer : top.layer;
    var keyTime =new Date().getTime().toString();
		
		//清缓存
		window.sessionStorage.clear();
		window.localStorage.clear();	

	    //登录按钮
	    form.on("submit(login)",function(data){
	    	//构建弹窗
			layer.open({
				type: 1,
				skin: "layui-layer-rim", //加上边框
				title: false,
				closeBtn: 1,
				anim: 2,
				area: ['440px', '255px'], //宽高
				content: "<div id='verify'></div>"
			});
			
			//验证登录
			$('#verify').slideVerify({
	    		title:"",
	    	    type : 2,
	    	    mode : 'fixed',
	    	    vOffset : 5,
	    	    vSpace : 5,
	    	    explain : '向右滑动完成验证',
	    	    imgUrl : 'static/verify/images/',
	    	    imgName : ['1.png', '2.png', '3.png','4.png', '5.png'],
	    	    imgSize : {
	    		    width: '430px',
	    		    height: '200px',
	    	    },
	    	    blockSize : {
	    		    width: '45px',
	    		    height: '45px',
	    	    },
	    	    barSize : {
	    		    width: '430px',
	    		    height : '40px',
	    	    },
	    	    ready : function() {
	    	    },
	    	    success : function() {
	    	    	//登录
	    	    	var password = application.encryptData(application.KEY,$("#password").val());
	    	    	
					//请求登录
					$.ajax({
						url: application.SERVE_URL+"/login", //ajax请求地址
						type: "POST",
						dataType: "json",
						headers : { 
							"Authorization" : ""
						},
						data: { 
							username : $("#username").val(),
							password : password,
							// authCode : $("#code").val(),
							comeFrom : application.COMEFROM
						},
						success: function (data) {
							if(data.code==application.REQUEST_SUCCESS){
								//将token保存在cookie中	
								sessionStorage.setItem("token", data.data.restToken.token);
								sessionStorage.setItem("tokenTime", data.data.restToken.tokenTime);
								sessionStorage.setItem("isUpdatePassword", JSON.stringify(data.data.updatePasswordVo));
								if(data.msg != "登录成功！"){
									layer.closeAll();
									layer.confirm("系统许可证到期还剩："+data.msg+"天"+"。<br/>"+ '<a href ="/views/module/license/licenselicupdate.html" style="color:red" >点击此处可上传相关系统许可证</a>', {
										btn: ['确定'],
										btnAlign: 'c',
										icon: 4,
										title: '温馨提示'
									}, function () {
										top.layer.msg("登录成功",{anim: 5,time: 1000},function(){
											layer.closeAll();
											window.location.href = "index.html";
										});
									})
								}else{
									top.layer.msg("登录成功",{anim: 5,time: 1000},function(){
										layer.closeAll();
										window.location.href = "index.html";
									});
								}

								// layer.open({
								// 	title: '温馨提示'
								// 	,content: "登录成功"
								// 	,success: function(){
								// 		alert();
								// 		window.location.href = "index.html"
								// 	}
								// });

							}else if(data.code == 601){
								debugger;
								sessionStorage.setItem("token","111");
								sessionStorage.setItem("tokenTime",new Date().getTime()+100000)
								//layer.closeAll();
								// top.layer.msg("登录成功",{anim: 5,time: 1000},function(){
								// 	layer.closeAll();
								// 	window.location.href = "index.html";
								// });
								// layer.tips('<a href ="/views/module/license/licenselicinfo.html" >点击此处可上传相关系统许可证</a>', '#login');
								layer.closeAll();
								top.layer.msg("请上传系统许可证相关证书<br/>页面跳转中......",{anim: 5,icon: 2,time: 4000,shade : [0.5 , '#000' , true]},function(){
									window.location.href = "/views/module/license/licenselicupdate.html"
								});
								// layer.closeAll();
								// layer.confirm("请上传相关系统许可证，点击确认上传", {
								// 	btn: ['确定'],
								// 	btnAlign: 'c',
								// 	icon: 4,
								// 	title: '温馨提示'
								// }, function () {
								// 	top.layer.msg("上传证书",{anim: 5,time: 1000},function(){
								// 		layer.closeAll();
								// 		window.location.href = "/views/module/license/licenselicupdate.html";
								// 	});
								// })

							}else{
								top.layer.msg(data.msg,{anim: 5,time: 1000},function(){
									layer.closeAll();
								});
							}
						},
						error: function(data){
							var result=data.responseJSON;
							if(result==undefined){
								top.layer.msg("服务连接中断，请检查网络连接情况",{anim: 5,time: 5000},function(){
									layer.closeAll();
								});
							}else{
								top.layer.msg(result.msg,{anim: 5,time: 1000},function(){
									layer.closeAll();
								});								
								$(this).text("登录").removeAttr("disabled").removeClass("layui-disabled");
								layer.closeAll();
							}
						}
					});
	    	    },
	    	    error : function() {
	    	    	top.layer.msg("验证码不匹配,请重新验证！",{time:1000});
	    	    }
	    	    
	    	});
			  return false;
      
    })


	$("#codeImg").on('click',function(){
		document.getElementById("codeImg").src = document.getElementById("codeImg").src + "?nocache=" + new Date().getTime();
	})



    //表单输入效果
    $(".loginBody .input-item").click(function(e){
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function(){
    	//$(this).attr("placeholder","");
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function(){
        $(this).parent().removeClass("layui-input-focus");
        if($(this).val() != ''){
            $(this).parent().addClass("layui-input-active");
        }else{
            $(this).parent().removeClass("layui-input-active");
        }
    })
    
//    setInterval(runBack(),100);
//    
//	function runBack(currentImage){
//	    	var imgArr=["login_bg.jpg", "login_bg01.jpg", "login_bg02.jpg"];
//	    	var img =parseInt(Math.random()*(imgArr.length)); 
//	    	var currentImage=imgArr[img];
//	        $(".loginBody").animate({opacity:1},700,function(){
//	    		$(".loginBody").css("background-image","url(static/images/"+currentImage+")");	
//	    	})
//	    	$(".loginBody").css("background-image","url(static/images/"+currentImage+")");	
//	 }

})


   
