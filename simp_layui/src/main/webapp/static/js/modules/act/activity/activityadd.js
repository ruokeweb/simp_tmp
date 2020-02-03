/**
 * @autor syp
 * @content 校友会你页面js
 * @returns
 * @Time 2018-08-02
 */
layui.config({
    base: '../../../../static/js/' // 此处路径请自行处理, 可以使用绝对路径
}).extend({
    /* formSelects: 'formSelects-v4', */
    "application": "application",
    "croppers": "croppers"
});


layui.use(['jquery', 'form', 'layer', 'laydate', 'publicUtil', 'croppers', 'upload', 'application'], function() {
    var form = layui.form,
        application = layui.application,
        publicUtil = layui.publicUtil,
        layer = layui.layer,
        laydate = layui.laydate,
        username = parent.parent.username,
        upload = layui.upload,
        croppers = layui.croppers,
        $ = layui.jquery;
    var imageId = "";
    form.verify({
        username: function(value, item) { //value：表单的值、item：表单的DOM对象
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '用户名不能有特殊字符';
            }
            if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                return '用户名首尾不能出现下划线\'_\'';
            }
            if (/^\d+\d+\d$/.test(value)) {
                return '用户名不能全为数字';
            }
        },
        personNum: function(value) {
            var message = "";
            //判断是否是编辑
            if (/^(\+)?\d+($|\\d+$)/.test(value)) {
                return;
            } else {
                message = "只能输入整数"
                return message;
            }
        }
    });
    // 初始化ckEdit
    var contentEditor;
    // 备注
    ClassicEditor.create(document.querySelector("#content"), {
            extraPlugins: [MyCustomUploadAdapterPlugin]
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
        constructor(loader) {
            this.loader = loader;
        }
        upload() {
            return this.loader.file
                .then(file => new Promise((resolve, reject) => {
                    this._initRequest();
                    this._initListeners(resolve, reject, file);
                    this._sendRequest(file);
                }));
        }
        abort() {
            if (this.xhr) {
                this.xhr.abort();
            }
        }
        _initRequest() {
            const xhr = this.xhr = new XMLHttpRequest();
            xhr.open('POST', application.SERVE_URL + "/file/upload/", true);
            xhr.responseType = 'json';
        }
        _initListeners(resolve, reject, file) {
            const xhr = this.xhr;
            const loader = this.loader;
            const genericErrorText = `Couldn't upload file: ${ file.name }.`;

            xhr.addEventListener('error', () => reject(genericErrorText));
            xhr.addEventListener('abort', () => reject());
            xhr.addEventListener('load', () => {
                const response = xhr.response;
                resolve({
                    default: application.SERVE_URL + application.FILEPATH + response.data
                });
            });
            if (xhr.upload) {
                xhr.upload.addEventListener('progress', evt => {
                    if (evt.lengthComputable) {
                        loader.uploadTotal = evt.total;
                        loader.uploaded = evt.loaded;
                    }
                });
            }
        }

        _sendRequest(file) {
            // Prepare the form data.
            const data = new FormData();

            data.append('file', file);
            this.xhr.send(data);
        }
    }

    function MyCustomUploadAdapterPlugin(editor) {
        editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
            // Configure the URL to the upload script in your back-end here!
            return new MyUploadAdapter(loader);
        };
    }

    // 编辑初始化
    initAssociationData();

    // 日期选择器4个
    laydate.render({
        elem: '#startDate',
        theme: 'molv',
        trigger: 'click'
    });

    laydate.render({
        elem: '#endDate',
        theme: 'molv',
        trigger: 'click'
    });
    laydate.render({
        elem: '#signStartDate',
        theme: 'molv',
        trigger: 'click'
    });

    laydate.render({
        elem: '#signEndDate',
        theme: 'molv',
        trigger: 'click'
    });

    // 国家省市县联动两个
    form.on('select(country)', function(data) {
        selectAreaAndSetVal(data.value, "province");
        selectAreaAndSetVal($("#province").val(), "city");
    })

    form.on('select(province)', function(data) {
        selectAreaAndSetVal(data.value, "city");
    })

    //自定义验证规则
    form.verify({
        $start_date: "",
        start_date: function(value) {
            $start_date = value;
        },
        end_date: function(value) {
            var message = "";
            if (!((value >= $start_date))) {
                message = "结束时间不能在开始时间之前";
            }
            return message;
        }
    });

    //自定义验证规则
    form.verify({
        $act_end_date: "",
        act_end_date: function(value) {
            $act_end_date = value;
        },
        sing_start_date: function(value) {
            var message = "";
            if (((value >= $act_end_date))) {
                console.log(value);
                message = "报名开始时间不能在活动结束时间之后";
            }
            return message;
        },
        sing_end_date: function(value) {
            var message = "";
            if (((value >= $act_end_date))) {
                message = "报名结束时间不能在活动结束时间之后";
            }
            return message;
        }
    });

    // 页面打开时，或者基本信息时，点击填充数据
    function initAssociationData() {
        if (parent.editFormData) {
            $("#id").val(publicUtil.htmlDecode(parent.editFormData.id));
            $("#parentId").val(publicUtil.htmlDecode(parent.editFormData.assoId));
            $("#parentName").val(publicUtil.htmlDecode(parent.editFormData.assoName));
            $("#name").val(publicUtil.htmlDecode(parent.editFormData.name));
            $('#limitNo').val(publicUtil.htmlDecode(parent.editFormData.limitNo));
            //$("#status").val(publicUtil.htmlDecode(parent.editFormData.status));
            $("#startDate").val(publicUtil.htmlDecode(parent.editFormData.startDate));
            $("#endDate").val(publicUtil.htmlDecode(parent.editFormData.endDate));
            $("#signStartDate").val(publicUtil.htmlDecode(parent.editFormData.signStartDate));
            $("#signEndDate").val(publicUtil.htmlDecode(parent.editFormData.signEndDate));
            $("#area").val(publicUtil.htmlDecode(parent.editFormData.area));
            // 图片赋值
            if (parent.editFormData.image != null && parent.editFormData.image != "") {
                $("#photo").attr("src", application.SERVE_URL + application.FILEPATH + parent.editFormData.image);
                $("#image").val(parent.editFormData.image);
                imageId = application.SERVE_URL + application.FILEPATH + parent.editFormData.image;
            }

        }
        // 状态
        publicUtil.selectBaseAndSetVal(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
            'typeCode': 'ACT_STATUS'
        }, "status", parent.editFormData ? parent.editFormData.status : null);

    }


    croppers.render({
        elem: '#selectphoto',
        saveW: 1200, //保存宽度   
        saveH: 600,
        mark: 2 / 1,
        resizable: false,
        viewMode: 1,
        imageId: $("#image"),
        photo: $("#photo"),
        data: imageId,
        area: '900px' //弹窗宽度
            ,
        url: application.SERVE_URL + '/file/upload/' //图片上传接口返回和（layui 的upload 模块）返回的JOSN一样
            ,
        done: function(url) { //上传完毕回调
            /* $("#inputimgurl").val(url);
             $("#srcimgurl").attr('src',url);
             $('#bigimg').attr('src', result);*/

        }
    });
    /*var uploadInst = upload.render({
    		elem: '#selectphoto',
    		url: application.SERVE_URL + '/file/upload/',
    		choose: function (obj) {
    			// 预读本地文件示例，不支持ie8
    			obj.preview(function (index, file, result) {
    				$('#photo').attr('src', result); // 图片链接（base64）
    			});
    		},
    		bindAction: '#add',
    		done: function (res, index, upload) {
    			$('#image').val(res.data);
    			// 如果上传失败
    			if (res.code > 0) {
    				return layer.msg('上传成功');
    			}
    			// 上传成功
    		},
    		error: function () {
    			// 演示失败状态，并实现重传
    			var photoPath = $('#image');
    			photoPath.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
    			photoPath.find('.demo-reload').on('click', function () {
    				uploadInst.upload();
    			});
    		}
    	});*/

    // submit(addUser) 绑定提交按钮（基本信息）
    form.on("submit(add)", function(data) {
        // 弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {
            icon: 16,
            time: false,
            shade: 0.8
        });

        $.ajax({
            url: application.SERVE_URL + '/act/actActivity/save', // ajax请求地
            data: {
                id: $("#id").val() == null || $("#id").val() == "" ? null : $("#id").val(),

                assoId: $("#parentId").val(),
                name: $("#name").val(),
                limitNo: $('#limitNo').val(),
                //status: $("#status").val(),
                startDate: $("#startDate").val(),
                endDate: $("#endDate").val(),
                signStartDate: $("#signStartDate").val(),
                signEndDate: $("#signEndDate").val(),
                area: $("#area").val(),
                image: $("#image").val(),
                content: contentEditor.getData()
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



    function selectSmAsso() {
        var index = layui.layer.open({
            type: 2,
            title: '校友会选择',
            shadeClose: true,
            shade: 0.8,
            area: ['280px', '65%'],
            // content: '../views/module/system/menu/menuselect.html',
            content: '../../association/asso/associationselect.html',
            success: function(layero, index) {
                //
                setTimeout(function() {
                    layui.layer.tips('点击此处返回', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            },
        })
    }

    $(".parentName").click(function() {
        selectSmAsso();
    })

})
