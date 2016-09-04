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
<script>
	$(document).ready(function() {
		$('select').each(function() {
			$(this).trigger('change');

		});
	});
</script>

</head>
<body style="background: #BABABA" onload="loadCustomerDateComponents();enableordisableCustomerId();enableCustomerButton();">
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
				<h3>Delivery details</h3>
				<div>
					<p></p>
					<ul>
						<li><a
							href="addOfferSheetDetails.do?from=addoffersheetdetails">Add Offer
								Sheet Detail</a>
						</li>
						<li><a
							href="getSalaryEntryPage.do?from=updateassocsalarydetails">Update Assoc
								Salary Detail</a>
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
						<div><c:choose>
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
						</c:choose></div>
						<form:form commandName="customerDetails" name="customerform">
							<table style="width: 100%" border="0" cellspacing="3"
								cellpadding="3">

								<tr>
									<td width=""><div>
											<h3 style="padding-left: 10px;">CUSTOMER DETAIL</h3>
										</div>
										<div id="customerDetailDiv"
											style="border-radius: 6px 6px 6px 6px; overflow-y: auto; overflow-x: hidden; float: left; width: 705px; height: 430px; border: 1px solid #ccc;">

											<table class="jobcardContent" cellspacing="6"
												style="padding: 10px 0px 0px 10px;">
												<tr>
													<td>Customer Id</td>
													<td><form:input path="customerId" id="customerId" />
													</td>
													 <td class="mandatory">* <%--<form:checkbox path="autoincrement" onclick="enableordisableCustomerId();" id="autoincrement" />Auto increment--%></td> 
												</tr>
												<tr>
													<td>Chasis Number</td>
													<td><form:input path="chasisNumber" id="chasisNumber" />
													</td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Engine Number</td>
													<td><form:input path="engineNumber" id="engineNumber" />
													</td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Name & Address</td>
													<td><form:textarea path="Nameaddr"
															id="Nameaddr" /></td>
													<td class="mandatory">* (Note: Name & Address separator is # )
													</td>
												</tr>
												<tr>
													<td>District</td>
													<td><form:select path="district"
															id="district">
															<c:forEach var="district" items="${districts}"
																varStatus="iter">
																<form:option value='${district}'
																	title='${district}'>
																${district}</form:option>
															</c:forEach>
														</form:select></td>
													<td> </td>
												</tr>
												<tr>
													<td>Taluk</td>
													<td><form:select path="taluk"
															id="taluk">
															<c:forEach var="taluk" items="${taluks}"
																varStatus="iter">
																<form:option value='${taluk}'
																	title='${taluk}'>
																${taluk}</form:option>
															</c:forEach>
														</form:select></td>
													<td> </td>
												</tr>
												<tr>
													<td>Post Office</td>
													<td><form:input path="postOffice" id="postOffice" />
													</td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Pincode</td>
													<td><form:input path="pincode" id="pincode" />
													</td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Contact Number</td>
													<td><form:textarea path="contactNumber"
															id="contactNumber" /></td><td></td>
												</tr>
												<tr>
													<td>Date of Sale</td>
													<td><form:input style="margin-right:5px;"
															path="dateOFsale" id="dateOfSale" /></td>
													<td class="mandatory">*</td>
												</tr>
												<tr>
													<td>Installation Date</td>
													<td><form:input style="margin-right:5px;"
															path="installedDate" id="installedDate" /></td>
													<td></td>
												</tr>
												<tr>
													<td>Registered Branch</td>
													<td><form:select path="registeredBranch"
															id="registeredBranch">
															<c:forEach var="registeredBranch" items="${registeredBranches}"
																varStatus="iter">
																<form:option value='${registeredBranch}'
																	title='${registeredBranch}'>
																${registeredBranch}</form:option>
															</c:forEach>
														</form:select></td>
													<td> </td>
												</tr>
												<tr>
													<td>Delivery Branch</td>
													<td><form:select path="delivaryBranch"
															id="delivaryBranch">
															<c:forEach var="deliveryBranch" items="${deliveryBranches}"
																varStatus="iter">
																<form:option value='${deliveryBranch}'
																	title='${deliveryBranch}'>
																${deliveryBranch}</form:option>
															</c:forEach>
														</form:select></td>
													<td> </td>
												</tr>
												<tr>
													<td>Sales Engg Name</td>
													<td><form:select path="serviceEngineerCode"
															id="serviceEngineerCode">
															<c:forEach var="serviceEngineerCode" items="${EmployeeDetails}"
																varStatus="iter">
																<form:option value='${serviceEngineerCode.employeeId}'
																	title='${serviceEngineerCode.employeeName}'>
																${serviceEngineerCode.employeeName}</form:option>
															</c:forEach>
														</form:select></td>
													<td></td>
												</tr>
											</table>
										</div>
							</table>

							<div>
								<div style="float: left; padding-left: 200px;">
									<input type='button' value='Save Customer Detail' id='saveCustomer' onclick="return validateCustomerForm('saveCustomer')"/>
								</div>
								<div style="float: left; padding-left: 15px;">
									<a href="customerReport.do?from=addCustomer" style="text-decoration: underline; font-weight:bold;" id='addCustomer'> Add Customer Detail </a>
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
