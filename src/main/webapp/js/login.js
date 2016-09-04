// Login Form
function loginPage() {
	$(function() {
		var button = $('#loginButton');
		var box = $('#loginBox');
		var form = $('#loginForm');
		button.removeAttr('href');
		button.mouseup(function(login) {
			box.toggle();
			button.toggleClass('active');
		});
		form.mouseup(function() {
			return false;
		});
		$(this).mouseup(function(login) {
			if (!($(login.target).parent('#loginButton').length > 0)) {
				button.removeClass('active');
				box.hide();
			} 
		});
	});
}

function closureDateChange(element){ 
	$(function() {
		
		//var closureDate = $('#closureDate').val();//2015-12-29
		var validateDate =element.value;
		var fieldName =element.id;
		var inputVal = document.getElementById(fieldName);
		 var regPattern = /^(19|20)\d\d(-)(0[1-9]|1[012])(-)(0[1-9]|[12][0-9]|3[01])$/;
         var checkArray = validateDate.match(regPattern);
         if (checkArray == null){
        	 	if(fieldName=='dateOfComplaint')
        	 		{
        	 		alert('SORRY !!! Please Enter Valid Date Of Complaint');
        	 		inputVal.style.backgroundColor = "yellow";
        	 		inputVal.focus();
                    //return false;
        	 		}
        	 	else {
        	 		alert('SORRY !!! Please Enter Valid Closure Date');
        	 		inputVal.style.backgroundColor = "yellow";
        	 		inputVal.focus();
                   // return false; 
        	 	} 
         }else
			{ 
 			console.log('DateChange Called !!! : '+validateDate); 
 			inputVal.style.backgroundColor = "white";
 			} 
	});
	
}
function getLastSixMonthServiceData(){
	var arrayData = [
		                ['Shanghai', 23.7],
		                ['Lagos', 16.1],
		                ['Instanbul', 14.2],
		                ['Karachi', 14.0],
		                ['Mumbai', 12.5],
		                ['Moscow', 12.1]
		            ];
	console.log("arrayData : "+arrayData);
$(function() {
		
		$.ajax({
			  url: 'createUsageForService.do',
			  type: 'GET',
			 // data: 'twitterUsername=jquery4u',
			  success: function(data) { 
				  responseData=data;
				  //responseData = JSON.parse(data);
				  console.log("responseData : "+responseData);
				  console.log("responseData.contentData : "+responseData.contentData);
				  console.log("Success , createUsageForService.do is Called !!!"); 
				  
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
function getSalesReport(){
	//var responseData = {categories:['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],soldData:[7.0, 6.9, 9.5, 14.5, 18.4, 21.5], committedData:[3.9, 4.2, 5.7, 8.5, 11.9, 15.2]}; 
	var responseData =null;
	 
	
	$(function() {
		
		$.ajax({
			  url: 'createSalesReport.do',
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



function getSparesCountMetrics(){
	//var responseData = {categories:['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],soldData:[7.0, 6.9, 9.5, 14.5, 18.4, 21.5], committedData:[3.9, 4.2, 5.7, 8.5, 11.9, 15.2]}; 
	var responseData =null;
	 
	
	$(function() {
		
		$.ajax({
			  url: 'getSparesCountMetrics.do',
			  type: 'GET',
			  data: 'twitterUsername=jquery4u',
			  success: function(data) { 
				  responseData=data;
				  //responseData = JSON.parse(data);
				  console.log("responseData : "+responseData);
				  console.log("Success , getSparesCountMetrics.do is Called !!!");
				  
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
/*
 * $(document).ready(function(){ $('#salesReportImage').width(200);
 * $('#salesReportImage').mouseover(function() {
 * $(this).css("cursor","pointer"); $(this).animate({width: "500px"}, 'slow');
 * });
 * 
 * $('#salesReportImage').mouseout(function() { $(this).animate({width:
 * "200px"}, 'slow'); }); });
 */

function getSalesReport_old() {
	$('#salesReportDiv')
			.html(
					'<img width="20" style="margin-top:150px;margin-left:30px;" height="20" alt="" src="./images/ajax-loader.gif">');
	$
			.get(
					'createSalesReport.do',
					function(response) {
						$('#salesReportDiv')
								.html(
										'<img id="salesReportImage" src="getSalesReportChart.do" alt="" width="100%" height="96%" />');
					});
}

function getServiceReport() {
	$('#serviceReportDiv')
			.html(
					'<img width="20" style="margin-top:150px;margin-left:250px;" height="20" alt="" src="./images/ajax-loader.gif">');
	$
			.get(
					'createServiceReport.do',
					function(response) {
						$('#serviceReportDiv')
								.html(
										'<img id="salesReportImage" src="getServiceReportChart.do" alt="" width="100%" height=96%" />');
					});
}

/* Ajax call for printing report */
function ajaxCallForPrint() {

	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var from = $('#from').val();
	var forCurrentMonth = $('#forCurrentMonth').val();
	var disp_setting = "";
	if ((document.getElementById('fromDate') != null)) {
		queryParam = "fromDate=" + fromDate + "&toDate=" + toDate + "&from="
				+ from;
	} else if (toDate == "") {
		queryParam = "forCurrentMonth=" + forCurrentMonth + "&from=" + from;
	} else {
		queryParam = "toDate=" + toDate + "&from=" + from;
	}
	$('#AjaxLoadingInfo').show();
	$('#exportmessage').hide();
	disableButton();
	disp_setting += "scrollbars=1,width=700, height=400, left=20, top=25";
	//var disp_setting = "";
	/*	disp_setting = "toolbar=no,location=no,directories=no,menubar=no,resizable=no ";
	 if (document.getElementById('fromDate') != null) {

	 queryParam = "fromDate=" + fromDate + "&toDate=" + toDate + "&from="
	 + from;
	 disp_setting += "scrollbars=1,width=700, height=400, left=20, top=25";
	 } else {
	 queryParam = "forCurrentMonth=" + toDate + "&from=" + from;

	 disp_setting += "scrollbars=1,width=700, height=400, left=20, top=25";
	 }
	 */$
			.post(
					"printReport.do?" + queryParam,
					function(data) {
						if (data.length > 0) {
							$('#AjaxLoadingInfo').hide();
							enableButton();
							var content_vlue = data;
							var docprint = window.open("", "", disp_setting);
							docprint.document.open();
							docprint.document.write("<html>");
							docprint.document
									.write("<body onload=\"self.print();\"><div style='padding:2px;overflow-x:hidden;height:100%;'>");
							docprint.document.write(content_vlue);
							docprint.document.write("</div></body></html>");
							docprint.document.close();
							docprint.focus();

						}
					});

}

/* Run report call from dashboard */
function runReport(form) {
	form.action = "getReport.do";
	form.submit();
	$("#AjaxLoadingInfo").show();
}

/* Save report call from dashboard */
function saveReport(form) {
	form.action = "exportReport.do";
	form.submit();
}

var url = "";
function getReportAjaxCall() {
	var queryParam = "";
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	var from = $('#from').val();
	var forCurrentMonth = $('#forCurrentMonth').val();
	// var navLink = $('#navLink').val();
	if (document.getElementById('fromDate') != null && fromDate == "") {
		alert("Enter Duration");
		return;
	} else if (document.getElementById('toDate') != null && toDate == "") {
		alert("Enter Duration");
		return;
	}
	$('#AjaxLoadingInfo').show();
	$('#exportmessage').hide();
	disableButton();
	if ((document.getElementById('fromDate') != null)) {
		queryParam = "fromDate=" + fromDate + "&toDate=" + toDate + "&from="
				+ from;
		if (fromDate == "" || toDate == "") {
			queryParam = "forCurrentMonth=" + forCurrentMonth + "&from=" + from;
		}
	} else if (toDate == "") {
		queryParam = "forCurrentMonth=" + forCurrentMonth + "&from=" + from;
	} else {
		queryParam = "toDate=" + toDate + "&from=" + from;
	}
	url = "rerunReport.do?" + queryParam;
	$.post(url, function(data) {
		if (data.length > 0) {
			$('#reportDiv').html(data);
		} else {
			$('#reportDiv').html("No Records Found");
		}
		$('#AjaxLoadingInfo').hide();
		enableButton();
		$('#reportDiv').scrollTop(0);
		$('#searchTermDiv').scrollTop(0);
	});
}

function getSpareCost(spareid, costid, quantityid, total) {
	//$("#spare-select2 option:selected").attr('title') 
	var spareval = $("#" + spareid.id + " option:selected").attr('title');
	//alert(spareval1);
	//var spareval = parseFloat(spareval1);
	//alert(spareval);
	$('#' + costid.id).val(spareval);
	var quantity = $('#' + quantityid.id).val();
	$('#' + total.id).val(quantity * spareval);
	calculateSpareTotal();
}

function getSpareCost1(costid, quantityid, total) {
	var cost = $('#' + costid.id).val();
	var quantity = $('#' + quantityid.id).val();
	$('#' + total.id).val(quantity * cost);
	calculateSpareTotal();
}

function calculateSpareTotal() {
	var text = 0;
	$("input[id^='total']").each(function() {
		var value = parseFloat($(this).val());
		text = value + text;
		$('#spareCost').val(text);
	});
}

function calculateTotal() {
	text1 = parseFloat($('#spareCost').val());
	text2 = parseFloat($('#labourAmount').val());
	$('#overallCost').val(text1 + text2);
}

function validateForm() {
	$('#saveJobcardButton').attr('disabled','disabled');
	var jobcardnumber = $("#jobcardNumber").val();
	if (jobcardnumber <= 0) {
		alert("Enter jobcard number");
		return false;
	} else if (!$("#chasisNumber").val()) {
		alert("Enter chasis number");
		return false;
	} else if (!$("#receiptNumber").val()) {
		alert("Enter Receipt number");
		return false;
	} else if (!$("#dateOfComplaint").val()) {
		alert("Enter Date of complaint");
		return false;
	}
}

function enableJobcardButton() {
	if($('#message').text() == 'successfully saved') {
		$('#saveJobcardButton').attr('disabled', 'disabled');
	}else{ 
	  $('#saveJobcardButton').removeAttr('disabled');
	}	
}

function validateCustomerForm(id) {
	var chasisno = $("#chasisNumber").val();
	var numberTest = "/^d[0-9]+/";
	if (!chasisno) {
		alert("Enter chasis number");
		return false;
	} else if (!$("#customerId").val() && !$('#autoincrement:checked').val()) {
		alert('Enter customerId or Auto increment option');
		if (numberTest.test(customerId)) {
			alert("Enter number format only");
		}
		return false;
	} else if (!$("#engineNumber").val()) {
		alert("Enter engine number");
		return false;
	} else if (!$("#dateOfSale").val()) {
		alert("Enter Date of Sale");
		return false;
	}  else if(id == 'saveCustomer') {
		saveCustomerDetail(customerform);
	} else if(id == 'updateSpares') {
		var value = confirmUpdate('Do you want to continue with spares detail update ?');
		return value;
	}
}

function saveCustomerDetail(form) {
	$('#saveCustomer').attr('disabled', 'disabled');
	form.action = "addCustomerDetails.do";
	form.submit();
}

// Enable button if customer details is not saved..
function enableCustomerButton() {
	if($('#message').text() == 'successfully saved') {
		$('#saveCustomer').attr('disabled', 'disabled');
	}else{ 
	  $('#saveCustomer').removeAttr('disabled');
	}
}

//Confirm before update to the jobcard or customer detail
function confirmUpdate(text) {
	var r=confirm(text);
	if (r==true)
	  {
	  return true;
	  }
	else
	  {
	  return false;
	  }
}

function clearCustomerDetail() {
	if ($("#customerId").val()) {
		$("#customerId").val('');
	} else if ($("#engineNumber").val()) {
		$("#engineNumber").val('');
	} else if ($("#chasisNumber").val()) {
		$("#chasisNumber").val('');
	} else if ($("#dateOfSale").val()) {
		$("#dateOfSale").val('');
	}
}

function loadDateComponents() {
	$("#accordion").accordion({
		animated : "bounceslide"
	});

	$(document).ready(function() {
		$("#dateOfComplaint").datepicker({
			showOn : "button",
			buttonImage : "images/calendar.gif",
			buttonImageOnly : true,
			dateFormat : 'yy-mm-dd',
			changeMonth : true,
			changeYear : true
		});

		$("#closureDate").datepicker({
			showOn : "button",
			buttonImage : "images/calendar.gif",
			buttonImageOnly : true,
			dateFormat : 'yy-mm-dd',
			changeMonth : true,
			changeYear : true
		});
/*		$("#dateOfFeedback").datepicker({
			showOn : "button",
			buttonImage : "images/calendar.gif",
			buttonImageOnly : true,
			dateFormat : 'yy-mm-dd',
			changeMonth : true,
			changeYear : true
		});
		$('#basic_example_1').datetimepicker();
		$('#basic_example_1').datetimepicker();
*/		

	});

}

function loadCustomerDateComponents() {
	$("#accordion").accordion({
		animated : "bounceslide"
	});

	$(document).ready(function() {
		$("#dateOfSale").datepicker({
			showOn : "button",
			buttonImage : "images/calendar.gif",
			buttonImageOnly : true,
			dateFormat : 'yy-mm-dd',
			changeMonth : true,
			changeYear : true
		});

		$("#installedDate").datepicker({
			showOn : "button",
			buttonImage : "images/calendar.gif",
			buttonImageOnly : true,
			dateFormat : 'yy-mm-dd',
			changeMonth : true,
			changeYear : true
		});

	});

}
function loadAssociateDateComponents() {
	$("#accordion").accordion({
		animated : "bounceslide"
	});

	$(document).ready(function() {
		$("#dateOfJoin").datepicker({
			showOn : "button",
			buttonImage : "images/calendar.gif",
			buttonImageOnly : true,
			dateFormat : 'yy-mm-dd',
			changeMonth : true,
			changeYear : true
		});


	});

}
function loadAccordian(){
	$("#accordion").accordion({
		animated : "bounceslide"
	});
}

/*function populateDefaultValue(selectbox, text) {
 value = $("#" + selectbox.id).val();
 alert(value);
 $("#" + text.id).val(value);
 }*/

function addSpareDetail() {
	var counter = $('#sparetable tr:last').attr("id");
	counter++;

	if (counter % 2 == 0) {
		$('#sparetable tr:last')
				.after(
						'<tr id="'
								+ counter
								+ '"><td><select onchange="getSpareCost(this,cost'
								+ counter
								+ ',quantity'
								+ counter
								+ ',total'
								+ counter
								+ ');" style="width:155px;border:1px solid white;"  name="spareDetails['
								+ counter
								+ '].spareCode" id="spare-select'
								+ counter
								+ '"></td><td><input type="text" name="spareDetails['
								+ counter
								+ '].cost" id="cost'
								+ counter
								+ '" value="0" onblur="getSpareCost1(cost'
								+ counter
								+ ',quantity'
								+ counter
								+ ',total'
								+ counter
								+ ')" style="border:1px solid white;"></td><td><input type="text" name="spareDetails['
								+ counter
								+ '].quantity" id="quantity'
								+ counter
								+ '" value="0" onblur="getSpareCost1(cost'
								+ counter
								+ ',quantity'
								+ counter
								+ ',total'
								+ counter
								+ ')" style="border:1px solid white;" ></td><td><input type="text" name="spareDetails['
								+ counter
								+ '].total" id="total'
								+ counter
								+ '" value="0" style="border:1px solid white;"></td></tr>');
	} else {
		$('#sparetable tr:last')
				.after(
						'<tr id="'
								+ counter
								+ '" style="background:#E6E6E6;"><td><select onchange="getSpareCost(this,cost'
								+ counter
								+ ',quantity'
								+ counter
								+ ',total'
								+ counter
								+ ');" style="width:155px;background-color:#E6E6E6;border:1px solid white;"  name="spareDetails['
								+ counter
								+ '].spareCode" id="spare-select'
								+ counter
								+ '"></td><td ><input type="text" style="background-color:#E6E6E6;border:1px solid white;" name="spareDetails['
								+ counter
								+ '].cost" id="cost'
								+ counter
								+ '" onblur="getSpareCost1(cost'
								+ counter
								+ ',quantity'
								+ counter
								+ ',total'
								+ counter
								+ ')" value="0" ></td><td><input style="background-color:#E6E6E6;border:1px solid white;" type="text" name="spareDetails['
								+ counter
								+ '].quantity" id="quantity'
								+ counter
								+ '" value="0" onblur="getSpareCost1(cost'
								+ counter
								+ ',quantity'
								+ counter
								+ ',total'
								+ counter
								+ ')" ></td><td><input style="background-color:#E6E6E6;border:1px solid white;" type="text" name="spareDetails['
								+ counter + '].total" id="total' + counter
								+ '" value="0" ></td></tr>');
	}
	$(document).ready(
			function() {
				//var sparevalue = $("#spare-select0 option").attr('title');
				$('#spare-select0 option').each(
						function() {
							var sparevalue = $(this).attr('title');
							var sparetext = $(this).text();
							$('#spare-select' + counter).append(
									$("<option></option>").attr("value",
											sparetext).text(sparetext).attr(
											'title', sparevalue));
							//$(this).attr('title',sparevalue);
						});

				$('#spare-select' + counter).trigger('change');

			});
	$('#spare-select' + counter).append('</select>');
}

function enableordisableCustomerId() {
	if ($('#autoincrement:checked').val()) {
		$('#customerId').val('');
		$('#customerId').attr('disabled', 'disabled');
	} else {
		$('#customerId').removeAttr('disabled');
	}
}

function validateReport() {
	var fromDate = $('#fromDate').val();
	var toDate = $('#toDate').val();
	if ((document.getElementById('fromDate') != null)) {
		if (fromDate == "" && toDate != "") {
			alert("Enter From Date");
			return false;
		}
	}
	ajaxCallForPrint();
}

function showLoadingIcon() {
	$('#AjaxLoadingInfo').show();
}

function hideLoadingIcon() {
	$('#AjaxLoadingInfo').hide();
}

function disableButton() {
	$('#savereport').attr('disabled', 'disabled');
	$('#printreport').attr('disabled', 'disabled');
	$('#runreport').attr('disabled', 'disabled');
}

function enableButton() {
	$('#runreport').removeAttr("disabled");
	$('#savereport').removeAttr("disabled");
	$('#printreport').removeAttr('disabled');
}