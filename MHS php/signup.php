<?php
include("DBconfig.php");
/*
$username = 'jaja';
$password = 'www';
$fullname = 'fullname';
$email = 'myemail@gmail.com';
$monthlyincome = '123';
*/

$usertype = $_POST['userType'];
//$usertype = 'Housing Officer';

if ($usertype === 'Applicant') {
	$username = $_POST['username'];
	$password = $_POST['password'];
	$fullname = $_POST['fullname'];
	$email = $_POST['email'];
	$monthlyincome = $_POST['monthlyIncome'];

	//Check whether fields are not empty
	if (!empty($username) && !empty($password) && !empty($fullname) && !empty($email) && !empty($monthlyincome)) {
		
		$sql_insert = "INSERT into applicant values('$username', '$password', '$fullname', '$email', '$monthlyincome')";

		$dupesql_username = "SELECT * FROM applicant WHERE username = '$username'";
		$duperaw_username = $con->query($dupesql_username);

		//Check for duplicates
		if (mysqli_num_rows($duperaw_username) > 0) {
			echo "duplicate";
		}
		else {
			if ($con->query($sql_insert) === TRUE) {
				echo "true";
			}

			else{
				echo "false";
			}
		  }

	 }
	 else{
	 	echo "empty";
	 }
}
else {
	$username = $_POST['username'];
	$password = $_POST['password'];
	$fullname = $_POST['fullname'];
	/*
	$username = 'jaja';
	$password = 'www';
	$fullname = 'fullname';
	*/
	//Check whether fields are not empty
	if (!empty($username) && !empty($password) && !empty($fullname)) {
		
		$sql_insert = "INSERT into houseingofficer values('$username', '$password', '$fullname')";

		$dupesql_username = "SELECT * FROM houseingofficer WHERE username = '$username'";
		$duperaw_username = $con->query($dupesql_username);

		//Check for duplicates
		if (mysqli_num_rows($duperaw_username) > 0) {
			echo "duplicate";
		}
		else {
			if ($con->query($sql_insert) === TRUE) {
				echo "true";
			}

			else{
				echo "false";
			}
		  }

	 }
	 else{
	 	echo "empty";
	 }

}


mysqli_close($con);

?>