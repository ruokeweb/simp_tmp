/**
 * @autor syp
 * @content 群组详情页面js
 * @returns
 * @Time 2018-09-12
 */
layui.config({
	base: "../../../../static/js/"
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
})
var mes_groupId, formDatas;
layui.use(['form','layer', 'laydate', 'table', 'laytpl', 'application', 'publicUtil'], function () {
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery,
		publicUtil = layui.publicUtil,
		application = layui.application,
		laydate = layui.laydate,
		laytpl = layui.laytpl,
		table = layui.table;
	var mesGroupUserListtableIns;

	application.init();


	//获取权限并加载按钮
	publicUtil.getPerms(application.PERMS_URL, application.HEADER, parent.cur_menu_id, 'get', 'but_per');
	rendmesGroupUserList("init");

	
	var tableIns = table.render({
		elem: '#mesGroupList',
		url: application.SERVE_URL+'/mes/mesGroup/list',
		even : true,
		cellMinWidth: 95,
		page: true,
		height: "full-160",
		limit: 10,
		id: "mesGroupList",
		cols: [
			[{
				type: 'checkbox'
			},{
				field: 'id',
				hide :true ,
				width: '0'
			},{
				field: 'name',
				title: '组名',
				event: 'click'
			}, {
				field: 'description',
				title: '群组描述',
				event: 'click'
			}]
		],
		done: function(res, curr, count){    //res 接口返回的信息,
			var flag = 0;
			$("[data-field = 'id']").children().each(function(){
					if(flag > 1){
						return false;
					}
					rendmesGroupUserList($(this).text());
					flag++;
			})
		}
	});

	//右键点击事件
	table.on('rowRight(mesGroupList)', function(obj){
		publicUtil.show_menu(obj);
	});
	
// 	//左键点击事件
// 	table.on('row(mesGroupList)', function(obj){
// 		publicUtil.hiddenMenu(obj);
// 	});
	
	//行事件
	table.on('row(mesGroupList)', function(obj){
		$(".layui-select-tr").removeClass("layui-select-tr");
		obj.tr.addClass("layui-select-tr");
		rendmesGroupUserList(obj.data.id);
		projectObj = obj.data.id;
		publicUtil.hiddenMenu(obj);
	});
	
	function rendmesGroupUserList(id)
	{
		if(id!=null&&""!=id&&id!=undefined&&id!="null")
		mesGroupUserListtableIns = table.render({
			elem: '#mesGroupUserList',
			url: application.SERVE_URL + '/mes/mesGroupCondition/list',
			even : true,
			cellMinWidth: 95,
			where: {groupId : id,},
			page: true,
			height: "full-160",
			limit: 10,
			id: "mesGroupUserList",
			cols: [
				[{
					field: 'field',
					title: '编码'
				}, {
					field: 'name',
					title: '值'
				}]
			]
		});
		return ;
	}

	//编辑操作
	$(document).on('click', '.PER_EDIT', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('mesGroupList').data);
		if (flag) {
			mes_groupId = table.checkStatus('mesGroupList').data[0].id
			$.ajax({
				url: application.SERVE_URL + "/mes/mesGroupCondition/loadAllListBy", //ajax请求地址
				data: {
					groupId: table.checkStatus('mesGroupList').data[0].id,
				},
				success: function(res) {
					 if(res.code==application.REQUEST_SUCCESS){
							formDatas=res.data;
							var index = layui.layer.open({
								title: "群组条件管理",
								type: 2,
								content: "mesGroupUserAdd.html",
								success: function(layero, index) {
									setTimeout(function() {
										layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
											tips: 3
										});
									}, 500)
								}
							})
						layui.layer.full(index);
					 }else{
						 layui.layer.msg(res.msg);
						 return false;
					 }
				}
			});				
		} else {
			return "请选中一行";
		}

	})

	//删除
	$(document).on('click', '.PER_DEL', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('mesGroupUserList').data);
		if (flag) {
			layer.confirm('确定删除此用户吗？', {
				icon: 3,
				title: '提示信息'
			}, function(index) {
				$.ajax({
					url: application.SERVE_URL + "/mes/mesGroupUser/delete", //ajax请求地址
					data: {
						id: table.checkStatus('mesGroupUserList').data[0].id
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
	
	//搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$(".search_btn").on("click", function() {
		table.reload("mesGroupList", {
			page: {
				curr: 1 //重新从第 1 页开始
			},
			where: {
				name: $(".searchVal").val(),
			}
		})
	});

})
