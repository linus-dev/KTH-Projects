@extends('master')
@section('header')
<link rel="stylesheet" href="{{ asset('css/recipes.css') }}"> 
@endsection
@section('content')
<div id="recipes">
</div>
<script>
  /* Set navigation active. */
  //$('#home_nav').addClass('active_nav'); 
  $('#recipes_nav').addClass('glow-pink-txt'); 
  //$('#recipes_nav').addClass('glow'); 
  
  $.getJSON("{{ asset('json/recipes.json') }}", function(data) {
    var recipes = data;
    /* D3 because, easy money. */ 
    var wrapper = d3.select('#recipes');
    var row = wrapper.append('div');
    var current_cell = 0; 
    for (var i in recipes) {
      row.append('div')
         .attr('class', 'dish')
         .append('a')
         .attr('href', '/dishes/' + i)
         .attr('class', 'glow-pink-txt')
         .text(recipes[i].dish);
      current_cell++;
      if (current_cell % 2 == 0) {
        row = wrapper.append('div');
      }
    }

  });
</script>
@endsection
