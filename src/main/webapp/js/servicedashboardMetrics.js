/**
 * New node file
 */

function getServiceDashboardMetricsOne(){
	$('#salesReportDiv').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: 'Browser market shares. January, 2015 to May, 2015'
        },
        subtitle: {
            text: 'Click the columns to view versions. Source: <a href="http://netmarketshare.com">netmarketshare.com</a>.'
        },
        xAxis: {
            type: 'category'
        },
        yAxis: {
            title: {
                text: 'Total percent market share'
            }

        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '{point.y:.1f}%'
                }
            }
        },

        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
        },

        series: [{
            name: 'Brands',
            colorByPoint: true,
            data: [{
                name: 'Microsoft Internet Explorer',
                y: 56.33,
                drilldown: 'Microsoft Internet Explorer'
            }, {
                name: 'Chrome',
                y: 24.03,
                drilldown: 'Chrome'
            }, {
                name: 'Firefox',
                y: 10.38,
                drilldown: 'Firefox'
            }, {
                name: 'Safari',
                y: 4.77,
                drilldown: 'Safari'
            }, {
                name: 'Opera',
                y: 0.91,
                drilldown: 'Opera'
            }, {
                name: 'Proprietary or Undetectable',
                y: 0.2,
                drilldown: null
            }]
        }],
        drilldown: {
            series: [{
                name: 'Microsoft Internet Explorer',
                id: 'Microsoft Internet Explorer',
                data: [
                    [
                        'v11.0',
                        24.13
                    ],
                    [
                        'v8.0',
                        17.2
                    ],
                    [
                        'v9.0',
                        8.11
                    ],
                    [
                        'v10.0',
                        5.33
                    ],
                    [
                        'v6.0',
                        1.06
                    ],
                    [
                        'v7.0',
                        0.5
                    ]
                ]
            }, {
                name: 'Chrome',
                id: 'Chrome',
                data: [
                    [
                        'v40.0',
                        5
                    ],
                    [
                        'v41.0',
                        4.32
                    ],
                    [
                        'v42.0',
                        3.68
                    ],
                    [
                        'v39.0',
                        2.96
                    ],
                    [
                        'v36.0',
                        2.53
                    ],
                    [
                        'v43.0',
                        1.45
                    ],
                    [
                        'v31.0',
                        1.24
                    ],
                    [
                        'v35.0',
                        0.85
                    ],
                    [
                        'v38.0',
                        0.6
                    ],
                    [
                        'v32.0',
                        0.55
                    ],
                    [
                        'v37.0',
                        0.38
                    ],
                    [
                        'v33.0',
                        0.19
                    ],
                    [
                        'v34.0',
                        0.14
                    ],
                    [
                        'v30.0',
                        0.14
                    ]
                ]
            }, {
                name: 'Firefox',
                id: 'Firefox',
                data: [
                    [
                        'v35',
                        2.76
                    ],
                    [
                        'v36',
                        2.32
                    ],
                    [
                        'v37',
                        2.31
                    ],
                    [
                        'v34',
                        1.27
                    ],
                    [
                        'v38',
                        1.02
                    ],
                    [
                        'v31',
                        0.33
                    ],
                    [
                        'v33',
                        0.22
                    ],
                    [
                        'v32',
                        0.15
                    ]
                ]
            }, {
                name: 'Safari',
                id: 'Safari',
                data: [
                    [
                        'v8.0',
                        2.56
                    ],
                    [
                        'v7.1',
                        0.77
                    ],
                    [
                        'v5.1',
                        0.42
                    ],
                    [
                        'v5.0',
                        0.3
                    ],
                    [
                        'v6.1',
                        0.29
                    ],
                    [
                        'v7.0',
                        0.26
                    ],
                    [
                        'v6.2',
                        0.17
                    ]
                ]
            }, {
                name: 'Opera',
                id: 'Opera',
                data: [
                    [
                        'v12.x',
                        0.34
                    ],
                    [
                        'v28',
                        0.24
                    ],
                    [
                        'v27',
                        0.17
                    ],
                    [
                        'v29',
                        0.16
                    ]
                ]
            }]
        }
    });
}
function getSalesReport(){
	//var responseData = {categories:['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],soldData:[7.0, 6.9, 9.5, 14.5, 18.4, 21.5], committedData:[3.9, 4.2, 5.7, 8.5, 11.9, 15.2]}; 
	var responseData =null;
	 
	
	$(function() {
		
		$.ajax({
			  url: 'getServiceDashboardMetricsOne.do',
			  type: 'GET',
			 // data: 'twitterUsername=jquery4u',
			  success: function(data) { 
				  responseData=data;
				  //responseData = JSON.parse(data);
				  console.log("responseData : "+responseData);
				  console.log("Success , createSalesReport.do is Called !!!");
				  
				  $('#salesReportDiv').highcharts({
				        chart: {
				            type: 'line'
				        },
				        title: {
				            text: responseData.xtitle
				        },
				        subtitle: {
				            text: responseData.subtitle
				        },
				        xAxis: {
				           // categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun']
				        	categories:responseData.categories
				        },
				        yAxis: {
				            title: {
				                text: responseData.ytitle
				            }
				        },
				        plotOptions: {
				            line: {
				                dataLabels: {
				                    enabled: true
				                },
				                enableMouseTracking: false
				            }
				        },
				        series: [{
				            name: responseData.AseriesData,
				            //data: [7.0, 6.9, 9.5, 14.5, 18.4, 21.5],
				            data: responseData.soldData,
				            color: 'green'
				        }, {
				            name: responseData.BseriesData,
				            //data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2],
				            data: responseData.committedData,
				            color: 'red'
				        }]
				    });
				  
				  
			  },
			  error: function(e) {
				//called when there is an error
				  console.log(e);
				  console.log(e.message);
				  console.log("Failure , createSalesReport.do is Called !!!");
			  }
			}); 
		
		
	});
}
