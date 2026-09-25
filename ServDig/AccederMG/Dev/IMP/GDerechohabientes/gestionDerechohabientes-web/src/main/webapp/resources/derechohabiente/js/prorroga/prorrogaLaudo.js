$(document)
		.ready(
				function() {

					
					if(modo == 'registro'){
						
						
						$.datepicker.setDefaults({
							onClose: function(){
								$(this).valid();
							}
						});
						
						$('#botonesAut').hide();
						$('#botones').show();
						
						$("#fechaInicio").mask("99/99/9999");
						$("#fechaInicio").datepicker({
							dateFormat : 'dd/mm/yy',
							changeMonth : true,
							changeYear : true,
							yearRange : '-112:+0',
							onSelect: function() {
								  $('#fechaFin').datepicker('option', {minDate: $("#fechaInicio").datepicker('getDate')});
							}
						});
						
						$("#idCaracter").change(
								function() {
									if($("#idCaracter").val() == 2) {
										$("#fechfin").hide();
										$("#fechaFin").val("1/02/3000");
									} else {
										
										$("#fechaFin").val("");
										$("#fechfin").show();
									}
								}
						);

						$("#fechaFin").mask("99/99/9999");
						$("#fechaFin").datepicker({
							dateFormat : 'dd/mm/yy',
							changeMonth : true,
							changeYear : true,
							yearRange : '-0:+10'
						});
						
						
						$("#frmRegistroProrroga").validate({
							rules : {
								fechaFin: {
									required : true,
									validDate : true,
									fechaMayorQue : "#fechaInicio"
								},
								fechaInicio:{
									required : true,
									validDate : true
								},
								observaciones:{
									required : true
								}
							},
							messages : {
								fechaFin: {
									required : "Obligatorio",
									validDate : "Fecha inv\u00e1lida",
									fechaMayorQue : "La fecha de t\u00e9rmino debe ser mayor o igual que la fecha de inicio y/o mayor o igual al dia de hoy."
								},
								fechaInicio: {
									required : "Obligatorio",
									validDate : "Fecha inv\u00e1lida"
								},
								observaciones: {
									required : "Obligatorio"
								}
							}
						});
					if (fileUploadFinish) {
						$('#cagarDocProbDiv').hide();
						$('#docProbTramDiv').show();

					} else {
						$('#cagarDocProbDiv').show();
						$('#docProbTramDiv').hide();
					}
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

					$('#aceptar')
							.click(
									function() {

										if ($("#idCaracter").val() != -1) {
											if ($("#frmRegistroProrroga")
													.valid())
												if (fileUploadFinish) {
													aceptarRegistro();
												} else {
													error('No se ha completado la captura del documento probatorio.');
												}
										} else {
											error('No se ha seleccionaro el carater para la pr\u00F3rroga.');
										}

									});

					$('#cancelar').click(function() {
						cancelarProrroga();
					});
					
					$('#regresar').click(
							 function(){
								 salir();
							 }
						);
					asignartextAreaLimites("observaciones",{styles:{}});
				});

function botonesPrincipales() {

	//Oculta el div de lectura de documentos
	document.getElementById('docProbTramDiv').style.display = 'none';
	document.getElementById('cagarDocProbDiv').style.display = 'block';

	$("#botones").html('');
	var guardar = '<input type="button" id="aceptar" onclick="aceptarRegistro()" value="Aceptar" class="mboton" />&nbsp;';
	var cancelar = '<input type="button" value="Cancelar" onclick="cancelarProrroga()" class="mboton" />';
	$("#botones").append(guardar);
	$("#botones").append(cancelar);
	$("#frmRegistroProrroga input:text").attr('disabled', false);
	$("#observaciones").attr('disabled', false);
	$("#idCaracter").attr('disabled', false);
	$("#mensajeConfirmacion").html('');
}

function guardar() {

	$('#frmRegistroProrroga').submit();
}

function botonesGuardado($div) {

	var guardar = '<input type="button" id="Aceptar"  onclick="guardarProrroga()" value="Aceptar" class="mboton" />&nbsp;';
	var regresar = '<input type="button" id="regresar" onclick="botonesPrincipales()" value="Regresar" class="mboton" />&nbsp;';
	var cancelar = '<input type="button" value="Cancelar" onclick="cancelarProrroga()" class="mboton" />';
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
		title : 'Cancelar Pr\u00F3rroga',
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
				location.href = "" + context_path + "/prorroga/laudo";
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision
			.text('\u00BF Est\u00E1 seguro que desea cancelar el registro de pr\u00F3rroga?');
	$decision.dialog('open');
}

function aceptarRegistro() {

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
		
					var mensaje = "Por favor verifique la informaci\u00F3n proporcionada y de clic en Aceptar para continuar con el registro. "
							+ "Si requiere corregir datos de clic en Regresar.";
					$("#mensajeConfirmacion").text(mensaje);
					prorrogaConfirmacion(mensaje);
		
					$("#botones").html('');
					$("#frmRegistroProrroga input:text").attr('disabled', 'disabled');
					$("#observaciones").attr('disabled', 'disabled');
					$("#idCaracter").attr('disabled', 'disabled');
					botonesGuardado($("#botones"));
			}else{
				alert('No se ha completado la captura del documento probatorio.');
			}
		}	
	} else {
		alert('No se ha seleccionado el caracter para la pr\u00F3rroga.');
	}
}

function guardarProrroga() {

	var prorroga = $("textarea#observaciones").val();
	var fechaInicio = $("input#fechaInicio").val();
	var fechaFin = $("input#fechaFin").val();
	var caracter = $("#idCaracter").val();

	var formulario = $("form");
	formulario.attr("id","rechazoF");
	formulario.attr("name","rechazoF");
	formulario.attr("method","POST");
	formulario.attr("action",context_path + "/prorroga/guardarLaudo/");
	formulario.append("<input type='hidden' name='laudo' value='"+prorroga+"'>");
	formulario.append("<input type='hidden' name='fechaInicio' value='"+fechaInicio+"'>");
	formulario.append("<input type='hidden' name='fechaFin' value='"+fechaInicio+"'>");
	formulario.append("<input type='hidden' name='caracter' value='"+caracter+"'>");
	formulario.submit();
	
	/*$.getJSON(contextPath + "/prorroga/guardarLaudo/", {
		'laudo' : prorroga,
		'fechaInicio' : fechaInicio,
		'fechaFin' : fechaFin,
		'caracter' : caracter
	}, function(data) {
		
		doSaveDocSinTramite(data);
		//muestra los documentos genrados para este tramite
		//location.href = contextPath + "/prorroga/resultados/";
		prorrogaGuardada('La pr\u00F3rroga ha sido registrada correctamente');
	}

	).success(function() {  }) 
	.error(function(jqXHR, textStatus, errorThrown) {
		error(errorThrown+" - "+'No fue posible guardar la pr�rroga.');
	 }).complete(function() {  });

	//Falta elimianr el idguardado en session
*/
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
				location.href = "" + context_path + "/prorroga/acuerdos";
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 seguro que desea cancelar el registro de pr\u00F3rroga?');
	$decision.dialog('open');
}
