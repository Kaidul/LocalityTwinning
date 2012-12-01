<?php
if (!is_admin()) add_action( 'wp_print_scripts', 'churchthemes_add_javascript' );
function churchthemes_add_javascript( ) {
	wp_enqueue_script('jquery');    
	//wp_enqueue_script( 'tabs', get_bloginfo('template_directory').'/includes/js/tabs.js', array( 'jquery' ) );
}
?>