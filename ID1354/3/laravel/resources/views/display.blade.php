@extends('master')
@section('header')
<link rel="stylesheet" href="{{ url('css/recipe.css') }}"> 
<meta name="csrf-token" content="{{ csrf_token() }}" />
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
@if (Auth::check())
      <form id="comment_form">
        <textarea class="text" id="comment_txt" name="comment" rows="4" cols="50"></textarea> 
        <input class="btn" id="comment_post" type="submit" value="POST!" name="comment_post">
      </form> 
@endif
    </div>
  </div>
  <script>
    $('#recipes_nav').addClass('glow-pink-txt'); 
    
    /* Current user. */
@if (Auth::check())
    const user = '{{ Auth::user()->email }}';
@endif
    const recipe_id = <?php echo $DISH_ID ?>;
    var CSRF_TOKEN = $('meta[name="csrf-token"]').attr('content');
    
    /* COMMENT - POST. */
    $('#comment_form').on('submit', function (e) {
      //ajax call here
      $.ajax({
        headers: {
          'X-CSRF-TOKEN': CSRF_TOKEN 
        },
        type : 'POST',
        url : '{{ url("comment/post") }}',
        data: {
          dish_id : recipe_id,
          txt: $('#comment_txt').val()
        },
        success : function(d) {
          location.reload(); 
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
          console.log(errorThrown);
          console.log(textStatus);
          console.log(XMLHttpRequest);
        }
      });
      e.preventDefault();
    });

    /* COMMENT - BUILD */
    $.ajax({
      type : 'GET',
      url : '{{ url("comment/get_all") }}',
      dataType : 'json',
      data: {
        dish_id : recipe_id
      },
      success : printComments,
      error : function(XMLHttpRequest, textStatus, errorThrown) {
        console.log(errorThrown);
        console.log(textStatus);
        console.log(XMLHttpRequest);
      }
    });

    function printComments(data) {
      /* Build comment section. */
      console.log(data)
      var wrapper = d3.select('#comments');
      for (var i in data) {
        var comment_user = data[i]['user'];
        var comment = data[i]['comment'];
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
            .attr("cmt_id", data[i]['id'])
            .on('click', function (el) {
              $.ajax({
                headers: {
                  'X-CSRF-TOKEN': CSRF_TOKEN 
                },
                type: 'DELETE',
                url: '{{ url("comment/delete") }}',
                data: { 
                  'cmt_id': d3.select(this).attr("cmt_id") 
                },
                success: function(msg){
                  location.reload(); 
                },
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                  console.log(errorThrown);
                  console.log(textStatus);
                  console.log(XMLHttpRequest);
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
