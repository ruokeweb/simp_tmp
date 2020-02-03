/**
 * @autor syp
 * @content 校友会你页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base: '../../../../static/js/' // 此处路径请自行处理, 可以使用绝对路径
}).extend({
	/* formSelects: 'formSelects-v4', */
	"application": "application"
});


layui.use(['jquery', 'form', 'layer', 'laydate', 'publicUtil', 'upload', 'application'],function () {
		var form = layui.form,
			application = layui.application,
			publicUtil = layui.publicUtil,
			layer = layui.layer,
			laydate = layui.laydate,
			username = parent.parent.username,
			upload = layui.upload,
			$ = layui.jquery;
		
		
		
		// 编辑初始化
		initData();
		
		// 页面打开时，或者基本信息时，点击填充数据
		function initData() { 
			if (parent.editFormData) {
				$("#id").val(publicUtil.htmlDecode(parent.editFormData.id));
				$("#parentId").val(publicUtil.htmlDecode(parent.editFormData.parentId));
				$("#parentName").val(publicUtil.htmlDecode(parent.editFormData.parentSysIndustry.name));
				$("#name").val(publicUtil.htmlDecode(parent.editFormData.name));
				$('#code').val(publicUtil.htmlDecode(parent.editFormData.code));
				$('#sort').val(publicUtil.htmlDecode(parent.editFormData.sort));
				$("#remark").val(publicUtil.htmlDecode(parent.editFormData.remark));
			}
		}
		
		// submit(addUser) 绑定提交按钮（基本信息）
		form.on("submit(add)", function (data) {
			// 弹出loading
			var index = top.layer.msg('数据提交中，请稍候', {
				icon: 16,
				time: false,
				shade: 0.8
			});
			
			$.ajax({
				url: application.SERVE_URL + '/sys/industry/save', // ajax请求地
				data: {
					id: $("#id").val() ==null|| $("#id").val() =="" ? null :$("#id").val(),
					parentId : $("#parentId").val(),
					name: $("#name").val(),
					code:$('#code').val(),
					sort:$('#sort').val(),
					remark: $("#remark").val()
				},
				success: function (data) {
					if (data.code == application.REQUEST_SUCCESS) {
						top.layer.close(index);
						top.layer.msg(data.msg);
						layer.closeAll("iframe");
						// 刷新父页面
						parent.location.reload();
					} else {
						top.layer.msg(data.msg + "(" + data.code + ")");
					}
				}
			});
			return false;
		})

		function closeSelf() {
			var index = parent.layer.getFrameIndex(window.name); // 先得到当前iframe层的索引
			parent.layer.close(index);
		}
		$("#close").click(closeSelf)
		
		
		
		function selectSmAsso(){
			var index = layui.layer.open({
				type: 2,
				title: '选择',
				shadeClose: true,
				shade: 0.8,
				area: ['280px', '65%'],
				content: 'industryselect.html',
				success : function(layero, index){
					//
					setTimeout(function(){
							layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
									tips: 3
							});
					},500)											
				},
			})
		}
		
		
		$(".parentName").click(function(){
			selectSmAsso();
		})	
		
		layer.closeAll();
	})
