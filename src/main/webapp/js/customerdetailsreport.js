/**
 * 
 */
var url = "";
function getCustomerReport(queryType) {
	$('#savereport').attr('disabled', true);
	$('#AjaxLoadingInfo').show();
	$('#runreport').hide();
	$('#printreport').hide();
	$('#savereport').hide();
	disableButton();
	if (queryType == 'TALUK') { 
		getCustomerTabulationDatasByTaluk();
	} else if (queryType == 'DISTRICT') {
		getCustomerTabulationDatasByDistrict();
	}  
}
function getGridData(){
	var grid;
	//var employeeDetails = $("#employeeDetails").val(); 
	
	// console.log('slickgrid object !!! : '+object);
	// console.log('slickgrid object !!! : '+theData);
	// var json = JSON.stringify(eval("(" + employeeDetails + ")"));
	// console.log('slickgrid json !!! : '+json);
	 
	  var columns = [
	    {id: "customerId", name: "Customer Id", field: "customerId"},
	    {id: "customerDetails", name: "Customer Details", field: "customerDetails"},
	    {id: "contactNumber", name: "Contact Number", field: "contactNumber"},
	    {id: "chasisNumber", name: "Chasis Number", field: "chasisNumber"},
	    {id: "engineNumber", name: "Engine Number", field: "engineNumber"},
	    {id: "dateOFsale", name: "Date Of Sale", field: "dateOFsale"}
	  ];
	  var options = {
	    enableCellNavigation: true,
	    enableColumnReorder: false
	  };
	  $(function () {
	    var data = [];
	    for (var i = 0; i < 5; i++) {
	      data[i] = {
	    		  feedbackid: "Task " + i,
	    		  remark: "5 days",
	    		  eid: Math.round(Math.random() * 100),
	    		  issueStatus: "01/01/2009"
	      };
	    }
	    console.log('slickgrid data !!! : '+data);
	    console.log('slickgrid columns !!! : '+columns);
	    
	    grid = new Slick.Grid("#myGrid", data, columns, options);
	  });
}

function getCustomerTabulationDatasByTaluk(){
	//var responseData = {categories:['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],soldData:[7.0, 6.9, 9.5, 14.5, 18.4, 21.5], committedData:[3.9, 4.2, 5.7, 8.5, 11.9, 15.2]}; 
	var dataForExport =null;
	var responseData =null;
	var taluk = $("#taluk").val(); 
	var yearData = $("#yearData").val(); 
	var month = $("#month").val();
	
	
	console.log('taluk : '+taluk);
	console.log('yearData : '+yearData);
	console.log('month : '+month);	
	var columns = [//customerDetails
	       	    {id: "customerId", name: "Customer Id", field: "customerId"},
	       	    {id: "customerDetails", name: "Customer Details", field: "customerDetails"},
	       	    {id: "contactNumber", name: "Contact Number", field: "contactNumber"},
	       	    {id: "chasisNumber", name: "Chasis Number", field: "chasisNumber"},
	       	    {id: "engineNumber", name: "Engine Number", field: "engineNumber"},
	    	    {id: "dateOFsale", name: "Date Of Sale", field: "dateOFsale"}
	       	  ];
	 var options = {
			    enableCellNavigation: true,
			    enableColumnReorder: false
			  };
	$(function() {
		
		$.ajax({
			  url: 'getCustomerDatas.do',
			  type: 'GET',
			  data: 'queryName='+taluk+'&yearData='+yearData+'&monthData='+month+'&diffData=TALUK',
			  success: function(data) { 
				  responseData=data.dataList;
				  dataForExport=data.exportData;
				  //responseData = JSON.parse(data);
				  console.log("responseData : "+responseData);
				  console.log("Success , getCustomerDatas.do is Called !!!"); 
				  	$('#AjaxLoadingInfo').hide();
				  	$('#runreport').show(); 
					$('#savereport').show();
					enableButton();
					console.log("dataForExport : "+JSON.stringify(dataForExport));
					$('#toStoreTableJsonData').text(JSON.stringify(dataForExport));
					if(data.noOfCustomers>0)
						{
							$('#noOfCustomers').text(data.noOfCustomers);
						}else
							{
								$('#noOfCustomers').text(data.noOfCustomers);
								$('#savereport').attr('disabled', 'disabled');
							}
					
					
					
				  grid = new Slick.Grid("#myGrid", responseData, columns, options);
			  },
			  error: function(e) {
				//called when there is an error
				  console.log(e);
				  console.log(e.message);
				  console.log("Failure , getCustomerDatas.do is Called !!!");
				  	$('#runreport').show();
					$('#printreport').show();
					$('#savereport').show();
					$('#runreport').attr('disabled', false);
					$('#printreport').attr('disabled', false);
					$('#savereport').attr('disabled', false);
			  }
			}); 
		
		
	});
}
function getCustomerTabulationDatasByDistrict(){
	//var responseData = {categories:['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],soldData:[7.0, 6.9, 9.5, 14.5, 18.4, 21.5], committedData:[3.9, 4.2, 5.7, 8.5, 11.9, 15.2]}; 
	var dataForExport =null;
	var responseData =null;
	var district = $("#district").val(); 
	var yearData = $("#yearData").val(); 
	var month = $("#month").val();
	console.log('month : '+month);		
	console.log('taluk : '+district);
	console.log('yearData : '+yearData);
	var columns = [//customerDetails
	       	    {id: "customerId", name: "Customer Id", field: "customerId"},
	       	    {id: "customerDetails", name: "Customer Details", field: "customerDetails"},
	       	    {id: "contactNumber", name: "Contact Number", field: "contactNumber"},
	       	    {id: "chasisNumber", name: "Chasis Number", field: "chasisNumber"},
	       	    {id: "engineNumber", name: "Engine Number", field: "engineNumber"},
	    	    {id: "dateOFsale", name: "Date Of Sale", field: "dateOFsale"}
	       	  ];
	 var options = {
			    enableCellNavigation: true,
			    enableColumnReorder: false
			  };
	$(function() {
		
		$.ajax({
			  url: 'getCustomerDatas.do',
			  type: 'GET',
			  data: 'queryName='+district+'&yearData='+yearData+'&monthData='+month+'&diffData=DISTRICT',
			  success: function(data) { 
				  responseData=data.dataList;
				  dataForExport=data.exportData;
				  //responseData = JSON.parse(data);
				  console.log("responseData : "+responseData);
				  console.log("Success , getCustomerDatas.do is Called !!!"); 
				  	$('#AjaxLoadingInfo').hide();
				  	$('#runreport').show(); 
					$('#savereport').show();
					enableButton();
					console.log("dataForExport : "+JSON.stringify(dataForExport));
					$('#toStoreTableJsonData').text(JSON.stringify(dataForExport));
					if(data.noOfCustomers>0)
						{
							$('#noOfCustomers').text(data.noOfCustomers);
						}else
							{
								$('#noOfCustomers').text(data.noOfCustomers);
								$('#savereport').attr('disabled', 'disabled');
							}
					
					
					
				  grid = new Slick.Grid("#myGrid", responseData, columns, options);
			  },
			  error: function(e) {
				//called when there is an error
				  console.log(e);
				  console.log(e.message);
				  console.log("Failure , getCustomerDatas.do is Called !!!");
				  	$('#runreport').show();
					$('#printreport').show();
					$('#savereport').show();
					$('#runreport').attr('disabled', false);
					$('#printreport').attr('disabled', false);
					$('#savereport').attr('disabled', false);
			  }
			}); 
		
		
	});
}  

function JSON2CSV(objArray) {
    var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;

    var str = '';
    var line = '';

    if ($("#labels").is(':checked')) {
        var head = array[0];
        if ($("#quote").is(':checked')) {
            for (var index in array[0]) {
                var value = index + "";
                line += '"' + value.replace(/"/g, '""') + '",';
            }
        } else {
            for (var index in array[0]) {
                line += index + ',';
            }
        }

        line = line.slice(0, -1);
        str += line + '\r\n';
    }

    for (var i = 0; i < array.length; i++) {
        var line = '';

        if ($("#quote").is(':checked')) {
            for (var index in array[i]) {
                var value = array[i][index] + "";
                line += '"' + value.replace(/"/g, '""') + '",';
            }
        } else {
            for (var index in array[i]) {
                line += array[i][index] + ',';
            }
        }

        line = line.slice(0, -1);
        str += line + '\r\n';
    }
    return str;
    
}
        
function downloadCustomerData() {
    var json = $("#toStoreTableJsonData").text();
	
	console.log('toStoreTableJsonData : '+json); 
	  
    var csv = JSON2CSV(json);
    window.open("data:text/csv;charset=utf-8," + escape(csv));
}