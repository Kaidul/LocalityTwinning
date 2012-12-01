<?php get_header(); ?>

<!-- start container -->
<div id="container">

	<!-- start leftcol -->
    	<div id="leftcol">
		<div class="post">
	 		<?php if (have_posts()) : while (have_posts()) : the_post(); ?>
			<h2 class="post_title"><?php the_title(); ?></h2>
			<span class="meta"><?php _e('Published', churchthemes); ?> <?php the_time('F j, Y'); ?></span>
			<div class="entry">
				<?php the_content(); ?>
			</div> <!-- //. entry -->
		</div> <!-- //. post -->
		<?php endwhile; ?>
		<?php endif; ?>
		<?php comments_template(); ?>
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