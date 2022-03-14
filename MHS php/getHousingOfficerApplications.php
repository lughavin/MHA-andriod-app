<?php

include("DBconfig.php");

$staffID = $_POST['staffID'];
//$staffID = 'umar';


if (!empty($staffID)) {

	$result = $con->query("SELECT * from application WHERE staffID = '$staffID'");

	$outp = '{"manageRecord":[';
	$aa=0;
	while($myRow = $result->fetch_array()){
		$residenceID = $myRow['residenceID'];
		$applicantID = $myRow['applicantID'];
		$requiredMonth = $myRow['requiredMonth'];
		$requiredYear = $myRow['requiredYear'];
		$applicationID = $myRow['applicationID'];

		$getResidenceDetails = $con->query("SELECT * FROM residence WHERE residenceID='$residenceID'");
		while($myRow2 = $getResidenceDetails->fetch_array()){
			$numOfUnit = $myRow2['numUnit'];
			$monthlyRental = $myRow2['monthlyRental'];
		}

		$getApplicantDetails = $con->query("SELECT * FROM applicant WHERE username ='$applicantID'");
		while($myRow3 = $getApplicantDetails->fetch_array()){
			$monthlyIncome = $myRow3['monthlyIncome'];
		}

		if($aa > 0)
		{
			$outp .= ",";
		}

		$outp .= '{"residenceID":"'.$residenceID.'",';
		$outp .= '"applicantID":"'.$applicantID.'",';
		$outp .= '"requiredYear":"'.$requiredYear.'",';
		$outp .= '"requiredMonth":"'.$requiredMonth.'",';
		$outp .= '"numOfUnit":"'.$numOfUnit.'",';
		$outp .= '"monthlyRental":"'.$monthlyRental.'",';
		$outp .= '"applicationID":"'.$applicationID.'",';
		$outp .= '"monthlyIncome":"'.$monthlyIncome.'"}';

		$aa = 1;
	}


    $outp .= ']}';

	echo($outp);
}
else{
	echo "false";
}

mysqli_close($con);

?>