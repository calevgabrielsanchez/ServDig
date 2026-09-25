$(document).ready(function(){
	
	if(modo == 'registro'){
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

				observaciones: {
					required:true,
					maxlength: 250
				} 
			},
			
		errorLabelContainer: "#warning", 


		messages: { 
			observaciones: {required:"Obligatorio", maxlength:"Debe ser de 250 digitos"}
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
	}	


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
	
	 $('#aceptar').click(
			 function(){
				 if($("#frmRegistroProrroga").valid())
						if(fileUploadFinish){	
							aceptarRegistro();
						}else{
							error('No se ha completado la captura del documento probatorio.');
						}

		});
	 
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
	
if($("#frmRegistroProrroga").valid()){
		
		if(fileUploadFinish){
		
				// Oculta el div de carga de documentos
				$("#cagarDocProbDiv").hide();
				// document.getElementById('cagarDocProbDiv').style.display='none';
				
				
				// Llama al componente de mostrar documentos
				$("#docProbTramDiv").show();
				// document.getElementById('docProbTramDiv').style.display='block';
				initMuestraDocumentosTramiteSession();
				
				var mensaje="Por favor verifique la informaci\u00F3n proporcionada y de clic en Aceptar para continuar con el registro. " +
				"Si requiere corregir datos de clic en Regresar.";
				$("#mensajeConfirmacion").text(mensaje);
				prorrogaConfirmacion(mensaje);
				
				$("#botones").html('');
				$("#frmRegistroProrroga input:text").attr('disabled', 'disabled');
				$("#observaciones").attr('disabled', 'disabled');
				botonesGuardado($("#botones"));
		}else{
			error('No se ha completado la captura del documento probatorio.');
		}
	}
}


function botonesPrincipales(){
	$("#botones").html('');
	var guardar ='<input type="button" id="aceptar" onclick="aceptarRegistro()" value="Aceptar" class="mboton" />&nbsp;';
	var cancelar='<input type="button" value="Cancelar" onclick="cancelarProrroga()" class="mboton" />';
	$("#botones").append(guardar);
	$("#botones").append(cancelar);
	$("#frmRegistroProrroga input:text").attr('disabled', false);
	$("#observaciones").attr('disabled', false);
	$("#mensajeConfirmacion").html('');
}

function guardar(){
	
	$('#frmRegistroProrroga').submit();
}
function botonesGuardado($div){
	
	var guardar ='<input type="button" onclick="guardarProrroga()" value="Aceptar" class="mboton" />&nbsp;';
	var regresar ='<input type="button" id="regresar" onclick="botonesPrincipales()" value="Regresar" class="mboton" />&nbsp;';
	var cancelar='<input type="button" value="Cancelar" onclick="cancelarProrroga()" class="mboton" />';
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
				
				location.href = "" + context_path + "/inicio/grupoFamiliar";
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 seguro que desea cancelar el registro de pr\u00F3rroga?');
	$decision.dialog('open');
}
function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}


function guardarProrroga(){
	
	var observacion =   $("textarea#observaciones").val();
	var formulario = $("form");
	formulario.attr("id","rechazoF");
	formulario.attr("name","rechazoF");
	formulario.attr("method","POST");
	formulario.attr("action",context_path + "/prorroga/guardarVigenciaTemporal/");
	formulario.append("<input type='hidden' name='observacion' value='"+observacion+"'>");
	formulario.submit();
	/*$.getJSON(contextPath + "/prorroga/guardarVigenciaTemporal/",
			{
				'observacion':observacion 
			},              
			function(data) {
				
				doSaveDocSinTramite(data);
				// muestra los documentos genrados para este tramite
				// location.href = contextPath + "/prorroga/resultados/";
				prorrogaGuardada('La pr\u00F3rroga ha sido registrada correctamente');
			}   
			
	).success(function() {  }) 
	.error(function(jqXHR, textStatus, errorThrown) {
		error(errorThrown+" - "+'No fue posible guardar la prórroga.');
	 }).complete(function() {  });
	
	
	*/
	// Falta elimianr el idguardado en session
	
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
