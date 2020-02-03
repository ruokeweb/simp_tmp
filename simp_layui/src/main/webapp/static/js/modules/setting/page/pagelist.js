/**
 * @autor zdl
 * @content 页面配置列表js
 * @returns
 * @Time 2019-05-28
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
		elem: '#pageList',
		url: application.SERVE_URL + '/settings/settingPage/list',
		even: true,
		cellMinWidth: 95,
		page: true,
		height: "full-160",
		limit: 10,
		headers: {
			'Authorization': application.HEADER
		},
		id: "pageList",
		cols: [
			[{
				type: 'checkbox'
			}, {
				field: 'name',
				title: '名称'
			}, {
				field: 'code',
				title: '编码'
			},{
				field: 'createDate',
				title: '创建时间'
			}]
		]
	});

	//右键点击事件
	table.on('rowRight(pageList)', function(obj) {
		publicUtil.show_menu(obj);
	});

	//左键点击事件
	table.on('row(pageList)', function(obj) {
		publicUtil.hiddenMenu(obj);
	});

	//新增操作
	$(document).on('click', '.PER_ADD', function() {
		addPage();
	});

	//编辑操作
	$(document).on('click', '.PER_EDIT', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('pageList').data);
		if (flag) {
			addPage(table.checkStatus('pageList').data[0]);
		} else {
			return false;
		}

	})

	//删除
	$(document).on('click', '.PER_DEL', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('pageList').data);
		if (flag) {
			layer.confirm('确定删除此页面配置吗？', {
				icon: 3,
				title: '提示信息'
			}, function(index) {
				$.ajax({
					url: application.SERVE_URL + "/settings/settingPage/delete", //ajax请求地址
					type: "POST",
					data: {
						id: table.checkStatus('pageList').data[0].id
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
		table.reload("pageList", {
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
		var restUrl = application.SERVE_URL + '/settings/settingPage/get';
		var id = edit ? (edit.id ? edit.id : null) : null;
		publicUtil.gotoEditPage(restUrl, id, "添加页面配置", "pageadd.html")
	}
})
