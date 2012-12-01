<?php
/**
 * Enable Custom Post Types for Events
 */
 
// Registers the new post type and taxonomy

function devinsays_event_posttype() {
	register_post_type( 'events',
		array(
			'labels' => array(
				'name' => __( 'Events' ),
				'singular_name' => __( 'event' ),
				
				'add_new' => __( 'Add New Event' ),
				'add_new_item' => __( 'Add New Event' ),
				'edit_item' => __( 'Edit Event' ),
				'new_item' => __( 'Add New Event' ),
				'view_item' => __( 'View Event' ),
				'search_items' => __( 'Search Event' ),
				'not_found' => __( 'No events found' ),
				'not_found_in_trash' => __( 'No events found in trash' )
			),
			'public' => true,
			'supports' => array( 'title', 'editor', 'thumbnail', 'comments' ),
			'capability_type' => 'post',
			'rewrite' => array( 'slug' => 'event', 'with_front' => true ),
			'menu_icon' => get_bloginfo('stylesheet_directory') . '/images/calendar-icon.gif',  // Icon Path
			'exclude_from_search' => false,
			'menu_position' => '5'
		)
	);
	
	register_taxonomy( 'event-tags', 'events', 
		array( 
			'hierarchical' => false,
			'label' => __( 'Event Tags' ), 
			'labels' => array(
				'name' => __( 'Event Tags' ),
				'singular_name' => __( 'Event Tag' )
			)
		) 
	);
}

add_action( 'init', 'devinsays_event_posttype' );



// Change the "Scheduled for" text on Event post types changing the translation
// http://blog.ftwr.co.uk/archives/2010/01/02/mangling-strings-for-fun-and-profit/

function devinsays_translation_mangler($translation, $text, $domain) {
        global $post;
		if ($post->post_type == 'events') {

		$translations = &get_translations_for_domain( $domain);
		if ( $text == 'Scheduled for: <b>%1$s</b>') {
			return $translations->translate( 'Event Date: <b>%1$s</b>' );
		}
		if ( $text == 'Published on: <b>%1$s</b>') {
			return $translations->translate( 'Event Date: <b>%1$s</b>' );
		}
		if ( $text == 'Publish <b>immediately</b>') {
			return $translations->translate( 'Event Date: <b>%1$s</b>' );
		}
	}

	return $translation;
}

add_filter('gettext', 'devinsays_translation_mangler', 10, 4);

// Show Scheduled Posts

function devinsays_show_scheduled_posts($posts) {
   global $wp_query, $wpdb;
   if(is_single() && $wp_query->post_count == 0) {
      $posts = $wpdb->get_results($wp_query->request);
   }
   return $posts;
}

add_filter('the_posts', 'devinsays_show_scheduled_posts');



// Add Metaboxes for events

function add_events_metaboxes(){

	add_meta_box("events_venue", "Event Venue", "events_metabox", "events", "normal", "high");

}

add_action("admin_init", "add_events_metaboxes");

// END // Add Metaboxes

// - - - - - - - - - - - - - - - - - - - - - - -

// Slide Link

function events_metabox(){

	global $post;

	$custom = get_post_custom($post->ID);

	$events_venue = $custom["events_venue"][0];

	?>

    <label>Event Venue:</label>

	<input name="events_venue" value="<?php echo $events_venue; ?>" style="width: 50%;" />

	<?php

}

// END // Slide Link

// - - - - - - - - - - - - - - - - - - - - - - -

// Save

function save_venue($post_id) {

	global $post;

	if (defined('DOING_AUTOSAVE') && DOING_AUTOSAVE) {

	return $post->ID;

	} 

	update_post_meta($post->ID, "events_venue", $_POST["events_venue"]);

}


add_action('save_post', 'save_venue');



add_action("manage_posts_custom_column",  "events_custom_columns");
add_filter("manage_edit-events_columns", "events_edit_columns");
 
function events_edit_columns($columns){
  $columns = array(
    "cb" => "<input type=\"checkbox\" />",
    "title" => "Event Title",
    
    "venue" => "Event Venue",
    "date" => "Event Date",
  );
 
  return $columns;
}
function events_custom_columns($column){
  global $post;
 
  switch ($column) {
    
    case "venue":
      $custom = get_post_custom();
      echo $custom["events_venue"][0];
      break;
    
  }
}


?>