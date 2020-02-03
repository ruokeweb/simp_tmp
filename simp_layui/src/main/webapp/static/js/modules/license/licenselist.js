layui.config({
    base: "../../../static/js/"
}).extend({
    "application": "application",
    "publicUtil": "publicUtil"
})
layui.use(['form', 'layer', 'laydate', 'table', 'laytpl', 'application', 'publicUtil'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        publicUtil = layui.publicUtil,
        application = layui.application,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    application.init();
    var donationTableIns;
    var tableIns = table.render({
        elem: '#projectList',
        url: application.SERVE_URL + '/sys/sysLicense/getSchoolList',
        even : true,
        cellMinWidth: 95,
        page: true,
        height: "full-160",
        limit: 10,
        headers: {
            'Authorization': application.HEADER
        },
        id: "projectList",
        cols: [
            [{
                type: 'numbers'
            }, {
                field: 'value',
                title: '学校编码'
            }, {
                field: 'label',
                title: '学校名称'
            }/*, {
                field: 'licenseType',
                title: '证书类型'
            }, {
                field: 'expireDate',
                title: '到期时间'
            }, {
                field: 'licensePri',
                title: '私钥'
            }, {
                field: 'licensePub',
                title: '公钥'
            }, {
                field: 'createDate',
                title: '创建时间'
            }*/]
        ],
        done: function(res, curr, count){    //res 接口返回的信息,
            // publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'LICENSETYPE'},'licenseType');
            // getSchoolInfo(application.SERVE_URL + "/sys/sysLicense/getSchoolNameByTypeCode", {
            //     'typeCode': 'SCHOOLLIST'
            // }, "schoolName");
            var flag = 0;
            projectObj = res.list[0];
            $("[data-field = 'value']").children().each(function(data){
                if(flag > 1){
                    return false;
                }
                rendRecordList($(this).text());
                flag++;
            })
        }
    });

    function rendRecordList(schoolName) {
        donationTableIns = table.render({
            elem: '#recordList',
            url: application.SERVE_URL + '/sys/sysLicense/list?schoolName=' + schoolName,
            even : true,
            cellMinWidth: 95,
            page: true,
            height: "full-160",
            limit: 10,
            headers: {
                'Authorization': application.HEADER
            },
            id: "recordList",
            cols: [
                [{
                    type: 'checkbox'
                },/* {
                    field: 'schoolName',
                    title: '学校名称'
                }, */{
                    field: 'licenseType',
                    title: '证书类型'
                }, {
                    field: 'expireDate',
                    title: '到期时间'
                }, {
                    field: 'licensePri',
                    title: '私钥'
                }, {
                    field: 'licensePub',
                    title: '公钥'
                }, {
                    field: 'createDate',
                    title: '创建时间'
                }]
            ],
            done: function(res, curr, count) { //res 接口返回的信息,
                publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'LICENSETYPE'},'licenseType');
                getSchoolInfo(application.SERVE_URL + "/sys/sysLicense/getSchoolNameByTypeCode", {
                    'typeCode': 'SCHOOLLIST'
                }, "schoolName");
            }
        });
    }

    //右键点击事件
    table.on('rowRight(recordList)', function(obj){
        publicUtil.show_menu(obj);
    });

    //左键点击事件
    table.on('row(projectList)', function(obj) {
        publicUtil.hiddenMenu(obj);
        projectObj = obj.data;
        rendRecordList(obj.data.value)
    });

    //新增操作
    $(document).on('click', '.PER_ADD', function () {
        addCard();
    });

    //编辑操作
    $(document).on('click', '.PER_EDIT', function () {
        var flag = publicUtil.jurgeSelectRows(table.checkStatus('recordList').data);
        if (flag) {
            addCard(table.checkStatus('recordList').data[0]);
        } else {
            return false;
        }

    })

    //删除
    $(document).on('click', '.PER_DEL', function () {
        var flag = publicUtil.jurgeSelectRows(table.checkStatus('recordList').data);
        if (flag) {
            layer.confirm('确定删除此证书信息吗？', {
                icon: 3,
                title: '提示信息'
            }, function (index) {
                $.ajax({
                    url: application.SERVE_URL + "/sys/sysLicense/delete", //ajax请求地址
                    type: "POST",
                    data: {
                        id: table.checkStatus('recordList').data[0].id
                    },
                    headers: {
                        'Authorization': application.HEADER
                    },
                    success: function (data) {
                        if (data.code==application.REQUEST_SUCCESS) {
                            tableIns.reload();
                            donationTableIns.reload();
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
    $(".search_btn").on("click", function () {
        table.reload("projectList", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                // typeCode: $(".searchVal").val(),
                 label: $(".searchVal").val()
                //schoolName: $(".searchVal").val()
            }
        })
    });
    //生成证书
    $(document).on('click', '.PER_LICENSE_GENERATE', function () {
        var flag = publicUtil.jurgeSelectRows(table.checkStatus('recordList').data);
        if (flag) {
            var load =layer.confirm('确定生成License吗？', {
                icon: 3,
                title: '提示信息'
            }, function () {
                $.ajax({
                    url: application.SERVE_URL + "/sys/sysLicense/create", //ajax请求地址
                    type: "post",
                    timeout: 100000,
                    data: {
                        id: table.checkStatus('recordList').data[0].id,
                        licensePub: table.checkStatus('recordList').data[0].licensePub,
                        licensePri: table.checkStatus('recordList').data[0].licensePri
                    },
                    headers: {
                        'Authorization': application.HEADER
                    },
                    success: function (res) {
                        if (res.code == application.REQUEST_SUCCESS) {
                            //layer.close(load);
                            //layer.msg(res.data);
                            layer.closeAll();
                            layer.open({
                                title: '提示'
                                ,content: res.data
                            });
                            // setTimeout(function(){
                            //     var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            //     parent.layer.close(index); //再
                            // },500);
                        } else {
                            layer.msg(res.data);
                        }
                    }
                });
            });
        } else {
            return false;
        }

    })
    //导出证书
    $(document).on('click', '.PER_LICENSE_GENERATE', function () {
        var flag = publicUtil.jurgeSelectRows(table.checkStatus('recordList').data);
        if (flag) {
            var load =layer.confirm('确定生成License吗？', {
                icon: 3,
                title: '提示信息'
            }, function () {
                $.ajax({
                    url: application.SERVE_URL + "/sys/sysLicense/exportLicense", //ajax请求地址
                    type: "post",
                    timeout: 100000,
                    responseType: 'blob',
                    data: {
                        id: table.checkStatus('recordList').data[0].id,
                        licensePub: table.checkStatus('recordList').data[0].licensePub,
                        licensePri: table.checkStatus('recordList').data[0].licensePri
                    },
                    headers: {
                        'Authorization': application.HEADER
                    },
                    success: function (res) {
                        if (res.code == application.REQUEST_SUCCESS) {
                            //window.location.href = application.SERVE_URL+ application.FILEPATH  + "/5d85ecc9738eef1ba81212c3";
                            window.location.href = application.SERVE_URL+ application.LICENSEFILEPATH  + res.data;
                                layer.close(load);
                                layer.msg("导出完成");
                                setTimeout(function(){
                                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                    parent.layer.close(index); //再
                                },500);
                        }else if(res.code == 601){
                                layer.close(load);
                                layer.msg("导出失败,证书已过期");
                                setTimeout(function(){
                                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                    parent.layer.close(index); //再
                                },500);
                        }else{
                                layer.close(load);
                                layer.msg("导出失败");
                                setTimeout(function(){
                                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                                    parent.layer.close(index); //再
                                },500);
                        }
                        // if(status == "success"){
                        //     var blob = new Blob([data],{type: "application/octet-stream"});
                        //     var reader = new FileReader();
                        //     reader.readAsDataURL(blob);
                        //     reader.onload = function (e) {
                        //         var a = document.createElement('a');
                        //         a.download = 'licenseinfo.zip';
                        //         a.href = e.target.result;
                        //         $("body").append(a);
                        //         a.click();
                        //         $(a).remove();
                        //      }
                        //
                        //     layer.close(load);
                        //     layer.msg("导出完成");
                        //     setTimeout(function(){
                        //         var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        //         parent.layer.close(index); //再
                        //     },500);
                        // }else{
                        //     layer.close(load);
                        //     layer.msg("导出失败");
                        //     setTimeout(function(){
                        //         var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        //         parent.layer.close(index); //再
                        //     },500);
                        // }

                    }
                });
            });
        } else {
            return false;
        }

    })
    //添加编码
    function addCard(edit) {
        var restUrl = application.SERVE_URL + '/sys/sysLicense/get';
        var id = edit ? (edit.id ? edit.id : null) : null;
        publicUtil.gotoEditPage(restUrl, id, "License编辑管理", "licenseadd.html")
    }

    //学校转码
    function getSchoolInfo(url,typeCode,str){

        var dict;
        $.ajax({
            url: url, //ajax请求地址
            success: function(data) {
                if (data.code == application.REQUEST_SUCCESS) {
                    dict = data.data;
                    //抓取相关字段属性
                    var data=dict;
                    /*渲染表格*/
                    $("[data-field = '"+str+"']").children().each(function(){
                        for(var i =0;i<data.length;i++){
                            if($(this).text().trim() == data[i].value){
                                $(this).text(data[i].label);
                            }
                        }
                    })
                } else {
                    $("[data-field = '"+str+"']").children().each(function(){
                         $(this).text("");
                    })
                }
            }
        });
    };

})
