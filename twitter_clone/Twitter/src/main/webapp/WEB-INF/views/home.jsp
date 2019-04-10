<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Twitter</title>
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
	        <!-- User Info  -->
      <div class="col-md-3">
			<div class="card my-2" style="margin-left: 2em;margin-right: 2em">
  
  		<div class="card-body">
    
    <h5 class="card-title">${sessionScope['user_logged'].name}</h5>
    <h6 class="card-subtitle mb-2 text-muted">@${sessionScope['user_logged'].handle}</h6>
    <p class="card-text"><pre>${sessionScope['user_logged'].description}</pre></p>
   <!-- <a class="btn btn-primary" href="${pageContext.request.contextPath}/profile/pranit24">go to  Pranit24's profile</a> --> 
  	</div>
		</div>
      </div>
      <!-- Tweets -->
   
      <div class="col-md-6 mt-2">
      
      <c:if test="${fn:length(requestScope.followingTweets) gt 0 }">
   		<c:forEach var="tweet" items="${requestScope.followingTweets}">
      	<div class="card h-10">
  
  		<div class="card-body">
    	<fmt:parseDate var="parsedDate" value="${tweet.timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/>
    	
    <h5 class="card-title clearkfix" style="margin-bottom:-0.1em">${tweet.getUser().name}   
    <font class="card-title mb-2 text-muted" size=3px>@${tweet.getUser().handle}</font>
    <font class="card-title mb-2 text-muted" size=3px><fmt:formatDate value="${parsedDate}" pattern="MMMM dd"/></font></h5>
    <pre><p class="card-text my-2 ml-2 lead ">${tweet.message }</p></pre>
    <div class="card-footer border-0 bg-white">
    
    <c:forEach var="likedUser" items="${tweet.likes }">
    
    <c:if test="${likedUser.userLikedId eq sessionScope.user_logged.userId }">
    <c:set var="likedAlready" value="${likedUser.tweetLiked.msgId}"/>
	<a href="${pageContext.request.contextPath}/tweet/like?handle=${requestScope.user.handle}&tweet=${tweet.msgId}"><i class="fas fa-heart fa-lg"></i></a>
    <font color="#000000" size=4.5cm class="mr-5">  ${fn:length(tweet.likes)}</font>
	</c:if>
    </c:forEach>
    <!-- LIKES -->
    <c:if test="${likedAlready ne tweet.msgId }">
    
    <a href="${pageContext.request.contextPath}/tweet/like?handle=${requestScope.user.handle}&tweet=${tweet.msgId}"><i class="far fa-heart fa-lg"></i></a>
    <font color="#000000" size=4.5cm class="mr-5">  ${fn:length(tweet.likes)}</font>
    </c:if>
    
    <!-- RETWEETS -->
    <a href="${pageContext.request.contextPath}/tweet/retweet?handle=${requestScope.user.handle}&tweet=${tweet.msgId}"><i class="fas fa-retweet fa-lg"></i></a>
    <font color="#000000" size=4.5cm>  ${fn:length(tweet.retweets)}</font>
    </div>
  	</div>
		</div>
    	</c:forEach>
    	</c:if>
    	<c:if test="${fn:length(sessionScope['user_logged'].following) eq 0 }">
    		<div class="card my-2">
    		<div class="card-body">
  			<h5 class="card-title"><i class="fas fa-broom mb-2"></i> Looks like you are not following anyone!</h5>
    		
			</div>
  			</div>	
    	</c:if>
    	<c:if test="${fn:length(sessionScope['user_logged'].following) ne 0 && fn:length(requestScope.followingTweets) eq 0 }">
    		<div class="card my-2">
    		<div class="card-body">
  			<h5 class="card-title"><i class="fas fa-broom mb-2"></i> Looks no one has tweeted anything yet!</h5>
    		
			</div>
  			</div>	
    	</c:if>
    </div>
    
      <div class="col-md-3"></div>
    </div>
</div>

</body>
</html>