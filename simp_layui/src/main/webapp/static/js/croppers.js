/*!
 * Cropper v3.0.0
 */

layui.config({
    base: '/static/js/cropperjs-master/' //layui自定义layui组件目录
}).define(['jquery','layer','cropper','upload'],function (exports) {
    var $ = layui.jquery,
        upload = layui.upload,
        layer = layui.layer;
    var html = "<link rel=\"stylesheet\" href=\"/static/css/cropperjs-master/cropper.css\">\n" +
        "<div class=\"layui-fluid showImgEdit\" style=\"display: none\">\n" +
        "    <div class=\"layui-form-item\">\n" +
        "        <div class=\"layui-input-inline layui-btn-container\" style=\"width: auto;\">\n" +
        /*"            <label for=\"cropper_avatarImgUpload\" class=\"layui-btn layui-btn-primary\">\n" +*/
        "               <button type=\"button\" class=\"layui-btn\" id=\"selectImage\"> 选择图片</button>\n" +
      /*  "            </label>\n" +*/
      /*  "            <input class=\"layui-upload-file\" id=\"cropper_avatarImgUpload\" type=\"file\" value=\"选择图片\" name=\"file\">\n" +*/

        "        </div>\n" +
        "    </div>\n" +
        "    <div class=\"layui-row layui-col-space15\">\n" +
        "        <div class=\"layui-col-xs9\">\n" +
        "            <div class=\"readyimg\" style=\"height:450px;background-color: rgb(247, 247, 247);\">\n" +
        "                <img src=\"\" >\n" +
        "            </div>\n" +
        "        </div>\n" +
        "        <div class=\"layui-col-xs3\">\n" +
        "            <div class=\"img-preview\" style=\"width:200px;height:200px;overflow:hidden\">\n" +
        "            </div>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "    <div class=\"layui-row layui-col-space15\">\n" +
        "        <div class=\"layui-col-xs9\">\n" +
        "            <div class=\"layui-row\">\n" +
        "                <div class=\"layui-col-xs6\">\n" +
        "                    <button type=\"button\" class=\"layui-btn layui-icon layui-icon-left\" cropper-event=\"rotate\" data-option=\"-15\" title=\"Rotate -90 degrees\"> 向左旋转</button>\n" +
        "                    <button type=\"button\" class=\"layui-btn layui-icon layui-icon-right\" cropper-event=\"rotate\" data-option=\"15\" title=\"Rotate 90 degrees\"> 向右旋转</button>\n" +
        "                </div>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "        <div class=\"layui-col-xs3\">\n" +
            "            <button class=\"layui-btn layui-btn-fluid\" cropper-event=\"confirmSave\" type=\"button\"> 保存修改</button>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "\n" +
        "</div>";
    var obj = {
        render: function(e){
            $('body').append(html);
            var self = this,
                elem = e.elem,
                saveW = e.saveW,
                saveH = e.saveH,
                mark = e.mark,
                area = e.area,
                url = e.url,
                data= e.data,
                done = e.done,
                imageId = e.imageId,
                photo = e.photo;

            var content = $('.showImgEdit')
                ,image = $(".showImgEdit .readyimg img")
                ,preview = '.showImgEdit .img-preview'
               /* ,file = $(".showImgEdit input[name='file']")*/
                , options = {aspectRatio: mark,preview: preview,viewMode:1};
            $(elem).on('click',function () {
                layer.open({
                    type: 1
                    , content: content
                    , area: area
                    , success: function () {
                        if(data!=""){
                            image.attr("src",data);
                        }
                        image.cropper(options);
                    }
                    , cancel: function (index) {
                        layer.close(index);
                        image.cropper('destroy');
                    }
                });
            });
            $(".layui-btn").on('click',function () {
                var event = $(this).attr("cropper-event");
                //监听确认保存图像
                if(event === 'confirmSave'){
                    image.cropper("getCroppedCanvas",{
                        width: saveW,
                        height: saveH
                    }).toBlob(function(blob){
                        var formData=new FormData();
                        formData.append('file',blob,"cut_"+new Date().getTime()+'.jpg');
                        var $imgData=image.cropper('getCroppedCanvas');
                        var dataurl = $imgData.toDataURL("image/jpeg", 0.8);
                        $.ajax({
                            method:"post",
                            url: url, //用于文件上传的服务器端请求地址
                            data: formData,
                            processData: false,
                            contentType: false,
                            success:function(result){
                                if(result.code> 0){
                                    $(photo).attr("src",dataurl); 
                                    $(imageId).val(result.data);
                                    layer.msg(result.msg,{icon: 1});
                                    layer.closeAll('page');
                                    return done(result.data.src);
                                }else if(result.code == -1){
                                    layer.alert(result.msg,{icon: 2});
                                }

                            }
                        });
                    },"image/jpeg",0.8);
                    //监听旋转
                }else if(event === 'rotate'){
                    var option = $(this).attr('data-option');
                    image.cropper('rotate', option);
                    //重设图片
                }else if(event === 'reset'){
                    image.cropper('reset');
                }
                //文件选择
                /*file.change(function () {
                    var r= new FileReader();
                    var f=this.files[0];
                    r.readAsDataURL(f);
                    r.onload=function (e) {
                        image.cropper('destroy').attr('src', this.result).cropper(options);
                    };
                });*/
            });
            var uploadInst = upload.render({
                elem: '#selectImage',
                auto: false,
                url: '',
                accept: 'images',
                exts : 'jpg|png|gif|bmp|jpeg',
                size : 1024,
                choose: function(obj) {
                    //预读本地文件示例，不支持ie8
                    obj.preview(function(index, file, result) {   
                        image.cropper('destroy').attr('src', result).cropper(options);
                    })

                },
                done: function(res, index, upload) {
                },
                error: function() {
                }
            });
        }

    };
    exports('croppers', obj);
});