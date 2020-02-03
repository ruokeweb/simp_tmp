/**
 * @autor syp
 * @content 日志列表页面js
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
		$ = layui.jquery,
		application = layui.application,
		laydate = layui.laydate,
		laytpl = layui.laytpl,
		table = layui.table,
		publicUtil = layui.publicUtil;

	application.init();
	var start;
	var end;
	//执行一个laydate实例
	laydate.render({
		elem: '#start',
		// type: 'datetime',
		theme: 'molv',
		trigger: 'click',
			//指定元素
		done: function(value, date, endDate) {
			//得到日期生成的值，如：2017-08-18
			start = value;
		}
	});


	//执行一个laydate实例
	laydate.render({
		elem: '#end' ,//指定元素,,
			
		// type: 'datetime',
		theme: 'molv',
		trigger: 'click',
		done: function(value, date, endDate) {
			//得到日期生成的值，如：2017-08-18
			end = value;
		}
	});
	//获取权限并加载按钮
	publicUtil.getPerms(application.PERMS_URL, application.HEADER, parent.cur_menu_id, 'get', 'but_per');

	//日志列表
	var tableIns = table.render({
		elem: '#smpointlogsList',
		url: application.SERVE_URL + '/settings/pointLogs/list',
		cellMinWidth: 95,
		page: true,
		even: true,
		headers: {
			'Authorization': application.HEADER
		},
		height: "full-160",
		limit: 10,
		id: "smpointlogsList",
		cols: [
			[
				//{type:'checkbox'},
				{
					field: 'username',
					title: '登录名'
				},
				{
					field: 'type',
					title: '类型'
				},
				{
					field: 'operateIp',
					title: '操作IP地址'
				},
				{
					field: 'createDate',
					title: '操作日期'
				},
				{
					field: 'integraltion',
					title: '获取积分'
				},
				{
					field: 'remark',
					title: '操作信息'
				}
			]
		],
		done: function(res, curr, count) { //res 接口返回的信息
			publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'POINT_LOGS_TYPE'
			}, 'type');
		}
	});

	//搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$(".search_btn").on("click", function() {
		if (end < start) {
			top.layer.msg("开始时间不能大于结束时间");
			return;
		}
		table.reload("smpointlogsList", {
			page: {
				curr: 1 //重新从第 1 页开始
			},
			where: {
				username: $(".searchVal").val(), //搜索的关键字
				queryBeginDate: start,
				queryEndDate: end
			}
		})
	});

	function openLogsInfo(edit) {
		var index = layui.layer.open({
			type: 2,
			title: '日志详情',
			shadeClose: true,
			shade: 0.8,
			area: ['80%', '75%'],
			// content: '../views/module/system/menu/menuselect.html',
			content: 'logsInfo.html',
			success: function(layero, index) {
				var body = layui.layer.getChildFrame('body', index);
				if (edit) {
					$.ajax({
						url: application.SERVE_URL + '/settings/pointLogs/get', //ajax请求地址
						data: {
							id: edit.id,
						},
						success: function(result) {
							if (result.code == application.REQUEST_SUCCESS) {
								body.find(".id").val(result.data.id);
								body.find(".type").val(result.data.type);
								body.find(".username").val(result.data.username);
								body.find(".createDate").val(result.data.createDate);
								body.find(".remoteAddr").val(result.data.remoteAddr);
								body.find(".userAgent").val(result.data.userAgent);
								body.find(".requestUri").val(result.data.requestUri);
								body.find(".method").val(result.data.method);
								body.find(".params").val(result.data.params);
								body.find(".excContent").val(result.data.excContent);
							}
						}
					});
					form.render();
				}
				setTimeout(function() {
					layui.layer.tips('点击此处返回日志列表', '.layui-layer-setwin .layui-layer-close', {
						tips: 3
					});
				}, 120)
			},
		})
	}
})