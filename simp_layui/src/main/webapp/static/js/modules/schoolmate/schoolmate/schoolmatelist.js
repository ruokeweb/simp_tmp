/**
 * @autor syp
 * @content 用户列表页面js
 * @returns
 * @Time 2018-08-03
 */

layui.config({
    base: "../../../../static/js/"
}).extend({
    "application": "application",
    "publicUtil": "publicUtil",
    "dateUtils": "dateUtils",
    "treeSelect": "treeSelect"
})
var selectTabRowId;
var selectUserId;
layui.use(['table', 'form', 'element', 'layer', 'jquery', 'application', 'upload', 'publicUtil', 'laydate', 'dateUtils',
    'treeSelect', 'upload'
], function() {
    var layer = layui.layer,
        element = layui.element,
        treeGrid = layui.treeGrid,
        form = layui.form,
        laydate = layui.laydate,
        dateUtils = layui.dateUtils,
        _$ = layui.jquery,
        dateUtils = layui.dateUtils,
        publicUtil = layui.publicUtil,
        table = layui.table,
        application = layui.application,
        upload = layui.upload,
        treeSelect = layui.treeSelect,
        protree = layui.protree;


    application.init();

    //左侧机构树
    var orgId = null;
    var rootAreaId = "0";
    var rootDepartmentId = "0";
    var tableIns;
    //选中标记
    var flag;
    //节点标记
    var treeCheckNode;
    //节点标记
    var treeObj;
    //ztree设置
    var setting = {
        view: {
            selectedMulti: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId"
            }
        },
        callback: {
            onClick: orgTreeonClick
        }
    };

    //获取权限并加载按钮
    publicUtil.getPerms(application.PERMS_URL, application.HEADER, parent.cur_menu_id, 'get', 'but_per');

    //详细信息检索
    element.on('collapse(panel)', function(data) {
        // layer.msg('展开状态：'+ data.show);  //展开为true, 闭合为false;
        if (data.show) {
            $(".opa").css('display', 'block');
        } else {
            $(".opa").css('display', 'none');
        }
    });

    //左侧校友会初始化树
    initTree();
    //初始化树高度
    _$(function() {
        $(".ztree").height($(window).height() - 163);
        $(window).resize(function() {
            $(".ztree").height($(window).height() - 163);

        });
    });
    //左侧校友会数据填充
    function initTree() {
        _$.ajax({
            url: application.SERVE_URL + '/as/asAssociation/findAsByUser',
            type: "post",
            success: function(data) {
                treeObj = $.fn.zTree.init($("#orgTree"), setting, covert(data.data)); //加载数据
                //初始化
                var nodeList = treeObj.getNodes();
                //展开第一个根节点
                treeObj.expandNode(nodeList[0], true);
                treeObj.setting.callback.onClick(null, treeObj.setting.treeId, nodeList[0]); //调用事件	
            }
        });
    }
    //校友会点击事件
    function orgTreeonClick(event, treeId, treeNode, clickFlag) {
        orgId = treeNode.id;
        /**
         * 校友管理列表
         */
        console.log(orgId);
        tableIns = table.render({
            elem: '#smList',
            url: application.SERVE_URL + '/sm/smSchoolmate/list',
            where: {
                smSchoolmate: {
                    type: $("#type").val() == "" ? null : $("#type").val(),
                    orgId: orgId,
                    name: $("#name").val() == "" ? null : $("#name").val().trim(),
                    sex: $("#sex").val() == "" ? null : $("#sex").val(),
                    birthday: $("#birthday").val() == "" ? null : $("#birthday").val(),
                    nation: $("#nation").val() == "" ? null : $("#nation").val(),
                    cardType: $("#cardType").val() == "" ? null : $("#cardType").val(),
                    cardNum: $("#cardNum").val() == "" ? null : $("#cardNum").val().trim(),
                    politics: $("#politics").val() == "" ? null : $("#politics").val(),
                    smContact: {
                        type: $("#contact_type").val() == "" ? null : $("#contact_type").val(),
                        contact: $("#contact").val() == "" ? null : $("#contact").val().trim()
                    },
                    smAddress: {
                        country: $("#country").val() == "" ? null : $("#country").val(),
                        province: $("#province").val() == "" ? null : $("#province").val(),
                        city: $("#city").val() == "" ? null : $("#city").val(),
                        detail: $("#detail").val() == "" ? null : $("#detail").val(),
                        type: $("#addr_type").val() == "" ? null : $("#addr_type").val()
                    },
                    smEducation: {
                        school: $("#school").val() == "" ? null : $("#school").val(),
                        college: $("#college").val() == "" ? null : $("#college").val(),
                        series: $("#department").val() == "" ? null : $("#department").val(),
                        specialty: $("#major").val() == "" ? null : $("#major").val(),
                        degree: $("#edu_degree").val() == "" ? null : $("#edu_degree").val(),
                        eduRecord: $("#edu_record").val() == "" ? null : $("#edu_record").val(),
                        classes:  $("#edu_classes").val() == "" ? null : $("#edu_classes").val().trim(),
                        studentNo:  $("#edu_studentNo").val() == "" ? null : $("#edu_studentNo").val().trim(),
                        schoollen: $("#edu_schoollen").val() == "" ? null : $("#edu_schoollen")
                            .val(),
                        startYearInternalFirst: $("#start_startdata").val() == "" ? null : $(
                            "#start_startdata").val().trim(),
                        startYearInternalSencond: $("#end_startdata").val() == "" ? null : $(
                            "#end_startdata").val().trim(),
                        endYearInternalFirst: $("#start_enddata").val() == "" ? null : $(
                            "#start_enddata").val().trim(),
                        endYearInternalSencond: $("#end_enddata").val() == "" ? null : $(
                            "#end_enddata").val().trim()
                    },
                    smProfession: {
                        country: $("#profession_country").val() == "" ? null : $(
                            "#profession_country").val(),
                        province: $("#profession_province").val() == "" ? null : $(
                            "#profession_province").val(),
                        city: $("#profession_city").val() == "" ? null : $(
                            "#profession_city").val(),
                        workplace: $("#profession_workplace").val() == "" ? null : $(
                            "#profession_workplace").val(),
                        nature: $("#profession_nature").val() == "" ? null : $(
                            "#profession_nature").val(),
                        industry: $("#industryId").val() == "" ? null : $(
                            "#industryId").val(),
                        position : $("#profession_position").val() == "" ? null : $(
                            "#profession_position").val(),
                        status: $("#profession_status").val() == "" ? null : $(
                            "#profession_status").val(),
                        endYearInternalFirst: $("#profession_start_enddata").val() == "" ? null : $(
                            "#profession_start_enddata").val().trim(),
                        endYearInternalSencond: $("#profession_end_enddata").val() == "" ? null : $(
                            "#profession_end_enddata").val().trim(),
                        startYearInternalFirst: $("#profession_start_startdata").val() == "" ? null : $(
                            "#profession_start_startdata").val().trim(),
                        startYearInternalSencond: $("#profession_end_startdata").val() == "" ? null : $(
                            "#profession_end_startdata").val().trim()
                    },
                    smSocial: {
                        name: $("#smSocial_name").val() == "" ? null : $("#smSocial_name").val().trim(),
                    },
                    smHonor: {
                        name: $("#honor_name").val() == "" ? null : $("#honor_name").val().trim(),
                        startDate:$("#honor_start_date").val() == "" ? null : $(
                            "#honor_start_date").val().trim(),
                        endDate: $("#honor_end_date").val() == "" ? null : $(
                            "#honor_end_date").val().trim()
                    },
                    smHistorydata: {
                        otherSchool: $("#otherSchool").val() == "" ? null : $("#otherSchool").val().trim(),
                        otherSpecialty: $("#otherSpecialty").val() == "" ? null : $(
                            "#otherSpecialty").val().trim(),
                        trustUnit: $("#trustUnit").val() == "" ? null : $("#trustUnit").val().trim(),
                        beforeSchoolunit: $("#beforeSchoolunit").val() == "" ? null : $("#beforeSchoolunit").val().trim(),
                    },
                    // smOtherInfo:{
                    // 	otherId: $("#other_name").val() == "" ? null : $("#other_name").val(),
                    // 	value: $("#other_value").val() == "" ? null : $("#other_value").val()
                    // },
                    // smHisEducation:{
                    // 	education: $("#education").val() == "" ? null : $("#education").val(),
                    // 	type: $("#hisE_type").val() == "" ? null : $("#hisE_type").val()
                    // },
                    markIds: $("#markId").val() == "" ? null : $("#markId").val()
                }
            },
            //生产坏境下请求后台                   
            cellMinWidth: 75,
            page: true,
            even: true,
            loading: true,
            headers: {
                'Authorization': application.HEADER
            },
            contentType: "application/json",
            height: "full-193",
            limit: 10,
            id: "smList",
            cols: [
                [
                    //姓名，性别，证件号码， 院，系，专业，工作单位，状态，操作
                    // {field: 'id', title: 'ID', align:"center"},
                    {
                        type: 'checkbox',
                        width: 50
                    },
                    {
                        field: 'name',
                        title: '姓名',
                        width: 100
                    },
                    {
                        field: 'sex',
                        title: '性别',
                        width: 80

                    },
                    {
                        field: 'birthday',
                        title: '出生年月',
                        width: 120
                        
                    },
                    {
                        field: 'startdate',
                        title: '入学日期',
                        templet: function(d) {
                            var res = d.smEducation;
                            var htm = "";
                            if (null != res && null != res && null != res.startdate && res.startdate !=0) {
                                htm = "<span>" + res.startdate + "</span>";
                            }
                            return htm;
                        },
                        width: 120
                    },
                    {
                        field: 'eduRecord',
                        title: '学历',
                        templet: function(d) {
                            var res = d.smEducation;
                            var htm = "";
                            if (null != res && null != res && null != res.eduRecord && res.eduRecord.length !=0) {
                                htm = res.eduRecord;
                            }
                            return htm;
                        },
                        width: 160
                    },
                    {
                        field: 'college',
                        title: '学院',
                        templet: function(d) {
                            var res = d.smEducation;
                            var htm = "";
                            if (null != res && null != res && null != res.college && res.college.length !=0) {
                                var collegeValue = publicUtil.getObjFromStorageById(
                                    "departBaseCache", res.college);
                                htm = "<span>" + collegeValue + "</span>";
                            }
                            return htm;
                        },
                        width: 160
                    },
                    {
                        field: 'specialty',
                        title: '专业',
                        templet: function(d) {
                            var res = d.smEducation;
                            var htm = "";
                            if (null != res && null != res && null != res.specialty && res.specialty.length !=0) {
                                var specialtyValue = publicUtil.getObjFromStorageById(
                                    "departBaseCache", res.specialty);
                                htm = "<span>" + specialtyValue + "</span>";
                            }
                            return htm;
                        },
                        width: 160
                    },
                    {
                        field: 'workplace',
                        title: '工作单位',
                        templet: function(d) {
                            var res = d.smProfession;
                            var htm = "";
                            if (null != res && null != res && null != res.workplace) {

                                htm = "<span>" + res.workplace + "</span>";
                            }
                            return htm;
                        },
                        width: 160
                    },
                    {
                        field: 'marks',
                        title: '标签',
                        templet: function(d) {
                            var res = d.marks;
                            var htm = "";
                            if (res != null && res != "") {
                                var data = res.split(",");
                                var htm = "";
                                for (var i = 0; i < data.length; i++) {
                                    if (data[i] != "校友标签") {
                                        htm = htm +
                                            "<span class='layui-badge-rim layui-bg-blue'>" +
                                            data[i] + "</span>";
                                    }
                                }
                            }
                            return htm;
                        }
                    },
                ]
            ],
            done: function(res, curr, count) { //res 接口返回的信息,,
                publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                    'typeCode': 'EDU_RECORD'
                }, 'eduRecord');
                publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                    'typeCode': 'SEX'
                }, 'sex');
                publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                    'typeCode': 'SCHOOLEMATE_TYPE'
                }, 'type');
                publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                    'typeCode': 'NATION'
                }, 'nation');
            },
        });
        table.on('rowDouble(smList)', function() {
            var flag = publicUtil.jurgeSelectRows(table.checkStatus('smList').data);
            if (flag) {
                layer.open({
                    title: "校友简历",
                    type: 2,
                    area: ["900px", "700px"],
                    content: [
                        '../../../../views/module/schoolmate/schoolmate/schoolmateinfo.html',
                        'no'
                    ],
                    success: function() {
                        sessionStorage.setItem("userId", table.checkStatus('smList').data[0]
                            .id);
                    }
                })
            }

        });
    }
    //校友类型点击事件
    form.on('select(sm_type)', function(data) {
        table.reload("smList", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            loading: true,
            where: {
                smSchoolmate: {
                    type: $("#type").val() == "" ? null : $("#type").val(),
                    orgId: orgId,
                    name: $("#name").val() == "" ? null : $("#name").val().trim(),
                    sex: $("#sex").val() == "" ? null : $("#sex").val(),
                    birthday: $("#birthday").val() == "" ? null : $("#birthday").val(),
                    nation: $("#nation").val() == "" ? null : $("#nation").val(),
                    cardType: $("#cardType").val() == "" ? null : $("#cardType").val(),
                    cardNum: $("#cardNum").val() == "" ? null : $("#cardNum").val().trim(),
                    politics: $("#politics").val() == "" ? null : $("#politics").val(),
                    smContact: {
                        type: $("#contact_type").val() == "" ? null : $("#contact_type").val(),
                        contact: $("#contact").val() == "" ? null : $("#contact").val().trim()
                    },
                    smAddress: {
                        country: $("#country").val() == "" ? null : $("#country").val(),
                        province: $("#province").val() == "" ? null : $("#province").val(),
                        city: $("#city").val() == "" ? null : $("#city").val(),
                        detail: $("#detail").val() == "" ? null : $("#detail").val(),
                        type: $("#addr_type").val() == "" ? null : $("#addr_type").val()
                    },
                    smEducation: {
                        school: $("#school").val() == "" ? null : $("#school").val(),
                        college: $("#college").val() == "" ? null : $("#college").val(),
                        series: $("#department").val() == "" ? null : $("#department").val(),
                        specialty: $("#major").val() == "" ? null : $("#major").val(),
                        degree: $("#edu_degree").val() == "" ? null : $("#edu_degree").val(),
                        eduRecord: $("#edu_record").val() == "" ? null : $("#edu_record").val(),
                        classes:  $("#edu_classes").val() == "" ? null : $("#edu_classes").val().trim(),
                        studentNo:  $("#edu_studentNo").val() == "" ? null : $("#edu_studentNo").val().trim(),
                        schoollen: $("#edu_schoollen").val() == "" ? null : $("#edu_schoollen")
                            .val(),
                        startYearInternalFirst: $("#start_startdata").val() == "" ? null : $(
                            "#start_startdata").val().trim(),
                        startYearInternalSencond: $("#end_startdata").val() == "" ? null : $(
                            "#end_startdata").val().trim(),
                        endYearInternalFirst: $("#start_enddata").val() == "" ? null : $(
                            "#start_enddata").val().trim(),
                        endYearInternalSencond: $("#end_enddata").val() == "" ? null : $(
                            "#end_enddata").val().trim()
                    },
                    smProfession: {
                        country: $("#profession_country").val() == "" ? null : $(
                            "#profession_country").val(),
                        province: $("#profession_province").val() == "" ? null : $(
                            "#profession_province").val(),
                        city: $("#profession_city").val() == "" ? null : $(
                            "#profession_city").val(),
                        workplace: $("#profession_workplace").val() == "" ? null : $(
                            "#profession_workplace").val(),
                        nature: $("#profession_nature").val() == "" ? null : $(
                            "#profession_nature").val(),
                        industry: $("#industryId").val() == "" ? null : $(
                            "#industryId").val(),
                        position : $("#profession_position").val() == "" ? null : $(
                            "#profession_position").val(),
                        status: $("#profession_status").val() == "" ? null : $(
                            "#profession_status").val(),
                        endYearInternalFirst: $("#profession_start_enddata").val() == "" ? null : $(
                            "#profession_start_enddata").val().trim(),
                        endYearInternalSencond: $("#profession_end_enddata").val() == "" ? null : $(
                            "#profession_end_enddata").val().trim(),
                        startYearInternalFirst: $("#profession_start_startdata").val() == "" ? null : $(
                            "#profession_start_startdata").val().trim(),
                        startYearInternalSencond: $("#profession_end_startdata").val() == "" ? null : $(
                            "#profession_end_startdata").val().trim()
                    },
                    smSocial: {
                        name: $("#smSocial_name").val() == "" ? null : $("#smSocial_name").val().trim(),
                    },
                    smHonor: {
                        name: $("#honor_name").val() == "" ? null : $("#honor_name").val().trim(),
                        startDate:$("#honor_start_date").val() == "" ? null : $(
                            "#honor_start_date").val().trim(),
                        endDate: $("#honor_end_date").val() == "" ? null : $(
                            "#honor_end_date").val().trim()
                    },
                    smHistorydata: {
                        otherSchool: $("#otherSchool").val() == "" ? null : $("#otherSchool").val().trim(),
                        otherSpecialty: $("#otherSpecialty").val() == "" ? null : $(
                            "#otherSpecialty").val().trim(),
                        trustUnit: $("#trustUnit").val() == "" ? null : $("#trustUnit").val().trim(),
                        beforeSchoolunit: $("#beforeSchoolunit").val() == "" ? null : $("#beforeSchoolunit").val().trim(),
                    },
                    // smOtherInfo:{
                    // 	otherId: $("#other_name").val() == "" ? null : $("#other_name").val(),
                    // 	value: $("#other_value").val() == "" ? null : $("#other_value").val()
                    // },
                    // smHisEducation:{
                    // 	education: $("#education").val() == "" ? null : $("#education").val(),
                    // 	type: $("#hisE_type").val() == "" ? null : $("#hisE_type").val()
                    // },
                    markIds: $("#markId").val() == "" ? null : $("#markId").val()
                }
            }
        })
    });

    //校友标签查询ztree设置
    var selectSetting = {
        view: {
            showLine: false,
            showIcon: false,
            selectedMulti: false,
            dblClickExpand: false,
            // addDiyDom: addDiyDom
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId"
            },

        },
        check: {
            enable: true,
            chkStyle: "radio",
            radioType: "all"
        },
        callback: {
            // onClick: selectOnCheck
            onCheck: selectOnCheck
        }
    };

    _$.ajax({
        url: application.SERVE_URL + '/sm/smMark/tree', //ajax请求地址
        data: {
        },
        success: function(res) {
            var selectTree = $.fn.zTree.init($("#markTree"), selectSetting, res); //加载数据
            var nodeList = selectTree.getNodes(); //展开第一个根节点
            selectTree.expandNode(nodeList[0], true);
        }
    });

    //行业tree设置
    //校友标签查询ztree设置
    var industryTreeSetting = {
        view: {
            showLine: false,
            showIcon: false,
            selectedMulti: false,
            dblClickExpand: false,
            // addDiyDom: addDiyDom
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId"
            },

        },
        check: {
            enable: true,
            chkStyle: "radio",
            radioType: "all"
        },
        callback: {
            // onClick: selectOnCheck
            onCheck: industryTreeOnCheck
        }
    };

    _$.ajax({
        url: application.SERVE_URL + '/sys/industry/tree', //ajax请求地址
        data: {
        },
        success: function(res) {
            var selectTree = $.fn.zTree.init($("#industryIdTree"), industryTreeSetting, res.data); //加载数据
            var nodeList = selectTree.getNodes(); //展开第一个根节点
            selectTree.expandNode(nodeList[0], true);
        }
    });

    //右键点击事件
    table.on('rowRight(smList)', function(obj) {
        publicUtil.show_menu(obj);
    });

    //左键点击事件
    table.on('row(smList)', function(obj) {
        publicUtil.hiddenMenu(obj);
    });

    //新增操作
    _$(document).on('click', '.PER_ADD', function() {
        addSm();
    });



    //校友合并功能
    _$(document).on('click', '.PER_SM_MERGE', function() {
        var flag = publicUtil.jurgeSelectRows(table.checkStatus('smList').data);
        if (flag) {
            selectUserId = table.checkStatus('smList').data[0];
            var index = layui.layer.open({
                type: 2,
                title: '选择将要合并的目标校友',
                shadeClose: true,
                shade: 0.8,
                area: ['90%', '90%'],
                content: 'userselect.html',
                success: function(layero, index) {
                    //
                    setTimeout(function() {
                        layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
                            tips: 3
                        });
                    }, 500)
                },
            })
        }else{
            return false;
        }
        
    })

    //审核校友卡
    _$(document).on('click', '.PER_AUDITING', function() {
        var flag = publicUtil.jurgeSelectRows(table.checkStatus('smList').data);
        if (flag) {
            _$.ajax({
                url: application.SERVE_URL + '/sm/smSchoolmate/get',
                data: {
                    "id": table.checkStatus('smList').data[0].id
                }, //ajax请求地址
                success: function(res) {
                    formdatas = res.data;
                    var index = layui.layer.open({
                        type: 2,
                        title: '校友卡审核',
                        shadeClose: true,
                        shade: 0.8,
                        area: ['400px', '30%'],
                        content: '/views/module/schoolmate/schoolmate/cardStatu.html',
                        success: function(layero, index) {
                            setTimeout(function() {
                                layui.layer.tips('点击此处返回',
                                    '.layui-layer-setwin .layui-layer-close', {
                                        tips: 3
                                    });
                            }, 500)
                        },
                    })
                }
            });
        } else {
            return false;
        }
    });

    //设置标签
    _$(document).on('click', '.PER_SETMARK', function() {
        var flag = publicUtil.jurgeSelectRows(table.checkStatus('smList').data);
        selectTabRowId = table.checkStatus('smList').data[0].id;
        if (flag) {
            _$.ajax({
                url: application.SERVE_URL + '/sm/smSchoolmate/getMarksBy',
                data: {
                    userId: table.checkStatus('smList').data[0].userId
                }, //ajax请求地址
                success: function(res) {
                    formdatas = res.data;
                    var index = layui.layer.open({
                        type: 2,
                        title: '标签选择',
                        shadeClose: true,
                        shade: 0.8,
                        area: ['280px', '65%'],
                        content: '/views/module/schoolmate/mark/markselect.html',
                        success: function(layero, index) {
                            setTimeout(function() {
                                layui.layer.tips('点击此处返回',
                                    '.layui-layer-setwin .layui-layer-close', {
                                        tips: 3
                                    });
                            }, 500)
                        },
                    })
                }
            });

        } else {
            return false;
        }
    });

    //编辑操作
    _$(document).on('click', '.PER_EDIT', function() {
        var flag = publicUtil.jurgeSelectRows(table.checkStatus('smList').data);
        if (flag) {
            addSm(table.checkStatus('smList').data[0]);
        } else {
            return false;
        }
    })

    //删除
    _$(document).on('click', '.PER_DEL', function() {
        var flag = publicUtil.jurgeSelectRows(table.checkStatus('smList').data);
        if (flag) {
            _$.ajax({
                url: application.SERVE_URL + "/sm/smFamous/loadAllListBy", //ajax请求地址
                data: {
                    userId: table.checkStatus('smList').data[0].userId
                },
                success: function(res) {
                    if (res.code == application.REQUEST_SUCCESS) {
                        if (res.data.length != 0) {
                            top.layer.msg("该校友为知名校友不能直接删除！");
                        } else {
                            layer.confirm('确定删除此校友吗？', {
                                icon: 3,
                                title: '提示信息'
                            }, function(index) {
                                _$.ajax({
                                    url: application.SERVE_URL +
                                        "/sm/smSchoolmate/delete", //ajax请求地址
                                    data: {
                                        userId: table.checkStatus('smList').data[
                                            0].userId
                                    },
                                    success: function(res) {
                                        if (res.code == application.REQUEST_SUCCESS) {
                                            tableIns.reload();
                                            // location.reload();
                                            layer.close(index);
                                            top.layer.msg(res.msg);
                                        } else {
                                            top.layer.msg(res.msg);
                                        }

                                    }
                                });
                            });
                        }
                    } else {
                        top.layer.msg(res.msg);

                    }
                }
            });
        } else {
            return false;
        }
    })

    //导入
    var str = "导入需要先下载模板<br><p style='color:red;'>提示：仅允许导入'xls'或'xlsx'格式文件！</p>" +
        "<blockquote class='layui-elem-quote layui-quote-nm'><br><br>日常信息：<button class='layui-btn' id='template'>下载模板</button><input type='file' id='upfile_template' class='layui-btn' value='上传文件'/> <button class='layui-btn' id='templateExp'>导入</span></button></blockquote>" +
        "<blockquote class='layui-elem-quote layui-quote-nm'><br><br>新生信息：<button class='layui-btn'   id='newtemplate'>下载模板</button><input type='file' id='upfile_template_new' class='layui-btn' value='上传文件'/> <button class='layui-btn' id='newtemplateExp'>导入</span></button></blockquote>" +
        "<blockquote class='layui-elem-quote layui-quote-nm'><br><br>就业信息：<button class='layui-btn' id='worktemplate'>下载模板</button><input type='file' id='upfile_template_work'  class='layui-btn' value='上传文件'/> <button class='layui-btn' id='worktemplateExp'>导入</span></button>"
    "</blockquote>";
    //导入
    _$(document).on('click', '.PER_IMPORT', function() {
        layer.open({
            content: str,
            btn: ['退出'],
            area: ["800px", "600px"],
            cancel: function() {
                //关闭
            }
        });
    });
    //下载点击事件
    _$(document).on('click', '#template', function() {
        window.location.href = application.SERVE_URL + "/static/daily.xlsx";
        return false;
    });
    _$(document).on('click', '#newtemplate', function() {
        window.location.href = application.SERVE_URL + "/static/newStudents.xlsx";
        return false;
    });
    _$(document).on('click', '#worktemplate', function() {
        window.location.href = application.SERVE_URL + "/static/graduates.xlsx";
        return false;
    });
    //导入点击事件
    _$(document).on('click', '#templateExp', function() {
        uploadFile("upfile_template", application.SERVE_URL + "/sm/smSchoolmate/importsmdatas", "DAILY")
        return false;
    });
    _$(document).on('click', '#newtemplateExp', function() {
        uploadFile("upfile_template_new", application.SERVE_URL + "/sm/smSchoolmate/importsmdatas",
            "NEWPUPIL")
        return false;
    });
    _$(document).on('click', '#worktemplateExp', function() {
        uploadFile("upfile_template_work", application.SERVE_URL + "/sm/smSchoolmate/importsmdatas",
            "EMPLOY")
        return false;
    });

    function uploadFile(fileid, url, type) {
        var formdata = new FormData; //创建一个FormData对象  
        formdata.append("type", type);
        formdata.append('file', _$("#" + fileid).get(0).files[0]);
        if (_$("#" + fileid).get(0).files[0] == "undefined" || _$("#" + fileid).get(0).files[0] == undefined) {
            layer.msg("请选择文件后再进行上传");
            return false;
        }
        var filePath = $("#" + fileid).val().toLowerCase().split(".");
        var fileType = filePath[filePath.length - 1];
        if ('xls' != fileType && 'xlsx' != fileType) {
            layer.msg("请选择正确的格式进行导入");
            return false;
        } else {
            var loadingindex = layer.msg("写入中...,请勿操作", {
                icon: 16,
                shade: 0.3,
                time: 3000 * 1000
            });
            _$.ajax({
                url: url,
                type: "POST",
                dataType: "json",
                data: formdata,
                timeout: 30000000,
                // async:true,
                processData: false,
                contentType: false,
                success: function(res) {
                    if (res.code == application.REQUEST_SUCCESS) {
                        layer.close(loadingindex);
                        top.layer.msg(res.msg);
                        location.reload();
                    } else if(res.code == application.REQUEST_ERROR){
                        //捕获页
                        layer.close(loadingindex);
                        layer.confirm(res.msg + '是否下载导入失败的数据？', {
                            btn: ['下载', '跳过'] //按钮
                        }, function() {
                            window.location.href = application.SERVE_URL + res.data;
                        }, function() {
                            location.reload();
                        });
                    }else{
                        top.layer.msg(res.msg);
                        top.layer.msg(res.data);
                    }
                },
                error: function(res) { // 出错时默认的处理函数
                    layer.msg(res.msg);
                },
                complete: function(res) { //请求完成后最终执行参数
                }
            });
        }
    }

    var  exportFlag;
    // 导出
    _$(document).on('click', '.PER_EXPORT', function() {
        var fuzzySearchFiled = $("#selectAllValue").val();
        console.log(orgId);
        layer.open({
            title: "导出信息",
            type: 2,
            area: ["500px", "300px"],
            data: {
                fuzzySearchFiled: sessionStorage.setItem("fuzzySearchFiled", fuzzySearchFiled),
                smSchoolmate: sessionStorage.setItem("smSchoolmate", JSON.stringify({
                    type: $("#type").val() == "" ? null : $("#type").val(),
                    orgId: orgId,
                    name: $("#name").val() == "" ? null : $("#name").val().trim(),
                    sex: $("#sex").val() == "" ? null : $("#sex").val(),
                    birthday: $("#birthday").val() == "" ? null : $("#birthday").val(),
                    nation: $("#nation").val() == "" ? null : $("#nation").val(),
                    cardType: $("#cardType").val() == "" ? null : $("#cardType").val(),
                    cardNum: $("#cardNum").val() == "" ? null : $("#cardNum").val().trim(),
                    politics: $("#politics").val() == "" ? null : $("#politics").val(),
                    smContact: {
                        type: $("#contact_type").val() == "" ? null : $("#contact_type").val(),
                        contact: $("#contact").val() == "" ? null : $("#contact").val().trim()
                    },
                    smAddress: {
                        country: $("#country").val() == "" ? null : $("#country").val(),
                        province: $("#province").val() == "" ? null : $("#province").val(),
                        city: $("#city").val() == "" ? null : $("#city").val(),
                        detail: $("#detail").val() == "" ? null : $("#detail").val(),
                        type: $("#addr_type").val() == "" ? null : $("#addr_type").val()
                    },
                    smEducation: {
                        school: $("#school").val() == "" ? null : $("#school").val(),
                        college: $("#college").val() == "" ? null : $("#college").val(),
                        series: $("#department").val() == "" ? null : $("#department").val(),
                        specialty: $("#major").val() == "" ? null : $("#major").val(),
                        degree: $("#edu_degree").val() == "" ? null : $("#edu_degree").val(),
                        eduRecord: $("#edu_record").val() == "" ? null : $("#edu_record").val(),
                        classes:  $("#edu_classes").val() == "" ? null : $("#edu_classes").val().trim(),
                        studentNo:  $("#edu_studentNo").val() == "" ? null : $("#edu_studentNo").val().trim(),
                        schoollen: $("#edu_schoollen").val() == "" ? null : $("#edu_schoollen")
                            .val(),
                        startYearInternalFirst: $("#start_startdata").val() == "" ? null : $(
                            "#start_startdata").val().trim(),
                        startYearInternalSencond: $("#end_startdata").val() == "" ? null : $(
                            "#end_startdata").val().trim(),
                        endYearInternalFirst: $("#start_enddata").val() == "" ? null : $(
                            "#start_enddata").val().trim(),
                        endYearInternalSencond: $("#end_enddata").val() == "" ? null : $(
                            "#end_enddata").val().trim()
                    },
                    smProfession: {
                        country: $("#profession_country").val() == "" ? null : $(
                            "#profession_country").val(),
                        province: $("#profession_province").val() == "" ? null : $(
                            "#profession_province").val(),
                        city: $("#profession_city").val() == "" ? null : $(
                            "#profession_city").val(),
                        workplace: $("#profession_workplace").val() == "" ? null : $(
                            "#profession_workplace").val(),
                        nature: $("#profession_nature").val() == "" ? null : $(
                            "#profession_nature").val(),
                        industry: $("#industryId").val() == "" ? null : $(
                            "#industryId").val(),
                        position : $("#profession_position").val() == "" ? null : $(
                            "#profession_position").val(),
                        status: $("#profession_status").val() == "" ? null : $(
                            "#profession_status").val(),
                        endYearInternalFirst: $("#profession_start_enddata").val() == "" ? null : $(
                            "#profession_start_enddata").val().trim(),
                        endYearInternalSencond: $("#profession_end_enddata").val() == "" ? null : $(
                            "#profession_end_enddata").val().trim(),
                        startYearInternalFirst: $("#profession_start_startdata").val() == "" ? null : $(
                            "#profession_start_startdata").val().trim(),
                        startYearInternalSencond: $("#profession_end_startdata").val() == "" ? null : $(
                            "#profession_end_startdata").val().trim()
                    },
                    smSocial: {
                        name: $("#smSocial_name").val() == "" ? null : $("#smSocial_name").val().trim(),
                    },
                    smHonor: {
                        name: $("#honor_name").val() == "" ? null : $("#honor_name").val().trim(),
                        startDate:$("#honor_start_date").val() == "" ? null : $(
                            "#honor_start_date").val().trim(),
                        endDate: $("#honor_end_date").val() == "" ? null : $(
                            "#honor_end_date").val().trim()
                    },
                    smHistorydata: {
                        otherSchool: $("#otherSchool").val() == "" ? null : $("#otherSchool").val().trim(),
                        otherSpecialty: $("#otherSpecialty").val() == "" ? null : $(
                            "#otherSpecialty").val().trim(),
                        trustUnit: $("#trustUnit").val() == "" ? null : $("#trustUnit").val().trim(),
                        beforeSchoolunit: $("#beforeSchoolunit").val() == "" ? null : $("#beforeSchoolunit").val().trim(),
                    },
                    // smOtherInfo:{
                    // 	otherId: $("#other_name").val() == "" ? null : $("#other_name").val(),
                    // 	value: $("#other_value").val() == "" ? null : $("#other_value").val()
                    // },
                    // smHisEducation:{
                    // 	education: $("#education").val() == "" ? null : $("#education").val(),
                    // 	type: $("#hisE_type").val() == "" ? null : $("#hisE_type").val()
                    // },
                    markIds: $("#markId").val() == "" ? null : $("#markId").val()
                }))
            },
            content: [
                '../../../../views/module/schoolmate/schoolmate/schoolmateInfoSelectExport.html',
                'no'
            ]
        })
    });

    //回车事件
    $(document).keydown(function (e) {
        if (e.keyCode === 13) {
            if($(".opa").css("display") == 'block'){
                $("#searchForm").trigger("click");
                return false;
            }else if($(".opa").css("display") == 'none'){
                $("#searchAllForm").trigger("click");
                return false;
            }
            
        }
    });

    //搜索 规定的全字段搜索
    form.on("submit(searchAllForm)", function(data) {
        //console.error("进入全字段查询");
        exportFlag = 2;
        sessionStorage.setItem("exportFlag",exportFlag);
        tableIns = table.render({
            elem: '#smList',
            url: application.SERVE_URL + '/sm/smSchoolmate/listSelect',
            where: {
                smSchoolmate: {
                    type: $("#type").val() == "" ? null : $("#type").val(),
                    name: $("#selectAllValue").val() == "" ? null : $("#selectAllValue").val().replace(/\s+/g,"")
                }
            },
            //生产坏境下请求后台                   
            cellMinWidth: 75,
            page: true,
            even: true,
            loading: true,
            headers: {
                'Authorization': application.HEADER
            },
            contentType: "application/json",
            height: "full-193",
            limit: 10,
            id: "smList",
            cols: [
                [
                    //姓名，性别，证件号码， 院，系，专业，工作单位，状态，操作
                    {
                        type: 'checkbox',
                        width: 50
                    },
                    {
                        field: 'name',
                        title: '姓名',
                        width: 100
                        
                    },
                    {
                        field: 'sex',
                        title: '性别',
                        width: 80

                    },
                    {
                        field: 'birthday',
                        title: '出生年月',
                        width: 120
                        
                    },
                    {
                        field: 'startdate',
                        title: '入学日期',
                        templet: function(d) {
                            var res = d.smEducation;
                            var htm = "";
                            if (null != res && null != res && null != res.startdate && res.startdate !=0) {
                                htm = "<span>" + res.startdate + "</span>";
                            }
                            return htm;
                        },
                        width: 120

                    },
                    {
                        field: 'eduRecord',
                        title: '学历',
                        templet: function(d) {
                            var res = d.smEducation;
                            var htm = "";
                            if (null != res && null != res && null != res.eduRecord && res.eduRecord.length !=0) {
                                htm = res.eduRecord;
                            }
                            return htm;
                        },
                        width: 160
                    },
                    {
                        field: 'college',
                        title: '学院',
                        templet: function(d) {
                            var res = d.smEducation;
                            var htm = "";
                            if (null != res && null != res && null != res.college && res.college.length !=0) {
                                var collegeValue = publicUtil.getObjFromStorageById(
                                    "departBaseCache", res.college);
                                htm = "<span>" + collegeValue + "</span>";
                            }
                            return htm;
                        },
                        width: 160
                        
                    },
                    {
                        field: 'specialty',
                        title: '专业',
                        templet: function(d) {
                            var res = d.smEducation;
                            var htm = "";
                            if (null != res && null != res && null != res.specialty && res.specialty.length !=0) {
                                var specialtyValue = publicUtil.getObjFromStorageById(
                                    "departBaseCache", res.specialty);
                                htm = "<span>" + specialtyValue + "</span>";
                            }
                            return htm;
                        },
                        width: 160
                    },
                    {
                        field: 'workplace',
                        title: '工作单位',
                        templet: function(d) {
                            var res = d.smProfession;
                            var htm = "";
                            if (null != res && null != res && null != res.workplace) {

                                htm = "<span>" + res.workplace + "</span>";
                            }
                            return htm;
                        },
                        width: 160
                    },
                    /* 	{field: 'birthday', title: '生日'},
                    	{field: 'cardStatus', title: '卡状态'}, */
                    {
                        field: 'marks',
                        title: '标签',
                        templet: function(d) {
                            var res = d.marks;
                            var htm = "";
                            if (res != null && res != "") {
                                var data = res.split(",");
                                var htm = "";
                                for (var i = 0; i < data.length; i++) {
                                    if (data[i] != "校友标签") {
                                        htm = htm +
                                            "<span class='layui-badge-rim layui-bg-blue'>" +
                                            data[i] + "</span>";
                                    }
                                }
                            }
                            return htm;
                        }
                    },
                ]
            ],
            done: function(res, curr, count) { //res 接口返回的信息,,
                publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                    'typeCode': 'EDU_RECORD'
                }, 'eduRecord');
                publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                    'typeCode': 'SEX'
                }, 'sex');
                publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                    'typeCode': 'SCHOOLEMATE_TYPE'
                }, 'type');
                publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                    'typeCode': 'NATION'
                }, 'nation');
                // publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'COLLEGE'},'college');
                // publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'DEPARTMENT'},'series');
                // publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'MAJOR'},'specialty');
                // layer.close(table_index);
            },

        })
        return false;
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    form.on("submit(searchForm)", function(data) {
        exportFlag = 1;
        sessionStorage.setItem("exportFlag",exportFlag);
        table.reload("smList", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            url: application.SERVE_URL + '/sm/smSchoolmate/list',
            where: {
                // birthday: $("#birthday").val() == "" ? null : $("#birthday").val(),
                smSchoolmate: {
                    type: $("#type").val() == "" ? null : $("#type").val(),
                    orgId: orgId,
                    name: $("#name").val() == "" ? null : $("#name").val().trim(),
                    sex: $("#sex").val() == "" ? null : $("#sex").val(),
                    birthday: $("#birthday").val() == "" ? null : $("#birthday").val(),
                    nation: $("#nation").val() == "" ? null : $("#nation").val(),
                    cardType: $("#cardType").val() == "" ? null : $("#cardType").val(),
                    cardNum: $("#cardNum").val() == "" ? null : $("#cardNum").val().trim(),
                    politics: $("#politics").val() == "" ? null : $("#politics").val(),
                    smContact: {
                        type: $("#contact_type").val() == "" ? null : $("#contact_type").val(),
                        contact: $("#contact").val() == "" ? null : $("#contact").val().trim()
                    },
                    smAddress: {
                        country: $("#country").val() == "" ? null : $("#country").val(),
                        province: $("#province").val() == "" ? null : $("#province").val(),
                        city: $("#city").val() == "" ? null : $("#city").val(),
                        detail: $("#detail").val() == "" ? null : $("#detail").val(),
                        type: $("#addr_type").val() == "" ? null : $("#addr_type").val()
                    },
                    smEducation: {
                        school: $("#school").val() == "" ? null : $("#school").val(),
                        college: $("#college").val() == "" ? null : $("#college").val(),
                        series: $("#department").val() == "" ? null : $("#department").val(),
                        specialty: $("#major").val() == "" ? null : $("#major").val(),
                        degree: $("#edu_degree").val() == "" ? null : $("#edu_degree").val(),
                        eduRecord: $("#edu_record").val() == "" ? null : $("#edu_record").val(),
                        classes:  $("#edu_classes").val() == "" ? null : $("#edu_classes").val().trim(),
                        studentNo:  $("#edu_studentNo").val() == "" ? null : $("#edu_studentNo").val().trim(),
                        schoollen: $("#edu_schoollen").val() == "" ? null : $("#edu_schoollen")
                            .val(),
                        startYearInternalFirst: $("#start_startdata").val() == "" ? null : $(
                            "#start_startdata").val().trim(),
                        startYearInternalSencond: $("#end_startdata").val() == "" ? null : $(
                            "#end_startdata").val().trim(),
                        endYearInternalFirst: $("#start_enddata").val() == "" ? null : $(
                            "#start_enddata").val().trim(),
                        endYearInternalSencond: $("#end_enddata").val() == "" ? null : $(
                            "#end_enddata").val().trim()
                    },
                    smProfession: {
                        country: $("#profession_country").val() == "" ? null : $(
                            "#profession_country").val(),
                        province: $("#profession_province").val() == "" ? null : $(
                            "#profession_province").val(),
                        city: $("#profession_city").val() == "" ? null : $(
                            "#profession_city").val(),
                        workplace: $("#profession_workplace").val() == "" ? null : $(
                            "#profession_workplace").val(),
                        nature: $("#profession_nature").val() == "" ? null : $(
                            "#profession_nature").val(),
                        industry: $("#industryId").val() == "" ? null : $(
                            "#industryId").val(),
                        position : $("#profession_position").val() == "" ? null : $(
                            "#profession_position").val(),
                        status: $("#profession_status").val() == "" ? null : $(
                            "#profession_status").val(),
                        endYearInternalFirst: $("#profession_start_enddata").val() == "" ? null : $(
                            "#profession_start_enddata").val().trim(),
                        endYearInternalSencond: $("#profession_end_enddata").val() == "" ? null : $(
                            "#profession_end_enddata").val().trim(),
                        startYearInternalFirst: $("#profession_start_startdata").val() == "" ? null : $(
                            "#profession_start_startdata").val().trim(),
                        startYearInternalSencond: $("#profession_end_startdata").val() == "" ? null : $(
                            "#profession_end_startdata").val().trim()
                    },
                    smSocial: {
                        name: $("#smSocial_name").val() == "" ? null : $("#smSocial_name").val().trim(),
                    },
                    smHonor: {
                        name: $("#honor_name").val() == "" ? null : $("#honor_name").val().trim(),
                        startDate:$("#honor_start_date").val() == "" ? null : $(
                            "#honor_start_date").val().trim(),
                        endDate: $("#honor_end_date").val() == "" ? null : $(
                            "#honor_end_date").val().trim()
                    },
                    smHistorydata: {
                        otherSchool: $("#otherSchool").val() == "" ? null : $("#otherSchool").val().trim(),
                        otherSpecialty: $("#otherSpecialty").val() == "" ? null : $(
                            "#otherSpecialty").val().trim(),
                        trustUnit: $("#trustUnit").val() == "" ? null : $("#trustUnit").val().trim(),
                        beforeSchoolunit: $("#beforeSchoolunit").val() == "" ? null : $("#beforeSchoolunit").val().trim(),
                    },
                    // smOtherInfo:{
                    // 	otherId: $("#other_name").val() == "" ? null : $("#other_name").val(),
                    // 	value: $("#other_value").val() == "" ? null : $("#other_value").val()
                    // },
                    // smHisEducation:{
                    // 	education: $("#education").val() == "" ? null : $("#education").val(),
                    // 	type: $("#hisE_type").val() == "" ? null : $("#hisE_type").val()
                    // },
                    markIds: $("#markId").val() == "" ? null : $("#markId").val()
                }
            }
        })
        $(".opa").css('display', 'none');
        $("#panel").click();
        return false;
    });

    //查看
    _$(document).on('click', '.PER_SHOW', function() {
        var flag = publicUtil.jurgeSelectRows(table.checkStatus('smList').data);
        if (flag) {
            layer.open({
                title: "校友简历",
                type: 2,
                area: ["900px", "700px"],
                content: ['../../../../views/module/schoolmate/schoolmate/schoolmateinfo.html',
                    'no'
                ],
                success: function() {
                    sessionStorage.setItem("userId", table.checkStatus('smList').data[0].id);
                }
            })
        }
    });

    //添加校友
    function addSm(edit) {
        publicUtil.gotoEditPage(application.SERVE_URL + '/sm/smSchoolmate/getSmInfoById', edit == undefined ?
            null : edit.id, "校友管理", "schoolmateAdd.html");
    }

    /* ------------------------------------------------------------筛选js--------------------------------------------------------------- */
    //初始化
    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'NATION'
    }, "nation", true);
    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'SCHOOLEMATE_TYPE'
    }, "type", true);
    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'SEX'
    }, "sex", true);
    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'IDCARD_TYPE'
    }, "cardType", true);
    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'CONTACT_TYPE'
    }, "contact_type", true, true);
    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'EDU_RECORD'
    }, "edu_record", true);
    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'EDU_DEGREE'
    }, "edu_degree", true);
    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'EDU_SCHOOLEN'
    }, "edu_schoollen", true);
    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'CARD_STATUS'
    }, "cardStatus", true);

    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'PROFESSION_STATUS'
    }, "profession_status", true);
    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'COMPANY_TPYE'
    }, "profession_nature", true);
    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'HONOR_TYPE'
    }, "honor_type", true);
    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'POLITICS_TYPE'
    }, "smSocial_type", true);
    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'POLITICS_TYPE'
    }, "politics_type", true);
    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'POLITICS_NAME'
    }, "politics_name", true);

    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'POLITICS_TYPE'
    }, "politics_type", true);
    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'POLITICS_NAME'
    }, "politics_name", true);
    //政治面貌
    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'POLITICS_NAME'
    }, "politics", true);
    //初始化地址
    publicUtil.selectAreaAndSetVal(rootAreaId, "country");
    publicUtil.selectAreaAndSetVal($("#country").val(), "province");
    publicUtil.selectAreaAndSetVal($("#province").val(), "city");
    publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'IS_NATION'
    }, "addr_type", true);


    //初始化职业经历地址
    publicUtil.selectAreaAndSetVal(rootAreaId, "profession_country");
    publicUtil.selectAreaAndSetVal($("#profession_country").val(), "profession_province");
    publicUtil.selectAreaAndSetVal($("#profession_province").val(), "profession_city");

    //初始化院系专业
    publicUtil.selectDepartmentSetVal(rootDepartmentId, "school");
    publicUtil.selectDepartmentSetVal($("#school").val(), "college");
    publicUtil.selectDepartmentSetVal($("#college").val(), "department");
    publicUtil.selectDepartmentSetVal($("#department").val(), "major");

    //类型（学历）
    publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        'typeCode': 'EDU_RECORD'
    }, "hisE_type");
    
    laydate.render({
        elem: '#birthday',
        theme: 'molv',
        trigger: 'click',
        done: function(value, date, endDate) {
            watchlaydate(value, "出生日期", "birthday");
        }
    });

    //初始化日期组件
    laydate.render({
        elem: '#startdate',
        theme: 'molv',
        trigger: 'click'
    });
    laydate.render({
        elem: '#enddate',
        theme: 'molv',
        trigger: 'click'
    });
    //教育经历-入学年
    laydate.render({
        elem: '#edu_startdate',
        type: 'year',
        trigger: 'click',
        range: true,
        done: function(value, date) {
            console.log(value)
            console.log(date)
            if (null != value && value.length > 0) {
                var strs = new Array();
                strs = value.split("-");
                $("#start_startdata").val(strs[0]);
                $("#end_startdata").val(strs[1]);
                watchInput("start_startdata", "入学开始年");
                watchInput("end_startdata", "入学结束年");
            } else {
                $("#start_startdata").val("");
                $("#end_startdata").val("");
                $("#condition_end_startdata").parent().remove();
                $("#condition_start_startdata").parent().remove();
                form.render();
            }
        }
    });
    //教育经历-毕业年
    laydate.render({
        elem: '#edu_enddate',
        type: 'year',
        trigger: 'click',
        range: true,
        done: function(value, date) {
            console.log(value)
            console.log(date)
            if (null != value && value.length > 0) {
                var strs = new Array();
                strs = value.split("-");
                $("#start_enddata").val(strs[0]);
                $("#end_enddata").val(strs[1]);
                watchInput("start_enddata", "毕业开始年");
                watchInput("end_enddata", "毕业结束年");
            } else {
                $("#start_enddata").val("");
                $("#end_enddata").val("");
                $("#condition_start_enddata").parent().remove();
                $("#condition_end_enddata").parent().remove();
                form.render();
            }
        }
    });

    //职业经历 -- 入职时间
    laydate.render({
        elem: '#profession_startdate',
        type: 'year',
        trigger: 'click',
        range: true,
        done: function(value, date) {
            console.log(value)
            console.log(date)
            if (null != value && value.length > 0) {
                var strs = new Array();
                strs = value.split("-");
                $("#profession_start_startdata").val(strs[0]);
                $("#profession_end_startdata").val(strs[1]);
                watchInput("profession_start_startdata", "入职起始时间");
                watchInput("profession_end_startdata", "入职截止时间");
            } else {
                $("#profession_start_startdata").val("");
                $("#profession_end_startdata").val("");
                $("#condition_profession_start_startdata").parent().remove();
                $("#condition_profession_end_startdata").parent().remove();
                form.render();
            }
        }
    });
    //职业经历 -- 离职时间
    laydate.render({
        elem: '#profession_enddate',
        type: 'year',
        trigger: 'click',
        range: true,
        done: function(value, date) {
            console.log(value)
            console.log(date)
            if (null != value && value.length > 0) {
                var strs = new Array();
                strs = value.split("-");
                $("#profession_start_enddata").val(strs[0]);
                $("#profession_end_enddata").val(strs[1]);
                watchInput("profession_start_enddata", "离职起始时间");
                watchInput("profession_end_enddata", "离职截止时间");
            } else {
                $("#profession_start_enddata").val("");
                $("#profession_end_enddata").val("");
                $("#condition_profession_start_enddata").parent().remove();
                $("#condition_profession_end_enddata").parent().remove();
                form.render();
            }
        }
    });
    //处理三联菜单的内容 如 ： ["102/10201/1020101"]
    function getFormSelectVal(arr) {
        var val = arr[0];
        if (arr != null && arr != "") {
            var arrs = new Array();
            arrs = val.split("/")
            return arrs[arrs.length - 1];
        }
        return null;
    }

    //荣誉成果
        //职业经历 -- 离职时间
    laydate.render({
        elem: '#honor_date',
        type: 'year',
        trigger: 'click',
        range: true,
        done: function(value, date) {
            console.log(value)
            console.log(date)
            if (null != value && value.length > 0) {
                var strs = new Array();
                strs = value.split("-");
                $("#honor_start_date").val(strs[0]);
                $("#honor_end_date").val(strs[1]);
                watchInput("honor_start_date", "荣誉获取起始时间");
                watchInput("honor_end_date", "荣誉获取截止时间");
            } else {
                $("#honor_start_date").val("");
                $("#honor_end_date").val("");
                $("#condition_honor_start_date").parent().remove();
                $("#condition_honor_end_date").parent().remove();
                form.render();
            }
        }
    });
    // 国家省市县联动两个
    form.on('select(country)', function(data) {
        watchSelect('country', '通讯地址(国家)');
        publicUtil.selectAreaAndSetVal(data.value, "province");
        publicUtil.selectAreaAndSetVal($("#province").val(), "city");
    })
    form.on('select(province)', function(data) {
        watchSelect('province', '通讯地址(省)');
        publicUtil.selectAreaAndSetVal(data.value, "city");
    })
    // 职业经历地址联动
    form.on('select(profession_country)', function(data) {
        watchSelect('profession_country', '工作国家');
        publicUtil.selectAreaAndSetVal(data.value, "profession_province");
        publicUtil.selectAreaAndSetVal($("#profession_province").val(), "profession_city");
    })
    form.on('select(profession_province)', function(data) {
        watchSelect('profession_province', '工作所在省');
        publicUtil.selectAreaAndSetVal(data.value, "profession_city");
    })
    //学校 学院 系 专业 联动
    form.on('select(school)', function(data) {
        watchSelect('school', '学校');
        publicUtil.selectDepartmentSetVal(data.value, "college");
    })
    form.on('select(college)', function(data) {
        watchSelect('college', '学院');
        publicUtil.selectDepartmentSetVal(data.value, "department");
    })
    form.on('select(department)', function(data) {
        watchSelect('department', '系');
        publicUtil.selectDepartmentSetVal(data.value, "major");
    })
    //基本信息
    form.on('select(cardType)', function(data) {
        watchSelect('cardType', '证件类型');
    });
    form.on('select(sex)', function(data) {
        watchSelect('sex', '性别');
    });
    form.on('select(nation)', function(data) {
        watchSelect('nation', '民族');
    });
    form.on('select(politics)', function(data) {
        watchSelect('politics', '政治面貌');
    });
    $('#name').change(function() {
        watchInput("name", "姓名");
    });
    $('#cardNum').change(function() {
        watchInput("cardNum", "证件号");
    });
    //联系方式
    form.on('select(contact_type)', function(data) {
        watchSelect('contact_type', '联系方式');
    });
    $('#contact').change(function() {
        watchInput("contact", "号码");
    });
    //职业经历
    // form.on('select(profession_country)', function(data){watchSelect('profession_country','工作国家');});
    // form.on('select(profession_province)', function(data){watchSelect('profession_province','工作所在省');});
    form.on('select(profession_city)', function(data) {
        watchSelect('profession_city', '工作所在市');
    });
    form.on('select(profession_nature)', function(data) {
        watchSelect('profession_nature', '单位性质');
    });
    form.on('select(profession_status)', function(data) {
        watchSelect('profession_status', '在职状态');
    });
    $('#profession_workplace').change(function() {
        watchInput("profession_workplace", "工作单位");
    });
    $('#profession_industry').change(function() {
        watchInput("profession_industry", "单位所属行业");
    });
    $('#profession_position').change(function() {
        watchInput("profession_position", "职位");
    });
    //社会兼职
    $('#smSocial_name').change(function() {
        watchInput("smSocial_name", "社会兼职名称");
    });
    $('#smSocial_position').change(function() {
        watchInput("smSocial_position", "社会兼职职位");
    });
    form.on('select(smSocial_type)', function(data) {
        watchSelect('smSocial_type', '是否在职');
    });
    form.on('select(smSocial_type)', function(data) {
        watchSelect('smSocial_type', '是否在职');
    });
    //政治面貌
    form.on('select(politics_name)', function(data) {
        watchSelect('politics_name', '政治面貌名称');
    });
    form.on('select(politics_type)', function(data) {
        watchSelect('politics_type', '政治面貌(是否在职)');
    });
    $('#politics_position').change(function() {
        watchInput("politics_position", "政治面貌职务");
    });
    //通讯地址
    // form.on('select(country)', function(data){watchSelect('country','通讯地址(国家)');});
    // form.on('select(province)', function(data){watchSelect('province','通讯地址(省)');});
    form.on('select(city)', function(data) {
        watchSelect('city', '通讯地址(市)');
    });
    //地址类型
    form.on('select(addr_type)', function(data) {
        watchSelect('addr_type', '地址类型');
    });
    
    //校园经历
    $('#smExperience_name').change(function() {
        watchInput("smExperience_name", "校园经历(组织名称)");
    });
    $('#smExperience_position').change(function() {
        watchInput("smExperience_position", "校园经历(职务)");
    });
    //荣誉成果
    $('#honor_name').change(function() {
        watchInput("honor_name", "荣誉名称");
    });
    //社会经历
    $('#smSocial_name').change(function() {
        watchInput("smSocial_name", "社会经历");
    });
    // $('#honor_industry').change(function(){	watchInput("honor_industry","获取荣誉行业");});
    // form.on('select(honor_type)', function(data) {
    //     watchSelect('honor_type', '荣誉类型');
    // });
    
    //教育经历
    // 		form.on('select(school)', function(data){watchSelect('school','学校');});
    // 		form.on('select(college)', function(data){watchSelect('college','学院');});
    // 		form.on('select(department)', function(data){watchSelect('department','系');});	
    form.on('select(major)', function(data) {
        watchSelect('major', '专业');
    });
    //班级
    $('#edu_classes').change(function() {
        watchInput("edu_classes", "班级");
    });
    //学号
    $('#edu_studentNo').change(function() {
        watchInput("edu_studentNo", "学号");
    });
    form.on('select(edu_record)', function(data) {
        watchSelect('edu_record', '学历');
    });
    form.on('select(edu_degree)', function(data) {
        watchSelect('edu_degree', '学位类型');
    });
    form.on('select(edu_schoollen)', function(data) {
        watchSelect('edu_schoollen', '学制');
    });
    // $('#edu_startdate').change(function() {
    // 		watchInput("start_startdata", "入学开始年");
    // 		watchInput("end_startdata", "入学结束年");
    // 	});
    // 	$('#edu_enddate').change(function() {
    // 			watchInput("start_enddata", "毕业开始年");
    // 			watchInput("end_enddata", "毕业结束年");
    // 		});

    //历史数据
    $('#otherSchool').change(function() {
        watchInput("otherSchool", "其他学校");
    });
    $('#otherSpecialty').change(function() {
        watchInput("otherSpecialty", "其他院系专业");
    });
    $('#trustUnit').change(function() {
        watchInput("trustUnit", "定向委培单位");
    });
    $('#beforeSchoolunit').change(function() {
        watchInput("beforeSchoolunit", "入学前地区单位");
    });
    
    //其他信息
    form.on('select(other_name)', function(data) {
        watchSelect('other_name', '其他信息类型');
    });
    $('#other_value').change(function() {
        watchInput("other_value", "其他信息内容");
    });
    
    //其他教育经历
    $('#education').change(function() {
        watchInput("education", "教育信息");
    });
    form.on('select(hisE_type)', function(data) {
        watchSelect('hisE_type', '类型');
    });
    // 地址 --地址详细信息
    $('#detail').change(function() {
        watchInput("detail", "地址详细信息");
    });
    //监听input框
    function watchInput(inputDomId, tabName) {
        if ($("#condition_" + inputDomId).length > 0) {
            if ($("#" + inputDomId).val() != null && $("#" + inputDomId).val() != "") {
                $("#condition_" + inputDomId).html($("#" + inputDomId).val());
            }
        } else {
            if ($("#" + inputDomId).val() != null && $("#" + inputDomId).val() != "") {
                $("#conditions").append("<span class='chosexuanxiang'><b>" + tabName +
                    "：</b><em id='condition_" + inputDomId +
                    "'>" + $("#" + inputDomId).val() + "</em><i class='delcondition'></i></span>");
            }
        }
    }
    //监听select
    function watchSelect(selectId, labelName) {
        console.log($("#" + selectId).val());
        if ($("#condition_" + selectId).length > 0) {
            if ($("#" + selectId).val() != null && $("#" + selectId).val() != "") {
                $("#condition_" + selectId).html($("#" + selectId + " :checked").text());
            }
        } else {
            if ($("#" + selectId).val() != null && $("#" + selectId).val() != "") {
                $("#conditions").append("<span class='chosexuanxiang'><b>" + labelName +
                    "：</b><em id='condition_" + selectId +
                    "'>" + $("#" + selectId + " :checked").text() +
                    "</em><i class='delcondition'></i></span>");
            }
        }
    }

    //监听select
    function watchRedio(selectId, labelName) {
        console.log($("#" + selectId).val());
        if ($("#condition_" + selectId).length > 0) {
            if ($("#" + selectId).val() != null && $("#" + selectId).val() != "") {
                $("#condition_" + selectId).html($("#" + selectId + " :checked").text());
            }
        } else {
            if ($("#" + selectId).val() != null && $("#" + selectId).val() != "") {
                $("#conditions").append("<span class='chosexuanxiang'><b>" + labelName +
                    "：</b><em id='condition_" + selectId +
                    "'>" + $("#" + selectId + " :checked").text() +
                    "</em><i class='delcondition'></i></span>");
            }
        }
    }
    //监听日期组件
    function watchlaydate(value, labelName, laydateId) {
        if ($("#condition_" + laydateId).length > 0) {
            if (value != null && value != "") {
                $("#condition_" + laydateId).html(value);
            }
        } else {
            if (value != null && value != "") {
                $("#conditions").append("<span class='chosexuanxiang'><b>" + labelName +
                    "：</b><em id='condition_" + laydateId +
                    "'>" + value + "</em><i class='delcondition'></i></span>");
            }
        }
    }

    /*上面条件删除方法*/
    _$(document).on('click', '.delcondition', function() {
        $(this).parents('span').remove();
        //获取上面对应的id
        let form_id = getIdBystr($(this).prev().attr("id"));
        console.log(form_id);
        if ("markTree" == form_id) {
            $("#markId").val("");
            $("#mark").val("");
            //请标签树的选中节点
            var selectTree = $.fn.zTree.init($("#markTree"), selectSetting, res);
            selectTree.checkAllNodes(false);
            selectTree.cancelSelectedNode();
            form.render();
        } else {
            $("#" + form_id).val(null);
            form.render();
        }
    })


    //获取id
    function getIdBystr(t) {
        var strs = t.split("_");
        if (strs.length == 2) {
            return strs[strs.length - 1];
        } else {
            return strs[strs.length - 2] + "_" + strs[strs.length - 1];
        }
    }


    function selectOnCheck(event, selectId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj(selectId);
        var nodes = zTree.getCheckedNodes(true);
        var labelName = treeNode.name;
        if ($("#condition_" + selectId).length > 0) {
            if (treeNode.checked) {
                $("#mark").val(labelName);
                $("#markId").val(treeNode.id);
                $("#condition_" + selectId).html(labelName);
            } else {
                $("#mark").val("");
                $("#markId").val("");
                $("#condition_" + selectId).parent().remove();
            }
        } else {
            if (treeNode.checked) {
                $("#mark").val(labelName);
                $("#markId").val(treeNode.id);
                $("#conditions").append("<span class='chosexuanxiang'><b>校友标签：</b><em id='condition_" +
                    selectId + "'>" + labelName + "</em><i class='delcondition'></i></span>");
            } else {
                $("#mark").val("");
                $("#markId").val("");
                $("#condition_" + selectId).parent().remove();
            }
        }

    }

    function industryTreeOnCheck(event, selectId, treeNode){
        var zTree = $.fn.zTree.getZTreeObj(selectId);
        var nodes = zTree.getCheckedNodes(true);
        var labelName = treeNode.name;
        if ($("#condition_" + selectId).length > 0) {
            if (treeNode.checked) {
                $("#industry").val(labelName);
                $("#industryId").val(treeNode.id);
                $("#condition_" + selectId).html(labelName);
            } else {
                $("#industry").val("");
                $("#industryId").val("");
                $("#condition_" + selectId).parent().remove();
            }
        } else {
            if (treeNode.checked) {
                $("#industry").val(labelName);
                $("#industryId").val(treeNode.id);
                $("#conditions").append("<span class='chosexuanxiang'><b>行业：</b><em id='condition_" +
                    selectId + "'>" + labelName + "</em><i class='delcondition'></i></span>");
            } else {
                $("#industry").val("");
                $("#industryId").val("");
                $("#condition_" + selectId).parent().remove();
            }
        }
    }
    //行业是否展示
    _$("#industry").click(function() {
        var markTree = document.getElementById("industryTreeDiv");
        markTree.style.display = (markTree.style.display === "block") ? "none" : "block";
    })
    
    function onClick(event, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj(treeId);
        if (zTree.setting.check.enable == true) {
            zTree.checkNode(treeNode, !treeNode.checked, false)
            assignment(treeId, zTree.getCheckedNodes());
        } else {
            assignment(treeId, zTree.getSelectedNodes());
            hideMenu();
        }
    }

    function onCheck(event, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj(treeId);
        assignment(treeId, zTree.getCheckedNodes());
    }

    function hideMenu() {
        _$(".select-tree").removeClass("layui-form-selected");
        _$(".tree-content").fadeOut("fast");
        _$("body").unbind("mousedown", onBodyDown);
    }

    function assignment(treeId, nodes) {
        var names = "";
        var ids = "";
        for (var i = 0, l = nodes.length; i < l; i++) {
            names += nodes[i].name + ",";
            ids += nodes[i].id + ",";
        }
        if (names.length > 0) {
            names = names.substring(0, names.length - 1);
            ids = ids.substring(0, ids.length - 1);
        }
        treeId = treeId.substring(0, treeId.length - 4);
        _$("#" + treeId + "Show").attr("value", names);
        _$("#" + treeId + "Show").attr("title", names);
        _$("#" + treeId + "Hide").attr("value", ids);
    }

    function onBodyDown(event) {
        if (_$(event.target).parents(".tree-content").html() == null) {
            hideMenu();
        }
    }


    function covert(data) {
        for (var i = 0; i < data.length; i++) {
            data[i].name = publicUtil.htmlDecode(data[i].name)
        }
        return data;
    }

    function judgeNull(data) {
        if (data == null || data == 'null' || data == "") {
            return "至今";
        } else {
            return dateUtils.getYearAndDay(data);
        }
    }


    //左侧树是否显示
    _$(".hidetreeMenu").click(function() {
        var oDiv = document.getElementById("leftTree");
        if(oDiv.style.display === "block"){
            $("#tablediv").removeClass("layui-col-md10");
            $("#tablediv").addClass("layui-col-md12"); 
            // $("#smListDiv").children("div").width("1300px");
        }else{
            $("#tablediv").removeClass("layui-col-md12");
            $("#tablediv").addClass("layui-col-md10");
            // $("#smListDiv").children("div").width("1300px");
            // $("#smListDiv").width("80%"); 
        }
        oDiv.style.display = (oDiv.style.display === "block") ? "none" : "block";
       
    })


    //校友标签是否显示
    _$("#mark").click(function() {
        var markTree = document.getElementById("markTreeDiv");
        markTree.style.display = (markTree.style.display === "block") ? "none" : "block";
    })

    //获取其他信息类型
    getOtherType();

    function getOtherType() {
        _$.ajax({
            url: application.SERVE_URL + '/sm/smOther/loadAllListBy', //ajax请求地址
            type: "POST", // 默认使用POST方式
            async: false,
            success: function(res) {
                selectOther("other_name", res.data);
            }
        });
    }

    //加载其他信息
    function selectOther(selectid, otherTypes) {
        $("#" + selectid).empty();
        $("#" + selectid).append('<option  value="" >' + "请选择" + ' </option>');
        for (var i = 0; i < otherTypes.length; i++) {
            $("#" + selectid).append('<option  value="' + otherTypes[i].id + '">' + otherTypes[i].name +
                '</option>'); //往下拉菜单里添加元素
        }
        form.render(); //菜单渲染 把内容加载进去
    }

})
