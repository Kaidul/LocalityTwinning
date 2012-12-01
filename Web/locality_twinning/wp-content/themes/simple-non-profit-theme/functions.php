<?php



//Start churchThemes Functions - Please refrain from editing this file.



$functions_path = TEMPLATEPATH . '/functions/';



$includes_path = TEMPLATEPATH . '/includes/';



// Options panel variables and functions



require_once ($functions_path . 'admin-setup.php');



// Custom functions and plugins



require_once ($functions_path . 'admin-functions.php');





// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -



// Home Link to Custom Menu (Main Navigation)



// function add_home_menu_link($menu_items, $args) {



// 	if('main_navigation' == $args->theme_location) {



// 		$class = '';



// 		$home_menu_item = '<li ' . $class . '>' .



// 			$args->before .



// 			'<a href="' . home_url( '/' ) . '" title="Home">' . $args->link_before . 'Home' . $args->link_after . // '</a>' . $args->after . '</li>';



// 		$menu_items = $home_menu_item . $menu_items;



// 	}



// 	return $menu_items;



// }



// add_filter( 'wp_nav_menu_items', 'add_home_menu_link', 10, 2 );



// END // Home Link to Custom Menu (Main Navigation)







// Thumbnails

include_once 'class.banglaDate.php';
$bn_date = new BanglaDate(time());
$date = $bn_date->get_date();

	function removeHeadLinks() {
    	remove_action('wp_head', 'rsd_link');
    	remove_action('wp_head', 'wlwmanifest_link');
    }
    add_action('init', 'removeHeadLinks');
    remove_action('wp_head', 'wp_generator');
    add_filter( 'show_admin_bar', '__return_false' );





add_theme_support('post-thumbnails');



set_post_thumbnail_size(610, 9999);



add_image_size('slide-background', 610, 315, true);







add_image_size('media-small', 85, 55, true);



add_image_size('media-medium', 180, 130, true);



add_image_size('media-large', 980, 9999);







add_image_size('event-small', 85, 55, true);



add_image_size('event-medium', 150, 100, true);



add_image_size('event-large', 450, 300, true);







// Custom fields 



// require_once ($functions_path . 'admin-custom.php');







// More churchThemes Page



require_once ($functions_path . 'admin-theme-page.php');







// Admin Interface!



require_once ($functions_path . 'admin-interface.php');







// Options panel settings



require_once ($includes_path . 'theme-options.php'); // What we do!







//Custom Theme Fucntions



require_once ($includes_path . 'theme-functions.php'); 







//Custom Comments



require_once ($includes_path . 'theme-comments.php'); 







// Load Javascript in wp_head



require_once ($includes_path . 'theme-js.php');







// Widgets  & Sidebars



require_once ($includes_path . 'sidebar-init.php');



require_once ($includes_path . 'theme-widgets.php');







add_action('wp_head', 'churchthemes_wp_head');



add_action('admin_menu', 'churchthemes_add_admin');



add_action('admin_head', 'churchthemes_admin_head');    


/**
 * This function converts all english number's to bangla number
 */


add_action('admin_menu', 'my_menu');

function my_menu() {
    add_menu_page('Questions', 'Q & A', 'manage_options', 'my-page-slug', 'my_function');
}

function my_function() {
	$host = "localhost";
    $db= "helperDatabase";
    $user = "root";
    $pass = "";
    $ms =@mysql_connect($host, $user, $pass);
    if ( !$ms ) die();
    mysql_select_db($db);
    mysql_query('SET CHARACTER SET utf8');
    mysql_query("SET SESSION collation_connection ='utf8_general_ci'");
    ?>
    <h2>Questions</h2>
    <?
    $q = "SELECT id, question FROM twinning_questions where flag = 0";
    $result = mysql_query($q);
    $i = 1;
    while ($arr = mysql_fetch_array($result)) {
        ?>
        <a href="<? echo bloginfo('url'); ?>/submission/?id=<? echo $arr['id']; ?>&question=<? echo $arr['question'];?>"><? echo $i.". ".$arr['question']; ?></a><br/>
        <?   
        $i++;
    }
}


function new_excerpt_length($length) {



	return 100;



}







add_filter('excerpt_length', 'new_excerpt_length');



function string_limit_words($string, $word_limit)



{



  $words = explode(' ', $string, ($word_limit+ 1));



 if(count($words) > $word_limit) {



  array_pop($words);



  //add a ... at last article when more than limitword count



  echo implode(' ', $words)."..."; } else {



 //otherwise



 echo implode(' ', $words); }



}



// Registering Menus For Theme



add_action( 'init', 'register_my_menus' );



function register_my_menus() {



	register_nav_menus(



		array(



			'main-nav-menu' => __( 'Main Navigation Menu' ),



			'footer-menu' => __( 'Footer Menu' )



	)



	);



}



add_action( 'init', 'create_my_post_types' );



function create_my_post_types() {



	register_post_type( 'slide',



		array(



		'labels' => array(



		'name' => __( 'Slides' ),



		'singular_name' => __( 'Slide' ),



		'add_new' => __( 'Add New' ),



		'add_new' => 'Add New Slide',



		'add_new_item' => __( 'Add New Slide' ),



		'edit' => __( 'Edit' ),



		'edit_item' => __( 'Edit Slide' ),



		'new_item' => __( 'New Slide' ),



		'view' => __( 'View Slide' ),



		'view_item' => __( 'View Slides' ),



		'search_items' => __( 'Search Slides' ),



		'not_found' => __( 'No Slides found' ),



		'not_found_in_trash' => __( 'No Slides found in Trash' ),



		'parent' => __( 'Parent Slide' )



		),



'public' => true,



'supports' => array('thumbnail','title'),



'rewrite' => true,



'query_var' => true,



'exclude_from_search' => true,



'show_ui' => true,



'capability_type' => 'post'



		)



	);



// Add Metaboxes for slider



function add_slide_metaboxes(){



	add_meta_box("slide_link", "Slide Link", "slide_metabox", "slide", "normal", "high");



}



add_action("admin_init", "add_slide_metaboxes");







// END // Add Metaboxes

// - - - - - - - - - - - - - - - - - - - - - - -



// Slide Link

function slide_metabox(){

	global $post;

	$custom = get_post_custom($post->ID);

	$slide_link = $custom["slide_link"][0];

	?>

    <label>Slide link:</label>

	<input name="slide_link" value="<?php echo $slide_link; ?>" style="width: 50%;" />



	<?php

}



// END // Slide Link

// - - - - - - - - - - - - - - - - - - - - - - -

// Save



function save_link($post_id) {



	global $post;



	if (defined('DOING_AUTOSAVE') && DOING_AUTOSAVE) {



	return $post->ID;



	} 

	update_post_meta($post->ID, "slide_link", $_POST["slide_link"]);



}



add_action('save_post', 'save_link');



// END // Save



	register_post_type( 'news',



		array(



			'labels' => array(



			'name' => __( 'News' ),



			'singular_name' => __( 'News' ),



			'add_new' => __( 'Add News' ),



			'add_new' => 'Add New News',



			'add_new_item' => __( 'Add New News' ),



			'edit' => __( 'Edit' ),



			'edit_item' => __( 'Edit News' ),



			'new_item' => __( 'New News' ),



			'view' => __( 'View News' ),



			'view_item' => __( 'View News' ),



			'search_items' => __( 'Search News' ),



			'not_found' => __( 'No News found' ),



			'not_found_in_trash' => __( 'No News found in Trash' ),



			'parent' => __( 'Parent News' )



	),



'public' => true,

'menu_icon' => get_bloginfo('stylesheet_directory') . '/images/news.gif',  // Icon Path

'supports' => array('title','editor','comments'),



'query_var' => true,



'exclude_from_search' => false,



		)



	);



}



/**

 * Enables the Event custom post type

 */



require_once(STYLESHEETPATH . '/extensions/event-post-type.php');



/**

 * Enables the Event Widget

 */



// require_once(STYLESHEETPATH . '/extensions/event-widget.php');



?>