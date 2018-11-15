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
  
  <link rel="stylesheet" href="/static/css/recipes.css"> 

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
      <div id="recipes">
      </div>
      <script>
        /* Set navigation active. */
        //$('#home_nav').addClass('active_nav'); 
        $('#recipes_nav').addClass('glow-pink-txt'); 
        //$('#recipes_nav').addClass('glow'); 
        
        $.getJSON("recipes/recipes.json", function(data) {
          var recipes = data;
          /* D3 because, easy money. */ 
          var wrapper = d3.select('#recipes');
          var row = wrapper.append('div');
          var current_cell = 0; 
          for (var i in recipes) {
            row.append('div')
               .attr('class', 'dish')
               .append('a')
               .attr('href', '/recipe.php?dish=' + i)
               .attr('class', 'glow-pink-txt')
               .text(recipes[i].dish);
            current_cell++;
            if (current_cell % 2 == 0) {
              row = wrapper.append('div');
            }
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
