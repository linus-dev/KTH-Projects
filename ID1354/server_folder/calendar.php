<!DOCTYPE html>
<html lang="en">
<head>
  <title>Linus' fabulously tasty cuisine</title>
  <link rel="stylesheet" href="/static/css/reset.css"> 
  <link rel="stylesheet" href="/static/css/style.css"> 
  <script src="/static/jquery-3.3.1.js"></script>
  <script src="https://d3js.org/d3.v5.js"></script>
  <link href="https://fonts.googleapis.com/css?family=Roboto|Bungee+Inline|Faster+One|Rock+Salt" rel="stylesheet"> 
  <link href="https://fonts.googleapis.com/css?family=Bungee+Shade|Permanent+Marker" rel="stylesheet"> 
  
<link rel="stylesheet" href="/static/css/cal.css"> 

</head>
<body>
  <div id="content">
    <h2 id="title" class="glow">Linus' Fabulously Tasty cuisine</h2>
    <ul id="navbar">
      <li><a id="home_nav" href="/">Home</a></li>
      <li><a id="recipes_nav" href="/recipes.php">Dishes</a></li>
      <li><a id="calendar_nav" href="/calendar.php">Calendar</a></li>
    </ul>
    <?php include('login.php') ?>
    <div id="content_inc">
      <div id="calendar">
      </div>
      <script>
        /* Set navigation active. */
        //$('#home_nav').addClass('active_nav'); 
        $('#calendar_nav').addClass('glow-pink-txt'); 
        //$('#calendar_nav').addClass('glow'); 
        
        var cur_week = 0;
        var picture_days = [1, 9, 13, 19, 27];
        
        $.getJSON("recipes/recipes.json", function(data) {
          var recipes = data;
          for (var i = 0; i < 5; i++) {
            var row = $("#calendar").append('<div class="week">');
            for (var j = 1; j < 8; j++) {
              var cell = '<div class="day">';
              cell += '<span class="day">' + ((cur_week + j) > 31 ? '' : (cur_week + j)) + '</span>';
              if (picture_days.indexOf(cur_week + j) != -1) {
                var index = Math.floor(Math.random() * recipes.length);
                var recipe = recipes[index];
                cell += '<div class="helper">';
                cell += '<a href="/recipes/' + index + '">';
                cell += '<img width=128 align="top" src=' + recipe.image + '></img>';
                cell += '</a>'; 
                cell += '</div>';
              }
              cell += '</div>';
              row.append(cell);
            }
            row.after('</div>');
            cur_week += 7;
          }
        });
      </script>
    </div>
  </div>
  <script>
    
    window.onload = function () {
      $('#content_inc').fadeIn(1000);
    }
  </script>
</body>
</html>
