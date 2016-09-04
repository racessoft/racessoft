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
<script  type="text/javascript" src="./js/jquery-1.4.2.js"></script>
<link rel="stylesheet" href="./css/jquery-ui.css" />
<script type="text/javascript"  src="./js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="./js/jquery-ui-1.9.2.js"></script>
<script type="text/javascript"  src="./js/jquery-ui-1.10.0.js"></script>

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
<body style="background: #BABABA" onload="hideLoadingIcon();">
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
				<div></div>
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
												<c:if test="${fromParam == 'durationReport'}">From : <form:input
														path="fromDate" id="fromDate" />
												</c:if>
											</div>
											<div style="padding: 10px 5px 5px 25px; float: left">
												<c:choose>
													<c:when test="${fromParam == 'durationReport'}"></c:when>
													<c:otherwise> Select Duration : </c:otherwise>
												</c:choose>
												<form:input path="toDate" id="toDate" />
											</div>
											<div style="padding: 12px 5px 5px 25px; float: left">
											Select Export Type:  <form:select path="exportTypeFilter" items="${exportType}" />
											</div><div id="AjaxLoadingInfo" style="padding: 5px 5px 5px 85px; float: left"><img src="./images/ajax-loader.gif"/></div>
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
															<input type="button" onclick="getReportAjaxCall();"
															value=" Run Report " id="runreport" class="" name="input">
														</td>
														<td align="left" valign="middle"
															style="padding-left: 20px; border-right: 1px solid #999;"
															colspan="4"><form:hidden path="from" id="from" /> <form:hidden
																path="navLink" id="navLink" /> <form:radiobutton
																path="formatType" value="PDF" checked="checked"
																name="format" id="pdfradio" /> <label for="radio1">PDF</label>
															&nbsp; <form:radiobutton path="formatType" value="CSV"
																name="format" id="csvradio" /> <label for="radio1">CSV</label>
															&nbsp; <form:radiobutton path="formatType" value="HTML"
																name="format" id="htmlradio" /> <label for="radio1">HTML</label>
															&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;<input type="submit"
															value=" Save Report " class="" id="savereport"
															name="input2">
														</td>
														<td align="left" valign="middle"
															style="padding-left: 20px;" colspan="2"><input
															type="button" id="printreport"
															onclick="return validateReport()" value="Print Report "
															class="" name="input3">
														</td>
													</tr>
												</tbody>
											</table>
										</div></td>
								</tr>
								<tr>
									<td width="">
										<div id="reportDiv"
											style="border-radius: 6px 6px 6px 6px; overflow-y: auto; overflow-x: hidden; float: left; width: 705px; height: 400px; border: 1px solid #ccc; text-align: center">
											<c:out value="${reportString}" escapeXml="false"></c:out>
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