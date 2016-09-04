<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<!-- <!doctype html> sashi just for testing
-->
<html>
<head>
<meta charset="utf8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Races</title>
<link rel="stylesheet" href="./css/style.css" />
<link rel="stylesheet" href="./css/styles.css" />
<link rel="stylesheet" href="./css/jquery-ui-1.10.0.css" />
<script type="text/javascript"  src="./js/jquery-1.4.2.js"></script>
<link rel="stylesheet" href="./css/jquery-ui.css" />
<script type="text/javascript"  src="./js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="./js/jquery-ui-1.9.2.js"></script>
<script type="text/javascript"  src="./js/jquery-ui-1.10.0.js"></script>
<script  type="text/javascript" >
	$(document).ready(function() {
		$('select').each(function() {
			$(this).trigger('change');

		});
	});
</script>
<script  type="text/javascript"  src="./js/login.js"></script>
</head>
<body style="background: #BABABA" onload="loadDateComponents();">
	<div>
		<div id="leftPanel">
			<div>
				<img src="images/images.jpg" width="100%" />
			</div>
			<div id="accordion">
				<h3>Job card details</h3>
				<div>
					<p></p>
					<ul>
						<li><a
							href="jobCardReport.do?navLink=leftPane&from=addJobCardReport">Add
								job card Detail</a></li>
						<li><a
							href="jobCardReport.do?navLink=leftPane&from=updateJobCardReport">Update
								job card Detail</a></li>
					</ul>
				</div>
				<h3>Customer details</h3>
				<div>
					<p></p>
					<ul>
						<li><a
							href="customerReport.do?from=addCustomer">Add
								Customer Detail</a>
						</li>
						<li><a
							href="customerReport.do?from=updateCustomer">Update
								Customer Detail</a>
						</li>
					</ul>
				</div>
				<h3>Spares details</h3>
				<div>
					<p></p>
					<ul>
						<li><a
							href="sparesReport.do?from=addSpares">Add
								Spares Detail</a>
						</li>
						<li><a
							href="sparesReport.do?from=updateSpares">Update
								Spares Detail</a>
						</li>
					</ul>
				</div>
				<h3>Associates details</h3>
				<div>
					<p></p>
					<ul>
						<li><a
							href="assocReport.do?from=addAssoc">Add
								Assoc Detail</a>
						</li>
						<li><a
							href="assocReport.do?from=updateAssoc">Update
								Assoc Detail</a>
						</li>
					</ul>
				</div>
				<h3>Service FeedBack details</h3>
				<div>
					<p></p>
					<ul>
						<li><a
							href="feedbackReport.do?from=addFeedBackReport">Add
								Feedback Detail</a>
						</li>
						<li><a
							href="feedbackReport.do?from=updateFeedBackReport">Update
								Feedback Detail</a>
						</li>
					</ul>
				</div>	
			</div>
		</div>
		<div id="wrap">
			<!--<div id="bar"></div>-->
			<div id="content">

				<div id="mainFull">
					<div id="topBar">
						<div class="success" style="float: left; padding-left: 10px;">Welcome
							${username}</div>
						<div style="float: right; padding-right: 3px;">
							<img src="images/Races.jpg">
						</div>
						<div style="float: right; padding-right: 5px;">
							<a href="logout.do" class="success">Logout</a>
						</div>
						<div style="float: right; padding-right: 5px;">
							<a href="homePage.do" class="success">Home</a>
						</div>
						<div id="titleDiv"
							style="text-align: center; width: 100%; height: 25px; line-height: 45px;">
							<span class="TabHead">Races Report</span>
						</div>
					</div>
					<div>
						<div></div>
						<form:form commandName="jobCardDetails"
							action="addJobcardReport.do" onsubmit="return validateForm();">
							<table style="width: 100%" border="0" cellspacing="3"
								cellpadding="3">

								<tr>
									<td width=""><div>
											<h3 style="padding-left: 10px;">JOBCARD DETAIL</h3>
										</div>
										<div id="jobcardDiv"
											style="border-radius: 6px 6px 6px 6px; overflow-y: auto; overflow-x: hidden; float: left; width: 705px; height: 430px; border: 1px solid #ccc;">

											<table class="jobcardContent"
												style="padding: 10px 0px 0px 10px;">

												<tr>
													<td>Job card Number</td>
													<td><form:input path="jobcardNumber"
															id="jobcardNumber" /></td>
													<td class="mandatory">*
														<div id="jobcardNumberValidation"></div>
													</td>
												</tr>
												<tr>
													<td>Chasis Number</td>
													<td><form:input path="chasisNumber" id="chasisNumber" />
													</td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Receipt Number</td>
													<td><form:input path="receiptNumber"
															id="receiptNumber" /></td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Type Of Service:</td>
													<td><form:select path="serviceType"
															id="serviceType">
															<c:forEach var="serviceTypes" items="${serviceNames}"
																varStatus="iter">
																<form:option value='${serviceTypes}'
																	title='${serviceTypes}'>
																${serviceTypes}</form:option>
															</c:forEach>
														</form:select></td>
													<td class="mandatory">* &nbsp; <form:checkbox path="cascadeData" disabled="disabled"/> Cascade Data &nbsp; </td>
												</tr>
												<tr>
													<td>Branch</td>
													<td><form:select path="branch"
															id="branch">
															<c:forEach var="branch1" items="${branches}"
																varStatus="iter">
																<form:option value='${branch1}'
																	title='${branch1}'>
																${branch1}</form:option>
															</c:forEach>
														</form:select></td>
													<%-- <form:input path="branch" id="branch" /> --%></td>
													<td></td>
												</tr>
												<tr>
													<td>Date of Complaint</td>
													<td><form:input style="margin-right:5px;"
															path="dateOfComplaint" id="dateOfComplaint" onchange="closureDateChange(this)"/></td>
													</td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td valign="top">Nature of Complaint</td>
													<td><form:textarea path="natureOfComplaint"
															id="natureOfComplaint" /></td>
													<td></td>
												</tr>
												<tr>
													<td valign="top">Action Taken</td>
													<td><form:textarea path="actionTaken" id="actionTaken" />
													</td>
													<td></td>
												</tr>
												<tr>
													<td>Hours</td>
													<td><form:input path="hours" id="hours" /></td>
													<td></td>
												</tr>
												<tr>
													<td>Closure Date</td>
													<td><form:input style="margin-right:5px;"
															path="closureDate" id="closureDate" onchange="closureDateChange(this)"/></td>
													<td></td>
												</tr>
												<tr>
													<td valign="top">Service Complaint</td>
													<td><form:textarea path="serviceComplaint"
															id="serviceComplaint" /></td>
													<td></td>
												</tr>
												<tr>
													<td>Labour Amount</td>
													<td><form:input path="labourAmount" id="labourAmount" />
													</td>
													<td></td>
												</tr>
												<tr>
													<td>Engineer Name</td>
													<td><form:select path="serviceEngineerCode"
															id="serviceEngineerCode">
															<c:forEach var="EngineerName" items="${EmployeeDetails}"
																varStatus="iter">
																<form:option value='${EngineerName.employeeId}'
																	title='${EngineerName.employeeName}'>
																${EngineerName.employeeName}</form:option>
															</c:forEach>
														</form:select></td>
													<td></td>
												</tr>

											</table>
											<h3 style="padding-left: 10px;">Spare Detail</h3>
											<table id="sparetable" class="jobcardContent"
												style="margin: 0px 0px 10px 10px;; font-size: 14px; border: 1px solid #BABABA;">
												<tr style="background-color: #E6E6E6;">
													<td>Spares code</td>
													<td>Cost of the spares</td>
													<td>Quantity</td>
													<td>Total cost</td>
												</tr>
												<%--  <c:forEach varStatus="spares" items="${spareDetails}"> --%>
												<tr id="0">
													<td><form:hidden path="from" id="from" /> <form:select
															path="spareDetails[0].spareCode" id="spare-select0"
															style="width:155px;border: 1px solid white"
															onchange="getSpareCost(this,cost0,quantity0,total0);">
															<%-- <form:options items="${durationDetails}" /> --%>
															<c:forEach var="spareCostDetail"
																items="${spareCostDetails}" varStatus="iteration">
																<form:option value='${spareCostDetail.key}'
																	title='${spareCostDetail.value}'>
																${spareCostDetail.key}</form:option>
															</c:forEach>
														</form:select></td>
													<td>
														<%-- <form:options items="${durationDetails}" /> --%> <form:input
															path="spareDetails[0].cost" id="cost0"
															style="border: 1px solid white" readonly="yes"
															onblur="getSpareCost1(cost0,quantity0,total0)" />
													</td>
													<td>
														<%-- <form:options items="${durationDetails}" /> --%> <form:input
															path="spareDetails[0].quantity" id="quantity0"
															style="border: 1px solid white"
															onblur="getSpareCost1(cost0,quantity0,total0)" />
													</td>
													<td><form:input path="spareDetails[0].total"
															id="total0" style="border: 1px solid white" /></td>
												</tr>
												<%-- </c:forEach> --%>
												<tr id="1" style="background: #E6E6E6">
													<td><form:select path="spareDetails[1].spareCode"
															id="spare-select1"
															style="width:155px;background-color:#E6E6E6;border: 1px solid white"
															onchange="getSpareCost(this,cost1,quantity1,total1);">
															<%-- <form:options items="${durationDetails}" /> --%>
															<c:forEach var="spareCostDetail"
																items="${spareCostDetails}" varStatus="iteration">
																<form:option value='${spareCostDetail.key}'
																	title='${spareCostDetail.value}'>
																${spareCostDetail.key}</form:option>
															</c:forEach>
														</form:select></td>
													<td>
														<%-- <form:options items="${durationDetails}" /> --%> <form:input
															path="spareDetails[1].cost"
															style="background-color:#E6E6E6;border: 1px solid white"
															id="cost1" readonly="yes"
															onblur="getSpareCost1(cost1,quantity1,total1)" />
													</td>
													<td>
														<%-- <form:options items="${durationDetails}" /> --%> <form:input
															path="spareDetails[1].quantity"
															style="background-color:#E6E6E6;border: 1px solid white"
															id="quantity1"
															onblur="getSpareCost1(cost1,quantity1,total1)" />
													</td>
													<td><form:input path="spareDetails[1].total"
															id="total1"
															style="background-color:#E6E6E6;border: 1px solid white" />
													</td>
												</tr>
												<%-- </c:forEach> --%>
												<tr id="2">
													<td><form:select path="spareDetails[2].spareCode"
															id="spare-select2"
															style="width:155px;border: 1px solid white"
															onchange="getSpareCost(this,cost2,quantity2,total2);">
															<%-- <form:options items="${durationDetails}" /> --%>
															<c:forEach var="spareCostDetail"
																items="${spareCostDetails}" varStatus="iteration">
																<form:option value='${spareCostDetail.key}'
																	title='${spareCostDetail.value}'>
																${spareCostDetail.key}</form:option>
															</c:forEach>
														</form:select></td>
													<td>
														<%-- <form:options items="${durationDetails}" /> --%> <form:input
															path="spareDetails[2].cost" id="cost2"
															style="border: 1px solid white" readonly="yes"
															onblur="getSpareCost1(cost2,quantity2,total2)" />
													</td>
													<td>
														<%-- <form:options items="${durationDetails}" /> --%> <form:input
															path="spareDetails[2].quantity"
															style="border: 1px solid white" id="quantity2"
															onblur="getSpareCost1(cost2,quantity2,total2)" />
													</td>
													<td><form:input path="spareDetails[2].total"
															id="total2" style="border: 1px solid white" /></td>
												</tr>
												<%-- </c:forEach> --%>
												<tr id="3" style="background-color: #E6E6E6;">
													<td><form:select path="spareDetails[3].spareCode"
															id="spare-select3"
															style="width:155px;background-color:#E6E6E6;border: 1px solid white"
															onchange="getSpareCost(this,cost3,quantity3,total3);">
															<%-- <form:options items="${durationDetails}" /> --%>
															<c:forEach var="spareCostDetail"
																items="${spareCostDetails}" varStatus="iteration">
																<form:option value='${spareCostDetail.key}'
																	title='${spareCostDetail.value}'>
																${spareCostDetail.key}</form:option>
															</c:forEach>
														</form:select></td>
													<td>
														<%-- <form:options items="${durationDetails}" /> --%> <form:input
															path="spareDetails[3].cost"
															style="background-color:#E6E6E6;border: 1px solid white"
															id="cost3" onblur="getSpareCost1(cost3,quantity3,total3)"
															readonly="yes" />
													</td>
													<td>
														<%-- <form:options items="${durationDetails}" /> --%> <form:input
															path="spareDetails[3].quantity"
															style="background-color:#E6E6E6;border: 1px solid white"
															id="quantity3"
															onblur="getSpareCost1(cost3,quantity3,total3)" />
													</td>
													<td><form:input path="spareDetails[3].total"
															id="total3"
															style="background-color:#E6E6E6;border: 1px solid white" />
													</td>
												</tr>
												<%-- </c:forEach> --%>
												<tr id="4">
													<td><form:select path="spareDetails[4].spareCode"
															id="spare-select4"
															style="width:155px;border: 1px solid white"
															onchange="getSpareCost(this,cost4,quantity4,total4);">
															<%-- <form:options items="${durationDetails}" /> --%>
															<c:forEach var="spareCostDetail"
																items="${spareCostDetails}" varStatus="iteration">
																<form:option value='${spareCostDetail.key}'
																	title='${spareCostDetail.value}'>
																${spareCostDetail.key}</form:option>
															</c:forEach>
														</form:select></td>
													<td>
														<%-- <form:options items="${durationDetails}" /> --%> <form:input
															path="spareDetails[4].cost" id="cost4"
															style="border: 1px solid white" readonly="yes"
															onblur="getSpareCost1(cost4,quantity4,total4)" />
													</td>
													<td>
														<%-- <form:options items="${durationDetails}" /> --%> <form:input
															path="spareDetails[4].quantity" id="quantity4"
															style="border: 1px solid white"
															onblur="getSpareCost1(cost4,quantity4,total4)" />
													</td>
													<td><form:input path="spareDetails[4].total"
															id="total4" style="border: 1px solid white" /></td>
												</tr>
												<tr id="5">
													<td><form:select path="spareDetails[5].spareCode"
															id="spare-select5"
															style="width:155px;border: 1px solid white"
															onchange="getSpareCost(this,cost5,quantity5,total5);">
															<%-- <form:options items="${durationDetails}" /> --%>
															<c:forEach var="spareCostDetail"
																items="${spareCostDetails}" varStatus="iteration">
																<form:option value='${spareCostDetail.key}'
																	title='${spareCostDetail.value}'>
																${spareCostDetail.key}</form:option>
															</c:forEach>
														</form:select></td>
													<td>
														<%-- <form:options items="${durationDetails}" /> --%> <form:input
															path="spareDetails[5].cost" id="cost5"
															style="border: 1px solid white" readonly="yes"
															onblur="getSpareCost1(cost5,quantity5,total5)" />
													</td>
													<td>
														<%-- <form:options items="${durationDetails}" /> --%> <form:input
															path="spareDetails[5].quantity" id="quantity5"
															style="border: 1px solid white"
															onblur="getSpareCost1(cost5,quantity5,total5)" />
													</td>
													<td><form:input path="spareDetails[5].total"
															id="total5" style="border: 1px solid white" /></td>
												</tr>
												<tr id="6">
													<td><form:select path="spareDetails[6].spareCode"
															id="spare-select6"
															style="width:155px;border: 1px solid white"
															onchange="getSpareCost(this,cost6,quantity6,total6);">
															<%-- <form:options items="${durationDetails}" /> --%>
															<c:forEach var="spareCostDetail"
																items="${spareCostDetails}" varStatus="iteration">
																<form:option value='${spareCostDetail.key}'
																	title='${spareCostDetail.value}'>
																${spareCostDetail.key}</form:option>
															</c:forEach>
														</form:select></td>
													<td>
														<%-- <form:options items="${durationDetails}" /> --%> <form:input
															path="spareDetails[6].cost" id="cost6"
															style="border: 1px solid white" readonly="yes"
															onblur="getSpareCost1(cost6,quantity6,total6)" />
													</td>
													<td>
														<%-- <form:options items="${durationDetails}" /> --%> <form:input
															path="spareDetails[6].quantity" id="quantity6"
															style="border: 1px solid white"
															onblur="getSpareCost1(cost6,quantity6,total6)" />
													</td>
													<td><form:input path="spareDetails[6].total"
															id="total6" style="border: 1px solid white" /></td>
												</tr>
												<tr id="7">
													<td><form:select path="spareDetails[7].spareCode"
															id="spare-select7"
															style="width:155px;border: 1px solid white"
															onchange="getSpareCost(this,cost7,quantity7,total7);">
															<%-- <form:options items="${durationDetails}" /> --%>
															<c:forEach var="spareCostDetail"
																items="${spareCostDetails}" varStatus="iteration">
																<form:option value='${spareCostDetail.key}'
																	title='${spareCostDetail.value}'>
																${spareCostDetail.key}</form:option>
															</c:forEach>
														</form:select></td>
													<td>
														<%-- <form:options items="${durationDetails}" /> --%> <form:input
															path="spareDetails[7].cost" id="cost7"
															style="border: 1px solid white" readonly="yes"
															onblur="getSpareCost1(cost7,quantity7,total7)" />
													</td>
													<td>
														<%-- <form:options items="${durationDetails}" /> --%> <form:input
															path="spareDetails[7].quantity" id="quantity7"
															style="border: 1px solid white"
															onblur="getSpareCost1(cost7,quantity7,total7)" />
													</td>
													<td><form:input path="spareDetails[7].total"
															id="total7" style="border: 1px solid white" /></td>
												</tr>
												<%-- </c:forEach> --%>
											</table>
										</div>
								<tr>
									<td colspan="2">
										<div style='clear: both; padding-top: 2px;'>
											<div class="jobcardContent"
												style="float: left; padding-left: 360px">Total Spare
												Cost:</div>
											<div style="float: left;">
												<div>
													<form:input path="totalSpareCost" id="spareCost" />
												</div>
											</div>
										</div>
									</td>
								</tr>
							</table>

							<div style='float: left; padding: 2px 0px 0px 5px;'>
								<div style="float: left">
									<input type='button' value='Add Row ' id='addButton'
										onclick="addSpareDetail();">
								</div>
								<div style='float: left;'>
									<input type='submit' value='Save Jobcard Detail' id='saveJobcardButton' />
								</div>
								<div style='float: left;padding-left:10px;'>
									<a href='jobCardReport.do?navLink=leftPane&from=addJobCardReport' style="text-decoration: underline; font-weight:bold;" id="addjobcard">Add jobcard</a>
								</div>
								<c:choose>
									<c:when
										test="${empty successmessage || successmessage == 'null'}">
										<div style='float: left; padding: 10px;'>
											<a
												href='addJobCard.do?navLink=leftPane&from=addJobCardReport'
												style="text-decoration: underline; font-weight: bold;pointer-events: none;;padding-top: 1px;padding-left:5px;"
												id="addjobcard">Update FeedBack</a>
										</div>
									</c:when>
									<c:otherwise>
										<div style='float: left; padding: 10px;'>
											<a
												href='addJobCard.do?navLink=leftPane&from=addJobCardReport'
												style="text-decoration: underline; font-weight: bold;padding-top: 1px;padding-left:5px;"
												id="addjobcard">Update FeedBack</a>
										</div>
									</c:otherwise>
								</c:choose>
							</div>
							<div>
								<div class="jobcardContent"
									style="float: left; padding-left: 20px;">Total Cost:</div>
								<div style="float: left;">
									<div>
										<form:input path="overallCost" id="overallCost" />
									</div>
								</div>
								<div>
									<input type='button' value='calculate' id='calculateButton'
										onclick="calculateTotal()" />
								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
			<!--end mainFull -->


			<div id="footer-wrap">
				<div id="footer">
					<address>
						&copy;
						<%=new java.util.Date().getYear() + 1900%>
						Powered by Races
					</address>
				</div>
				<!-- end footer -->
			</div>
			<!-- end footer-wrap -->


		</div>
		<!-- end content-->
	</div>
</body>
</html>
