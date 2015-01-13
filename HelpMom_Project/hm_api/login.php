<?php

$error = "";

$result = [];

require_once('conn.php');

$user = $_POST['email'];
$pass = $_POST['pass'];

/*
$result = [];
$result['message'] = "ok";
$result['email'] = $_POST['email'];
$result['pass'] = $_POST['pass'];
*/

$query = "SELECT id FROM users WHERE username = '" . $user . "' AND password = '" . $pass . "';";
$qres = mysql_query($query, $conn);
if (!$qres) {
	$error = "db error: " . mysql_error() . " -- " . $query;
}

$no = mysql_num_rows($qres);
if ($no > 0 ) {
	$row = mysql_fetch_assoc($qres);
	$result['uid'] = (int)$row['id'];
} else {
	$error = "Wrong user / password combination";
}


if ($error != "") {
	$result['message'] = $error;	
} else {
	$result['message'] = "ok";
}

echo json_encode($result);
?>