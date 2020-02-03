layui.config({
    base: "../../../../static/js/"
}).extend({
    "application": "application",
    "publicUtil": "publicUtil"
})

var use = layui.use(['form', 'element', 'laytpl', 'layer', 'laydate', 'application', 'publicUtil', 'form'], function() {
    var form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate,
        $ = layui.jquery,
        application = layui.application;

    laydate.render({
        elem: '#startdate',
        range: true,
        theme: 'molv',
        trigger: 'click',
        min:-30,
        max: 0
    }); 
    creatRadio($("#payStyle"));




    $(document).on('click', '.export_don', function() {
        console.log($("#startdate").val());
        var queryBeginDate = null;
        var queryEndDate = null;
        
        var startdate = $('#startdate').val();;
        if (startdate == null || startdate == undefined || startdate == '') {
            layer.msg("时间不能为空", {
                icon: 5,
                shift: 6
            });
            return false;
        }else{
            let strs = new Array();
            strs = startdate.split(" - ");
            queryBeginDate = strs[0].trim();
            queryEndDate = strs[1].trim();
        }
        var payStyle = $("input[name='payStyle']:checked").val();
        if(payStyle ==null || payStyle == ""){
            layer.msg("请选择付款类型", {
                icon: 5,
                shift: 6
            });
            return false; 
        }
        
        console.log(queryBeginDate);
        console.log(queryEndDate);
        console.log($("input[name='payStyle']:checked").val());
        
        layer.confirm('确定导出每日捐赠统计吗？', {
            icon: 3,
            title: '提示信息'
        }, function(index) {
            var load = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '中，请勿重复操作....... ',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });
            $.ajax({
                url: application.SERVE_URL + "/don/donRecord/exportStatement", //ajax请求地址
                data: {
                    queryBeginDate: queryBeginDate,
                    queryEndDate: queryEndDate,
                    style: payStyle
                },
                success: function(res) {
                    if (res.code == application.REQUEST_SUCCESS) {
                        window.location.href = application.SERVE_URL + res.data;
                        layer.close(load);
                        layer.msg("导出完成");
                        setTimeout(function() {
                            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            parent.layer.close(index); //再
                        }, 500);
                    } else {
                        layer.msg(res.msg);
                    }

                }
            });
        })

    })
    
    //生成redio
    function creatRadio(dom, val) {
        
        var dict=sessionStorage.getItem("dictCache");
        //抓取相关字段属性
        var d=JSON.parse(dict)["PAY_STYLE"];
        dom.empty();
        for (var i = 0; i < d.length; i++) {
            if (val == d[i].value) {
                dom.append("<input name='payStyle' value='" + d[i].value + "' title='" + d[i].label +
                    "'checked=''  type='radio'>"); //往下拉菜单里添加元
                form.render();
            } else {
                dom.append("<input name='payStyle' value='" + d[i].value + "' title='" + d[i].label + "'  type='radio'>"); //往下拉菜单里添加元素	
                form.render();
            }
        }		
    }
    
});
