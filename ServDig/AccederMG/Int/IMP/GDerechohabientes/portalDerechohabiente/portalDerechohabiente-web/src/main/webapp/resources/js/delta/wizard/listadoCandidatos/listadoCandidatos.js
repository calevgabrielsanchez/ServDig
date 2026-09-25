/**
 * @author Mario Teran Blanco
 */
var dialogoICA;
var idPersonaParam;

//variables para ICA
var existeA = false;

$(document).ready(
	function() {
		//En caso de que la pantalla sea para la seleccion de mas de un integrante establecemos el evento del checkbox
		if($("#seleccionMultiple").val() == 1) {
			/*
			 * Activamos o desactivamos todos los checkbox en caso
			 * de que el checkbox todos sea seleccionado o no
			 */
			$("#checkearTodos").change(
				function() {
					var checado = $(this).is(":checked");
					$("input[name='candidato']").each(function() {
						 $(this).attr("checked",checado);
					 });
				}	
			);
		}
		
		$("#cerrarWizard").click(
			function() {
				//Si el wizard simplemente se cierra establecemos la busqueda de persona en false
				parent.WizardListadoCandidatosCtrl.personaSeleccionada = false;
				//y cerramos el wizard
				parent.WizardListadoCandidatosCtrl.cerrar();
			}
		);
		
		$('#aceptar').click(function() {
			//Verificamos si la pantalla es para seleccion multiple
			var seleccionMult = $("#seleccionMultiple").val() == 1 ? true : false;
			//en caso de serlo invocamos al metodo que procesa la seleccion de multiples candidatos
			if(seleccionMult) {
				seleccionMultiple();
			} else {				
				//En caso contrario invocamos al metodo que valida la seleccion de un solo integrante
				seleccionNormal();
			}
			
		});
	}
);

/**
 * Metodo para el procesamiento de la seleccion de un solo candidato
 */
function seleccionNormal() {
	var derechohabiente = $("input:radio[name=candidato]:checked").val();

	//si existe algun integrante seleccionado lo establecemos y cerramos el wizard
	if(derechohabiente != undefined && derechohabiente != null) {

		// Se setean los datos restantes, el curp desde la lista se sete� al seleccionar al candidato previamente (getCurpFromList)
		parent.WizardListadoCandidatosCtrl.setIdPersona(derechohabiente);
		
		parent.WizardListadoCandidatosCtrl.cerrar();
	}
	else // de lo contrario mostramos un error en pantalla
		errorNoSeleccionado(false);
}

/**
 * Metodo para el procesamiento de la seleccion de mas de in integrante del grupo familiar
 */
function seleccionMultiple() {
	var derechohabiente = [];
	//Llenamos el array derechohabiente con los ids de las personas seleccionadas
	 $("input[@name='candidato']:checked").each(function() {
		 if($.trim($(this).val()).length > 0){
			 derechohabiente[derechohabiente.length] = $(this).val();
		 }
	 });
	//En caso de que el arreglo no este vacio mandamos la lista de los derechohabientes seleccionados 
	if(derechohabiente.length != 0){
		parent.WizardListadoCandidatosCtrl.setIdPersona(derechohabiente);
		parent.WizardListadoCandidatosCtrl.cerrar();
	}
	//En caso de que el arreglo este vacio mostramos un error
	else
		errorNoSeleccionado(true);
}

/**
 * Metodo para mostrar mensaje de error en pantalla 
 * @param seleccionM - Bandera que indica si la pantalla es para seleccionar a un candidato o a varios
 */
function errorNoSeleccionado(seleccionM) {
	$noSeleccionado = $('<div></div');

	$noSeleccionado.dialog({
		autoOpen : false,
		resizable : false,
		height : 'auto',
		title : 'Error',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	if(!seleccionM) {
		$noSeleccionado.html('Debe seleccionar un integrante');
	} else {
		$noSeleccionado.html('Debe seleccionar al menos un integrante');
	}
	
	$noSeleccionado.dialog('open');
}

/**
 * Metodo para cerrar un dialogo
 * @param $dialogo - dialogo que se cerrara
 */
function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}

// Obtenemos el curp de la lista de candidatos y lo seteamos al objeto que se envia al controller para el inicio de la solicitud de correccion
function getCurpFromList(curp){
	if(curp){
		parent.WizardListadoCandidatosCtrl.setCurpPersona(curp);
	} else {
		parent.WizardListadoCandidatosCtrl.setCurpPersona(""); // el curp se debe asignar de alguna forma ay que se usa en el portal del derechohabiente (sin listado de candidatos)
	}
}

function mostrarAvisoPrivacidad(tramite, parentesco) {
	console.log("verifico el aviso de privacidad");
	var homoclave = null;
	
	if(tramite && (tramite == 24 || tramite ==6)) {
		if(parentesco == 5 || parentesco == 6) {
			homoclave = "del asegurado (a) o pensionado (a) como derechohabiente en el IMSS con Homoclave IMSS-02-066-N.";
		} else if(parentesco == 2) {
			homoclave = "de hijo (a) derechohabiente en el IMSS con Homoclave IMSS-02-066-K.";
		} else if(parentesco == 3) {
			homoclave = "de esposa (o) como derechohabiente en el IMSS con Homoclave IMSS-02-066-B.";
		}
		
		if(homoclave != null) {
			$("#homoclaveTramite").html(homoclave);
			$("#avisoPrivacidadCorreccion").show();
		} else {
			$("#avisoPrivacidadCorreccion").hide();
		}
	} else {
		$("#avisoPrivacidadCorreccion").hide();
	}
}
