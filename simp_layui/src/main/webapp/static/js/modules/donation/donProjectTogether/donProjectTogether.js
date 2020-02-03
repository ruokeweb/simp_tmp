/**
 * @autor syp
 * @content 一起捐列表页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base: "../../../../static/js/"
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
})
var selectTabRow;
var tableIns;
var status;
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
	//获取权限并加载按钮
	publicUtil.getPerms(application.PERMS_URL, application.HEADER, parent.cur_menu_id, 'get', 'but_per');

	var tableIns = table.render({
		elem: '#donProjectTogether',
		url: application.SERVE_URL + '/donContent/donProjectTogether/list',
		even: true,
		cellMinWidth: 95,
		page: true,
		height: "full-160",
		limit: 10,
		headers: {
			'Authorization': application.HEADER
		},
		id: "donProjectTogether",
		cols: [
			[{
					type: 'checkbox'
				}, {
					field: 'id',
					hide: true,
					width: '0'
				}, {
					field: 'name',
					title: '项目名称'
				}, {
					title: '来源项目名称',
					templet: function(d) {
						return d.donProject.name
					}
				},
				{
					title: '发起人',
					templet: function(d) {
						return d.smSchoolmate.name
					}
				}, {
					field: 'targetMoney',
					title: '目标金额'
				}, {
					field: 'gotMoney',
					title: '当前金额'
				}, {
					field: 'status',
					title: '状态'
				}
			]
		],
		done: function(res, curr, count) { //res 接口返回的信息,
			var flag = 0;
			$("[data-field = 'id']").children().each(function() {
				if ($("[data-field = 'id']").children().length == 1) {
					rendRecordList("0");
					return false;
				}
				if (flag > 1) {
					return false;
				}
				rendRecordList($(this).text());
				flag++;
			})
			publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'SELFORG_STATUS'
			}, 'status');
		}
	});

	//右键点击事件
	table.on('rowRight(donProjectTogether)', function(obj) {
		selectTabRow = obj.data;
		console.log(selectTabRow)
		publicUtil.show_menu(obj);
	});

	//左键点击事件
	table.on('row(donProjectTogether)', function(obj) {
		selectTabRow = obj.data;
		$(".layui-select-tr").removeClass("layui-select-tr");
		obj.tr.addClass("layui-select-tr");
		rendRecordList(obj.data.id);
		publicUtil.hiddenMenu(obj);
	});

	//搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$(".search_btn").on("click", function() {
		table.reload("donProjectTogether", {
			page: {
				curr: 1 //重新从第 1 页开始
			},
			where: {
				name: $(".searchVal").val(),
				// money: $(".searchVal").val(),
				// summary:$(".searchVal").val(),
				// targetMoney:$(".searchVal").val()
			}
		})
	});

	function rendRecordList(id) {
		donationTableIns = table.render({
			elem: '#recordList',
			url: application.SERVE_URL + '/don/donRecord/list',
			where: {
				'togetherId': id
			},
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
					field: 'name',
					title: '捐赠人'
				}, {
					field: 'money',
					title: '捐赠金额'
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



	//审核是否显示一起捐项目
	$(document).on('click', '.PER_AUDITING', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('donProjectTogether').data);

		if (flag) {
			selectTabRow = table.checkStatus('donProjectTogether').data[0];
			var _html = "<div style='line-height: 25px;padding:20px;'>" +
				"<div ><span style='padding: 0 20px 0 10px;font-weight: bold;'>标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题:</span><span>" + selectTabRow.name + "</span></div>" +
				"<div style='padding: 0 20px 0 10px;font-weight: bold;'><span style='padding: 0 20px 0 0px'>简&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;介:</span><span>" + selectTabRow.summary +
				"</span></div>" +
				"<div style='padding: 0 20px 0 10px;font-weight: bold;'><span style='padding: 0 20px 0 0px'>目标金额:</span><span>" + selectTabRow.targetMoney +
				"</span></div>" +
				"</div>"
			// var index = layui.layer.open({
			// 	type: 2,
			// 	title: '审核一起捐是否显示',
			// 	shadeClose: true,
			// 	shade: 0.8,
			// 	area: ['55%', '55%'],
			// 	content: 'setting.html',
			// 	success: function(layero, index) {
			// 		setTimeout(function() {
			// 			layui.layer.tips('点击此处返回一起捐列表', '.layui-layer-setwin .layui-layer-close', {
			// 				tips: 3
			// 			});
			// 		}, 500)
			// 	},
			// })
			layer.open({
				title: '确定审核该一起捐项目?',
				type: 1,
				content: _html,
				skin: 'layui-layer-rim', //加上边框
				area: ['420px', '300px'], //宽高
				btn: ['通过', '驳回', '取消']
				,yes: function(index, layero) {
					$.ajax({
						url: application.SERVE_URL + '/donContent/donProjectTogether/updateStatus', //ajax请求地址
						data: {
							id: selectTabRow.id,
							status: "SUCCESS"
						},
						success: function(data) {
							top.layer.msg(data.msg);
							tableIns.reload("donProjectTogether", {
								page: {
									curr: 1 //重新从第 1 页开始
								},
								where: {
									status: status,
								}
							})
							layer.close(index);
						}
					});

				},
				btn2: function(index, layero) {
					$.ajax({
						url: application.SERVE_URL + '/donContent/donProjectTogether/updateStatus', //ajax请求地址
						data: {
							id: selectTabRow.id,
							status: "LOSE"
						},
						success: function(data) {
							top.layer.msg(data.msg);
							tableIns.reload("donProjectTogether", {
								page: {
									curr: 1 //重新从第 1 页开始
								},
								where: {
									status: status,
								}
							})
							layer.close(index);
						}
					});
				},
				btn3: function(index, layero) {},
				cancel: function() {}
			});
		} else {
			return false;
		}
	})
})
