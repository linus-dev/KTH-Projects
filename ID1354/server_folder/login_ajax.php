<?php
  session_start();
  $msg = '';
  
  if (isset($_REQUEST['username']) && isset($_REQUEST['password'])) {
    $USR = $_REQUEST['username'];
    $PSW = $_REQUEST['password'];
  }
  
  if (!isset($_SESSION['logged_in']) || !$_SESSION['logged_in']) {
    if ($USR != '' && $PSW != '') {
      $dbconn = pg_connect("host=localhost dbname=ID1354 user=linus password=hej")
        or die('Could not connect: ' . pg_last_error());
      $result = pg_query_params($dbconn,
                                'SELECT * FROM users WHERE user=$1 and psw=$2',
                                array($USR, $PSW)); 
      if (pg_num_rows($result)) {
        $_SESSION['username'] = $USR;
        $_SESSION['logged_in'] = true;
        
        $msg = 'You have entered valid username and password';
      } else {
        $msg = 'Wrong username or password';
      }
    }
  } else {
    $msg = 'You logged out!';
    $_SESSION['username'] = '';
    $_SESSION['logged_in'] = false;
  }

echo $msg;
