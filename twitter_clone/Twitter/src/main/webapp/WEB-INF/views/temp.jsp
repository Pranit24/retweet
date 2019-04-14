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
  <script type="text/javascript">var contextPath = ${pageContext.request.contextPath}</script>
  <script type="text/javascript"
    src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
    function Ajax() {
        $.ajax({
            url : '/twitter/ajax/test',
            success : function(data) {
                $('#result').html(data);
            }
        });
    }
</script>
 
<script type="text/javascript">
    var intervalId = 0;
    intervalId = setInterval(Ajax, 100);
</script>
</head>
<body class="bg-info">
	<div id="result"></div>
</body>
</html>