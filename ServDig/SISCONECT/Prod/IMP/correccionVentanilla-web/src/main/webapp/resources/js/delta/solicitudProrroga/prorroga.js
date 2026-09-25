/**
 * JS para Solicitud de Prorroga Modificado
 */

var idDgValida 	= "#dgProrrogaValida";
var idDataTable 	= "#dtSolicitudCorreccion";
var crtAnexoConsulta;
//Objeto del DataTable
var oDtProrroga;

//Dialogos
var oDgValida;
var selloDigitalImssProrroga;
var ROL_USUARIO_INTERNET=5;



var motivoVentana;
var lugarVentana;
var representanteLegalVentana;

$(document).ready(function() {
	
	errorDialog = $("#dialog-error").dialog({
		autoOpen: false,
		modal: true,
		resizable: true,
		width: 930,
		buttons: {
			Ok: function() {
				$(this).dialog("close");
			}
		}
	});
	
	 // Fecha de Inicio y Termino
	 //$( "#txFechaInicialInput, #txFechaFinalInput, #txFecElaboracionInput, #txFechaLimiteInput, #txfecFechaRecepcionOficioInput  " ).datepicker( { dateFormat: 'dd-mm-yy' });
	
	 
//	 $("#txFechaInicialInput, #txFechaFinalInput, #txFecElaboracionInput, #txFechaLimiteInput, #txfecFechaRecepcionOficioInput  " ).datepicker('option', 'maxDate', getFechaServidor());
//	 $("#txFechaInicialInput, #txFechaFinalInput, #txFecElaboracionInput, #txFechaLimiteInput, #txfecFechaRecepcionOficioInput  " ).datepicker('option', 'minDate', getFechaServidorMenos45Dias());
		
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
		"iDeferLoading": 0,
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
				"sClass":"dtCenterClassColumn"
			}
			, {
				"sTitle" : "Fecha L\u00edmite",
				"mDataProp" : "fecFechaLimite",
				"sClass":"dtCenterClassColumn"
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
				 }).error(function(datas){ 
						validarSesionExpirada(datas);
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
				
					
				motivoVentana=$("#motivoInput").val();
				lugarVentana=$("#txRepresentanteLegalInput").val();
				representanteLegalVentana=$("#txLugarInput").val();
				
		//		bloquear();
				
		//		abrirDialogoFirma();
				//registrarFirmado();
				bloquear();
				$(this).dialog("close"); 
				if((recuperarRolUsuario()!=ROL_USUARIO_INTERNET)){
					selloDigitalImssProrroga=recuperaSelloImssProrroga();
					if(selloDigitalImssProrroga==null){
						alert("No se pudo firmar el documento,vuelva a intentar mas tarde");
						desbloquear();
						return;
					}
					recuperaMensaje(TRAMITE_PRORROGA);
					objMensajeFirma.cveMensaje=null;
					
					registrarFirmado(selloDigitalImssProrroga.tramite,selloDigitalImssProrroga.sello,generaCadenaOriginalProrroga());							
				}else{
					if(confirm(recuperaMensaje(TRAMITE_PRORROGA))){
						var param=generaParamFirmaProrroga();
						if(param!=null){
							firmarDocumentoSISCONET('contenedorFirmaProrroga',callBackFirmaProrroga,param);
						}else{
							alert("No se puede generar el documento,favor de reintentar");
						}
							
					}
				}
				
				
				
			
				
				$(this).dialog("close"); 
				
		//		desbloquear();
										 
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
	
	
	 $.postJSON("prorroga/obtenerUsuarioSession.do", null,function(data) {
		
		 if(data!=null && data.registroPatronal != null){
			 $('#registroPatronalInput').val(data.registroPatronal);
			 $('#registroPatronalInput').prop("readonly", "readonly");
		 }
		 
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(data){
			

			
		});
	
	 
	 triggerPatronInternet('','nuFolioInput','btnBuscarProrroga');
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
			alert('Folio de Correci\u00f3n No encontrado'); }
		else if(data.estadoFolioCorr==1){
			alert('La Solicitud de la Correci\u00f3n No ha sido aceptada');
		}
		else if(data.estadoFolioCorr==2){
			alert('La Fecha limite para la Solicitud ha vencido');
		}
		else if(data.estadoFolioCorr==3){
			alert('La Solicitud de la Correci\u00f3n ya cuenta con una Prorroga');
		}
		else if(data.estadoFolioCorr==4){
			alert('La Solicitud de la Correci\u00f3n ya ha sido Presentada');
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
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#txfecFechaRecepcionOficioInput').val(data.solicitudCorreccion.fechaRecepcionOficio);
			
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
			
              // agregados para firma electr�nica
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#cveNroRegObra').val(data.solicitudCorreccion.cveNumeroRegObra);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#idTipoDeSolicitud').val(data.solicitudCorreccion.idTipoSolicitud);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#idSubDelegacion').val(data.solicitudCorreccion.cveSubdelegacion);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#fechaCadenaOriginal').val(data.fechaCadenaOriginal);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#cveDelegacion').val(data.usuarioFirmado.cveCodigoDelegacion);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#cveSubDelegacion').val(data.usuarioFirmado.cveCodigoSubDelegacion);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#numeroTrabajadores').val(data.solicitudCorreccion.numTrabajadores);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#actividad').val(data.txActividad);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#clase').val(data.txClase);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#fraccion').val(data.txFraccion);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#prima').val(data.txPrima);
			
			
			oDgValida.dialog('open');
			crtAnexoConsulta=data;
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
			alert('Folio de Solicitud de la Correci\u00f3n No encontrado'); }
		else if(data.estadoFolioCorr==1){
			alert('La Solicitud de la Correci\u00f3n No ha sido aceptada');
		}
		else if(data.estadoFolioCorr==2){
			alert('La Fecha limite para la Solicitud ha vencido');
		}
		else if(data.estadoFolioCorr==3){
			alert('La Solicitud de la Correci\u00f3n ya cuenta con una Prorroga');
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
			
            // agregados para firma electr�nica
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#cveNroRegObra').val(data.solicitudCorreccion.cveNumeroRegObra);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#idTipoDeSolicitud').val(data.solicitudCorreccion.idTipoSolicitud);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#idSubDelegacion').val(data.solicitudCorreccion.cveSubdelegacion);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#fechaCadenaOriginal').val(data.fechaCadenaOriginal);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#cveDelegacion').val(data.usuarioFirmado.cveCodigoDelegacion);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#cveSubDelegacion').val(data.usuarioFirmado.cveCodigoSubDelegacion);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#numeroTrabajadores').val(data.solicitudCorreccion.numTrabajadores);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#actividad').val(data.txActividad);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#clase').val(data.txClase);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#fraccion').val(data.txFraccion);
			$('#wrapperDatosProrroga,#prorrogaFormDatos,#prima').val(data.txPrima);
			crtAnexoConsulta=data;
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
	 $.postJSON("prorroga/obtenerUsuarioSession.do", null,function(data) {
		 
		 if(data!=null && data.registroPatronal != null){
			 $('#registroPatronalInput').val(data.registroPatronal);
			 $('#registroPatronalInput').prop("readonly", "readonly");
		 }
		 
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(data){
		});

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
			alert('No se han encontrado Folios de Correcci�n para el Registro Patronal'); }
		else {
			resetDisplayStart(oDtProrroga);
			oDtProrroga.fnDraw();
		}	
	}).error(function(data){
		validarSesionExpirada(data);
		alert("error" + data);
	});
}

function getCadenaOriginal(){
	var cveDelegacion = $('#cveDelegacion').val();	
	cveDelegacion =  $.trim(cveDelegacion);	
	if(cveDelegacion.length=1){
		cveDelegacion = '0' + cveDelegacion;
	}
	
	var cveSubDelegacion = $('#cveSubDelegacion').val();
	cveSubDelegacion =  $.trim(cveSubDelegacion);	
	if(cveSubDelegacion.length=1){
		cveSubDelegacion = '0' + cveSubDelegacion;
	}
	

	var idSubDelegacion = $('#idSubDelegacion').val();
	idSubDelegacion =  $.trim(idSubDelegacion);	

	var regPatronCorregir = $('#txRegistroPatronalInput').val();
	regPatronCorregir =  $.trim(regPatronCorregir);
	
	var regPatronFiscal = '';

	var unoVariosRp = '';

	var idTipoSolicitud = $('#idTipoDeSolicitud').val();
	idTipoSolicitud =  $.trim(idTipoSolicitud);

	var fechaAntecedente = '';  // checar
	
	var antecedente = ''; 	
	if($('#radioEspontanea').checked=true){
		antecedente='ESPONTANEA';
	}else if($('#radioInvitacion').checked=true){
		antecedente='INVITACION';
		fechaAntecedente= $('#fechaAceptacionInvitacionCorreccion').val();
	}
	
	var fecIni = $('#txFechaInicialInput').val();
	fecIni =  $.trim(fecIni);
	
	var fecFin = $('#txFechaFinalInput').val();
	fecFin =  $.trim(fecFin);
	

	var obraSatic = $('#cveNroRegObra').val();
	obraSatic =  $.trim(obraSatic);	

	var actividadRegPatInput = $('#actividad').val();
	actividadRegPatInput =  $.trim(actividadRegPatInput);
	
	var claseRegPatInput = $('#clase').val();
	claseRegPatInput =  $.trim(claseRegPatInput);
	var fraccionRegPatInput = $('#fraccion').val();
	fraccionRegPatInput =  $.trim(fraccionRegPatInput);
	var primaRegPatInput = $('#prima').val();
	primaRegPatInput =  $.trim(primaRegPatInput);

	var txRepLegalInput = $('#txRepresentanteLegal').val();
	txRepLegalInput =  $.trim(txRepLegalInput);
	
	var trabajadoresDom = $('#numeroTrabajadores').val();
	trabajadoresDom =  $.trim(trabajadoresDom);
	
	var fechaHora = $('#fechaCadenaOriginal').val();
	fechaHora =  $.trim(fechaHora);
	
	var tipoDocumento= 'CORP-003'
	var procedencia= $('#procedencia').val();
	procedencia =  $.trim(procedencia);
	
	var resultado = cveDelegacion + cveSubDelegacion + '|' + idSubDelegacion + '|' + regPatronCorregir + '|'+regPatronFiscal +'|';
	resultado = resultado + unoVariosRp + '|' + idTipoSolicitud + '|'+antecedente +'|';	
	resultado = resultado + fecIni + '|' + fecFin + '|' + fechaAntecedente +'|';	
	resultado = resultado + obraSatic + '|' + actividadRegPatInput + '|' + claseRegPatInput +'|';	
	resultado = resultado + fraccionRegPatInput+'|'+ primaRegPatInput +'|'+ txRepLegalInput +'|'+ fechaHora +'|';
	resultado = resultado + tipoDocumento+'|'+ procedencia;

	return resultado;
}


function registrarFirmado(sello, selloIMSS, cadenaOriginal,urlAcuseFirmado) {
	
	bloquear();
	
//	var cert = validaTipoCertificado(document.forms.formaFirma.certificado.value);
//
//	 $("#prorrogaFormDatos #firmaElectronica").val(firmaDigitalResp);
//	 $("#prorrogaFormDatos #cadenaOriginal").val(getCadenaOriginal());
//	 
//	 $("#prorrogaFormDatos #tipoCertificado").val(cert);
	 
	var crtAnexosolcorrpat = $("#prorrogaFormDatos").toObject({mode:'first'});
	
//	var crtAnexosolcorrpat = crtAnexoConsulta;
	
	crtAnexosolcorrpat["firmaElectronica"] = sello;
	crtAnexosolcorrpat["selloIMSS"] = selloIMSS;
	crtAnexosolcorrpat["cadenaOriginal"] = cadenaOriginal;
	crtAnexosolcorrpat["mensaje"]=objMensajeFirma;
	
	
	
	crtAnexosolcorrpat["cveAnexoSolicitudCorrPat"]=crtAnexoConsulta.cveAnexoSolicitudCorrPat;
	
	
	
	crtAnexosolcorrpat["strDelegacion"]=crtAnexoConsulta.strDelegacion;
	crtAnexosolcorrpat["strSubdelegacion"]=crtAnexoConsulta.strSubdelegacion;
	crtAnexosolcorrpat["txRazonSocial"]=crtAnexoConsulta.txRazonSocial;
	crtAnexosolcorrpat["nuFolio"]=crtAnexoConsulta.nuFolio;
	crtAnexosolcorrpat["registroPatronal"]=crtAnexoConsulta.registroPatronal;
	crtAnexosolcorrpat["digitoVerificador"]=crtAnexoConsulta.digitoVerificador;
	crtAnexosolcorrpat["txCurp"]=crtAnexoConsulta.txCurp;
	crtAnexosolcorrpat["txRfc"]=crtAnexoConsulta.txRfc;	
	crtAnexosolcorrpat["calle"]=crtAnexoConsulta.calle;
	crtAnexosolcorrpat["txRfc"]=crtAnexoConsulta.txRfc;
	crtAnexosolcorrpat["colonia"]=crtAnexoConsulta.colonia;
	crtAnexosolcorrpat["municipio"]=crtAnexoConsulta.municipio;	
	crtAnexosolcorrpat["codigoPostal"]=crtAnexoConsulta.codigoPostal;
	crtAnexosolcorrpat["txTelefono"]=crtAnexoConsulta.txTelefono;
	crtAnexosolcorrpat["txEmail"]=crtAnexoConsulta.txEmail;
	
	crtAnexosolcorrpat["motivo"]=motivoVentana;	
	crtAnexosolcorrpat["txRepresentanteLegal"]=lugarVentana;
	crtAnexosolcorrpat["lugar"]=representanteLegalVentana;
	
	crtAnexosolcorrpat["entidadFederativa"]=crtAnexoConsulta.entidadFederativa;
	crtAnexosolcorrpat["localidad"]=crtAnexoConsulta.localidad;
	
	
	
	if(urlAcuseFirmado!=undefined){	
		crtAnexosolcorrpat["urlAcuseFirma"]=urlAcuseFirmado;		
	}
	
	
	
	$.postJSON("prorroga/agregar.do", crtAnexosolcorrpat, function(data) {
		
		verifyCustomDataError(data);
		if(data.error==""){
			alert('SE HA RECIBIDO LA SOLICITUD DE LA PRORROGA EXITOSAMENTE\n' + 
			'Tiene que acudir a su subdelegacion para obtener la respuesta a su solicitud');
			
			inicializaPosicionPaginador();	
			desbloquear();		
			document.forms[0].submit();
		}
		
	}).error(function(data){
		desbloquear();
		validarSesionExpirada(data);
		alert("error" + data);
	}).complete(function(data){
		desbloquear();
		
	});
	
	
//	oDgValida.dialog("close");
}


///////////////////////  Ejecucion de la firma digital ////////////////////



var resFirmadoDocumentoProrroga;
function callBackFirmaProrroga(){		
	resFirmadoDocumentoProrroga=firma.getDatosSalida();
	if(resFirmadoDocumentoProrroga!=null){
		if(resFirmadoDocumentoProrroga.Resultado==0){
			window.open(resFirmadoDocumentoProrroga.acuse,'', "scrollbars=1,height=500,width=700");
			var sello=recuperaSelloImssProrroga(resFirmadoDocumentoProrroga.folio);
			if(sello==null){
				alert("No se pudo firmar el documento,favor de reintenar");
				desbloquear();
				return;
			}
			registrarFirmado(resFirmadoDocumentoProrroga.folio, sello.sello, generaCadenaOriginalProrroga(),resFirmadoDocumentoProrroga.acuse);
		}else{
			errorDialog.html('<font size="2px">No se ha podido firmar el documento,favor de reintentar</font>');
			errorDialog.dialog("open");
			desbloquear();
		}
	}
	
}

function generaParamFirmaProrroga(){   
//	selloDigitalImssProrroga=recuperaSelloImssProrroga();
//	if(selloDigitalImssProrroga=="-1"){
//		return null;
//	}
	var parmVals = {			
			acuse:"AcuseV1.0",
			aplicacion:"portalimssdigital",
			operacion:"firmaCMS",
//			origen:"http://dnassd.imss.gob.mx",
			origen:"http://correcciondigital.imss.gob.mx",
			salida:"resultado,descripcion,folio,acuse,qr,rfc,curp,serie_cert,archivos,firmas,contenedores,vigencias",
			tipo_archivos:"",
			forma_firma_archivos:"0",
			firma_archivo:true,
			//val_rfc:true,
			max_archivos:5,
			min_archivos:1,
			curp:$("#txCurpInput").val().trim(),
			rfc:$("#txRfcInput").val().trim(),
			//rfc:'DUSL821218LN8',
			nombreCompleto:$("#txRazonSocialInput").val(),
			registroPatronal:$("#txRegistroPatronalInput").val()+recuperaDigitoVerificador($("#txRegistroPatronalInput").val()),
			idTipoSolicitud:6,								
			cad_original:quitaAcentos(generaCadenaOriginalProrroga().substring(0,generaCadenaOriginalProrroga().length-2)+"||"),		
			descripcionTipoSolicitud:"SOLICITUD DE PR�RROGA",
			fechaElectronica:getFechaServidor()		
			};	
		return parmVals;
		
	}






function generaCadenaOriginalProrroga(){
	var campos = new Object();
	var cadenaPrincipal="||";
	campos['txRazonSocialInput'] = 'Nombre o Denominacion Social';
	campos['txNuFolio'] = 'Folio de Correcion';
	campos['txRegistroPatronalInput'] = 'Numero Registro Patronal';
	campos['txdigitoVerificadorInput'] = ' 	Dig. Ver.';
	campos['txCurpInput'] = 'Clave Unica de Registro de Poblacion';
	campos['txRfcInput'] = 'Registro Federal de Contribuyentes';
	campos['txFechaInicialInput'] = 'Fecha Inicial';
	campos['txFechaFinalInput'] = 'Fecha Final';
	campos['txFecElaboracionInput'] = 'Fecha de Elaboracion';
	campos['txFechaLimiteInput'] = 'Fecha Limite';
	campos['txtipoCorreccionInput'] = 'Tipo de Correccion';
	campos['txfecFechaRecepcionOficioInput'] = 'Fecha de Invitacion';
	campos['calleRegPatInput'] = 'Calle';
	campos['numExteriorRegPatInput'] = 'Numero exterior';
	campos['numInteriorRegPatInput'] = 'Numero interior';
	campos['coloniaRegPatInput'] = 'Colonia';
	campos['municipioRegPatInput'] = 'Municipio';
	campos['localidadRegPatInput'] = ' 	Localidad';
	campos['entidadFederativaRegPatInput'] = 'Entidad federativa';
	campos['codigoPostalRegPatInput'] = 'Codigo postal';
	campos['telefonoRegPatInput'] = 'Telefono';
	campos['emailRegPatInput'] = 'Correo electronico';
	campos['txRepresentanteLegalInput'] = 'Nombre del patron o representante legal';
	campos['txLugarInput'] = 'Lugar';

	
	var total=0;
	for (var k in campos) {
		total++;
	}
	
	var s=0;
	for (var k in campos) {		
	    if (campos.hasOwnProperty(k)) {
	    		var val=$("#"+k).val();

	    	 	cadenaPrincipal=cadenaPrincipal+campos[k]+"|"+val;	
	    	 	if(s<total-1){
	    	 		cadenaPrincipal+="|";
	    	 	 }
	    		s++;
	      }	   
	}
	
	
	cadenaPrincipal+="||";
	
	return cadenaPrincipal;	
}



function recuperaSelloImssProrroga(idTramite){
	
	
	var datos='{"selloIMSS":""}';
	var crtSolicitudCorr = jQuery.parseJSON(datos);
	crtSolicitudCorr.selloIMSS=generaCadenaOriginalProrroga();
	if(idTramite!=undefined){
		crtSolicitudCorr.idTramite=idTramite;
	}
	var sello;
	$.postJSON_Sync("correcion/recuperaSelloDigital.do", crtSolicitudCorr, function(data) {
		sello=data.respuestaObjetoFirmadoSimple;
		//desbloquear();
		
	});	
	 
	return sello;
}

function recuperarRolUsuario(){
	
	var cveRol;
	$.postJSON_Sync("correcion/consultarRolUsuario.do", null, function(data) {
		cveRol=data.cveRol;
		//desbloquear();
		
	});
	return cveRol;
}



function recuperaDigitoVerificador(rp){
	
	var digitoVerificador;
	
	var valor='{"registroPatronal":"'+rp+'"}';
	var crtSolicitudCorr = jQuery.parseJSON(valor);
	$.postJSON_Sync("correcion/generaDigitoVerificador.do", crtSolicitudCorr, function(data) {
		digitoVerificador=data;
		//desbloquear();		
	});
	return digitoVerificador;
}