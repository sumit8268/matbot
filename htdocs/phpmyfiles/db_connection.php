<?php

define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','matbot');


$conn = new mysqli(HOST, USER, PASS, DB);
 
// check connection
if ($conn->connect_error) {
  trigger_error('Database connection failed: '  . $conn->connect_error, E_USER_ERROR);
}
?>