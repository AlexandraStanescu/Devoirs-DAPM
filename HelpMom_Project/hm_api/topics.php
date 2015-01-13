<?php

$error = "";

$result = [];

require_once('conn.php');

$topics = [];





$query = "SELECT * FROM topics;";
$qres = mysql_query($query, $conn);
if (!$qres) {
	$error = "db error: " . mysql_error() . " -- " . $query;
}

$no = mysql_num_rows($qres);
if ($no > 0 ) {
	$row = mysql_fetch_assoc($qres);
	
	do {
		array_push($topics, array('id' => (int)$row['id'], 'title' => $row['title']));
	} while ($row = mysql_fetch_assoc($qres));
}

$result['topics'] = $topics;

if ($error != "") {
	$result['message'] = $error;	
} else {
	$result['message'] = "ok";
}

echo json_encode($result);

?>