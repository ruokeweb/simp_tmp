/**
 * @autor lzq
 * @content 校友企业列表页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base: "../../../../static/js/"
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
})

var  companySel;
layui.use(['form', 'layer', 'laydate', 'table', 'laytpl', 'application', 'publicUtil'], function() {
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		_$ = layui.jquery,
		publicUtil = layui.publicUtil,
		application = layui.application,
		laydate = layui.laydate,
		laytpl = layui.laytpl,
		table = layui.table;

	application.init();
	//选中标记
	var flag;

	//右键点击事件enterprise
	table.on('rowRight(enterpriseList)', function(obj) {
		publicUtil.show_menu(obj);
	});
	
	//左键点击事件
	table.on('row(enterpriseList)', function(obj) {
		publicUtil.hiddenMenu(obj);
	});
	
	//行事件
// 	table.on('row(companyList)', function(obj) {
// 		$(".layui-select-tr").removeClass("layui-select-tr");
// 		obj.tr.addClass("layui-select-tr");
// 		companySel = obj.data
// 		rendschoolmateList(obj.data.id);
// 		companyTableIns = obj.data.id;
// 	});

	//生产坏境下请求后台
	var tableIns = table.render({
		elem: '#enterpriseList',
		//生产坏境下请求后台
		url: application.SERVE_URL + '/ent/enterprise/list',
		cellMinWidth: 95,
		page: true,
		even: true,
		height: "full-220",
		id: "enterpriseList",
		cols: [
			[{
				type: 'checkbox'
			}, {
				field: 'name',
				title: '校友企业名称'
			}, {
				field: 'remark',
				title: '所属行业'
			// }, {
			// 	field: 'type',
			// 	title: '单位性质'
			// }, {
			// 	field: 'phone',
			// 	title: '联系电话'
			}, {
				field: 'email',
				title: '邮箱'
			// }, {
			// 	field: 'webSite',
			// 	title: '网址'
			}, {
				field: 'wechat',
				title: '公众号'
			// }, {
			// 	field: 'address',
			// 	title: '地址'
			}]
		],
		done: function(res, curr, count) { //res 接口返回的信息,
			publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'COMPANY_TPYE'
			}, 'type');
		}
	});

	//新增操作
	_$(document).on('click', '.PER_ADD', function() {
		addAssociation(table.checkStatus('enterpriseList').data[0], "add");
	});


	//编辑操作
	_$(document).on('click', '.PER_EDIT', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('enterpriseList').data);
		if (flag) {
			addAssociation(table.checkStatus('enterpriseList').data[0], "edit");
		} else {
			return false;
		}
	})
	
	//校友意向
	_$(document).on('click', '.PER_INTENTION', function() {
		var data = table.checkStatus('enterpriseList').data;
		var flag = publicUtil.jurgeSelectRows(data);
		if (flag) {
			var edit = data[0];
			companySel = edit;
			var restUrl = application.SERVE_URL + '/ent/entIntention/list';
			var id = "edit" == "intention" ? (edit.id ? edit.id : null) : null;
			publicUtil.gotoEditPage(restUrl, id, "校友意向列表", "../intention/intentionlist.html")
		} else {
			return false;
		}
	})
	

	//删除
	_$(document).on('click', '.PER_DEL', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('enterpriseList').data);
		if (flag) {
			layer.confirm('确定删除此校友企业吗？', {
				icon: 3,
				title: '提示信息'
			}, function(index) {
				_$.ajax({
					url: application.SERVE_URL + "/ent/enterprise/delete", //ajax请求地址
					data: {
						id: table.checkStatus('enterpriseList').data[0].id
					},
					success: function(data) {
						if (data.code == application.REQUEST_SUCCESS) {
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
	_$(".search_btn").on("click", function() {
		table.reload("enterpriseList", {
			page: {
				curr: 1 //重新从第 1 页开始
			},
			where: {
				name: $(".searchVal").val()
			}
		})
	});

	//添加
	function addAssociation(edit, action) {
		var restUrl = application.SERVE_URL + '/ent/enterprise/get';
		var id = "edit" == action ? (edit.id ? edit.id : null) : null;
		publicUtil.gotoEditPage(restUrl, id, "校友企业信息管理", "enterpriseadd.html")
	}
})