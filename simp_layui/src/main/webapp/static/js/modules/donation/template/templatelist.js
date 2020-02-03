/**
 * @autor syp
 * @content 捐赠模板列表页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base: "../../../../static/js/"
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
})
layui.use(['form', 'layer', 'laydate', 'table', 'laytpl', 'application', 'publicUtil'], function () {
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
		elem: '#templateList',
		url: application.SERVE_URL + '/don/donTemplate/list',
		even : true,
		cellMinWidth: 95,
		page: true,
		height: "full-160",
		limit: 10,
		headers: {
			'Authorization': application.HEADER
		},
		id: "templateList",
		cols: [
			[{
				type: 'checkbox'
			}, {
				field: 'name',
				title: '模板名称',
				event: 'setSign'
			}, {
			// 	field: 'description',
			// 	title: '模板描述',
			// 	event: 'click'
			// }, {
			// 	field: 'url',
			// 	title: '模板地址',
			// 	event: 'click'
			// }, {
				field: 'description',
				title: '描述',
				// event: 'click'
			}, {
				field: 'startAmount',
				title: '金额起始值',
				// event: 'click'
			}, {
				field: 'endAmount',
				title: '金额结束值',
				// event: 'click'
// 			}, {
// 				field: 'remark',
// 				title: '备注',
// 				event: 'click'
			}]
		],
		done: function(res, curr, count){    //res 接口返回的信息,
			// publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'PROJECT_TYPE'},'type');
		}
	});
	
	//右键点击事件
	table.on('rowRight(templateList)', function(obj){
		publicUtil.show_menu(obj);
	});
	
	//左键点击事件
	table.on('row(templateList)', function(obj){
		publicUtil.hiddenMenu(obj);
	});
	
	//新增操作
	$(document).on('click', '.PER_ADD', function () {
		addTemplate();
	});

	//编辑操作
	$(document).on('click', '.PER_EDIT', function () {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('templateList').data);
		if (flag) {
			addTemplate(table.checkStatus('templateList').data[0]);
		} else {
			return false;
		}
	})

	//删除
	$(document).on('click', '.PER_DEL', function () {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('templateList').data);
		if (flag) {
			layer.confirm('确定删除此项目吗？', {
				icon: 3,
				title: '提示信息'
			}, function (index) {
				$.ajax({
					url: application.SERVE_URL + "/don/donTemplate/delete", //ajax请求地址
					type: "POST",
					data: {
						id: table.checkStatus('templateList').data[0].id
					},
					headers: {
						'Authorization': application.HEADER
					},
					success: function (data) {
						if (data.code==application.REQUEST_SUCCESS) {
							tableIns.reload();
							layer.close(index);
						}
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
	$(".search_btn").on("click", function () {
		table.reload("templateList", {
			page: {
				curr: 1 //重新从第 1 页开始
			},
			where: {
				// typeCode: $(".searchTypeCode").val(),
				// label: $(".searchLabel").val(),
				name: $(".searchVal").val()
			}
		})
	});

	//添加编码
	function addTemplate(edit) {
		var restUrl = application.SERVE_URL + '/don/donTemplate/get';
		var id = edit ? (edit.id ? edit.id : null) : null;
		publicUtil.gotoEditPage(restUrl, id, "捐赠模板信息管理", "templateadd.html")
	}
})
