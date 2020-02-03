/**
 * @autor syp
 * @content echarts 实例
 * @returns
 * @Time 2018-08-04
 */
layui.config({
	base: "../../../../static/js/"
}).extend({
	"application": "application",
	"publicUtil": "publicUtil"
})
layui.use(['element', 'layer', 'jquery', 'application','publicUtil'], function () {
	element = layui.element;
	$ = layui.$;
	layer = parent.layer === undefined ? layui.layer : top.layer;
	application = layui.application;
	publicUtil = layui.publicUtil;
	var myBarChart = echarts.init(document.getElementById('bar'));
	var myLineChart = echarts.init(document.getElementById('line'));
	initLineChart();
	initBarChart();

	//初试化柱状图
	function initBarChart() {
		$.ajax({
			url: application.SERVE_URL + "/chart/statistic/donation/month/6",
			success: function (res) {
				option = {
					title: {
						text: '捐赠项目状态月统计'
					},
					legend: {
						data:['未开始','进行中','已完成']
					},
					tooltip: {},
					xAxis: {
						name: '时间',
						type: 'category',
						data: getvalue(res.WILL,'date')
					},
					yAxis : [
						{
							name: '数量',
							type : 'value',
							boundaryGap : [ 0, 0.1 ],
							minInterval : 1
						}
					],
					series: [
						{
							name:'未开始',
							type:'bar',
							stack: '状态',
							data:getvalue(res.WILL,'count')
						},
						{
							name:'进行中',
							type:'bar',
							stack: '状态',
							data:getvalue(res.BEDOING,'count')
						},
						{
							name:'已完成',
							type:'bar',
							stack: '状态',
							data:getvalue(res.HASDONE,'count')
						},												
					]
				}
				myBarChart.setOption(option);
			}
		});
	}
	
		//初试化折线图
	function initLineChart() {
		$.ajax({
			url: application.SERVE_URL + "/chart/statistic/donation/day/15",
			success: function (res) {
				option = {
					title: {
						text: '捐赠统计'
					},
					legend: {},
					tooltip: {},
					dataset: {
						// 序号为 1 的 dataset。
						source: res
					},
					xAxis: {
						name:'日期',
						type: 'time'
					},
					yAxis: {
						name:'金额',
						type: 'value'						
					},
					series: {
							datasetIndex: 0,
							name:'捐赠金额',
							type: 'line',
							encode: {
								x: 'date',
								y: 'count'
							}
						}
				}
				myLineChart.setOption(option);
			}
		});
	}
	
	
	
	//处理数据
	function getvalue(list,value){
		console.log(list);
		var arr =[];
		for(var i =0;i<list.length;i++){
			arr.push(list[i][value])
		}
		console.log(arr);
		return arr;
	}
});
