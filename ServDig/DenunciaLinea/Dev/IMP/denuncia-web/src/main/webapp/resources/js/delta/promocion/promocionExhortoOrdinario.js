var objDataTableCriterioSeleccion;
var confirmDialogPromocion;
var validaDomicilioGeo;
var mensajeDialog;

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
	
	$.postJSON("ordinario/obtenerFechaServidor.do", null,function(data) {
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
//		alert(JSON.stringify(data, null, 4));
		$('#fechaPromocionID').datepicker('option', 'maxDate', data.responseText);
		$('#fechaNotificacion').datepicker('option', 'maxDate', data.responseText);
	});
	
	$("#criterioSeleccionadoDivID").hide();
	
	$(function() {		   
	   $('#tableCriteriosSeleccion tbody').delegate("tr", "click", muestraConfirmacionPromocion);
	});
	
	objDataTableCriterioSeleccion = $('#tableCriteriosSeleccion').dataTable({
		bJQueryUI : true,
		bFilter : false,
		bInfo:true,
		bSort: false,
		"bPaginate": true,
		"bAutoWidth" : false,
		"bServerSide" : true,
		"sAjaxSource" : "ordinario/buscarCriteriosSeleccion.do",
		"aoColumns" : [ {
							"sTitle" : "Promover",
							fnRender :function(oObj){
								var retVal = '<input type="radio" value="' + oObj.aData['cveSelector'] +'" id="radioTable" class="radioDeteccion" name="radio"/> ';
								return retVal;
							 }, 
							 aTargets: [0]
						}, {
							"sTitle" : "Registro Patronal",
							"mDataProp" : "registroPatronal",
							"sClass": "dtCenterClassColumn"
						}, {
							  "sTitle" : "Nombre o Raz\u00F3n Social",
							  "mDataProp" : "razonSocial",
							  "Class": "dtCenterClassColumn"
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
			"Si deseo promocionar este registro patronal": function() {
				$( this ).dialog( "close" );
				
				$("#wrapperDataTableCriteriosSeleccion").hide();
				$("#cargaCedula").hide();
				$("#criterioSeleccionadoDivID").show();
				
				$("#tituloArriba").html('Generar nuevo folio  Promoci\u00F3n Ordinario');
				$("#resgistroPatronalSeleccionadoID").html('<b>' + selector.registroPatronal + '</b>');
				$("#descCriterioseleccion").html('<b>' + selector.descripcion + '</b>');
				$("#razonSocialSeleccionadoID").html('<b>' + selector.razonSocial + '</b>');
				
				
				//alert(selector.cveSelector);
				$.postJSON("ordinario/quitarDomicilioGeo.do", promocion,function(data) {
					
				}).error(function(data){
					validarSesionExpirada(data);
				}).complete(function(){
					
				});
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
		objDataTableCriterioSeleccion.fnDraw();
	});
	
	$("a#btnPromocionar").click(function(event){
		if(validaDomicilioGeo.form()){
			promocion.cveSelector = selector.cveSelector;
			promocion.fechaNotificacion = $("#fechaNotificacion").val();
			promocion.fechaPromocion = $("#fechaPromocionID").val();
			promocion.observaciones = $("#observacionesId").val();
			promocion.numeroOficio = $("#numeroOficioID").val();
			promocion.regPatronal = selector.registroPatronal;
			bloquear();
			$.postJSON("ordinario/promocionar.do", promocion,function(data) {
//				$("#observacionesId").val(JSON.stringify(data, null, 4));
				mensajeDialog.html('La promocion ha sido efectuada num folio:' + data.nuFoliopromocion);
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
	$('#radioTable',this).attr("checked", "checked");
	var registroPatronal = $("td:eq(1)", this).text();
	var razon = $("td:eq(2)", this).text();
	var cveSelector = $('#:checked').val();
	var txt = '<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>El registro Patronal: ' + registroPatronal +' sera promovido</p>'
	$("#dialog-confirm-promocion").html(txt);
	selector.cveSelector = cveSelector;  
	selector.registroPatronal = registroPatronal;
	selector.razonSocial = razon;
	selector.descripcion = $('#idCriterio :selected').text(); 
	confirmDialogPromocion.dialog("open");
}

function mostrarCriteriosSeleccion(){
	$("#wrapperDataTableCriteriosSeleccion").show();
	$("#cargaCedula").show();
	$("#criterioSeleccionadoDivID").hide();
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
		$("form#"+form+" #domCalle").val(data.domCalle);
		$("form#"+form+" #refColonia").val(data.refColonia);
//		alert(data.domicilioInegi.numintnum);
		$("form#"+form+" #numNroint").val(data.domicilioInegi.numintnum);
		$("form#"+form+" #numNroext").val(data.numNroext);
		$("form#"+form+" #numCodigopostal").val(data.numCodigopostal);
	}).error(function(data){
		validarSesionExpirada(data);
		mensajeDialog.html(data.responseText);
		mensajeDialog.dialog("open");
	}).complete(function(){
		
	});
}