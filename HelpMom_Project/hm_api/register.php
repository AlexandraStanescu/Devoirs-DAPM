<?php

$error = "";

require_once('conn.php');

$user = $_POST['email'];
$pass = $_POST['password'];

$query = "INSERT INTO users (username, password) VALUES('" . $user . "', '" . $pass . "');";
$qres = mysql_query($query, $conn);
if (!$qres) {
	$error = "db error: " . mysql_error() . " -- " . $query;
}

$result = [];

if ($error != "") {
	$result['message'] = $error;	
} else {
	$result['message'] = "ok";
}

echo json_encode($result);

?>