<?php get_header(); ?>

<!-- start container -->
<div id="container">

	<!-- start leftcol -->
    	<div id="leftcol">
		<div class="post">
			<?php if (is_category()) { ?>
        	
            			<h2 class="post_title"><?php _e('Entries Posted in',churchthemes); ?>"<?php echo single_cat_title(); ?>"</h2>        
           	
				<?php } elseif (is_day()) { ?>
				<h2 class="post_title"><?php _e('Archive',churchthemes); ?> | <?php the_time('F jS, Y'); ?></h2>

				<?php } elseif (is_month()) { ?>
				<h2 class="post_title"><?php _e('Archive',churchthemes); ?> | <?php the_time('F, Y'); ?></h2>

				<?php } elseif (is_year()) { ?>
				<h2 class="post_title"><?php _e('Archive',churchthemes); ?> | <?php the_time('Y'); ?></h2>
				
			<?php } ?>
	 		
			<?php if (have_posts()) : while (have_posts()) : the_post(); ?>
			<div class="entry listing">
                	<h3><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a></h3>
                	<span class="meta"><?php _e('Published', churchthemes); ?> <?php the_time('F j, Y'); ?></span>
                	<?php the_excerpt(); ?>
                    	<span class="dis-block"><a href="<?php the_permalink(); ?>" class="underline"><?php _e('Read more', churchthemes); ?></a></span>
                <div class="clear"></div>
                </div>
                <!-- entry1 -->
		<?php endwhile; ?>
		<?php endif; ?>
		</div> <!-- //. post -->
		
<?php if (function_exists("pagination")) {
			pagination($additional_loop->max_num_pages);
			} ?>
	</div>
    	<!-- end leftcol -->
	<!-- start rightcol -->
    	<div id="rightcol">
			<?php get_sidebar(); ?>
	</div>
        <!-- end rightcol -->
    <div class="clear"></div>
    </div>
    <!-- end container -->
<div class="clear"></div>
</div>
</div>
<!-- end wrap -->
<?php get_footer(); ?>

			
