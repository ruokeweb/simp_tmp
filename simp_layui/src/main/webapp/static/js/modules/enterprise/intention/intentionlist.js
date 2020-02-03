/**
 * @autor lzq
 * @content 校友意向列表页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
    base: "../../../../static/js/"
}).extend({
    "application": "application",
    "publicUtil": "publicUtil"
})
layui.use(['form', 'layer', 'laydate', 'table', 'laytpl', 'application', 'publicUtil'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        _$ = layui.jquery,
        publicUtil = layui.publicUtil,
        application = layui.application,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    application.init();
    //选中标记
    var flag;
    //获取校友企业信息
    var tableIns = table.render({
        elem: '#companyList',
        url: application.SERVE_URL + '/ent/enterprise/list',
        even: true,
        cellMinWidth: 95,
        page: true,
        height: "full-160",
        limit: 10,
        headers: {
            'Authorization': application.HEADER
        },
        id: "companyList",
        cols: [
            [
                // {
                //     field: 'id',
                //     type: 'hidden'
                // },
                {
                field: 'name',
                title: '校企名称'
            }, {
                field: 'type',
                title: '单位性质'
            }, {
                field: 'phone',
                title: '联系电话'
            }]
        ],
        done: function (res, curr, count) { //res 接口返回的信息,
            publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                'typeCode': 'COMPANY_TPYE'
            }, 'type');
            var flag = 0;
            companySel = res.list[0];
            if(null != companySel){
                addintentionList(companySel.id);
            }else{
                addintentionList();
            }
            // _$("[data-field = 'id']").children().each(function () {
            //     if (flag > 1) {
            //         return false;
            //     }
            //     //加载企业意向管理
            //     addintentionList(_$(this).text());
            //     flag++;
            // })
        }
    });


    // var companyData = parent.companySel;

    //右键点击事件
    table.on('rowRight(intentionList)', function (obj) {
        publicUtil.show_menu(obj);
    });

    //左键点击事件
    table.on('row(intentionList)', function (obj) {
        publicUtil.hiddenMenu(obj);

    });

    //行事件
    table.on('row(companyList)', function (obj) {
        _$(".layui-select-tr").removeClass("layui-select-tr");
        obj.tr.addClass("layui-select-tr");
        addintentionList(obj.data.id);
    });

    // addintentionList();

    function addintentionList(id) {
        //生产坏境下请求后台
        var intentionListtables = table.render({
            elem: '#intentionList',
            //生产坏境下请求后台
            // url: application.SERVE_URL + '/ent/entIntention/smlist?enterId=' + companyData.id,
            url: application.SERVE_URL + '/ent/entIntention/list?enterId=' + id,
            cellMinWidth: 95,
            page: true,
            even: true,
            height: "full-160",
            id: "enteciationList",
            cols: [
                [{
                    type: 'checkbox'
                }, {
                    field: 'userId',
                    title: '姓名',
                    templet: function(d) {

                        var htm = "";
                        var res = d.smSchoolmate;
                        if (res != null && res != "") {
                            htm =  d.smSchoolmate.name ;
                        }else{
                            htm ='管理员账号登录'
                        }
                        return htm;
                    }
                }, {
                    field: 'title',
                    title: '标题'
                }, {
                    field: 'content',
                    title: '内容'
                }]
            ],
            done: function (res, curr, count) { //res 接口返回的信息,
            }
        });
    }


    //新增操作
    _$(document).on('click', '.PER_ADD', function () {
        addAssociation(table.checkStatus('intentionList').data[0], "add");
    });


    //编辑操作
    _$(document).on('click', '.PER_EDIT', function () {
        var flag = publicUtil.jurgeSelectRows(table.checkStatus('intentionList').data);
        if (flag) {
            addAssociation(table.checkStatus('intentionList').data[0], "edit");
        } else {
            return false;
        }
    })

    //删除
    _$(document).on('click', '.PER_DEL', function () {
        var flag = publicUtil.jurgeSelectRows(table.checkStatus('enteciationList').data);
        if (flag) {
            layer.confirm('确定删除此校友企业吗？', {
                icon: 3,
                title: '提示信息'
            }, function (index) {
                _$.ajax({
                    url: application.SERVE_URL + "/ent/entIntention/delete", //ajax请求地址
                    data: {
                        id: table.checkStatus('intentionList').data[0].id
                    },
                    success: function (data) {
                        if (data.code == application.REQUEST_SUCCESS) {
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

    //获取权限并加载按钮
    publicUtil.getPerms(application.PERMS_URL, application.HEADER, parent.cur_menu_id, 'get', 'but_per');
    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    _$(".search_btn").on("click", function () {
        table.reload("enteciationList", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                title: _$(".searchVal").val(),
                content:_$(".searchVal").val(),
                paramA: _$(".searchVal").val()

            }
        })
    });

})