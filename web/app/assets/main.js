$(document).ready(function() {


	$(window).scroll(function() {
		if($(document).scrollTop() > 75) {
			$("#navBack").stop().addClass("inview");
		}

		else {
			$("#navBack").stop().removeClass("inview");
		}
	});



	$("#login").click(function() {
		$("#login-form-holder").removeClass("hidden");
	});


	$("#login-close-holder").click(function() {
		$("#login-form-holder").addClass("hidden");
	});


	$(".login-btn").click(function() {
		$("#login-form-holder").addClass("hidden");
	});

	$("#create-user-login-form").click(function() {
		$("#login-form-holder").addClass("hidden");
	});
});