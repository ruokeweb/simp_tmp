/**
 * @autor syp
 * @content 积分等级页面js
 * @returns
 * @Time 2018-09-14
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
	
    //列表
    var tableIns = table.render({
		even : true 		
        ,elem: '#smlevelList'
        ,url : application.SERVE_URL+'/settings/pointLevel/list',
        cellMinWidth : 95,
        page : true,
        height : "full-160",
        limit : 10,
        id : "smlevelList",
        cols : [[
			{type:'checkbox'},
			{field: 'levelIcon', 
				title: '等级图标',
				templet: function(d) {
					var res = d.levelIcon;
					var htm = "";
					if (res != null && res != "") {
						htm ="<img class='table-tr-td-img' src='"+application.SERVE_URL +application.FILEPATH + res +"' style='width:100px;height:60px;'>"
					}else{
						htm ='<img class="table-tr-td-img" src="../../../../../static/images/def_card.png" style="width:100px;height:60px;">'
					}
					return htm;									
				}
			},
            {field: 'name', title: '等级名称'},
            {field: 'value', title: '等级值'},
            {field: 'code', title: '等级编码'},
			{field: 'startPoint',title: '开始积分'},
            {field: 'endPoint', title: '结束积分'},
			{field: 'remark', title: '备注'},
        ]],
		done: function (res, curr, count) {
			$('th div span').css({'font-weight:': 'bold'});
		}
    });

	//右键点击事件
	table.on('rowRight(smlevelList)', function(obj){
		publicUtil.show_menu(obj);
	});
	
	//左键点击事件
	table.on('row(smlevelList)', function(obj){
		publicUtil.hiddenMenu(obj);
	});
	
	//新增操作
	$(document).on('click','.PER_ADD',function(){
    	_addDict();
    });
	
	//编辑操作
	$(document).on('click','.PER_EDIT',function(){		
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('smlevelList').data);
		if(flag){			
			_addDict(table.checkStatus('smlevelList').data[0]);
		}else{
			return false;
		}

    })
	
	//删除
	$(document).on('click','.PER_DEL',function(){		
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('smlevelList').data);
		if(flag){
            layer.confirm('确定删除此等级吗？',{icon:3, title:'提示信息'},function(index){				
				$.ajax({
					url: application.SERVE_URL+"/settings/pointLevel/delete", //ajax请求地址
					data:{
						id : table.checkStatus('smlevelList').data[0].id  
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
            table.reload("smlevelList",{
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
		publicUtil.gotoEditPage(application.SERVE_URL +'/settings/pointLevel/get',edit ==undefined?null:edit.id,"积分等级管理","pointLevelAdd.html");
	}

})