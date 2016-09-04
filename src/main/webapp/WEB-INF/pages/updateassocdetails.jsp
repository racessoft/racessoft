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
<script>
	$(document).ready(function() {
		$('select').each(function() {
			$(this).trigger('change');

		});
	});
</script>
<script type="text/javascript"  src="./js/login.js"></script>
<script type="text/javascript"  src="./js/spares.js"></script>
<script type="text/javascript"  src="./js/associate.js"></script>
</head>
<body style="background: #BABABA"
	onload="loadCustomerDateComponents();enableordisableCustomerId();">
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
						<form:form commandName="employeeDetails"
							action="updateAssocDetails.do"
							onsubmit="return validateAssocForm('updateAssoc');">
							<table style="width: 100%" border="0" cellspacing="3"
								cellpadding="3">

								<tr>
									<td width=""><div>
											<h3 style="padding-left: 10px;">ASSOCIATE DETAIL</h3>
										</div>
										<div id="customerDetailDiv"
											style="border-radius: 6px 6px 6px 6px; overflow-y: auto; overflow-x: hidden; float: left; width: 705px; height: 430px; border: 1px solid #ccc;">

											<table class="jobcardContent" cellspacing="6"
												style="padding: 10px 0px 0px 10px;">
												<tr>
													<td>Associate Id</td>
													<td><form:input path="employeeId" id="employeeId" /></td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Associate Name</td>
													<td><form:input path="employeeName"
															id="employeeName" /></td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Date Of Join</td>
													<td><form:input path="dateOfJoin"
															id="dateOfJoin" onchange="dateOfJoinChange(this)"/></td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Department</td>
													<td><form:input path="department"
															id="department" /></td> 
												</tr>		
												<tr>
													<td>Designation</td>
													<td><form:input path="designation" id="designation" />
													</td> 
												</tr>
												<tr>
													<td>address</td>
													<td><form:input path="address" id="address" /></td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Contact Number</td>
													<td><form:input path="mobileNo" id="mobileNo" />
													</td>
													<td class="mandatory">*</td>
												</tr> 
											</table>
										</div>
							</table>

							<div>
								<div style="float: left; padding-left: 200px;">
									<input type='submit' value='Update Details' id='updateSpares' />
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
