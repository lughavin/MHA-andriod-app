<?php
include("DBconfig.php");

/*
$address = $_POST['address'];
$numOfUnit = $_POST['numOfUnit'];
$sizePerUnit = $_POST['sizePerUnit'];
$monthlyRental = $_POST['monthlyRental'];
$staffID = $_POST['staffID'];
*/

//$residenceID ='1';
$residenceID =$_POST['residenceID'];
//$residenceID = '20';

//Check whether fields are not empty

	
	$sql_delete_residence = "DELETE  FROM residence WHERE residenceID = '$residenceID'";

	//Check for duplicates
	if ($con->query($sql_delete_residence) === TRUE) {
		$sql_delete_application = "DELETE FROM application WHERE residenceID='$residenceID'";
		$con->query($sql_delete_application);
		echo 'true';
	}
	else {
		echo "false";
	}
 
?>