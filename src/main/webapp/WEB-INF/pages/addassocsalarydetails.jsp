<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<link rel="stylesheet" href="./css/jquery-ui.css" />
<script type="text/javascript"  src="./js/jquery-1.4.2.js"></script>
<script type="text/javascript"  src="./js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="./js/jquery-ui-1.9.2.js"></script>
<script type="text/javascript"  src="./js/jquery-ui-1.10.0.js"></script>
<script type="text/javascript"  src="./js/login.js"></script>
<script type="text/javascript"  src="./js/salary.js"></script>
<link rel="stylesheet" href="./css/jquery-ui-timepicker-addon.css" />
<script type="text/javascript"  src="./js/jquery-ui-timepicker-addon.js"></script>

<script>
	$(document).ready(function() {
		$('select').each(function() {
			$(this).trigger('change');

		});
	});
</script>
</head>
<body style="background: #BABABA" onload="loadSalaryDateComponents();">
	
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
				<h3>Salary details</h3>
				<div>
					<p></p>
					<ul>
						<!-- 
						<li><a
							href="addassocsalarydetails.do?from=addassocsalarydetails">Add Assoc
								Salary Detail</a>
						</li>
						 -->
						<li><a
							href="getSalaryEntryPage.do?from=addassocsalarydetails">Add Assoc
								Salary Detail</a>
						</li>
						<li><a
							href="getSalaryEntryPage.do?from=updateassocsalarydetails">Update Assoc
								Salary Detail</a>
						</li>
						<li><a
							href="feedbackReport.do?from=addFeedBackReport">Add DA Detail</a>
						</li>
						<li><a
							href="feedbackReport.do?from=updateFeedBackReport">Update DA Detail</a>
						</li>
						<li><a
							href="getSalaryDataEntryPage.do?from=addassocsalarydata">Add Salary Detail</a>
						</li>
						<li><a
							href="getSalaryDataEntryPage.do?from=retreiveassocsalarydata">Update Salary Detail</a>
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
					<!-- 
						<div style="float: right; padding-right: 5px;">
						<div id="currentMonthYear" style="float: right; padding-right: 5px;">CurrentMonthYear : ${currentMonthYear} </div>
						<div id="noOfWorkingDays" style="float: right; padding-right: 5px;"> NoOfWorkingDays : ${noOfWorkingDays} </div>
						</div>
					 -->	
							<c:choose>
								<c:when
									test="${empty successmessage || successmessage == 'null'}">
									<div id="message"
										style="text-align: center; padding-left: 200px; padding-top: 10px"
										class="mandatory">${failuremessage}</div>
								</c:when>
								<c:otherwise>
									<div class="success" id="message"
										style="text-align: center; padding-left: 250px; padding-top: 10px">${successmessage}</div>
								</c:otherwise>
							</c:choose>
						</div>
						<form:form commandName="associateSalaryDetails" name="associateSalaryDetailsform" action="addAssocSalaryDetails.do" onsubmit="return validateSalaryDetailsForm();">
						<div id="AjaxLoadingInfo" style="display:none;padding: 5px 5px 5px 85px; float: left"><img src="./images/ajax-loader.gif"/></div>
							<table style="width: 100%" border="0" cellspacing="3"
								cellpadding="3">
								<tr>
									<td width=""><div>
											<h3 style="padding-left: 10px;">ASSOCIATE SALARY DETAIL</h3>
										</div>
										<div id="sparesDetailDiv"
											style="border-radius: 6px 6px 6px 6px; overflow-y: auto; overflow-x: hidden; float: left;height: 430px; border: 1px solid #ccc;/*  width: 705px;*/">

											<table class="jobcardContent" cellspacing="6"
												style="padding: 10px 0px 0px 10px;">
												<tr>
													<td>Current Month Year :</td>
													<!--  
													<td id="currentMonthYear">${currentMonthYear}</td>
													-->
													<td>
													<form:input path="salaryDate" name="currentMonthYear" id="currentMonthYear" class="date-picker" onblur="updateSalaryDateDetails();getAssociateBaseSalary();" onchange="updateSalaryDateDetails();getAssociateBaseSalary();"/>
													<!-- 
													
													<input  name="currentMonthYear" id="currentMonthYear" class="date-picker" onblur="updateSalaryDateDetails();" onchange="updateSalaryDateDetails()"/>
													
													 -->
													
													
													</td>
												</tr>
												<tr>
													<td>No Of Working Days :</td>
													<td id="noOfWorkingDays">${noOfWorkingDays}</td>
												</tr>
												<tr>
													<td>Total No Of Days :</td>
													<td id="noOfDaysInCurrentMonth">${noOfDaysInCurrentMonth}</td>
												</tr>
												<tr>
													<td>Associate Name</td>
													<td><form:select path="eid"
															id="eid" onchange="getAssociateBaseSalary()">
															<c:forEach var="EngineerName" items="${EmployeeDetails}"
																varStatus="iter">
																<form:option value='${EngineerName.employeeId}'
																	title='${EngineerName.employeeName}'>
																${EngineerName.employeeName}</form:option>
															</c:forEach>
														</form:select></td>
												</tr>
												<tr>
													<td>Associate Base Salary</td>
													<td><form:input path="baseSalary" id="baseSalary" readonly="true" class="required"/></td> 
												</tr>
												<tr></tr>
												<tr>
													<td>
														<c:if test="${fn:length(daMappedDetails) gt 0}">
															<div id="daDetailDiv" style="border-radius: 6px 6px 6px 6px; overflow-y: auto;width: 500px; height: 130px; overflow-x: hidden; float: left;border: 1px solid #ccc;">
   																<label style="font-family: Verdana;font-size: 14px;font-weight: 900;">DA - Details :</label>
   																<table class="daContent" style="padding: 10px 0px 0px 10px;font-family: Verdana;font-size: 12px;color: #006393;width: 470px; height: 100px;">
   																
																	<!-- <tr /* style="border: 1px solid black;"  style="border:1px solid white;background-color:#E6E6E6;">
																	 --><tr style="border:1px solid white;background-color:#E6E6E6;">
																		<td>DA Type</td> 
																		<td>DA Amount</td> 
																		<td>No Of Days</td>  
																		<td>Total Amount</td>  
																	</tr>	
																
																<!-- 
																<c:forEach items="${daDetails}" var="daData" varStatus="daDatacounter">
																	<tr style="border: 1px solid black;">
																		<td><c:out value="${daData.daType}"/></td> 
																		<td><c:out value="${daData.amount}"/></td>  
																		<td><input type="text" id="noOfDays" style="width:25px" onchange="updateDaTotalAmount('${daData.daType}','${daData.amount}',this,'${daDatacounter.index}')"></td>  
																		<td><label id="${daDatacounter.index}" class="DaTotalAmountValue"></label></td>  
																	</tr>
																</c:forEach>
																-->
																
												<c:forEach items="${daMappedDetails}" varStatus="iteration"
													var="spare">
													<c:choose>
														<c:when test="${iteration.index % 2 ==  0}">
															<tr id="${iteration.index}">
																
																<td>
																 <form:input
																		path="associateDaDetails[${iteration.index}].datype"
																		id="datype${iteration.index}" readonly="yes"
																		style="border:1px solid white;" />
																 </td>
																 
																 <c:choose>
																 
																 	<c:when test="${fn:contains('associateDaDetails[${iteration.index}].datype', 'SPECIAL')}">
																 	
																<td><form:input
																		path="associateDaDetails[${iteration.index}].daperday"
																		id="daperday${iteration.index}" readonly="yes" style="border:1px solid white;" class="daPerDayClass"/>
																</td>
																 	
																 	
																 	</c:when>
																 	<c:otherwise>
														
																<td><form:input
																		path="associateDaDetails[${iteration.index}].daperday"
																		id="daperday${iteration.index}" readonly="yes"
																		style="border:1px solid white;" class="daPerDayClass"/>
																</td>
																	</c:otherwise>
																</c:choose>
																<td><form:input
																		path="associateDaDetails[${iteration.index}].noofdays"
																		id="noOfDays${iteration.index}"
																		style="border:1px solid white;" 
																		onblur="updateDaTotalAmount('${iteration.index}')" class="noOfDaysClass"
																		/>
																</td>
																<td><form:input
																		path="associateDaDetails[${iteration.index}].amount"
																		id="amount${iteration.index}"
																		style="border:1px solid white;" readonly="yes" class="DaTotalAmountValue"/>
																</td>
															</tr>
														</c:when>
														<c:otherwise>
															<tr id="${iteration.index}"
																style="background-color: #E6E6E6; border: 1px solid white;">
																<td><form:input
																		path="associateDaDetails[${iteration.index}].datype"
																		id="daType${iteration.index}" readonly="yes"
																		style="border:1px solid white;background-color:#E6E6E6;"/>
																</td>
																<td><form:input
																		path="associateDaDetails[${iteration.index}].daperday"
																		id="daperday${iteration.index}" readonly="yes"
																		style="border:1px solid white;background-color:#E6E6E6;" class="daPerDayClass"/>
																</td>
																<td><form:input
																		path="associateDaDetails[${iteration.index}].noofdays"
																		id="noOfDays${iteration.index}"
																		style="border:1px solid white;background-color:#E6E6E6;"
																		onblur="updateDaTotalAmount('${iteration.index}')" class="noOfDaysClass"/>
																</td>
																<td><form:input
																		path="associateDaDetails[${iteration.index}].amount"
																		id="amount${iteration.index}"
																		style="border:1px solid white;background-color:#E6E6E6;" class="DaTotalAmountValue"/>
																</td>
															</tr>
														</c:otherwise>
													</c:choose>
												</c:forEach>																
																
																
																
																
																
																
																</table>
															<input type='button' value='Calculate DA' id='calculateDA' onclick="return calculateTotalDA()" />
															</div>
														</c:if>  
													</td>
												</tr>
												<tr>
													<td>Total Daily Allowance</td>
													<td><form:input path="dailyAllowance" id="dailyAllowance" readonly="true" class="required"/></td>
												</tr>
												<tr>
													<td>No Of Days Worked</td>
													<td><form:input path="noOfDaysWorked" id="noOfDaysWorked"/></td>
												</tr>
												<tr>
													<td>No Of Days On Leave</td>
													<td><form:input path="leaveCount" id="leaveCount" onblur="calculateTotalSalary()"/></td>
												</tr>
												<tr>
													<td>Total Salary</td>
													<td><form:input path="totalSalary" id="totalSalary" readonly="true" class="required"/></td> 
												</tr> 
												<tr hidden="true">
													<td id="fromValue">${fromValue}</td> 
												</tr> 
											</table>
										</div>
							</table>

							<div>
								<div style="float: left; padding-left: 200px;">
									<input type='button' value='Save Assoc Salary Detail' id='saveSalaryDetails'
										onclick="return validateSalaryDetailsForm('saveFeedback')" />
								</div>
								<div style="float: left; padding-left: 15px;">
									<a href="getSalaryEntryPage.do?from=addassocsalarydetails"
										style="text-decoration: underline; font-weight: bold;"
										id='addSpares'> Add Assoc Salary Detail </a>
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
