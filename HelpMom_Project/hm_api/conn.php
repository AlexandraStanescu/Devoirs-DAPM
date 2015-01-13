<?php

$servername = "localhost";
$username = "hmom";
$password = "123xt3nding@13";

// Create connection
$conn = mysql_connect($servername, $username, $password);

// Check connection
if (!$conn) {
    $error = "Connection failed: " . mysql_error();
} 

$db_selected = mysql_select_db('hmom', $conn);
if (!$db_selected) {
    $error =  'Can\'t connect to db : ' . mysql_error();
}

?>