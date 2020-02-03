layui.config({
    base: "static/js/"
}).extend({
    "application": "application",
    "publicUtil": "publicUtil"
})
layui.use(['element', 'laytpl', 'layer', 'application', 'publicUtil', 'form'], function() {
    var element = layui.element,
        layer = layui.layer,
        $ = layui.jquery,
        application = layui.application,
        publicUtil = layui.publicUtil,
        form = layui.form,
        laytpl = layui.laytpl;

    var thisUser = null;

    var index = layer.load(2, {
        time: 3 * 1000,
        shade: 0.1
    });

    $.ajax({
        url: application.SERVE_URL + "/sm/smSchoolmate/getLikeById", //ajax请求地址
        data: {
            "userId": sessionStorage.getItem("currentAuthUserId")
        },
        success: function(data) {
            layer.close(index);
            //渲染模版
            var getTpl = demo.innerHTML;
            var view = document.getElementById('info');
            laytpl(getTpl).render(data.data, function(html) {
                view.innerHTML = html;
            });

        }
    });

    // 
    $(".no").click(function() {
        layer.open({
            id: 2,
            type: 1,
            title: '驳回原因',
            skin: 'layui-layer-rim',
            area: ['450px', '300px'],
            content: '<textarea id = "resultCon" style="width: 420px;height:150px; margin-left:7px; margin-top:10px;"></textarea>',
            btn: ['确认', '取消'],
            btn1: function(index, layero) {
                if ($("#resultCon").val().length == 0) {
                    $("#resultCon").focus();
                    layer.msg('驳回的内容不能为空', {
                        icon: 5,
                        shift: 6
                    });
                    return;
                }
                //执行驳回流程 
                $.ajax({
                    url: application.SERVE_URL +
                        "/activiti/myactiviti/authTurnDown", //ajax请求地址
                    data: {
                        "userId": sessionStorage.getItem("currentAuthUserId"),
                        "processInstanceId": sessionStorage.getItem(
                            "currentProcessInstanceId"),
                        "resultCon": $("#resultCon").val()
                    },
                    success: function(data) {
                        top.layer.msg(data.msg);
                        layer.close(index);
                        //刷新父页面
                        //刷新父页面
                        var topindex = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.close(topindex);
                        parent.tableIns.reload();
                        sessionStorage.setItem("currentAuthUserId", "");
                        sessionStorage.setItem("currentProcessInstanceId", "");
                    }
                })
            },
            btn2: function(index, layero) {
                layer.close(index);
            }

        })
    });

    //审核通过
    $(".yes").click(function() {
        //执行审核通过流程
        $.ajax({
            url: application.SERVE_URL + "/activiti/myactiviti/authPass", //ajax请求地址
            data: {
                "userId": sessionStorage.getItem("currentAuthUserId"),
                "processInstanceId": sessionStorage.getItem("currentProcessInstanceId"),
            },
            success: function(data) {
                top.layer.msg(data.msg);
                //刷新父页面
                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                parent.layer.close(index);
                parent.tableIns.reload();
                sessionStorage.setItem("currentAuthUserId", "");
                sessionStorage.setItem("currentProcessInstanceId", "");
            }
        })

    })

    $(document).on('click', '.goDetil', function() {
        var currindex = layer.load(2, {
            time: 3 * 1000,
            shade: 0.1
        });
        $("#likeSmId").val($(this).attr("id"));     
        $.ajax({
            url: application.SERVE_URL + "/sm/smSchoolmate/getUserINfoById", //ajax请求地址
            data: {
                "userId": $(this).attr("id")
            },
            success: function(data) {

                layer.close(currindex);
                $("#tip").css('display', 'none');
                $("#merge").css('display', 'block');
                //渲染模版
                var getTpl = likeDetail.innerHTML;
                var view = document.getElementById('detail');
                laytpl(getTpl).render(data.data, function(html) {
                    view.innerHTML = html;
                });

            }
        });
    });

    $(document).on('click', '#merge', function() {
        layer.confirm('该操作不可恢复，是否确认合并此校友数据吗？', {
            btn: ['合并', '返回'] //按钮
        }, function() {
            var currindex = layer.load(2, {
                time: 5 * 1000,
                shade: 0.1,
                title: "合并中"
            });
            $.ajax({
                url: application.SERVE_URL + "/sm/smSchoolmate/mergeSchoolmateInfo", //ajax请求地址
                type: "POST",
                dataType: "json",
                data: {                    
                    "oldUserId": $("#likeSmId").val(),
                    "processInstanceId": sessionStorage.getItem("currentProcessInstanceId"),
                    "newUserId":  sessionStorage.getItem("currentAuthUserId"),
                },
                success: function(data) {
                    if (data.code == application.REQUEST_SUCCESS) {
                        layer.close(currindex);
                        layer.msg('合并完成', {
                            icon: 1
                        });
                        //刷新父页面
                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.close(index);
                        parent.tableIns.reload();
                    } else {
                        layer.close(currindex);
                        layer.msg('合并失败', {
                            icon: 1
                        });
                    }
                },
                error: function(data) {
                    layer.close(currindex);
                    layer.msg('合并失败', {
                        icon: 1
                    });
                    //刷新父页面
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.close(index);
                    parent.tableIns.reload();
                }
            });
        }, function() {
            layer.msg('不合并', {
                icon: 2
            });
        });
    });
});
