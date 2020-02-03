/**
 * @autor zdl
 * @content 机构增加页面js
 * @returns
 * @Time 2019-02-15
 */
 layui.config({
 	base : "../../../../static/js/"
 }).extend({
 	"validparam"  : "validparam"
 }) 
layui.use(['form','layer','upload','laydate','publicUtil','application'],function(){
    var form = layui.form
        layer =layui.layer,
		laydate = layui.laydate,
		application = layui.application,
		publicUtil  = layui.publicUtil,
		upload = layui.upload;
        $ = layui.jquery;
		
		function formEdit(FormDatas){
			if(FormDatas != ''){
				var data = FormDatas;	 
				 $(".id").val(publicUtil.htmlDecode(data.id));
				 $(".parentId").val(publicUtil.htmlDecode(data.parentId));
				 $(".name").val(publicUtil.htmlDecode(data.name));
				 $(".code").val(publicUtil.htmlDecode(data.code));													
				 $(".master").val(publicUtil.htmlDecode(data.master));  
				 $(".mobile").val(publicUtil.htmlDecode(data.mobile));  
				 //$(".mobile").val(publicUtil.htmlDecode(data.mobile));
				 $("#startDate").val(publicUtil.htmlDecode(data.startDate));
				 $("#endDate").val(publicUtil.htmlDecode(data.endDate));
				 $(".remark").val(publicUtil.htmlDecode(data.remark));
				 $(".sort").val(publicUtil.htmlDecode(data.sort));
				 $(".parentName").val(publicUtil.htmlDecode(data.parentStDt.name));
                 $(".remark").val( publicUtil.htmlDecode(data.remark));
				publicUtil.selectBaseAndSetVal(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'ORG_TYPE'} ,"type",data.type);		
				//publicUtil.selectBaseAndSetVal(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'ORG_USEABLE'} ,"useable",data.useable);
			}else{
				publicUtil.selectBase(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'ORG_TYPE'} ,"type");		
				//publicUtil.selectBase(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'ORG_USEABLE'} ,"useable");
				return false;
			}
		}													
	
	
	/**
	* 表单回填
	*/
	formEdit(parent.editFormData);
	
	var startDate;
	var endDate ;
	//执行一个laydate实例
	laydate.render({
		elem: '#startDate'
		,theme: 'molv',
		trigger: 'click'
		 //指定元素
		,done: function(value, date, endDate){
			//得到日期生成的值，如：2017-08-18
            startDate =value;
		}
	});

	//执行一个laydate实例
	laydate.render({
		elem: '#endDate' //指定元素,,
		,theme: 'molv',
		trigger: 'click'
		,done: function(value, date, endDate){
			//得到日期生成的值，如：2017-08-18
            endDate =value;
		}
	});		
		
    form.on("submit(addOrg)",function(data){
		
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //参数封装
        var data={
    		"id" : $(".id").val() ==null|| $(".id").val() =="" ? "" : $(".id").val(),
			"parentId" : $(".parentId").val(),
			"name" : $(".name").val(),
			"type" : $("#type").val(),
			"code" : $(".code").val(),
			"master" : $(".master").val(),
			"mobile" : $(".mobile").val(),
			"sort" : $(".sort").val(),
			"startDate" :  $("#startDate").val(),
			"endDate" :  $("#endDate").val(),
			"remark" : $(".remark").val()

        }
        
        $.ajax({
			url: application.SERVE_URL+"/settings/settingDepartment/save", //ajax请求地址
			data: data,
			success: function (res) {
				if(res.code==application.REQUEST_SUCCESS){
				 	top.layer.close(index);
		            top.layer.msg(res.msg,{time:1000});	
		            layer.closeAll("iframe");
		            //刷新父页面
		            parent.location.reload();
				}else{
					layer.msg(res.msg,{time:1000});
				}
			}
		}); 
        return false;
    })
    
	function selectOrg(){
		var index = layui.layer.open({
			type: 2,
			title: '机构选择',
			shadeClose: true,
			shade: 0.8,
			area: ['280px', '65%'],
			// content: '../views/module/system/menu/menuselect.html',
			content: 'departmentselect.html',
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
	
	$(".parentName").click(function(){
		selectOrg();
	})	
	$("#close").click(function(){
		layer.closeAll("iframe");
		//刷新父页面
		parent.location.reload();	
	})	
})