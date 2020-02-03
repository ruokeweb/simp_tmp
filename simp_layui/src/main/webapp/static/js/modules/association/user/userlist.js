/**
 * @autor lzq
 * @content 校友会成员信息管理
 * @returns
 * @Time 2018-08-02
 */
layui.config({
	base : "../../../../static/js/"
}).extend({
	"application" : "application",
	"publicUtil" : "publicUtil"
})
// 节点标记
var treeCheckNode;
layui
		.use(
				[ 'form', 'layer', 'laydate', 'table', 'laytpl', 'application',
						'publicUtil' ],
				function() {
					var form = layui.form, layer = parent.layer === undefined ? layui.layer
							: top.layer, _$ = layui.jquery, publicUtil = layui.publicUtil, application = layui.application, laydate = layui.laydate, laytpl = layui.laytpl, table = layui.table;

					application.init();
					// 选中标记
					var flag;
					var userObj;
					// 节点标记
					var treeObj;

					var tableIns;

					// ztree设置
					var setting = {
						view : {
							selectedMulti : false
						},
						data : {
							simpleData : {
								enable : true,
								idKey : "id",
								pIdKey : "parentId"
							}
						},
						callback : {
							onClick : onClick
						}
					};
					// 初始化树
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
							url : application.SERVE_URL
									+ '/as/asAssociation/findAsByUser',
							method : 'post',
							success : function(data) {
								treeObj = $.fn.zTree.init($("#assoTree"),
										setting, covert(data.data)); // 加载数据
								// 初始化
								// 获取根节点个数,getNodes获取的是根节点的集合
								var nodeList = treeObj.getNodes();
								// 展开第一个根节点
								treeObj.expandNode(nodeList[0], true);
								treeObj.setting.callback.onClick(null,
										treeObj.setting.treeId, nodeList[0]);// 调用事件
							}
						});
					}

					function covert(data) {
						for (var i = 0; i < data.length; i++) {
							data[i].name = publicUtil.htmlDecode(data[i].name)
						}
						return data;
					}

					// 右键点击事件
					table.on('rowRight(userList)', function(obj) {
						publicUtil.show_menu(obj);
					});

					// 左键点击事件
					table.on('row(userList)', function(obj) {
						schoolmateSel = obj.data;
						publicUtil.hiddenMenu(obj);
					});

					// table.on('row(userList)', function (obj) {
					// $(".layui-select-tr").removeClass("layui-select-tr");
					// obj.tr.addClass("layui-select-tr");
					// // console.log(obj.data);
					// schoolmateSel = obj.data;
					// // rendStarInfo(obj.data.id);
					// });

					// 加载右侧数据 userList
					function onClick(event, treeId, treeNode, clickFlag) {
						// 生产坏境下请求后台
						var url = "";
						// if("root" ==treeNode.id)
						// {
						// url =
						// application.SERVE_URL+'/as/asAssociationUser/list'
						// }else{
						// url =
						// application.SERVE_URL+'/as/asAssociationUser/list?associationId='+treeNode.id
						// }
						if ("root" == treeNode.id) {
							url = application.SERVE_URL
									+ '/as/asAssociation/loadSysAs'
						} else {
							url = application.SERVE_URL
									+ '/as/asAssociation/loadSysAs?id='
									+ treeNode.id
						}
						treeCheckNode = treeNode.id;
						tableIns = table.render({
							elem : '#userList',
							// 生产坏境下请求后台
							url : url,
							cellMinWidth : 95,
							page : true,
							even : true,
							height : "full-230",
							id : "userList",
							cols : [ [ {
								type : 'checkbox'
							}, {
								field : 'name',
								title : '校友',
							}, {
								field : 'birthday',
								title : '生日'
							}, {
								field : 'sex',
								title : '性别'
							} ] ],
							done : function(res, curr, count) { // res 接口返回的信息,
								// publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode",
								// {'typeCode' : 'ASPOST_TYPE'},'roleType');
								// publicUtil.tableSetStr(application.SERVE_URL+"/sys/sysdict/getByTypeCode",
								// {'typeCode' : 'BOOLEAN_TYPE'},'isPosting');
								publicUtil.tableSetStr(application.SERVE_URL
										+ "/sys/sysdict/getByTypeCode", {
									'typeCode' : 'SEX'
								}, 'sex');
							}
						});
					}

					// 新增操作
					_$(document).on('click', '.PER_ADD', function() {
						addCard();
					});

					// 编辑操作
					_$(document)
							.on(
									'click',
									'.PER_EDIT',
									function() {
										var flag = publicUtil
												.jurgeSelectRows(table
														.checkStatus('userList').data);
										if (flag) {
											addCard(table
													.checkStatus('userList').data[0]);
										} else {
											return false;
										}
									})

					// 删除
					_$(document)
							.on(
									'click',
									'.PER_DEL',
									function() {
										var flag = publicUtil
												.jurgeSelectRows(table
														.checkStatus('userList').data);
										var user_id = table
												.checkStatus('userList').data[0].userId;
										if (flag) {
											layer
													.confirm(
															'是否将校友移出校友会？',
															{
																icon : 3,
																title : '提示信息'
															},
															function(index) {
																_$
																		.ajax({
																			url : application.SERVE_URL
																					+ "/as/asAssociation/deleteSysAs", // ajax请求地址
																			data : {
																				asId : treeCheckNode,
																				userId : table
																						.checkStatus('userList').data[0].userId
																			},
																			success : function(
																					data) {
																				if (data.code == application.REQUEST_SUCCESS) {
																					tableIns
																							.reload();
																					layer
																							.close(index);
																				}
																			}
																		});
															});
										} else {
											return false;
										}
									})

					// 获取权限并加载按钮
					publicUtil.getPerms(application.PERMS_URL,
							application.HEADER, parent.cur_menu_id, 'get',
							'but_per');
					// 搜索【此功能需要后台配合，所以暂时没有动态效果演示】
					_$(".search_btn").on("click", function() {
						table.reload("userList", {
							page : {
								curr : 1
							// 重新从第 1 页开始
							},
							where : {
								// associationId:treeCheckNode,
								paramA : $(".searchVal").val()
							}
						})
					});
					// 添加校友会成员
					function addCard(edit, parien) {
						userObj = table.checkStatus('postList').data[0];

						var restUrl = '';
						if (userObj) {
							restUrl = application.SERVE_URL
									+ '/as/asAssociationUser/get?associationId='
									+ userObj.associationId + '&userId'
									+ userObj.userId;
						} else {
							restUrl = application.SERVE_URL
									+ '/as/asAssociationUser/get';
						}

						var id = edit ? (edit.id ? edit.id : null) : null;
						publicUtil.gotoEditPage(restUrl, id, "校友会成员管理",
								"useradd.html")
					}
				})