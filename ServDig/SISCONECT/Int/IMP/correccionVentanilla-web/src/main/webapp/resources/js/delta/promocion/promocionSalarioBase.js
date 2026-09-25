var idDisponiblesSelector 	= "#dtDisponiblesSelector";
var idDgRegistroPromocionSB = "#dgRegistroPromocionSB";
var patron = "";
var oDtDisponiblesSelector;
var oDgRegistroPromocionSB;
var validaDomicilioGeo;
var jsRegPatronValidarSBC;
var jsRazonSocialValidarSBC;
var jsRegistroPatronal;
var hlr = 0;   

var Selector = function(cveSelector,registroPatronal,patron){  
    this.cveSelector = '';  
    this.registroPatronal = '';
    this.patron =0;
}  

var Deteccion = function(domCalle, numCodigopostal, numNroext, numNroint, refColonia){
	this.domCalle = '';
	this.numCodigopostal = '';
	this.numNroext = '';
	this.numNroint = '';
	this.refColonia = '';
}

var selector = new Selector('','',0);
var deteccion = new Deteccion('','','','','');

$(document).ready(function() {
	
	$( "#fechaNotificacion, #fechaAtencion" ).datepicker( { dateFormat: 'dd-mm-yy' });
			
	jsLlenaOrigen();

//	$("#fecOficio").datepicker( { 
//		dateFormat: 'dd/mm/yy'
//		maxDate:getFechaServidor(),
//		minDate:getFechaServidorMenos45Dias()
//	});

		
	
	oDtDisponiblesSelector = $(idDisponiblesSelector).dataTable({
		 bJQueryUI : true,
		 bFilter : false,
		 bInfo:true,
		 bSort: false,			 
		 "bPaginate": true,
		 "bAutoWidth" : false,
		 "bServerSide" :	true,		
		 "iDeferLoading": 0,
		 "aoColumns" : [ {
			 fnRender :function(oObj){
				 var retVal = '<input type="radio" value="' +
				 oObj.aData['cveSelector'] +'" id="radioTable" class="radioDeteccion" name="radio" onclick="asignaRPxValidarSBC(&quot;' + oObj.aData['nuRegistroPatronal'] +'&quot;)"/> ';
				 return retVal;
			 }, 
			 aTargets: [0]
		 }, {
			 "sTitle" : "Registro Patronal",
			 "mDataProp" : "nuRegistroPatronal",
			 "sClass": "dtCenterClassColumn"
		 }	
		 ],"bProcessing" : true,
		 "sAjaxSource" : 'promocionSB/paginar.do',
		 "fnServerData" : function(sSource, aoData, fnCallback) {	
			 aoData.push({
					"name" : "sSearch",
					"value" : $('#selectCriterios').val()
				});

			 var wrapper = new Object();
			 wrapper.aoData = aoData;								

			 var oForm = $("#promocionSBForm").toObject({mode:'first'});
			 wrapper.oForm = oForm;

			 $.postJSON(sSource, wrapper, function(data) {										
				 fnCallback(data);					
			 }).error(function(datas){ 
					validarSesionExpirada(datas);				 
			 });
		 }
	 });
	
	oDgRegistroPromocionSB = $(idDgRegistroPromocionSB).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,		 
		open:function(event, ui)
        {			
			 var idCriterioSeleccion = $('#selectCriterios').val();
			 var descripcion = $("#selectCriterios option:selected").text();
			 $("#descCriterioseleccion").html('<label> ' + descripcion + ' </label>'); 
			 $("#domCalle").val("");
			 $("#refColonia").val("");
			 $("#numNroint").val("");
			 $("#numNroext").val("");
			 $("#numCodigopostal").val("");
			 // Cambio 21/03/2012  Enrique Duran
			 $("#regPatronal").html('<label> ' + jsRegPatronValidarSBC + ' </label>'); 
			 $("#nomRazonSocial").html('<label> ' + jsRazonSocialValidarSBC + ' </label>'); 
			 $("#regPatronalSalarioBase").html(jsRegPatronValidarSBC);
        },
		buttons: {
			"Promover": function() {

				var resp = jsValidaRegistrar();
				
				if(resp == true){
					if(validaDomicilioGeo.form()){
						jsagregaPromocionSB();
					}
				}
			}, 
	        "Salir": function() {
	        	limpiarFormulario("#formRegistroPromocionSB");	
	        	oDgRegistroPromocionSB.dialog("close");																		
			} 
		}
	});
	
	

	validaDomicilioGeo = $("#promocionCargaModel").validate({
		 rules: {			 
			 domCalle: {
				 required: true
			 },
			 refColonia: {
				 required: true
			 },			 
			 numNroext: {
				 required: true
			 },
			 numCodigopostal: {
				 required: true
			 },
			 fechaPromocionID: {
				 required: true
			 },
			 numeroOficioID: {
				 required: true
			 }
		 }
	});
	
	
	$("#fecOficio").datepicker( { 
		dateFormat: 'dd/mm/yy',
		maxDate:getFechaServidor(),
		minDate:getFechaServidorMenos45Dias()
	});


});


function registroEventoClick()
{
   $('#radioTable',this).attr("checked", "checked");
   ocultaDiv();
   selector.patron = $("td:eq(1)", this).find("input").val();
   patron = selector.patron;
   selector.registroPatronal  =  $("td:eq(2)", this).text();
   selector.cveSelector = $('#:checked').val();
   
   
}  

function ocultaDiv(){
	$("#btnVisualizar").show("fast");
}


function mostrar(){
	
	var id = $('#:checked').val();
	if(id!=undefined){
		var sDeteccion = '{"cveDeteccion":'+id+'}';	
		var variable = '{' +
	  	'"idOrigen":"0"}';					
		var variableJson = jQuery.parseJSON(variable);
		$.postJSON("promocionSB/quitarDomicilioGeo.do", variableJson,function(data) {
			
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(){
			
		});
		oDgRegistroPromocionSB.dialog('open');	
	}else alert("Seleccione un elemento de la lista");
}

function buscar(){		
	// Buscamos el elemento
	oDtDisponiblesSelector.fnDraw();
}



/**
 * FUncion para el procesamiento de errores cuando la peticion es asincrona
 * @param data
 * @param contenedor
 */
function fnProcesarErrores(data , contenedor){
	
	
	switch(data.status)
	{
	case 403:
		//La sesion expiro
		window.location.reload(true);
	  break;
	case 412:
		//Existen errores de captura
	  fnProcesarErroresDeCaptura(data, contenedor);
	  break;
	case 500:
		//Existen errores de negocio
		fnProcesarErrorNegocio(data, contenedor);
		break;
	  
	}
}





/**
* Funcion para mostrar los errores de captura 
* (campos invalidos o vacios) cuando es una invocacion asincrona
* y response con JSON.
* @param data
* @param contenedor
*/	
function fnProcesarErroresDeCaptura(data, contenedor){
  var objErrores = jQuery.parseJSON(data.responseText);
  var form = $(contenedor);
 
  for( index = 0 ; index < objErrores.erroresCaptura.length ; index ++){
	  var campo = objErrores.erroresCaptura[index].campo;
	  var mensaje = objErrores.erroresCaptura[index].mensaje;
	  var filtroCampoError = contenedor +' #'+campo +'Error';
	  fnShowError(   filtroCampoError , mensaje  );
  }

}



/**
* 
* @param data
* @param contenedor
* @param campo
*/
function fnProcesarErrorNegocio (data, contenedor){

var campo = 'errorNegocio';

var objError = jQuery.parseJSON(data.responseText);
var mensaje = objError.erroresNegocio;
  var filtroCampoError = contenedor +' #'+campo +'Label';
      
  fnShowError(   filtroCampoError , mensaje  );

}




var nClassShow = 'showElement';
var nClassHidden ='hiddenElement';		

/**
* Funcion para mostrar el error
* @param idCampoError
* @param mensajeError
*/
function fnShowError(idCampoError , mensajeError){
$(idCampoError).removeClass(nClassHidden);
$(idCampoError).addClass(nClassShow);
$(idCampoError).text(mensajeError);
}

/**
* Funcion para ocultar los errores 
* de un contenedor.
* span con clase error
* 
* @param contenedor
*/
function fnHideErrores(contenedor){
var filtroErrores = contenedor + ' span.error';
$(filtroErrores).each(function(index) {
    
	$(this).removeClass(nClassShow);
	$(this).addClass(nClassHidden);
	$(this).text();
});
}

function jsValidaFec(fec){
	
	if(jsValidaFecha(fec)){
		 $("#fechaNotificacion").val(fec);
		 $("#labelSBC").html('');
		 
	}else{
		 $("#fechaNotificacion").val("");
		 $("#labelSBC").html('<label style="color: red;">La Fecha de Notificaci&oacute;n  no puede ser mayor al d&iacute;a actual</label>');
	}
}



function jsValidaFecOficio(fec){
	
	if(jsValidaFecha(fec)){
		 $("#fechaOficio").val(fec);
		 $("#labelSBC").html('');
		 
	}else{
		 $("#fechaOficio").val("");
		 $("#labelSBC").html('<label style="color: red;">La Fecha de Oficio de Promoci&oacute;n no puede ser mayor al d&iacute;a actual</label>');
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

	function jsvalidarNumerico(e) { 
		
	    tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true;
	    patron = /[1234567890]/;
	    te = String.fromCharCode(tecla);
	    
	    return patron.test(te);
	} 


	function jsValidaRegistrar(){
		
		$("#labelSBC").html('');
		var resp = true;
		var nuOficiopro = $("#nuOficiopro").val();
		var fechaOficio = $("#fechaOficio").val();
		if(nuOficiopro == ''){
			$("#labelSBC").html('<label style="color: red;">Capturar Campos Obligatorios</label>');
			resp = false;
		}
		if(fechaOficio == ''){
			$("#labelSBC").html('<label style="color: red;">Capturar Campos Obligatorios</label>');
			resp = false;
		}
		
		return resp;
	}
	
	function contar(texto,e) { 
		if (texto.length > 300 ) { 
			tecla = e.keyCode 
			if (tecla != 8) return false 
		} 
		
		return true 
	}  
	
	function jsCortaTextArea(valor){
		if(valor.length > 200){
			valor = valor.substring(0,199);
		}
		
		$("#txObservaciones").val(valor);
	}
	
	function jsvalidarAlfaNumerico(e) { 
		
	    tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true;
	    patron = /[1234567890abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ ]/;
	    te = String.fromCharCode(tecla);
	    
	    return patron.test(te);
	} 
	

	function jsLlenaOrigen(){
		
		var oForm = $("#promocionSBForm").toObject({mode : 'first'});
		$.postJSON("promocionSB/cboOrigen.do", oForm, function(data) {
			
			var myselect=document.getElementById("selectOrigen");
			myselect.options.length = 1;
			
			for(var i = 0 ; i < data.length ; i++){
				myselect.add(new Option(data[i][1], data[i][0]));
			}
			
			
		}).error(function(data){ 
				alert("error" + data);
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
	}
	
	function jsActualizaCriterios(valor){
		
		$("#labelcveSelector").html('');
		var variable = '{' +
		  	'"idOrigen":"'+valor+'"}';					
		var variableJson = jQuery.parseJSON(variable);
		$.postJSON("promocionSB/cboCriterios.do", variableJson, function(data) {
			
			var myselect=document.getElementById("selectCriterios");
			myselect.options.length = 1;
			
			for(var i = 0 ; i < data.length ; i++){
				myselect.add(new Option(data[i][3], data[i][0]));
			}
			
			
		}).error(function(data){ 
				alert("error" + data);
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
	}
	
	function jsLimpiarEtiqueta(){
		$("#labelcveSelector").html('');
	}
	

	function mostarDomGeo(context,fuente){
		
		var url = context + '/catalogo/deteccion/obtenerDomicilioSession.do';
		var func;
		if(fuente == 'registro'){
			func="refrescar('promocionCargaModel', '"+url+"')";
		}if(fuente == 'buscar'){
			func="refrescar('promocionCargaModel', '"+url+"')";
		}if(fuente == 'validar'){
			func="refrescar('promocionCargaModel', '"+url+"')";
		}
		var resultado = openWindowregistraDomicilioInegi(context,'catalogo/deteccion/deteccionDomGeografico.do',null,func);
	}

	function refrescar(form, url){
		$.postJSON(url, deteccion,function(data) {
			$("#domCalle").val(data.domCalle);
			$("#refColonia").val(data.refColonia);
			$("#numNroint").val(data.numNroint);
			$("#numNroext").val(data.numNroext);
			$("#numCodigopostal").val(data.numCodigopostal);
			$("#municipio").val(data.estado);
		}).error(function(data){
			validarSesionExpirada(data);
			mensajeDialog.html(data.responseText);
			mensajeDialog.dialog("open");
		}).complete(function(){
			
		});
	}

	function jsValidaNotificacion(){
		 var fecIni = $("#fechaOficio").val();
		 var fecFinal =  $("#fechaNotificacion").val();
		 if(fecIni != '' && fecFinal != ''){
			 if(jsValidaFechas(fecIni,fecFinal)){
				 $("#fechaNotificacion").val(fecFinal);
				 $("#labelSBC").html('');
			 }else{
				 $("#fechaNotificacion").val('');
				 $("#labelSBC").html('<label class="etiquetaError">La fecha notificaci&oacute;n no puede ser menor a la fecha oficio</label>');
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
	
	function jsValidarMunicipio(){	
		$("#labelSBC").html('');	
				
		var regPatron =  selector.registroPatronal;
		
		var variable = '{' +
		  	'"regPatron":"'+regPatron+'"}';					
		var variableJson = jQuery.parseJSON(variable);
		$.postJSON("promocionSB/validaMunicipio.do", variableJson, function(data) {
			
			if(data == false){
				
				$("#labelSBC").html('<label class="etiquetaError">El registro patronal seleccionado no coincide con la subdelegaci\u00f3n del usuario registrado</label>');
			}else{
				$("#labelSBC").html('');				
				jsagregaPromocionSB();
			}
		}).error(function(data){ 
				alert("error" + data);
			}).complete(function(data){
				
			});		
	}
	
	function jsagregaPromocionSB(){

		var idCriterioSeleccion = $('#selectCriterios').val();
		var nuOficiopro = $("#nuOficiopro").val();
		var fechaOficio = $("#fecOficio").val();
		var observaciones = $("#txObservaciones").val();
		var domicilio = $("#domCalle").val() + ' Numero: ' + $("#numNroext").val() + ' Colonia: ' + $("#refColonia").val() + ' C.P. ' + $("#numCodigopostal").val();
		var variable = '{' +
		   '"domicilio":"'+domicilio+'",'+
		   '"idCriterioSeleccion":"'+idCriterioSeleccion+'",'+
		   '"nuOficiopro":"'+nuOficiopro+'",'+
		   '"fechaOficio":"'+fechaOficio+'",'+
		   '"cveSelector":"'+selector.cveSelector+'",'+
		   '"cveFkPatron":"'+selector.patron+'",'+
		   '"txObservaciones":"'+observaciones+'"}';					
		var variableJson = jQuery.parseJSON(variable);
		
		
		$.postJSON("promocionSB/agregaPromocionSB.do", variableJson, function(data) {
			alert('El registro patronal se ha promovido con el numero de folio: ' + data.nuFoliopromocion);
			oDgRegistroPromocionSB.dialog("close");							
			limpiarFormulario("#formRegistroPromocionSB");	
			oDtDisponiblesSelector.fnDraw();
		}).error(function(data){ 
			alert("error" + data);
		}).complete(function(){
			//Instrucciones para el 'complete'
		});		
	
	}
	
	function asignaRPxValidarSBC(regPatron){
		jsRegPatronValidarSBC=regPatron;
		selector.cveSelector = $('#:checked').val();		
		$("#botonValidarRPSBC").show();
		$("#botonPromoverRPSBC").hide();
	}

	function validarRPatronalSBCProm(){
		bloquear();
		paramRegPatron=limitTextSBC(jsRegPatronValidarSBC, 10);
		$.postJSON(getAppContextParaJS()+"/promocion/seguimiento/generico/validaPatron.do", paramRegPatron, function(data) { 
			if(data == null){
				alert("El registro Patronal es inv\u00e1lido");
			} else if(data.cveRespuestaWS == JSERROR_WS){			
				alert(data.descRespuestaWS);
			} else if(data != null && data.razonSocial != null){
				//habilitar el boton de promover
				$("#botonPromoverRPSBC").show();
				$("#botonValidarRPSBC").hide();
				selector.patron = data.cvePK;
				selector.registroPatronal = data.registroPatronal;
				jsRazonSocialValidarSBC = data.razonSocial;
				jsRegistroPatronal=data.registroPatronal;
			}
			desbloquear();
		}).error(function(data){ 
			desbloquear();
			validarSesionExpirada(data);			
		}).complete(function(){						
			desbloquear();												
		});
	}	
	
	function limitTextSBC(limitField, limitNum) {
	    if (limitField.length > limitNum) {
	        limitField = limitField.substring(0, limitNum);
	    } 
	    return limitField;
	}	