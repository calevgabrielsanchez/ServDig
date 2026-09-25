/**
 * 
 */
MUNICIPIOS_IMSS = null;
MUNICIPIO_IMSS = null;

$(document).ready(function() {
	// Se incializa el blockUI para las peticiones AJAX
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	//inicializamos las validaciones
	initValidacionesFormContacto();
	//inicializamos el componente de domicilio
	initComponenteDomicilio();
	//seteamos el evento del boton guardar
	$("#guardarDomicilioCT").on("click", guardarInfo);
	//cancelar domicilio
	$("#cancelarDomicilioCT").on("click",function(){paginaPrevia()});
	//Seteamos el evento del xehckbox
	$("#mismoDomicilioFiscal").on("change",mismoDomicilio);
});

var mismoDomicilio = function() {
	var $checkBox = $(this);
	
	if($checkBox.is(":checked")) {
		var domicilioF = personaMoralAP.domicilioFiscal;
		if(domicilioF != null) {
			$("#componenteDomicilio").domicilioRecortado("set",domicilioF);
			$("#componenteDomicilio").domicilioRecortado("block",true,["colonia"]);
		}
	} else {
		$("#componenteDomicilio").domicilioRecortado("clean");
		$("#componenteDomicilio").domicilioRecortado("block",false);
	}
}

var guardarInfo = function() {
	
	var domicilioCapturado = $("#componenteDomicilio").domicilioRecortado("get");
	var $formularioMedios = $("#form-medios-contacto");
	var mediosValidos = $formularioMedios.valid();
	mostrarMensajeErrorGenerico("form_nom_comercial",false,MENSAJE_ERROR_FORM);
	if(domicilioCapturado != null && mediosValidos) {
		var nombreComercial = $.trim($("#nombreComercial").val()).toUpperCase();
		if(nombreComercial.length > 0) {
			solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.nombreComercial = nombreComercial;
		}
		
		solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.cntroTrabajo = domicilioCapturado;
		solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.cntroTrabajo.mediosContacto = crearListaMedios($formularioMedios);
		solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.municipioIMSS = MUNICIPIO_IMSS;
		solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.subdelegacion = MUNICIPIO_IMSS.subdelegacion;
		paginaSiguiente();
		
	} else {
		mostrarMensajeErrorGenerico("form_nom_comercial",true,MENSAJE_ERROR_FORM);
	}
};

var crearListaMedios = function($formularioMedios){
	var listaMedios=new Array();
	
	var lada = $formularioMedios.find("#lada").val() != "" ? $formularioMedios.find("#lada").val() :"";
	var tel = $formularioMedios.find("#telefonoFijo").val() != "" ? $formularioMedios.find("#telefonoFijo").val() : "";
	var ext = $formularioMedios.find("#extension").val() ? $formularioMedios.find("#extension").val() : "";
	listaMedios.push(crearMedioContacto(1,2,lada+ "|"+tel+"|"+ext));
	
	if($.trim($formularioMedios.find("#telefonoFijo2").val()) != ""){
		var ladaB = $formularioMedios.find("#lada2").val() != "" ? $formularioMedios.find("#lada2").val() :"";
		var telB = $formularioMedios.find("#telefonoFijo2").val() != "" ? $formularioMedios.find("#telefonoFijo2").val() : "";
		var extB = $formularioMedios.find("#extension2").val() != "" ? $formularioMedios.find("#extension2").val() : "";
		listaMedios.push(crearMedioContacto(2,2,ladaB+ "|"+telB+"|"+extB));
	}
	
	//agregamos el correo electronico capturado
	listaMedios.push(crearMedioContacto(3,1,$("#correo").val()!=undefined ? $("#correo").val() : ""));

	return listaMedios;
}

var crearMedioContacto = function(idVista, tipoMedio, descripcion) {
	var medio = new Object();
	medio.idVista = idVista;
	medio.tipoMedioContacto= new Object();
	medio.tipoMedioContacto.idTipoMedioContacto=tipoMedio;
	medio.desFormaContacto=descripcion;
	
	return medio;
};

/**
 * Metodo para inicializar el componente de domicilio
 */
var initComponenteDomicilio = function() {
	$("#componenteDomicilio").domicilioRecortado({
		funcionError: null,
		mostrarMensajeCaptura: false,
		mostrarFormulario: true,
		mostrarTitulosDialogs: true,
		mostrarMesajeRequeridos: false,
		funcionCambioColonia: obtenerMunicipiosImss,
		habilitarTooltips: false
	});
};

/**
 * Metodo para inicializar el validador de medios de contacto
 */
var initValidacionesFormContacto = function() {
	$("#form-medios-contacto").validate($.extend({},DEFAULTS_VALIDATE,{
		verifyErrors: function(existError) {
			marcarAsteriscos($("#form-medios-contacto"),".errorDocs","div");
		},
		rules: {
			correo: {required: true,email:true},
			lada: {required: true,number:true,maxlength: 3},
			telefonoFijo: {required: true,number:true,maxlength: 8},
			extension: {number:true,maxlength: 6},
			lada2: {number:true,maxlength: 3},
			telefonoFijo2: {number:true,maxlength: 8},
			extension2: {number:true,maxlength: 6}
		}
	}));
}

/**
 * Funcion que se ejecuta cuando se cambia de colonia para buscar las subdelegaciones
 */
var obtenerMunicipiosImss = function(asentamiento) {
	MUNICIPIO_IMSS = null;
	
	if(asentamiento != null) {
		var url = '/gestionDomicilios-web-ciudadano/widget/domicilio/utility/consulta/municipioImss';
		
		var domicilioConsulta = new Object();
		domicilioConsulta.asentamiento = asentamiento;
		domicilioConsulta.codigoPostal = asentamiento.codigoPostal;
	
		$.postJSON(url, domicilioConsulta, function(data){
			if(data.error) {
				$('#contenedorSubdelegacion').html("<div class=\"alert alert-danger\">"+data.error+"</div>");
			} else {
				procesarListaMunicipios(data.municipios);
			}
		}).error(function (data){
			$('#contenedorSubdelegacion').html("<div class=\"alert alert-danger\">Existi&oacute; un error al cargar las subdelegaciones.</div>");
		});
	} else {
		$('#contenedorSubdelegacion').html("<div class=\"alert alert-info\">Seleccione una colonia para ubicar la subdelegaci&oacute;n que le corresponde.</div>");
	}
}

/**
 * Cuando se de click en el radio se ejecutara esta funcion
 */
var seleccionarMunicipio = function (idMunicipio) {
	if(MUNICIPIOS_IMSS) {
		$.each(MUNICIPIOS_IMSS, function(index, value){
			if(value.idMunicipio == idMunicipio) {
				MUNICIPIO_IMSS = value;
				return false;
			}
		});
		
		return;
	} else {
		//console.log("No existen municipios")
	}
}

var procesarListaMunicipios = function(municipios) {
	MUNICIPIOS_IMSS = municipios;
	var $contenedorSub = $('#contenedorSubdelegacion');
	$contenedorSub.html("");
	var $tabla = $("<table class=\"table table-striped table-bordered\"></table>");
	
	$.each(MUNICIPIOS_IMSS, function(index, value) {
		if(index == 0){
			MUNICIPIO_IMSS = value;
		}
		var $trDelegacion = $("<tr>");
		var $tdRadio = $("<td rowspan=\"3\" style=\"text-align: center; vertical-align: middle;\"><input type=\"radio\" name=\"idMunicipioImssRadio\" style=\"width: 100%;\" id=\"idMunicipioImssRadio\""+(index==0 ? " checked=\"checked\" ": "")+" onclick=\"seleccionarMunicipio("+value.idMunicipio+")\"/></td>");
		var $tdTituloDel = $("<td><strong>Delegaci&oacute;n:</strong></td>");
		var $tdInfoDel = $("<td>"+value.subdelegacion.delegacion.clave+" - " +value.subdelegacion.delegacion.descripcion+"</td>");
		$trDelegacion.append($tdRadio).append($tdTituloDel).append($tdInfoDel);
		
		var $trSubdelegacion = $("<tr>");
		var $tdTituloSDel = $("<th>Subdelegaci&oacute;n:</th>");
		var $tdInfoSDel = $("<td>"+value.subdelegacion.clave+" - " +value.subdelegacion.descripcion+"</td>");
		$trSubdelegacion.append($tdTituloSDel).append($tdInfoSDel);
		
		var $trMunicipio = $("<tr>");
		var $tdTituloMun = $("<td><label>Municipio IMSS:</label></td>");
		var $tdInfoMun = $("<td>"+value.cvecMunicipioSINDO + " - "+ value.descMunicipio+"</td>");
		$trMunicipio.append($tdTituloMun).append($tdInfoMun);
		
		$tabla.append($trDelegacion).append($trSubdelegacion).append($trMunicipio);
		
	})
	
	$contenedorSub.append($tabla);
	$("#idMunicipioImssRadio").focus();
}