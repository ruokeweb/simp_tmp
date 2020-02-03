layui.config({
	base: '../../../../static/js/' // 此处路径请自行处理, 可以使用绝对路径
}).extend({
	"application": "application"
});

layui.use(['jquery', 'form', 'layer', 'laydate', 'publicUtil', 'upload', 'application'],function () {
		var form = layui.form,
			application = layui.application,
			publicUtil = layui.publicUtil,
			layer = layui.layer,
			laydate = layui.laydate,
			$ = layui.jquery;
		//获取表单定义
		$.ajax({
			url: application.SERVE_URL + '/act/actSetting/loadAll?activityId='+parent.editFormData.id, 
			success: function (data) {
				if (data.code == application.REQUEST_SUCCESS) {				
					showForm(data.data);
					form.render();
				} else {
					top.layer.msg(data.msg + "(" + data.code + ")");
				}
			}
		});
		//获取活动信息
		$.ajax({
			url: application.SERVE_URL + '/act/actActivity/get?id='+parent.editFormData.id, 
			success: function (data) {
				if (data.code == application.REQUEST_SUCCESS) {
					$("#actContent").html(publicUtil.htmlDecode(data.data.content));
				} else {
					top.layer.msg(data.msg + "(" + data.code + ")");
				}
			}
		});
		
		//拼接dom
		function showForm(data){
			$.each(data,function(index,value){
				//参数
				var code=value.code;
		    	var name=value.name;
		    	var defaultVal=value.defaultVal;
		    	var domval="";
			    if(value.formType==="TEXT"){
			    	domval="<div class='layui-form-item'><label class='layui-form-label'>"+name+"</label><div class='layui-input-block'><input type='text' class='layui-input name' id='" +code+ "'lay-verify='required' name='"+code+"' value='"+defaultVal+"'> </div></div>";
			    	
			    }else if(value.formType==="SELECT"){
			    	var vals=defaultVal.split(",");
			    	var opt="";
			    	for(i=0;i<vals.length;i++){
			    		opt=opt+"<option value='"+vals[i]+"'>"+vals[i]+"</option>";
			    	}
			    	domval="<div class='layui-form-item'><label class='layui-form-label'>"+name+"</label><div class='layui-input-block'><select id='" +code+ "' name='"+code+"'>"+opt+"</select></div></div>";
			    }else if(value.formType==="CHECKBOX"){
			    	var vals=defaultVal.split(",");
			    	var opt="";
			    	for(i=0;i<vals.length;i++){
			    		opt=opt+"<input type='checkbox' name='"+code+"' value='"+vals[i]+"' title='"+vals[i]+"'>";
			    	}
			    	domval="<div class='layui-form-item'><label class='layui-form-label'>"+name+"</label><div class='layui-input-block'>"+opt+"</div></div>";
			    }else if(value.formType==="RADIO"){
			    	var vals=defaultVal.split(",");
			    	var opt="";
			    	for(i=0;i<vals.length;i++){
			    		opt=opt+"<input type='radio' name='"+code+"' value='"+vals[i]+"' title='"+vals[i]+"'>";
			    	}
			    	domval="<div class='layui-form-item'><label class='layui-form-label'>"+name+"</label><div class='layui-input-block'>"+opt+"</div></div>";
			    }else if(value.formType==="TEXTAREA"){
			    	domval="<div class='layui-form-item'><label class='layui-form-label'>"+name+"</label><div class='layui-input-block'><textarea placeholder='请输入内容' class='layui-textarea' id='"+code+"' name='"+code+"'></textarea></div></div>";
			    }else if(value.formType==="DATE"){
			    	domval="<div class='layui-form-item'><label class='layui-form-label'>"+name+"</label><div class='layui-input-block'>  <input type='text' name='"+code+"' id='"+code+"' autocomplete='off' class='layui-input'></div></div>";
			    }
			    $("#showForm").append(domval);	
			    laydate.render({elem: "#"+code,trigger: 'click',theme: 'molv'});
			});
			
		}
	})
