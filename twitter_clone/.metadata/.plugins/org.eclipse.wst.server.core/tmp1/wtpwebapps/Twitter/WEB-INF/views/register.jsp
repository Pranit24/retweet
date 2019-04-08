<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
	<title>Register to Twitter!</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.css" />"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />"/>
</head>
<body class="bg-info">
<div class="d-flex align-items-center flex-column justify-content-center h-100 bg-info text-white " id="header">
    <h1 class="display-4">Register</h1>
    <form:form action="${pageContext.request.contextPath}/register/create.htm" method="post" modelAttribute="register">
    <div class="alert alert-danger"><form:errors path="*"></form:errors></div> 
        <div class="form-group">
        	<h6>Email</h6>
            <form:input class="form-control form-control-lg" path="email" placeholder="john.doe@gmail.com" type="text"/>
        </div>
        <div class="form-group">
        	<h6>Password</h6>
            <form:input class="form-control form-control-lg" path="password" placeholder="*******" type="password"/>
        </div>
        <div class="form-group">
            <h6>Name</h6>
            <form:input class="form-control form-control-lg" path="name" placeholder="John Doe" type="text"/>
        </div>
        <div class="form-group">
            <h6>Handle</h6>
            <form:input class="form-control form-control-lg" path="handle" placeholder="@johndoe" type="text"/>
        </div>
        <div class="form-group">
            <button class="btn btn-light btn-lg btn-block">Register</button>
        </div>
    </form:form>
    
    <h6>Back to sign in! <a href="${pageContext.request.contextPath}" class="text-primary">Sign In!</a></h6>
    
</div>
</body>
</html>