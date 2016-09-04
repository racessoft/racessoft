/**
 * 
 */

function loadSalaryDateComponents() {
	$("#accordion").accordion({
		animated : "bounceslide"
	});
	  $('.date-picker').datepicker(
              {
                  dateFormat: "mm/yy",
                  changeMonth: true,
                  changeYear: true,
                  showButtonPanel: true,
                  onClose: function(dateText, inst) {


                      function isDonePressed(){
                    	  ($('#ui-datepicker-div').html().indexOf('ui-datepicker-close ui-state-default ui-priority-primary ui-corner-all ui-state-hover') > -1);
                    	  return true;
                      }

                      if (isDonePressed()){
                          var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                          var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                          $(this).datepicker('setDate', new Date(year, month, 1)).trigger('change');
                          
                           $('.date-picker').focusout();//Added to remove focus from datepicker input box on selecting date
                      }
                  },
                  beforeShow : function(input, inst) {

                      inst.dpDiv.addClass('month_year_datepicker');

                      if ((datestr = $(this).val()).length > 0) {
                          year = datestr.substring(datestr.length-4, datestr.length);
                          month = datestr.substring(0, 2);
                          $(this).datepicker('option', 'defaultDate', new Date(year, month-1, 1));
                          $(this).datepicker('setDate', new Date(year, month-1, 1));
                          $(".ui-datepicker-calendar").hide();
                      }
                  }
              });
	  $('#asOnDate').datepicker(
              {
                  dateFormat: "yy-mm-dd",
                  changeMonth: true,
                  changeYear: true,
                  showButtonPanel: true,
                  onClose: function(dateText, inst) {


                      function isDonePressed(){
                    	  ($('#ui-datepicker-div').html().indexOf('ui-datepicker-close ui-state-default ui-priority-primary ui-corner-all ui-state-hover') > -1);
                    	  return true;
                      }

                      if (isDonePressed()){
                          var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                          var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                          $(this).datepicker('setDate', new Date(year, month, 1)).trigger('change');
                          
                           $('.date-picker').focusout();//Added to remove focus from datepicker input box on selecting date
                      }
                  },
                  beforeShow : function(input, inst) {

                      inst.dpDiv.addClass('month_year_datepicker');

                      if ((datestr = $(this).val()).length > 0) {
                          year = datestr.substring(datestr.length-4, datestr.length);
                          month = datestr.substring(0, 2);
                          $(this).datepicker('option', 'defaultDate', new Date(year, month-1, 1));
                          $(this).datepicker('setDate', new Date(year, month-1, 1));
                          $(".ui-datepicker-calendar").hide();
                      }
                  }
              });	  

}

function updateSalaryDateDetails(){
	
	  var monthyearDetails = $("#currentMonthYear").val();
		console.log("monthyearDetails : " + monthyearDetails);	  
$(function() {
		
		$.ajax({
			url : 'getAddSalaryDateDetails.do',
			type : 'GET',
			data : 'monthYear='+monthyearDetails,
			success : function(data) {
				var noOfWorkingDays = data.noOfWorkingDays;
				var noOfDaysInQueryMonth = data.noOfDaysInQueryMonth;
				// responseData = JSON.parse(data);
				console.log("noOfWorkingDays : " + noOfWorkingDays);
				console.log("noOfDaysInQueryMonth : " + noOfDaysInQueryMonth);
				console
						.log("Success , getAddSalaryDateDetails.do is Called !!!");
				$("#noOfWorkingDays").text(noOfWorkingDays);
				$("#noOfDaysInCurrentMonth").text(noOfDaysInQueryMonth);
				//$("#baseSalary").text(responseData);
				
			},
			error : function(e) {
				// called when there is an error
				console.log(e);
				console.log(e.message);
				console
						.log("Failure , getAddSalaryDateDetails.do is Called !!!");
			}
		}); 
		
		
	});
}

function resetUIPage(){
	$('input[type="text"][class="noOfDaysClass"]').prop("value", 0);
	$('input[type="text"][class="DaTotalAmountValue"]').prop("value", 0);
	$('.noOfDaysClass').val(0); 
	$('.DaTotalAmountValue').val("0.0"); 
	$('.noOfDaysClass').text(0); 
	$('.DaTotalAmountValue').text("0.0"); 
	$("#dailyAllowance").val("0.0");
	$("#leaveCount").val("0.0");
	$("#totalSalary").val("0.0");
	$("#dailyAllowance").val("0.0");
	$("#noOfDaysWorked").val("0.0");
	$("#totalSalary").val("0.0"); 
	
}
function validateSalaryDetailsForm(){
	/*var dailyAllowance = parseFloat($("#dailyAllowance").val());
	var totalSalary = parseFloat($("#totalSalary").val());
	*/
	var dailyAllowance = parseFloat($("#dailyAllowance").text());
	var totalSalary = parseFloat($("#totalSalary").text());
	
	if(totalSalary<=0.0){
		alert("Salary is Zero.. Please Evaluate");
		return false;
	} 
	$(".DaTotalAmountValue").each(function(){
		if($(this).text()!="" && dailyAllowance==0)
			{
				alert("Please Calculate the DA - Details..");
				return false;
			}
		else
			{
			} 
		}); 
	saveSalaryDetail(associateSalaryDetailsform);
}
function saveSalaryDetail(form) {
	$('#saveSalaryDetails').attr('disabled', 'disabled');
	var fromValue = $('#fromValue').text();
	if(fromValue=='addassocsalarydetails'){
		form.action = "addAssocSalaryDetails.do?from=addassocsalarydetails";
	}else{
		form.action = "addAssocSalaryDetails.do?from=updateassocsalarydetails";
	}
	
	form.submit(); 
	resetUIPage();
}




function validateSalaryDataForm(){ 
	var totalSalary = parseFloat($("#salary").val());
	console.log('validateSalaryDataForm : Salary : '+totalSalary);
	if(totalSalary<=0.0){
		alert("Salary is Zero.. Please Evaluate");
		return false;
	} 
	saveSalaryData(getSalaryDataForm);
}
function saveSalaryData(form) {
	$('#saveSalaryDetails').attr('disabled', 'disabled');
	var fromValue = $('#fromValue').text();
	if(fromValue=='addassocsalarydata'){
		form.action = "addAssocSalaryData.do?from=addassocsalarydata";
	}else{
		form.action = "addAssocSalaryData.do?from=updateassocsalarydata";
	}
	
	form.submit();  
}

function calculateTotalDA(){
	var daAmount=0;  
	
	$(".DaTotalAmountValue").each(function(){
		if($(this).val()!="")
			{
			daAmount+= parseFloat($(this).val());
			}
		else
			{
			
			}
		console.log('TEST : daAmount : '+daAmount);
		}); 
	
	console.log('daAmount : '+daAmount);
	$("#dailyAllowance").val(daAmount);
	//$("#dailyAllowance").text(daAmount);
	
}
function calculateTotalSalary(){
	
	var dailyAllowance =parseFloat($("#dailyAllowance").val());
	var baseSalary =parseFloat($("#baseSalary").val());
	var leaveTolerance =2;
	/*
	var dailyAllowance =parseFloat($("#dailyAllowance").text());
	var baseSalary =parseFloat($("#baseSalary").text());
	*/
	
	var leaveCount =parseFloat($("#leaveCount").val());
	var noOfDaysWorked = parseFloat($("#noOfDaysWorked").val()); 
	
	var noOfDaysInCurrentMonth = parseFloat($("#noOfDaysInCurrentMonth").text());
	
	/*
	var data = ($("#noOfWorkingDays").text()).split(":");
	var noOfWorkingDays =parseInt(data[1].trim());
	*/ 
	
	var noOfWorkingDays =parseFloat($("#noOfWorkingDays").text().trim());
	
	if(noOfDaysInCurrentMonth<(noOfDaysWorked+leaveCount)){
		alert('noOfWorkingDays<(noOfDaysWorked+leaveCount) .. Please Check');
		return false;
	}
	
	if(leaveCount==0 && noOfDaysWorked==noOfWorkingDays){
		
		totalSalary = baseSalary+((baseSalary/noOfDaysInCurrentMonth)*(2))+dailyAllowance;
		$("#totalSalary").val(Math.ceil(totalSalary));
		//$("#totalSalary").text(totalSalary);
		return;
	}
	if(leaveCount==0 && noOfDaysWorked>noOfWorkingDays){
		var extraDaysWorked = noOfDaysWorked-noOfWorkingDays;
		totalSalary = baseSalary+((baseSalary/noOfDaysInCurrentMonth)*(2+extraDaysWorked))+dailyAllowance;
		$("#totalSalary").val(Math.ceil(totalSalary));
		return;
	}else if (leaveCount>0 && noOfDaysWorked>noOfWorkingDays)
	{
		//var extraDaysWorked = noOfDaysWorked-noOfWorkingDays-leaveCount;
		//totalSalary = baseSalary+((baseSalary/noOfDaysInCurrentMonth)*(extraDaysWorked))+dailyAllowance;
		totalSalary = ((baseSalary/noOfDaysInCurrentMonth)*(noOfDaysWorked))+dailyAllowance;
		$("#totalSalary").val(Math.ceil(totalSalary));
		return;
	}else if (leaveCount>0 && noOfDaysWorked<noOfWorkingDays)
	{
		//totalSalary = ((baseSalary/noOfDaysInCurrentMonth)*(noOfDaysInCurrentMonth-leaveCount))+dailyAllowance;
		if(leaveCount <= leaveTolerance){
			console.log("Leaves to Encash :"+leaveTolerance-leaveCount);
			totalSalary = baseSalary+((baseSalary/noOfDaysInCurrentMonth)*(leaveTolerance-leaveCount))+dailyAllowance;
			// $("#totalSalary").val(Math.ceil(totalSalary));	
			
		}else{
			totalSalary =baseSalary-((baseSalary/noOfDaysInCurrentMonth)*(leaveCount-leaveTolerance))+dailyAllowance;
			//totalSalary = ((baseSalary/noOfDaysInCurrentMonth)*(noOfDaysWorked))+dailyAllowance;
				 
		} 
		$("#totalSalary").val(Math.ceil(totalSalary));
		return;
	}else if(leaveCount>0 && noOfDaysWorked==noOfWorkingDays)
	{
		totalSalary = baseSalary+dailyAllowance;
		$("#totalSalary").val(Math.ceil(totalSalary));
		//$("#totalSalary").text(totalSalary);
		return;
	}else{
		totalSalary = baseSalary+dailyAllowance;
		$("#totalSalary").val(Math.ceil(totalSalary));
		return;
	}  
}
function updateDaTotalAmount(tagId){ 
	var perDayDAAmount = parseInt($("#daperday"+tagId).val());
	var noOfDays = parseInt($("#noOfDays"+tagId).val());
	var tagDetails = "#amount"+tagId;
	
	var totalDAAmount = perDayDAAmount*noOfDays;
	$(tagDetails).text(totalDAAmount+"");
	$(tagDetails).val(totalDAAmount+"");
	
	
}
function updateAssociateSalary(baseSalary){
	$('#baseSalary').val(baseSalary);
	//$('#baseSalary').text(baseSalary);
	
}


function getAssociateBaseSalary(){
	//var responseData = {categories:['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],soldData:[7.0, 6.9, 9.5, 14.5, 18.4, 21.5], committedData:[3.9, 4.2, 5.7, 8.5, 11.9, 15.2]}; 
	var responseData =null;
	var eid = $('#eid').val();
	var monthyearDetails = $('#currentMonthYear').val();
	console.log("eid : "+eid);
	$(function() {
		
		$.ajax({
			url : 'getAssociateBaseSalary.do',
			type : 'GET',
			data : 'eid='+eid+'&monthYear='+monthyearDetails,
			success : function(data) {
				responseData = data.baseSalary;
				// responseData = JSON.parse(data);
				console.log("responseData : " + responseData);
				console
						.log("Success , getAssociateBaseSalary.do is Called !!!");
				$("#baseSalary").val(responseData);
				//$("#baseSalary").text(responseData);
				
			},
			error : function(e) {
				// called when there is an error
				console.log(e);
				console.log(e.message);
				console
						.log("Failure , getAssociateBaseSalary.do is Called !!!");
			}
		}); 
		
		
	});
}

function fetchTotalWorkingDays(){
	var responseData =null;
	var endDate = $("#endDate").val(); 
	var startDate = $("#startDate").val(); 
	console.log('endDate : '+endDate);
	console.log('startDate : '+startDate);
	
	$(function() {
		$(document).ajaxStart(function() {
			$("#AjaxLoadingInfo").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#AjaxLoadingInfo").css("display", "none");
		});

		$
				.ajax({
					url : 'fetchTotalWorkingDays.do',
					type : 'GET',
					data : 'endDate=' + jobcardNumber+'startDate='+startDate,
					success : function(data) {
						responseData = data.noOfWorkingDays;
						// responseData = JSON.parse(data);
						console.log("responseData : " + responseData);
						console.log("responseData isValid : "
								+ responseData.isValid);
						console.log("responseData message : "
								+ responseData.message); 
						console
								.log("Success , validateJobcardAvailability.do is Called !!!");
						$("#chasisNumber").val(responseData.placeHolderString);
						$("#validityCheckId").text(responseData.message);
						if (responseData.isValid==false){
							$("#saveFeedback").prop('disabled', true);
							
						}else{
							$("#saveFeedback").prop('disabled', false);
						};
					},
					error : function(e) {
						// called when there is an error
						console.log(e);
						console.log(e.message);
						console
								.log("Failure , validateJobcardAvailability.do is Called !!!");
					}
				});

	});
}


var url = "";
function getCustomerReport(queryType) {
	$('#savereport').attr('disabled', true);
	$('#AjaxLoadingInfo').show();
	$('#runreport').hide();
	$('#printreport').hide();
	$('#savereport').hide();
	disableButton();
	getSalaryDetailsByDuration();
}

function getCompleteSalaryReport() {
	$('#savereport').attr('disabled', true);
	$('#AjaxLoadingInfo').show();
	$('#runreport').hide();
	$('#printreport').hide();
	$('#savereport').hide();
	disableButton();
	getCompleteSalaryReport();
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
function getCompleteSalaryReport(){

	//var responseData = {categories:['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],soldData:[7.0, 6.9, 9.5, 14.5, 18.4, 21.5], committedData:[3.9, 4.2, 5.7, 8.5, 11.9, 15.2]}; 
	var responseData =null;
	var eid = $("#eid").val(); 
	var yearData = $("#yearData").val(); 
	var month = $("#month").val();
	
	
	console.log('eid : '+eid);
	console.log('yearData : '+yearData);
	console.log('month : '+month);	
	var columns = [//customerDetails
	       	    {id: "eid", name: "Associate Id", field: "eId"},
	       	    {id: "employeeName", name: "Associate Name", field: "employeeName"},
	       	    {id: "salary", name: "Salary", field: "salary"},
	       	    {id: "asOnDate", name: "As On Date", field: "asOnDate"}
	       	  ];
	 var options = {
			    enableCellNavigation: true,
			    enableColumnReorder: false
			  };
	$(function() {
		
		$.ajax({
			  url: 'getCompleteSalaryDatas.do',
			  type: 'GET',
			  data: 'yearData='+yearData,
			  success: function(data) { 
				  responseData=data.dataList; 
				  //responseData = JSON.parse(data);
				  console.log("responseData : "+responseData);
				  console.log("Success , getCompleteSalaryReport.do is Called !!!"); 
				  	$('#AjaxLoadingInfo').hide();
				  	$('#runreport').show(); 
					$('#savereport').show();
					enableButton();
					console.log("dataForExport : "+JSON.stringify(responseData));
					$('#toStoreTableJsonData').text(JSON.stringify(responseData));
					if(data.noOfRecords>0)
						{
							$('#noOfRecords').text(data.noOfRecords);
						}else
							{
								$('#noOfRecords').text(data.noOfCustomers);
								$('#savereport').attr('disabled', 'disabled');
							}
					
					
					
				  grid = new Slick.Grid("#myGrid", responseData, columns, options);
			  },
			  error: function(e) {
				//called when there is an error
				  console.log(e);
				  console.log(e.message);
				  console.log("Failure , getCompleteSalaryReport.do is Called !!!");
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
function getSalaryDetailsByDuration(){
	//var responseData = {categories:['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],soldData:[7.0, 6.9, 9.5, 14.5, 18.4, 21.5], committedData:[3.9, 4.2, 5.7, 8.5, 11.9, 15.2]}; 
	var responseData =null;
	var eid = $("#eid").val(); 
	var yearData = $("#yearData").val(); 
	var month = $("#month").val();
	
	
	console.log('eid : '+eid);
	console.log('yearData : '+yearData);
	console.log('month : '+month);	
	var columns = [//customerDetails
	       	    {id: "eid", name: "Associate Id", field: "eid"},
	       	    {id: "assocName", name: "Associate Name", field: "assocName"},
	       	    {id: "baseSalary", name: "Basic Pay", field: "baseSalary"},
	       	    {id: "leaveCount", name: "Leave Count", field: "leaveCount"},
	       	    {id: "noOfDaysWorked", name: "No Of Days Worked", field: "noOfDaysWorked"},
	       	    {id: "dailyAllowance", name: "Daily Allowance", field: "dailyAllowance"},
	       	    {id: "daDetailsString", name: "Daily Allowance Data", field: "daDetailsString"},
	    	    {id: "totalSalary", name: "Total Salary", field: "totalSalary"},
	    	    {id: "salaryDate", name: "Salary Date", field: "salaryDate"}
	       	  ];
	 var options = {
			    enableCellNavigation: true,
			    enableColumnReorder: false
			  };
	$(function() {
		
		$.ajax({
			  url: 'getSalaryDatas.do',
			  type: 'GET',
			  data: 'queryName='+eid+'&yearData='+yearData+'&monthData='+month+'&diffData=TALUK',
			  success: function(data) { 
				  responseData=data.dataList; 
				  //responseData = JSON.parse(data);
				  console.log("responseData : "+responseData);
				  console.log("Success , getSalaryDatas.do is Called !!!"); 
				  	$('#AjaxLoadingInfo').hide();
				  	$('#runreport').show(); 
					$('#savereport').show();
					enableButton();
					console.log("dataForExport : "+JSON.stringify(responseData));
					$('#toStoreTableJsonData').text(JSON.stringify(responseData));
					if(data.noOfRecords>0)
						{
							$('#noOfRecords').text(data.noOfRecords);
						}else
							{
								$('#noOfRecords').text(data.noOfCustomers);
								$('#savereport').attr('disabled', 'disabled');
							}
					
					
					
				  grid = new Slick.Grid("#myGrid", responseData, columns, options);
			  },
			  error: function(e) {
				//called when there is an error
				  console.log(e);
				  console.log(e.message);
				  console.log("Failure , getSalaryDatas.do is Called !!!");
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
        
function downloadData() {
    var json = $("#toStoreTableJsonData").text();
	
	console.log('toStoreTableJsonData : '+json); 
	  
    var csv = JSON2CSV(json);
    window.open("data:text/csv;charset=utf-8," + escape(csv));
}




