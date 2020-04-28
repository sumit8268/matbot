<?php  
  
$con = mysqli_connect('localhost','root','');  
  
if(!$con)  
{  
    echo 'not connect to the server';  
}  
if(!mysqli_select_db($con,'matbot'))  
{  
    echo 'database not selected';  
}  

@$source = $_POST['source'];
@$dest = $_POST['dest'];




if($source != '' && $dest != ''){
    $sql = "INSERT INTO current VALUES ('$source','$dest')";  
}
    @$myobj->success = 'Not inserted';

    if(@!mysqli_query($con,$sql)) 
    {  
        $myobj["success"] = 'Not inserted'; 
        $myobj["free_bot"] = 'false';   
    }  
    else  
    {  
        $myobj["success"] = 'Data Inserted';
        $myobj["free_bot"] = 'true';  
    }

    $myjson = json_encode($myobj);
    echo $myjson;

?>