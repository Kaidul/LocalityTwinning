<?php get_header(); ?>



<!-- start container -->

<div id="container">



	<!-- start leftcol -->

    	<div id="leftcol">

		<div class="post">

	 		<h2 class="post_title"><?php _e('Blog', churchthemes); ?></h2>

			<?php if (have_posts()) : while (have_posts()) : the_post(); ?>

			<div class="entry listing">

                	<h3><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a></h3>

                	<span class="meta"><?php _e('Published', churchthemes); ?> <?php the_time('F jS'); ?></span>

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



			

