/**
 * @autor syp
 * @content 消息群组页面js
 * @returns
 * @Time 2018-09-14
 */
layui.config({
	base : "../../../../static/js/"
}).extend({
	"publicUtil" : "publicUtil"
}) 
layui.use(['form','layer','application','upload','publicUtil'],function(){
    var form = layui.form,
    	application = layui.application,
    	publicUtil = layui.publicUtil,
		upload = layui.upload,
        layer = parent.layer,
        $ = layui.jquery;
	
	function formEdit(FormDatas){
		if(FormDatas != ''){
			var data = FormDatas;
			$(".id").val( publicUtil.htmlDecode(data.id));
			$(".remark").val( publicUtil.htmlDecode(data.remark));
			$(".description").val( publicUtil.htmlDecode(data.description));
			$(".name").val( publicUtil.htmlDecode(data.name));
		}else{
			return false;
		}
	}

	
	/**
	 * 表单回显
	 */
	formEdit(parent.editFormData);
	
    form.on("submit(addMesGroup)",function(){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
		
		console.log()
        
		$.ajax({
			url: application.SERVE_URL+"/mes/mesGroup/save", //ajax请求地址
			data:{
				id : $(".id").val() ==null|| $(".id").val() =="" ? null : $(".id").val(),
				description : $(".description").val(),
				name : $(".name").val(),
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