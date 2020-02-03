/**
 * @autor syp
 * @content 捐赠记录页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base: '../../../../static/js/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
	/* 	formSelects: 'formSelects-v4', */
	"application": "application"
});

layui.use(['jquery', 'form', 'layer', 'laydate', /* 'formSelects', */ 'publicUtil', 'application', 'element'],
	function() {
		var form = layui.form,
			$ = layui.jquery,
			publicUtil = layui.publicUtil,
			application = layui.application,
			layer = layui.layer,
			element = layui.element,
			laydate = layui.laydate;

   		form.verify({	
		  sortNum: function(value){
    		var message="";
    		//判断是否是编辑
				if (/^(\+)?\d+($|\\d+$)/.test(value)){
					return;
				}else{
					message = "只能输入整数"
					return message;
				}
			}
   		});

		//页面打开时，或者基本信息时，点击填充数据
		function initAssociationData() {
			if (parent.editFormData) {
				$("#id").val(publicUtil.htmlDecode(parent.editFormData.id));
				$("#activityId").val(publicUtil.htmlDecode(parent.editFormData.activityId));
				$("#name").val(publicUtil.htmlDecode(parent.editFormData.name));
				$("#code").val(publicUtil.htmlDecode(parent.editFormData.code));
				$("#formType").val(publicUtil.htmlDecode(parent.editFormData.formType));
				$("#defaultValue").val(publicUtil.htmlDecode(parent.editFormData.defaultVal));
				$("#sort").val(publicUtil.htmlDecode(parent.editFormData.sort));
				}
			//捐赠类型
			publicUtil.selectBaseAndSetVal(null, {
				'typeCode': 'ACT_FORM_TYPE'
			}, "formType", parent.editFormData ? parent.editFormData.formType : null);
		}
		initAssociationData();

		//submit(addUser)  绑定提交按钮（基本信息）
		form.on("submit(add)", function(data) {
			//弹出loading
			var index = top.layer.msg('数据提交中，请稍候', {
				icon: 16,
				time: false,
				shade: 0.8
			});
			$.ajax({
				url: application.SERVE_URL + '/act/actSetting/save', //ajax请求地址
				data: {
					id: $("#id").val(),
					activityId: parent.actObj.id,
					name: $("#name").val(),
					code: $("#code").val(),
					phone: $("#phone").val(),
					formType: $("#formType").val(),
					defaultVal: $("#defaultValue").val(),
					sort: $("#sort").val()
				},
				success: function(data) {
					if (data.code == application.REQUEST_SUCCESS) {
						top.layer.close(index);
						top.layer.msg(data.msg);
						closeSelf();
						//刷新父页面
						parent.location.reload();
					} else {
						top.layer.msg(data.msg + "(" + data.code + ")");
					}
				},
				error: function(data) {
					var result = data.responseJSON;
					top.layer.msg(result.msg + "(" + result.code + ")");
				}
			});
			return false;
		})

		function closeSelf() {
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index);
		}
		$("#close").click(closeSelf)
	})
