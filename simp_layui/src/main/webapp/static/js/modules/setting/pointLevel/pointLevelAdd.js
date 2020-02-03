/**
 * @autor syp
 * @content 积分等级增加页面js
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
	
	
    form.verify({
    	level: function(value){
    		var message="";
    		//判断是否是编辑
			if (/^(\+)?\d+($|\\d+$)/.test(value)){
				return;
			}else{
				message = "只能输入整数"
				return message;
			}
    	},
		endlevel: function(value){
			var message="";
			if(value < $(".startPoint").val()){
				message = "结束积分必须大于开始积分"
			}
			return message;
		}
    });	
	
	function formEdit(FormDatas){
		if(FormDatas != ''){
			var data = FormDatas;
			$(".id").val( publicUtil.htmlDecode(data.id));
			$(".remark").val( publicUtil.htmlDecode(data.remark));
			$(".value").val( publicUtil.htmlDecode(data.value));
			$(".code").val( publicUtil.htmlDecode(data.code));
			$(".name").val( publicUtil.htmlDecode(data.name));
			$(".startPoint").val( publicUtil.htmlDecode(data.startPoint));
			$(".endPoint").val( publicUtil.htmlDecode(data.endPoint));
            $('#photoPath').attr('src', application.SERVE_URL + "/" + data.levelIcon);
            if(data.levelIcon != null || data.levelIcon != ''){
                document.getElementById("photo").src= application.SERVE_URL + application.FILEPATH + data.levelIcon;
                $('#photoPath').html(data.levelIcon);
            }

		}else{
			return false;
		}
	}
	/**
	 * 等级图标上传
	 */
	var uploadInst = upload.render({
		elem: '#selectphoto'
		,url: application.SERVE_URL + '/file/upload/'
		,accept: 'images'
		,exts : 'jpg|png|gif|bmp|jpeg'
		,size : 50
		,choose: function(obj){
			//预读本地文件示例，不支持ie8
			obj.preview(function(index, file, result){
				$('#photo').attr('src', result); //图片链接（base64）
			});
		}
		,done: function(res){
			$('#photoPath').html(res.data);
			//如果上传失败
			if(res.code > 0){
				return layer.msg('上传成功');							
			}
			//上传成功
		}
		,error: function(){
			//演示失败状态，并实现重传
			var photoPath = $('#photoPath');
			photoPath.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
			photoPath.find('.demo-reload').on('click', function(){
				uploadInst.upload();
			});
		}
	});	


	
	/**
	 * 表单回显
	 */
	formEdit(parent.editFormData);
	
    form.on("submit(addSmLevel)",function(){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});        
		$.ajax({
			url: application.SERVE_URL+"/settings/pointLevel/save", //ajax请求地址
			data:{
				id : $(".id").val() ==null|| $(".id").val() =="" ? null : $(".id").val(),
				code : $(".code").val(),
				value : $(".value").val(),
				name : $(".name").val(),
				startPoint : $(".startPoint").val(),
				endPoint : $(".endPoint").val(),
				levelIcon :$('#photoPath').html(),
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