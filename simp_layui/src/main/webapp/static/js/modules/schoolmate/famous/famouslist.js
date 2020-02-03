/**
 * @autor zdl
 * @content 知名校友页面js
 * @returns
 * @Time 2019-03-01
 */
layui.config({
	base: "../../../../static/js/"
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
})

var  companySel;
layui.use(['form', 'layer', 'laydate', 'table', 'laytpl', 'application', 'publicUtil'], function() {
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		_$ = layui.jquery,
		publicUtil = layui.publicUtil,
		application = layui.application,
		laydate = layui.laydate,
		laytpl = layui.laytpl,
		table = layui.table;

	application.init();
	//选中标记
	var flag;


	var tableIns = table.render({
		elem: '#famousList',
		//生产坏境下请求后台
		url: application.SERVE_URL + '/sm/smFamous/list',
		cellMinWidth: 95,
		page: true,
		even: true,
		height: "full-215",
		id: "famousList",

		cols: [
			[{
				type: 'checkbox'
			}, {
				field: 'name',
				title: '知名校友姓名'
			}, {
				field: 'sort',
				title: '排序'
			}, {
				field: 'weight',
				title: '权重'
			},{
                field: 'photo',
                title: '照片',
                templet: function(d) {
                    var res = d.photo;
                    var htm = "";
                    if (res != null && res != "") {
                        htm ="<img class='table-tr-td-img' src='"+ application.SERVE_URL +application.FILEPATH + res +"' style='width:50px;height:60px;'>"
                    }else{
                        htm ='<img class="table-tr-td-img" src="../../../../../static/images/default.jpg" style="width:50px;height:60px;">'
                    }
                    return htm;
                }
            },{
				field: 'remark',
				title: '备注'
			}]
		],
		done: function(res, curr, count) { //res 接口返回的信息,
			/*publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'COMPANY_TPYE'
			}, 'type');*/
		}
	});

    //右键点击事件
    table.on('rowRight(famousList)', function(obj) {
        publicUtil.show_menu(obj);
    });

    //左键点击事件
    table.on('row(famousList)', function(obj) {
        publicUtil.hiddenMenu(obj);
    });

	//新增操作
	_$(document).on('click', '.PER_ADD', function() {
		addAssociation(table.checkStatus('enteciationList').data[0], "add");
	});


	//编辑操作
	_$(document).on('click', '.PER_EDIT', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('famousList').data);
		if (flag) {
			addAssociation(table.checkStatus('famousList').data[0], "edit");
		} else {
			return false;
		}
	})


	//删除
	_$(document).on('click', '.PER_DEL', function() {
		var flag = publicUtil.jurgeSelectRows(table.checkStatus('famousList').data);
		if (flag) {
			layer.confirm('确定删除此知名校友吗？', {
				icon: 3,
				title: '提示信息'
			}, function(index) {
				_$.ajax({
					url: application.SERVE_URL + "/sm/smFamous/delete", //ajax请求地址
					data: {
						id: table.checkStatus('famousList').data[0].id
					},
					success: function(data) {
						if (data.code == application.REQUEST_SUCCESS) {
							tableIns.reload();
							layer.close(index);
						}
					}
				});
			});
		} else {
			return false;
		}
	})

	//获取权限并加载按钮
	publicUtil.getPerms(application.PERMS_URL, application.HEADER, parent.cur_menu_id, 'get', 'but_per');
	//搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	_$(".search_btn").on("click", function() {
		table.reload("famousList", {
			page: {
				curr: 1 //重新从第 1 页开始
			},
			where: {
				name: $(".searchVal").val()
			}
		})
	});

	//添加
	function addAssociation(edit, action) {
		var restUrl = application.SERVE_URL + '/sm/smFamous/get';
		var id = "edit" == action ? (edit.id ? edit.id : null) : null;
		publicUtil.gotoEditPage(restUrl, id, "添加知名校友", "famousadd.html")
	}


    _$(document).on('click', '.table-tr-td-img', function() {
            var _this = $(this);//将当前的pimg元素作为_this传入函数
            imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
    });

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

        _$(document).on('click', '#outerdiv', function() {
            $(this).fadeOut("slow");
        });
    }

})