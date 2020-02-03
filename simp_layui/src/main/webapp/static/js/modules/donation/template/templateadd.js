/**
 * @autor syp
 * @content 捐赠模板编辑页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base: '../../../../static/js/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
	"application": "application",
	"dateUtils": "dateUtils"
});

layui.use(['jquery', 'form', 'layer', 'laydate', /* 'formSelects', */ 'publicUtil', 'upload', 'dateUtils',
	'application', 'element'
], function() {
	var form = layui.form,
		$ = layui.jquery,
		publicUtil = layui.publicUtil,
		application = layui.application,
		layer = layui.layer,
		element = layui.element,
		dateUtils = layui.dateUtils,
		laydate = layui.laydate,
		upload = layui.upload;

	// 初始化ckEdit
	var contentEditor;
	// 模板内容
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
        startmoney:function(){
            if ($("#startAmount").val()==""){
                return "金额起始值不能为空";
            }
		},
		endmoney: function(value, item) {

            if ($("#endAmount").val()==""){
                return "金额结束值不能为空";
            }
			if ($("#startAmount").val() > $("#endAmount").val()) {
				return "金额结束值不能小于金额起始值";
			}
		}
	})
	
	//页面打开时，或者基本信息时，点击填充数据
	function initAssociationData() {
		if (parent.editFormData) {
			$("#templateid").val(publicUtil.htmlDecode(parent.editFormData.id));
			$("#name").val(publicUtil.htmlDecode(parent.editFormData.name));
			$("#startAmount").val(publicUtil.htmlDecode(parent.editFormData.startAmount));
			$("#endAmount").val(publicUtil.htmlDecode(parent.editFormData.endAmount));
			$("#description").val(publicUtil.htmlDecode(parent.editFormData.description));
			// $("#content").val(publicUtil.htmlDecode(parent.editFormData.content));
			$("#photo").attr('src', application.SERVE_URL + application.FILEPATH + parent.editFormData.url);
			$("#photoPath").text(parent.editFormData.url);
		}
	}
	
	initAssociationData();
	
//submit(addUser)  绑定提交按钮（基本信息）
	form.on("submit(addTemplate)", function(data) {
		//弹出loading
		var index = top.layer.msg('数据提交中，请稍候', {
			icon: 16,
			time: false,
			shade: 0.8
		});
		$.ajax({
			url: application.SERVE_URL + '/don/donTemplate/save', //ajax请求地址
			data: {
				id: $("#templateid").val(),
				name: $("#name").val(),
				startAmount: $("#startAmount").val(),
				endAmount: $("#endAmount").val(),
				description: $("#description").val(),
				content: contentEditor.getData(),
				url: $('#photoPath').text()
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

	//上传模板图片
	var uploadInst = upload.render({
		elem: '#selectphoto',
		url: application.SERVE_URL + '/file/upload/',
		choose: function(obj) {
			//预读本地文件示例，不支持ie8
			obj.preview(function(index, file, result) {
				$('#photo').attr('src', result); //图片链接（base64）
			});
		},
		bindAction: '#addTemplate',
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
	});

    function closeSelf() {
        // var index = parent.layer.getFrameIndex(window.name); // 先得到当前iframe层的索引
        parent.layer.closeAll();
    }
    $("#close").click(closeSelf);
})
