<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!doctype html>
<html>
<head>
    <meta charset="utf8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Races</title>
    <link rel="stylesheet" href="./css/style.css" />
    <script type="text/javascript" src="./js/jquery-1.4.2.js"></script>
    <script type="text/javascript" src="./js/login.js"></script>
</head>
<body onload="loginPage()">
    <div id="bar">
        <div id="container">
            <!-- Login Starts Here -->
            <div id="loginContainer">
                <a href="#" id="loginButton"><span>Login</span><em></em></a>
                <div style="clear:both"></div>
                <div id="loginBox">                
                    <form id="loginForm" action="racesHome.do" method="post">
                        <fieldset id="body">
                            <fieldset>
                                <label for="username">User Name</label>
                                <input type="text" name="username" id="username" />
                            </fieldset>
                            <fieldset>
                                <label for="password">Password</label>
                                <input type="password" name="password" id="password" />
                            </fieldset>
                            <input type="submit" id="login" value="Sign in" />
                            <c:if test="${!empty message || message != 'null'}">
                <div style="color:red" id="errormessage">${message}</div></c:if>
                        </fieldset>
                        <span><a id="forgotButton"  href="#" onclick="">Forgot your password?</a></span>
                </div>
            </div>
            <!-- Login Ends Here -->
        </div>
    </div>
</body>
</html>