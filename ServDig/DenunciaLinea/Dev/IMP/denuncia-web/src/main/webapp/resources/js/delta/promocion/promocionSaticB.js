
var idDisponiblesPromocionSaticB 	= "#dtDisponiblesPromocionSaticB";
var idDisponiblesSaticBsinRelaciones = "#dtDisponiblesSaticBsinRelaciones"; 
var idDisponiblesSaticB	= "#dtDisponiblesSaticB";
var idDgPromocion			= "#dgPromocion";
var idPromocionConsultaDet 	= "#dgPromocionConsultaDet";
var idRegistroPromocion		= "#dgPromocionRegistro";
var idDatosSatic			= "#dgPromocionDatosSaticB";
var idPeriodosSatic			= "#dgPeriodosSaticB";
var idPromover              = "#dgPromoverSATICB";

var oDtDisponiblesPromocionSaticB;
var oDtDisponiblesSaticBsinRelaciones;
var oDtDisponiblesSaticB;
var oDgPromocion;
var oDgPromocionConsultaDet;
var oDgRegistroPromocion; 
var oDgDatosSatic;
var oDgPeriodosSatic;
var validaDatos;
var validaForm;
var oDgPromover;

$(document).ready(function()
{
	
	$("form#promoverSATICBForm #fecOficio").datepicker( { 
		dateFormat: 'dd/mm/yy',
		changeMonth: true,
        changeYear: true,
		onSelect: function(dateText, inst) { 
	       // $('#fecOficio').datepicker('option', 'minDate', dateText); 
	    }
	});
	$( "form#promocionSaticbForm #fechaEstimIncio,form#promocionSaticbForm #fechaEstTerm" ).datepicker( { dateFormat: 'dd-mm-yy' });

	
	$.postJSON("promocionSaticB/obtenerFechaServidor.do", null,function(data) {
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
//		alert(JSON.stringify(data, null, 4));
		$('form#promoverSATICBForm #fecOficio').datepicker('option', 'maxDate', data.responseText);
		
	});
	
	$.postJSON("promocionSaticB/obtenerFechaServidorMinima.do", null,function(data) {
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
//		alert(JSON.stringify(data, null, 4));
		$('form#promoverSATICBForm #fecOficio').datepicker('option', 'minDate', data.responseText);
	});
	
	oDtDisponiblesSaticB = $(idDisponiblesSaticB).dataTable({		 
		 bFilter : true,
		 bInfo:true,
		 bSort: true,
		 "bJQueryUI" : true,
		 "bPaginate": true,
		 "bAutoWidth" : true,
		 "bServerSide" :	true,
		 "aoColumns" : [ {
			 fnRender :function(oObj){
				 var retVal = '<input type="radio" value="' +
				 oObj.aData['numRegObra'] + '|' +   oObj.aData['bandera'] +'" id="radioTable" class="radioDeteccion" name="radio" onclick="activaDivPromocion()"/> ';
				 return retVal;
			 }, 
			 aTargets: [0]
		 	}, {
				"sTitle" : "Registro de Obra",
				"mDataProp" : "numRegObra",
				"sClass":"dtCenterClassColumn"
			},{
				"sTitle" : "Registro Patronal",
				"mDataProp" : "regPatron",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Raz&oacute;n Social",
				"mDataProp" : "nomRazonsocial",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Tipo",
				"mDataProp" : "desTipoObra",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Fase",
				"mDataProp" : "desFaseCostruccion",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Superficie",
				"mDataProp" : "canSuperficie",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Costo Total",
				"mDataProp" : "impCostoobra",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Clase",
				"mDataProp" : "tipClaseobra",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "SATIC",
				"mDataProp" : "bandera",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Fecha de Inicio",
				"mDataProp" : "fechaIncial",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Fecha de Termino",
				"mDataProp" : "fechaFinal",
				"sClass": "dtCenterClassColumn"
			}
			 ],"bProcessing" : true,
			 "sAjaxSource" : 'promocionSaticB/paginarSatic.do',
			 "fnServerData" : function(sSource, aoData, fnCallback) {				

			 var wrapper = new Object();
			 wrapper.aoData = aoData;								

			 var oForm = $("#promocionSaticbForm").toObject({mode:'first'});
			 wrapper.oForm = oForm;

			 $.postJSON(sSource, wrapper, function(data) {										
				 fnCallback(data);
				 if(data.aaData!=null)
					 if(data.aaData.length<=0)
						 $('#labelError').html('<label style="color: red;">No se encontraron resultados de su b&uacute;squeda, por favor realice nueva b&uacute;squeda</label>');
					 else{
						 $('#labelError').html('');
					 }
				 
			 }).complete(function(){
				 desbloquear();
			 });
		 }
	 });
	
	oDgRegistroPromocion = $(idRegistroPromocion).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		beforeClose :function(event,ui){		    				    
			limpiarFormulario("#promocionFormRegPromocion");
			$("form#promocionFormRegPromocion #cveNroregobraSatic").prop('readOnly','');
			var crtPromocion = $("#promocionFormRegPromocion").toObject({mode:'first'});
			$.postJSON("promocionSaticB/removerDomicilioSession.do", crtPromocion, function(data) {					
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el complete													
			});				    
		},
		open:function(event, ui)
        {					
			var promocion = $("#promocionFormRegPromocion").serializeObject(true);
			  $.postJSON("promocionSaticB/consultaMotivos.do", promocion, function(datas) {
					 var options = "<option value='' >--Por favor seleccione--</option>";
					 if(datas!=null)
					   for (var i = 0; i < datas.length; i++) {
				         options += "<option value='"+ datas[i].idCriterioseleccion +"'>"+ datas[i].descCriterioseleccion +"</option>";		     
				       }
					 $('select#idCriterioSeleccion').html(options);
					//Agrega las opciones al control
				}).error(function(datas){ 
					validarSesionExpirada(datas);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
        },
		buttons: {
			"Registrar": function() {
				if(validaDatos.form()){
					bloquear();
					var promocion = $("#promocionFormRegPromocion").serializeObject(true);
					$.postJSON("promocionSaticB/agregaPromocion.do", promocion, function(data) {
						alert("La promocion ha sido agregada con el Folio: "+data.nuFoliopromocion);
						oDtDisponiblesSaticB.fnDraw();
						oDgRegistroPromocion.dialog("close");					
						limpiarFormulario("#promocionFormRegPromocion");						
					}).error(function(data){ 
						desbloquear();
						validarSesionExpirada(data);
					}).complete(function(){
						//Instrucciones para el 'complete'
					});					
				}																				
			} 
		}
	});
	
	oDgDatosSatic = $(idDatosSatic).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		beforeClose :function(event,ui){		    				    
			limpiarFormulario("#promocionFormDatosSaticB");
			$('select#periodosfaltantes').html('');
			 $('select#periodosPresentados').html('');
		},
		open:function(event, ui)
        {					
			var deteccion = $("#promocionFormDatosSaticB").serializeObject(true);
			  $.postJSON("promocionSaticB/periodos.do", deteccion, function(datas) {
				  	 var options;
				  	 var options2;
					 if(datas!=null)
					   for (var i = 0; i < datas.length; i++) {
						   if(datas[i].substring(6,7)==0) 
							   options += "<option value='"+ datas[i].substring(0,6) +"'>"+ datas[i].substring(0,6) +"</option>";
						   else
							   options2 += "<option value='"+ datas[i].substring(0,6) +"'>"+ datas[i].substring(0,6) +"</option>";
				       }
					 $('select#periodosfaltantes').html(options);
					 $('select#periodosPresentados').html(options2);
					//Agrega las opciones al control
				}).error(function(datas){ 
					validarSesionExpirada(datas);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
        },
		buttons: {
			"Promover": function(){
				jsLlenaCriteriosPromover();
				$("form#promoverSATICBForm #numRegObra").val($("form#promocionFormDatosSaticB #numRegObra").val());
				$("form#promoverSATICBForm #cveFkPatron").val($("form#promocionFormDatosSaticB #cveFkPatron").val());
				oDgPromover.dialog("open");
				
			},
			"Regresar": function() { 
				$(this).dialog("close"); 
				limpiarFormulario("#dgPromocionDatosSaticB");				
			} 
		}
	});
	
	oDgPeriodosSatic = $(idPeriodosSatic).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 330,
		open:function(event, ui)
        {					
			var deteccion = $("#promocionFormRegPromocion").serializeObject(true);
			  $.postJSON("promocionSaticB/periodos.do", deteccion, function(datas) {					 
					 if(datas!=null)
					   for (var i = 0; i < datas.length; i++) {
				         options += "<option value='"+ datas[i].idCriterioseleccion +"'>"+ datas[i].descCriterioseleccion +"</option>";		     
				       }
					 $('select#periodosfaltantes').html(options);
					//Agrega las opciones al control
				}).error(function(datas){ 
					validarSesionExpirada(datas);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});
        },
		buttons: {		
			"Regresar": function() { 
				$(this).dialog("close"); 								
			} 
		}
	});
	
	oDgPromover = $(idPromover).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		buttons: {
			"Promover": function() {
				if(jsValidarPromover() == true){
					oDgPromover.dialog('close');
					jsPromoverDeteccion();
					oDgPromover.dialog('open');
				}
			}, 
			"Salir"   : function() {
				jsLimpiarFormaPromover();
				$(this).dialog("close"); 
			}
		}
	});
	
	validaDatos = $("#promocionFormRegPromocion").validate({
		 rules: {			 
			 idCriterioSeleccion: {
				 required: true
			 },
			 nuOficiopro: {
				 required: true
			 },
			 cveNroregobraSatic: {
				 required: true				 
			 },
			 fechaOficio: {
				 required: true
			 },
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
			 estado:{
				 required: true
			 },
			 municipio:{
				 required: true
			 }
		 }
	});
	
	validaForm = $('#promocionSaticbForm').validate({
		rules:{
			fechaEstimIncio:{
				 required: true
			},
			fechaEstTerm:{
				 required: true
			}
		}
	});
	
//	$(".tab_content").hide();
//	$("ul.tabs li:first").addClass("active").show();
//	$(".tab_content:first").show();
//
//	$("ul.tabs li").click(function()
//       {
//		$("ul.tabs li").removeClass("active");
//		$(this).addClass("active");
//		$(".tab_content").hide();
//
//		var activeTab = $(this).find("a").attr("href");
//		$(activeTab).fadeIn();
//		return false;
//	});
//		
}); 

function buscar(){	
	if(validaForm.form()){
		$("#promocionSaticBObrasSatic").show();
		$("#divTitulo").show();
		oDtDisponiblesSaticB.fnDraw();
//		oDtDisponiblesSaticBsinRelaciones.fnDraw();
		bloquear();	
	}
}

function validaFecha(form,fec,fec2,leyendafec,leyendafec2){
	fec = "form#"+form+" #"+fec+"";
	fec2 = "form#"+form+" #"+fec2+"";
	if($(fec2).val()!=""){
		if(!validaFechas($(fec2).val(),$(fec).val())){
			alert("La "+leyendafec+" es menor a la "+leyendafec2);
			$(fec2).val("");
		}
	}	
}

function validafechaSistema(form,fec,leyendafec){
	fec = "form#"+form+" #"+fec+"";	
	var fechaSis = new Date();
	var diaS = fechaSis.getDate();
	var mesS = fechaSis.getMonth() + 1;
	var anioS = fechaSis.getFullYear()+"";
	if(diaS<10){
		diaS = "0"+diaS;
	}
	if(mesS<10){
		mesS = "0"+mesS;
	}
	var fec2 = diaS+"-"+mesS+"-"+anioS;
	if(!validaFechas(fec2,$(fec).val())){
		alert("La "+leyendafec+" debe ser menor a la Fecha del sistema");
		$(fec).val("");
	}
}

function activaDivPromocion(){
	$("#buttonPromover").show();
}

function promover(){
	var idDeteccion = $('#:checked').val();
	$('#wrapperDialogPromocionRegistro, #promocionFormRegPromocion, #cveNroregobraSatic').val(idDeteccion);
	$("form#promocionFormRegPromocion #cveNroregobraSatic").attr('readonly','true');
	oDgRegistroPromocion.dialog('open');
}

function mostrar(){
	bloquear();
	var radios = document.getElementsByName("radio");
	for (i=0;i<radios.length;i++)
	 {
		if(radios[i].checked)
		{
	
			var tocken = radios[i].value;
			var array = tocken.split("|"); 
			var idObra = array[0];
			var satic = array[1];
			
			var sObra = '{"numRegObra":'+idObra+'}';
			var obra = jQuery.parseJSON(sObra);

			$.postJSON("promocionSaticB/mostrar.do", obra, function(data) {
				$("form#promocionFormDatosSaticB #sdelegOrig").val(data.sdelegOrig);
				$("form#promocionFormDatosSaticB #cveTipocorr").val(data.cveTipocorr);
				$("form#promocionFormDatosSaticB #cveFkPatron").val(data.cveFkPatron);
				$("form#promocionFormDatosSaticB #nomRazonsocial").val(data.nomRazonsocial);
				$("form#promocionFormDatosSaticB #regPatron").val(data.regPatron);				
				$("form#promocionFormDatosSaticB #txRfcpatron").val(data.txRfcpatron);
				$("form#promocionFormDatosSaticB #txCurppatron").val(data.txCurppatron);
				$("form#promocionFormDatosSaticB #numRegObra").val(data.numRegObra);
				$("form#promocionFormDatosSaticB #incidencia").val(data.incidencia);
				$("form#promocionFormDatosSaticB #fechaIncial").val(data.fechaIncial);
				$("form#promocionFormDatosSaticB #fechaFinal").val(data.fechaFinal);
				$("form#promocionFormDatosSaticB #refColonia").val(data.refColonia);
				$("form#promocionFormDatosSaticB #numNroext").val(data.numNroext);
				$("form#promocionFormDatosSaticB #domCalle").val(data.domCalle);
				$("form#promocionFormDatosSaticB #numCodigopostal").val(data.numCodigopostal);
				$("form#promocionFormDatosSaticB #cveDeteccion").val(data.cveDeteccion);
				$("form#promocionFormDatosSaticB #bandera").val(satic);
				$("form#promocionFormDatosSaticB #numCodigopostal").val(data.numCodigopostal);
				 
				
				oDgDatosSatic.dialog("open");
				desbloquear();
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
		}
	 } 
}

function Periodos(){
	oDgPeriodosSatic.dialog('open');
}

function mostarDomGeo(context){
	var resultado = openWindowregistraDomicilioInegi(context,'catalogo/promocionSaticB/promocionDomGeografico.do');	
	refrescar('promocionFormRegPromocion');	
}

function refrescar(form){
	var crtPromocion = $("#"+form).serializeObject(true);
	$.postJSON("promocionSaticB/obtenerDomicilioSession.do", crtPromocion,function(data) {
		$("form#"+form+" #estado").val(data.estado);
		$("form#"+form+" #municipio").val(data.municipio);
		$("form#"+form+" #domCalle").val(data.domCalle);
		$("form#"+form+" #refColonia").val(data.refColonia);
		$("form#"+form+" #numNroint").val(data.numNroint);
		$("form#"+form+" #numNroext").val(data.numNroext);
		$("form#"+form+" #numCodigopostal").val(data.numCodigopostal);
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
							
	});
}

function jsValidarPromover(){
	
	$("form#promoverSATICBForm #labelcriterios").html('');
	$("form#promoverSATICBForm #labelNuOficio").html('');
	$("form#promoverSATICBForm #labelFecOficio").html('');
	var resp = false;
	
	if($("form#promoverSATICBForm #selectCriterios").val() == ''){
		$("form#promoverSATICBForm #labelcriterios").html('<label class="etiquetaError">Campo Requerido</label>');
	}

	if($("form#promoverSATICBForm #nuOficiopro").val() == ''){
		$("form#promoverSATICBForm #labelNuOficio").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	
	if($("form#promoverSATICBForm #fecOficio").val() == ''){
		$("form#promoverSATICBForm #labelFecOficio").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	
	if($("form#promoverSATICBForm #selectCriterios").val() != '' &&
	   $("form#promoverSATICBForm #nuOficiopro").val() != '' &&
	   $("form#promoverSATICBForm #fecOficio").val() != ''){
		resp = true;
	}
		
	return resp;	
}

function jsPromoverDeteccion(){
	
	bloquear();
	var deteccion = $("#promoverSATICBForm").serializeObject(true);
	$.postJSON("promocionSaticB/promoverSATICB.do", deteccion, function(data) {
		alert("La detecci\u00f3n se a promovido con el n\u00famero de folio : " + data.nuFoliopromocion);
		oDgPromover.dialog("close");
		//jsLimpiarForma();
		jsLimpiarFormaPromover();
		oDgDatosSatic.dialog("close");
		oDtDisponiblesSaticB.fnDraw();
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		desbloquear();
	});			
}


function jsLimpiarFormaPromover(){
	$('form#promoverSATICBForm #selectCriterios').val('');
	$('form#promoverSATICBForm #nuOficiopro').val('');
	$('form#promoverSATICBForm #fecOficio').val('');
}

function jsLlenaCriteriosPromover(){
	
	var variable = '{"idOrigen":"1"}';		
	var variableJson = jQuery.parseJSON(variable);
	$.postJSON("promocionSaticB/cboCriteriosSeleccion.do", variableJson, function(data) {
		
		var myselect=document.getElementById("selectCriterios");
		myselect.options.length = 1;
		
		for(var i = 0 ; i < data.length ; i++){
			myselect.add(new Option(data[i][3], data[i][0]));
		}
		
	});
}

function jsvalidarNumerico(e) { 
	
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890.]/;
    te = String.fromCharCode(tecla);
    
    return patron.test(te);
} 