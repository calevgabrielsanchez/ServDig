/**
 * JS para el soporte del modulo invitacion
 */

var idDgInvitacion	= "#dgInvitacionGuardar"
var tableResult     = "#tableResult";
var oDtTableResult;
var oDgInvitacion;

$(document).ready(function() {
	
	// Fecha de Inicio y Termino
	 $( "#fecIni, #fecFin" ).datepicker( { dateFormat: 'dd-mm-yy' });					
					
					/**
					 * Inicializacion del grid DETECCION	
					 */
	 				$("#labelOficio").html('');
					oDtTableResult = $(tableResult).dataTable(
									{
										bJQueryUI : true,
										bFilter : false,
										bInfo : true,
										bSort : false,
										
										"bPaginate" : true,
										"bAutoWidth" : false,
										"bServerSide" : true,
										"aoColumns" : [
												{
													fnRender : function(
															oObj) {
														var retVal = '<input type="radio" value="' + oObj.aData['cveInvitacion']
																+ '" id="radioTable" class="radioDeteccion" name="radio" /> ';
														return retVal;
													},
													aTargets : [ 0 ]
												},
												{
													"sTitle" : "Folio",
													"mDataProp" : "nuFolioInvitacion",
													"sClass" : "dtCenterClassColumn"
												},{
													"sTitle" : "Fecha Emisi&oacute;n",
													"mDataProp" : "fechaEmision",
													"sClass" : "dtCenterClassColumn"
												}
												 ],
										"bProcessing" : true,
										"sAjaxSource" : 'cancelarInvitacion/paginar.do',
										"fnServerData" : function(sSource,aoData,fnCallback) {

											aoData.push({
												"name" : "sSearch",
												"value" : $('#cveTipocorr').val()
											});
											
											//bloquear();
											
											var wrapper = new Object();
											wrapper.aoData = aoData;

											var oForm = $("#formInvitacion2").toObject({mode : 'first'});
											wrapper.oForm = oForm;

											$.postJSON(sSource,wrapper,function(data) { fnCallback(data);
											
											desbloquear();
											});
										}
									});
					
					
					// Dialog de Elemento a Tabla de Resultado de Invitacion
					oDgInvitacion = $(idDgInvitacion).dialog({
							autoOpen: false,
							modal:true,
							resizable:false,
							width: 830,
							buttons: {
								
								"Aceptar": function() {
									
									$("#labelOficio").html('');
									var oficio = $("#oficio").val();
									var motivo = $("#idMotivocancelacion").val();
									var auditor = $("#selectAuditor").val();
									var cveInvitacion = $("#cveInvitacion").val();
									var nuFolio = $("#folioDeteccion").val();
									
									var variable = '{' +
												   '"cveInvitacion":"'+cveInvitacion+'",'+
												   '"idMotivoCancelacion":"'+motivo+'",'+
												   '"numOficioCancelacion":"'+oficio+'",'+
												   '"nuFolioInvitacion":"'+nuFolio+'",'+
												   '"cveIdAuditor":"'+auditor+'"}';	
									
									var variableJson = jQuery.parseJSON(variable);
									$.postJSON("cancelarInvitacion/guardarCancelacion.do",variableJson,function(data) { 
										if(data != null){
											
											oDgInvitacion.dialog("close");
											alert('Se cancelo la invitacion con el folio : ' + data.nuFolioInvitacion);
											oDtTableResult.fnDraw();
											
										}
										
									});
								}, 
								"Cancelar": function() { 
								
									$("#cveInvitacion").val("");
									$(this).dialog("close"); 
							} 
								
							}
					 });
					
					
					$("#btnBuscar").click(function(){
						$("#labelOficio").html('');
						if($("#fecIni").val() != '' && $("#fecFin").val() != '' && $("#cveTipocorr").val() != '-1'){
							oDtTableResult.fnDraw();
						}else{
							$("#labelOficio").html('<label class="etiquetaError" >Capturar campos requeridos</label>');
						}
					});
					
});

					

	
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
	
	
	
	function jsValidaFecFinal(fecFinal){
		
		if(jsValidaFecha(fecFinal)){
			 $("#fecFin").val(fecFinal);
			 $("#labelFecFin").html('');
			 var fecIni = $("#fecIni").val();
			 if(fecIni != '' && fecFinal != ''){
				 if(jsValidaFechas(fecIni,fecFinal)){
					 $("#fecFin").val(fecFinal);
					 $("#labelFecFin").html('');
				 }else{
					 $("#fecFin").val('');
					 $("#labelFecFin").html('<label class="etiquetaError">La fecha final no puede ser menor a la fecha inicial</label>');
				 }
			 }
		}else{
			 $("#fecFin").val("");
			 $("#labelFecFin").html('<label class="etiquetaError">La fecha final no puede ser mayor al dia actual</label>');
		}
	}
	
	function jsValidaFecIni(fecIni){
		
		if(jsValidaFecha(fecIni)){
			 $("#fecIni").val(fecIni);
			 $("#labelFecIni").html('');
		}else{
			 $("#fecIni").val("");
			 $("#labelFecIni").html('<label class="etiquetaError" >La fecha inicial no puede ser mayor al dia actual</label>');
		}
	}
	
	
	function jsValidaFechas(fecIni, fecFin){
			
		var array_fechaIni = fecIni.split("-"); 
		var array_fechaFin = fecFin.split("-"); 
		
		var anioIni = parseInt(array_fechaIni[2]);
		var anioFin = parseInt(array_fechaFin[2]);
		
		var mesIni = parseInt(array_fechaIni[1]);
		var mesFin = parseInt(array_fechaFin[1]);
		
		var diaIni = parseInt(array_fechaIni[0]);
		var diaFin = parseInt(array_fechaFin[0]);
		
		
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
		if(id!=undefined){
			jsLimpiarGuardar();
			jsMuestraInv(id);
		}else alert("Seleccione un elemento de la lista");
		
	}

	function jsMuestraInv(cveInvitacion){
		
		$("#cveInvitacion").val(cveInvitacion);
		var variable = '{"cveInvitacion":'+'"'+cveInvitacion+'"}';
		var variableJson = jQuery.parseJSON(variable);
		bloquear();
		$.postJSON("cancelarInvitacion/buscaInvitacion.do",variableJson,function(data) { 
			if(data == null){
				desbloquear();
			}else{
				$("#folioDeteccion").val(data.nuFolioInvitacion);
				jsLlenaAuditor();
				oDgInvitacion.dialog("open");	
				desbloquear();
			}
			
		});
		
	}
	
	function jsLimpiarGuardar(){
		
		$("#labelOficio").html('');
		$("#folioDeteccion").val('');
		$("#oficio").val('');
		$("#cveInvitacion").val('');
		$("#selectAuditor").val('-1');
		$("#idMotivocancelacion").val('-1');
		
	}
	
	function jsvalidarAlfaNumerico(e) { 
		
	    tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true;
	    patron = /[1234567890abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ]/;
	    te = String.fromCharCode(tecla);
	    
	    return patron.test(te);
	} 
	
	function jsvalidarNumerico(e) { 
		
	    tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true;
	    patron = /[1234567890]/;
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
		var ini = $("#fecIni").val();
		var fin = $("#fecFin").val();
		var resp = true;
		if(ini != '' && fin != ''){
			
			var array_fechaIni = ini.split("-"); 
			var array_fechaFin = fin.split("-"); 
			
			var anioIni = parseInt(array_fechaIni[2]);
			var anioFin = parseInt(array_fechaFin[2]);
			
			var mesIni = parseInt(array_fechaIni[1]);
			var mesFin = parseInt(array_fechaFin[1]);
			
			var diaIni = parseInt(array_fechaIni[0]);
			var diaFin = parseInt(array_fechaFin[0]);
			
			var anioFinTemp = anioFin - 1;
			
			if(anioIni < anioFin){
				if(anioIni == anioFinTemp){
					if(mesIni > mesFin){
						resp = true;
					}else if(mesIni == mesFin){
						if(diaIni >= diaFin){
							resp = true;
						}else if(diaIni < diaFin){
							resp = false;
						}
					}if(mesIni < mesFin){
						resp = false;
					}
				}else if(anioIni < anioFinTemp){
					resp = false;
				}
			}else if(anioIni == anioFin){
				resp = true;
			}
		}
		if(resp == false){
			$("#fecIni").val("");
			$("#labelFecIni").html('<label class="etiquetaError" >El rango de fechas permitido es maximo un a\u00f1o</label>');
		}
	}
	
	function jsLlenaAuditor(){
		var cveInvitacion = $("#cveInvitacion").val();
		var variable = '{"cveInvitacion":'+'"'+cveInvitacion+'"}';
		var variableJson = jQuery.parseJSON(variable);
		bloquear();
		$.postJSON("cancelarInvitacion/llenaAuditor.do",variableJson,function(data) {
			var myselect=document.getElementById("selectAuditor");
			myselect.options.length = 1;
			
			for(var i = 0 ; i < data.length ; i++){
				
				myselect.add(new Option(data[i][1], data[i][0]));
			}
		});
	}
	
	