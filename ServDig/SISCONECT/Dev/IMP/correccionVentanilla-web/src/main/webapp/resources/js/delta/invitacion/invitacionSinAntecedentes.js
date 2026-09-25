/**
 * JS para el soporte del modulo invitacion sin antecedentes
 */

var idTableRegPatronales = "#tableResult";
var idDgConfirmar = "#dgConfirmarInvitacion";
var oDgConfirmar;
var oDgTabla;
var cadena = '';
var jsContextoPromocion= getAppContextParaJS() + "/promocion/";
$(document).ready(function() {
	
	// Metodo que llena el combo Criterios de Seleccion
	
	$("form#invitacionSinAntecedentesForm #cveTipocorr").change(function(){
		jsLlenaCriterios();		
	});
	/**
	 * Metodo que da formato a los campos datepicker dd/mm/yy
	 */
	$("form#invitacionSinAntecedentesForm #fechaEmision").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onSelect: function(dateText, inst) { 
			jsValidaFecEmisionInvitacion();
	    }
	});
	
	$("form#invitacionSinAntecedentesForm #fechaIncial").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onSelect: function(dateText, inst) { 
			jsValidaFecPeriodo();
	    }
	});
	
	$("form#invitacionSinAntecedentesForm #fechaFinal").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onSelect: function(dateText, inst) { 
			jsValidaFecEmisionInvitacion();
			jsValidaFecPeriodo();
	    }
	});
	
	$("form#invitacionSinAntecedentesForm #fechaIncial").datepicker('option', 'beforeShowDay', null);
	$("form#invitacionSinAntecedentesForm #fechaFinal").datepicker('option', 'beforeShowDay', null);
	
	/**
	 * Metodo que limita el dia actual en el datepicker
	 */
	$.postJSON("invitacionSinAntecedentes/obtenerFechaServidor.do", null,function(data) {
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
//		alert(JSON.stringify(data, null, 4));
		$('form#invitacionSinAntecedentesForm #fechaEmision, form#invitacionSinAntecedentesForm #fechaIncial, form#invitacionSinAntecedentesForm #fechaFinal').datepicker('option', 'maxDate', data.responseText);
		
	});
	
	/**
	 * Metodo que limita 45 dias antes del dia actual en el datepicker la fechaEmision
	 */
	$.postJSON("invitacionSinAntecedentes/obtenerFechaServidorMinima.do", null,function(data) {
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
//		alert(JSON.stringify(data, null, 4));
		$('form#invitacionSinAntecedentesForm #fechaEmision').datepicker('option', 'minDate', data.responseText);
	});
	
	
	/**
	 * Dialogo de confirmar
	 */
	oDgConfirmar = $(idDgConfirmar).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		height: 150,
		width: 700,
		closeOnEscape: false,
		buttons: {
			"Si": function() {	
				$(this).dialog("close");
				bloquear();
				$("form#invitacionSinAntecedentesForm #cveFkPatronTemp").val(cadena);
				var invitacion = $("#invitacionSinAntecedentesForm").serializeObject(true);		
				$.postJSON("invitacionSinAntecedentes/guardar.do", invitacion, function(data) {
					
					if(data.nuFolioInvitacion==null){
						alert(data.error);
					}else{
						alert("La invitaci\u00f3n ha sido generada con el Folio: " + data.nuFolioInvitacion);	
						limpiarFormulario("#invitacionSinAntecedentesForm");
						cadena = '';
						oDgTabla.fnDraw();
						$("#labelNomRazonSocial").html('');
						$("#labelDomicilio").html('');
					}
					
				}).error(function(data){ 
					desbloquear();
					validarSesionExpirada(data);
				}).complete(function(){
					desbloquear();
					$("#cveFkPatronTemp").val("");
				});		
			}, 
			"No": function() { 
				$(this).dialog("close"); 
			} 
		}
	}); // Fin modal confirmar
	
	/**
	 * Inicia Grid Regisros patronales asociados
	 */
	
	oDgTabla = $(idTableRegPatronales).dataTable({
		 bJQueryUI : true,
		 bFilter : false,
		 bInfo:true,
		 bSort: true,
		 "bPaginate": true,
		 "bAutoWidth" : false,
		 "bServerSide" :	true,
		 "iDeferLoading": 0,
		 "aoColumns" : [ {
			 fnRender :function(oObj){
				 var retVal = '<input type="radio" value="' +
				 oObj.aData['cvePK'] +'" id="radio" class="radioDeteccion" name="radio" /> ';
				 return retVal;
			 }, 
			 aTargets: [0]
		 	},{
				"sTitle" : "Registro Patronal",
				"mDataProp" : "registroPatronal",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Domicilio",
				"mDataProp" : "domicilioCompleto",
				"sClass": "dtCenterClassColumn"
			}
			 ],"bProcessing" : false,
			 "sAjaxSource" : 'invitacionSinAntecedentes/paginar.do',
			 "fnServerData" : function(sSource, aoData, fnCallback) {				
				 	
				 	var variable = '{"cveFkPatronTemp":'+'"'+cadena+'"}';
				 	var wrapper = new Object();
					wrapper.aoData = aoData;

					var variableJson = jQuery.parseJSON(variable);
					wrapper.oForm = variableJson;
					
					
	
					$.postJSON(sSource,wrapper,function(data) { 
						fnCallback(data);
						$("form#invitacionSinAntecedentesForm #cveFkPatronTemp").val('')
				 
					 }).complete(function(){
						desbloquear();
					 }).error(function(datas){ 
							validarSesionExpirada(datas);				 
					 });
		 }
	 });   // termina grid
	
	
});

/**
 * Funcion que llena el combo Criterios de Seleccion
 */
function jsLlenaCriterios(){

	
	var variable = '{"idOrigen":"1","idTipo":"'+$("#cveTipocorr").val()+'"}';		
	var variableJson = jQuery.parseJSON(variable);
	$.postJSON("invitacionSinAntecedentes/cboCriterios.do", variableJson, function(data) {
		
		var myselect=document.getElementById("selectCriterios");
		myselect.options.length = 1;
		
		for(var i = 0 ; i < data.length ; i++){
			myselect.add(new Option(data[i][1], data[i][0]));
		}
		
	});
}

/**
 * Funcion que llena los periodos de correccion
 */
function jsLlenaPeriodos(valor){
		
	$("#labelcveTipocorr").html('');
	var hoy = new Date();
	var hoyDia = '';
	var mes = hoy.getMonth() + 1;
	var anio =  hoy.getFullYear() - 2;
	if(mes < 10){
		mes = '0' + mes;
	}
	if(hoy.getDate() < 10){
		hoyDia = '0' + hoy.getDate();
	}else{
		hoyDia = hoy.getDate();
	}
	var fechaIni = '01-' + '01-' + anio; 
	var fechaFin = hoyDia + '-' + mes + '-' + hoy.getFullYear(); 
	if(valor == '11'){
		$("form#invitacionSinAntecedentesForm #fechaIncial").val(fechaIni);
		$("form#invitacionSinAntecedentesForm #fechaFinal").val(fechaFin);
	}if(valor == '10'){
		fechaIni = '01-' + mes + '-' + hoy.getFullYear();
		$("form#invitacionSinAntecedentesForm #fechaIncial").val(fechaIni);
		$("form#invitacionSinAntecedentesForm #fechaFinal").val(fechaFin);
	}
}

/**
 * Funcion que muestra la seccion Registros Patronales Asociados
 */
function jsMuestraDiv(valor){
	$("#labelRPS").html('');
	if(valor == 'simple'){
		$("form#invitacionSinAntecedentesForm #trRegPatronales").hide();
		$("#labelDom").html('<label>Domicilio del Centro de Trabajo</label>');
	}if(valor == 'varios'){
		$("form#invitacionSinAntecedentesForm #trRegPatronales").show();
		$("#labelDom").html('<label>Domicilio Fiscal</label>');
	}
}

/**
 * Funcion que valida el registro patronal en el domicilio fiscal
 */
function jsValidarRP(){
	$("#labelRegistroPatronal").html('');
	var rp = $("form#invitacionSinAntecedentesForm #regPatronal").val();
	if(rp != '' && rp.length == 10){
		 bloquear();
		 $.postJSON(jsContextoPromocion+"seguimiento/generico/validaPatron.do",rp,function(data) { 
			 
			 if(data == null){
				 	$("#labelRegistroPatronal").html('<label class="etiquetaError">El Patr&oacute;n se encuentra dado de baja o no existe</label>');
					$("#labelNomRazonSocial").html('');
					$("#labelDomicilio").html('');
					$("form#invitacionSinAntecedentesForm #rpValidado").val(-1);
//				}
//				else if(data != null && data.cveRespuestaWS <= JSERROR_WS){
//					$("#labelRegistroPatronal").html('<label class="etiquetaError">'+data.descRespuestaWS +'</label>');
//					$("#labelNomRazonSocial").html('');
//					$("#labelDomicilio").html('');
//					$("form#invitacionSinAntecedentesForm #rpValidado").val(-1);
				}else{
					
					if(data != null && data.cveRespuestaWS <= JSERROR_WS){
						 alert("El registro patronal no pertenece a la subdelegaci\u00f3n");
						 $("#labelNomRazonSocial").html('<label>' + "" + '</label>');
							$("#labelDomicilio").html('<label>' + "" + '</label>');
							$("form#invitacionSinAntecedentesForm #cveFkPatron").val("");	
							$("form#invitacionSinAntecedentesForm #rpValidado").val(0);
							$("form#invitacionSinAntecedentesForm #regPatronal").val("");
							
					}else{
						$("#labelNomRazonSocial").html('<label>' + data.razonSocial + '</label>');
						$("#labelDomicilio").html('<label>' + data.domicilioCompleto + '</label>');
						$("form#invitacionSinAntecedentesForm #cveFkPatron").val(data.cvePK);	
						$("form#invitacionSinAntecedentesForm #rpValidado").val(1);
					}
				}
		
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(){
			desbloquear();	
		});
	}else{
		$("#labelRegistroPatronal").html('<label class="etiquetaError">Capturar un Registro Patronal</label>');
	}
}

/**
 * Funcion que valida que se capturan solo ALFANUMERICOS
 */
function jsvalidarAlfaNumerico(e) { 
	
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ]/;
    te = String.fromCharCode(tecla);
    
    return patron.test(te);
} 


/**
 * Funcion que valida que se capturan solo NUMEROS
 */
function jsvalidarNumerico(e) { 
	
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890]/;
    te = String.fromCharCode(tecla);
    
    return patron.test(te);
} 


function jsGenerar(){
	
	
	if(jsValidaGenerar() == true){
		
		oDgConfirmar.dialog('open');
		
	}
	
}


/**
 * Funcion que valida los campos capturados antes de generar la invitacion
 */
function jsValidaGenerar(){
	
	$("#labelcveFkPatronTemp").html('');
	$("#labelcveTipocorr").html('');
	$("#labelfechaPeriodo").html('');
	$("#labelselectCriterios").html('');
	$("#labelnuFolioInvitacion").html('');
	$("#labelfechaEmision").html('');
	$("#labelRegistroPatronal").html('');
	$("#labelRPS").html('');
	
	var respuesta = false;
	var validaVarios = false;
	
	if($("form#invitacionSinAntecedentesForm #cveTipocorr").val() == '-1'){
		$("#labelcveTipocorr").html('<label class="etiquetaError">Campo Requerido</label>');		
	}
	if($("form#invitacionSinAntecedentesForm #selectCriterios").val() == ''){
		$("#labelselectCriterios").html('<label class="etiquetaError">Campo Requerido</label>');		
	}
	if($("form#invitacionSinAntecedentesForm #nuOficioinv").val() == ''){
		$("#labelnuFolioInvitacion").html('<label class="etiquetaError">Campo Requerido</label>');		
	}
	if($("form#invitacionSinAntecedentesForm #fechaEmision").val() == ''){
		$("#labelfechaEmision").html('<label class="etiquetaError">Campo Requerido</label>');		
	}
	if($("#:checked").val() == undefined || $("#:checked").val() == ''){
		$("#labelRPS").html('<label class="etiquetaError">Campo Requerido</label>');		
	}
	if($("form#invitacionSinAntecedentesForm #regPatronal").val() == ''){
		$("#labelRegistroPatronal").html('<label class="etiquetaError">Campo Requerido</label>');		
	}	
	if($("form#invitacionSinAntecedentesForm #fechaIncial").val() == '' || $("form#invitacionSinAntecedentesForm #fechaFinal").val() == ''){
		$("#labelfechaPeriodo").html('<label class="etiquetaError">Campo Requerido</label>');		
	}
	if($("form#invitacionSinAntecedentesForm #:checked").val() == 'varios' && cadena.length > 2 || $("form#invitacionSinAntecedentesForm #:checked").val() == 'simple'){
		validaVarios = true;
	}else{
		$("#labelcveFkPatronTemp").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	
	if($("form#invitacionSinAntecedentesForm #cveTipocorr").val() != '' && $("form#invitacionSinAntecedentesForm #selectCriterios").val() != '' &&
	   $("form#invitacionSinAntecedentesForm #nuFolioInvitacion").val() != '' && $("form#invitacionSinAntecedentesForm #fechaEmision").val() != '' &&
	   $("form#invitacionSinAntecedentesForm #regPatronal").val() != '' && ( $("#:checked").val() == 'simple' || $("#:checked").val() == 'varios') &&
	   $("form#invitacionSinAntecedentesForm #fechaIncial").val() != '' && $("form#invitacionSinAntecedentesForm #fechaFinal").val() != '' &&
	   validaVarios == true){
		respuesta = true;
	}
	
	if($("form#invitacionSinAntecedentesForm #rpValidado").val()<=0){
		alert("Favor de validar el Registro Patronal");
		respuesta = false;
	}
	
	return respuesta;
}


/**
 * @author Enrique Duran Jimenez
 * Funcion que Agrega un registro patronal a la lista
 */
function jsAgregarRP(){
	
	$("#labelcveFkPatronTemp").html('');
	if($("form#invitacionSinAntecedentesForm #cveFkPatronTemp").val() != '' && $("form#invitacionSinAntecedentesForm #cveFkPatronTemp").val().length == 10){
		bloquear();
		var registroPatronal = $("form#invitacionSinAntecedentesForm #cveFkPatronTemp").val();
		var variable = '{"registroPatronal":"' + registroPatronal + '"}';		
		var variableJson = jQuery.parseJSON(variable);
		 $.postJSON(jsContextoPromocion+"seguimiento/generico/validaPatron.do",registroPatronal,function(data) { 
			 if(data == null){
				 $("form#invitacionSinAntecedentesForm #labelcveFkPatronTemp").html('<label class="etiquetaError">El Patr&oacute;n se encuentra dado de baja o no existe</label>');
				
//			 }else if(data != null && data.cveRespuestaWS <= JSERROR_WS){
//				 $("form#invitacionSinAntecedentesForm #labelcveFkPatronTemp").html('<label class="etiquetaError">'+data.descRespuestaWS+'</label>');
			}else if(data != null && data.cvePK != null){
					if(jsValidaRPTemp(data.cvePK) == true){
						cadena = cadena + data.cvePK + '$';	
						$("form#invitacionSinAntecedentesForm #cveFkPatronTemp").val('')
						oDgTabla.fnDraw();
					}else{
						$("#labelcveFkPatronTemp").html('<label class="etiquetaError">El Registro Patronal ya existe en la lista</label>');
					}
			}else{
				 $("form#invitacionSinAntecedentesForm #labelcveFkPatronTemp").html('<label class="etiquetaError">El Patr&oacute;n se encuentra dado de baja o no existe</label>');
			}
			 
			
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(){
			desbloquear();	
		});
	}else{
		$("#labelcveFkPatronTemp").html('<label class="etiquetaError">Capturar un Registro Patronal valido</label>');
	}
}

/**
 * Funcion que valida el registro patronal antes de generarlo
 */
function jsValidaAgregarRP(){
	$("#labelcveFkPatronTemp").html('');
	
	
	if($("form#invitacionSinAntecedentesForm #regPatronal").val() == ''){
		$("form#invitacionSinAntecedentesForm #labelcveFkPatronTemp").html('<label class="etiquetaError">Se requiere capturar un Domicilio Fiscal</label>');	
	}else if($("form#invitacionSinAntecedentesForm #regPatronal").val() == $("form#invitacionSinAntecedentesForm #cveFkPatronTemp").val()){
		$("form#invitacionSinAntecedentesForm #labelcveFkPatronTemp").html('<label class="etiquetaError">El Registro Patronal Asociado no puede ser el mismo Domicio Fiscal</label>');
	}
	
	if($("form#invitacionSinAntecedentesForm #regPatronal").val() != ''){
		if($("form#invitacionSinAntecedentesForm #regPatronal").val() != $("form#invitacionSinAntecedentesForm #cveFkPatronTemp").val()){
			jsAgregarRP();
		}
	}
	
}

/**
 * Funcion que Elimina un registro patronal en la lista
 */
function jsEliminaRP(){
	var id = $("input[name='radio']:checked").val(); 	
	var cadenaTemp = '';
	if(id!=undefined){
		var array = cadena.split("$"); 
		for(var i=0; i < array.length; i++){
			if(array[i] != id){
				cadenaTemp = cadenaTemp + array[i] + '$';
			}
		}
		cadena = cadenaTemp;
		oDgTabla.fnDraw();
	}else alert("Seleccione un elemento de la lista");
}

function jsLimpiaetiqueta(){
$('form#invitacionSinAntecedentesForm #labelfechaEmision').html('');
}

function jsValidaRPTemp(rp){
	var array = cadena.split("$"); 
	var resp = true;
	for(var i=0; i < array.length; i++){
		
		if(array[i] == rp){
			resp = false;
		}
	}
	
	return resp;
}

function jsCortaTextArea(valor){
	if(valor.length > 200){
		valor = valor.substring(0,199);
	}
	
	$("#txObservaciones").val(valor);
}


function jsValidaFecPeriodo(){
	$("form#invitacionSinAntecedentesForm #labelfechaPeriodo").html('');	
	var fecIni = $("form#invitacionSinAntecedentesForm #fechaIncial").val();
	var fecFin = $("form#invitacionSinAntecedentesForm #fechaFinal").val();
	if(fecIni != '' && fecFin != ''){
		 if(jsValidaFechas(fecIni,fecFin)){
			 $("form#invitacionSinAntecedentesForm #fechaFinal").val(fecFin);
			 $("form#invitacionSinAntecedentesForm #labelfechaPeriodo").html('');
		 }else{
			 $("form#invitacionSinAntecedentesForm #fechaFinal").val('');
			 $("form#invitacionSinAntecedentesForm #labelfechaPeriodo").html('<label class="etiquetaError">La fecha Del: del periodo a corregir no puede ser mayor a la fecha Al</label>');
		 }
	 }
}

function jsValidaFecEmisionInvitacion(){
	$("form#invitacionSinAntecedentesForm #labelfechaEmision").html('');	
	 var fecIni = $("form#invitacionSinAntecedentesForm #fechaFinal").val();
	 var fecFinal = $("form#invitacionSinAntecedentesForm #fechaEmision").val();
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecIni,fecFinal)){
			 $("form#invitacionSinAntecedentesForm #fechaEmision").val(fecFinal);
			 $("form#invitacionSinAntecedentesForm #labelfechaEmision").html('');
		 }else{
			 $("form#invitacionSinAntecedentesForm #fechaFinal").val(fecFinal);
			 $("form#invitacionSinAntecedentesForm #labelfechaEmision").html('');
		 }
	 }
}

function jsValidaFechas(fecIni, fecFin){
	
	var array_fechaIni = fecIni.split("-"); 
	var array_fechaFin = fecFin.split("-"); 
	
	var anioIni = parseInt(array_fechaIni[2],10);
	var anioFin = parseInt(array_fechaFin[2],10);
	
	var mesIni = parseInt(array_fechaIni[1],10);
	var mesFin = parseInt(array_fechaFin[1],10);
	
	var diaIni = parseInt(array_fechaIni[0],10);
	var diaFin = parseInt(array_fechaFin[0],10);
	
	
	if(anioIni > anioFin){
		return false;
	}else {
		if(anioFin == anioIni){
			if(mesIni > mesFin){
				return false;
			}else{
				if(mesIni == mesFin){
					
					if(diaIni > diaFin){
						
						return false;
					}else{
						if(diaIni <= diaFin){
							
							return true;
						}
					}
				}else{
					if(mesIni < mesFin){
						return true;
					}
			  }
			}
		}else{
			if(anioIni < anioFin){
				return true;
			}
		}
	}
	
}

function jsValidaUnoVariosRP(obj){
	
	if(obj.value==10){//CCI
		$(":radio[name='rarioRP'][value='simple']").attr('checked', true);
		$(":radio[name='rarioRP'][value='varios']").attr('checked', false);
		$(":radio[name='rarioRP'][value='simple']").attr('disabled', false);
		$(":radio[name='rarioRP'][value='varios']").attr('disabled', true);
		
		jsMuestraDiv('simple');
		
	}else{
		$(":radio[name='rarioRP'][value='simple']").attr('checked', false);
		$(":radio[name='rarioRP'][value='varios']").attr('checked', false);
		$(":radio[name='rarioRP'][value='simple']").attr('disabled', false);
		$(":radio[name='rarioRP'][value='varios']").attr('disabled', false);
	}
	
}