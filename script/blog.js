$(document).ready(function() {
    $.ajaxSetup({ cache: true });
	$.getScript('//connect.facebook.net/ko_KRsdk.js', function(){
		FB.init({
			appId: '190532464351842',
			version: 'v2.7' 		
		});     
	});
}
