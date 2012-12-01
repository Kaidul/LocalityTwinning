<?php

// Register widgetized areas

function the_widgets_init() {
    if ( !function_exists('register_sidebars') )
        return;

    register_sidebar(array('name' => 'Sidebar','id' => 'sidebar-1','before_widget' => '<div id="%1$s" class="content1 widget %2$s"><div class="rc_lt"></div><div class="rc_rt"></div><div class="rc_lb"></div><div class="rc_rb"></div><div class="feature1 gap_tb1">','after_widget' => '</div></div>','before_title' => '<h3>','after_title' => '</h3>'));
	
	 

}

add_action( 'init', 'the_widgets_init' );


    
?>