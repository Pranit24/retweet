<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Tweet</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.css" />"/>
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body class="bg-info">
<nav class="navbar navbar-expand-md navbar-navbar-light" style="background-color: #e3f2fd;">

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="nav navbar-nav mr-auto" style="margin-left: 10em">
      <li class="nav-item active mr-2">
        <a class="nav-link  text-dark" href="/twitter/"><span class="fas fa-home"></span>Home <span class="sr-only">(current)</span></a>
      </li>
      <!-- <li class="nav-item">
        <a class="nav-link  text-dark" href="aboutPage.jsp">About</a>
      </li> -->
     
    </ul>
    <form class="form-inline" method="get" action="${pageContext.request.contextPath}/search/">
    	<div class="input-group">
            <input class="form-control py-2 border-right-0 border" name="search" type="search" placeholder="Search" id="example-search-input">
            <span class="input-group-append">
                <div class="input-group-text bg-light">
                <i class="fa fa-search"></i>
                </div>
            </span>
        </div>
    </form>
 
 <form class="form-inline" action="twitter/tweet/tweet.htm" method="get">
		
		<button type="submit" class="btn btn-primary mx-sm-3"  style="background-color: #1DA1F2">
   		<span class="fas fa-edit"></span> Tweet
		</button>
	</form>
      
    

<a class="nav-link dropdown-toggle text-dark" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        <c:out value="${sessionScope['user_logged'].name}"/>
        </a>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
          <a class="dropdown-item" href="${pageContext.request.contextPath}/profile/${sessionScope['user_logged'].handle}"><i class="fas fa-user-circle"></i>Profile</a>
          <a class="dropdown-item" href="${pageContext.request.contextPath}/register/edit.htm"><i class="fas fa-cog"></i>Settings</a>
          <a class="dropdown-item" href="${pageContext.request.contextPath}/signout.htm"><i class="fas fa-sign-out-alt"></i>Sign Out</a>
        </div>
  </div>
</nav>

<div class="container-fluid">
	<div class="row">
		<div class="col">
		
      </div>
		<div class="col-md-5">
			
			<div class="card my-2 mr-3">
  
  <div class="card-body">
    
     <h5 class="card-title clearkfix" style="margin-bottom:-0.1em">${sessionScope.user_logged.name}
    <font class="card-title mb-2 text-muted" size=3px>@${sessionScope.user_logged.handle}</font></h5>
    <p class="card-text">
    <form:form  action="${pageContext.request.contextPath}/tweet/tweet.htm" method="post" modelAttribute="message" class="form-inline">
    <div class="form-group">
        	<form:textarea class="form-control d-flex justify-content-center white-space" maxlength="140" rows="5" cols="60" path="message" placeholder="Whats on your mind?"/>
        	
        </div>
        
    
    <button type="submit" class="btn btn-primary mr-sm-3 mt-sm-3 float-left"  style="background-color: #1DA1F2">
   		<span class="fas fa-edit"></span> Tweet
		</button>
	<div class="text-muted float-right mt-n4 h2" style="font-size:12px;margin-left:225px">Maximum Characters=140</div>
		
	</form:form>

  	</div>
		</div>


</div>
<div class="col"></div>

</div>
</div>
</body>
</html>