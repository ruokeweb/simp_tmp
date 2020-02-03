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
    var status;
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

    initStatus( );
    //加载状态
    function initStatus() {
        //从缓存中获取字典类型
        var flag =true;
        var dict=sessionStorage.getItem("dictCache");
        if(dict==null){
        }
        //抓取相关字段属性
        var data=JSON.parse(dict)['SELFORG_STATUS'];
        $("#status").empty();
        if(flag){
            $("#status").append('<option  value="" >'+"请选择"+' </option>');
        }
        for(var i =0;i<data.length;i++){
            // if(data[i].value!='AUDITING')
            $("#status").append('<option  value="'+data[i].value+'" >'+data[i].label+' </option>');//往下拉菜单里添加元素
        }
        form.render();//菜单渲染 把内容加载进去





    }
	//获取权限并加载按钮
	publicUtil.getPerms(application.PERMS_URL,application.HEADER,parent.cur_menu_id,'get','but_per');
	
    //列表
    var tableIns = table.render({
		even : true 		
        ,elem: '#thanksList'
        ,url : application.SERVE_URL+'/sm/smWish/list',
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
            {field: 'isshow', title: '审核状态'}
        ]],
		done: function (res, curr, count) {
			$('th div span').css({'font-weight:': 'bold'});
			publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'SELFORG_STATUS'
			}, 'isshow');
		}
    });


    //右键点击事件
    table.on('rowRight(thanksList)', function(obj) {
        publicUtil.show_menu(obj);
    });

    //左键点击事件
    table.on('row(thanksList)', function(obj) {
        publicUtil.hiddenMenu(obj);
    });


    //删除
    $(document).on('click','.PER_DEL',function(){
        var flag = publicUtil.jurgeSelectRows(table.checkStatus('thanksList').data);
        if(flag){
            layer.confirm('确定删除此条祝福语？',{icon:3, title:'提示信息'},function(index){
                $.ajax({
                    url: application.SERVE_URL+"/sm/smWish/delete", //ajax请求地址
                    data:{
                        id : table.checkStatus('thanksList').data[0].id,
                    },
                    success: function (res) {
                        if(res.code==application.REQUEST_SUCCESS){
                            table.reload("thanksList",{
                                page: {
                                    curr: 1 //重新从第 1 页开始

                                },
                                where: {
                                    isshow: $(".searchVal").val(),
                                    queryBeginDate: start,
                                    queryEndDate: end
                                }
                            });
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
    });
    //审核
    $(document).on('click', '.PER_EDIT', function () {
        var flag = publicUtil.jurgeSelectRows(table.checkStatus('thanksList').data);
        if (flag) {

            layer.confirm('确定审核此条祝福语？', {
                btn: ['通过', '驳回', '取消'] //可以无限个按钮
                ,btn3: function(index, layero){
                }
            }, function(index, layero){
                $.ajax({
                    url: application.SERVE_URL + "/sm/smWish/save", //ajax请求地址
                    data: {
                        id: table.checkStatus('thanksList').data[0].id,
                        isshow: "SUCCESS",
                    },
                    success: function (data) {
                        if (data.code==application.REQUEST_SUCCESS) {
                            table.reload("thanksList",{
                                page: {
                                    curr: 1 //重新从第 1 页开始

                                },
                                where: {
                                    isshow: $(".searchVal").val(),
                                    queryBeginDate: start,
                                    queryEndDate: end
                                }
                            });
                            layer.close(index);
                            top.layer.msg("审核成功",{time: 1000});
                        }
                    }
                });
            }, function(index){
                $.ajax({
                    url: application.SERVE_URL + "/sm/smWish/save", //ajax请求地址
                    data: {
                        id: table.checkStatus('thanksList').data[0].id,
                        isshow: "LOSE",
                    },
                    success: function (data) {
                        if (data.code==application.REQUEST_SUCCESS) {
                            table.reload("thanksList",{
                                page: {
                                    curr: 1 //重新从第 1 页开始

                                },
                                where: {
                                    isshow: $(".searchVal").val(),
                                    queryBeginDate: start,
                                    queryEndDate: end
                                }
                            });
                            layer.close(index);
                            top.layer.msg("审核成功",{time: 1000});
                        }
                    }
                });
            });
        } else {
            return false;
        }
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){

            table.reload("thanksList",{
                page: {
                    curr: 1 //重新从第 1 页开始

                },
                where: {
                    isshow: $(".searchVal").val(),
                    queryBeginDate: start,
                    queryEndDate: end
                }
            })
    });

})