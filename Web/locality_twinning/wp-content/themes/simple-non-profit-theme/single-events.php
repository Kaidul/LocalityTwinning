<?php

/**

 * The Template for displaying all single events posts.

 */

get_header(); ?>



<!-- start container -->

<div id="container">



	<!-- start leftcol -->

    	<div id="leftcol">

		<div class="post">

	 		<?php if (have_posts()) : while (have_posts()) : the_post(); ?>

			<h2><?php the_title(); ?></h2>

			<div class="entry">

				<p><span class="dis-block"><strong><?php _e('Event Date:', churchthemes); ?></strong> <?php the_time('F j, Y'); ?> <?php _e('at', churchthemes); ?> <?php the_time() ?></span>

				<strong><?php _e('Event Location:', churchthemes); ?></strong> <?php echo get_post_meta($post->ID, 'events_venue', true); ?></p>

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