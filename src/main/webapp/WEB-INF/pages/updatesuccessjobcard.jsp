<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<!-- <!doctype html> -->
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
<script type="text/javascript"  src="./js/login.js"></script>
<script type="text/javascript" >
$(document).ready(function() { 

	    $('select').each(function() {
	    	$(this).trigger('change');
	    	
	    });
	});
</script>
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
						<c:choose>
							<c:when
								test="${empty successmessage || successmessage == 'null'}">
								<div
									style="text-align: center; padding-left: 200px; padding-top: 10px"
									class="mandatory">${failuremessage}</div>
							</c:when>
							<c:otherwise>
								<div class="success"
									style="text-align: center; padding-left: 250px; padding-top: 10px">${successmessage}</div>
							</c:otherwise>
						</c:choose>
						<form:form commandName="jobCardDetails"
							action="updateJobcardReport.do" return="confirmUpdate('Do you want to continue with jobcard detail update ?')">
							<table style="width: 100%" border="0" cellspacing="10"
								cellpadding="10">

								<tr>
									<td width="">
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
													<td class="mandatory">* &nbsp; <form:checkbox path="cascadeData" id="cascadeData"/> Cascade Data &nbsp; </td>
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
											<form:hidden path="from" id="from" />
											<h3 style="padding-left: 10px;">Spare Detail</h3>
											<table id="sparetable" class="jobcardContent"
												style="margin: 0px 0px 10px 10px;; font-size: 14px; border: 1px solid #BABABA;">
												<tr style="background-color: #E6E6E6;">
													<td>Spares code</td>
													<td>Cost of the spares</td>
													<td>Quantity</td>
													<td>Total cost</td>
												</tr>
												<c:forEach items="${spareDetail}" varStatus="iteration"
													var="spare">
													<c:choose>
														<c:when test="${iteration.index % 2 ==  0}">
															<tr id="${iteration.index}">
																<td><form:select
																		path="spareDetails[${iteration.index}].spareCode"
																		id="spare-select${iteration.index}"
																		style="width:155px;border:1px solid white;"
																		onchange="getSpareCost(this,cost${iteration.index},quantity${iteration.index},total${iteration.index});">
																		<%-- <form:options items="${durationDetails}" /> --%>
																		<c:forEach var="spareCostDetail"
																			items="${spareCostDetails}" varStatus="iter">
																			<form:option value='${spareCostDetail.key}'
																				title='${spareCostDetail.value}'>
																${spareCostDetail.key}</form:option>
																		</c:forEach>
																	</form:select></td>
																<td><form:input
																		path="spareDetails[${iteration.index}].cost"
																		id="cost${iteration.index}" readonly="yes"
																		style="border:1px solid white;" /></td>
																<td><form:input
																		path="spareDetails[${iteration.index}].quantity"
																		id="quantity${iteration.index}"
																		style="border:1px solid white;" /></td>
																<td><form:input
																		path="spareDetails[${iteration.index}].total"
																		id="total${iteration.index}"
																		style="border:1px solid white;" /></td>
															</tr>
														</c:when>
														<c:otherwise>
															<tr id="${iteration.index}"
																style="background-color: #E6E6E6; border: 1px solid white;">
																<td><form:select
																		path="spareDetails[${iteration.index}].spareCode"
																		id="spare-select${iteration.index}"
																		style="border:1px solid white;width:155px;background-color:#E6E6E6;"
																		onchange="getSpareCost(this,cost${iteration.index},quantity${iteration.index},total${iteration.index});">
																		<%-- <form:options items="${durationDetails}" /> --%>
																		<c:forEach var="spareCostDetail"
																			items="${spareCostDetails}" varStatus="iter">
																			<form:option value='${spareCostDetail.key}'
																				title='${spareCostDetail.value}'>
																${spareCostDetail.key}</form:option>
																		</c:forEach>
																	</form:select></td>
																<td><form:input
																		path="spareDetails[${iteration.index}].cost"
																		id="cost${iteration.index}" readonly="yes"
																		style="border:1px solid white;background-color:#E6E6E6;"
																		onblur="getSpareCost1(cost${iteration.index},quantity${iteration.index},total${iteration.index})" />
																</td>
																<td><form:input
																		path="spareDetails[${iteration.index}].quantity"
																		id="quantity${iteration.index}"
																		style="border:1px solid white;background-color:#E6E6E6;"
																		onblur="getSpareCost1(cost${iteration.index},quantity${iteration.index},total${iteration.index})" />
																</td>
																<td><form:input
																		path="spareDetails[${iteration.index}].total"
																		id="total${iteration.index}"
																		style="border:1px solid white;background-color:#E6E6E6;" />
																</td>
															</tr>
														</c:otherwise>
													</c:choose>
												</c:forEach>
												<%-- </c:forEach> --%>
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
									<input type='submit' value='Update Jobcard Detail'
										id='addButton' />
								</div>
							</div>
							<div>
								<div class="jobcardContent"
									style="float: left; padding-left: 185px;">Total Cost:</div>
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
