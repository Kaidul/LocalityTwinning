<?php

class WP_Widget_Events_Posts extends WP_Widget {

	function WP_Widget_Events_Posts() {
		$widget_ops = array('classname' => 'widget_upcoming_entries', 'description' => __( "List upcoming events", 'events_posts_widget') );
		$this->WP_Widget('upcoming-posts', __('Upcoming Events', 'events_posts_widget'), $widget_ops);

		add_action( 'save_post', array(&$this, 'flush_widget_cache') );
		add_action( 'deleted_post', array(&$this, 'flush_widget_cache') );
		add_action( 'switch_theme', array(&$this, 'flush_widget_cache') );
	}

	function widget($args, $instance) {
		$cache = wp_cache_get('widget_upcoming_events', 'widget');

		if ( !is_array($cache) )
			$cache = array();

		if ( isset($cache[$args['widget_id']]) )
			return $cache[$args['widget_id']];

		ob_start();
		extract($args);

		$title = empty($instance['title']) ? __('Upcoming Events', 'events_posts_widget') : apply_filters('widget_title', $instance['title']);
		if ( !$number = (int) $instance['number'] )
			$number = 10;
		else if ( $number < 1 )
			$number = 1;
		else if ( $number > 15 )
			$number = 15;

		$queryArgs = array(
			'showposts'			=> $number,
			'post_type'			=> 'events',
			'nopaging'			=> 0,
			'caller_get_posts'	=> 1,
			'post_status' => 'future',
			'order'				=> 'ASC'
		);

		$r = new WP_Query($queryArgs);
		if ($r->have_posts()) :
?>
		<?php echo $before_widget; ?>
		<?php echo $before_title . $title . $after_title; ?>
		<ul>
		<?php  while ($r->have_posts()) : $r->the_post(); ?>
		<li><a href="<?php the_permalink(); ?>" class="list_title"><?php if ( get_the_title() ) the_title(); else the_ID(); ?></a><span><?php echo get_the_date( $d ); ?> at  <?php the_time() ?></span></li>
		<?php endwhile; ?>
		</ul>
		<span class="redlink"><a href="<?php bloginfo('home'); ?>/events/" class="underline">view all upcoming events</a></span>
		<?php echo $after_widget; ?>
<?php
			wp_reset_query();  // Restore global post data stomped by the_post().
		endif;

		$cache[$args['widget_id']] = ob_get_flush();
		wp_cache_add('widget_upcoming_events', $cache, 'widget');
	}

	function update( $new_instance, $old_instance ) {
		$instance = $old_instance;
		$instance['title'] = strip_tags($new_instance['title']);
		$instance['number'] = (int) $new_instance['number'];
		$this->flush_widget_cache();

		$alloptions = wp_cache_get( 'alloptions', 'options' );
		if ( isset($alloptions['widget_upcoming_entries']) )
			delete_option('widget_upcoming_entries');

		return $instance;
	}

	function flush_widget_cache() {
		wp_cache_delete('widget_upcoming_events', 'widget');
	}

	function form( $instance ) {
		$title = attribute_escape($instance['title']);
		if ( !$number = (int) $instance['number'] )
			$number = 5;
?>
		<p><label for="<?php echo $this->get_field_id('title'); ?>">
		<?php _e('Title:'); ?>
		<input class="widefat" id="<?php echo $this->get_field_id('title'); ?>" name="<?php echo $this->get_field_name('title'); ?>" type="text" value="<?php echo $title; ?>" /></label></p>

		<p><label for="<?php echo $this->get_field_id('number'); ?>">
		<?php _e('Number of events to show:'); ?>
		<input id="<?php echo $this->get_field_id('number'); ?>" name="<?php echo $this->get_field_name('number'); ?>" type="text" value="<?php echo $number; ?>" /></label>
		<br /><small><?php _e('(at most 15)'); ?></small></p>
<?php
	}
}
function registerUpcomingPostsWidget() {
	register_widget('WP_Widget_Events_Posts');
}
add_action('widgets_init', 'registerUpcomingPostsWidget');

?>