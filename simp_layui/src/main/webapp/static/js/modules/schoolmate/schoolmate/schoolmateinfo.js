layui.config({
    base: "../../../../static/js/"
}).extend({
    "application": "application",
    "publicUtil": "publicUtil"
})
layui.use(['element', 'laytpl', 'layer', 'application', 'publicUtil', 'form'], function () {
    var element = layui.element,
        layer = layui.layer,
        $ = layui.jquery,
        application = layui.application,
        publicUtil = layui.publicUtil,
        form = layui.form,
        laytpl = layui.laytpl;

    var index = layer.load(2);
    $.ajax({
        url: application.SERVE_URL + '/sm/smSchoolmate/getUserINfoById',
        data: {"id": sessionStorage.getItem("userId")}, //ajax请求地址
        success: function (data) {

            //渲染模版
            var getTpl = likeDetail.innerHTML;
            var view = document.getElementById('info');
            laytpl(getTpl).render(data.data, function (html) {
                view.innerHTML = html;
            });
            //图片设置src值
            if(data.data.truePhoto != null && data.data.truePhoto != "" && data.data.truePhoto != undefined && data.data.truePhoto != "undefined"){
                var  imageId=application.SERVE_URL + application.FILEPATH + data.data.truePhoto;
                document.getElementById("photoPath").src = imageId;
                //$("#photoPath").attr('src',imageId);
            }else{
                document.getElementById("photoPath").src = "/default.png";
            }
            layer.close(index);
        }
    });
    //导出word
    $(".yes").click(function () {
        layer.confirm('确定导出校友信息吗？', {
            icon: 3,
            title: '提示信息'
        }, function (index) {
            var load = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '请勿重复操作....... ',
                success: function (layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });
            //执行导出流程
            $.ajax({
                url: application.SERVE_URL + '/sm/smSchoolmate/exportsWordDatas', //ajax请求地址
                timeout: 30000000,
                data: {
                    id : sessionStorage.getItem("userId")
                }, //ajax请求地址
                success: function (res) {
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
    })
})