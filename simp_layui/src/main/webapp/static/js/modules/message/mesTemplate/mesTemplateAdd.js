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
layui.use(['form','layer','application','laydate','upload','publicUtil'],function(){
    var form = layui.form,
    	application = layui.application,
    	publicUtil = layui.publicUtil,
		upload = layui.upload,
        layer = parent.layer,
		laydate = layui.laydate,
        $ = layui.jquery;
		
	function formEdit(FormDatas){
		if(FormDatas != ''){
			var data = FormDatas;
			$(".id").val( publicUtil.htmlDecode(data.id));
			$(".remark").val( publicUtil.htmlDecode(data.remark));
			$("#content").val( publicUtil.htmlDecode(data.content));
			$(".name").val( publicUtil.htmlDecode(data.name));
			if(data.backgroupImg != null || data.backgroupImg != ''){
				document.getElementById("virtualPhoto").src= application.SERVE_URL + application.FILEPATH + data.backgroupImg;
				$('#photoPath').html(data.backgroupImg);
			}			
			publicUtil.selectBaseAndSetVal(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'MES_TEMPLATE_TYPE'} ,"type",data.type);
		}else{
			publicUtil.selectBase(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'MES_TEMPLATE_TYPE'} ,"type");
			return false;
		}
	}

	
		var uploadInst = upload.render({
			elem: '#selectphoto',
			url: application.SERVE_URL + '/file/upload/',
			accept: 'images',
			exts : 'jpg|png|gif|bmp|jpeg',
			size : 500,
			choose: function(obj){
				//预读本地文件示例，不支持ie8
				obj.preview(function(index, file, result){
					$('#virtualPhoto').attr('src', result); //图片链接（base64）
				});
			},
			done: function(res){
				var data=res;
				if(data.code==application.REQUEST_SUCCESS){
					$('#photoPath').html(res.data);
					top.layer.msg(data.msg,{time: 1000});
				}else{
					top.layer.msg(data.msg+"("+data.code+")",{time: 1000});
				}
			}
		});	
	
	/**
	 * 表单回显
	 */
	formEdit(parent.editFormData);
	
    form.on("submit(addMesTemplate)",function(){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
		
		console.log()
        
		$.ajax({
			url: application.SERVE_URL+"/mes/mesTemplate/save", //ajax请求地址
			data:{
				id : $(".id").val() ==null|| $(".id").val() =="" ? null : $(".id").val(),
				content : $("#content").val(),
				name : $(".name").val(),
				remark : $(".remark").val(),
				type : $("#type").val(),
				backgroupImg : $('#photoPath').html()
				
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