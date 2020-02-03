/**
 * @autor syp
 * @content 校友会校友成员js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base: '../../../../static/js/' // 此处路径请自行处理, 可以使用绝对路径
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
});

var topIndex;
var userObj;
var schoolmateSel;
layui.use(['jquery', 'form', 'layer', 'laydate', 'publicUtil', 'upload', 'application'], function() {
	var form = layui.form,
		application = layui.application,
		publicUtil = layui.publicUtil,
		layer = layui.layer,
		laydate = layui.laydate,
		username = parent.parent.username,
		$ = layui.jquery;

	// 编辑初始化
	initAssociationData();
	// 页面打开时，或者基本信息时，点击填充数据
	function initAssociationData() {
		//校友会信息
        $('#associationid').val(publicUtil.htmlDecode(parent.treeCheckNode));
		if( parent.editFormData){
            var userData = parent.editFormData;
            schoolmateSel = parent.schoolmateSel;
            $('#as_id').val(publicUtil.htmlDecode(userData.id));
			$('#user_id').val(publicUtil.htmlDecode(userData.userId));
            $("#user_name").val(publicUtil.htmlDecode(schoolmateSel.smSchoolmate.name));
            // $("#user_name").val(publicUtil.htmlDecode(userData.smSchoolmate.name));
			$('#schoolmate_id').val(publicUtil.htmlDecode(userData.schoomateId));
			$('#joinDate').val(publicUtil.htmlDecode(userData.joinDate));
			$('#exitDate').val(publicUtil.htmlDecode(userData.exitDate));
            //校友任职信息
            // schoolmateSel = parent.schoolmateSel;
            // if (schoolmateSel) {
            //     $("#user_id").val(publicUtil.htmlDecode(schoolmateSel.userId));
            //     $("#user_name").val(publicUtil.htmlDecode(schoolmateSel.smSchoolmate.name));
            //     $("#schoolmate_id").val(publicUtil.htmlDecode(schoolmateSel.smSchoolmate.id));
            //     $("#postDate").val(publicUtil.htmlDecode(schoolmateSel.postDate));
            //     $("#leaveDate").val(publicUtil.htmlDecode(schoolmateSel.leaveDate));
            // }
		}
        //更新 校友会职务（角色）
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'ASPOST_TYPE'
        }, "roleType", parent.editFormData ? parent.editFormData.isPosting : null);
        //更新 是否现任职
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'BOOLEAN_TYPE'
        }, "isPosting", parent.editFormData ? parent.editFormData.isPosting : null);
		//初始化加入时间和退出时间
		laydate.render({
			elem: '#joinDate'
			,theme: 'molv',
			trigger: 'click'
		});
		laydate.render({
			elem: '#exitDate'
			,theme: 'molv',
			trigger: 'click'
		});
		//初始化任职时间和离任时间
		laydate.render({
			elem: '#postDate'
			,theme: 'molv',
			trigger: 'click'
		});
		laydate.render({
			elem: '#leaveDate'
			,theme: 'molv',
			trigger: 'click'
		});
	}
    form.verify({
        $start_date:"",
        start_date : function(value){
            $start_date = value;
        },
        end_date : function(value){
            var message = "";
            if(!((value>=$start_date))){
                message = "退出时间不能在加入时间之前";
            }
            return message;
        }
    })
     //选择校友
	$(document).on('click', '.user_name', function(edit) {
// 		alert("打开选择校友界面");
		topIndex = layui.layer.open({
								type: 2,
								title: '选择校友',
								shadeClose: true,
								shade: 0.8,
								area: ['85%', '65%'],
								content: 'userselect.html',
								success: function (layero, index) {
									setTimeout(function () {
										layui.layer.tips('点击此处返回角色列表', '.layui-layer-setwin .layui-layer-close', {
											tips: 3

										});
									}, 500)
								},
							})
		
	})


	// submit(addUser) 绑定提交按钮（基本信息）
	form.on("submit(addUser)", function(data) {

		// 弹出loading
		var index = top.layer.msg('数据提交中，请稍候', {
			icon: 16,
			time: false,
			shade: 0.8
		});

		$.ajax({
			url: application.SERVE_URL + '/as/asAssociation/addSysAs', // ajax请求地
			data: {
				asId:$("#associationid").val(),
				userId: $("#user_id").val()
			},
			success: function(data) {
				if (data.code == application.REQUEST_SUCCESS) {
					top.layer.close(index);
					top.layer.msg(data.msg);
					layer.closeAll("iframe");
					// 刷新父页面
					parent.location.reload();
				} else {
					top.layer.msg(data.msg );
				}
			}
		});
		return false;
	})

	function closeSelf() {
		var index = parent.layer.getFrameIndex(window.name); // 先得到当前iframe层的索引
		parent.layer.close(index);
	}
	$("#close").click(closeSelf)

	layer.closeAll();
})
