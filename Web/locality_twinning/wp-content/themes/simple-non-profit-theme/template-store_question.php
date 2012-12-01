<?php

/*

Template Name: Store Question

*/

?>

<?php get_header(); ?>

<?php
$question = isset($_GET['msg'])?$_GET['msg']:"";
$email = isset($_GET['email'])?$_GET['email']:"";
$contact_no = isset($_GET['phone'])?$_GET['phone']:"";

function error(){
die("Error connecting to database.Something is wrong.Sorry for your inconvenience.We will fix it soon.");
}
//configure
$host = "localhost";
$db= "helperDatabase";
$user = "root";
$pass = "";
$ms =@mysql_connect($host, $user, $pass);
if ( !$ms ) error();
mysql_select_db($db);
mysql_query('SET CHARACTER SET utf8');
mysql_query("SET SESSION collation_connection ='utf8_general_ci'");
$all="INSERT INTO twinning_questions(email, phone_no, question, answer, flag) VALUES('$email', '$contact_no', '$question', '', 0)";
$result = mysql_query($all);
?>




<?php get_footer(); ?>