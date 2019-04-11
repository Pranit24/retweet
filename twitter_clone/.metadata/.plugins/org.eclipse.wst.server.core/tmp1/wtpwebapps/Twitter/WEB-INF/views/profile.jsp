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
<title>Profile</title>
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

	<c:if test="${not empty requestScope.user }">
	<div class="row">
	
	<div  class="col-lg-12">
	<!-- PROFILE IMAGE -->
	<c:if test="${requestScope.user.profileImage ne null }">
	
	<img class="rounded-circle border border-light" 
	 src="data:image/jpeg;base64,${requestScope.user.getProfileImageAsString() }"
	
	alt="Card image cap" style="margin-left:65px;margin-top:150px;position:absolute;width:220px;height:220px;z-index:1;border-width:10px"
	onerror="this.onerror=null;this.src='<c:url value="/resources/images/default_profile.png" />'"/>	
	</c:if>
	
	<c:if test="${requestScope.user.profileImage eq null }">
	<img class="rounded-circle border border-light" 
	 src="<c:url value="/resources/images/default_profile.png" />"
	
	alt="Card image cap" style="margin-left:65px;margin-top:150px;position:absolute;width:220px;height:220px;z-index:1;border-width:10px"/>	
	</c:if>
	
	
	<!-- BACKGROUND IMAGE -->
	<c:if test="${requestScope.user.profileBackgroundImage ne null }">
	<img class="ml-n3 mt-n2" 
	 src="data:image/jpeg;base64,${requestScope.user.getProfileBackgroundImageAsString() }"
	alt="Card image cap" style="width:2000px;height:300px;"
	onerror="this.onerror=null;this.src='<c:url value="/resources/images/default_profile_background.jpg" />'"/>	
	</c:if>
	
	<c:if test="${requestScope.user.profileBackgroundImage eq null }">
	<img class="ml-n3 mt-n2" 
	 src="<c:url value="/resources/images/default_profile_background.jpg" />"
	
	alt="Card image cap" style="width:2000px;height:300px;"/>	
	</c:if>

	</div>
	
	<div class=" bg-white col-12 border-bottom border-dark" style="padding-left:400px;height:60px">
	<ul class="list-inline">
		  <li class="list-inline-item"><a href="${pageContext.request.contextPath}/profile/${requestScope.user.handle}">
		  Tweets <p class="text-center">${fn:length(requestScope.user.listOfTweets) }</p>
		  </a></li>
		  <li class="list-inline-item"><a href="${pageContext.request.contextPath}/profile/${requestScope.user.handle}">
		  Followers <p class="text-center">${requestScope.user.followers}</p>
		  </a></li>
		  <li class="list-inline-item"><a href="${pageContext.request.contextPath}/profile/${requestScope.user.handle}">
		  Following <p class="text-center">${fn:length(requestScope.user.following) }</p>
		  </a></li>
		  
		  
		  <!-- FOLLOW BUTTON -->
		  <c:if test="${requestScope.user.handle ne sessionScope['user_logged'].handle && requestScope.alreadyFollowing eq false}">
		  <li class="list-inline-item float-right mt-2" style="padding-right:370px">
		   <form:form class="form-inline" action="${pageContext.request.contextPath}/profile/follow/follow.htm" method="post" modelAttribute="following">
			<input type="hidden" name="profile" value="${requestScope.user.handle}"/>
			<form:input type="hidden" path="fId" value="${requestScope.user.userId}"/>
			<button type="submit" class="btn btn-primary mx-sm-3"  style="background-color: #1DA1F2">
	   		Follow
			</button>
		</form:form>
		  </li>
		  </c:if>
		  <!-- TODO IMPLEMENT UNFOLLOW -->
		  <c:if test="${requestScope.user.handle ne sessionScope['user_logged'].handle && requestScope.alreadyFollowing eq true}">
		  <li class="list-inline-item float-right mt-2" style="padding-right:370px">
		   <form:form class="form-inline" action="${pageContext.request.contextPath}/profile/follow/unfollow.htm" method="post" modelAttribute="following">
			
			<input type="hidden" name="profile" value="${requestScope.user.handle}"/>
			<form:input type="hidden" path="fId" value="${requestScope.user.userId}"/>
			<button type="submit" class="btn btn-outline-primary mx-sm-3">
	   		Following
			</button>
		</form:form>
		  </li>
		  </c:if>
	</ul>
	</div>
	
	</div>
	</c:if>
	<div class="row">    
	        <!-- User Info  -->
	  <c:if test="${not empty requestScope.user}">
      <div class="col-md-3">
	<div class="card mb-2" style="width: 300px;margin-left: 2em;margin-right: 2em;border:none">
  	 
  		<div class="card-body">
    
    <h4 class="card-title">${requestScope.user.name}</h4>
    <h5 class="card-subtitle mb-2 text-muted">@${requestScope.user.handle}</h5>
    <p class="card-text"><pre>${requestScope.user.description}</pre></p>
  	</div>
		</div>
      </div>
      </c:if>
      <!--  INVALID USER -->
      
      
      <!-- Tweets -->
   
      <div class="col-md-6">
      
      <c:if test="${fn:length(requestScope.user.listOfTweets) gt 0 }">
   		<c:forEach var="tweet" items="${requestScope.user.listOfTweets}">
      	<div class="card h-10">
  
  		<div class="card-body">
    	<fmt:parseDate var="parsedDate" value="${tweet.timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/>
    	
    <h5 class="card-title clearkfix" style="margin-bottom:-0.1em">${requestScope.user.name}
    <font class="card-title mb-2 text-muted" size=3px>@${requestScope.user.handle}</font>
    <font class="card-title mb-2 text-muted" size=3px><fmt:formatDate value="${parsedDate}" pattern="MMMM dd"/></font>
    <a class="card-title float-right nav-link dropdown-toggle text-dark" href="#" id="navbarDropdownOptionLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        </a>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownOptionLink">
          <a class="dropdown-item" href="#">Copy link to Tweet</a>
          <c:if test="${requestScope.user.handle eq sessionScope.user_logged.handle }">
          <a class="dropdown-item" href="${pageContext.request.contextPath}/tweet/delete?tweet=${tweet.msgId}">Delete Tweet</a>
          </c:if>
        </div>
    </h5>
    
    <pre><p class="card-text my-2 ml-2 lead ">${tweet.message }</p></pre>
    <div class="card-footer border-0 bg-white">
    <c:forEach var="likedUser" items="${tweet.likes }">
    
    <c:if test="${likedUser.userLikedId eq sessionScope.user_logged.userId }">
    <c:set var="likedAlready" value="${likedUser.tweetLiked.msgId}"/>
	<a href="${pageContext.request.contextPath}/tweet/like?handle=${requestScope.user.handle}&tweet=${tweet.msgId}" style="color:hotpink"><i class="fas fa-heart fa-lg"></i></a>
    <font color="#000000" size=4.5cm class="mr-5">  ${fn:length(tweet.likes)}</font>
	</c:if>
    </c:forEach>
    <!-- LIKES  -->
    <c:if test="${likedAlready ne tweet.msgId }">
    <a href="${pageContext.request.contextPath}/tweet/like?handle=${requestScope.user.handle}&tweet=${tweet.msgId}"><i class="far fa-heart fa-lg"></i></a>
    <font color="#000000" size=4.5cm class="mr-5">  ${fn:length(tweet.likes)}</font>
    </c:if>
    
    <!-- ALREADY RETWEETED -->
    <c:forEach var="retweetedUser" items="${tweet.retweets}">
    
    <c:if test="${retweetedUser.userRetweetId eq sessionScope.user_logged.userId }">
    <c:set var="retweetedAlready" value="${retweetedUser.tweetRetweeted.msgId}"/>
	<a href="${pageContext.request.contextPath}/tweet/retweet?handle=${requestScope.user.handle}&tweet=${tweet.msgId}" style="color:yellow"><i class="fas fa-retweet fa-lg"></i></i></a>
    <font color="#000000" size=4.5cm class="mr-5">  ${fn:length(tweet.retweets)}</font>
	</c:if>
    </c:forEach>
    <!-- RETWEETS -->
    <c:if test="${retweetedAlready ne tweet.msgId }">
    <a href="${pageContext.request.contextPath}/tweet/retweet?handle=${requestScope.user.handle}&tweet=${tweet.msgId}"><i class="fas fa-retweet fa-lg"></i></a>
    <font color="#000000" size=4.5cm>  ${fn:length(tweet.retweets)}</font>
    </c:if>
    </div>
  	</div>
		</div>
    	</c:forEach>
    	</c:if>
    	<c:if test="${fn:length(requestScope.user.listOfTweets) eq 0 && not empty requestScope.user && requestScope.user.handle eq sessionScope['user_logged'].handle }">
    		<div class="card my-2">
    		<div class="card-body">
  			<h5 class="card-title"><i class="fas fa-broom mb-2"></i> Looks like you haven't tweeted anything yet</h5>
    		<form class="form-inline d-flex justify-content-center" action="${pageContext.request.contextPath}/tweet/tweet.htm" method="get">
		
		<button type="submit" class="btn btn-primary mx-5 "  style="background-color: #1DA1F2">
   		<span class="fas fa-edit"></span> Tweet
		</button>
	</form>
	</div>
  			</div>
    	
    		
    	</c:if>
    	
    	<c:if test="${not empty requestScope.user && not empty sessionScope['user_logged'] && sessionScope['user_logged'].handle ne requestScope.user.handle && fn:length(requestScope.user.listOfTweets) eq 0 }">
    		<div class="card my-2">
    		<div class="card-body">
  			<h5 class="card-title"> @${requestScope.user.handle} hasn't tweeted yet!</h5>
    		<form class="form-inline d-flex justify-content-center" action="${pageContext.request.contextPath}/tweet/tweet.htm" method="get">
		
	</form>
	</div>
  			</div>
    	
    		
    	</c:if>
    	
    	<c:if test="${empty sessionScope['user_logged'] && not empty requestScope.user && fn:length(requestScope.user.listOfTweets) eq 0 }">
    		<div class="card my-2">
    		<div class="card-body">
  			<h5 class="card-title"> @${requestScope.user.handle} hasn't tweeted yet!</h5>
    		<form class="form-inline d-flex justify-content-center" action="${pageContext.request.contextPath}/tweet/tweet.htm" method="get">
		
	</form>
	</div>
  			</div>
    	
    		
    	</c:if>
    	
    	
    </div>
    <c:if test="${empty requestScope.user }">
    		<div class="card my-2 " style="margin-left:600px">
    		<div class="card-body">
  			<h5 class="card-title"> @${requestScope.error} doesn't exist!</h5>
    		<form class="form-inline d-flex justify-content-center" action="${pageContext.request.contextPath}/tweet/tweet.htm" method="get">
		
	</form>
	</div>
  			</div>
    	
    		
    	</c:if>
    
      <div class="col-md-3"></div>
    </div>
</div>

</body>
</html>