/**
 * @autor zdl
 * @content 知名校友页面js
 * @returns
 * @Time 2019-03-01
 */
layui.config({
	base: '../../../../static/js/' // 此处路径请自行处理, 可以使用绝对路径
}).extend({
	"application": "application",
	"publicUtil": "publicUtil",
    "croppers":"croppers"
});

var userId=parent.editFormData.userId;
layui.use(['jquery', 'form', 'layer', 'laydate', 'publicUtil', 'croppers','upload', 'application'], function() {
	var form = layui.form,
		application = layui.application,
		publicUtil = layui.publicUtil,
		layer = layui.layer,
		laydate = layui.laydate,
		username = parent.parent.username,
		upload = layui.upload,
        croppers = layui.croppers,
		$ = layui.jquery;
    var imageId="";
	// 初始化ckEdit
    var contentEditor;
  
    console.log(userId);
    // 备注
    ClassicEditor.create(document.querySelector("#information"),{
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
	
	
	
	
	
    function formEdit(FormDatas){
        if(FormDatas != ''){
            var data = FormDatas;
            $(".id").val(publicUtil.htmlDecode(data.id));
            $(".userId").val(publicUtil.htmlDecode(data.userId));
            $(".username").val(publicUtil.htmlDecode(data.name));
            if(data.photo != null && data.photo != ""){
                document.getElementById("real_photo").src= application.SERVE_URL + application.FILEPATH + data.photo;
                $('#photoPath').val(data.photo);
                imageId=application.SERVE_URL + application.FILEPATH + data.photo;
            }
            $(".information").val(publicUtil.htmlDecode(data.information));
            $(".sort").val(publicUtil.htmlDecode(data.sort));
            $(".weight").val(publicUtil.htmlDecode(data.weight));
            $("#remark").val( publicUtil.htmlDecode(data.remark));
        }else{
            return false;
        }
    }
	
		//验证text字段长度符文本编辑器
	form.verify({
	    ckeditContent : function(value){
	        if(value.length >= 65535){
	            return "已超过最大长度，请重新编辑";
	        }
	    },
	}) 
    /**
     * 表单回填
     */
    formEdit(parent.editFormData);
    //创建一个头像上传组件
    croppers.render({
        elem: '#selectphoto'
        ,saveW:300     //保存宽度
        ,saveH:400
        ,mark: 3 / 4
        ,resizable: false
        ,viewMode:1
        ,imageId:$("#photoPath")
        ,photo:$("#real_photo")
        ,data:imageId
        ,area:'900px'  //弹窗宽度
        ,url: application.SERVE_URL + '/file/upload/'  //图片上传接口返回和（layui 的upload 模块）返回的JOSN一样
        ,done: function(url){ //上传完毕回调
           /* $("#inputimgurl").val(url);
            $("#srcimgurl").attr('src',url);
            $('#bigimg').attr('src', result);*/

        }
    });
    form.on("submit(add)", function(data) {
		if(contentEditor.getData().length  >= 65535 ){
			layer.msg("生平简介超过最大长度，请重新编辑", {icon: 5, shift: 6});
			return false;
		}
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
		$.ajax({
			url: application.SERVE_URL + '/sm/smFamous/save', // ajax请求地
			data: {
                "id" : $(".id").val() ==null|| $(".id").val() =="" ? "" : $(".id").val(),
                "userId" : $(".userId").val(),
                "sort" : $("#sort").val(),
                "weight" : $("#weight").val(),
                "photo": $("#photoPath").val(),
                "information": contentEditor.getData(),
                "remark" : $("#remark").val()
			},
			success: function(data) {
				if (data.code == application.REQUEST_SUCCESS) {
					top.layer.close(index);
					top.layer.msg(data.msg);
					layer.closeAll("iframe");
					// 刷新父页面
					parent.location.reload();
				} else {
					top.layer.msg(data.msg);
				}
			}
		});
		return false;
	})

    function selectUser(){
        var index = layui.layer.open({
            type: 2,
            title: '校友选择',
            shadeClose: true,
            shade: 0.8,
            area: ['90%', '90%'],
            content: 'userselect.html',
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

    $(".username").click(function(){
        selectUser();
    })
    $("#close").click(function(){
        layer.closeAll("iframe");
        //刷新父页面
        parent.location.reload();
    })
    // 上传适配器，格式官网上有，以一种Promise 的方式。Promise好像是有阻塞的意思在里面。
	class UploadAdapter {
        constructor(loader) {
            this.loader = loader;
        }
        upload() {
            return new Promise((resolve, reject) => {
                const data = new FormData();
                data.append('file', this.loader.file);
                data.append('allowSize', 5); // 允许图片上传的大小/兆
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

            }
			);
        }
        abort() {}
    }
	
    function imgShow(outerdiv, innerdiv, bigimg, _this){
        var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
        $(bigimg).attr("src", src);//设置#bigimg元素的src属性

        /*获取当前点击图片的真实大小，并显示弹出层及大图*/
        $("<img/>").attr("src", src).load(function(){
            var windowW = $(window).width();//获取当前窗口宽度
            var windowH = $(window).height();//获取当前窗口高度
            var realWidth = this.width;//获取图片真实宽度
            var realHeight = this.height;//获取图片真实高度
            var imgWidth, imgHeight;
            var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放

            if(realHeight>windowH*scale) {//判断图片高度
                imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放
                imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度
                if(imgWidth>windowW*scale) {//如宽度扔大于窗口宽度
                    imgWidth = windowW*scale;//再对宽度进行缩放
                }
            } else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度
                imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放
                imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度
            } else {//如果图片真实高度和宽度都符合要求，高宽不变
                imgWidth = realWidth;
                imgHeight = realHeight;
            }
            $(bigimg).css("width",imgWidth);//以最终的宽度对图片缩放

            var w = (windowW-imgWidth)/2;//计算图片与窗口左边距
            var h = (windowH-imgHeight)/2;//计算图片与窗口上边距
            $(innerdiv).css({"top":h, "left":w});//设置#innerdiv的top和left属性
            $(outerdiv).fadeIn("slow");//淡入显示#outerdiv及.pimg
        });


    }
})
