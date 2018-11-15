<?php
$DISH_ID = $_REQUEST['dish_id'];

$dbconn = pg_connect("host=localhost dbname=ID1354 user=linus password=hej")
  or die('Could not connect: ' . pg_last_error());

$result = pg_query_params($dbconn,
                          'SELECT * FROM comments WHERE recipe=$1',
                          array($DISH_ID)); 

$myarray = array();
while ($row = pg_fetch_row($result)) {
  $myarray[] = $row;
}

header('Content-Type: application/json');
echo json_encode($myarray);
