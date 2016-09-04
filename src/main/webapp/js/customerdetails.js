function getDtTlkData(){

	
	 
}

function populateTalukData(){
	

	$(function() {
			
			$.ajax({
				  url: 'getTalukData.do',
				  type: 'GET',
				 // data: 'twitterUsername=jquery4u',
				  success: function(data) { 
					  responseData=data;
					  //responseData = JSON.parse(data);
					  console.log("responseData : "+responseData);
					  console.log("getTalukData.talukData : "+responseData.talukData);
					  console.log("Success , populateTalukData is Called !!!"); 
					  
					    $('#serviceReportDiv').highcharts({
					        chart: {
					            type: 'column'
					        },
					        title: {
					            text: responseData.title
					        },
					        subtitle: {
					            text: responseData.subtitle
					        },
					        xAxis: {
					            type: 'category',
					            labels: {
					                rotation: -45,
					                style: {
					                    fontSize: '13px',
					                    fontFamily: 'Verdana, sans-serif'
					                }
					            }
					        },
					        yAxis: {
					            min: 0,
					            title: {
					                text: responseData.ytitle
					            }
					        },
					        legend: {
					            enabled: false
					        },
					        tooltip: {
					            pointFormat: 'No Of Services: <b>{point.y:.0f}</b>'
					        },
					        series: [{
					            name: 'Population',
					            data: responseData.contentData,
					            dataLabels: {
					                enabled: true,
					                rotation: -90,
					                color: '#FFFFFF',
					                align: 'right',
					                format: '{point.y:.0f}', // one decimal
					                y: 10, // 10 pixels down from the top
					                style: {
					                    fontSize: '13px',
					                    fontFamily: 'Verdana, sans-serif'
					                }
					            }
					        }]
					    });
					
					  
					  
				  },
				  error: function(e) {
					//called when there is an error
					  console.log(e);
					  console.log(e.message);
					  console.log("Failure , createUsageForService.do is Called !!!");
				  }
				}); 
			
			
		});
	
}