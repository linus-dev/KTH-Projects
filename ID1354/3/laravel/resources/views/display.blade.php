@extends('master')
@section('header')
<link rel="stylesheet" href="{{ url('css/recipe.css') }}"> 
@endsection

@section('content')
  <?php 
    /* After session start in login.php! */
    $recipe_file = 'json/recipes.json';
    $string = file_get_contents($recipe_file);
    $json = json_decode($string, true);

  ?>
  <div id="recipe">
    <!-- Looks better without this 
    <img id="dish_img" width=256 src=""></img>
    -->
    <div id="dish_info">
      <span id="dish"></span>
      <span id="prep_time"></span>
      <span id="servings"></span>
    </div>
    <span id="ing">
      Ingredients
    </span>
    <ul id="ingredients">
    </ul>
    <span id="inst">
      Instructions
    </span>
    <p id="instructions">
    <?php
      $filename = "recipes/" . ($json[$DISH_ID]["instruction"]);
      $text = file_get_contents($filename);
      echo $text; 
    ?>
    </p>
    <div id="comments">
    <span id="comments_txt">
      Comments
    </span>
      <?php if (isset($_SESSION['logged_in']) && $_SESSION['logged_in']) : ?>
      <form id="comment_form">
        <textarea class="text" id="comment_txt" name="comment" rows="4" cols="50"></textarea> 
        <input class="btn" id="comment_post" type="submit" value="POST!" name="comment_post">
      </form> 
      <?php endif; ?>
    </div>
  </div>
  <script>
    $('#recipes_nav').addClass('glow-pink-txt'); 
    
    /* Current user. */
    const user = "<?php echo (isset($_SESSION['username']) ? $_SESSION['username'] : ""); ?>"; 
    const recipe_id = <?php echo $DISH_ID ?>;

    /* COMMENT - POST. */
    $('#comment_form').on('submit', function (e) {
      //ajax call here
      $.ajax({
        type : 'POST',
        url : 'PostComment.php',
        data: {
          dish_id : recipe_id,
          usr: user,
          txt: $('#comment_txt').val()
        },
        success : function(d) {
          location.reload(); 
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {}
      });
      e.preventDefault();
    });

    /* COMMENT - BUILD */
    $.ajax({
      type : 'GET',
      url : 'GetComments.php',
      dataType : 'json',
      data: {
        dish_id : recipe_id
      },
      success : printComments,
      error : function(XMLHttpRequest, textStatus, errorThrown) {}
    });

    function printComments(data) {
      /* Build comment section. */
      var wrapper = d3.select('#comments');
      for (var i in data) {
        var comment_user = data[i][0];
        var comment = data[i][1];
        var row = wrapper.append('div').attr("class", "comment");
        var cm = row.append('span')
                    .text('\'' + comment + '\' - ' + comment_user);
        
        /* If the user is logged in... */
        /* DELETE COMMENT BTN */
        if (user == comment_user) {
          cm.append('input')
            .attr('type', 'button')
            .attr('class', 'btn')
            .attr('name', 'del-comment')
            .attr('value', 'Delete')
            .attr("cmt_id", data[i][3])
            .on('click', function (el) {
              $.ajax({
                type: 'POST',
                url: '/delete.php',
                data: { 
                  'cmt_id': d3.select(this).attr("cmt_id") 
                },
                success: function(msg){
                  location.reload(); 
                }
              });
            });
        }
      }
    }
   
    /* Build recipes, this is only here with json files to display it is possible to do? */
    /* ajax request for json. */
    $.getJSON("{{ asset('json/recipes.json') }}", function(data) {
      var recipe = data[recipe_id]; 
      $('#dish').text(recipe.dish);
      $('#prep_time').text('Prep time: ' + recipe.prep);
      $('#servings').text('Servings: ' + recipe.servings);
      for (var key in recipe.ingredients) {
        $("#ingredients").append('<li>'+ key + ' - ' + recipe.ingredients[key] + '</li>');
      }
    });
  </script>
@endsection
