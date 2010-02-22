$(document).ready(function() {
  $(".indexes-toggle").bind("click", function() {
    $(this).parent().next(".index-listing").toggle();
    return false;
  });
})