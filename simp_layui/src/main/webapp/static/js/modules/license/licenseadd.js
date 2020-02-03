/**
 * license add
 */
layui.config({
    base: '../../../static/js/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    "application": "application",
    "dateUtils": "dateUtils",
    "croppers":"croppers"
});

layui.use(['jquery', 'form', 'layer', 'laydate', 'publicUtil', 'upload', 'dateUtils','croppers',
    'application', 'element'
], function() {
    var form = layui.form,
         $ = layui.jquery,
        publicUtil = layui.publicUtil,
        application = layui.application,
        layer = layui.layer,
        element = layui.element,
        dateUtils = layui.dateUtils,
        croppers=layui.croppers,
        laydate = layui.laydate,
        username = parent.parent.username,
        upload = layui.upload;

    //页面打开时，或者基本信息时，点击填充数据
    initAssociationData();
    function initAssociationData() {
        if (parent.editFormData) {
            $("#licenseid").val(publicUtil.htmlDecode(parent.editFormData.id));
            //$("#schoolName").val(publicUtil.htmlDecode(parent.editFormData.schoolName));
            $("#schoolName").attr('disabled',true);
            $("#enddate").val(publicUtil.htmlDecode(parent.editFormData.expireDate));
            $("#privateKeyValue").val(publicUtil.htmlDecode(parent.editFormData.licensePri));
            $("#publicKeyValue").val(publicUtil.htmlDecode(parent.editFormData.licensePub));
            $("#privateKey").removeClass("layui-btn layui-btn-normal");
            $("#privateKeyAction").removeClass("layui-btn");
            $("#publicKey").removeClass("layui-btn layui-btn-normal");
            $("#publicKeyAction").removeClass("layui-btn");
            $("#privateKey").attr('disabled',true);
            $("#privateKeyAction").attr('disabled',true);
            $("#publicKey").attr('disabled',true);
            $("#publicKeyAction").attr('disabled',true);
            //证书状态
            publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                'typeCode': 'LICENSETYPE'
            }, "licensetype", parent.editFormData.licenseType);
            getSchoolInfo(application.SERVE_URL + "/sys/sysLicense/getSchoolNameByTypeCode", {
                'typeCode': 'SCHOOLLIST'
            }, "schoolName",parent.editFormData.schoolName);

        }else{
            publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                'typeCode': 'LICENSETYPE'
            }, "licensetype");
            getSchoolInfo(application.SERVE_URL + "/sys/sysLicense/getSchoolNameByTypeCode", {
                'typeCode': 'SCHOOLLIST'
            }, "schoolName");
        }

    }
    //submit(addUser)  绑定提交按钮（基本信息）
    form.on("submit(addLicense)", function(data) {
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });
        $.ajax({
            url: application.SERVE_URL + '/sys/sysLicense/save', //ajax请求地址
            data: {
                id: $("#licenseid").val(),
                schoolName: $("#schoolName").val(),
                expireDate: $("#enddate").val(),
                licensePri: $("#privateKeyValue").val(),
                licensePub: $("#publicKeyValue").val(),
                licenseType: $("#licensetype").val()
            },
            success: function(data) {
                if (data.code == application.REQUEST_SUCCESS) {
                    top.layer.close(index);
                    top.layer.msg(data.msg);
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                } else {
                    top.layer.msg(data.msg + "(" + data.code + ")");
                }
            }
        });
        return false;
    })

    function closeSelf() {
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index);
    }

    $("#close").click(closeSelf);

    layer.closeAll();

    var fileId="";
    //私钥
    upload.render({
        elem: '#privateKey'
        ,exts: 'store'
        ,url: application.SERVE_URL + '/licensefile/upload/'
        ,auto: false
        //,multiple: true
        ,bindAction: '#privateKeyAction'
        ,done: function(res){
            if(res.msg == "上传成功"){
                $("#privateKeyValue").val(res.data);
                $("#privateKeyId").css('display','');
                $("#privateKeyAction").css('display','none');
            }else{
                $("#privateKeyIdFail").css('display','');
            }
        }
    });
    //公钥
    upload.render({
        elem: '#publicKey'
        ,exts: 'store'
        ,accept: 'file'
        ,url: application.SERVE_URL + '/licensefile/upload/'
        ,auto: false
        //,multiple: true
        ,bindAction: '#publicKeyAction'
        ,done: function(res){
            if(res.msg == "上传成功") {
                $("#publicKeyValue").val(res.data);
                $("#publicKeyId").css('display', '');
                $("#publicKeyAction").css('display', 'none');
            }else{
                $("#publicKeyIdFail").css('display','');
            }
        }
    });

    laydate.render({
        elem: '#enddate',
        theme: 'molv',
        trigger: 'click'
    });

    $("#privateKey").click(function(){
        $("#privateKeyId").css('display','none');
        $("#privateKeyIdFail").css('display','none');
        $("#privateKeyAction").css('display','');
    });

    $("#publicKey").click(function(){
        $("#publicKeyId").css('display','none');
        $("#publicKeyIdFail").css('display','none');
        $("#publicKeyAction").css('display','');
    });

    form.on('select(licensetype)', function(data) {
        if(data.value == "TEMPORARY"){
            var now = new Date();
            now.setFullYear(now.getFullYear()+1);
            $("#enddate").val(dateFormat("YYYY-mm-dd", now));
        }else if(data.value == "LONGTERM"){
            var now = new Date();
            now.setFullYear(now.getFullYear()+10);
            $("#enddate").val(dateFormat("YYYY-mm-dd", now));
        }
    })

    function getSchoolInfo(url,typeCode,selectid,selectValue){
        var dict;
        $.ajax({
            url: url, //ajax请求地址
            success: function(data) {
                if (data.code == application.REQUEST_SUCCESS) {
                    dict = data.data;

                    //抓取相关字段属性
                    //var data=JSON.parse(dict);
                    var data=dict;
                    $("#"+selectid).empty();
                    $("#"+selectid).append('<option  value="" >'+"请选择"+' </option>');
                    for(var i =0;i<data.length;i++){
                        $("#"+selectid).append('<option  value="'+data[i].value+'">'+data[i].label+'</option>');//往下拉菜单里添加元素
                    }
                    if(selectValue){
                        $('#'+selectid).val(selectValue);
                    }

                    form.render('select');//菜单渲染 把内容加载进去

                } else {
                    dict = "";
                }
            }
        });
    };
    function dateFormat(fmt, date) {
        let ret;
        let opt = {
            "Y+": date.getFullYear().toString(),        // 年
            "m+": (date.getMonth() + 1).toString(),     // 月
            "d+": date.getDate().toString(),            // 日
            "H+": date.getHours().toString(),           // 时
            "M+": date.getMinutes().toString(),         // 分
            "S+": date.getSeconds().toString()          // 秒
        };
        for (let k in opt) {
            ret = new RegExp("(" + k + ")").exec(fmt);
            if (ret) {
                fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
            };
        };
        return fmt;
    }
})
