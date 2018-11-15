<?php
session_start();
$CMT_ID = $_REQUEST['cmt_id'];
if (isset($_SESSION['logged_in']) && $_SESSION['logged_in']) {
  $dbconn = pg_connect("host=localhost dbname=ID1354 user=linus password=hej")
    or die('Could not connect: ' . pg_last_error());
  $result = pg_query_params($dbconn,
                            'SELECT * FROM comments WHERE user=$1 and id=$2',
                            array($_SESSION['username'], $CMT_ID)); 
  if (pg_num_rows($result)) {
    $result = pg_query_params($dbconn,
                              'DELETE FROM comments WHERE id=$1',
                              array($CMT_ID)); 
  }
}
?>
