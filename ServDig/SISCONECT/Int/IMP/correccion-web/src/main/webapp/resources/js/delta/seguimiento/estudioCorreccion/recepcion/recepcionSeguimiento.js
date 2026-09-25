
var jsTrabRevisadosRegularObraConsolImpte =  "form#formRecepcionSeguimiento #trabRevisadosSegCorr";
var jsSuertePpalDetCopRegularObraConsolImpte = "form#formRecepcionSeguimiento #suertePpalDetCopSegCorr";
var jsSuertePpalDetRcvRegularObraConsolImpte = "form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr";


//Dialog Confirmar Generico para Seguimiento SATIC B
var idDgConfirmarRecepcionTab = "#dgConfirmarRecepcionSolCorr";
var oDgConfirmarRecepcionTab;

var numeroPagosPorCorreccion = 0;
var cveEstatusRecepcion;
var rolUsuario;
function procesarRecepcionSeguimiento(){

	//definicion de dialogo de confirmacion
	oDgConfirmarRecepcionTab = $(idDgConfirmarRecepcionTab).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 700,
		closeOnEscape: false,
		buttons: {
			"Si": function() { 
				guardaRecepcionSeguimiento();
				oDgConfirmarRecepcionTab.dialog("close");
				
							
			}, "No": function(){
				$(this).dialog("close"); 
			} 
		}
	});
	
	//apertura de dialogo
	oDgConfirmarRecepcionTab.dialog("open");

}


function guardaRecepcionSeguimiento() {
	
	
	if(validaDatosGuardarRecepcion() && validaTrabRegularizadosConsolImpte()){
	
		//if(numeroPagosPorCorreccion != 0 || $("form#formRecepcionSeguimiento #cbRecPagoTra").attr('checked') !=  'checked'){
		
			$('form#formRecepcionSeguimiento #preGuardadoHdn').val('false');
			var oForm = $("#formRecepcionSeguimiento").toObject(true);
			bloquear();
			$.postJSON("correccion/guardarRecepcion.do", oForm, function(data) {
	
				$("form#formRecepcionSeguimiento #fecPresentCorrecConsol").val(data.fechaPresentacionCorr);
				$('form#formRecepcionSeguimiento #estatusPresentaCorrecConsolida').val(data.estatusPresentacionCorr);
				
				if(data.cveRevRecepcion != null && data.cveRevRecepcion != ''){
					alert("La informaci\u00f3n se guardo correctamente.");
					
					$("form#formRecepcionSeguimiento #cveRevRecepcionHdn").val(data.cveRevRecepcion);
				}
			}).error(function(data) {
				validarSesionExpirada(data);
				alert("error"+data);
			}).complete(function(data){	

				desbloquear();
				//completaFlujoComprobConvenio();
				completaRecepcionSolCorr();
		
				$("form#formRecepcionSeguimiento #btnGuardarRecepcion").prop("disabled", "disabled");
				$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").prop("disabled", "disabled");
				$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").prop("disabled", "disabled");
				
				
				$("form#formRecepcionSeguimiento #btnGuardarRecepcion").removeClass("red");
				$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").removeClass("red");
				$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").removeClass("red");
				cveEstatusRecepcion=10;
				ejecutaReglasValidacion(1);
			});	

		//}else{
			//alert("Debe ingresar por lo menos un pago para poder generar una Recepci\u00f3n");
		//}
	}
}






/**
 * Funcion de inicializacion de datos y componentes de la pestala de Recepcion, es mandada llamar desde el inicio
 * del seguimiento de Correccion.
 * @param cveTipoCorreccion
 * @author Oscar Beltran Ortega
 */
function initRecepcionSeguimientoTab(cveTipoCorreccion,folioCorreccion){
	
	var cvePresCorreccion =	$('form#formRecepcionSeguimiento #idPresentaCorreccionHtml').val();
	var sCvePresCorr = '{"idPresentaCorreccion":'+cvePresCorreccion+'}';
	var recepcionVO = jQuery.parseJSON(sCvePresCorr);
	var seleccionoPagos = false;
	
	$('form#formRecepcionSeguimiento #cveRevRecepcionHdn').val('');
	$('form#formRecepcionSeguimiento #nuFolioHdn').val(folioCorreccion);
	
	//buscar si ya existe una recepcion creada para esa solcorr
	$.postJSON_Sync("correccion/consultaRecepcion.do", recepcionVO, function(data) {
		cveEstatusRecepcion=-1;
		
	
		
		//ya existe una recepcion
		if(data != null){
			
			//se reasignan los valores a los elementos hml
		
			cveEstatusRecepcion=data.cveStatus;
			generaResumenRecepcion(data);
			$('form#formRecepcionSeguimiento #cveRevRecepcionHdn').val(data.cveRevRecepcion);
			$('form#formRecepcionSeguimiento #porcentajeAvanceSegCorr').val(data.porcentajeAvance);
			$('form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr').val(data.porcentajeRegula);
			$('form#formRecepcionSeguimiento #numeroParcialidadesSegCorr').val(data.numParcialidades);
			$('form#formRecepcionSeguimiento #fecPresentCorrecConsol').val(data.fechaPresentacionTxt);
			$('form#formRecepcionSeguimiento #taRecObservacion').val(data.observaciones);
			
			//seccion trabajadores
			var myNumber = Number(data.numTrabrevisados);
			$('form#formRecepcionSeguimiento #trabRevisadosSegCorr').val(myNumber.formatMoney(0, '.', ',')); 
			myNumber = Number(data.numTrabomisos);
			$('form#formRecepcionSeguimiento #trabOmisosUniSegCorr').val(myNumber.formatMoney(0, '.', ','));
			myNumber = Number(data.numTrabSubdeclarados);
			$('form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr').val(myNumber.formatMoney(0, '.', ','));
			
			//COP y RCV
			myNumber = Number(data.impSPCopAutDet);
			$('form#formRecepcionSeguimiento #suertePpalDetCopSegCorr').val(myNumber.formatMoney(2, '.', ','));
			myNumber = Number(data.impSPRcvAutDet);
			$('form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr').val(myNumber.formatMoney(2, '.', ','));
			
			//estatus
			if(data.estatusPresentacionDescripcion != null && data.estatusPresentacionDescripcion != undefined && data.estatusPresentacionDescripcion != "" ){
				$('form#formRecepcionSeguimiento #estatusPresentaCorrecConsolida').val(data.estatusPresentacionDescripcion);
			}else{
				$('form#formRecepcionSeguimiento #estatusPresentaCorrecConsolida').val('');
			}
			// si hay pagos
			if(data.nuComprobantePago!= null &&  data.nuComprobantePago ==1){
				var tOmisos=parseInt(data.numTrabomisos);
				var tSubdecla =	parseInt(data.numTrabSubdeclarados);
				var totalTrabRegulariza = tOmisos + tSubdecla;
				myNumber = Number(totalTrabRegulariza);
				$('form#formRecepcionSeguimiento #trabRegularizadosRegObraSegCorr').val(myNumber.formatMoney(0, '.', ','));
				seleccionoPagos = true;
			}else{
				$('form#formRecepcionSeguimiento #trabRegularizadosRegObraSegCorr').val('');
				seleccionoPagos = false;
			}

			//checkbox
			if(data.nuDoctoSustento == 1){
				$("form#formRecepcionSeguimiento #cbRecDocu").attr('checked', true);
			}else{
				$("form#formRecepcionSeguimiento #cbRecDocu").attr('checked', false);
			}
			
			if(data.nuComprobantePago == 1){
				$("form#formRecepcionSeguimiento #cbRecPagoTra").attr('checked', true);
			}else{
				$("form#formRecepcionSeguimiento #cbRecPagoTra").attr('checked', false);
			}
			
			if(data.nuComproMovAfil == 1){
				$("form#formRecepcionSeguimiento #cbRecAvisoAfi").attr('checked', true);
			}else{
				$("form#formRecepcionSeguimiento #cbRecAvisoAfi").attr('checked', false);
			}
			
			if(data.comprobanteConvenio == 1){
				$("form#formRecepcionSeguimiento #cbxComprobConvenio").attr('checked', true);
			}else{
				$("form#formRecepcionSeguimiento #cbxComprobConvenio").attr('checked', false);
			}
			
			
			
			inicializaEstadoInicialPantalla(cveTipoCorreccion, false,seleccionoPagos,data,folioCorreccion);
			//METODO QUE DEFINE TODA LA NAVEGACION DEL SEGUIMIENTO OGBO , ver si debe ir aqui
			habilitaNavegacion(data,folioCorreccion);
			
			
			
			$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").prop("disabled", "disabled");
			$("form#formRecepcionSeguimiento #trabOmisosUniSegCorr").prop("disabled", "disabled");
			$("form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr").prop("disabled", "disabled");
			$("form#formRecepcionSeguimiento #btnGuardarRecepcion").prop("disabled", "disabled");
			
			
			$("form#formRecepcionSeguimiento #cbRecDocu").prop("disabled", "disabled");
			$("form#formRecepcionSeguimiento #cbRecPagoTra").prop("disabled", "disabled");
			$("form#formRecepcionSeguimiento #cbRecAvisoAfi").prop("disabled", "disabled");
			
			$("form#formRecepcionSeguimiento #cbRecDocu").removeClass("red");
			$("form#formRecepcionSeguimiento #cbRecPagoTra").removeClass("red");
			$("form#formRecepcionSeguimiento #cbRecAvisoAfi").removeClass("red");
			
			$("form#formRecepcionSeguimiento #cbxComprobConvenio").prop("disabled", "disabled");
			$("form#formRecepcionSeguimiento #cbxComprobConvenio").removeClass("red");
			
			$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").removeClass("red");
			$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").prop("disabled", "disabled");

			$("form#formRecepcionSeguimiento #taRecObservacion").removeClass("red");
			$("form#formRecepcionSeguimiento #taRecObservacion").prop("disabled", "disabled");

		
		
		}else{ // no existe recepcion
			
			$('form#formRecepcionSeguimiento #porcentajeAvanceSegCorr').val();
			$('form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr').val('');
			$('form#formRecepcionSeguimiento #numeroParcialidadesSegCorr').val('');
			
			$('form#formRecepcionSeguimiento #fecPresentCorrecConsol').val('');
			$('form#formRecepcionSeguimiento #taRecObservacion').val('');
			
			//seccion trabajadores
			$('form#formRecepcionSeguimiento #trabRevisadosSegCorr').val(''); 
			$('form#formRecepcionSeguimiento #trabOmisosUniSegCorr').val('');
			$('form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr').val('');
			
			//COP y RCV
			$('form#formRecepcionSeguimiento #suertePpalDetCopSegCorr').val('');
			$('form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr').val('');
			
			//estatus
			
			$('form#formRecepcionSeguimiento #estatusPresentaCorrecConsolida').val("");
			
			inicializaEstadoInicialPantalla(cveTipoCorreccion,true,false,data,folioCorreccion);
		}
		
	}).error(function(data) {
		validarSesionExpirada(data);
		alert("error"+data);
	}).complete(function(){				
		desbloquear();
	});	
	
}


/**
 * Función para abrir el dialogo de pagos autodeterminacion
 * @author Oscar German Beltrán Ortega
 */
function openDialogoPagosAutodetermina(){
	var fechaElaboracionPresenta = $('form#seguimientoCorreccionForm #fecElaboraPreSegCorrMainHdn').val();
	//solo se puede abrir la pantalla de pagos cuando se hayan capturado todos los datos de la pantalla de recepcion
	if(validaFechaElaboraPresenta()){
		if(validaDatosGuardarRecepcion()){
			if(confirm("Este proceso puede tardar varios minutos, desea continuar?")){
			
				bloquear();
			
				var periodoInicialPag = $("form#seguimientoCorreccionForm #fecFechaPeriodoIniSegHdn").val();
				var periodoFinalPag = $("form#seguimientoCorreccionForm #fecFechaPeriodoFinSegHdn").val();
				var folioCorreccionPag= $("form#seguimientoCorreccionForm #nuFolioSegCorrHdn").val();
				var cvePresentacionCorr = $("form#seguimientoCorreccionForm #cvePresentaCorreccionHdnSeg").val();
				var indTipoPagoCorr = "1";
			
			
			
				var accion = "seguimiento/ec/pagos.do?periodoInicial=" + periodoInicialPag
			     +"&periodoFinal="+periodoFinalPag+"&folioCorreccion="+folioCorreccionPag
			     +"&idPresentacion="+cvePresentacionCorr+"&indTipoPago="+indTipoPagoCorr+"&fechaMinDateCalendar="+fechaElaboracionPresenta;
	
	
				//regresa mayor a 0 en caso de que se haya guardado un pago en la pantalla de pagos
				var respuesta = openWindowPagosSeguimientoFII(getAppContextParaJS(),accion);
	
				//variable para almacenar el numero de pagos
				numeroPagosPorCorreccion = respuesta;
			
			
				completaRecepcionSolCorr(); // puesta esta instruccion 
				desbloquear();
			}else{
				alert("Cancelo.");
			}
		}
	}
}



/**
 * Función que valida que los datos capturados en las text de tipo porcentaje , sean mayor a cero, y menores o iguales a 100
 * @author Oscar German Beltrán Ortega
 */
function validaPorcentajeConsolImpte(campo,labelMsg){
	var respuesta= true;
	var valorValidar = $("form#formRecepcionSeguimiento #" +campo).val();
	$("form#formRecepcionSeguimiento #" + labelMsg).html('');

	if(valorValidar != ''){
		if(parseInt(valorValidar,10) >0 && parseInt(valorValidar,10)<= 100){
			respuesta = true;
		}else{
		//caso erroneo
			$("form#formRecepcionSeguimiento #" + labelMsg).html('<label class="etiquetaError">El valor debe ser entre 1 y 100 </label>');
			respuesta = false;
			
		}
	}
	
	return respuesta;
}

/**
 * Función que realiza los calculos para obtener el total de Trabajadores
 * regularizados
 * @author Oscar German Beltrán Ortega
 */
function calcTotalTrabRegularizadoConsolImpte(){
	
	var valorTotalReg = 0;
	var valorTrabSub = 0;
	var valorOmisosUni = 0; 
	

	if(document.getElementById("trabSubdeclaUniSegCorr").value != ''){
		valorTrabSub = parseIntComas(document.getElementById("trabSubdeclaUniSegCorr").value,10);
	}
	if(document.getElementById("trabOmisosUniSegCorr").value !=''){
		valorOmisosUni = parseIntComas(document.getElementById("trabOmisosUniSegCorr").value,10);
	}
	
	
	valorTotalReg = valorTrabSub +  valorOmisosUni;
	
	var myNumber = Number(valorTotalReg);
	$("form#formRecepcionSeguimiento #trabRegularizadosRegObraSegCorr").val(myNumber.formatMoney(0, '.', ','));
	validaTrabRegularizadosConsolImpte();
}


/**
 * Función  de validacion de los datos requeridos y datos validos para el tab de regularizar obra
 * @author Oscar German Beltrán Ortega
 */
function validaTrabRegularizadosConsolImpte(){
	
	$("form#formRecepcionSeguimiento #labelTrabRegularizadosRegObraSegCorr").html('');
	var trabajosRegularizados = parseIntComas($("form#formRecepcionSeguimiento #trabRegularizadosRegObraSegCorr").val() != ''?$("form#formRecepcionSeguimiento #trabRegularizadosRegObraSegCorr").val():"0",10);
	var trabajosRevisados = parseIntComas($(jsTrabRevisadosRegularObraConsolImpte).val() != '' ? $(jsTrabRevisadosRegularObraConsolImpte).val():"0",10);
	var respuesta = true;

	//COmentado pq no aplica aqui la regla
	if(trabajosRegularizados <= trabajosRevisados){
		respuesta = true
		
	}else{
		
		$("form#formRecepcionSeguimiento #labelTrabRegularizadosRegObraSegCorr").html('<label class="etiquetaError"  align="rigth">La suma de los trab. Omisos + trab. Subdeclarados No puede ser MAYOR que los Trabajadores Revisados</label>');
		respuesta = false;
	}
	
	
	return respuesta;
}

/**
 * Funcion para validar los datos obligatorios y que los datos sean validas del tab de Recepcion
 * @author Oscar Beltran Ortega
 * @version 1.0.0
 */
function validaDatosGuardarRecepcion(){
	
	var regresa = true;
	var tipoCorreccion = $('form#formRecepcionSeguimiento #cveTipoCorreccionHdn').val();
	var nuFolio = $('form#formRecepcionSeguimiento #nuFolioHdn').val();
	var arrayNumFolio = nuFolio.split("/");
	var tipoFolio = arrayNumFolio[1];
	
	$("form#formRecepcionSeguimiento #labeldocSustenta").html('');
	$("form#formRecepcionSeguimiento #labelPorcAvanceGenSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelPorcRegularizadoSegCor").html('');
	
	$("form#formRecepcionSeguimiento #labelTrabRevisadosSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelSuertePpalDetCopSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelSuertePpalDetRcvSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelTrabOmisosUniSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelTrabSubdeclaUniSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelNumParcialidiSegCorr").html('');
	
	
	//SI ES PAGOS
	if($("form#formRecepcionSeguimiento #cbRecPagoTra").attr('checked') ==  'checked'){
		//regresa = true;
		if(!validaParcialidadesSegCorr()){
			//alert("validacion desde val");
			regresa = false;
		}
		
		
		//valida que Suerte ppal det sea >1 y <= 200 MM
		var suertPpalDetPagoCOP = $("form#formRecepcionSeguimiento #suertePpalDetCopSegCorr").val();
		var suertPpalDEtPagoRCV = $("form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr").val();
		
		
		if(suertPpalDetPagoCOP == '' || !(parseFloatComas(suertPpalDetPagoCOP,10)>1 && parseFloatComas(suertPpalDetPagoCOP,10)<=200000000)){
			$("form#formRecepcionSeguimiento #labelSuertePpalDetCopSegCorr").html('<label class="etiquetaError">El rango debe ser >1 y <= 200 000 000</label>');
			regresa = false;
		}
		
		if(suertPpalDEtPagoRCV == '' || !(parseFloatComas(suertPpalDEtPagoRCV)>=0 && parseFloatComas(suertPpalDEtPagoRCV)<=200000000)){
			$("form#formRecepcionSeguimiento #labelSuertePpalDetRcvSegCorr").html('<label class="etiquetaError">El rango debe ser >= 0 y <= 200 000 000</label>');
			regresa = false;
		}
		
		
		var numParcialidadesSegC=  $("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").val()
		
		if(numParcialidadesSegC == '' || parseInt(numParcialidadesSegC)<1){
			$("form#formRecepcionSeguimiento #labelNumParcialidiSegCorr").html('<label class="etiquetaError">Debe ser mayor a 1 </label>');
		}
		
		//comentado pq es ambiguo CU
		/*if(valCbxComprobConvenio == 'checked'){
			var suertPpalPentPagoCOP = $("form#formRecepcionSeguimiento #suertePpalPenPagoCopSegCorr").val();
			var suertPpalPentPagoRCV = $("form#formRecepcionSeguimiento #suertePpalPenPagoRcvSegCorr").val();
			
			if( (parseInt(suertPpalPentPagoCOP != '' ? suertPpalPentPagoCOP:0) >0 ) || (parseInt(suertPpalPentPagoRCV != '' ? suertPpalPentPagoRCV:0) >0 ) ){
				regresa = true;
			}else{
				regresa = false;
				alert("Suerte principal debe ser mayor a Cero");
			}
			
		}else{
			
		}*/
		
		
	}
	if($("form#formRecepcionSeguimiento #cbRecDocu").attr('checked') !=  'checked'){
		$("form#formRecepcionSeguimiento #labeldocSustenta").html('<label class="etiquetaError">Campo Requerido</label>');
		regresa = false;
	}else {
		
		//regresa = true;
		
	}
	
	if($("form#formRecepcionSeguimiento #cbRecAvisoAfi").attr('checked') ==  'checked'){
		if($("form#formRecepcionSeguimiento #trabRevisadosSegCorr").val() == ''){
			$("form#formRecepcionSeguimiento #labelTrabRevisadosSegCorr").html('<label class="etiquetaError">Campo Requerido</label>');
			regresa = false;
		}
		if($("form#formRecepcionSeguimiento #trabOmisosUniSegCorr").val() == ''){
			$("form#formRecepcionSeguimiento #labelTrabOmisosUniSegCorr").html('<label class="etiquetaError">Campo Requerido</label>');
			regresa = false;
		}
		if($("form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr").val() == ''){
			$("form#formRecepcionSeguimiento #labelTrabSubdeclaUniSegCorr").html('<label class="etiquetaError">Campo Requerido</label>');
			regresa = false;
		}
	}
	
	
	if(tipoFolio =='CCI' || tipoFolio == 'CCE'){
		if($("form#formRecepcionSeguimiento #porcentajeAvanceSegCorr").val() == ''){
			$("form#formRecepcionSeguimiento #labelPorcAvanceGenSegCorr").html('<label class="etiquetaError">Campo Requerido</label>');
			regresa = false;
		}
		if($("form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr").val() == ''){
			$("form#formRecepcionSeguimiento #labelPorcRegularizadoSegCor").html('<label class="etiquetaError">Campo Requerido</label>');
			regresa = false;
		}
		
	}
	
	
	return regresa;
}

function inicializaEstadoInicialPantalla(cveTipoCorreccion,esNuevaRecepcion, seleccionoPagos,data,folioCorreccion){
	
	//para definir el tipo de sol Corr que trae el folio
	var arrayFolio = folioCorreccion.split("/");
	var tipoFolio = arrayFolio[1];
	
	//borra mensajes de validacion 
	$("form#formRecepcionSeguimiento #labeldocSustenta").html('');
	$("form#formRecepcionSeguimiento #labelPorcAvanceGenSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelPorcRegularizadoSegCor").html('');
	$("form#formRecepcionSeguimiento #labelTrabRevisadosSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelSuertePpalDetCopSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelSuertePpalDetRcvSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelTrabOmisosUniSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelTrabSubdeclaUniSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelNumParcialidiSegCorr").html('');
	

	//Preguardado
	if( data!= null && data.fechaPresentacionTxt == '' && data.fechaRegTxt == ''){
		
		//checkbox
		$("form#formRecepcionSeguimiento #cbRecDocu").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #cbRecPagoTra").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #cbRecAvisoAfi").removeAttr('disabled');
			
		$("form#formRecepcionSeguimiento #cbRecDocu").addClass("red");
		$("form#formRecepcionSeguimiento #cbRecPagoTra").addClass("red");
		$("form#formRecepcionSeguimiento #cbRecAvisoAfi").addClass("red");
		
		//estilo observaciones
		$("form#formRecepcionSeguimiento #taRecObservacion").addClass("red");
		$("form#formRecepcionSeguimiento #taRecObservacion").removeAttr('disabled');
		
		//botones
		$("form#formRecepcionSeguimiento #btnGeneraPDFRecepcion").prop("disabled", "disabled");
		$("form#formRecepcionSeguimiento #btnGuardarRecepcion").removeAttr('disabled');
		
		//porcentajes
		$("form#formRecepcionSeguimiento #porcentajeAvanceSegCorr").prop("disabled", "disabled");
		$("form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr").prop("disabled", "disabled");
			
		$("form#formRecepcionSeguimiento #porcentajeAvanceSegCorr").removeClass("red");
		$("form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr").removeClass("red");
		$("form#formRecepcionSeguimiento #spanReqTRabRegulaSegCorr").hide();
		
		//parcialidades
		$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").addClass("red");
			
		$('form#formRecepcionSeguimiento #cbxComprobConvenio').removeAttr('disabled');
		$("form#formRecepcionSeguimiento #cbxComprobConvenio").addClass("red");
			
		$("form#formRecepcionSeguimiento #trabRegularizadosRegObraSegCorr").val('');
			
		$("form#formRecepcionSeguimiento #suertePpalDetCopSegCorr").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #suertePpalDetCopSegCorr").addClass("red");
			
		$("form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr").addClass("red");
		
		//si son CCI o CCE
		if(tipoFolio =='CCE' || tipoFolio =='CCI'){
			//habilitar
			$("form#formRecepcionSeguimiento #porcentajeAvanceSegCorr").removeAttr('disabled');
			$("form#formRecepcionSeguimiento #porcentajeAvanceSegCorr").addClass("red");
				
			$("form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr").removeAttr('disabled');
			$("form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr").addClass("red");
				
			$("form#formRecepcionSeguimiento #spanReqTRabRegulaSegCorr").show();
			$("form#formRecepcionSeguimiento #spanReqTRabRegulaSegCorr").show();
				
		}
		
	}else
	
	if(esNuevaRecepcion){
		
		//checkbox
		$("form#formRecepcionSeguimiento #cbRecDocu").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #cbRecPagoTra").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #cbRecAvisoAfi").removeAttr('disabled');
		
		$("form#formRecepcionSeguimiento #cbRecDocu").attr('checked', false);
		$("form#formRecepcionSeguimiento #cbRecPagoTra").attr('checked', false);
		$("form#formRecepcionSeguimiento #cbRecAvisoAfi").attr('checked', false);
		
		$("form#formRecepcionSeguimiento #cbRecDocu").addClass("red");
		$("form#formRecepcionSeguimiento #cbRecPagoTra").addClass("red");
		$("form#formRecepcionSeguimiento #cbRecAvisoAfi").addClass("red");
		
		//estilo observaciones
		$("form#formRecepcionSeguimiento #taRecObservacion").addClass("red");
		$("form#formRecepcionSeguimiento #taRecObservacion").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #taRecObservacion").val('');
		
		$('form#formRecepcionSeguimiento #fecPresentCorrecConsol').val('');
		//botones
		$("form#formRecepcionSeguimiento #btnGeneraPDFRecepcion").prop("disabled", "disabled");
		$("form#formRecepcionSeguimiento #btnDatosRegularizacionSegCorr").prop("disabled", "disabled");
		$("form#formRecepcionSeguimiento #btnGuardarRecepcion").removeAttr('disabled');
		
		//porcentajes
		$("form#formRecepcionSeguimiento #porcentajeAvanceSegCorr").prop("disabled", "disabled");
		$("form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr").prop("disabled", "disabled");
		$("form#formRecepcionSeguimiento #porcentajeAvanceSegCorr").val('');
			
		$("form#formRecepcionSeguimiento #porcentajeAvanceSegCorr").removeClass("red");
		$("form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr").removeClass("red");
		$("form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr").val('');
		$("form#formRecepcionSeguimiento #spanReqTRabRegulaSegCorr").hide();
		
		//parcialidades
		$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").prop("disabled", "disabled");
		$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").removeClass("red");
		$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").val('');
			
		$('form#formRecepcionSeguimiento #cbxComprobConvenio').prop('disabled','disabled');
		$("form#formRecepcionSeguimiento #cbxComprobConvenio").attr('checked', false);
		$("form#formRecepcionSeguimiento #cbxComprobConvenio").removeClass("red");
			
		$("form#formRecepcionSeguimiento #trabRegularizadosRegObraSegCorr").val('');
			
		$("form#formRecepcionSeguimiento #suertePpalDetCopSegCorr").prop("disabled", "disabled");
		$("form#formRecepcionSeguimiento #suertePpalDetCopSegCorr").removeClass("red");
		$("form#formRecepcionSeguimiento #suertePpalDetCopSegCorr").val('');
			
			
		$("form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr").prop("disabled", "disabled");
		$("form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr").removeClass("red");
		$("form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr").val('');
					
		//los totales de pagos se borran
		$("form#formRecepcionSeguimiento #suertePrincipalCopSegCorr").val('');
		$("form#formRecepcionSeguimiento #suertePrincipalRcvSegCorr").val('');
		
		$("form#formRecepcionSeguimiento #actualizacionCopSegCorr").val('');
		$("form#formRecepcionSeguimiento #actualizacionRcvSegCorr").val('');
		
		$("form#formRecepcionSeguimiento #recargosCopSegCorr").val('');
		$("form#formRecepcionSeguimiento #recargosRcvSegCorr").val('');
		
		$("form#formRecepcionSeguimiento #multasCopSegCorr").val('');
		$("form#formRecepcionSeguimiento #multasRcvSegCorr").val('');
		
		$("form#formRecepcionSeguimiento #totalPagadoCopSegCorr").val('');
		$("form#formRecepcionSeguimiento #totalPagadoRcvSegCorr").val('');
		
		$("form#formRecepcionSeguimiento #suertePpalPenPagoCopSegCorr").val('');
		$("form#formRecepcionSeguimiento #suertePpalPenPagoRcvSegCorr").val('');
		
		//si son CCI o CCE
		if(tipoFolio =='CCE' || tipoFolio =='CCI'){
			//habilitar
			$("form#formRecepcionSeguimiento #porcentajeAvanceSegCorr").removeAttr('disabled');
			$("form#formRecepcionSeguimiento #porcentajeAvanceSegCorr").addClass("red");
			$("form#formRecepcionSeguimiento #porcentajeAvanceSegCorr").val('');
			
				
			$("form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr").removeAttr('disabled');
			$("form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr").addClass("red");
			$("form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr").val('');
		
				
			$("form#formRecepcionSeguimiento #spanReqTRabRegulaSegCorr").show();
			$("form#formRecepcionSeguimiento #spanReqTRabRegulaSegCorr").show();
				
		}
		
	//existe una recepcion
	}else{

		var rolUsuario = $('form#seguimientoCorreccionForm #cveRolUsuario').val();
		
		$('form#formRecepcionSeguimiento input[type=checkbox]').prop('disabled','disabled');
		$('form#formRecepcionSeguimiento input[type=checkbox]').removeClass("red");
		
		$('form#formRecepcionSeguimiento #taRecObservacion').removeClass("red");
		$('form#formRecepcionSeguimiento #taRecObservacion').prop('disabled','disabled');
		
		if(rolUsuario == AUDITOR){
			//bloquear todo menos el btn de pagos
			$('form#formRecepcionSeguimiento input[type=text]').prop('disabled','disabled');
			$('form#formRecepcionSeguimiento input[type=text]').removeClass("red");
			
			$('form#formRecepcionSeguimiento #btnGuardarRecepcion').prop('disabled','disabled');
			
		
		}else 	if(seleccionoPagos && (rolUsuario == JEFE_OF_CORRECCION || rolUsuario == SUPERVISOR_OF_CORRECCION || rolUsuario == JEFE_OF_CORRECCION_Y_DICTAMEN)){
			
			$('form#formRecepcionSeguimiento #taRecObservacion').removeClass("red");
			$('form#formRecepcionSeguimiento #taRecObservacion').prop('disabled','disabled');
			
			$('form#formRecepcionSeguimiento #porcentajeAvanceSegCorr').removeClass("red");
			$('form#formRecepcionSeguimiento #porcentajeAvanceSegCorr').prop('readonly',true);
			
			$('form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr').removeClass("red");
			$('form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr').prop('readonly',true);
			
			$('form#formRecepcionSeguimiento #trabRevisadosSegCorr').addClass("red");
			$('form#formRecepcionSeguimiento #trabRevisadosSegCorr').removeAttr('disabled');
			
			$('form#formRecepcionSeguimiento #trabOmisosUniSegCorr').addClass("red");
			$('form#formRecepcionSeguimiento #trabOmisosUniSegCorr').removeAttr('disabled');
			
			$('form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr').addClass("red");
			$('form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr').removeAttr('disabled');
			
			$('form#formRecepcionSeguimiento #suertePpalDetCopSegCorr').addClass("red");
			$('form#formRecepcionSeguimiento #suertePpalDetCopSegCorr').removeAttr('disabled');
			
			$('form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr').addClass("red");
			$('form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr').removeAttr('disabled');
			
			$('form#formRecepcionSeguimiento #btnGuardarRecepcion').removeAttr('disabled');
			
		}else{
			
			$('form#formRecepcionSeguimiento input[type=text]').prop('disabled','disabled');
			$('form#formRecepcionSeguimiento input[type=text]').removeClass("red");
			$('form#formRecepcionSeguimiento #btnGuardarRecepcion').prop('disabled','disabled');
			$('form#formRecepcionSeguimiento #btnGeneraPDFRecepcion').prop('disabled','disabled');
			
		}
		
		//habilitaPestaniaRecepcion();
	
	}//fin del else
	
}


function seleccionaComprobantePago(){
	
	var seleccioncbxCompPago = $("form#formRecepcionSeguimiento #cbRecPagoTra").attr('checked');
	
	$("form#formRecepcionSeguimiento #labeldocSustenta").html('');
	$("form#formRecepcionSeguimiento #labelPorcAvanceGenSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelPorcRegularizadoSegCor").html('');
	
	$("form#formRecepcionSeguimiento #labelTrabRevisadosSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelSuertePpalDetCopSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelSuertePpalDetRcvSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelTrabOmisosUniSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelTrabSubdeclaUniSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelNumParcialidiSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelTrabRegularizadosRegObraSegCorr").html('');
	
	
	if(seleccioncbxCompPago == 'checked'){
		
		$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").addClass("red");
		
		$("form#formRecepcionSeguimiento #suertePpalDetCopSegCorr").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #suertePpalDetCopSegCorr").addClass("red");
		
		$("form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr").addClass("red");
		
		$("form#formRecepcionSeguimiento #cbxComprobConvenio").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #cbxComprobConvenio").addClass("red");
		
		
		
	}else{
		$("form#formRecepcionSeguimiento #spnDocSusteReq").show();
		
		$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").prop("disabled", "disabled");
		$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").removeClass("red");
		$("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").val('');
		
		$("form#formRecepcionSeguimiento #suertePpalDetCopSegCorr").prop("disabled", "disabled");
		$("form#formRecepcionSeguimiento #suertePpalDetCopSegCorr").removeClass("red");
		$("form#formRecepcionSeguimiento #suertePpalDetCopSegCorr").val('');
		
		$("form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr").prop("disabled", "disabled");
		$("form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr").removeClass("red");
		$("form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr").val('');
		
		$("form#formRecepcionSeguimiento #cbxComprobConvenio").prop("disabled", "disabled");
		$("form#formRecepcionSeguimiento #cbxComprobConvenio").removeClass("red");
		$("form#formRecepcionSeguimiento #cbxComprobConvenio").attr('checked', false);
	}
	
	
}



function continuaFlujoPagos(){

	if($("form#formRecepcionSeguimiento #cveRevRecepcionHdn").val() == ''  ){

		//habilitar la seccion de trabajadores
		$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").addClass("red");
		
		$("form#formRecepcionSeguimiento #trabOmisosUniSegCorr").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #trabOmisosUniSegCorr").addClass("red");
		
		$("form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr").addClass("red");
		
		
	}
	
}


function validaParcialidadesSegCorr(){
	var respuesta = false;
	if($("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").val() != '' ){
		var valorNumParcialid = parseInt($("form#formRecepcionSeguimiento #numeroParcialidadesSegCorr").val());
		
		if(valorNumParcialid>0 && valorNumParcialid <= 48){
			respuesta = true;
			$("form#formRecepcionSeguimiento #labelNumParcialidiSegCorr").html('');
		}else{
			//mensaje de validacion
			respuesta = false
			
			$("form#formRecepcionSeguimiento #labelNumParcialidiSegCorr").html('<label class="etiquetaError">Valor de 1 a 48</label>');
		}
		
	}
	
	return respuesta;
}


/*function completaFlujoComprobConvenio(){
	//var selComprobanteConvenio = $("form#formRecepcionSeguimiento #cbxComprobConvenio").attr('checked');
	
	
	var suertPpalPentPagoCOP = $("form#formRecepcionSeguimiento #suertePpalPenPagoCopSegCorr").val();
	var suertPpalPentPagoRCV = $("form#formRecepcionSeguimiento #suertePpalPenPagoRcvSegCorr").val();
	
	
	
	if(selComprobanteConvenio == 'checked'){
		if( (parseInt(suertPpalPentPagoCOP != '' ? suertPpalPentPagoCOP:0) >0 ) || (parseInt(suertPpalPentPagoRCV != '' ? suertPpalPentPagoRCV:0) >0 ) ){
			//habilita pestaña de Cedula de revision
			setTabHabilitado("seguimientoCorreccionCedRevision");
		}
		
	}else{
		if( (parseInt(suertPpalPentPagoCOP != '' ? suertPpalPentPagoCOP:0) >0 ) || (parseInt(suertPpalPentPagoRCV != '' ? suertPpalPentPagoRCV:0) >0 ) ){
			//habilita pestaña de derivar fiscalizacion
			setTabHabilitado("seguimientoCorreccionDerivarFis");

		}else if( (parseInt(suertPpalPentPagoCOP != '' ? suertPpalPentPagoCOP:0) <= 0 ) || (parseInt(suertPpalPentPagoRCV != '' ? suertPpalPentPagoRCV:0) <= 0 ) ){
			//habilita pestaña de cedula de revision
			setTabHabilitado("seguimientoCorreccionCedRevision");

		}
			
		
	}
}*/


function completaRecepcionSolCorr(){
	var rolUsuario = $('form#seguimientoCorreccionForm #cveRolUsuario').val();

	if(rolUsuario == AUDITOR){
		//bloquear todo menos el btn de pagos
		$('form#formRecepcionSeguimiento input[type=text]').prop('disabled','disabled');
		$('form#formRecepcionSeguimiento input[type=text]').removeClass("red");
		$('form#formRecepcionSeguimiento input[type=checkbox]').prop('disabled','disabled');
		$('form#formRecepcionSeguimiento input[type=checkbox]').removeClass("red");
		$('form#formRecepcionSeguimiento #btnGuardarRecepcion').prop('disabled','disabled');
		
		$('form#formRecepcionSeguimiento #taRecObservacion').removeClass("red");
		$('form#formRecepcionSeguimiento #taRecObservacion').prop('disabled','disabled');
		
	}else 	if(rolUsuario == JEFE_OF_CORRECCION || rolUsuario == SUPERVISOR_OF_CORRECCION || rolUsuario == JEFE_OF_CORRECCION_Y_DICTAMEN){
		
		$('form#formRecepcionSeguimiento input[type=checkbox]').prop('disabled','disabled');
		$('form#formRecepcionSeguimiento input[type=checkbox]').removeClass("red");
		
		$('form#formRecepcionSeguimiento #taRecObservacion').removeClass("red");
		$('form#formRecepcionSeguimiento #taRecObservacion').prop('disabled','disabled');
		
		$('form#formRecepcionSeguimiento #porcentajeAvanceSegCorr').removeClass("red");
		$('form#formRecepcionSeguimiento #porcentajeAvanceSegCorr').prop('disabled','disabled');
		
		$('form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr').removeClass("red");
		$('form#formRecepcionSeguimiento #porcentajeRegularizadoSegCorr').prop('disabled','disabled');
	}
	
	if(numeroPagosPorCorreccion != 0){
		$("form#formRecepcionSeguimiento #cbRecPagoTra").attr('readonly','true');
		$("form#formRecepcionSeguimiento #cbRecPagoTra").removeClass("red");
	
		continuaFlujoPagos();
		obtenerTotalesPagos();

	}
	
	habilitaPestaniaRecepcion();
	
}

function habilitaPestaniaRecepcion(){
	var selDocumentacionSustenta = $("form#formRecepcionSeguimiento #cbRecDocu").attr('checked');
	var selComprobantePago = $("form#formRecepcionSeguimiento #cbRecPagoTra").attr('checked');
	var selComprobAvisosAfilia = $("form#formRecepcionSeguimiento #cbRecAvisoAfi").attr('checked');
	var selComprobanteConvenio = $("form#formRecepcionSeguimiento #cbxComprobConvenio").attr('checked');
	
	var suertPpalAutDetCop =$('form#formRecepcionSeguimiento #suertePpalDetCopSegCorr').val();
	var suertPpalAutDetCopFloat = parseFloatComas(suertPpalAutDetCop != '' ? suertPpalAutDetCop:0); //float
	var suertPpalAutDetRcv =$('form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr').val();
	var suertPpalAutDetRcvFloat = parseFloatComas(suertPpalAutDetRcv != '' ? suertPpalAutDetRcv:0); //FLoat
	
	var suertPpalPentPagoCOP = $("form#formRecepcionSeguimiento #suertePpalPenPagoCopSegCorr").val();
	var suertePpalPendPagCopFloat = parseFloatComas(suertPpalPentPagoCOP != '' ? suertPpalPentPagoCOP:0); //float
	var suertPpalPentPagoRCV = $("form#formRecepcionSeguimiento #suertePpalPenPagoRcvSegCorr").val();
	var suertePpalPendPagRcvFloat = parseFloatComas(suertPpalPentPagoRCV != '' ? suertPpalPentPagoRCV:0); //float
	

	//aqui va la logica de deshabilitacion de componentes y tabs
	if(selDocumentacionSustenta=='checked' && selComprobantePago == undefined && selComprobAvisosAfilia == undefined && selComprobanteConvenio == undefined){
		//setTabHabilitado("seguimientoCorreccionCedRevision");
		//$("form#formRecepcionSeguimiento #btnDatosRegularizacionSegCorr").removeAttr('disabled');
		
	}else if(selDocumentacionSustenta=='checked' && (selComprobantePago == 'checked' || selComprobAvisosAfilia == 'checked') && (selComprobanteConvenio == undefined || selComprobanteConvenio == null)){
		    //habilita pestaña de cedula de revision
		//	setTabHabilitado("seguimientoCorreccionCedRevision");
			$("form#formRecepcionSeguimiento #btnDatosRegularizacionSegCorr").removeAttr('disabled');


	}else if (selDocumentacionSustenta=='checked' && (selComprobantePago == 'checked' || selComprobAvisosAfilia == 'checked') && selComprobanteConvenio == 'checked'){
		//setTabHabilitado("seguimientoCorreccionCedRevision");
		$("form#formRecepcionSeguimiento #btnDatosRegularizacionSegCorr").removeAttr('disabled');
	}
	
	
	//validacion de totales para bloquear btn de pagos de la pestaña de recepcion
	if( ( suertePpalPendPagCopFloat <= 0 && suertPpalAutDetCopFloat > 0 ) 
			&& ( suertePpalPendPagRcvFloat <= 0 && suertPpalAutDetRcvFloat >0 ) ){
		$("form#formRecepcionSeguimiento #btnDatosRegularizacionSegCorr").prop('disabled','disabled');
	}

}

function validaPagosExistentes(){
	
	var cvePresCorreccion =	$('form#seguimientoCorreccionForm #cvePresentaCorreccionHdnSeg').val();
	var sCvePresCorr = '{"cvePresentaCorr":'+cvePresCorreccion+'}';
	var corrSeguimientoVO = jQuery.parseJSON(sCvePresCorr);
	var respuesta = true;
	
	$.postJSON_Sync("correccion/consultaPagosSolCorr.do", corrSeguimientoVO, function(data) {
		numeroPagosPorCorreccion = data.length;
	}).error(function(data) {
		validarSesionExpirada(data);
		alert("error"+data);
	}).complete(function(){				
		desbloquear();
	});	
	
	return respuesta;
}


function obtenerTotalesPagos(){

	var nuFolioCorreccion =	$('form#seguimientoCorreccionForm #nuFolioSegCorrHdn').val();
	var sNuFolioCorr = '{"nuFolio":"'+nuFolioCorreccion+'",'+
	'"banderaTipoPago":"1"}';
	
	var corrSeguimientoVO = jQuery.parseJSON(sNuFolioCorr);
	var respuesta = true;
	
	$.postJSON_Sync("correccion/obtenerTotalesPagosSolCorr.do", corrSeguimientoVO, function(data) {
	  
		if(data != null){
			
			var myNumber = Number(data.impCopsp);
			$('form#formRecepcionSeguimiento #suertePrincipalCopSegCorr').val(myNumber.formatMoney(2, '.', ','));
			myNumber = Number(data.impRcvsp);
			$('form#formRecepcionSeguimiento #suertePrincipalRcvSegCorr').val(myNumber.formatMoney(2, '.', ','));
			
			myNumber = Number(data.impCopact);
			$('form#formRecepcionSeguimiento #actualizacionCopSegCorr').val(myNumber.formatMoney(2, '.', ','));
			myNumber = Number(data.impRcvact);
			$('form#formRecepcionSeguimiento #actualizacionRcvSegCorr').val(myNumber.formatMoney(2, '.', ','));
			
			myNumber = Number(data.impCoprec);
			$('form#formRecepcionSeguimiento #recargosCopSegCorr').val(myNumber.formatMoney(2, '.', ','));
			myNumber = Number(data.impRcvrec);
			$('form#formRecepcionSeguimiento #recargosRcvSegCorr').val(myNumber.formatMoney(2, '.', ','));
		
			myNumber = Number(data.impCopmulta);
			$('form#formRecepcionSeguimiento #multasCopSegCorr').val(myNumber.formatMoney(2, '.', ','));
			myNumber = Number(data.impRcvmulta);
			$('form#formRecepcionSeguimiento #multasRcvSegCorr').val(myNumber.formatMoney(2, '.', ','));
			
			myNumber = Number(data.impCoptot);
			$('form#formRecepcionSeguimiento #totalPagadoCopSegCorr').val(myNumber.formatMoney(2, '.', ','));
			myNumber = Number(data.impRcvtot);
			$('form#formRecepcionSeguimiento #totalPagadoRcvSegCorr').val(myNumber.formatMoney(2, '.', ','));
		
			calculaSuertePpalPentPago(data);
			
			//RN08
			var selCbxPagos = $("form#formRecepcionSeguimiento #cbRecPagoTra").attr('checked');
			var suertPpalAutDetCop =$('form#formRecepcionSeguimiento #suertePpalDetCopSegCorr').val();
			var suertPpalAutDetCopFloat = parseFloatComas(suertPpalAutDetCop != '' ? suertPpalAutDetCop:0); //float
			var suertPpalAutDetRcv =$('form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr').val();
			var suertPpalAutDetRcvFloat = parseFloatComas(suertPpalAutDetRcv != '' ? suertPpalAutDetRcv:0); //FLoat
			
			var suertePpalPendPagCop = $('form#formRecepcionSeguimiento #suertePpalPenPagoCopSegCorr').val();
			var suertePpalPendPagCopFloat = parseFloatComas(suertePpalPendPagCop != '' ? suertePpalPendPagCop:0); //float
			var suertePpalPendPagRcv = $('form#formRecepcionSeguimiento #suertePpalPenPagoRcvSegCorr').val();
			var suertePpalPendPagRcvFloat = parseFloatComas(suertePpalPendPagRcv != '' ? suertePpalPendPagRcv:0); //float
			
			if(selCbxPagos == 'checked' && ((suertPpalAutDetCopFloat >0 && suertePpalPendPagCopFloat==0 ) 
					&& (suertPpalAutDetRcvFloat >0 && suertePpalPendPagRcvFloat==0 ))  ){
				$('form#formRecepcionSeguimiento #estatusPresentaCorrecConsolida').val("Correccion Pagada");
			}
			
			if( ( suertePpalPendPagCopFloat <= 0 && suertPpalAutDetCopFloat > 0 ) 
					&& ( suertePpalPendPagRcvFloat <= 0 && suertPpalAutDetRcvFloat >0 ) ){
				$("form#formRecepcionSeguimiento #btnDatosRegularizacionSegCorr").prop('disabled','disabled');
			}

			generaPagosResumen(data);
			
			
			if(rolIsJefe){
				ejecutaReglasValidacion(1);				 
			}
		}

	}).error(function(data) {
		validarSesionExpirada(data);
		alert("error"+data);
	}).complete(function(){				
		desbloquear();
	});	
	
	return respuesta;
}


function calculaSuertePpalPentPago(data){
	
	//calculo de Suerte ppal pent pago
	//Suerte Ppal Aut - Suerte ppal pagada
	var suertPpalAutDetCop =$('form#formRecepcionSeguimiento #suertePpalDetCopSegCorr').val() != '' ?$('form#formRecepcionSeguimiento #suertePpalDetCopSegCorr').val():"0";
	var suertPpalAutDetRcv =$('form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr').val() != '' ? $('form#formRecepcionSeguimiento #suertePpalDetRcvSegCorr').val():"0";
	var suertePpalPentPagoCop = null;
	var suertePpalPentPagoRcv = null;
	if(data == null){	
	 suertePpalPentPagoCop = parseFloatComas(suertPpalAutDetCop) - parseFloat($('form#formRecepcionSeguimiento #suertePrincipalCopSegCorr').val());
	 suertePpalPentPagoRcv = parseFloatComas(suertPpalAutDetRcv) - parseFloat($('form#formRecepcionSeguimiento #suertePrincipalRcvSegCorr').val());
	} else{
		suertePpalPentPagoCop = parseFloatComas(suertPpalAutDetCop) - parseFloatComas(data.impCopsp);
		 suertePpalPentPagoRcv = parseFloatComas(suertPpalAutDetRcv) - parseFloatComas(data.impRcvsp);
	}
	var myNumber = Number(suertePpalPentPagoCop);
	$('form#formRecepcionSeguimiento #suertePpalPenPagoCopSegCorr').val(myNumber.formatMoney(2, '.', ',')); 
	myNumber = Number(suertePpalPentPagoRcv);
	$('form#formRecepcionSeguimiento #suertePpalPenPagoRcvSegCorr').val(myNumber.formatMoney(2, '.', ',')); 
}



function seleccionaAvisoAfilia(){
	
	var seleccioncbxAvisoAfil = $("form#formRecepcionSeguimiento #cbRecAvisoAfi").attr('checked');

	$("form#formRecepcionSeguimiento #labelTrabRevisadosSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelTrabOmisosUniSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelTrabSubdeclaUniSegCorr").html('');
	$("form#formRecepcionSeguimiento #labelTrabRegularizadosRegObraSegCorr").html('');
	
	
	if(seleccioncbxAvisoAfil == 'checked'){
		$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").addClass("red");
		//$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").val('');
			
		$("form#formRecepcionSeguimiento #trabOmisosUniSegCorr").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #trabOmisosUniSegCorr").addClass("red");
		//$("form#formRecepcionSeguimiento #trabOmisosUniSegCorr").val('');
			
		$("form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr").addClass("red");
		//$("form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr").val('');
			
		//$("form#formRecepcionSeguimiento #trabRegularizadosRegObraSegCorr").val('');
	}else{
		$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").prop("disabled", "disabled");
		$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").removeClass("red");
		//$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").val('');
			
			
		$("form#formRecepcionSeguimiento #trabOmisosUniSegCorr").prop("disabled", "disabled");
		$("form#formRecepcionSeguimiento #trabOmisosUniSegCorr").removeClass("red");
		$("form#formRecepcionSeguimiento #trabOmisosUniSegCorr").val('');
			
			
		$("form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr").prop("disabled", "disabled");
		$("form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr").removeClass("red");
		$("form#formRecepcionSeguimiento #trabSubdeclaUniSegCorr").val('');
			
		$("form#formRecepcionSeguimiento #trabRegularizadosRegObraSegCorr").val('');
		
	}
		
	
}

function seleccionaDocSustenta(){
	var seleccioncbxAvisoAfil = $("form#formRecepcionSeguimiento #cbRecDocu").attr('checked');

	if(seleccioncbxAvisoAfil == 'checked'){
		$("form#formRecepcionSeguimiento #labeldocSustenta").html('');
		$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").removeAttr('disabled');
		$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").addClass("red");
		$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").val('');
	}else{
		$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").prop("disabled", "disabled");
		$("form#formRecepcionSeguimiento #trabRevisadosSegCorr").removeClass("red");
	}
}

function validaFechaElaboraPresenta(){
	var resultado = false;
	var fechaElaboracionPresenta = $('form#seguimientoCorreccionForm #fecElaboraPreSegCorrMainHdn').val();
	
	if(fechaElaboracionPresenta == null || fechaElaboracionPresenta == undefined  || fechaElaboracionPresenta == ""){
		resultado = false;
	}else{
		resultado = true
	}
	
	return resultado;
	
}


function generaResumenRecepcion(data){
	
	$('form#formResumenSeguimiento #lbValFechaAutSolCorr').text(data.fechaRegTxt);
	
	
}


function generaPagosResumen(data){
	
	var totalSuertePrincipal=parseFloat(data.impCopsp)+parseFloat(data.impRcvsp);	
	var totalActualizaciones=parseFloat(data.impCopact)+parseFloat(data.impRcvact);
	var totalRecargos=parseFloat(data.impCoprec)+parseFloat(data.impRcvrec);
	var totalMultas=parseFloat(data.impCopmulta)+parseFloat(data.impRcvmulta);
	var totalTotales=parseFloat(data.impCoptot)+parseFloat(data.impRcvtot);
	
	drawData("#lbCopSPVal",data.impCopsp);
	drawData("#lbRcvSPVal",data.impRcvsp);
	
	drawData("#lbCopActVal",data.impCopact);
	drawData("#lbRcvActVal",data.impRcvact);
	
	drawData("#lbCopRecVal",data.impCoprec);
	drawData("#lbRcvRecVal",data.impRcvrec);
	
	drawData("#lbCopMulVal",data.impCopmulta);
	drawData("#lbRcvMulVal",data.impRcvmulta);


	drawData("#lbCopTotaVal",data.impCoptot);
	drawData("#lbRcvTotaVal",data.impRcvtot);

	
	drawData("#lbTotalSPVal",totalSuertePrincipal);	
	drawData("#lbTotalActVal",totalActualizaciones);	
	drawData("#lbTotalRecVal",totalRecargos);
	drawData("#lbTotalMulVal",totalMultas);
	drawData("#lbTotalTotaVal",totalTotales);
	
	

}
