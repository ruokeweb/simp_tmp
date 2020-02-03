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
layui.use(['form', 'layer', 'laydate', 'table', 'laytpl', 'application', 'publicUtil'], function () {
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
		url: application.SERVE_URL + '/don/donProject/list',
		even : true,
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
				type: 'radio'
			}, {
				field: 'name',
				title: '项目名称'
			}, {
				field: 'type',
				title: '项目类型'
			}, {
				field: 'secondName',
				title: '项目编号'
			}]
		],
		done: function(res, curr, count){    //res 接口返回的信息,
			publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'PROJECT_TYPE'},'type');
		}
	});

    //选择归档
    $(document).on('click', '#fileRecord', function(data) {
        var data = table.checkStatus('projectList').data;
        var flag = publicUtil.jurgeSelectRows(table.checkStatus('projectList').data);
        if (flag) {
			layer.confirm('确定将此记录合并到该项目中吗？', {
				icon: 3,
				title: '提示信息'
			}, function(index) {
				$.ajax({
					url: application.SERVE_URL + "/don/donRecord/fileRecord", //ajax请求地址
                    type: 'POST',
					data: {
						id: parent.recordId,
                        donProjectId: table.checkStatus('projectList').data[0].id
					},
					success: function(data) {
						if (data.code == application.REQUEST_SUCCESS) {
                            top.layer.msg(data.msg);	
                            layer.closeAll();
                            //刷新父页面
                            parent.location.reload();
						}else{
                            top.layer.msg(data.msg);	
                            layer.closeAll();
                            //刷新父页面
                            parent.location.reload();
                        }
					}
				});
			});
            console.log(parent.recordId);
        } else {
           return false;
        }

    });

	//搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$(".search_btn").on("click", function () {
		table.reload("projectList", {
			page: {
				curr: 1 //重新从第 1 页开始
			},
			where: {
				// typeCode: $(".searchVal").val(),
				// label: $(".searchVal").val(),
                name: $(".searchVal").val()
			}
		})
	});

})
