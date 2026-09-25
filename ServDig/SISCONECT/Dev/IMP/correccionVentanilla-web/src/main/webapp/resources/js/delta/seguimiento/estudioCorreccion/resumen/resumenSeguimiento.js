

var dtPatronesAsociados="form#formResumenSeguimiento #dtPatronesAsociados";
var dtCOP="form#formResumenSeguimiento #dtResumenCOP";
var oDTPatAsoc;
var dgDetalleCedula="#dgDetalleCedula";
var numFolio;
var regPatro;
var dataCedulas;
var ejer="";

function inicializaResumen(data){
	inicializaPestanias();
	numFolio=data.nuFolio;	
	regPatro=data.regPatronal;	
	bloquear();
	var sVarSeg = '{"cveSolCorr":"'+data.cveSolCorr+'","regPatronal":"'+data.regPatronal+'"}';
	var clase = jQuery.parseJSON(sVarSeg);
	
	$.postJSON_Sync("estudioCorreccion/seguimientoCorreccionResumen.do", clase, function(data) {		
		$("form#formResumenSeguimiento #domicilioFiscal").text(data.domicilioFiscal);
		$("form#formResumenSeguimiento #domicilioCentroTrabajo").text(data.domicilioCentroTrabajo);
		$("form#formResumenSeguimiento #domicilioObra").text(data.domicilioObra);	
		
		$("form#formResumenSeguimiento #lbFechaPreSolCorrVal").text(data.fechaElaboSolCorr);
		
		$("form#ofSeguimientoCorreccionForm #fecPresentaCorrRecepcion").val(data.fechaIngresoSolCorr);
		
		$("form#formResumenSeguimiento #lbFechaAutSolCorrVal").text(data.fechaAutoIngresoSolCorr);
		$("form#formResumenSeguimiento #lbFechaSolProrrVal").text(data.fechaSolProrroga);
		$("form#formResumenSeguimiento #lbFechaAutoProrrVal").text(data.fechaAutoProrroga);
		$("form#formResumenSeguimiento #lbFechaRechProrrVal").text(data.fechaRechazoProrroga);
		$("form#formResumenSeguimiento #lbFechaPresentaVal").text(data.fechaPresentacion);
//		$("form#formResumenSeguimiento #lbFechaAutoPresentVal").text(data.fechaAutoPresenta);		
		
		$("form#formResumenSeguimiento #lbOrigenVal").text(data.origen);
		$("form#formResumenSeguimiento #lbFolioProgramaVal").text(data.folioPrograma);
		$("form#formResumenSeguimiento #lbFechaEmiOfVal").text(data.fechaEmisionOficio);
		$("form#formResumenSeguimiento #lbFechaNotOfVal").text(data.fechaNotificacionOficio);
			
		generaTablaPatronesAsociados(data);
		generaTablaCOP(data);
		iniciaDGCedula();
		generaCedulas();
		
	}).error(function(data){ 		
		validarSesionExpirada(data);
		alert("error" + data);
	}).complete(function(){
		desbloquear();													
	});		
}


function iniciaDGCedula(){
	$( "#dialog-form" ).dialog({
	autoOpen: false,
	modal:true,
	resizable:false,
	width: 1000,
	height: 500
});

}

var fnCedA=function(data){	
	
	$( "#dialog-form" ).html(data.tablaCedulaA);
	$( "#dialog-form" ).dialog('open');	
}

var fnCedG=function(data){	
	$( "#dialog-form" ).html(data.tablaCedulaG);
	$( "#dialog-form" ).dialog('open');	
}

var fnCedH=function(data){	
	$( "#dialog-form" ).html(data.tablaCedulaH);
	$( "#dialog-form" ).dialog('open');	
}

var fnCedI=function(data){	
	$( "#dialog-form" ).html(data.tablaCedulaI);
	$( "#dialog-form" ).dialog('open');	
}

var fnCedO=function(data){	
	$( "#dialog-form" ).html(data.tablaCedulaO);
	$( "#dialog-form" ).dialog('open');	
}

var fnCedQ=function(data){	
	$( "#dialog-form" ).html(data.tablaCedulaQ);
	$( "#dialog-form" ).dialog('open');	
}

var fnCedCop=function(data){	
	$( "#dialog-form" ).html(data.tablaCop);
	$( "#dialog-form" ).dialog('open');	
}

var fnCedR=function(data){	
	$( "#dialog-form" ).html(data.tablaCedulaR);
	$( "#dialog-form" ).dialog('open');	
}


function generaTablaPatronesAsociados(data){	
	
	 $(dtPatronesAsociados).dataTable( {
			"aaData": data.registrosPatronalesAsociados,
			bFilter : false,
			bJQueryUI : true,
			bInfo:true,
			"bDestroy": true,
			bSort: false,
			//crollY: "200px",
			"aoColumns" : [ {
					"sWidth": "50%",
					"sTitle" : "Registro Patronal ",
					"mDataProp" : "registroPatronalSD",
					"sClass": "dtCenterClassColumn"
				},{
					"sWidth": "50%",
					"sTitle" : "Raz\u00f3n Social ",
					"mDataProp" : "razonSocial",
					"sClass": "dtCenterClassColumn"
				}
				]
	    } );  
	
	 	
}


function generaTablaCOP(data){
	
//	$("form#formResumenSeguimiento #lbCopSPVal").text(moneyMaskDT(data.copPagadas[0].suertePrincipal));	
//	$("form#formResumenSeguimiento #lbCopActVal").text(moneyMaskDT(data.copPagadas[0].actualizaciones));
//	$("form#formResumenSeguimiento #lbCopRecVal").text(moneyMaskDT(data.copPagadas[0].recargos));
//	$("form#formResumenSeguimiento #lbCopTotaVal").text(moneyMaskDT(data.copPagadas[0].totales));	
//	$("form#formResumenSeguimiento #lbRcvSPVal").text(moneyMaskDT(data.copPagadas[1].suertePrincipal));	
//	$("form#formResumenSeguimiento #lbRcvActVal").text(moneyMaskDT(data.copPagadas[1].actualizaciones));
//	$("form#formResumenSeguimiento #lbRcvRecVal").text(moneyMaskDT(data.copPagadas[1].recargos));
//	$("form#formResumenSeguimiento #lbRcvTotaVal").text(moneyMaskDT(data.copPagadas[1].totales));	
//	$("form#formResumenSeguimiento #lbTotalSPVal").text(moneyMaskDT(data.copPagadas[2].suertePrincipal));	
//	$("form#formResumenSeguimiento #lbTotalActVal").text(moneyMaskDT(data.copPagadas[2].actualizaciones));
//	$("form#formResumenSeguimiento #lbTotalRecVal").text(moneyMaskDT(data.copPagadas[2].recargos));
//	$("form#formResumenSeguimiento #lbTotalTotaVal").text(moneyMaskDT(data.copPagadas[2].totales));
//	$("form#formResumenSeguimiento #lbnumTraRegu").text(moneyMaskDT(data.numTrabajaRegular));
	
	
}

function generaCedulas(){
	
	$("#lbDetalleCedulaA").click(function(){
		 listenerCedula(fnCedA);	
	 });
	
	$("#lbDetalleCedulaG").click(function(){
		listenerCedula(fnCedG);	 
	 });
	
	$("#lbDetalleCedulaH").click(function(){
		listenerCedula(fnCedH);
	 });
	
	$("#lbDetalleCedulaI").click(function(){		
		listenerCedula(fnCedI);		
	 });
	
	$("#lbDetalleCedulaO").click(function(){	
		listenerCedula(fnCedO);	
	 });
	
	$("#lbDetalleCedulaQ").click(function(){
		listenerCedula(fnCedQ);			 
	 });
	
	$("#lbDetalleCedulaCOP").click(function(){
		listenerCedula(fnCedCop);			
	 });
	
	$("#lbDetalleCedulaR").click(function(){
		listenerCedula(fnCedR);	
	 });
}



function recuperaCedula(folio,ejercicio,funcion){
		bloquear();
		var sVarSeg = '{"folioCorreccion":"'+folio+'","periodo":"'+ejercicio+'","registroPatronal":"'+regPatro+'"}';
		var clase = jQuery.parseJSON(sVarSeg);
		
		$.postJSON("../consultaEstudioCorreccion/consultarCedula.do", clase, function(data) {
				dataCedulas=data;
				funcion(data);
			}).error(function(data){ 		
				validarSesionExpirada(data);
				alert("error" + data);
			}).complete(function(){
				desbloquear();													
			});	
	}

function inicializaPestanias(){
	
	
	ocultarPestania('#titleDatosGenerales', '#bodyDatosGenerales');
	ocultarPestania('#titleAntecedentes', '#bodyAntecedentes');
	ocultarPestania('#titleSolCorr', '#bodySolCorr');
	ocultarPestania('#titlePressCorr', '#bodyPressCorr');
	ocultarPestania('#titleDerivaFizca', '#bodyDerivaFiza');
	ocultarPestania('#titleDerivaSubde', '#bodyDerivaSubde');
	ocultarPestania('#titleDerivaDicta', '#bodyDerivaDicta');
	ocultarPestania('#titleCancelacion', '#bodyCancelacion');
	ocultarPestania('#titleObserva', '#bodyObserva');
	
		
}


function ocultarPestania(barraTitulo,body){
	$(barraTitulo).unbind();
	$(body).hide();	
	$(barraTitulo).click(function(){		
		if ($(body).is(':visible')){	
			$(body).hide();
		}else{
			$(body).show();
		}
	});
	$(barraTitulo).css('cursor', 'pointer');
	
}



function validaEjercicio(ejercicio) {
    var RegExPattern = /^[0-9]{4}$/;
    return RegExPattern.test(ejercicio);    
}

function listenerCedula(func){
	jPrompt('Ingrese Ejercicio', ejer, 'Ejercicio', function(ejercicio) {
		var estatus=true;
		    if(ejercicio==""){
		    	jAlert('El ejercicio es necesario para la consulta', 'Informacion');
		    	estatus=false;
		    }else if(ejercicio!="" && ejercicio!=null && validaEjercicio(ejercicio)){
		    	estatus=false;
		    	 if(ejercicio!="" && ejercicio!=ejer){		
					 recuperaCedula(numFolio,ejercicio,func);
					 ejer=ejercicio;
				 }else{
					 func(dataCedulas);
				 }
		    }else{
		    	jAlert('Ingrese un ejercicio valido', 'Informacion');
		    }	    
	});	
	
	
}
