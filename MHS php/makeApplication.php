<?php
include("DBconfig.php");

$applicationDate = date("Y/m/d");
$residenceID = $_POST['residenceID'];
$date = $_POST['date'];
$d = date_parse_from_format("Y-m-d", $date);
$month = $d["month"];
$year = $d["year"];
$applicantID = $_POST['applicantID'];
$staffID = $_POST['staffID'];
/*
$applicationDate = date("Y-m-d");
$residenceID = (int)'23';
$date = '2019-12-24';
$d = date_parse_from_format("Y-m-d", $date);
$month = $d["month"];
$year = $d["year"];
$applicantID = 'a';
$staffID = '1';
*/


//$usertype = 'Housing Officer';

//Check whether fields are not empty
if (!empty($residenceID) && !empty($date) && !empty($applicantID)) {
	$getStaffID = $con->query("SELECT staffID FROM residence WHERE residenceID='$residenceID'");
	while($myRow2 = $getStaffID->fetch_array()){
			$staffID = $myRow2['staffID'];
	}

	$sql_insert = "INSERT into application (applicationDate, requiredMonth, requiredYear, status, residenceID, applicantID, staffID) values('$applicationDate', '$month', '$year', 'New', '$residenceID', '$applicantID', '$staffID')";

	
	if ($con->query($sql_insert) === TRUE) {
		echo "true";
	}

	else{
		echo "false";
	}
 }
 else{
 	echo "empty";
 }

mysqli_close($con);

?>