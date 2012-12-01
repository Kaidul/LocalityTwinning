<?php

/*

Template Name: Answer Submission

*/

?>



<?php get_header(); ?>
<?
	$host = "localhost";
    $db= "helperDatabase";
    $user = "root";
    $pass = "";
    $ms =@mysql_connect($host, $user, $pass);
    if ( !$ms ) die();
    mysql_select_db($db);
$status = isset($_GET['status'])?$_GET['status']:"";
if($status == ""){
$question = isset($_GET['question'])?$_GET['question']:"";
$id = isset($_GET['id'])?$_GET['id']:""; 
?>
<div>প্রশ্নের উত্তর</div>
<div>
<form method="GET" action="<? echo bloginfo('url'); ?>/submission/" name="form_ans">
	<span><? echo $question; ?></span>
	<textarea name="answer"></textarea>
	<input type="hidden" name="id" value="<? echo $id; ?>"/>
	<input type="hidden" name="status" value="ok"/>
	<input type="submit" name="submit"/>
</form>
</div>
<?
}
else if($status == "ok" ){
	$id = isset($_GET['id'])?$_GET['id']:""; 
	$ans = isset($_GET['answer'])?$_GET['answer']:"";
$all="UPDATE twinning_questions SET answer = '$ans' , flag = 1 WHERE id='$id' ";
$result = mysql_query($all);
?>
<script type="text/javascript">
$('#appspot').click(function(){
	var answer = $('#a').val();
	var ph = $('#b').val();
	var em = $('#c').val();
$.ajax(
   {
      type:'GET',
      url:'http://jhal-muri.appspot.com/sendAll',
      data:"email=em&phone=ph&answer=answer",
      success: function(data){
        $('#wow').hide();
        $('.xx').html("Successfully Sent to User Agent");
      }
   }
);
});
</script>
<?php
$as="SELECT phone, email FROM twinning_questions WHERE id='$id' ";
$res = mysql_query($as);
$row = mysql_fetch_array($res);
?>
<form id="wow">
	<input id="a" name="a" value="<? echo $ans; ?>"/>
	<input id="b" name="b" value="<? echo $row['phone']; ?>"/>
	<input id="c" name="c" value="<? echo $row['email']; ?>"/>
	<input type="submit" name="sub" id="appspot" value="Send the User"/>
</form> <span class="xx"></span>
<?
}


?>
<div class="clear"></div>
<?php get_footer(); ?>