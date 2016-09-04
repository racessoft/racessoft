<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/style.css" />
<link rel="stylesheet" href="./css/styles.css" />
<script type="text/javascript"  src="./js/jquery-1.4.2.js"></script>
<link rel="stylesheet" href="./css/jquery-ui.css" />
<script type="text/javascript"  src="./js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="./js/jquery-ui-1.9.2.js"></script>
</head>
<body style="background: #BABABA">
<div><!-- Start Content Div -->
		<div id="leftPanel">
			<div>
				<img src="images/images.jpg" width="100%" />
			</div>
			<div id="accordion">
				<h3>Spares details</h3>
				<div>
					<p></p>
					<ul>
					   <li><a
							href="viewSpares.do?navLink=leftPane&from=updateJobCardReport">View
								Spares Detail</a></li>
						<li><a
							href="addSpares.do?navLink=leftPane&from=addJobCardReport">Add
								Spares Detail</a></li>
						<li><a
							href="updateSparesDetails.do?navLink=leftPane&from=updateJobCardReport">Update
								Spares Detail</a></li>
						<li><a
							href="deleteSpares.do?navLink=leftPane&from=updateJobCardReport">Delete
								Spares Detail</a></li>		
					</ul>
				</div>
			</div>
		</div>

<!-- start footer-wrap -->
			<div id="footer-wrap">
				<div id="footer">
					<address>
						&copy;
						<%= new java.util.Date().getYear() + 1900 %>
						Powered by Races
					</address>
				</div>
				<!-- end footer -->
			</div>
			<!-- end footer-wrap -->
</div><!-- End Content Div -->
</body>
</html>