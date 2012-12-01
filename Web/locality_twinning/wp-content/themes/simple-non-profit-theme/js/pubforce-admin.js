jQuery(document).ready(function($)
{	
$(".evdate").datepicker({
    dateFormat: 'D, M d, yy',
    showOn: 'button',
    buttonImage: '../images/icon-datepicker.png',
    buttonImageOnly: true,
    numberOfMonths: 3

    });
});