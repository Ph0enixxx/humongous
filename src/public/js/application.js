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

  $(".fields button").bind("click", function() {
    var $el = $(this).parent().next("p").clone();
    var split_id = $el.find("input").attr("id").split("-");
    var field_num = parseInt(split_id[1]) + 1;
    var new_id = split_id[0] + "-" + field_num.toString();

    $el.attr("id", new_id);
    $("div.fields").append($el);
    return false;
  })
})