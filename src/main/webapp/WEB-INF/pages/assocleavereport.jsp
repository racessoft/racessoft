<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>
<!doctype html>
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
<!-- 
<script src="./slickgrid/lib/jquery-1.7.min.js"></script>
 -->

<link rel="stylesheet" href="./slickgrid/slick.grid.css" type="text/css"/>
<link rel="stylesheet" href="./slickgrid/css/smoothness/jquery-ui-1.8.16.custom.css" type="text/css"/>
<link rel="stylesheet" href="./slickgrid/examples/examples.css" type="text/css"/>
<script src="./slickgrid/lib/jquery.event.drag-2.2.js"></script>

<script src="./slickgrid/slick.core.js"></script>
<script src="./slickgrid/slick.grid.js"></script>
 


<script type="text/javascript" >
	$(function() {
		$("#accordion").accordion({
			animated : "bounceslide"
		});

		$("#fromDate").datepicker({
			showOn : "button",
			buttonImage : "images/calendar.gif",
			buttonImageOnly : true,
			dateFormat : 'yy-mm-dd',
			changeMonth: true,
            changeYear: true
			
		});

		$("#toDate").datepicker({
			showOn : "button",
			buttonImage : "images/calendar.gif",
			buttonImageOnly : true,
			dateFormat : 'yy-mm-dd',
			changeMonth: true,
            changeYear: true
		});
	});
</script>
<script type="text/javascript"  src="./js/login.js"></script>
</head>
<body style="background: #BABABA" onload="getGridData();getCustomerReport('TALUK');">
	<div>
		<div id="leftPanel">
			<div>
				<img src="images/images.jpg" width="100%" />
			</div>
			<div id="accordion">
				<h3>Monthly Report</h3>
				<div>
					<p>
					<ul>
						<li><a href="getReport.do?navLink=leftPane&from=construction">Free Service Report</a>
						</li>
						<li><a
							href="getReport.do?navLink=leftPane&from=monthlyReport&forCurrentMonth=<%=new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>">Particular
								Service Report</a>
						</li>
					</ul>
					</p>
				</div>
				<h3>Report For Selected Duration</h3>
				<div>
					<p>
					<ul>
						<li><a href="getReport.do?navLink=leftPane&from=construction">Free Service Report</a>
						</li>
						<li><a
							href="getReport.do?navLink=leftPane&from=durationReport&forCurrentMonth=<%=new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>">Particular
								Service Report</a>
						</li>
					</ul>
					</p>
				</div>
				<h3>Report For Customer Details</h3>
				<div>
					<p>
					<ul>
						<li><a href="getCustomerReport.do?navLink=leftPane&reportType=reportByCustomer&from=customerByTaluk">Data By Taluk</a>
						</li>
						<li><a
							href="getCustomerReport.do?navLink=leftPane&reportType=reportByCustomer&from=customerByDistrict">Data By District</a>
						</li>
					</ul>
					</p>
				</div>
				<h3>Report For Associate Salary</h3>
				<c:choose>
							<c:when
								test="${username =='admin' || username == 'ADMIN'}">
				<div>
					<p>
					<ul>
						<li><a href="getSalaryReport.do?navLink=leftPane&reportType=reportByDistrict&from=assocleavereport">Salary Report</a>
						</li> 
					</ul>
					</p>
					<p>
					<ul>
						<li><a href="getSalaryReport.do?navLink=leftPane&reportType=reportByDistrict&from=completeSalaryListByYear">Complete Salary Report</a>
						</li> 
					</ul>
					</p>					
				</div>
							</c:when>
							<c:otherwise>
				<div>
					<p>
					<ul>
						<li><a href="#">Salary Report</a>
						</li> 
					</ul>
					</p>
					<p>
					<ul>
						<li><a href="#">Complete Salary Report</a>
						</li> 
					</ul>
					</p>					
				</div>
							</c:otherwise>
						</c:choose>										
				<h3>Complete Report</h3>
				<div>
					<p></p>
					<ul>
						<li><a
							href="getReport.do?navLink=leftPane&from=completeReport&toDate=<%=new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>">Complete
								jobcard report</a></li>
					</ul>
				</div>
				<h3>Report Based on Service Engineer</h3>
				<div>
					<p>
					<ul>
						<li><a href="getLeaveReport.do?navLink=leftPane&reportType=reportByDistrict&from=assocleavereport">Leave Report</a>
						</li> 
						<li><a href="getLeaveTrend.do?navLink=leftPane&reportType=reportByDistrict&from=assocleavetrend">Leave Trend</a>
						</li> 
					</ul>
					</p>
				
				</div>
			</div>

		</div>
		<div id="wrap">
			<!--<div id="bar"></div>-->
			<div id="content">
			<div id="toStoreTableJsonData" hidden="true"></div>
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
								<div
									style="text-align: center; padding-left: 200px; padding-top: 10px"
									class="mandatory">${failuremessage}</div>
							</c:when>
							<c:otherwise>
								<div id="exportmessage" class="success"
									style="text-align: center; padding-left: 250px; padding-top: 10px">${successmessage}</div>
							</c:otherwise>
						</c:choose></div></div>
						<form:form commandName="reportFilter" action="exportReport.do" onsubmit="if($('#exportTypeFilter').val() != 'DEFAULT'){$('#exportmessage').hide();showLoadingIcon();disableButton()};return validateReport();">
							<table style="width: 100%" border="0" cellspacing="10"
								cellpadding="10">
								<form:hidden id="forCurrentMonth" path="forCurrentMonth" />
								<tr>
									<td>
										<div id="reportContentDiv"
											style="border-radius: 6px 6px 6px 6px; float: left; width: 705px; height: 95px; border: 1px solid #ccc; text-align: center">
											<div style="padding: 10px 5px 5px 25px; float: left">
												<div>Taluk</div>
											</div>
											<div style="padding: 10px 5px 5px 25px; float: left">
												<!--<c:choose>
													<c:when test="${fromParam == 'durationReport'}"></c:when>
													<c:otherwise> Select Taluk : </c:otherwise>
												</c:choose>
												<form:input path="toDate" id="toDate" />
												--> 
											<form:select path="eid"
															id="eid">
															<c:forEach var="EngineerName" items="${EmployeeDetails}"
																varStatus="iter">
																<form:option value='${EngineerName.employeeId}'
																	title='${EngineerName.employeeName}'>
																${EngineerName.employeeName}</form:option>
															</c:forEach>
											</form:select>
											</div>
											
											<div style="padding: 12px 5px 5px 25px; float: left">
											Select Year:  <form:select id='yearData' path="yearOfSale" items="${vehicleYears}" />
											</div>
											
											<div style="padding: 12px 5px 5px 25px; float: left">
											Select Month:  <form:select id='month' path="month" items="${months}" />
											</div>
											
											<div id="AjaxLoadingInfo" style="padding: 5px 5px 5px 85px; float: left"><img src="./images/ajax-loader.gif"/></div>
											<table style="width: 100%" cellspacing="0" cellpadding="0"
												border="0">
												<tbody>
													<tr>
														<td>&nbsp;</td>
														<td align="center">&nbsp;</td>
														<td align="left" valign="middle" colspan="6">&nbsp;</td>
													</tr>
													<tr>
														<td>&nbsp;</td>
														<td align="center"
															style="border-right: 1px solid #999; padding-left: 20px;">
															<input type="button" onclick="getCustomerReport('TALUK');"
															value=" Run Report " id="runreport" class="" name="input">
														</td>
														<td align="left" valign="middle"
															style="padding-left: 20px; border-right: 1px solid #999;"
															colspan="4"><form:hidden path="from" id="from" /> <form:hidden
																path="navLink" id="navLink" />     
															&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
															<input disabled="disabled" type="button"
															value=" Save Report " class="" id="savereport" onclick="downloadData();"
															name="input2" >
														</td>
														<td align="left" valign="middle"
															style="padding-left: 20px;" colspan="2">

																<label id="Customer Count" >NoOfRecords :</label>&nbsp;&nbsp;
																<label id="noOfRecords">0</label>
														</td>
													</tr>
												</tbody>
											</table>
										</div></td>
								</tr>
								<tr>
									<td width="">
										<div id="myGrid"
											style="border-radius: 6px 6px 6px 6px; overflow-y: auto; overflow-x: hidden; float: left; width: 705px; height: 400px; border: 1px solid #ccc; text-align: center">
										</div></td>
								</tr>
							</table>
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
	</div>
</body>
</html>