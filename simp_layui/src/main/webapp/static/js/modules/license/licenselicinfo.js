layui.config({
    base: "../../../static/js/"
}).extend({
    "application": "application",
    "publicUtil": "publicUtil"
})

layui.use(['form', 'layer', 'laydate', 'table', 'laytpl', 'application', 'publicUtil','upload'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        publicUtil = layui.publicUtil,
        application = layui.application,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        upload = layui.upload,
        table = layui.table;

    application.init();

    initLicenseInfo();

    function initLicenseInfo(){
        $.ajax({
            url: application.SERVE_URL + '/sys/sysLicense/getLicenseInfo',
            type: "get",
            success: function(res) {
                $("#licenseid").val(publicUtil.htmlDecode(res.data.id));
                $("#schoolName").val(publicUtil.htmlDecode(res.data.schoolName));
                $("#schoolName").attr('disabled',true);
                $("#enddate").val(publicUtil.htmlDecode(res.data.expireDate));
                $("#enddate").attr('disabled',true);
                // $("#privateKey").attr('disabled',true);
                // $("#publicKey").attr('disabled',true);
                // $("#publicKeyAction").attr('disabled',true);
                // $("#privateKeyAction").attr('disabled',true);
                //$("#licensetype").attr('disabled',true);
                // publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                //     'typeCode': 'LICENSETYPE'
                // }, "licensetype", res.data.licenseType);
            }
        });
    }
    upload.render({
        elem: '#publicKey'
        ,exts: 'store'
        ,accept: 'file'
        ,url: application.SERVE_URL + '/licensefile/uploadLicense/'
        ,auto: false
        ,data: { schoolName : $("#schoolName").val()}
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

    $("#publicKey").click(function(){
        $("#publicKeyId").css('display','none');
        $("#publicKeyIdFail").css('display','none');
        $("#publicKeyAction").css('display','');
    });

    upload.render({
        elem: '#licenseKey'
        ,exts: 'lic'
        ,accept: 'file'
        ,url: application.SERVE_URL + '/licensefile/uploadLicense/'
        ,auto: false
        ,data: { schoolName : $("#schoolName").val()}
        ,bindAction: '#licenseKeyAction'
        ,done: function(res){
            if(res.msg == "上传成功") {
                $("#licenseKeyValue").val(res.data);
                $("#licenseKeyId").css('display', '');
                $("#licenseKeyAction").css('display', 'none');
            }else{
                $("#licenseKeyIdFail").css('display','');
            }
        }
    });

    $("#licenseKey").click(function(){
        $("#licenseKeyId").css('display','none');
        $("#licenseKeyIdFail").css('display','none');
        $("#licenseKeyAction").css('display','');
    });

})
