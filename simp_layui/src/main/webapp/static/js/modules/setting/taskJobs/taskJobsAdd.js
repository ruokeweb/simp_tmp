/**
 * @autor syp
 * @content 字典增加页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base : "../../../../static/js/"
}).extend({
	"validparam"  : "validparam"
}) 
layui.use(['form','layer','application','validparam','publicUtil'],function(){
    var form = layui.form,
    	application = layui.application,
    	validparam = layui.validparam,
    	publicUtil = layui.publicUtil,
        layer = parent.layer,
        $ = layui.jquery;
    
    form.verify(validparam);
	
	function formEdit(FormDatas){
		if(FormDatas != ""){
			var data = FormDatas;

			$(".id").val(publicUtil.htmlDecode(data.id));
			$(".jobGroup").val(publicUtil.htmlDecode(data.jobGroup));
			$(".jobName").val(publicUtil.htmlDecode(data.jobName));
			$(".cronExcute").val(publicUtil.htmlDecode(data.cronExcute));
			$(".className").val(publicUtil.htmlDecode(data.className));
			$("input:radio[name='status'][value='"+data.status+"']").attr('checked',true);
			$(".remark").val(publicUtil.htmlDecode(data.remark));
		}else{
			return false;
		}
		form.render();
	}

	
	/**
	 * 表单回显
	 */
	formEdit(parent.editFormData);
	
    form.on("submit(addtaskJobs)",function(){
        //弹出loading
		var index = top.layer.msg('数据提交中，请稍候', {
        	icon: 16,
        	time: false,
        	shade: 0.8
        });
		var data = {
			id : $(".id").val() ==null|| $(".id").val() =="" ? null : $(".id").val(),
			jobGroup : $(".jobGroup").val(),
			jobName : $(".jobName").val(),
			cronExcute : $(".cronExcute").val(),
			className : $(".className").val(),
			status : $("input[name='status']:checked").val(),
			remark : $(".remark").val()
		}
		$.ajax({
			url: application.SERVE_URL+"/task/taskJobs/save", //ajax请求地址
			data:data,
			success: function (res) {
				if(res.code==application.REQUEST_SUCCESS){
					console.log(res.code);		
					top.layer.msg(res.msg,{time:1000});	
					layer.closeAll("iframe");
					//刷新父页面
					parent.location.reload();
				}else{					
					layer.msg(res.msg,{time:1000});
					top.layer.close(index);
					parent.location.reload();
				}
			}
		}); 
        return false;
    })
	
	// $(".cronExcute").click(function(){
	// 	var index = layui.layer.open({
	// 		type: 2,
	// 		title: '定时任务时间设置',
	// 		shadeClose: true,
	// 		shade: 0.8,
	// 		area: ['80%', '70%'],
	// 		// content: '../views/module/system/menu/menuselect.html',
	// 		content: '/static/js/cron/index.html',
	// 		success : function(layero, index){
	// 			//
	// 			setTimeout(function(){
	// 					layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
	// 							tips: 3
	// 					});
	// 			},500)											
	// 		},
	// 	})
	// })		
	
	
	$("#close").click(function(){
		layer.closeAll("iframe");
		//刷新父页面
		parent.location.reload();	
	})
})