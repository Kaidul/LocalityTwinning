<?php
/*
Template Name: Events
*/
?>


<?php get_header(); ?>

<!-- start container -->
<div id="container">

	<!-- start leftcol -->
    	<div id="leftcol">
		<div class="post">
	 		<h2 class="post_title"><?php _e('Upcoming Events', churchthemes); ?></h2>
			<?php query_posts( array( 'post_type' => 'events', 'post_status' => 'future', 'posts_per_page' => 10, 'order' => 'ASC', 'paged' => get_query_var('paged') ) ); ?>
			<?php if (have_posts()) : while (have_posts()) : the_post(); ?>
			<div class="entry listing events">
                	<h3><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a></h3>
                	<span class="meta"><?php the_time('F j, Y'); ?> <?php _e('at', churchthemes); ?> <?php the_time() ?></span>
			<span class="meta"><?php _e('Location: ', churchthemes); ?> <?php echo get_post_meta($post->ID, 'events_venue', true); ?></span>
                <div class="clear"></div>
                </div>
                <!-- entry1 -->
		<?php endwhile; ?>
		<?php endif; ?>
		<?php wp_reset_query(); ?>
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