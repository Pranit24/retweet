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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>
    <script type="text/javascript"
    src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/myjavascript.js" />"></script>

 
<script type="text/javascript">
	var tweetCount = '${fn:length(requestScope.followingTweets)}';
	var user = '${sessionScope.user_logged.handle}';
    var intervalId = 0;
    intervalId = setInterval(function() {homeAjax(user, tweetCount);}, 5000);
</script>
 
<script type="text/javascript">
function homePopularAjax() {
	$.ajax({
    	type : 'POST',
        url : '/twitter/ajax/getPopular',
        cache: false,
        dataType:'text',
        success : function(data) {
        	var json = JSON.parse(data);
            var myHtml = '<ul class="list-group"><li class="list-group-item"><a href=${pageContext.request.contextPath}/profile/'+json.firstUserHandle+'>'+json.firstUserName+'</li>'+
            '<li class="list-group-item"><a href=${pageContext.request.contextPath}/profile/'+json.secondUserHandle+'>'+json.secondUserName+'</li>'+
                '<li class="list-group-item"><a href=${pageContext.request.contextPath}/profile/'+json.thirdUserHandle+'>'+json.thirdUserName+'</li>'+
                '</ul>';
            
            console.log(json.firstUserName);
    		$('#topUsers').html(myHtml);
        }
    });
}

    setInterval(homePopularAjax, 1000);
</script>
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
                <button class="input-group-text bg-light" type="submit">
                <i class="fa fa-search"></i>
                </button>
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
	<!-- Profile Image -->
	<c:if test="${sessionScope.user_logged.profileImage ne null }">
	
	<img class="rounded-circle border-4 border-light" 
	 src="data:image/jpeg;base64,${sessionScope.user_logged.getProfileImageAsString() }"
	
	alt="Card image cap" style="width:100px;height:100px;margin-left:55px;position:absolute;z-index:1;margin-top:70px;border-width:10px"
	onerror="this.onerror=null;this.src='<c:url value="/resources/images/default_profile.png" />'"/>	
	</c:if>
	
	<c:if test="${sessionScope.user_logged.profileImage eq null }">
	<img class="rounded-circle border-4 border-light" 
	 src="<c:url value="/resources/images/default_profile.png" />"
	
	alt="Card image cap" style="width:100px;height:100px;margin-left:55px;position:absolute;z-index:1;margin-top:70px;border-width:10px"/>	
	</c:if>
      <div class="col-md-3">
			<div class="card my-2" style="margin-left: 2em;margin-right: 2em;border:none">
			
	<!-- BACKGROUND IMAGE -->
	<c:if test="${sessionScope.user_logged.profileBackgroundImage ne null }">
	<img class="card-img-top mb-2" 
	 src="data:image/jpeg;base64,${sessionScope.user_logged.getProfileBackgroundImageAsString() }"
	alt="Card image cap" style="height:120px"
	onerror="this.onerror=null;this.src='<c:url value="/resources/images/default_profile_background.jpg" />'"/>	
	</c:if>
	
	<c:if test="${sessionScope.user_logged.profileBackgroundImage eq null }">
	<img class="card-img-top mb-2" 
	 src="<c:url value="/resources/images/default_profile_background.jpg" />"
	
	alt="Card image cap" style="height:120px"/>	
	
	</c:if>
  	
  		<div class="card-body" style="height:115px">
    
    <h5 class="card-title mt-n3" style="margin-left:90px">
    <a href="${pageContext.request.contextPath}/profile/${sessionScope['user_logged'].handle}" style="color:black">
    ${sessionScope['user_logged'].name}<c:if test="${sessionScope['user_logged'].verified eq true}">
    <i class="fas fa-user-check fa-sm"></i>
    </c:if>
    </a></h5>
    <h6 class="card-subtitle mb-2 mt-n3 text-muted" style="margin-left:90px">
    <a href="${pageContext.request.contextPath}/profile/${sessionScope['user_logged'].handle}" style="color:black">
    @${sessionScope['user_logged'].handle}</a></h6>
     <div class="row">
     <div  class="col-12">
     <ul class="list-inline">
		  
		  <li class="list-inline-item pr-2">
		  <a href="${pageContext.request.contextPath}/profile/${sessionScope['user_logged'].handle}" style="color:black" id="hoverBlue">
		  Tweets<p class="text-center" style="color:blue">${fn:length(sessionScope['user_logged'].listOfTweets) + requestScope.retweetedTweetsSize }</p>
		  </a></li>
		  
		  <li class="list-inline-item pr-2"> 
		  <a href="${pageContext.request.contextPath}/profile/${sessionScope['user_logged'].handle}/followers" style="color:black">
		  Followers <p class="text-center" style="color:blue">${sessionScope['user_logged'].followers}</p>
		  </a></li>
		  
		  <li class="list-inline-item pr-2">
		  <a href="${pageContext.request.contextPath}/profile/${sessionScope['user_logged'].handle}/following" style="color:black">
		  Following <p class="text-center" style="color:blue">${fn:length(sessionScope['user_logged'].following) }</p>
		  </a></li>
	</ul>
    
     </div>
     </div>
   <!-- <a class="btn btn-primary" href="${pageContext.request.contextPath}/profile/pranit24">go to  Pranit24's profile</a> --> 
  	</div>
		</div>
      </div>
      <!-- Tweets -->
   
      <div class="col-md-6 mt-2">
      <!-- Tweets AJAX -->
    		<a class="text-center text-danger" href="${pageContext.request.contextPath}/">
    		<p class="card-text" id="newHomeTweets"></p>
    		</a>
      <!-- TWEETS -->
      <c:if test="${fn:length(requestScope.followingTweets) gt 0 }">
   		<c:forEach var="tweet" items="${requestScope.followingTweets}">
      	<div class="card h-10">
  
  		<div class="card-body">
    	<fmt:parseDate var="parsedDate" value="${tweet.timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/>
    	
    <a href="${pageContext.request.contextPath}/profile/${tweet.tweet_user.handle}" 
    style="color:black">
    <h5 class="card-title clearkfix" style="margin-bottom:-0.1em">${tweet.getUser().name}   
    <font class="card-title mb-2 text-muted" size=3px>@${tweet.getUser().handle}</font>
    </a>
    <c:if test="${tweet.tweet_user.verified eq true}">
    <i class="fas fa-user-check fa-sm"></i>
    </c:if>
    <c:if test="${tweet.tweet_user.role eq true }">
    <button type="button" class="btn btn-outline-info btn-sm" disable>Staff</button>
    </c:if>
    <font class="card-title mb-2 text-muted" size=3px><fmt:formatDate value="${parsedDate}" pattern="MMMM dd"/></font></h5>
    <pre><p class="card-text my-2 ml-2 lead ">${tweet.message }</p></pre>
    <div class="card-footer border-0 bg-white">
    
    <!-- ALREADY LIKED -->
    <c:forEach var="likedUser" items="${tweet.likes }">
    
    <c:if test="${likedUser.userLikedId eq sessionScope.user_logged.userId }">
    <c:set var="likedAlready" value="${likedUser.tweetLiked.msgId}"/>
	<a href="${pageContext.request.contextPath}/tweet/like?handle=${requestScope.user.handle}&tweet=${tweet.msgId}" style="color:hotpink"><i class="fas fa-heart fa-lg"></i></a>
    <font color="#000000" size=4.5cm class="mr-5">  ${fn:length(tweet.likes)}</font>
	</c:if>
    </c:forEach>
    <!-- LIKES -->
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
    
      <div class="col-md-3">
      <div class="card" style="margin-left: 2em;margin-right: 2em;border:none">
      <div class="card-body">
    <h5 class="card-title">Popular Users</h5>
    <div class="card-text" id="topUsers"></div>
  </div>
      </div>
      </div>
    </div>
</div>

</body>
</html>