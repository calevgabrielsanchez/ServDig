var total_letras = 4000;

function limitarCampo(nombreText){
	var ctrlDown = false;
	var ctrlKey = 17, vKey = 86, cKey = 67;

	$(document).keydown(function(e){
		if (e.keyCode == ctrlKey) ctrlDown = true;
	}).keyup(function(e){
		if (e.keyCode == ctrlKey) ctrlDown = false;
	});
	$('#'+nombreText).keypress(function(event){
		if($(this).val().length > (total_letras))
		event.preventDefault();
	});
	
	$("#"+nombreText).keydown(function(e){
		if (ctrlDown && (e.keyCode == vKey || e.keyCode == cKey)) return false;
	});
}