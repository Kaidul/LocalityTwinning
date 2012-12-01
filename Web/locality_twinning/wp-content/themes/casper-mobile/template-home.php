<?php

/*

Template Name: Home

*/

?>

<?php get_header(); ?>

<!-- start container -->
<script type="text/javascript">
$(function() {
 function imageresize() {
 var contentwidth = $(window).width();
  $('.fluidimage').attr('width', contentwidth );
 }
 imageresize();
 $(window).bind("resize", function(){
 imageresize();
 });
 });
</script>
<div id="slideshow">
    <img class="fluidimage" src="http://vandelaydesign.net/simple-non-profit-theme/files/2012/01/slider-2.jpg"/>
</div>
<style type="text/css">
.ui-li .ui-btn-inner  a.ui-link-inherit, .ui-li-static.ui-li {
    padding-top:0px;
    padding-bottom:0px;
}
</style>
<?php 
           function liMaker($name, $tagline, $url, $imageName) { ?>
               <li  data-icon="index-arrow" class="index">
               	  <a href="<?php  ?>" data-transition="slide">
			          <img src="<?php echo get_stylesheet_directory_uri(); ?>/images/<? echo $imageName;?>.png" class="ui-li-thumb"/>
			          <?=$name; if($tagline != "") { ?>
			          <span><?=$tagline;?></span>
			          <? } ?>
			      </a>
			   </li>           	
<? } ?>
	<div data-role="content" data-theme="b" id="index_body">
  <?php if(have_posts()) :?>
                  <?php the_post();the_content(); ?>
<?php endif; ?>
		<ul data-role="listview" data-theme="b" class="upper_case">
         <?php
           liMaker("search", "", "search", "search_black");
           liMaker("call", "", "call", "call_black");
           liMaker("inquire", "", "inquire", "inquire");
           liMaker("trade-in", "", "trade", "trade_in");
           liMaker("loan", "", "loan", "loan");
           liMaker("service", "need maintenance or repair?", "service", "service");
         ?> 
		</ul>	
</div>

<?php get_footer(); ?>