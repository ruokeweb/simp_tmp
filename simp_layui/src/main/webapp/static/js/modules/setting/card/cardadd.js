/**
 * @autor zdl
 * @content 校友卡增加js
 * @returns
 * @Time 2019-02-15
 */
layui.config({
	base: '../../../../static/js/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
	/* 	formSelects: 'formSelects-v4', */
	"application": "application"
});

layui.use(['jquery', 'form', 'layer', 'laydate', /* 'formSelects', */ 'publicUtil', 'upload', 'application','colorpicker', 'element'],
	function() {
		var form = layui.form,
			$ = layui.jquery,
			/* 		formSelects = layui.formSelects, */
			publicUtil = layui.publicUtil,
			application = layui.application,
			layer = layui.layer,
			element = layui.element,
			laydate = layui.laydate,
			colorpicker =  layui.colorpicker,
			username = parent.parent.username,
			upload = layui.upload;


		var backFontColor = null;
		var frontFontColor = null;
		//表单赋值
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
			if(value < $(".startLevel").val()){
				message = "结束等级必须大于开始等级"
			}
			return message;
		}
    });

		//页面打开时，或者基本信息时，点击填充数据
		function initSchoolmateData() {
			if (parent.editFormData) {
				$("#cardid").val(publicUtil.htmlDecode(parent.editFormData.id));
				$(".name").val(publicUtil.htmlDecode(parent.editFormData.name));
				$(".startLevel").val(parent.editFormData.startLevel);
				$(".endLevel").val(parent.editFormData.endLevel);
				$("input:radio[value='2']").attr('checked','true');
				/*$("input[name='backFontStyle'][value = '"+ parent.editFormData.backFontStyle+"']").attr('checked','true');
				$("input[name='frontFontStyle'][value = '"+ parent.editFormData.frontFontStyle+"']").attr('checked','true');
				$("#frontFont").val(parent.editFormData.frontFont);
				$("#backFont").val(parent.editFormData.backFont);*/
				$(".cardStyleLine").val(parent.editFormData.cardStyleLine);
				$(".cardStyleRadius").val(parent.editFormData.cardStyleRadius);
				$(".cardStyleOpacity").val(parent.editFormData.cardStyleOpacity);
				$('#front_photo').attr('src', application.SERVE_URL + "/" + parent.editFormData.frontBackground);
				$('#behind_photo').attr('src', application.SERVE_URL + "/" + parent.editFormData.backBackground);
                if(parent.editFormData.frontBackground != null || parent.editFormData.frontBackground != ''){
                    document.getElementById("front_photo").src= application.SERVE_URL + application.FILEPATH + parent.editFormData.frontBackground;
                    $('#front_photoPath').html(parent.editFormData.frontBackground);
                }
                if(parent.editFormData.backBackground != null || parent.editFormData.backBackground != ''){
                    document.getElementById("behind_photo").src= application.SERVE_URL + application.FILEPATH + parent.editFormData.backBackground;
                    $('#behind_photoPath').html(parent.editFormData.backBackground);
                }
                initAssoSelect("asId",parent.editFormData.asId);

			}else{
                initAssoSelect("asId");
            }
			//校友卡类型
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'CARD_TYPE'
			}, "type", parent.editFormData ? parent.editFormData.type : null);
			
		/*	var bFColor = parent.editFormData.backFontColor == '' || parent.editFormData.backFontColor == null ||
				parent.editFormData.backFontColor ==undefined ? "#0b0b0b" : parent.editFormData.backFontColor ;
				
			var fFColor = parent.editFormData.frontFontColor == '' || parent.editFormData.frontFontColor == null || 
				parent.editFormData.frontFontColor ==undefined ? "#0b0b0b" : parent.editFormData.frontFontColor;*/
			
			/*colorpicker.render({
				elem: '#backFontColor',
				color: bFColor,
				done: function(color){
					backFontColor = color;
				}
			});
			
			colorpicker.render({
				elem: '#frontFontColor',
				color: fFColor,
				done: function(color){					
					frontFontColor = color;
				}
			});	*/
			form.render();
		}
		initSchoolmateData();
		var uploadInst = upload.render({
			elem: '.selectphoto',
			url: application.SERVE_URL + '/file/upload/',
            accept: 'images',
            exts : 'jpg|png|gif|bmp|jpeg',
            size : 500,
			choose: function(obj) {
				var photoType = this.item.data("phototype");
				//预读本地文件示例，不支持ie8
				obj.preview(function(index, file, result) {
					$('#' + photoType + '_photo').attr('src', result); //图片链接（base64）
				});
			},
			bindAction: '#addUser',
			done: function(res, index, upload) {
				var photoType = this.item.data("phototype");
				$('#' + photoType + '_photoPath').html(res.data);
				//如果上传失败
				if (res.code > 0) {
					return layer.msg('上传成功');
				}
				//上传成功
			},
			error: function() {
				var photoType = this.item.data("phototype");
				//演示失败状态，并实现重传
				var photoPath = $('#' + photoType + '_photoPath');
				photoPath.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
				photoPath.find('.demo-reload').on('click', function() {
					uploadInst.upload();
				});
			}
		});

		//submit(addUser)  绑定提交按钮（基本信息）
		form.on("submit(addCard)", function(data) {
			//弹出loading
			var index = top.layer.msg('数据提交中，请稍候', {
				icon: 16,
				time: false,
				shade: 0.8
			});
			$.ajax({
				url: application.SERVE_URL + '/settings/settingCard/save', //ajax请求地址
				type: "POST",
				data: {
					id: $(".id").val(),
					name: $(".name").val(),
					type: $("#type").val(),
					startLevel: $(".startLevel").val(),
					endLevel: $(".endLevel").val(),
                    asId: $("#asId").val(),
					/*frontFont: $("#frontFont").val(),
					frontFontColor: frontFontColor,
					frontFontStyle: $("input[name='frontFontStyle']:checked").val(),
					backFont: $("#backFont").val(),
					backFontColor: backFontColor,
					backFontStyle: $("input[name='backFontStyle']:checked").val(),*/
                    cardStyleLine: $(".cardStyleLine").val(),
                    cardStyleOpacity:$(".cardStyleRadius").val(),
                    cardStyleRadius:$(".cardStyleOpacity").val(),
					frontBackground: $("#front_photoPath").html(),
					backBackground: $("#behind_photoPath").html()
				},
				success: function(data) {
					if (data.code == application.REQUEST_SUCCESS) {
						top.layer.close(index);
						top.layer.msg(data.msg);
						layer.closeAll("iframe");
						//刷新父页面
						parent.location.reload();
					} else {
						top.layer.msg(data.msg + "(" + data.code + ")");
					}
				}
			});
			return false;
		})

		function closeSelf() {
			var index = parent.layer.getFrameIndex(window.namselectphotoe); //先得到当前iframe层的索引
			parent.layer.close(index);
		}
        
        function initAssoSelect(selectid,selectVal) {
            $.ajax({
                url: application.SERVE_URL + '/as/asAssociation/loadAllListBy', //ajax请求地址
                success: function(res) {
                    var assocList = res.data;
                    $("#" + selectid).empty();
                    for (var i = 0; i < assocList.length; i++) {
                        $("#" + selectid).append('<option  value="' + assocList[i].id + '">' + assocList[i].name +
                            '</option>'); //往下拉菜单里添加元素
                    }
                    if(selectVal){
                        $("#" + selectid).val(selectVal);
                    }
                    form.render(); //菜单渲染 把内容加载进去
                }
            });
        }
        
		$("#close").click(function () {
            layer.closeAll("iframe");
            //刷新父页面
            parent.location.reload();
        })

    })
