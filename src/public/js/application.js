function double_confirm(frst_conf, sec_conf){
  if (confirm(frst_conf)) {
    if(confirm(sec_conf)) {
      return true;
    } else {
      return false
    }
  } else {
    return false;
  }
}

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
    return double_confirm("Are you sure you want to drop this collection?",
                          "This will delete all data in this collection.  Are you really sure?")
  });

  $(".drop-db").bind("click", function() {
    return double_confirm("Are you sure you want to drop this database?",
                          "This will delete all data in this database.  Are you really sure?")
  });
  
  $(".drop-indexes").bind("click", function() {
    return confirm("Are you sure you want to drop these indexes?")
  });

  $(".fields button").bind("click", function() {
    var $el = $(this).parent().next("p").clone();
    var split_id = $el.find("input").attr("id").split("-");
    var field_num = parseInt(split_id[1]) + 1;
    var new_id = split_id[0] + "-" + field_num.toString();

    $el.attr("id", new_id);
    $el.find("input").attr("value", "");
    $("div.fields").append($el);
    return false;
  })

  $(".new-collection-form form input[type=submit]").formValidator({
    scope: "form",
    errorClass: "ui-state-error"
  })

  $(".new-index-form form input[type=submit]").formValidator({
    scope: "form",
    errorClass: "ui-state-error"
  })

  $(".clickable-header").click(function() {
    $(this).next("div").toggle();
  })

  $(".ui-button").hover(function() {
    $(this).toggleClass("ui-state-hover");
  })

  $("#add-field").click(function() {
    var rem_button = "<button class=\"remove-field ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only\"><span class=\"ui-button-text\">Remove</span></button>"
    var field = $(".def-field").clone();
    field.removeClass("def-field").addClass("field");
    field.append(rem_button);
    $("#fields").append(field);
    return false;
  })

  $(".remove-field").live("click", function() {
    $(this).parent().remove();
  })
})
