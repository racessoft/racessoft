<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<!doctype html>
<html>
<head>
<meta charset="utf8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Races</title>
<link rel="stylesheet" href="./css/style.css" />
<link rel="stylesheet" href="./css/styles.css" />
<script src="./js/jquery-1.4.2.js"></script>
<link rel="stylesheet" href="./css/jquery-ui.css" />
<script type="text/javascript" src="./js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="./js/jquery-ui-1.9.2.js"></script>
<script type="text/javascript" src="./js/highcharts.js"></script>
<script type="text/javascript" src="./js/exporting.js"></script>
<script type="text/javascript">
	$(function() {
		$("#accordion").accordion({
			animated : "bounceslide"
		});
	});
</script>
<script type="text/javascript" src="./js/login.js"></script>
</head>
<!-- 
onload="getSalesReport(),getServiceReport(),getLastSixMonthServiceData()"
 -->
<body style="background: #BABABA"
	onload="getSalesReport(),getLastSixMonthServiceData()">
	<div>
		<div id="leftPanel">
			<div>
				<img src="images/farmhouse-artwork.jpg" width="100%" />
			</div>
			<div id="accordion">
				<h3>Home</h3>
				<div>
					<p>Races</p>
				</div>
				<h3>Spares Details</h3>
				<div>
					<p></p>
				</div>
				<h3>Employee Details</h3>
				<div>
					<p></p>
					<ul>
						<li><a
							href="viewSpares.do?navLink=leftPane&from=updateJobCardReport">View
								Spares Detail</a></li>
						<li><a
							href="viewSpares.do?navLink=leftPane&from=updateJobCardReport">View
								Spares Detail</a></li>
						<li><a
							href="viewSpares.do?navLink=leftPane&from=updateJobCardReport">View
								Spares Detail</a></li>
						<li><a
							href="viewSpares.do?navLink=leftPane&from=updateJobCardReport">View
								Spares Detail</a></li>
					</ul>
				</div>
				<h3>Customer Details</h3>
				<div>
					<p></p>
					<ul>
						<li><a
							href="viewSpares.do?navLink=leftPane&from=updateJobCardReport">View
								Spares Detail</a></li>
						<li><a
							href="viewSpares.do?navLink=leftPane&from=updateJobCardReport">View
								Spares Detail</a></li>
						<li><a
							href="viewSpares.do?navLink=leftPane&from=updateJobCardReport">View
								Spares Detail</a></li>
						<li><a
							href="viewSpares.do?navLink=leftPane&from=updateJobCardReport">View
								Spares Detail</a></li>
					</ul>
				</div>
				<h3>Service Details</h3>
				<div>
					<p></p>
					<ul>
						<li><a
							href="servicedashboard.do?navLink=leftPane&fromServiceDashboard=dashboardOne">View
								Service Dashboard</a></li>
						<li><a
							href="servicedashboard.do?navLink=leftPane&fromServiceDashboard=dashboardTwo">View
								Spares Detail</a></li>
						<li><a
							href="servicedashboard.do?navLink=leftPane&fromServiceDashboard=dashboardThree">View
								Spares Detail</a></li>
						<li><a
							href="servicedashboard.do?navLink=leftPane&fromServiceDashboard=dashboardFour">View
								Spares Detail</a></li>
					</ul>
				</div>
			</div>

		</div>
		<div id="wrap">
			<!--<div id="bar"></div>-->
			<div id="content">


				<div id="mainFull">
					<div id="topBar">
						<%-- 	<% if (!session.getAttribute("username").equals("")){%>
<div style="float:left;">welcome <%=session.getAttribute("username")%> </div><%}%> --%>
						<div class="success" style="float: left; padding-left: 10px;">Welcome
							${username}</div>
						<div style="float: right; padding-right: 3px;">
							<img src="images/Races.jpg">
						</div>
						<div style="float: right; padding-right: 5px;">
							<a href="logout.do" class="success">Logout</a>
						</div>
						<div id="titleDiv"
							style="text-align: center; width: 100%; height: 25px; line-height: 45px;">
							<span class="TabHead">Races Report Analytics Dashboard</span>
						</div>
						<!-- <div style="float:right;padding-right:10px;"><a class="success" href="logout.do">Logout</a></div> -->
					</div>
					<table width="90%" border="0" cellspacing="2" cellpadding="2">
						<tr>
							<td width="45%">
								<div id="block1"
									style="float: left; width: 100%; height: 405px; padding: 5px;">
									<div class="leftTagName">
										Sales Report <span
											style="color: #F00; font-size: 10px; font-weight: bold"></span>
									</div>
									<div id="salesReportDiv"
										style="border-radius: 6px 6px 6px 6px; float: left; width: 100%; height: 340px; border: 1px solid #ccc; text-align: center">
									</div>
									<div id="graphDiv"
										style="float: left; width: 100%; height: 33px; text-align: center; padding: 10px;">
										<form name="usageTrend">
											<input readonly="readonly" id="salesReportSave"
												name="usageTrendSave" type="button" class=""
												value=" Save Sales Report "
												onclick="saveSalesReport(usageTrend)" />
										</form>
									</div>
								</div>
							</td>

							<td width="45%" style="padding-right: 10px;">
								<div id="block2"
									style="float: left; width: 100%; height: 405px; padding: 5px 5px 5px 5px;">
									<div class="nobelwinner">
										Service Report <span
											style="color: #F00; font-size: 10px; font-weight: bold"></span>
									</div>
									<div id="serviceReportDiv"
										style="border-radius: 6px 6px 6px 6px; float: left; width: 100%; height: 340px; overflow-x: auto; overflow-y: auto; border: 1px solid #ccc;">

									</div>
									<div id="graphDiv"
										style="float: left; width: 100%; height: 33px; text-align: center; padding: 10px;">
										<form name="snapshot">
											<input readonly="readonly" id="serviceReportSave"
												name="Submit" type="submit" value=" Save Service Report "
												onclick="saveServiceReport(snapshot)" />
										</form>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr>
							<td align="left" valign="middle" style="font-size: 16px;">
								<div id="block3"
									style="border-radius: 6px 0px 6px 6px; float: left; width: 100%; height: 25px; border: 1px solid #ccc; border-right: 0px; padding: 5px; color: #005a84; text-decoration: underline;">
									<div style="width: 100%">
										<a id="researchAreaLink"
											style="padding-left: 35%; text-align: center"
											href="getReport.do?forCurrentMonth=<%=new SimpleDateFormat("yyyy-MM-dd")
					.format(new java.util.Date())%>&from=monthlyReport&navLink=leftPane"><strong>Report
												Analysis</strong> </a>
									</div>
								</div>
							</td>
							<td style="font-size: 16px;">
								<div style="width: 98%">
									<div id="block3"
										style="border-radius: 6px 6px 6px 0px; float: left; width: 100%; height: 25px; border: 1px solid #ccc; border-left: 0px; padding: 5px; color: #005a84; text-decoration: underline;">
									<label id="test" hidden="true">${loginType}</label> 
										<c:choose>
											<c:when test="${loginType =='ADMIN'}">

												<a id="dataEntryLink"
													style="padding-left: 42%; text-align: center;"
													href="customerReport.do?from=addCustomer"><strong>Data
														Entry</strong> </a>
											</c:when>
											<c:when test="${loginType =='USER'}">

												<a id="dataEntryLink"
													style="padding-left: 42%; text-align: center;"
													href="customerReport.do?from=addCustomer"><strong>Data
														Entry</strong> </a>
											</c:when>
											<c:otherwise>

												<a id="dataEntryLink"
													style="padding-left: 42%; text-align: center;" href="#"><strong>Data
														Entry</strong> </a>
											</c:otherwise>
										</c:choose>


									</div>
								</div>
							</td>
						</tr>
					</table>

				</div>
			</div>
			<!--end mainFull -->

			<!-- start footer-wrap -->
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