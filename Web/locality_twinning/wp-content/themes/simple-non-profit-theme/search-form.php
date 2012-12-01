<div id="search_main" class="widget">
	<form method="get" id="searchform" action="<?php bloginfo('url'); ?>">
		<div>
			<input type="text" class="field" name="s" id="s"  value="<?php _e('to search, type and hit enter',churchthemes); ?>" onfocus="if (this.value == '<?php _e('to search, type and hit enter',churchthemes); ?>') {this.value = '';}" onblur="if (this.value == '') {this.value = '<?php _e('to search, type and hit enter',churchthemes); ?>';}" />
			<input type="submit" class="submit" name="submit" value="<?php _e('Search...',churchthemes); ?>" />
		</div>
	</form>
</div>

