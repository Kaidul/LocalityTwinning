<?php
/*
Template Name: News
*/
?>

<?php get_header(); ?>

<!-- start container -->
<div id="container">

	<!-- start leftcol -->
    	<div id="leftcol">
		<div class="post">
	 		<h2 class="post_title"><?php _e('News', churchthemes); ?></h2>
			<?php query_posts( array( 'post_type' => 'news', 'posts_per_page' => 10, 'order' => 'DESC', 'paged' => get_query_var('paged') ) ); ?>
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
		<?php wp_reset_query(); ?>
		</div> <!-- //. post -->
		
<?php if (function_exists("pagination")) {
			pagination($additional_loop->max_num_pages);
			} ?>
	</div>
    	<!-- end leftcol -->
	<!-- start rightcol -->
    	<div id="rightcol">
			<!-- start content1 -->
            <div class="content1 widget">
                <!-- rc -->
                <div class="rc_lt"></div>
                <div class="rc_rt"></div>
                <div class="rc_lb"></div>
                <div class="rc_rb"></div>
                <!-- rc -->
                <!-- start feature1 -->
                <div class="feature1 gap_tb1">
                    <h3><?php _e('UPCOMING EVENTS', churchthemes); ?></h3>
			<?php query_posts( array( 'post_type' => 'events', 'post_status' => 'future', 'posts_per_page' => 5, 'order' => 'ASC', 'paged' => get_query_var('paged') ) ); ?>
			<?php if (have_posts()) : ?>
                    <ul>
                        <?php while (have_posts()) : the_post(); ?>
                	<li><span class="list_title"><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a></span> <?php the_time('F j, Y'); ?></li>
			<?php endwhile; ?>
                    </ul>
			<?php endif; ?>
            <?php wp_reset_query(); ?> 
                </div>
                <!-- end feature1 -->
            </div>
            <!-- end content1 -->

<?php dynamic_sidebar(); ?>
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