<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
	<title>Welcome to twitter</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.css" />"/>
	
	<link rel="stylesheet" href="<c:url value="/resources/css/style.css" />"/>
</head>
<body class="bg-info">
<div class="d-flex align-items-center flex-column justify-content-center h-100 bg-info text-white " id="header">
    <h1 class="display-4">Sign In!</h1>
    <form:form modelAttribute="login" method="post" action="${pageContext.request.contextPath}/profile/">
    	
    	
            <div class="alert alert-danger"><form:errors path="*"></form:errors></div> 
		
        <div class="form-group">
        	<h6>Email</h6>
            <form:input class="form-control form-control-lg" path="email" placeholder="Email" type="text"/>
        </div>
        <div class="form-group">
        	<h6>Password</h6>
            <form:input class="form-control form-control-lg" path="password" placeholder="Password" type="password"/>
        </div>
        <div class="form-group">
            <button class="btn btn-light btn-lg btn-block">Sign In</button>
        </div>
    </form:form>
    
    <h6>New User? <a href="${pageContext.request.contextPath}/register/create.htm" class="text-primary">Register</a></h6>
    
</div>
	
</body>
</html>