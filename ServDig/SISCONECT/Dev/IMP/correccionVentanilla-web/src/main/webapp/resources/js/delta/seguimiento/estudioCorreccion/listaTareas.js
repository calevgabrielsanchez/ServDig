var idDataTable 	= "#dtListaTareas";
var idDgAviso = "#dgAvisoDobleFiltro";
var idDgAvisoSelecSol = "#dgAvisoSelecSol";
var idDgAvisoSeguimiento = "#dgAvisoSeguimiento";
//var idDgGenRecepcion = "#dgGenRecepcion";
var folioSeguimientoCorreccion;
var idDgGenRecepcion = "#dgCorreccionMain";

var oDgAviso;
var oDgAvisoSelecSol;
var oDgAvisoSeguimiento;
var oDgGenRecepcion;
var cveRolUsuario;
var rolIsJefe=false;
var oDtTareas;

var jsFechaPresentacionInicialSegCorr = "form#formListaTareas #inFechaIni";
var jsFechaPresentacionFinalSegCorr = "form#formListaTareas #inFechaFin";
var jsEjercicioSegCorr = "form#formListaTareas #ejercSolCorreccion";
var jsRegPatronalSegCorr = "form#formListaTareas #inRegPatron";
var jsFolioCorrSegCorr = "form#formListaTareas #inFolioCor";
var jsEstatusSelSegCorr = "form#formListaTareas #idEstadoSel";

var jsFechaMaxSeguimiento;
var jsFechaMinSeguimiento;

var JEFE_OF_CORRECCION = 4;
var JEFE_OF_CORRECCION_Y_DICTAMEN = 6;
var JEFE_DEP_AUD_PAT = 7;
var SUPERVISOR_OF_CORRECCION= 10;
var AUDITOR= 2;
var registroPatronalSeguimientoCorreccion;
var fechaPresentacionSeguimientoCorreccion;


var estatusRegistro;
var diasTransitadosRegistro;
var semaforoRegistro;


/**
 * Iniciamos la configuracion de los componentes visuales de jQuery.
 */
$(document).ready(function() {
	 	 	
	$(jsFechaPresentacionInicialSegCorr).datepicker( { dateFormat: 'dd-mm-yy' });
	$(jsFechaPresentacionFinalSegCorr).datepicker( { dateFormat: 'dd-mm-yy' });	
	
	$.postJSON("estudioCorreccion/obtenerFechaServidor.do", null,function(data) {
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
		$(jsFechaPresentacionInicialSegCorr).datepicker('option', 'maxDate', data.responseText);
		$(jsFechaPresentacionFinalSegCorr).datepicker('option', 'maxDate', data.responseText);
		jsFechaMaxSeguimiento = data.responseText;
	});
	
	/*seccion para definir la fecha minima del servidor y establecerle un limite minima a las fechas
	 * maximas de los calendarios para las fechas siguientes , con formato dd-MM-yyyy
	 */ 
	$.postJSON(getAppContextParaJS() + "/seguimiento/correccion/obtenerFechaServidorMinima.do", null,function(data) {
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
		jsFechaMinSeguimiento = data.responseText;
	});
	
	/*seccion para definir la fecha minima del servidor y establecerle un limite minima a las fechas
	 * maximas de los calendarios para las fechas siguientes , con formato dd-MM-yyyy
	 */ 
	$.postJSON(getAppContextParaJS() + "/seguimiento/correccion/obtenerFechaServidorMinima.do", null,function(data) {
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
		jsFechaMinSeguimiento = data.responseText;
	});
	
	/**
	 * Inicializacion del data table
	 */
	oDtTareas = $(idDataTable).dataTable({
		bJQueryUI : true,
		bFilter : false,
		bInfo:true,
		bSort: false,
		"bPaginate": true,
		"bAutoWidth" : true,
		"bServerSide" :	true,
		"iDeferLoading": 0,
		"aoColumns" : [ {
			fnRender :function(oObj){
				var retVal = '<input type="radio" value="' +
				oObj.aData['idSolicitud'] +'" id="radioTable" class="radioClase" name="radio" /> ';
				estatusRegistro=oObj.aData['descripcionEstatus'];
				diasTransitadosRegistro=oObj.aData['diasTranscurridos'];
				semaforoRegistro=oObj.aData['diasTranscurridos'];
				
				
				return retVal;
			}, 
			aTargets: [0]
			},{
				"sWidth": "25%",
				"sTitle" : "Folio ",
				"mDataProp" : "folioCorr",
				"sClass": "dtCenterClassColumn"
			},{
				"sWidth": "20%",
				"sTitle" : "Registro Patronal ",
				"mDataProp" : "regPatronal",
				"sClass": "dtCenterClassColumn",
				"fnRender":function(o,val){
					return o.aData['regPatronal'].substring(0,10);
				}
			},{
				"sWidth": "25%",
				"sTitle" : "Raz\u00f3n Social ",
				"mDataProp" : "razonSocial",
				"sClass": "dtCenterClassColumn"
			},{
				"sWidth": "20%",
				"sTitle" : "Fecha Estatus ",
				"mDataProp" : "fechaEstatusTxt",
				"sClass": "dtCenterClassColumn"
			},{
				"sWidth": "20%",
				"sTitle" : "Estatus ",
				"mDataProp" : "descripcionEstatus",
				"sClass": "dtCenterClassColumn"
			},{
				"sWidth": "20%",
				"sTitle" : "D\u00edas Trans. ",
				"mDataProp" : "diasTranscurridos",
				"sClass": "dtCenterClassColumn"
			},{
				"sWidth": "20%",
				"sTitle" : "Sem\u00e1foro ",
				"mDataProp" : "diasTranscurridos",
				"sClass": "dtCenterClassColumn"
			}
			],"bProcessing" : true,
			"sAjaxSource" : 'estudioCorreccion/pagina.do',
			"fnServerData" : function(sSource, aoData, fnCallback) {				
				
				var wrapper = new Object();
				wrapper.aoData = aoData;								
		
				var oForm = $("#formListaTareas").toObject({mode:'first'});
				wrapper.oForm = oForm;
								
				$.postJSON(sSource, wrapper, function(data) {			
										
					verifyCustomDataError(data);
					fnCallback(data);					
				 }).error(function(datas){ 
						validarSesionExpirada(datas);
				});
			}
		});
	
	oDgAviso = $(idDgAviso).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		height: 150,
		width: 700,
		closeOnEscape: false,
		buttons: {
			"Aceptar": function() {	
				$(this).dialog("close");
			}
		}
	});
	
	oDgAvisoSelecSol = $(idDgAvisoSelecSol).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		height: 150,
		width: 700,
		closeOnEscape: false,
		buttons: {
			"Aceptar": function() {	
				$(this).dialog("close");
			}
		}
	});
	
	oDgAvisoSeguimiento = $(idDgAvisoSeguimiento).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		height: 150,
		width: 700,
		closeOnEscape: false,
		buttons: {
			"Aceptar": function() {	
				$(this).dialog("close");
			}
		}
	});
	
	oDgGenRecepcion = $(idDgGenRecepcion).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 1130,
		height: 800,
		closeOnEscape: false,
		open:function(event, ui){
//			inicializaCancelacionSegCorreccion();
//			//inicializaReqDocumentacionSegCorreccion();
//			inicializaFechasOfResultadosSegCorreccion();
//			inicializaConclusionSegCorreccion();
		},
		close:function(){
				
			if((cveRolUsuario==JEFE_OF_CORRECCION || cveRolUsuario==JEFE_DEP_AUD_PAT ||
					cveRolUsuario==SUPERVISOR_OF_CORRECCION || cveRolUsuario==JEFE_OF_CORRECCION_Y_DICTAMEN)){
				desbloquearFormas();
			}
		},
		buttons: {
			"Regresar": function() {	
				$(this).dialog("close");
			}
		}
	});
	
	calculaEjercicios();
	
});

function buscar(){
	//alert("buscar");
	//alert("inRegPatron : " + $("#inRegPatron").val() +" ejercSolCorreccion:  " + $("form#formListaTareas #ejercSolCorreccion").val() );
	//alert("inEstado : " + $("#idEstadoSel").val() +" inFechaIni:  " + $(jsFechaPresentacionInicialSegCorr).val() +" inFechaFin : " + $("#inFechaFin").val()  );
	//alert("inFolioCor : " + $("#inFolioCor").val() );
	//alert($("#idEstadoSel").val() == "-1");
	if ($(jsRegPatronalSegCorr).val()!= '' && $(jsEjercicioSegCorr).val() == "-1"){
		//alert("if1");
		oDgAviso.dialog('open');
	}else if ($(jsRegPatronalSegCorr).val()=='' && $(jsEjercicioSegCorr).val() != "-1"){
		//alert("if2");
		oDgAviso.dialog('open')
	}else if ($(jsEstatusSelSegCorr).val()!="-1" && ( $(jsFechaPresentacionInicialSegCorr).val()=='' ||  $(jsFechaPresentacionFinalSegCorr).val()=='')){
		//alert("if3");
		oDgAviso.dialog('open');
	}else if (( $(jsFechaPresentacionInicialSegCorr).val()!='' ||  $(jsFechaPresentacionFinalSegCorr).val() !='') && $(jsEstatusSelSegCorr).val()=="-1" ) {
		//alert("if4");
		oDgAviso.dialog('open');
	}else if ($(jsRegPatronalSegCorr).val()=='' && $(jsEjercicioSegCorr).val() == "-1" && $(jsFolioCorrSegCorr).val() ==""
			&& $(jsEstatusSelSegCorr).val()=="-1" && $(jsFechaPresentacionInicialSegCorr).val()=='' && $(jsFechaPresentacionFinalSegCorr).val()=='' ) {
		//alert("if5");
		oDgAviso.dialog('open');
	}else {
		//alert("else");
		oDtTareas.fnDraw();
	}
			

}

function seguimiento() {
	var banderaContinuar = false;
	var idSolEst = $('#:checked').val();
	if(idSolEst!=undefined){		
		var sVarSeg = '{"cveSolCorr":'+idSolEst+'}';
		var clase = jQuery.parseJSON(sVarSeg);
		bloquear();
		var rol;
		$.postJSON("estudioCorreccion/seguimientoCorreccionMain.do", clase, function(data) {
			if(data != null){
				cveRolUsuario=data.user.cveRol;
				cvePresentacion=data.cvePresentaCorr;
				//alert("data.user.cveRol : " + data.user.cveRol + "_  JEFE_OF_CORRECCION :"  + JEFE_OF_CORRECCION);
				//auditor due�o de la correccion o perfiles con permisos para ver folios
//				if((data.user.cveIdUsuario  == data.cveAuditorAsignado) || (data.user.cveRol==JEFE_OF_CORRECCION || data.user.cveRol==JEFE_DEP_AUD_PAT ||
//						data.user.cveRol==SUPERVISOR_OF_CORRECCION || data.user.cveRol==JEFE_OF_CORRECCION_Y_DICTAMEN)){					
				if((data.user.curpUsuario  == data.cveAuditorAsignado) || (data.user.cveRol==JEFE_OF_CORRECCION || data.user.cveRol==JEFE_DEP_AUD_PAT ||
						data.user.cveRol==SUPERVISOR_OF_CORRECCION || data.user.cveRol==JEFE_OF_CORRECCION_Y_DICTAMEN)){
						if(data.user.cveRol==JEFE_OF_CORRECCION || data.user.cveRol==JEFE_DEP_AUD_PAT ||
								data.user.cveRol==SUPERVISOR_OF_CORRECCION || data.user.cveRol==JEFE_OF_CORRECCION_Y_DICTAMEN){
							rolIsJefe=true;
						}
					
						
						if(data.cveAuditorAsignado != null && data.cveAuditorAsignado != '0'){
							$('form#seguimientoCorreccionForm #cveSolCorrSeguimientoCorreccion').val(data.cveSolCorr);
							folioSeguimientoCorreccion=data.nuFolio;
							$('form#seguimientoCorreccionForm #labelFolioCorr').html('<label>'+ data.nuFolio +'</label>');
							//alert("data.nuFolio : " + data.nuFolio);
							$('form#formCedValidacionSeguimientoCorr #numFolioCedValHdn').val(data.nuFolio);
							$('form#seguimientoCorreccionForm #nuFolioSegCorrHdn').val(data.nuFolio);
							if(data.fecFechaPeriodoIni != null){
								$('form#seguimientoCorreccionForm #labelFecInicio').html('<label>'+ data.fecFechaPeriodoIni +'</label>');
								$('form#seguimientoCorreccionForm #fecFechaPeriodoIniSegHdn').val(data.fecFechaPeriodoIni);
							}
							if(data.fecFechaPeriodoFin != null){
								$('form#seguimientoCorreccionForm #labelFecFinal').html('<label>'+ data.fecFechaPeriodoFin +'</label>');
								$('form#seguimientoCorreccionForm #fecFechaPeriodoFinSegHdn').val(data.fecFechaPeriodoFin);
							}
							if(data.regPatronal != null){
								$('form#seguimientoCorreccionForm #labelRegPatronal').html('<label>'+ data.regPatronal +'</label>');
								registroPatronalSeguimientoCorreccion=data.regPatronal;
							}
							if(data.razonSocial != null){
								$('form#seguimientoCorreccionForm #labelRazonSocial').html('<label>'+ data.razonSocial +'</label>');
							}
							if(data.regObra != null){
								$('form#seguimientoCorreccionForm #labelRegObra').html('<label>'+ data.regObra +'</label>');
							}
							if(data.fecElaboraPre != null){
								$('form#seguimientoCorreccionForm #labelFechaPresentacionCorr').html('<label>'+ data.fecElaboraPre +'</label>');
								fechaPresentacionSeguimientoCorreccion=data.fecElaboraPre;
								$("form#conclusionSeguimientoCorreccionForm #fecEmiConclusion").datepicker('option','minDate',fechaPresentacionSeguimientoCorreccion);
							}
							
							$('form#seguimientoCorreccionForm #labelSemaforo').html('<label>'+ diasTransitadosRegistro +'</label>');
							$('form#seguimientoCorreccionForm #labelEstatus').html('<label>'+ estatusRegistro +'</label>');
							$('form#seguimientoCorreccionForm #labelDiasTrans').html('<label>'+ diasTransitadosRegistro +'</label>');
							
							
							//metodo inicializacion de resumen		//metodo inicializacion de resumen
							inicializaResumen(data);
						
							
							determinaRolRevisionCedula(data);
							
							
							// manda cveSolCOrr a hiden de Oficio de Resultados
							$("form#ofSeguimientoCorreccionForm #cveSolCorrOfResSegCorr").val(data.cveSolCorr);
							// manda cveSolCOrr a hiden de Requerimiento de documentacion
							$("form#reqDocumentacionSeguimientoCorreccionForm #cveSolCorrReqDocSegCorr").val(data.cveSolCorr);
							// manda cveSolCOrr a hiden de Conclusion
							$("form#conclusionSeguimientoCorreccionForm #cveSolCorrConclusionSegCorr").val(data.cveSolCorr);
							// manda cveSolCOrr a hiden de Cancelacion
							$("form#cancelacionSeguimientoCorreccionForm #cveSolCorrCancelacionSegCorr").val(data.cveSolCorr);
							
							// Para inicializar tab de derivaci�n a subdelegaci�n
							inicializaDevSubDelSegCorreccion(data);
							//parametros para tab de derivacion subdel
							$('form#devSubDelegacionSeguimientoCorreccionForm #cveSolicitud').val(data.cveSolCorr);
							
							//parametros para tab de recepcion
							$('form#formRecepcionSeguimiento #idPresentaCorreccionHtml').val(data.cvePresentaCorr);
							
							$('form#formRecepcionSeguimiento #cveTipoCorreccionHdn').val(data.cveTipoCorreccion);
							
							$('form#seguimientoCorreccionForm #cvePresentaCorreccionHdnSeg').val(data.cvePresentaCorr);
							
							//rol del usuario
							$('form#seguimientoCorreccionForm #cveRolUsuario').val(data.user.cveRol);
							
							//para Cedula de validacion
							$('form#formCedValidacionSeguimientoCorr #cvePresentaCorrCedVal').val(data.cvePresentaCorr);
							

							$('form#reqDocumentacionSeguimientoCorreccionForm #fechaElaboraPresenHdn').val(data.fecElaboraPre);
							
							//alert("data.fecElaboraSolCorr");
							$('form#seguimientoCorreccionForm #fecElaboraPreSegCorrMainHdn').val(data.fecElaboraSolCorr);
							
							
							inicializaReqDocumentacionSegCorreccion(data);
							
							//se llama al init del tab de recepcion
							initRecepcionSeguimientoTab(data.cveTipoCorreccion,data.nuFolio);
							
							//se llama al init del tab de derivaci�n a dictamen
							inicializaDevDictamenSegCorreccion(data);
							
							// Se llama al init del tab de derivacion a fiscalizaci�n
							inicializaDevFiscalizacionSegCorreccion(data);							

							// Se llama al init del tab de derivacion a reactivacion
							inicializaReactivacionSegCorreccion(data);
							
							//para la Cedula de Validacion
							inicializaCedulaValidacion(data);
							
							inicializaCancelacionSegCorreccion();
							
							
							
							inicializaConclusionSegCorreccion();
							
							
							inicializaFechasOfResultadosSegCorreccion();
							oDgGenRecepcion.dialog("open");
							banderaContinuar = true;
						}else{
							alert(" Folio sin auditor asignado, Favor de asignarlo. ");
							banderaContinuar = false;
						}
					
				}else{
					
					alert(" El folio esta asignado a otro auditor ");
					banderaContinuar = false;
				}
				
				
				rol=data.user.cveRol;
			
				
			}
		}).error(function(data){ 
			validarSesionExpirada(data);
			alert("error" + data.statusText);
		}).complete(function(){
			desbloquear();	
			if(banderaContinuar){
					
				validaPagosExistentes();
				obtenerTotalesPagos();
				obtenerTotalesPagosCedVal("inicio");
			}
			ejecutaReglasValidacion(rol);
		});							
	} else {
		oDgAvisoSelecSol.dialog('open');
	}
	
	$("#accessTabs").val("seguimientoCorreccionResumen-seguimientoCorreccionRecepcion-seguimientoCorreccionCedRevision-seguimientoCorreccionReqDocumentacion-" +
			"seguimientoCorreccionCedValidacion-seguimientoCorreccionOfiResultados-seguimientoCorreccionDerivarSub-seguimientoCorreccionDerivarFis-seguimientoCorreccionReactivar-" +
			"seguimientoCorreccionDerivarDic-seguimientoCorreccionCancelacion-seguimientoCorreccionConclusion");
	$("#forbidenTabs").val();
	
	
	
}

function jsValidaFecFinal(){	
	 $("form#formListaTareas #labelValidaFecha").html('');	 
	 var fecIni = $(jsFechaPresentacionInicialSegCorr).val();
	 var fecFinal = $(jsFechaPresentacionFinalSegCorr).val();	 
	 if(fecIni != '' && fecFinal != ''){
		 if(jsValidaFechas(fecIni,fecFinal)){
			 $(jsFechaPresentacionFinalSegCorr).val(fecFinal);
			 $("form#formListaTareas #labelValidaFecha").html('');
		 }else{			 
			 $(jsFechaPresentacionFinalSegCorr).val('');
			 $("form#formListaTareas #labelValidaFecha").html('<label class="etiquetaError">La fecha final no puede ser menor a la fecha inicial</label>');
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

function jsValidaFechasLimite(){	
	var ini = $(jsFechaPresentacionInicialSegCorr).val();
	var fin = $(jsFechaPresentacionFinalSegCorr).val();
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
		$(jsFechaPresentacionInicialSegCorr).val("");
		$(jsFechaPresentacionFinalSegCorr).val("");
		$("form#formListaTareas #labelValidaFecha").html('<label class="etiquetaError" >El rango de fechas permitido es maximo un a\u00f1o</label>');
	}
}

function limpiaFormTareas() {
	$(jsRegPatronalSegCorr).val("");
	$(jsFolioCorrSegCorr).val("");
	$(inEstado).val("-1");
	$(jsFechaPresentacionInicialSegCorr).val("");
	$(jsFechaPresentacionFinalSegCorr).val("");
	$("form#formListaTareas #labelValidaFecha").html('');
}

function lTrimJS(){	
	var cadena = $(jsRegPatronalSegCorr).val();
	//alert(cadena.length);
	if(cadena.charAt(cadena.length-1)==" ")
	{
		cadena = cadena.substr(0, cadena.length - 1);
	}
	if (cadena.charAt(0)==" ") {
		cadena = cadena.substr(1, cadena.length - 1);
	}
    $(jsRegPatronalSegCorr).val(cadena);
}

function calculaEjercicios(){
	
	var options = "<option value='-1' >--Por favor seleccione--</option>";
	//if(datas!=null)
		/*for (var i = 0; i < datas.length; i++) {
			options += "<option value='"+ datas[i].idCriterioseleccion +"'>"+ datas[i].descCriterioseleccion +"</option>";		     
			
		}*/
	options += "<option value='"+ 2011 +"'>"+ 2011 +"</option>";
	options += "<option value='"+ 2012 +"'>"+ 2012 +"</option>";
	options += "<option value='"+ 2013 +"'>"+ 2013 +"</option>";
	options += "<option value='"+ 2014 +"'>"+ 2014 +"</option>";
	options += "<option value='"+ 2015 +"'>"+ 2015 +"</option>";
	$('select#ejercSolCorreccion').html(options);
}

function busquedaPorRegPatronalEjercicio(){
	
	$(jsFolioCorrSegCorr).val("");
	$(jsEstatusSelSegCorr).val("-1");
	$(jsFechaPresentacionInicialSegCorr).val("");
	$(jsFechaPresentacionFinalSegCorr).val("");
}

function busquedaPorFolioCorreccion(){
	
	$(jsRegPatronalSegCorr).val("");
	$(jsEjercicioSegCorr).val("-1");
	$(jsEstatusSelSegCorr).val("-1");
	$(jsFechaPresentacionInicialSegCorr).val("");
	$(jsFechaPresentacionFinalSegCorr).val("");
}

function busquedaPorEstadoFechaStatus(){
	$(jsFolioCorrSegCorr).val("");
	$(jsRegPatronalSegCorr).val("");
	$(jsEjercicioSegCorr).val("-1");
	
}


function determinaRolRevisionCedula(data){
	
		var rol=data.user.cveRol;
		if(rol==AUDITOR){
			//caso de auditor
			$("#supervisorIdRevCedula").css("display", "none");
			$("#auditorIdRevCedula").css("display", "block");
			incializaCedulaRevision(data);
		}else if(rol==JEFE_OF_CORRECCION || rol==JEFE_DEP_AUD_PAT ||
				rol==SUPERVISOR_OF_CORRECCION || rol==JEFE_OF_CORRECCION_Y_DICTAMEN || rol=='10'){
			//caso de supervisor			
			$("#auditorIdRevCedula").css("display", "none");
			$("#supervisorIdRevCedula").css("display", "block");
			incializaCedulaRevisionSupv(data);
		}
		
}

function bloqueaSegCorr(){
	bloquear();
	$("#dgCorreccionMain").dialog("close");
}

function desbloqueaSegCorr(){
	desbloquear();
	$("#dgCorreccionMain").dialog("open");
}


function drawData(id,valor){
	if(valor=='' || valor==null || valor==undefined || valor=='null'){
		valor="0.0";
	}
	 $("form#formResumenSeguimiento "+id).text(moneyMaskDT(valor));
}


function recuperaEjercicios(){
		$.postJSON_Sync("estudioCorreccion/recuperaEjercicios.do", $("#inFolioCor").val(), function(data) {

		var options = "<option value='-1' >--Por favor seleccione--</option>";
		
		
		for(var s=0;s<data.length;s++){
			options += "<option value='"+ data[s] +"'>"+ data[s]+"</option>";				
		}
		$("#ejercSolCorreccion").html(options);
	});
	
}