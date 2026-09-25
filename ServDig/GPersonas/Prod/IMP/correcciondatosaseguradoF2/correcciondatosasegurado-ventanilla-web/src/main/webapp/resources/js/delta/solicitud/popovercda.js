$(document).ready(function() {
  $('[data-toggle="tooltip"]').tooltip();

  $("#solicitud\\.observaciones").blur(function(){
		$("#solicitud\\.observaciones").val(validaCadenaOnBlur($("#solicitud\\.observaciones").val()));
	});

	$("#solicitud\\.observaciones").on('input',function(){
		$("#solicitud\\.observaciones").val(validaCadenaOnChange($("#solicitud\\.observaciones").val()));
	});

	$('#solicitud\\.observaciones').bind("cut copy paste", function(e) {

		fnShowError('span#solicitud\\.observacionesError' , 'No se permite copiar-pegar para este campo, es necesario que se capture');
		e.preventDefault();
	});
});

function validaCadenaOnChange(calle){

	var out = '';
	var exp_reg = /[A-Za-z—Ò0-9\s]/;
	for(var i=0; i<calle.length; i++){
		if((exp_reg).exec(calle.charAt(i)))
			out += calle.charAt(i);
	}
	return out;
}

function validaCadenaOnBlur(calle){
	return calle.trim();
}
