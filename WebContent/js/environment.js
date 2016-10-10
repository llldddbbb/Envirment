$(function(){
	$('.cities:nth-child(3n)').css('margin-right', '0');
	$('.places li').click(function(event) {
		$(this).addClass('selected').siblings().removeClass('selected');
	});
});




