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
        ,elem: '#smpointfuncList'
        ,url : application.SERVE_URL+'/settings/pointFunction/list',
        cellMinWidth : 95,
        page : true,
        height : "full-160",
        limit : 10,
				headers : { 'Authorization' : application.HEADER},	
        id : "smpointfuncList",
        cols : [[
						{type:'checkbox'},
            {field: 'name', title: '场景名称'},
            {field: 'point', title: '场景积分'},
            {field: 'code', title: '场景编号'},
			/*{field: 'isuse',title: '是否激活'}*/

            {field: 'isuse', title: '是否激活', align:'center', templet:function(d){
                    var currstatus = d.isuse == 'YES' ? "checked": ""
                    return '<input type="checkbox" name="isuse" data-id ="'+d.id+'" lay-filter="isuse" lay-skin="switch" lay-text="是|否" '+currstatus+'>'
            }},
			{field: 'remark', title: '备注'},
        ]],
		done: function (res, curr, count) {
			$('th div span').css({'font-weight:': 'bold'});
			publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'BOOLEAN_TYPE'
			}, 'isuse');
		}
    });

	//右键点击事件
	table.on('rowRight(smpointfuncList)', function(obj){
		publicUtil.show_menu(obj);
	});
	/*
	//左键点击事件
	table.on('row(smpointfuncList)', function(obj){
		publicUtil.hiddenMenu(obj);
	});	*/
	
	//新增操作
	$(document).on('click','.PER_ADD',function(){
    	_addDict();
    });
    form.on('switch(isuse)', function(data){
        var this_id  = $(this).attr("data-id");
        var alert_value =this.checked ? 'YES' : 'NO';
        var tipText = '确定停止？';
        if(data.elem.checked){
            tipText = '确定激活？'
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
                url: application.SERVE_URL+"/settings/pointFunction/save", //ajax请求地址
                data:{
                    id : this_id,
                    isuse:  alert_value
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
    //编辑操作
	$(document).on('click','.PER_EDIT',function(){		
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('smpointfuncList').data);
		if(flag){			
			_addDict(table.checkStatus('smpointfuncList').data[0]);
		}else{
			return false;
		}

    })
	
	//删除
	$(document).on('click','.PER_DEL',function(){		
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('smpointfuncList').data);
		if(flag){
            layer.confirm('确定删除此积分场景吗？',{icon:3, title:'提示信息'},function(index){				
				$.ajax({
					url: application.SERVE_URL+"/settings/pointFunction/delete", //ajax请求地址
					data:{
						id : table.checkStatus('smpointfuncList').data[0].id  
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
            table.reload("smpointfuncList",{
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
		publicUtil.gotoEditPage(application.SERVE_URL +'/settings/pointFunction/get',edit ==undefined?null:edit.id,"积分场景管理","pointfuncAdd.html");
	}

})