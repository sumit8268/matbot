<?php

include('db_connection.php'); 

global $conn ;
mysqli_set_charset($conn, 'utf8');

$source = $_POST['source'];
$dest = $_POST['dest'];
if(!empty($source) && !empty($dest)){
$sql = "INSERT INTO current (source,destination) VALUES ('$source','$dest')";

if ($conn->query($sql) === TRUE) {
    $flag['success']="Data inserted successfully";
    $flag['free_bot']="true";
} else {
    $flag['success']="Data insertion failed";
    $flag['free_bot']=FALSE;
}
} else {
    $flag['success']="Data insertion failed";
    $flag['free_bot']="false";
}
print(json_encode($flag));
?>