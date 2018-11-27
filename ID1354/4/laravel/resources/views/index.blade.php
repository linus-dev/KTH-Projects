@extends('master')
@section('header')
  <link rel="stylesheet" href="{{ asset('css/index.css') }}"> 
@endsection
@section('content')
<div id="flex"> 
  <img id="linus" alt="Meh" src="{{ asset('images/linus.jpg') }}"/>
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
@endsection
