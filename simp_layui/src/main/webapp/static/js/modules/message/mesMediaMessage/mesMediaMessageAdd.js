/**
 * @autor syp
 * @returns
 * @Time 2018-09-14
 */
layui.config({
	base : "../../../../static/js/"
}).extend({
	"publicUtil" : "publicUtil",
    "application": "application",
    "croppers": "croppers"
}) 
var contentEditor = null;
layui.use(['form','layer','application','upload','laydate','publicUtil','croppers'],function(){
    var form = layui.form,
    	application = layui.application,
    	publicUtil = layui.publicUtil,
		upload = layui.upload,
        layer = parent.layer,
        croppers = layui.croppers,
		laydate = layui.laydate,
        $ = layui.jquery;
	var imageId = "";
    var loading = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '数据加载中...',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });;
		//执行一个laydate实例
        
	laydate.render({
		elem: '#delDate'
		,theme: 'molv',
        max: fun_date(7),
		trigger: 'click'
	});

    form.verify({
        Imgrequired: function(value) {
            //判断是否是编辑
            if ((value == null || value.length == 0)) {
                return "请上传封面图"
            }
        }
    });



    upload.render({
        elem: '#uploadAudio'
        ,url: application.SERVE_URL+'/file/fileUpload/'
        ,size: 10000
        ,accept: 'audio' //音频
        ,before: function(res){
            loading = layer.load(2, { //icon支持传入0-2
                shade: [0.5, 'gray'], //0.5透明度的灰色背景
                content: '上传中...',
                success: function(layero) {
                    layero.find('.layui-layer-content').css({
                        'padding-top': '39px',
                        'width': '300px'
                    });
                }
            });
        }
        ,done: function(res){
            if (res.code == application.REQUEST_SUCCESS) {
                $("#appendixName").html(res.data.name);
                $("#audioId").attr("src",application.SERVE_URL + application.FILEPATH +res.data.id);
                // $("#appendixName").attr("href",application.SERVE_URL + application.FILEPATH +res.data.id);
                $("#appendixPath").val(res.data.id);
                top.layer.msg(res.msg);
                layer.close(loading);
            } else {
            	top.layer.msg(res.msg);
                layer.close(loading);
            }
        }
    });

	
    // 初始化ckEdit
	$(function() {
		ClassicEditor.create(document.querySelector("#content"),{
			extraPlugins: [ MyCustomUploadAdapterPlugin ]
		}).then(editor => {
			contentEditor = editor;
			if(parent.editFormData.content!=null){
				contentEditor.setData(publicUtil.htmlDecode(parent.editFormData.content));
			}	
		})
		.catch(error => {
			console.error(error);
		});
	})
	
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
    
   	/**
    * 表单回显
    */
    formEdit(parent.editFormData); 
    
    
	function formEdit(FormDatas){
		if(FormDatas != ''){
			var data = FormDatas;
			$(".id").val( publicUtil.htmlDecode(data.id));
			$(".remark").val( publicUtil.htmlDecode(data.remark));
			$("#delDate").val(publicUtil.htmlDecode(data.delDate));
			$("#content").val(publicUtil.htmlDecode(data.content));	
			$("#title").val( publicUtil.htmlDecode(data.title));
            if (parent.editFormData.coverImage != null && parent.editFormData.coverImage != "") {
                $("#coverImagePhoto").attr("src", application.SERVE_URL + application.FILEPATH + parent.editFormData.coverImage);
                $("#coverImagePath").val(parent.editFormData.coverImage);
                imageId = application.SERVE_URL + application.FILEPATH + parent.editFormData.coverImage;
            }
            if (parent.editFormData.appendix != null && parent.editFormData.appendix != "") {
                $("#appendixName").html(parent.editFormData.appendixName);
                $("#appendixPath").val(parent.editFormData.appendix);
                $("#audioId").attr("src",application.SERVE_URL + application.FILEPATH +parent.editFormData.appendix);
                // $("#appendixName").attr("href",application.SERVE_URL + application.FILEPATH +parent.editFormData.appendix);
            }
			form.render();//菜单渲染 把内容加载进去
            layer.close(loading);
			return false;
		}else{
            layer.close(loading);
            return false;
        }
	}

	
    form.on("submit(addmesMediaMessage)",function(){
		if(contentEditor.getData() == null || contentEditor.getData().length==0){
			layer.msg("发送内容不能为空", {icon: 5, shift: 6});
			return false;
		}
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        
		$.ajax({
			url: application.SERVE_URL+"/mes/mediaMessage/save", //ajax请求地址
			data:{
				id : $(".id").val() ==null|| $(".id").val() =="" ? null : $(".id").val(),
				delDate : $("#delDate").val(),
				title : $("#title").val(),
                appendix: $("#appendixPath").val(),
				coverImage: $("#coverImagePath").val(),
				remark : $(".remark").val(),
				content: contentEditor.getData(),
			},
			success: function (res) {
				if(res.code==application.REQUEST_SUCCESS){
				 	top.layer.close(index);
		            top.layer.msg(res.msg);	
		            layer.closeAll("iframe");
		            //刷新父页面
		            parent.location.reload();
				}else{
					layer.msg(res.msg);
				}
			}
		}); 
        return false;
    })
	/* 选择模板 */
	function selectTemplate(selectid,selectValue){
		$.ajax({
			url: application.SERVE_URL+"/mes/mesTemplate/loadAllListBy", //ajax请求地址
			success: function (res) {
				if(res.code==application.REQUEST_SUCCESS){
					var templates = res.data;
					$("#"+selectid).empty();
					for(var i =0;i<templates.length;i++){
						$("#"+selectid).append('<option  value="'+templates[i].id+'"  layui-data = "'+templates[i].content+'">'+templates[i].name+'</option>');//往下拉菜单里添加元素
					}
					if(selectValue){
						$('#'+selectid).val(selectValue);		
					}else{
						contentEditor.setData(templates[0].content);
					}
										
					form.render();//菜单渲染 把内容加载进去
				}else{
					layer.msg(res.msg);
				}
			}
		}); 		
	}	
    
    croppers.render({
        elem: '#selectphoto'
        ,saveW: 300 //保存宽度
        ,saveH: 400
        ,mark: 3 / 4
        ,resizable: false
        ,viewMode: 1
        ,imageId: $("#coverImagePath")
        ,photo: $("#coverImagePhoto")
        ,data: imageId
        ,area: '900px' //弹窗宽度
        ,url: application.SERVE_URL + '/file/upload/' //图片上传接口返回和（layui 的upload 模块）返回的JOSN一样
        ,done: function(res) { //上传完毕回调
            //此处有坑
        }
    });
    
	$("#close").click(function(){
		layer.closeAll("iframe");
		//刷新父页面
		parent.location.reload();	
	})
	
		//  上传适配器，格式官网上有，以一种Promise 的方式。Promise好像是有阻塞的意思在里面。
	class UploadAdapter {
		constructor(loader) {
			this.loader = loader;
		}
		upload() {
			return new Promise((resolve, reject) => {
				const data = new FormData();
				data.append('upload', this.loader.file);
				data.append('allowSize', 5); //允许图片上传的大小/兆
				$.ajax({
					url: application.SERVE_URL + "/file/upload/",
					type: 'POST',
					data: data,
					dataType: 'json',
					processData: false,
					contentType: false,
					success: function(data) {
						resolve({
							default: application.SERVE_URL + application.FILEPATH + data.data
						});
					}
				});
	
			});
		}
		abort() {}
	}
    //获取几天前后的日期
    function fun_date(days) {
        var date1 = new Date(),
            time1 = date1.getFullYear() + "-" + (date1.getMonth() + 1) + "-" + date1.getDate(); //time1表示当前时间
        var date2 = new Date(date1);
        date2.setDate(date1.getDate() + days);
        var time2 = date2.getFullYear() + "-" + (date2.getMonth() + 1) + "-" + date2.getDate();
        return time2;
    }
})