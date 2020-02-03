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
        padding: [20, 20, 50, 60]
    });

    function initBarChart() {
        $("#startYearInternal").val();
        $("#endYearInternal").val();
        var loadingindex = layer.msg("数据获取中", {
            icon: 16,
            shade: 0.3,
            time: 1000000
        });
        _$.ajax({
            url: application.SERVE_URL + "/chart/statistic/unitnature/",
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
                chart.source(data, {
                    value: {
                        tickCount: 5
                    }
                });
                chart.scale('value', {
                    alias: '人数'
                });
                chart.axis('type', {
                    label: {
                        textStyle: {
                            fill: '#aaaaaa',
                            fontWeight: 'bold'
                        }
                    },
                    tickLine: {
                        alignWithLabel: false,
                        length: 0
                    }
                });
                chart.axis('value', {
                    label: {
                        textStyle: {
                            fill: '#aaaaaa',
                            fontWeight: 'bold'
                        },
                        formatter: function formatter(text) {
                            return text.replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
                        }
                    },
                    title: {
                        offset: 70
                    }
                });
                chart.tooltip({
                    share: true
                });
                // chart.guide().dataMarker({
                //     top: true,
                //     content: '因政策调整导致销量下滑',
                //     position: ['2014-01', 1750],
                //     style: {
                //         text: {
                //             fontSize: 13
                //         }
                //     },
                //     lineLength: 30
                // });
                chart.interval().position('type*value').opacity(1);
                chart.render();
                layer.close(loadingindex);
            }
        });
    }


    _$('.sort-button').click(function () {
        sortType = sortType === 'positive' ? 'negative' : 'positive';
        sortData(sortType);
        chart.repaint();
    });

    _$('.search_btn').click(function () {
        initBarChart();
    });

    function sortData(sortType) {
        if (sortType === 'positive') {
            data.sort(function (a, b) {
                return b.value - a.value;
            });
        } else {
            data.sort(function (a, b) {
                return a.value - b.value;
            });
        }
    }

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

})