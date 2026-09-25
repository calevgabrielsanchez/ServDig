
var idDisponiblesPromocion 	= "#dtDisponiblesPromocion";
var idDgPromocion			= "#dgPromocion";
var idPromocionConsultaDet 	= "#dgObraRegistradaSaticA";
var idRegistroPromocion		= "#dgPromocionRegistro"
var idConfirmarSaticA       = "#dgConfirmarSaticA"; 
var idDgPromover            = "#dgPromoverSaticA";
var flagBloquear =false;
var oDtDisponiblesPromocion;
var oDgPromocion;
var oDgPromocionConsultaDet;
var oDgRegistroPromocion;
var validaCapturaVal;
var validaDatos;
var validaForm;
var oDgConfirmar;
var oDgPromover;
var cvefkPatron;
var fechaDeteccion;
var jsContextoPromocion= getAppContextParaJS() + "/promocion/";
$(document).ready(function() {

	
	 $('#canSuperficie').blur(function(){		 
		 $('#canSuperficie').toNumber();
		 if($('#canSuperficie').val()!=''){
			  $('#canSuperficie').prop("value", parseFloat($('#canSuperficie').val()).toFixed(2));
		 }
        $('#canSuperficie').formatCurrency();
        $('#canSuperficie').val($('#canSuperficie').val().replace("$", ""));       
	 });
	 
	 $('#impCostoobra').blur(function()
             {
		 var v = $('#impCostoobra').asNumber();
         var t = parseFloat(v).toFixed(2);
         $('#impCostoobra').prop("value", t);
                 $('#impCostoobra').formatCurrency();
             });
	
	
	 // Fecha de Inicio y Termino
	 $( "#fechaIncial, #fechaFinal, form#promocionFormConsultaDet #fecFechainicioEst, form#promocionFormConsultaDet #fecFechaterminoEst, form#promocionFormRegPromocion #fechaOficio, form#promocionFormRegPromocion #fechaNotificacion, form#promocionFormConsultaDet #fechaEstimIncio, form#promocionFormConsultaDet #fechaEstTerm" ).datepicker( { dateFormat: 'dd-mm-yy' });	 	

	 // Fechas de datosCompletosDeteccion.jsp
	 $( "form#promocionFormConsultaDet #fechaEstimIncio2, form#promocionFormConsultaDet #fechaEstTerm2" ).datepicker( { dateFormat: 'dd-mm-yy' });
	 
		$("form#promoverSatiAForm #fechaOficioPromocion").datepicker( { 
			dateFormat: 'dd-mm-yy',
			onSelect: function() { 
				$('form#promoverSatiAForm #labelFecOficio').html('');
				jsValidarFecOficioPromoSaticA();
		    }
		});
		
			$('form#promoverSatiAForm #fechaOficioPromocion,form#promocionForm #fechaIncial,form#promocionForm #fechaFinal').datepicker('option', 'maxDate', getFechaServidor());
			$('form#promoverSatiAForm #fechaOficioPromocion').datepicker('option', 'minDate', getFechaServidorMenos45Dias());
			$('form#promocionForm #fechaIncial,form#promocionForm #fechaFinal').datepicker('option', 'beforeShowDay', null);
			
	 
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
		 }, {
			 
			 "sTitle" : "Calle",
			 "mDataProp" : "domCalle",
			 "sClass": "dtCenterClassColumn"
		 },{
			
			 "sTitle" : "Colonia",
			 "mDataProp" : "refColonia",
			 "sClass": "dtCenterClassColumn"
		 },{
			
			 "sTitle" : "Num Interior",
			 "mDataProp" : "numNroint",
			 "sClass": "dtCenterClassColumn"
		 },{
			
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
				 		$('#labelError').html('<label style="color: red;">No se encontraron resultados, por favor realice nueva b&uacute;squeda</label>');
				 	}else{
				 		$('#labelError').html('');
				 	}
				 }
				}).error(function(data){ 
					validarSesionExpirada(data);
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
			title: "Generar Nuevo Folio SATIC A",
			beforeClose :function(event,ui){		    				    
				jsLimpiarCombos();    
			},
			buttons: {
				"Guardar Datos": function() {
					var respuesta = jsValidaGuardar();
					if(respuesta == true){
						oDgConfirmar.dialog('open');
					}
				}, 
				"Promover": function() {
					if(jsValidaGuardar() == true){
						$("form#promoverSatiAForm #cveDeteccion").val($("form#promocionFormConsultaDet #cveDeteccion").val());
						jsLimpiaFormaPromover();
						jsLlenaCriteriosPromover();
						oDgPromover.dialog('open');
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
						if(jsValidarPromover() == true && !flagBloquear){
							flagBloquear=true;
							var resp = jsValidaNuFolio();
							if(resp == false){
								$(this).dialog("close"); 
								oDgPromover.dialog('close');
								jsPromoverDeteccion();								
							}else if(resp == true){
								$('form#promoverSatiAForm #labelNuOficio').html('<label style="color: red;">El n&uacute;mero de oficio capturado ya existe</label>');
							}
						}
				}, 
				"Salir"   : function() {
					jsLimpiarFormaPromover();
					flagBloquear=false;
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
					var v = $('form#promocionFormConsultaDet #impCostoobra').asNumber();
			         var t = parseFloat(v).toFixed(2);
			         $('#impCostoobra').prop("value", t);
			         $('#canSuperficie').toNumber();
			           
			         
			         
					var deteccion = $("#promocionFormConsultaDet").serializeObject(true);
					
					delete deteccion.regPatronalDetecc; 
					delete deteccion.razonSocialDetecc; 
					
					deteccion.razonSocial=$("#razonSocialDetecc").val();
					deteccion.valRegPat=$("#regPatronalDetecc").val();
					if($("#regPatronalDetecc").val()!=""){
						deteccion.cveFkPatron=cvefkPatron;
					}
					
					
					$.postJSON("promocionDet/promueve.do", deteccion, function(data) {
						alert('La detecci\u00f3n se guardo exitosamente');
						$(this).dialog("close");
						 $('form#promocionFormConsultaDet #canSuperficie').trigger('blur');
					}).error(function(data){ 
						validarSesionExpirada(data);
					}).complete(function(){
						desbloquear();
						var v = $('#impCostoobra').asNumber();
				         var t = parseFloat(v).toFixed(2);
				         $('#impCostoobra').prop("value", t);
				         $('#impCostoobra').formatCurrency();
				        
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
					jsLimpiarCombos();
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
//	$('form#promocionFormConsultaDet #regPatronalDetecc').on('change',function(){									  
//		$('#razonSocialDetecc').val("");				
//	});
});

/**
 * Gerardo Salazar  
 * Funcion para recuperar el valor del combo de tipo de obra 
 */
function recargaComboTipoObra(parmCveTipoObra){
	if(parmCveTipoObra!=null && parmCveTipoObra!=""){
		$('form#promocionFormConsultaDet #cvePkTipObra').val(parmCveTipoObra);
	}
}	

function mostrar(){
	flagBloquear=false;
	var radios = document.getElementsByName("radio");
	for (i=0;i<radios.length;i++){
		if(radios[i].checked){
	
			var idDeteccion = radios[i].value;
			var sDeteccion = '{"cveDeteccion":'+idDeteccion+'}';
			var deteccion = jQuery.parseJSON(sDeteccion);
			jsLimpiarForma();
			// Buscamos el elemento
			bloquear();
			$.postJSON("promocionDet/mostrar.do", deteccion, function(data) {
				
				if(data.nomRazonsocial!=null && data.nomRazonsocial!=undefined && data.nomRazonsocial!=""){
					$("#razonSocialDetecc").val(data.nomRazonsocial);	
				}
				
				
				$('form#promocionFormConsultaDet #cveDeteccion').val(data.cveDeteccion);	
				$("#labelFolioDeteccion").html('<label> ' + data.nuFoliodeteccion + ' </label>');
				if(data.nuReportectrlobra != null){
					$("#labelNumReporteObra").html('<label> ' + data.nuReportectrlobra + ' </label>');
				}
				$("#labelFechaDeteccion").html('<label> ' + data.fechaDeteccion + ' </label>');
				// Manda al JSP de promover la fecha de deteccion
				$("form#promoverSatiAForm #fechaDeteccionPromover").val(data.fechaDeteccion);
				
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
							
				
				$('form#promocionFormConsultaDet #canSuperficie').toNumber();
				 if($('form#promocionFormConsultaDet #canSuperficie').val()!=''){
					  $('form#promocionFormConsultaDet #canSuperficie').prop("value", parseFloat($('form#promocionFormConsultaDet #canSuperficie').val()).toFixed(2));
				 }
		        $('form#promocionFormConsultaDet #canSuperficie').formatCurrency();
		        $('form#promocionFormConsultaDet #canSuperficie').val($('form#promocionFormConsultaDet #canSuperficie').val().replace("$", ""));  
				
								
				
				
				$('#tipClaseobra').trigger('change');
				setTimeout(function() { recargaComboTipoObra(data.cvePkTipObra); }, 1000);		
				$('form#promocionFormConsultaDet #regPatronalDetecc').val(data.regPatron);
				
				if(!(data.regPatron==undefined || data.regPatron=="" || data.regPatron==null)){
					$('form#promocionFormConsultaDet #razonSocialDetecc').val(data.nomRazonsocial);
				}
				
				
				$('form#promocionFormConsultaDet #cvePkFaseConst').val(data.cvePkFaseConst);
				$('form#promocionFormConsultaDet #impCostoobra').val(data.impCostoobra);
				$('form#promocionFormConsultaDet #porAvanceobraEst').val(data.porAvanceobraEst);
				$('form#promocionFormConsultaDet #cveFkZona').val(data.cveFkZona);
				$('form#promocionFormConsultaDet #numTrabajdores').val(data.numTrabajdores);
				$('form#promocionFormConsultaDet #desDependenciapub').val(data.desDependenciapub);
				$('form#promocionFormConsultaDet #desDepcontratante').val(data.desDepcontratante);
				$('form#promocionFormConsultaDet #fechaEstimIncio2').val(data.fechaEstimIncio2);	
				fechaDeteccion=data.fechaDeteccion;
				//$('form#promocionFormConsultaDet #fechaEstimIncio2').datepicker('option', 'minDate', data.fechaDeteccion );
				$('form#promocionFormConsultaDet #fechaEstTerm2').datepicker('option', 'minDate', data.fechaDeteccion );
				$('form#promocionFormConsultaDet #fechaEstTerm2').val(data.fechaEstTerm2);
//				var v1 = $('#canSuperficie').asNumber();
//		         var t1 = parseFloat(v1).toFixed(2);
//		         $('#canSuperficie').prop("value", t1);
//		         $('#canSuperficie').toNumber();
		         
				 var v = $('#impCostoobra').asNumber();
		         var t = parseFloat(v).toFixed(2);
		         $('#impCostoobra').prop("value", t);
		         $('#impCostoobra').formatCurrency();
				
				$('form#promocionFormConsultaDet #fechaEstimIncio2').datepicker('option','beforeShowDay',null);
				$('form#promocionFormConsultaDet #fechaEstTerm2').datepicker('option','beforeShowDay',null);
				
				/**
				 * Enrique Duran JImenez  
				 * Cambio muestra los datos por fuente externa � 
				 * los datos por censo
				 * @Sinse 29/08/2012
				 */
				if(data.cveTipocorr == 8){  // Fuente externa
					$('form#promocionFormConsultaDet #trFase1SaticA').hide();
					$('form#promocionFormConsultaDet #trFase2SaticA').hide();
				}else{
					$('form#promocionFormConsultaDet #trFase1SaticA').show();
					$('form#promocionFormConsultaDet #trFase2SaticA').show();
				}
				
				//se agrega reg patronal y razon social
				
				if(data.regPatron!=null){
					$('form#promocionFormConsultaDet #regPatronalDetecc').val(data.regPatron);
					$('form#promocionFormConsultaDet #razonSocialDetecc').val(data.razonSocial);						
				}
				
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
	if($("form#promocionForm #nuFoliodeteccion").val() == ''){
		if(validaForma() == true){
			$("#promocionObrasDeteccion").show();
			resetDisplayStart(oDgPromocion);
			oDgPromocion.fnDraw();	
		}
	}else{
		$("#promocionObrasDeteccion").show();
		resetDisplayStart(oDgPromocion);
		oDgPromocion.fnDraw();	
	}
}

function validaForma(){
	$("#labelPeriodo").html('');
	$("form#promocionForm #labelRegPatron").html('');
	var respuesta = false;
	if($("form#promocionForm #regPatron").val() == ''){
		if($("form#promocionForm #fechaIncial").val() == '' || $("form#promocionForm #fechaFinal").val() == ''){
			$("#labelPeriodo").html('<label class="etiquetaError">Campo Requerido</label>');
		}
	}
	
	if($("form#promocionForm #regPatron").val() != '' && $("form#promocionForm #regPatron").val().length < 10 ){
		$("form#promocionForm #labelRegPatron").html('<label class="etiquetaError">Capturar un Registro Patronal Valido</label>');
	}
	
	if( ($("form#promocionForm #fechaIncial").val() != '' && $("form#promocionForm #fechaFinal").val() != '') || ($("form#promocionForm #regPatron").val() != '' && $("form#promocionForm #regPatron").val().length == 10)){
		respuesta = true;
	}
	return respuesta;
}

function mostarDomGeo(context){
	var func="refrescar('promocionFormRegPromocion')";
	var resultado = openWindowregistraDomicilioInegi(context,'catalogo/promocionDet/promocionDomGeografico.do',null,func);	
		
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
	$("form#promocionFormConsultaDet #labelRegistroPatronal").html('');
	
	var resp = false;
	if($("form#promocionFormConsultaDet #tipClaseobra").val() != '' && $("form#promocionFormConsultaDet #cvePkTipObra").val() != '-1'){
		$("form#promocionFormConsultaDet #labeltipoObra").html('');
		$("form#promocionFormConsultaDet #labelClaseObra").html('');
		resp = true;
	}
	
	if($("form#promocionFormConsultaDet #tipClaseobra").val() == ''){
		$("form#promocionFormConsultaDet #labelClaseObra").html('<label class="etiquetaError">Campo Requerido</label>');
		resp = false;
	}
	if($("input#razonSocialDetecc").val() == ''){
		if($("input#regPatronalDetecc").val() == ''){
			resp = true;
		}else{
			$("form#promocionFormConsultaDet #labelRegistroPatronal").html('<label class="etiquetaError">Se debe validar el registro patronal</label>');
			resp = false;
		}
		
	}
	
	if($("form#promocionFormConsultaDet #cvePkTipObra").val() == '-1'){
		$("form#promocionFormConsultaDet #labeltipoObra").html('<label class="etiquetaError">Campo Requerido</label>');
		resp = false;
	}
	
	
		
	
	else if(fechaDeteccion!='' && $("form#promocionFormConsultaDet #fechaEstimIncio2").val()!='' && !jsValidaFechas($("form#promocionFormConsultaDet #fechaEstimIncio2").val(),fechaDeteccion)){
		resp=false;
		alert("La fecha estimada de inicio no puede ser mayor que la fecha de detecci\u00f3n");
	}
	
	
	var regPatr=$("#regPatronalDetecc").val();
	
	if(regPatr!=''){
		if(regPatr.length!=10){
			resp=false;
			alert("No se puede continuar debido a que el registro patronal no se encuentra a 10 posiciones");
		}
		
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
	
	if($("form#promoverSatiAForm #fechaOficioPromocion").val() == ''){
		$("form#promoverSatiAForm #labelFecOficio").html('<label class="etiquetaError">Campo Requerido</label>');
	}
	
	if($("form#promoverSatiAForm #selectCriterios").val() != '' &&
	   $("form#promoverSatiAForm #nuOficiopro").val() != '' &&
	   $("form#promoverSatiAForm #fechaOficioPromocion").val() != ''){
		resp = true;
	}
		
	return resp;	
}

function jsPromoverDeteccion(){
	
	bloquear();
	$("form#promoverSatiAForm input#regPatron").val($('input#regPatronalDetecc').val());
	var deteccion = $("#promoverSatiAForm").serializeObject(true);
	
	$.postJSON("promocionDet/promoverSaticA.do", deteccion, function(data) {
		alert("La detecci\u00f3n se ha promovido con el n\u00famero de folio: " + data.nuFoliopromocion);
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
	$('form#promoverSatiAForm #fechaOficioPromocion').val('');
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
    patron = /[1234567890abcdefghijklmn�opqrstuvwxyzABCDEFGHIJKLMN�OPQRSTUVWXYZ]/;
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

function jsValidarFecOficioPromoSaticA(){
	$("form#promoverSatiAForm #labelFecOficio").html('');	
	$("form#promoverSatiAForm #labelNuOficio").html('');	
	 var fecIni = $("form#promoverSatiAForm #fechaDeteccionPromover").val();
	 var fecFinal = $("form#promoverSatiAForm #fechaOficioPromocion").val();
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecIni,fecFinal)){
			 $("form#promoverSatiAForm #fechaOficioPromocion").val(fecFinal);
			 $("form#promoverSatiAForm #labelFecOficio").html('');
		 }else{
			 $("form#promoverSatiAForm #fechaOficioPromocion").val('');
			 $("form#promoverSatiAForm #labelFecOficio").html('<label class="etiquetaError">La Fecha Oficio Promoci\u00f3n no puede ser menor a la Fecha de Detecci\u00f3n </label>');
		 }
	 }
}

/**
 * Funcion que valida que la fecha inicial no sea mayor a la fecha final
 * @author Enrique Duran Jimenez
 * @since 27/07/2012
 */
function jsValidarFecOficioPromo(){
	$("form#promocionForm #labelPeriodo").html('');	
	 var fecIni = $("form#promocionForm #fechaIncial").val();
	 var fecFinal = $("form#promocionForm #fechaFinal").val();
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecIni,fecFinal)){
			 $("form#promocionForm #fechaFinal").val(fecFinal);
			 $("form#promocionForm #labelPeriodo").html('');
		 }else{
			 $("form#promocionForm #fechaFinal").val('');
			 $("form#promocionForm #labelPeriodo").html('<label class="etiquetaError">La Fecha De no puede ser mayor a la Fecha A</label>');
		 }
	 }
}

/**
 * Funcion que valida que solo se elija un rango de un a�o
 * @author Enrique Duran Jimenez
 * @since 27/07/2012
 */
function jsValidaFechasLimite(){
	var ini = $("form#promocionForm #fechaIncial").val();
	var fin = $("form#promocionForm #fechaFinal").val();
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
		$("form#promocionForm #fechaIncial").val("");
		$("form#promocionForm #fechaFinal").val("");
		$("form#promocionForm #labelPeriodo").html('<label class="etiquetaError" >El rango de fechas permitido es maximo un a\u00f1o</label>');
		oDgPromocion.fnDraw();
	}
}

/**
 * @Author Enrique Duran Jimenez
 * @since  29/08/2012
 * Funcion que limpia la forma de la busqueda inicial
 */
function limpiar(){
	$("form#promocionForm #fechaIncial").val("");
	$("form#promocionForm #fechaFinal").val("");
	$("form#promocionForm #nuFoliodeteccion").val("");
	$("form#promocionForm #regPatron").val("");
	oDgPromocion.fnDraw();
}

/**
 * @Author Enrique Duran Jimenez
 * @since  29/08/2012
 * Funcion que valida que no exista el Numero de Folio ingresado
 */
function jsValidaNuFolio(){
	var resp = false;
	var valor = $("form#promoverSatiAForm #nuOficiopro").val();
	$.ajax({
        url: getAppContextParaJS()+"/catalogo/promocionDet/validaFolio.do",
        async:false,
        contentType: "application/json",
        data: "nuOficio="+valor,
        error: function(objeto, quepaso, otroobj){
        	errorOcurrido = otroobj;
        	resp = false;
        },
        success: function(dato){
        	resp = dato;
        },
        type: "GET"
	});
	return resp;
}

function jsLimpiaFormaPromover(){
	$("form#promoverSatiAForm #labelcriterios").html('');
	$("form#promoverSatiAForm #labelNuOficio").html('');
	$("form#promoverSatiAForm #labelFecOficio").html('');
}

function jsLimpiarCombos(){
	
	$("form#promocionFormConsultaDet #cvePkTipObra").val('-1');
	$("form#promocionFormConsultaDet #cvePkFaseConst").val('-1');
	$("form#promocionFormConsultaDet #cveFkZona").val('-1');

}

function validaMaximo(texto, maximo, campo){
	var cantidad = $('#'+texto).asNumber();
	var valor = parseFloat(cantidad);
	var maximo = parseFloat(maximo);
	
	var n=''+maximo;
	var va=n.indexOf(".");
	if(valor>maximo){
		alert('El valor maximo permitido para ' + campo + ' es de '+ va+" enteros ");
		$('#'+texto).prop("value", "");
	}	
}


function validarRegistroPatronalSatica(){
	var rpSatica = $("input#regPatronalDetecc").val();
	var jsMensajeError="El registro Patronal es inv&aacute;lido";
	
	//$(jsRegPatronSaticaLabel).html("");
	//if (rpSatica==""){
		//alert ("Proporcione el registro patronal");
		//return;
//	}
	
	if(!longitudMandatoria($("input#regPatronalDetecc").val(),10,"Registro Patronal")) return false;
	
	bloquear();

	$.postJSON(jsContextoPromocion+"seguimiento/generico/validaPatron.do", rpSatica, function(data) {
		if(data == null || data.cveRespuestaWS == JSERROR_WS) {
			//if (data!=null && data.descRespuestaWS.length > 0) jsMensajeError = data.descRespuestaWS; 
			$('#labelRegistroPatronal').html(jsMensajeError);
			$("input#razonSocialDetecc").val('');
			//eliminaDatosRPMain();
			return false;
		} else if(data != null && data.razonSocial != null){
			$("input#razonSocialDetecc").val(data.razonSocial);
			// asigna la clave del registro patronal al tab de seguimiento para guardarlo
			//jsRPSaticaMainValido = true;
			cvefkPatron=data.cvePK;
			$('#labelRegistroPatronal').html('');
			return true;
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
}


function limpiaCampo(){
	
	$('form#promocionFormConsultaDet #razonSocialDetecc').val("");
}

