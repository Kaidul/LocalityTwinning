<?php

function church_options(){

// VARIABLES

$themename = "church-wp";

$manualurl = 'http://www.wordpress.org/';

$shortname = "church";

$GLOBALS['template_path'] = get_bloginfo('template_directory');



//Access the WordPress Categories via an Array

$church_categories = array();  

$church_categories_obj = get_categories('hide_empty=0');



foreach ($church_categories_obj as $church_cat) {



    $church_categories[$church_cat->cat_ID] = $church_cat->cat_name;}



$categories_tmp = array_unshift($church_categories, "Select a category:");    



//Access the WordPress Pages via an Array



$church_pages = array();

$church_pages_obj = get_pages('sort_column=post_parent,menu_order');    



foreach ($church_pages_obj as $church_page) {



    $church_pages[$church_page->ID] = $church_page->post_name; }

$church_pages_tmp = array_unshift($church_pages, "Select a page:");       



//Stylesheets Reader

$alt_stylesheet_path = TEMPLATEPATH . '/styles/';

$alt_stylesheets = array();



if ( is_dir($alt_stylesheet_path) ) {



    if ($alt_stylesheet_dir = opendir($alt_stylesheet_path) ) { 



        while ( ($alt_stylesheet_file = readdir($alt_stylesheet_dir)) !== false ) {



            if(stristr($alt_stylesheet_file, ".css") !== false) {



              $alt_stylesheets[] = $alt_stylesheet_file;



            }

        }    

    }



}





//More Options



$all_uploads_path = get_bloginfo('home') . '/wp-content/uploads/';

$all_uploads = get_option('church_uploads');

$other_entries = array("Select a number:","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19");



// THIS IS THE DIFFERENT FIELDS



$options = array();   



$options[] = array( "name" => "General Settings",



                    "type" => "heading");





$options[] = array( "name" => "Theme Stylesheet",

					"desc" => "Select your themes alternative color scheme.",

					"id" => $shortname."_alt_stylesheet",

					"std" => "green.css",

					"type" => "select",

					"options" => $alt_stylesheets);

					

$options[] = array( "name" => "Custom Logo",



					"desc" => "Upload a logo for your theme, or specify the image address of your online logo. (http://yoursite.com/logo.png)",



					"id" => $shortname."_logo",

					"std" => "",

					"type" => "upload");   







					$options[] = array( "name" => "Custom Favicon",



					"desc" => "Upload a 16px x 16px Png/Gif image that will represent your wechurchte's favicon.",







					"id" => $shortname."_custom_favicon",







					"std" => "",















					"type" => "upload"); 


$options[] = array( "name" => "Tracking Code",


					"desc" => "Paste your Google Analytics (or other) tracking code here. This will be added into the footer template of your theme.",

					"id" => $shortname."_google_analytics",

					"std" => "",

					"type" => "textarea");        


$options[] = array( "name" => "RSS URL",

					"desc" => "Enter your preferred RSS URL. (Feedburner or other)",

					"id" => $shortname."_feedburner_url",

					"std" => "",

					"type" => "text");


$options[] = array( "name" => "Custom CSS",

                    "desc" => "Quickly add some CSS to your theme by adding it to this block.",

                    "id" => $shortname."_custom_css",

                    "std" => "",

                    "type" => "textarea");

$options[] = array( "name" => "Extra Scripts for header",

					"desc" => "You can add extra scripts in here to add at the head section'",

					"id" => $shortname."_exscripts",

					"type" => "textarea");   

$options[] = array( "name" => "Slideshow Options",

				 	"type" => "heading"); 

$options[] = array( "name" => "Do you want to display a slideshow on the homepage?",

					"desc" => "If you want to show slideshow on the homepage, check the checkbox.",

					"id" => $shortname."_slideshow",

					"options" => array('No', 'Yes'),

					"std" => "No",

					"type" => "select"); 



$options[] = array( "name" => "Choose a Slideshow Effect",

					"desc" => "Choose a effect for changing slides.",

					"id" => $shortname."_slideshow_effect",

					"options" => array('random', 'sliceDown', 'sliceDownLeft', 'sliceUp', 'sliceUpLeft', 'sliceUpDown', 'sliceUpDownLeft', 'fold', 'fade', 'slideInRight', 'slideInLeft', 'boxRandom', 'boxRain', 'boxRainReverse', 'boxRainGrow', 'boxRainGrowReverse' ),

					"std" => "random",

					"type" => "select");


$options[] = array(  

					"name" => "Slideshow Pause time",

					"desc" => "Fill the Pause time of the slide before changing in milliseconds. Default is 7000.",

					"id" => $shortname."_slideshow_pausetime",

					"std" => "",

					"type" => "text");

$options[] = array( "name" => "Common Options",

				 	"type" => "heading"); 


	

$options[] = array(  



	"name" => "Adrress for the Footer",



	"desc" => "Please Enter Adrress for Footer.",



	"id" => $shortname."_address",



	"std" => "",



	"type" => "textarea");


$options[] = array(  



	"name" => "Facebook URL",



	"desc" => "Please Enter Facebook account URL (Ex: http://www.facebook.com/example).",



	"id" => $shortname."_facebook",



	"std" => "",



	"type" => "text");

	

$options[] = array(  



	"name" => "Twitter URL",



	"desc" => "Please Enter Twitter account URL (Ex: http://www.twitter.com/example).",



	"id" => $shortname."_twitter",



	"std" => "",



	"type" => "text");





update_option('church_template',$options);      



update_option('church_themename',$themename);   



update_option('church_shortname',$shortname);



update_option('church_manual',$manualurl);


// church Metabox Options

/*

$church_metaboxes = array(

		"image" => array (

		"name"		=> "image",

		"default" 	=> "",

		"label" 	=> "Image",

		"type" 		=> "upload",

		"desc"      => "Enter the URL for image to be used by the Dynamic Image resizer."

		)

    );



update_option('church_custom_template',$church_metaboxes);      

*/

/*

function church_update_options(){

        $options = get_option('church_template',$options);  

        foreach ($options as $option){

            update_option($option['id'],$option['std']);

        }   

}

function church_add_options(){

        $options = get_option('church_template',$options);  

        foreach ($options as $option){

            update_option($option['id'],$option['std']);

        }   

}

//add_action('switch_theme', 'church_update_options'); 

if(get_option('template') == 'churchframework'){       

    church_add_options();

} // end function 

*/

}

add_action('init','church_options');  

?>