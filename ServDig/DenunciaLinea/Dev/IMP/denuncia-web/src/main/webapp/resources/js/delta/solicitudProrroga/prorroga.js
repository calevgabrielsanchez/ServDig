/**
 * JS para Solicitud de Prorroga Modificado
 */

var idDgValida 	= "#dgProrrogaValida";
var idDataTable 	= "#dtSolicitudCorreccion";

//Objeto del DataTable
var oDtProrroga;

//Dialogos
var oDgValida;


$(document).ready(function() {
	
	
	 // Fecha de Inicio y Termino
	 $( "#txFechaInicialInput, #txFechaFinalInput, #txFecElaboracionInput, #txFechaLimiteInput, #txfecFechaRecepcionOficioInput  " ).datepicker( { dateFormat: 'yy-mm-dd' });
	
	/**
	 * Inicializacion del data table
	 */
	oDtProrroga = $(idDataTable).dataTable({
		bJQueryUI : true,
		bFilter : false,
		bInfo:true,
		bSort: false,
		"bPaginate": true,
		"bAutoWidth" : true,
		"bServerSide" :	true,
		"aoColumns" : [ {
			fnRender :function(oObj){
				var retVal = '<input type="radio" value="' +
				oObj.aData['nuFolio'] +'" id="radioTable" class="radioClase" name="radio" onclick="muestraProrrogaDiv()"/> ';
				return retVal;
			}, 
			aTargets: [0]
			},{
				"sTitle" : "Folio Correcci&oacute;n ",
				"mDataProp" : "nuFolio",
				"sClass": "dtCenterClassColumn"
			}, {
				"sTitle" : "Registro Patronal",
				"mDataProp" : "registroPatronal",
				"sClass":"dtJustifyClassColumn"
			}
			, {
				"sTitle" : "Fecha Limite",
				"mDataProp" : "fecFechaLimite",
				"sClass":"dtJustifyClassColumn"
			}
			],"bProcessing" : true,
			"sAjaxSource" : 'prorroga/paginar.do',
			"fnServerData" : function(sSource, aoData, fnCallback) {	
				aoData.push({
					"name" : "sSearch",
					"value" : $('#registroPatronalInput').val()
				});
								
				var wrapper = new Object();
				wrapper.aoData = aoData;								
		
				var oForm = $("#prorrogaForm").toObject({mode:'first'});
				wrapper.oForm = oForm;
				
				$.postJSON(sSource, wrapper, function(data) {										
					fnCallback(data);					
				});
			}
		});
	
	
	// Dialog de Elemento Nuevo			
	oDgValida = $(idDgValida).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		width: 1000,
		beforeClose :function(event,ui){
		    limpiarFormulario("#prorrogaFormDatos");
		},
		buttons: {
			"Registrar": function() {	
				
				if(!tieneDato($("#motivoInput").val(),"Motivo")) return false;
				if(!tieneDato($("#txRepresentanteLegalInput").val(),"Representante Legal")) return false;
				if(!tieneDato($("#txLugarInput").val(),"Lugar")) return false;
				if(!verificarComentario()) return false;
				
					
				bloquear();
				
				abrirDialogoFirma();
				
				desbloquear();
										 
			}, 
			"Cancelar": function() { 
				$(this).dialog("close"); 
			} 
		}
	});
	
	
	$("#dgProrrogaCaptura").hide();
	$("#nuFolioInput").focus();
	$('#nuFolioInput').val('');
	$('#registroPatronalInput').val('');
	
});//$(document).ready(function()


function valida(){
	oDgValida.dialog('open');
}


function consultarPorFolio(){		
	// Buscamos el elemento
	var idFolio = $('#nuFolioInput').val();
	var sFolio= '{"nuFolio":'+'"'+idFolio+'"}';
	var crtAnexosolcorrpat = jQuery.parseJSON(sFolio);
	$.postJSON("prorroga/consultarPorFolio.do", crtAnexosolcorrpat, function(data) {
		if(data==null){
			alert('Folio de Correcion No encontrado'); }
		else if(data.estadoFolioCorr==1){
			alert('La Solicitud de la Correcion No ha sido aceptada');
		}
		else if(data.estadoFolioCorr==2){
			alert('La Fecha limite para la Solicitud ha vencido');
		}
		else if(data.estadoFolioCorr==3){
			alert('La Solicitud de la Correcion ya cuenta con una Prorroga');
		}
		else if(data.estadoFolioCorr==4){
			alert('La Solicitud de la Correcion ya ha sido Presentada');
		}
		else{
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txCurpInput').val(data.txCurp);			
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txRfcInput').val(data.txRfc);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txRazonSocialInput').val(data.txRazonSocial);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#telefonoRegPatInput').val(data.txTelefono);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txRegistroPatronalInput').val(data.registroPatronal);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txdigitoVerificadorInput').val(data.digitoVerificador);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txNuFolio').val(data.nuFolio);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#emailRegPatInput').val(data.txEmail);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#cveSolicitudCorr').val(data.cveSolicitudCorr);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txtipoCorreccionInput').val(data.tipoCorreccion);
			
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txFechaInicialInput').val(data.solicitudCorreccion.fecFechaPeriodoIni);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txFechaFinalInput').val(data.solicitudCorreccion.fecFechaPeriodoFin);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txFecElaboracionInput').val(data.solicitudCorreccion.fecFechaElacoracionCorreccion);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txFechaLimiteInput').val(data.solicitudCorreccion.fecFechaLimite);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txfecFechaRecepcionOficioInput').val(data.solicitudCorreccion.fecFechaRecepcionOficio);
			
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#calleRegPatInput').val(data.calle);
			
			if(data.numExterior==null)
				$('#wrapperDatosProrroga,#prorrogaFormDatos,#numExteriorRegPatInput').val(data.numExteriorAlfa);
			else
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#numExteriorRegPatInput').val(data.numExterior);
			
			if(data.numInterior==null)
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#numInteriorRegPatInput').val(data.numInteriorAlfa);
			else
				$('#wrapperDatosProrroga,#prorrogaFormDatos,#numInteriorRegPatInput').val(data.numInterior);
			
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#coloniaRegPatInput').val(data.colonia);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#municipioRegPatInput').val(data.municipio);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#localidadRegPatInput').val(data.localidad);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#entidadFederativaRegPatInput').val(data.entidadFederativa);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#codigoPostalRegPatInput').val(data.codigoPostal);
			
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#hiddenNumExterior').val(data.numExterior);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#hiddenNumExteriorAlfa').val(data.numExteriorAlfa);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#hiddenNumInterior').val(data.numInterior);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#hiddeNumInteriorAlfa').val(data.numInteriorAlfa);
			
			oDgValida.dialog('open');
			}	
	}).error(function(data){ 
		validarSesionExpirada(data);
		alert("error" + data);
	});
}

function consultaGral(){	
	if(!(validaDato($("#nuFolioInput").val()))&&!(validaDato($("#registroPatronalInput").val())))
		alert("Capturar Criterio de Busqueda [Folio o Registro Patronal]");
	else if(validaDato($("#nuFolioInput").val())){// consultar por folio
		consultarPorFolio();
	}else if(validaDato($("#registroPatronalInput").val())){// Consultar por Registro Patronal
		if(!longitudMandatoria($("#registroPatronalInput").val(),11,"Registro Patronal")) return false;
		$("#dgProrrogaCaptura").hide();
		//oDtProrroga.fnDraw();
		consultar();
	}
}

function validaDato(campo){
	if((campo == null) || (campo =="")){
		return false;
	}else
		return true;
}


function porRegistroPat(){		
	// Buscamos el elemento
	var idFolio = $('#:checked').val();
	var sFolio= '{"nuFolio":'+'"'+idFolio+'"}';
	var crtAnexosolcorrpat = jQuery.parseJSON(sFolio);
	$.postJSON("prorroga/consultarPorFolio.do", crtAnexosolcorrpat, function(data) {
		if(data==null){
			alert('Folio de Solicitud de la Correcion No encontrado'); }
		else if(data.estadoFolioCorr==1){
			alert('La Solicitud de la Correcion No ha sido aceptada');
		}
		else if(data.estadoFolioCorr==2){
			alert('La Fecha limite para la Solicitud ha vencido');
		}
		else if(data.estadoFolioCorr==3){
			alert('La Solicitud de la Correcion ya cuenta con una Prorroga');
		}
		else{
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txCurpInput').val(data.txCurp);			
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txRfcInput').val(data.txRfc);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txRazonSocialInput').val(data.txRazonSocial);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#telefonoRegPatInput').val(data.txTelefono);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txRegistroPatronalInput').val(data.registroPatronal);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txdigitoVerificadorInput').val(data.digitoVerificador);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txNuFolio').val(data.nuFolio);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#emailRegPatInput').val(data.txEmail);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#cveSolicitudCorr').val(data.cveSolicitudCorr);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txtipoCorreccionInput').val(data.tipoCorreccion);
			
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txFechaInicialInput').val(data.solicitudCorreccion.fecFechaPeriodoIni);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txFechaFinalInput').val(data.solicitudCorreccion.fecFechaPeriodoFin);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txFecElaboracionInput').val(data.solicitudCorreccion.fecFechaElacoracionCorreccion);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txFechaLimiteInput').val(data.solicitudCorreccion.fecFechaLimite);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txfecFechaRecepcionOficioInput').val(data.solicitudCorreccion.fecFechaRecepcionOficio);
			
			
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#calleRegPatInput').val(data.calle);
			
			if(data.numExterior==null)
				$('#wrapperDatosProrroga,#prorrogaFormDatos,#numExteriorRegPatInput').val(data.numExteriorAlfa);
			else
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#numExteriorRegPatInput').val(data.numExterior);
			
			if(data.numInterior==null)
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#numInteriorRegPatInput').val(data.numInteriorAlfa);
			else
				$('#wrapperDatosProrroga,#prorrogaFormDatos,#numInteriorRegPatInput').val(data.numInterior);
			
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#coloniaRegPatInput').val(data.colonia);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#municipioRegPatInput').val(data.municipio);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#localidadRegPatInput').val(data.localidad);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#entidadFederativaRegPatInput').val(data.entidadFederativa);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#codigoPostalRegPatInput').val(data.codigoPostal);
			
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#hiddenNumExterior').val(data.numExterior);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#hiddenNumExteriorAlfa').val(data.numExteriorAlfa);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#hiddenNumInterior').val(data.numInterior);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#hiddeNumInteriorAlfa').val(data.numInteriorAlfa);
						
			oDgValida.dialog('open');
			}	
	}).error(function(data){
		validarSesionExpirada(data);
		alert("error" + data);
	});
}

//Inicializa el paginador
function inicializaPosicionPaginador(){
	oDtProrroga.fnDisplayStart(0);
}

function datosRegPatronal(){
	$('#nuFolioInput').val('');
}

function datosNumeroFolio(){
	$('#registroPatronalInput').val('');
}

function muestraProrrogaDiv(){
	$("#dgProrrogaCaptura").show("fast");
}

function verificarComentario() {
    var comentario = $('#motivoInput').val();
       
    
    if ( comentario.length > 1000 ) {
        alert('El motivo debe ser menor o igual a 1000 Caracteres, Caracteres:' + comentario.length);
        return false;
    }
    
    return true;
}




function consultar(){		
	// Buscamos el elemento
	var idRegistro = $('#registroPatronalInput').val();
	var sRegistro= '{"registroPatronal":'+'"'+idRegistro+'"}';
	var crtAnexosolcorrpat = jQuery.parseJSON(sRegistro);
	$.postJSON("prorroga/consultar.do", crtAnexosolcorrpat, function(data) {
		if(data==null){
			alert('No se han encontrado Folios de Correccion para el Registro Patronal'); }
		else
			oDtProrroga.fnDraw();		
	}).error(function(data){
		validarSesionExpirada(data);
		alert("error" + data);
	});
}

function getCadenaOriginal(){
	return 'vladimiraguirrepiedragil|60321975|AUPV8102095Q4';
}


function registrarFirmado() {
	
	bloquear();
	
	var cert = validaTipoCertificado(document.forms.formaFirma.certificado.value);

	 $("#prorrogaFormDatos #firmaElectronica").val(firmaDigitalResp);
	 $("#prorrogaFormDatos #cadenaOriginal").val(getCadenaOriginal());
	 
	 $("#prorrogaFormDatos #tipoCertificado").val(cert);
	 
	var crtAnexosolcorrpat = $("#prorrogaFormDatos").toObject({mode:'first'});
	
	$.postJSON("prorroga/agregar.do", crtAnexosolcorrpat, function(data) {
		
		verifyCustomDataError(data);
		if(data.error==""){
			alert('SE HA RECIBIDO LA SOLICITUD DE LA PRORROGA EXITOSAMENTE\n' + 
			'Tiene que acudir a su subdelegacion para obtener la respuesta a su solicitud');
			
			inicializaPosicionPaginador();	
			//desbloquear();		
			document.forms[0].submit();
		}
		
	}).error(function(data){
		desbloquear();
		validarSesionExpirada(data);
		alert("error" + data);
	}).complete(function(data){
		desbloquear();
		
	});
	
	
	oDgValida.dialog("close");
}