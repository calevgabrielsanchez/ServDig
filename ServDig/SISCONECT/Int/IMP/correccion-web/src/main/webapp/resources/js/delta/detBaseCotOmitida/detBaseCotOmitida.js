/**
 * JS para el soporte del modulo DETERMINACION DE LA BASE DE COTIZACION OMITIDA
 */

var tableResult     = "#dtdetBaseCotOmitida";
var idDgNuevoElemento	= "#dgdetBaseCotOmitidaBaseMayorCabecera"
var oDtTableResult;
var oDgNuevoElemento;
var obj = new Object();
var mode = 'impar';
var fijoMenos;
var impBalanzaAux;

$(document).ready(function() {
	
	$("#detBaseCotOmitidaBaseMayorJSP").hide();
	
		$("#buscar").click(function(){
			
						oDtTableResult = $(tableResult).dataTable({
							bJQueryUI : true,
							bFilter : false,
							bInfo:true,
							bSort: false,
							"bPaginate": true,
							"bAutoWidth" : true,
							"bServerSide" :	true,
							"aoColumns" : [ {
								fnRender :function(oObj){
									var retVal = '<input type="radio" value="' +
									oObj.aData['cveDetBaseCotOmit'] +'" id="radioTable" class="radioDeteccion" name="radio"/> ';
									return retVal;
								}, 
								aTargets: [0]
								},{
									"sTitle" : "Folio Correcci&oacute;n",
									"mDataProp" : "folioCorreccion",
									"sClass": "dtCenterClassColumn"
								},{
									"sTitle" : "Raz&oacute;n Social",
									"mDataProp" : "RazonSocial",
									"sClass": "dtCenterClassColumn"
								},{
									"sTitle" : "Ejercicio",
									"mDataProp" : "ejercicio",
									"sClass": "dtCenterClassColumn"
								}
								],"bProcessing" : true,
								"sAjaxSource" : 'detBaseCotOmitida/paginar.do',
								"fnServerData" : function(sSource, aoData, fnCallback) {				
									
									bloquear();
									var anexo = $("#selectRegPatron").val();
									var ejercicio = $("#selectEjercicio option:selected").text();									
									
									$("#formData,#cveAnexoSolCorrPat").val(anexo);
									$("#formData,#cveEjercicio").val(ejercicio);
									
									var wrapper = new Object();
									wrapper.aoData = aoData;

									var oForm = $("#formData").toObject({mode : 'first'});
									wrapper.oForm = oForm;
									
									$.postJSON(sSource, wrapper, function(data) {										
										fnCallback(data);
										desbloquear();
									 }).error(function(datas){ 
											validarSesionExpirada(datas);										
									});
								}
							});
		});
		
		// Dialog de nuevo elemento 
		oDgNuevoElemento = $(idDgNuevoElemento).dialog({
				autoOpen: false,
				modal:true,
				resizable:true,
				width: 800,
				buttons: {
					"Guardar": function(event){
						event.preventDefault();
						$("#labelimpISR").html('');
						$("#labelimpInmediatoA").html('');
						$("#labelimpAdicional").html('');
						var valida = true;
						valida = jsValidaGuardar();
						if(valida == true){
							jsGuardaDetBaseCotOm();
							$(this).dialog("close");
						}else{
							if($("#impISR").val() == ''){
								$("#labelimpISR").html('<label style="color: red;"> Campo Requerido </label>');
							}
							if($("#impInmediatoA").val() == ''){
								$("#labelimpInmediatoA").html('<label style="color: red;"> Campo Requerido </label>');
							}
							if($("#impAdicional").val() == ''){
								$("#labelimpAdicional").html('<label style="color: red;"> Campo Requerido </label>');
							}
						}
					}, 
					"Cancelar": function(event){
						event.preventDefault();
						jsLimpiarForma();
						$("#tableConceptos tr").each(function(){
							 $(this).remove();							
						});
					$(this).dialog("close"); 
				} 
					
				}
		 });
		
		$("#btnAgregar").click(function(event){
			
			event.preventDefault();
			var validacion = jsValidaAgregarMain();
			if(validacion == true){
				$("#labelFolioMain").html('');
				var anexo = $("#selectRegPatron").val();
				var ejercicio = $("#selectEjercicio option:selected").text();	
				var variable = '{' +
							   '"cveEjercicio":"'+ejercicio+'",'+
							   '"cveAnexoSolCorrPat":"'+anexo+'"}';					
				var variableJson = jQuery.parseJSON(variable);
				$.postJSON("detBaseCotOmitida/validaDetBaseCotOm.do",variableJson,function(data) { 
					if(data != null &&  !data.isCedulaIVacia){
						alert('ya existe una solicitud asociada a el ejercicio : ' + ejercicio);
						$(this).dialog("close"); 					
					}else{
						jsLimpiarForma();
						oDgNuevoElemento.dialog('open');
						//Limpiar tabla
						 $("#tableConceptos tr").remove();
						// llena Datos Cabecera		
						jsllenaCabecera();
						// llena campo 8
						jsllenaOcho();
						// Llena sección de excedentes topados
						jsCalculaExcedente();
						// llena seccion menos
						jsllenaBalanza();
						// llena Base cotizacion pagada
						jsCalculaCatorce();

						// Suma Balanza
						jsSumaBalanzaComp(0);
						
					}			
				});		
			}else{
				if($.trim($('#folioCorreccionMain').val()) == ""){
					$("#labelFolioMain").html('<label style="color: red;">Campo requerido</label>');
				 }
				 
				if($("#selectRegPatron option:selected").text() == 'Seleccione...'){
					$("#labelRegPatronalMain").html('<label style="color: red;">Campo requerido</label>');
				}
				if($("#selectEjercicio option:selected").text() == 'Seleccione...'	){
					$("#labelEjercicioMain").html('<label style="color: red;">Campo requerido</label>');
				}
			
			}
		});
		
		$("#btnAgregarConceptos").click(function(event){
			
			event.preventDefault();
			// Agregamos las opciones
			var selectCon = $("#selectConceptos").val();
			var select = document.getElementById("selectConceptos");
			var table = $("#tableConceptos");
			if(selectCon != '-1'){
				for(var i = 0; i < obj.length; i++){
					if(obj[i].cvePercepcion == selectCon){
						table.append('<tr valign="top" class="par"><td width="40%"><label><b>' + obj[i].txRemuneracion + '</b></label></td><td align="left" width="25%">$<input type="text" id="tCon' + obj[i].cvePercepcion + '" onchange="jsactualizaTotalConceptos();moneyMask(this,2);" onkeypress="return jsvalidarNumeros(event);" maxlength="10"><a href="#" id="' + i + '" onclick="jsBorrarTRConceptos();"><label style="color: red;">  [X] </label></a></td></tr>');
						
						// Borramos la opcion del combo
						for(var j = 0; j < select.options.length; j++){
							if(select.options[j].value == selectCon){
								select.options[j] = null;
							}
						}
						//jsSumaImpConceptos(obj[i].sumRemuneracion);
					} 
				}
			}
		});
		
		triggerPatronInternet('','folioCorreccionMain','btnValidarDetBase');
		
		
		
		
		
		
		triggerPatronInternet('selectRegPatron','','selectRegPatron');
		var valCombo=-1;
		$("#selectRegPatron option").each(function(){
		    var val=$("#regPatronal").val();
		    if($(this).text()==val){
		         valCombo=$(this).val();		         
		    }
		   
		});
		if(valCombo!=-1){
			$("#selectRegPatron").val(valCombo);
			$("#selectRegPatron").trigger('onchange');
		}
});



	function validar(){
		$("#labelFolioMain").html('');
		var folio = $.trim($('#folioCorreccionMain').val());
		if(folio == ''){
			$("#labelFolioMain").html('<label style="color: red;">Campo requerido</label>');
			return false;
		}else{
			var variable = '{"nuFolio":'+'"'+folio+'"}';
			var variableJson = jQuery.parseJSON(variable);
			bloquear();
			$.postJSON_Sync("detBaseCotOmitida/buscaFolio.do",variableJson,function(data) { 
				if(data == null){
					$("#labelFolioMain").html('<label style="color: red;">Folio no valido</label>');
					var myselect=document.getElementById("selectRegPatron");
					myselect.options.length = 1;
					var myselect2=document.getElementById("selectEjercicio");
					myselect2.options.length = 1;
					desbloquear();
				}else{
					var myselect=document.getElementById("selectRegPatron");
					myselect.options.length = 1;
					var myselect2=document.getElementById("selectEjercicio");
					myselect2.options.length = 1;
					for(var i = 0 ; i < data.length ; i++){
						myselect.add(new Option(data[i].patron.registroPatronal, data[i].cveAnexoSolicitudCorrPat));
					}
					desbloquear();
				}
				
			});
		}
		
	}
	
	function jsLlenaEjercicio(valor){
		
		var variable = '{"cveAcexoCorrPat":'+'"'+valor+'"}';
		var variableJson = jQuery.parseJSON(variable);
		bloquear();
		$.postJSON("detBaseCotOmitida/buscaEjercicio.do",variableJson,function(data) { 
			if(data == null){
				alert('No tiene ejercicio asignado');
				desbloquear();
			}else{
				var myselect=document.getElementById("selectEjercicio");
				myselect.options.length = 1;
				for(var i = 0 ; i < data.length ; i++){
					myselect.add(new Option(data[i].cveEjercicio, data[i].cveAcexoCorrPat));
				}	
				desbloquear();
			}
			
		});
	}
	
	function jsllenaBalanza(){
		
		var anexo = $("#selectRegPatron").val();
		var ejercicio = $("#selectEjercicio option:selected").text();	
		var tpSeleccion = $("#tpSeleccion").val();
		var variable = '{' +
					   '"cveEjercicio":"'+ejercicio+'",'+
					   '"tpSeleccionBalanzaOAux":"'+tpSeleccion+'",'+
					   '"cveAnexosolcorrpat":"'+anexo+'"}';					
		var variableJson = jQuery.parseJSON(variable);
		$.postJSON("detBaseCotOmitida/buscaPercepciones.do",variableJson,function(data) { 
			if(data != null){
				obj = data;
				var total = 0;
				var tipoTD = 'par';
				var tabla = $("#tablaMenos");
				var trs = '';
				var myselectCon =document.getElementById("selectConceptos");
				myselectCon.options.length = 1;
				for(var i = 0 ; i < data.length; i++){
					trs = trs + '<tr id="trMenos' + i + '" valign="top" class="' + tipoTD + '"><td align="left" width="70%"><label>' + data[i].txRemuneracion + '</label></td><td align="left" width="30%">$<input type="text" id="inMenos' + i + '" maxlength="10" value="' + moneyMaskDT(data[i].sumRemuneracion,2) + '" onkeypress="return jsvalidarNumeros(event);" onchange="jsactualizaTotalPercepciones();jsCalculaAutoDet();moneyMask(this,2)"><a href="#" id="' + i + '" onclick="jsBorrarTRPersepcion();"><label style="color: red;">  [X] </label></a></td></tr>' + '\n';
					// Llena Select Conceptos omitidos
					myselectCon.add(new Option(data[i].txRemuneracion, data[i].cvePercepcion));
					var remuneracion = data[i].sumRemuneracion;
					if(remuneracion == '' || remuneracion == null){
						remuneracion = 0;
					}
					total = parseFloat(total) + parseFloat(data[i].sumRemuneracion);
					var roundedNumber = roundNumber(total,2);
					fijoMenos = roundedNumber;
				}
				fijoMenos = fijoMenos + roundNumber(Number($("#excedentesTopados").val()),2);
				roundedNumber = roundedNumber + roundNumber(Number($("#excedentesTopados").val()),2);
				$("#totalMenos").val(moneyMaskDT(roundedNumber,2));
				if(trs != ''){
					tabla.html(trs);
				}
			}
			
		});
	}
	
	function jsSumaAdicional(obj) {
		
		$("#labelimpAdicional").html('');
		if(obj == ''){
			obj = 0;
		}
		var suma = parseFloat(fijoMenos) + parseFloat(obj) +  Number($("#excedentesTopados").val());
		var roundedNumber = roundNumber(suma,2);
		$("#totalMenos").val(roundedNumber);
		jsCalculaAutoDet()
	}
	
	function jsCalculaAutoDet(){
		
		$("#labelimpInmediatoA").html('');
		var diez = $("#impMayor").val();
		
		diez = diez.replace(/[\,]+/gi,"");
		
		if(diez == ''){
			diez = 0;
		}
		var once = $("#impInmediatoA").val();
		once = once.replace(/[\,]+/gi,"");
		if(once == ''){
			once = 0;
		}
		var doce = $("#totalMenos").val();
		doce = doce.replace(/[\,]+/gi,"");
		if(doce == ''){
			doce = 0;
		}
		// 13 = 10 + 11 - 12
		
		var trece = (parseFloat(diez) + parseFloat(once)) - parseFloat(doce);
		var roundedNumber = roundNumber(trece,2);
		
		 $("#autoDet").val(moneyMaskDT(roundedNumber,2));
		 jsSumaIgualA();
	}
	
	function jsSumaImpConceptos(obj) {
		
		var impConcepto = $("#impConceptos").val();
		if(impConcepto == ''){
			impConcepto = 0;
		}
		var suma = parseFloat(impConcepto) + parseFloat(obj);
		var roundedNumber = roundNumber(suma,2);
		$("#impConceptos").val(roundedNumber);
	}
	
	function roundNumber(num, dec) {
		var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
	}
	
	function jsllenaCabecera(){
		
		
		var anexo = $("#selectRegPatron").val();
		var ejercicio = $("#selectEjercicio option:selected").text();	
		var variable = '{' +
					   '"cveEjercicio":"'+ejercicio+'",'+
					   '"cveAnexosolcorrpat":"'+anexo+'"}';					
		var variableJson = jQuery.parseJSON(variable);
		$.postJSON("detBaseCotOmitida/llenaCabecera.do",variableJson,function(data) { 
			if(data != null){
				$("#labelMayorRazonSocial").html('<label>' + data.txRazonSocial + '</label>');
				$("#labelMayorFolio").html('<label >' + data.solicitudCorreccion.nuFolio + '</label>');
				$("#labelMayorRegistroP").html('<label >' + data.patron.registroPatronal + '</label>');
				$("#labelMayorEjercicio").html('<label > ' + data.crcEjercicio.cveEjercicio  + ' </label>');
			}			
		});
		
		
	}
	
	function jsllenaOcho(){
		
		
		var anexo = $("#selectRegPatron").val();
		var ejercicio = $("#selectEjercicio option:selected").text();	
		var variable = '{' +
					   '"cveEjercicio":"'+ejercicio+'",'+
					   '"cveAnexosolcorrpat":"'+anexo+'"}';					
		var variableJson = jQuery.parseJSON(variable);
		$.postJSON("detBaseCotOmitida/calculaBaloAux.do",variableJson,function(data) { 
			if(data != null){
				$("#impBalanzaComp").val(moneyMaskDT(data.imBalanzaOAux,2));
				$("#tpSeleccion").val(data.tpSeleccionBalanzaOAux);
				
				impBalanzaAux = data.imBalanzaOAux;
				if(impBalanzaAux == '' || impBalanzaAux == null){
					impBalanzaAux = 0;
				}
				$("#impMayor").val(moneyMaskDT(impBalanzaAux,2));
			}			
		});
	}
	
	function jsSumaBalanzaComp(valor){
		valor=valor+"";
		valor=valor.replace(",","");
		$("#labelimpISR").html('');
		
		
		if(valor == ''){
			valor = 0;
		}
		
		if(impBalanzaAux !=null && impBalanzaAux  != undefined)
			impBalanzaAux = impBalanzaAux.replace(/[\,]+/gi,"");
		
		/*if(valor !=null && valor  != undefined)
			valor = valor.replace(/[\,]+/gi,"");
			*/
		if(parseFloat(impBalanzaAux)>parseFloat(valor)){
			valor = impBalanzaAux;
		}
		
		$("#impMayor").val(moneyMaskDT(roundNumber(valor,2)),2);
		
	}
	
	function jsCalculaExcedente(){
	 	var anexo = $("#selectRegPatron").val();
	 	var folioCorreccion = $("#folioCorreccionMain").val();
		var ejercicio = $("#selectEjercicio option:selected").text();
	
		var variable = '{' +
					   '"cveEjercicio":"'+ejercicio+'",'+
					   '"folioCorreccion":"'+folioCorreccion+'",'+
					   '"cveAnexoSolCorrPat":"'+anexo+'"}';					
		var variableJson = jQuery.parseJSON(variable);
		$.postJSON("detBaseCotOmitida/calculaExcedente.do",variableJson,function(data) { 
			if(data != null){
				$("#excedentesTopados").val(moneyMaskDT(data.excedenteTopado,2));
			}			
		});
		
	}

	
	function jsCalculaCatorce(){
		
		 	var anexo = $("#selectRegPatron").val();
			var ejercicio = $("#selectEjercicio option:selected").text();	
			var variable = '{' +
						   '"cveEjercicio":"'+ejercicio+'",'+
						   '"cveAnexoSolCorrPat":"'+anexo+'"}';					
			var variableJson = jQuery.parseJSON(variable);
			$.postJSON("detBaseCotOmitida/calculaMenos.do",variableJson,function(data) { 
				if(data != null){
					$("#cotPagada").val(moneyMaskDT(data.sumaImpTotGuadPrest,2));
				}			
			});
	}
	
	function jsBorrarTRPersepcion(){
		var val = 0;
		
		
		

		 $("#tablaMenos tr td a").click(function( event){
			val = "";
			var longitud = $("#tablaMenos tr").length;
			 event.preventDefault();
			 val = $(this).parent("td").find("input").val();
			 if(longitud >= 1){
					
				$(this).parent("td").parent("tr").remove(); 
			 }
			 
			 jsactualizaTotalPercepciones();
			 jsCalculaAutoDet();
		});
		 
		 jsCalculaAutoDet();
		
	}
	
	function jsactualizaTotalPercepciones(){
		 var sumaConcepotos = 0;
		 var impAdicional = $("#impAdicional").val();
		 impAdicional = impAdicional.replace(/[\,]+/gi,"");
			if(impAdicional == ''){
				impAdicional = 0;
			}
		
		 var longitud = $("#tablaMenos tr").length;
		 
			 $("#tablaMenos tr").each(function(){
				 if(longitud >= 1){
					 var val = $(this).find("input").val();
					 if(val == ''){
						 val = 0;
					 }
					 
					val =val.replace(/[\,]+/gi,"");
					val= Number(val);
					 
					 sumaConcepotos = parseFloat(sumaConcepotos) +  parseFloat(val);
				 }
				 
			 });
		 
		var auxExcedentes = $("#excedentesTopados").val();
		auxExcedentes = auxExcedentes.replace(/[\,]+/gi,"");
		
	    sumaConcepotos = sumaConcepotos + Number(auxExcedentes);	 
		var roundedNumber = roundNumber(sumaConcepotos,2);
		
		roundedNumber = parseFloat(roundedNumber) + parseFloat(impAdicional);
		
		$("#totalMenos").val(moneyMaskDT(roundedNumber,2));
	}
	
	function jsBorrarTRConceptos(){
		
		var resp = true;
		var id;
		var val = 0;
		var select = document.getElementById("selectConceptos");
		
		 $("#tableConceptos tr td a").click(function(event){
			 event.preventDefault();
			 val = $(this).parent("td").find("input").val();
			 id = $(this).attr("id");
			 // Llenamos el select
			 for(var i = 0; i < select.options.length; i ++){
				 if(select.options[i].value == obj[id].cvePercepcion){
					 resp = false;					
				 }
			 }
			 if(resp){
				 select.add(new Option(obj[id].txRemuneracion, obj[id].cvePercepcion));
				
			 }			 	
			//Borramos el tr
			 $(this).parent("td").parent("tr").remove();
			 jsactualizaTotalConceptos();
		});
	}
	
	function jsactualizaTotalConceptos(){
		 var sumaConcepotos = 0;
		 var longitud = $("#tableConceptos tr").length;
		 if(longitud >= 1){
			 $("#tableConceptos tr").each(function(){
				 var val = $(this).find("input").val();
				 if(val == ''){
					 val = 0;
				 }
				 val = val.replace(/[\,]+/gi,"");
				 sumaConcepotos = parseFloat(sumaConcepotos) +  parseFloat(val);
				 
			 });
		 }
		var roundedNumber = roundNumber(sumaConcepotos,2);
		
		$("#impConceptos").val(moneyMaskDT(roundedNumber,2));
	}
	
	function jsGuardaDetBaseCotOm(){
		
		var idDetBaseCotOm;
		var anexo        = $("#selectRegPatron").val();
		var ejercicio    = $("#selectEjercicio option:selected").text();	
		var impBalanza   = $("#impBalanzaComp").val();
		var impISR       = $("#impISR").val();
		var impInmediato = $("#impInmediatoA").val();
		var impAdicional = $("#impAdicional").val();	
		
		
		impBalanza = impBalanza.replace(/[\,]+/gi,"");
		impISR = impISR.replace(/[\,]+/gi,"");
		impInmediato = impInmediato.replace(/[\,]+/gi,"");
		impAdicional = impAdicional.replace(/[\,]+/gi,"");
		
		var variable = '{' +
					   '"cveAnexoSolCorrPat":"'+anexo+'",'+
					   '"cveEjercicio":"'+ejercicio+'",'+
					   '"impSueldoBalanzaComp":"'+impBalanza+'",'+
					   '"impSueldoDelAnualISR":"'+impISR+'",'+
					   '"impVarMasSextoBim":"'+impInmediato+'",'+
					   '"impVarMenosSextoBim":"'+impAdicional+'"}';
		var variableJson = jQuery.parseJSON(variable);
		$.postJSON("detBaseCotOmitida/guardaDetBaseCotOm.do",variableJson,function(data) { 
			if(data != null){
				
				idDetBaseCotOm = data.cveDetBaseCotOmit;
				jsGuardaDetBaseCotOmDet1(idDetBaseCotOm);
				jsGuardaDetBaseCotOmDet2(idDetBaseCotOm);
				alert('El registro se guardo exitosamente');
				
			}
		});
	}
	
	function jsGuardaDetBaseCotOmDet1(idDetBaseCotOm){
		
		$("#tablaMenos tr").each(function(){
				
				 var val = $(this).find("td").find("input").val();
				 var id = $(this).find("td").find("a").attr("id");
				 var cvePercepcion = obj[id].cvePercepcion;
				 var concepto = '1';
				 
				 val = val.replace(/[\,]+/gi,"");
				 
				 var variable = '{' +
				   '"crtDetBaseCotOmitida":"'+idDetBaseCotOm+'",'+
				   '"crcPercepciones":"'+cvePercepcion+'",'+
				   '"imRemuneracion":"'+val+'",'+
				   '"idConceptoOmitido":"'+concepto+'"}';	
				 var variableJson = jQuery.parseJSON(variable);
				$.postJSON("detBaseCotOmitida/guardaDetBaseCotOmDet.do",variableJson,function(data) { 
					
					$(this).dialog("close");
			});
			 
		});
		

	}
	
	function jsGuardaDetBaseCotOmDet2(idDetBaseCotOm){
		
		$("#tableConceptos tr").each(function(){
			 var val = $(this).find("td").find("input").val();
			 var id = $(this).find("td").find("a").attr("id");
			 var cvePercepcion = obj[id].cvePercepcion;
			 var concepto = '2';
			 
			 val = val.replace(/[\,]+/gi,"");
			 
			 var variable = '{' +
			   '"crtDetBaseCotOmitida":"'+idDetBaseCotOm+'",'+
			   '"crcPercepciones":"'+cvePercepcion+'",'+
			   '"imRemuneracion":"'+val+'",'+
			   '"idConceptoOmitido":"'+concepto+'"}';	
			 var variableJson = jQuery.parseJSON(variable);
			$.postJSON("detBaseCotOmitida/guardaDetBaseCotOmDet.do",variableJson,function(data) { 
				$(this).dialog("close");
			});
		 
		});
	}
	
	function jsValidaDetBaseCotOmitida(){
		
		var resp = true;
		var anexo = $("#selectRegPatron").val();
		var ejercicio = $("#selectEjercicio option:selected").text();	
		var variable = '{' +
					   '"cveEjercicio":"'+ejercicio+'",'+
					   '"cveAnexoSolCorrPat":"'+anexo+'"}';					
		var variableJson = jQuery.parseJSON(variable);
		$.postJSON("detBaseCotOmitida/validaDetBaseCotOm.do",variableJson,function(data) { 
			if(data != null && !data.isCedulaIVacia){
				alert('Ya existe una solicitud asociada a el ejercicio : ' + ejercicio);
				resp = false;
			}else if(data != null && data.isCedulaIVacia){
				alert('No se ha cargado la c\u00e9dula I correspondiente');
				resp = false;
			}			
		});
		
		return resp;
		
	}
	
	function jsSumaIgualA(){
		
		var autoDet = $("#autoDet").val();
		autoDet = autoDet.replace(/[\,]+/gi,"");
		if(autoDet == ''){
			autoDet = 0;
		}
		var cotPagada = $("#cotPagada").val();
		cotPagada = cotPagada.replace(/[\,]+/gi,"");
		
		if(cotPagada == ''){
			cotPagada = 0;
		}
		var suma = parseFloat(autoDet) - parseFloat(cotPagada);
		var roundedNumber = roundNumber(suma,2);
		$("#cotOmitida").val(moneyMaskDT(roundedNumber,2));		
	}
	
	
	function jsLimpiarForma(){
		
		$("#impISR").val('');
		$("#impInmediatoA").val('');
		$("#impBalanzaComp").val('');
		$("#impMayor").val('');
		$("#impAdicional").val('');
		$("#totalMenos").val('');
		$("#autoDet").val('');
		$("#cotPagada").val('');
		$("#cotOmitida").val('');
		$("#impConceptos").val('');
		
		$("#labelimpISR").html('');
		$("#labelimpInmediatoA").html('');
		$("#labelimpAdicional").html('');
		
		
	}
	
	function jsValidaGuardar(){
		
		 var salida = true;
		 
		if($("#impISR").val() == ''){
			salida = false;
		}
		if($("#impInmediatoA").val() == ''){
			salida = false;
		}
		if($("#impAdicional").val() == ''){
			salida = false;
		}
		
		return salida;
	}
	
	function jsvalidarNumeros(e) { 
		
	    tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true;
	    patron = /[0123456789.]/;
	    te = String.fromCharCode(tecla);
	    
	    return patron.test(te);
	} 
	
	function jsValidaAgregarMain(){
		
		 var salida = true;
		 
		 $("#labelFolioMain").html('');
		 $("#labelRegPatronalMain").html('');
		 $("#labelEjercicioMain").html('');
		
		 
		 if($.trim($('#folioCorreccionMain').val()) == ""){
			 salida = false;
		 }
		 
		if($("#selectRegPatron option:selected").text() == 'Seleccione...'){
			salida = false;
		}
		if($("#selectEjercicio option:selected").text() == 'Seleccione...'	){
			salida = false;
		}
		
		return salida;
	}
	
	
	
	
	
	
	
	
	
	
	