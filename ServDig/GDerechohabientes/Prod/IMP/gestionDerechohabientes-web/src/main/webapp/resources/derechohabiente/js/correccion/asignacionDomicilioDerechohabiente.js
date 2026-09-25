/**
 * Mario Teran Blanco 
 * IMSS (Instituto Mexicano del Seguro Social) 
 * 20/04/2012
 */

var asentamientosUbicados;
var validacion;
var guardarValidacion=false;

$(document).ready(

function() {
	//Obtenemos el id de la umf del usuario firmado
	var idUmfUsuario = $("#idUmfUsuario").val();
	//Obtenemos el id de la umf de la persona
	var idUmfPersona = $("#idUmfPersona").val();
	//Verificamos si tendremos que cargar las umfs
	var cargarUmfs = idUmfPersona == 0 || $("#domicilioOtro").val() == "1";
	//Verificamos si la persona tiene domicilio
	var tieneDomicilio = $("#domicilio\\.clave").val() != "";
	//seteamos la umfs para el componente
	var umfs = {
		'umfUsuario' : idUmfUsuario,
		'umfPersona' : idUmfPersona
	};

	//Verificamos si tenemos que cargar las UMFS
	if(cargarUmfs && tieneDomicilio) {
		$("#datosMedico").show();
		//En caso de que la persona tenga domicilio pero no tengamos umf, cargaremos las UMFs correspondientes al codigo postal
		getUmfsDisponibles();
		//Indicaremos que el tramite es un cambio de clinica
		$("#indSeleccionMedico").val(1);
	}
	
	/**
	 * Inicializamos el componente de domicilios
	 */
	DomicilioCtrl.init('ubicarDomi',101, umfs);
	DomicilioCtrl.setOnCloseCallback(cambiarDomicilio);

	$('#ubicar').click(function() {
		var tieneConcubina = $("#tieneConcubinaPadres").val() == 1;
		if(tieneConcubina) {
			
			mostrarMensajeDomicilioConcubina();
		} else {
			DomicilioCtrl.localizar();
		}
	});

	
	$("#aceptar").click(function(event) {
		
		var mensaje = "";
		
		if(document.getElementById('domicilio.asentamiento.nombre').value == ""){
			mensaje = mensaje + "Falta el Asentamiento del domicilio.";
		}
		if(document.getElementById('domicilio.codigoPostal.codigoPostal').value == ""){
			mensaje = mensaje + " Falta el C�digo Postal.";
		}
		
		if(mensaje=="") {
			if($("#indSeleccionMedico").val() == 1) {
				var turnoSeleccionado = $("#medicoEnTurno\\.turno\\.idTurno").val();
				var consultorioSeleccionado = $("#medicoEnTurno\\.consultorio\\.idConsultorio").val();
				
				if(turnoSeleccionado == "-1" || consultorioSeleccionado=="-1") {
					mensaje = " Es necesario elegir el consultorio y turno";
				}
			}
		}
		
		if(mensaje != ""){
			event.stopImmediatePropagation();
			event.preventDefault();
			event.stopPropagation();
			
			mostrarMensajeErrorAsignacion(mensaje);
		} else {
			event.stopImmediatePropagation();
			event.preventDefault();
			event.stopPropagation();
			
			var mensajeError = 'El domicilio del grupo familiar ser&aacute; actualizado.' +
				' De clic an Aceptar para asignar el nuevo domicilio.';

			$razonRechazo = $('<div></div');
			$razonRechazo.html(mensajeError);
			$razonRechazo.dialog({
				autoOpen : false,
				title: 'Corfimaci&oacute;n',
				show: "blind",
				hide: "explode",
				resizable: false,
				modal: true,
				width: 500,
				buttons: {
					"Aceptar" : function() {
						cierraDialogo($(this));
						$.blockUI();
						habilitarCampos(true);
						$("#registro").attr("action","/${mvn.web.app.root}/asignacionDomicilio/finalizar");
						$("#registro").submit();
					},
					"Cerrar": function() {
						cierraDialogo($(this));
					}
				}
			}
			).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
			
			$razonRechazo.dialog('open');
		}
		
		}
	);
	
	$("#regresarGrupoFamiliar").click(
			function() {
				salirCorreccion();
			}
		);

	// ------------------------------------------------
	// Limita el n�mero de caracteres en la text area
	// ------------------------------------------------
	if( $("#observacion").length > 0 ){
		var offsetLeft = $("#observacion").position().left - parseInt($("#observacion").css("padding-left"));
		asignartextAreaLimites("observacion",{styles: {marginLeft:offsetLeft}});
	}
	
});

function mostrarMensajeDomicilioConcubina(){
	
	var texto='<div class="ui-widget">'+
	'<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>'+
	'Al modificar el domicilio del asegurado/pensionado tambien de actualizar&aacute; el de sus beneficiarios con parentesco concubina(rio) y padres'+
	'</p></div></div>';
	$razonRechazo = $('<div></div');
	$razonRechazo.html(texto);
	$razonRechazo.dialog({
		autoOpen : false,
		title: 'Advertencia',
		show: "blind",
		//hide: "explode",
		resizable: false,
		closeOnEscape: false,
		modal: true,
		width: 500,
		buttons: {
			"Aceptar" : function() {
				cierraDialogo($(this));
				DomicilioCtrl.localizar();
			},
			"Cerrar": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$razonRechazo.dialog('open');
}

function mostrarMensajeErrorAsignacion(mensaje) {
	var mensajeError = '<div class="ui-widget">' +
	'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
	'<strong>' + mensaje +'</strong></p></div></div>';

	$razonRechazo = $('<div></div');
	$razonRechazo.html(mensajeError);
	$razonRechazo.dialog({
		autoOpen : false,
		title: '',
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			"Cerrar": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$razonRechazo.dialog('open');
}

function mensajeConfirmacion(mensaje){
	$ventana = $('<div></div');
	
	$ventana.html(mensaje);	
	$ventana.dialog({
		autoOpen : false,
		title: 'Mensaje',
		show: "blind",
		hide: "explode",
		modal: true,
		width: 500,
		buttons: {
			"Cerrar": function() {
				cierraDialogo($(this));
			}
		}
	
		}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	
	$ventana.dialog('open');
}

function cierraDialogo($dialogo){
	$dialogo.dialog('close'); 
	$dialogo.dialog('destroy');
	$dialogo.html('');
}


/**
 * Metodo para editar o deshabilitar campos
 * 
 */
function habilitarCampos(estado){

	$("#registro select").each(
		function(index) {
			$(this).removeAttr("disabled");
			if(estado==false)
				$(this).attr("disabled","disabled");
		}
	);
		
	$("#registro input:text").each(
		function(index) {
			$(this).removeAttr("disabled");
			if(estado==false)
				$(this).attr("disabled","disabled");
		}
	);
}

/**
 * MEtodo para establecer la accion dependiendo si es la validacion o no
 * @param isValidacion
 */
function setValidacion(isValidacion) {
	
	validacion = isValidacion;
	if(validacion != 1) {
		
		$("#registro").attr("action",""+context_path+"/derechohabiente/correccion/valida/AsignacionDomicilio");
		$("#domicilioNuevo input:text").each(
				function(index) {
					$(this).attr("disabled","disabled");
				}
			);	
		
	} else {
		$("#registro").attr("action",""+context_path+"/derechohabiente/correccion/guarda/AsignacionDomicilio");

		habilitarCampos(false);
		$("#domicilioNuevo input:text").each(
			function(index) {
				$(this).attr("disabled","disabled");
			}
		);
	}
}


function salirCorreccion() {

	$decision = $('<div></div');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		width : 500,
		title : 'Seleccione una opci\u00F3n',
		modal : true,
		buttons : {
			"Si" : function() {
				location.href = context_path +"/welcome/uno/busqueda";
				$.blockUI();
				cierraDialogo($(this));
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 Seguro que desea salir del tr\u00E1mite de Correcci\u00F3n? Se perderan todos los datos no guardados');
	$decision.dialog('open');
}

/**
 * Metodo para enviar los datos del formulario
 */
function salvarCorreccion() {
	$.blockUI();
	$("#registro input:text").each(
		function(index) {
			$(this).removeAttr("disabled");
			$(this).attr("readonly","readonly");
		}
	);
	
	$("#registro select").each(
		function(index) {
			$(this).removeAttr("disabled");
			$(this).attr("readonly","readonly");
		}
	);
	
	$("#fechaNacimiento").removeAttr("disabled");
	$("#fechaNacimiento").attr("readonly", "readonly");
	
	$("#mediosActuales input:text").each(
			function(index) {
				$(this).removeAttr("disabled");
				$(this).attr("readonly","readonly");
			}
		);
	
	$("#domicilioNuevo input:text").each(
			function(index) {
				$(this).removeAttr("disabled");
				$(this).attr("readonly","readonly");
			}
		);
	
	$('#registro').submit();
}

/**
 * MEtodo para cerrar un dialogo y borrar su contenido
 * @param $dialogo
 */
function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}
 
 var cambiarDomicilio = function() {
		$.blockUI();
		var objDomicilio =  this;
		
		if(objDomicilio != undefined && objDomicilio != null) {
			$("#umfSeleccionada").val(DomicilioCtrl.getIdUmfTramite());
			
			docimicilioUbicado=true;
			try {
				
				//Se llama a la funcion 
				setDomicilioCommon(objDomicilio);
				//se verifica si se tienen que ocultar las alertas de los padres
				if($("#alertPadres1").length >0) {
					$("#alertPadres1").hide();
					$("#alertPadres2").hide();
				}
				//Se obitenen las umf disponibles
				getUmfsDisponibles();
				//Se verifica si la persona cuenta con una UMF
				var tieneUmf = $("#idUmfPersona").val() != "0";
				//Se verifica si hubo cambio de clinica
				if(DomicilioCtrl.isCambioClinica() || !tieneUmf) {
					$("#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").attr("disabled","disabled");
					$("#indSeleccionMedico").val(1);
					$("#datosMedico").show();
				} else {
					$("#indSeleccionMedico").val(0);
					$("#datosMedico").hide();
				}
				
			} catch(e) {}
		}
		$.unblockUI();
}