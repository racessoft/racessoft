/**
 * 
 */

function validateAssocForm(id) {
	var spareCode = $("#employeeId").val();
	var numberTest = "/^d[0-9]+/";
	if (!spareCode) {
		alert("Enter Employee Id ");
		return false;
	} else if (!$("#employeeName").val()) {
		alert("Enter Employee Name");
		return false;
	} else if (!$("#dateOfJoin").val()) {
		alert("Enter Date Of Join");
		return false;
	}else if (!$("#mobileNo").val()) {
		alert("Enter Mobile No");
		return false;
	}else if (!$("#address").val()) {
		alert("Enter Address Details");
		return false;
	}else if(id == 'saveAssoc') {
		saveSparesDetail(assocform);
	} else if(id == 'updateAssoc') {
		var value = confirmUpdate('Do you want to continue with Assoc detail update ?');
		return value;
	}
}
function dateOfJoinChange(element){ 
	$(function() {
		
		//var closureDate = $('#closureDate').val();//2015-12-29
		var validateDate =element.value;
		var fieldName =element.id;
		var inputVal = document.getElementById(fieldName);
		 var regPattern = /^(19|20)\d\d(-)(0[1-9]|1[012])(-)(0[1-9]|[12][0-9]|3[01])$/;
         var checkArray = validateDate.match(regPattern);
         if (checkArray == null){
        	 	if(fieldName=='dateOfJoin')
        	 		{
        	 		alert('SORRY !!! Please Enter Valid Date Of Join');
        	 		inputVal.style.backgroundColor = "yellow";
        	 		inputVal.focus();
                    //return false;
        	 		}
         }else
			{ 
 			console.log('DateChange Called !!! : '+validateDate); 
 			inputVal.style.backgroundColor = "white";
 			} 
	});
	
}
function saveSparesDetail(form) {
	$('#saveAssoc').attr('disabled', 'disabled');
	form.action = "addAssocDetails.do";
	form.submit();
}



function checkEmployeeIdAvailability(){
	var responseData =null;
	var eid = $('#employeeId').val();
	console.log("eid : "+eid);
	$(function() {
		
		$.ajax({
			url : 'checkEmployeeIdAvailability.do',
			type : 'GET',
			data : 'eid='+eid,
			success : function(data) {
				responseData = data.availability;
				// responseData = JSON.parse(data);
				console.log("responseData : " + responseData);
				console
						.log("Success , checkEmployeeIdAvailability.do is Called !!!");
				$("#availabilityMessage").val(responseData);
				$("#availabilityMessage").text(responseData);
				
				if(responseData=="Change the Employee Id"){
					$("#saveAssoc").prop("disabled",true);
				}else
					{
						$("#saveAssoc").prop("disabled",false);
					}
				
				//$("#baseSalary").text(responseData);
				
			},
			error : function(e) {
				// called when there is an error
				console.log(e);
				console.log(e.message);
				console
						.log("Failure , checkEmployeeIdAvailability.do is Called !!!");
			}
		}); 
		
		
	});
}

function plotAssociateAvailability(){
	$('#runreport').attr('disabled', 'disabled');
	var responseData =null;
	var eid = $("#eid").val(); 
	var yearData = $("#yearData").val(); 
	var month = "0";
	
	
	console.log('eid : '+eid);
	console.log('yearData : '+yearData);
	console.log('month : '+month);
	
	$.ajax({
		 url: 'getLeaveDatas.do',
		  type: 'GET',
		  data: 'queryName='+eid+'&yearData='+yearData+'&monthData='+month+'&diffData=TALUK',
		 // data: 'twitterUsername=jquery4u',
		  success: function(data) {
			  responseData=data;
			  //responseData = JSON.parse(data);
			  console.log("responseData : "+responseData);
			  console.log("Success , getLeaveDatas.do is Called !!!");
			  $('#AjaxLoadingInfo').hide();
			  $('#runreport').removeAttr("disabled");
			  $('#myGrid').highcharts({
			        chart: {
			            type: 'line'
			        },
			        title: {
			            text: 'Associate Availability Trend'
			        },
			        subtitle: {
			            text: 'Source: RACES'
			        },
			        xAxis: {
			         //   categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
			        	   categories: responseData.monthDetails
			        },
			        yAxis: {
			            title: {
			                text: 'No Of Days'
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
			            name: 'Leave Details',
			           // data: [8.0, 7.9, 9.5, 15.5, 19.4, 22.5, 26.2, 27.5, 24.3, 19.3, 14.9, 10.6],
			            data: responseData.noOfLeaves,
			            color: 'red'
			        },
			        {
			            name: 'WorkingDays Details',
			            data: responseData.noOfWorkingDays,
			            color: 'green'
			        }, {
			            name: 'NoOfDays In Month',
			            data: responseData.noOfDaysInMonth,
			            color: 'blue'
			        }, {
			            name: 'NoOfDays Worked',
			            data: responseData.noOfDaysWorked,
			            color: 'black'
			        }]
			    });
			  
		  },
		  error: function(e) {
				//called when there is an error
				  console.log(e);
				  console.log(e.message);
				  console.log("Failure , getLeaveDatas.do is Called !!!");
				  $('#runreport').removeAttr("disabled");
			  }
	});
	
   
};