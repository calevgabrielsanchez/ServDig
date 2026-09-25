
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
var flagRecarga=true;
var regPatronalExCons;

$('#impCostoobra').html('$'+moneyMaskDT(0,2));
$(document).ready(function()
{
	$('#impCostoobra').formatCurrency();
	
	 $('#canSuperficie').blur(function()
            {
		 var s = '$'+$('#canSuperficie').val();
		 $('#canSuperficie').val(s);
		 var v = $('#canSuperficie').asNumber();
        var t = parseFloat(v).toFixed(2);
      //  var t = parseFloat($('#canSuperficie').val()).toFixed(2);
        $('#canSuperficie').prop("value", t);
                $('#canSuperficie').formatCurrency();
                $('#canSuperficie').val($('#canSuperficie').val().replace("$", ""));
            });
	 
	 $('#canSuperficie').keyup(function()
             {
		 var s = '$'+$('#canSuperficie').val();
		 $('#canSuperficie').val(s);
		 var elems = ($('#canSuperficie').asNumber()+'').split(".");
  		if(elems.length==1){
  			if(elems[0].length>9){
  				alert('La cantidad solo puede tener 9 enteros');
  				$('#canSuperficie').prop("value", elems[0].substring(0,9));
  				 $('#canSuperficie').val($('#canSuperficie').val().replace("$", ""));

  				return false;
  			}
  		}
  		
  		if(elems.length==2){
  			
      		if(elems[0].length>12){
      				alert('La cantidad solo puede tener 9 enteros');
      				$('#canSuperficie').prop("value", elems[0].substring(0,9)+ '.' + elems[1]);
      				 $('#canSuperficie').val($('#canSuperficie').val().replace("$", ""));

      				return false;
      		}
      		if(elems[1].length>2){
  				alert('La cantidad solo puede tener 2 decimales');
  				$('#canSuperficie').prop("value", elems[0]+ '.' + elems[1].substring(0,2));
  				 $('#canSuperficie').val($('#canSuperficie').val().replace("$", ""));

  				return false;
  			}
  		}
  		 $('#canSuperficie').val($('#canSuperficie').val().replace("$", ""));
		
         		
             });
	 
	 $('#impCostoobra').keyup(function()
             {
         		var elems = ($('#impCostoobra').asNumber()+'').split(".");
         		if(elems.length==1){
         			if(elems[0].length>12){
         				alert('La cantidad solo puede tener 12 enteros');
         				$('#impCostoobra').prop("value", elems[0].substring(0,12));
         				return false;
         			}
         		}
         		
         		if(elems.length==2){
         			
             		if(elems[0].length>12){
             				alert('La cantidad solo puede tener 9 enteros');
             				$('#impCostoobra').prop("value", elems[0].substring(0,12)+ '.' + elems[1]);
             				$('#impCostoobra').formatCurrency();
             				return false;
             		}
             		if(elems[1].length>2){
         				alert('La cantidad solo puede tener 2 decimales');
         				$('#impCostoobra').prop("value", elems[0]+ '.' + elems[1].substring(0,2));
         				$('#impCostoobra').formatCurrency();
         				return false;
         			}
         		}
         		
         		
             });

	 
	 $('#impCostoobra').blur(function()
             {
		 var v = $('#impCostoobra').asNumber();
         var t = parseFloat(v).toFixed(2);
         $('#impCostoobra').prop("value", t);
                 $('#impCostoobra').formatCurrency();
             });
	 
	$( "form#promocionExhortoConstFormRegPromocion #fechaNotificacion,form#promocionExhortoConstFormRegPromocion #fechaOficio,form#promocionExhortoConstFormExhortoCons #fechaEstimIncio2,form#promocionExhortoConstFormExhortoCons #fechaEstTerm2" ).datepicker( { dateFormat: 'dd-mm-yy' });
	$( "form#obraRegistradaForm #fechaEstimIncio2,form#obraRegistradaForm #fechaEstTerm2" ).datepicker( { dateFormat: 'dd-mm-yy' });

	$("form#promoverConstruccionForm #fechaOficio").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onSelect: function(dateText, inst) { 
			jsValidarFecOficioPromonEX();
	    }
	});
	
	$("form#promocionExhortoConstForm #fechaEstimIncio").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onSelect: function(dateText, inst) { 
			jsValidarFecOficioPromo();
			jsValidaFechasLimite();
	    }
	});
	$("form#promocionExhortoConstForm #fechaEstTerm").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onSelect: function(dateText, inst) { 
			jsValidarFecOficioPromo();
			jsValidaFechasLimite();
	    }
	});
	

		$('form#promoverConstruccionForm #fechaOficio,form#promocionExhortoConstForm #fechaEstimIncio,form#promocionExhortoConstForm #fechaEstTerm').datepicker('option', 'maxDate', getFechaServidor());
		$('form#promoverConstruccionForm #fechaOficio').datepicker('option', 'minDate', getFechaServidorMenos45Dias());
		
		$('form#promocionExhortoConstForm #fechaEstimIncio,form#promocionExhortoConstForm #fechaEstTerm').datepicker('option', 'beforeShowDay', null);
		

	oDgPromocionExhorto = $(idPromocionExhorto).dataTable({
		 bJQueryUI : true,
		 bFilter : false,
		 bInfo:true,
		 bSort: true,
		 "bPaginate": true,
		 "bAutoWidth" : false,
		 "bServerSide" :	true,
		 "iDeferLoading": 0,
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
			 var fechaEstTerm = $("form#promocionExhortoConstForm #fechaEstTerm").val(); 
			 var nuFoliodeteccion = $("form#promocionExhortoConstForm #nuFoliodeteccion").val(); 
			 var regPatron = $("form#promocionExhortoConstForm #regPatron").val(); 
			
			 
			 if(!flagRecarga){
					flagRecarga=true;
					 oForm.regPatron=" ";
				}
			 wrapper.oForm = oForm;
			 $.postJSON(sSource, wrapper, function(data) {										
				 fnCallback(data);
//				 if(data.aaData!=null){
//					 if(data.aaData.length == 0 && fechaInicial!="" && fechaEstTerm!=""){
//						 $('#labelError').html('<label style="color: red;">No se encontraron resultados de su b&uacute;squeda, por favor realice nueva b&uacute;squeda</label>');
//					 }else if(data.aaData.length == 0 && nuFoliodeteccion!=""){
//						 $('#labelError').html('<label style="color: red;">No se encontraron resultados de su b&uacute;squeda, por favor realice nueva b&uacute;squeda</label>'); 
//					 }else if(data.aaData.length == 0 && regPatron!=""){
//						 $('#labelError').html('<label style="color: red;">No se encontraron resultados de su b&uacute;squeda, por favor realice nueva b&uacute;squeda</label>'); 
//					 }else{
//						 $('#labelError').html('');
//					 }
//				 }
			 }).complete(function(){
				desbloquear();
			 }).error(function(datas){ 
					validarSesionExpirada(datas);				 
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
				
				var deteccion = $("#promocionExhortoConstFormExhortoCons").serializeObject(true);
				deteccion.canSuperficie=quitaFormat($("#canSuperficie").val());
				deteccion.impCostoobra=$("#impCostoobra").asNumber();
				
				delete deteccion.regPatronalDetecc; 
				delete deteccion.razonSocialDetecc; 
				
				deteccion.razonSocial=$("#razonSocialDetecc").val();
				oDgPromocionExhortoCons.dialog('close');
				$.postJSON("ExhortoConst/promueve.do", deteccion, function(data) {
					$("form#promocionExhortoConstFormRegPromocion #cveDeteccion").val(data.cveDeteccion);
					$("form#promocionExhortoConstFormRegPromocion #cveTipocorr").val(data.cveTipocorr);
					$("form#promocionExhortoConstFormRegPromocion #cveFkPatron").val(data.cveFkPatron);
					$("form#promocionExhortoConstFormRegPromocion #idTipo").val(data.idTipo);
					$("form#promocionExhortoConstFormRegPromocion #idOrigen").val(data.idOrigen);
					oDgPromocionExhortoCons.dialog('close');
					oDgRegistroPromocion.dialog('open');
					limpiarFormulario("#promocionExhortoConstFormExhortoCons");					
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
		title: "Generar Nuevo Folio de Construcci&oacute;n",
		beforeClose :function(){
			limpiarFormaEx();
		},
		buttons: {
			"Guardar Datos": function() {
				var respuesta = jsValidaGuardar();
				if(respuesta == true){
					oDgConfirmar.dialog('open');
				}
			}, 
			"Promover": function() {
			
				if($("#regPatronalDetecc").val()!=""){
					var valores=validarRegistroPatronalExhorto();	
					if(!valores){
						return;
					}
				}
				
				if($("form#obraRegistradaForm #tipClaseobra").val() != '' && $("form#obraRegistradaForm #cvePkTipObra").val() != '-1'){//&& $("input#razonSocialDetecc").val()!=''
					$("form#promoverConstruccionForm #cveDeteccion").val($("form#obraRegistradaForm #cveDeteccion").val());
					jsLlenaCriteriosPromover();
					$('form#promoverConstruccionForm #labelNuOficio').html('');
					oDgPromover.dialog('open');
				}else{
					if($("input#razonSocialDetecc").val() == ''){
						if($("input#regPatronalDetecc").val() == ''){
							resp = true;
						}else{
							$("form#obraRegistradaForm #labelRegistroPatronal").html('<label class="etiquetaError">Se debe validar el registro patronal</label>');
							resp = false;
						}
						
					}
					alert('Capturar y guardar campos requeridos');
				}
				
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
					if(jsValidaNuOficio() == false){
						oDgPromover.dialog('close');
						$(this).dialog("close"); 
						jsPromoverDeteccion();
//						oDgPromover.dialog('open');
					}else{
						$('form#promoverConstruccionForm #labelNuOficio').html('<label style="color: red;">El n&uacute;mero de oficio capturado ya existe</label>');
					}					
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
				deteccion.canSuperficie=quitaFormat($("#canSuperficie").val());
				deteccion.impCostoobra=$("#impCostoobra").asNumber();
				
				delete deteccion.regPatronalDetecc; 
				delete deteccion.razonSocialDetecc; 
				deteccion.razonSocial=$("#razonSocialDetecc").val();
				deteccion.cveFkPatron=regPatronalExCons;
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
			limpiarFormulario("#promocionExhortoConstFormRegPromocion");
			var crtPromocion = $("#promocionExhortoConstFormRegPromocion").toObject({mode:'first'});
			$.postJSON("ExhortoConst/removerDomicilioSession.do", crtPromocion, function(data) {					
			}).error(function(data){ 
				validarSesionExpirada(data);
			}).complete(function(){
				//Instrucciones para el complete													
			});				    
		},
		open:function(event, ui)
		{					
			var promocion = $("#promocionExhortoConstFormRegPromocion").serializeObject(true);
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
					var promocion = $("#promocionExhortoConstFormRegPromocion").serializeObject(true);
					$.postJSON("ExhortoConst/agregaPromocion.do", promocion, function(data) {
						alert("La promoci\u00f3n ha sido agregada con el Folio: "+data.nuFoliopromocion);
						oDgPromocionExhorto.fnDraw();
						oDgRegistroPromocion.dialog("close");							
						limpiarFormulario("#promocionExhortoConstFormRegPromocion");						
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
	
	validaDatos = $("#promocionExhortoConstFormRegPromocion").validate({
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
		resetDisplayStart(oDgPromocionExhorto);
		oDgPromocionExhorto.fnDraw();	
		bloquear();
	}
}

function muestraDiv(){
	$("#btnVisualizar").show();
}

/**
 * Gerardo Salazar  
 * Funcion para recuperar el valor del combo de tipo de obra 
 * en la pantalla generar nuevo folio de construccion
 */
function recargaComboTipoObraCon(parmCveTipoObra){
	if(parmCveTipoObra!=null && parmCveTipoObra!=""){
		$('form#obraRegistradaForm #cvePkTipObra').val(parmCveTipoObra);
	}
}



function limpiaCampoRazonSocial(){	
	
	$('form#obraRegistradaForm #razonSocialDetecc').val('');
	$('form#obraRegistradaForm #razonSocialDetecc').val($('form#obraRegistradaForm #razonSocialDetecc').val().toUpperCase());
}
function mostrar(){
	$("#labelRazonSocial").text("");
	$("#labelRegPatronal").text("");
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
				$('#labelRegistroPatronal').html('');
				$('form#obraRegistradaForm #cveDeteccion').val(data.cveDeteccion);	
				$("#labelFolioDeteccion").html('<label> ' + data.nuFoliodeteccion + ' </label>');
				if(data.nuReportectrlobra != null){
					$("#labelNumReporteObra").html('<label> ' + data.nuReportectrlobra + ' </label>');
				}
				$("#labelFechaDeteccion").html('<label> ' + data.fechaDeteccion + ' </label>');
				$("#fechaEstimIncio2").datepicker('option', 'maxDate',data.fechaDeteccion);
				$("#labelRazonSocial").text(data.nomRazonsocial);
				
				
				// mandamos la fecha de deteccion al jsp de promoverConstruccion.jsp
				$("form#promoverConstruccionForm #fecDeteccionPromocionEX").val(data.fechaDeteccion);
				// Llenamos el registro patronal y razon social si lo trae
				if(data.cveFkPatron != null){
					$("form#obraRegistradaForm #labelRegPatronal").html('<label> ' + data.regPatron + ' </label>');
					$("form#obraRegistradaForm #labelRazonSocial").html('<label> ' + data.razonSocial + ' </label>');
				}
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
				$('form#obraRegistradaForm #tipClaseobra').trigger('change');
				$('form#obraRegistradaForm #canSuperficie').val(data.canSuperficie);
				   var t = parseFloat($('form#obraRegistradaForm #canSuperficie').val()).toFixed(2);
			         $('#canSuperficie').prop("value", t);
			                 $('#canSuperficie').toNumber();
			       
				setTimeout(function() { recargaComboTipoObraCon(data.cvePkTipObra); }, 1000);
				$('form#obraRegistradaForm #cvePkFaseConst').val(data.cvePkFaseConst);
				$('form#obraRegistradaForm #impCostoobra').val(data.impCostoobra);
				
				
				
				$('form#obraRegistradaForm #impCostoobra').formatCurrency();
				$('form#obraRegistradaForm #porAvanceobraEst').val(data.porAvanceobraEst);
				$('form#obraRegistradaForm #cveFkZona').val(data.cveFkZona);
				$('form#obraRegistradaForm #numTrabajdores').val(data.numTrabajdores);
				$('form#obraRegistradaForm #desDependenciapub').val(data.desDependenciapub);
				$('form#obraRegistradaForm #desDepcontratante').val(data.desDepcontratante);
				$('form#obraRegistradaForm #fechaEstimIncio2').val(data.fechaEstimIncio2);
				
				
				//$("form#obraRegistradaForm #fechaEstimIncio2").datepicker('option','minDate',data.fechaDeteccion);
				$("form#obraRegistradaForm #fechaEstTerm2").datepicker('option','minDate',data.fechaDeteccion);
				$("form#obraRegistradaForm #fechaEstimIncio2").val(data.fechaEstimIncio2);
				
				$('form#obraRegistradaForm #fechaEstTerm2').val(data.fechaEstTerm2);
				$("form#obraRegistradaForm input#regPatronalDetecc").val(data.regPatron);
				$("form#obraRegistradaForm input#razonSocialDetecc").val(data.razonSocial);
			
				if(data.cveTipocorr == 8){  // Fuente externa
					$('form#obraRegistradaForm #tdFase1').hide();
					$('form#obraRegistradaForm #tdFase2').hide();
				}else{
					$('form#obraRegistradaForm #tdFase1').show();
					$('form#obraRegistradaForm #tdFase2').show();
				}
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
	
	var func="refrescar('promocionExhortoConstFormRegPromocion')";
	var resultado = openWindowregistraDomicilioInegi(context,'promocion/ExhortoConst/promocionDomGeografico.do',null,func);	
		
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
	if(!longitudMandatoria($("form#promocionExhortoConstFormExhortoCons #regPatron").val(),10,"Registro Patronal")) return false;
	oDgPromocionExhortoCons.dialog('close');
	bloquear();	
	var deteccion = $("#promocionExhortoConstFormExhortoCons").serializeObject(true);	
	$.postJSON("ExhortoConst/validaRegPatron.do", deteccion, function(data) {
		if(data.cveFkPatron==null){
			alert("El registro Patronal es invalido");
			$("form#promocionExhortoConstFormExhortoCons #nomRazonsocial").val("");
			$("form#promocionExhortoConstFormExhortoCons #txRfcpatron").val("");
			$("form#promocionExhortoConstFormExhortoCons #txCurppatron").val("");
			$("form#promocionExhortoConstFormExhortoCons #cveFkPatron").val("");
			$("form#promocionExhortoConstFormExhortoCons #actividad").val("");
		}else{
			alert("El registro Patronal es valido");
			$("form#promocionExhortoConstFormExhortoCons #nomRazonsocial").val(data.nomRazonsocial);
			$("form#promocionExhortoConstFormExhortoCons #txRfcpatron").val(data.txRfcpatron);
			$("form#promocionExhortoConstFormExhortoCons #txCurppatron").val(data.txCurppatron);
			$("form#promocionExhortoConstFormExhortoCons #cveFkPatron").val(data.cveFkPatron);
			$("form#promocionExhortoConstFormExhortoCons #actividad").val(data.actividad);
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
	var fechaEstTerm = $("#fechaEstTerm").val();
	if(fechaEstTerm != '' && fecha != ''){
		var anioI = fechaEstTerm.substring(6,10);
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
	var fechaEstimIncio = $("#fechaEstimIncio").val();
	if(fechaEstimIncio != '' && fecha != ''){
		var anioI = fechaEstimIncio.substring(6,10);
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
	}else if($("#fechaEstimIncio").val() == '' || $("#fechaEstTerm").val() == ''){		
		$("#labelFechas").html('<label style="color: red;">Campo obligatorio</label>');
	}else{
		resp = true;
	}
	
	return resp;
}

function jsValidafechaEstTerm(){
	
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
	$("form#promoverConstruccionForm input#regPatron").val($('input#regPatronalDetecc').val());
	$("form#promoverConstruccionForm input#razonSocial").val($('input#razonSocialDetecc').val());
	var deteccion = $("#promoverConstruccionForm").serializeObject(true);
	$.postJSON("ExhortoConst/promoverConstruccion.do", deteccion, function(data) {
		alert("La detecci\u00f3n se ha promovido con el n\u00famero de folio: " + data.nuFoliopromocion);
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
	if($("input#razonSocialDetecc").val() == ''){
		if($("input#regPatronalDetecc").val() == ''){
			resp = true;
		}else{
			$("form#promoverConstruccionForm #labelRegistroPatronal").html('<label class="etiquetaError">Se debe validar el registro patronal</label>');
			resp = false;
		}
		
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
    patron = /[1234567890abcdefghijklmn�opqrstuvwxyzABCDEFGHIJKLMN�OPQRSTUVWXYZ]/;
    te = String.fromCharCode(tecla);
    
    return patron.test(te);
} 


/**
 * Funcion que valida que la fecha inicial no sea mayor a la fecha final
 * @author Enrique Duran Jimenez
 * @since 30/07/2012
 */
function jsValidarFecOficioPromo(){
	$("form#promocionExhortoConstForm #labelFechas").html('');	
	 var fecIni = $("form#promocionExhortoConstForm #fechaEstimIncio").val();
	 var fecFinal = $("form#promocionExhortoConstForm #fechaEstTerm").val();
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecIni,fecFinal)){
			 $("form#promocionExhortoConstForm #fechaEstTerm").val(fecFinal);
			 $("form#promocionExhortoConstForm #labelFechas").html('');
		 }else{
			 $("form#promocionExhortoConstForm #fechaEstTerm").val('');
			 $("form#promocionExhortoConstForm #labelFechas").html('<label class="etiquetaError">La Fecha De no puede ser mayor a la Fecha A</label>');
		 }
	 }
}

/**
 * Funcion que valida que solo se elija un rango de un a�o
 * @author Enrique Duran Jimenez
 * @since 30/07/2012
 */
function jsValidaFechasLimite(){
	var ini = $("form#promocionExhortoConstForm #fechaEstimIncio").val();
	var fin = $("form#promocionExhortoConstForm #fechaEstTerm").val();
	var resp = true;
	if(ini != '' && fin != ''){
		
		var array_fechaIni = ini.split("-"); 
		var array_fechaFin = fin.split("-"); 
		
		var anioIni = parseInt(array_fechaIni[2]);
		var anioFin = parseInt(array_fechaFin[2]);		
		
		if(anioIni < anioFin){
			resp = false;
		}else if(anioIni == anioFin){
			resp = true;
		}
	}
	if(resp == false){
		$("form#promocionExhortoConstForm #fechaEstimIncio").val("");
		$("form#promocionExhortoConstForm #fechaEstTerm").val("");
		$("form#promocionExhortoConstForm #labelFechas").html('<label class="etiquetaError" >El rango de fechas permitido es maximo un a\u00f1o</label>');
	}
}

/**
 * @author Enrique Duran Jimenez
 * @since 30/08/2012
 * Funcion que valida la fecha de oficio de Promoci�n vs la fecha de detecci�n
 */
function jsValidarFecOficioPromonEX(){
	$("form#promoverConstruccionForm #labelFecOficio").html('');	
	$("form#promoverConstruccionForm #labelNuOficio").html('');	
	 var fecIni = $("form#promoverConstruccionForm #fecDeteccionPromocionEX").val();
	 var fecFinal = $("form#promoverConstruccionForm #fechaOficio").val();
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecIni,fecFinal)){
			 $("form#promoverConstruccionForm #fechaOficio").val(fecFinal);
			 $("form#promoverConstruccionForm #labelFecOficio").html('');
		 }else{
			 $("form#promoverConstruccionForm #fechaOficio").val('');
			 $("form#promoverConstruccionForm #labelFecOficio").html('<label class="etiquetaError">La Fecha Oficio Promoci\u00f3n no puede ser menor a la Fecha de Detecci\u00f3n </label>');
		 }
	 }
}

/**
 * @Author Enrique Duran Jimenez
 * @since  29/08/2012
 * Funcion que valida que no exista el Numero de Folio ingresado
 */
function jsValidaNuOficio(){
	var resp = false;
	var valor = $("form#promoverConstruccionForm #nuOficiopro").val();
//	$.ajax({
//        url: getAppContextParaJS()+"/promocion/ExhortoConst/validaFolio.do",
//        async:false,
//        contentType: "application/json",
//        data: "nuOficio="+valor,
//        error: function(objeto, quepaso, otroobj){
//        	errorOcurrido = otroobj;
//        	resp = false;
//        },
//        success: function(dato){
//        	resp = dato;
//        },
//        type: "GET"
//	});
	
	
	var sVarSeg = '{"nuOficio":"'+valor+'"}';
	var clase = jQuery.parseJSON(sVarSeg);
	resp=false;
	$.postJSON_Sync(getAppContextParaJS()+"/promocion/ExhortoConst/validaFolio.do", clase, function(data) {
		resp = data;		
	});
	
	return resp;
}

function limpiarFormaEx(){
	$("#cvePkTipObra").val('-1');
	$("#cvePkFaseConst").val('-1');
	$("#cveFkZona").val('-1');
}

function validarFechaFinEx(){
	
	 var fecIni = $("form#obraRegistradaForm #fechaEstimIncio2").val();
	 var fecFinal = $("form#obraRegistradaForm #fechaEstTerm2").val();
	 
	 if(fecIni==''){
		 alert("Ingrese primero la fecha inicial");
		 $("form#obraRegistradaForm #fechaEstTerm2").val('');
	 }
	 
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecIni,fecFinal)){
			 $("form#obraRegistradaForm #fechaEstTerm2").val(fecFinal);
		 }else{
			 $("form#obraRegistradaForm #fechaEstTerm2").val('');
			 alert('La fecha final no puede ser menor a la fecha inicial');
		 }
	 }
}

/**
 * Funcion que limpia los campos de la busqueda en la pantalla
 * alta de Promocion de Exhorto de Construccion
 * 
 * @author Gerardo Salazar Vega 
 */
function limpiarAltaExConstruccion(){
	$("form#promocionExhortoConstForm #fechaEstimIncio").val("");
	$("form#promocionExhortoConstForm #fechaEstTerm").val("");
	$("form#promocionExhortoConstForm #nuFoliodeteccion").val("");
	$("form#promocionExhortoConstForm #regPatron").val("");
	flagRecarga=false;
	oDgPromocionExhorto.fnClearTable();
	} 

function quitaFormat(valor){	
	var currentValue = valor.replace(/[\,]+/gi,"");
	return Number(currentValue);	
}

function validarRegistroPatronalExhorto(){
	var flagEstado=false;
	var rpSatica = $("input#regPatronalDetecc").val();
	var jsMensajeError="El registro Patronal es inv&aacute;lido";
	
	//$(jsRegPatronSaticaLabel).html("");
	//if (rpSatica==""){
		//alert ("Proporcione el registro patronal");
		//return;
//	}
	
	if(!longitudMandatoria($("input#regPatronalDetecc").val(),10,"Registro Patronal")) return false;
	
	bloquear();

	$.postJSON_Sync("ExhortoConst/validaPatron.do", rpSatica, function(data) {
		if(data == null || data.cveRespuestaWS == JSERROR_WS) {
			//if (data!=null && data.descRespuestaWS.length > 0) jsMensajeError = data.descRespuestaWS; 
			$('#labelRegistroPatronal').html(jsMensajeError);
			$("input#razonSocialDetecc").val('');
			//eliminaDatosRPMain();
			flagEstado=false;
			//return false;
		} else if(data != null && data.razonSocial != null){
			$("input#razonSocialDetecc").val(data.razonSocial);
			// asigna la clave del registro patronal al tab de seguimiento para guardarlo
			//jsRPSaticaMainValido = true;
			$('#labelRegistroPatronal').html('');
			regPatronalExCons=data.cvePK;
			flagEstado=true;
			//return true;
			//$(jsClaveFkPatronSaticaMain).val(data.cvePK)
			//$(jsClaveFkPatronSaticaSeg).val($(jsClaveFkPatronSaticaMain).val());
			//$(jsRazonSocialSaticaMain).html(data.razonSocial);
			//$(jsRegistroPatronalTabPagos).val(data.registroPatronal);
		}
		desbloquear();
	}).error(function(data){ 
		desbloquear();
		validarSesionExpirada(data);			
	}).complete(function(){		
		desbloquear();												
	});
	return flagEstado;
}



function limpiaCampo(){	
	$('form#obraRegistradaForm #razonSocialDetecc').val("");
}