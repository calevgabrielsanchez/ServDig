var jsContextoPromocion= getAppContextParaJS() + "/promocion/";

/**
 * JS para el soporte del modulo invitacion
 */

var idDgInvitacion	= "#dgInvitacionGuardar"
var tableResult     = "#tableResult";
var idInvitacionAntecedente = "#dgInvitacionAntecedente";
var idDgConfirmar = "#dgConfirmarInvitacion";
var oDtTableResult;
var oDgInvitacion;
var oDgInvitacionAntecedente;
var oDgConfirmar;

$(document).ready(function() {
	
	jsLlenaFuente();
	
		
		$("form#invitacionAntecedenteForm #fechaEmisionFec").datepicker( { 
			dateFormat: 'dd/mm/yy',
			onSelect: function() { 
				jsValidaFecEmisionInvitacion();
				jsValidaFecEmisionAl();
		    }
		});
		
		$("form#invitacionAntecedenteForm #fechaFinalInv").datepicker( { 
			dateFormat: 'dd/mm/yy',
			onSelect: function() { 
				jsValidaFecEmisionAl();
				$("form#invitacionAntecedenteForm #fechaIncialinv").datepicker('option', 'maxDate', $("form#invitacionAntecedenteForm #fechaFinalInv").val());
		    }
		});
		
		$("form#invitacionAntecedenteForm #fechaIncialinv").datepicker( { 
			dateFormat: 'dd/mm/yy',
			onSelect: function() { 
				jsValidaFecEmisionAl();
				$("form#invitacionAntecedenteForm #fechaFinalInv").datepicker('option', 'minDate', $("form#invitacionAntecedenteForm #fechaIncialinv").val());
		    }
		});
		
		$("form#invitacionAntecedenteForm #fechaIncialinv").datepicker('option', 'beforeShowDay', null);
		$("form#invitacionAntecedenteForm #fechaFinalInv").datepicker('option', 'beforeShowDay', null);
		
		$("form#formInvitacion #fecIni,form#formInvitacion #fecFin").datepicker( { 
			dateFormat: 'dd/mm/yy'
		});
		
		$.postJSON("invitacion/obtenerFechaServidor.do", null,function(data) {
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(data){
//			alert(JSON.stringify(data, null, 4));
			
			$('form#invitacionAntecedenteForm #fechaIncialinv').datepicker('option', 'maxDate', data.responseText);
			$('form#invitacionAntecedenteForm #fechaEmisionFec').datepicker('option', 'maxDate', data.responseText);
			$('form#formInvitacion #fecIni,form#formInvitacion #fecFin').datepicker('option', 'maxDate', data.responseText);
		});
		
		$.postJSON("invitacion/obtenerFechaServidorFormat.do", null,function(data) {
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(data){
//			alert(JSON.stringify(data, null, 4));
			$('form#formInvitacion #fecIni,form#formInvitacion #fecFin').datepicker('option', 'maxDate', data.responseText);
		});
		
		$.postJSON("invitacion/obtenerFechaServidorMinima.do", null,function(data) {
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(data){
//			alert(JSON.stringify(data, null, 4));
			$('form#invitacionAntecedenteForm #fechaEmisionFec').datepicker('option', 'minDate', data.responseText);
		});
	 
	 				$("#inviacionBotones").hide();
					$("#menuGrid").hide();						
					$("#btnBuscar").click(
									function() {
										$("form#formInvitacion #labelNuFolio").html('');
										jsLlenaResultados();
									});  // fin change
					
					$("#btnLimpiar").click(function() {
						 limpiarFormulario("#formInvitacion");
						 $("#labelTipoProagrama").html('');
						 $("#labelFecIni").html('');
						 $("#labelFecFin").html('');
						 $("form#formInvitacion #labelNuFolio").html('');
						 if(oDtTableResult != undefined){
							 oDtTableResult.fnDraw();							  
						  }
						
					});
					
					
					// Dialog de Invitacion Antecedentes
					oDgInvitacionAntecedente = $(idInvitacionAntecedente).dialog({
							autoOpen: false,
							modal:true,
							resizable:false,
							width: 930,
							closeOnEscape: false,
							beforeClose :function(event,ui){
							    limpiarFormulario("#invitacionAntecedenteForm");
							},
							buttons: {
								"Aceptar": function() {
									
									if(jsValidaInvitacion() == true){
										
										oDgConfirmar.dialog('open');
									}
								
								},
								"Cancelar": function() { 
									
									$(this).dialog("close"); 
									jsLimpiaFormaInvitacion();
									
								} 
							}
					 });
					
					oDgConfirmar = $(idDgConfirmar).dialog({
						autoOpen: false,
						modal:true,
						resizable:false,
						height: 150,
						width: 700,
						closeOnEscape: false,
						buttons: {
							"Si": function() {	
							if(jsValidaFecEmisionDel() && jsValidaFecEmisionAl()){
								$(this).dialog("close");
								bloquear();
										//var invitacion = $("#invitacionAntecedenteForm").serializeObject(true);
								$("form#invitacionAntecedenteForm #fechaEmision").val($("form#invitacionAntecedenteForm #fechaEmisionFec").val());
								$("form#invitacionAntecedenteForm #fechaIncial").val($("form#invitacionAntecedenteForm #fechaIncialinv").val());
								$("form#invitacionAntecedenteForm #fechaFinal").val($("form#invitacionAntecedenteForm #fechaFinalInv").val());
								var invitacion = $("#invitacionAntecedenteForm").toObject({mode:'first'});
								$.postJSON("invitacion/guardar.do", invitacion, function(data) {
									alert('La invitaci\u00f3n fue generada con el folio : ' + data.nuFolioInvitacion);
									jsLimpiaFormaInvitacion();
									if($("form#invitacionAntecedenteForm #funcionSeguimientoInv").val() != ''){
										eval($("form#invitacionAntecedenteForm #funcionSeguimientoInv").val());
									}
									oDgInvitacionAntecedente.dialog('close');
									$("form#formInvitacion #cveTemp").val('');
									jsLlenaResultados();
									
								}).error(function(data){ 
									desbloquear();
									validarSesionExpirada(data);
									alert('Ocurrio un error');
								}).complete(function(){
									//Instrucciones para el 'complete'
									desbloquear();
								});
							}else{
								$(this).dialog("close");
							}
							}, 
							"No": function() { 
								$(this).dialog("close"); 
							} 
						}
					});
					 
					 $("#btnValidar").click(function(event){
						 event.preventDefault();
						 $("#labelRegistroPatronal").html('');
						
						
						 var patron = $("#registroPatronal").val();
						 if(patron != '' && patron.length == 10){							 
							 bloquear();
							 oDgInvitacion.dialog('close');	
							$.postJSON("invitacion/validaPatron.do",patron,function(data) { 
								if(data == null){
									$("#labelRegistroPatronal").html('<label class="etiquetaError">El registro patronal no es valido</label>');
									$("#razonSocial").val('');
									$("#btnGuardar").hide();
									var id = $('#:checked').val();
									 jsMuestraInv(id);
									 oDgInvitacion.dialog('open');	
									desbloquear();	
								}
								else if(data != null && data.razonSocial != null){
									
									$("#razonSocial").val(data.razonSocial);
									$("#invitacionFormRegistro,#patron").val(patron);
									$("#labelOficio").html('');
									 var id = $('#:checked').val();
									 jsMuestraInv(id);
									 oDgInvitacion.dialog('open');	
									desbloquear();	
								}
							}).error(function(data){ 
								var id = $('#:checked').val();
								 jsMuestraInv(id);
								 oDgInvitacion.dialog('open');	
								desbloquear();	
							}).complete(function(){
								var id = $('#:checked').val();
								 jsMuestraInv(id);
								 oDgInvitacion.dialog('open');	 
								desbloquear();	
							});
					 }else{
						 $("#labelRegistroPatronal").html('<label class="etiquetaError">Capturar un registro patronal valido</label>');
						 $("#razonSocial").html('');
					 }
					 });
});
						

						

	function jsMuestraInv(obj){
		var id = obj;
		bloquear();
		$("form#formInvitacion #cveTemp").val(id);
		var forma = $("#formInvitacion").serializeObject(true);
		$.postJSON("invitacion/invitacionAntecedente.do", forma, function(data) {
			
			var fecha=data.fecFechanotifi;
			if(fecha!=null){
				var datos=fecha.split('-');			
				var fechaNo=datos[2]+"/"+datos[1]+"/"+datos[0];			
				$('#fechaEmisionFec').datepicker('option', 'minDate', fechaNo);
			}
			
			
			jsLimpiarGuardar();
			$("form#invitacionAntecedenteForm #labelFolioAntecedente").html('<label>' + data.folioAntecedente + '</label>');
			if(data.regPatronal != null){
				$("form#invitacionAntecedenteForm #labelRegPatronal").html('<label>' + data.regPatronal + '</label>');
			}
			if(data.razonSocial != null){
				$("form#invitacionAntecedenteForm #labelNomRazonSocial").html('<label>' + data.razonSocial + '</label>');
			}			
			$("form#invitacionAntecedenteForm #cveDeteccion").val(data.cveDeteccion);
			$("form#invitacionAntecedenteForm #cvePromocion").val(data.cvePromocion);
			$("form#invitacionAntecedenteForm #tipoPrograma").val(data.tipoPrograma);
			$("form#invitacionAntecedenteForm #fechaIncialinv").val(data.fechaIncial);
			$("form#invitacionAntecedenteForm #fechaFinalInv").val(data.fechaFinal);
			$("form#invitacionAntecedenteForm #cveFkPatronInv").val(data.cveFkPatron);
			$("form#invitacionAntecedenteForm #fechaNotificacionInv").val(data.fecFechanotifi);
			oDgInvitacionAntecedente.dialog('open');
		}).error(function(data){ 
			validarSesionExpirada(data);
		}).complete(function(){
			desbloquear();
		});			
	}
	
	function replaceAll( text, busca, reemplaza ){
		while (text.toString().indexOf(busca) != -1) 
		   	text = text.toString().replace(busca,reemplaza); 
		   return text; 
	}


	
//	function jsValidaFechas(fecIni, fecFin){
//		
//		var array_fechaIni = fecIni.split("-"); 
//		var array_fechaFin = fecFin.split("-"); 
//		
//		var anioIni = parseInt(array_fechaIni[2],10);
//		var anioFin = parseInt(array_fechaFin[2],10);
//		
//		var mesIni = parseInt(array_fechaIni[1],10);
//		var mesFin = parseInt(array_fechaFin[1],10);
//		
//		var diaIni = parseInt(array_fechaIni[0],10);
//		var diaFin = parseInt(array_fechaFin[0],10);
//		
//		
//		if(anioIni > anioFin){
//			return false;
//		}else {
//			if(anioFin == anioIni){
//				if(mesIni > mesFin){
//					return false;
//				}else{
//					if(mesIni == mesFin){
//						
//						if(diaIni > diaFin){
//							
//							return false;
//						}else{
//							if(diaIni <= diaFin){
//								
//								return true;
//							}
//						}
//					}else{
//						if(mesIni < mesFin){
//							return true;
//						}
//				  }
//				}
//			}else{
//				if(anioIni < anioFin){
//					return true;
//				}
//			}
//		}
//		
//	}
//	
	function jsValida(){
		
		 $("#labelOficio").html('');
		var oficio  = $("#oficio").val();
		var emision = $("#fecEmision").val();
		var regPatronal = $("#registroPatronal").val();
		var razonSocial = $("#razonSocial").val();
		
		if(oficio != '' && emision != '' && regPatronal != '' && regPatronal.length == 10 && razonSocial != ''){
			 $("#labelOficio").html('');
			 return true;
		}else{
			if(razonSocial == ''){
				$("#labelOficio").html('<label class="etiquetaError">Capturar registro patronal valido</label>');
			}
			return false;
		}
		
	}
	
	
	function jsValidaFecFinal(){
		
			 $("form#formInvitacion #labelFecFin").html('');
			 $("form#formInvitacion #labelFecIni").html('');
			 var fecIni = $("form#formInvitacion #fecIni").val();
			 var fecFinal = $("form#formInvitacion #fecFin").val();
			 if(fecIni != '' && fecFinal != ''){
				 if(jsValidaFechas(fecIni,fecFinal)){
					 $("form#formInvitacion #fecFin").val(fecFinal);
					 $("form#formInvitacion #labelFecFin").html('');
				 }else{
					 $("form#formInvitacion #fecFin").val('');
					 $("form#formInvitacion #labelFecFin").html('<label class="etiquetaError">La fecha final no puede ser menor a la fecha inicial</label>');
				 }
			 }
	}
	
	
	
function jsValidaFechas(fecIni, fecFin){
		
		var array_fechaIni = fecIni.split("/"); 
		var array_fechaFin = fecFin.split("/"); 
		
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
	
	function confirmar(){

		
		var id = $('#:checked').val();
		//llega el ID concantendo con el RP separados por un Pipe
		var arrayId_RP = id.split("|");
		
		if(arrayId_RP[0]!=undefined ){
			
			// para las invitaciones de Promocion
			if(arrayId_RP[1] == undefined){
				//alert("otras promociones");
				jsLimpiarGuardar();
				jsMuestraInv(arrayId_RP[0]);
			}else{
				//para invitaciones de Deteccion
				if(arrayId_RP[1] == "NO_TIENE_RP"){
					//parametros RP y id en ese orden
					alert("No tiene un Registro Patronal ");
					return;
				}else{
					validarRegPatronalInvitacion(arrayId_RP[1],arrayId_RP[0]);
				}
			}
			
			
			
		}else alert("Seleccione un elemento de la lista");
		
	}
	
	function jsLimpiarGuardar(){
		
		$("form#invitacionAntecedenteForm #cveDeteccion").val();
		$("form#invitacionAntecedenteForm #cvePromocion").val();
		$("form#invitacionAntecedenteForm #nuOficioinv").val();
		$("form#invitacionAntecedenteForm #fechaEmisionFec").val();
		$("form#invitacionAntecedenteForm #fechaIncialinv").val();
		$("form#invitacionAntecedenteForm #fechaFinalInv").val();
		
	}
	
	function jsvalidarAlfaNumerico(e) { 
		
	    tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true;
	    patron = /[1234567890abcdefghijklmnï¿½opqrstuvwxyzABCDEFGHIJKLMNï¿½OPQRSTUVWXYZ/]/;
	    te = String.fromCharCode(tecla);
	    
	    return patron.test(te);
	} 
	
	function jsvalidarNumerico(e) { 
		
	    tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true;
	    patron = /[1234567890/]/;
	    te = String.fromCharCode(tecla);
	    
	    return patron.test(te);
	} 
	
	function jsLimpiaFechas(){
		
		$("#fecIni").val("");
		$("#fecFin").val("");
		$("#invitacionFormRegistro,#fecFechaemision").val("");
		$("#invitacionFormRegistro,#fecFechanotifi").val("");
		
		$('#formInvitacion2').get(0).reset();
		$('#invitacionFormRegistro').get(0).reset();
		 if(oDtTableResult != undefined){
			  oDtTableResult.fnDraw();
		  }
	}
	
	function jsValidaFechasLimite(){
		var ini = $("form#formInvitacion #fecIni").val();
		var fin = $("form#formInvitacion #fecFin").val();
		var resp = true;
		if(ini != '' && fin != ''){
			
			var array_fechaIni = ini.split("/"); 
			var array_fechaFin = fin.split("/"); 
			
			var anioIni = parseInt(array_fechaIni[2]);
			var anioFin = parseInt(array_fechaFin[2]);		
			
			if(anioIni < anioFin){
				resp = false;
			}else if(anioIni == anioFin){
				resp = true;
			}
		}
		if(resp == false){
			$("form#formInvitacion #fecIni").val("");
			$("form#formInvitacion #fecFin").val("");
			$("form#formInvitacion #labelFecFin").html('<label class="etiquetaError" >El rango de fechas permitido es maximo un a\u00f1o</label>');
		}
	}
	
function jsValidaFecha(fecha){
		
		var resp = true;
		var fechaSis = new Date();
		var diaS = fechaSis.getDate();
		var mesS = fechaSis.getMonth() + 1;
		var anioS = fechaSis.getFullYear();
		var diaP = fecha.substring(0,2);
		var mesP = fecha.substring(3,5);
		var anioP = fecha.substring(6,10);
		
		
		if(anioP > anioS){
			resp = false;
		}else {
			if(anioP == anioS){
				if(mesP > mesS){
					resp = false;
				}else{
					if(mesP == mesS){
						if(diaP > diaS){
							resp = false;
						}else{
							if(diaP <= diaS){
								resp = true;
							}
						}
					}else if(mesP < mesS){
						resp = true;
					}
				}
			}else{
				if(anioP < anioS){
					resp = true;
				}
			}
		}
		
		return resp;		
		
	}
	

function jsLlenaFuente(){
	
	var variable = '{"idOrigen":"1"}';		
	var variableJson = jQuery.parseJSON(variable);
	$.postJSON("invitacion/cboFuente.do", variableJson, function(data) {
		
		var myselect=document.getElementById("selectFuente");
		myselect.options.length = 1;
		
		for(var i = 0 ; i < data.length ; i++){
			myselect.add(new Option(data[i][2], data[i][0]));
		}
		
	});
}

function jsValidaFolio(){
	
	var folio = $("form#formInvitacion #folioTemp").val();
	var array_folio = folio.split("/");
	if(array_folio != undefined && array_folio.length > 1){
		var tipo = array_folio[1];
		
		var regresa;
		if(tipo != ''){
			if(tipo == 'EXO'){
				regresa = 'promocion';
			}
			if(tipo == 'EX'){
				regresa = 'promocion';
			}
			if(tipo == 'SBC'){
				regresa = 'promocion';
			}
			if(tipo == 'SATICB'){
				regresa = 'promocion';
			}
			if(tipo == 'DET'){
				regresa = 'deteccion';
			}	
		}else{
			regresa = 'vacio';
		}
	}else{
		regresa = 'vacio';
	}
	
	return regresa;

}

function jsValidaInvitacion(){
	$("form#invitacionAntecedenteForm #labelFecEmision").html('');
	$("form#invitacionAntecedenteForm #labelOficio").html('');
	$("form#invitacionAntecedenteForm #labelFechaEmisionGuardar").html('');	
	
	var regresa = false;
	if($("form#invitacionAntecedenteForm #nuOficioinv").val() == ''){
		$("form#invitacionAntecedenteForm #labelOficio").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("form#invitacionAntecedenteForm #fechaEmisionFec").val() == ''){
		$("form#invitacionAntecedenteForm #labelFecEmision").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("form#invitacionAntecedenteForm #fechaIncialinv").val() == '' || $("form#invitacionAntecedenteForm #fechaFinalInv").val() == ''){
		$("form#invitacionAntecedenteForm #labelFechaEmisionGuardar").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("form#invitacionAntecedenteForm #nuOficioinv").val() != '' && $("form#invitacionAntecedenteForm #fechaEmisionFec").val() !='' &&
	   $("form#invitacionAntecedenteForm #fechaIncialinv").val() != '' && $("form#invitacionAntecedenteForm #fechaFinalInv").val() != ''){
		regresa = true;
	}
	
	return regresa;
}

function jsValidaForma(){
	
	$("form#formInvitacion #labelFecIni").html('');
	$("form#formInvitacion #labelFecFin").html('');
	$("form#formInvitacion #labelTipoProagrama").html('');
	
	var respuesta = '';
	if($("form#formInvitacion #folioTemp").val() == ''){
		if($("form#formInvitacion #fecIni").val() == ''){
			$("form#formInvitacion #labelFecIni").html('<label class="etiquetaError">Campo Requerido</label>');
		}
		if($("form#formInvitacion #fecFin").val() == ''){
			$("form#formInvitacion #labelFecFin").html('<label class="etiquetaError">Campo Requerido</label>');
		}
		if($("form#formInvitacion #selectFuente").val() == '-1'){
			$("form#formInvitacion #labelTipoProagrama").html('<label class="etiquetaError">Campo Requerido</label>');
		}
		if($("form#formInvitacion #fecIni").val() != '' && 
		   $("form#formInvitacion #fecFin").val() != '' &&
		   $("form#formInvitacion #selectFuente").val() != '-1'){
			
			var tipo = $("form#formInvitacion #selectFuente").val();
			if(tipo == 8 || tipo == 9){
				respuesta = 'deteccion';
			}else if(tipo >= 4 && tipo <= 7){
				respuesta = 'promocion';
			}
		}
		
	}else{
		respuesta = jsValidaFolio();
		$("form#formInvitacion #fecIni").val("");
		$("form#formInvitacion #fecFin").val("");
		$("form#formInvitacion #selectFuente").val("-1");
	}
	
	return respuesta;
}

function jsLimpiaFormaInvitacion(){
	$("form#invitacionAntecedenteForm #nuOficioinv").val('');
	$("form#invitacionAntecedenteForm #fechaEmisionFec").val('');
	$("form#invitacionAntecedenteForm #fechaIncialinv").val('');
	$("form#invitacionAntecedenteForm #fechaFinalInv").val('');
	$("form#invitacionAntecedenteForm #labelFechaEmisionGuardar").html('');
}

function jsValidaFecEmisionInvitacion(){
	$("form#invitacionAntecedenteForm #labelFecEmision").html('');
	
	 var fecIni = $("form#invitacionAntecedenteForm #fechaNotificacionInv").val();
	 var fecFinal = $("form#invitacionAntecedenteForm #fechaEmisionFec").val();
	 fecIni = replaceAll(fecIni, '-','/');
	 fecIni = convertDate(fecIni);
	 if(fecIni != '' && fecFinal != ''){
//		 if(jsValidaFechas(fecFinal, fecIni)){
			 $("form#invitacionAntecedenteForm #fechaEmisionFec").val(fecFinal);
			 $("form#invitacionAntecedenteForm #labelFecEmision").html('');
//		 }else{
//			 $("form#invitacionAntecedenteForm #fechaEmisionFec").val('');
//			 $("form#invitacionAntecedenteForm #labelFecEmision").html('La fecha de emisi&oacute;n no puede ser mayor a la fecha de notificaci&oacute;n');
//			 return false;
//		 }
	 }
	 jsValidaFecEmisionPeriodoInvitacion();
	
}

function convertDate(inputFormat) {
	  var d = new Date(inputFormat);
	  return [d.getDate(), d.getMonth()+1, d.getFullYear()].join('/');
	}

	

function jsValidaFecEmisionAl(){
	$("form#labelFechaEmisionGuardar#labelFechaEmisionGuardar").html('');
	var fecIni = $("form#invitacionAntecedenteForm #fechaEmisionFec").val();
	 var fecFinal = $("form#invitacionAntecedenteForm #fechaFinalInv").val();
	 $("form#invitacionAntecedenteForm #fechaFinalInv").val(replaceAll($("form#invitacionAntecedenteForm #fechaFinalInv").val(),"-","/") );
	 fecFinal = $("form#invitacionAntecedenteForm #fechaFinalInv").val();
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecFinal,fecIni)){
			 $("form#invitacionAntecedenteForm #labelFechaEmisionGuardar").html('<label class="etiquetaError"></label>');
			 $("form#invitacionAntecedenteForm #fechaFinalInv").val(fecFinal);
			 return jsValidaFecEmisionDel();
			 
		 }else{
			 
			 $("form#invitacionAntecedenteForm #labelFechaEmisionGuardar").html('<label class="etiquetaError">La fecha final del periodo a corregir no puede ser mayor a la fecha de emisi&oacute;n</label>');
			 $("form#invitacionAntecedenteForm #fechaFinalInv").val(fecFinal);
			// jsValidaFecEmisionInvitacion();
			 return false;
		 }
	 }

	
}

function jsLlenaResultados(){

	 $("form#formInvitacion #tipoPrograma").val("");
    var validacion = jsValidaForma();
   									
    $("form#formInvitacion #tipoPrograma").val(validacion);
			if (validacion == 'deteccion') {
			
				/**
				 * Inicializacion del grid DETECCION	
				 */
			
				if(oDtTableResult != undefined){
					  oDtTableResult.fnDestroy();
					  
				  }
				
				$(tableResult).html('');
				
				$("#menuGrid").hide();
				$("#tablaResultados").hide();
				
				oDtTableResult = $(tableResult).dataTable(
								{
									bJQueryUI : true,
									bFilter : false,
									bInfo : true,
									bSort : false,
									
									"bPaginate" : true,
									"bAutoWidth" : false,
									"bServerSide" : false,
									"iDeferLoading": 0,
									"aoColumns" : [
											{
												fnRender : function(
														oObj) {
													var retVal = "";
													
													//alert("regPatron :" +oObj.aData['regPatron']);
													if(oObj.aData['regPatron'] != null && oObj.aData['regPatron'] != ""){
														retVal = '<input type="radio" value="' + oObj.aData['cveDeteccion'] + "|"+ oObj.aData['regPatron']
														+ '" id="radioTable" class="radioDeteccion" name="radio" /> ';
													}else{
														retVal = '<input type="radio" value="' + oObj.aData['cveDeteccion']+"|" +"NO_TIENE_RP"
														+ '" id="radioTable" class="radioDeteccion" name="radio" /> ';
													}
													 
													//alert("retVal : " + retVal);
													
													return retVal;
												},
												aTargets : [ 0 ]
											},
											{
												"sWidth": "15%",
												"sTitle" : "Folio",
												"mDataProp" : "nuFoliodeteccion",
												"sClass" : "dtCenterClassColumn"
											},{
												"sWidth": "20%",
												"sTitle" : "Registro Patronal",
												"mDataProp" : "regPatron",
												"sClass" : "dtCenterClassColumn"
											},
											{
												"sWidth": "20%",
												"sTitle" : "Raz&oacute;n Social",
												"mDataProp" : "nomRazonsocial",
												"sClass" : "dtCenterClassColumn"
											}
											,{
												"sWidth": "15%",
												"sTitle" : "Fecha de Detecci&oacute;n",
												"mDataProp" : "fechaAtencion",
												"sClass" : "dtCenterClassColumn"
											},{
												"sWidth": "10%",
												"sTitle" : "Clase de Obra",
												"mDataProp" : "claseObra",
												"sClass" : "dtCenterClassColumn"
											},{
												"sWidth": "10%",
												"sTitle" : "Tipo de Obra",
												"mDataProp" : "tipoObra",
												"sClass" : "dtCenterClassColumn"
											},{
												"sWidth": "10%",
												"sTitle" : "Fase de Obra",
												"mDataProp" : "faseObra",
												"sClass" : "dtCenterClassColumn"
											}
											 ],
									"bProcessing" : true,
									"sAjaxSource" : 'invitacion/paginar.do',
									"fnServerData" : function(sSource,aoData,fnCallback) {

										aoData.push({
											"name" : "sSearch",
											"value" : $('#cveTipocorr').val()
										});
										
										bloquear();
										
										var wrapper = new Object();
										wrapper.aoData = aoData;

										var oForm = $("#formInvitacion").toObject({mode : 'first'});
										wrapper.oForm = oForm;

										$.postJSON(sSource,wrapper,function(data) { fnCallback(data);
										
										
										$("#menuGrid").show();
										$("#tablaResultados").show();
										$("#inviacionBotones").show();
										desbloquear();
										 }).error(function(datas){ 
												validarSesionExpirada(datas);				 
										});
									}
								});

		}     // FIN si el tipo programa es deteccion
		else if(validacion == 'promocion'){
			
			var ini = $("#fecIni").val();
			$("#invFecIni").val(ini);
			var fin = $("#fecFin").val();
			$("#invFecFin").val(fin);
			
			/**
			 * Inicializacion del grid PROMOCION
			 */
			
			if(oDtTableResult != undefined){
				  oDtTableResult.fnDestroy();
			  }
			
			$(tableResult).html('');
			
			oDtTableResult = $(tableResult)
					.dataTable(
							{
								bJQueryUI : true,
								bFilter : false,
								bInfo : true,
								bSort : false,
								
								"bPaginate" : true,
								"bAutoWidth" : false,
								"bServerSide" : false,
								"iDeferLoading": 0,
								"aoColumns" : [
										{
											fnRender : function(
													oObj) {
												var retVal = '<input type="radio" value="' + oObj.aData['cvePromocion']
														+ '" id="radioTable" class="radioDeteccion" name="radio" /> ';
												return retVal;
											},
											aTargets : [ 0 ]
										},
										{
											"sTitle" : "Folio",
											"mDataProp" : "nuFoliopromocion",
											"sClass" : "dtCenterClassColumn"
										},
										{
											"sTitle" : "Oficio Promoci&oacute;n",
											"mDataProp" : "nuOficiopro",
											"sClass" : "dtCenterClassColumn"
										},
										{
											"sTitle" : "Fecha Emisi&oacute;n Promoci&oacute;n",
											"mDataProp" : "fechaAtencion",
											"sClass" : "dtCenterClassColumn"
										}
										,
										{
											"sTitle" : "Fecha Notificaci&oacute;n",
											"mDataProp" : "fechaNotificacion",
											"sClass" : "dtCenterClassColumn"
										}
//										{
//											"sTitle" : "Domicilio",
//											"mDataProp" : "domicilio",
//											"sClass" : "dtCenterClassColumn"
//										}
										 ],
								"bProcessing" : true,
								"sAjaxSource" : 'invitacion/paginarPromocion.do',
								"fnServerData" : function(sSource,aoData,fnCallback) {
									
									aoData.push({
										"name" : "sSearch",
										"value" : $('#cveTipocorr').val()
									});
									
									bloquear();
									
									var wrapper = new Object();
									wrapper.aoData = aoData;
									
									
									var oForm = $("#formInvitacion").toObject({mode : 'first'});
									wrapper.oForm = oForm;

									$.postJSON(sSource,wrapper,function(data) { fnCallback(data); 
																												
									$("#menuGrid").show();
									$("#tablaResultados").show();
									$("#inviacionBotones").show();
									desbloquear();
									}).error(function(data){ 
										validarSesionExpirada(data);
									}).complete(function(){
										desbloquear();
									});
								}
							});

			
		}else if('vacio'){
			$("form#formInvitacion #labelNuFolio").html('<label class="etiquetaError">El n&uacute;mero de folio ingresado no es valido</label>');
		}
			
	
}

function jsValidaFecPeriodoInvitacion(){
	$("form#invitacionAntecedenteForm #labelFechaEmisionGuardar").html('');
	
	 var fecIni = $("form#invitacionAntecedenteForm #fechaIncialinv").val();
	 var fecFinal = $("form#invitacionAntecedenteForm #fechaFinalInv").val();
	 $("form#invitacionAntecedenteForm #fechaFinalInv").val(replaceAll($("form#invitacionAntecedenteForm #fechaFinalInv").val(),"-","/") );
	 fecFinal = $("form#invitacionAntecedenteForm #fechaFinalInv").val();
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecIni,fecFinal)){
			 $("form#invitacionAntecedenteForm #fechaFinalInv").val(fecFinal);
			 $("form#invitacionAntecedenteForm #labelFechaEmisionGuardar").html('');
		 }else{
			 $("form#invitacionAntecedenteForm #fechaFinalInv").val('');
			 $("form#invitacionAntecedenteForm #labelFechaEmisionGuardar").html('<label class="etiquetaError">La fecha AL del periodo a corregir no puede ser mayor a la fecha Del</label>');
		 }
	 }
}

function jsValidaFecEmisionPeriodoInvitacion(){
	$("form#invitacionAntecedenteForm #labelFechaEmisionGuardar").html('');
	
	 var fecIni = $("form#invitacionAntecedenteForm #fechaFinalInv").val();
	 var fecFinal = $("form#invitacionAntecedenteForm #fechaEmisionFec").val();
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecFinal,fecIni)){
			 $("form#invitacionAntecedenteForm #fechaFinalInv").val(fecIni);
			 $("form#invitacionAntecedenteForm #labelFechaEmisionGuardar").html('<label class="etiquetaError"></label>');
			 $("form#invitacionAntecedenteForm #labelFechaEmisionGuardar").html('<label class="etiquetaError"></label>');
		 }else{
			
			 $("form#invitacionAntecedenteForm #labelFechaEmisionGuardar").html('<label class="etiquetaError">La fecha AL del periodo a corregir no puede ser mayor a la fecha de emisi\u00f3n</label>');
			 return false;
		 }
	 }
	 jsValidaFecPeriodoInvitacion();
}

function validarRegPatronalInvitacion(registroPatronal,id) {
	var respuesta = false;
	registroPatronal = registroPatronal.substring(0,10);
	if(registroPatronal != '' && registroPatronal.length == 10){							 
		 bloquear();
			$.postJSON(jsContextoPromocion+"seguimiento/generico/validaPatron.do",registroPatronal,function(data){ 
				
				if(data == null){

					alert("El registro patronal no es v&aacute;lido");
					respuesta = false;
				}
				else if(data != null && data.cveRespuestaWS <= JSERROR_WS){
						alert(data.descRespuestaWS );
						respuesta = false;
					 } else if(data.razonSocial != null){
						 nombre = data.razonSocial;
						 respuesta=true;
						 //var id = $("form#seguimientoSaticbTABForm #cvePromocion").val();
						 //jsMuestraInvitacionSaticB(id);
						 jsLimpiarGuardar();
						
						 jsMuestraInv(id);
						 
					 }					

			}).error(function(data){ 
				alert('Ocurri\u00F3 un error al consultar al patr\u00F3n, intentelo nuevamente por favor');
				validarSesionExpirada(data);
				//alert("respuesta1:: " + respuesta);
				return respuesta;
			}).complete(function(){
				//alert("respuesta2= " + respuesta);
				desbloquear();	
				return respuesta;
			});
	 }
	
	
}

function jsValidaFecEmisionDel(){
	 //var fecEmision = $("form#invitacionAntecedenteForm #fechaEmisionFec").val();
	 var fechaIni =  $('form#invitacionAntecedenteForm #fechaIncialinv').val();
	 var fecFinal = $("form#invitacionAntecedenteForm #fechaFinalInv").val();

	 $('form#invitacionAntecedenteForm #fechaIncial').val(fechaIni);
	 $("form#invitacionAntecedenteForm #fechaFinal").val(fecFinal);
	 
	 var fechaIniHdn =  $('form#invitacionAntecedenteForm #fechaIncial').val();
	 var fecFinalHdn = $("form#invitacionAntecedenteForm #fechaFinal").val();
	 
	 if(fechaIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fechaIni,fecFinal)){
			 $("form#invitacionAntecedenteForm #labelFechaEmisionGuardar").html('<label class="etiquetaError"></label>');
			 $("form#invitacionAntecedenteForm #fechaFinalInv").val(fecFinal);
			 return true;
		 }else{
			 $("form#invitacionAntecedenteForm #labelFechaEmisionGuardar").html('<label class="etiquetaError">La fecha final del periodo a corregir no puede ser menor a la fecha de inicio</label>');
			 $("form#invitacionAntecedenteForm #fechaFinalInv").val(fecFinal);
			 return false;
		 }
	 
	 }
	 
}
