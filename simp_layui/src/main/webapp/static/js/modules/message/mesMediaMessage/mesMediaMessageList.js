/**
 * @autor syp
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
        ,elem: '#mesMediaMessageList'
        ,url : application.SERVE_URL+'/mes/mediaMessage/list',
        cellMinWidth : 95,
        page : true,
        height : "full-160",
        limit : 10,
        id : "mesMediaMessageList",
        cols : [[
			{type:'checkbox'},
            {field: 'title', title: '标题'},
            {field: 'delDate', title: '过期时间'},
			{field: 'status',title: '状态'}
        ]],
		done: function(res, curr, count) { //res 接口返回的信息,			
            publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'MES_STATUS'},'status');
		}
    });

	//右键点击事件
	table.on('rowRight(mesMediaMessageList)', function(obj){
		publicUtil.show_menu(obj);
	});
	
	//左键点击事件
	table.on('row(mesMediaMessageList)', function(obj){
		publicUtil.hiddenMenu(obj);
	});
	
	//新增操作
	$(document).on('click','.PER_ADD',function(){
    	_addDict();
    });
	
	//编辑操作
	$(document).on('click','.PER_EDIT',function(){		
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('mesMediaMessageList').data);
		if(flag){			
			_addDict(table.checkStatus('mesMediaMessageList').data[0]);
		}else{
			return false;
		}

    })
	
	//删除
	$(document).on('click','.PER_DEL',function(){		
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('mesMediaMessageList').data);
		if(flag){
            layer.confirm('确定删除此多媒体通知吗？',{icon:3, title:'提示信息'},function(index){				
				$.ajax({
					url: application.SERVE_URL+"/mes/mediaMessage/delete", //ajax请求地址
					data:{
						id : table.checkStatus('mesMediaMessageList').data[0].id  
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
            table.reload("mesMediaMessageList",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                	title: $(".searchVal").val(),
                }
            })
    });

	
	
	//添加编码
	function _addDict(edit){
		publicUtil.gotoEditPage(application.SERVE_URL +'/mes/mediaMessage/get',edit ==undefined?null:edit.id,"每日多媒体通知编辑","mesMediaMessageAdd.html");
	}

})