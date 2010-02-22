$(document).ready(function() {
  $(".new-collection").bind("click", function() {
    $(".new-collection-form").toggle();
    return false;
  });

  $(".new-index").bind("click", function() {
    $(".new-index-form").toggle();
    return false;
  });

  $(".drop-collection").bind("click", function() {
    if (confirm("Are you sure you want to drop this collection?")) {
      return true;
    } else {
      return false;
    }
  });

  $(".drop-db").bind("click", function() {
    if (confirm("Are you sure you want to drop this database?")) {
      return true;
    } else {
      return false;
    }
  });
  
  $(".drop-indexes").bind("click", function() {
    if (confirm("Are you sure you want to drop these indexes?")) {
      return true;
    } else {
      return false;
    }
  });

})