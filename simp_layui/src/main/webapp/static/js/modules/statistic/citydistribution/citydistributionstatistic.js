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
layui.use(['element', 'layer', 'jquery', 'application', 'publicUtil'], function() {
	element = layui.element;
	$ = layui.$;
	layer = parent.layer === undefined ? layui.layer : top.layer;
	application = layui.application;
	publicUtil = layui.publicUtil;
	var myChart = echarts.init(document.getElementById('map'));
	initMapDataChart();
	
	
	
	function initMapDataChart(){
		
		$.ajax({
			url: application.SERVE_URL + "/chart/statistic/smCountByArea",
			success: function (res) {
				console.log(res);
				initMapChart(res);
			},
			error: function(res){ // 出错时默认的处理函数
				initMapChart(null);
			}
		})
	}
	
	//初试化地图
	function initMapChart(res) {
		// 基于准备好的dom，初始化echarts实例
		var option = {
			title: {
					text: '校友区域分布图',
					left:'center'
			},			
			tooltip : {
					trigger: 'item'
			},
			toolbox: {
					show: true,
					orient : 'vertical',
					left: 'right',
					top: 'center',
					feature : {
							mark : {show: true},
							dataView : {show: true, readOnly: false},
							restore : {show: true},
							saveAsImage : {show: true}
					}
			},			
			series: {
            name: '校友区域分布',
            type: 'map',
            mapType: 'china',
            roam: false,
						zoom: 1.2,
            label: {
                normal: {
                    show: false
                },
                emphasis: {
                    show: true
                }
            },
						itemStyle: {
							normal: {
								areaColor: '#1AA094',
								borderColor: ' #031525',
								shadowColor: 'rgba(0, 0, 0, 0.5)',
							},
							emphasis: {
								areaColor: '#b3f3f3'
							}
						},
						data : res
//             data:[
//                 {name: '北京',value: Math.round(Math.random()*10)},
//                 {name: '天津',value: Math.round(Math.random()*10)},
//                 {name: '上海',value: Math.round(Math.random()*10)},
//                 {name: '重庆',value: Math.round(Math.random()*10)},
//                 {name: '河北',value: Math.round(Math.random()*10)},
//                 {name: '河南',value: Math.round(Math.random()*10)},
//                 {name: '云南',value: Math.round(Math.random()*10)},
//                 {name: '辽宁',value: Math.round(Math.random()*10)},
//                 {name: '黑龙江',value: Math.round(Math.random()*10)},
//                 {name: '湖南',value: Math.round(Math.random()*10)},
//                 {name: '安徽',value: Math.round(Math.random()*10)},
//                 {name: '山东',value: Math.round(Math.random()*10)},
//                 {name: '新疆',value: Math.round(Math.random()*10)},
//                 {name: '江苏',value: Math.round(Math.random()*10)},
//                 {name: '浙江',value: Math.round(Math.random()*10)},
//                 {name: '江西',value: Math.round(Math.random()*10)},
//                 {name: '湖北',value: Math.round(Math.random()*10)},
//                 {name: '广西',value: Math.round(Math.random()*10)},
//                 {name: '甘肃',value: Math.round(Math.random()*10)},
//                 {name: '山西',value: Math.round(Math.random()*10)},
//                 {name: '内蒙古',value: Math.round(Math.random()*10)},
//                 {name: '陕西',value: Math.round(Math.random()*10)},
//                 {name: '吉林',value: Math.round(Math.random()*10)},
//                 {name: '福建',value: Math.round(Math.random()*10)},
//                 {name: '贵州',value: Math.round(Math.random()*10)},
//                 {name: '广东',value: Math.round(Math.random()*10)},
//                 {name: '青海',value: Math.round(Math.random()*10)},
//                 {name: '西藏',value: Math.round(Math.random()*10)},
//                 {name: '四川',value: Math.round(Math.random()*10)},
//                 {name: '宁夏',value: Math.round(Math.random()*10)},
//                 {name: '海南',value: Math.round(Math.random()*0)},
//                 {name: '台湾',value: Math.round(Math.random()*10)},
//                 {name: '香港',value: Math.round(Math.random()*10)},
//                 {name: '澳门',value: Math.round(Math.random()*10)}
//             ]
			},
			visualMap: {
					min: 0,
					max: 10000,
					left: 'left',
					top: 'bottom',
					text:['高','低'],
					calculable: true,
					inRange: {
							color: ['lightskyblue','yellow','orangered']
					}
			},
						
// 			geo: {
// 				//引入中国的地图 
// 				map: 'china',
// 				label: {
// 					emphasis: {
// 						show: false
// 					}
// 				},
// 				roam: true,  
// 				zoom: 1.1,
// 				itemStyle: {
// 					normal: {
// 						areaColor: '#1AA094',
// 						borderColor: ' #031525',
// 						shadowColor: 'rgba(0, 0, 0, 0.5)',
// 						shadowBlur: 10,
// 						shadowOffsetX: 10
// 					},
// 					emphasis: {
// 						areaColor: '#b3f3f3'
// 					}
// 				}
// 			}
		};
		myChart.setOption(option);

	}

});
