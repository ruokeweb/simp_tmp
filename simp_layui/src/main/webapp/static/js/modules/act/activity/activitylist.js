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
	
	//右键点击事件
	table.on('rowRight(list)', function(obj){
		publicUtil.show_menu(obj);
	});
	
	//左键点击事件
	table.on('row(list)', function(obj){
		publicUtil.hiddenMenu(obj);
	});

	var tableIns = table.render({
		elem: '#list',
		//生产坏境下请求后台
		url : application.SERVE_URL+'/act/actActivity/list',
		cellMinWidth : 95,
		page : true,
		even : true ,
		height : "full-220",
		id : "list",
		cols: [
			[{
				type: 'checkbox'
			}, {
				field: 'name',
				title: '名称'
			}, {
				field: 'area',
				title: '地点'
			}, {
				field: 'assoName',
				title: '校友会'
			}, {
				field: 'startDate',
				title: '开始时间'
			}, {
				field: 'endDate',
				title: '结束时间'
			}, {
				field: 'limitNo',
				title: '上限人数'
			}, {
				field: 'readyNo',
				title: '已报名'
			}, {
				field: 'status',
				title: '活动状态'
			}]
		],
		done: function(res, curr, count){    //res 接口返回的信息,
			publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'ACT_STATUS'},'status');
		}
	});	

	//查看操作
	_$(document).on('click', '.PER_SHOW', function() {
		show(table.checkStatus('list').data[0],"show");
	});
	
	//新增操作
	_$(document).on('click', '.PER_ADD', function() {
		add(table.checkStatus('list').data[0],"add");
	});

	
	//编辑操作
	_$(document).on('click', '.PER_EDIT', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('list').data);
		if(flag) {
			add(table.checkStatus('list').data[0],"edit");
		} else {
			return false;
		}

	})

	//删除
	_$(document).on('click', '.PER_DEL', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('list').data);
		if(flag) {
			layer.confirm('确定删除此活动吗？', {
				icon: 3,
				title: '提示信息'
			}, function(index) {
				_$.ajax({
					url: application.SERVE_URL + "/act/actActivity/delete", //ajax请求地址
					data: {
						id: table.checkStatus('list').data[0].id
					},
					success: function(data) {
						if(data.code==application.REQUEST_SUCCESS) {
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
		table.reload("list", {
			page: {
				curr: 1 //重新从第 1 页开始
			},
			where: {
				name: $(".searchVal").val()
			}
		})
	});

	//添加
	function add(edit,action) {
		var restUrl=application.SERVE_URL + '/act/actActivity/get';
		var id="edit"==action?(edit.id?edit.id:null):null;
		publicUtil.gotoEditPage(restUrl,id,"添加活动","actActivityAdd.html")
	}

	//查看
	function show(edit,action){
		var restUrl=application.SERVE_URL + '/act/actActivity/get';
		var id="show"==action?(edit.id?edit.id:null):null;
		publicUtil.gotoEditPage(restUrl,id,"显示表单","actActivityShow.html")
	}
})