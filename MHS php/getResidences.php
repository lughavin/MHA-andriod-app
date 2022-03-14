<?php 
include "DBconfig.php";

$staffID = $_POST['staffID'];
//$staffID = "Luwi";


//$bankID = '1';

$result = $con->query("SELECT * from residence WHERE staffID = '$staffID'");

$outp = '{"manageRecord":[';
$aa=0;

while($row = $result->fetch_array()){

	if($aa > 0)
	{
		$outp .= ",";
	}

	
	$outp .= '{"address":"'.$row["address"].'",';
	$outp .= '"numOfUnit":"'.$row["numUnit"].'",';
	$outp .= '"residenceID":"'.$row["residenceID"].'",';
	$outp .= '"sizePerUnit":"'.$row["sizePerUnit"].'",';
	$outp .= '"monthlyRental":"'.$row["monthlyRental"].'"}';



	$aa = 1;
}




$con->close();

$outp .= ']}';

echo($outp);

?>