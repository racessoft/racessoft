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
<script type="text/javascript"  src="./js/jquery-1.4.2.js"></script>
<link rel="stylesheet" href="./css/jquery-ui.css" />
<script type="text/javascript"  src="./js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="./js/jquery-ui-1.9.2.js"></script>
<script type="text/javascript"  src="./js/jquery-ui-1.10.0.js"></script>
<script>
	$(function() {
		$("#accordion").accordion({
			animated : "bounceslide"
		});
	});
</script>

<script type="text/javascript"  src="./js/login.js"></script>
</head>
<body style="background: #BABABA" onload="loadAccordian();">
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
					<div style="text-align:center;padding-top:100px"><img src="images/comingsoon.jpg"/>
						<!-- <table>
						<tr>
						<td align="center">
						</td>
						</tr>
						</table> -->
						
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