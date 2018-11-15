<?php
  session_start();
  $_SESSION["test"] = 2;
?>
<div id="login_bar">
  <div id="login_form">
    <form id="auth_form">
<?php if (!isset($_SESSION['logged_in']) || !$_SESSION['logged_in']) : ?>
      <input id="usr_name" class="text" placeholder="Username" type="text" name="username" required>
      <input id="usr_psw" class="text" placeholder="Password" type="password" name="password" required>
      <input class="btn" type="submit" value="Login" name="login">
<?php else : ?>
      <input class="btn" type="submit" value="Logout" name="logout">
<?php endif; ?>
    </form> 
  </div>
</div>
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
      location.reload(); 
    },
    error : function(XMLHttpRequest, textStatus, errorThrown) {
    }
  });
  e.preventDefault();
});
</script>

