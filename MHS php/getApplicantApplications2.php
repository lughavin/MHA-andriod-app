<?php

include("DBconfig.php");

$applicantID = $_POST['applicantID'];
//$applicantID = 'luwi';


if (!empty($applicantID)) {

	$result = $con->query("SELECT * from application WHERE applicantID = '$applicantID'");

	$outp = '{"manageRecord":[';
	$aa=0;
	while($myRow = $result->fetch_array()){
		$residenceID = $myRow['residenceID'];
		$status = $myRow['status'];

		$getResidenceDetails = $con->query("SELECT * FROM residence WHERE residenceID='$residenceID'");
		while($myRow2 = $getResidenceDetails->fetch_array()){
			$numOfUnit = $myRow2['numUnit'];
			$monthlyRental = $myRow2['monthlyRental'];
			$address = $myRow2['address'];
		}

		if($aa > 0)
		{
			$outp .= ",";
		}

		$outp .= '{"residenceID":"'.$residenceID.'",';
		$outp .= '"status":"'.$status.'",';
		$outp .= '"numOfUnit":"'.$numOfUnit.'",';
		$outp .= '"address":"'.$address.'",';
		$outp .= '"monthlyRental":"'.$monthlyRental.'"}';

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