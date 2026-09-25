
var idDisponiblesPromocion 	= "#dtDisponiblesPromocion";
var idDgPromocion			= "#dgPromocion";
var idPromocionConsultaDet 	= "#dgObraRegistradaSaticA";
var idRegistroPromocion		= "#dgPromocionRegistro"
var idConfirmarSaticA       = "#dgConfirmarSaticA"; 
var idDgPromover            = "#dgPromoverSaticA";

var oDtDisponiblesPromocion;
var oDgPromocion;
var oDgPromocionConsultaDet;
var oDgRegistroPromocion;
var validaCapturaVal;
var validaDatos;
var validaForm;
var oDgConfirmar;
var oDgPromover;

$(document).ready(function() {

	 // Fecha de Inicio y Termino
	 $( "#fechaIncial, #fechaFinal, form#promocionFormConsultaDet #fecFechainicioEst, form#promocionFormConsultaDet #fecFechaterminoEst, form#promocionFormRegPromocion #fechaOficio, form#promocionFormRegPromocion #fechaNotificacion, form#promocionFormConsultaDet #fechaEstimIncio, form#promocionFormConsultaDet #fechaEstTerm" ).datepicker( { dateFormat: 'dd-mm-yy' });	 	

	 // Fechas de datosCompletosDeteccion.jsp
	 $( "form#promocionFormConsultaDet #fechaEstimIncio2, form#promocionFormConsultaDet #fechaEstTerm2" ).datepicker( { dateFormat: 'dd-mm-yy' });
	 
		$("form#promoverSatiAForm #fechaOficio").datepicker( { 
			dateFormat: 'dd/mm/yy',
			onSelect: function(dateText, inst) { 
		       // $('#fecOficio').datepicker('option', 'minDate', dateText); 
		    }
		});
		
		$.postJSON("promocionDet/obtenerFechaServidor.do", null,function(data) {
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(data){
//			alert(JSON.stringify(data, null, 4));
			$('form#promoverSatiAForm #fechaOficio').datepicker('option', 'maxDate', data.responseText);
			
		});
		
		$.postJSON("promocionDet/obtenerFechaServidorMinima.do", null,function(data) {
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(data){
//			alert(JSON.stringify(data, null, 4));
			$('form#promoverSatiAForm #fechaOficio').datepicker('option', 'minDate', data.responseText);
		});
	 
	/**
	 * Inicializacion del data table
	 */
	 oDgPromocion = $(idDisponiblesPromocion).dataTable({
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
				 oObj.aData['cveDeteccion'] +'" id="radioTable" class="radioDeteccion" name="radio" onclick="ocultaDiv()"/> ';
				 return retVal;
			 }, 
			 aTargets: [0]
		 },{
			 "sWidth": "20%",
			 "sTitle" : "Folio Detecci&oacute;n ",
			 "mDataProp" : "nuFoliodeteccion",
			 "sClass": "dtCenterClassColumn"
		 },{
			 "sWidth": "15%",
			 "sTitle" : "Reporte de Obra",
			 "mDataProp" : "nuReportectrlobra",
			 "sClass": "dtCenterClassColumn"
		 },{
			 "sWidth": "15%",
			 "sTitle" : "Fecha de Detecci&oacute;n",
			 "mDataProp" : "fechaDet",
			 "sClass": "dtCenterClassColumn"
		 }, {
			 "sWidth": "20%",
			 "sTitle" : "Calle",
			 "mDataProp" : "domCalle",
			 "sClass": "dtCenterClassColumn"
		 },{
			 "sWidth": "15%",
			 "sTitle" : "Colonia",
			 "mDataProp" : "refColonia",
			 "sClass": "dtCenterClassColumn"
		 },{
			 "sWidth": "7%",
			 "sTitle" : "Num Interior",
			 "mDataProp" : "numNroint",
			 "sClass": "dtCenterClassColumn"
		 },{
			 "sWidth": "8%",
			 "sTitle" : "Num Exterior",
			 "mDataProp" : "numNroext",
			 "sClass": "dtCenterClassColumn"
		 }
		 ],"bProcessing" : true,
		 "sAjaxSource" : 'promocionDet/paginar.do',
		 "fnServerData" : function(sSource, aoData, fnCallback) {				

			 var wrapper = new Object();
			 wrapper.aoData = aoData;								

			 var oForm = $("#promocionForm").toObject({mode:'first'});
			 var fechaInicial = $("form#promocionForm #fechaIncial").val();
			 var fechaFinal = $("form#promocionForm #fechaFinal").val();
			 wrapper.oForm = oForm;

			 $.postJSON(sSource, wrapper, function(data) {						
				 fnCallback(data);							
				 if(data.aaData!=null){
				 	if(data.aaData.length<=0 && fechaInicial!="" && fechaFinal!=""){
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
	 
	 oDgPromocionConsultaDet = $(idPromocionConsultaDet).dialog({
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
					$("form#promoverSatiAForm #cveDeteccion").val($("form#promocionFormConsultaDet #cveDeteccion").val());
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
	 
	 oDgConfirmar = $(idConfirmarSaticA).dialog({
			autoOpen: false,
			modal:true,
			resizable:false,
			height: 150,
			width: 700,
			buttons: {
				"Si": function() { 
					bloquear();
					var deteccion = $("#promocionFormConsultaDet").serializeObject(true);
					$.postJSON("promocionDet/promueve.do", deteccion, function(data) {
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
					$.postJSON("promocionDet/removerDomicilioSession.do", crtPromocion, function(data) {					
					}).error(function(data){ 
						validarSesionExpirada(data);
					}).complete(function(){
						//Instrucciones para el complete													
					});				    
				},
				open:function(event, ui)
		        {					
					var promocion = $("#promocionFormRegPromocion").serializeObject(true);
					  $.postJSON("promocionDet/consultaMotivos.do", promocion, function(datas) {
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
							$.postJSON("promocionDet/agregaPromocion.do", promocion, function(data) {
								alert("La promocion ha sido agregada con el Folio: "+data.nuFoliopromocion);
								oDgPromocion.fnDraw();
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
	
	validaForm = $('#promocionForm').validate({
		rules:{
			fechaIncial:{
				 required: true
			},
			fechaFinal:{
				 required: true
			}
		}
	});
	
	//VALIDACIONES DE FORMULARIOS
	$("#promocionFormConsultaDet").validate({
		 rules: {			 
			 fechaDeteccion: {
				 required: true
			 },
			 nomRazonsocial: {
				 required: true
			 },
			 regPatron: {
				 required: true				 
			 },
			 fechaEstimIncio: {
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
	
//	$("form#promocionFormConsultaDet #cvePkTipObra").change(function(){
//		if($("form#promocionFormConsultaDet #cvePkTipObra").val()!=-1){
//			$("form#promocionFormConsultaDet #cvePkFaseConst").prop('disabled','disabled');			
//		}else{
//			$("form#promocionFormConsultaDet #cvePkFaseConst").prop('disabled','');
//		}
//	});
//	
//	$("form#promocionFormConsultaDet #cvePkFaseConst").change(function(){
//		if($("form#promocionFormConsultaDet #cvePkFaseConst").val()!=-1){
//			$("form#promocionFormConsultaDet #cvePkTipObra").prop('disabled','disabled');			
//		}else{
//			$("form#promocionFormConsultaDet #cvePkTipObra").prop('disabled','');
//		}
//	});
});

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
			$.postJSON("promocionDet/mostrar.do", deteccion, function(data) {	
				$('form#promocionFormConsultaDet #cveDeteccion').val(data.cveDeteccion);	
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
				$('form#promocionFormConsultaDet #tipClaseobra').val(data.tipClaseobra);
				$('form#promocionFormConsultaDet #canSuperficie').val(data.canSuperficie);
				$('form#promocionFormConsultaDet #cvePkTipObra').val(data.cvePkTipObra);
				$('form#promocionFormConsultaDet #cvePkFaseConst').val(data.cvePkFaseConst);
				$('form#promocionFormConsultaDet #impCostoobra').val(data.impCostoobra);
				$('form#promocionFormConsultaDet #porAvanceobraEst').val(data.porAvanceobraEst);
				$('form#promocionFormConsultaDet #cveFkZona').val(data.cveFkZona);
				$('form#promocionFormConsultaDet #numTrabajdores').val(data.numTrabajdores);
				$('form#promocionFormConsultaDet #desDependenciapub').val(data.desDependenciapub);
				$('form#promocionFormConsultaDet #desDepcontratante').val(data.desDepcontratante);
				$('form#promocionFormConsultaDet #fechaEstimIncio2').val(data.fechaEstimIncio2);
				$('form#promocionFormConsultaDet #fechaEstTerm2').val(data.fechaEstTerm2);
				desbloquear();
				oDgPromocionConsultaDet.dialog('open');	
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el 'complete'
			});
		}
	 }
}

function ocultaDiv(){
	$("#btnVisualizar").show();
}

function validaRegPatron(){
	if(!longitudMandatoria($("form#promocionFormConsultaDet #regPatron").val(),10,"Registro Patronal")) return false;
	oDgPromocionConsultaDet.dialog('close');
	bloquear();	
	var deteccion = $("#promocionFormConsultaDet").serializeObject(true);	
	$.postJSON("promocionDet/validaRegPatron.do", deteccion, function(data) {
		if(data.cveFkPatron==null){
			alert("El registro Patronal es invalido");
			$("form#promocionFormConsultaDet #nomRazonsocial").val("");
			$("form#promocionFormConsultaDet #txRfcpatron").val("");
			$("form#promocionFormConsultaDet #txCurppatron").val("");
			$("form#promocionFormConsultaDet #cveFkPatron").val("");
			$("form#promocionFormConsultaDet #actividad").val("");
		}else{
			alert("El registro Patronal es valido");
			$("form#promocionFormConsultaDet #nomRazonsocial").val(data.nomRazonsocial);
			$("form#promocionFormConsultaDet #txRfcpatron").val(data.txRfcpatron);
			$("form#promocionFormConsultaDet #txCurppatron").val(data.txCurppatron);
			$("form#promocionFormConsultaDet #cveFkPatron").val(data.cveFkPatron);
			$("form#promocionFormConsultaDet #actividad").val(data.actividad);
		}						
		oDgPromocionConsultaDet.dialog('open');
		desbloquear();	
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		//Instrucciones para el 'complete'		
	});
}

function buscar(){
	if(validaForm.form()){
		$("#promocionObrasDeteccion").show();
		if($("form#promocionForm #regPatron").val() != ''){
			obtieneIdRegistro('promocionForm','regPatron');
		}
		oDgPromocion.fnDraw();	
	}	
}

function mostarDomGeo(context){
	var resultado = openWindowregistraDomicilioInegi(context,'catalogo/promocionDet/promocionDomGeografico.do');	
	refrescar('promocionFormRegPromocion');	
}

function refrescar(form){
	var crtPromocion = $("#"+form).serializeObject(true);
	$.postJSON("promocionDet/obtenerDomicilioSession.do", crtPromocion,function(data) {
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

function obtieneIdRegistro(form,campo){
	if(!longitudMandatoria($("form#"+form+" #"+campo).val(),10,"Registro Patronal")) return false;
	if($("form#"+form+" #"+campo).val()==""){
		return false;
	}
	bloquear();
	var deteccion = $("#"+form).serializeObject(true);	
	$.postJSON("promocionDet/verficaPatron.do", deteccion, function(data) {
		if(data==0){
			alert("El registro Patronal es Incorrecto");
			$("form#"+form+" #cveFkPatron").val("");
			$("form#"+form+" #"+campo).val("");
		}else{
			$("form#promocionForm #cveFkPatron").val(data);
		}						
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		desbloquear();
	});
}

function jsValidaGuardar(){
	
	$("form#promocionFormConsultaDet #labelClaseObra").html('');
	$("form#promocionFormConsultaDet #labeltipoObra").html('');
	var resp = false;
	
	if($("form#promocionFormConsultaDet #tipClaseobra").val() == ''){
		$("form#promocionFormConsultaDet #labelClaseObra").html('<label class="etiquetaError">Campo Requerido</label>');
		resp = false;
	}
	
	if($("form#promocionFormConsultaDet #cvePkTipObra").val() == '-1'){
		$("form#promocionFormConsultaDet #labeltipoObra").html('<label class="etiquetaError">Campo Requerido</label>');
		resp = false;
	}
	
	if($("form#promocionFormConsultaDet #tipClaseobra").val() != '' && $("form#promocionFormConsultaDet #cvePkTipObra").val() != '-1'){
		$("form#promocionFormConsultaDet #labeltipoObra").html('');
		$("form#promocionFormConsultaDet #labelClaseObra").html('');
		resp = true;
	}
		
	return resp;	
	
}

function jsLlenaCriteriosPromover(){
	
	var variable = '{"idOrigen":"1"}';		
	var variableJson = jQuery.parseJSON(variable);
	$.postJSON("promocionDet/cboCriteriosSeleccion.do", variableJson, function(data) {
		
		var myselect=document.getElementById("selectCriterios");
		myselect.options.length = 1;
		
		for(var i = 0 ; i < data.length ; i++){
			myselect.add(new Option(data[i][3], data[i][0]));
		}
		
	});
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
	$('form#promocionFormConsultaDet #tipClaseobra').val('');
	$('form#promocionFormConsultaDet #canSuperficie').val('');
	$('form#promocionFormConsultaDet #cvePkTipObra').val('');
	$('form#promocionFormConsultaDet #cvePkFaseConst').val('');
	$('form#promocionFormConsultaDet #impCostoobra').val('');
	$('form#promocionFormConsultaDet #porAvanceobraEst').val('');
	$('form#promocionFormConsultaDet #cveFkZona').val('');
	$('form#promocionFormConsultaDet #numTrabajdores').val('');
	$('form#promocionFormConsultaDet #desDependenciapub').val('');
	$('form#promocionFormConsultaDet #desDepcontratante').val('');
	$('form#promocionFormConsultaDet #fechaEstimIncio2').val('');
	$('form#promocionFormConsultaDet #fechaEstTerm2').val('');
}

function jsValidarPromover(){
	
	$("form#promoverSatiAForm #labelcriterios").html('');
	$("form#promoverSatiAForm #labelNuOficio").html('');
	$("form#promoverSatiAForm #labelFecOficio").html('');
	var resp = false;
	
	if($("form#promoverSatiAForm #selectCriterios").val() == ''){
		$("form#promoverSatiAForm #labelcriterios").html('<label class="etiquetaError">Campo Requerido</label>');
	}

	if($("form#promoverSatiAForm #nuOficiopro").val() == ''){
		$("form#promoverSatiAForm #labelNuOficio").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	
	if($("form#promoverSatiAForm #fechaOficio").val() == ''){
		$("form#promoverSatiAForm #labelFecOficio").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	
	if($("form#promoverSatiAForm #selectCriterios").val() != '' &&
	   $("form#promoverSatiAForm #nuOficiopro").val() != '' &&
	   $("form#promoverSatiAForm #fechaOficio").val() != ''){
		resp = true;
	}
		
	return resp;	
}

function jsPromoverDeteccion(){
	
	bloquear();
	var deteccion = $("#promoverSatiAForm").serializeObject(true);
	$.postJSON("promocionDet/promoverSaticA.do", deteccion, function(data) {
		alert("La detecci\u00f3n se a promovido con el n\u00famero de folio : " + data.nuFoliopromocion);
		oDgPromover.dialog("close");
		jsLimpiarForma();
		jsLimpiarFormaPromover();
		oDgPromocionConsultaDet.dialog("close");
		oDgPromocion.fnDraw();
	}).error(function(data){ 
		validarSesionExpirada(data);
	}).complete(function(){
		desbloquear();
	});			
}

function jsLimpiarFormaPromover(){
	$('form#promoverSatiAForm #selectCriterios').val('');
	$('form#promoverSatiAForm #nuOficiopro').val('');
	$('form#promoverSatiAForm #fechaOficio').val('');
}

function jsValidaFechaFinal(){
	
	 var fecIni = $("form#promocionFormConsultaDet #fechaEstimIncio2").val();
	 var fecFinal = $("form#promocionFormConsultaDet #fechaEstTerm2").val();
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecIni,fecFinal)){
			 $("form#promocionFormConsultaDet #fechaEstTerm2").val(fecFinal);
		 }else{
			 $("form#promocionFormConsultaDet #fechaEstTerm2").val('');
			 alert('La fecha final no puede ser menor a la fecha inicial');
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

function jsvalidarAlfaNumerico(e) { 
	
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ]/;
    te = String.fromCharCode(tecla);
    
    return patron.test(te);
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