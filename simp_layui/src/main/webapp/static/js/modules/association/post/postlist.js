/**
 * @autor lzq
 * @content 校友会任职信息管理
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
// 列表信息
var schoolmateSel;
layui
		.use(
				[ 'form', 'layer', 'laydate', 'table', 'laytpl', 'application',
						'publicUtil' ],
				function() {
					var form = layui.form, layer = parent.layer === undefined ? layui.layer
							: top.layer, _$ = layui.jquery, publicUtil = layui.publicUtil, application = layui.application, laydate = layui.laydate, laytpl = layui.laytpl, table = layui.table;

					application.init();

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
					// 初始化树高度
					_$(function() {
						$(".ztree").height($(window).height() - 105);
						$(window).resize(function() {
							$(".ztree").height($(window).height() - 105);

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
					table.on('rowRight(postList)', function(obj) {
						publicUtil.show_menu(obj);
					});

					// 左键点击事件
					table.on('row(postList)', function(obj) {
						publicUtil.hiddenMenu(obj);
						schoolmateSel = obj.data;
					});

					// 加载右侧数据 postList
					function onClick(event, treeId, treeNode, clickFlag) {
						// console.log(treeNode.id);
						// 生产坏境下请求后台
						treeCheckNode = treeNode.id;
						tableIns = table.render({
							elem : '#postList',
							// 生产坏境下请求后台
							url : application.SERVE_URL+'/as/asPost/listschool',
							where : {
								associationId : treeNode.id
							},
							cellMinWidth : 60,
							page : true,
							even : true,
							height : "full-230",
							id : "postList",
							cols : [ [ {
								type : 'checkbox'
							}, {
								field : 'smSchoolmate',
								title : '校友',
								templet : function(d) {
									var res = d.smSchoolmate.name;
									// alert(d);
									console.log(d.smSchoolmate.name);
									var htm = "";
									if (res != null && res != "") {
										htm = res;
									} else {
										htm = '校友为空'
									}
									return htm;
								}
							}, {
								field : 'roleType',
								title : '校友会职务'
							}, {
								field : 'isPosting',
								title : '是否现任职'
							}, {
								field : 'postNum',
								title : '任职届次'
							}, {
								field : 'postDate',
								title : '任职时间'
							}, {
								field : 'leaveDate',
								title : '离任时间'
							} ] ],
							done : function(res, curr, count) { // res 接口返回的信息,
								publicUtil.tableSetStr(application.SERVE_URL
										+ "/sys/sysdict/getByTypeCode", {
									'typeCode' : 'ASPOST_TYPE'
								}, 'roleType');
								publicUtil.tableSetStr(application.SERVE_URL
										+ "/sys/sysdict/getByTypeCode", {
									'typeCode' : 'BOOLEAN_TYPE'
								}, 'isPosting');
							}
						});
					}

					// 新增操作
					_$(document).on('click', '.PER_ADD', function() {
						addPost(table.checkStatus('postList').data[0], "add");
					});

					// 编辑操作
					_$(document)
							.on(
									'click',
									'.PER_EDIT',
									function() {
										var flag = publicUtil
												.jurgeSelectRows(table
														.checkStatus('postList').data);
										if (flag) {
											addPost(
													table
															.checkStatus('postList').data[0],
													"edit");
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
														.checkStatus('postList').data);
										if (flag) {
											layer
													.confirm(
															'确定删除此校友的任职信息吗？',
															{
																icon : 3,
																title : '提示信息'
															},
															function(index) {
																_$
																		.ajax({
																			url : application.SERVE_URL
																					+ "/as/asPost/delete", // ajax请求地址
																			data : {
																				id : table
																						.checkStatus('postList').data[0].id
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
						table.reload("postList", {
							page : {
								curr : 1
							// 重新从第 1 页开始
							},
							where : {
								associationId : treeCheckNode,
								paramA : $(".searchVal").val()
							}
						})
					});

					// 添加任职信息
					function addPost(edit, action) {
						var restUrl = application.SERVE_URL + '/as/asPost/get';
						var id = "edit" == action ? (edit.id ? edit.id : null)
								: null;
						publicUtil.gotoEditPage(restUrl, id, "校友任职信息管理",
								"postadd.html")
					}

				})