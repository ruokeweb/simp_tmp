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
	var myLineChart = echarts.init(document.getElementById('line'));
	initLineChart();

	//初试化折线图
	function initLineChart() {
		$.ajax({
			url: application.SERVE_URL + "/chart/statistic/inited/day/30",
			success: function (initedData) {
				$.ajax({
					url: application.SERVE_URL + "/chart/statistic/regist/day/30",
					success: function (registData) {
						option = {
							title: {
								text: '热度统计'
							},
							legend: {},
							tooltip: {},
							dataset: [{
								// 序号为 0 的 dataset。
								source: initedData,
							}, {
								// 序号为 1 的 dataset。
								source: registData
							}],
							xAxis: {
								type: 'time'
							},
							yAxis: {},
							series: [{
									datasetIndex: 0,
									name:'注册人数',
									type: 'line',
									encode: {
										x: 'date',
										y: 'count'
									}
								},
								{
									datasetIndex: 1,
									name:'有效人数',
									type: 'line',
									encode: {
										x: 'date',
										y: 'count'
									}
								}
							]
						}
						myLineChart.setOption(option);
					}
				});
			}
		});
	}

});
