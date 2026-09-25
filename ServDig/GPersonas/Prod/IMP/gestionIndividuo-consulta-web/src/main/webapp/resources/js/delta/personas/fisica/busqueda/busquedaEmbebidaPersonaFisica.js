
var wrapper = new Object();
var url_busqueda = context_path + '/persona/fisica/busqueda/bdu/json'; 
var url_getPersona = context_path + '/persona/fisica/busqueda-embebida/';
var objDialog;
var registroSeleccionado;

// Expresion regular para validar una fecha en formato dd/MM/yyyy
var ER_FECHAS = /^(0[1-9]|[12][0-9]|3[01])[//](0[1-9]|1[012])[//](19|20)[0-9][0-9]$/;

// Bandera que indicara si ya se selecciono a alguna persona del listado. Al inicio, sera false
var personaSeleccionada = false;
                                                              

// Seccion de codigo a ejecutar en cuanto el DOM envie la señar de que esta
// listo para procesar de modificaciones al DOM
$(document).ready(function() {
	
	$('#limpiar').click(function(){
 		$('#fechaNacimientoError').hide();
		$('#busquedaPersonaFisicaForm').clearForm();
	});
	
	$('#buscar').click(function(event){
		var fecha = $('#busquedaFechaNacimiento').val();
		
		// Solo se valida la fecha en caso de que no sea vacia. Pero si sí esta vacia, va directio 
		// al submit para que el resto de las validaciones se hagan en el controller
		if(fecha != ''){
			// Si la fecha es valida, entonces sí hacemos el submit
			if(ER_FECHAS.test(fecha)){
				fnValidar();
				$('#fechaNacimientoError').hide();
			}else{
				$('#fechaNacimientoError').show();
			}
		}else{
			$('#fechaNacimientoError').hide();
			fnValidar();
		}
		
	});
	
	// Inicializacion del dialogo de confirmacion de los datos de la persona
	objDialog = $('#dgPersonaFisicaDetalle').dialog({
	        autoOpen:false,
	        resizable: false,
	        height:350,
	        width:450,
	        modal: true,
	        buttons: {
	            "Aceptar": function(data) {
	            	$( this ).dialog( "close" );

	        		
	        		
	        		var ctrl = parent.PersonaFisicaCtrl;
	            	if(ctrl != null){
	            		ctrl.setPersona(registroSeleccionado);
	            		ctrl.dialogo.dialog('close');
	            	}
	        		
	            },
	            "Cancelar": function(data) {
	            	$( this ).dialog( "close" );
					return false;
	            }
	        }
	 });
	 
});


// Manejo de la consulta de personas
var fnConsulta = function(){
	var aoData = $('form#aoDataForm').serializeArray();
    wrapper.aoData = aoData;
    
    var oForm = $('form#busquedaPersonaFisicaForm').toObject();
    wrapper.oForm = oForm;

     // Seteamos los datos de la persona buscada
    var ctrl = parent.PersonaFisicaCtrl;
	if(ctrl != null){
		ctrl.personaBuscada = oForm;
	}
    
    $.ajax({
    	
    	type: 'POST',
    	url: url_busqueda,
    	contentType: 'application/json',
    	data: JSON.stringify(wrapper),
    	dataType: 'html', 
		success: function(respuesta){
				$('#resultados').html(respuesta);
				fnSetEstilo();
			},
			error: function(respuesta2){
				fnProcesarErrores(respuesta2, "#busquedaPersonaFisicaForm");
			}
	    });
       
};

// Se valida el formulario, y si todo esta bien, entonces se realizara la consulta
var fnValidar = function(){
	
	fnHideErrores("#busquedaPersonaFisicaForm");
	
	var aoData = $('form#aoDataForm').serializeArray();
    wrapper.aoData = aoData;
    
    var oForm = $('form#busquedaPersonaFisicaForm').toObject();
    wrapper.oForm = oForm;

    $.postJSON(context_path + '/persona/fisica/busqueda/bdu/json/validar', wrapper, function(data) {
    	fnConsulta();
    }).error(function(respuesta) {
		fnProcesarErrores(respuesta, "#busquedaPersonaFisicaForm");
    });
    
};

// Funcion para obtener el detalle de una persona
var fnGetPersonaDetalle = function(idPersona){
	$.getJSON(url_getPersona + "" + idPersona, null, function(data){
		fnSetDetallePersona(data);
	});
};

var fnSetDetallePersona = function(persona){
	var form = $('form#formPersonaFisica');
	
    $('#rfc_', form).val(persona.rfc);
    $('#curp_', form).val(persona.curp);
    $('#nombre_', form).val(persona.nombre);
    $('#primerApellido_', form).val(persona.primerApellido);
    $('#segundoApellido_', form).val(persona.segundoApellido);
    $('#sexo_', form).val(persona.sexo.descripcion);
    $('#fechaNacimiento_', form).val(persona.fechaNacimientoFormateada);
    $('#lugarNacimiento_', form).val(persona.lugarNacimiento.nombre);
    registroSeleccionado = persona;
	objDialog.dialog('open');
	
};

// Configura los eventos a setear a los elementos de la busqueda de personas fisicas
var fnSetEstilo = function(){
	
	$('div.elemento-resultado').hover(
		function(){
			$(this).fadeTo('slow', 0.3);
			$(this).css('cursor', 'pointer');
		},
		function(){
			$(this).fadeTo('slow', 1);
	});
	
	
	// Configuracion del evento de seleccion
	$('div.elemento-resultado').click(function(event){
		var idPersona =$('input#idPersona', this).val(); 
		
		fnGetPersonaDetalle(idPersona);
	});
};