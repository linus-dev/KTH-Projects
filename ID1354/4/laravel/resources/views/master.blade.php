<!DOCTYPE html>
<html lang="en">
<head>
  <title>Linus' fabulously tasty cuisine</title>
  <link rel="stylesheet" href="{{ asset('css/reset.css') }}"> 
  <link rel="stylesheet" href="{{ asset('css/style.css') }}"> 
  <script src="{{ asset('js/jquery-3.3.1.js') }}"></script>
  <script src="https://d3js.org/d3.v5.js"></script>
  <link href="https://fonts.googleapis.com/css?family=Roboto|Bungee+Inline|Faster+One|Rock+Salt" rel="stylesheet"> 
  <link href="https://fonts.googleapis.com/css?family=Bungee+Shade|Permanent+Marker" rel="stylesheet"> 
  <meta name="csrf-token" content="{{ csrf_token() }}" />
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
@if (!Auth::check())
        <form id="auth_login">
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
          <button type="submit" class="btn btn-primary">
            {{ __('Login') }}
          </button>
        </form>
@else
      <form id="auth_logout">
        @csrf
        <input class="btn" type="submit" value="Logout" name="logout">
      </form>
@endif
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
    var CSRF_TOKEN = $('meta[name="csrf-token"]').attr('content');
    $('#auth_login').on('submit', function (e) {
      var data = {
        email: $('#email').val(),
        password: $('#password').val()
      };

      $.ajax({
        headers: {
          'X-CSRF-TOKEN': CSRF_TOKEN 
        },
        type : 'POST',
        url : '{{ route("login") }}',
        data: data,   
        success : function(d) {
          location.reload(); 
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
          console.log(XMLHttpRequest);
          console.log(textStatus);
          console.log(errorThrown);
        }
      });
      e.preventDefault();
    });
    
    $('#auth_logout').on('submit', function (e) {
      $.ajax({
        headers: {
          'X-CSRF-TOKEN': CSRF_TOKEN 
        },
        type : 'POST',
        url : '{{ route("logout") }}',
        data: {},   
        success : function(d) {
          location.reload(); 
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
          console.log(XMLHttpRequest);
          console.log(textStatus);
          console.log(errorThrown);
        }
      });
      e.preventDefault();
    });
  </script>

</body>
</html>
