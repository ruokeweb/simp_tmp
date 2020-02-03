/**
 * @autor lzq
 * @content 校友企业内容页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base: '../../../../static/js/' // 此处路径请自行处理, 可以使用绝对路径
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
});


layui.use(['jquery', 'form', 'layer', 'laydate', 'publicUtil', 'upload', 'application'], function() {
	var form = layui.form,
		application = layui.application,
		publicUtil = layui.publicUtil,
		layer = layui.layer,
		laydate = layui.laydate,
		username = parent.parent.username,
		upload = layui.upload,
		$ = layui.jquery;
    var INFOTYPE_PROFESSION = "PROFESSION";
	// 初始化ckEdit
	var contentEditor;
	// 简介
	ClassicEditor.create(document.querySelector("#content"),{
		extraPlugins: [ MyCustomUploadAdapterPlugin ]
	}).then(editor => {
		contentEditor = editor;
		if (parent.editFormData.summary != null) {
			contentEditor.setData(publicUtil.htmlDecode(parent.editFormData.summary));
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
	// 编辑初始化
	initAssociationData();

	// 日期选择器两个
	laydate.render({
		elem: '#create_date',
		theme: 'molv',
		trigger: 'click'
	});

    //职业经历（选择行业）
    $(document).on('click', '.profession_industryName', function() {
        selectIndustry(getIdBystr($(this).attr("id")),INFOTYPE_PROFESSION);
    })
    //行业选择
    function selectIndustry(idStr,type){
        var index = layui.layer.open({
            type: 2,
            title: '行业选择',
            shadeClose: true,
            shade: 0.8,
            area: ['320px', '65%'],
            content: '../../schoolmate/schoolmate/industryselect.html',
            success: function(layero, index) {
                //
                // 				// 获取子页面的iframe
                var iframe = window['layui-layer-iframe' + index];
                // 				// 向子页面的全局函数child传参
                iframe.child(idStr,type);
                setTimeout(function() {
                    layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            },
        })
    }

    //获取id
    function getIdBystr(t) {
        var strs = t.split("_");
        return strs[strs.length - 1];
    }
	// 页面打开时，或者基本信息时，点击填充数据
	function initAssociationData() {
		if (parent.editFormData) {
			$("#enterpriseid").val(publicUtil.htmlDecode(parent.editFormData.id));
			$("#name").val(publicUtil.htmlDecode(parent.editFormData.name));
			$('#create_date').val(publicUtil.htmlDecode(parent.editFormData.createDate));
			$("#profession_industry_0").val(publicUtil.htmlDecode(parent.editFormData.industry));
			$("#type").val(publicUtil.htmlDecode(parent.editFormData.type));
			$("#phone").val(publicUtil.htmlDecode(parent.editFormData.phone));
			$("#email").val(publicUtil.htmlDecode(parent.editFormData.email));
			$("#web_site").val(publicUtil.htmlDecode(parent.editFormData.webSite));
			$("#wechat").val(publicUtil.htmlDecode(parent.editFormData.wechat));
			$("#address").val(publicUtil.htmlDecode(parent.editFormData.address));
			$("#content").val(publicUtil.htmlDecode(parent.editFormData.content));
           $('#profession_industryName_0').val(publicUtil.htmlDecode(parent.editFormData.remark));
		}
		// 校友企业所属行业
		// 			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
		// 				'typeCode': 'PROFESSION'
		// 			}, "profession_industryName_0", parent.editFormData ? parent.editFormData.industry : null);
		// 单位性质
		publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
			'typeCode': 'COMPANY_TPYE'
		}, "type", parent.editFormData ? parent.editFormData.type : null);
		// 
	}

	// 	var uploadInst = upload.render({
	// 			elem: '#selectphoto',
	// 			url: application.SERVE_URL + '/file/upload/',
	// 			choose: function (obj) {
	// 				// 预读本地文件示例，不支持ie8
	// 				obj.preview(function (index, file, result) {
	// 					$('#photo').attr('src', result); // 图片链接（base64）
	// 				});
	// 			},
	// 			bindAction: '#addAssociation',
	// 			done: function (res, index, upload) {
	// 				$('#logo').val(res.data);
	// 				// 如果上传失败
	// 				if (res.code > 0) {
	// 					return layer.msg('上传成功');
	// 				}
	// 				// 上传成功
	// 			},
	// 			error: function () {
	// 				// 演示失败状态，并实现重传
	// 				var photoPath = $('#photoPath');
	// 				photoPath.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
	// 				photoPath.find('.demo-reload').on('click', function () {
	// 					uploadInst.upload();
	// 				});
	// 			}
	// 		});

	//参数验证
	function validate(){
		
		return false;
	}

	// submit(addUser) 绑定提交按钮（基本信息）
	form.on("submit(addEnterprise)", function(data) {
		if(!$("#phone").val()){
			
		}		
		if(!$("#email").val()){
			
		}	
		if(!$("#web_site").val()){
			
		}	
		// 弹出loading
		var index = top.layer.msg('数据提交中，请稍候', {
			icon: 16,
			time: false,
			shade: 0.8
		});

		$.ajax({
			url: application.SERVE_URL + '/ent/enterprise/save', // ajax请求地
			data: {
				id: $("#enterpriseid").val() == null || $("#enterpriseid").val() == "" ? null : $("#enterpriseid").val(),
				name: $("#name").val(),
				createDate: $('#create_date').val(),
				industry: $("#profession_industry_0").val(),
				type: $("#type").val(),
				phone: $("#phone").val(),
				email: $("#email").val(),
				webSite: $("#web_site").val(),
				wechat: $("#wechat").val(),
				address: $("#address").val(),
				content: contentEditor.getData(),
                remark:$("#profession_industryName_0").val()
			},
			success: function(data) {
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
	$("#close").click(closeSelf);
})
