<?php
header("Content-Type:text/xml");
include ("DBconfig.php");
//$residenceID = $_POST['residenceID'];
$residenceID = 10;

$result = mysqli_query($con, "SELECT * FROM unit WHERE residenceID = '$residenceID' and availability = 'available'") or die(mysqli_error());

$_xml = '<?xml version="1.0"?>';
$_xml .="<units>";

while($row=mysqli_fetch_assoc($result)) {
	$_xml .="<unit>";
	$_xml .="<unitNo>".$row['unitNo']."</unitNo>";
	$_xml .="<availability>".$row['availability']."</availability>";
	$_xml .="</unit>";	
}
$_xml.="</units>";
$xmlObject = new SimpleXMLElement($_xml);

print $xmlObject->asXML();
	//print $xmlObject->asXML("SSS.xml");
?>