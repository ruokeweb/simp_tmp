/**
 * @autor syp
 * @content 临时专业合并js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base: "../../../../static/js/"
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
})
var selecttempSeries={};
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

	//获取权限并加载按钮
	publicUtil.getPerms(application.PERMS_URL, application.HEADER, parent.cur_menu_id, 'get', 'but_per');
	

	var tableIns = table.render({
		elem: '#specialtyMergeList',
		url: application.SERVE_URL + '/settings/settingDepartment/loadAllTempSpecialty',
		cellMinWidth: 95,
		page: true,
		even: true,
		headers: {
			'Authorization': application.HEADER
		},
		height: "full-160",
		limit: 10,
		id: "specialtyMergeList",
		cols: [
			[
				{
					type:'checkbox'
			    },
				{
					field: 'schoolName',
					title: '学校名称'
				},
				{
					field: 'collegeName',
					title: '学院名称'
				},
				{
					field: 'seriesName',
					title: '系名称'
				},
				{
					field: 'tempSpecialty',
					title: '专业名称'
				},
				{
					field: 'num',
					title: '人数'
				}
			]
		],
		done: function(res, curr, count) { //res 接口返回的信息
			
		}
	});
	//右键点击事件
	table.on('rowRight(specialtyMergeList)', function(obj){
		publicUtil.show_menu(obj);
	});
	

	//左键点击事件
	table.on('row(specialtyMergeList)', function(obj){
		publicUtil.hiddenMenu(obj);
	});


	//编辑操作
	$(document).on('click','.PER_EDIT',function(){
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('specialtyMergeList').data);
		if(flag){
			selecttempSeries=table.checkStatus('specialtyMergeList').data[0];
			selectOrg();
		}else{
			return false;
		}

	})
	function selectOrg(){
		var index = layui.layer.open({
			type: 2,
			title: '合并专业选择',
			shadeClose: true,
			shade: 0.8,
			area: ['280px', '65%'],
			// content: '../views/module/system/menu/menuselect.html',
			content: 'departmentselect.html',
			success : function(layero, index){
				//
				setTimeout(function(){
					layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
						tips: 3
					});
				},500)
			},
		})
	}
	
})
