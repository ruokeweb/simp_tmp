/**
 * @autor syp
 * @content 字典列表页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base: "../../../../static/js/"
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
})
layui.use(['form', 'layer', 'laydate', 'treeGrid', 'laytpl', 'application', 'publicUtil'], function() {
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery,
		publicUtil = layui.publicUtil,
		application = layui.application,
		laydate = layui.laydate,
		laytpl = layui.laytpl,
		treeGrid = layui.treeGrid;

	application.init();

	var treeTable = treeGrid.render({
		elem: '#markList',
		url: application.SERVE_URL + '/sm/smMark/list',
		even: true,
		page: false,
		cellMinWidth : 95,
		height: "full-165",
		id: "markList",
		method: "post",
		treeId:'id',//树形id字段名称 ,
		treeUpId:'parentId',//树形父id字段名称
		treeShowName:'name',//以树形式显示的字段
		cols: [
			[{
					type: 'checkbox'
				},
				{
					field: 'name',
					title: '标签名称'
				}, {
					field: 'code',
					title: '标签编码'
				}, {
					field: 'remark',
					title: '备注'
				}
// 				,
// 				 {
// 					field: 'useable',
// 					title: '状态'
// 				}
			]
		],
		done: function(res, curr, count) { //res 接口返回的信息,
			publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'MARK_USEABLE'
			}, 'useable');
		}
	});
	
	
	//右键点击事件
	treeGrid.on('rowRight(markList)', function(obj) {
		publicUtil.show_menu(obj);
	});

	//左键点击事件
	treeGrid.on('row(markList)', function(obj) {
		publicUtil.hiddenMenu(obj);
	});


	//新增操作
	$(document).on('click', '.PER_ADD', function() {
		var flag = publicUtil.jurgeSelectRows(treeGrid.checkStatus('markList').data);
		if (flag) {
			addMark(treeGrid.checkStatus('markList').data[0], 'add');
		} else {
			return false;
		}
	});

	//设置
	$(document).on('click', '.PER_PERM', function() {
		var flag = publicUtil.jurgeSelectRows(treeGrid.checkStatus('markList').data);
		if (flag) {
			setUnable(treeGrid.checkStatus('markList').data[0]);
		} else {
			return false;
		}
	});


	//编辑操作
	$(document).on('click', '.PER_EDIT', function() {
		var flag = publicUtil.jurgeSelectRows(treeGrid.checkStatus('markList').data);
		if (flag) {
			addMark(treeGrid.checkStatus('markList').data[0], "edit");
		} else {
			return false;
		}

	})

	//删除
	$(document).on('click', '.PER_DEL', function() {
		var flag = publicUtil.jurgeSelectRows(treeGrid.checkStatus('markList').data);
		if (flag) {
			if(treeGrid.checkStatus('markList').data[0].id == "root"){
				layer.msg("校友标签父节点不允许删除,请进行其他操作", {icon: 5, shift: 6});
				return false;
			}
			layer.confirm('确定删除此标签吗？<br><span style="color:red;font-size:8px">*删除此标签将同时删除其子标签</span>', {
				icon: 3,
				title: '提示信息'
			}, function(index) {
				$.ajax({
					url: application.SERVE_URL + "/sm/smMark/delete", //ajax请求地址
					data: {
						id: treeGrid.checkStatus('markList').data[0].id,
						name: treeGrid.checkStatus('markList').data[0].name
					},
					success: function(data) {
						if (data.code == application.REQUEST_SUCCESS) {
							treeTable.reload();
							layer.msg(data.msg)
							layer.close(index);
						}else if(data.code == application.DATA_USED){
							layer.msg(data.msg, {icon: 5, shift: 6});
							layer.close(index);
						}else{
							layer.msg(data.msg,{time:1000});
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
	$(".search_btn").on("click", function() {
		treeGrid.reload("markList", {
			page: {
				curr: 1 //重新从第 1 页开始
			},
			where: {
				name: $(".searchVal").val()
			}
		})
	});

	//设置禁用
	function setUnable(edit){
		var set = '';
		if(treeGrid.checkStatus('markList').data[0].useable  == 'ABLE'){
			set = "禁用";
		}else{
			set = "启用";
		}
		
		layer.confirm('即将'+set+'该标签及其子标签状态,确认修改？', {
			icon: 3,
			title: '提示信息'
		}, function(index) {
			$.ajax({
				url: application.SERVE_URL + "/sm/smMark/updateUnable", //ajax请求地址
				data: {
					id: treeGrid.checkStatus('markList').data[0].id,
					useable: treeGrid.checkStatus('markList').data[0].useable
				},
				success: function(data) {
					if (data.code == application.REQUEST_SUCCESS) {
						layer.msg(data.msg);
						treeTable.reload();
						layer.close(index);
					}
				}
			});
		});
	}


	//添加标签
	function addMark(edit, action) {
		if(treeGrid.checkStatus('markList').data[0].id == "root" && action == "edit"){
			layer.msg("校友标签父节点不允许编辑,请进行其他操作", {icon: 5, shift: 6});
			return false;
		};
		var restUrl = application.SERVE_URL + '/sm/smMark/get';
		var id = "edit" == action ? (edit.id ? edit.id : null) : null;
		publicUtil.gotoEditPage(restUrl, id, "标签管理", "markadd.html")
	}
})
