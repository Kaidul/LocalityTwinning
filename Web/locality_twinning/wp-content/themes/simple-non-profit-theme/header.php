<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<head profile="http://gmpg.org/xfn/11">

<link href='http://fonts.googleapis.com/css?family=Copse' rel='stylesheet' type='text/css'>

<title>

<?php if ( is_home() ) { ?><?php bloginfo('name'); ?>&nbsp;|&nbsp;<?php bloginfo('description'); ?><?php } ?>

<?php if ( is_search() ) { ?><?php bloginfo('name'); ?>&nbsp;|&nbsp;<?php _e('Search Results',churchthemes); ?><?php } ?>

<?php if ( is_author() ) { ?><?php bloginfo('name'); ?>&nbsp;|&nbsp;<?php _e('Author Archives',churchthemes); ?><?php } ?>

<?php if ( is_single() ) { ?><?php wp_title(''); ?>&nbsp;|&nbsp;<?php bloginfo('name'); ?><?php } ?>

<?php if ( is_page() ) { ?><?php bloginfo('name'); ?>&nbsp;|&nbsp;<?php wp_title(''); ?><?php } ?>

<?php if ( is_category() ) { ?><?php bloginfo('name'); ?>&nbsp;|&nbsp;<?php _e('Archive',churchthemes); ?>&nbsp;|&nbsp;<?php single_cat_title(); ?><?php } ?>

<?php if ( is_month() ) { ?><?php bloginfo('name'); ?>&nbsp;|&nbsp;<?php _e('Archive',churchthemes); ?>&nbsp;|&nbsp;<?php the_time('F'); ?><?php } ?>

<?php if (function_exists('is_tag')) { if ( is_tag() ) { ?><?php bloginfo('name'); ?>&nbsp;|&nbsp;<?php _e('Tag Archive',churchthemes); ?>&nbsp;|&nbsp;<?php  single_tag_title("", true); } } ?>

</title>

<meta http-equiv="Content-Type" content="<?php bloginfo('html_type'); ?>; charset=<?php bloginfo('charset'); ?>" />

<link rel="stylesheet" type="text/css" href="<?php bloginfo('stylesheet_url'); ?>" media="screen" />

<link rel="stylesheet" href="<?php bloginfo('template_directory'); ?>/css/slider/default/default.css" type="text/css" media="screen" />

<link rel="stylesheet" href="<?php bloginfo('template_directory'); ?>/css/slider/nivo-slider.css" type="text/css" media="screen" />

<link rel="stylesheet" href="<?php bloginfo('template_directory'); ?>/css/css3.css" type="text/css" media="screen" />

<!--[if IE 7]>

<link rel="stylesheet" type="text/css" href="css/ie7.css" media="screen" />

<![endif]-->



<link rel="alternate" type="application/rss+xml" title="RSS 2.0" href="<?php if ( get_option('church_feedburner_url') <> "" ) { echo get_option('church_feedburner_url'); } else { echo get_bloginfo_rss('rss2_url'); } ?>" />

<link rel="pingback" href="<?php bloginfo('pingback_url'); ?>" />

<?php if ( is_single() ) wp_enqueue_script( 'comment-reply' ); ?>

<?php echo get_option( 'church_exscripts' ); ?>

<?php wp_head(); ?>



<script type="text/javascript" src="<?php bloginfo('template_directory'); ?>/js/jquery-1.6.1.min.js"></script>

<script type="text/javascript" src="<?php bloginfo('template_directory'); ?>/js/jquery.nivo.slider.pack.js"></script>

<script type="text/javascript" src="<?php bloginfo('template_directory'); ?>/js/jquery.simplemodal.js"></script>

</head>

<!-- start top -->

<div id="top">

<div class="box">

	<!-- start navigation -->

    <div id="navigation">

    	<?php wp_nav_menu (array ( 'theme_location' => 'main-nav-menu', 'exclude' => '76,82') ); ?>

    </div>

    <!-- end navigation -->

    <!-- start top right -->

    <div class="top_right">

    	<div class="text_field1">

        <form method="get" id="searchform" action="<?php bloginfo('url'); ?>" class="search_block">



    	<input type="text" class="input1" name="s" id="s"  value="" />



        <input type="submit" class="button1" name="submit" value="" />



    </form>

        </div>

    </div>

    <!-- end top right -->

</div>

</div>

<!-- end top -->

<!-- start wrap -->

<div id="wrap">

<div class="box">

	<!-- start header -->

    <div id="header">

    	<!-- start logo -->

        <div id="logo">

        	<a href="<?php bloginfo('home'); ?>" title="<?php bloginfo('title'); ?>"><?php if ( get_option( 'church_logo' ) <> "" ) { ?><img src="<?php echo get_option( 'church_logo' ); ?>" alt="logo" /><?php } else { ?><?php bloginfo('title'); ?><?php } ?></a>

        </div>

        <!-- end logo -->

    </div>

    <!-- end header -->