/**
 * @autor syp
 * @content 星级列表页面js
 * @returns
 * @Time 2018-09-12
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

	application.init();

	//获取权限并加载按钮
	publicUtil.getPerms(application.PERMS_URL, application.HEADER, parent.cur_menu_id, 'get', 'but_per');
	rendStarInfo("init");

	
	var tableIns = table.render({
		elem: '#starList',
		url: application.SERVE_URL + '/settings/smStar/list',
		even : true,
		cellMinWidth: 95,
		page: true,
		height: "full-160",
		limit: 10,
		id: "starList",
		cols: [
			[{
				type: 'checkbox'
			},{
				field: 'name',
				title: '星级名称',
				event: 'click'
			},{
				field: 'id',
				hide :true ,
				width: '0'
			}, {
				field: 'infoLevel',
				title: '星级',
				event: 'click'
			}]
		],
		done: function(res, curr, count) { //res 接口返回的信息,
			var flag = 0;
			$("[data-field = 'id']").children().each(function(){
                if($("[data-field = 'id']").children().length==1){
                    rendStarInfo("0");
                    return false;
                }
				if(flag > 1){
					return false;
				}
				rendStarInfo($(this).text());
				flag++;
			})
		}
	});
	
	//右键点击事件
	table.on('rowRight(starList)', function(obj){
		publicUtil.show_menu(obj);
	});
	
	//左键点击事件
	table.on('row(starList)', function(obj){
		publicUtil.hiddenMenu(obj);
		rendStarInfo(obj.data.id);
	});
	
	function rendStarInfo(id)
	{
		starInfotableIns = table.render({
			elem: '#starInfoList',
			url: application.SERVE_URL + '/settings/smStarSminfo/list',
			even : true,
			cellMinWidth: 95,
			where: {smStarId : id},
			page: true,
			height: "full-160",
			limit: 10,
			id: "starInfoList",
			cols: [
				[{
					field: 'smSminfoId',
					title: '信息名称'
				}, {
					field: 'smSminfoCode',
					title: '信息编码'
				},{
					field: 'remark',
					title: '信息说明'
				}]
			]
		});
	}

	/*table.on('row(starList)', function (obj) {
		$(".layui-select-tr").removeClass("layui-select-tr");
		obj.tr.addClass("layui-select-tr");
	
	});*/


	//新增操作
	$(document).on('click', '.PER_ADD', function () {
		addStarInfo();
	});

	//编辑操作
	$(document).on('click', '.PER_EDIT', function () {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('starList').data);
		if (flag) {
			addStarInfo(table.checkStatus('starList').data[0]);
		} else {
			return false;
		}

	})

	//删除
	$(document).on('click', '.PER_DEL', function () {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('starList').data);
		if (flag) {
			layer.confirm('确定删除此星级设置吗？', {
				icon: 3,
				title: '提示信息'
			}, function (index) {
				$.ajax({
					url: application.SERVE_URL + "/settings/smStarSminfo/delete", //ajax请求地址
					data: {
						id: table.checkStatus('starList').data[0].id
					},
					success: function (data) {
						if (data.code==application.REQUEST_SUCCESS) {
							tableIns.reload();
							starInfotableIns.reload();
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
	$(".search_btn").on("click", function () {
		table.reload("starList", {
			page: {
				curr: 1 //重新从第 1 页开始
			},
			where: {
				name: $(".searchVal").val(),
			}
		})
	});

	//添加星级
	function addStarInfo(edit) {		
		var restUrl = application.SERVE_URL + '/settings/smStarSminfo/getInfo';
		var id = edit ? (edit.id ? edit.id : null) : null;
		publicUtil.gotoEditPage(restUrl, id, "星级配置", "starsettingadd.html")
	}
})
