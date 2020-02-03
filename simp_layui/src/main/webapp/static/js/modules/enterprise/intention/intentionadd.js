/**
 * @autor lzq
 * @content 校友企业任职信息内容面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base: '../../../../static/js/' // 此处路径请自行处理, 可以使用绝对路径
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
});

var topIndex;
var selectSchoolmate;
layui.use(['jquery', 'form', 'layer', 'laydate', 'publicUtil', 'upload', 'application'], function() {
	var form = layui.form,
		application = layui.application,
		publicUtil = layui.publicUtil,
		layer = layui.layer,
		laydate = layui.laydate,
		username = parent.parent.username,
		$ = layui.jquery;

	// 编辑初始化
	initAssociationData();
	// 页面打开时，或者基本信息时，点击填充数据
	function initAssociationData() {
		var data = parent.companySel;
		if(data){
			$('#enter_id').val(publicUtil.htmlDecode(data.id));
			$('#enter_name').val(publicUtil.htmlDecode(data.name));
		}
		if (parent.editFormData) {
			$("#schoolmateid").val(publicUtil.htmlDecode(parent.editFormData.id));
			$("#user_id").val(publicUtil.htmlDecode(parent.editFormData.userId));
	
			$("#position").val(publicUtil.htmlDecode(parent.editFormData.position));
			$("#department").val(publicUtil.htmlDecode(parent.editFormData.department));
			$("#remark").val(publicUtil.htmlDecode(parent.editFormData.remark));
		}
		// 更新校友企业的名字
		// 			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
		// 				'typeCode': 'ASSOCIATION_TYPE'
		// 			}, "industry", parent.editFormData ? parent.editFormData.industry : null);
		// 更新校友名字  				url : application.SERVE_URL+'/sm/smSchoolmate/list',

// 		publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sm/smSchoolmate/get", {
// 			"id":publicUtil.htmlDecode(parent.editFormData.userId)
// 		}, "name", parent.editFormData ? parent.editFormData.name : null);
		
			$.ajax({
			url: application.SERVE_URL + "/sm/entIntention/get", //ajax请求地址
			data: {
				id:publicUtil.htmlDecode(parent.editFormData.userId)
			},
			success: function(data) {
				if (data.code == application.REQUEST_SUCCESS) {
					$("#user_name").val(data.data.name);
				}
			}
		});
		
		
	}

     //选择校友
	$(document).on('click', '.user_name', function(edit) {
// 		var restUrl = application.SERVE_URL + '/ent/entSchoolmate/get';
// 		var id = edit ? (edit.id ? edit.id : null) : null;
// 		publicUtil.gotoEditPage(restUrl, id, "选择校友", "schoolmateselect.html");
// 		alert("打开选择校友界面");
		topIndex = layui.layer.open({
								type: 2,
								title: '选择校友',
								shadeClose: true,
								shade: 0.8,
								area: ['85%', '65%'],
								// content: '../views/module/system/role/menuselect.html',
								content: 'schoolmateselect.html',
								success: function (layero, index) {
									setTimeout(function () {
										layui.layer.tips('点击此处返回角色列表', '.layui-layer-setwin .layui-layer-close', {
											tips: 3
										});
									}, 500)
								},
							})
		
	})

		//删除
	$(document).on('click', '.PER_DEL', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('schoolmateList').data);
		if (flag) {
			layer.confirm('确定删除此校友信息吗？', {
				icon: 3,
				title: '提示信息'
			}, function(index) {
				$.ajax({
					url: application.SERVE_URL + "/ent/entIntention/delete", //ajax请求地址
					data: {
						id: table.checkStatus('schoolmateList').data[0].id
					},
					success: function(data) {
						if (data.code == application.REQUEST_SUCCESS) {
							donationTableIns.reload();
							layer.close(index);
						}
					}
				});
			});
		} else {
			return false;
		}
	})

	// submit(addUser) 绑定提交按钮（基本信息）
	form.on("submit(addIntention)", function(data) {

		// 弹出loading
		var index = top.layer.msg('数据提交中，请稍候', {
			icon: 16,
			time: false,
			shade: 0.8
		});

		$.ajax({
			url: application.SERVE_URL + '/ent/entIntention/save', // ajax请求地
			data: {
				id: $("#intentionid").val() == null || $("#intentionid").val() == "" ? null : $("#intentionid").val(),
				userId: $("#user_id").val(),
				enterId: $('#enter_id').val(),
				position: $("#position").val(),
				department: $("#department").val(),
				remark: $("#remark").val(),
			},
			success: function(data) {
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

	layer.closeAll();
})
