layui.config({
	base: "../../../../static/js/"
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
})
layui.use(['layer', 'application', "publicUtil", 'application'], function() {
	var form = layui.form;
	layer = layui.layer,
		application = layui.application,
		publicUtil = layui.publicUtil,
		_$ = layui.jquery,
		layer = layui.layer;

	var MaxMarkNum = 10;

	//ztree设置
	var setting = {
		view: {
			selectedMulti: false
		},
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId",
				rootPId: "0"								
			}
		}
	};

	$(document).ready(function() {
		_$.ajax({
			url: application.SERVE_URL + '/sm/smMark/tree', //ajax请求地址
			data: {
			},
			success: function(res) {
				ztreeObj = $.fn.zTree.init($("#tree"), setting, res); //加载数据
				var nodeList = ztreeObj.getNodes(); //展开第一个根节点
				ztreeObj.expandNode(nodeList[0], true);
				publicUtil.setTreeSel(parent.formdatas.markList, ztreeObj);
			}
		});

		$("#reset").click(function() {
			ztreeObj.checkAllNodes(false);
		});
	});


	form.on("submit(getSelectData)", function(data) {
        var open =  parent.layer.getFrameIndex(window.name); 
        
		if(getSelectData(removeRoot(ztreeObj.getCheckedNodes(true))).length > MaxMarkNum){
			top.layer.msg("请选择"+MaxMarkNum+"个以内的标签!",{time: 1000});
			return false;
		}		
		//弹出loading
		var index = top.layer.msg('数据提交中，请稍候', {
			icon: 16,
			time: false,
			shade: 0.8
		});
		_$.ajax({
			url: application.SERVE_URL + "/sm/smSchoolmate/saveSchoolmateMark", //ajax请求地址
			contentType: "application/json",
			data: JSON.stringify({
				id : parent.selectTabRowId ,
				userId: parent.formdatas.userId,
				markList: getSelectData(removeRoot(ztreeObj.getCheckedNodes(true))),
				marks : getmarks(removeRoot(ztreeObj.getCheckedNodes(true))),
				markIds : getmarkIds(removeRoot(ztreeObj.getCheckedNodes(true)))
			}),
			success: function(data) {
				if (data.code == application.REQUEST_SUCCESS) {
					top.layer.close(index);
					top.layer.msg(data.msg);
                    parent.layer.close(open);
					//刷新父页面
					// parent.location.reload();
                    $('.layui-laypage-btn').click();
				} else {
					top.layer.msg(data.msg + "(" + data.code + ")");
				}
			}
		});
		return false;
	})

});


//去除根节点
function removeRoot(treeCheckedDatas){
	for (var i = 0; i < treeCheckedDatas.length; i++) {
		if(treeCheckedDatas[i].parentId == '' || treeCheckedDatas[i].parentId == null){
			treeCheckedDatas.splice(i, 1);
		}
	}
	return treeCheckedDatas;
}


function getSelectData(treeCheckedDatas) {
	var menuIds = new Array();
	for (var i = 0; i < treeCheckedDatas.length; i++) {
		menuIds.push({
			"id": treeCheckedDatas[i].id
		});
	}
	return menuIds;
}

function getmarks(treeCheckedDatas) {
	var marks = "";
	for (var i = 0; i < treeCheckedDatas.length; i++) {
		if(i == 0){
			marks = treeCheckedDatas[i].name ;
		}else{
			marks =  marks + "," +treeCheckedDatas[i].name ;			
		}
	}
	return marks;
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