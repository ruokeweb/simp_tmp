/**
 * @autor syp
 * @content 消息群组页面js
 * @returns
 * @Time 2018-11-13
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
	
	//获取权限并加载按钮
	publicUtil.getPerms(application.PERMS_URL,application.HEADER,parent.cur_menu_id,'get','but_per');
	
    //列表
    var tableIns = table.render({
		even : true 		
        ,elem: '#mesSchedulerList'
        ,url : application.SERVE_URL+'/mes/mesScheduler/list',
        cellMinWidth : 95,
        page : true,
        height : "full-160",
        limit : 10,
        id : "mesSchedulerList",
        cols : [[
			{type:'checkbox'},
            {field: 'name', title: '名称'},
            {field: 'date', title: '日期'},
            {field: 'sendType', title: '发送类型'},
			{field: 'mesTemplate',title: '模板'}
        ]],
		done: function(res, curr, count) { //res 接口返回的信息,
			publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'MES_SENDTYPE'
			}, 'sendType');
			$.ajax({
				url: application.SERVE_URL+"/mes/mesTemplate/loadAllListBy", //ajax请求地址
				success: function (res) {
					if(res.code==application.REQUEST_SUCCESS){
						var templates = res.data;
						$("[data-field = 'mesTemplate']").children().each(function(){
							for(var i =0; i< templates.length;i++){
								if($(this).text() == templates[i].id){
									$(this).text (publicUtil.htmlDecode(templates[i].name));
								}
							}
						})	
					}else{
						
					}
				}
			}); 					
		}
    });

	//右键点击事件
	table.on('rowRight(mesSchedulerList)', function(obj){
		publicUtil.show_menu(obj);
	});
	
	//左键点击事件
	table.on('row(mesSchedulerList)', function(obj){
		publicUtil.hiddenMenu(obj);
	});
	
	//新增操作
	$(document).on('click','.PER_ADD',function(){
    	_addDict();
    });
	
	//编辑操作
	$(document).on('click','.PER_EDIT',function(){		
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('mesSchedulerList').data);
		if(flag){			
			_addDict(table.checkStatus('mesSchedulerList').data[0]);
		}else{
			return false;
		}

    })
	
	//删除
	$(document).on('click','.PER_DEL',function(){		
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('mesSchedulerList').data);
		if(flag){
            layer.confirm('确定删除此定时通知消息吗？',{icon:3, title:'提示信息'},function(index){				
				$.ajax({
					url: application.SERVE_URL+"/mes/mesScheduler/delete", //ajax请求地址
					data:{
						id : table.checkStatus('mesSchedulerList').data[0].id  
					},										
					success: function (res) {
						if(res.code==application.REQUEST_SUCCESS){
							tableIns.reload();
							// location.reload();
							top.layer.close(index);	
							top.layer.msg(res.msg);							
						}else{
							top.layer.msg(res.msg);
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
            table.reload("mesSchedulerList",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                	name: $(".searchVal").val(),
                }
            })
    });

	
	
	//添加编码
	function _addDict(edit){
		publicUtil.gotoEditPage(application.SERVE_URL +'/mes/mesScheduler/get',edit ==undefined?null:edit.id,"消息群组编辑","mesSchedulerAdd.html");
	}

})