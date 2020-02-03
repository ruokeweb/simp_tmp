/**
 * @autor syp
 * @content 学校列表页面js
 * @returns
 * @Time 2019-09-17
 */
layui.config({
	base: "../../../../static/js/"
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
})
//表单回填
var formdatas;
layui.use(['application', 'form', 'layer', 'laydate', 'table', 'publicUtil'], function () {
	var form = layui.form,
		layer = layui.layer,
		$ = layui.jquery,
		publicUtil = layui.publicUtil,
		application = layui.application,
		laydate = layui.laydate, 
		laytpl = layui.laytpl,
		table = layui.table;
	//选中标记
	var flag;

	application.init();
	//编码列表
	var tableIns = table.render({
		elem: '#schoolList',
		url: application.SERVE_URL + '/sys/school/list',
		cellMinWidth: 95,
		method: "POST",
		even : true ,
		page: true,
		height: "full-160",
		limit: 20,
		limits: [10, 15, 20, 25],
		id: "schoolList",
		cols: [
			[{
				type: 'checkbox'
			},{
				field: 'name',
				title: '学校名称'
			},{
				field: 'code',
				title: '编码'
			},{
				field: 'type',
				title: '类型'
			},{
				field: 'password',
				title: '访问码'
			},{
				field: 'remark',
				title: '备注信息'
			}]
		],
		done: function (res, curr, count) { //res 接口返回的信息,
			publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'GRADE'
			}, 'type');
		}
	});

	//获取权限并加载按钮
	publicUtil.getPerms(application.PERMS_URL, application.HEADER, parent.cur_menu_id, 'get', 'but_per');

	//右键点击事件
	table.on('rowRight(schoolList)', function (obj) {
		publicUtil.show_menu(obj);
	});

	//左键点击事件
	table.on('row(schoolList)', function (obj) {
		publicUtil.hiddenMenu(obj);
	});

	//搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$(".search_btn").on("click", function () {
		//if ($(".searchVal").val() != '') {
			table.reload("schoolList", {
				page: {
					curr: 1 //重新从第 1 页开始
				},
				where: {
					name: $(".searchVal").val() //搜索的关键字
				}
			})
		//} else {
		//	layer.msg("请输入搜索的内容",{time: 1000});
		//}
	});

	//新增操作
	$(document).on('click', '.PER_ADD', function () {
		_addRole()
	});

	//编辑操作
	$(document).on('click', '.PER_EDIT', function () {
		flag = publicUtil.jurgeSelectRows(table.checkStatus('schoolList').data);
		if (flag) {
			_addRole(table.checkStatus('schoolList').data[0]);
		} else {
			return false;
		}

	})

	//删除
	$(document).on('click', '.PER_DEL', function () {
		flag = publicUtil.jurgeSelectRows(table.checkStatus('schoolList').data);
		if (flag) {
			layer.confirm('确定删除此此角色？', {
				icon: 3,
				title: '提示信息'
			}, function (index) {
				$.ajax({
					url: application.SERVE_URL + "/sys/school/delete", //ajax请求地址
					data: {
						id: table.checkStatus('schoolList').data[0].id
					},
					success: function (res) {
						if (res.code == application.REQUEST_SUCCESS) {
							table.reload('schoolList');
							top.layer.msg(res.msg,{time: 1000});
							layer.close(index);
							
						} else {
							layer.msg(res.msg,{time: 1000});
						}
					}
				})
			});
		} else {
			return false;
		}
	})


	//添加角色
	function _addRole(edit) {
		publicUtil.gotoEditPage(application.SERVE_URL + '/sys/school/get', edit == undefined ? null : edit.id, "角色管理",
			"schoolAdd.html");
	}

})
