/**
 * Guillermo Hernandez Dolores
 * 
 */
var guardarValidacion =false;

$(document).ready(
	function() {
		
	if($("#validacion").val() == 2) {
		$("#aceptar").hide();
		$("#regresarLista").hide();
		$("#regresar").hide();
		$("#ubicar").hide(); 
		$("#ubicarUMF").hide();
		$("#rechazarValidacion").show();
		$("#regresarGrupoFamiliar").show();
		$("#aceptarValidacion").show();
		habilitarUmf();
		$("#regresarGrupoFamiliar").click(
			function() {
				cancelarCorreccion();
			}
		);
		
		$("#aceptarValidacion").click(
			function() {
				if($("#documentos").val() == 0) {
					if(fileUploadFinish){
						console.log('aceptarValidacion', documentos);
						guardarCC();
					}else{
						var mensaje= '<div class="ui-widget-content ui-corner-all">';
						mensaje+= '<div class="ui-state-error ui-corner-all" align="center">';
						mensaje+= '<div class="ui-icon ui-icon-alert"></div>';
						mensaje+= '<p class="ui-helper-reset ui-state-error-text">Debe completar la documentaci\u00F3n probatoria para poder guardar la validaci\u00F3n</p>';
						mensaje+= '</div>';
						mensaje+= '</div>';
						mensajeConfirmacionCC(mensaje);
						console.log('faltanDocs'.documentos);
					}
				}else {
					console.log('le valió', documentos);
					guardarCC();
				}
			}
		);
		
		$("#rechazarValidacion").click(
				function() {
					cargarRazonRechazo();
				}
			);
	}else if($("#validacion").val()==1){	
		
		$("#aceptar").hide();
		$("#regresarLista").hide();
		$("#regresar").hide();
		$("#ubicarUMF").hide(); 
		habilitarUmf();
		//Botones de validacion de datos
		$("#aceptarValidacion").click(
				function() {
					if(guardarValidacion){
						console.log('guardar', guardarValidacion);
						guardar();
					}else{
						if(fileUploadFinish){
							console.log('docs');
							guardar();
						}else{
							console.log('error', validacion);
							var mensaje= '<div class="ui-widget-content ui-corner-all">';
							mensaje+= '<div class="ui-state-error ui-corner-all" align="center">';
							mensaje+= '<div class="ui-icon ui-icon-alert"></div>';
							mensaje+= '<p class="ui-helper-reset ui-state-error-text">Debe completar la documentaci\u00F3n probatoria para poder guardar la validaci\u00F3n</p>';
							mensaje+= '</div>';
							mensaje+= '</div>';
							mensajeConfirmacionCC(mensaje);
						}
					}
				}
				
			);
		$("#regresarGrupoFamiliar").click(
				function() {
					if(guardarValidacion){
						guardarValidacion=false;
						regresarDocumentacion();
						$("#mensajeConfirmacion").val('');
						$("#ubicarUMF").hide();
					}else{
						cancelarCorreccion();
					}
						
					
				}
			);
		$("#rechazarValidacion").click(
				function() {
					cargarRazonRechazo();;
				}
			);
			
		
	} else {
		$("#aceptarValidacion").hide();
		$("#regresarGrupoFamiliar").hide();
		$("#rechazarValidacion").hide();
	}
	
	}
);


function mensageConfirmacion(mensaje){
	$ventana = $('<div></div');
	
		
	$ventana.dialog({
		autoOpen : false,
		title: 'Mensaje',
		resizable : false,
		modal: true,
		width: 500,
		buttons: {
			"Cerrar": function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	$ventana.html(mensaje);
	$ventana.dialog('open');
}


function guardar(){
	
	$decision = $('<div></div');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : '',
		modal : true
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('Espere un momento por favor');
	$decision.dialog('open');
	
	habilitarCampos();
	if($("#documentos").val() == 0)
		doSaveDocSinTramite($("#tramiteId").val());
	$("#correccionDatos").submit();

}

function espere() {
	$espere = $('<div></div');

	$espere.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : '',
		modal : true
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$espere.text('Espere un momento por favor');
	$espere.dialog('open');
}

function guardarCC(){
	$decision = $('<div></div');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : 'Selecciona una opci\u00F3n',
		modal : true,
		buttons : {
			"Si" : function() {
				
				cierraDialogo($(this));
				$.blockUI();
			
				habilitarCampos();
				$("#correccionDatos").on("submit",function(){$.blockUI();});
				$("#correccionDatos").submit();
			},
			"No" : function() {
				guardarValidacion=false;
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 seguro que desea guardar el tr\u00E1mite de cambio de UMF?');
	$decision.dialog('open');

}

function colocarMensaje(mensaje){
	
	$decision = $('<div></div');
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 160,
		width:300,
		title : 'Advertencia',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.html(mensaje);
	$decision.dialog('open');
}


function cancelarCorreccion() {

	$decision = $('<div></div');

	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 160,
		title : 'Advertencia',
		modal : true,
		buttons : {
			"Si" : function() {
				location.href = "" + context_path + "/inicio/grupoFamiliar";
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 seguro que desea salir del tr\u00E1mite de cambio de clinica?');
	$decision.dialog('open')
}


function cargarRazonRechazo() {
	
	$razonRechazo = $('<div></div');
	$razonRechazo.html('Cargando Razones de rechazo...')
	$razonRechazo.dialog({
		autoOpen : false,
		title: 'Raz\u00F3n rechazo',
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 480,
		buttons: {
			"Si": function() {
				var razon = $('#idRazonRechazo').val();
				rechazarSolicitud(razon,$(this));
			},
			"No": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	var ajaxResource = context_path + '/solicitud/cargarRazonRechazo';
	$razonRechazo.load(ajaxResource);
	$razonRechazo.dialog('open');
}

function rechazarSolicitud(razonRechazo, $dialogo) {
	
	
	var formulario = $("form");
	formulario.attr("id","rechazoF");
	formulario.attr("name","rechazoF");
	formulario.attr("method","POST");
	formulario.attr("action",context_path + "/tramite/rechazar");
	formulario.append("<input type='hidden' name='idSolicitud' value='"+$('#idSolicitud').val()+"'>");
	formulario.append("<input type='hidden' name='idPersona' value='"+$('#idPersona').val()+"'>");
	formulario.append("<input type='hidden' name='idTipoTramite' value='36'>");
	formulario.append("<input type='hidden' name='idRazonRechazo' value='"+razonRechazo+"'>");
	formulario.append("<input type='hidden' name='idTramite' value='"+$('#tramiteId').val()+"'>");
	formulario.append("<input type='hidden' name='observaciones' value='"+$('#observacionesRechazo').val()+"'>");
	formulario.submit();
}

function parametrosRespuestaRechazo($dialogo,solicitud,tramite) {
	
	opciones = {
		autoOpen : false,
		resizable: false,
		width: 480,
		title: 'Resultado',
		modal: true,
		buttons: {
			"Aceptar" : function() {
					cierraDialogo($dialogo);
					showComprobanteValidacionCircunscripcion("AUTORIZACI�N DE CIRCUNSCRIPCI�N FOR�NEA");
					location.href= context_path + "/inicio/grupoFamiliar";
				}
				
			}
	};

	return opciones;
}

function showComprobanteValidacionCircunscripcion(titulo){
	var direccion=context_path + "/documentos/rechazoSolicitud?titulo="+titulo+"&tipoTramite=24";
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}

function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}


function habilitarUmf() {
	$("fieldset#medicoTurno select#medicoEnTurno\\.unidadMedicaFamiliar\\.idUMF").attr("disabled","disabled");
	$("fieldset#medicoTurno select#medicoEnTurno\\.turno\\.idTurno").attr("disabled","disabled");
	$("fieldset#medicoTurno select#medicoEnTurno\\.consultorio\\.idConsultorio").attr("disabled","disabled");
}

function habilitarCampos(){
	$("fieldset#domicilio input:text").each(
			function(index) {
				$(this).removeAttr("disabled");
			}
		);
		
	$("fieldset#domicilio select").each(
		function(index) {
			$(this).removeAttr("disabled");
		}
	);
	$("fieldset#medicoTurno select").each(
			function(index) {
				$(this).removeAttr("disabled");
			}
		);
	
}

function regresarDocumentacion(){

	var lenght=doctosCargadosLenght+1;
	$('#thAccion').show();
	for(var i=0;i<lenght;i++){
		$('#eliminaDoc'+i).show();
	}
	
	$('#fileUploadMessages').html("");
}