	<div data-role="footer" data-theme="b" id="footer">
	        <!--Social Icon--> 	
			<center>
				<h4>Stay Connected</h4>
				<div class="social">
                <?php if ( get_option( 'church_twitter' ) <> "" ) { ?><a href="<?php echo get_option( 'church_twitter' ); ?>" title="Twitter"><img src="<?php bloginfo('template_directory'); ?>/images/twitter.png" alt="Twitter icon" /></a><?php } ?>

                <?php if ( get_option( 'church_facebook' ) <> "" ) { ?><a href="<?php echo get_option( 'church_facebook' ); ?>" title="Facebook"><img src="<?php bloginfo('template_directory'); ?>/images/facebook.png" alt="Facebook icon" /></a><?php } ?>
            <?php bloginfo('title'); ?>
	            </div>
	        </center>


	        <!--Copyright-text, address, phone, email-->
	        <div id="custom_footer">
    		    <p style="color:#FFF;"><?php echo get_option( 'church_address' ); ?></p>				
    		<!---->
		</div>

	</div><!-- /footer -->
	
</div><!-- page ends -->

</body>
</html>