/**
 * @autor syp
 * @content echarts 实例
 * @returns
 * @Time 2018-08-04
 */
layui.config({
    base: "static/js/"
}).extend({
    "application": "application",
    "publicUtil": "publicUtil"
})
var tableIns;
layui.use(['element', 'layer', 'jquery', 'table', 'application', 'publicUtil'], function() {
    element = layui.element,
        $ = layui.$,
        table = layui.table,
        application = layui.application,
        publicUtil = layui.publicUtil;
    // layer = parent.layer === undefined ? layui.layer : top.layer;

    tableIns = table.render({
        pageName: 'pageNo' //页码的参数名称，默认：page
        ,limitName: 'pageSize' //每页数据量的参数名，默认：limit,
        ,page : true,
        cellMinWidth: 95,
        limit : 10,
        height: "full-380",
        elem: '#willDo',
        url: application.SERVE_URL + '/activiti/myactiviti/getWilDoByPage',
        id: "willDo",
        cols: [
            [
                // {field: 'id', title: 'ID', align:"center",style:'display:none;'},
                {
                    field: 'name',
                    title: '任务名称'
                },
                {
                    field: 'userName',
                    title: '待审核的用户名'
                }, {
                    field: 'userId',
                    hide: true,
                    width: '0'
                }, {
                    field: 'processInstanceId',
                    hide: true,
                    width: '0'
                },{
                    title: '操作',
                    templet: '#willDoBar',
                    fixed: "right",
                    align: "center"
                }
                
            ]
        ],
        done: function(res, curr, count) {
            // $('th div span').css({
            //     'font-weight:': 'bold'
            // });
        }
    });

    table.on('tool(willDo)', function(obj) {
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === 'authThis') { //预览
            console.log(data);
            var index = layer.open({
                title: "校友审核",
                type: 2,
                area: ["1200px", "600px"],
                content: ['information.html', 'no'],
                // scrolling:'no',
                // iframe: {
                //     src:'information.html'
                //     ,scrolling:'no'
                // },

                success: function(layero, index) {
                    sessionStorage.setItem("currentAuthUserId",data.userId);
                    sessionStorage.setItem("currentProcessInstanceId",data.processInstanceId);
                    console.log($("#layui-layer-iframe"+index));
                    console.log($("#layui-layer-iframe"+index).prop("scrolling","no"));
                    setTimeout(function() {
                        layui.layer.tips('点击此处返回待办列表',
                            '.layui-layer-setwin .layui-layer-close', {
                                tips: 3
                            });
                    }, 500)
                }
            })
        }
    });
});
