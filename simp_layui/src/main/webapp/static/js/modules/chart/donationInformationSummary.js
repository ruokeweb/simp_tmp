/**
 * @autor zdl
 * @content 捐赠信息汇总（列表）
 * @returns
 * @Time 2019-12-09
 */
layui.config({
	base : "../../../../static/js/"
}).extend({
	"application" : "application",
	"publicUtil"  : "publicUtil"
})
layui.use(['form','layer','laydate','table','application','publicUtil'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
		publicUtil = layui.publicUtil,
        application = layui.application,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

	application.init();
	    //列表
    var tableMoney = table.render({
		even : true 		
        ,elem: '#donationMoney'
		,autoSort: false
        ,url : application.SERVE_URL+'/chart/donation/getDonationMoney',
        cellMinWidth : 95,
        page : true,
		where: {field:'field', type:'desc'},
        height : "full-160",
        limit : 10,
        id : "donationMoney",
        cols : [[
            {field: 'name', title: '捐赠人'},
            {field: 'field', title: '捐赠金额(元)',sort:true},
        ]],
		initSort: {field:'field', type:'desc'}
    });
	var tableCount = table.render({
		even : true
		,elem: '#donationCount'
		,autoSort: false
		,url : application.SERVE_URL+'/chart/donation/getDonationCount',
		cellMinWidth : 95,
		page : true,
		where: {field:'field', type:'desc'},
		height : "full-160",
		limit : 10,
		id : "donationCount",
		cols : [[
			{field: 'name', title: '捐赠人'},
			{field: 'field', title: '捐赠次数',sort:true},
		]],
		initSort: {field:'field', type:'desc'}
	});
	var tableProject = table.render({
		even : true
		,elem: '#donationProject'
		,autoSort: false
		,url :application.SERVE_URL+'/chart/donation/getDonationProject',
		cellMinWidth : 95,
		page : true,
		where: {field:'field', type:'desc'},
		height : "full-160",
		limit : 10,
		id : "donationProject",
		cols : [[
			{field: 'name', title: '捐赠项目'},
			{field: 'field', title: '捐赠次数' ,sort:true},
		]],
		initSort: {field:'field', type:'desc'}
	});
//右键点击事件
	table.on('sort(donationMoney)', function(obj){
		console.log(obj);
		table.reload('donationMoney', {
			initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。
			,where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
				field: obj.field //排序字段
				,type: obj.type //排序方式
			}
		});
	});
//右键点击事件
	table.on('sort(donationCount)', function(obj){
		table.reload('donationCount', {
			initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。
			,where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
				field: obj.field //排序字段
				,type: obj.type //排序方式
			}
		});
	});
	//右键点击事件
	table.on('sort(donationProject)', function(obj){
		table.reload('donationProject', {
			initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。
			,where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
				field: obj.field //排序字段
				,type: obj.type //排序方式
			}
		});
	});


})