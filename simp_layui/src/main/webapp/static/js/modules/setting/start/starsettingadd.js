/**
 * @autor syp
 * @content 星级增加页面js
 * @returns
 * @Time 2018-09-12
 */
layui.config({
	base : "../../../../static/js/"
}).extend({
	"validparam"  : "validparam",
	publicUtil : "publicUtil"
}) 
layui.use(['form','layer','application','validparam','publicUtil'],function(){
    var form = layui.form,
    	application = layui.application,
    	validparam = layui.validparam,
    	publicUtil = layui.publicUtil,
        layer = parent.layer,
        $ = layui.jquery;
    
	
	
	$(function(){
		formEdit(parent.editFormData);

	})
	
	function initCheckBox(values){		
		$.ajax({
			url: application.SERVE_URL+"/settings/smStarSminfo/getStarInfo", //ajax请求地址
			data:{},
			success: function (res) {
				if(res.code==application.REQUEST_SUCCESS){
					var data = res.data;
					$("#starInfocheck").empty();
					if(values){
						for(var key in data){
							if(-1 == $.inArray(key, values)){
								$("#starInfocheck").append("<input type='checkbox' name= 'starInfoCode' title='"+data[key]+"' value = '"+key+"'>")					
							}else{
								$("#starInfocheck").append("<input type='checkbox' name= 'starInfoCode' checked title='"+data[key]+"' value = '"+key+"'>")	
							}
						}
					}else{
						for(var key in data){
							$("#starInfocheck").append("<input type='checkbox' name= 'starInfoCode' title='"+data[key]+"' value = '"+key+"'>")	
						}
					}				
					form.render();
				}else{
					layer.msg(res.msg);
				}
			}
		}); 
	}
	
	function getCheckBoxVal(){
		var arr =[];
		$.each($('input:checkbox:checked'),function(){
			arr.push($(this).val());
        });
		return arr;
	}
	
	
	form.verify({
		starLevel : function(value, item){
			if(value > 10 || value <0){
				return "目前只支持到10星";
			}
		}
	});
	
	
	function formEdit(FormDatas){
		if(FormDatas != ''){
			var data = FormDatas;
			$(".id").val( publicUtil.htmlDecode(data.smStar.id));
			$(".name").val( publicUtil.htmlDecode(data.smStar.name));
			$(".remark").val( publicUtil.htmlDecode(data.smStar.remark));
			$(".infoLevel").val( publicUtil.htmlDecode(data.smStar.infoLevel));
			initCheckBox(data.startInfoCodes);
			form.render();
		}else{
			initCheckBox();
			return false;
		}
	}

	
	/**
	 * 表单回显
	 */
	// formEdit(parent.editFormData);
	
    form.on("submit(addStarInfo)",function(){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
		$.ajax({
			url: application.SERVE_URL+"/settings/smStarSminfo/saveStarInfo", //ajax请求地址
			contentType: "application/json",
			data:JSON.stringify({
				remark : $(".remark").val(),
				smStar : {
					id : $(".id").val() ==null|| $(".id").val() =="" ? null : $(".id").val(),
					name : $(".name").val(),
					infoLevel : $(".infoLevel").val(),
					remark : $(".remark").val()
				},
				startInfoCodes : getCheckBoxVal()
			}),
			success: function (res) {
				if(res.code==application.REQUEST_SUCCESS){
					console.log('success');
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