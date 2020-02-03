/**
 * @autor syp
 * @content 捐赠项目列表页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base: "../../../../static/js/"
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
})

var actObj;
var settingTableIns;
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
		elem: '#actList',
		url: application.SERVE_URL + '/act/actActivity/list',
		even: true,
		cellMinWidth: 95,
		page: true,
		height: "full-160",
		limit: 10,
		headers: {
			'Authorization': application.HEADER
		},
		id: "actList",
		cols: [
			[{
				field: 'name',
				title: '活动名称'
			}, {
				field: 'status',
				title: '活动状态'
			},{
                field: 'id',
                hide :true ,
                width: '0'
            }]
		],
		done: function(res, curr, count) { //res 接口返回的信息,
			publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'ACT_STATUS'
			}, 'status');
            if(res.list.length > 0){
                actObj = res.list[0];
                rendList(actObj.id);
            }
			/*actObj = res.list[0];
			rendList(actObj.id);*/
//             var flag = 0;
//             $("[data-field = 'id']").children().each(function(){
//             	if($("[data-field = 'id']").children().length==1){
//                     rendList("0");
//                     return false;
// 				}
//                 if(flag > 1){
//                     return false;
//                 };
// 
//                 rendList($(this).text());
//                 flag++;
//             })
		}
	});

	function rendList(id) {
		settingTableIns = table.render({
			elem: '#settingList',
			url: application.SERVE_URL + '/act/actSetting/list?activityId=' + id,
			even: true,
			cellMinWidth: 95,
			page: true,
			height: "full-160",
			limit: 10,
			headers: {
				'Authorization': application.HEADER
			},
			id: "settingList",
			cols: [
				[{
					type: 'checkbox'
				}, {
					field: 'name',
					title: '名称'
				}, {
					field: 'code',
					title: '编码'
				}, {
					field: 'formType',
					title: '类型'
				}, {
					field: 'sort',
					title: '排序'
				}]
			],
			done: function(res, curr, count) { //res 接口返回的信息,
				
			}
		});
	}
	// rendList();

	//右键点击事件
	table.on('rowRight(actList)', function(obj) {
		publicUtil.show_menu(obj);
	});

	//左键点击事件
	table.on('row(actList)', function(obj) {
		publicUtil.hiddenMenu(obj);
	});

	//右键点击事件
	table.on('rowRight(settingList)', function(obj) {
		publicUtil.update_check_ed(obj);
	});

	//左键点击事件
	table.on('row(settingList)', function(obj) {
		publicUtil.update_check_ed(obj);
	});

	//行事件
	table.on('row(actList)', function(obj) {
		$(".layui-select-tr").removeClass("layui-select-tr");
		obj.tr.addClass("layui-select-tr");
		rendList(obj.data.id);
		actObj = obj.data;
	});

	//新增操作
	$(document).on('click', '.PER_ADD', function() {
		if (actObj) {
            if(actObj.status == 'DOING'){
                top.layer.msg("该活动正在报名中，不能新增表单！");
                return false;
            }else if(actObj.status == 'DONE'){
                top.layer.msg("该活动报名已经结束，不能新增表单！");
                return false;
            }else if(actObj.status == 'READAY'){
                addCard();
            }
		} else {
			top.layer.msg("请先选择项目！");
		}
	});

	//编辑操作
	$(document).on('click', '.PER_EDIT', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('settingList').data);
		if (flag) {
			addCard(table.checkStatus('settingList').data[0]);
		} else {
			return false;
		}

	})

	//删除
	$(document).on('click', '.PER_DEL', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('settingList').data);
		if (flag) {
            if(actObj.state != 'DOING'){
                top.layer.msg("该活动正在报名中，不能删除表单！");
                return false;
            }else if(actObj.state != 'DONE'){
                top.layer.msg("该活动报名已经结束，不能删除表单！");
                return false;
            }else if(actObj.state != 'READAY'){
                layer.confirm('确定删除此活动表单吗？', {
                    icon: 3,
                    title: '提示信息'
                }, function(index) {
                    $.ajax({
                        url: application.SERVE_URL + "/act/actSetting/delete", //ajax请求地址
                        data: {
                            id: table.checkStatus('settingList').data[0].id
                        },
                        success: function(data) {
                            if (data.code == application.REQUEST_SUCCESS) {
                                settingTableIns.reload();
                                layer.close(index);
                            }
                        }
                    });
                });
            }
		} else {
			return false;
		}
	})

	//获取权限并加载按钮
	publicUtil.getPerms(application.PERMS_URL, application.HEADER, parent.cur_menu_id, 'get', 'but_per');
	//搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$(".search_btn").on("click", function() {
        table.reload("actList", {
			page: {
				curr: 1 //重新从第 1 页开始
			},
			where: {
				name: $(".searchVal").val(),
			}
		})
	});

	//添加捐赠信息
	function addCard(edit) {
		var restUrl = application.SERVE_URL + '/act/actSetting/get';
		var id = edit ? (edit.id ? edit.id : null) : null;
		publicUtil.gotoEditPage(restUrl, id, "添加设置信息", "actSettingAdd.html")
	}
})
