<?php
include("DBconfig.php");


$address = $_POST['address'];
$numOfUnit = $_POST['numOfUnit'];
$sizePerUnit = $_POST['sizePerUnit'];
$monthlyRental = $_POST['monthlyRental'];
$staffID = $_POST['staffID'];
/*
$address = 'Beltepa';
$numOfUnit = '3';
$sizePerUnit = '100';
$monthlyRental = '999';
$staffID = 'umar';
*/
//Check whether fields are not empty
if (!empty($address) && !empty($numOfUnit) && !empty($sizePerUnit) && !empty($monthlyRental)) {
	
	$sql_insert_residence = "INSERT into residence (address, numUnit, sizePerUnit, monthlyRental, staffID) values('$address', '$numOfUnit', '$sizePerUnit', '$monthlyRental', '$staffID')";

	//Check for duplicates
	if ($con->query($sql_insert_residence) === TRUE) {
		echo 'true';
		$residenceID = (int)$con->insert_id;
		$counter = 1;
		while ( $counter <= (int)$numOfUnit) {
			$sql_insert_unit = "INSERT into unit (availability, residenceID) values('available', '$residenceID')";
			$con->query($sql_insert_unit);
			$counter++;
		}
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