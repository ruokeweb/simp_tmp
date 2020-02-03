/**
 * @autor lzq
 * @content 用户列表页面js
 * @returns
 * @Time 2018-08-03
 */

layui.config({
	base : "../../../../static/js/"
}).extend({
	"application" : "application",
	"publicUtil" : "publicUtil",
	"dateUtils"  : "dateUtils"
})

var selectTabRowId;
layui.use(['table','form','element','layer','jquery','application','upload','publicUtil','laydate','dateUtils'],function(){
	var layer = layui.layer,
		element = layui.element, 
		form = layui.form,
		laydate = layui.laydate,
		dateUtils = layui.dateUtils,
		_$ = layui.jquery,
		dateUtils = layui.dateUtils,
		publicUtil = layui.publicUtil,
		table = layui.table,
		application = layui.application,
		upload = layui.upload;
		
		application.init();
		//获取权限并加载按钮
		publicUtil.getPerms(application.PERMS_URL,application.HEADER,parent.cur_menu_id,'get','but_per');
		
		element.on('collapse(panel)', function(data){
			// layer.msg('展开状态：'+ data.show);  //展开为true, 闭合为false;
			if(data.show){
				$(".opa").css('display','block');
			}else{
				$(".opa").css('display','none');
			}
		});		
		
		var tableIns;
		//选中标记
		var flag ;
		//节点标记
		var treeCheckNode ;
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
		//初始化树
		initTree();
		function initTree() {
			_$.ajax({
				url: application.SERVE_URL+'/as/asAssociation/tree',
				type:"post",
				success: function (data) {
					treeObj = $.fn.zTree.init($("#orgTree"), setting, covert(data.data)); //加载数据
					//初始化
					var nodeList = treeObj.getNodes();
		　　　　　　	//展开第一个根节点
					treeObj.expandNode(nodeList[0], true);
					treeObj.setting.callback.onClick(null, treeObj.setting.treeId, nodeList[0]);//调用事件	
				}
			});		
		}	
		
		//选择校友确认操作
		_$(document).on('click', '.selectSchoolmate', function(data) {
			// console.log(data);
			console.log(table.checkStatus('smList').data)
			var data = table.checkStatus('smList').data;
			var flag = publicUtil.jurgeSelectRows(table.checkStatus('smList').data);
			// 多个  
			//	var flag = data.length >0;    //
			if (flag) {
				// addSchoolmate(table.checkStatus('smList').data[0]);
				// layui.close(parent.topIndex);
				publicUtil.setAcrossName(data,".user_id",".user_name");
				
				
			} else {
				// top.layer.msg("请先选择要添加的校友！");
				// return false;
			}
			
		});
    table.on('rowRight(smList)', function(obj) {
        publicUtil.show_menu(obj);
    });
    ///左键点击事件
    table.on('row(smList)', function(obj) {
        tableIns = obj.data.id;
        publicUtil.hiddenMenu(obj);
    });
			//行事件
		// table.on('row(smList)', function(obj) {
		// 	$(".layui-select-tr").removeClass("layui-select-tr");
		// 	obj.tr.addClass("layui-select-tr");
		// 	 // rendschoolmateList(obj.data.id);
		// 	tableIns = obj.data.id;
		// });
		
		
		function orgTreeonClick(event, treeId, treeNode, clickFlag){			
			orgId = treeNode.id;
			/**
			 * 校友管理列表
			 */
			tableIns = table.render({
				elem: '#smList',
				url : application.SERVE_URL+'/sm/smSchoolmate/list',
				where : {
					name: $("#name").val(),
					type: $("#type").val(),
					sex : $("#sex").val(),
					nation : $("#nation").val(),
					cardType : $("#cardType").val(),
					cardStatus: $("#cardStatus").val(),
					cardNum : $("#cardNum").val(),
					orgId : orgId,
					studentid : $("#cardNum").val(),
					degree : $("#degree").val(),
					degreetype : $("#degreetype").val(),
					schoollen : $("#schoollen").val(),
					queryBeginDate : $("#startdate").val(),
					queryEndDate : $("#enddate").val(),
					contactType : $("#contact_type").val(),
					contact : $("#contact").val(),
					areaId : $("#address" + "Hide").val()
				},
				//生产坏境下请求后台
				cellMinWidth : 95,
				page : true,
				even : true ,
				headers : { 'Authorization' : application.HEADER},
				height : "full-185",
				limit : 10,
				id : "smList",
				cols : [[
					{type:'checkbox'},
					{field: 'name', title: '姓名'},
					{field: 'sex', title: '性别'},
					{field: 'cardNum', title: '证件号码'},
					{field: 'type', title: '校友类型'},
					{field: 'birthday', title: '生日'},
					{field: 'cardStatus', title: '卡状态'},
				]]
				,done: function(res, curr, count){    //res 接口返回的信息,,
					publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'CARD_STATUS'},'cardStatus');
					publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'SEX'},'sex');
					publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'SCHOOLEMATE_TYPE'},'type');
					publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'NATION'},'nation');
				}	
			});				
		}
		
		form.on('select(sm_type)', function(data){
			table.reload("smList",{
				page: {
					curr: 1 //重新从第 1 页开始
				},
				where: {
					name: $("#name").val(),
					type: $("#type").val(),
					sex : $("#sex").val(),
					nation : $("#nation").val(),
					cardStatus: $("#cardStatus").val(),
					cardType : $("#cardType").val(),
					cardNum : $("#cardNum").val(),
					orgId : orgId,
					studentid : $("#cardNum").val(),
					degree : $("#degree").val(),
					degreetype : $("#degreetype").val(),
					schoollen : $("#schoollen").val(),
					queryBeginDate : $("#startdate").val(),
					queryEndDate : $("#enddate").val(),
					contactType : $("#contact_type").val(),
					contact : $("#contact").val(),
					areaId : $("#address" + "Hide").val()
				}
			})			
		});     
		 
		
		//搜索【此功能需要后台配合，所以暂时没有动态效果演示】
		form.on("submit(searchForm)",function(data){
				table.reload("smList",{
					page: {
						curr: 1 //重新从第 1 页开始
					},
					where: {
						name: $("#name").val(),
						type: $("#type").val(),
						sex : $("#sex").val(),
						nation : $("#nation").val(),
						cardStatus: $("#cardStatus").val(),
						cardType : $("#cardType").val(),
						cardNum : $("#cardNum").val(),
						orgId : orgId,
						studentid : $("#cardNum").val(),
						degree : $("#degree").val(),
						degreetype : $("#degreetype").val(),
						schoollen : $("#schoollen").val(),
						queryBeginDate : $("#startdate").val(),
						queryEndDate : $("#enddate").val(),
						contactType : $("#contact_type").val(),
						contact : $("#contact").val(),
						areaId : $("#address" + "Hide").val()
					}
				})
				$(".opa").css('display','none');
				$("#panel").click();
		});

		
		/* ------------------------------------------------------------筛选js--------------------------------------------------------------- */
		//初始化
		publicUtil.selectBase(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'NATION'} ,"nation",true);
		publicUtil.selectBase(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'SCHOOLEMATE_TYPE'} ,"type",true);
		publicUtil.selectBase(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'SEX'} ,"sex",true);
		publicUtil.selectBase(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'IDCARD_TYPE'} ,"cardType",true);
		publicUtil.selectBase(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'CONTACT_TYPE'} ,"contact_type",true,true);
		publicUtil.selectBase(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'EDU_DEGREE'} ,"degree",true);
		publicUtil.selectBase(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'EDU_DEGREETYPE'} ,"degreetype",true);
		publicUtil.selectBase(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'EDU_SCHOOLEN'} ,"schoollen",true);
		publicUtil.selectBase(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'CARD_STATUS'} ,"cardStatus",true);
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
		
		//处理三联菜单的内容 如 ： ["102/10201/1020101"]
		function getFormSelectVal(arr){
			var val = arr[0];
			if(arr != null && arr != ""){
				var arrs =  new Array();
				arrs = val.split("/")
				return arrs[arrs.length -1];
			}
			return null;
		}
		
		/* 树级下拉 */
		function initSelectTree(id,url) {
			var setting = {
				view: {
					selectedMulti: false,
					showLine: false
				},
				data: {
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "parentId"			
					}
				},
				check: {
					enable: false,
					chkboxType: {"Y": "ps", "N": "s"}
				},
				callback: {
		            onClick: onClick,
		            onCheck: onCheck			
				}
			};
		    var html = '<div class = "layui-select-title" >' +
		        '<input id="' + id + 'Show"' + 'type = "text" placeholder = "请选择" value = "" class = "layui-input" readonly>' +
		        '<i class= "layui-edge" ></i>' +
		        '</div>';
		    _$("#" + id).append(html);
		    _$("#" + id).parent().append('<div class="tree-content scrollbar">' +
		        '<input hidden id="' + id + 'Hide" ' +
		        'name="' + $(".select-tree").attr("id") + '">' +
		        '<ul id="' + id + 'Tree" class="ztree scrollbar" style="margin-top:0;"></ul>' +
		        '</div>');
		    $("#" + id).bind("click", function () {
		        if (_$(this).parent().find(".tree-content").css("display") !== "none") {
		            hideMenu()
		        } else {
		            _$(this).addClass("layui-form-selected");
		            var Offset = _$(this).offset();
		            var width = _$(this).width() - 2;
		            _$(this).parent().find(".tree-content").css({
		                left: Offset.left + "px",
		                top: Offset.top + _$(this).height() + "px"
		            }).slideDown("fast");
		            _$(this).parent().find(".tree-content").css({
		                width: width
		            });
		            _$("body").bind("mousedown", onBodyDown);
		        }
		    });
			_$.ajax({
				url: url,
				type: "POST", // 默认使用POST方式
				success: function (data) {
					$.fn.zTree.init($("#"+id+ "Tree"), setting, data.data); //加载数据	
				}
			});	
		}
		
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
				data[i].name = data[i].name +"("+dateUtils.getYearAndDay(data[i].openDate)+"--"+ judgeNull(data[i].closeDate)+")"
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
})