$(document).ready(function() {
	$(window).scroll(function() {
		if($(document).scrollTop() > 75) {
			$("#navBack").stop().addClass("inview");

			$(".button-main").stop().addClass("button-text-white");
		}

		else {
			$("#navBack").stop().removeClass("inview");
			$(".button-main").stop().removeClass("button-text-white");
		}
	});



	$("#login").click(function() {
		$("#login-form-holder").removeClass("hidden");
	});


	$("#login-close-holder").click(function() {
		$("#login-form-holder").addClass("hidden");
	});
});