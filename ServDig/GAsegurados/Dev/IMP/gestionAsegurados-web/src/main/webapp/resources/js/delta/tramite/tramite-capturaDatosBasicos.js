/*
 * Seccion de codigo a ejecutar en cuanto el DOM envie la señar de que esta
 * listo para procesar de modificaciones al DOM
 */
$(document).ready(function() {
		
	$("#registroFechaNacimiento").datepicker({
		showOn: 'both',
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
		changeYear: true,
		yearRange: '-112:+0'
	});
	
	$('#buscar').click(function(event){
		event.preventDefault();
		
		$('form#registroAseguradoDatosBasicosForm input[type=text]').each(function(){
			$(this).val($.trim($(this).val()));
			$(this).val($(this).val().replace(/\s+/gi,' '));
			$(this).val($(this).val().replace(/-+/gi,'-'));
			$(this).val($(this).val().replace(/\'+/gi,'\''));
			$(this).val($(this).val().replace(/\.+/gi,'\.'));
		});
				
		$('form#registroAseguradoDatosBasicosForm').submit();
	});
	
	
	//Boton de limpiar formulario
	$('#limpiar').click(function(event){
		fnHideErrores('form#registroAseguradoDatosBasicosForm');
		$('form#registroAseguradoDatosBasicosForm').clearForm();
	});

	if ($('#nombre\\.errors').length > 0
			|| $('#primerApellido\\.errors').length > 0
			|| $('#sexo\\.idSexo\\.errors').length > 0
			|| $('#fechaNacimiento\\.errors').length > 0
			|| $('#lugarNacimiento\\.clave\\.errors').length > 0
			|| $('#curp\\.errors').length > 0) {
		$('#fisica\\.errors').hide();
	} else {
		$('#fisica\\.errors').show();
	}
	
	$('button#ignoreCurrentRecord').click(function(){
		$('form#extranjeroSIMEIgnoreForm').submit();
	});
});



function fnOnClosePersona(){
	var oPersonaLocalizada = this;
	alert(oPersonaLocalizada);
}