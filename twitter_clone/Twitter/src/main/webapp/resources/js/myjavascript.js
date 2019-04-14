var check = function() {
		  if (document.getElementById('password').value ==
		    document.getElementById('confirm_password').value) {
		    document.getElementById('message').style.color = 'green';
		    document.getElementById('message').innerHTML = 'Passwords Match';
		  } else {
		    document.getElementById('message').style.color = 'red';
		    document.getElementById('message').innerHTML = 'Passwords do not match';
		  }
		}

function homeAjax(user, tweetCount) {
    $.ajax({
    	type : 'POST',
    	data : {user: user, tweetCount: tweetCount},
        url : '/twitter/ajax/checkHome',
        dataType: 'text',
        cache: false,
        success : function(data) {
        	console.log(data);
            $('#newHomeTweets').html(data);
        }
    });
}

function profileAjax(user, tweetCount) {
	$.ajax({
    	type : 'POST',
    	data : {user: user, tweetCount: tweetCount},
        url : '/twitter/ajax/check',
        cache: false,
        dataType: 'text',
        success : function(data) {
        	console.log(data);
            $('#newTweets').html(data);
        }
    });
}