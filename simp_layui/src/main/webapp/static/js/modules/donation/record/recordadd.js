/**
 * @autor syp
 * @content 捐赠记录页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base: '../../../../static/js/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
	/* 	formSelects: 'formSelects-v4', */
	"application": "application"
});

layui.use(['jquery', 'form', 'layer', 'laydate', /* 'formSelects', */ 'publicUtil', 'upload', 'application', 'element'],
	function() {
		var form = layui.form,
			$ = layui.jquery,
			publicUtil = layui.publicUtil,
			application = layui.application,
			layer = layui.layer,
			element = layui.element,
			laydate = layui.laydate,
			upload = layui.upload;

		//添加验证规则
		form.verify({
			validateMoney: function(value, item) {
				if (!/^[+]{0,1}(\d+)$/.test(value)) {
					return '金额只能为正整数'; //提示信息
				}
			}
		})

		//页面打开时，或者基本信息时，点击填充数据
		function initAssociationData() {
			if (parent.editFormData) {
				$("#recordid").val(publicUtil.htmlDecode(parent.editFormData.id));
				$("#name").val(publicUtil.htmlDecode(parent.editFormData.name));
				$("#donType").val(publicUtil.htmlDecode(parent.editFormData.donType));
				$("#phone").val(publicUtil.htmlDecode(parent.editFormData.phone));
				$("#email").val(publicUtil.htmlDecode(parent.editFormData.email));
				$("#money").val(publicUtil.htmlDecode(parent.editFormData.money));
				$("#moneyType").val(publicUtil.htmlDecode(parent.editFormData.moneyType));
				$("#donationName").val(publicUtil.htmlDecode(parent.editFormData.goodsName));
				$("#time").val(publicUtil.htmlDecode(parent.editFormData.time));
				$("#isCertificate").val(publicUtil.htmlDecode(parent.editFormData.isCertificate));
				$("#isInvoice").val(publicUtil.htmlDecode(parent.editFormData.isInvoice));
				$("#style").val(publicUtil.htmlDecode(parent.editFormData.style));
				// $("#isShow").val(publicUtil.htmlDecode(parent.editFormData.isShow));
				$("#invoiceTitle").val(publicUtil.htmlDecode(parent.editFormData.invoiceTitle));
				$("#address").val(publicUtil.htmlDecode(parent.editFormData.address));
				$("#templateId").val(publicUtil.htmlDecode(parent.editFormData.templateId));
                $("#state").val(publicUtil.htmlDecode(parent.editFormData.state));
			}
			//捐赠类型
			publicUtil.selectBaseAndSetVal(null, {
				'typeCode': 'DONATION_TYPE'
			}, "donType", parent.editFormData ? parent.editFormData.donType : null);
			//币种
			publicUtil.selectBaseAndSetVal(null, {
				'typeCode': 'MONEY_TYPE'
			}, "moneyType", parent.editFormData ? parent.editFormData.moneyType : null);
			//是否需要证书
			publicUtil.selectBaseAndSetVal(null, {
				'typeCode': 'BOOLEAN_TYPE'
			}, "isCertificate", parent.editFormData ? parent.editFormData.isCertificate : null);
			//是否需要发票
			publicUtil.selectBaseAndSetVal(null, {
				'typeCode': 'BOOLEAN_TYPE'
			}, "isInvoice", parent.editFormData ? parent.editFormData.isInvoice : null);
			//支付方式
			publicUtil.selectBaseAndSetVal(null, {
				'typeCode': 'PAY_STYLE'
			}, "style", parent.editFormData ? parent.editFormData.style : null);
			//是否需要宣传
			publicUtil.selectBaseAndSetVal(null, {
				'typeCode': 'BOOLEAN_TYPE'
			}, "isShow", parent.editFormData ? parent.editFormData.isShow : null);
			laydate.render({
				elem: '#time',
				theme: 'molv',
			trigger: 'click'
			});
		}
		initAssociationData();

		//submit(addUser)  绑定提交按钮（基本信息）
		form.on("submit(addRecord)", function(data) {
			//弹出loading
			var index = top.layer.msg('数据提交中，请稍候', {
				icon: 16,
				time: false,
				shade: 0.8
			});
			$.ajax({
				url: application.SERVE_URL + '/don/donRecord/save', //ajax请求地址
				data: {
					id: $("#recordid").val(),
					donProjectId: parent.projectObj.id,
					name: $("#name").val(),
					// donType: $("#donType").val(),
                    //目前版本暂不支持捐物
                    donType: "MONEY",
					phone: $("#phone").val(),
					email: $("#email").val(),
					money: $("#money").val(),
					// moneyType: $("#moneyType").val(),
                    //目前版本只支持人民币
                    moneyType: "RMB",
                    goodsName: $("#donationName").val(),
					time: $("#time").val(),
					isCertificate: $("#isCertificate").val(),
					isInvoice: $("#isInvoice").val(),
					style: $("#style").val(),
					isShow: $("#isShow").val(),
					invoiceTitle: $("#invoiceTitle").val(),
					address: $("#address").val(),
					state: $("#state").val() =="" || $("#state").val() ==null ? "NORMAL": $("#state").val()
				},
				success: function(data) {
					if (data.code == application.REQUEST_SUCCESS) {
						top.layer.close(index);
						top.layer.msg(data.msg);
						closeSelf();
						//刷新父页面
						parent.location.reload();
					} else {
						top.layer.msg(data.msg + "(" + data.code + ")");
					}
				},
				error: function(data) {
					var result = data.responseJSON;
					top.layer.msg(result.msg + "(" + result.code + ")");
				}
			});
			return false;
		})

		function closeSelf() {
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index);
		}
		$("#close").click(closeSelf)
	})
