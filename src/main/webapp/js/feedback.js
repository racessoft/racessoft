/**
 * 
 */

function validateFeedbackForm(id) {
	var jobcardNumber = $("#jobcardNumber").val(); 
	var chasisNumber = $("#chasisNumber").val();   
	
	if (!jobcardNumber) {
		alert("Enter spare Code ");
		return false;
	} else if ($("#qualityOfWork").val() > 5 ) {
		alert("qualityOfWork cannot be more than 5");
		return false;
	} else if ($("#cleaningOfVehicle").val() > 5 ) {
		alert("cleaningOfVehicle cannot be more than 5");
		return false;
	} else if ($("#timelyCompletion").val() > 5 ) {
		alert("timelyCompletion cannot be more than 5");
		return false;
	} else if ($("#overallRating").val() > 5 ) {
		alert("overallRating cannot be more than 5");
		return false;
	}
	else if (!chasisNumber) {
		alert("Enter chasisNumber");
		return false;
	}else if(id == 'saveFeedback') {
		saveFeedbackDetail(feedbackDetailsform);
	} else if(id == 'updateFeedback') {
		var value = confirmUpdate('Do you want to continue with Feedback detail update ?');
		return value;
	}
}

function loadFeedBackDateComponents() {
	$("#accordion").accordion({
		animated : "bounceslide"
	});
	
	$("#dateOfFeedback").datepicker({
		showOn : "button",
		buttonImage : "images/calendar.gif",
		buttonImageOnly : true,
		dateFormat : 'yy-mm-dd',
		changeMonth : true,
		changeYear : true
	});
	$('#servicestarttime').datetimepicker();
	$('#serviceendtime').datetimepicker();
	 
	
}


function saveFeedbackDetail(form) {
	$('#saveFeedback').attr('disabled', 'disabled');
	form.action = "addfeedBackDetails.do";
	form.submit();
}
function getGridData(){
	var grid;
	//var employeeDetails = $("#employeeDetails").val(); 
	
	// console.log('slickgrid object !!! : '+object);
	// console.log('slickgrid object !!! : '+theData);
	// var json = JSON.stringify(eval("(" + employeeDetails + ")"));
	// console.log('slickgrid json !!! : '+json);
	 
	  var columns = [
	    {id: "feedbackid", name: "FeedbackId", field: "feedbackid"},
	    {id: "remark", name: "Customer Remark", field: "remark"},
	    {id: "eid", name: "Engineer Id", field: "eid"},
	    {id: "issueStatus", name: "Issue Status", field: "issueStatus"}
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
function loadFeedbackDateComponents() {
	$("#accordion").accordion({
		animated : "bounceslide"
	});

/*	$(document).ready(function() {
		$("#dateOfFeedback").datepicker({
			showOn : "button",
			buttonImage : "images/calendar.gif",
			buttonImageOnly : true,
			dateFormat : 'yy-mm-dd',
			changeMonth : true,
			changeYear : true
		});


	});
*/
}
function feedbackDateChange(element){ 
	$(function() {
		
		//var closureDate = $('#closureDate').val();//2015-12-29
		var validateDate =element.value;
		var fieldName =element.id;
		var inputVal = document.getElementById(fieldName);
		 var regPattern = /^(19|20)\d\d(-)(0[1-9]|1[012])(-)(0[1-9]|[12][0-9]|3[01])$/;
         var checkArray = validateDate.match(regPattern);
         if (checkArray == null){
        	 	if(fieldName=='dateOfFeedback')
        	 		{
        	 		alert('SORRY !!! Please Enter Valid Date Of Feedback');
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
function feedbackDateTimeChange(element){ 
	$(function() {
		
		//var closureDate = $('#closureDate').val();//2015-12-29
		var validateDate =element.value;//02/24/2016 08:24
		var fieldName =element.id;
		var inputVal = document.getElementById(fieldName);
		// var regPattern = '^\d{4}\/\d{2}\/\d{2} \d{2}:\d{2}$';
         
		 //var regPattern = '^\d{2}\/\d{2}\/\d{4} \d{2}:\d{2}$';
		var regPattern = /^\d{2}\/\d{2}\/\d{4} \d{2}:\d{2}$/;
		var checkArray = validateDate.match(regPattern);
         if (checkArray == null){
        	 	if(fieldName=='servicestarttime')
        	 		{
        	 		alert('SORRY !!! Please Enter Valid Service Start Time');
        	 		inputVal.style.backgroundColor = "yellow";
        	 		inputVal.focus();
                    //return false;
        	 		}
        	 	else if(fieldName=='serviceendtime')
    	 		{
    	 		alert('SORRY !!! Please Enter Valid Service End Time');
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
 			//feedbackTimeRangesChange();
 			inputVal.style.backgroundColor = "white";
 			} 
	});
	
}
function feedbackTimeRangesChange(){ 
	$(function() {
		
		var startDateTextBox = $('#servicestarttime');
		var endDateTextBox = $('#serviceendtime');
		$.timepicker.datetimeRange(
				startDateTextBox,
				endDateTextBox,
				{
					minInterval: (1000*60*60), // 1hr
					dateFormat: 'dd M yy', 
					timeFormat: 'HH:mm',
					start: {}, // start picker options
					end: {} // end picker options					
				}
			);
		
	});
	
}
function dateTimeValidation(d) {
    return !!new Date(d).getTime();
}

function formatter(row, cell, value, columnDef, dataContext) {
    return value;
}


function validateJobCard(){
	var responseData =null;
	var jobcardNumber = $("#jobcardNumber").val(); 
	console.log('jobcardNumber : '+jobcardNumber);
	$(function() {
		$(document).ajaxStart(function() {
			$("#AjaxLoadingInfo").css("display", "block");
		});
		$(document).ajaxComplete(function() {
			$("#AjaxLoadingInfo").css("display", "none");
		});

		$
				.ajax({
					url : 'validateJobcardAvailability.do',
					type : 'GET',
					data : 'jobcardNumber=' + jobcardNumber,
					success : function(data) {
						responseData = data.dataList;
						// responseData = JSON.parse(data);
						console.log("responseData : " + responseData);
						console.log("responseData isValid : "
								+ responseData.isValid);
						console.log("responseData message : "
								+ responseData.message);
						console.log("responseData message : "
								+ responseData.placeHolderString);
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

function getFeedbackTabulationDatas(){
	//var responseData = {categories:['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],soldData:[7.0, 6.9, 9.5, 14.5, 18.4, 21.5], committedData:[3.9, 4.2, 5.7, 8.5, 11.9, 15.2]}; 
	var responseData =null;
	var jobcardNumber = $("#jobcardNumberForAjax").val(); 
	console.log('jobcardNumber : '+jobcardNumber);
	var columns = [
	       	    {id: "feedbackId", name: "FeedbackId", field: "feedbackId",cssClass: "cell-title",formatter: formatter},
	       	    {id: "remark", name: "Customer Remark", field: "remark"},
	       	    {id: "eid", name: "Engineer Id", field: "eid"},
	       	    {id: "issueStatus", name: "Issue Status", field: "issueStatus"}
	       	  ];
	 var options = {
			    enableCellNavigation: true,
			    enableColumnReorder: false
			  };
	$(function() {
		
		$.ajax({
			  url: 'getFeedBackDatas.do',
			  type: 'GET',
			  data: 'jobcardNumber='+jobcardNumber,
			  success: function(data) { 
				  responseData=data.dataList;
				  //responseData = JSON.parse(data);
				  console.log("responseData : "+responseData);
				  console.log("Success , getFeedBackDatas.do is Called !!!"); 
				  grid = new Slick.Grid("#myGrid", responseData, columns, options);
			  },
			  error: function(e) {
				//called when there is an error
				  console.log(e);
				  console.log(e.message);
				  console.log("Failure , getFeedBackDatas.do is Called !!!");
			  }
			}); 
		
		
	});
}
