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
                    <h3><?php _e('LATEST NEWS', churchthemes); ?></h3>
			<?php query_posts( array( 'post_type' => 'news', 'posts_per_page' => 5, 'order' => 'DESC', 'paged' => get_query_var('paged') ) ); ?>
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