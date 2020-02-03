/**
 * @autor zdl
 * @content 值年返校js
 * @returns
 * @Time 2019-05-27
 */
layui.config({
	base: "../../../../static/js/"
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
})
layui.use(['form','layer', 'laydate', 'table', 'laytpl', 'application', 'publicUtil'], function () {
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery,
		publicUtil = layui.publicUtil,
		application = layui.application,
		laydate = layui.laydate,
		laytpl = layui.laytpl,
		table = layui.table;
	var starInfotableIns;
	var status;
	application.init();

	//获取权限并加载按钮
	publicUtil.getPerms(application.PERMS_URL, application.HEADER, parent.cur_menu_id, 'get', 'but_per');
	//rendStarInfo("init");


	var tableIns = table.render({
		elem: '#selforgList',
		url: application.SERVE_URL + '/actContent/actSelforg/list',
		even : true,
		cellMinWidth: 95,
		page: true,
		height: "full-160",
		limit: 10,
		id: "selforgList",
		cols: [
			[{
				type: 'checkbox'
			},{
				field: 'status',
				title: '状态',
				even: 'click'
			},{
				field: 'id',
				hide :true ,
				width: '0'
			},{
				field: 'name',
				title: '名称',
				event: 'click'
			},{
				field: 'conName',
				title: '发起人姓名',
				event: 'click'
			},{
				field: 'conPhone',
				title: '发起人电话',
				event: 'click'
			},{
				field: 'actDate',
				title: '返校时间',
				event: 'click'
			},{
				field: 'readyNo',
				title: '人数',
				event: 'click'
			},{
				field: 'receptionService',
				title: '接待服务',
				event: 'click',
				templet: function(d){
					console.log(d);
					var res = d.receptionService;
					var htm="";
					if(res != null && res != ""){
						var data = res.split(",");
						var htm="";
						for(var i = 0; i<data.length ;i++){
							if(data[i] != "校友标签"){
								htm =htm + "<span class='layui-badge-rim layui-bg-blue'>"+data[i]+"</span>";
							}
						}
					}
					return htm;
				}
			}]
		],
		done: function(res, curr, count) { //res 接口返回的信息,
			var flag = 0;
			$("[data-field = 'id']").children().each(function(){
				if($("[data-field = 'id']").children().length==1){
					return false;
				}
				if(flag > 1){
					return false;
				}
				rendStarInfo($(this).text());
				flag++;
			})
			publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'SELFORG_STATUS'},'status');
		}
	});


	//右键点击事件
	table.on('rowRight(selforgList)', function(obj){
		publicUtil.show_menu(obj);
	});

	/*//左键点击事件
	table.on('row(selforgList)', function(obj){
		publicUtil.hiddenMenu(obj);
		rendStarInfo(obj.data.id);
	});*/
	//行事件
	table.on('row(selforgList)', function(obj){
		//$(".layui-select-tr").removeClass("layui-select-tr");
		//obj.tr.addClass("layui-select-tr");
		publicUtil.hiddenMenu(obj);
		rendStarInfo(obj.data.id);
		projectObj = obj.data.id;
		
	});



	function rendStarInfo(id)
	{
		starInfotableIns = table.render({
			elem: '#selforgContentList',
			url: application.SERVE_URL + '/actContent/actSelforgContent/list',
			even : true,
			cellMinWidth: 95,
			where: {actSelforgId : id},
			page: true,
			height: "full-160",
			limit: 10,
			id: "selforgContentList",
			cols: [
				[{
					field: 'name',
					title: '报名人员'
				}, {
					field: 'createDate',
					title: '报名时间'
				}]]
		});
		return ;
	}

	//删除
	$(document).on('click', '.PER_DEL', function () {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('selforgList').data);
		if (flag) {
			layer.confirm('确定删除此值年返校信息？', {
				icon: 3,
				title: '提示信息'
			}, function (index) {
				$.ajax({
					url: application.SERVE_URL + "/actContent/actSelforg/delete", //ajax请求地址
					data: {
						id: table.checkStatus('selforgList').data[0].id
					},
					success: function (data) {
						if (data.code==application.REQUEST_SUCCESS) {
							top.layer.msg(data.msg);
							table.reload("selforgList", {
								page: {
									curr: 1 //重新从第 1 页开始
								},
								where: {
									status: $(".searchVal").val(),
								}
							})
							layer.close(index);
						}
					}
				});
			});
		} else {
			return false;
		}
	});
	$(document).on('click', '.PER_EDIT', function () {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('selforgList').data);
		var receptionService =table.checkStatus('selforgList').data[0].receptionService==null?"无":table.checkStatus('selforgList').data[0].receptionService;
		var otherReception =table.checkStatus('selforgList').data[0].otherReception==null?"无":table.checkStatus('selforgList').data[0].otherReception;
		var _html ="<div style='line-height: 25px; padding:20px; '>" +
			"<div ><span style='padding: 0 20px 0 10px;font-weight: bold;'>名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称:</span><span>"+table.checkStatus('selforgList').data[0].name+"</span></div>" +
			"<div style='padding: 0 20px 0 10px;'><span style='padding: 0 20px 0 0px;font-weight: bold;'>内&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;容:</span><span>"+table.checkStatus('selforgList').data[0].content+"</span></div>" +
			"<div style='padding: 0 20px 0 10px'><span style='padding: 0 20px 0 0px;font-weight: bold;'>接待服务:</span><span>"+receptionService+"</span></div>" +
			"<div style='padding: 0 20px 0 10px'><span style='padding: 0 20px 0 0px;font-weight: bold;'>其他诉求:</span><span>"+otherReception+"</span></div>" +
			"</div>"
		if (flag) {
			layer.open({
				title: '确定审核该值年返校?'
				,type: 1
				,content: _html
				,skin: 'layui-layer-rim'//加上边框
				,area: ['420px', '300px'] //宽高
				,btn: ['通过', '驳回', '取消']
				,yes: function(index, layero){
					$.ajax({
						url: application.SERVE_URL + "/actContent/actSelforg/save", //ajax请求地址
						data: {
							id: table.checkStatus('selforgList').data[0].id,
							status: "SUCCESS"
						},
						success: function (data) {
							if (data.code==application.REQUEST_SUCCESS) {
								top.layer.msg("审核成功");
								table.reload("selforgList", {
									page: {
										curr: 1 //重新从第 1 页开始
									},
									where: {
										status: $(".searchVal").val(),
									}
								})
								starInfotableIns.reload();
								layer.close(index);
							}
						}
					});
				}
				,btn2: function(index, layero){
					$.ajax({
						url: application.SERVE_URL + "/actContent/actSelforg/save", //ajax请求地址
						data: {
							id: table.checkStatus('selforgList').data[0].id,
							status: "LOSE"
						},
						success: function (data) {
							if (data.code==application.REQUEST_SUCCESS) {
								top.layer.msg("审核成功");
								table.reload("selforgList", {
									page: {
										curr: 1 //重新从第 1 页开始
									},
									where: {
										status: $(".searchVal").val(),
									}
								})
								starInfotableIns.reload();
								layer.close(index);
							}
						}
					});
				}
				,btn3: function(index, layero){
				}
				,cancel: function(){
				}
			});
		} else {
			return false;
		}

	})

	//搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$(".search_btn").on("click", function () {
		table.reload("selforgList", {
			page: {
				curr: 1 //重新从第 1 页开始
			},
			where: {
				status: $(".searchVal").val(),
			}
		})
	});

})
