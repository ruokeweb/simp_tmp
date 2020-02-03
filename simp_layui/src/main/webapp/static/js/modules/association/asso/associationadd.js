/**
 * @autor lzq
 * @content 校友会你页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base: '../../../../static/js/' // 此处路径请自行处理, 可以使用绝对路径
}).extend({
	/* formSelects: 'formSelects-v4', */
	"application": "application"
});


layui.use(['jquery', 'form', 'layer', 'laydate', 'publicUtil', 'upload', 'application'],function () {
		var form = layui.form,
			application = layui.application,
			publicUtil = layui.publicUtil,
			layer = layui.layer,
			laydate = layui.laydate,
			username = parent.parent.username,
			upload = layui.upload,
			$ = layui.jquery;
		
		
		// 初始化ckEdit
		var summaryEditor;
		var constitutionEditor;
	// 备注
	ClassicEditor.create(document.querySelector("#summary"),{
		extraPlugins: [ MyCustomUploadAdapterPlugin ]
	}).then(editor => {
		summaryEditor = editor;
		if(parent.editFormData.summary!=null){
			summaryEditor.setData(publicUtil.htmlDecode(parent.editFormData.summary));
		}
	})
	.catch(error => {
		console.error(error);
	});
		
	// 章程
	ClassicEditor.create(document.querySelector("#constitution"),{
		extraPlugins: [ MyCustomUploadAdapterPlugin ]
	}).then(editor => {
		constitutionEditor = editor;
		if(parent.editFormData.constitution!=null){
			constitutionEditor.setData(publicUtil.htmlDecode(parent.editFormData.constitution));
		}
	})
	.catch(error => {
		console.error(error);
	});
		
	class MyUploadAdapter {
		constructor( loader ) {
			this.loader = loader;
		}
		upload() {
			return this.loader.file
				.then( file => new Promise( ( resolve, reject ) => {
					this._initRequest();
					this._initListeners( resolve, reject, file );
					this._sendRequest( file );
				} ) );
		}
		abort() {
			if ( this.xhr ) {
				this.xhr.abort();
			}
		}
		_initRequest() {
			const xhr = this.xhr = new XMLHttpRequest();
			xhr.open( 'POST', application.SERVE_URL + "/file/upload/", true );
			xhr.responseType = 'json';
		}
		_initListeners( resolve, reject, file ) {
			const xhr = this.xhr;
			const loader = this.loader;
			const genericErrorText = `Couldn't upload file: ${ file.name }.`;

			xhr.addEventListener( 'error', () => reject( genericErrorText ) );
			xhr.addEventListener( 'abort', () => reject() );
			xhr.addEventListener( 'load', () => {
				const response = xhr.response;
				resolve( {
					default: application.SERVE_URL + application.FILEPATH + response.data
				} );
			} );
			if ( xhr.upload ) {
				xhr.upload.addEventListener( 'progress', evt => {
					if ( evt.lengthComputable ) {
						loader.uploadTotal = evt.total;
						loader.uploaded = evt.loaded;
					}
				} );
			}
		}

		_sendRequest( file ) {
			// Prepare the form data.
			const data = new FormData();

			data.append( 'file', file );
			this.xhr.send( data );
		}
	}	
	function MyCustomUploadAdapterPlugin( editor ) {
		editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
			// Configure the URL to the upload script in your back-end here!
			return new MyUploadAdapter( loader );
		};
	}			
		var rootAreaId = "0";
		// 编辑初始化
		initAssociationData(rootAreaId);
		
		// 日期选择器两个
		laydate.render({
			elem: '#open_date'
			,theme: 'molv',
			trigger: 'click'
		});
		
		laydate.render({
			elem: '#next_periods_date'
			,theme: 'molv',
			trigger: 'click'
		});

		// 国家省市县联动两个
		form.on('select(country)', function (data) {
			selectAreaAndSetVal(data.value,"province");
			selectAreaAndSetVal($("#province").val(),"city");
		})
		
		form.on('select(province)', function (data) {
			selectAreaAndSetVal(data.value,"city");
		})
		
		
		// 页面打开时，或者基本信息时，点击填充数据
		function initAssociationData(rootAreaId) {
			if (parent.editFormData) {
				$("#associationid").val(publicUtil.htmlDecode(parent.editFormData.id));
				$("#parentId").val(publicUtil.htmlDecode(parent.editFormData.parentId));
				$("#parentName").val(publicUtil.htmlDecode(parent.editFormData.parentAsAssociation.name));
				$("#name").val(publicUtil.htmlDecode(parent.editFormData.name));
				$('#open_date').val(publicUtil.htmlDecode(parent.editFormData.openDate));
				$("#type").val(publicUtil.htmlDecode(parent.editFormData.type));
				$("#phone").val(publicUtil.htmlDecode(parent.editFormData.phone));
				$("#email").val(publicUtil.htmlDecode(parent.editFormData.email));
				$("#periods").val(publicUtil.htmlDecode(parent.editFormData.periods));
				$("#next_periods_date").val(publicUtil.htmlDecode(parent.editFormData.nextPeriodsdate));
				$("#chairman").val(publicUtil.htmlDecode(parent.editFormData.chairman));
				$("#secretary").val(publicUtil.htmlDecode(parent.editFormData.secretary));
				$("#address").val(publicUtil.htmlDecode(parent.editFormData.address));
				$("#logo").val(publicUtil.htmlDecode(parent.editFormData.logo));
				$("#sum").val(parent.editFormData.sum);
				// 图片赋值
				$("#photo").attr("src",application.SERVE_URL + application.FILEPATH + parent.editFormData.logo);
				if(parent.editFormData.country != null){
					selectAreaAndSetVal(rootAreaId,"country",parent.editFormData.country);
				}else{
					selectAreaAndSetVal(rootAreaId,"country");
				}
				
				if(parent.editFormData.province != null){
					selectAreaAndSetVal(parent.editFormData.country,"province",parent.editFormData.province);			
				}else{
					selectAreaAndSetVal(parent.editFormData.country,"province");
				}
				if(parent.editFormData.city != null){
					selectAreaAndSetVal(parent.editFormData.province,"city",parent.editFormData.city);
				}

			}else{
				// 初始化
				selectAreaAndSetVal(rootAreaId,"country");
				selectAreaAndSetVal($("#country").val(),"province");
				selectAreaAndSetVal($("#province").val(),"city");
			}
			
			// 校友会类型
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'ASSOCIATION_TYPE'
			}, "type", parent.editFormData ? parent.editFormData.type : null);
			// 区域类型
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'ASSOCIATION_REGION_TYPE'
			}, "region_type", parent.editFormData ? parent.editFormData.regionType : null);
			// 是否授旗
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'BOOLEAN_TYPE'
			}, "authorize_flag", parent.editFormData ? parent.editFormData.authorizeFlag : null);
			// 是否证书
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'BOOLEAN_TYPE'
			}, "certificate_flag", parent.editFormData ? parent.editFormData.certificateFlag : null);
			// 是否注册
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'BOOLEAN_TYPE'
			}, "register_flag", parent.editFormData ? parent.editFormData.registerFlag : null);
			// 是否备案
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'BOOLEAN_TYPE'
			}, "record_flag", parent.editFormData ? parent.editFormData.recordFlag : null);
			// 是否办公室
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'BOOLEAN_TYPE'
			}, "has_office_flag", parent.editFormData ? parent.editFormData.hasOfficeflag : null);
			// 是否网站
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'BOOLEAN_TYPE'
			}, "has_web_flag", parent.editFormData ? parent.editFormData.hasWebflag : null);
			// 是否公众号
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'BOOLEAN_TYPE'
			}, "has_wechat_flag", parent.editFormData ? parent.editFormData.hasWechatflag : null);
			// 是否微博
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'BOOLEAN_TYPE'
			}, "has_blog_flag", parent.editFormData ? parent.editFormData.hasBlogflag : null);
		}
		
	

	var uploadInst = upload.render({
			elem: '#selectphoto',
			url: application.SERVE_URL + '/file/upload/',
			choose: function (obj) {
				// 预读本地文件示例，不支持ie8
				obj.preview(function (index, file, result) {
					$('#photo').attr('src', result); // 图片链接（base64）
				});
			},
			bindAction: '#addAssociation',
			done: function (res, index, upload) {
				$('#logo').val(res.data);
				// 如果上传失败
				if (res.code > 0) {
					return layer.msg('上传成功');
				}
				// 上传成功
			},
			error: function () {
				// 演示失败状态，并实现重传
				var photoPath = $('#photoPath');
				photoPath.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
				photoPath.find('.demo-reload').on('click', function () {
					uploadInst.upload();
				});
			}
		});

		// submit(addUser) 绑定提交按钮（基本信息）
		form.on("submit(addAssociation)", function (data) {
			// 弹出loading
			var index = top.layer.msg('数据提交中，请稍候', {
				icon: 16,
				time: false,
				shade: 0.8
			});
			
			$.ajax({
				url: application.SERVE_URL + '/as/asAssociation/save', // ajax请求地
				data: {
					id: $("#associationid").val() ==null|| $("#associationid").val() =="" ? null :$("#associationid").val(),
					parentId : $("#parentId").val(),
					name: $("#name").val(),
					openDate:$('#open_date').val(),
					type: $("#type").val(),
					regionType: $("#region_type").val(),
					logo: $("#logo").val(),
					periods: $("#periods").val(),
					nextPeriodsdate:$("#next_periods_date").val(),
					chairman:$("#chairman").val(),
					secretary:$("#secretary").val(),
					country: $("#country").val(),
					province: $("#province").val(),
					city: $("#city").val(),
					address: $("#address").val(),
					authorizeFlag: $("#authorize_flag").val(),
					certificateFlag: $("#certificate_flag").val(),
					registerFlag: $("#register_flag").val(),
					recordFlag: $("#record_flag").val(),
					hasOfficeflag: $("#has_office_flag").val(),
					hasWebflag: $("#has_web_flag").val(),
					hasWechatflag: $("#has_wechat_flag").val(),
					hasBlogflag: $("#has_blog_flag").val(),
					sum: $("#sum").val(),
					summary: summaryEditor.getData(),
					constitution: constitutionEditor.getData()
				},
				success: function (data) {
					if (data.code == application.REQUEST_SUCCESS) {
						top.layer.close(index);
						top.layer.msg(data.msg);
						layer.closeAll("iframe");
						// 刷新父页面
						parent.location.reload();
					} else {
						top.layer.msg(data.msg + "(" + data.code + ")");
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
		
		
		// 选择地区
		function selectAreaAndSetVal(parentId,selectid,selectValue){
			var area=sessionStorage.getItem("areaCache");
			// 抓取相关字段属性
			var data=JSON.parse(area)[parentId];
			$("#"+selectid).empty();
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
		
		function selectSmAsso(){
			var index = layui.layer.open({
				type: 2,
				title: '校友会选择',
				shadeClose: true,
				shade: 0.8,
				area: ['280px', '65%'],
				// content: '../views/module/system/menu/menuselect.html',
				content: 'associationselect.html',
				success : function(layero, index){
					//
					setTimeout(function(){
							layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
									tips: 3
							});
					},500)											
				},
			})
		}
		
		
		$(".parentName").click(function(){
			selectSmAsso();
		})	
		
		
		layer.closeAll();
	})
