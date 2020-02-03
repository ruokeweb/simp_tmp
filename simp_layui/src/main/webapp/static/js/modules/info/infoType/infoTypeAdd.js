/**
 * @autor syp
 * @content 信息类型增加页面js
 * @returns
 * @Time 2018-12-10
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
			$(".remark").val(publicUtil.htmlDecode(data.remark));
			$(".typeName").val(publicUtil.htmlDecode(data.typeName));
			$(".typeCode").val(publicUtil.htmlDecode(data.typeCode));
			$(".sort").val(publicUtil.htmlDecode(data.sort));
		}else{
			return false;
		}
	}

	
	/**
	 * 表单回显
	 */
	formEdit(parent.editFormData);
	
    form.on("submit(addinfoType)",function(){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var data = {
			id : $(".id").val() ==null|| $(".id").val() =="" ? null : $(".id").val(),
			typeCode : $(".typeCode").val(),
			typeName : $(".typeName").val(),
			sort : $(".sort").val(),
			remark : $(".remark").val()
		}
		$.ajax({
			url: application.SERVE_URL+"/info/infoType/save", //ajax请求地址
			data:data,
			success: function (res) {
				if(res.code==application.REQUEST_SUCCESS){
				 	top.layer.close(index);
		            top.layer.msg(res.msg,{time:1000});	
		            layer.closeAll("iframe");
		            //刷新父页面
		            parent.location.reload();
				}else{
					layer.msg(res.msg,{time:1000});
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