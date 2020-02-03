/**
 * @autor zdl
 * @content 捐赠致谢js
 * @returns
 * @Time 2019-03-27
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
    var start;
    var end;
    //执行一个laydate实例
    laydate.render({
        elem: '#start',
        // type: 'datetime',,
		trigger: 'click',
        theme: 'molv'
        //指定元素
        ,
        done: function(value, date, endDate) {
            //得到日期生成的值，如：2017-08-18
            start = value;
        }
    });


    //执行一个laydate实例
    laydate.render({
        elem: '#end' ,//指定元素,,
		trigger: 'click',
        // type: 'datetime',
        theme: 'molv',
        done: function(value, date, endDate) {
            //得到日期生成的值，如：2017-08-18
            end = value;
        }
    });
	//获取权限并加载按钮
	publicUtil.getPerms(application.PERMS_URL,application.HEADER,parent.cur_menu_id,'get','but_per');
	
    //列表
    var tableIns = table.render({
		even : true 		
        ,elem: '#thanksList'
        ,url : application.SERVE_URL+'/don/donThanks/list',
        cellMinWidth : 95,
        page : true,
        height : "full-160",
        limit : 10,
				headers : { 'Authorization' : application.HEADER},	
        id : "thanksList",
        cols : [[
			{type:'checkbox'},
            {field: 'name', title: '姓名'},
            {field: 'information', title: '寄语'},
            {field: 'createDate', title: '时间'},
            {field: 'isshow', title: '是否显示', align:'center', templet:function(d){
                    var currstatus = d.isshow == 'YES' ? "checked": ""
                    return '<input type="checkbox" name="isshow" data-id ="'+d.id+'" lay-filter="isshow" lay-skin="switch" lay-text="是|否" '+currstatus+'>'
            }},
			{field: 'remark', title: '备注'},
        ]],
		done: function (res, curr, count) {
			$('th div span').css({'font-weight:': 'bold'});
			publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'BOOLEAN_TYPE'
			}, 'isshow');
		}
    });

	//右键点击事件
	/*table.on('rowRight(thanksList)', function(obj){
		publicUtil.show_menu(obj);
	});

    //左键点击事件
    table.on('row(thanksList)', function(obj) {
        publicUtil.hiddenMenu(obj);
    });*/

    form.on('switch(isshow)', function(data){
        var this_id  = $(this).attr("data-id");
        var alert_value =this.checked ? 'YES' : 'NO';
        var tipText = '确定不显示？';
        if(data.elem.checked){
            tipText = '确定显示？'
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
                url: application.SERVE_URL+"/don/donThanks/save", //ajax请求地址
                data:{
                    id : this_id,
                    isshow:  alert_value
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

    //删除
    $(document).on('click','.PER_DEL',function(){
        var flag = publicUtil.jurgeSelectRows(table.checkStatus('thanksList').data);
        if(flag){
            layer.confirm('确定删除此条寄语？',{icon:3, title:'提示信息'},function(index){
                $.ajax({
                    url: application.SERVE_URL+"/don/donThanks/delete", //ajax请求地址
                    data:{
                        id : table.checkStatus('thanksList').data[0].id
                    },
                    success: function (res) {
                        if(res.code==application.REQUEST_SUCCESS){
                            tableIns.reload();
                            // location.reload();
                            top.layer.close(index);
                            top.layer.msg(res.msg,{time: 1000});
                        }else{
                            top.layer.msg(res.msg,{time: 1000});
                        }

                    }
                })
            });
        }else{
            return false;
        }
    })

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
            table.reload("thanksList",{
                page: {
                    curr: 1 //重新从第 1 页开始

                },
                where: {
                	name: $(".searchVal").val(),
                    queryBeginDate: start,
                    queryEndDate: end
                }
            })
    });

})