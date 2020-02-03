/**
 * @autor syp
 * @content 字典列表页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base : "../../../../static/js/"
}).extend({
	"application" : "application",
	"publicUtil"  : "publicUtil"
})
layui.use(['form','layer','laydate','table','laytpl','application','publicUtil'],function(){
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
	publicUtil.getPerms(application.PERMS_URL,application.HEADER,parent.cur_menu_id,'get','but_per');
	
    //编码列表
    var tableIns = table.render({
    	statusCode: 200
		,even : true 		
        ,elem: '#backList'
        ,url : application.SERVE_URL+'/sys/backup/list',
        cellMinWidth : 95,
        page : false,
        height : "full-160",
        limit : 10,
        id : "backList",
        response:{
        	dataName: 'data'
        },
        cols : [[
        	//{field: 'id', title: 'ID', align:"center",style:'display:none;'},
			{type:'checkbox'},
            {field: 'name', title: '名称'},
            {field: 'type', title: '类型'},
            {field: 'path', title: '路径'},
			{field: 'date',sort: true, title:'日期'}
        ]],
		done: function (res, curr, count) {
			$('th div span').css({'font-weight:': 'bold'});
		}
    });

	//右键点击事件
	table.on('rowRight(backList)', function(obj){
		publicUtil.show_menu(obj);
	});
	
	//左键点击事件
	table.on('row(backList)', function(obj){
		publicUtil.hiddenMenu(obj);
	});
	
	//新增操作
	$(document).on('click','.PER_ADD',function(){
		layer.confirm('备份数大于15条后，将会自动替换最老的备份。请确定手动备份数据吗？',{icon:3, title:'提示信息'},function(index){	
		// 弹出loading
		var index = top.layer.msg('数据正在努力的备份中，请稍候...', {
			icon: 16,
			time: false,
			shade: 0.8
		});
		
		$.ajax({
			url: application.SERVE_URL+"/sys/backup/back", //ajax请求地址
			data:{
				type:'Manual'
			},
			success: function (res) {
				if(res.code==application.REQUEST_SUCCESS){
		            top.layer.msg(res.msg,{time:1000});	
		            layer.closeAll("iframe");
		            //刷新页面
		            location.reload();
				}else{
					layer.msg(res.msg,{time:1000});
				}
			}
		}); 
		});
    });

	//编辑操作
	$(document).on('click','.PER_IMPORT',function(){		
		layer.confirm('确定恢复数据吗？',{icon:3, title:'提示信息'},function(index){	
		// 弹出loading
		var index = top.layer.msg('数据正在努力的恢复中，请稍候...', {
			icon: 16, 
			time: false,
			shade: 0.8
		});
		
		$.ajax({
			url: application.SERVE_URL+"/sys/backup/recoverMysql", //ajax请求地址
			data:{
				id:table.checkStatus('backList').data[0].id
			},
			success: function (res) {
				if(res.code==application.REQUEST_SUCCESS){
		            top.layer.msg(res.msg,{time:1000});	
		            layer.closeAll("iframe");
		            //刷新页面
		            location.reload();
				}else{
					layer.msg(res.msg,{time:1000});
				}
			}
		}); 
		});

    })
    //编辑操作
	$(document).on('click','.PER_EXPORT',function(){
		layer.confirm('确定恢复数据吗？',{icon:3, title:'提示信息'},function(index){		
		// 弹出loading
		var index = top.layer.msg('文件正在努力的恢复中，请稍候...', {
			icon: 16, 
			time: false,
			shade: 0.8
		});
		
		$.ajax({
			url: application.SERVE_URL+"/sys/backup/recoverMongo", //ajax请求地址
			data:{
				id:table.checkStatus('backList').data[0].id
			},
			success: function (res) {
				if(res.code==application.REQUEST_SUCCESS){
		            top.layer.msg(res.msg,{time:1000});	
		            layer.closeAll("iframe");
		            //刷新页面
		            location.reload();
				}else{
					layer.msg(res.msg,{time:1000});
				}
			}
		}); 
		});
    })
	
	//删除
	$(document).on('click','.PER_DEL',function(){		
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('backList').data);
		if(flag){
            layer.confirm('确定删除这些备份文件吗？',{icon:3, title:'提示信息'},function(index){				
				$.ajax({
					url: application.SERVE_URL+"/sys/backup/delete", //ajax请求地址
					data:{
						id : table.checkStatus('backList').data[0].id
					},										
					success: function (res) {
						if(res.code==application.REQUEST_SUCCESS){
							tableIns.reload();
							// location.reload();
							top.layer.close(index);	
							top.layer.msg(res.msg,{time:1000});							
						}else{
							top.layer.msg(res.msg,{time:1000});
						}

					}
				});	
            });			
		}else{
			return false;
		}
    })	


})