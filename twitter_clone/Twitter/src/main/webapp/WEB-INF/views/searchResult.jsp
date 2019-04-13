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
		<h2 class="text-center">Users with similar names</h2>
		<c:forEach var="UserFoundByName" items="${requestScope.UsersFoundByName }">
		<c:if test="${UserFoundByName.userId ne sessionScope.user_logged.userId }">
		
	<div class="card" style="width:350px;margin: 0 auto;margin-bottom:10px">
			<!-- Background -->
			<c:if test="${UserFoundByName.profileBackgroundImage ne null }">
			
	<img class="card-img-top mb-2" 
	 src="data:image/jpeg;base64,${UserFoundByName.getProfileBackgroundImageAsString() }"
	alt="Card image cap" style="height:100px"
	onerror="this.onerror=null;this.src='<c:url value="/resources/images/default_profile_background.jpg" />'"/>	
	</c:if>
	
	<c:if test="${UserFoundByName.profileBackgroundImage eq null }">
	<img class="card-img-top mb-2"
	 src="<c:url value="/resources/images/default_profile_background.jpg" />"
	
	alt="Card image cap" style="height:100px"/>	
	
	</c:if>
	
	<!-- Profile -->
	<c:if test="${UserFoundByName.profileImage ne null }">
	
	<img class="card-img-top rounded-circle border border-light" 
	 src="data:image/jpeg;base64,${UserFoundByName.getProfileImageAsString() }"
	
	alt="Card image cap" style="width:100px;height:100px;position:absolute;margin-top:45px;margin-left:10px"
	onerror="this.onerror=null;this.src='<c:url value="/resources/images/default_profile.png" />'"/>	
	</c:if>
	
	<c:if test="${UserFoundByName.profileImage eq null }">
	<img class="card-img-top rounded-circle border border-light" 
	 src="<c:url value="/resources/images/default_profile.png" />"
	
	alt="Card image cap"  style="width:100px;height:100px;position:absolute;margin-top:45px;margin-left:10px"
	/>	
	</c:if>
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
		  <a href="${pageContext.request.contextPath}/profile/${UserFoundByName.handle}" style="color:black">
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
	
	<!-- Handle Results -->
	<div class="col-4">
		<h2 class="text-center">Users with similar handles</h2>
		<c:forEach var="UserFoundByHandle" items="${requestScope.UsersFoundByHandle }">
		<c:if test="${UserFoundByHandle.userId ne sessionScope.user_logged.userId }">
		
	<div class="card" style="width:350px;margin: 0 auto;margin-bottom:10px">
			<!-- Background -->
			<c:if test="${UserFoundByHandle.profileBackgroundImage ne null }">
			
	<img class="card-img-top mb-2" 
	 src="data:image/jpeg;base64,${UserFoundByHandle.getProfileBackgroundImageAsString() }"
	alt="Card image cap" style="height:100px"
	onerror="this.onerror=null;this.src='<c:url value="/resources/images/default_profile_background.jpg" />'"/>	
	</c:if>
	
	<c:if test="${UserFoundByHandle.profileBackgroundImage eq null }">
	<img class="card-img-top mb-2"
	 src="<c:url value="/resources/images/default_profile_background.jpg" />"
	
	alt="Card image cap" style="height:100px"/>	
	
	</c:if>
	
	<!-- Profile -->
	<c:if test="${UserFoundByHandle.profileImage ne null }">
	
	<img class="card-img-top rounded-circle border border-light" 
	 src="data:image/jpeg;base64,${UserFoundByHandle.getProfileImageAsString() }"
	
	alt="Card image cap" style="width:100px;height:100px;position:absolute;margin-top:45px;margin-left:10px"
	onerror="this.onerror=null;this.src='<c:url value="/resources/images/default_profile.png" />'"/>	
	</c:if>
	
	<c:if test="${UserFoundByHandle.profileImage eq null }">
	<img class="card-img-top rounded-circle border border-light" 
	 src="<c:url value="/resources/images/default_profile.png" />"
	
	alt="Card image cap"  style="width:100px;height:100px;position:absolute;margin-top:45px;margin-left:10px"
	/>	
	</c:if>
		<div class="card-body" style="height:115px">
    
    <h5 class="card-title mt-n3" style="margin-left:90px">
    <a href="${pageContext.request.contextPath}/profile/${UserFoundByHandle.handle}" style="color:black">
    ${UserFoundByHandle.name}</a></h5>
    <h6 class="card-subtitle mb-2 mt-n3 text-muted" style="margin-left:90px">
    <a href="${pageContext.request.contextPath}/profile/${UserFoundByHandle.handle}" style="color:black">
    @${UserFoundByHandle.handle}</a></h6>
     <div class="row">
     <div  class="col-12">
     <ul class="list-inline">
		  
		  <li class="list-inline-item pr-3">
		  <a href="${pageContext.request.contextPath}/profile/${UserFoundByHandle.handle}" style="color:black">
		  Tweets <p class="text-center" style="color:blue">${fn:length(UserFoundByHandle.listOfTweets) }</p>
		  </a></li>
		  
		  <li class="list-inline-item pr-3"> 
		  <a href="${pageContext.request.contextPath}/profile/${UserFoundByHandle.handle}/followers" style="color:black">
		  Followers <p class="text-center" style="color:blue">${UserFoundByHandle.followers}</p>
		  </a></li>
		  
		  <li class="list-inline-item pr-2">
		  <a href="${pageContext.request.contextPath}/profile/${UserFoundByHandle.handle}/following" style="color:black">
		  Following <p class="text-center" style="color:blue">${fn:length(UserFoundByHandle.following) }</p>
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
	<!-- End of handle results -->
	
	<!-- Tweets with that hashtag -->
	<div class="col-4">
		<h2 class="text-center">Tweets with the hashtag</h2>
		
		<c:forEach var="TweetFoundByHashTag" items="${requestScope.TweetsFoundByHashtag }">
		<c:if test="${TweetFoundByHashTag.tweet_user.userId ne sessionScope.user_logged.userId }">
		
		<div class="card h-10">
  
  		<div class="card-body">
    	<fmt:parseDate var="parsedDate" value="${TweetFoundByHashTag.timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/>
    	
    <h5 class="card-title clearkfix" style="margin-bottom:-0.1em">${TweetFoundByHashTag.getUser().name}   
    <font class="card-title mb-2 text-muted" size=3px>@${TweetFoundByHashTag.getUser().handle}</font>
    <font class="card-title mb-2 text-muted" size=3px><fmt:formatDate value="${parsedDate}" pattern="MMMM dd"/></font></h5>
    <pre><p class="card-text my-2 ml-2 lead ">${TweetFoundByHashTag.message }</p></pre>
    <div class="card-footer border-0 bg-white">
    
    <!-- ALREADY LIKED -->
    <c:forEach var="likedUser" items="${TweetFoundByHashTag.likes }">
    
    <c:if test="${likedUser.userLikedId eq sessionScope.user_logged.userId }">
    <c:set var="likedAlready" value="${likedUser.tweetLiked.msgId}"/>
	<a href="${pageContext.request.contextPath}/tweet/like?handle=${TweetFoundByHashTag.tweet_user.handle}&tweet=${TweetFoundByHashTag.msgId}" style="color:hotpink"><i class="fas fa-heart fa-lg"></i></a>
    <font color="#000000" size=4.5cm class="mr-5">  ${fn:length(TweetFoundByHashTag.likes)}</font>
	</c:if>
    </c:forEach>
    <!-- LIKES -->
    <c:if test="${likedAlready ne TweetFoundByHashTag.msgId }">
    
    <a href="${pageContext.request.contextPath}/tweet/like?handle=${TweetFoundByHashTag.tweet_user.handle}&tweet=${TweetFoundByHashTag.msgId}"><i class="far fa-heart fa-lg"></i></a>
    <font color="#000000" size=4.5cm class="mr-5">  ${fn:length(TweetFoundByHashTag.likes)}</font>
    </c:if>
    
    <!-- ALREADY RETWEETED -->
    <c:forEach var="retweetedUser" items="${TweetFoundByHashTag.retweets}">
    
    <c:if test="${retweetedUser.userRetweetId eq sessionScope.user_logged.userId }">
    <c:set var="retweetedAlready" value="${retweetedUser.tweetRetweeted.msgId}"/>
	<a href="${pageContext.request.contextPath}/tweet/retweet?handle=${TweetFoundByHashTag.tweet_user.handle}&tweet=${TweetFoundByHashTag.msgId}" style="color:yellow"><i class="fas fa-retweet fa-lg"></i></i></a>
    <font color="#000000" size=4.5cm class="mr-5">  ${fn:length(TweetFoundByHashTag.retweets)}</font>
	</c:if>
    </c:forEach>
    <!-- RETWEETS -->
    <c:if test="${retweetedAlready ne TweetFoundByHashTag.msgId }">
    <a href="${pageContext.request.contextPath}/tweet/retweet?handle=${TweetFoundByHashTag.tweet_user.handle}&tweet=${TweetFoundByHashTag.msgId}"><i class="fas fa-retweet fa-lg"></i></a>
    <font color="#000000" size=4.5cm>  ${fn:length(TweetFoundByHashTag.retweets)}</font>
    </c:if>
    </div>
  	</div>
		</div>
	</c:if>
		</c:forEach>
	</div>
	<!-- End of tweet result -->
	<!-- End of all search -->
	
</div>

</body>
</html>