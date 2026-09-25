/**
 *
 * @author Oscar Beltran
 * @version 1.0.1
 */



var idIncidenciasDisponibles = "#dtIncidenciasDisponiblesPromocion";
var idPeriodosPresentados = "#dtPeriodosPresentadosPromocion";
var oDgIncidencias;
var oDgPeriodos;

//dialogo de confirmacion de estatus obra
var idDgConfirmarEstObra = "#dgConfirmarRegularizaObra";
var oDgConfirmarEstObra;

function iniciaDataTables(){

	var numeroDeRegistroDeObra=$("form#estatusObraTABForm #numeroDeRegistroDeObra").val();
	
	
	// DATA TABLE DE INCIDENCIAS
	   oDgIncidencias = $(idIncidenciasDisponibles).dataTable({
			 bJQueryUI : true,
			 bFilter : false,
			 bInfo:true,
			 bSort: false,
			 "bPaginate": true,
			 "bDestroy": true,
			 "bAutoWidth" : false,
			 "bServerSide" :	true,
			 "aoColumns" : [ {
				 "sWidth": "20%",
				 "sTitle" : "Incidencia",
				 "mDataProp" : "tipoIncidencia",
				 "sClass": "dtCenterClassColumn"
			 },{
				 "sWidth": "15%",
				 "sTitle" : "Fecha Inicial",
				 "mDataProp" : "fechaInicioIncidencia",
				 "sClass": "dtCenterClassColumn"
			 },{
				 "sWidth": "15%",
				 "sTitle" : "Fecha Final",
				 "mDataProp" : "fechaFinIncidencia",
				 "sClass": "dtCenterClassColumn"
			 }, {
				 "sWidth": "20%",
				 "sTitle" : "Fecha Presentaci&oacute;n",
				 "mDataProp" : "fechaPresentacionIncidencia",
				 "sClass": "dtCenterClassColumn"
			 }
			 ],"bProcessing" : true,
			 "sAjaxSource" : jsContextoPromocion + 'seguimiento/generico/paginarIncidencias.do',
			 "fnServerData" : function(sSource, aoData, fnCallback) {			

				 var wrapper = new Object();
				 wrapper.aoData = aoData;				
		
  			     var sNumeroDeRegistroDeObra =  '{"numeroDeRegistroDeObra":"'+ numeroDeRegistroDeObra +'"}';
				 var oForm = jQuery.parseJSON(sNumeroDeRegistroDeObra);
				
				  wrapper.oForm = oForm;

				 $.postJSON(sSource, wrapper, function(data) {						
					 fnCallback(data);							
					 
					}).error(function(data){ 
						validarSesionExpirada(data);
					}).complete(function(){
					 desbloquear();
				 });
			 }
		 });	 
	   
	   
	   
	// DATA TABLE DE PERIODOS PRESENTADOS
	   oDgPeriodos = $(idPeriodosPresentados).dataTable({
			 bJQueryUI : true,
			 bFilter : false,
			 bInfo:true,
			 bSort: false,
			 "bPaginate": true,
			 "bDestroy": true,
			 "bAutoWidth" : false,
			 "bServerSide" :	true,
			 "aoColumns" : [ {
				 "sWidth": "50%",
				 "sTitle" : "Periodo",
				 "mDataProp" : "numPeriodo",
				 "sClass": "dtCenterClassColumn"
			 }, {
				 "sWidth": "50%",
				 "sTitle" : "Fecha Presentaci&oacute;n",
				 "mDataProp" : "fechaPresentacion",
				 "sClass": "dtCenterClassColumn"
			 }
			 ],"bProcessing" : true,
			 "sAjaxSource" : jsContextoPromocion + 'seguimiento/generico/paginarPeriodosPresentados.do',
			 "fnServerData" : function(sSource, aoData, fnCallback) {				

				 var wrapper = new Object();
				 wrapper.aoData = aoData;				
		
			     var sNumeroDeRegistroDeObra =  '{"numeroDeRegistroDeObra":"'+ numeroDeRegistroDeObra +'"}';
				 var oForm = jQuery.parseJSON(sNumeroDeRegistroDeObra);
				
				  wrapper.oForm = oForm;

				 $.postJSON(sSource, wrapper, function(data) {						
					 fnCallback(data);							
					 
					}).error(function(data){ 
						validarSesionExpirada(data);
					}).complete(function(){
					 desbloquear();
				 });
			 }
		 });
		 

}


function initTabEstatusObraGenerico(esNuevoEstatusObra){
	
	 var numeroObra=  $("form#estatusObraTABForm #numeroDeRegistroDeObra").val();
	 
	 if(numeroObra.length==0){
	 //    $("form#estatusObraTABForm #numeroDeRegistroDeObra").val("200828010286"); // para probar
		 $("form#estatusObraTABForm #numeroDeRegistroDeObra").val("0"); // Valor por default
	 }
	 iniciaDataTables();
			$( "form#estatusObraTABForm #fecAtencionOficioEstObraGenerico").datepicker( { dateFormat: 'dd-mm-yy' });
	
	/*seccion para definir la fecha maxima del servidor y establecerle un limite maximo a las fechas
	 * maximas de los calendarios para las fechas siguientes , con el formato dd-MM-yyyy
	 */ 
	$.postJSON(getAppContextParaJS() + "/promocion/seguimiento/generico/obtenerFechaServidor.do", null,function(data) {
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(data){
			$("form#estatusObraTABForm #fecAtencionOficioEstObraGenerico").datepicker('option', 'maxDate', data.responseText);
	});

	/*seccion para definir la fecha minima del servidor y establecerle un limite minima a las fechas
	 * maximas de los calendarios para las fechas siguientes , con formato dd-MM-yyyy
	 */ 
	$.postJSON(getAppContextParaJS() + "/promocion/seguimiento/generico/obtenerFechaServidorMinima.do", null,function(data) {
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
		//alert(JSON.stringify(data, null, 4));
					$("form#estatusObraTABForm #fecAtencionOficioEstObraGenerico").datepicker('option', 'minDate', data.responseText);
	});
	
		
		
			//es la primera vez que se llega al tab de estatus
			if(esNuevoEstatusObra){
				$( "form#estatusObraTABForm #fecAtencionOficioEstObraGenerico").addClass("red");
				$( "form#estatusObraTABForm #cbxRegulaObraEstObraGen").prop('disabled','disabled');
				$( "form#estatusObraTABForm #cbxRegulaObraEstObraGen").removeAttr('checked');
				$( "form#estatusObraTABForm #btnGuardarEstObraGen").prop('disabled','disabled');
				$( "form#estatusObraTABForm #fecAtencionOficioEstObraGenerico").removeAttr('disabled');
				$( "form#estatusObraTABForm #fecAtencionOficioEstObraGenerico").val('');
				$("#spnFecAtnEstObra").hide();
				
			}else{ //es guardado parcial y el tab debe estar deshabilitado
				
				
			}
			
}


function jsValidaFecAtnEstObra(fecIni){

	if(jsValidaFecha(fecIni)){
		 $( "form#estatusObraTABForm #fecAtencionOficioEstObraGenerico").val(fecIni);
		 $("#labelfecAtencionOficioEstObraGenerico").html('');
		 if(jsValidaVsfecNotifEstObra(fecIni)){
			 $( "form#estatusObraTABForm #fecAtencionOficioEstObraGenerico").val(fecIni);
			 $("#labelfecAtencionOficioEstObraGenerico").html('');
			 $("#spnFecAtnEstObra").show();
			 $( "form#estatusObraTABForm #btnGuardarEstObraGen").removeAttr('disabled');
			 ejecutaPasoFechaAtencion();
			 
		 }else{
			 $( "form#estatusObraTABForm #fecAtencionOficioEstObraGenerico").val("");
			 $("#labelfecAtencionOficioEstObraGenerico").html('<label class="etiquetaError" >La fecha de Atenci&oacute;n del oficio no puede ser menor a la fecha de notificaci&oacute;n</label>');
		 }
	}else{
		 $( "form#estatusObraTABForm #fecAtencionOficioEstObraGenerico").val("");
		 $("#labelfecAtencionOficioEstObraGenerico").html('<label class="etiquetaError" >La fecha de Atenci&oacute;n del oficio no puede ser mayor al dia actual</label>');
	}
}


function jsValidaVsfecNotifEstObra(fecIni){
	var fechaNotificaSaticb = $("form#seguimientoSaticbTABForm #fechaNotificaSaticb").val();
//	alert("fechaNotificaSaticb: " + fechaNotificaSaticb + ", fecha Atención: " + fecIni);
	var resp = false;
	if(fecIni != '' && fechaNotificaSaticb != ''){
		if(jsValidaFechas(fechaNotificaSaticb,fecIni)){
			 resp = true;
		 }
	}
	return resp;
}


function ejecutaPasoFechaAtencion(){
	
	$( "form#estatusObraTABForm #cbxRegulaObraEstObraGen").removeAttr('disabled');
	$( "form#estatusObraTABForm #cbxRegulaObraEstObraGen").addClass("red");
//	setTabHabilitado("autAviDictamenGenericoTab_saticb");
}


function limpiaFechaAtencionOficio(){
	
	$("form#estatusObraTABForm #fecAtencionOficioEstObraGenerico").val("");
	$( "form#estatusObraTABForm #cbxRegulaObraEstObraGen").removeAttr('checked');
	$( "form#estatusObraTABForm #cbxRegulaObraEstObraGen").prop('disabled','disabled');
	$( "form#estatusObraTABForm #btnGuardarEstObraGen").prop('disabled','disabled');
	setTabDesHabilitado("autAviDictamenGenericoTab_saticb");
	setTabDesHabilitado("regularizarObraGenericoTAB_saticb");
	$( "form#estatusObraTABForm #cbxRegulaObraEstObraGen").removeClass("red");
	$("#spnFecAtnEstObra").hide();
}

function seleccionaRegulaObra(){

	var cbxRegulaObra = $("form#estatusObraTABForm #cbxRegulaObraEstObraGen").attr('checked');

//	if(cbxRegulaObra == 'checked'){
//		setTabHabilitado("regularizarObraGenericoTAB_saticb");	
//		setTabDesHabilitado("autAviDictamenGenericoTab_saticb");		
//	}else{
//		setTabDesHabilitado("regularizarObraGenericoTAB_saticb");
//		setTabHabilitado("autAviDictamenGenericoTab_saticb");
//	
//	}
	
	
	//guardarEstatusObraCbxGenerico();
	inicializaDialogConfirmarEstatusObra();

}

	
	function guardarEstatusRegularizada(){
		var jsChkObraRegularizada = $("form#estatusObraTABForm #cbxRegulaObraEstObraGen").is(':checked');
		//var confirmaNoRegularizar = confirm("Se cerrar\u00e1 el folio sin regularizar la obra, desea continuar ?");
		var txtConfirmRegulariza = "Se cerrar\u00e1 el folio sin regularizar la obra, desea continuar ?";
		
		if(confirm("La informaci\u00f3n es correcta?") && (!jsChkObraRegularizada && confirm(txtConfirmRegulariza))) {			
			var objForma =$("form#estatusObraTABForm").toObject({mode:'first'});
		    var formAction = jsContextoPromocion + "seguimiento/generico/mandarARegularizar.do";		    
		
		try {
				bloquear();
				$.postJSON(formAction, objForma, function(data) {
					if(data == null){
						alert('Error General: Consulte a su administrador');
						desbloquear();
					}else{
						
						verifyCustomDataError(data);
						$("form#estatusObraTABForm #fecAtencionOficioEstObraGenerico").attr('disabled','disabled'); 
						$("form#estatusObraTABForm #cbxRegulaObraEstObraGen").attr('disabled','disabled');
						$("form#estatusObraTABForm #btnGuardarEstObraGen").attr('disabled','disabled');
						desbloquear();
						try {
							//recupera el nombre de la funcion generica
							eval($("form#estatusObraTABForm #functionAuxEstatusObra").val());	
						} catch (e) {

						}
						
					}
				}).error(function(data){
					validarSesionExpirada(data);
					alert("error" + data);
					desbloquear();
				});

		}catch(error){
			alert("Error al procesar la forma, verifique el códido JS:"+error);
			desbloquear();		
		}
		
		return true;
			
			
	}else{
		return false;
	}
		
}
	
	
	/**
	 * @author Oscar  Beltran
	 * @since 12/07/2012
	 * Funcion que manda ejecutar el metodo de Guardar el check de estatus Obra
	 */
	function guardarEstatusObraCbxGenerico(){
		var booleanCbxEstatusObra = false;
		var cbxEstatusObra = $("form#estatusObraTABForm #cbxRegulaObraEstObraGen").attr('checked');
		var cvePromocion           = $("form#seguimientoSaticbTABForm #cvePromocion").val();
		var fechaAtnEstatus     = $("form#estatusObraTABForm #fecAtencionOficioEstObraGenerico").val();
		var observacionesSegSaticb = $("form#seguimientoSaticbTABForm #observacionesSegSaticb").val();
		
		if(cbxEstatusObra == 'checked'){
			booleanCbxEstatusObra = true;
		}
		bloquear();
		   
		var sDatosVo = '{"cvePromocion":"'+cvePromocion+'", "estatusObraVO": {"regularizarObra":"'+booleanCbxEstatusObra+
		'" ,"fechaAtencionOficio":"'+fechaAtnEstatus+'" } }';
		var objectJson = jQuery.parseJSON(sDatosVo);
		   
		$.postJSON(jsContextoPromocion + "seguimiento/generico/mandarARegularizarCheckBox.do", objectJson, function(data) {	
		
		}).error(function(datas){ 
			validarSesionExpirada(datas);
		}).complete(function(){				
			desbloquear();
		});	
	}
	
	
	function inicializaDialogConfirmarEstatusObra(){
		oDgConfirmarEstObra = $(idDgConfirmarEstObra).dialog({
			autoOpen: false,
			modal:true,
			resizable:false,
			width: 700,
			closeOnEscape: false,
			buttons: {
				   "Si": function() { 
					   $("form#estatusObraTABForm #cbxRegulaObraEstObraGen").attr('checked', 'checked');
						guardarEstatusObraCbxGenerico();
						completaEstatusObraAuxB();
						$(this).dialog("close"); 
						setTabHabilitado("regularizarObraGenericoTAB_saticb");					
						return true;
								
				}, "No": function(){
					setTabDesHabilitado("regularizarObraGenericoTAB_saticb");						
					$("form#estatusObraTABForm #cbxRegulaObraEstObraGen").removeAttr('checked');
					$(this).dialog("close"); 
					return false;
				} 
			}
		});
		oDgConfirmarEstObra.dialog('open');
		return true;
	}
	
	
	
