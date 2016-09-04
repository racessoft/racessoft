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
<script  type="text/javascript" src="./js/jquery-1.4.2.js"></script>
<link rel="stylesheet" href="./css/jquery-ui.css" />
<script  type="text/javascript" src="./js/jquery-1.8.3.js"></script>
<script  type="text/javascript" src="./js/jquery-ui-1.9.2.js"></script>
<script  type="text/javascript" src="./js/jquery-ui-1.10.0.js"></script>
<script type="text/javascript" >
	$(document).ready(function() {
		$('select').each(function() {
			$(this).trigger('change');

		});
	});
</script>
<script type="text/javascript"  src="./js/login.js"></script>
<script type="text/javascript"  src="./js/feedback.js"></script>
<link rel="stylesheet" href="./css/jquery-ui-timepicker-addon.css" />
<script type="text/javascript"  src="./js/jquery-ui-timepicker-addon.js"></script>
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
						<li><a href="customerReport.do?from=addCustomer">Add
								Customer Detail</a></li>
						<li><a href="customerReport.do?from=updateCustomer">Update
								Customer Detail</a></li>
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
						</div>
						<form:form commandName="feedbackDetails"
							action="updateFeedbackDetails.do"
							onsubmit="return validateFeedbackForm('updateFeedback');">
							<table style="width: 100%" border="0" cellspacing="3"
								cellpadding="3">

								<tr>
									<td width=""><div>
											<h3 style="padding-left: 10px;">FEEDBACK DETAIL</h3>
										</div>
										<div id="customerDetailDiv"
											style="border-radius: 6px 6px 6px 6px; overflow-y: auto; overflow-x: hidden; float: left; width: 705px; height: 430px; border: 1px solid #ccc;">

											<table class="jobcardContent" cellspacing="6"
												style="padding: 10px 0px 0px 10px;">
												<tr>
													<td>FeedBack Id</td>
													<td><form:input path="feedbackId" id="feedbackId" readonly="true" /></td>
													<td class="mandatory">*</td>
												</tr>												
												<tr>
													<td>Jobcard Number</td>
													<td><form:input path="jobcardNumber" id="jobcardNumber" readonly="true" onchange="validateJobCard()"/></td>
													<td class="mandatory">*</td>
													<td class="validityCheck"><label id="validityCheckId"></label></td>
												</tr>
												
												<tr>
													<td>Chasis Number</td>
													<td><form:input path="chasisNumber" id="chasisNumber" readonly="true"/></td>
													<td class="mandatory">*</td>
												</tr>  
												<tr>
													<td>Quality Of Work</td>
													<td><form:input path="qualityOfWork" id="qualityOfWork" /></td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Cleaning Of Vehicle</td>
													<td><form:input path="cleaningOfVehicle" id="cleaningOfVehicle" /></td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Timely Completion</td>
													<td><form:input path="timelyCompletion" id="timelyCompletion" /></td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Overall Rating</td>
													<td><form:input path="overallRating"
															id="overallRating" /></td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Remark</td>
													<td><form:input path="remark" id="remark" /></td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Date Of FeedBack</td>
													<td><form:input style="margin-right:5px;"
															path="dateOfFeedback" id="dateOfFeedback" onchange="feedbackDateChange(this)"/></td>
													<td></td>
												</tr>
												<tr>
													<td>Service Start Time</td>
													<td><form:input style="margin-right:5px;"
															path="servicestarttime" id="servicestarttime" onchange="feedbackDateTimeChange(this)"/></td>
													<td></td>
												</tr> 
												<tr>
													<td>Service End Time</td>
													<td><form:input style="margin-right:5px;"
															path="serviceendtime" id="serviceendtime" onchange="feedbackDateTimeChange(this)"/></td>
													<td></td>
												</tr> 
												<tr>
													<td>Engineer Name</td>
													<td><form:select path="eid"
															id="eid">
															<c:forEach var="EngineerName" items="${EmployeeDetails}"
																varStatus="iter">
																<form:option value='${EngineerName.employeeId}'
																	title='${EngineerName.employeeName}'>
																${EngineerName.employeeName}</form:option>
															</c:forEach>
														</form:select></td>
													<td></td>
												</tr>
												
												

												<tr>
													<td>Score For Explanation Of Problem</td>
													<td><form:input path="explanationofproblem" id="explanationofproblem" /></td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Score For Explanation Of Tractor</td>
													<td><form:input path="explanationoftractor" id="explanationoftractor" /></td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Score For Explanation Of Implement</td>
													<td><form:input path="explanationofimplement" id="explanationofimplement" /></td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Other Problem Details</td>
													<td><form:input path="otherProblemDetails" id="otherProblemDetails" /></td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Technician Solution Details</td>
													<td><form:input path="technicianSolution" id="technicianSolution" /></td>
													<td class="mandatory">*</td>
												</tr>  
												<tr>
													<td>Technician Reply For Problem Score</td>
													<td><form:input path="technitianreplyforproblem" id="technitianreplyforproblem" /></td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Test After Troubleshoot</td>
													<td><form:input path="posttroubleshoottest" id="posttroubleshoottest" /></td>
													<td class="mandatory">*</td>
												</tr>
											 	<tr>
												<td>Issue Status</td>
												<td><form:select path="issueStatus" id="issueStatus">
																	<c:forEach var="statusValue" items="${statusValues}"
																		varStatus="iter"> 
																		<form:option value='${statusValue}' title='${statusValue}'>
																${statusValue}</form:option>
																	</c:forEach>
																</form:select>
												</td>
												<td class="mandatory">*</td>
												
												</tr> 
											</table>
										</div>
							</table>

							<div>
								<div style="float: left; padding-left: 200px;">
									<input type='submit' value='Update Feedback' id='updateFeedback' />
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
