<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
    <title>Update Profile</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.css" />"/>
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />"/>
</head>
<body class="bg-info">
<nav class="navbar navbar-expand-md navbar-navbar-light" style="background-color: #e3f2fd;">

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="nav navbar-nav mr-auto" style="margin-left: 10em">
      <li class="nav-item active mr-2">
        <a class="nav-link  text-dark" href="/twitter/"><span class="fas fa-home"></span>Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link  text-dark" href="#">About</a>
      </li>
     
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
 
 <form class="form-inline" action="tweet/tweet.htm" method="get">
		
		<button type="submit" class="btn btn-primary mx-sm-3"  style="background-color: #1DA1F2">
   		<span class="fas fa-edit"></span> Tweet
		</button>
	</form>
      
    

<a class="nav-link dropdown-toggle text-dark" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        <c:out value="${sessionScope['user_logged'].name}"/>
        </a>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
          <a class="dropdown-item" href="${pageContext.request.contextPath}/profile/${sessionScope['user-logged'].handle}"><i class="fas fa-user-circle"></i>Profile</a>
          <a class="dropdown-item" href="${pageContext.request.contextPath}/register/edit.htm"><i class="fas fa-cog"></i>Settings</a>
          <a class="dropdown-item" href="${pageContext.request.contextPath}/signout.htm"><i class="fas fa-sign-out-alt"></i>Sign Out</a>
        </div>
  </div>
</nav>

<div class="d-flex align-items-center flex-column justify-content-center h-100 bg-info text-white " id="header">
    <h2 class="display-5">Update Profile</h2>
    <!-- <button onclick="document.getElementById('change').readOnly=false" class="btn btn-light btn-sm" >
   		<i class="far fa-edit"></i> Edit
		</button>  -->
    <form:form action="${pageContext.request.contextPath}/register/edit.htm" method="post" modelAttribute="update">
    	<div class="alert alert-danger"><form:errors path="*"></form:errors></div> 
        <div class="form-group">
            <h6>Email</h6>
            <form:input class="form-control form-control-lg" path="email" placeholder="${sessionScope['user-logged'].email}" type="text" id="change"  />
        </div>
        <div class="form-group">
            <h6>Password</h6>
            <form:input class="form-control form-control-lg" path="password" placeholder="*******" type="password" id="change"  />
        </div>
        <div class="form-group">
            <h6>Name</h6>
            <form:input class="form-control form-control-lg" path="name" placeholder="${sessionScope['user-logged'].name}" type="text" id="change"  />
        </div>
        <div class="form-group">
            <h6>Handle</h6>
            <form:input class="form-control form-control-lg" path="handle" placeholder="${sessionScope['user-logged'].handle}" type="text" id="change"  />
        </div>
        <div class="form-group">
            <h6>Description</h6>
            <form:textarea class="form-control form-control-lg white-space" rows="5" cols="30" path="description" placeholder="${sessionScope['user-logged'].description}" id="change"  />
        </div>
        <div class="form-group">
            <button class="btn btn-light btn-lg btn-block">Update</button>
        </div>
    </form:form>
    <form class="form-group" action="${pageContext.request.contextPath}/delete" method="post">
    	<button class="btn btn-danger btn-lg btn-block">Delete</button>
    </form>
    
</div>

    
</body>
</html>