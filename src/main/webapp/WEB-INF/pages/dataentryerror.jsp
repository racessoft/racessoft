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
<script type="text/javascript" src="./js/jquery-1.4.2.js"></script>
<link rel="stylesheet" href="./css/jquery-ui.css" />
<script type="text/javascript" src="./js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="./js/jquery-ui-1.9.2.js"></script>
<script type="text/javascript" src="./js/jquery-ui-1.10.0.js"></script>
<script>
	$(document).ready(function() {
		$('select').each(function() {
			$(this).trigger('change');

		});
	});
</script>
<script type="text/javascript" src="./js/login.js"></script>
</head>
<body style="background: #BABABA" onload="loadAccordian();">
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
						<li><a href="#">Add job card Detail</a></li>
						<li><a href="#">Update job card Detail</a></li>
					</ul>
				</div>
				<h3>Customer details</h3>
				<div>
					<p></p>
					<ul>
						<li><a href="#">Add Customer Detail</a></li>
						<li><a href="#">Update Customer Detail</a></li>
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
					</div>
					<div>

						<div>
							<table align="center" width="100%" style="padding-top: 150px;">
								<tr>
									<td style="font-weight: bold;">
									<div style="overflow-y: auto; border: 2px solid; padding: 10px; float: left; width: 54%; height: 85px;">Exception
											: ${ExceptionMessage}</div>
									<!-- <div style="overflow-y: auto; border: 2px solid; padding: 10px; float: left; width: 25%; height: 100px;"> <img src="images/exceptionwarning.gif"> </div>		
									 -->		
									</td>


								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>

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