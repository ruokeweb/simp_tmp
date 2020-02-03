/**
 * @autor syp
 * @content 捐赠项目页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base: '../../../../static/js/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
	/* 	formSelects: 'formSelects-v4', */
	"application": "application",
	"dateUtils": "dateUtils",
    "croppers":"croppers"
});

layui.use(['jquery', 'form', 'layer', 'laydate', /* 'formSelects', */ 'publicUtil', 'upload', 'dateUtils','croppers',
	'application', 'element'
], function() {
	var form = layui.form,
		$ = layui.jquery,
		/* 		formSelects = layui.formSelects, */
		publicUtil = layui.publicUtil,
		application = layui.application,
		layer = layui.layer,
		element = layui.element,
		dateUtils = layui.dateUtils,
        croppers=layui.croppers,
		laydate = layui.laydate,
		username = parent.parent.username,
		upload = layui.upload;
	// 初始化ckEdit
	// var summaryEditor;
	var contentEditor;
    var imageId="";
	// 备注
	// ClassicEditor.create(document.querySelector("#summary"),{
	// 	extraPlugins: [ MyCustomUploadAdapterPlugin ]
	// }).then(editor => {
	// 	summaryEditor = editor;
	// 	if (parent.editFormData.summary != null) {
	// 		summaryEditor.setData(publicUtil.htmlDecode(parent.editFormData.summary));
	// 	}
	// })
	// .catch(error => {
	// 	console.error(error);
	// });

	// 章程
	ClassicEditor.create(document.querySelector("#content"),{
		extraPlugins: [ MyCustomUploadAdapterPlugin ]
	}).then(editor => {
		contentEditor = editor;
		if (parent.editFormData.content != null) {
			contentEditor.setData(publicUtil.htmlDecode(parent.editFormData.content));
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

	//添加验证规则
	form.verify({
		// 			startdate : function(value, item){
		// 					if(value < dateUtils.formatterDate2(new Date())){
		// 							return "开始时间不能在现在之前";
		// 					}
		// 			},
		validateMoney: function(value, item) {
			if (!/^[+]{0,1}(\d+)$/.test(value)) {
				return '金额只能为正整数'; //提示信息
			}
		},
		enddate: function(value, item) {
			if (null!=$("#enddate").val() &&  ""!=$("#enddate").val() &&($("#startdate").val() > $("#enddate").val())) {
				return "开始时间不能大于结束时间";
			}
		}
	})
	//页面打开时，或者基本信息时，点击填充数据
	function initAssociationData() {
		if (parent.editFormData) {
			$("#projectid").val(publicUtil.htmlDecode(parent.editFormData.id));
			$("#name").val(publicUtil.htmlDecode(parent.editFormData.name));
			$("#secondName").val(publicUtil.htmlDecode(parent.editFormData.secondName));
			$("#master").val(publicUtil.htmlDecode(parent.editFormData.master));
			$("#targetMoney").val(publicUtil.htmlDecode(parent.editFormData.targetMoney));
			$("#gotMoney").val(publicUtil.htmlDecode(parent.editFormData.gotMoney));
			$("#donatingNum").val(publicUtil.htmlDecode(parent.editFormData.donatingNum));
			// $("#topic").val(publicUtil.htmlDecode(parent.editFormData.topic));
			$("#sort").val(publicUtil.htmlDecode(parent.editFormData.sort));
			$("#startdate").val(publicUtil.htmlDecode(parent.editFormData.startdate));
			$("#enddate").val(publicUtil.htmlDecode(parent.editFormData.enddate));
			$("#phone").val(publicUtil.htmlDecode(parent.editFormData.phone));
			$("#email").val(publicUtil.htmlDecode(parent.editFormData.email));
			$("#summary").val(publicUtil.htmlDecode(parent.editFormData.summary));
			$("#content").val(publicUtil.htmlDecode(parent.editFormData.content));
			$("#defaultMoney").val(publicUtil.htmlDecode(parent.editFormData.defaultMoney));
		/*	$("#photo").attr('src', application.SERVE_URL + parent.editFormData.pic);
			$("#photoPath").text(parent.editFormData.pic);*/
            if(parent.editFormData.pic != null && parent.editFormData.pic != ""){
                document.getElementById("photo").src= application.SERVE_URL + application.FILEPATH + parent.editFormData.pic;
                $('#photoPath').val(parent.editFormData.pic);
                imageId=application.SERVE_URL + application.FILEPATH + parent.editFormData.pic;
            }
		}
		//捐赠项目状态
		publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
			'typeCode': 'PROJECT_STATUS'
		}, "status", parent.editFormData ? parent.editFormData.status : null);
		//捐赠项目类型
		publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
			'typeCode': 'PROJECT_TYPE'
		}, "type", parent.editFormData ? parent.editFormData.type : null);
		//是否置顶
		publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
			'typeCode': 'BOOLEAN_TYPE'
		}, "topic", parent.editFormData ? parent.editFormData.topic : null);

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
	}
	initAssociationData();
    croppers.render({
        elem: '#selectphoto'
        ,saveW:1200     //保存宽度
        ,saveH:600
        ,mark: 2 / 1
        ,resizable: false
        ,viewMode:1
        ,imageId:$("#photoPath")
        ,photo:$("#photo")
        ,data:imageId
        ,area:'900px'  //弹窗宽度
        ,url: application.SERVE_URL + '/file/upload/'  //图片上传接口返回和（layui 的upload 模块）返回的JOSN一样
        ,done: function(url){ //上传完毕回调
            /* $("#inputimgurl").val(url);
             $("#srcimgurl").attr('src',url);
             $('#bigimg').attr('src', result);*/

        }
    });
	/*var uploadInst = upload.render({
		elem: '#selectphoto',
		url: application.SERVE_URL + '/file/upload/',
		choose: function(obj) {
			//预读本地文件示例，不支持ie8
			obj.preview(function(index, file, result) {
				$('#photo').attr('src', result); //图片链接（base64）
			});
		},
		bindAction: '#addProject',
		done: function(res, index, upload) {
			$('#photoPath').text(res.data);
			//如果上传失败
			if (res.code > 0) {
				return layer.msg('上传成功');
			}
			//上传成功
		},
		error: function() {
			//演示失败状态，并实现重传
			var photoPath = $('#photoPath');
			photoPath.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
			photoPath.find('.demo-reload').on('click', function() {
				uploadInst.upload();
			});
		}
	});*/


	// 附件上传
	/* 	var uploadFile = upload.render({
			elem: '#selectfile'
			,url: application.SERVE_URL+'/sys/sysuser/uploadimg'
			,choose: function(obj){
				//预读本地文件示例，不支持ie8
				obj.preview(function(index, file, result){
					//$('#file').attr('src', result); //图片链接（base64）
				});
			}
			,bindAction: '#addUser'
			,done: function(res,index, upload){
				//如果上传失败
				if(res.code > 0){
					$('#filePath').append("<div class=\"layui-field-box\"><span class=\"uploaded-filename\" data-filename=\""+res.data+"\">"+res.data+"<span><button type=\"button\" class=\"layui-btn layui-btn-danger layui-btn-sm delete-uploaded-file\"><i class=\"layui-icon\">&#xe640;</i></button></div>");
					return layer.msg('上传成功');
				}
				//上传成功
			}
			,error: function(){
				//演示失败状态，并实现重传
				var filePath = $('#filePath');
				filePath.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
				filePath.find('.demo-reload').on('click', function(){
					uploadInst.upload();
				});
			}
		}); */

	// 	$(document).on('click', '.delete-uploaded-file', function () {
	// 		$(this).parent().parent().parent().remove();
	// 	});

	//submit(addUser)  绑定提交按钮（基本信息）
	form.on("submit(addProject)", function(data) {
        if($("#summary").val().length  >= 350 ){
            layer.msg("简介超过最大长度，请重新编辑", {icon: 5, shift: 6});
            return false;
        }
        if(contentEditor.getData().length  >= 512 ){
            layer.msg("内容超过最大长度，请重新编辑", {icon: 5, shift: 6});
            return false;
        }
		//弹出loading
		var index = top.layer.msg('数据提交中，请稍候', {
			icon: 16,
			time: false,
			shade: 0.8
		});
		$.ajax({
			url: application.SERVE_URL + '/don/donProject/save', //ajax请求地址
			data: {
				id: $("#projectid").val(),
				name: $("#name").val(),
				secondName: $("#secondName").val(),
				master: $("#master").val(),
				targetMoney: $("#targetMoney").val(),
				gotMoney: $("#gotMoney").val(),
				donatingNum: $("#donatingNum").val(),
				topic: $("#topic").val(),
				type: $("#type").val(),
				sort: $("#sort").val(),
				startdate: $("#startdate").val(),
				enddate: $("#enddate").val(),
				phone: $("#phone").val(),
				email: $("#email").val(),
				summary: $("#summary").val(),
				content: contentEditor.getData(),
				pic:  $("#photoPath").val(),
				defaultMoney: $("#defaultMoney").val()
				// files:$("#filesname").val().length>0?$("#filesname").val().substring(1):""     //项目附件

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
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index);
		}
		$("#close").click(closeSelf)
		layer.closeAll();
})
