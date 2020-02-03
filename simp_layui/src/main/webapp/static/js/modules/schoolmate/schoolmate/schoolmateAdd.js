/**
 * @autor syp
 * @content 角色列表页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
    base: '../../../../static/js/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
    "interact": "interact",
    formSelects: 'formSelects-v4',
    "application": "application",
    'publicUtil': 'publicUtil',
    "croppers": "croppers"
});
layui.use(['interact', 'jquery', 'form', 'layer', 'laydate', 'formSelects', 'upload', 'application', 'publicUtil',
    'croppers',
    'element', 'laytpl'
], function() {
    var interact = layui.interact;
    var form = layui.form,
        $ = layui.jquery,
        formSelects = layui.formSelects,
        publicUtil = layui.publicUtil,
        application = layui.application,
        layer = layui.layer,
        upload = layui.upload,
        element = layui.element,
        laydate = layui.laydate,
        croppers = layui.croppers,
        laytpl = layui.laytpl;
    var imageId = "";
    var rootAreaId = 0;
    //籍贯
    var nation_place_datas;
    var formSelectDatas = null;
    //信息类型
    var INFOTYPE_HONOR = "HONOR";
    var INFOTYPE_PROFESSION = "PROFESSION";
    form.verify({
        $edu_start_date: "",
        $politics_start_date: "",
        $smSocial_start_date: "",
        $smExperience_start_date: "",
        $profession_start_date: "",
        $historydata_start_date: "",
        contact: function(value, item) {
            var index = getIdBystr($(item).attr("id"));
            if ($("#type_" + index).val() == 'PHONE') {
                if (!/^1\d{10}$/.test(value))
                    return '请输入正确的手机号';
            } else {
                if (!/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(value))
                    return '请输入正确的邮箱'
            }
        },
        user_exist: function(value) {
            var message = "";
            //判断是否是编辑
            if ($("#schoolmate_id").val()) {
                return;
            } else {
                $.ajax({
                    url: application.SERVE_URL + '/sys/sysuser/checkUserExist',
                    async: false,
                    method: 'post',
                    data: {
                        username: value
                    },
                    success: function(res) {
                        if (res.code == application.DATA_USED) {
                            message = "该登录名已存在";
                        } else {
                            return;
                        }
                    }
                })
            }
            return message;
        },
        user_login: function(value) {
            var message = "";
            if (value.length > 6 &&
                ((/^1\d{10}$/.test(value)) ||
                    (value).length == 32
                )) {
                return;
            }
            message = "请输入正确登录名";
            return message;
        },

        //教育经历
        edu_start_date: function(value) {
            $edu_start_date = value;
        },
        edu_end_date: function(value) {
            var message = "";
            if(null!=value && ''!=value){
                if (!((value >= $edu_start_date))) {
                    message = "毕业日期不能在入学日期之前";
                }
            }
            return message;
        },
        //政治面貌
        politics_start_date: function(value) {
            $politics_start_date = value;
        },
        politics_end_date: function(value) {
            var message = "";
			if(null!=value && ''!=value){
				if (!((value >= $profession_start_date))) {
				    message = "退出时间不能在入职时间之前";
				}
			}
            return message;
        },
        //社会兼职
        smSocial_start_date: function(value) {
            $smSocial_start_date = value;
        },
        smSocial_end_date: function(value) {
            var message = "";
            if(null!=value && ''!=value){
                if (!((value >= $smSocial_start_date))) {
                    message = "退出时间不能在加入时间之前";
                }
            }
            return message;
        },
        //校园经历
        smExperience_start_date: function(value) {
            $smExperience_start_date = value;
        },
        smExperience_end_date: function(value) {
            var message = "";
            if(null!=value && ''!=value){
                if (!((value >= $smExperience_start_date))) {
                    message = "退出时间不能在加入时间之前";
                }
            }
            return message;
        },
        //职业经历
        profession_start_date: function(value) {
            $profession_start_date = value;
        },
        profession_end_date: function(value) {
            var message = "";
			if(null!=value && ''!=value){
				if (!((value >= $profession_start_date))) {
				    message = "退出时间不能在入职时间之前";
				}
			}
            return message;
        },
        //历史数据
        historydata_start_date: function(value) {
            $historydata_start_date = value;
        },
        historydata_end_date: function(value) {
            var message = "";
            if(null!=value && ''!=value){
                if (!((value >= $historydata_start_date))) {
                    message = "毕业时间不能在入学时间之前";
                }
            }
            return message;
        },
    });


    //生日日期绑定
    laydate.render({
        elem: '#birthday',
        theme: 'molv',
        trigger: 'click'
    });


    // 选择地区
    function selectAreaAndSetVal(parentId, selectid, selectValue) {
        var area = sessionStorage.getItem("areaCache");
        // 抓取相关字段属性
        var data = JSON.parse(area)[parentId];
        $("#" + selectid).empty();
        if(!selectValue){
            $("#" + selectid).append('<option  value="" >' + "请选择" + ' </option>'); 
        }
        if (data != null) {
            for (var i = 0; i < data.length; i++) {
                $("#" + selectid).append('<option  value="' + data[i].id + '">' + data[i].name + '</option>'); // 往下拉菜单里添加元素
            }
        }

        if (selectValue) {
            $('#' + selectid).val(selectValue);
        }
        form.render();
    }

    // 选择院系专业
    function selectDepartAndSetVal(parentId, selectid, selectValue) {
        var area = sessionStorage.getItem("departCache");
        // 抓取相关字段属性
        var data = JSON.parse(area)[parentId];
        $("#" + selectid).empty();
        if(!selectValue){
            $("#" + selectid).append('<option  value="" >' + "请选择" + ' </option>');            
        }
        if (data != null) {
            for (var i = 0; i < data.length; i++) {
                $("#" + selectid).append('<option  value="' + data[i].id + '">' + data[i].name + '</option>'); // 往下拉菜单里添加元素
            }
        }

        if (selectValue) {
            $('#' + selectid).val(selectValue);
        }
        form.render();
    }

    // 院系专业
    form.on('select(school)', function(data) {
        var i = getIdBystr($(data.elem.form).find(".mark").attr("id"));
        console.log(i);
        selectDepartAndSetVal(data.value, "college_" + i);
        selectDepartAndSetVal($("#college_" + i).val(), "series_" + i);
        selectDepartAndSetVal($("#series_" + i).val(), "specialty_" + i);
    })

    form.on('select(college)', function(data) {
        var i = getIdBystr($(data.elem.form).find(".mark").attr("id"));
        selectDepartAndSetVal(data.value, "series_" + i);
        selectDepartAndSetVal($("#series_" + i).val(), "specialty_" + i);
    })

    form.on('select(series)', function(data) {
        var i = getIdBystr($(data.elem.form).find(".mark").attr("id"));
        selectDepartAndSetVal(data.value, "specialty_" + i);
    })

    // 国家省市县联动(地址)
    form.on('select(country)', function(data) {
        var i = getIdBystr($(data.elem.form).find(".mark").attr("id"));
        selectAreaAndSetVal(data.value, "province_" + i);
        selectAreaAndSetVal($("#province_" + i).val(), "city_" + i);
        selectAreaAndSetVal($("#city_" + i).val(), "district_" + i);
    })

    form.on('select(province)', function(data) {
        var i = getIdBystr($(data.elem.form).find(".mark").attr("id"));
        selectAreaAndSetVal(data.value, "city_" + i);
        selectAreaAndSetVal($("#city_" + i).val(), "district_" + i);
    })

    form.on('select(city)', function(data) {
        var i = getIdBystr($(data.elem.form).find(".mark").attr("id"));
        selectAreaAndSetVal(data.value, "district_" + i);
    })


    // 国家省市县联动(职业经历地址)
    form.on('select(pro_country)', function(data) {
        var i = getIdBystr($(data.elem.form).find(".mark").attr("id"));
        selectAreaAndSetVal(data.value, "pro_province_" + i);
        selectAreaAndSetVal($("#pro_province_" + i).val(), "pro_city_" + i);
        selectAreaAndSetVal($("#pro_city_" + i).val(), "pro_district_" + i);
    })

    form.on('select(pro_province)', function(data) {
        var i = getIdBystr($(data.elem.form).find(".mark").attr("id"));
        selectAreaAndSetVal(data.value, "pro_city_" + i);
        selectAreaAndSetVal($("#pro_city_" + i).val(), "pro_district_" + i);
    })

    form.on('select(pro_city)', function(data) {
        var i = getIdBystr($(data.elem.form).find(".mark").attr("id"));
        selectAreaAndSetVal(data.value, "pro_district_" + i);
    })




    //获取地区数据
    function initArea(parentId, dom, value) {
        selectAreaAndSetVal(parentId, dom, value);
    }

    function initDepartMent(parentId, dom, value) {
        selectDepartAndSetVal(parentId, dom, value)
    }

    //获取其他信息类型
    var otherTypes = [];

    function getOtherType() {
        $.ajax({
            url: application.SERVE_URL + '/sm/smOther/loadAllListBy', //ajax请求地址
            async: false,
            success: function(res) {
                otherTypes = res.data;
            }
        });
    }
    //禁止多选
    // formSelects.disabled('native_place');
    var config = {
        open: '{{',
        close: '}}'
    };


    //     //籍贯下拉配置
    //     formSelects.config('native_place', {
    // 
    //     });

    //基础信息编辑表单回填
    if (parent.editFormData != '') {
        data = parent.editFormData;
        $('#schoolmate_id').val(publicUtil.htmlDecode(data.id));
        $('#userId').val(publicUtil.htmlDecode(data.userId));
        // $('#smcontact_Id').val(publicUtil.htmlDecode(data.smContact.id));
        if (data.smAddress != null) {
            $('#smAddress_Id').val(publicUtil.htmlDecode(data.smAddress.id));
        }
        $(".name").val(publicUtil.htmlDecode(data.name));
        $('#contact').val(publicUtil.htmlDecode(data.sysUser.username));
        $('.cardNum').val(publicUtil.htmlDecode(data.cardNum));
        $('#birthday').val(publicUtil.htmlDecode(data.birthday));
        $('#remark').val(publicUtil.htmlDecode(data.remark));
        if (data.truePhoto != null && data.truePhoto != "") {
            document.getElementById("virtualPhoto").src = application.SERVE_URL + application.FILEPATH + data.truePhoto;
            $('#photoPath').val(data.truePhoto);
            imageId = application.SERVE_URL + application.FILEPATH + data.truePhoto;
        }
        if ($("#schoolmate_id").val()) {
            $("#contact").attr("readonly", "true");
        };
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'NATION'
        }, "nation", data.nation);
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'SCHOOLEMATE_TYPE'
        }, "type", data.type);
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'SEX'
        }, "sex", data.sex);
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'IDCARD_TYPE'
        }, "cardType", data.cardType);
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'POLITICS_NAME'
        }, "politics", data.politics);
        //籍贯回填
        // nation_place_datas = new Array();
        // var addresses = new Array();
        // var a = "";
        // if(data.smAddress != null){
        // 	if(data.smAddress.country !=null && data.smAddress.country.length != 0){
        // 		a = data.smAddress.country
        // 	}										
        // 	if(data.smAddress.province !=null && data.smAddress.province.length != 0){
        // 		a = data.smAddress.country + "/" + data.smAddress.province
        // 	}
        // 	if(data.smAddress.city !=null && data.smAddress.city.length != 0){
        // 		a = data.smAddress.country + "/" +data.smAddress.province + "/" +data.smAddress.city
        // 	}
        // 	if(data.smAddress.district !=null && data.smAddress.district.length != 0){
        // 		a = data.smAddress.country + "/" + data.smAddress.province + "/" + data.smAddress.city+ "/"+ data.smAddress.district
        // 	}
        // 	nation_place_datas.push(a);			
        // }		

        // initSelect();
        form.render();
        // formSelects.render();
    } else {
        //页面初始化
        initSchoolmateData();
        form.render();
    }

    croppers.render({
        elem: '#selectphoto',
        saveW: 300 //保存宽度
            ,
        saveH: 400,
        mark: 3 / 4,
        resizable: false,
        viewMode: 1,
        imageId: $("#photoPath"),
        photo: $("#virtualPhoto"),
        data: imageId,
        area: '900px' //弹窗宽度
            ,
        url: application.SERVE_URL + '/file/upload/' //图片上传接口返回和（layui 的upload 模块）返回的JOSN一样
            ,
        done: function(url) { //上传完毕回调
            /* $("#inputimgurl").val(url);
             $("#srcimgurl").attr('src',url);
             $('#bigimg').attr('src', result);*/

        }
    });



    // 卡片页签点击触发事件
    element.on('tab(info)', function(elem) {
        location.hash = $(this).attr('lay-id');
        //获取卡片对应的数据,回显数据
        var sysUserId = $('#userId').val();
        //基本信息

        if (elem.index == 0) {

        } else if (elem.index == 1) { //教育经历
            var load = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '加载中...',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });
            initSchoolInfo(0);
            console.log(sysUserId);
            if (!sysUserId) {
                judgeIsSaveSm(sysUserId);
            } else {
                if (edu >= 1) {
                    $("#eduFrom").nextAll("form").remove();
                }
                $.ajax({
                    url: application.SERVE_URL + "/sm/smEducation/loadAllListBy", //ajax请求地址
                    type: "POST",
                    data: {
                        "userId": sysUserId
                    },
                    success: function(res) {
                        if (res.code == application.REQUEST_SUCCESS) {
                            //获取长度
                            var len = res.data.length;
                            if (len > 0) {
                                //加载DOM{}
                                edu = len - 1;
                                for (var i = 1; i <= edu; i++) {
                                    $("#eduPos").before(appendEduForm(i));
                                    initSchoolInfo(i);
                                }
                                //值回填
                                for (var j = 0; j < res.data.length; j++) {
                                    //设置默认
                                    if (publicUtil.htmlDecode(res.data[j].isDefault ==
                                            "YES")) {
                                        $('#setDefEdu_' + j).addClass("layui-btn-disabled");
                                        $('#setDefEdu_' + j).attr("disabled", true);
                                    }
                                    $('#eduId_' + j).val(publicUtil.htmlDecode(res.data[j].id));

                                    initSchoolInfo(j, res.data[j].school, res.data[j].college,
                                        res.data[j].series, res.data[j].specialty);
                                    // $('#edu_departmentId_' + j).val(publicUtil.htmlDecode(
                                    //         res.data[j].departmentId)),
                                    // $('#edu_departmentName_' + j).val(publicUtil.htmlDecode(
                                    //     res.data[j].departmentName)),
                                    $('#edu_tempSeries_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].tempSeries)),
                                        $('#edu_tempSpecialty_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].tempSpecialty)),
                                        $('#edu_degreeType_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].degreeType)),
                                        $('#edu_subjectName_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].subjectName)),
                                        $('#edu_classes_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].classes)),
                                        $('#edu_studentNo_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].studentNo)),
                                        $('#edu_startdate_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].startdate)),
                                        $('#edu_enddate_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].enddate)),
                                        $('#edu_remark_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].remark)),
                                        //学历
                                        publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                                            "/sys/sysdict/getByTypeCode", {
                                                'typeCode': 'EDU_RECORD'
                                            }, "eduRecord_" + j, res.data[j].eduRecord);
                                    //学位类型
                                    publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                                        "/sys/sysdict/getByTypeCode", {
                                            'typeCode': 'EDU_DEGREE'
                                        }, "edu_degree_" + j, res.data[j].degree);
                                    //学制
                                    publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                                        "/sys/sysdict/getByTypeCode", {
                                            'typeCode': 'EDU_SCHOOLEN'
                                        }, "edu_schoollen_" + j, res.data[j].schoollen);
                                    //教育方式
                                    publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                                        "/sys/sysdict/getByTypeCode", {
                                            'typeCode': 'EDU_MODEL'
                                        }, "edu_model_" + j, res.data[j].eduModel);
                                    //培养方式
                                    publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                                        "/sys/sysdict/getByTypeCode", {
                                            'typeCode': 'EDU_TYPE'
                                        }, "edu_type_" + j, res.data[j].eduType);
                                    //书院
                                    publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                                        "/sys/sysdict/getByTypeCode", {
                                            'typeCode': 'ACADEMY_NAME'
                                        }, "academy_" + j, res.data[j].academy);
                                }
                            }
                            layer.close(load);
                            form.render();
                        } else {
                            layer.close(load);
                            layer.msg(res.msg);
                        }
                    }
                });
            }
        } else if (elem.index == 3) { //联系方式
            var load = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '加载中...',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });
            initContact(0);
            if (!sysUserId) {
                judgeIsSaveSm(sysUserId);
            } else {
                if (conta >= 1) {
                    $("#contactForm").nextAll("form").remove();
                }
                $.ajax({
                    url: application.SERVE_URL + "/sm/smContact/loadAllListBy", //ajax请求地址
                    data: {
                        "userId": sysUserId
                    },
                    success: function(res) {
                        if (res.code == application.REQUEST_SUCCESS) {
                            //获取长度
                            var len = res.data.length;
                            if (len > 0) {
                                //加载DOM
                                conta = len - 1;
                                for (var i = 1; i <= conta; i++) {
                                    $("#contactPos").before(appendContactForm(i));
                                    initContact(i);
                                }
                                //值回填
                                for (var j = 0; j < res.data.length; j++) {
                                    if (res.data[j].isDefault == 'IS_DEFAULT') {
                                        $('#contact_' + j).attr("readonly", "true");
                                        $("#type_" + j).attr("disabled", "none");
                                    }
                                    $('#contId_' + j).val(publicUtil.htmlDecode(res.data[j]
                                        .id));
                                    $('#contact_' + j).val(publicUtil.htmlDecode(res.data[j]
                                        .contact));
                                    publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                                        "/sys/sysdict/getByTypeCode", {
                                            'typeCode': 'CONTACT_TYPE'
                                        }, "type_" + j, res.data[j].type);
                                }
                            }
                            layer.close(load);
                            form.render();
                        } else {
                            layer.close(load);
                            layer.msg(res.msg);
                        }
                    }
                });
            }
        } else if (elem.index == 4) { //通讯地址
            var load = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '加载中...',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });
            initAddress(0)
            if (!sysUserId) {
                judgeIsSaveSm(sysUserId);
            } else {
                if (addr >= 1) {
                    $("#addFrom").nextAll("form").remove();
                }
                $.ajax({
                    url: application.SERVE_URL + "/sm/smAddress/loadAllListBy", //ajax请求地址
                    data: {
                        "userId": sysUserId
                    },
                    success: function(res) {
                        if (res.code == application.REQUEST_SUCCESS) {
                            //获取长度
                            var len = res.data.length;
                            if (len > 0) {
                                //加载DOM
                                addr = len - 1;
                                for (var i = 1; i <= addr; i++) {
                                    $("#addPos").before(appendAddressForm(i));
                                    initAddress(i);
                                }
                                //值回填
                                for (var j = 0; j < res.data.length; j++) {
                                    initAddress(j, res.data[j].country, res.data[j].province,
                                        res.data[j].city, res.data[j].district);
                                    $('#addId_' + j).val(publicUtil.htmlDecode(res.data[j].id));
                                    $('#detail_' + j).val(publicUtil.htmlDecode(res.data[j]
                                        .detail));
                                    $('#zipcode_' + j).val(publicUtil.htmlDecode(res.data[j]
                                        .zipcode));
                                    $('#remark_' + j).val(publicUtil.htmlDecode(res.data[j]
                                        .remark));
                                    publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                                        "/sys/sysdict/getByTypeCode", {
                                            'typeCode': 'IS_NATION'
                                        }, "add_type_" + j, res.data[j].type);
                                }
                            }
                            layer.close(load);
                            form.render();
                        } else {
                            layer.close(load);
                            layer.msg(res.msg);
                        }
                    }
                });
            }
        } else if (elem.index == 200) { //政治面貌
            var load = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '加载中...',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });
            initPolitics(0);
            if (!sysUserId) {
                judgeIsSaveSm(sysUserId);
            } else {
                if (politics >= 1) {
                    $("#politicsForm").nextAll("form").remove();
                }
                $.ajax({
                    url: application.SERVE_URL + "/sm/smPolitics/loadAllListBy", //ajax请求地址
                    data: {
                        "userId": sysUserId
                    },
                    success: function(res) {
                        if (res.code == application.REQUEST_SUCCESS) {
                            //获取长度
                            var len = res.data.length;
                            if (len > 0) {
                                //加载DOM
                                politics = len - 1;
                                for (var i = 1; i <= politics; i++) {
                                    $("#politicsPos").before(appendPoliticsForm(i));
                                    initPolitics(i);
                                }
                                //值回填
                                for (var j = 0; j < res.data.length; j++) {
                                    $('#politicsId_' + j).val(publicUtil.htmlDecode(res.data[
                                        j].id));
                                    $('#position_' + j).val(publicUtil.htmlDecode(res.data[
                                        j].position));
                                    $('#politics_startdate_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].startdate)),
                                        $('#politics_enddate_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].enddate)),
                                        $('#infoval_' + j).val(publicUtil.htmlDecode(res.data[
                                            j].infoval));
                                    publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                                        "/sys/sysdict/getByTypeCode", {
                                            'typeCode': 'POLITICS_NAME'
                                        }, "name_" + j, res.data[j].name);
                                    publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                                        "/sys/sysdict/getByTypeCode", {
                                            'typeCode': 'POLITICS_TYPE'
                                        }, "politics_type_" + j, res.data[j].type);
                                }
                            }
                            layer.close(load);
                            form.render();
                        } else {
                            layer.close(load);
                            layer.msg(res.msg);
                        }
                    }
                });
            }
        } else if (elem.index == 100) { //家庭成员
            var load = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '加载中...',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });
            initFamliy(0);
            if (!sysUserId) {
                judgeIsSaveSm(sysUserId);
            } else {
                if (famliy >= 1) {
                    $("#famliyForm").nextAll("form").remove();
                }
                $.ajax({
                    url: application.SERVE_URL + "/sm/smFamily/loadAllListBy", //ajax请求地址
                    data: {
                        "userId": sysUserId
                    },
                    success: function(res) {
                        if (res.code == application.REQUEST_SUCCESS) {
                            //获取长度
                            var len = res.data.length;
                            if (len > 0) {
                                //加载DOM
                                famliy = len - 1;
                                for (var i = 1; i <= famliy; i++) {
                                    $("#famliyPos").before(appendFamliyForm(i));
                                    initFamliy(i);
                                }
                                //值回填
                                for (var j = 0; j < res.data.length; j++) {
                                    $('#famliyId_' + j).val(publicUtil.htmlDecode(res.data[
                                        j].id));
                                    $('#famliy_name_' + j).val(publicUtil.htmlDecode(res.data[
                                        j].name));
                                    $('#famliy_birthday_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].birthday)),
                                        $('#politics_enddate_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].enddate)),
                                        $('#famliy_phone_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].phone));
                                    $('#famliy_profession_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].profession));
                                    $('#famliy_workplace_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].workplace));
                                    publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                                        "/sys/sysdict/getByTypeCode", {
                                            'typeCode': 'FAMLIY_RELATION'
                                        }, "famliy_relation_" + j, res.data[j].relation
                                    );
                                    publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                                        "/sys/sysdict/getByTypeCode", {
                                            'typeCode': 'SEX'
                                        }, "famliy_sex_" + j, res.data[j].sex);
                                    publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                                        "/sys/sysdict/getByTypeCode", {
                                            'typeCode': 'BOOLEAN_TYPE'
                                        }, "isschool_" + j, res.data[j].isschool);
                                }
                            }
                            layer.close(load);
                            form.render();
                        } else {
                            layer.close(load);
                            layer.msg(res.msg);
                        }
                    }
                });
            }
        } else if (elem.index == 6) { //社会兼职
            var load = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '加载中...',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });
            initSocial(0);
            if (!sysUserId) {
                judgeIsSaveSm(sysUserId);
            } else {
                if (social >= 1) {
                    $("#smSocialForm").nextAll("form").remove();
                }
                $.ajax({
                    url: application.SERVE_URL + "/sm/smSocial/loadAllListBy", //ajax请求地址
                    data: {
                        "userId": sysUserId
                    },
                    success: function(res) {
                        if (res.code == application.REQUEST_SUCCESS) {
                            //获取长度
                            var len = res.data.length;
                            if (len > 0) {
                                //加载DOM
                                social = len - 1;
                                for (var i = 1; i <= social; i++) {
                                    $("#smSocialPos").before(appendSocialForm(i));
                                    initSocial(i);
                                }
                                //值回填
                                for (var j = 0; j < res.data.length; j++) {
                                    $('#smSocialId_' + j).val(publicUtil.htmlDecode(res.data[
                                        j].id));
                                    $('#smSocial_name_' + j).val(publicUtil.htmlDecode(res.data[
                                        j].name));
                                    $('#smSocial_position_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].position)),
                                        $('#smSocial_startdate_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].startdate)),
                                        $('#smSocial_enddate_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].enddate));
                                    $('#smSocial_infoval_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].infoval));
                                    publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                                        "/sys/sysdict/getByTypeCode", {
                                            'typeCode': 'POLITICS_TYPE'
                                        }, "smSocial_type_" + j, res.data[j].status);
                                }
                            }
                            layer.close(load);
                            form.render();
                        } else {
                            layer.close(load);
                            layer.msg(res.msg);
                        }
                    }
                });
            }
        } else if (elem.index == 300) { //校园经历
            var load = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '加载中...',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });
            initExperience(0);
            if (!sysUserId) {
                judgeIsSaveSm(sysUserId);
            } else {
                if (experience >= 1) {
                    $("#SmExperienceForm").nextAll("form").remove();
                }
                $.ajax({
                    url: application.SERVE_URL + "/sm/smExperience/loadAllListBy", //ajax请求地址
                    data: {
                        "userId": sysUserId
                    },
                    success: function(res) {
                        if (res.code == application.REQUEST_SUCCESS) {
                            //获取长度
                            var len = res.data.length;
                            if (len > 0) {
                                //加载DOM
                                experience = len - 1;
                                for (var i = 1; i <= experience; i++) {
                                    $("#smExperiencePos").before(appendExperience(i));
                                    initExperience(i);
                                }
                                //值回填
                                for (var j = 0; j < res.data.length; j++) {
                                    $('#smExperienceId_' + j).val(publicUtil.htmlDecode(res
                                        .data[j].id));
                                    $('#smExperience_name_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].name));
                                    $('#smExperience_position_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].position));
                                    $('#smExperience_startDate_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].startDate));
                                    $('#smExperience_endDate_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].endDate));
                                    $('#smExperience_content_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].content));
                                }
                            }
                            layer.close(load);
                            form.render();
                        } else {
                            layer.close(load);
                            layer.msg(res.msg);
                        }
                    }
                });
            }
        } else if (elem.index == 2) { //职业经历
            var load = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '加载中...',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });
            initProfession(0);
            if (!sysUserId) {
                judgeIsSaveSm(sysUserId);
            } else {
                if (profession >= 1) {
                    $("#professionForm").nextAll("form").remove();
                }
                $.ajax({
                    url: application.SERVE_URL + "/sm/smProfession/loadAllListBy", //ajax请求地址
                    data: {
                        "userId": sysUserId
                    },
                    success: function(res) {
                        if (res.code == application.REQUEST_SUCCESS) {
                            //获取长度
                            var len = res.data.length;
                            if (len > 0) {
                                //加载DOM
                                profession = len - 1;
                                for (var i = 1; i <= profession; i++) {
                                    $("#ProfessionPos").before(appendProfession(i));
                                    initProfession(i);
                                }
                                //值回填
                                for (var j = 0; j < res.data.length; j++) {
                                    initProfession(j, res.data[j].country, res.data[j].province,
                                        res.data[j].city, res.data[j].district);
                                    $('#professionId_' + j).val(publicUtil.htmlDecode(res.data[
                                        j].id));
                                    $('#profession_detail_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].detail));
                                    $('#profession_industry_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].industry));
                                    $('#profession_industryName_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].industryName));
                                    $('#profession_department_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].department));
                                    $('#profession_position_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].position));
                                    $('#profession_telephone_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].telephone));
                                    $('#profession_fax_' + j).val(publicUtil.htmlDecode(res
                                        .data[j].fax));
                                    $('#profession_workplace_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].workplace));
                                    $('#profession_startDate_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].startDate));
                                    $('#profession_endDate_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].endDate));
                                    $('#profess_remark_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].remark));
                                    publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                                        "/sys/sysdict/getByTypeCode", {
                                            'typeCode': 'PROFESSION_STATUS'
                                        }, "profession_status_" + j, res.data[j].status
                                    );
                                    publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                                        "/sys/sysdict/getByTypeCode", {
                                            'typeCode': 'COMPANY_TPYE'
                                        }, "profession_nature_" + j, res.data[j].nature
                                    );

                                }
                            }
                            layer.close(load);
                            form.render();
                        } else {
                            layer.close(load);
                            layer.msg(res.msg);
                        }
                    }
                });
            }
        } else if (elem.index == 5) { //荣誉成果
            var load = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '加载中...',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });
            initHonor(0);
            if (!sysUserId) {
                judgeIsSaveSm(sysUserId);
            } else {
                if (honor >= 1) {
                    $("#honorForm").nextAll("form").remove();
                }
                $.ajax({
                    url: application.SERVE_URL + "/sm/smHonor/loadAllListBy", //ajax请求地址
                    data: {
                        "userId": sysUserId
                    },
                    success: function(res) {
                        if (res.code == application.REQUEST_SUCCESS) {
                            //获取长度
                            var len = res.data.length;
                            if (len > 0) {
                                //加载DOM
                                honor = len - 1;
                                for (var i = 1; i <= honor; i++) {
                                    $("#HonorPos").before(appendHonor(i));
                                    initHonor(i);
                                }
                                //值回填
                                for (var j = 0; j < res.data.length; j++) {
                                    console.log(res.data[j].type);
                                    $('#honorId_' + j).val(publicUtil.htmlDecode(res.data[j]
                                        .id));
                                    $('#honor_name_' + j).val(publicUtil.htmlDecode(res.data[
                                        j].name));
                                    $('#honor_date_' + j).val(publicUtil.htmlDecode(res.data[
                                        j].date));
                                    $('#honor_industry_' + j).val(publicUtil.htmlDecode(res
                                        .data[j].industry));
                                    $('#honor_industryName_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].industryName));
                                    $('#historydata_infoval_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].infoval));
                                    // publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                                    //     "/sys/sysdict/getByTypeCode", {
                                    //         'typeCode': 'HONOR_TYPE'
                                    //     }, "honor_type_" + j, res.data[j].type);
                                    
                                }
                            }
                            layer.close(load);
                            form.render();
                        } else {
                            layer.close(load);
                            layer.msg(res.msg);
                        }
                    }
                });
            }
        } else if (elem.index == 7) { //历史数据
            var load = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '加载中...',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });
            initHistorydata(0);
            if (!sysUserId) {
                judgeIsSaveSm(sysUserId);
            } else {
                if (historydata >= 1) {
                    $("#historydataForm").nextAll("form").remove();
                }
                $.ajax({
                    url: application.SERVE_URL + "/sm/smHistorydata/loadAllListBy", //ajax请求地址
                    data: {
                        "userId": sysUserId
                    },
                    success: function(res) {
                        if (res.code == application.REQUEST_SUCCESS) {
                            //获取长度
                            var len = res.data.length;
                            if (len > 0) {
                                //加载DOM
                                historydata = len - 1;
                                for (var i = 1; i <= historydata; i++) {
                                    $("#historydataPos").before(appendHistorydata(i));
                                    initHistorydata(i);
                                }
                                //值回填
                                for (var j = 0; j < res.data.length; j++) {
                                    $('#historydataId_' + j).val(publicUtil.htmlDecode(res.data[
                                        j].id));
                                    $('#historydata_otherSchool_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].otherSchool));
                                    $('#historydata_otherSpecialty_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].otherSpecialty)),
                                        $('#historydata_joinDate_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].joinDate)),
                                        $('#historydata_degreeDate_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].degreeDate));
                                    $('#historydata_trustUnit_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].trustUnit));
                                    $('#historydata_beforeSchoolunit_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].beforeSchoolunit));
                                    $('#historydata_nativeAdress_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].nativeAdress));
                                    $('#historydata_personLove_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].personLove));
                                }
                            }
                            layer.close(load);
                            form.render();
                        } else {
                            layer.close(load);
                            layer.msg(res.msg);
                        }
                    }
                });
            }
        } else if (elem.index == 8) {
            var load = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '加载中...',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });
            getOtherType();
            initOtherInfo(0, null);
            if (!sysUserId) {
                judgeIsSaveSm(sysUserId);
            } else {
                if (smOtherInfo >= 1) {
                    $("#smOtherInfoForm").nextAll("form").remove();
                }
                $.ajax({
                    url: application.SERVE_URL + "/sm/smOtherInfo/loadAllListBy", //ajax请求地址
                    data: {
                        "userId": sysUserId
                    },
                    success: function(res) {
                        if (res.code == application.REQUEST_SUCCESS) {
                            //获取长度
                            var len = res.data.length;
                            console.log(len);
                            if (len > 0) {
                                //加载DOM
                                smOtherInfo = len - 1;
                                for (var i = 1; i <= smOtherInfo; i++) {
                                    $("#otherInfoPos").before(appendSmOtherInfo(i));
                                    initOtherInfo(i, null);
                                }
                                //值回填
                                for (var j = 0; j < res.data.length; j++) {

                                    // selectOther("otherType_"+ j,publicUtil.htmlDecode(res.data[j].otherId));
                                    initOtherInfo(j, publicUtil.htmlDecode(res.data[j].otherId));
                                    $('#smOtherInfo_' + j).val(publicUtil.htmlDecode(res.data[j].id));
                                    $('#otherInfo_' + j).val(publicUtil.htmlDecode(res.data[
                                        j].value));
                                    // $('#otherType_' + j).attr("disabled",true);			
                                }
                            }
                            layer.close(load);
                            form.render();
                        } else {
                            layer.close(load);
                            layer.msg(res.msg);
                        }
                    }
                });
            }
        } else if (elem.index == 9) {
            var load = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '加载中...',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });
            initHisEdu(0);
            if (!sysUserId) {
                judgeIsSaveSm(sysUserId);
            } else {
                if (smHisEdu >= 1) {
                    $("#hisEduForm").nextAll("form").remove();
                }
                $.ajax({
                    url: application.SERVE_URL + "/sm/smHisEducation/loadAllListBy", //ajax请求地址
                    data: {
                        "userId": sysUserId
                    },
                    success: function(res) {
                        if (res.code == application.REQUEST_SUCCESS) {
                            //获取长度
                            var len = res.data.length;
                            if (len > 0) {
                                //加载DOM
                                smHisEdu = len - 1;
                                for (var i = 1; i <= smHisEdu; i++) {
                                    $("#hisEduPos").before(appendHisEdu(i));
                                    initHisEdu(i);
                                }
                                //值回填
                                for (var j = 0; j < res.data.length; j++) {
                                    $('#hisEduId_' + j).val(publicUtil.htmlDecode(res.data[
                                        j].id));
                                    $('#hisEdu_education_' + j).val(publicUtil.htmlDecode(
                                        res.data[j].education));
                                    $('#hisEdu_startdate_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].startdate)),
                                        $('#hisEdu_enddate_' + j).val(publicUtil.htmlDecode(
                                            res.data[j].enddate)),
                                        //学历
                                        publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                                            "/sys/sysdict/getByTypeCode", {
                                                'typeCode': 'EDU_RECORD'
                                            }, "hisEdu_type_" + j, res.data[j].type);
                                }
                            }
                            layer.close(load);
                            form.render();
                        } else {
                            layer.close(load);
                            layer.msg(res.msg);
                        }
                    }
                });
            }
        }
    });


    //新增教育经历
    var edu = 0;
    $("#addEduinfo").click(function() {
        edu++;
        $("#eduPos").before(appendEduForm(edu));
        initSchoolInfo(edu);
    });


    //新增地址
    var addr = 0;
    $("#addAddressinfo").click(function() {
        addr++;
        $("#addPos").before(appendAddressForm(addr));
        initAddress(addr);
    });


    //新增联系方式
    var conta = 0;
    $("#addContactinfo").click(function() {
        conta++;
        $("#contactPos").before(appendContactForm(conta));
        initContact(conta);
    });

    //新增政治面貌
    var politics = 0;
    $("#addPoliticsinfo").click(function() {
        politics++;
        $("#politicsPos").before(appendPoliticsForm(politics));
        initPolitics(politics);
    });

    //新增家庭成员
    var famliy = 0;
    $("#addFamliyinfo").click(function() {
        famliy++;
        $("#famliyPos").before(appendFamliyForm(famliy));
        initFamliy(famliy);
    });

    //新增社会兼职
    var social = 0;
    $("#addSmSocialinfo").click(function() {
        social++;
        $("#smSocialPos").before(appendSocialForm(social));
        initSocial(social);
    });

    //新增校园经历		
    var experience = 0;
    $("#addSmExperienceinfo").click(function() {
        experience++;
        $("#smExperiencePos").before(appendExperience(experience));
        initExperience(experience);
    });

    //新增历史数据
    var historydata = 0;
    $("#addHistorydatainfo").click(function() {
        historydata++;
        $("#historydataPos").before(appendHistorydata(historydata));
        initHistorydata(historydata);
    });


    //新增荣誉成果
    var honor = 0;
    $("#addHonorinfo").click(function() {
        honor++;
        $("#HonorPos").before(appendHonor(honor));
        initHonor(honor);
    });

    //新增职业经历
    var profession = 0;
    $("#addProfessioninfo").click(function() {
        profession++;
        $("#ProfessionPos").before(appendProfession(profession));
        initProfession(profession);
    });

    //新增其他信息
    var smOtherInfo = 0;
    $("#addSmOtherInfo").click(function() {
        smOtherInfo++;
        $("#otherInfoPos").before(appendSmOtherInfo(smOtherInfo));
        initOtherInfo(smOtherInfo, null);
    });

    //新增其他教育经历
    var smHisEdu = 0;
    $("#addHisEduinfo").click(function() {
        smHisEdu++;
        $("#hisEduPos").before(appendHisEdu(smHisEdu));
        console.log("======================" + smHisEdu + "========================")
        initHisEdu(smHisEdu);
    });

    //页面打开时，或者基本信息时，点击填充数据
    function initSchoolmateData() {
        initSelect();
        publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'NATION'
        }, "nation");
        publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'SCHOOLEMATE_TYPE'
        }, "type");
        publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'SEX'
        }, "sex");
        publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'IDCARD_TYPE'
        }, "cardType");
        publicUtil.selectBase(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'POLITICS_NAME'
        }, "politics");
    }




    //submit()  绑定提交按钮（基本信息）
    form.on("submit(addSchoolfellow)", function(data) {
        var index = top.layer.msg('数据提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });
        var smAdd;
        // if (layui.formSelects.value('native_place', 'val') != null && layui.formSelects.value(
        //         'native_place', 'val') !=
        //     "") {
        //     smAdd = excFormSelectVal(layui.formSelects.value('native_place', 'val'));
        // }
        $.ajax({
            url: application.SERVE_URL + "/sm/smSchoolmate/save", //ajax请求地址
            contentType: "application/json",
            data: JSON.stringify({
                id: $('#schoolmate_id').val() == null || $('#schoolmate_id').val() ==
                    "" ? null : $('#schoolmate_id').val(),
                userId: $('#userId').val() == null || $('#userId').val() == "" ? null :
                    $('#userId').val(),
                name: $('.name').val(),
                sex: $('#sex').val(),
                smContact: {
                    id: $('#smcontact_Id').val() == null || $('#smcontact_Id').val() ==
                        "" ? null : $('#smcontact_Id').val(),
                    contact: $('#contact').val(),
                },
                type: $('#type').val(),
                nation: $('#nation').val(),
                cardType: $('#cardType').val(),
                cardNum: $('.cardNum').val(),
                birthday: $('#birthday').val(),
                truePhoto: $('#photoPath').val(),
                politics: $('#politics').val(),
                remark: $('#remark').val()
                // smAddress: {
                // 	id : $('#smAddress_Id').val()== null || $('#smAddress_Id').val() == "" ? null : $('#smAddress_Id').val(),
                // 	country: smAdd == undefined  || smAdd[0] == undefined ? "" : smAdd[0],
                // 	province: smAdd== undefined || smAdd[1] == undefined ? "" : smAdd[1],
                // 	city: smAdd== undefined || smAdd[2] == undefined ? "" : smAdd[2],
                // 	district: smAdd== undefined || smAdd[3] == undefined ? "" : smAdd[3]
                // }
            }),
            success: function(res) {
                if (res.code == application.REQUEST_SUCCESS) {
                    top.layer.close(index);
                    top.layer.msg(res.msg);
                    layer.closeAll("iframe");
                    //刷新父页面
                    $("#contact").val(publicUtil.htmlDecode(res.data.smContact.contact));
                    $("#userId").val(res.data.userId);
                    $("#schoolmate_id").val(res.data.id);
                    $("#contact").attr("readonly", "true");
					$('#birthday').val(publicUtil.htmlDecode(res.data.birthday));                    //此处不刷新
                    // location.reload();
                } else {
                    layer.msg(res.msg);
                }
            }
        });
        return false;
    })

    //submit(addUser)  绑定提交按钮（教育经历）
    form.on("submit(addEdu)", function(data) {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        // var edus = [];
        // $("#education_" + i + " select").each(function(item, index) {
        //     edus.push($(index).find("option:selected").val());
        // })
        var index = top.layer.msg('数据提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });
        $.ajax({
            url: application.SERVE_URL + "/sm/smEducation/save", //ajax请求地址
            data: {
                id: $('#eduId_' + i).val() == null || $('#eduId_' + i).val() == "" ? null : $(
                    '#eduId_' + i).val(),
                userId: $('#userId').val(),
                school: $('#school_' + i).val(),
                college: $('#college_' + i).val(),
                series: $('#series_' + i).val(),
                specialty: $('#specialty_' + i).val(),
                academy: $('#academy_' + i).val(),
                tempSeries: $('#edu_tempSeries_' + i).val(),
                tempSpecialty: $('#edu_tempSpecialty_' + i).val(),
                degreeType: $('#edu_degreeType_' + i).val(),
                classes: $('#edu_classes_' + i).val(),
                eduRecord: $('#eduRecord_' + i).val(),
                degree: $('#edu_degree_' + i).val(),
                studentNo: $('#edu_studentNo_' + i).val(),
                schoollen: $('#edu_schoollen_' + i).val(),
                eduModel: $('#edu_model_' + i).val(),
                eduType: $('#edu_type_' + i).val(),
                startdate: $('#edu_startdate_' + i).val(),
                enddate: $('#edu_enddate_' + i).val(),
                remark: $('#edu_remark_' + i).val()
            },
            success: function(res) {
                //保存结束后数据回显
                if (res.code == application.REQUEST_SUCCESS) {
                    var data = res.data;
                    $('#eduId_' + i).val(data.id);
                    top.layer.close(index);
                    top.layer.msg(res.msg);
                    layer.closeAll("iframe");
                    //刷新父页面
                    //此处不刷新
                    // location.reload();
                } else {
                    layer.msg(res.msg);
                }
            }
        });
        return false;
    });

    //设置默认（教育经历）
    $(document).on('click', '.setDefEdu', function() {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));

        // var i = getIdBystr($(data.form).find(".mark").attr("id"));
        if ($('#eduId_' + i).val() == null || $('#eduId_' + i).val() == "") {
            top.layer.msg("请先保存该条记录");
            return false;
        } else {
            var index = top.layer.msg('数据提交中，请稍候', {
                icon: 16,
                time: false,
                shade: 0.8
            });
            $.ajax({
                url: application.SERVE_URL + "/sm/smEducation/setDefEdu", //ajax请求地址
                data: {
                    id: $('#eduId_' + i).val() == null || $('#eduId_' + i).val() == "" ? null :
                        $('#eduId_' + i).val(),
                    userId: $('#userId').val()
                },
                success: function(res) {
                    //保存结束后数据回显
                    if (res.code == application.REQUEST_SUCCESS) {
                        $('.setDefEdu').attr("disabled", false);
                        $('.setDefEdu').removeClass("layui-btn-disabled");
                        $('#setDefEdu_' + i).addClass("layui-btn-disabled");
                        $('#setDefEdu_' + i).attr("disabled", true);

                        top.layer.close(index);
                        top.layer.msg(res.msg);
                        layer.closeAll("iframe");
                        //刷新父页面
                        //此处不刷新
                        // location.reload();
                    } else {
                        layer.msg(res.msg);
                    }
                }
            });
        }
        return false;
    })


    //删除教育经历
    $(document).on('click', '.delEdu', function() {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        if ($('#eduId_' + i).val() == null || $('#eduId_' + i).val() == "") {
            $('#eduId_' + i).parents('form').remove();
            return false;
        }
        layer.confirm('确定删除？', {
            icon: 3,
            title: '提示信息'
        }, function(index) {
            $.ajax({
                url: application.SERVE_URL + "/sm/smEducation/delete", //ajax请求地址
                data: {
                    id: $('#eduId_' + i).val()
                },
                success: function(res) {
                    if (res.code == application.REQUEST_SUCCESS) {
                        layer.close(index);
                        $('#eduId_' + i).parents('form').remove();
                        top.layer.msg(res.msg);
                    } else {
                        top.layer.msg(res.msg);
                    }
                    layer.close(index);
                }
            });
        });
        return false;
    })

    //submit()  绑定提交按钮（联系方式）
    form.on("submit(addContact)", function(data) {
        var i = getIdBystr($(data.form).find(".mark").attr("id"));
        var index = top.layer.msg('数据提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });
        $.ajax({
            url: application.SERVE_URL + "/sm/smContact/save", //ajax请求地址
            data: {
                id: $('#contId_' + i).val() == null || $('#contId_' + i).val() == "" ? null : $(
                    '#contId_' + i).val(),
                userId: $('#userId').val(),
                type: $('#type_' + i).val(),
                contact: $('#contact_' + i).val(),
            },
            success: function(res) {
                //保存结束后数据回显
                if (res.code == application.REQUEST_SUCCESS) {
                    var data = res.data;
                    $(".contact").val(data.contact);
                    $('#contId_' + i).val(data.id);
                    publicUtil.selectBaseAndSetVal(application.SERVE_URL +
                        "/sys/sysdict/getByTypeCode", {
                            'typeCode': 'CONTACT_TYPE'
                        }, "type_" + i, data.type);
                    top.layer.close(index);
                    top.layer.msg(res.msg);
                    layer.closeAll("iframe");
                    //刷新父页面
                    //此处不刷新
                    // location.reload();
                } else {
                    layer.msg(res.msg);
                }
            }
        });
        return false;

    });

    //删除联系方式
    $(document).on('click', '.delContact', function() {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        if ($('#contId_' + i).val() == null || $('#contId_' + i).val() == "") {
            $('#contId_' + i).parents('form').remove();
            return false;
        }
        layer.confirm('确定删除？', {
            icon: 3,
            title: '提示信息'
        }, function(index) {
            $.ajax({
                url: application.SERVE_URL + "/sm/smContact/delete", //ajax请求地址
                data: {
                    id: $('#contId_' + i).val()
                },
                success: function(res) {
                    if (res.code == application.REQUEST_SUCCESS) {
                        layer.close(index);
                        $('#contId_' + i).parents('form').remove();
                        top.layer.msg(res.msg);
                    } else {
                        top.layer.msg(res.msg);
                    }
                    layer.close(index);
                }
            });
        });
        return false;
    });

    //submit()  绑定提交按钮（通讯地址）

    form.on("submit(addAddress)", function(data) {
        console.log($(data.form));
        var i = getIdBystr($(data.form).find(".mark").attr("id"));

        // var address = excFormSelectVal(layui.formSelects.value('address_' + i, 'val'));
        var index = top.layer.msg('数据提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });
        $.ajax({
            url: application.SERVE_URL + "/sm/smAddress/save", //ajax请求地址
            data: {
                id: $('#addId_' + i).val() == null || $('#addId_' + i).val() == "" ? null : $(
                    '#addId_' + i).val(),
                userId: $('#userId').val(),
                detail: $('#detail_' + i).val(),
                zipcode: $('#zipcode_' + i).val(),
                type: $('#add_type_' + i).val(),
                remark: $('#remark_' + i).val(),
                country: $('#country_' + i).val(),
                province: $('#province_' + i).val(),
                city: $('#city_' + i).val(),
                district: $('#district_' + i).val()
            },
            success: function(res) {
                //保存结束后数据回显
                if (res.code == application.REQUEST_SUCCESS) {
                    var data = res.data;
                    $('#addId_' + i).val(data.id);
                    top.layer.close(index);
                    top.layer.msg(res.msg);
                    layer.closeAll("iframe");
                    //刷新父页面
                    //此处不刷新
                    // location.reload();
                } else {
                    layer.msg(res.msg);
                }
            }
        });
        return false;
    })

    //删除通讯地址
    $(document).on('click', '.delAddress', function() {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        if ($('#addId_' + i).val() == null || $('#addId_' + i).val() == "") {
            $('#addId_' + i).parents('form').remove();
            return false;
        }
        layer.confirm('确定删除？', {
            icon: 3,
            title: '提示信息'
        }, function(index) {
            $.ajax({
                url: application.SERVE_URL + "/sm/smAddress/delete", //ajax请求地址
                data: {
                    id: $('#addId_' + i).val()
                },
                success: function(res) {
                    if (res.code == application.REQUEST_SUCCESS) {
                        layer.close(index);
                        $('#addId_' + i).parents('form').remove();
                        top.layer.msg(res.msg);
                    } else {
                        top.layer.msg(res.msg);
                    }
                    layer.close(index);
                }
            });
        });
        return false;
    })

    //保存政治面貌
    form.on("submit(addPolitics)", function(data) {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        var index = top.layer.msg('数据提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });
        $.ajax({
            url: application.SERVE_URL + "/sm/smPolitics/save", //ajax请求地址
            data: {
                id: $('#politicsId_' + i).val() == null || $('#politicsId_' + i).val() == "" ?
                    null : $('#politicsId_' + i).val(),
                userId: $('#userId').val(),
                name: $('#name_' + i).val(),
                position: $('#position_' + i).val(),
                startdate: $('#politics_startdate_' + i).val(),
                enddate: $('#politics_enddate_' + i).val(),
                type: $('#politics_type_' + i).val(),
                infoval: $('#infoval_' + i).val()
            },
            success: function(res) {
                //保存结束后数据回显
                if (res.code == application.REQUEST_SUCCESS) {
                    var data = res.data;
                    $('#politicsId_' + i).val(data.id);
                    top.layer.close(index);
                    top.layer.msg(res.msg);
                    layer.closeAll("iframe");
                    //刷新父页面
                    //此处不刷新
                    // location.reload();
                } else {
                    layer.msg(res.msg);
                }
            }
        });
        return false;
    })

    //删除政治面貌
    $(document).on('click', '.delPolitics', function() {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        if ($('#politicsId_' + i).val() == null || $('#politicsId_' + i).val() == "") {
            $('#politicsId_' + i).parents('form').remove();
            return false;
        }
        layer.confirm('确定删除？', {
            icon: 3,
            title: '提示信息'
        }, function(index) {
            $.ajax({
                url: application.SERVE_URL + "/sm/smPolitics/delete", //ajax请求地址
                data: {
                    id: $('#politicsId_' + i).val()
                },
                success: function(res) {
                    if (res.code == application.REQUEST_SUCCESS) {
                        layer.close(index);
                        $('#politicsId_' + i).parents('form').remove();
                        top.layer.msg(res.msg);
                    } else {
                        top.layer.msg(res.msg);
                    }
                    layer.close(index);
                }
            });
        });
        return false;
    })

    //保存家庭成员信息
    form.on("submit(addFamliy)", function(data) {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        var index = top.layer.msg('数据提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });
        $.ajax({
            url: application.SERVE_URL + "/sm/smFamily/save", //ajax请求地址
            data: {
                id: $('#famliyId_' + i).val() == null || $('#famliyId_' + i).val() == "" ? null :
                    $('#famliyId_' + i).val(),
                userId: $('#userId').val(),
                name: $('#famliy_name_' + i).val(),
                relation: $('#famliy_relation_' + i).val(),
                birthday: $('#famliy_birthday_' + i).val(),
                sex: $('#famliy_sex_' + i).val(),
                profession: $('#famliy_profession_' + i).val(),
                isschool: $('#isschool_' + i).val(),
                workplace: $('#famliy_workplace_' + i).val(),
                phone: $('#famliy_phone_' + i).val()

            },
            success: function(res) {
                //保存结束后数据回显
                if (res.code == application.REQUEST_SUCCESS) {
                    var data = res.data;
                    $('#famliyId_' + i).val(data.id);
                    top.layer.close(index);
                    top.layer.msg(res.msg);
                    layer.closeAll("iframe");
                    //刷新父页面
                    //此处不刷新
                    // location.reload();
                } else {
                    layer.msg(res.msg);
                }
            }
        });
        return false;
    })
    //删除家庭成员信息
    $(document).on('click', '.delFamliy', function() {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        if ($('#famliyId_' + i).val() == null || $('#famliyId_' + i).val() == "") {
            $('#famliyId_' + i).parents('form').remove();
            return false;
        }
        layer.confirm('确定删除？', {
            icon: 3,
            title: '提示信息'
        }, function(index) {
            $.ajax({
                url: application.SERVE_URL + "/sm/smFamily/delete", //ajax请求地址
                data: {
                    id: $('#famliyId_' + i).val()
                },
                success: function(res) {
                    if (res.code == application.REQUEST_SUCCESS) {
                        layer.close(index);
                        $('#famliyId_' + i).parents('form').remove();
                        top.layer.msg(res.msg);
                    } else {
                        top.layer.msg(res.msg);
                    }
                    layer.close(index);
                }
            });
        });
        return false;
    })

    //保存社会兼职信息
    form.on("submit(addSmSocial)", function(data) {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        var index = top.layer.msg('数据提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });
        $.ajax({
            url: application.SERVE_URL + "/sm/smSocial/save", //ajax请求地址
            data: {
                id: $('#smSocialId_' + i).val() == null || $('#smSocialId_' + i).val() == "" ?
                    null : $('#smSocialId_' + i).val(),
                userId: $('#userId').val(),
                name: $('#smSocial_name_' + i).val(),
                position: $('#smSocial_position_' + i).val(),
                startdate: $('#smSocial_startdate_' + i).val(),
                enddate: $('#smSocial_enddate_' + i).val(),
                status: $('#smSocial_type_' + i).val(),
                infoval: $('#smSocial_infoval_' + i).val()
            },
            success: function(res) {
                //保存结束后数据回显
                if (res.code == application.REQUEST_SUCCESS) {
                    var data = res.data;
                    $('#smSocialId_' + i).val(data.id);
                    top.layer.close(index);
                    top.layer.msg(res.msg);
                    layer.closeAll("iframe");
                    //刷新父页面
                    //此处不刷新
                    // location.reload();
                } else {
                    layer.msg(res.msg);
                }
            }
        });
        return false;
    })
    //删除社会兼职信息
    $(document).on('click', '.delSocial', function() {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        if ($('#smSocialId_' + i).val() == null || $('#smSocialId_' + i).val() == "") {
            $('#smSocialId_' + i).parents('form').remove();
            return false;
        }
        layer.confirm('确定删除？', {
            icon: 3,
            title: '提示信息'
        }, function(index) {
            $.ajax({
                url: application.SERVE_URL + "/sm/smSocial/delete", //ajax请求地址
                data: {
                    id: $('#smSocialId_' + i).val()
                },
                success: function(res) {
                    if (res.code == application.REQUEST_SUCCESS) {
                        layer.close(index);
                        $('#smSocialId_' + i).parents('form').remove();
                        top.layer.msg(res.msg);
                    } else {
                        top.layer.msg(res.msg);
                    }
                    layer.close(index);
                }
            });
        });
        return false;
    })

    //保存校园经历信息
    form.on("submit(addSmExperience)", function(data) {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        var index = top.layer.msg('数据提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });
        $.ajax({
            url: application.SERVE_URL + "/sm/smExperience/save", //ajax请求地址
            data: {
                id: $('#smExperienceId_' + i).val() == null || $('#smExperienceId_' + i).val() ==
                    "" ? null : $('#smExperienceId_' + i).val(),
                userId: $('#userId').val(),
                name: $('#smExperience_name_' + i).val(),
                position: $('#smExperience_position_' + i).val(),
                startDate: $('#smExperience_startDate_' + i).val(),
                endDate: $('#smExperience_endDate_' + i).val(),
                content: $('#smExperience_content_' + i).val()
            },
            success: function(res) {
                //保存结束后数据回显
                if (res.code == application.REQUEST_SUCCESS) {
                    var data = res.data;
                    $('#smExperienceId_' + i).val(data.id);
                    top.layer.close(index);
                    top.layer.msg(res.msg);
                    layer.closeAll("iframe");
                    //刷新父页面
                    //此处不刷新
                    // location.reload();
                } else {
                    layer.msg(res.msg);
                }
            }
        });
        return false;
    })
    //删除校园经历信息
    $(document).on('click', '.delSmExperience', function() {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        if ($('#smExperienceId_' + i).val() == null || $('#smExperienceId_' + i).val() == "") {
            $('#smExperienceId_' + i).parents('form').remove();
            return false;
        }
        layer.confirm('确定删除？', {
            icon: 3,
            title: '提示信息'
        }, function(index) {
            $.ajax({
                url: application.SERVE_URL + "/sm/smExperience/delete", //ajax请求地址
                data: {
                    id: $('#smExperienceId_' + i).val()
                },
                success: function(res) {
                    if (res.code == application.REQUEST_SUCCESS) {
                        layer.close(index);
                        $('#smExperienceId_' + i).parents('form').remove();
                        top.layer.msg(res.msg);
                    } else {
                        top.layer.msg(res.msg);
                    }
                    layer.close(index);
                }
            });
        });
        return false;
    })

    //保存校友历史数据
    form.on("submit(addHistorydata)", function(data) {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        var index = top.layer.msg('数据提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });
        $.ajax({
            url: application.SERVE_URL + "/sm/smHistorydata/save", //ajax请求地址
            data: {
                id: $('#historydataId_' + i).val() == null || $('#historydataId_' + i).val() ==
                    "" ? null : $('#historydataId_' + i).val(),
                userId: $('#userId').val(),
                otherSchool: $('#historydata_otherSchool_' + i).val(),
                otherSpecialty: $('#historydata_otherSpecialty_' + i).val(),
                joinDate: $('#historydata_joinDate_' + i).val(),
                degreeDate: $('#historydata_degreeDate_' + i).val(),
                trustUnit: $('#historydata_trustUnit_' + i).val(),
                beforeSchoolunit: $('#historydata_beforeSchoolunit_' + i).val(),
                nativeAdress: $('#historydata_nativeAdress_' + i).val(),
                personLove: $('#historydata_personLove_' + i).val()
            },
            success: function(res) {
                //保存结束后数据回显
                if (res.code == application.REQUEST_SUCCESS) {
                    var data = res.data;
                    $('#historydataId_' + i).val(data.id);
                    top.layer.close(index);
                    top.layer.msg(res.msg);
                    layer.closeAll("iframe");
                    //刷新父页面
                    //此处不刷新
                    // location.reload();
                } else {
                    layer.msg(res.msg);
                }
            }
        });
        return false;
    })
    //删除校园历史数据
    $(document).on('click', '.delHistorydata', function() {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        if ($('#historydataId_' + i).val() == null || $('#historydataId_' + i).val() == "") {
            $('#historydataId_' + i).parents('form').remove();
            return false;
        }
        layer.confirm('确定删除？', {
            icon: 3,
            title: '提示信息'
        }, function(index) {
            $.ajax({
                url: application.SERVE_URL + "/sm/smHistorydata/delete", //ajax请求地址
                data: {
                    id: $('#historydataId_' + i).val()
                },
                success: function(res) {
                    if (res.code == application.REQUEST_SUCCESS) {
                        layer.close(index);
                        $('#historydataId_' + i).parents('form').remove();
                        top.layer.msg(res.msg);
                    } else {
                        top.layer.msg(res.msg);
                    }
                    layer.close(index);
                }
            });
        });
        return false;
    })

    //保存校友荣誉类型
    form.on("submit(addHonor)", function(data) {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        var index = top.layer.msg('数据提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });
        $.ajax({
            url: application.SERVE_URL + "/sm/smHonor/save", //ajax请求地址
            data: {
                id: $('#honorId_' + i).val() == null || $('#honorId_' + i).val() == "" ? null :
                    $('#honorId_' + i).val(),
                userId: $('#userId').val(),
                name: $('#honor_name_' + i).val(),
                // type: $('#honor_type_' + i).val(),
                date: $('#honor_date_' + i).val() != null && $('#honor_date_' + i).val() != "" ?
                    $('#honor_date_' + i).val() + "-01" : $('#honor_date_' + i).val(),
                industry: $('#honor_industry_' + i).val(),
                infoval: $('#historydata_infoval_' + i).val()
            },
            success: function(res) {
                //保存结束后数据回显
                if (res.code == application.REQUEST_SUCCESS) {
                    var data = res.data;
                    $('#honorId_' + i).val(data.id);
                    top.layer.close(index);
                    top.layer.msg(res.msg);
                    layer.closeAll("iframe");
                    //刷新父页面
                    //此处不刷新
                    // location.reload();
                } else {
                    layer.msg(res.msg);
                }
            }
        });
        return false;
    })
    //删除校友荣誉类型
    $(document).on('click', '.delHonor', function() {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        if ($('#honorId_' + i).val() == null || $('#honorId_' + i).val() == "") {
            $('#honorId_' + i).parents('form').remove();
            return false;
        }
        layer.confirm('确定删除？', {
            icon: 3,
            title: '提示信息'
        }, function(index) {
            $.ajax({
                url: application.SERVE_URL + "/sm/smHonor/delete", //ajax请求地址
                data: {
                    id: $('#honorId_' + i).val()
                },
                success: function(res) {
                    if (res.code == application.REQUEST_SUCCESS) {
                        layer.close(index);
                        $('#honorId_' + i).parents('form').remove();
                        top.layer.msg(res.msg);
                    } else {
                        top.layer.msg(res.msg);
                    }
                    layer.close(index);
                }
            });
        });
        return false;
    })

    //保存职业经历
    form.on("submit(addProfession)", function(data) {
        var i = getIdBystr($(data.form).find(".mark").attr("id"));
        // var address = excFormSelectVal(layui.formSelects.value('profession_address_' + i, 'val'));
        var index = top.layer.msg('数据提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });
        $.ajax({
            url: application.SERVE_URL + "/sm/smProfession/save", //ajax请求地址
            data: {
                id: $('#professionId_' + i).val() == null || $('#professionId_' + i).val() ==
                    "" ? null : $('#professionId_' + i).val(),
                userId: $('#userId').val(),
                detail: $('#profession_detail_' + i).val(),
                industry: $('#profession_industry_' + i).val(),
                department: $('#profession_department_' + i).val(),
                position: $('#profession_position_' + i).val(),
                status: $('#profession_status_' + i).val(),
                workplace: $('#profession_workplace_' + i).val(),
                nature: $('#profession_nature_' + i).val(),
                telephone: $('#profession_telephone_' + i).val(),
                fax: $('#profession_fax_' + i).val(),
                startDate: $('#profession_startDate_' + i).val(),
                endDate: $('#profession_endDate_' + i).val(),
                remark: $('#profess_remark_' + i).val(),
                country: $('#pro_country_' + i).val(),
                province: $('#pro_province_' + i).val(),
                city: $('#pro_city_' + i).val(),
                district: $('#pro_district_' + i).val(),
            },
            success: function(res) {
                //保存结束后数据回显
                if (res.code == application.REQUEST_SUCCESS) {
                    var data = res.data;
                    $('#professionId_' + i).val(data.id);
                    top.layer.close(index);
                    top.layer.msg(res.msg);
                    layer.closeAll("iframe");
                    //刷新父页面
                    //此处不刷新
                    // location.reload();
                } else {
                    layer.msg(res.msg);
                }
            }
        });
        return false;
    })
    //删除职业经历
    $(document).on('click', '.delProfession', function() {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        if ($('#professionId_' + i).val() == null || $('#professionId_' + i).val() == "") {
            $('#professionId_' + i).parents('form').remove();
            return false;
        }
        layer.confirm('确定删除？', {
            icon: 3,
            title: '提示信息'
        }, function(index) {
            $.ajax({
                url: application.SERVE_URL + "/sm/smProfession/delete", //ajax请求地址
                data: {
                    id: $('#professionId_' + i).val()
                },
                success: function(res) {
                    if (res.code == application.REQUEST_SUCCESS) {
                        layer.close(index);
                        $('#professionId_' + i).parents('form').remove();
                        top.layer.msg(res.msg);
                    } else {
                        top.layer.msg(res.msg);
                    }
                    layer.close(index);
                }
            });
        });
        return false;
    })

    //保存校友其他信息
    form.on("submit(addSmOther)", function(data) {
        var i = getIdBystr($(data.form).find(".mark").attr("id"));
        var index = top.layer.msg('数据提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });
        $.ajax({
            url: application.SERVE_URL + "/sm/smOtherInfo/save", //ajax请求地址
            data: {
                id: $('#smOtherInfo_' + i).val() == null || $('#smOtherInfo_' + i).val() == "" ?
                    null : $('#smOtherInfo_' + i).val(),
                userId: $('#userId').val(),
                value: $('#otherInfo_' + i).val(),
                otherId: $('#otherType_' + i).val(),
            },
            success: function(res) {
                //保存结束后数据回显
                if (res.code == application.REQUEST_SUCCESS) {
                    var data = res.data;
                    $('#smOtherInfo_' + i).val(data.id);
                    $('#otherType_' + i).attr("disabled", true);
                    top.layer.close(index);
                    top.layer.msg(res.msg);
                    layer.closeAll("iframe");
                    form.render();
                    //刷新父页面
                    //此处不刷新
                    // location.reload();
                } else {
                    layer.msg(res.msg);
                }
            }
        });
        return false;

    });

    //删除校友其他信息
    $(document).on('click', '.delSmOther', function() {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        if ($('#smOtherInfo_' + i).val() == null || $('#smOtherInfo_' + i).val() == "") {
            $('#smOtherInfo_' + i).parents('form').remove();
            return false;
        }
        layer.confirm('确定删除？', {
            icon: 3,
            title: '提示信息'
        }, function(index) {
            $.ajax({
                url: application.SERVE_URL + "/sm/smOtherInfo/delete", //ajax请求地址
                data: {
                    id: $('#smOtherInfo_' + i).val()
                },
                success: function(res) {
                    if (res.code == application.REQUEST_SUCCESS) {
                        layer.close(index);
                        $('#smOtherInfo_' + i).parents('form').remove();
                        top.layer.msg(res.msg);
                    } else {
                        top.layer.msg(res.msg);
                    }
                    layer.close(index);
                }
            });
        });
        return false;
    });

    //保存校友历史数据
    form.on("submit(addHisEdu)", function(data) {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        var index = top.layer.msg('数据提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });
        $.ajax({
            url: application.SERVE_URL + "/sm/smHisEducation/save", //ajax请求地址
            data: {
                id: $('#hisEduId_' + i).val() == null || $('#hisEduId_' + i).val() == "" ? null :
                    $('#hisEduId_' + i).val(),
                userId: $('#userId').val(),
                education: $('#hisEdu_education_' + i).val(),
                type: $('#hisEdu_type_' + i).val(),
                startdate: $('#hisEdu_startdate_' + i).val(),
                enddate: $('#hisEdu_enddate_' + i).val()
            },
            success: function(res) {
                //保存结束后数据回显
                if (res.code == application.REQUEST_SUCCESS) {
                    var data = res.data;
                    $('#hisEduId_' + i).val(data.id);
                    top.layer.close(index);
                    top.layer.msg(res.msg);
                    layer.closeAll("iframe");
                    //刷新父页面
                    //此处不刷新
                    // location.reload();
                } else {
                    layer.msg(res.msg);
                }
            }
        });
        return false;
    })

    //删除其他教育信息
    $(document).on('click', '.delHisEdu', function() {
        var i = getIdBystr($(this).parents('form').find(".mark").attr("id"));
        if ($('#hisEduId_' + i).val() == null || $('#hisEduId_' + i).val() == "") {
            $('#hisEduId_' + i).parents('form').remove();
            return false;
        }
        layer.confirm('确定删除？', {
            icon: 3,
            title: '提示信息'
        }, function(index) {
            $.ajax({
                url: application.SERVE_URL + "/sm/smHisEducation/delete", //ajax请求地址
                data: {
                    id: $('#hisEduId_' + i).val()
                },
                success: function(res) {
                    if (res.code == application.REQUEST_SUCCESS) {
                        layer.close(index);
                        $('#hisEduId_' + i).parents('form').remove();
                        top.layer.msg(res.msg);
                    } else {
                        top.layer.msg(res.msg);
                    }
                    layer.close(index);
                }
            });
        });
        return false;
    })

    //判断是否填写基本信息
    function judgeIsSaveSm(sysUserId) {
        if (null == sysUserId || "" == sysUserId) {
            layer.msg("请先填写并保存基础信息", {
                icon: 1,
                time: 800
            }, function() {
                location.reload();
            });
        }
    }


    //教育经历数据填充
    function initSchoolInfo(i, school, college, series, specialty) {
        //学历
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'EDU_RECORD'
        }, "eduRecord_" + i);
        //学位类型
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'EDU_DEGREE'
        }, "edu_degree_" + i);
        //学制
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'EDU_SCHOOLEN'
        }, "edu_schoollen_" + i);
        //教育方式
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'EDU_MODEL'
        }, "edu_model_" + i);
        //培养方式
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'EDU_TYPE'
        }, "edu_type_" + i);
        //书院
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'ACADEMY_NAME'
        }, "academy_" + i);

        //入学日期绑定
        laydate.render({
            elem: '#edu_startdate_' + i,
            theme: 'molv',
            trigger: 'click'
        });
        //毕业日期绑定
        laydate.render({
            elem: '#edu_enddate_' + i,
            theme: 'molv',
            trigger: 'click'
        });
        if (school == null || school == undefined || school.length == 0) {
            selectDepartAndSetVal(rootAreaId, 'school_' + i);
        } else {
            initDepartMent(rootAreaId, 'school_' + i, school)
            initDepartMent(school, 'college_' + i, college)
            initDepartMent(college, 'series_' + i, series)
            initDepartMent(series, 'specialty_' + i, specialty)
        }
    }

    //籍贯联动下拉框处理
    function initSelect() {
        if (formSelectDatas == null) {
            formSelects.data('native_place', 'local', {
                arr: formSelectDatas,
                linkage: true,
                linkageWidth: 180
            })
            if (nation_place_datas != null && nation_place_datas != '' && nation_place_datas != 'undefined') {
                layui.formSelects.value('native_place', nation_place_datas);
            }
        } else {
            $.ajax({
                url: application.SERVE_URL + '/sys/sysarea/getFormSelectDatas', //ajax请求地址
                timeout: 10000,
                success: function(rs) {
                    formSelects.data('native_place', 'local', {
                        arr: rs.data.children,
                        linkage: true,
                        linkageWidth: 180
                    })
                    if (nation_place_datas != null && nation_place_datas != '' &&
                        nation_place_datas != 'undefined') {
                        layui.formSelects.value('native_place', nation_place_datas);
                    }
                }
            });
        }
    }


    //联系方式初始化
    function initContact(i) {
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'CONTACT_TYPE'
        }, "type_" + i);
    }

    //政治面貌初始化
    function initPolitics(i) {
        //加入日期绑定
        laydate.render({
            elem: '#politics_startdate_' + i,
            theme: 'molv',
            trigger: 'click'
        });
        //退出日期绑定
        laydate.render({
            elem: '#politics_enddate_' + i,
            theme: 'molv',
            trigger: 'click'
        });
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'POLITICS_TYPE'
        }, "politics_type_" + i);
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'POLITICS_NAME'
        }, "name_" + i);
    }

    //家庭成员初始化
    function initFamliy(i) {
        //家庭成员出生日期
        laydate.render({
            elem: '#famliy_birthday_' + i,
            theme: 'molv',
            trigger: 'click'
        });
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'SEX'
        }, "famliy_sex_" + i);
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'FAMLIY_RELATION'
        }, "famliy_relation_" + i);
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'BOOLEAN_TYPE'
        }, "isschool_" + i);
    }


    //通讯地址初始化
    function initAddress(i, country, province, city, district) {
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'IS_NATION'
        }, "add_type_" + i);
        if (country == null || country == undefined || country.length == 0) {
            selectAreaAndSetVal(rootAreaId, 'country_' + i);
        } else {
            initArea(rootAreaId, 'country_' + i, country)
            initArea(country, 'province_' + i, province)
            initArea(province, 'city_' + i, city)
            initArea(city, 'district_' + i, district)
        }
        form.render();
    }

    //社会兼职初始化
    function initSocial(i) {
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'POLITICS_TYPE'
        }, "smSocial_type_" + i);
        //加入日期绑定
        laydate.render({
            elem: '#smSocial_startdate_' + i,
            theme: 'molv',
            trigger: 'click'
        });
        //退出日期绑定
        laydate.render({
            elem: '#smSocial_enddate_' + i,
            theme: 'molv',
            trigger: 'click'
        });
    }

    //校园经历初始化
    function initExperience(i) {
        //加入日期绑定
        laydate.render({
            elem: '#smExperience_startDate_' + i,
            theme: 'molv',
            trigger: 'click'
        });
        //退出日期绑定
        laydate.render({
            elem: '#smExperience_endDate_' + i,
            theme: 'molv',
            trigger: 'click'
        });
    }

    //校园历史数据初始化
    function initHistorydata(i) {
        //入学日期绑定
        laydate.render({
            elem: '#historydata_joinDate_' + i,
            theme: 'molv',
            trigger: 'click'
        });
        //毕业日期绑定
        laydate.render({
            elem: '#historydata_degreeDate_' + i,
            theme: 'molv',
            trigger: 'click'
        });
    }

    //其他教育经历初始化
    function initHisEdu(i) {
        //入学日期绑定
        laydate.render({
            elem: '#hisEdu_startdate_' + i,
            theme: 'molv',
            trigger: 'click',
            type: 'year'
        });
        //毕业日期绑定
        laydate.render({
            elem: '#hisEdu_enddate_' + i,
            theme: 'molv',
            trigger: 'click',
            type: 'year'
        });
        //类型（学历）
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'EDU_RECORD'
        }, "hisEdu_type_" + i);
    }

    //荣誉成果初始化
    function initHonor(i) {
        //荣誉类型
        // publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
        //     'typeCode': 'HONOR_TYPE'
        // }, "honor_type_" + i);
        //获取时间
        laydate.render({
            elem: '#honor_date_' + i,
            type: 'year',
            theme: 'molv',
            trigger: 'click'
        });
    }

    //职业经历初始化
    function initProfession(i, country, province, city, district) {
        //地址
        //         formSelects.data('profession_address_' + i, 'local', {
        //             arr: formSelectDatas,
        //             linkage: true,
        //             linkageWidth: 180
        //         })
        //         if (addresses != null && addresses != '' && addresses != 'undefined') {
        //             layui.formSelects.value('profession_address_' + i, addresses);
        //         }
        //         formSelects.config('profession_address_' + i, {
        // 
        //         });
        if (country == null || country == undefined || country.length == 0) {
            selectAreaAndSetVal(rootAreaId, 'pro_country_' + i);
        } else {
            initArea(rootAreaId, 'pro_country_' + i, country)
            initArea(country, 'pro_province_' + i, province)
            initArea(province, 'pro_city_' + i, city)
            initArea(city, 'pro_district_' + i, district)
        }
        //是否离职
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'PROFESSION_STATUS'
        }, "profession_status_" + i);
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'COMPANY_TPYE'
        }, "profession_nature_" + i);
        //入职时间
        laydate.render({
            elem: '#profession_startDate_' + i,
            theme: 'molv',
            trigger: 'click'
        });
        //离职时间
        laydate.render({
            elem: '#profession_endDate_' + i,
            theme: 'molv',
            trigger: 'click'
        });
    }

    //其他信息初始化
    function initOtherInfo(i, selectVal) {
        selectOther("otherType_" + i, selectVal);
    }
    //机构选择
    function selectDepartment(idStr) {
        var index = layui.layer.open({
            type: 2,
            title: '院系专业选择',
            shadeClose: true,
            shade: 0.8,
            area: ['320px', '65%'],
            // content: '../views/module/system/menu/menuselect.html',
            content: 'departmentselect.html?' + idStr,
            success: function(layero, index) {
                //
                // 				// 获取子页面的iframe
                var iframe = window['layui-layer-iframe' + index];
                // 				// 向子页面的全局函数child传参
                iframe.child(idStr);
                setTimeout(function() {
                    layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            },
        })
    }
    $(document).on('click', '.edu_departmentName', function() {
        selectDepartment(getIdBystr($(this).attr("id")));
    })
    //学科选择
    function selectSubject(idStr) {
        var index = layui.layer.open({
            type: 2,
            title: '学科选择',
            shadeClose: true,
            shade: 0.8,
            area: ['320px', '65%'],
            content: 'subjectselect.html',
            success: function(layero, index) {
                //
                // 				// 获取子页面的iframe
                var iframe = window['layui-layer-iframe' + index];
                // 				// 向子页面的全局函数child传参
                iframe.child(idStr);
                setTimeout(function() {
                    layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            },
        })
    }
    $(document).on('click', '.edu_subjectName', function() {
        selectSubject(getIdBystr($(this).attr("id")));
    })

    //行业选择
    function selectIndustry(idStr, type) {
        var index = layui.layer.open({
            type: 2,
            title: '行业选择',
            shadeClose: true,
            shade: 0.8,
            area: ['320px', '65%'],
            content: 'industryselect.html',
            success: function(layero, index) {
                //
                // 				// 获取子页面的iframe
                var iframe = window['layui-layer-iframe' + index];
                // 				// 向子页面的全局函数child传参
                iframe.child(idStr, type);
                setTimeout(function() {
                    layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            },
        })
    }
    //荣誉(选择行业)
    $(document).on('click', '.honer_industryName', function() {
        selectIndustry(getIdBystr($(this).attr("id")), INFOTYPE_HONOR);
    })
    //职业经历（选择行业）
    $(document).on('click', '.profession_industryName', function() {
        selectIndustry(getIdBystr($(this).attr("id")), INFOTYPE_PROFESSION);
    })


    //加载其他信息
    function selectOther(selectid, selectValue) {
        $("#" + selectid).empty();
        $("#" + selectid).append('<option  value="" >' + "请选择" + ' </option>');
        for (var i = 0; i < otherTypes.length; i++) {
            $("#" + selectid).append('<option  value="' + otherTypes[i].id + '">' + otherTypes[i].name +
                '</option>'); //往下拉菜单里添加元素
        }
        if (selectValue) {
            $('#' + selectid).val(selectValue);
        }
        // else {
        //     var i = getIdBystr(selectid);
        //     $("#otherInfo_" + i).val(otherTypes[0].value);
        // }
        form.render(); //菜单渲染 把内容加载进去
    }

    //监听其他信息类型
    form.on('select(otherSelect)', function(data) {
        var i = getIdBystr(data.elem.id);
        for (var k = 0; k < otherTypes.length; k++) {
            if (data.value == otherTypes[k].id) {
                $("#otherInfo_" + i).val(otherTypes[k].value);
            }
        }
        form.render();
    })
    //获取id
    function getIdBystr(t) {
        var strs = t.split("_");
        return strs[strs.length - 1];
    }

    //处理三联菜单的内容 如 ： ["102/10201/1020101"]
    function excFormSelectVal(arr) {
        var val = arr[0];
        var arrs = new Array();
        arrs = val.split("/")
        return arrs;
    }

    /*Dom 拼接*/
    /* 教育经历表单 */
    function appendEduForm(i) {
        var addEdu = "<form class='layui-form' style='width:100%;' id='eduFrom'>" +
            "<fieldset class='layui-elem-field site-demo-button' style='padding:15px;'>" +
            "<input type='hidden' class='layui-input mark' id='eduId_" + i + "'>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs8'>" +
            "<label class='layui-form-label'>院系<span class='span-required'>*</span></label>" +
            "<div class='layui-col-xs2'>" +
            "<select id='school_" + i + "' lay-filter='school' lay-verify='required'>" +
            "<option value=''>请选择</option>" +
            "</select>" +
            "</div>" +
            "<div class='layui-col-xs2'>" +
            "<select id='college_" + i + "' lay-filter='college'>" +
            "<option value=''>请选择</option>" +
            "</select>" +
            "</div>" +
            "<div class='layui-col-xs2'>" +
            "<select id='series_" + i + "' lay-filter='series'>" +
            "<option value=''>请选择</option>" +
            "</select>" +
            "</div>" +
            "<div class='layui-col-xs2'>" +
            "<select id='specialty_" + i + "' lay-filter='specialty'>" +
            "<option value=''>请选择</option>" +
            "</select>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs4'>" +
            "<label class='layui-form-label'>书院</label>" +
            "<div class='layui-input-block'>" +
            "<select id='academy_" + i + "' >" +
            "</select>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>临时系</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='edu_tempSeries_" + i +
            "'  placeholder='请输入临时系(最大长度64位)' maxlength ='64'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>临时专业</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='edu_tempSpecialty_" + i +
            "'  placeholder='请输入临时专业(最大长度64位)' maxlength ='64'>" +
            "</div>" +
            "</div>															" +
            "</div>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>学科</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input edu_degreeType' id='edu_degreeType_" + i +
            "' style='display: none;'>" +
            "<input type='text' class='layui-input edu_subjectName' id='edu_subjectName_" + i +
            "' readonly='readonly'>" +

            // "<input type='text' class='layui-input ' id='edu_degreeType_"+ i +"' lay-verify='required' placeholder='请输入学科(最大长度64位)' maxlength ='64'>																	" + 
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>班级</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='edu_classes_" + i +
            "'  placeholder='请输入班级(最大长度64位)' maxlength ='64'>" +
            "</div>" +
            "</div>																" +
            "</div>														" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs3'>" +
            "<label class='layui-form-label'>学历</label>" +
            "<div class='layui-input-block'>" +
            "<select id='eduRecord_" + i + "'>" +
            "</select>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs3'>" +
            "<label class='layui-form-label'>学位类型</label>" +
            "<div class='layui-input-block'>" +
            "<select id='edu_degree_" + i + "'>" +
            "</select>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs3'>" +
            "<label class='layui-form-label'>学号</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='edu_studentNo_" + i +
            "'  placeholder='请输入学号(最大长度16位)' maxlength ='16'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15 layui-col-xs3'>" +
            "<label class='layui-form-label'>学制</label>" +
            "<div class='layui-input-block'>" +
            "<select id='edu_schoollen_" + i + "' >" +
            "</select>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>教育方式</label>" +
            "<div class='layui-input-block'>" +
            "<select id='edu_model_" + i + "' >" +
            "</select>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>培养方式</label>" +
            "<div class='layui-input-block'>" +
            "<select id='edu_type_" + i + "' >" +
            "</select>" +
            "</div>" +
            "</div>								" +
            "</div>								" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>入学日期</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='edu_startdate_" + i +
            "' lay-verify='edu_start_date' placeholder='yyyy-mm-dd' readonly='readonly'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>毕业日期</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input' id='edu_enddate_" + i +
            "' lay-verify='edu_end_date' placeholder='yyyy-mm-dd'  readonly='readonly'>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div class='layui-row'>" +
            "<div class='magb15 layui-col-md12 layui-col-xs12'>" +
            "<label class='layui-form-label'>备注</label>" +
            "<div class='layui-input-block'>" +
            "<textarea placeholder='请输入备注(最大长度512位)' id='edu_remark_" + i +
            "' class='layui-textarea remark' maxlength='512'></textarea>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</fieldset>" +
            "<div class='layui-layer-btn'>" +
            "<button class='layui-btn layui-btn-danger delEdu' >删除</button>" +
            // "<button class='layui-btn setDefEdu' id ='setDefEdu_" + i + "' >设为默认</button>" +
            "<button class='layui-btn' lay-submit='' lay-filter='addEdu'>保存</button>" +
            "</div>" +
            "</form>";
        return addEdu;
    }

    /*地址*/
    function appendAddressForm(i) {
        var Address = "<form  class='layui-form' style='width:100%;'> " +
            "<fieldset  class='layui-elem-field site-demo-button' style='padding:15px;'> " +
            "<input type='hidden' class='layui-input mark' id='addId_" + i + "'   > " +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs12'>" +
            "<label class='layui-form-label'>地址<span class='span-required'>*</span></label>" +
            "<div class='layui-col-xs2'>" +
            "<select  id='country_" + i + "'  lay-filter='country' lay-verify='required'>" +
            "<option value=''>请选择</option>" +
            "</select>" +
            "</div> " +
            "<div class='layui-col-xs2'>" +
            "<select  id='province_" + i + "'  lay-filter='province' >" +
            "<option value=''>请选择</option>" +
            "</select>" +
            "</div> " +
            "<div class='layui-col-xs2'>" +
            "<select  id='city_" + i + "'  lay-filter='city' >" +
            "<option value=''>请选择</option>" +
            "</select>" +
            "</div> " +
            "<div class='layui-col-xs2'>" +
            "<select  id='district_" + i + "'  lay-filter='district' >" +
            "<option value=''>请选择</option>" +
            "</select>" +
            "</div>  " +
            "</div> " +
            "</div>" +
            "<div class='layui-row'> " +
            "<div class='magb15  layui-col-xs6'> " +
            "<label class='layui-form-label'>类型</label>" +
            "<div class='layui-input-block'>" +
            "<select id='add_type_" + i + "'>" +
            "</select>" +
            "</div>" +
            "</div> " +
            "<div class='magb15  layui-col-xs6'> " +
            "<label class='layui-form-label'>详细地址</label> " +
            "<div class='layui-input-block'> " +
            "<input type='text' class='layui-input '  id='detail_" + i +
            "'  placeholder='请输入详细地址(最大长度256位)' maxlength ='256'> " +
            "</div> " +
            "</div> " +
            "</div> " +
            "<div class='layui-row'> " +
            "<div class='magb15  layui-col-xs6'> " +
            "<label class='layui-form-label'>邮编</label> " +
            "<div class='layui-input-block'> " +
            "<input type='text' class='layui-input ' id='zipcode_" + i +
            "'   placeholder='请输入邮编(最大长度16位)' maxlength ='16'> " +
            "</div> " +
            "</div> " +
            "<div class='magb15  layui-col-xs6'> " +
            "<label class='layui-form-label'>备注</label> " +
            "<div class='layui-input-block'> " +
            "<input type='text' class='layui-input'  id='remark_" + i +
            "'   placeholder='请输入备注(最大长度512位)' maxlength ='256'> " +
            "</div> " +
            "</div> " +
            "</div> " +
            "</fieldset> " +
            "<div class='layui-layer-btn '> " +
            "<button class='layui-btn layui-btn-danger delAddress' >删除</button> " +
            "<button class='layui-btn' lay-submit='' lay-filter='addAddress'>保存</button>" +
            "</div> " +
            "</form>";

        return Address;
    }


    /*联系方式*/

    function appendContactForm(i) {
        var addContact = "<form   class='layui-form' style='width:100%;'> " +
            "<fieldset class='layui-elem-field site-demo-button' style='padding:15px;'> " +
            "<input type='hidden' class='layui-input mark' id='contId_" + i + "'   > " +
            "<div class='layui-row'> " +
            "<div class='magb15  layui-col-xs4'> " +
            "<label class='layui-form-label'>联系方式<span class='span-required'>*<span></label> " +
            "<div class='layui-input-block'> " +
            "<select  id='type_" + i + "' lay-verify='required'> " +
            "</select> " +
            "</div> " +
            "</div> " +
            "<div class='magb15  layui-col-xs8'> " +
            "<label class='layui-form-label'>内容<span class='span-required'>*<span></label> " +
            "<div class='layui-input-block'> " +
            "<input type='text' class='layui-input'  lay-verify='required' id='contact_" + i +
            "' placeholder='请输入内容(最大长度64位)' maxlength =''64'> " +
            "</div> " +
            "</div> " +
            "</div> " +
            "</fieldset> " +
            "<div class='layui-layer-btn '> " +
            "<button class='layui-btn layui-btn-danger delContact'  >删除</button>" +
            "<button class='layui-btn' lay-submit='' lay-filter='addContact'>保存</button>" +
            "</div> " +
            "</form> ";

        return addContact;
    }


    /*政治面貌*/
    function appendPoliticsForm(i) {
        var addPolitics = "<form class='layui-form' style='width:100%;' id='politicsForm'>" +
            "<fieldset  class='layui-elem-field site-demo-button' style='padding:15px;'>" +
            "<input type='hidden' class='layui-input mark' id='politicsId_" + i + "'>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>名称<span class='span-required'>*<span></label>" +
            "<div class='layui-input-block'>" +
            "<select id='name_" + i + "' lay-verify='required'>" +
            "</select>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>职务<span class='span-required'>*<span></label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input' id='position_" + i +
            "' lay-verify='required' placeholder='请输入内容(最大长度64位)' maxlength ='64'>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>加入时间</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='politics_startdate_" + i +
            "'  placeholder='yyyy-MM-dd' lay-verify='politics_start_date' readonly='readonly'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>退出时间</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='politics_enddate_" + i +
            "' placeholder='yyyy-MM-dd'  lay-verify='politics_end_date' readonly='readonly'>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>详细说明</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input' id='infoval_" + i +
            "'  placeholder='请输入内容(最大长度256位)' maxlength ='256'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>状态<span class='span-required'>*<span></label>" +
            "<div class='layui-input-block'>" +
            "<select id='politics_type_" + i + "' lay-verify='required'>" +
            "</select>" +
            "</div>" +
            "</div>" +
            "</div>															" +
            "</fieldset>" +
            "<div class='layui-layer-btn '>" +
            "<button class='layui-btn layui-btn-danger delPolitics' >删除</button> " +
            "<button class='layui-btn' lay-submit='' lay-filter='addPolitics'>保存</button>" +
            "</div>" +
            "</form>";
        return addPolitics;
    }

    //家庭成员
    function appendFamliyForm(i) {
        var addFamily = "<form class='layui-form' style='width:100%;' id='famliyForm'>" +
            "<fieldset  class='layui-elem-field site-demo-button' style='padding:15px;'>" +
            "<input type='hidden' class='layui-input mark' id='famliyId_" + i + "'>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs4'>" +
            "<label class='layui-form-label'>关系<span class='span-required'>*<span></label>" +
            "<div class='layui-input-block'>" +
            "<select id='famliy_relation_" + i + "' lay-verify='required'>" +
            "</select>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs4'>" +
            "<label class='layui-form-label'>姓名<span class='span-required'>*<span></label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input' id='famliy_name_" + i +
            "' lay-verify='required' placeholder='请输入内容(最大长度64位)' maxlength ='64'>										" +
            "</div>" +
            "</div>								" +
            "<div class='magb15  layui-col-xs4'>" +
            "<label class='layui-form-label'>性别<span class='span-required'>*<span></label>" +
            "<div class='layui-input-block'>" +
            "<select id='famliy_sex_" + i + "' lay-verify='required'>" +
            "</select>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs4'>" +
            "<label class='layui-form-label'>出生日期</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='famliy_birthday_" + i +
            "'  placeholder='yyyy-MM-dd' readonly='readonly'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs4'>" +
            "<label class='layui-form-label'>联系电话</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='famliy_phone_" + i +
            "' lay-verify='mobile' placeholder='请输入手机号' maxlength ='11'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs4'>" +
            "<label class='layui-form-label'>是否校友<span class='span-required'>*<span></label>" +
            "<div class='layui-input-block'>" +
            "<select id='isschool_" + i + "' lay-verify='required'>" +
            "</select>" +
            "</div>" +
            "</div>								" +
            "</div>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>职业</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input' id='famliy_profession_" + i +
            "'  placeholder='请输入内容(最大长度64位)' maxlength ='64'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>工作单位</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input' id='famliy_workplace_" + i +
            "'  placeholder='请输入内容(最大长度256位)' maxlength ='256'>" +
            "</div>" +
            "</div>" +
            "</div>															" +
            "</fieldset>" +
            "<div class='layui-layer-btn '>" +
            "<button class='layui-btn layui-btn-danger delFamliy'  >删除</button>" +
            "<button class='layui-btn' lay-submit='' lay-filter='addFamliy'>保存</button>" +
            "</div>" +
            "</form>";
        return addFamily;
    }

    //社会兼职
    function appendSocialForm(i) {
        var addSocial = "<form class='layui-form' style='width:100%;' id='smSocialForm'>" +
            "<fieldset class='layui-elem-field site-demo-button' style='padding:15px;'>" +
            "<input type='hidden' class='layui-input mark' id='smSocialId_" + i + "'>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>名称<span class='span-required'>*<span></label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input' id='smSocial_name_" + i +
            "' lay-verify='required' placeholder='请输入内容(最大长度64位)' maxlength ='64'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>职务</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input' id='smSocial_position_" + i +
            "' placeholder='请输入内容(最大长度64位)' maxlength ='64'>" +
            "</div>" +
            "</div>" +
            "</div>" +
            // "<div class='layui-row'>" +
            // "<div class='magb15  layui-col-xs6'>" +
            // "<label class='layui-form-label'>加入时间</label>" +
            // "<div class='layui-input-block'>" +
            // "<input type='text' class='layui-input ' id='smSocial_startdate_" + i +
            // "' lay-verify='smSocial_start_date' placeholder='yyyy-MM-dd'  readonly='readonly'>" +
            // "</div>" +
            // "</div>" +
            // "<div class='magb15  layui-col-xs6'>" +
            // "<label class='layui-form-label'>退出时间</label>" +
            // "<div class='layui-input-block'>" +
            // "<input type='text' class='layui-input ' id='smSocial_enddate_" + i +
            // "' lay-verify='smSocial_end_date' placeholder='yyyy-MM-dd'  readonly='readonly'>" +
            // "</div>" +
            // "</div>" +
            // "</div>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>详细说明</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input' id='smSocial_infoval_" + i +
            "'  placeholder='请输入内容(最大长度256位)' maxlength ='256'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>状态</label>" +
            "<div class='layui-input-block'>" +
            "<select id='smSocial_type_" + i + "' >" +
            "</select>" +
            "</div>" +
            "</div>" +
            "</div>															" +
            "</fieldset>" +
            "<div class='layui-layer-btn '>" +
            "<button class='layui-btn layui-btn-danger delSocial' >删除</button> " +
            "<button class='layui-btn' lay-submit='' lay-filter='addSmSocial'>保存</button>" +
            "</div>" +
            "</form>";
        return addSocial;
    }

    //校园经历
    function appendExperience(i) {
        var addexperience = "<form class='layui-form' style='width:100%;' id='SmExperienceForm'>" +
            "<fieldset class='layui-elem-field site-demo-button' style='padding:15px;'>" +
            "<input type='hidden' class='layui-input mark' id='smExperienceId_" + i + "'>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>组织名称<span class='span-required'>*<span></label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input' id='smExperience_name_" + i +
            "' lay-verify='required' placeholder='请输入内容(最大长度64位)' maxlength ='64'>										" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>职务<span class='span-required'>*<span></label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input' id='smExperience_position_" + i +
            "' lay-verify='required' placeholder='请输入内容(最大长度64位)' maxlength ='64'>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>加入时间</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='smExperience_startDate_" + i +
            "' lay-verify='smExperience_start_date' placeholder='yyyy-MM-dd'  readonly='readonly'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>退出时间</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='smExperience_endDate_" + i +
            "' lay-verify='smExperience_end_date' placeholder='yyyy-MM-dd'  readonly='readonly'>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs12'>" +
            "<label class='layui-form-label'>详细说明</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input' id='smExperience_content_" + i +
            "' placeholder='请输入内容(最大长度64位)' maxlength ='64'>" +
            "</div>" +
            "</div>" +
            "</div>	" +
            "</fieldset>" +
            "<div class='layui-layer-btn '>" +
            "<button class='layui-btn layui-btn-danger delSmExperience' >删除</button> " +
            "<button class='layui-btn' lay-submit='' lay-filter='addSmExperience'>保存</button>" +
            "</div>" +
            "</form>";
        return addexperience;
    }

    //历史数据
    function appendHistorydata(i) {
        var addhistorydata = "<form class='layui-form' style='width:100%;' id='historydataForm'>" +
            "<fieldset  class='layui-elem-field site-demo-button' style='padding:15px;'>" +
            "<input type='hidden' class='layui-input mark' id='historydataId_" + i + "'>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label-defind'>其他学校<span class='span-required'>*<span></label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input layui-input-defind layui-textarea-defind' id='historydata_otherSchool_" +
            i + "' lay-verify='required' placeholder='请输入内容(最大长度64位)' maxlength ='64'>										" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label-defind'>其他学校院系专业</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input layui-input-defind layui-textarea-defind' id='historydata_otherSpecialty_" +
            i + "'  placeholder='请输入内容(最大长度64位)' maxlength ='64'>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label-defind'>入学时间</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input layui-input-defind layui-textarea-defind '  placeholder='yyyy-MM-dd'  id='historydata_joinDate_" +
            i + "' lay-verify='historydata_start_date' readonly='readonly'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label-defind'>毕业时间</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input layui-input-defind layui-textarea-defind' placeholder='yyyy-MM-dd'  id='historydata_degreeDate_" +
            i + "' lay-verify='historydata_end_date' readonly='readonly'>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label-defind'>定向委培单位</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input layui-input-defind layui-textarea-defind' id='historydata_trustUnit_" +
            i + "'  placeholder='请输入内容(最大长度128位)' maxlength ='128'>										" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label-defind'>入学前地区单位</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input layui-input-defind layui-textarea-defind' id='historydata_beforeSchoolunit_" +
            i + "' placeholder='请输入内容(最大长度128位)' maxlength ='128'>" +
            "</div>" +
            "</div>" +
            "</div>	" +
            // "<div class='layui-row'>" +
            // "<div class='magb15  layui-col-xs6'>" +
            // "<label class='layui-form-label-defind'>原始通讯地址</label>" +
            // "<div class='layui-input-block'>" +
            // "<input type='text' class='layui-input layui-input-defind layui-textarea-defind' id='historydata_nativeAdress_" +
            // i + "'  placeholder='请输入内容(最大长度128位)' maxlength ='128'>										" +
            // "</div>" +
            // "</div>" +
            // "<div class='magb15  layui-col-xs6'>" +
            // "<label class='layui-form-label-defind'>个人爱好</label>" +
            // "<div class='layui-input-block'>" +
            // "<input type='text' class='layui-input layui-input-defind layui-textarea-defind' id='historydata_personLove_" +
            // i + "'  placeholder='请输入内容(最大长度256位)' maxlength ='256'>" +
            // "</div>" +
            // "</div>" +
            // "</div>" +
            "</fieldset>" +
            "<div class='layui-layer-btn '>" +
            "<button class='layui-btn layui-btn-danger delHistorydata'>删除</button>" +
            "<button class='layui-btn' lay-submit='' lay-filter='addHistorydata'>保存</button>" +
            "</div>" +
            "</form>";
        return addhistorydata;
    }

    //荣誉成果
    function appendHonor(i) {
        var addHonor = "<form class='layui-form' style='width:100%;' id='honorForm'>" +
            "<fieldset  class='layui-elem-field site-demo-button' style='padding:15px;'>" +
            "<input type='hidden' class='layui-input mark' id='honorId_" + i + "'>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>名称<span class='span-required'>*<span></label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input' id='honor_name_" + i +
            "' lay-verify='required' placeholder='请输入内容(最大长度64位)' maxlength ='64'>" +
            "</div>" +
            "</div>" +
            // "<div class='magb15  layui-col-xs6'>" +
            // "<label class='layui-form-label'>类型<span class='span-required'>*<span></label>" +
            // "<div class='layui-input-block'>" +
            // "<select id='honor_type_" + i + "' lay-verify='required'>" +
            // "</select>										" +
            // "</div>" +
            // "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>获得时间</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='honor_date_" + i +
            "' readonly='readonly' placeholder='yyyy-MM-dd'>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>行业</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input honer_industry' id='honor_industry_" + i +
            "' style='display: none;'> " +
            "<input type='text' class='layui-input honer_industryName' id='honor_industryName_" + i +
            "'  readonly='readonly'> " +
            // "<input type='text' class='layui-input' id='honor_industry_"+ i +"'>" + 
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>详细说明</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input' id='historydata_infoval_" + i +
            "'  placeholder='请输入内容(最大长度256位)' maxlength ='256'>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</fieldset>" +
            "<div class='layui-layer-btn '>" +
            "<button class='layui-btn layui-btn-danger delHonor'>删除</button>" +
            "<button class='layui-btn' lay-submit='' lay-filter='addHonor'>保存</button>" +
            "</div>" +
            "</form>";

        return addHonor;
    }

    //职业经历
    function appendProfession(i) {
        var addProfession = "<form class='layui-form' style='width:100%;' id='professionForm'>" +
            "<fieldset class='layui-elem-field site-demo-button' style='padding:15px;'>" +
            "<input type='hidden' class='layui-input mark' id='professionId_" + i + "'>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>工作单位<span class='span-required'>*<span></label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='profession_workplace_" + i +
            "' lay-verify='required' placeholder='请输入工作单位(最大长度256位)' maxlength ='256'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>单位性质<span class='span-required'>*<span></label>" +
            "<div class='layui-input-block'>" +
            "<select id='profession_nature_" + i + "' >" +
            "</select>											" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs8'>" +
            "<label class='layui-form-label'>地址</label>" +
            "<div class='layui-col-xs2'>" +
            "<select id='pro_country_" + i + "' lay-filter='pro_country'>" +
            "<option value=''>请选择</option>" +
            "</select>" +
            "</div>" +
            "<div class='layui-col-xs2'>" +
            "<select id='pro_province_" + i + "' lay-filter='pro_province'>" +
            "<option value=''>请选择</option>" +
            "</select>" +
            "</div>" +
            "<div class='layui-col-xs2'>" +
            "<select id='pro_city_" + i + "' lay-filter='pro_city'>" +
            "<option value=''>请选择</option>" +
            "</select>" +
            "</div>" +
            "<div class='layui-col-xs2'>" +
            "<select id='pro_district_" + i + "' lay-filter='pro_district'>" +
            "<option value=''>请选择</option>" +
            "</select>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs4'>" +
            "<label class='layui-form-label'>详细地址</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='profession_detail_" + i +
            "'  placeholder='请输入详细地址(最大长度256位)' maxlength ='256'>" +
            "</div>" +
            "</div>" +
            "</div>	" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>行业</label>" +
            "<div class='layui-input-block'>" +
            // "<input type='text' class='layui-input ' id='profession_industry_"+ i +"' lay-verify='required' placeholder='请输入详细地址(最大长度32位)' maxlength ='32'>" + 					
            "<input type='text' class='layui-input profession_industry' id='profession_industry_" + i +
            "' style='display: none;'>" +
            "<input type='text' class='layui-input profession_industryName' id='profession_industryName_" + i +
            "'  readonly='readonly'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>部门</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='profession_department_" + i +
            "'  placeholder='请输入部门(最大长度64位)' maxlength ='64'>" +
            "</div>																			" +
            "</div>" +
            "</div>																																							<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>职位</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='profession_position_" + i +
            "'  placeholder='请输入职位(最大长度256位)' maxlength ='256'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>在职状态</label>" +
            "<div class='layui-input-block'>" +
            "<select id='profession_status_" + i + "' >" +
            "</select>											" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>电话</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='profession_telephone_" + i +
            "' placeholder='请输入电话(最大长度64位)' maxlength ='64'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>传真</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='profession_fax_" + i +
            "'  placeholder='请输入传真(最大长度64位)' maxlength ='64'>	" +
            "</div>" +
            "</div>" +
            "</div>							" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>入职时间</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='profession_startDate_" + i +
            "' placeholder='yyyy-MM-dd' lay-verify='profession_start_date' readonly='readonly'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>离职时间</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input ' id='profession_endDate_" + i +
            "' placeholder='yyyy-MM-dd' lay-verify='profession_end_date' readonly='readonly'>" +
            "</div>" +
            "</div>" +
            "</div>	" +
            "<div class='layui-row'>" +
            "<div class='magb15 layui-col-md12 layui-col-xs12'>" +
            "<label class='layui-form-label'>备注</label>" +
            "<div class='layui-input-block'>" +
            "<textarea placeholder='请输入备注(最大长度512位)' id='profess_remark_" + i +
            "' class='layui-textarea remark' maxlength='512'></textarea>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</fieldset>" +
            "<div class='layui-layer-btn '>" +
            "<button class='layui-btn layui-btn-danger delProfession'>删除</button> " +
            "<button class='layui-btn' lay-submit='' lay-filter='addProfession'>保存</button>" +
            "</div>" +
            "</form>";
        return addProfession;
    }

    //其他信息
    function appendSmOtherInfo(i) {
        var addOtherInfo = "<form class='layui-form' style='width:100%;' id='smOtherInfoForm'>" +
            "<fieldset  class='layui-elem-field site-demo-button' style='padding:15px;'>" +
            "<input type='hidden' class='layui-input mark' id='smOtherInfo_" + i + "'>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs4'>" +
            "<label class='layui-form-label'>名称<span class='span-required'>*<span></label>" +
            "<div class='layui-input-block'>" +
            "<select id='otherType_" + i + "' lay-verify='required' lay-filter ='otherSelect'>" +
            "</select>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs8'>" +
            "<label class='layui-form-label'>信息内容<span class='span-required'>*<span></label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input' id='otherInfo_" + i +
            "' lay-verify='required' placeholder='请输入内容(最大长度256位)' maxlength ='256'>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</fieldset>" +
            "<div class='layui-layer-btn '>" +
            "<button class='layui-btn layui-btn-danger delSmOther' >删除</button>" +
            "<button class='layui-btn' lay-submit='' lay-filter='addSmOther'>保存</button>" +
            "</div>" +
            "</form>"
        return addOtherInfo;
    }

    //其他教育经历
    function appendHisEdu(i) {
        var hisEducation = "<form class='layui-form' style='width:100%;' id='hisEduForm'>" +
            "<fieldset  class='layui-elem-field site-demo-button' style='padding:15px;'>" +
            "<input type='hidden' class='layui-input mark' id='hisEduId_" + i + "'>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label-defind'>教育信息<span class='span-required'>*<span></label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input layui-input-defind layui-textarea-defind'" +
            "id='hisEdu_education_" + i + "' lay-verify='required' placeholder='请输入教育信息(最大长度128位)'" +
            "maxlength='128'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label'>类型<span class='span-required'>*<span></label>" +
            "<div class='layui-input-block'>" +
            "<select id='hisEdu_type_" + i + "' lay-verify='required'>" +
            "</select>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div class='layui-row'>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label-defind'>入学时间</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input layui-input-defind layui-textarea-defind '" +
            "placeholder='yyyy' id='hisEdu_startdate_" + i + "' lay-verify='hisEdu_start_date'" +
            "readonly='readonly'>" +
            "</div>" +
            "</div>" +
            "<div class='magb15  layui-col-xs6'>" +
            "<label class='layui-form-label-defind'>毕业时间</label>" +
            "<div class='layui-input-block'>" +
            "<input type='text' class='layui-input layui-input-defind layui-textarea-defind'" +
            "placeholder='yyyy' id='hisEdu_enddate_" + i + "' lay-verify='hisEdu_end_date'" +
            "readonly='readonly'>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</fieldset>" +
            "" +
            "<div class='layui-layer-btn '>" +
            "<button class='layui-btn layui-btn-danger delHisEdu'  >删除</button>" +
            "<button class='layui-btn' lay-submit='' lay-filter='addHisEdu'>保存</button>" +
            "</div>" +
            "</form>";
        return hisEducation;
    }
})
