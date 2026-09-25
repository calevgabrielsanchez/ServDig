var idDisponiblesSelector 	= "#dtDisponiblesSelector";
var idDgRegistroPromocionSB = "#dgRegistroPromocionSB";
var patron = "";
var oDtDisponiblesSelector;
var oDgRegistroPromocionSB;
var validaDomicilioGeo;

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
	
	$( "#fechaNotificacion, #fechaAtencion, #fechaOficio" ).datepicker( { dateFormat: 'dd-mm-yy' });
			
	jsLlenaOrigen();
	
	$(function() {		   
		   $('#dtDisponiblesSelector tbody').delegate("tr", "click", registroEventoClick);
		});
	
	$("#fecOficio").datepicker( { 
		dateFormat: 'dd/mm/yy',
		onSelect: function(dateText, inst) { 
	       // $('#fecOficio').datepicker('option', 'minDate', dateText); 
	    }
	});
	
	$.postJSON("promocionSB/obtenerFechaServidor.do", null,function(data) {
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
//		alert(JSON.stringify(data, null, 4));
		$('#fecOficio').datepicker('option', 'maxDate', data.responseText);
		
	});
	
	$.postJSON("promocionSB/obtenerFechaServidorMinima.do", null,function(data) {
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
//		alert(JSON.stringify(data, null, 4));
		$('#fecOficio').datepicker('option', 'minDate', data.responseText);
	});
	
	
	oDtDisponiblesSelector = $(idDisponiblesSelector).dataTable({
		 bJQueryUI : true,
		 bFilter : false,
		 bInfo:true,
		 bSort: false,			 
		 "bPaginate": true,
		 "bAutoWidth" : false,
		 "bServerSide" :	true,		
		 "aoColumns" : [ {
			 fnRender :function(oObj){
				 var retVal = '<input type="radio" value="' +
				 oObj.aData['cveSelector'] +'" id="radioTable" class="radioDeteccion" name="radio" onclick="ocultaDiv()"/> ';
				 return retVal;
			 }, 
			 aTargets: [0]
		 }, {
			
				 fnRender :function(oObj){
					 var retVal = '<input type="hidden" value="' + oObj.aData['patron'] +'" /> ';
					 return retVal;
				 }, 
				 aTargets: [1]
		 }, {
			 "sTitle" : "Registro Patronal",
			 "mDataProp" : "registroPatronal",
			 "sClass": "dtCenterClassColumn"
		 }, {
			 "sTitle" : "Raz&oacute;n Social",
			 "mDataProp" : "razonSocial",
			 "sClass":"dtCenterClassColumn"

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
			
			
			var variable = '{' +
			   '"idCriterioSeleccion":"'+idCriterioSeleccion+'",'+
			   '"cveFkPatron":"'+selector.patron+'",'+
			   '"cveSelector":"'+selector.cveSelector+'"}';	
			var variableJson = jQuery.parseJSON(variable);
			
			  $.postJSON("promocionSB/filtraCriterios.do",variableJson, function(datas) {
					 var descripcion = $("#selectCriterios option:selected").text();
					 $("#descCriterioseleccion").html('<label> ' + descripcion + ' </label>'); 
					 $("#domCalle").val("");
					 $("#refColonia").val("");
					 $("#numNroint").val("");
					 $("#numNroext").val("");
					 $("#numCodigopostal").val("");
					 // Cambio 21/03/2012  Enrique Duran
					 $("#regPatronal").html('<label> ' +datas.regPatron + ' </label>'); 
					 $("#nomRazonSocial").html('<label> ' + datas.razonSocial + ' </label>'); 
					
					//Agrega las opciones al control
				}).error(function(datas){ 
					fnProcesarErrores(datas, sIdDialogEliminarProductos);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
        },
		buttons: {
			"Promover": function() {
				
				var resp = jsValidaRegistrar();
				
				if(resp == true){
					if(validaDomicilioGeo.form()){
						jsValidarMunicipio();
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

});


function registroEventoClick()
{
//   if (hlr){
//      $("td:first", hlr).parent().children().each(function(){$(this).removeClass('registroMarcado');});
//   }   
//   hlr = this;
//   $("td:first", this).parent().children().each(function(){$(this).addClass('registroMarcado');}); 
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

function valildaPromocion(){		
	// Buscamos el elemento
	$("#labelcveSelector").html('');
	var idClaveSelector = $('#:checked').val();
	var sRegistro= '{"criterioSeleccion":'+'"'+idCriterioSeleccion+'"}';
	var crtSelector = jQuery.parseJSON(sRegistro);
	//alert(sCampo);
	$.postJSON("promocionSB/consultar.do", crtSelector, function(data) {
		if(data==null){
			$("#labelcveSelector").html('<label style="color: red;">No se han encontrado Folios de salario base</label>'); }
		else
			oDtDisponiblesSelector.fnDraw();		
	}).error(function(data){ 
		alert("error" + data);
	}).complete(function(){
//		//Instrucciones para el 'complete'
	
	});
}



function buscar(){		
	// Buscamos el elemento
	$("#labelcveSelector").html('');
	var idCriterioSeleccion = $('#selectCriterios').val();
	var sRegistro= '{"criterioSeleccion":'+'"'+idCriterioSeleccion+'"}';
	var crtSelector = jQuery.parseJSON(sRegistro);
	//alert(sCampo);
	$.postJSON("promocionSB/consultar.do", crtSelector, function(data) {
		if(data == null || data.length == 0){	
			$("#labelcveSelector").html('<label style="color: red;">No se han encontrado Folios de salario base</label>');
			oDtDisponiblesSelector.fnDraw();
			}
		else
			oDtDisponiblesSelector.fnDraw();		
	}).error(function(data){ 
		alert("error" + data);
	}).complete(function(){
//		//Instrucciones para el 'complete'	
	});
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
		 $("#labelSBC").html('<label style="color: red;">La Fecha de Notificacion  no puede ser mayor al dia actual</label>');
	}
}



function jsValidaFecOficio(fec){
	
	if(jsValidaFecha(fec)){
		 $("#fechaOficio").val(fec);
		 $("#labelSBC").html('');
		 
	}else{
		 $("#fechaOficio").val("");
		 $("#labelSBC").html('<label style="color: red;">La Fecha de Oficio de Promocion no puede ser mayor al dia actual</label>');
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

	function jsvalidarAlfaNumerico(e) { 
		
	    tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true;
	    patron = /[1234567890abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ"$%]/;
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
		var resultado = openWindowregistraDomicilioInegi(context,'catalogo/deteccion/deteccionDomGeografico.do');
		var url = context + '/catalogo/deteccion/obtenerDomicilioSession.do'
		if(fuente == 'registro'){
			refrescar('promocionCargaModel', url);
		}if(fuente == 'buscar'){
			refrescar('promocionCargaModel', url);
		}if(fuente == 'validar'){
			refrescar('promocionCargaModel', url);
		}
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
				 $("#labelSBC").html('<label class="etiquetaError">La fecha notificacion no puede ser menor a la fecha oficio</label>');
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
		var municipio =  $("#municipio").val();
		var variable = '{' +
		  	'"municipio":"'+municipio+'"}';					
		var variableJson = jQuery.parseJSON(variable);
		$.postJSON("promocionSB/validaMunicipio.do", variableJson, function(data) {
			
			if(data == false){
				
				$("#labelSBC").html('<label class="etiquetaError">El estado seleccionado no coincide con el estado del usuario registrado</label>');
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
			alert('El registro patronal se a promovido con el numero de folio : ' + data.nuFoliopromocion);
			oDgRegistroPromocionSB.dialog("close");							
			limpiarFormulario("#formRegistroPromocionSB");	
			oDtDisponiblesSelector.fnDraw();
		}).error(function(data){ 
			alert("error" + data);
		}).complete(function(){
			//Instrucciones para el 'complete'
		});		
	
	}
