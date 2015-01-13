<?php

$error = "";

require_once('conn.php');

$tid = $_POST['tid'];
$uid = $_POST['uid'];
$title = $_POST['title'];
$body = $_POST['body'];


$query = "INSERT INTO post (topic_id, user_id, title, body) VALUES(" . $tid . ", " . $uid . ", '" . $title . "', '" . $body . "');";
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