/* Region Javascript Functions */

var NAME;


function newRegionForm(){
	$('#form_sandbox div form').empty();	
	$('#sandbox_1 form').append('<p> Region Name:  <input type = "text" name = "region_name"/> </p>');
	$('#submit_area > form').append('<button name = "submit_New_Region" type="submit" onClick="openNewRegionForm()"> Submit </button>');
	document.getElementById('sandbox_1').style.display = 'none';					
	document.getElementById('sandbox_1').style.display = 'block';			
}
function openNewRegionForm(){
	window.NAME = document.getElementById('sandbox_form')[0].value;
	nameStoreConvert(window.NAME);
	$('#sandbox_form').empty();	
	$('.region_type > ul').prepend('<li id = "' + nameStore[window.NAME] + '" class = "region"><a>' + window.NAME + '</a><ul>');
	updateSidebar();
	document.getElementById('wrapper').style.display = 'none';					
	document.getElementById('wrapper').style.display = 'block';
}

function updateSidebar(){
	$('ul li ul').each(function(){
		$(this).prev('a').find('.total').find('div').remove()
	  	$(this).prev('a').find('.total').append('<div>'+ ($(this).find('li').length - $(this).find('li > ul > li').length - 1) +'</div>');
	});
}