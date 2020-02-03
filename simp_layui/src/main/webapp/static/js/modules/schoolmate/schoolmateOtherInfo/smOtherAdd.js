/**
 * @autor syp
 * @content 校友其他信息编辑页面js
 * @returns
 * @Time 2019-03-04
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
			$(".value").val(publicUtil.htmlDecode(data.value));
			$(".name").val(publicUtil.htmlDecode(data.name));
		}else{
			return false;
		}
	}

	
	/**
	 * 表单回显
	 */
	formEdit(parent.editFormData);
	
    form.on("submit(addSmOther)",function(){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var data = {
			id : $(".id").val() ==null|| $(".id").val() =="" ? null : $(".id").val(),
			name : $(".name").val(),
			value : $(".value").val(),
			remark : $(".remark").val()
		}
		$.ajax({
			url: application.SERVE_URL+"/sm/smOther/save", //ajax请求地址
			data:data,
			success: function (res) {
				if(res.code==application.REQUEST_SUCCESS){
				 	top.layer.close(index);
		            layer.msg(res.msg,{time:1000});	
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