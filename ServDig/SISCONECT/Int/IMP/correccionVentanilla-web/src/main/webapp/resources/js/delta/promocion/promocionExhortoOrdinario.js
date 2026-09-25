var objDataTableCriterioSeleccion;
var confirmDialogPromocion;
var validaDomicilioGeo;
var mensajeDialog;
var jsRegPatronValidarOrd;
var jsRazonSocialValidarOrd;

var Selector = function(cveSelector, registroPatronal, descripcion, razonSocial){
    this.cveSelector = '';  
    this.registroPatronal = '';
    this.descripcion = '';
    this.razonSocial = '';
    
}

var Deteccion = function(domCalle, numCodigopostal, numNroext, numNroint, refColonia){
	this.domCalle = '';
	this.numCodigopostal = '';
	this.numNroext = '';
	this.numNroint = '';
	this.refColonia = '';
}

var Promocion = function(cveSelector, fechaNotificacion, numeroOficio, fechaPromocion, observaciones, regPatronal){
	this.cveSelector = 0;
	this.fechaNotificacion = '';
	this.numeroOficio = '';
	this.fechaPromocion = '';
	this.observaciones = '';
	this.regPatronal = '';
}

var selector = new Selector('','','','');
var deteccion = new Deteccion('','','','','');
var promocion = new Promocion(0,'','','','','');



$(document).ready(function() {
	
	$("#fechaPromocionID").datepicker( { 
		dateFormat: 'dd/mm/yy',
		onSelect: function(dateText, inst) { 
	        $('#fechaNotificacion').datepicker('option', 'minDate', dateText); 
	    }
	});
	
	$("#fechaNotificacion").datepicker( { 
		dateFormat: 'dd/mm/yy',
		onSelect: function(dateText, inst) {
			//No utilizamos esta parte del date picker ya que ocasiona que el componente haga cosas extrannas
//	        $('#fechaPromocionID').datepicker('option', 'maxDate', dateText); 
	    } 
	});

		$('#fechaPromocionID').datepicker('option', 'maxDate',getFechaServidor());
		$('#fechaNotificacion').datepicker('option', 'maxDate',getFechaServidor());
		$('#fechaPromocionID').datepicker('option', 'minDate',getFechaServidorMenos45Dias());
		$('#fechaNotificacion').datepicker('option', 'minDate',getFechaServidorMenos45Dias());

	
	$("#criterioSeleccionadoDivID").hide();
	
	objDataTableCriterioSeleccion = $('#tableCriteriosSeleccion').dataTable({
		bJQueryUI : true,
		bFilter : false,
		bInfo:true,
		bSort: false,
		"bPaginate": true,
		"bAutoWidth" : true,
		"bServerSide" : true,
		"iDeferLoading": 0,
		"sAjaxSource" : "ordinario/buscarCriteriosSeleccion.do",
		"aoColumns" : [ {
							"sTitle" : "Promover",
							fnRender :function(oObj){
								var retVal = '<input type="radio" value="' + oObj.aData['cveSelector'] +'" id="radioTable" class="radioDeteccion" name="radio" onclick="asignaRPxValidarOrd(&quot;' + oObj.aData['nuRegistroPatronal'] +'&quot;);"/> ';
								return retVal;
							 }, 
							 aTargets: [0]
						}, {
							"sTitle" : "Registro Patronal",
							"mDataProp" : "nuRegistroPatronal",
							"sClass": "dtCenterClassColumn"
						}
					],
		"fnServerData" : function(sSource, aoData, fnCallback) {
			var wrapper = new Object();
			wrapper.aoData = aoData;								
	
			var oForm = $("#promocionCargaModel").toObject({mode:'first'});
			wrapper.oForm = oForm;
			bloquear();
			$.postJSON(sSource, wrapper, function(data) {										
				fnCallback(data);					
			}).error(function(data){ 
				validarSesionExpirada(data);				 
				mensajeDialog.html(data.responseText);
				mensajeDialog.dialog("open");
			}).complete(function(){
				desbloquear(); 
			});	
		}
	});
	
	confirmDialogPromocion = $("#dialog-confirm-promocion").dialog({
		autoOpen: false,
		resizable: false,
		height:160,
		width: 500,
		modal: true,
		buttons: {
			"S\u00cd deseo promocionar este registro patronal": function() {
				$( this ).dialog( "close" );
				
				$("#wrapperDataTableCriteriosSeleccion").hide();
				$("#cargaCedula").hide();
				$("#criterioSeleccionadoDivID").show();
				
				$("#tituloArriba").html('Generar Nuevo Folio  Promoci\u00F3n Ordinario');
				$("#descCriterioseleccion").html('<b>' + selector.descripcion + '</b>');
				
				var variable = '{"cveSelector":"'+ selector.cveSelector +'"}';		
				var variableJson = jQuery.parseJSON(variable);
				selector.razonSocial = jsRazonSocialValidarOrd;
				selector.registroPatronal = jsRegPatronValidarOrd;
				$("#resgistroPatronalSeleccionadoID").html('<b>' + selector.registroPatronal + '</b>');
				$("#razonSocialSeleccionadoID").html('<b>' + selector.razonSocial + '</b>');
				//alert(selector.cveSelector);
				$.postJSON("ordinario/quitarDomicilioGeo.do", promocion,function(data) {
					
				}).error(function(data){
					validarSesionExpirada(data);
				}).complete(function(){
					
				});
				// Se oculta el div con los botones de boton de promover y validar RP
				$("#botonPromoverRPOrdinario").hide();
				$("#botonesPromoverOrdinario").hide();
			},
			"NO deseo promocionar este registro patronal": function() {
				$( this ).dialog( "close" );
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
	
	$("a#btnBuscarCriterios").click(function(event){
		if(jsValidaBuscar() == true){
			objDataTableCriterioSeleccion.fnDraw();
		}		
	});
	
	$("a#btnPromocionar").click(function(event){
		
		if($("#regPat").val()!='' && $("#razonSocial").val()==''){
			alert("Favor de validar el registro patronal");
		}else if(validaDomicilioGeo.form()){
			promocion.cveSelector = selector.cveSelector;
			promocion.fechaNotificacion = $("#fechaNotificacion").val();
			promocion.fechaPromocion = $("#fechaPromocionID").val();
			promocion.observaciones = $("#observacionesId").val();
			promocion.numeroOficio = $("#numeroOficioID").val();
			promocion.regPatronal = selector.registroPatronal;
			promocion.idCriterioSeleccion = $("#idCriterio").val();
			bloquear();
			$.postJSON("ordinario/promocionar.do", promocion,function(data) {
//				$("#observacionesId").val(JSON.stringify(data, null, 4));
				mensajeDialog.html('La promocion ha sido promovida con num folio:' + data.nuFoliopromocion);
				mensajeDialog.dialog("open");
			}).error(function(data){
				validarSesionExpirada(data);
				mensajeDialog.html(data.responseText);	
				mensajeDialog.dialog("open");
			}).complete(function(){
				desbloquear();
				limpiaFormularioInicial();
				mostrarCriteriosSeleccion();
				objDataTableCriterioSeleccion.fnDraw();
				$("#botonesPromoverOrdinario").show();
			});
		}
	});
	
	mensajeDialog = $("#dialog-mensaje").dialog({
		autoOpen: false,
		modal: true,
		resizable: true,
		width: 500,
		buttons: {
			Ok: function() {
				$(this).dialog("close");
			}
		}
	});
	
});


function formatDate(value){
	return value.getDate() + "/" + value.getMonth()+1 + "/" + value.getYear();
}


function limpiaFormularioInicial(){
	$("form#promocionCargaModel #fechaNotificacion").val('');
	$("form#promocionCargaModel #fechaPromocionID").val('');
	$("form#promocionCargaModel #numeroRegistroPatronalInputID").val('');
	$("form#promocionCargaModel #observacionesId").val('');
	$("form#promocionCargaModel #numeroOficioID").val('');
	$("form#promocionCargaModel #domCalle").val('');
	
	$("form#promocionCargaModel #refColonia").val('');
	$("form#promocionCargaModel #numNroint").val('');
	$("form#promocionCargaModel #numNroext").val('');
	$("form#promocionCargaModel #numCodigopostal").val('');
}

function muestraConfirmacionPromocion(){
	
	var cveSelector = $('#:checked').val();
	var txt = '<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>\u00BF Est\u00e1 seguro que desea promocionar el registro patronal seleccionado ?</p>'
	$("#dialog-confirm-promocion").html(txt);
	selector.cveSelector = cveSelector;  
	selector.descripcion = $('#idCriterio :selected').text(); 
	confirmDialogPromocion.dialog("open");
}

function mostrarCriteriosSeleccion(){
	$("#wrapperDataTableCriteriosSeleccion").show();
	$("#cargaCedula").show();
	$("#criterioSeleccionadoDivID").hide();
}

function mostarDomGeo(context,fuente){
	
	var url = context + '/catalogo/deteccion/obtenerDomicilioSession.do'
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
		$("form#"+form+" #domCalle").val(data.domCalle);
		$("form#"+form+" #refColonia").val(data.refColonia);
//		alert(data.domicilioInegi.numintnum);
		$("form#"+form+" #numNroint").val(data.numNroint);
		$("form#"+form+" #numNroext").val(data.numNroext);
		$("form#"+form+" #numCodigopostal").val(data.numCodigopostal);
	}).error(function(data){
		validarSesionExpirada(data);
		mensajeDialog.html(data.responseText);
		mensajeDialog.dialog("open");
	}).complete(function(){
		
	});
}

function jsValidaBuscar(){
	
	$("#labelIdOrigen").html('');
	$("#labelCriterio").html('');
	
	var resp = false;
	if($("#idOrigen").val() == '-1'){
		$("#labelIdOrigen").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("#idCriterio").val() == '-1'){
		$("#labelCriterio").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	if($("#idOrigen").val() != '-1' && $("#idCriterio").val() != '-1'){
		resp = true;
	}
	
	return resp;
}

function asignaRPxValidarOrd(regPatron){
	jsRegPatronValidarOrd=regPatron;	
	$("form#promocionCargaModel #botonValidarRPOrdinario").show();
	$("form#promocionCargaModel #botonPromoverRPOrdinario").hide();
}

function validarRPatronalOrdinarioProm(){
	bloquear();
	paramRegPatron=limitTextOrdinario(jsRegPatronValidarOrd, 10);
	$.postJSON(getAppContextParaJS()+"/promocion/seguimiento/generico/validaPatron.do", paramRegPatron, function(data) { 
		if(data == null){
			alert("El registro Patronal es invalido");
		} else if(data.cveRespuestaWS == JSERROR_WS){			
			alert(data.descRespuestaWS);
		} else if(data != null && data.razonSocial != null){
			//habilitar el boton de promover
			$("#botonPromoverRPOrdinario").show();
			$("#botonValidarRPOrdinario").hide();
			jsRazonSocialValidarOrd = data.razonSocial;
		}
		desbloquear();
	}).error(function(data){ 
		desbloquear();
		validarSesionExpirada(data);			
	}).complete(function(){						
		desbloquear();												
	});
}

function limitTextOrdinario(limitField, limitNum) {
    if (limitField.length > limitNum) {
        limitField = limitField.substring(0, limitNum);
    } 
    return limitField;
}