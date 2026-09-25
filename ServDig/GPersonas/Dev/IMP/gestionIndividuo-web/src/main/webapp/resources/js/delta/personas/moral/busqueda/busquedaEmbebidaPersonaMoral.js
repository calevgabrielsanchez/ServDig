
var wrapper = new Object();
var url_busqueda = context_path + '/persona/moral/busqueda/bdu/json'; 
var url_getPersona = context_path + '/persona/moral/busqueda-embebida/';
var objDialog;
var objPersonaSeleccionada;

/*
 * Seccion de codigo a ejecutar en cuanto el DOM envie la señar de que esta
 * listo para procesar de modificaciones al DOM
 */
$(document).ready(function() {
	$('#buscar').click(function(event){
		$('#idTipoSociedad').attr("value", -1);
		fnConsulta();
	});
	
	/* Inicializacion del dialogo de confirmacion de los datos de la persona */
	objDialog = $('#dgPersonaMoralDetalle').dialog({
	        autoOpen:false,
	        resizable: false,
	        height:350,
	        width:450,
	        modal: true,
	        buttons: {
	            "Aceptar": function(data) {
	            	$( this ).dialog( "close" );

//					ESTO LO COMENTAMOS EN LO QUE LUCIO VE QUE PEDO CON ESTA ACCION DE CERRAR EL DIALOGO
//	        		window.returnValue = registroSeleccionado; // esta variable esta en datateibolBusquedaPersonaFisicaSeleccion.js
//	        		window.close();
	            	
	            	
	            	var ctrl = parent.PersonaMoralCtrl;
	            	if(ctrl != null){
	            		ctrl.setPersona( objPersonaSeleccionada);
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

/*
 * Manejo de la consulta de personas.
 */
var fnConsulta = function(){
	var aoData = $('form#aoDataForm').serializeArray();
    wrapper.aoData = aoData;
    
    var oForm = $('form#busquedaPersonaMoralForm').toObject();
    wrapper.oForm = oForm;
    
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
			error: function(){
				alert("error");
			}
	    });
		    
};

/*
 * Funcion para obtener el detalle de una persona.
 */
var fnGetPersonaDetalle = function(idPersona){
	$.getJSON(url_getPersona+""+idPersona, null, function(data){
		fnSetDetallePersona(data);
	});
};

var fnSetDetallePersona = function(persona){
	var form = $('form#formPersonaMoral');
	
	$('#rfc_', form).val(persona.rfcSat);
	$('#razonSocial_', form).val(persona.razonSocial);
	$('#actaConstitutiva_', form).val(persona.actaConstitutiva);
	$('#fechaCreacion_', form).val(persona.fechaCreacion);
	
	
	//Seteamos la variable global de persona
	objPersonaSeleccionada = persona;
	
	objDialog.dialog('open');
	
};

/*
 * Configura los eventos a setear a los 
 * elementos de la busqueda de personas morales.
 */
var fnSetEstilo = function(){
	
	$('div.elemento-resultado').hover(
	function(){
		$(this).fadeTo('slow', 0.3);
		$(this).css('cursor', 'pointer');
	},
	function(){
		$(this).fadeTo('slow', 1);
	});
	
	/**
	 * Configuracion del evento de seleccion
	 * de una persona moral.
	 */
	$('div.elemento-resultado').click(function(event){
		var idPersona =$('input#idPersona', this).val(); 
		
		fnGetPersonaDetalle(idPersona);
	});
};