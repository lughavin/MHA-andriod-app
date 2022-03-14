<?php 
include "DBconfig.php";

//$staffID = "g";

$result = $con->query("SELECT * from residence");

$outp = '{"manageRecord":[';
$aa=0;

while($row = $result->fetch_array()){

	if($aa > 0)
	{
		$outp .= ",";
	}

	
	$outp .= '{"address":"'.$row["address"].'",';
	$outp .= '"residenceID":"'.$row["residenceID"].'",';
	$outp .= '"numOfUnit":"'.$row["numUnit"].'",';
	$outp .= '"sizePerUnit":"'.$row["sizePerUnit"].'",';
	$outp .= '"monthlyRental":"'.$row["monthlyRental"].'"}';



	$aa = 1;
}




$con->close();

$outp .= ']}';

echo($outp);

?>