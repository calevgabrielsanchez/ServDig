
var idPromocionExhorto		= "#dtDisponiblesPromocionExhorto";
var idPromocionExhortoCons = "#dgPromocionExhortoCons";
var idRegistroPromocion		= "#dgPromocionRegistro";
var idObrasRegistradas = "#dgObraRegistrada";
var idDgConfirmar = "#dgConfirmarExConst";
var idDgPromover = "#dgPromoverConstruccion";

var oDgPromocionExhorto;
var oDgPromocionExhortoCons;
var oDgObrasRegistradas;
var validaDatos;
var validaForm;
var oDgConfirmar;
var oDgPromover;

$(document).ready(function()
{
	
	$( "form#promocionExhortoConstForm #fechaEstimIncio,form#promocionExhortoConstForm #fechaEstTerm,form#promocionFormRegPromocion #fechaNotificacion,form#promocionFormRegPromocion #fechaOficio,form#promocionFormExhortoCons #fechaEstimIncio2,form#promocionFormExhortoCons #fechaEstTerm2" ).datepicker( { dateFormat: 'dd-mm-yy' });
	$( "form#obraRegistradaForm #fechaEstimIncio2,form#obraRegistradaForm #fechaEstTerm2" ).datepicker( { dateFormat: 'dd-mm-yy' });

	
	$("form#promoverConstruccionForm #fechaOficio").datepicker( { 
		dateFormat: 'dd/mm/yy',
		onSelect: function(dateText, inst) { 
	       // $('#fecOficio').datepicker('option', 'minDate', dateText); 
	    }
	});
	
	$.postJSON("ExhortoConst/obtenerFechaServidor.do", null,function(data) {
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
//		alert(JSON.stringify(data, null, 4));
		$('form#promoverConstruccionForm #fechaOficio').datepicker('option', 'maxDate', data.responseText);
		
	});
	
	$.postJSON("ExhortoConst/obtenerFechaServidorMinima.do", null,function(data) {
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
//		alert(JSON.stringify(data, null, 4));
		$('form#promoverConstruccionForm #fechaOficio').datepicker('option', 'minDate', data.responseText);
	});
	
	oDgPromocionExhorto = $(idPromocionExhorto).dataTable({
		 bJQueryUI : true,
		 bFilter : false,
		 bInfo:true,
		 bSort: true,
		 "bPaginate": true,
		 "bAutoWidth" : false,
		 "bServerSide" :	true,
		 "aoColumns" : [ {
			 fnRender :function(oObj){
				 var retVal = '<input type="radio" value="' +
				 oObj.aData['cveDeteccion'] +'" id="radioTable" class="radioDeteccion" name="radio" onclick="muestraDiv()"/> ';
				 return retVal;
			 }, 
			 aTargets: [0]
		 	},{
				"sTitle" : "Folio Detecci&oacute;n ",
				"mDataProp" : "nuFoliodeteccion",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Reporte de Obra",
				"mDataProp" : "nuReportectrlobra",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Fecha de Detecci&oacute;n",
				"mDataProp" : "fechaDet",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Calle",
				"mDataProp" : "domCalle",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Colonia",
				"mDataProp" : "refColonia",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Numero Interior",
				"mDataProp" : "numNroint",
				"sClass": "dtCenterClassColumn"
			},{
				"sTitle" : "Numero Exterior",
				"mDataProp" : "numNroext",
				"sClass": "dtCenterClassColumn"
			}
			 ],"bProcessing" : true,
			 "sAjaxSource" : 'ExhortoConst/paginarExhorto.do',
			 "fnServerData" : function(sSource, aoData, fnCallback) {				

			 var wrapper = new Object();
			 wrapper.aoData = aoData;								

			 var oForm = $("#promocionExhortoConstForm").toObject({mode:'first'});
			 var fechaInicial = $("form#promocionExhortoConstForm #fechaEstimIncio").val();
			 var fechaFinal = $("form#promocionExhortoConstForm #fechaEstTerm").val(); 
			 var nuFoliodeteccion = $("form#promocionExhortoConstForm #nuFoliodeteccion").val(); 
			 var regPatron = $("form#promocionExhortoConstForm #regPatron").val(); 
			 wrapper.oForm = oForm;

			 $.postJSON(sSource, wrapper, function(data) {										
				 fnCallback(data);
				 if(data.aaData!=null){
					 if(data.aaData.length == 0 && fechaInicial!="" && fechaFinal!=""){
						 $('#labelError').html('<label style="color: red;">No se encontraron resultados de su b&uacute;squeda, por favor realice nueva b&uacute;squeda</label>');
					 }else if(data.aaData.length == 0 && nuFoliodeteccion!=""){
						 $('#labelError').html('<label style="color: red;">No se encontraron resultados de su b&uacute;squeda, por favor realice nueva b&uacute;squeda</label>'); 
					 }else if(data.aaData.length == 0 && regPatron!=""){
						 $('#labelError').html('<label style="color: red;">No se encontraron resultados de su b&uacute;squeda, por favor realice nueva b&uacute;squeda</label>'); 
					 }else{
						 $('#labelError').html('');
					 }
				 }
			 }).complete(function(){
				desbloquear();
			 });
		 }
	 });
	
	oDgPromocionExhortoCons = $(idPromocionExhortoCons).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		buttons: {
			"Promover": function() {
				var deteccion = $("#promocionFormExhortoCons").serializeObject(true);
				$.postJSON("ExhortoConst/promueve.do", deteccion, function(data) {
					$("form#promocionFormRegPromocion #cveDeteccion").val(data.cveDeteccion);
					$("form#promocionFormRegPromocion #cveTipocorr").val(data.cveTipocorr);
					$("form#promocionFormRegPromocion #cveFkPatron").val(data.cveFkPatron);
					$("form#promocionFormRegPromocion #idTipo").val(data.idTipo);
					$("form#promocionFormRegPromocion #idOrigen").val(data.idOrigen);
					oDgPromocionExhortoCons.dialog('close');
					oDgRegistroPromocion.dialog('open');
					limpiarFormulario("#promocionFormExhortoCons");					
				}).error(function(data){ 
					validarSesionExpirada(data);
				}).complete(function(){
					//Instrucciones para el 'complete'
				});															
			}, 
			"Regresar": function() { 
				$(this).dialog("close");
				limpiarFormulario("#dgPromocionExhortoCons");
			} 
		}
	});
	
	oDgObrasRegistradas = $(idObrasRegistradas).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		buttons: {
			"Guardar Datos": function() {
				var respuesta = jsValidaGuardar();
				if(respuesta == true){
					oDgConfirmar.dialog('open');
				}
			}, 
			"Promover": function() {
				$("form#promoverConstruccionForm #cveDeteccion").val($("form#obraRegistradaForm #cveDeteccion").val());
				jsLlenaCriteriosPromover();
				oDgPromover.dialog('open');
			},
			"Salir"   : function() {
				jsLimpiarForma();
				$(this).dialog("close"); 
			}
		}
	});
	
	
	oDgPromover = $(idDgPromover).dialog({
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
	
	oDgConfirmar = $(idDgConfirmar).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		height: 150,
		width: 700,
		buttons: {
			"Si": function() { 
				bloquear();
				var deteccion = $("#obraRegistradaForm").serializeObject(true);
				$.postJSON("ExhortoConst/promueve.do", deteccion, function(data) {
					alert('La detecci\u00f3n se guardo exitosamente');
					$(this).dialog("close"); 
				}).error(function(data){ 
					validarSesionExpirada(data);
				}).complete(function(){
					desbloquear();
				});			
				
				$(this).dialog("close"); 
			}, 
			"No": function() { 
				$(this).dialog("close"); 
			} 
		}
	});
	
	oDgRegistroPromocion = $(idRegistroPromocion).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 930,
		beforeClose :function(event,ui){		    				    
			limpiarFormulario("#promocionFormRegPromocion");
			var crtPromocion = $("#promocionFormRegPromocion").toObject({mode:'first'});
			$.postJSON("ExhortoConst/removerDomicilioSession.do", crtPromocion, function(data) {					
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el complete													
			});				    
		},
		open:function(event, ui)
		{					
			var promocion = $("#promocionFormRegPromocion").serializeObject(true);
			$.postJSON("ExhortoConst/consultaMotivos.do", promocion, function(datas) {
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
					$.postJSON("ExhortoConst/agregaPromocion.do", promocion, function(data) {
						alert("La promoci\u00f3n ha sido agregada con el Folio: "+data.nuFoliopromocion);
						oDgPromocionExhorto.fnDraw();
						oDgRegistroPromocion.dialog("close");							
						limpiarFormulario("#promocionFormRegPromocion");						
					}).error(function(data){ 
						validarSesionExpirada(data);
					}).complete(function(){
						//Instrucciones para el 'complete'
					});						
				}														
			} 
		}
	});
	
	validaForm = $('#promocionExhortoConstForm').validate({
		rules:{
			fechaEstimIncio:{
				 required: true
			},
			fechaEstTerm:{
				 required: true
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
			 fechaOficio: {
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
		
}); 

function buscar(){
	
	if(jsValidaBuscar() == true){
		$("#promocionExhortoDeteccion").show();
		$("#promocionExhortoObrasDeteccion").show();
		oDgPromocionExhorto.fnDraw();	
		bloquear();
	}
}

function muestraDiv(){
	$("#btnVisualizar").show();
}

function mostrar(){
	var radios = document.getElementsByName("radio");
	for (i=0;i<radios.length;i++)
	 {
		if(radios[i].checked)
		{
	
			var idDeteccion = radios[i].value;
			var sDeteccion = '{"cveDeteccion":'+idDeteccion+'}';
			var deteccion = jQuery.parseJSON(sDeteccion);
			jsLimpiarForma();
			// Buscamos el elemento
			bloquear();
			$.postJSON("ExhortoConst/mostrar.do", deteccion, function(data) {	
				$('form#obraRegistradaForm #cveDeteccion').val(data.cveDeteccion);	
				$("#labelFolioDeteccion").html('<label> ' + data.nuFoliodeteccion + ' </label>');
				if(data.nuReportectrlobra != null){
					$("#labelNumReporteObra").html('<label> ' + data.nuReportectrlobra + ' </label>');
				}
				$("#labelFechaDeteccion").html('<label> ' + data.fechaDeteccion + ' </label>');
				$("#labelEstado").html('<label> ' + data.estado + ' </label>');
				$("#labelMunicipio").html('<label> ' + data.municipio + ' </label>');
				$("#labelCalle").html('<label> ' + data.domCalle + ' </label>');				
				$("#labelColonia").html('<label> ' + data.refColonia + ' </label>');
				if(data.numNroint != null)
				$("#labelNUmInt").html('<label> ' + data.numNroint + ' </label>');
				if(data.numNroext != null)
				$("#labelNumExt").html('<label> ' + data.numNroext + ' </label>');
				$("#labelCP").html('<label> ' + data.numCodigopostal + ' </label>');
				$('form#obraRegistradaForm #tipClaseobra').val(data.tipClaseobra);
				$('form#obraRegistradaForm #canSuperficie').val(data.canSuperficie);
				$('form#obraRegistradaForm #cvePkTipObra').val(data.cvePkTipObra);
				$('form#obraRegistradaForm #cvePkFaseConst').val(data.cvePkFaseConst);
				$('form#obraRegistradaForm #impCostoobra').val(data.impCostoobra);
				$('form#obraRegistradaForm #porAvanceobraEst').val(data.porAvanceobraEst);
				$('form#obraRegistradaForm #cveFkZona').val(data.cveFkZona);
				$('form#obraRegistradaForm #numTrabajdores').val(data.numTrabajdores);
				$('form#obraRegistradaForm #desDependenciapub').val(data.desDependenciapub);
				$('form#obraRegistradaForm #desDepcontratante').val(data.desDepcontratante);
				$('form#obraRegistradaForm #fechaEstimIncio2').val(data.fechaEstimIncio2);
				$('form#obraRegistradaForm #fechaEstTerm2').val(data.fechaEstTerm2);
				desbloquear();
				oDgObrasRegistradas.dialog('open');	
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
		}
	 }
}

function mostarDomGeo(context){
	var resultado = openWindowregistraDomicilioInegi(context,'promocion/ExhortoConst/promocionDomGeografico.do');	
	refrescar('promocionFormRegPromocion');	
}

function refrescar(form){
	var crtPromocion = $("#"+form).serializeObject(true);
	$.postJSON("ExhortoConst/obtenerDomicilioSession.do", crtPromocion,function(data) {
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

function valorMaxim(campo,leyenda,form){
	campo = "form#"+form+" #"+campo;
	if(!valorMaximo($(campo).val(),leyenda,100)){		
		$(campo).val("");		
	}
}

function validaRegPatron(){
	if(!longitudMandatoria($("form#promocionFormExhortoCons #regPatron").val(),10,"Registro Patronal")) return false;
	oDgPromocionExhortoCons.dialog('close');
	bloquear();	
	var deteccion = $("#promocionFormExhortoCons").serializeObject(true);	
	$.postJSON("ExhortoConst/validaRegPatron.do", deteccion, function(data) {
		if(data.cveFkPatron==null){
			alert("El registro Patronal es invalido");
			$("form#promocionFormExhortoCons #nomRazonsocial").val("");
			$("form#promocionFormExhortoCons #txRfcpatron").val("");
			$("form#promocionFormExhortoCons #txCurppatron").val("");
			$("form#promocionFormExhortoCons #cveFkPatron").val("");
			$("form#promocionFormExhortoCons #actividad").val("");
		}else{
			alert("El registro Patronal es valido");
			$("form#promocionFormExhortoCons #nomRazonsocial").val(data.nomRazonsocial);
			$("form#promocionFormExhortoCons #txRfcpatron").val(data.txRfcpatron);
			$("form#promocionFormExhortoCons #txCurppatron").val(data.txCurppatron);
			$("form#promocionFormExhortoCons #cveFkPatron").val(data.cveFkPatron);
			$("form#promocionFormExhortoCons #actividad").val(data.actividad);
		}						
	}).error(function(data){ 
		desbloquear();
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el 'complete'
		oDgPromocionExhortoCons.dialog('open');		
	});
}

function jsValidaPeriodoInicial(fecha){
	var fechaFinal = $("#fechaEstTerm").val();
	if(fechaFinal != '' && fecha != ''){
		var anioI = fechaFinal.substring(6,10);
		var anioF = fecha.substring(6,10);
		
		if(anioI == anioF){
			$("#fechaEstimIncio").val(fecha);
			return true;
		}else if(anioI < anioF){
			alert('La fecha inicial no puede ser mayor a la fecha final');
			$("#fechaEstimIncio").val("");
			$("#fechaEstTerm").val("");
			return;
		}else{
			alert('No se puede elegir mas de un ejercicio');
			$("#fechaEstimIncio").val("");
			$("#fechaEstTerm").val("");
			return false;
		}
	}	
}

function jsValidaPeriodoFinal(fecha){
	var fechaIncial = $("#fechaEstimIncio").val();
	if(fechaIncial != '' && fecha != ''){
		var anioI = fechaIncial.substring(6,10);
		var anioF = fecha.substring(6,10);
		
		if(anioI == anioF){
			$("#fechaEstTerm").val(fecha);
			return true;
		}else{
			alert('No se puede elegir mas de un ejercicio');
			$("#fechaEstTerm").val("");
			$("#fechaEstimIncio").val("")
			return false;
		}
	}	
}

function jsValidaBuscar(){
	
	$("#labelFechas").html('');
	var resp = false;
	if($("#nuFoliodeteccion").val() != ''){
		$("#fechaEstimIncio").val('');
		$("#fechaEstTerm").val('');
		$("#regPatron").val('');		
		resp = true;
	}else if($("#regPatron").val() != ''){
		$("#fechaEstimIncio").val('');
		$("#fechaEstTerm").val('');
		resp = true;
	}else if($("#fechaEstimIncio").val() == '' || $("#fechaEstimIncio").val() == ''){		
		$("#labelFechas").html('<label style="color: red;">Campo obligatorio</label>');
	}else{
		resp = true;
	}
	
	return resp;
}

function jsValidaFechaFinal(){
	
	 var fecIni = $("form#obraRegistradaForm #fechaEstimIncio2").val();
	 var fecFinal = $("form#obraRegistradaForm #fechaEstTerm2").val();
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecIni,fecFinal)){
			 $("form#obraRegistradaForm #fechaEstTerm2").val(fecFinal);
		 }else{
			 $("form#obraRegistradaForm #fechaEstTerm2").val('');
			 alert('La fecha final no puede ser menor a la fecha inicial');
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

function jsvalidarNumerico(e) { 
	
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890.]/;
    te = String.fromCharCode(tecla);
    
    return patron.test(te);
} 

function jsValidaFecIni(fecIni){
	
	if(jsValidaFecha(fecIni)){
		 $("form#obraRegistradaForm #fechaEstimIncio2").val(fecIni);
	}else{
		 $("form#obraRegistradaForm #fechaEstimIncio2").val("");
		 alert('La fecha inicial no puede ser mayor al dia actual');
	}
}


function jsValidaFecFin(fecIni){
	
	if(jsValidaFecha(fecIni)){
		 $("form#obraRegistradaForm #fechaEstTerm2").val(fecIni);
	}else{
		 $("form#obraRegistradaForm #fechaEstTerm2").val("");
		 alert('La fecha final no puede ser mayor al dia actual');
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

function jsValidaGuardar(){
	
	$("form#obraRegistradaForm #labelClaseObra").html('');
	$("form#obraRegistradaForm #labeltipoObra").html('');
	var resp = false;
	
	if($("form#obraRegistradaForm #tipClaseobra").val() == ''){
		$("form#obraRegistradaForm #labelClaseObra").html('<label class="etiquetaError">Campo Requerido</label>');
		resp = false;
	}
	
	if($("form#obraRegistradaForm #cvePkTipObra").val() == '-1'){
		$("form#obraRegistradaForm #labeltipoObra").html('<label class="etiquetaError">Campo Requerido</label>');
		resp = false;
	}
	
	if($("form#obraRegistradaForm #tipClaseobra").val() != '' && $("form#obraRegistradaForm #cvePkTipObra").val() != '-1'){
		$("form#obraRegistradaForm #labeltipoObra").html('');
		$("form#obraRegistradaForm #labelClaseObra").html('');
		resp = true;
	}
		
	return resp;	
	
}

function jsLlenaCriteriosPromover(){
	
	var variable = '{"idOrigen":"1"}';		
	var variableJson = jQuery.parseJSON(variable);
	$.postJSON("ExhortoConst/cboCriteriosSeleccion.do", variableJson, function(data) {
		
		var myselect=document.getElementById("selectCriterios");
		myselect.options.length = 1;
		
		for(var i = 0 ; i < data.length ; i++){
			myselect.add(new Option(data[i][3], data[i][0]));
		}
		
	});
}

function jsPromoverDeteccion(){
	
	bloquear();
	var deteccion = $("#promoverConstruccionForm").serializeObject(true);
	$.postJSON("ExhortoConst/promoverConstruccion.do", deteccion, function(data) {
		alert("La detecci\u00f3n se a promovido con el n\u00famero de folio : " + data.nuFoliopromocion);
		oDgPromover.dialog("close");
		jsLimpiarForma();
		jsLimpiarFormaPromover();
		oDgObrasRegistradas.dialog("close");
		oDgPromocionExhorto.fnDraw();
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		desbloquear();
	});			
}

function jsValidarPromover(){
	
	$("form#promoverConstruccionForm #labelcriterios").html('');
	$("form#promoverConstruccionForm #labelNuOficio").html('');
	$("form#promoverConstruccionForm #labelFecOficio").html('');
	var resp = false;
	
	if($("form#promoverConstruccionForm #selectCriterios").val() == ''){
		$("form#promoverConstruccionForm #labelcriterios").html('<label class="etiquetaError">Campo Requerido</label>');
	}

	if($("form#promoverConstruccionForm #nuOficiopro").val() == ''){
		$("form#promoverConstruccionForm #labelNuOficio").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	
	if($("form#promoverConstruccionForm #fechaOficio").val() == ''){
		$("form#promoverConstruccionForm #labelFecOficio").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	
	if($("form#promoverConstruccionForm #selectCriterios").val() != '' &&
	   $("form#promoverConstruccionForm #nuOficiopro").val() != '' &&
	   $("form#promoverConstruccionForm #fechaOficio").val() != ''){
		resp = true;
	}
		
	return resp;	
}

function jsLimpiarForma(){
	$("#labelFolioDeteccion").html('');
	$("#labelNumReporteObra").html('');
	$("#labelFechaDeteccion").html('');
	$("#labelEstado").html('');
	$("#labelMunicipio").html('');
	$("#labelCalle").html('');				
	$("#labelColonia").html('');
	$("#labelNUmInt").html('');
	$("#labelNumExt").html('');
	$("#labelCP").html('');
	$('form#obraRegistradaForm #tipClaseobra').val('');
	$('form#obraRegistradaForm #canSuperficie').val('');
	$('form#obraRegistradaForm #cvePkTipObra').val('');
	$('form#obraRegistradaForm #cvePkFaseConst').val('');
	$('form#obraRegistradaForm #impCostoobra').val('');
	$('form#obraRegistradaForm #porAvanceobraEst').val('');
	$('form#obraRegistradaForm #cveFkZona').val('');
	$('form#obraRegistradaForm #numTrabajdores').val('');
	$('form#obraRegistradaForm #desDependenciapub').val('');
	$('form#obraRegistradaForm #desDepcontratante').val('');
	$('form#obraRegistradaForm #fechaEstimIncio2').val('');
	$('form#obraRegistradaForm #fechaEstTerm2').val('');
}

function jsLimpiarFormaPromover(){
	$('form#promoverConstruccionForm #selectCriterios').val('');
	$('form#promoverConstruccionForm #nuOficiopro').val('');
	$('form#promoverConstruccionForm #fechaOficio').val('');
}

function jsvalidarAlfaNumerico(e) { 
	
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ]/;
    te = String.fromCharCode(tecla);
    
    return patron.test(te);
} 

