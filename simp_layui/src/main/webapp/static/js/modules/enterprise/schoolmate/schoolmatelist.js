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
var schoolmateTableIns;
var schoolmateSel;
var companySel;
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
		elem: '#companyList',
		url: application.SERVE_URL + '/ent/enterprise/list',
		even: true,
		cellMinWidth: 95,
		page: true,
		height: "full-160",
		limit: 10,
		headers: {
			'Authorization': application.HEADER
		},
		id: "companyList",
		cols: [
			[{
				field: 'name',
				title: '校友企业名称'
			},{
				field: 'id',
				hide :true ,
				width: '0'
			}, {
				field: 'type',
				title: '单位性质'
			},  {
				field: 'phone',
				title: '联系电话'
			}]
		],
		done: function(res, curr, count) { //res 接口返回的信息,
			publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'COMPANY_TPYE'
			}, 'type');     
			var flag = 0;
			companySel = res.list[0];
            console.log(companySel);
			$("[data-field = 'id']").children().each(function(){
					if(flag > 1){
						return false;
					}
                rendschoolmateList($(this).text());
					flag++;
			})
		}
	});

	function rendschoolmateList(id) {
		schoolmateTableIns = table.render({
			elem: '#schoolmateList',
			url: application.SERVE_URL + '/ent/entSchoolmate/list?enterId=' + id,
			even: true,
			cellMinWidth: 95,
			page: true,
			height: "full-160",
			limit: 10,
			headers: {
				'Authorization': application.HEADER
			},
			id: "schoolmateList",
			cols: [
				[{
					field: 'id',
					type: 'checkbox'
				}, {
					field: 'userId',
					title: '姓名',
                    templet: function(d) {
                        var res = d.smSchoolmate.name;
                        var htm = "";
                        if (res != null && res != "") {
                            htm = res ;
                        }else{
                            htm ='校友为空'
                        }
                        return htm;
                    }
				}, {
					field: 'position',
					title: '职位'
				}, {
					field: 'department',
					title: '部门'
				}]
			],
			done: function(res, curr, count) { //res 接口返回的信息,
			}
		});
	}
	// rendschoolmateList();

	//行事件
	table.on('rowRight(companyList)', function(obj) {
        publicUtil.show_menu(obj);
	});

	//行事件
	table.on('row(companyList)', function(obj) {
		// $(".layui-select-tr").removeClass("layui-select-tr");
		// obj.tr.addClass("layui-select-tr");
        publicUtil.hiddenMenu(obj);
		companySel = obj.data
		rendschoolmateList(obj.data.id);
	});


	//右键点击事件
	table.on('rowRight(schoolmateList)', function(obj) {
		publicUtil.update_check_ed(obj);
	});

	//左键点击事件
	table.on('row(schoolmateList)', function(obj) {
		// publicUtil.hiddenMenu(obj);
        publicUtil.update_check_ed(obj);
        schoolmateSel = obj.data;
	});
    
    

	//新增操作
	$(document).on('click', '.PER_ADD', function() {
		if (companySel) {
			addSchoolmate();
		} else {
			top.layer.msg("请先选择校友企业！");
		}
	});

	//编辑操作
	$(document).on('click', '.PER_EDIT', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('schoolmateList').data);
		if (flag) {
			addSchoolmate(table.checkStatus('schoolmateList').data[0]);
		} else {
			return false;
		}
	})

	//删除
	$(document).on('click', '.PER_DEL', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('schoolmateList').data);
		if (flag) {
			layer.confirm('确定删除此校友信息吗？', {
				icon: 3,
				title: '提示信息'
			}, function(index) {
				$.ajax({
				url: application.SERVE_URL +"/ent/entSchoolmate/delete", //ajax请求地址
					data: {
						id: table.checkStatus('schoolmateList').data[0].id
					},
					success: function(data) {
						if (data.code == application.REQUEST_SUCCESS) {
							schoolmateTableIns.reload();
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
	$(".search_btn").on("click", function () {
    table.reload("schoolmateList", {
        page: {
            curr: 1 //重新从第 1 页开始
        },
        where: {
            // typeCode: $(".searchTypeCode").val(),
            // label: $(".searchLabel").val(),
            paramA: $(".searchVal").val()
        }
    })
});

	//添加捐赠信息
	function addSchoolmate(edit) {
		var restUrl = application.SERVE_URL + '/ent/entSchoolmate/get';
		var id = edit ? (edit.id ? edit.id : null) : null;
		publicUtil.gotoEditPage(restUrl, id, "校友任职信息管理", "schoolmateadd.html")
	}
})