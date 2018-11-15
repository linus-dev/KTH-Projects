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
  
  <link rel="stylesheet" href="/static/css/index.css"> 

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
      <div id="flex"> 
        <img id="linus" alt="Meh" src="/static/linus.jpg"/>
        <p id="welcome">
          DO YOU WANT SOME BITCHIN' NEW GRUB?
          THEN YOU'VE COME TO THE RIGHT PLACE!

          WELCOME TO <span class="logo glow-pink-txt">Linus' Fabulously Tasty cuisine!</span>

          That's me, on the left, <span class="glow-txt">majestic</span>, right?
          My food is exactly the same, majestic, forged from passion and the most organic homegrown produce, perfected through years of trial and hardship.

          So put some synthwave on blast and follow along for this journey of taste and bewilderment.
        </p>
      </div>
      <script>
        $('#home_nav').addClass('glow-pink-txt'); 
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
