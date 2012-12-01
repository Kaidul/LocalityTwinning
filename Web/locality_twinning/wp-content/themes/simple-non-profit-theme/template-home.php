<?php

/*

Template Name: Home

*/

?>



<?php get_header(); ?>

<!-- start container -->

    <div id="container">

    <?php if (get_option('church_slideshow') == 'Yes') { ?>

    	<!-- start content1 -->

        <div class="content1">

        	<!-- rc -->

        	<div class="rc_lt"></div>

            <div class="rc_rt"></div>

            <div class="rc_lb"></div>

            <div class="rc_rb"></div>

            <!-- rc -->

            <!-- start showcase -->

            <script type="text/javascript">

		jQuery(window).load(function() { jQuery('#slider').nivoSlider({ effect: '<?php echo get_option('church_slideshow_effect'); ?>', slices: 5, boxCols: 5, boxRows: 5, animSpeed: 700,directionNav:true, controlNav: false, pauseTime: <?php if (get_option('church_slideshow_pausetime') != "") { echo get_option('church_slideshow_pausetime'); } else { echo 7000; } ?> }); });

	</script>

            <div id="showcase">

            	<div class="slider-wrapper theme-default">

                	<div id="slider">

                    	<?php



				$query = new WP_Query();



				$query->query('post_type=slide');



				$post_count = $query->post_count;



				$count = 0;



				if ($query->have_posts()) : while ($query->have_posts()) : $query->the_post(); $captions[] = '<h2>'.get_the_title($post->ID).'</h2><p>'.get_the_content().'</p>'; $thumb_attrs = array( 'title' => '#caption'.$count );

				?>

				<?php if ( get_post_meta($post->ID, 'slide_link', true) ) { ?>

				<?php if ( has_post_thumbnail()) { ?>

				<a href="<?php echo get_post_meta($post->ID, 'slide_link', true); ?>" title="<?php the_title(); ?>" class="imagelink"><?php the_post_thumbnail('slide-background', $thumb_attrs); ?></a>

				<?php } ?>

				<?php } else { ?>

					<?php if ( has_post_thumbnail()) { ?>

					<?php the_post_thumbnail('slide-background', $thumb_attrs); ?>

					<?php } ?>

					<?php } ?>

					<?php $count++; endwhile; endif; ?>

                    </div>

                </div>

            </div>

            <!-- end showcase -->

<?php } ?>

            <?php query_posts( array( 'post_type' => 'news', 'posts_per_page' => 3, 'order' => 'DESC', 'paged' => get_query_var('paged') ) ); ?>

             <?php if (have_posts()) : ?>

            <!-- start feature1 -->

            <div class="feature1 gap_tb1">

            	<h3><?php _e('সর্বশেষ খবরসমূহ', churchthemes); ?></h3>

                <ul>

                <?php while (have_posts()) : the_post(); ?>

                	<li><span class="list_title"><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a></span> <?php the_time('F j, Y'); ?></li>

						<?php endwhile; ?>		



				</ul>

            </div>

            <!-- end feature1 -->

            <?php endif; ?>

			<?php wp_reset_query(); ?> 

        </div>

        <!-- end content1 -->

        <!-- start content2 -->

        <div class="content1">

        	<!-- rc -->

        	<div class="rc_lt"></div>

            <div class="rc_rt"></div>

            <div class="rc_lb"></div>

            <div class="rc_rb"></div>

            <!-- rc -->

            

			<!-- start feature1 -->

            <div class="feature1 in_width">

            	<h3 class="new_title"><?php _e('গন সচেতনতা',churchthemes); ?></h3>

		<?php if (have_posts()) : while (have_posts()) : the_post(); ?>

                <!-- txt block -->

                <div class="txt_block">

                	<?php the_content(); ?>

				</div>

                <!-- txt block -->

		<?php endwhile; ?>

		<?php endif; ?>

		<?php wp_reset_query(); ?>

            </div>

            <!-- end feature1 -->

            <!-- start feature2 -->

            <div class="feature1">

            	<h3 class="new_title"><?php _e('আসন্ন কার্যক্রম সমূহ', churchthemes); ?></h3>

		<?php query_posts( array( 'post_type' => 'events', 'post_status' => 'future', 'posts_per_page' => 5, 'order' => 'ASC', 'paged' => get_query_var('paged') ) ); ?>

            	<?php if (have_posts()) : ?>

                <ul class="border-line">

                <?php while (have_posts()) : the_post(); ?>

                	<li><span class="list_title"><a href="<?php the_permalink(); ?>" title="<?php printf( esc_attr__( '%s', 'churchthemes' ), the_title_attribute( 'echo=0' ) ); ?>" rel="bookmark"><?php the_title(); ?></a></span> <?php the_time('F j, Y'); ?> <?php _e('at', churchthemes); ?> <?php the_time() ?></li>

<?php endwhile; ?>

                    <li class="last"><a href="<?php bloginfo('home'); ?>/events/" title="<?php _e('See all upcoming events', churchthemes); ?>"><?php _e('See all upcoming events', churchthemes); ?></a></li>

                </ul>

		<?php endif; ?>

            	<?php wp_reset_query(); ?>

            </div>

            <!-- end feature2 -->



            

            <!-- start feature3 -->

            <div class="feature1">

            	<h3 class="new_title"><?php _e('ব্লগ থেকে গৃহীত', churchthemes); ?></h3>

		<?php query_posts( 'posts_per_page=5' ); ?>

            	<?php if (have_posts()) : ?>

                <ul class="border-line">

                	<?php while (have_posts()) : the_post(); ?>

                	<li><span class="list_title"><a href="<?php the_permalink(); ?>"><?php the_title(); ?></a></span> লিখেছেন <?php the_author_posts_link(); ?> - <?php comments_popup_link(__('0 মন্তব্য',churchthemes), __('১ মন্তব্য',churchthemes), __('% মন্তব্য',churchthemes) ); ?></li>

			<?php endwhile; ?>  

                    <li class="last"><a href="<?php bloginfo('home'); ?>/blog" title="সকল আর্কাইভ পরিদর্শন"><?php _e('ব্লগ আর্কাইভ পরিদর্শন', churchthemes); ?></a></li>

                    

				</ul>

			<?php endif; ?>

            <?php wp_reset_query(); ?> 

            </div>

            <!-- end feature3 -->

            

        </div>
    <div class="clear"></div>

    </div>
    <!-- end container -->

<div class="clear"></div>

</div>

</div>

<!-- end wrap -->





<?php get_footer(); ?>