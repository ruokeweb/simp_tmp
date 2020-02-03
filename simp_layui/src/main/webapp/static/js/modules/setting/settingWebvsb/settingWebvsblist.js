/**
 * @autor syp
 * @content 校友网设置
 * @returns
 * @Time 2019-09-09
 */
layui.config({
	base: "../../../../static/js/"
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
})
layui.use(['form', 'layer', 'laydate', 'table', 'laytpl', 'application', 'publicUtil'], function() {
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery,
		publicUtil = layui.publicUtil,
		application = layui.application,
		laydate = layui.laydate,
		laytpl = layui.laytpl,
		table = layui.table;

	application.init();

	var tableIns = table.render({
		elem: '#settingWebvsbList',
		url: application.SERVE_URL + '/settings/settingwebvsb/list',
		even: true,
		cellMinWidth: 95,
		page: true,
		height: "full-160",
		limit: 10,
		headers: {
			'Authorization': application.HEADER
		},
		id: "settingWebvsbList",
		cols: [
			[{
				type: 'checkbox'
			}, {
				field: 'name',
				title: '名称'
			}, {
				field: 'treeId',
				title: '栏目ID'
			},{
				field: 'viewId',
				title: '组件ID'
			}]
		]
	});

	//右键点击事件
	table.on('rowRight(settingWebvsbList)', function(obj) {
		publicUtil.show_menu(obj);
	});

	//左键点击事件
	table.on('row(settingWebvsbList)', function(obj) {
		publicUtil.hiddenMenu(obj);
	});

	//新增操作
	$(document).on('click', '.PER_ADD', function() {
		addPage();
	});

	//编辑操作
	$(document).on('click', '.PER_EDIT', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('settingWebvsbList').data);
		if (flag) {
			addPage(table.checkStatus('settingWebvsbList').data[0]);
		} else {
			return false;
		}

	})

	//删除
	$(document).on('click', '.PER_DEL', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('settingWebvsbList').data);
		if (flag) {
			layer.confirm('确定删除此校友网配置吗？', {
				icon: 3,
				title: '提示信息'
			}, function(index) {
				$.ajax({
					url: application.SERVE_URL + "/settings/settingwebvsb/delete", //ajax请求地址
					type: "POST",
					data: {
						id: table.checkStatus('settingWebvsbList').data[0].id
					},
					headers: {
						'Authorization': application.HEADER
					},
					success: function(data) {
						if (data.code == application.REQUEST_SUCCESS) {
							tableIns.reload();
							layer.close(index);

                        }
                        top.layer.msg(data.msg);
					}
				});
			});
		} else {
			return false;
		}
	})

	//获取权限并加载按钮
	publicUtil.getPerms(application.PERMS_URL, application.HEADER, parent.cur_menu_id, 'get', 'but_per');
	//搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$(".search_btn").on("click", function() {
		table.reload("settingWebvsbList", {
			page: {
				curr: 1 //重新从第 1 页开始
			},
			where: {
                name: $(".searchVal").val(),

			}
		})
	});

	//添加编码
	function addPage(edit) {
		var restUrl = application.SERVE_URL + '/settings/settingwebvsb/get';
		var id = edit ? (edit.id ? edit.id : null) : null;
		publicUtil.gotoEditPage(restUrl, id, "添加校友网配置", "settingWebvsbadd.html")
	}
})
