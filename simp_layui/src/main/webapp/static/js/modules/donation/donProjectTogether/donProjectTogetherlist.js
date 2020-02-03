/**
 * @autor syp
 * @content 一起捐列表及捐赠记录页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base: "../../../../static/js/"
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
})
var donationTableIns;
var projectObj;
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
		elem: '#projectList',
		url: application.SERVE_URL + '/donContent/donProjectTogether/list',
		even: true,
		cellMinWidth: 95,
		page: true,
		height: "full-160",
		limit: 10,
		headers: {
			'Authorization': application.HEADER
		},
		id: "projectList",
		cols: [
			[{
				field: 'name',
				title: '项目名称'
			},{
				field: 'id',
				hide :true ,
				width: '0'
			}, {
				field: 'targetMoney',
				title: '目标金额'
			}, {
				field: 'gotMoney',
				title: '当前金额'
			}]
		],
		done: function(res, curr, count) { //res 接口返回的信息,
			
			projectObj = res.list[0];
			console.log(projectObj)
			var flag = 0;
			rendRecordList(projectObj.id);
			$("[data-field = 'id']").children().each(function(data){
					if(flag > 0){
						return false;
					}
					rendRecordList($(this).text());
					flag++;
			})
		}
	});

	function rendRecordList(id) {
		donationTableIns = table.render({
			elem: '#recordList',
			url: application.SERVE_URL + '/don/donRecord/list?togetherId=' + id,
			even: true,
			cellMinWidth: 95,
			page: true,
			height: "full-160",
			limit: 10,
			headers: {
				'Authorization': application.HEADER
			},
			id: "recordList",
			cols: [
				[{
					type: 'checkbox'
				}, {
                    field: 'name',
                    title: '捐赠人'
                }, {
					field: 'donType',
					title: '捐赠类型'
				}, {
					field: 'money',
					title: '捐赠金额'
				}, {
					field: 'moneyType',
					title: '币种'
				}, {
					field: 'time',
					title: '捐赠时间'
				}]
			],
			done: function(res, curr, count) { //res 接口返回的信息,
				publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
					'typeCode': 'DONATION_TYPE'
				}, 'donType');
				publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
					'typeCode': 'MONEY_TYPE'
				}, 'moneyType');
			}
		});
	}

	//右键点击事件
	table.on('rowRight(recordList)', function(obj) {
		publicUtil.show_menu(obj);
	});

	//左键点击事件
	table.on('row(recordList)', function(obj) {
		publicUtil.hiddenMenu(obj);
        // projectObj = obj.data;
	});

    //右键点击事件
    table.on('rowRight(projectList)', function(obj) {
        publicUtil.show_menu(obj);
    });

    //左键点击事件
    table.on('row(projectList)', function(obj) {
        publicUtil.hiddenMenu(obj);
        projectObj = obj.data;
        rendRecordList(obj.data.id)
    });


	//新增操作
	// $(document).on('click', '.PER_ADD', function() {
	// 	if (projectObj) {
	// 		addCard();
	// 	} else {
	// 		top.layer.msg("请先选择项目！");
	// 	}
	// });

	//编辑操作
// 	$(document).on('click', '.PER_EDIT', function() {
// 		var flag = publicUtil.jurgeSelectRows(table.checkStatus('recordList').data);
// 		if (flag) {
// 			addCard(table.checkStatus('recordList').data[0]);
// 		} else {
// 			return false;
// 		}
// 
// 	})

	//删除
	// $(document).on('click', '.PER_DEL', function() {
	// 	var flag = publicUtil.jurgeSelectRows(table.checkStatus('recordList').data);
	// 	if (flag) {
	// 		layer.confirm('确定删除此捐赠信息吗？', {
	// 			icon: 3,
	// 			title: '提示信息'
	// 		}, function(index) {
	// 			_$.ajax({
	// 				url: application.SERVE_URL + "/don/donRecord/delete", //ajax请求地址
 //                    type: 'POST',
	// 				data: {
	// 					id: table.checkStatus('recordList').data[0].id
	// 				},
	// 				success: function(data) {
	// 					if (data.code == application.REQUEST_SUCCESS) {
	// 						donationTableIns.reload();
	// 						layer.close(index);
	// 					}
	// 				}
	// 			});
	// 		});
	// 	} else {
	// 		return false;
	// 	}
	// })

	//获取权限并加载按钮
	publicUtil.getPerms(application.PERMS_URL, application.HEADER, parent.cur_menu_id, 'get', 'but_per');
	//搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$(".search_btn").on("click", function() {
		table.reload("recordList", {
			page: {
				curr: 1 //重新从第 1 页开始
			},
			where: {
                name:  $(".searchVal").val(),
               money: $(".searchNumVal").val(),
			}
		})
	});

})
