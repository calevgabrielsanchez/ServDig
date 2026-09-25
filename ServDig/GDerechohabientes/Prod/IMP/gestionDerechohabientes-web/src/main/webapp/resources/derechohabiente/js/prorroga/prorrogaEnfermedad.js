$(document).ready(
	function(){
		if(modo == 'registro'){
			
			$.datepicker.setDefaults({
				onClose: function(){
					$(this).valid();
				}
			});
			
			$("#fechaFin").mask("99/99/9999");
			
			$("#fechaFin").datepicker({
				dateFormat : 'dd/mm/yy',
				changeMonth : true,
				changeYear : true,
				yearRange : '-0:+10'
			});
			
			$('#botonesAut').hide();
			$('#botones').show();
			
			if(fileUploadFinish){
				$('#cagarDocProbDiv').hide();
				$('#docProbTramDiv').show();
				
			}else{
				$('#cagarDocProbDiv').show();
				$('#docProbTramDiv').hide();
			}
			
			var isDocumentacionValida =	$("#frmRegistroProrroga").validate({ 
	
				rules: { 
					fechaFin :{
						required:true,
						validDate : true
					},
					observaciones: {
						required:true,
						maxlength: 250
					} 
				},
				
			errorLabelContainer: "#warning", 
	
	
			messages: { 
				observaciones: {required:"Obligatorio", maxlength:"Debe ser de 250 digitos"},
				fechaFin: {required:"Obligatorio", validDate : "Fecha inv\u00e1lida"}
			} 
	
			});
			
			$("#idCaracter").change(function() {
				index = $(this)[0].selectedIndex;
				if (index == 1) {
					$("#fechaFin").hide();
					$("#mFFin").hide();
					$("#fechaInicio").attr('disabled', 'disabled');
				} else {
					$("#fechaFin").show();
					$("#mFFin").show();
					$("#fechaInicio").attr('readonly', 'readonly');
				}
			});
		}else{
			
			$('#botonesAut').show();
			$('#botones').hide();
			$('#cagarDocProbDiv').hide();
			$('#docProbTramDiv').show();
			
			initMuestraDocumentosTramite( idTramite );
			
			$("#frmRegistroProrroga input:text").attr('disabled', 'disabled');
			$("#observaciones").attr('disabled', 'disabled');
			$("#idCaracter").attr('disabled', 'disabled');
		}		
		var inicio=false;
		
		$('#aceptarAut').click(
				 function(){
					 location.href = context_path + "/prorroga/validaProrroga/";
				 }		 
			);

		$('#cancelarAut').click(
				 function(){

					 cargarRazonRechazo();
				 }		 
			);

		$('#regresarAut').click(
				 function(){
					 regresar();
				 }		 
			);
		
		//inicia barra de botones
		 $('#aceptar').click(
				 function(){
					if($("#idCaracter").val() != -1){
						
					 if($("#frmRegistroProrroga").valid())
							if(fileUploadFinish){
								aceptarRegistro();
							}else{
								error('No se ha completado la captura del documento probatorio.');
							}
							 
					}else{
						
						error('No se ha seleccionado el carater para la pr\u00f3rroga.');
					}
				}
			);
		 
		 $('#cancelar').click(
				 function(){
					 cancelarProrroga();
				 }
				);
		
		 $('#regresar').click(
				 function(){
					 salir();
				 }
			);
		 
		 asignartextAreaLimites("observaciones",{styles:{}});
});


function aceptarRegistro(){
	if($("#idCaracter").val() != -1){
		if($("#frmRegistroProrroga").valid()){
				
			if(fileUploadFinish){
		
					//Oculta el div de carga de documentos
					$("#cagarDocProbDiv").hide();
					//document.getElementById('cagarDocProbDiv').style.display='none';
					
					
					//Llama al componente de mostrar documentos
					$("#docProbTramDiv").show();
					//document.getElementById('docProbTramDiv').style.display='block';
					initMuestraDocumentosTramiteSession();
					
					
					var mensaje="Por favor verifique la informaci\u00F3n proporcionada y de clic en Aceptar para continuar con el registro. " +
					"Si requiere corregir datos de clic en Regresar.";
					$("#mensajeConfirmacion").text(mensaje);
					
					prorrogaConfirmacion(mensaje);
					
					$("#botones").html('');
					$("#frmRegistroProrroga input:text").attr('disabled', 'disabled');
					$("#observaciones").attr('disabled', 'disabled');
					$("#idCaracter").attr('disabled', 'disabled');
					$("#fechaInicio").attr('disabled', 'disabled');
					
					botonesGuardado($("#botones"));
			}else{
				alert('No se ha completado la captura del documento probatorio.');
			}
		}	
	}else{
		 alert('No se ha seleccionado el caracter para la pr\u00f3rroga.');
    }
}

function botonesPrincipales(){
	
	//Oculta el div de lectura de documentos
	document.getElementById('docProbTramDiv').style.display='none';
	document.getElementById('cagarDocProbDiv').style.display='block';

	
	$("#botones").html('');
	var guardar ='<input type="button" id="aceptar" onclick="aceptarRegistro()" value="Aceptar" class="mboton" />&nbsp;';
	var cancelar='<input type="button" value="Cancelar" onclick="cancelarProrroga()" class="mboton" />';
	$("#botones").append(guardar);
	$("#botones").append(cancelar);
	$("#frmRegistroProrroga input:text").attr('disabled', false);
	$("#observaciones").attr('disabled', false);
	$("#idCaracter").attr('disabled', false);
	
	$("#mensajeConfirmacion").html('');
}

function guardar(){

		$('#frmRegistroProrroga').submit();
}



function guardarProrroga(){
	
	var observacion =   $("textarea#observaciones").val();
	var caracter = $("#idCaracter").val();
	var fechaFinF = $("#fechaFin").val();
	
	var formulario = $("form");
	formulario.attr("id","rechazoF");
	formulario.attr("name","rechazoF");
	formulario.attr("method","POST");
	formulario.attr("action",context_path + "/prorroga/guardarEnfermedad/");
	formulario.append("<input type='hidden' name='observacion' value='"+observacion+"'>");
	formulario.append("<input type='hidden' name='caracter' value='"+caracter+"'>");
	formulario.append("<input type='hidden' name='fechaFinF' value='"+fechaFinF+"'>");
	formulario.submit();
	
	/*$.getJSON(contextPath + "/prorroga/guardarEnfermedad/",
			{
				'observacion':observacion, 
				'caracter':caracter, 
				'fechaFinF':fechaFinF
			},              
			function(data) {
				
				if(data == 0){
					prorrogaError('No es posible otorgar la pr\u00F3rroga debido a que no existe estado de incapacidad.');
				}else{
					
					doSaveDocSinTramite(data);
					//muestra los documentos genrados para este tramite
					//location.href = contextPath + "/prorroga/resultados/";
					prorrogaGuardada('La pr\u00F3rroga ha sido registrada correctamente');
				}
			}           	
	).success(function() {  }) 
	.error(function(jqXHR, textStatus, errorThrown) {
		error(errorThrown+" - "+'No fue posible guardar la prórroga.');
	 }).complete(function() {  });
	
	
	
	//Falta elimianr el idguardado en session
*/	
}



function colocarMensajeError($div){
	$div.html('<p id="mensaje" style="color: red">&nbsp;&nbsp;Debe seleccionar una opci\u00F3n</p>');
}
function limpiarError($td){
	$td.html('');
}

function botonesGuardado($div){
	
		var guardar = '<input type="button" id="Aceptar"  onclick="guardarProrroga()" value="Aceptar" class="mboton" />&nbsp;';
		var regresar ='<input type="button" id="regresar" onclick="botonesPrincipales()" value="Regresar" class="mboton" />&nbsp;';
		var cancelar= '<input type="button" value="Cancelar" onclick="cancelarProrroga()" class="mboton" />';
		$div.append(guardar);
		$div.append(regresar);
		$div.append(cancelar);
		
}


function cancelarProrroga() {

$decision = $('<div></div');
$decision.dialog({
	autoOpen : false,
	resizable : false,
	height : 140,
	title : 'Selecciona una opci\u00F3n',
	modal : true,
	buttons : {
		"Si" : function() {
			$.getJSON(contextPath + "/prorroga/limpiarSession/",
					{
						
					},              
					function(data) {
						alert('resultado '+data);
						
					}   
					
			);
			
			location.href = "" + context_path + "/prorroga/enfermedad";
		},
		"No" : function() {
			cierraDialogo($(this));
		}
	}
}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

$decision.text('\u00BF Est\u00E1 seguro que desea cancelar el registro de pr\u00F3rroga?');
$decision.dialog('open');
}

function prorrogaGuardada(mensaje) {

	$decision = $('<div></div');
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : 'Confirmaci\u00F3n',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				$.getJSON(contextPath + "/prorroga/limpiarSession/",
						{
							
						},              
						function(data) {
							alert('resultado '+data);
							
						}   
						
				);
				
				if(modo == 'registro'){
					location.href = "" + context_path + "/inicio/grupoFamiliar";
				}else{
					location.href = "" + context_path + "/welcome/uno/valida";
				}
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text(mensaje);
	$decision.dialog('open');
}

function regresar() {
	location.href = "" + context_path + "/welcome/uno/valida";
}


function prorrogaError(mensaje) {

	$decision = $('<div></div');
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : 'Error',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				$decision.dialog('close');
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text(mensaje);
	$decision.dialog('open');
}

