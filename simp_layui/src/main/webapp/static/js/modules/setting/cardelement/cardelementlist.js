/**
 * @autor zdl
 * @content 校友卡元素列表页面js
 * @returns
 * @Time 2019-02-18
 */
var cardId=null;
layui.config({
    base: "../../../../static/js/"
}).extend({
    "application": "application",
    "publicUtil": "publicUtil"
})
layui.use(['form','layer', 'laydate', 'table', 'laytpl', 'application', 'publicUtil'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        publicUtil = layui.publicUtil,
        application = layui.application,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;
    var starInfotableIns;

    application.init();

    //获取权限并加载按钮
    publicUtil.getPerms(application.PERMS_URL, application.HEADER, parent.cur_menu_id, 'get', 'but_per');
    rendStarInfo("init");


    var tableIns = table.render({
        elem: '#cardList',
        url: application.SERVE_URL + '/settings/settingCard/list',
        even : true,
        cellMinWidth: 95,
        page: true,
        height: "full-160",
        limit: 10,
        id: "cardList",
        cols: [
            [{
                type: 'checkbox'
            },{
                field: 'name',
                title: '卡片名称',
                event: 'click'
            },{
                field: 'id',
                hide :true ,
                width: '0'
            }]
        ],
        done: function(res, curr, count) { //res 接口返回的信息,
            var flag = 0;
            $("[data-field = 'id']").children().each(function(){
                if(flag > 1){
                    return false;
                }
                rendStarInfo($(this).text());
                flag++;
            })
        }
    });

    //右键点击事件
    table.on('rowRight(cardList)', function(obj){
        publicUtil.show_menu(obj);
    });

    //左键点击事件
    table.on('row(cardElementList)', function(obj){
        publicUtil.hiddenMenu(obj);
    });

    function rendStarInfo(id)
    {
        starInfotableIns = table.render({
            elem: '#cardElementList',
            url: application.SERVE_URL + '/settings/settingCardElement/list',
            even : true,
            cellMinWidth: 95,
            where: {cardId : id},
            page: true,
            height: "full-160",
            limit: 10,
            id: "cardElementList",
            cols: [
                [{
                    type: 'checkbox'
                },{
                    field: 'face',
                    title: '正反'
                }, {
                    field: 'position',
                    title: '位置'
                },{
                    field: 'type',
                    title: '类型'
                },{
                    field: 'status',
                    title: '名称'
                },{
                    field: 'code',
                    title: '编码'
                },{
                    field: 'color',
                    title: '颜色'
                },{
                    field: 'style',
                    title: '元素样式'
                },{
                    field: 'remark',
                    title: '备注'
                }]]/*,
            done: function (res, curr, count) {
                $('th div span').css({'font-weight:': 'bold'});
                publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                    'face': 'CARD_ELEMENT_FACE'
                }, 'face');
            }*/
        });
    }

    table.on('row(cardList)', function (obj) {
        $(".layui-select-tr").removeClass("layui-select-tr");
        obj.tr.addClass("layui-select-tr");
        rendStarInfo(obj.data.id);
    });


    //新增操作
    $(document).on('click', '.PER_ADD', function () {
        //addStarInfo();
        if (table.checkStatus('cardList').data.length == 1) {
            cardId=table.checkStatus('cardList').data[0].id;
            addStarInfo();
        } else {
            top.layer.msg("请选中一个校友卡进行操作");
            return false;
        }
    });

    //编辑操作
    $(document).on('click', '.PER_EDIT', function () {
        if (table.checkStatus('cardElementList').data.length == 1) {
            addStarInfo(table.checkStatus('cardElementList').data[0]);
        } else {
            top.layer.msg("请选中一个校友卡下的一个元素进行操作");
            return false;
        }



        var flag = publicUtil.jurgeSelectRows(table.checkStatus('cardElementList').data);
        if (flag) {

        } else {
            return false;
        }

    })

    //删除
    $(document).on('click', '.PER_DEL', function () {
        var flag = publicUtil.jurgeSelectRows(table.checkStatus('cardElementList').data);
        if (flag) {
            layer.confirm('确定删除此星级设置吗？', {
                icon: 3,
                title: '提示信息'
            }, function (index) {
                $.ajax({
                    url: application.SERVE_URL + "/settings/settingCardElement/delete", //ajax请求地址
                    data: {
                        id: table.checkStatus('cardElementList').data[0].id
                    },
                    success: function (data) {
                        if (data.code==application.REQUEST_SUCCESS) {
                            tableIns.reload();
                            layer.close(index);
                        }
                    }
                });
            });
        } else {
            return false;
        }
    })

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click", function () {
        table.reload("cardList", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                name: $(".searchVal").val(),
            }
        })
    });

    //编辑元素
    function addStarInfo(edit) {
        var restUrl = application.SERVE_URL + '/settings/settingCardElement/get';
        var id = edit ? (edit.id ? edit.id : null) : null;
        publicUtil.gotoEditPage(restUrl, id, "元素设置", "cardelementadd.html")
    }
})
