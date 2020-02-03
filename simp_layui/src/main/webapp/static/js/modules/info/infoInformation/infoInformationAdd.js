layui.config({
	base: "../../../../static/js/"
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
})
var myEditor = null;
layui.use(['form', 'layer', 'layedit', 'application', 'publicUtil', 'laydate', 'upload'], function() {
	var form = layui.form
	layer = parent.layer,
		application = layui.application,
		publicUtil = layui.publicUtil,
		laypage = layui.laypage,
		upload = layui.upload,
		layedit = layui.layedit,
		$ = layui.jquery;

	// 初始化ckEdit
	$(function() {
		ClassicEditor.create(document.querySelector("#editor"),{
			extraPlugins: [ MyCustomUploadAdapterPlugin ]
		}).then(editor => {
			myEditor = editor;
			initData() ;
		})
		.catch(error => {
			console.error(error);
		});

		selectInfoTypeSelect(null);
		if (!parent.editFormData) {
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'INFO_PUB_STATUS'
			}, "pubStatus", null);
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'INFO_IS_TOPIC'
			}, "isTopnic", null);
		}
		// 
	})



	//首图组
	var HeadImgs = "";

	//表单回显
	function initData() {
		if (parent.editFormData) {
			$("#id").val(publicUtil.htmlDecode(parent.editFormData.id));
			$("#author").val(publicUtil.htmlDecode(parent.editFormData.author));
			$("#title").val(publicUtil.htmlDecode(parent.editFormData.title));
			$("#secondTitle").val(publicUtil.htmlDecode(parent.editFormData.secondTitle));
			$("#sort").val(publicUtil.htmlDecode(parent.editFormData.sort));
			selectInfoTypeSelect(publicUtil.htmlDecode(parent.editFormData.typeId));
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'INFO_PUB_STATUS'
			}, "pubStatus", parent.editFormData.pubStatus);
			publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'INFO_IS_TOPIC'
			}, "isTopnic", parent.editFormData.isTopnic);

			myEditor.setData(publicUtil.htmlDecode(parent.editFormData.content));
			selectedHeadImgs(publicUtil.htmlDecode(parent.editFormData.indexImages));
		}
	}








	//信息类型下拉 (带回填)
	function selectInfoTypeSelect(selectValue) {
		$.ajax({
			url: application.SERVE_URL + "/info/infoType/loadAllListBy", //ajax请求地址
			success: function(res) {
				if (res.code == application.REQUEST_SUCCESS) {
					//抓取相关字段属性
					var data = res.data;
					$("#type_id").empty();
					for (var i = 0; i < data.length; i++) {
						$("#type_id").append('<option  value="' + data[i].id + '">' + data[i].typeName + '</option>'); //往下拉菜单里添加元素
					}
					$('#type_id').val(selectValue);
					form.render('select'); //菜单渲染 把内容加载进去
				} else {
					layer.msg(res.msg);
				}
			}
		});
	}

	form.verify({
		tittle: function(val) {
			if (val == '') {
				return "文章标题不能为空";
			}
		},
		content: function(val) {
			if (val == '') {
				return "文章内容不能为空";
			}
		},
	})

	//相关内容提交
	form.on("submit(addinfoInformation)", function(data) {
		//截取文章内容中的一部分文字放入文章摘要
		//弹出loading
		//判断是否有首图或者首图的数量
		if ($('input:radio[name="indexImgPos"]:checked').val() != "noImg") {
			var Doms = $("#selectHeadImages").find(".selectedImg");
			if (!(Doms.length == 1 || Doms.length == 3)) {
				layer.msg("请选择1张或者3张图片作为背景图片", {
					icon: 5,
					shift: 6
				});
				return false;
			}
			//获取首图
			var Doms = $("#selectHeadImages").find(".selectedImg");
			console.log(Doms.length);
			if (Doms.length == 1) {
				HeadImgs = $(Doms[0]).attr("src")
			} else {
				for (var i = 0; i < Doms.length; i++) {
					if(i==0){
						HeadImgs = $(Doms[i]).attr("src")
					}else{
						HeadImgs = $(Doms[i]).attr("src") + "," + HeadImgs 
					}				
				}
			}
		}
		var index = top.layer.msg('数据提交中，请稍候', {
			icon: 16,
			time: false,
			shade: 0.8
		});
		$.ajax({
			url: application.SERVE_URL + '/info/infoInformation/save', //ajax请求地址
			data: {
				id: $("#id").val(),
				title: $("#title").val(),
				author: $("#author").val(),
				secondTitle: $("#secondTitle").val(),
				content: myEditor.getData(),
				typeId: $("#type_id").val(),
				isTopnic: $("#isTopnic").val(),
				pubStatus: $("#pubStatus").val(),
				sort: $("#sort").val(),
				indexImgPos: $('input:radio[name="indexImgPos"]:checked').val(),
				indexImages: HeadImgs
			},
			success: function(data) {
				if (data.code == application.REQUEST_SUCCESS) {
					top.layer.close(index);
					top.layer.msg(data.msg);
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
		setTimeout(function() {
			top.layer.close(index);
			top.layer.msg("文章添加成功！");
			layer.closeAll("iframe");
			//刷新父页面
			parent.location.reload();
		}, 500);
		return false;
	})

	//选择图片
	$(document).on('click', '.ImgLi', function() {
		if ($(this).find("div").html() != "") {
			$(this).find("div").html("")
			$(this).find("img").removeClass("selectedImg")
		} else {
			$(this).find("div").html("√")
			$(this).find("img").addClass("selectedImg")
		}
	})

	//监听首图位置
	form.on('radio(indexImgPos)', function(data) {
		if (data.value == "noImg") {
			HeadImgs = "";
			$('.imgPosFlag').css("display", "none");
		} else {
			$('.imgPosFlag').css("display", "block");
		}
	})

	$("#close").click(function() {
		layer.closeAll("iframe");
		//刷新父页面
		parent.location.reload();
	})

	//截取content中所有的图片
	function getckImgs(){
		var ckImgUrl = new Array();
		var ckImgs = $(".ck-content").find("img");
		for(var i=0;i<ckImgs.length;i++){
			ckImgUrl.push($(ckImgs[i]).attr("src"))
		}
		return ckImgUrl;
	}
	//解析首图
	function splitIndexImgs(IndexImgs){
		var ckImgUrl = new Array();
		ckImgUrl = IndexImgs.split(",")
		return ckImgUrl;
	}
	
	
	//将所有图片显示在选择首图位置并回显首图
	function selectedHeadImgs(IndexImgs){
		var headImgs = splitIndexImgs(IndexImgs);
		var ckImgUrl = getckImgs();
		for(var i=0;i<ckImgUrl.length;i++){	
			// console.log(j);
			var j=0;
			for(j;j<headImgs.length;j++){
				if(ckImgUrl[i] == headImgs[j]){
					$("#selectHeadImages").append("<li class = 'ImgLi'><div class ='selected'>√</div><img src =" + ckImgUrl[i] + " class= 'headImgs selectedImg'/></li>") 
					break;
				}
			}
			if(j>=headImgs.length){
					$("#selectHeadImages").append("<li class = 'ImgLi'><div class ='selected'></div><img src =" + ckImgUrl[i] + " class= 'headImgs '/></li>")		
			}
		}
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
	function MyCustomUploadAdapterPlugin( editor ) {
		editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
			// Configure the URL to the upload script in your back-end here!
			return new MyUploadAdapter( loader );
		};
	}		
})
