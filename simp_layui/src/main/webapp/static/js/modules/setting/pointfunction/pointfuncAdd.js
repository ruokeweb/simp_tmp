/**
 * @autor syp
 * @content 积分等级增加页面js
 * @returns
 * @Time 2018-09-14
 */
layui.config({
	base : "../../../../static/js/"
}).extend({
	"validparam"  : "validparam"
}) 
layui.use(['form','layer','application','validparam','upload','publicUtil'],function(){
    var form = layui.form,
    	application = layui.application,
    	validparam = layui.validparam,
    	publicUtil = layui.publicUtil,
		upload = layui.upload,
        layer = parent.layer,
        $ = layui.jquery;
    
    form.verify({
    	score: function(value){
    		var message="";
    		//判断是否是编辑
			if (/^(\+|-)?\d+($|\ \d+$)/.test(value)){
				return;
			}else{
				message = "只能输入整数"
				return message;
			}
    	}
    });
	
	
	function formEdit(FormDatas){
		if(FormDatas != ''){
			var data = FormDatas;
			$(".id").val( publicUtil.htmlDecode(data.id));
			$(".remark").val( publicUtil.htmlDecode(data.remark));
			$(".point").val( publicUtil.htmlDecode(data.point));
			$(".code").val( publicUtil.htmlDecode(data.code));
			$(".name").val( publicUtil.htmlDecode(data.name));
			publicUtil.selectBaseAndSetVal(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'BOOLEAN_TYPE'} ,"isuse",data.isuse);	
		}else{
			publicUtil.selectBase(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'BOOLEAN_TYPE'} ,"isuse");	
			return false;
		}
	}



	
	/**
	 * 表单回显
	 */
	formEdit(parent.editFormData);
	
    form.on("submit(addsmpointfunc)",function(){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
		
		console.log()
        
		$.ajax({
			url: application.SERVE_URL+"/settings/pointFunction/save", //ajax请求地址
			data:{
				id : $(".id").val() ==null|| $(".id").val() =="" ? null : $(".id").val(),
				code : $(".code").val(),
				point : $(".point").val(),
				name : $(".name").val(),
				isuse : $("#isuse").val(),
				remark : $(".remark").val()
			},
			success: function (res) {
				if(res.code==application.REQUEST_SUCCESS){
				 	top.layer.close(index);
		            top.layer.msg(res.msg);	
		            layer.closeAll("iframe");
		            //刷新父页面
		            parent.location.reload();
				}else{
					layer.msg(res.msg);
				}
			}
		}); 
        return false;
    })
	
	$("#close").click(function(){
		layer.closeAll("iframe");
		//刷新父页面
		parent.location.reload();	
	})
})