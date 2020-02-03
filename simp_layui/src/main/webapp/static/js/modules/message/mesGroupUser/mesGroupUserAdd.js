/**
 * @autor syp
 * @content 群组详情页面js
 * @returns
 * @Time 2018-09-12
 */
layui.config({
    base : "../../../../static/js/"
}).extend({
    "validparam"  : "validparam",
    publicUtil : "publicUtil",
    "dateUtils"  : "dateUtils",
    "application": "application"
})
var mes_groupId;
layui.use(['form','layer','application','table','validparam','dateUtils','publicUtil','laydate'],function(){
    var form = layui.form,
        application = layui.application,
        validparam = layui.validparam,
        dateUtils = layui.dateUtils,
        table = layui.table,
        laydate = layui.laydate,
        publicUtil = layui.publicUtil,
        layer = parent.layer,
        _$ = layui.jquery;

    //左侧机构树(群组Id)
    mes_groupId = parent.mes_groupId;
    var rootAreaId = "0";
    //选中标记
    var flag ;
    //节点标记
    var treeCheckNode ;
    //节点标记
    var treeObj;
    //校友标签标记
    var markTreeObj;
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
        check: {
            enable: true,
            chkStyle: "radio", //单选框
            radioType: "all" //对所有节点设置单选
        }
    };
    //校友标签树 复选
    // var markTreeSetting = {
    //     view: {
    //         dblClickExpand: false,
    //         showLine: false
    //     },
    //     check: {
    //         enable: true,
    //         chkboxType: {"Y": "ps", "N": "s"}
    //     },
    //     data: {
    //         simpleData: {
    //             enable: true,
    //             idKey: "id",
    //             pIdKey: "parentId",
    //             rootPId: "0"
    //         }
    //     },
    //     callback: {
    //         onClick: onClick,
    //         onCheck: onCheck
    //     }
    // };
    //初始化树
    initTree(parent.formDatas);
    function initTree(FormDatas) {
        _$.ajax({
            url: application.SERVE_URL+'/settings/settingDepartment/tree',
            type:"post",
            success: function (data) {
                treeObj = $.fn.zTree.init($("#orgTree"), setting, covert(data.data)); //加载数据
                //初始化
                //获取根节点个数,getNodes获取的是根节点的集合
                var nodeList = treeObj.getNodes();
                //展开第一个根节点
                treeObj.expandNode(nodeList[0], true);

                treeObj = $.fn.zTree.init($("#orgTree"), setting, covert(data.data)); //加载数据
                //获取根节点个数,getNodes获取的是根节点的集合
                var nodeList = treeObj.getNodes();
                //展开第一个根节点
                treeObj.expandNode(nodeList[0], true);

                //回显之前的数据
                if(FormDatas.length > 0){

                    for(var i =0; i< FormDatas.length; i++ ){
                        if(FormDatas[i].field == 'department'){
                            publicUtil.setTreeSel(FormDatas[i].value,treeObj);
                        }else if(FormDatas[i].field == 'sex'){
                            publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                                'typeCode': 'SEX'
                            }, "sex",FormDatas[i].value);
                        }else if(FormDatas[i].field == 'type'){
                            publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
                                'typeCode': 'SCHOOLEMATE_TYPE'
                            }, "type", FormDatas[i].value);
                        }else if(FormDatas[i].field == 'startdate'){
                            $("#startDate").val(FormDatas[i].value);
                        }else if(FormDatas[i].field == 'enddate') {
                            $("#endDate").val(FormDatas[i].value);
                        }else if(FormDatas[i].field == 'country') {
                            $("#country").val(FormDatas[i].value);
                        }
                        form.render();
                    }
                }else{
                    initSelectTree();
                }
                //initMarkTree(FormDatas);
                initSelectTree("markListTree", true, {"Y": "ps", "N": "s"},FormDatas)
                $("#markListTree"+"Tree").css('display', 'none');
            }

        });

        selectAreaAndSetVal(rootAreaId,"country");
// 
//         selectAreaAndSetVal($("#country").val(),"province");

        publicUtil.selectBase(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'SCHOOLEMATE_TYPE'} ,"type",true);
        publicUtil.selectBase(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'SEX'},'sex',true);
    }

    //初始化标签树
    // function initMarkTree(FormDatas){
    //     _$.ajax({
    //         url: application.SERVE_URL + '/sm/smMark/schoolMarkList',
    //         data:{},
    //         success: function (data) {
    //             markTreeObj = $.fn.zTree.init($("#markListTree"), markTreeSetting, markCovert(data.data));
    //             var nodeList = markTreeObj.getNodes();
    //             markTreeObj.expandNode(nodeList[0], true);
    //             if (FormDatas!=null && FormDatas.length > 0) {
    //                 for (var i = 0; i < FormDatas.length; i++) {
    //
    //                     if (FormDatas[i].field == 'schoolmatemark') {
    //                         var idsArray= new Array();
    //                         idsArray =  FormDatas[i].value.split(",");
    //                         setMarkTreeIds(idsArray, markTreeObj);
    //                     }else if(FormDatas[i].field == 'province') {
    //                         selectProvinceAndSetVal($("#country").val(),"province",FormDatas[i].value);
    //                     }
    //                 }
    //                 for (var i = 0; i < FormDatas.length; i++) {
    //                     if(FormDatas[i].field == 'city') {
    //                         selectProvinceAndSetVal($("#province").val(),"city",FormDatas[i].value);
    //                     }
    //                 }
    //             } else {
    //                 return false;
    //             }
    //         }
    //     });
    // }
    var markTreeSetting = {
        view: {
            dblClickExpand: false,
            showLine: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "parentId",
                rootPId: "0"
            }
        },
        check: {
            enable: false,
            chkboxType: {"Y": "ps", "N": "s"}
        },
        callback: {
            //onAsyncSuccess: onAsyncSuccess,
            onClick: onClick,
            onCheck: onCheck
        }

    };
    function initSelectTree(id, isMultiple, chkboxType,FormDatas) {

        if (isMultiple) {
            markTreeSetting.check.enable = isMultiple;
        }
        if (chkboxType !== undefined && chkboxType != null) {
            markTreeSetting.check.chkboxType = chkboxType;
        }
        var html = '<div class = "layui-select-title" >' +
            '<input id="' + id + 'Show"' + 'type = "text" placeholder = "请选择" value = "" class = "layui-input" readonly>' +
            '<i class= "layui-edge" ></i>' +
            '</div>';
        $("#" + id).append(html);
        $("#" + id).parent().append('<div class="tree-content scrollbar">' +
            '<input hidden id="' + id + 'Hide" ' +
            'name="' + $(".select-tree").attr("id") + '">' +
            '<ul id="' + id + 'Tree" class="ztree scrollbar" style="margin-top:0;"></ul>' +
            '</div>');
        $("#" + id).bind("click", function () {
            if ($(this).parent().find(".tree-content").css("display") !== "none") {
                hideMenu()
            } else {
                $("#"+id+"Tree").css('display', 'block');
                $(this).addClass("layui-form-selected");
                var Offset = $(this).offset();
                var width = $(this).width() - 2;
                $(this).parent().find(".tree-content").css({
                    left: Offset.left + "px",
                    top: Offset.top + $(this).height() + "px"
                }).slideDown("fast");
                $(this).parent().find(".tree-content").css({
                    width: width
                });
                $("body").bind("mousedown", onBodyDown);
            }
        });
        _$.ajax({
            url: application.SERVE_URL + '/sm/smMark/schoolMarkList',
            data:{},
            success: function (data) {
                markTreeObj = $.fn.zTree.init($("#" + id + "Tree"), markTreeSetting, markCovert(data.data));
                //$("#" + treeId + "Hide").attr("value", true);
                //markTreeObj = $.fn.zTree.init($("#markListTree"), markTreeSetting, markCovert(data.data));
                //var nodeList = markTreeObj.getNodes();
                //markTreeObj.expandNode(nodeList[0], true);
                if (FormDatas!=null && FormDatas.length > 0) {
                    for (var i = 0; i < FormDatas.length; i++) {

                        if (FormDatas[i].field == 'schoolmatemark') {
                            debugger;
                            var idsArray= new Array();
                            idsArray =  FormDatas[i].value.split(",");
                            setMarkTreeIds(idsArray, markTreeObj);
                            assignmentCondition(id + "Tree",  idsArray ,data.data);
                        }else if(FormDatas[i].field == 'province') {
                            selectProvinceAndSetVal($("#country").val(),"province",FormDatas[i].value);
                        }
                    }
                    for (var i = 0; i < FormDatas.length; i++) {
                        if(FormDatas[i].field == 'city') {
                            selectProvinceAndSetVal($("#province").val(),"city",FormDatas[i].value);
                        }
                    }
                } else {
                    return false;
                }
                //hideMenu();
            }
        });
    }

    function covert(data) {
        for (var i = 0; i < data.length; i++) {
			if(data[i].startDate==null||data[i].startDate==''||data[i].startDate==undefined){
				data[i].name = publicUtil.htmlDecode(data[i].name);
			}else{
				data[i].name = publicUtil.htmlDecode(data[i].name) +"("+dateUtils.getYearAndDay(data[i].startDate)+"--"+ judgeNull(data[i].endDate)+")"
            }
        }
        return data;
    }

    function markCovert(data) {
        for (var i = 0; i < data.length; i++) {
            data[i].name = publicUtil.htmlDecode(data[i].name);
        }
        return data;
    }

    function judgeNull(data){
        if(data == null ||data =='null' ||data ==""){
            return "至今";
        }else{
            return dateUtils.getYearAndDay(data);
        }
    }

    // 国家省市县联动两个
    form.on('select(country)', function (data) {
        selectAreaAndSetVal(data.value,"province");
        selectAreaAndSetVal($("#province").val(),"city");
    })

    form.on('select(province)', function (data) {
        selectAreaAndSetVal(data.value,"city");
    })

    var department = 0;
    var schoolmatemark;

    form.on("submit(addMesGroupCondition)",function(data){

        if(treeObj.getCheckedNodes().length != 0){
            department = treeObj.getCheckedNodes()[0].id;
        }
        if(markTreeObj.getCheckedNodes().length != 0){
            schoolmatemark  = getmarkIds(removeRoot(markTreeObj.getCheckedNodes(true)))
        }
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        _$.ajax({
            url: application.SERVE_URL+"/mes/mesGroupCondition/saveInfo", //ajax请求地址
            data:{
                groupId: mes_groupId,  //代替群组ID
                department : department,
                type : $("#type").val(),
                sex : $("#sex").val(),
                //新增条件
                country: $("#country").val(),
                province: $("#province").val(),
                city: $("#city").val(),
                schoolmatemark : schoolmatemark,
                startdate : $("#startDate").val(),
                enddate : $("#endDate").val(),
            },
            success: function (res) {
                if(res.code==application.REQUEST_SUCCESS){
                    console.log('success');
                    top.layer.close(index);
                    top.layer.msg(res.msg);
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                }else{
                    layer.msg(res.msg);
                }
            }
        });
        return false;
    })



    $("#close").click(function(){
        layer.closeAll("iframe");
        //刷新父页面
        parent.location.reload();
    })
    laydate.render({
        elem: '#startDate'
        ,type: 'year'
		,trigger: 'click'
        ,range: true
    });
    laydate.render({
        elem: '#endDate'
        ,type: 'year'
        ,trigger: 'click'
        ,range: true
    });
    // 选择地区
    function selectAreaAndSetVal(parentId,selectid,selectValue){
        var area=sessionStorage.getItem("areaCache");
        // 抓取相关字段属性
        var data=JSON.parse(area)[parentId];
        $("#"+selectid).empty();
        $("#"+selectid).append('<option  value="">请选择</option>');
        if(data!=null){
            for(var i =0;i<data.length;i++){
                $("#"+selectid).append('<option  value="'+data[i].id+'">'+data[i].name+'</option>');// 往下拉菜单里添加元素
            }
        }

        if(selectValue){
            $('#'+selectid).val(selectValue);
        }
        form.render('select');
    }

    function selectProvinceAndSetVal(parentId,selectid,proviceid){
        var area=sessionStorage.getItem("areaCache");
        // 抓取相关字段属性
        var data=JSON.parse(area)[parentId];
        $("#"+selectid).empty();
        if(data!=null){
            for(var i =0;i<data.length;i++){
                if(proviceid == data[i].id) {
                    $("#" + selectid).append('<option  value="' + data[i].id + '"  selected="selected"   >' + data[i].name + '</option>');// 往下拉菜单里添加元素
                }
                $("#" + selectid).append('<option  value="' + data[i].id + '">' + data[i].name + '</option>');
            }
        }
        form.render('select');
    }
    //去除根节点
    function removeRoot(treeCheckedDatas){
        for (var i = 0; i < treeCheckedDatas.length; i++) {
            if(treeCheckedDatas[i].parentId == '' || treeCheckedDatas[i].parentId == null){
                treeCheckedDatas.splice(i, 1);
            }
        }
        return treeCheckedDatas;
    }


    function getmarkIds(treeCheckedDatas) {
        var markIds = "";
        for (var i = 0; i < treeCheckedDatas.length; i++) {
            if(i == 0){
                markIds = treeCheckedDatas[i].id ;
            }else{
                markIds =  markIds + "," +treeCheckedDatas[i].id ;
            }
        }
        return markIds;
    }

    function setMarkTreeIds(treeNode,treeObj){
        if(typeof treeNode == 'string'){
            var nodes = treeObj.getNodesByParam("id",
                treeNode, null);
            //勾选当前选中的节点
            if(nodes.length > 0){
                treeObj.checkNode(nodes[0], true, true);
                treeObj.expandNode(nodes[0], true);
            }
        }else{
            if(treeNode.length < 0){
                return;
            }else{
                //遍历勾选角色关联的菜单数据
                for (var i = 0; i < treeNode.length; i++) {
                    //根据角色菜单节点数据的属性搜索，获取与完整菜单树完全匹配的节点JSON对象集合
                    var nodes = treeObj.getNodesByParam("id",
                        treeNode[i], null);
                    //勾选当前选中的节点
                    if(nodes != null || nodes.length != 0 ){
                        treeObj.checkNode(nodes[0], true, false);
                        treeObj.expandNode(nodes[0], true);
                    }
                }
            }
        }
    }

    // function beforeClick(treeId, treeNode) {
    //     var check = (treeNode && !treeNode.isParent);
    //     if (!check) alert("只能选择城市...");
    //     return check;
    // }

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
        $(".select-tree").removeClass("layui-form-selected");
        $(".tree-content").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
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
        $("#" + treeId + "Show").attr("value", names);
        $("#" + treeId + "Show").attr("title", names);
        $("#" + treeId + "Hide").attr("value", ids);
    }

    function assignmentCondition(treeId, nodes ,data) {
        var names = "";
        var ids = "";
        for (var i = 0, l = data.length; i < l; i++) {
            for (var j = 0, m = nodes.length; j< m; j++) {
                if(data[i].id == nodes[j]){
                    names += data[i].name + ",";
                    ids += data[i].id + ",";
                }
            }
        }

        if (names.length > 0) {
            names = names.substring(0, names.length - 1);
            ids = ids.substring(0, ids.length - 1);
        }
        treeId = treeId.substring(0, treeId.length - 4);
        $("#" + treeId + "Show").attr("value", names);
        $("#" + treeId + "Show").attr("title", names);
        $("#" + treeId + "Hide").attr("value", ids);
    }

    function onBodyDown(event) {
        if ($(event.target).parents(".tree-content").html() == null) {
            hideMenu();
        }
    }
    // function onAsyncSuccess(event, treeId, treeNode, msg) {
    //     alert();
    //     assignment(treeId, zTree.getCheckedNodes());
    // };
})