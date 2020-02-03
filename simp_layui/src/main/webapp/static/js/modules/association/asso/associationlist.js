/**
 * @autor lzq
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
	// 选中标记
	var flag ;
	// 节点标记
	var treeCheckNode ;
	// 节点标记
	var treeObj;
	
	var tableIns;

	// ztree设置
	var setting = {		
			view: {
				selectedMulti: false
			},
			data: {
				simpleData: {
					enable: true,
					idKey: "id",
					pIdKey: "parentId"				
				}
			},
			callback: {
				onClick: onClick
			}
	};
	// 初始化树
	initTree();
	// 初始化树高度
	_$(function(){
		$(".ztree").height($(window).height()-105);
		$(window).resize(function (){
			$(".ztree").height($(window).height()-105);
			
		});
	});
	function initTree() {
		_$.ajax({
			url: application.SERVE_URL+'/as/asAssociation/tree',
			method:'post',
			success: function (data) {
				treeObj = $.fn.zTree.init($("#assoTree"), setting, covert(data.data)); // 加载数据
				// 初始化
				// 获取根节点个数,getNodes获取的是根节点的集合
				var nodeList = treeObj.getNodes();
	　　　　　　	// 展开第一个根节点
				treeObj.expandNode(nodeList[0], true);
				treeObj.setting.callback.onClick(null, treeObj.setting.treeId, nodeList[0]);// 调用事件
			}
		});		
	}	

	function covert(data) {
		for (var i = 0; i < data.length; i++) {
			data[i].name = publicUtil.htmlDecode(data[i].name)
		}
		return data;
	}

	// 右键点击事件
	table.on('rowRight(associationList)', function(obj){
		publicUtil.show_menu(obj);
	});
	
	// 左键点击事件
	table.on('row(associationList)', function(obj){
		publicUtil.hiddenMenu(obj);
	});

	// 加载右侧数据
	function onClick(event, treeId, treeNode, clickFlag) {
		 console.log(treeNode.id);
		// 生产坏境下请求后台
		treeCheckNode = treeNode.id;
			tableIns = table.render({
				elem: '#associationList',
				// 生产坏境下请求后台
				url : application.SERVE_URL+'/as/asAssociation/list',
				where :{parentId : treeNode.id},
				cellMinWidth : 95,
				page : true,
				even : true ,
				height : "full-230",
				id : "associationList",
				cols: [
					[{
						type: 'checkbox'
					}, {
						field: 'name',
						title: '校友会名称'
					}, {
						field: 'periods',
						title: '当前届次'
					}, {
						field: 'type',
						title: '校友会类型'
					}, {
						field: 'openDate',
						title: '成立时间'
					}, {
						field: 'sum',
						title: '成员数'
					}]
				],
				done: function(res, curr, count){    // res 接口返回的信息,
					publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'ASSOCIATION_TYPE'},'type');
				}
			});	
	}		


	
	// 新增操作
	_$(document).on('click', '.PER_ADD', function() {
		addAssociation(table.checkStatus('markList').data[0],"add");
	});

	// 编辑操作
	_$(document).on('click', '.PER_EDIT', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('associationList').data);
		if(flag) {
			addAssociation(table.checkStatus('associationList').data[0],"edit");
		} else {
			return false;
		}

	})

	// 删除
	_$(document).on('click', '.PER_DEL', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('associationList').data);
		if(flag) {
			layer.confirm('确定删除此校友会吗？', {
				icon: 3,
				title: '提示信息'
			}, function(index) {
				_$.ajax({
					url: application.SERVE_URL + "/as/asAssociation/delete", // ajax请求地址
					data: {
						id: table.checkStatus('associationList').data[0].id
					},
					success: function(data) {
						if(data.code==application.REQUEST_SUCCESS) {
							tableIns.reload();
							layer.close(index);
							initTree();
							top.layer.msg(data.msg,{time: 1000});
						}else{
							top.layer.msg(data.msg,{time: 1000});
						}
					}
				});
			});
		} else {
			return false;
		}
	})
	
	// 获取权限并加载按钮
	publicUtil.getPerms(application.PERMS_URL, application.HEADER, parent.cur_menu_id, 'get', 'but_per');
	// 搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	_$(".search_btn").on("click", function() {
		table.reload("associationList", {
			page: {
				curr: 1 // 重新从第 1 页开始
			},
			where: {
				name: $(".searchVal").val()
			}
		})
	});

	// 添加
	function addAssociation(edit,action) {
		var restUrl=application.SERVE_URL + '/as/asAssociation/get';
		var id="edit"==action?(edit.id?edit.id:null):null;
		publicUtil.gotoEditPage(restUrl,id,"校友会信息管理","associationadd.html")
	}
	
})