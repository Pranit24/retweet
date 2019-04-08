<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Profile</title>
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
      <li class="nav-item">
        <a class="nav-link  text-dark" href="#">About</a>
      </li>
     
    </ul>
    <form class="form-inline">
    	<div class="input-group">
            <input class="form-control py-2 border-right-0 border" type="search" placeholder="Search" id="example-search-input">
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
        ${requestScope.user.name}
        </a>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
          <a class="dropdown-item" href="profile/">Profile</a>
          <a class="dropdown-item" href="register/edit">Settings</a>
        </div>
  </div>
</nav>


<div class="container-fluid mt-2">
	<div class="row">    
	        <!-- User Info  -->
      <div class="col-md-3">
			<div class="card my-2" style="margin-left: 2em;margin-right: 2em">
  
  		<div class="card-body">
    
    <h5 class="card-title">${requestScope.user.name}</h5>
    <h6 class="card-subtitle mb-2 text-muted">@${requestScope.user.handle}</h6>
    <p class="card-text">DESCRIPTION</p>
  	</div>
		</div>
      </div>
      <!-- Tweets -->
      <div class="col-md-6">
      	<div class="card my-2">
  
  		<div class="card-body">
    
    <h5 class="card-title">${requestScope.user.name}</h5>
    <h6 class="card-title mb-2 text-muted">@${requestScope.user.handle}</h6>
    <p class="card-text">DESCRIPTION</p>
  	</div>
		</div></div>
      <div class="col-md-3"></div>
    </div>

</div>

</body>
</html>