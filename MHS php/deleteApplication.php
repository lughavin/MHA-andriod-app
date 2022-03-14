<?php
include("DBconfig.php");

/*
$address = $_POST['address'];
$numOfUnit = $_POST['numOfUnit'];
$sizePerUnit = $_POST['sizePerUnit'];
$monthlyRental = $_POST['monthlyRental'];
$staffID = $_POST['staffID'];
*/

//$applicationID =$_POST['applicationID'];
$applicantID = 'hothaifa';


	$sql_delete_application = "DELETE  FROM application WHERE applicantID = '$applicantID'";

	//Check for duplicates
	if ($con->query($sql_delete_application) === TRUE) {
		echo 'true';
	}
	else {
		echo "false";
	}
 
?>