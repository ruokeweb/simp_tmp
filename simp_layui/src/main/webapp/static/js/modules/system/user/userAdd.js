/**
 * @autor syp
 * @content 用户列表页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
    base: '../../../../static/js/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    "formSelects": 'formSelects-v4',
    "application": "application",
    'publicUtil': 'publicUtil',
    "validparam": "validparam",
    "treeSelect": "treeSelect"
});

layui.use(['jquery', 'form', 'layer', 'formSelects', 'publicUtil', 'upload', 'validparam', 'application', 'treeSelect'],
    function() {
        var form = layui.form,
            $ = layui.jquery,
            formSelects = layui.formSelects,
            publicUtil = layui.publicUtil,
            upload = layui.upload,
            application = layui.application,
            validparam = layui.validparam,
            layer = layui.layer,
            treeSelect = layui.treeSelect;

        var formSelectsdata;
        // var treeSelect;
        var setting = {
            view: {
                selectedMulti: true
            },
            check: {
                enable: true,
                chkboxType: {
                    'Y': 's',
                    'N': 's'
                }
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",
                    pIdKey: "parentId"
                }
            }
        }

        if (parent.editFormData != '') {
            data = parent.editFormData;
            $(".id").val(publicUtil.htmlDecode(data.id));
            $(".username").val(publicUtil.htmlDecode(data.username));
            $(".virtualName").val(publicUtil.htmlDecode(data.virtualName));
            $(".email").val(publicUtil.htmlDecode(data.email));
            $(".mobile").val(publicUtil.htmlDecode(data.mobile));
            $(".idcard").val(publicUtil.htmlDecode(data.idcard));
            $(".remark").val(publicUtil.htmlDecode(data.remark));
            if (data.virtualPhoto != null || data.virtualPhoto != '') {
                document.getElementById("virtualPhoto").src = application.SERVE_URL + application.FILEPATH + data.virtualPhoto;
                $('#photoPath').html(data.virtualPhoto);
            }
            if ($(".id").val()) {
                $(".username").addClass("layui-disabled");
                $(".username").attr("readonly", "true");
            }
            publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                'typeCode': 'USER_TYPE'
            }, "userType", data.userType);
            publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                'typeCode': 'USER_SOURCE'
            }, "userSource", data.userSource);

            formSelectsdata = data.roleList;
            form.render();
            formSelects.render();
        } else {
            publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                'typeCode': 'USER_TYPE'
            }, "userType");
            publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                'typeCode': 'USER_SOURCE'
            }, "userSource");
            form.render();
        }


        var uploadInst = upload.render({
            elem: '#selectphoto',
            url: application.SERVE_URL + '/file/upload/',
            accept: 'images',
            exts: 'jpg|png|gif|bmp|jpeg',
            size: 500,
            choose: function(obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result) {
                    $('#virtualPhoto').attr('src', result); //图片链接（base64）
                });
            },
            done: function(res) {
                var data = res;
                if (data.code == application.REQUEST_SUCCESS) {
                    $('#photoPath').html(res.data);
                    top.layer.msg(data.msg, {
                        time: 1000
                    });
                } else {
                    top.layer.msg(data.msg + "(" + data.code + ")", {
                        time: 1000
                    });
                }
            }
        });

        //多选下拉框配置
        formSelects.config('userRole', {
            keyName: 'name', //自定义返回数据中name的key, 默认 name
            keyVal: 'id',
        }, true);

        //初始化用户下拉框(此处应该时经后台过滤处理 选中与为选中)
        // selected: boolean,         //自定义返回数据中selected的key, 默认 selected
        // selected: boolean',         //自定义返回数据中disabled的key, 默认 disabled
        function initSelect() {
            $.ajax({
                url: application.SERVE_URL + '/sys/sysrole/loadAll', //ajax请求地址
                success: function(rs) {
                    formSelects.data('userRole', 'local', {
                        arr: rs.data
                    })
                    if (formSelectsdata != null && formSelectsdata != '' && formSelectsdata !=
                        'undefined') {
                        layui.formSelects.value('userRole', formSelectsSetValue(formSelectsdata));
                    }
                }
            });
        }

        //验证表单
        form.verify(validparam);
        //自定义验证规则-校验用户名是否存在
        form.verify({
            user_exist: function(value) {
                var message = "";
                //判断是否是编辑
                if ($(".id").val()) {
                    return;
                } else {
                    $.ajax({
                        url: application.SERVE_URL + '/sys/sysuser/checkUserExist',
                        async: false,
                        data: {
                            username: value
                        },
                        success: function(res) {
                            if (res.code == application.DATA_USED) {
                                message = res.msg;
                            } else {
                                return;
                            }
                        }
                    })
                }
                return message;
            }
        });


        initSelect();
        initFormSelect();
        form.on("submit(addUser)", function(data) {
            //弹出loading
            var index = top.layer.msg('数据提交中，请稍候', {
                icon: 16,
                time: false,
                shade: 0.8
            });

            var data = {
                "id": $(".id").val() == null || $(".id").val() == "" ? null : $(".id").val(),
                "username": $(".username").val(),
                "virtualName": $(".virtualName").val(),
                "virtualPhoto": $('#photoPath').html(),
                "email": $(".email").val(),
                "mobile": $(".mobile").val(),
                "idcard": $(".idcard").val(),
                "remark": $(".remark").val(),
                "userType": $("#userType").val(),
                "userSource": $("#userSource").val(),
                "roleList": convert(layui.formSelects.value('userRole', 'val')),
                "asList": convertasIds($("#tree").val())
            }

            $.ajax({
                url: application.SERVE_URL + '/sys/sysuser/save', //ajax请求地址
                contentType: "application/json",
                data: JSON.stringify(data), //publicUtil.htmlEscape(JSON.stringify(data)), 前端可格式化特殊字符的方法，现已置于后台处理		
                success: function(res) {
                    top.layer.close(index);
                    top.layer.msg(res.msg, {
                        time: 1000
                    }, function() {
                        layer.closeAll("iframe");
                        parent.location.reload();
                    });

                }
            });
            return false;
        })



        function convert(arr) {
            var roleIds = new Array();
            for (var i = 0; i < arr.length; i++) {
                roleIds.push({
                    "id": arr[i]
                });
            }
            return roleIds;
        }

        function convertasIds(val) {
            var associs = new Array();
            if (val != null && val != '') {
                var arr = val.split(",");
                for (var i = 0; i < arr.length; i++) {
                    associs.push({
                        "id": arr[i]
                    });
                }
            }
            return associs;
        }
        //回显多选的下拉框
        function formSelectsSetValue(arr) {
            var Ids = new Array();
            for (var i = 0; i < arr.length; i++) {
                Ids.push(arr[i].id);
            }
            return Ids;
        }

        function initFormSelect() {
            treeSelect.render({
                // 选择器
                elem: '#tree',
                // 数据
                data: application.SERVE_URL + '/as/asAssociation/tree',
                type: 'post',
                // 占位符
                placeholder: '请选择该用户的要管理的校友会',
                // 是否开启搜索功能：true/false，默认false
                search: false,
                setting: setting,
                // 点击回调
                click: function(d) {
                    document.getElementById("tree").value = d.current.id;
                },
                check: function(d) {
                    var value = "";
                    for (var i = 0; i < d.current.length; i++) {
                        value += d.current[i].id;
                        if (i < d.current.length - 1) {
                            value += ","
                        }
                    }
                    document.getElementById("tree").value = value;
                },
                // 加载完成后的回调函数
                success: function(d) {
                    if (parent.editFormData != '') {
                        //获取当前校友所在的校友会
                        $.ajax({
                            url: application.SERVE_URL + '/sys/sysuser/getAsIdsByUser',
                            async: false,
                            data: {
                                id: parent.editFormData.id
                            },
                            success: function(res) {
                                //                获取zTree对象，可以调用zTree方法
                                var treeObj = treeSelect.zTree('tree');
                                var data = res.data;
                                for (var i = 0; i < data.length; i++) {
                                    var nodes = treeObj.getNodesByParam("id",
                                        data[i], null);
                                    treeObj.checkNode(nodes[0], true, false);
                                    treeObj.expandNode(nodes[0], true);
                                    //                刷新树结构
                                    treeSelect.refresh('tree');
                                }
                                var nodes = treeObj.getCheckedNodes(true);
                                var value = "";
                                var v = "";
                                for (var i = 0; i < nodes.length; i++) {
                                    v += "<span class='laytreelabel'>" + nodes[i].name +
                                        '&nbsp;<i class="layui-icon layui-icon-close layclose"></i>' +
                                        "</span>";
                                    if (i === 0) {
                                        value = nodes[0].id
                                    } else {
                                        value = value + "," + nodes[i].id
                                    }

                                }
                                document.getElementById("tree").value = value;
                                tmp = new Date().getTime(),
                                    TREE_SELECT_TITLE_ID = 'layui-select-title-' + tmp,
                                    $('#' + TREE_SELECT_TITLE_ID + ' input').siblings().remove()
                                $('#' + TREE_SELECT_TITLE_ID + ' input').before(v);
                            }
                        })
                    }

                }
            })
        }


        $("#close").click(function() {
            layer.closeAll("iframe");
            //刷新父页面
            parent.location.reload();
        })
    })
