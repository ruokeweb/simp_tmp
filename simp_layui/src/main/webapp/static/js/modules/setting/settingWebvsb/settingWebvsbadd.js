/**
 * @autor syp
 * @content 校友网设置
 * @returns
 * @Time 2019-09-09
 */
layui.config({
	base: '../../../../static/js/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
});

layui.use(['jquery', 'form', 'layer', 'laydate',  'publicUtil', 'application', 'element'],
	function() {
		var form = layui.form,
			$ = layui.jquery,
			publicUtil = layui.publicUtil,
			application = layui.application,
			layer = layui.layer

		//页面打开时，或者基本信息时，点击填充数据
		function initPageData() {
			if (parent.editFormData) {
				$("#id").val(publicUtil.htmlDecode(parent.editFormData.id));
                $("#name").val(publicUtil.htmlDecode(parent.editFormData.name));
				$("#treeId").val(publicUtil.htmlDecode(parent.editFormData.treeId));
				$("#viewId").val(publicUtil.htmlDecode(parent.editFormData.viewId));
                $("#remark").val(publicUtil.htmlDecode(parent.editFormData.remark));
			}
			form.render();
		}
		initPageData();


		//submit(addUser)  绑定提交按钮（基本信息）
		form.on("submit(add)", function(data) {
			//弹出loading
			var index = top.layer.msg('数据提交中，请稍候', {
				icon: 16,
				time: false,
				shade: 0.8
			});
			$.ajax({
				url: application.SERVE_URL + '/settings/settingwebvsb/save', //ajax请求地址
				type: "POST",
				data: {
					id: $("#id").val() ==null|| $("#id").val() =="" ? null : $("#id").val(),
                    name: $("#name").val(),
					treeId: $("#treeId").val(),
					viewId: $("#viewId").val(),
					remark: $("#remark").val()
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
		$("#close").click(function () {
            layer.closeAll("iframe");
            //刷新父页面
            parent.location.reload();
        })

    })
