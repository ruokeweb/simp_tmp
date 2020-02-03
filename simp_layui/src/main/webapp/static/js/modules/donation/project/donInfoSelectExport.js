layui.config({
    base: "../../../../static/js/"
}).extend({
    "application": "application",
    "publicUtil": "publicUtil"
})

var use = layui.use(['form','element', 'laytpl', 'layer', 'laydate','application', 'publicUtil', 'form'], function () {
        var form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate,
        $ = layui.jquery,
        application = layui.application;
        Date.prototype.Format = function (fmt) { //author: meizz
            var o = {
                "M+": this.getMonth() + 1, //月份
                "d+": this.getDate(), //日
                "h+": this.getHours(), //小时
                "m+": this.getMinutes(), //分
                "s+": this.getSeconds(), //秒
                "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                "S": this.getMilliseconds() //毫秒
            };
            if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        };
        $('#date').val((new Date()).Format("yyyy-M-d") );
        laydate.render({
            elem: '#startdate'
            ,theme: 'molv',
            trigger: 'click'
            ,position: 'static'
            ,showBottom: false
            ,min:-30
            ,max: 0
            ,change: function(value, date){ //监听日期被切换
                console.log(value);
                $('#date').val(value);
            }
        });

        laydate.render({
            elem: '#enddate'
            ,theme: 'molv',
            trigger: 'click'

        });

    $(document).on('click', '.export_don', function() {

            var  startdate = $('#date').val();;
            if(startdate==null||startdate==undefined||startdate==''){
                layer.msg("时间不能为空", {icon: 5, shift: 6});
                return false;
            }

            layer.confirm('确定导出每日捐赠统计吗？',{icon:3, title:'提示信息'},function(index){
                var load =  layer.load(2, { //icon支持传入0-2
                    shade: [0.5, 'gray'], //0.5透明度的灰色背景
                    content: '中，请勿重复操作....... ',
                    success: function (layero) {
                        layero.find('.layui-layer-content').css({
                            'padding-top': '39px',
                            'width': '300px'
                        });
                    }
                });
                $.ajax({
                    url: application.SERVE_URL+"/don/donRecord/exportRecordByDay", //ajax请求地址
                    data:{
                        startdate :startdate
                    },
                    success: function (res) {
                        if(res.code==application.REQUEST_SUCCESS){
                            window.location.href = application.SERVE_URL + res.data;
                            layer.close(load);
                            layer.msg("导出完成");
                            setTimeout(function(){
                                var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                parent.layer.close(index); //再
                            },500);
                        }else{
                            layer.msg(res.msg);
                        }

                    }
                });
            })

        })
});
