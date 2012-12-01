<?php 

// Custom fields for WP write panel
// This code is protected under Creative Commons License: http://creativecommons.org/licenses/by-nc-nd/3.0/

function churchthemes_meta_box_content() {
    global $post;
    $church_metaboxes = get_option('church_custom_template');     
    $output = '';
    $output .= '<table class="church_metaboxes_table">'."\n";
    foreach ($church_metaboxes as $church_id => $church_metabox) {
    if(        
                $church_metabox['type'] == 'text' 
    OR      $church_metabox['type'] == 'select' 
    OR      $church_metabox['type'] == 'checkbox' 
    OR      $church_metabox['type'] == 'textarea'
    OR      $church_metabox['type'] == 'radio'
    )
            $church_metaboxvalue = get_post_meta($post->ID,$church_metabox["name"],true);
            
            if ($church_metaboxvalue == "" || !isset($church_metaboxvalue)) {
                $church_metaboxvalue = $church_metabox['std'];
            }
            if($church_metabox['type'] == 'text'){
            
                $output .= "\t".'<tr>';
                $output .= "\t\t".'<th class="church_metabox_names"><label for="'.$church_id.'">'.$church_metabox['label'].'</label></th>'."\n";
                $output .= "\t\t".'<td><input class="church_input_text" type="'.$church_metabox['type'].'" value="'.$church_metaboxvalue.'" name="churchthemes_'.$church_metabox["name"].'" id="'.$church_id.'"/>';
                $output .= '<span class="church_metabox_desc">'.$church_metabox['desc'].'</span></td>'."\n";
                $output .= "\t".'<td></td></tr>'."\n";  
                              
            }
            
            elseif ($church_metabox['type'] == 'textarea'){
            
                $output .= "\t".'<tr>';
                $output .= "\t\t".'<th class="church_metabox_names"><label for="'.$church_metabox.'">'.$church_metabox['label'].'</label></th>'."\n";
                $output .= "\t\t".'<td><textarea class="church_input_textarea" name="churchthemes_'.$church_metabox["name"].'" id="'.$church_id.'">' . $church_metaboxvalue . '</textarea>';
                $output .= '<span class="church_metabox_desc">'.$church_metabox['desc'].'</span></td>'."\n";
                $output .= "\t".'<td></td></tr>'."\n";  
                              
            }

            elseif ($church_metabox['type'] == 'select'){
                       
                $output .= "\t".'<tr>';
                $output .= "\t\t".'<th class="church_metabox_names"><label for="'.$church_id.'">'.$church_metabox['label'].'</label></th>'."\n";
                $output .= "\t\t".'<td><select class="church_input_select" id="'.$church_id.'" name="churchthemes_'. $church_metabox["name"] .'">';
                $output .= '<option value="">Select to return to default</option>';
                
                $array = $church_metabox['options'];
                
                if($array){
                
                    foreach ( $array as $id => $option ) {
                        $selected = '';
                       
                                                       
                        if($church_metabox['default'] == $option && empty($church_metaboxvalue)){$selected = 'selected="selected"';} 
                        else  {$selected = '';}
                        
                        if($church_metaboxvalue == $option){$selected = 'selected="selected"';}
                        else  {$selected = '';}  
                        
                        $output .= '<option value="'. $option .'" '. $selected .'>' . $option .'</option>';
                    }
                }
                
                $output .= '</select><span class="church_metabox_desc">'.$church_metabox['desc'].'</span></td></td><td></td>'."\n";
                $output .= "\t".'</tr>'."\n";
            }
            
            elseif ($church_metabox['type'] == 'checkbox'){
            
                if($church_metaboxvalue == 'true') { $checked = ' checked="checked"';} else {$checked='';}

                $output .= "\t".'<tr>';
                $output .= "\t\t".'<th class="church_metabox_names"><label for="'.$church_id.'">'.$church_metabox['label'].'</label></th>'."\n";
                $output .= "\t\t".'<td><input type="checkbox" '.$checked.' class="church_input_checkbox" value="true"  id="'.$church_id.'" name="churchthemes_'. $church_metabox["name"] .'" />';
                $output .= '<span class="church_metabox_desc" style="display:inline">'.$church_metabox['desc'].'</span></td></td><td></td>'."\n";
                $output .= "\t".'</tr>'."\n";
            }
            
            elseif ($church_metabox['type'] == 'radio'){
            
                $array = $church_metabox['options'];
            
            if($array){
            
            $output .= "\t".'<tr>';
            $output .= "\t\t".'<th class="church_metabox_names"><label for="'.$church_id.'">'.$church_metabox['label'].'</label></th>'."\n";
            $output .= "\t\t".'<td>';
            
                foreach ( $array as $id => $option ) {
                              
                    if($church_metaboxvalue == $option) { $checked = ' checked';} else {$checked='';}

                        $output .= '<input type="radio" '.$checked.' value="' . $id . '" class="church_input_radio"  id="'.$church_id.'" name="churchthemes_'. $church_metabox["name"] .'" />';
                        $output .= '<span class="church_input_radio_desc" style="display:inline">'. $option .'</span><div class="church_spacer"></div>';
                    }
                    $output .=  '</td></td><td></td>'."\n";
                    $output .= "\t".'</tr>'."\n";    
                 }
            }
            
            elseif($church_metabox['type'] == 'upload')
            {
            
                $output .= "\t".'<tr>';
                $output .= "\t\t".'<th class="church_metabox_names"><label for="'.$church_id.'">'.$church_metabox['label'].'</label></th>'."\n";
                $output .= "\t\t".'<td class="church_metabox_fields">'. churchthemes_uploader_custom_fields($post->ID,$church_metabox["name"],$church_metabox["default"],$church_metabox["desc"]);
                $output .= '</td>'."\n";
                $output .= "\t".'</tr>'."\n";
                
            }
        }
    
    $output .= '</table>'."\n\n";
    echo $output;
}

function churchthemes_uploader_custom_fields($pID,$id,$std,$desc){

    // Start Uploader
    $upload = get_post_meta( $pID, $id, true);
    $uploader .= '<input class="church_input_text" name="'.$id.'" type="text" value="'.$upload.'" />';
    $uploader .= '<div class="clear"></div>'."\n";
    $uploader .= '<input type="file" name="attachement_'.$id.'" />';
    $uploader .= '<input type="submit" class="button button-highlighted" value="Save" name="save"/>';
    $uploader .= '<span class="church_metabox_desc">'.$desc.'</span></td>'."\n".'<td class="church_metabox_image"><a href="'. $upload .'"><img src="'.get_bloginfo('template_url').'/thumb.php?src='.$upload.'&w=150&h=80&zc=1" alt="" /></a>';

return $uploader;
}

function churchthemes_metabox_insert() {
    global $globals;
    $church_metaboxes = get_option('church_custom_template');     
    $pID = $_POST['post_ID'];
    $counter = 0;
                       
     $files = array();
     $errors = array();
                
    foreach ($church_metaboxes as $church_metabox) { // On Save.. this gets looped in the header response and saves the values submitted
    if($church_metabox['type'] == 'text' OR $church_metabox['type'] == 'select' OR $church_metabox['type'] == 'checkbox' OR $church_metabox['type'] == 'textarea' ) // Normal Type Things...
        {
            $var = "churchthemes_".$church_metabox["name"];
            if (isset($_POST[$var])) {            
                if( get_post_meta( $pID, $church_metabox["name"] ) == "" )
                    add_post_meta($pID, $church_metabox["name"], $_POST[$var], true );
                elseif($_POST[$var] != get_post_meta($pID, $church_metabox["name"], true))
                    update_post_meta($pID, $church_metabox["name"], $_POST[$var]);
                elseif($_POST[$var] == "") {
                   delete_post_meta($pID, $church_metabox["name"], get_post_meta($pID, $church_metabox["name"], true));
                }
            }
            elseif(!isset($_POST[$var]) && $church_metabox['type'] == 'checkbox') { 
                update_post_meta($pID, $church_metabox["name"], 'false'); 
            }      
            else {
                if ($_POST['action'] == 'editpost'){
                  delete_post_meta($pID, $church_metabox["name"], get_post_meta($pID, $church_metabox["name"], true)); // Deletes check boxes OR no $_POST
                }
            }    
        }
  
    elseif($church_metabox['type'] == 'upload') // So, the upload inputs will do this rather
        {    

                $uploaddir = WP_CONTENT_DIR . '/church_uploads/' ;
                $loc = WP_CONTENT_URL .'/church_uploads/';
                
                if(!is_dir($uploaddir)){
                    mkdir($uploaddir,0755);
                }
               
                // Error Tracking - Dir was not created 
                if (!is_dir($uploaddir)) {
                        $error = array('name' => '', 'error' => 'folder_not_created');
                        $errors[] = $error;            
                }
                else 
                {
                $dir = opendir($uploaddir);
                $id = $church_metabox['name'];

                  if(isset($_FILES['attachement_'.$id]) && !empty($_FILES['attachement_'.$id]['name'])) 
                  {
                      if(eregi('image/', $_FILES['attachement_'.$id]['type']))
                      {
                        while($file = readdir($dir)) { if ($file != "." && $file != "..") { array_push($files,$file); }} closedir($dir);
                        $name = $_FILES['attachement_'.$id]['name'];
                        $file_name = substr($name,0,strpos($name,'.'));
                        $file_name = str_replace(' ','_',$file_name);
                         
                        $_FILES['attachement_'.$id]['name'] = $loc . ceil(count($files) + 1).'-'. $file_name .''.strrchr($name, '.');
                        $uploadfile = $uploaddir . basename($_FILES['attachement_'.$id]['name']);
                    
                         if(move_uploaded_file($_FILES['attachement_'.$id]['tmp_name'], $uploadfile)) {
                         
                         $uploaded_file = $_FILES['attachement_'.$id]['name'];
                                  
                          if (isset($uploaded_file)) {    
                              if( get_post_meta( $pID, $id ) == "" )
                              {
                                  add_post_meta($pID, $id, $uploaded_file, true );
                              }
                              elseif($uploaded_file != get_post_meta($pID, $id, true))
                              {
                                  update_post_meta($pID, $id, $uploaded_file);
                              }
                              elseif($uploaded_file == "")
                              {
                                delete_post_meta($pID, $id, get_post_meta($pID, $id, true));
                              }    
                             }
                          } 
                    }
                     else 
                     {
                            $error = array('name' => $_FILES['attachement_'.$id]['name'], 'error' => 'invalid_file');
                            $errors[] = $error;
                        }
                  }
               elseif(!empty($_POST[$id]) && !isset($uploaded_file)){
                    update_post_meta($pID, $id, $_POST[$id]);
               }
               elseif (empty($_POST[$id]) && !isset($uploaded_file) && $_POST['action'] == 'editpost') {   // Upload input is empty - delete the value
                  delete_post_meta($pID, $id, get_post_meta($pID, $id, true));
               } 
        }

    }
   // Error Tracking - File upload was not an Image
   update_option('church_upload_custom_errors',$errors);
}
}

function churchthemes_meta_box() {
    if ( function_exists('add_meta_box') ) {
        //add_meta_box('churchthemes-settings',get_option('church_themename').' Custom Settings','churchthemes_meta_box_content','post','normal');
        //add_meta_box('churchthemes-settings',get_option('church_themename').' Custom Settings','churchthemes_meta_box_content','page','normal');
    }
}

function churchthemes_header_inserts(){
?>
<script type="text/javascript">

    jQuery(document).ready(function(){
        jQuery('form#post').attr('enctype','multipart/form-data');
        jQuery('form#post').attr('encoding','multipart/form-data');
        jQuery('.church_metaboxes_table th:last, .church_metaboxes_table td:last').css('border','0');
        var val = jQuery('input#title').attr('value');
        if(val == ''){ 
        jQuery('.church_metabox_fields .button-highlighted').after("<em class='church_red_note'>Please add a Title before uploading a file</em>");
        };
        <?php //Errors
        $upload_errors = get_option('church_upload_custom_errors');
        if(!empty($upload_errors)){
         $error_shown == false;
         foreach($upload_errors as $error)
            {
                 if($error['error'] == 'folder_not_created' && $error_shown != true){
                    ?>
                    jQuery('form#post').before('<div class="updated fade"><p>churchThemes: <strong>Oh No!</strong> We don\'t have the permissions to create the upload folder on your server. Please create it manually: <em>/wp-content/<strong>church_uploads</strong></em>. Thanks!</strong></p></div>');
                    <?php
                $error_shown = true;
                }
                if($error['error'] == 'invalid_file' ){
                ?>
                 jQuery('form#post').before('<div class="updated fade"><p>churchThemes: <strong><?php echo $error['name']; ?></strong> is not a valid image file, please try another file.</p></div>');
                 <?php
                }
            }
           delete_option('church_upload_custom_errors');
        } ?>
    
    });

</script>
<style type="text/css">
.church_input_text { margin:0 0 10px 0; background:#f4f4f4; color:#444; width:80%; font-size:11px; padding: 5px;}
.church_input_select { margin:0 0 10px 0; background:#f4f4f4; color:#444; width:60%; font-size:11px; padding: 5px;}
.church_input_checkbox { margin:0 10px 0 0; }
.church_input_radio { margin:0 10px 0 0; }
.church_input_radio_desc { font-size: 12px; color: #666 ; }
.church_spacer { display: block; height:5px}
.church_metabox_desc { font-size:10px; color:#aaa; display:block}
.church_metaboxes_table{ border-collapse:collapse; width:100%}
.church_metaboxes_table tr:hover th,
.church_metaboxes_table tr:hover td { background:#f8f8f8}
.church_metaboxes_table th,
.church_metaboxes_table td{ border-bottom:1px solid #ddd; padding:10px 10px;text-align: left; vertical-align:top}
.church_metabox_names { width:20%}
.church_metabox_fields { width:70%}
.church_metabox_image { text-align: right;}
.church_red_note { margin-left: 5px; color: #c77; font-size: 10px;}
.church_input_textarea { width:80%; height:120px;margin:0 0 10px 0; background:#f0f0f0; color:#444;font-size:11px;padding: 5px;}
</style>
<?php
}
add_action('admin_menu', 'churchthemes_meta_box');
add_action('admin_head', 'churchthemes_header_inserts');
add_action('save_post', 'churchthemes_metabox_insert');
?>