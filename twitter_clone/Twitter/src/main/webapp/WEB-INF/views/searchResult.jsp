<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Search Result</title>
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.css" />"/>
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body class="bg-light">
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
 
 <form class="form-inline" action="${pageContext.request.contextPath}/tweet/tweet.htm" method="get">
		
		<button type="submit" class="btn btn-primary mx-sm-3"  style="background-color: #1DA1F2">
   		<span class="fas fa-edit"></span> Tweet
		</button>
	</form>
      
    
<c:if test="${sessionScope['user_logged']!=null}">
<a class="nav-link dropdown-toggle text-dark" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        <c:out value="${sessionScope['user_logged'].name}"/>
        </a>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
          <a class="dropdown-item" href="${pageContext.request.contextPath}/profile/${sessionScope['user_logged'].handle}"><i class="fas fa-user-circle"></i>Profile</a>
          <a class="dropdown-item" href="${pageContext.request.contextPath}/register/edit.htm"><i class="fas fa-cog"></i>Settings</a>
          <a class="dropdown-item" href="${pageContext.request.contextPath}/signout.htm"><i class="fas fa-sign-out-alt"></i>Sign Out</a>
        </div>
        
</c:if>
<c:if test="${sessionScope['user_logged']==null}">
<a class="nav-link text-dark" href="${pageContext.request.contextPath}" id="navbarDropdownMenuLink" aria-haspopup="true" aria-expanded="false">Login</a>
</c:if>
  </div>
  
</nav>
<div class="container-fluid mt-2">
	<div class="row">
	
	<!-- Name results -->
	<div class="col-4">
		<h2>Users with similar names</h2>
		<c:forEach var="UserFoundByName" items="${requestScope.UsersFoundByName }">
		<c:if test="${UserFoundByName.userId ne sessionScope.user_logged.userId }">
			<div class="card">
		<div class="card-body" style="height:115px">
    
    <h5 class="card-title mt-n3" style="margin-left:90px">
    <a href="${pageContext.request.contextPath}/profile/${UserFoundByName.handle}" style="color:black">
    ${UserFoundByName.name}</a></h5>
    <h6 class="card-subtitle mb-2 mt-n3 text-muted" style="margin-left:90px">
    <a href="${pageContext.request.contextPath}/profile/${UserFoundByName.handle}" style="color:black">
    @${UserFoundByName.handle}</a></h6>
     <div class="row">
     <div  class="col-12">
     <ul class="list-inline">
		  
		  <li class="list-inline-item pr-3">
		  <a href="${pageContext.request.contextPath}/profile/${UserFoundByName.handle}" style="color:black" id="hoverBlue">
		  Tweets <p class="text-center" style="color:blue">${fn:length(UserFoundByName.listOfTweets) }</p>
		  </a></li>
		  
		  <li class="list-inline-item pr-3"> 
		  <a href="${pageContext.request.contextPath}/profile/${UserFoundByName.handle}" style="color:black">
		  Followers <p class="text-center" style="color:blue">${UserFoundByName.followers}</p>
		  </a></li>
		  
		  <li class="list-inline-item pr-2">
		  <a href="${pageContext.request.contextPath}/profile/${UserFoundByName.handle}" style="color:black">
		  Following <p class="text-center" style="color:blue">${fn:length(UserFoundByName.following) }</p>
		  </a></li>
	</ul>
    
     </div>
     </div>
   <!-- <a class="btn btn-primary" href="${pageContext.request.contextPath}/profile/pranit24">go to  Pranit24's profile</a> --> 
  	
	</div>
	
	</div>
	</c:if>
		</c:forEach>
		
	
	</div>
	<!-- END OF NAME RESULTS -->
</div>

</body>
</html>