/**
 * @autor zdl
 * @content 活动信息汇总（列表）
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
        ,elem: '#actSign'
		,autoSort: false
        ,url : application.SERVE_URL+'/chart/activity/getSignActNum',
        cellMinWidth : 95,
        page : true,
		where: {field:'field', type:'desc'},
        height : "full-160",
        limit : 10,
        id : "actSign",
        cols : [[
            {field: 'name', title: '活动名称'},
            {field: 'field', title: '报名人数',sort:true},
        ]],
		initSort: {field:'field', type:'desc'}
    });
	var tableCount = table.render({
		even : true
		,elem: '#actBackSign'
		,autoSort: false
		,url : application.SERVE_URL+'/chart/activity/getSignActBackNum',
		cellMinWidth : 95,
		page : true,
		where: {field:'field', type:'desc'},
		height : "full-160",
		limit : 10,
		id : "actBackSign",
		cols : [[
			{field: 'name', title: '值年返校名称'},
			{field: 'field', title: '报名人数',sort:true},
		]],
		initSort: {field:'field', type:'desc'}
	});

//右键点击事件
	table.on('sort(actSign)', function(obj){
		console.log(obj);
		table.reload('actSign', {
			initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。
			,where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
				field: obj.field //排序字段
				,type: obj.type //排序方式
			}
		});
	});
//右键点击事件
	table.on('sort(actBackSign)', function(obj){
		table.reload('actBackSign', {
			initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。
			,where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
				field: obj.field //排序字段
				,type: obj.type //排序方式
			}
		});
	});
})