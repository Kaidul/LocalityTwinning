<?php get_header(); ?>

<!-- start container -->
<div id="container">

	<!-- start leftcol -->
    	<div id="leftcol">
		<div class="post">
	 		<?php if (have_posts()) : while (have_posts()) : the_post(); ?>
			<h2><?php the_title(); ?></h2>
			<div class="entry">
				<?php the_content(); ?>
			</div> <!-- //. entry -->
		</div> <!-- //. post -->
		<?php endwhile; ?>
		<?php endif; ?>
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