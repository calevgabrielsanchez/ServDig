//Prorrogas


$(document).ready(function(){	
	
	$('form:not(.formNotBlock)').submit(function(){
        $.blockUI();
	 });
	
	var modo = $("#modo").val();
	var tipoTramite = $("#tipoTramite").val();
	var documentoP = $("#documentoP").val();
	var validaFechaInicio = false;
	
	$("#prorroga\\.tramite\\.observacion").hide();	
	
	//se setea la bandera para saber si se valida o no la fecha de inicio para la prorroga //
	if(tipoTramite == PRORROGA_ACUERDOS || tipoTramite == PRORROGA_LAUDO || tipoTramite == PRORROGA_ENFERMEDAD){
		validaFechaInicio = true;
	}
	
	if(modo == 'registro'){		
		if(tipoTramite == PRORROGA_ENFERMEDAD){
			loadFileUpload(documentoP);
		}else{
			loadFileUpload(tipoTramite);
		}
		
		setFunctionFinalizadoCapturaDocumentos(getInfoDocumentos);
		
		if(tipoTramite == PRORROGA_PERMANENTE) {			
			$("#tituloVigenciaPermanente").show();
			$("#comboVigencia").hide();
			$("#inicioP").show();
			$("#defuncion").show();
			$("#fInicio").hide();
			$("#fechfin").hide();
			$("#permanente").show();
		}else if(tipoTramite == PRORROGA_ACUERDOS){			
			$("#tituloAcuerdo").show();						
			$("#defuncion").hide();
			$("#inicioP").show();
			$("#fInicio").show();
			$("#fechfin").show();
			$("#permanente").hide();
		}else if(tipoTramite == PRORROGA_OBSTETRICOS){			
			$("#tituloObtetrico").show();
			$("#comboVigencia").hide();
			$("#defuncion").hide();
			$("#inicioP").show();
			$("#fInicio").show();
			$("#fechfin").show();
			$("#permanente").hide();
		}else if(tipoTramite == PRORROGA_ENFERMEDAD){			
			$("#tituloEnfermedad").show();					
			$("#inicioP").show();			
			$("#fInicio").show();
			$("#permanente").hide();
		}else if(tipoTramite == PRORROGA_ESTUDIOS){			
			$("#tituloEstudios").show();
			$("#comboVigencia").hide();
			$("#defuncion").hide();
			$("#inicioP").show();
			$("#fInicio").show();
			$("#fechfin").show();
			$("#permanente").hide();
		}else if(tipoTramite == PRORROGA_LAUDO){			
			$("#tituloLaudo").show();
			$("#defuncion").hide();
			$("#inicioP").show();
			$("#fInicio").show();
			$("#fechfin").show();
			$("#permanente").hide();
		}else if(tipoTramite == PRORROGA_TEMPORAL){
			$("#tituloVigenciaTemporal").show();		
			$("#comboVigencia").hide();
			$("#inicioP").hide();			
			$("#fechfin").hide();
			$("#permanente").hide();
		}						
		
		$('#botonesAut').hide();
		$('#botones').show();
		
		$.datepicker.setDefaults({
			dateFormat : 'dd/mm/yy',
			changeMonth : true,
			changeYear : true,
			yearRange : '-10:+10',
			onClose: function(){
				$(this).valid();
			}
			}
		);
		
		if(tipoTramite == PRORROGA_ACUERDOS || tipoTramite == PRORROGA_ENFERMEDAD || tipoTramite == PRORROGA_LAUDO){
			$("#idCaracter").change(
					function() {
						if($("#tipoTramite").val() == PRORROGA_ENFERMEDAD){
							index = $(this)[0].selectedIndex;
							if (index == 1) {
								$("#miFechaFin").hide();
								$("#fechfin").hide();
								$("#permanente").show();
								$("#miFechaFin").val("01/01/3000");
								$("#prorroga\\.fechaFinProrroga").val("01/01/3000");
							} else {
								$("#miFechaFin").show();
								$("#fechfin").show();
								$("#permanente").hide();
								$("#miFechaFin").val("");
								$("#prorroga\\.fechaFinProrroga").val("");
							}
						}else{	
							if($("#idCaracter").val() == 2) {
								$("#fechfin").hide();
								$("#permanente").show();
								$("#miFechaFin").val("1/01/3000");
								$("#prorroga\\.fechaFinProrroga").val("1/01/3000");
							} else {						
								$("#miFechaFin").val("");
								$("#prorroga\\.fechaFinProrroga").val("");
								$("#fechfin").show();
								$("#permanente").hide();
							}
						}
					}
			);
		}
		
		if(tipoTramite != 29 && tipoTramite != 34) {
			$("#miFechaInicio").mask("99/99/9999");	
			$("#miFechaInicio").datepicker({
				yearRange : '-112:+0',
				maxDate: '0', 
				onSelect: function() {
					var max = null;
					
					if(tipoTramite == 29){
						var str =  $("#miFechaInicio").val().match(/^(\d{2})\/(\d{2})\/(\d{4})$/);
						max = new Date(str[3], str[2]-1, str[1]);
						max.setFullYear(max.getFullYear() + 1);
					}
					  $('#miFechaFin').datepicker('option', {minDate: $("#fechaDeHoy").val(),maxDate: max});
					}	
			});
			
			$("#miFechaInicio").change(function(){
				$("#prorroga\\.fechaInicioProrroga").val($("#miFechaInicio").val());
			});
			
			var max = null;
			
			if(tipoTramite == 29){
				max = new Date();
				max.setFullYear(max.getFullYear() + 1);
			}
			
			$("#miFechaFin").mask("99/99/9999");
			$("#miFechaFin").datepicker({
				showOn : 'focus',
				minDate: new Date(),
				maxDate: max
			});
			
			$("#miFechaFin").change(function(){
				$("#prorroga\\.fechaFinProrroga").val($("#miFechaFin").val());
			});
		}
		
		
		
		$("#frmRegistroProrroga").validate({						
			onchange: false,		    
			rules : {
				miFechaFin: {
					required : true,
					validDate : true ,
					fechaMayorQue : "#fechaDeHoy"

				},
				miFechaInicio:{
					required : true,
					validDate : true,
					validDateToDay : validaFechaInicio

				},
				observaciones:{
					required : true
				}
			},
			messages : {
				miFechaFin: {
					required : "Obligatorio", 
					validDate : "Fecha inv\u00e1lida",
					fechaMayorQue : "La fecha de t\u00e9rmino debe ser mayor o igual que la fecha de inicio y/o mayor o igual al dia de hoy."
				},
				miFechaInicio: {
					required : "Obligatorio",
					validDate : "Fecha inv\u00e1lida",
					validDateToDay : "La fecha no puede ser mayor a hoy."	
				},
				observaciones: {
					required : "Obligatorio"
				}
			}
		});
		
		if(fileUploadFinish){
			$('#cagarDocProbDiv').hide();
			$('#docProbTramDiv').show();
			
		}else{
			$('#cagarDocProbDiv').show();
			$('#docProbTramDiv').hide();
		}
	}else{
		
		if(tipoTramite == PRORROGA_PERMANENTE) {
			$("#tituloVigenciaPermanente").show();
			$("#comboVigencia").hide();
			$("#inicioP").show();
			$("#defuncion").show();
			$("#fInicio").hide();
			$("#fechfin").hide();
		}else if(tipoTramite == PRORROGA_ACUERDOS){
			$("#acuerdos").show();
			$("#tituloAcuerdo").show();						
			$("#defuncion").hide();
			$("#inicioP").show();
			$("#fInicio").show();
			if($("#idCaracter").val() == 2) {
				$("#fechfin").show();
				$("#permanente").hide();
			}else{
				$("#fechfin").hide();
				$("#permanente").show();
			}			
		}else if(tipoTramite == PRORROGA_OBSTETRICOS){
			$("#obtetricos").show();
			$("#tituloObtetrico").show();
			$("#comboVigencia").hide();
			$("#defuncion").hide();
			$("#inicioP").show();
			$("#fInicio").show();
			$("#fechfin").show();
		}else if(tipoTramite == PRORROGA_ENFERMEDAD){
			$("#inicioP").show();
			$("#fInicio").show();
		}else if(tipoTramite == PRORROGA_ESTUDIOS){
			$("#estudios").show();
			$("#tituloEstudios").show();
			$("#comboVigencia").hide();
			$("#defuncion").hide();
			$("#inicioP").show();
			$("#fInicio").show();
			$("#fechfin").show();
		}else if(tipoTramite == PRORROGA_LAUDO){
			$("#laudo").show();
			$("#tituloLaudo").show();			
			$("#defuncion").hide();
			$("#inicioP").show();
			$("#fInicio").show();
			$("#fechfin").show();
		}else if(tipoTramite == PRORROGA_TEMPORAL){
			$("#inicioP").show();
			$("#fInicio").show();
		}
		
		$('#botonesAut').show();
		$('#botones').hide();
		$('#cagarDocProbDiv').hide();
		$('#docProbTramDiv').show();
		
		initMuestraDocumentosTramite($("#idTramite").val());
		
		$("#frmRegistroProrroga input:text").attr('disabled', 'disabled');
		$("#observaciones").attr('disabled', 'disabled');
		if(tipoTramite == PRORROGA_ACUERDOS)
			$("#idCaracter").attr('disabled', 'disabled');
	}	
	
		

	$('#aceptarAut').click(
			 function(){
				 fnAbrirMensajeEsperePorFavor();
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

				 if($("#idCaracter").val() != -1 || $("#tipoTramite").val() == PRORROGA_PERMANENTE || $("#tipoTramite").val() == PRORROGA_OBSTETRICOS 
						 || $("#tipoTramite").val() == PRORROGA_ESTUDIOS || $("#tipoTramite").val() == PRORROGA_TEMPORAL){
					 
					 if($("#frmRegistroProrroga").valid())
						 
						if(fileUploadFinish){	
							aceptarRegistro();
						}else{
							error('No se ha completado la captura del documento probatorio.');
						}
				
				}else{
					error('No se ha seleccionado el caracter para la pr\u00f3rroga.');
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
	 
	 asignartextAreaLimitesProrrogas("observaciones",{styles:{}});
});

var getInfoDocumentos = function () {
	var urlFechas = contextPath + '/prorroga/getFechasInicioFinProrroga';
	var idTipoTramite = $("#tipoTramite").val();
	
	var tipoTramite = {
		'idTipoTramite' : idTipoTramite	
	};
	
	if((idTipoTramite == 29 || idTipoTramite == 34) && fileUploadFinish){
		$.postJSON(urlFechas,tipoTramite, function(data) {
			if(data != null ) {
				
				if(data.fechaInicio != undefined && data.fechaInicio != null ) {
					$("#prorroga\\.fechaInicioProrroga").val(data.fechaInicio);
					$("#miFechaInicio").val(data.fechaInicio);
				}
				
				if(data.fechaFin != undefined && data.fechaFin != null) {
					$("#miFechaFin").val(data.fechaFin);
					$("#prorroga\\.fechaFinProrroga").val(data.fechaFin);
				}
			}
		});
	}
}

function aceptarRegistro(){
	
	 if($("#idCaracter").val() != -1 || $("#tipoTramite").val() == PRORROGA_PERMANENTE || 
					 $("#tipoTramite").val() == PRORROGA_OBSTETRICOS || $("#tipoTramite").val() == PRORROGA_ESTUDIOS
					 || $("#tipoTramite").val() == PRORROGA_TEMPORAL ){
		 
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
					$("#mensajeConf").text(mensaje);
					$("#mensajeConfirmacion").show();
					
					$("#botones").html('');
					$("#frmRegistroProrroga input:text").attr('disabled', 'disabled');
					$("#observaciones").attr('disabled', 'disabled');
					$("#idCaracter").attr('disabled', 'disabled');
					botonesGuardado($("#botones"));
				}else{
					alert('No se ha completado la captura del documento probatorio.');
					
				}
		 }	
	 }else{
		 alert('No se ha seleccionado el caracter para la pr\u00f3rroga.');
	 }
		 
}


function botonesGuardado($div){
	
	var guardar = '<input type="button" id="Aceptar"  onclick="guardarProrroga()" value="Aceptar" class="mboton" />&nbsp;';
	var regresar ='<input type="button" id="regresar" onclick="botonesPrincipales()" value="Regresar" class="mboton" />&nbsp;';
	var cancelar= '<input type="button" value="Cancelar" onclick="cancelarProrroga()" class="mboton" />';
	$div.append(guardar);
	$div.append(regresar);
	$div.append(cancelar);
}


function botonesPrincipales(){
	
	// Oculta el div de lectura de documentos
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
	$("#mensajeConf").html('');
	$("#mensajeConfirmacion").hide();
}


function guardarProrroga(){
	
	$("#prorroga\\.fechaInicioProrroga").val($("#miFechaInicio").val());
	$("#prorroga\\.fechaFinProrroga").val($("#miFechaFin").val());
	$("#prorroga\\.caracter\\.idCaracter").val($("#idCaracter").val());
	$("#prorroga\\.tramite\\.observacion").val($("#observaciones").val());

	$("#frmRegistroProrroga").submit();	
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
				$.blockUI();
				
				$.getJSON(contextPath + "/prorroga/limpiarSession/",{},function(data) {
						
					}   	
				);
				cierraDialogo($(this));
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

function colocarMensajeError($div){
	$div.html('<p id="mensaje" style="color: red">&nbsp;&nbsp;Debe seleccionar una opci\u00F3n</p>');
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


function validarFecha(fecha){
	 alert('fecha '+fecha);
	var fechaArr = fecha.split('/');
	 var aho = fechaArr[2];
	 var mes = fechaArr[1];
	 var dia = fechaArr[0];
	 
	 var plantilla = new Date(aho, mes - 1, dia);//mes empieza de cero Enero = 0

	 if(!plantilla || plantilla.getFullYear() == aho && plantilla.getMonth() == mes -1 && plantilla.getDate() == dia){
		 alert('valida');
	 return true;
	 }else{
		 alert('inValida');
	 return false;
	 }
	 
	 
}