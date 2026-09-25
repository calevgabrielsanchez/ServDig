

var derivSubdDatosValidos=true;

/**
 * @author Enrique Duran Jimenez
 * @since 01/08/2012
 * Funcion que inicializa la pesta�a de Derivar a subdelegacion del seguimiento de correccion
 */
function inicializaDevSubDelSegCorreccion(data){
	$("form#devSubDelegacionSeguimientoCorreccionForm #botonAgregarDomicilio").show();
	$('form#devSubDelegacionSeguimientoCorreccionForm input[type=text]').val('');
	$("form#devSubDelegacionSeguimientoCorreccionForm #cveDelegacion").val('-1');
	$("form#devSubDelegacionSeguimientoCorreccionForm #selectNvaSubDeleg").val('-2');
	$("form#devSubDelegacionSeguimientoCorreccionForm #nombreFuncionario").val(data.nombreFuncionario);
	inicializaFechasDevSubDelSegCorreccion();
	$("form#devSubDelegacionSeguimientoCorreccionForm #cveSolicitud").val(data.cveSolCorr);
	estiloCapturableSubd(true);
	$('#spnFechaDerivarSubdel').hide();	
	
	filtraDelegaciones();
	
	// VALOR FIJO TEMPORALMENTE -- CAMBIAR	
	$("form#devSubDelegacionSeguimientoCorreccionForm #idAnteriorSubdelegacion").val('64');
	generaResumenDevSubDelega(data);
}

function filtraDelegaciones(){
	var comboDel = $("form#devSubDelegacionSeguimientoCorreccionForm #cveDelegacion");
	$('option[value="35"]',comboDel).remove();
	$('option[value="36"]',comboDel).remove();
	$('option[value="37"]',comboDel).remove();
	$('option[value="38"]',comboDel).remove();
}


/**
 * @author Enrique Duran Jimenez
 * @since 01/08/2012
 * Funcion inicializa el datepicker de la pantalla de Derivar a subdelegacion del seguimiento de correccion
 */
function inicializaFechasDevSubDelSegCorreccion(){
	$("form#devSubDelegacionSeguimientoCorreccionForm #fechaDerivacion").datepicker( { 
		dateFormat: 'dd-mm-yy',
		onchange: function(dateText, inst) { 
			
	    }
	});

	$( "form#devSubDelegacionSeguimientoCorreccionForm #fechaDerivacion").datepicker('option', 'maxDate', jsFechaMaxSeguimiento);
	$( "form#devSubDelegacionSeguimientoCorreccionForm #fechaDerivacion").datepicker('option', 'minDate', jsFechaMinSeguimiento);
	
}



function actualizDom(){
	var sAtributo = '{"patron":'+'"CORR_DOM_DERIVAR_SUBD"}';
	var crcAtributo = jQuery.parseJSON(sAtributo);
	bloquear();
	$.postJSON(getAppContextParaJS() +"/seguimiento/correccion/derivSubdel/actualizaDom.do", crcAtributo, function(data) {
		if (data != null) {
			$('form#devSubDelegacionSeguimientoCorreccionForm #calleDerivarSubdel').val(data.dgVialidadByCveViaPrin.nomVia);
			$('form#devSubDelegacionSeguimientoCorreccionForm #numExtDerivarSubdel').val(data.numextnum);
			$('form#devSubDelegacionSeguimientoCorreccionForm #numIntDerivarSubdel').val(data.numintnum);
			$('form#devSubDelegacionSeguimientoCorreccionForm #coloniaDerivarSubdel').val(data.dgAsentamiento.nomAsen);	
			$('form#devSubDelegacionSeguimientoCorreccionForm #municipioDerivarSubdel').val(data.dgCatLocalidad.dgCatMunicipio.nomMun);		
			$('form#devSubDelegacionSeguimientoCorreccionForm #estadoDerivarSubdel').val(data.dgCatLocalidad.dgCatMunicipio.dgCatEstado.nomEnt);		
			$('form#devSubDelegacionSeguimientoCorreccionForm #codigoPostalDerivarSubdel').val(data.dgCodigosPostales.id.codigo);
			obtenCveNuevaDelegacion();
		}
		desbloquear();		
	}).error(function(data){ 
		desbloquear();
		alert("Error: Conexion no disponible, intente de nuevo");
	}).complete(function(){
	});	
	
}




function obtenerDomGeo(){
	var resultado = openWindowregistraDomicilioInegi(getAppContextParaJS(),'/seguimiento/correccion/derivSubdel/obtenerDomGeografico.do',"CORR_DOM_DERIVAR_SUBD","actualizDom()");
	var sAtributo = '{"patron":'+'"CORR_DOM_DERIVAR_SUBD"}';
	var crcAtributo = jQuery.parseJSON(sAtributo);
	bloquear();
	$.postJSON(getAppContextParaJS() +"/seguimiento/correccion/derivSubdel/actualizaDom.do", crcAtributo, function(data) {
		if (data != null) {
			$('form#devSubDelegacionSeguimientoCorreccionForm #calleDerivarSubdel').val(data.dgVialidadByCveViaPrin.nomVia);
			$('form#devSubDelegacionSeguimientoCorreccionForm #numExtDerivarSubdel').val(data.numextnum);
			$('form#devSubDelegacionSeguimientoCorreccionForm #numIntDerivarSubdel').val(data.numintnum);
			$('form#devSubDelegacionSeguimientoCorreccionForm #coloniaDerivarSubdel').val(data.dgAsentamiento.nomAsen);	
			$('form#devSubDelegacionSeguimientoCorreccionForm #municipioDerivarSubdel').val(data.dgCatLocalidad.dgCatMunicipio.nomMun);		
			$('form#devSubDelegacionSeguimientoCorreccionForm #estadoDerivarSubdel').val(data.dgCatLocalidad.dgCatMunicipio.dgCatEstado.nomEnt);		
			$('form#devSubDelegacionSeguimientoCorreccionForm #codigoPostalDerivarSubdel').val(data.dgCodigosPostales.id.codigo);
			obtenCveNuevaDelegacion();
		}
		desbloquear();		
	}).error(function(data){ 
		desbloquear();
		alert("Error: Conexion no disponible, intente de nuevo");
	}).complete(function(){
	});	
}





function validarAlfaNumericoDevSub(e) { 
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890abcdefghijklmn\U00F1opqrstuvwxyzABCDEFGHIJKLMN\U00D1OPQRSTUVWXYZ/]/;
    te = String.fromCharCode(tecla);
    return patron.test(te);
} 

function obtenCveNuevaDelegacion(){
	var nombreEstado = $('form#devSubDelegacionSeguimientoCorreccionForm #estadoDerivarSubdel').val();
	var nuevaClaveDelegacion ='-1';
	var comboDel = $("form#devSubDelegacionSeguimientoCorreccionForm #cveDelegacion");
	nombreEstado = $.trim(nombreEstado);    
	if(nombreEstado=='AGUASCALIENTES'){ nuevaClaveDelegacion='1'; }         
	else if(nombreEstado=='BAJA CALIFORNIA'){ nuevaClaveDelegacion='2';}         
	else if(nombreEstado=='BAJA CALIFORNIA SUR'){ nuevaClaveDelegacion='3';}   
	else if(nombreEstado=='CAMPECHE'){ nuevaClaveDelegacion='4';}          
	else if(nombreEstado=='COAHUILA DE ZARAGOZA'){ nuevaClaveDelegacion='5';}         
	else if(nombreEstado=='COLIMA'){ nuevaClaveDelegacion='6';}         
	else if(nombreEstado=='CHIAPAS'){ nuevaClaveDelegacion='7';}         
	else if(nombreEstado=='CHIHUAHUA'){ nuevaClaveDelegacion='8';}         
	else if(nombreEstado=='DURANGO'){ nuevaClaveDelegacion='10';}         
	else if(nombreEstado=='GUANAJUATO'){ nuevaClaveDelegacion='11';}         
	else if(nombreEstado=='GUERRERO'){ nuevaClaveDelegacion='12';}         
	else if(nombreEstado=='HIDALGO'){ nuevaClaveDelegacion='13';}         
	else if(nombreEstado=='JALISCO'){ nuevaClaveDelegacion='14';}         
	else if(nombreEstado=='MICHOAC\U00C1N DE OCAMPO'){ nuevaClaveDelegacion='17';}         
	else if(nombreEstado=='MORELOS'){ nuevaClaveDelegacion='18';}         
	else if(nombreEstado=='NAYARIT'){ nuevaClaveDelegacion='19';}         
	else if(nombreEstado=='NUEVO LE\U00D3N'){ nuevaClaveDelegacion='20';}         
	else if(nombreEstado=='OAXACA'){ nuevaClaveDelegacion='21';}         
	else if(nombreEstado=='PUEBLA'){ nuevaClaveDelegacion='22';}         
	else if(nombreEstado=='QUER\U00C9TARO'){ nuevaClaveDelegacion='23';}         
	else if(nombreEstado=='QUINTANA ROO'){ nuevaClaveDelegacion='24';}         
	else if(nombreEstado=='SAN LUIS POTOS\U00CD'){ nuevaClaveDelegacion='25';}         
	else if(nombreEstado=='SINALOA'){ nuevaClaveDelegacion='26';}         
	else if(nombreEstado=='SONORA'){ nuevaClaveDelegacion='27';}         
	else if(nombreEstado=='TABASCO'){ nuevaClaveDelegacion='28';}         
	else if(nombreEstado=='TAMAULIPAS'){ nuevaClaveDelegacion='29';}         
	else if(nombreEstado=='TLAXCALA'){ nuevaClaveDelegacion='30';}         
	else if(nombreEstado=='YUCAT\U00C1N'){ nuevaClaveDelegacion='33';}        
	else if(nombreEstado=='ZACATECAS'){ nuevaClaveDelegacion='34';}       
	if(nuevaClaveDelegacion != '-1'){
	  $('form#devSubDelegacionSeguimientoCorreccionForm #cveDelegacion').val(nuevaClaveDelegacion);
	  $('option[value=' + nuevaClaveDelegacion + ']',comboDel).prop('selected','true');
	  $("form#devSubDelegacionSeguimientoCorreccionForm #cveDelegacion").val(nuevaClaveDelegacion);
	  try{ $(comboDel).trigger('change'); }catch(err){}
	}

}

function mensajeValidaFechaDSubDSegCorr(nombreFecha){
	var msgErrorDerivaSubD="<label class='etiquetaError'>La fecha de derivaci&oacute;n no puede ser menor a la "+ nombreFecha + "</label>";
	return msgErrorDerivaSubD;
}

function validaFechaDerivacionSubdel () {
	var fechaDerivacionSub=$("form#devSubDelegacionSeguimientoCorreccionForm #fechaDerivacion").val();	
	
	$("form#devSubDelegacionSeguimientoCorreccionForm #labelFechaDerivarSubdel").html("");
	
	if (fechaDerivacionSub!=""){
		 resultValFecha = validaListaFechas(fechaDerivacionSub, ARRAY_FECHAS_SEGCORR);
		 if(!resultValFecha[0]){
			 $("form#devSubDelegacionSeguimientoCorreccionForm #labelFechaDerivarSubdel").html(mensajeValidaFechaDSubDSegCorr(resultValFecha[1]));
			 $("form#devSubDelegacionSeguimientoCorreccionForm #fechaDerivacion").val("");
			 $('#spnFechaDerivarSubdel').hide();
			 derivSubdDatosValidos = false;
		 } else {
			 $('#spnFechaDerivarSubdel').show('fast');
		 }
	} else {
		derivSubdDatosValidos = false;
	} 	
}

function validaCampoReqDerivSubd(forma, campo, etiqueta) {
	var mensaje_requerido = "<label class='etiquetaError'>Requerido</label>";
	var rutaForm = "form#" + forma + " #";
	var v_campo = rutaForm + campo;
	var v_label = rutaForm + etiqueta;
	
	if ($(v_campo).val() == '') {
		$(v_label).html(mensaje_requerido);
		derivSubdDatosValidos=false;
	}
}

function validaSubdelegacionesDerivSubd(){
	var idSubdelOrigen = $("form#devSubDelegacionSeguimientoCorreccionForm #idAnteriorSubdelegacion").val();
	var idSubdelDestino = $("form#devSubDelegacionSeguimientoCorreccionForm #idNuevaSubdelegacion").val();
	if(idSubdelDestino<0){
		var mensaje_requerido = "<label class='etiquetaError'>Campo Requerido</label>";
		$("form#devSubDelegacionSeguimientoCorreccionForm #labelIdSubdelDestDerivarSubdel").html(mensaje_requerido);
		derivSubdDatosValidos= false;
	}else if(idSubdelOrigen==idSubdelDestino){
		var mensaje_requerido = "<label class='etiquetaError'>No puede ser la misma subdelegaci&oacute;n</label>";
		$("form#devSubDelegacionSeguimientoCorreccionForm #labelIdSubdelDestDerivarSubdel").html(mensaje_requerido);
		derivSubdDatosValidos= false;
	}
}

function estiloCapturableSubd(asignar){
	
	if (asignar){		
		$("form#devSubDelegacionSeguimientoCorreccionForm #fechaDerivacion").addClass("red");
		$("form#devSubDelegacionSeguimientoCorreccionForm #folioDerivacion").addClass("red");		
		$('form#devSubDelegacionSeguimientoCorreccionForm #spnFechaDerivarSubdel').show('fast');
		$('form#devSubDelegacionSeguimientoCorreccionForm #botonAgregarDomicilio').show('fast');
		$('form#devSubDelegacionSeguimientoCorreccionForm input[type=text]').removeAttr("disabled");
		$('form#devSubDelegacionSeguimientoCorreccionForm input[type=button]').removeAttr("disabled");
		$("form#devSubDelegacionSeguimientoCorreccionForm #cveDelegacion").removeAttr("disabled");
		$("form#devSubDelegacionSeguimientoCorreccionForm #selectNvaSubDeleg").removeAttr("disabled");
	} else {
		$("form#devSubDelegacionSeguimientoCorreccionForm #fechaDerivacion").removeClass("red");
		$("form#devSubDelegacionSeguimientoCorreccionForm #folioDerivacion").removeClass("red");
		$('form#devSubDelegacionSeguimientoCorreccionForm #spnFechaDerivarSubdel').hide();
		$('form#devSubDelegacionSeguimientoCorreccionForm #botonAgregarDomicilio').hide();
		$('form#devSubDelegacionSeguimientoCorreccionForm input[type=text]').prop("disabled", "disabled");
		$('form#devSubDelegacionSeguimientoCorreccionForm input[type=button]').prop("disabled", "disabled");		
		$("form#devSubDelegacionSeguimientoCorreccionForm #cveDelegacion").prop("disabled", "disabled");
		$("form#devSubDelegacionSeguimientoCorreccionForm #selectNvaSubDeleg").prop("disabled", "disabled");
		
	}
}

/**
 * Funcion donde se valida los campos requeridos para el tab  
 * derivar a otra subdelegacion.
 *  
 * @author Sa&uacute;l Rosales Piedragil
 * @version 1.0.0
 */
function validaCamposSegCorrDerivacionSub(){
	derivSubdDatosValidos = true;
	var nForma="devSubDelegacionSeguimientoCorreccionForm";
	var fechaDerivacionSubValidar=$("form#devSubDelegacionSeguimientoCorreccionForm #fechaDerivacion").val();
	
	// se inicializan los label
	$("form#devSubDelegacionSeguimientoCorreccionForm #labelFechaDerivarSubdel").html('');
	$("form#devSubDelegacionSeguimientoCorreccionForm #labelFolioDerivarSubdel").html('');	
	$("form#devSubDelegacionSeguimientoCorreccionForm #labelIdSubdelDestDerivarSubdel").html('');
	
	$("form#devSubDelegacionSeguimientoCorreccionForm #labelCalleDerivarSubdel").html('');
	$("form#devSubDelegacionSeguimientoCorreccionForm #labelColoniaDerivarSubdel").html('');
	$("form#devSubDelegacionSeguimientoCorreccionForm #labelNumExtDerivarSubdel").html('');
	$("form#devSubDelegacionSeguimientoCorreccionForm #labelMunicipioDerivarSubdel").html('');
	$("form#devSubDelegacionSeguimientoCorreccionForm #labelEstadoDerivarSubdel").html('');
	$("form#devSubDelegacionSeguimientoCorreccionForm #labelCodigoPostalDerivarSubdel").html('');

	validaCampoReqDerivSubd(nForma, "folioDerivacion", "labelFolioDerivarSubdel");
	validaCampoReqDerivSubd(nForma, "fechaDerivacion", "labelFechaDerivarSubdel");
	validaCampoReqDerivSubd(nForma, "idNuevaSubdelegacion", "labelIdSubdelDestDerivarSubdel"); 
	validaCampoReqDerivSubd(nForma, "calleDerivarSubdel", "labelCalleDerivarSubdel");
	validaCampoReqDerivSubd(nForma, "coloniaDerivarSubdel", "labelColoniaDerivarSubdel");
	validaCampoReqDerivSubd(nForma, "numExtDerivarSubdel", "labelNumExtDerivarSubdel");
	validaCampoReqDerivSubd(nForma, "municipioDerivarSubdel", "labelMunicipioDerivarSubdel"); 
	validaCampoReqDerivSubd(nForma, "estadoDerivarSubdel", "labelEstadoDerivarSubdel");
	validaCampoReqDerivSubd(nForma, "codigoPostalDerivarSubdel", "labelCodigoPostalDerivarSubdel");
	validaSubdelegacionesDerivSubd();

	if (fechaDerivacionSubValidar.length > 0){
		validaFechaDerivacionSubdel();
	}
	return derivSubdDatosValidos;
}

function onchangeSubDel(){
	alert($("form#devSubDelegacionSeguimientoCorreccionForm #selectNvaSubDeleg").val());
}


function limpiaFechaDerivacionSubdel(){	
	$("form#devSubDelegacionSeguimientoCorreccionForm #fechaDerivacion").val("");
	$("form#devSubDelegacionSeguimientoCorreccionForm #labelFechaDerivarSubdel").html("");
	$('#spnFechaDerivarSubdel').hide();	
}


function procesaFormularioDerivacionSubSegCorr() {	
	$("form#devSubDelegacionSeguimientoCorreccionForm #idNuevaSubdelegacion").val($("form#devSubDelegacionSeguimientoCorreccionForm #selectNvaSubDeleg").val());
	
	var cve_solicitud = $("form#devSubDelegacionSeguimientoCorreccionForm #cveSolicitud").val();
	if (cve_solicitud.length == 0){
		alert ("No se se encontro la clave de solicitud");
		return false;
	}

	if (confirm("Estais seguro que la informacion es correcta?")) {
		
	  if(validaCamposSegCorrDerivacionSub()){
	   var objForma = $("form#devSubDelegacionSeguimientoCorreccionForm").toObject({mode:'first'});
	   objForma.cvePresentaCorr=cvePresentacion;
	   $.postJSON("correccion/derivSubdel/registrarDerivacionSubdel.do", objForma, function(data) {	
		   if(data != null && data.exito!='' && data.exito!=null && data.exito!=undefined){
			   estiloCapturableSubd(false);
			   
			   $("form#formResumenSeguimiento #lbValFecDerSubde").text($("#fechaDerivacion").val());
			   $("form#formResumenSeguimiento #lbValFolioDerSubde").text($("#folioDerivacion").val());
			   $("form#formResumenSeguimiento #lbValSubdeDestDerSub").text($("#selectNvaSubDeleg option:selected").text());
				
				setTabDesHabilitado('seguimientoCorreccionCedRevision');
				setTabDesHabilitado('seguimientoCorreccionReqDocumentacion');
				setTabDesHabilitado('seguimientoCorreccionCedValidacion');
				setTabDesHabilitado('seguimientoCorreccionOfiResultados');
				setTabDesHabilitado('seguimientoCorreccionDerivarFis');
				setTabDesHabilitado('seguimientoCorreccionDerivarDic');
				setTabDesHabilitado('seguimientoCorreccionCancelacion');		
				setTabDesHabilitado('seguimientoCorreccionConclusion');		
				setTabDesHabilitado('seguimientoCorreccionReactivar');		
				
				 $('form#segimientoConstruccionForm #regPatronalSegConstruccion').prop('disabled','disabled');
				 $('form#segimientoConstruccionForm #btnValidarRegPatronal').prop('disabled','disabled');
				 
			   alert(data.exito);
		   }else{
			   alert(data.error);
		   }
		}).error(function(data){ 
			alert('Error al procesar los datos');
			validarSesionExpirada(data);
		}).complete(function(){		
			desbloquear();
		});	
	  }
	}
}


function generaResumenDevSubDelega(data){
	
	var sVarSeg = '{"cveSolCorr":"'+data.cveSolCorr+'"}';
	var clase = jQuery.parseJSON(sVarSeg);
	
	$.postJSON_Sync("correccion/derivSubdel/buscarDerivacionSubdel.do", clase, function(data) {
			if(data!=null){
				$("form#formResumenSeguimiento #lbValFecDerSubde").text(data.fechaFecDerivacion);
				$("form#formResumenSeguimiento #lbValFolioDerSubde").text(data.numFolio);
				$("form#formResumenSeguimiento #lbValSubdeDestDerSub").text(data.subdelegDestino.nomNombre);
				
				
				$("form#devSubDelegacionSeguimientoCorreccionForm #fechaDerivacion").val(data.fechaDerivacion);
				$("form#devSubDelegacionSeguimientoCorreccionForm #folioDerivacion").val(data.numFolio);
				
				
				$("form#devSubDelegacionSeguimientoCorreccionForm #cveDelegacion").val(data.subdelegDestino.sacDelegacion.cvePk);
				$("form#devSubDelegacionSeguimientoCorreccionForm #selectNvaSubDeleg").val(data.subdelegDestino.cvePk);
				
			
				
				$('form#devSubDelegacionSeguimientoCorreccionForm #calleDerivarSubdel').val(data.domicilio.dgVialidadByCveViaPrin.nomVia);
				$('form#devSubDelegacionSeguimientoCorreccionForm #numExtDerivarSubdel').val(data.domicilio.numextnum);
				$('form#devSubDelegacionSeguimientoCorreccionForm #numIntDerivarSubdel').val(data.domicilio.numintnum);
				$('form#devSubDelegacionSeguimientoCorreccionForm #coloniaDerivarSubdel').val(data.domicilio.dgAsentamiento.nomAsen);	
				$('form#devSubDelegacionSeguimientoCorreccionForm #municipioDerivarSubdel').val(data.domicilio.dgCatLocalidad.dgCatMunicipio.nomMun);		
				$('form#devSubDelegacionSeguimientoCorreccionForm #estadoDerivarSubdel').val(data.domicilio.dgCatLocalidad.dgCatMunicipio.dgCatEstado.nomEnt);		
				$('form#devSubDelegacionSeguimientoCorreccionForm #codigoPostalDerivarSubdel').val(data.domicilio.dgCodigosPostales.id.codigo);
				$("form#devSubDelegacionSeguimientoCorreccionForm #botonAgregarDomicilio").hide();
			}
		}
	);
	
	
}