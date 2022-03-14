<?php
include("DBconfig.php");

$username = $_POST['username'];
$password = $_POST['password'];
$correct_username_ap = '';
$correct_password_ap = '';
$correct_username_ho = '';
$correct_password_ho = '';

if (!empty($username) && !empty($password)) {
  $sql_applicant = "SELECT username, password from applicant WHERE username='$username' and password='$password'";
  $result_applicant = $con->query($sql_applicant);
  $sql_houseofficer = "SELECT username, password from houseingofficer WHERE username='$username' and password='$password'";
  $result_houseofficer = $con->query($sql_houseofficer);



  while ($rs_applicant = $result_applicant->fetch_array()) {
     $correct_username_ap = $rs_applicant['username'];
     $correct_password_ap = $rs_applicant['password'];
  }

  while ($rs_houseofficer = $result_houseofficer->fetch_array()) {
    $correct_username_ho = $rs_houseofficer['username'];
    $correct_password_ho = $rs_houseofficer['password'];
  }

 if ($correct_username_ap == $username && $correct_password_ap==$password) {
 	echo 'a';
 }
else if ($correct_username_ho == $username && $correct_password_ho==$password) {
    echo 'h';
 }
 else {
 	echo 'false';
 }
}