layui.config({
    base : "../../../../static/js/"
}).extend({
    publicUtil : "publicUtil",
    "dateUtils"  : "dateUtils",
    "application": "application",
    "treeSelect": "treeSelect"
})
layui.use(['form','layer','application','table','dateUtils','publicUtil','laydate','treeSelect'],function(){
    var form = layui.form,
        application = layui.application,
        dateUtils = layui.dateUtils,
        table = layui.table,
        laydate = layui.laydate
        publicUtil = layui.publicUtil,
        treeSelect = layui.treeSelect,
        layer = parent.layer,
        _$ = layui.jquery;

    //初始化院系专业
    var rootDepartmentId = "0";
    var sortType = 'positive';

    var setting = {
        view: {
            selectedMulti: false
        },
        check: {
            enable: true,
            chkStyle: "radio",
            radioType: "all"
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId"
            }
        }
    }

    initFormSelect();

    function initFormSelect() {
        treeSelect.render({
            // 选择器
            elem: '#department',
            // 数据
            data: application.SERVE_URL + '/settings/settingDepartment/tree',
            type: 'post',
            // 占位符
            placeholder: '请选院系/专业',
            // 是否开启搜索功能：true/false，默认false
            search: false,
            setting: setting,
            // 点击回调
            click: function(d) {
                document.getElementById("department").value = d.current.parentIds +','+ d.current.id;
            },
            check: function(d) {
                var value = "";
                for (var i = 0; i < d.current.length; i++) {
                    value += d.current[i].parentIds +','+d.current[i].id;
                    if (i < d.current.length - 1) {
                        value += ","
                    }
                }
                document.getElementById("department").value = value;
            },
            // 加载完成后的回调函数
            success: function(d) {
                var treeObj = treeSelect.zTree('department');
            }
        })
    }

    initBarChart();
    //初试化柱状图
    var chart = new G2.Chart({
        container: 'xydwxztj',
        forceFit: true,
        height: window.innerHeight,
        animate: false,
        padding: [40, 20, 50, 60]
    });
    function initBarChart() {
        var loadingindex = layer.msg("数据获取中", {
            icon: 16,
            shade: 0.3,
            time: 1000000
        });
        $("#startYearInternal").val();
        $("#endYearInternal").val();
        _$.ajax({
            url: application.SERVE_URL + "/chart/statistic/politicalappearance/",
            type:"post",
            contentType:"application/json",
            data: JSON.stringify({
                    startYearInternal: $("#startYearInternal").val()== "" ? null : $("#startYearInternal").val(),
                    endYearInternal: $("#endYearInternal").val()== "" ? null : $("#endYearInternal").val(),
                    smSchoolmate: {
                        smEducation: {
                            school: ($("#department").val() == "root,0" || $("#department").val() == "") ? null : $("#department").val(),
                        }
                    }
                })
            ,
            success: function (data) {
                var totalPersion = 0;
                for (var i = 0; i < data.length; i++) {
                    totalPersion+=data[i].value;
                }
                chart.source(data, {
                    valuePercent: {
                        formatter: function formatter(val) {
                            val = toDecimal(val) + '%';
                            return val;
                        }
                    }
                });
                chart.coord('theta', {
                    radius: 0.75,
                    innerRadius: 0.6
                });
                chart.tooltip({
                    showTitle: false,
                    typeTpl: '<li><span style="background-color:{color};" class="g2-tooltip-marker"></span>{name}: {value}</li>'
                });
                // 辅助文本
                chart.guide().html({
                    position: ['50%', '50%'],
                    html: '<div style="color:#8c8c8c;font-size: 14px;text-align: center;width: 10em;">校友<br><span style="color:#8c8c8c;font-size:20px" id="totalsum"></span></div>',
                    alignX: 'middle',
                    alignY: 'middle'
                });
                var interval =chart.intervalStack().position('valuePercent').color('type').label('valuePercent', {
                    formatter: function formatter(val, type) {
                        return type.point.type + ': ' + val;
                    }
                }).tooltip('type*valuePercent', function(type, valuePercent) {
                    valuePercent = toDecimal(valuePercent) + '%';
                    return {
                        name: type,
                        value: valuePercent
                    };
                }).style({
                    lineWidth: 1,
                    stroke: '#fff'
                });
                chart.render();
                $("#totalsum").html(totalPersion +"人");
                interval.setSelected(data[0]);
                layer.close(loadingindex);
            }
        });
    }

    _$('.search_btn').click(function () {
        initBarChart();
    });

    laydate.render({
        elem: '#startYearInternal'
        ,type: 'year'
        ,trigger: 'click'
        ,range: true
    });
    laydate.render({
        elem: '#endYearInternal'
        ,type: 'year'
        ,trigger: 'click'
        ,range: true
    });


    function toDecimal(x) {
        var f = parseFloat(x);
        if (isNaN(f)) {
            return false;
        }
        var f = Math.round(x*10000)/100;
        var s = f.toString();
        var rs = s.indexOf('.');
        if (rs < 0) {
            rs = s.length;
            s += '.';
        }
        while (s.length <= rs + 2) {
            s += '0';
        }
        return s;
    }

})