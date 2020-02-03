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
        ,elem: '#mesGroupList'
        ,url : application.SERVE_URL+'/mes/mesGroup/list',
        cellMinWidth : 95,
        page : true,
        height : "full-160",
        limit : 10,
        id : "mesGroupList",
        cols : [[
			{type:'checkbox'},
            {field: 'name', title: '群组名称'},
            {field: 'description', title: '群组描述'},
            {field: 'createDate', title: '创建时间'},
			{field: 'remark',title: '备注'}
        ]]
    });

	//右键点击事件
	table.on('rowRight(mesGroupList)', function(obj){
		publicUtil.show_menu(obj);
	});
	
	//左键点击事件
	table.on('row(mesGroupList)', function(obj){
		publicUtil.hiddenMenu(obj);
	});
	
	//新增操作
	$(document).on('click','.PER_ADD',function(){
    	_addDict();
    });
	
	//编辑操作
	$(document).on('click','.PER_EDIT',function(){		
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('mesGroupList').data);
		if(flag){			
			_addDict(table.checkStatus('mesGroupList').data[0]);
		}else{
			return false;
		}

    })
	
	//删除
	$(document).on('click','.PER_DEL',function(){		
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('mesGroupList').data);
		if(flag){
            layer.confirm('确定删除此群组吗？',{icon:3, title:'提示信息'},function(index){				
				$.ajax({
					url: application.SERVE_URL+"/mes/mesGroup/delete", //ajax请求地址
					data:{
						id : table.checkStatus('mesGroupList').data[0].id  
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
            table.reload("mesGroupList",{
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
		publicUtil.gotoEditPage(application.SERVE_URL +'/mes/mesGroup/get',edit ==undefined?null:edit.id,"消息群组编辑","mesGroupAdd.html");
	}

})