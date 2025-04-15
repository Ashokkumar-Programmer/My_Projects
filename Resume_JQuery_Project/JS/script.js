$(document).ready(function(){
	$("button").click(function(){
		$("#animate").css("display","none")
		$("#left").css("display","block");
		$("#left").animate({
			"margin-left":"190px"
		},"slow");
		function right(){
			$("#right").css("display","block");
			$("#right").animate({
				"margin-top":"0px"
			},"slow");
		}
		window.setTimeout( right, 1000 );
		function left_menu(){
			$("#contact").css("display","block");
			$("#contact").animate({
				"margin-left":"10px"
			},"slow");
			$("#project").css("display","block");
			$("#project").animate({
				"margin-left":"10px"
			},"slow");
			$("#education").css("display","block");
			$("#education").animate({
				"margin-left":"10px"
			},"slow");
			$("#certificate").css("display","block");
			$("#certificate").animate({
				"margin-left":"10px"
			},"slow");
		}
		function left_menu_content(){
			$("#contacts").css("display","block");
			$("#contacts").animate({
				"margin-left":"10px"
			},"slow");
			$("#left .projects").css("display","block");
			$("#left .projects").animate({
				"margin-left":"10px"
			},"slow");
			$("#educations").css("display","block");
			$("#educations").animate({
				"margin-left":"10px"
			},"slow");
		}
		function right_menu_content(){
			$("#right>*").slideDown();
			$("#my_image").fadeIn(4000);
		}
		function shadow(){
			$("#left").css("box-shadow","10px 10px 20px black");
			$("#right").css("box-shadow","0px 10px 20px black");
		}
   		window.setTimeout( left_menu, 2200 );
   		window.setTimeout( left_menu_content, 3200 );
   		window.setTimeout( right_menu_content, 3200 );
   		window.setTimeout( shadow, 3200 );
	});
	$("#linkedin").hover(function(){
		var url = 'https://www.linkedin.com/in/ashok-kumar-94b22b211/';
        window.open(url, '_blank');
	},function(){
	});
	$("#github").hover(function(){
		var url = 'https://github.com/Ashokkumar-Programmer';
        window.open(url, '_blank');
	},function(){
	});
	$("#project1").hover(function(){
		var url = 'https://github.com/Ashokkumar-Programmer/My_Projects/tree/main/Resume_Builder_Project';
        window.open(url, '_blank');
	},function(){
	});
	$("#project2").hover(function(){
		var url = 'https://github.com/Ashokkumar-Programmer/My_Projects/tree/main/Detect_Malpractice_Project';
        window.open(url, '_blank');
	},function(){
	});
	$("#project3").hover(function(){
		var url = 'https://github.com/Ashokkumar-Programmer/My_Projects/tree/main/Billing_System_Project';
        window.open(url, '_blank');
	},function(){
	});
	$("#certificate1").hover(function(){
		var url = 'https://github.com/Ashokkumar-Programmer/My_Certificates/tree/main/HackerRank';
        window.open(url, '_blank');
	},function(){
	});
	$("#certificate2").hover(function(){
		var url = 'https://github.com/Ashokkumar-Programmer/My_Certificates/blob/main/Red%20Hat%20System%20Administration%20I%20(RH124)%20.jpg';
        window.open(url, '_blank');
	},function(){
	});
	$("#certificate3").hover(function(){
		var url = 'https://github.com/Ashokkumar-Programmer/My_Certificates/blob/main/Spring_Boot_Udemy_Certificate.png';
        window.open(url, '_blank');
	},function(){
	});
});