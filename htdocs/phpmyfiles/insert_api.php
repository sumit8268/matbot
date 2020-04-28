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

@$source = $_POST['source1'];
@$dest = $_POST['dest1'];
@$color1 = $_POST['color1'];
@$color2 = $_POST['color2'];
@$color3 = $_POST['color3'];
@$color4 = $_POST['color4'];
@$color5 = $_POST['color5'];
@$color6 = $_POST['color6'];
@$color7 = $_POST['color7'];
@$color8 = $_POST['color8'];



if($source != '' && $dest != '' && $color1 !=''){
    $sql = "INSERT INTO points VALUES ('$source','$dest','$color1','$color2','$color3','$color4','$color5','$color6','$color7','$color8')";  
}
    @$myobj->success = 'Not inserted';

    if(@!mysqli_query($con,$sql)) 
    {  
        $myobj->success = 'Not inserted';  
    }  
    else  
    {  
        $myobj->success = 'Data Inserted';  
    }

    $myjson = json_encode($myobj);
    echo $myjson;

?>