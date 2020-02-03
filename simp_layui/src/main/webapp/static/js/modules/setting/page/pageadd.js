/**
 * @autor zdl
 * @content 页面配置增加js
 * @returns
 * @Time 2019-05-28
 */
layui.config({
	base: '../../../../static/js/' //此处路径请自行处理, 可以使用绝对路径
}).extend({
	"application": "application"
});

layui.use(['jquery', 'form', 'layer', 'laydate',  'publicUtil', 'application','colorpicker', 'element'],
	function() {
		var form = layui.form,
			$ = layui.jquery,
			publicUtil = layui.publicUtil,
			application = layui.application,
			layer = layui.layer
		var contentEditor;
		// 备注
		ClassicEditor.create(document.querySelector("#content"),{
			extraPlugins: [ MyCustomUploadAdapterPlugin ]
		}).then(editor => {
			contentEditor = editor;
			if(parent.editFormData.content!=null){
				contentEditor.setData(publicUtil.htmlDecode(parent.editFormData.content));
			}
		})

		//页面打开时，或者基本信息时，点击填充数据
		function initPageData() {
			if (parent.editFormData) {
				$("#id").val(publicUtil.htmlDecode(parent.editFormData.id));
				$("#name").val(publicUtil.htmlDecode(parent.editFormData.name));
				$("#code").val(publicUtil.htmlDecode(parent.editFormData.code));


			}
			form.render();
		}
		initPageData();


		//submit(addUser)  绑定提交按钮（基本信息）
		form.on("submit(add)", function(data) {
			if(contentEditor.getData()==""){
				layer.msg("必填项不能为空", {icon: 5, shift: 6});
				return false;
			}
			//弹出loading
			var index = top.layer.msg('数据提交中，请稍候', {
				icon: 16,
				time: false,
				shade: 0.8
			});
			$.ajax({
				url: application.SERVE_URL + '/settings/settingPage/save', //ajax请求地址
				type: "POST",
				data: {
					id: $("#id").val(),
					name: $("#name").val(),
					code: $("#code").val(),
					content:contentEditor.getData()
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
		function MyCustomUploadAdapterPlugin( editor ) {
			editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
				// Configure the URL to the upload script in your back-end here!
				return new MyUploadAdapter( loader );
			};
		}
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
