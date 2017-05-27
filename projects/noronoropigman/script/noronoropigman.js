$( document ).ready(function() {
  $( "#facebook-btn" ).click(function() {
    $(location).attr('href', 'http://www.facebook.com/noronoropigman');
    console.info("facebook clicked");
  });
  $( "#instagram-btn" ).click(function() {
    $(location).attr('href', 'https://www.instagram.com/noronoropigman');
    console.info("instagram clicked");
  });
});
