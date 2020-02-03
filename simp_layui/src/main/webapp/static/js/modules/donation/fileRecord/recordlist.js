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
var recordId;
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

    initSearchForm();

    function initSearchForm(){
        laydate.render({
            elem: '#time',
            theme: 'molv'
        });
        publicUtil.selectBase(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'PAY_STYLE'} ,"style",true);
    }


    var donationTableIns = table.render({
            elem: '#recordList',
            url: application.SERVE_URL + '/don/donRecord/loadRecordBy',
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
                    field: 'style',
                    title: '支付方式'
                }, {
                    field: 'money',
                    title: '捐赠金额'
                }, {
                    field: 'time',
                    title: '捐赠时间'
                }, {
                    field: 'remark',
                    title: '备注'
                }]
            ],
            done: function(res, curr, count) { //res 接口返回的信息,
                publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                    'typeCode': 'DONATION_TYPE'
                }, 'donType');
                publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                    'typeCode': 'MONEY_TYPE'
                }, 'moneyType');
                publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                    'typeCode': 'PAY_STYLE'
                }, 'style');
            }
    });



	//退款操作
	$(document).on('click', '.PER_PER_REFUND', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('recordList').data);
		if (flag) {
			layer.confirm('确定对此记录执行退款操作吗？', {
				icon: 3,
				title: '提示信息'
			}, function(index) {
				$.ajax({
					url: application.SERVE_URL + "/don/donRecord/refund", //ajax请求地址
                    type: 'POST',
					data: {
						id: table.checkStatus('recordList').data[0].id
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
    
    
	//归档操作
	$(document).on('click', '.PER_FILE_RECORD', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('recordList').data);
		if (flag) {
            recordId = table.checkStatus('recordList').data[0].id;
            var Index = layui.layer.open({
                type: 2,
                title: '选择要归档的捐赠项目',
                shadeClose: true,
                shade: 0.8,
                area: ['80%', '60%'],
                content: 'projectlist.html',
                success: function (layero, index) {
                    setTimeout(function () {
                        layui.layer.tips('点击此处返回校友会任职列表', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                },
            })
		} else {
			return false;
		}
	})

	//获取权限并加载按钮
	publicUtil.getPerms(application.PERMS_URL, application.HEADER, parent.cur_menu_id, 'get', 'but_per');
    
	//搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$(".search_btn").on("click", function() {
		table.reload("recordList", {
			page: {
				curr: 1 //重新从第 1 页开始
			},
			where: {
                name:  $("#name").val(),
               style: $("#style").val(),
               time: $("#time").val(),
			}
		})
	});

})
