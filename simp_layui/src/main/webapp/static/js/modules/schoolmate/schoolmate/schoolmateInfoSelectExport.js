layui.config({
    base: "../../../../static/js/"
}).extend({
    "application": "application",
    "publicUtil": "publicUtil"
})

layui.use(['element', 'laytpl', 'layer', 'application', 'publicUtil', 'form'], function () {
        layer = layui.layer,
        _$ = layui.jquery,
        application = layui.application

    _$(".baseType").click(function () {
        layer.confirm('确定导出校友常用信息吗？', {
            icon: 3,
            title: '提示信息'
        }, function(index) {
            var load = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '请勿重复操作....... ',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });
            _$.ajax({
                url: application.SERVE_URL + "/sm/smSchoolmate/exportsmDatas", //ajax请求地址
                contentType: "application/json",
                timeout: 30000000,
                data: JSON.stringify({
                    exportFlag:sessionStorage.getItem("exportFlag"),
                    informationType: "baseType",
                    fuzzySearchFiled: sessionStorage.getItem("fuzzySearchFiled"),
                    smSchoolmate: JSON.parse(sessionStorage.getItem("smSchoolmate"))
                }),
                success: function(res) {
                    if (res.code == application.REQUEST_SUCCESS) {
                        window.location.href = application.SERVE_URL + res.data;
                        layer.close(load);
                        layer.msg("导出完成");
                        setTimeout(function(){
                            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            parent.layer.close(index); //再
                        },500);
                    } else {
                        layer.msg(res.msg);
                    }

                }
            });
        })

    });
    _$(".telephoneType").click(function () {
        layer.confirm('确定导出校友手机信息吗？', {
            icon: 3,
            title: '提示信息'
        }, function(index) {
            var load = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '请勿重复操作....... ',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });
            _$.ajax({
                url: application.SERVE_URL + "/sm/smSchoolmate/exportsmDatas", //ajax请求地址
                contentType: "application/json",
                timeout: 30000000,
                data: JSON.stringify({
                    exportFlag:sessionStorage.getItem("exportFlag"),
                    informationType: "telephoneType",
                    fuzzySearchFiled: sessionStorage.getItem("fuzzySearchFiled"),
                    smSchoolmate: JSON.parse(sessionStorage.getItem("smSchoolmate"))
                }),
                success: function(res) {
                    if (res.code == application.REQUEST_SUCCESS) {
                        window.location.href = application.SERVE_URL + res.data;
                        layer.close(load);
                        layer.msg("导出完成");
                        setTimeout(function(){
                            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            parent.layer.close(index); //再
                        },500);
                    } else {
                        layer.msg(res.msg);
                    }

                }
            });
        })

    });
    _$(".emailType").click(function () {
        layer.confirm('确定导出校友邮箱信息吗？', {
            icon: 3,
            title: '提示信息'
        }, function(index) {
            var load = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '请勿重复操作....... ',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });
            _$.ajax({
                url: application.SERVE_URL + "/sm/smSchoolmate/exportsmDatas", //ajax请求地址
                contentType: "application/json",
                timeout: 30000000,
                data: JSON.stringify({
                    exportFlag:sessionStorage.getItem("exportFlag"),
                    informationType: "emailType",
                    fuzzySearchFiled: sessionStorage.getItem("fuzzySearchFiled"),
                    smSchoolmate: JSON.parse(sessionStorage.getItem("smSchoolmate"))
                }),
                success: function(res) {
                    if (res.code == application.REQUEST_SUCCESS) {
                        window.location.href = application.SERVE_URL + res.data;
                        layer.close(load);
                        layer.msg("导出完成");
                        setTimeout(function(){
                            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            parent.layer.close(index); //再
                        },500);
                    } else {
                        layer.msg(res.msg);
                    }

                }
            });
        })

    });
})
