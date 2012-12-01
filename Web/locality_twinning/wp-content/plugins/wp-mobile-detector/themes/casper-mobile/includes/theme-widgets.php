<?php



// =============================== Flickr widget ======================================

function flickrWidget()

{

	$settings = get_option("widget_flickrwidget");



	$id = $settings['id'];

	$number = $settings['number'];



?>



<div id="flickr" class="widget">

	<h3 class="widget_title"><?php _e('Flickr Photos',churchthemes); ?><span class="fold">&nbsp;</span></h3>

	<div class="wrap">

		<div class="fix"></div>

		<script type="text/javascript" src="http://www.flickr.com/badge_code_v2.gne?count=<?php echo $number; ?>&amp;display=latest&amp;size=s&amp;layout=x&amp;source=user&amp;user=<?php echo $id; ?>"></script>        

		<div class="clear"></div>

	</div>

</div>



<?php

}



function flickrWidgetAdmin() {



	$settings = get_option("widget_flickrwidget");



	// check if anything's been sent

	if (isset($_POST['update_flickr'])) {

		$settings['id'] = strip_tags(stripslashes($_POST['flickr_id']));

		$settings['number'] = strip_tags(stripslashes($_POST['flickr_number']));



		update_option("widget_flickrwidget",$settings);

	}



	echo '<p>

			<label for="flickr_id">Flickr ID (<a href="http://www.idgettr.com">idGettr</a>):

			<input id="flickr_id" name="flickr_id" type="text" class="widefat" value="'.$settings['id'].'" /></label></p>';

	echo '<p>

			<label for="flickr_number">Number of photos:

			<input id="flickr_number" name="flickr_number" type="text" class="widefat" value="'.$settings['number'].'" /></label></p>';

	echo '<input type="hidden" id="update_flickr" name="update_flickr" value="1" />';



}



register_sidebar_widget('church - Flickr', 'flickrWidget');

register_widget_control('church - Flickr', 'flickrWidgetAdmin', 400, 200);









// =============================== Ad 305  x 240 widget ======================================

function adsWidget()

{

$settings = get_option("widget_adswidget");

$number = $settings['number'];

if ($number == 0) $number = 1;

$img_url = array();

$dest_url = array();



$numbers = range(1,$number); 

$counter = 0;



if (get_option('church_ads_rotate') == 'true') {

	shuffle($numbers);

}

?>

<div id="ads" class="widget">



<?php

	foreach ($numbers as $number) {	

		$counter++;

		$img_url[$counter] = get_option('church_ad_image_'.$number);

		$dest_url[$counter] = get_option('church_ad_url_'.$number);

	

?>

        <a href="<?php echo "$dest_url[$counter]"; ?>"><img src="<?php echo "$img_url[$counter]"; ?>" alt="Ad" /></a>

<?php } ?>



<div class="clear"></div>



</div><!-- /#ads -->

<?php



}

register_sidebar_widget('church - Ads 305x240', 'adsWidget');



function adsWidgetAdmin() {



	$settings = get_option("widget_adswidget");



	// check if anything's been sent

	if (isset($_POST['update_ads'])) {

		$settings['number'] = strip_tags(stripslashes($_POST['ads_number']));



		update_option("widget_adswidget",$settings);

	}



	echo '<p>

			<label for="ads_number">Number of ads (1-4):

			<input id="ads_number" name="ads_number" type="text" class="widefat" value="'.$settings['number'].'" /></label></p>';

	echo '<input type="hidden" id="update_ads" name="update_ads" value="1" />';



}

register_widget_control('church - Ads 305x240', 'adsWidgetAdmin', 200, 200);





// =============================== Twitter widget ======================================

function widget_Twidget_init() {



	if ( !function_exists('register_sidebar_widget') )

		return;



	function widget_Twidget($args) {



		// "$args is an array of strings that help widgets to conform to

		// the active theme: before_widget, before_title, after_widget,

		// and after_title are the array keys." - These are set up by the theme

		extract($args);



		// These are our own options

		$options = get_option('widget_Twidget');

		$account = $options['account'];  // Your Twitter account name

		$title = $options['title'];  // Title in sidebar for widget

		$show = $options['show'];  // # of Updates to show



        // Output

		echo $before_widget ;



		// start

		echo '<div id="twitter" class="widget">';

		echo '<h3><img src="http://www.negrita.com/new2011/wp-content/uploads/2011/09/twitter1.png" alt="Twitter" width="48"  height="48"/></h3>';              

		echo '<ul id="twitter_update_list"><li></li></ul>

		      <script type="text/javascript" src="http://twitter.com/javascripts/blogger.js"></script>';

		echo '<script type="text/javascript" src="http://twitter.com/statuses/user_timeline/'.$account.'.json?callback=twitterCallback2&amp;count='.$show.'"></script>';

		echo '<span class="wechurchte"><a href="http://www.twitter.com/'.$account.'/" title="Follow us on Twitter" target="_blank"><img src="http://www.negrita.com/new2011/wp-content/uploads/2011/09/follow-us-on-twitter.jpg" alt="" /></a></span></div>';





		// echo widget closing tag

		echo $after_widget;

	}



	// Settings form

	function widget_Twidget_control() {



		// Get options

		$options = get_option('widget_Twidget');

		// options exist? if not set defaults

		if ( !is_array($options) )

			$options = array('account'=>'churchthemes', 'title'=>'Twitter Updates', 'show'=>'3');



        // form posted?

		if ( $_POST['Twitter-submit'] ) {



			// Remember to sanitize and format use input appropriately.

			$options['account'] = strip_tags(stripslashes($_POST['Twitter-account']));

			$options['title'] = strip_tags(stripslashes($_POST['Twitter-title']));

			$options['show'] = strip_tags(stripslashes($_POST['Twitter-show']));

			update_option('widget_Twidget', $options);

		}



		// Get options for form fields to show

		$account = htmlspecialchars($options['account'], ENT_QUOTES);

		$title = htmlspecialchars($options['title'], ENT_QUOTES);

		$show = htmlspecialchars($options['show'], ENT_QUOTES);



		// The form fields

		echo '<p style="text-align:right;">

				<label for="Twitter-account">' . __('Account:') . '

				<input style="width: 200px;" id="Twitter-account" name="Twitter-account" type="text" value="'.$account.'" />

				</label></p>';

		echo '<p style="text-align:right;">

				<label for="Twitter-title">' . __('Title:') . '

				<input style="width: 200px;" id="Twitter-title" name="Twitter-title" type="text" value="'.$title.'" />

				</label></p>';

		echo '<p style="text-align:right;">

				<label for="Twitter-show">' . __('Show:') . '

				<input style="width: 200px;" id="Twitter-show" name="Twitter-show" type="text" value="'.$show.'" />

				</label></p>';

		echo '<input type="hidden" id="Twitter-submit" name="Twitter-submit" value="1" />';

	}





	// Register widget for use

	register_sidebar_widget(array('church - Twitter', 'widgets'), 'widget_Twidget');



	// Register settings for use, 300x200 pixel form

	register_widget_control(array('church - Twitter', 'widgets'), 'widget_Twidget_control', 300, 200);

}



// Run code and init

add_action('widgets_init', 'widget_Twidget_init');









// =============================== Search widget ======================================

function searchWidget()

{

include(TEMPLATEPATH . '/search-form.php');

}

register_sidebar_widget('church - Search', 'SearchWidget');





/* Deregister Default Widgets */



/*

function church_deregister_widgets(){

    unregister_widget('WP_Widget_Search');         

}

add_action('widgets_init', 'church_deregister_widgets');  

*/



?>