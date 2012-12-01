<!-- start footer -->
<div id="footer">
<div class="box">
	<!-- start left -->
    <div id="footer_left">
    	<?php wp_nav_menu (array ( 'theme_location' => 'footer-menu', 'depth' => 0, 'exclude' => '76,82,118,116,51') ); ?>
    </div>
    <!-- end left -->
    <!-- start right -->
    <div id="footer_right">
    	<!-- top -->
        <div class="topline">
        	<?php if ( get_option( 'church_twitter' ) <> "" ) { ?><a href="<?php echo get_option( 'church_twitter' ); ?>" title="Twitter"><img src="<?php bloginfo('template_directory'); ?>/images/ico_tw.png" alt="Twitter icon" /></a><?php } ?>

				<?php if ( get_option( 'church_facebook' ) <> "" ) { ?><a href="<?php echo get_option( 'church_facebook' ); ?>" title="Facebook"><img src="<?php bloginfo('template_directory'); ?>/images/ico_fb.png" alt="Facebook icon" /></a><?php } ?>
        	<?php bloginfo('title'); ?>
        </div>
        <!-- top -->
        <span class="dis-block"><?php echo get_option( 'church_address' ); ?></span>
    </div>
    <!-- end right -->
</div>
</div>
<!-- end footer -->

<?php wp_footer(); ?>

<?php if ( get_option('church_google_analytics') <> "" ) { echo stripslashes(get_option('church_google_analytics')); } ?>

</body>
</html>