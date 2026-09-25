/**
 * JS para el soporte del modulo invitacion
 */

var idDgInvitacion	= "#dgInvitacionGuardar"
var tableResult     = "#tableResult";
var oDtTableResult;
var oDgInvitacion;

$(document).ready(function() {
	
	// Fecha de Inicio y Termino
	 $( "#fecIni, #fecFin, #fecEmision" ).datepicker( { dateFormat: 'dd-mm-yy' });
	 
	 				$("#inviacionBotones").hide();
					$("#menuGrid").hide();						
					$("#btnBuscar").click(
									function() {
										
										
											ini = $("#fecIni").val();
											fin = $("#fecFin").val();
											
											if(ini.length == 10 && fin.length == 10){
												
												$("#labelTipoProagrama").html('');
												$("#labelFecIni").html('');
												$("#labelFecFin").html('');
												  
												
												$("#labelFecIni").html('');
												$("#labelFecFin").html('');
												// validar que fec fin no sea menos a fec ini
												var resp = jsValidaFechas(ini,fin);
												if(resp){
													$("#labelFecIni").html('');
													$("#labelFecFin").html('');
													x = $("#cveTipocorr").val();
												
													if (x == 8 || x == 9) {
													
														/**
														 * Inicializacion del grid DETECCION	
														 */
														if(oDtTableResult != undefined){
															  oDtTableResult.fnDestroy();
														  }
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
																			"bServerSide" : true,
																			"aoColumns" : [
																					{
																						fnRender : function(
																								oObj) {
																							var retVal = '<input type="radio" value="' + oObj.aData['cveDeteccion']
																									+ '" id="radioTable" class="radioDeteccion" name="radio" /> ';
																							return retVal;
																						},
																						aTargets : [ 0 ]
																					},
																					{
																						"sTitle" : "Folio",
																						"mDataProp" : "nuFoliodeteccion",
																						"sClass" : "dtCenterClassColumn"
																					},{
																						"sTitle" : "Registro Patronal",
																						"mDataProp" : "desDependenciapub",
																						"sClass" : "dtCenterClassColumn"
																					},
																					{
																						"sTitle" : "Raz&oacute;n Social",
																						"mDataProp" : "nomRazonsocial",
																						"sClass" : "dtCenterClassColumn"
																					}
//																					,{
//																						"sTitle" : "Domicilio",
//																						"mDataProp" : "domCalle",
//																						"sClass" : "dtCenterClassColumn"
//																					}
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
			
																				var oForm = $("#formInvitacion2").toObject({mode : 'first'});
																				wrapper.oForm = oForm;
			
																				$.postJSON(sSource,wrapper,function(data) { fnCallback(data);
																				
																				
																				$("#menuGrid").show();
																				$("#tablaResultados").show();
																				$("#inviacionBotones").show();
																				desbloquear();
																				});
																			}
																		});
		
												}     // FIN si el tipo programa es deteccion
												else if(x >= 3 && x <= 7){
													
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
													oDtTableResult = $(tableResult)
															.dataTable(
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
																				//,
//																				{
//																					"sTitle" : "Fecha Notificaci&oacute;n",
//																					"mDataProp" : "fecFechanotif",
//																					"sClass" : "dtCenterClassColumn"
//																				},
//																				{
//																					"sTitle" : "Domicilio",
//																					"mDataProp" : "domicilio",
//																					"sClass" : "dtCenterClassColumn"
//																				}
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
																			
																			var ini = $("#fecIni").val();
																			
																			$("#invitacionFormRegistro,#fecFechaemision").val(ini);
																			var fin = $("#fecFin").val();
																			$("#invitacionFormRegistro,#fecFechanotifi").val(fin);
																			
																			var oForm = $("#invitacionFormRegistro").toObject({mode : 'first'});
																			wrapper.oForm = oForm;
		
																			$.postJSON(sSource,wrapper,function(data) { fnCallback(data); 
																																						
																			$("#menuGrid").show();
																			$("#tablaResultados").show();
																			$("#inviacionBotones").show();
																			desbloquear();
																			});
																		}
																	});
		
													
												}else if (x == '-1'){
													$("#labelTipoProagrama").html('<label class="etiquetaError">Seleccionar Tipo Programa</label>');
													}
											}else{
												$("#fecFin").val('');
												$("#labelFecFin").html('<label class="etiquetaError">fecha final no puede ser menor a fecha Inicial</label>');
											}	
											}else{
											
												 
												//$('#formInvitacion').get(0).reset();
												//$('#formInvitacion2').get(0).reset();
												if($("#fecIni").val() == ''){
													$("#labelFecIni").html('<label style="color: red;">Campo requerido</label>');
												}
												if($("#fecFin").val() == ''){
													$("#labelFecFin").html('<label style="color: red;">Campo requerido</label>');
												}
												
												
											}
										
									});  // fin change
					
					
					// Dialog de Elemento a Tabla de Resultado de Invitacion
					oDgInvitacion = $(idDgInvitacion).dialog({
							autoOpen: false,
							modal:true,
							resizable:false,
							width: 730,
							buttons: {
								
								"Guardar": function() {
									
									$("#labelOficio").html('');
									$("#labelRegistroPatronal").html('');
									if(jsValida()){
										bloquear();
										var tipoPrograma = $("#cveTipocorr").val();
										
										$("#invitacionFormRegistro,#tipoPrograma").val(tipoPrograma);
										var oForm = $("#invitacionFormRegistro").toObject({mode : 'first'});
										$.postJSON("invitacion/guardar.do",oForm,function(data) { 																
																				
																				$("#menuGrid").hide();			
																				$("#tablaResultados").hide();
																				
																				alert('La invitacion se guardo correctamente con el folio : ' + data.nuFolioInvitacion);
																				oDgInvitacion.dialog('close');	
																				$("#inviacionBotones").hide();
																				$('#formInvitacion').get(0).reset();
																				$('#formInvitacion2').get(0).reset();
																				$('#invitacionFormRegistro').get(0).reset();
																				
										}).error(function(data){ 
											alert('Verificar Clave Patron');
										}).complete(function(){
											
											desbloquear();
											
											
										});
									}else{
										if($("#oficio").val() == ''){
											$("#labelOficio").html('<label class="etiquetaError">Favor de capturar los campos requeridos</label>');
										}
										if($("#fecEmision").val() == ''){
											$("#labelOficio").html('<label class="etiquetaError">Favor de capturar los campos requeridos</label>');
										}
										if($("#registroPatronal").val() == ''){
											$("#labelOficio").html('<label class="etiquetaError">Favor de capturar los campos requeridos</label>');
										}
									}
								}, 
								"Cancelar": function() { 
								
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
		
		oDgInvitacion.dialog('open');
			
		
		$("#invitacionGuardarIni").show();	
		$("#dgInvitacionGuardar").show();	
		$("#pie").show();
		bloquear();
		
		var x = $("#cveTipocorr").val();
	
		if (x == 8 || x == 9) {  // deteccion
			$("#invitacionFormRegistro,#cveDeteccion").val(obj);
			$("#tdFolioDet").show();
			$("#tdInputFolioDet").show();
			$("#tdInputFolioDet").attr("colspan","3");
			$("#tdFolioProm").hide();
			$("#tdInputFolioProm").hide();
			
			var variable = jQuery.parseJSON(obj);
			$.postJSON("invitacion/buscaFolioDeteccion.do",variable,function(data) { 
				
				$("#folioDeteccion").val(data.nuFoliodeteccion);
				
			}).error(function(data){ 
				
			}).complete(function(){
				
				desbloquear();
			});
		}else if(x >= 3 && x <= 7){  // promocion
			$("#invitacionFormRegistro,#cvePromocion").val(obj);
			$("#tdFolioDet").hide();
			$("#tdInputFolioDet").hide();
			$("#tdInputFolioDet2").hide();
			$("#tdFolioProm").show();
			$("#tdInputFolioProm").show();
			$("#tdInputFolioProm").attr("colspan","3");
			
			
			var variable = jQuery.parseJSON(obj);
			$.postJSON("invitacion/buscaFolioPromocion.do",variable,function(data) { 
				
				$("#folioPromocion").val(data.nuFoliopromocion);
			
			}).error(function(data){ 
				desbloquear();
			}).complete(function(){
				
				desbloquear();
				
				
			});
		}
		
		
		$("#pie").show();	
		
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
	
	function jsValidaFecEmision(fecEmision){
		
		if(jsValidaFecha(fecEmision)){
			 $("#fecEmision").val(fecEmision);
			 $("#labelOficio").html('');
		}else{
			 $("#fecEmision").val("");
			 $("#labelOficio").html('<label class="etiquetaError">La fecha emision no puede ser mayor al dia actual</label>');
		}
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
		if(id!=undefined){
			jsLimpiarGuardar();
			jsMuestraInv(id);
		}else alert("Seleccione un elemento de la lista");
		
	}
	
	function jsLimpiarGuardar(){
		$("#labelOficio").html('');
		$("#labelFecEmision").html('');
		$("#labelRegistroPatronal").html('');
		$("#folioDeteccion").val("");
		$("#oficio").val("");
		$("#fecEmision").val("");
		$("#registroPatronal").val("");
		$("#razonSocial").val("");
		$("#folioPromocion").val("");
		
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
	