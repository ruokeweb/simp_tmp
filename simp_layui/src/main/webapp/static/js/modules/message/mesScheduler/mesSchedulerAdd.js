/**
 * @autor syp
 * @content 消息群组页面js
 * @returns
 * @Time 2018-09-14
 */
layui.config({
	base : "../../../../static/js/"
}).extend({
	"publicUtil" : "publicUtil"
}) 
var contentEditor = null;
layui.use(['form','layer','application','upload','laydate','publicUtil'],function(){
    var form = layui.form,
    	application = layui.application,
    	publicUtil = layui.publicUtil,
		upload = layui.upload,
        layer = parent.layer,
		laydate = layui.laydate,
        $ = layui.jquery;
	
		//执行一个laydate实例
	laydate.render({
		elem: '#date'
		,theme: 'molv',
		trigger: 'click'
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
	form.on('select(mesTemplate)', function(data){
		var content = $("#mesTemplate").find("option:selected").attr("layui-data");	
		contentEditor.setData(content);
	});
	
	function formEdit(FormDatas){
		if(FormDatas != ''){
			var data = FormDatas;
			$(".id").val( publicUtil.htmlDecode(data.id));
			$(".remark").val( publicUtil.htmlDecode(data.remark));
			$("#date").val( publicUtil.htmlDecode(data.date));
			$("#content").val(publicUtil.htmlDecode(data.content));	
// 			$(".description").val( publicUtil.htmlDecode(data.description));
// 			$(".description").val( publicUtil.htmlDecode(data.description));
			$("#name").val( publicUtil.htmlDecode(data.name));
			selectTemplate("mesTemplate",data.mesTemplate);
			publicUtil.selectBaseAndSetVal(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'MES_SENDTYPE'} ,"sendType",data.sendType);

			var dict=sessionStorage.getItem("dictCache");
			//抓取相关字段属性
			var mes_data=JSON.parse(dict)['MES_SENDTYPE'];
			$("#sendType").empty();
			$("#sendType").append('<option  value="" >'+"请选择"+' </option>');
			for(var i =0;i<mes_data.length;i++){
				if("MES_SENDTYPE_CARD"!=mes_data[i].value) {
					$("#sendType").append('<option  value="' + mes_data[i].value + '">' + mes_data[i].label + '</option>');//往下拉菜单里添加元素
				}
			}
			if(data.sendType){
				$('#sendType').val(data.sendType);
			}

			form.render();//菜单渲染 把内容加载进去
			return false;
		}else{
			selectTemplate("mesTemplate");
			//publicUtil.selectBase(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'MES_SENDTYPE'} ,"sendType",false);
				//从缓存中获取字典类型
			var dict=sessionStorage.getItem("dictCache");
			//抓取相关字段属性
			var data=JSON.parse(dict)['MES_SENDTYPE'];
			$("#sendType").empty();
			for(var i =0;i<data.length;i++){
				if("MES_SENDTYPE_CARD"!=data[i].value){
					$("#sendType").append('<option  value="'+data[i].value+'" >'+data[i].label+' </option>');//往下拉菜单里添加元素
				}

			}
			form.render();//菜单渲染 把内容加载进去
			return false;
		}
	}

	
	/**
	 * 表单回显
	 */
	formEdit(parent.editFormData);

	
    form.on("submit(addMesScheduler)",function(){
		if(contentEditor.getData() == null || contentEditor.getData().length==0){
			layer.msg("发送内容不能为空", {icon: 5, shift: 6});
			return false;
		}
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        
		$.ajax({
			url: application.SERVE_URL+"/mes/mesScheduler/save", //ajax请求地址
			data:{
				id : $(".id").val() ==null|| $(".id").val() =="" ? null : $(".id").val(),
				date : $("#date").val(),
				name : $("#name").val(),
				sendType: $("#sendType").val(),
				remark : $(".remark").val(),
				content: contentEditor.getData(),
				mesTemplate: $("#mesTemplate").val()
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
})