<!DOCTYPE html>
<html lang="en">
<?php session_start(); ?>
<head>
  <title>Linus' fabulously tasty cuisine</title>
  <link rel="stylesheet" href="{{ asset('css/reset.css') }}"> 
  <link rel="stylesheet" href="{{ asset('css/style.css') }}"> 
  <script src="{{ asset('js/jquery-3.3.1.js') }}"></script>
  <script src="https://d3js.org/d3.v5.js"></script>
  <link href="https://fonts.googleapis.com/css?family=Roboto|Bungee+Inline|Faster+One|Rock+Salt" rel="stylesheet"> 
  <link href="https://fonts.googleapis.com/css?family=Bungee+Shade|Permanent+Marker" rel="stylesheet"> 
  @yield('header')
</head>
<body>
  <div id="content">
    <h2 id="title" class="glow">Linus' Fabulously Tasty cuisine</h2>
    <ul id="navbar">
      <li><a id="home_nav" href="{{ route('home') }}">Home</a></li>
      <li><a id="recipes_nav" href="{{ route('dishes') }}">Dishes</a></li>
      <li><a id="calendar_nav" href=" {{ route('cal') }}">Calendar</a></li>
    </ul>
    <div id="login_bar">
      <div id="login_form">
        <form method="POST" action="{{ route('login') }}">
          @csrf
          <input id="email" type="email" class="text form-control{{ $errors->has('email') ? ' is-invalid' : '' }}" name="email" value="{{ old('email') }}" required autofocus>
          @if ($errors->has('email'))
              <span class="invalid-feedback" role="alert">
                  <strong>{{ $errors->first('email') }}</strong>
              </span>
          @endif
          <input id="password" type="password" class="text form-control{{ $errors->has('password') ? ' is-invalid' : '' }}" name="password" required>
          @if ($errors->has('password'))
              <span class="invalid-feedback" role="alert">
                  <strong>{{ $errors->first('password') }}</strong>
              </span>
          @endif
              <input class="form-check-input" type="checkbox" name="remember" id="remember" {{ old('remember') ? 'checked' : '' }}>
              <label class="form-check-label" for="remember">
                  {{ __('Remember Me') }}
              </label>
          <button type="submit" class="btn btn-primary">
              {{ __('Login') }}
          </button>
        </form>
      </div>
    </div>
    <div id="content_inc">
      @yield('content')
    </div>
  </div>
  <script>
    window.onload = function () {
      $('#content_inc').fadeIn(1000);
    }
  </script>
  
  <script>
    $('#auth_form').on('submit', function (e) {
      var data = {
        username: $('#usr_name').val(),
        password: $('#usr_psw').val()
      };

      $.ajax({
        type : 'POST',
        url : 'login_ajax.php',
        data: data,   
        success : function(d) {
          alert(d);
          location.reload(); 
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
        }
      });
      e.preventDefault();
    });
  </script>

</body>
</html>
