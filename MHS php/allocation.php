<?php
include("DBconfig.php");

$status = $_POST['status'];
//$status = 'Rejected';

/*
$fromDate = '2019-07-08';
$duration = '18 months';
$unitNo = '4';
$applicationID = '10';
*/

//Check whether fields are not empty
if ($status === 'Rejected') {
	$applicantID = $_POST['applicantID'];
	//$applicantID = 'a';
	$reject_application_status_for_applicant = "UPDATE application SET status = '$status' WHERE applicantID = '$applicantID'";
	$con->query($reject_application_status_for_applicant);
	echo 'true';
}
else if ($status === 'Approved'){
	$applicantID = $_POST['applicantID'];
	$fromDate = $_POST['fromDate'];
	$duration = $_POST['duration'];
	$unitNo = $_POST['unitNo'];
	$applicationID = $_POST['applicationID'];
	/*
	$applicantID ='a';
	$fromDate = '2019-11-11';
	$duration = '12 months';
	$unitNo = '4';
	$applicationID = '9';
	*/
	$endDate = date('Y-m-d', strtotime("+$duration", strtotime($fromDate)));
	$sql_insert_allocation = "INSERT into allocation (fromDate, duration, endDate, unitNo, applicationID) values('$fromDate', '$duration', '$endDate', '$unitNo', '$applicationID')";

	//Check for duplicates
	if ($con->query($sql_insert_allocation) === TRUE) {
		$update_application_status = "UPDATE application SET status = '$status' WHERE applicationID = '$applicationID'";
		$update_application_status_for_others = "UPDATE application SET status = 'Rejected' WHERE applicantID = '$applicantID' AND applicationID != '$applicationID'";
		$con->query($update_application_status);
		$con->query($update_application_status_for_others);
		echo 'true';
	}
	else{
		echo "false";
	}
}
else if ($status === 'Waitlist'){
	$applicationID = $_POST['applicationID'];
	//$applicationID = '9';
	$waitlist_application_status_for_applicant = "UPDATE application SET status = '$status' WHERE applicationID = '$applicationID'";
	$con->query($waitlist_application_status_for_applicant);
	echo 'true';
	
}

mysqli_close($con);

?>