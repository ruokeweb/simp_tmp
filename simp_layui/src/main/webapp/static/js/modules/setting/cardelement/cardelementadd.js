/**
 * @autor zdl
 * @content 校友卡增加js
 * @returns
 * @Time 2019-02-15
 */
layui.config({
	base: '../../../../static/js/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
	/* 	formSelects: 'formSelects-v4', */
	"application": "application"
});

layui.use(['jquery', 'form', 'layer', 'laydate', /* 'formSelects', */ 'publicUtil', 'upload', 'application','colorpicker', 'element'],
	function() {
		var form = layui.form,
			$ = layui.jquery,
			/* 		formSelects = layui.formSelects, */
			publicUtil = layui.publicUtil,
			application = layui.application,
			layer = layui.layer,
			element = layui.element,
			laydate = layui.laydate,
			colorpicker =  layui.colorpicker,
			username = parent.parent.username,
			upload = layui.upload;

		var cardId =parent.cardId;
		var styleColor = null;
		//表单赋值
    form.verify({
    	level: function(value){
    		var message="";
    		//判断是否是编辑
			if (/^(\+)?\d+($|\\d+$)/.test(value)){
				return;
			}
			else{
				message = "只能输入整数"
				return message;
			}
    	}
    });
		//页面打开时，或者基本信息时，点击填充数据
		function initSchoolmateData() {
			if (parent.editFormData) {
                cardId=parent.editFormData.cardId;
                $(".id").val(publicUtil.htmlDecode(parent.editFormData.id));
				$(".style").val(publicUtil.htmlDecode(parent.editFormData.style));
                styleColor=parent.editFormData.color;
			}
			//校友卡正反面
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode':'CARD_ELEMENT_FACE'
			}, "face", parent.editFormData ? parent.editFormData.face : null);
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode':'CARD_ELEMENT_POSITION'
			}, "position", parent.editFormData ? parent.editFormData.position : null);
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode':'CARD_ELEMENT_STATUS'
			}, "status", parent.editFormData ? parent.editFormData.code : null);
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode':'CARD_ELEMENT_TYPE'
			}, "type", parent.editFormData ? parent.editFormData.type : null);
            var stColor = parent.editFormData.color == '' || parent.editFormData.color == null ||
            parent.editFormData.color ==undefined ? "#0b0b0b" : parent.editFormData.color;

            colorpicker.render({
                elem: '#styleColor',
                color: stColor,
                done: function(color){
                    styleColor = color;
                }
            });
            form.render();
		}
		initSchoolmateData();


		//submit(addUser)  绑定提交按钮（基本信息）
		form.on("submit(addCard)", function(data) {
			console.log(data);
			//弹出loading
			var index = top.layer.msg('数据提交中，请稍候', {
				icon: 16,
				time: false,
				shade: 0.8
			});
			$.ajax({
				url: application.SERVE_URL + '/settings/settingCardElement/save', //ajax请求地址
				type: "POST",
				data: {
					id: $(".id").val(),
                    position: $("#position").val(),
                    cardId: cardId,
                    code: $("#status").val(),
                    status: $("#status :selected").text(),
                    color:styleColor,
                    style:$(".style").val(),
                    type:$("#type").val(),
                    face:$("#face").val(),
				},
				success: function(data) {
					if (data.code == application.REQUEST_SUCCESS) {
						top.layer.close(index);
						top.layer.msg(data.msg);
						layer.closeAll("iframe");
						//刷新父页面
						parent.location.reload();
					} else {
						top.layer.msg(data.msg + "(" + data.code + ")");
					}
				}
			});
			return false;
		})

		function closeSelf() {
			var index = parent.layer.getFrameIndex(window.namselectphotoe); //先得到当前iframe层的索引
			parent.layer.close(index);
		}
		$("#close").click(function () {
            layer.closeAll("iframe");
            //刷新父页面
            parent.location.reload();
        })

    })
