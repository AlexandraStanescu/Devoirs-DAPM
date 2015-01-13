<?php

$error = "";

$result = [];

require_once('conn.php');

$posts = [];


$topic_id = $_POST['tid'];


$query = "SELECT * FROM post WHERE topic_id = " . $topic_id . ";";

$qres = mysql_query($query, $conn);
if (!$qres) {
	$error = "db error: " . mysql_error() . " -- " . $query;
}

$no = mysql_num_rows($qres);
if ($no > 0 ) {
	$row = mysql_fetch_assoc($qres);
	
	do {
		array_push($posts, array('id' => (int)$row['id'], 'title' => $row['title'], 'body' => $row['body']));
	} while ($row = mysql_fetch_assoc($qres));
}

$result['posts'] = $posts;

if ($error != "") {
	$result['message'] = $error;	
} else {
	$result['message'] = "ok";
}

echo json_encode($result);


?>