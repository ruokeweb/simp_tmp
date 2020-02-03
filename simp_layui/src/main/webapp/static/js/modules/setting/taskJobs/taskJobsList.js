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
		pageName: 'pageNo' //页码的参数名称，默认：page
		,limitName: 'pageSize' //每页数据量的参数名，默认：limit,
		,dataName: 'list'
		,statusCode: 200
		,even : true 		
        ,elem: '#taskJobsList'
        ,url : application.SERVE_URL+'/task/taskJobs/list',
        cellMinWidth : 95,
        page : true,
        height : "full-160",
        limit : 10,
        id : "taskJobsList",
        cols : [[
        	//{field: 'id', title: 'ID', align:"center",style:'display:none;'},
			{type:'radio'},
            {field: 'jobName', title: '任务名称'},
            {field: 'jobGroup', title: '任务组'},
            {field: 'className', title: '任务类名'},
			{field: 'cronExcute', title: '执行时间'},
            {field: 'status', title: '当前状态', align:'center', templet:function(d){
				var currstatus = d.status == 0 ? "checked": ""
                return '<input type="checkbox" name="status" data-id ="'+d.id+'" lay-filter="status" lay-skin="switch" lay-text="是|否" '+currstatus+'>'
            }}
        ]],
		done: function (res, curr, count) {
// 			$("[data-field = 'status']").children().each(function(){
// 				if($(this).text() == "NORMAL"){
// 					$(this).text("正常");
// 				}else{
// 					$(this).text("停止");
// 				}
// 			})
		}
    });

// 	//右键点击事件
// 	table.on('rowRight(taskJobsList)', function(obj){
// 		publicUtil.show_menu(obj);
// 	});
	
// 	//左键点击事件
// 	table.on('row(taskJobsList)', function(obj){
// 		publicUtil.hiddenMenu(obj);
// 	});
	
	//新增操作
	$(document).on('click','.PER_ADD',function(){
    	_addDict();
    });
	
	//编辑操作
	$(document).on('click','.PER_EDIT',function(){		
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('taskJobsList').data);
		if(flag){			
			_addDict(table.checkStatus('taskJobsList').data[0]);
		}else{
			return false;
		}

    })
	
    form.on('switch(status)', function(data){
		var this_id  = $(this).attr("data-id");
		var alert_value =this.checked ? '0' : '1';
        var tipText = '确定停止当前任务？';
        if(data.elem.checked){
            tipText = '确定启用当前任务？'
        }
        layer.confirm(tipText,{
            icon: 3,
            title:'系统提示',
            cancel : function(index){
                data.elem.checked = !data.elem.checked;
                form.render();
                layer.close(index);
            }
        },function(index){
			console.log(data);
			//修改后台状态
			$.ajax({
				url: application.SERVE_URL+"/task/taskJobs/updateStatus", //ajax请求地址
				data:{
					id : this_id,
					status:  alert_value
				},										
				success: function (res) {
					if(res.code==application.REQUEST_SUCCESS){
						// tableIns.reload();
						// location.reload();
						top.layer.close(index);	
						top.layer.msg(res.msg,{time:1000});							
					}else{
						top.layer.msg(res.msg,{time:1000});
					}

				}
			});				
            layer.close(index);
        },function(index){
            data.elem.checked = !data.elem.checked;
            form.render();
            layer.close(index);
        });
    });	
	
	
	$(document).on('click','PER_AUDITING',function(){		
// 		var flag = publicUtil.jurgeSelectRows(table.checkStatus('taskJobsList').data);
// 		if(flag){
// 	        layer.confirm('确定修改此任务状态吗？',{icon:3, title:'提示信息'},function(index){				
// 				$.ajax({
// 					url: application.SERVE_URL+"/task/taskJobs/update", //ajax请求地址
// 					data:{
// 						id : table.checkStatus('taskJobsList').data[0].id  
// 					},										
// 					success: function (res) {
// 						if(res.code==application.REQUEST_SUCCESS){
// 							tableIns.reload();
// 							// location.reload();
// 							top.layer.close(index);	
// 							top.layer.msg(res.msg,{time:1000});							
// 						}else{
// 							top.layer.msg(res.msg,{time:1000});
// 						}
// 	
// 					}
// 				});	
// 	        });			
// 		}else{
// 			return false;
// 		}
	})	
	
	
	//删除
	$(document).on('click','.PER_DEL',function(){		
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('taskJobsList').data);
		if(flag){
            layer.confirm('确定删除此此任务吗？',{icon:3, title:'提示信息'},function(index){				
				$.ajax({
					url: application.SERVE_URL+"/task/taskJobs/delete", //ajax请求地址
					data:{
						id : table.checkStatus('taskJobsList').data[0].id  
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
	
	
	
	
    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
            table.reload("taskJobsList",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
					jobName: $(".name").val(),
                }
            })
    });

	
	
	//添加编码
	function _addDict(edit){
		publicUtil.gotoEditPage(application.SERVE_URL +'/task/taskJobs/get',edit ==undefined?null:edit.id,"任务管理","taskJobsAdd.html");
	}

})