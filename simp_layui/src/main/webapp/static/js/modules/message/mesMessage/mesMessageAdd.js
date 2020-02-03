/**
 * @autor syp
 * @content 消息群组页面js
 * @returns
 * @Time 2018-09-14
 */
layui.config({
    base: "../../../../static/js/"
}).extend({
    "publicUtil": "publicUtil"
})
var contentEditor = null;
var sendTypeOnClick = null;
var tempData=null;
layui.use(['form', 'layer', 'application', 'laydate', 'upload', 'publicUtil'], function() {
    var form = layui.form,
        application = layui.application,
        publicUtil = layui.publicUtil,
        upload = layui.upload,
        layer = parent.layer,
        laydate = layui.laydate,
        $ = layui.jquery;

    //执行一个laydate实例
    laydate.render({
        elem: '#delDate',
        theme: 'molv',
        max: fun_date(7),
        trigger: 'click'
    });

    // 初始化ckEdit
    $(function() {
        ClassicEditor.create(document.querySelector("#content"), {
                extraPlugins: [MyCustomUploadAdapterPlugin]
            }).then(editor => {
                contentEditor = editor;
                if (parent.editFormData.content != null) {
                    contentEditor.setData(publicUtil.htmlDecode(parent.editFormData.content));
                }
            })
            .catch(error => {
                console.error(error);
            });
    })

    //表单赋值
    form.verify({
        judgeNull: function(value) {
            var message = "";
            //判断是否是编辑
            if (('NEWS' == type)) {
                if (($("#receiveGroupId").val() == null && $("#receiveUserId").val() == null) ||
                    ($("#receiveGroupId").val() == null && $("#receiveUserId").val() == "") ||
                    ($("#receiveGroupId").val() == "" && $("#receiveUserId").val() == null) ||
                    ($("#receiveGroupId").val() == "" && $("#receiveUserId").val() == "")) {
                    message = "接受人或组为必填"
                    return message;
                }
            }
            return;
        }
    });

    //获取几天前后的日期
    function fun_date(days) {
        var date1 = new Date(),
            time1 = date1.getFullYear() + "-" + (date1.getMonth() + 1) + "-" + date1.getDate(); //time1表示当前时间
        var date2 = new Date(date1);
        date2.setDate(date1.getDate() + days);
        var time2 = date2.getFullYear() + "-" + (date2.getMonth() + 1) + "-" + date2.getDate();
        return time2;
    }

    form.on('radio(receiveObj)', function(data) {
        var radioVal = data.value;
        if (radioVal == 'group') {
            $("#group").css('display', 'block');
            $("#person").css('display', 'none');
            $("#receiveUserId").val(null);
            $("#receiveUserName").val("");
        } else {
            $("#person").css('display', 'block');
            $("#group").css('display', 'none');
            $("#receiveGroupId").val(null);
        }
    });

    form.on('select(type)', function(data) {
        if ($("#type").val() == 'NEWS') {
            $("#receiveObj").css('display', 'block');
            $("#person").css('display', 'none');
            $("#group").css('display', 'block');
        } else {
            $("#receiveObj").css('display', 'none');
            $("#group").css('display', 'none');
            $("#person").css('display', 'none');
        }
    });
    form.on('select(sendType)', function(data) {
        if ($("#sendType").val() == 'MES_SENDTYPE_CARD'&&sendTypeOnClick!='MES_SENDTYPE_CARD') {
            sendTypeOnClick = 'MES_SENDTYPE_CARD' ;
            $("#mesTemplateDiv").css('display', 'none');
            $("#delDateDiv").css('display', 'block');
            $("#mesTemplateDiv").val("");
            $("div[role='textbox']").removeAttr("style");
        } else {
            sendTypeOnClick = $("#sendType").val() ;
            $("#mesTemplateDiv").css('display', 'block');
            $("#delDateDiv").css('display', 'none');
            getMesTemplate("mesTemplate",$("#mesTemplate").val(),contentEditor.getData());
        }
    });

    function getMesTemplate(selectid,id,content){
        $.ajax({
            url: application.SERVE_URL + "/mes/mesTemplate/get", //ajax请求地址
            data:{
                id:id
            },
            success: function(res) {
                if (res.code == application.REQUEST_SUCCESS) {
                    var template = res.data;
                    // content = contentEditor.getData();
                    var imgUrl = template.backgroupImg;
                    var repeat = "no-repeat";
                    // var height = $(publicUtil.htmlDecode(selectData.content)).css("height");
                    $("div[role='textbox']").css('background-image','url('+application.SERVE_URL + application.FILEPATH +imgUrl+')');
                    $("div[role='textbox']").css('background-repeat',repeat);
                    var img_url =application.SERVE_URL + application.FILEPATH +imgUrl;
                    // 创建对象
                    var img = new Image();
                    // 改变图片的src
                    img.src = img_url;

                    //加载完成执行
                    img.onload =function(){
                        $("div[role='textbox']").css("min-height",img.height+"px");
                        // $("textarea").next().css("min-width",img.width+"px");
                        // $("textarea").next().css("man-width","100%");
                        form.render();
                    };
                    //菜单渲染 把内容加载进去
                } else {
                    layer.msg(res.msg);
                }
            }
        });
    }

    form.on('select(mesTemplate)', function(data) {
        var content = $("#mesTemplate").find("option:selected").attr("layui-data");
        var val = $("#mesTemplate").find("option:selected").val();
        contentEditor.setData(content);
        // tempData={"mesTemplate":val ,"sendType":sendTypeOnClick ,"content":content};
        /**
         * 新增开始
         * @type {jQuery}
         */

        var imgUrl =$("#mesTemplate").find("option:selected").attr("layui-imgUrl");

        $("div[role='textbox']").css('background-image','url('+application.SERVE_URL + application.FILEPATH +imgUrl+')');
        $("div[role='textbox']").css('background-repeat','no-repeat');
        var img_url = application.SERVE_URL + application.FILEPATH +imgUrl;
        // 创建对象
        var img = new Image();

        // 改变图片的src
        img.src = img_url;

        //加载完成执行
        img.onload =function(){
            $("div[role='textbox']").css("min-height",img.height+"px");
            // $("textarea").next().css("min-width",img.width+"px");
            // $("textarea").next().css("man-width","100%");
        }
        /**
         * 新增结束
         */
    });

    $(function() {
        selectMesGroupSelect(null);
    })

    //消息群组下拉菜单并进行回填
    function selectMesGroupSelect(selectValue) {
        $.ajax({
            url: application.SERVE_URL + "/mes/mesGroup/loadAllListBy", //ajax请求地址
            success: function(res) {
                if (res.code == application.REQUEST_SUCCESS) {
                    //抓取相关字段属性
                    var data = res.data;
                    $("#receiveGroupId").empty();
                    for (var i = 0; i < data.length; i++) {
                        $("#receiveGroupId").append('<option  value="' + data[i].id + '">' + data[i]
                            .name + '</option>'); //往下拉菜单里添加元素
                    }
                    $('#receiveGroupId').val(selectValue);
                    form.render('select'); //菜单渲染 把内容加载进去
                } else {
                    layer.msg(res.msg);
                }
            }
        });
    }

    function formEdit(FormDatas) {
        if (FormDatas != '') {
            var data = FormDatas;
            $(".id").val(publicUtil.htmlDecode(data.id));
            $(".title").val(publicUtil.htmlDecode(data.title));
            $("#delDate").val(publicUtil.htmlDecode(data.delDate));
            $("#content").val(publicUtil.htmlDecode(data.content));
            $(".remark").val(publicUtil.htmlDecode(data.remark));
            publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                'typeCode': 'MES_TYPE'
            }, "type", publicUtil.htmlDecode(data.type));
            var dict=sessionStorage.getItem("dictCache");
            //回显发送方式
            var dataOld=JSON.parse(dict)['MES_SENDTYPE'];
            $("#sendType").empty();
            for(var i =0;i<dataOld.length;i++){
                $("#sendType").append('<option  value="'+dataOld[i].value+'">'+dataOld[i].label+'</option>');//往下拉菜单里添加元素
            }
            if(data.sendType){
                $('#sendType').val(data.sendType);
            }
            form.render('select');
            sendTypeOnClick = data.sendType;

            $(".receiveUserId").val(publicUtil.htmlDecode(data.receiveUserId));
            domByType(data.type, publicUtil.htmlDecode(data.receiveUserName), publicUtil.htmlDecode(data.receiveGroupId),
                data.sendType)
            selectTemplate("mesTemplate", data);

        } else {
            publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                'typeCode': 'MES_SENDTYPE'
            }, "sendType", false);
            var dict=sessionStorage.getItem("dictCache");
            //回显发送方式
            var data=JSON.parse(dict)['MES_SENDTYPE'];
            sendTypeOnClick = data[0].value;
            publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                'typeCode': 'MES_TYPE'
            }, "type", false);
            selectTemplate("mesTemplate");
            return false;
        }
    }
    //表单回填
    function domByType(type, receiveUserName, receiveGroupId, sendType) {
        //消息
        if ('NEWS' == type) {
            $("#receiveObj").css('display', 'block');
            if ('MES_SENDTYPE_CARD' == sendType) {
                $("#delDateDiv").css('display', 'block');
                $("#mesTemplateDiv").css('display', 'none');
            }
            if (receiveUserName != null && receiveUserName != '') {
                $("input[name='receiveObj'][value = 'person']").attr('checked', 'true');
                $("#group").css('display', 'none');
                $("#person").css('display', 'block');
                $("#receiveUserName").val(publicUtil.htmlDecode(receiveUserName));
            }
            if (receiveGroupId != null && receiveGroupId != '') {
                $("#group").css('display', 'block');
                $("#person").css('display', 'none');
                $("input[name='receiveObj'][value = 'group']").attr('checked', 'true');
                selectMesGroupSelect(publicUtil.htmlDecode(receiveGroupId))
            }
        } else {
            $("#receiveObj").css('display', 'none');
            $("#group").css('display', 'none');
            $("#person").css('display', 'none');
        }
        form.render();
    }

    /**
     * 表单回显
     */
    formEdit(parent.editFormData);

    form.on("submit(addMesGroup)", function() {
        if (contentEditor.getData() == null || contentEditor.getData().length == 0) {
            layer.msg("发送内容不能为空", {
                icon: 5,
                shift: 6
            });
            return false;
        } else {
            content = "";
            if ($("#sendType").val() == "MES_SENDTYPE_SMS") {
                content = getText(contentEditor.getData());
            }else if($("#sendType").val() == "MES_SENDTYPE_EMAIL"){
                // content = contentEditor.getData();
                content =contentEditor.getData();
                var rep=$("div[role='textbox']").css("background-repeat");
                var img = $("div[role='textbox']").css("background-image");
                var width= $("div[role='textbox']").css("width");
                var height= $("div[role='textbox']").css("height");
                content ="<div style='background-size:cover \;background-image: "+img+"\;background-repeat:"+rep+"\;width:"+width+"\;height: "+height+"'>"+content+"</div>";
            }else{
                content = contentEditor.getData();
            }
            if($("#sendType").val() == "MES_SENDTYPE_CARD"){
                sendsmCardMesReceives();
                return false;
            }else {
                layer.confirm('确认保存此消息吗？', {
                    icon: 3,
                    title: '提示信息'
                }, function(indexshadow) {
                    //弹出loading
                    var index = top.layer.msg('数据提交中，请稍候', {
                        icon: 16,
                        time: false,
                        shade: 0.8
                    });
                    $.ajax({
                        url: application.SERVE_URL + "/mes/mesMessage/save", //ajax请求地址
                        data: {
                            id: $(".id").val() == null || $(".id").val() == "" ? null :
                                $(".id").val(),
                            title: $(".title").val(),
                            type: $("#type").val(),
                            delDate: $("#delDate").val(),
                            mesTemplate: $("#mesTemplate").val(),
                            sendType: $("#sendType").val(),
                            // sort : $(".sort").val(),
                            receiveGroupId: $("#receiveGroupId").val() == null || $(
                                "#receiveGroupId").val() == "" ? "" : $(
                                "#receiveGroupId").val(),
                            receiveUserId: $("#receiveUserId").val() == null || $(
                                    "#receiveUserId").val() == "" ? "" : $(
                                    "#receiveUserId")
                                .val(),
                            content: content,
                            remark: $(".remark").val()
                        },
                        success: function(res) {
                            top.layer.close(index);
                            top.layer.close(indexshadow);
                            if (res.code == application.REQUEST_SUCCESS) {
                                $(".id").val(res.data.id);
                                // top.layer.msg(res.msg);
                                layer.open({
                                    content: "即将发送" + res.data.preSendCount +
                                        "条消息到校友<br/>" +
                                        "<p style='color:red;'>提示：操作不可中断！</p> <br/>",
                                    btn: ['导出列表', '确认发送', '退出'],
                                    yes: function(index, layero) {
                                        var load = layer.load(2, { //icon支持传入0-2
                                            shade: [0.5, 'gray'], //0.5透明度的灰色背景
                                            content: '导出中，请勿重复操作....... ',
                                            success: function(
                                                layero) {
                                                layero.find(
                                                    '.layui-layer-content'
                                                ).css({
                                                    'padding-top': '39px',
                                                    'width': '300px'
                                                });
                                            }
                                        });
                                        $.ajax({
                                            url: application.SERVE_URL +
                                                "/mes/mesMessage/exportsendusers", //ajax请求地址
                                            timeout: 30000000,
                                            data: {
                                                id: $(".id").val()
                                            },
                                            success: function(
                                                res) {
                                                if (res.code == application.REQUEST_SUCCESS
                                                ) {
                                                    window.location
                                                        .href =
                                                        application
                                                        .SERVE_URL +
                                                        res
                                                        .data;
                                                    layer.close(
                                                        load
                                                    );
                                                    // layer.msg("导出完成");
                                                } else {
                                                    layer.close(
                                                        load
                                                    );
                                                    // layer.msg(res.msg);
                                                }

                                            }
                                        });
                                    },
                                    btn2: function(index, layero) {
                                        $.ajax({
                                            url: application.SERVE_URL +
                                                "/mes/mesMessage/sendMes", //ajax请求地址
                                            data: {
                                                id: $(".id").val()
                                            },
                                            success: function(
                                                res) {
                                                if (res.code ==
                                                    application
                                                    .REQUEST_SUCCESS
                                                ) {
                                                    layer.msg(
                                                        "发送中"
                                                    );
                                                    layer.closeAll(
                                                        "iframe"
                                                    );
                                                    //刷新父页面
                                                    parent.location
                                                        .reload();
                                                } else {
                                                    layer.msg(
                                                        res
                                                        .msg
                                                    );
                                                }

                                            }
                                        });
                                    },
                                    cancel: function() {
                                        //关闭
                                        layer.closeAll("iframe");
                                        parent.location.reload();
                                    }
                                });


                                // layer.closeAll("iframe");
                                //刷新父页面
                                // parent.location.reload();
                            } else {
                                layer.msg(res.msg);
                            }
                        }
                    });
                });
            }
        }
        return false;
    })

    function sendsmCardMesReceives() {
        layer.confirm('确认保存此消息吗？', {
            icon: 3,
            title: '提示信息'
        }, function(indexshadow) {
            //弹出loading
            var index = top.layer.msg('数据提交中，请稍候', {
                icon: 16,
                time: false,
                shade: 0.8
            });
            $.ajax({
                url: application.SERVE_URL + "/mes/mesMessage/sendSmCardMesReceive", //ajax请求地址
                data: {
                    id: $(".id").val() == null || $(".id").val() == "" ? null : $(
                        ".id").val(),
                    title: $(".title").val(),
                    type: $("#type").val(),
                    delDate: $("#delDate").val(),
                    mesTemplate: $("#mesTemplate").val(),
                    sendType: $("#sendType").val(),
                    // sort : $(".sort").val(),
                    receiveGroupId: $("#receiveGroupId").val() == null || $(
                        "#receiveGroupId").val() == "" ? "" : $(
                        "#receiveGroupId").val(),
                    receiveUserId: $("#receiveUserId").val() == null || $(
                            "#receiveUserId").val() == "" ? "" : $("#receiveUserId")
                        .val(),
                    content: content,
                    remark: $(".remark").val()
                },
                success: function(res) {
                    top.layer.close(index);
                    top.layer.close(indexshadow);
                    if (res.code == application.REQUEST_SUCCESS) {
                        $(".id").val(res.data.id);
                        // top.layer.msg(res.msg);
                        layer.open({
                            content: "即将发送" + res.data.preSendCount + "条消息到校友卡<br/>" +
                                "<p style='color:red;'>提示：操作不可中断！</p> <br/>",
                            btn: ['导出列表', '确认发送', '退出'],
                            yes: function(index, layero) {
                                var load = layer.load(2, { //icon支持传入0-2
                                    shade: [0.5, 'gray'], //0.5透明度的灰色背景
                                    content: '导出中，请勿重复操作....... ',
                                    success: function(layero) {
                                        layero.find(
                                            '.layui-layer-content'
                                        ).css({
                                            'padding-top': '39px',
                                            'width': '300px'
                                        });
                                    }
                                });
                                $.ajax({
                                    url: application.SERVE_URL +
                                        "/mes/mesMessage/exportsendusers", //ajax请求地址
                                    timeout: 30000000,
                                    data: {
                                        id: $(".id").val() == null || $(".id").val() == "" ? null :
                                            $(".id").val(),
                                        title: $(".title").val(),
                                        type: $("#type").val(),
                                        delDate: $("#delDate").val(),
                                        mesTemplate: $("#mesTemplate").val(),
                                        sendType: $("#sendType").val(),
                                        // sort : $(".sort").val(),
                                        receiveGroupId: $("#receiveGroupId").val() == null || $(
                                            "#receiveGroupId").val() == "" ? "" : $(
                                            "#receiveGroupId").val(),
                                        receiveUserId: $("#receiveUserId").val() == null || $(
                                                "#receiveUserId").val() == "" ? "" : $(
                                                "#receiveUserId")
                                            .val(),
                                        content: content,
                                        remark: $(".remark").val()
                                    },
                                    success: function(res) {
                                        if (res.code == application
                                            .REQUEST_SUCCESS) {
                                            window.location.href =
                                                application.SERVE_URL +
                                                res.data;
                                            layer.close(load);
                                            // layer.msg("导出完成");
                                        } else {
                                            layer.close(load);
                                            // layer.msg(res.msg);
                                        }

                                    }
                                });
                            },
                            btn2: function(index, layero) {
                                $.ajax({
                                    url: application.SERVE_URL +
                                        "/mes/mesMessage/save", //ajax请求地址
                                    data: {
                                        id: $(".id").val() == null || $(".id").val() == "" ? null :
                                            $(".id").val(),
                                        title: $(".title").val(),
                                        type: $("#type").val(),
                                        delDate: $("#delDate").val(),
                                        mesTemplate: $("#mesTemplate").val(),
                                        sendType: $("#sendType").val(),
                                        receiveGroupId: $("#receiveGroupId").val() == null || $(
                                            "#receiveGroupId").val() == "" ? "" : $(
                                            "#receiveGroupId").val(),
                                        receiveUserId: $("#receiveUserId").val() == null || $(
                                                "#receiveUserId").val() == "" ? "" : $(
                                                "#receiveUserId")
                                            .val(),
                                        content: content,
                                        remark: $(".remark").val()
                                    },
                                    success: function(res) {
                                        if (res.code == application
                                            .REQUEST_SUCCESS) {
                                            layer.msg("发送中");
                                            layer.closeAll("iframe");
                                            //刷新父页面
                                            parent.location.reload();
                                        } else {
                                            layer.msg(res.msg);
                                        }

                                    }
                                });
                            },
                            cancel: function() {
                                //关闭
                                layer.closeAll("iframe");
                                parent.location.reload();
                            }
                        });


                        // layer.closeAll("iframe");
                        //刷新父页面
                        // parent.location.reload();
                    } else {
                        layer.msg(res.msg);
                    }
                }
            });
        });
        return false;
    }




$("#close").click(function() {
    layer.closeAll("iframe");
    //刷新父页面
    parent.location.reload();
})

function selectUser() {
    var index = layui.layer.open({
        type: 2,
        title: '校友选择',
        shadeClose: true,
        shade: 0.8,
        area: ['90%', '90%'],
        content: 'userselect.html',
        success: function(layero, index) {
            //
            setTimeout(function() {
                layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            }, 500)
        },
    })
}

$(".receiveUserName").click(function() {
    selectUser();
})
/* 选择模板 */
function selectTemplate(selectid, selectData, flag) {
    $.ajax({
        url: application.SERVE_URL + "/mes/mesTemplate/loadAllListBy", //ajax请求地址
        success: function(res) {
            if (res.code == application.REQUEST_SUCCESS) {
                var templates = res.data;
                $("#" + selectid).empty();
                for (var i = 0; i < templates.length; i++) {
                    $("#" + selectid).append('<option  value="' + templates[i].id +
                        '"  layui-data = "' + templates[i].content + '"  layui-imgUrl = "'+templates[i].backgroupImg+'">' + templates[i].name +
                        '</option>'); //往下拉菜单里添加元素
                }
                if (selectData) {
                    $('#' + selectid).val(selectData.mesTemplate);
                    if(selectData.sendType=="MES_SENDTYPE_EMAIL"){
                        var imgUrl = $(publicUtil.htmlDecode(selectData.content)).css("backgroundImage");
                        var size = $(publicUtil.htmlDecode(selectData.content)).css("backgroundSize");
                        var repeat = $(publicUtil.htmlDecode(selectData.content)).css("backgroundRepeat");
                        // var height = $(publicUtil.htmlDecode(selectData.content)).css("height");
                        $("div[role='textbox']").css('background-image',imgUrl);
                        $("div[role='textbox']").css('background-repeat',repeat);
                        $("div[role='textbox']").css('background-size',size);
                        var img_url =imgUrl.replace('url("','').replace('")','');
                        // 创建对象
                        var img = new Image();
                        // 改变图片的src
                        img.src = img_url;

                        //加载完成执行
                        img.onload =function(){
                            $("div[role='textbox']").css("min-height",img.height+"px");
                            // $("textarea").next().css("min-width",img.width+"px");
                            // $("text
                            // area").next().css("man-width","100%");
                        }
                    }
                    // tempData = selectData;
                } else {
                    contentEditor.setData(templates[0].content);
                    $("div[role='textbox']").css('background-image','url('+application.SERVE_URL + application.FILEPATH +templates[0].backgroupImg+')');
                    $("div[role='textbox']").css('background-repeat','no-repeat');
                    var img_url = application.SERVE_URL + application.FILEPATH +templates[0].backgroupImg;
                    // 创建对象
                    var img = new Image();

                    // 改变图片的src
                    img.src = img_url;

                    //加载完成执行
                    img.onload =function(){
                        $("div[role='textbox']").css("min-height",img.height+"px");
                        // $("textarea").next().css("min-width",img.width+"px");
                    }
                    // tempData = templates[0];
                }
                form.render(); //菜单渲染 把内容加载进去
            } else {
                layer.msg(res.msg);
            }
        }
    });
}
function getText(data) {
    data = data.replace(/<[^>]*>/ig, "");
    return data;
}
class MyUploadAdapter {
    constructor(loader) {
        this.loader = loader;
    }
    upload() {
        return this.loader.file
            .then(file => new Promise((resolve, reject) => {
                this._initRequest();
                this._initListeners(resolve, reject, file);
                this._sendRequest(file);
            }));
    }
    abort() {
        if (this.xhr) {
            this.xhr.abort();
        }
    }
    _initRequest() {
        const xhr = this.xhr = new XMLHttpRequest();
        xhr.open('POST', application.SERVE_URL + "/file/upload/", true);
        xhr.responseType = 'json';
    }
    _initListeners(resolve, reject, file) {
        const xhr = this.xhr;
        const loader = this.loader;
        const genericErrorText = `Couldn't upload file: ${ file.name }.`;

        xhr.addEventListener('error', () => reject(genericErrorText));
        xhr.addEventListener('abort', () => reject());
        xhr.addEventListener('load', () => {
            const response = xhr.response;
            resolve({
                default: application.SERVE_URL + application.FILEPATH + response.data
            });
        });
        if (xhr.upload) {
            xhr.upload.addEventListener('progress', evt => {
                if (evt.lengthComputable) {
                    loader.uploadTotal = evt.total;
                    loader.uploaded = evt.loaded;
                }
            });
        }
    }

    _sendRequest(file) {
        // Prepare the form data.
        const data = new FormData();

        data.append('file', file);
        this.xhr.send(data);
    }
}

function MyCustomUploadAdapterPlugin(editor) {
    editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
        // Configure the URL to the upload script in your back-end here!
        return new MyUploadAdapter(loader);
    };
}
})