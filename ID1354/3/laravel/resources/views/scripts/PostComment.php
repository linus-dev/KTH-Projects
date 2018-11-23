<?php
$DISH_ID = $_REQUEST['dish_id'];
$USR_ID = $_REQUEST['usr'];
$CMT_TXT = $_REQUEST['txt'];

$dbconn = pg_connect("host=localhost dbname=ID1354 user=linus password=hej")
  or die('Could not connect: ' . pg_last_error());

$result = pg_query_params($dbconn,
                          'INSERT INTO comments("user", comment, recipe) VALUES ($1, $2, $3)',
                          array($USR_ID, $CMT_TXT, $DISH_ID)); 

