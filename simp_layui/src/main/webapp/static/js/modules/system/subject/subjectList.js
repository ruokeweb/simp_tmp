/**
 * @autor zdl
 * @content 机构列表页面js
 * @returns
 * @Time 2019-02-15
 */
layui.config({
	base : "../../../../static/js/"
}).extend({
	"application" : "application",
	"publicUtil"  : "publicUtil",
	"dateUtils"  : "dateUtils"
})
//表单回填
var formdatas;
layui.use(['element', 'layer', 'form', 'tree','table','laydate','application','publicUtil','dateUtils','upload'], function () {
	var layer = parent.layer === undefined ? layui.layer : top.layer
			,_$ = layui.jquery,
			form = layui.form,
			laydate = layui.laydate,
			laytpl = layui.laytpl,
			application = layui.application,
			dateUtils = layui.dateUtils,
			publicUtil = layui.publicUtil,
			table = layui.table;
			
			application.init();
			//获取权限并加载按钮
			publicUtil.getPerms(application.PERMS_URL,application.HEADER,parent.cur_menu_id,'get','but_per');
			
		//选中标记
		var flag ;
		//节点标记
		var treeCheckNode ;
		//节点标记
		var treeObj;
		//ztree设置
		var setting = {		
				view: {
					selectedMulti: false
				},
				data: {
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "parentId"				
					}
				},
				callback: {
					onClick: onClick
				}
		};
		//初始化树
		initTree();
		//初始化树高度
		_$(function(){
			$(".ztree").height($(window).height()-105);
			$(window).resize(function (){
				$(".ztree").height($(window).height()-105);
				
			});
		});
		function initTree() {
			_$.ajax({
				url: application.SERVE_URL+'/settings/settingSubject/tree',
				type:"post",
				success: function (data) {
					treeObj = $.fn.zTree.init($("#subjectTree"), setting, data.data); //加载数据
					//初始化
										//获取根节点个数,getNodes获取的是根节点的集合
					var nodeList = treeObj.getNodes();
		　　　　　　//展开第一个根节点
					treeObj.expandNode(nodeList[0], true);
					treeObj.setting.callback.onClick(null, treeObj.setting.treeId, nodeList[0]);//调用事件	
				}
			});		
		}	
			

		//加载右侧数据
		function onClick(event, treeId, treeNode, clickFlag) {
			//生产坏境下请求后台
            if(treeNode.level<3){
                treeCheckNode = treeNode.id;
                tableIns = table.render({
                    elem: '#subjectList',
                    //生产坏境下请求后台
                    url : application.SERVE_URL+'/settings/settingSubject/list',
                    where :{parentId : treeNode.id},
                    cellMinWidth : 95,
                    page : true,
                    even : true ,
                    height : "full-230",
                    id : "subjectList",
                    cols : [[
                        {type:'checkbox'},
                        {field: 'name', title: '学科名称'},
                        {field: 'code', title: '学科编码'},
                        {field: 'remark', title: '备注'}
                    ]]
                    ,done: function(res, curr, count){    //res 接口返回的信息,
                        /*publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'ORG_USEABLE'},'useable');*/
                        // publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode", {'typeCode' : 'ORG_TYPE'},'type');
                        // $("[data-field = 'endDate']").children().each(function(){
                        //     if($(this).text().trim() ==''){
                        //         $(this).text('至今');
                        //     }
                        // })
                    }
                });
            }

		}		
				
		//搜索【此功能需要后台配合，所以暂时没有动态效果演示】
		$(".search_btn").click(function(){
            table.reload("subjectList",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    name: $(".searchVal").val()
                }
            })
			/*if($(".searchVal").val() != ''){

			}else{
				layer.msg("请输入搜索的内容",{time: 1000});
			}*/
		});			

		
		//右键点击事件
		table.on('rowRight(subjectList)', function(obj){
			publicUtil.show_menu(obj);
		});
		
		//左键点击事件
		table.on('row(subjectList)', function(obj){
			publicUtil.hiddenMenu(obj);
		});
 
		//新增操作
		_$(document).on('click','.PER_ADD',function(){
				_addOrg();
    });

		
		//编辑操作
		_$(document).on('click','.PER_EDIT',function(){		
			var flag = publicUtil.jurgeSelectRows(table.checkStatus('subjectList').data);
				if(flag){
					//console.log(table.checkStatus('departmentList').data[0]);
					_addOrg(table.checkStatus('subjectList').data[0]);
				}else{
					return false;
				}

		})
		
		//删除
		_$(document).on('click','.PER_DEL',function(){		
				var flag = publicUtil.jurgeSelectRows(table.checkStatus('subjectList').data);
				if(flag){
					layer.confirm('确定删除此学科吗？',{icon:3, title:'提示信息'},function(index){
						_$.ajax({
							url: application.SERVE_URL+"/settings/settingSubject/deleteAllChildrenByParentId", //ajax请求地址
							data:{
								id : table.checkStatus('subjectList').data[0].id
							},							
							success: function (res) {
								if(res.code==application.REQUEST_SUCCESS){
									tableIns.reload();
                                    initTree();
									// location.reload();
									top.layer.close(index);	
									top.layer.msg(res.msg,{time: 1000});							
								}else{
									top.layer.msg(res.msg,{time: 1000});
								}

							}
						})
					});													
				}else{
					return false;
				}
			})			
		 
	  
		//添加机构
		function _addOrg(edit){
			publicUtil.gotoEditPage(application.SERVE_URL +'/settings/settingSubject/get',edit ==undefined?null:edit.id,"学科修改","subjectAdd.html");
		}
		
});