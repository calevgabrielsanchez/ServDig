/**
*Guillermo Hernandez Dolores
*/
var guardarValidacion=false;
var mandaGrupoFamiliar =false;

$(document).ready(
		function(){
			
			if($("#validacion").val()==1){
				$("#ubicar").hide();
				$("#aceptar").hide();
				$("#regresarGrupoFamiliar").hide();
				$("#regresar").hide();
				habilitarCampos(false);
				$("#aceptarValidacion").show();
				$("#rechazarTramite").show();
				$("#regresarGrupoFamiliarValidacion").hide();
				//$("#guiaTramite").show();
				$("#regresarValidacion").hide();
				
				//Botones de validacion de datos
				$("#aceptarValidacion").click(
						function() {
							
							if( !esCorrectaFechaNacimientoRecienNacido() ){
								$("#aceptarValidacion").hide();
								errorFechaNacimientoRecienNacido();
								return false;
							}	
							
							if( $("#saltarCargaDocumento").val()==1 ){
								$("#registro input:text").each(
									function(index) {
										$(this).removeAttr("disabled");
										$(this).attr("readonly","readonly");
									}
								);
								$("#registro").append("<input type='hidden' name='observacion' id='observacion' value='"+$("#observaciones").val()+"'/>");
								$("#registro").submit();
							}
							var requiereDocs = $("#requiereDocs").val() == "1";
//							if(guardarValidacion){
							if(requiereDocs){
								if(fileUploadFinish) {
									guardarCambioMedico();
								}
								else {
									mensajeError("Debe proporcionar la documentaci\u00f3n probatorio");
								}
							}else{
								guardarCambioMedico();
							}
/*							}else{
								
								aceptarValidacion()
							}*/
						}
					);
				$("#regresarGrupoFamiliarValidacion").click(
						function() {
							cancelarCorreccion();
						}
					);
				$("#guiaTramiteValidacion").click(
						function() {
							alert('En construccion');
						}
					);
				$("#regresarValidacion").click(
						function() {
							$("#regresarValidacion").hide();
							$("#mensajeConfirmacion").html("");
							$("#observaciones").removeAttr("disabled");
							guardarValidacion=false;
							habilitarCombos(false);
							habilitarCampos(true);
							regresarDocumentacion();
							$("#regresarGrupoFamiliarValidacion").show();
						}
					);
				
				//termino del if
			}else{
				$("#aceptarValidacion").hide();
				$("#rechazarTramite").hide();
				$("#regresarGrupoFamiliarValidacion").hide();
				$("#guiaTramite").hide();
				$("#regresarValidacion").hide();
			}
			
		
});

function guardarCambioMedico(){

	$ventana = $('<div></div');
	var mensajeConfirmacion = 'Se actualizaran los datos, est&aacute; seguro que desea continuar?';
	$ventana.append(mensajeConfirmacion);	
	$ventana.dialog({
		autoOpen : false,
		title: 'Confirmacion Requerida',
		show: "blind",
		hide: "explode",
		modal: true,
		width: 500,
		buttons: {
			"Aceptar" : function() {
				cierraDialogo($(this));
				guardarCorr();
			},
			"Cancelar": function() {
				cierraDialogo($(this));
			}
		}
	
		}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	
	$ventana.dialog('open');
	

}

function guardarCorr() {
	
	var verificarRequisitosMinimos = false;
	var existeCambiosDatosPersonales = verificarCambioDatosPersonales();
	//Si hubo cambio de datos verificamos si fue de datos personales o parentesco, solo en esos casos se validan los requisitos minimos
	if( existeCambiosDatosPersonales || verificarCambioParentesco()) {
		validacionDeRequisitosMiminosYEnvio();
	} else {
		guardarValidacionDeCorreccionDeDatos();
	}
	


}

function validacionDeRequisitosMiminosYEnvio() {
	var url = context_path + "/derechohabiente/correccion/datosPersonales/requisitos";
	var fechan = null;
	var curp = null;
	var indRecienNacido = null;
	
	try{
		if( $.trim($("#fechaNacimiento").val()).length > 0 ){
			fechan = $("#fechaNacimiento").val();
		}
		
		if( $("form#registro input#indicadorRN") )
			indRecienNacido = $("form#registro input#indicadorRN").val();
	}catch(e){
		
	}
		
	
	// ----------------------------------------------
	// Cambio la CURP
	// ----------------------------------------------
	if($.trim($("#curpActual").val()) != $.trim($("#curpCap").val()))
		curp = $.trim($("#curpCap").val());
	
	var datos = {
		"derechohabiente": {
			"idPersona":$("#idPersona").val(),
			"sexo": {
				"idSexo": $('#sexo\\.idSexo').val()
			},
			"fechaNacimiento": fechan,
			"curp" : curp
		},
		"parentesco": {
			"idParentesco": $('#parentesco\\.idParentesco').val()
		},
		"domicilio" : {
			"clave" : $("#domicilio\\.clave").val(),
			"codigoPostal" : {
				"codigoPostal" : $("#domicilio\\.codigoPostal\\.codigoPostal").val()
			}
		},
		"indRecienNacido":indRecienNacido
	};
	
	
	
	$.postJSON(url, datos, function(result) {
		
		if(result.modelo.aprobado == 1){
			guardarValidacionDeCorreccionDeDatos();
		}else{
			mensajeError(result.modelo.motivo);
		}
		
			
	});
}

function guardarValidacionDeCorreccionDeDatos() {
	hailitarCamposValidacion();
	$("#registro").on("submit",function(){$.blockUI();});
	$("#registro").append("<input type='hidden' name='observacion' id='observacion' value='"+$("#observaciones").val()+"'/>");
	$("#registro").submit();
}

/**
 * Funcion para quitarles el atributo disabled a los campos
 * y sellects del formulartio
 */
function hailitarCamposValidacion(){

	$("#registro input:text").each(
			function(index) {
				$(this).removeClass("disabled");
				$(this).removeAttr("disabled");
				$(this).attr("readonly","readonly");
			}
	);

	$("#registro select").each(
			function(index) {
				$(this).removeClass("disabled");
				$(this).removeAttr("disabled");
				$(this).attr("readonly","readonly");
			}
	);


	$("#observaciones").removeAttr("disabled");
	$("#observaciones").attr("readonly","readonly");	
	
}

function mensajeError(mensaje){
	$ventana = $('<div></div');
	var mensajeError = '<div class="ui-widget">' +
	'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
	'<strong>'+mensaje+'</strong></p></div></div>';
	$ventana.append(mensajeError);	
	$ventana.dialog({
		autoOpen : false,
		title: 'Error',
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


function aceptarValidacion(){
	var mensaje= '<div class="ui-widget">' +
	'<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;"> ' +
	'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>' +
	'Por favor verifique la informaci\u00F3n proporcionada y de clic en Aceptar para continuar con el registro.'+
	' Si requiere corregir datos de clic en Regresar.</p></div></div>';
	guardarValidacion=true;
	mensajeConfirmacionValidacion(mensaje);
	cargaFinalizada();
	habilitarCampos(false);
	$("#observaciones").attr("disabled","disabled");
	$("#mensajeConfirmacion").html(mensaje);
    $("#regresarGrupoFamiliarValidacion").hide();
    $("#regresarValidacion").show();
    $("#guiaTramite").hide();
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

	$decision.text('\u00BF Est\u00E1 seguro que desea cancelar el tr\u00E1mite de correcci\u00F3n de datos?');
	$decision.dialog('open');
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
					showComprobanteValidacionCircunscripcion("Correcci\u00F3n de datos".toUpperCase());
					location.href= context_path + "/inicio/grupoFamiliar";
				}
			}
	};

	return opciones;
}

function habilitarCombos(estado){
		$("fieldset#medicoEnTurno select").each(
				function(index) {
					if(estado)
						$(this).removeAttr("disabled");
					else
						$(this).attr("disabled","disabled");
				}
			);
	
}


function showComprobanteValidacionCircunscripcion(titulo){
	var direccion=context_path + "/documentos/rechazoSolicitud?titulo="+titulo+"&tipoTramite=24";
	var page=context_path + "/resources/js/delta/viewPdf.html";
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");	
}

function mensajeConfirmacionValidacion(mensaje){
	$ventana = $('<div></div');
	
	$ventana.html(mensaje);	
	$ventana.dialog({
		autoOpen : false,
		title: 'Mensaje',
		show: "blind",
		hide: "explode",
		modal: true,
		height: 200,
		resizable: false,
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

function regresarDocumentacion(){

	var lenght=doctosCargadosLenght+1;
	$('#thAccion').show();
	for(var i=0;i<lenght;i++){
		$('#eliminaDoc'+i).show();
	}
	
	$('#fileUploadMessages').html("");
}