<?php
include("DBconfig.php");


$address = $_POST['address'];
$residenceID = $_POST['residenceID'];
$numOfUnit = $_POST['numOfUnit'];
$sizePerUnit = $_POST['sizePerUnit'];
$monthlyRental = $_POST['monthlyRental'];

//$staffID = $_POST['staffID'];

/*
$address = 'Desaa KIarar';
$numOfUnit = '3';
$sizePerUnit = '100';
$monthlyRental = '999';
$residenceID = '9';
*/
//Check whether fields are not empty
if (!empty($address) && !empty($numOfUnit) && !empty($sizePerUnit) && !empty($monthlyRental)) {
	
	$sql_update_residence = "UPDATE residence SET address='$address', numUnit='$numOfUnit', sizePerUnit='$sizePerUnit', monthlyRental='$monthlyRental' WHERE residenceID='$residenceID'";

	//Check for duplicates
	if ($con->query($sql_update_residence) === TRUE) {
		echo 'true';
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