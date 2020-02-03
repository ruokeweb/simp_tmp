layui.config({
	base: "../../../../static/js/"
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
})
var settingTableIns;
var actObj;
layui.use(['form', 'layer', 'laydate', 'table', 'laytpl', 'application', 'publicUtil'], function() {
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery,
		publicUtil = layui.publicUtil,
		application = layui.application,
		laydate = layui.laydate,
		laytpl = layui.laytpl,
		table = layui.table;

	application.init();


	  
	var tableIns = table.render({
		elem: '#actList',
		url: application.SERVE_URL + '/act/actActivity/list',
		even: true,
		cellMinWidth: 95,
		page: true,
		height: "full-50",
		limit:15,
   	 	limits:[15,30,50,100],
		headers: {
			'Authorization': application.HEADER
		},
		id: "actList",
		cols: [
			[{
				field: 'name',
				title: '活动名称'
			}, {
				field: 'status',
				title: '活动状态'
			}]
		],
		done: function(res, curr, count) { //res 接口返回的信息,
			publicUtil.tableSetStr(application.SERVE_URL + "/sys/sysdict/getByTypeCode", {
				'typeCode': 'ACT_STATUS'
			}, 'status');
			console.log(res);
			if(res.list.length > 0){
				actObj = res.list[0];
				rendList(actObj.id);
			}else{
				rendList(0);
			}
		}
	});
	
	//行事件
	table.on('row(actList)', function(obj) {
		$(".layui-select-tr").removeClass("layui-select-tr");
		obj.tr.addClass("layui-select-tr");
		rendList(obj.data.id);
	});
	
	function rendList(id) {
		table.render({
            elem: '#contentList',
            loading: true,
            url: application.SERVE_URL + '/act/actContent/list',
            where: {actId :id},
            cols: [[{
                field: 'userTrueName',
                title: '姓名'
            },{
            	field: 'userSex',
				title: '性别',
			}, {
                field: 'userMobile',
                title: '手机号'
            }]],
            done:function(res,curr,count) {
                        	//拼接表头
				publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'SEX'},'userSex');
               var listCol = [];
				for (k=0;k<res.list.length;k++){
					var for_listCol = [];
					var dataCol = res.list[k];				
					var eachSize=0
					if(dataCol!=undefined&&dataCol!=null&&dataCol.code!=undefined&&dataCol.code!=null){
						eachSize=dataCol.code.split(',').length;
					}

					//if(eachSize>0){
						for_listCol = [
							{field:'userTrueName', title:'姓名', align:'center'}
							,{field:'userSex', title:'性别', align:'center'}
							,{field:'userMobile', title:'手机号', align:'center'}
						];
					//}
					for(i=0;i<eachSize;i++){
						if(null != dataCol&&dataCol.code!=undefined&&dataCol.code!=null&&dataCol.name!=undefined&&dataCol.name!=null)
							for_listCol[i+3]={field:dataCol.code.split(',')[i], title:dataCol.name.split(',')[i], align:'center'};
					}
					if(for_listCol.length>listCol.length){
						listCol = for_listCol;
					}
				}


            	//拼接数据
            	var dataValAll=[];
            	for(j=0;j<res.list.length;j++){
            		
            		var dataVal={};
            		//编码数组
					var codes=[];
					if(res.list[j]!=undefined&&res.list[j]!=null&&res.list[j].code!=undefined&&res.list[j].code!=null){
						codes=res.list[j].code.split(",");
					}

            		//值数组
					var vals=[];
					if(res.list[j]!=undefined&&res.list[j]!=null&&res.list[j].val!=undefined&&res.list[j].val!=null){
						vals=res.list[j].val.split(";");
					}
            		//if(codes.length>0){
                        dataVal['userTrueName']=res.list[j].userTrueName;
                        dataVal['userSex']=res.list[j].userSex;
                        dataVal['userMobile']=res.list[j].userMobile;
					//}

            		//循环拼接json键值对
            		for(k=0;k<codes.length;k++){
            			dataVal[codes[k]]=vals[k];
            		}
            		dataValAll[j]=dataVal;             		
            	}

            	//构建表格
                table.init('contentList',{
                	 cols:[listCol],
                	 data:dataValAll,
                	 cellMinWidth: 85,
                	 height: "full-50",
                	 page: true,
                	 limit:15,
                	 limits:[15,30,50,100]
                });
                publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'SEX'},'userSex');

            }
        });
	
	}



	

	
})
