//Variable con los asentamientos
var asentamientosUbicados;
var cveEntidadOriginal;
var cveMunicipioOriginal;
var localidadesUbicados;
var oFormQueryDomicilio;
var mensajeAsentamiento = 'Nombre propio que identifica el tipo de asentamiento humano: Colonia, Ampliaci&oacute;n, Residencial, Fraccionamiento, Secci&oacute;n, Unidad Habitacional, Parque o Corredor Industrial, etc.';
/*
 * Seccion de codigo a ejecutar en cuanto el DOM envie la se�aal de que esta
 * listo para procesar de modificaciones al DOM
 */
$(document).ready(function() {
	$.blockUI();
	
	$('div#cuerpo_principal').removeClass('main_wrap');
	$('div.page_holder').removeClass('page_holder');
	
	mostrarVialidadPrimaria();
	habiliarBusquedaPorCPoMun();
	getLocalidadPorMunicipio();
	
	if($('#idPopoverAsentamiento').length > 0) {
		var infoAsentamientoMun = '<p>'+mensajeAsentamiento+'</p>';
	
		$('#idPopoverAsentamiento').popover({
			animation : true,
			html : true,
			title : 'Colonia',
			content : infoAsentamientoMun,
			trigger : 'hover',
			placement : 'right',
			container : 'body'
		});
	}

	if ($('#idPopoverAsentamientoCP').length > 0) {
		var infoAsentamientoCP = '<p>'+mensajeAsentamiento+'</p>';
	
		$('#idPopoverAsentamientoCP').popover({
			animation : true,
			html : true,
			title : 'Colonia',
			content : infoAsentamientoCP,
			trigger : 'hover',
			placement : 'right',
			container : 'body'
		});
	}

	/*
	 * Configuramos la invocacion a la peticion de asentamientos por
	 * codigo postal al perder el foco.
	 */
	$("form#formCodigoPostal input#codigoPostal\\.codigoPostal").live('blur', function() {
		getAsentamientoPorCodigo(true);
	});

	//Configuramos la invocacion al cambio del valor del municipio
	$("form#formMunicipio select#localidad\\.municipio\\.clave").change(function() {
		getAsentamientoPorMunicipio();
	});

	/*
	 * Se agrega un evento al combo de entidad federativa, para que
	 * cada vez que cambie ademas de ejecutar la carga de su combo
	 * dependiente, limpie el combo de asentamientos
	 */
	$("form#formMunicipio select#localidad\\.municipio\\.entidadFederativa\\.clave").change(function(){
		$('select#clave').empty();
		var optionSeleccione = "<option value='-1'>--Selecciona por favor--</option>";
		$("select#clave").append(optionSeleccione);
	});

	// Se vuelve disabled los datos que tengan la clase disabled
	$(".disabled").each(function(i) {
		$(this).attr("disabled", "true");
	});

	$("form").submit(function() {
		$(".disabled").each(function(i) {
			$(this).removeAttr("disabled");
		});
	});

	//Manejo del maxlength del textarea
	$('textarea[maxlength]').live('keyup blur', function() {
		// Store the maxlength and value of the field.
		var maxlength = $(this).attr('maxlength');
		var val = $(this).val();

		// Trim the field if it has content over the maxlength.
		if (val.length > maxlength) {
			$(this).val(val.slice(0, maxlength));
		}
	});

	$("button#regresar").click(function(event) {
		$("form#formRegreso").submit();
	});
	
	$("a#regresarBusqueda").click(
			function() {
				elegirNuevamenteVialidad();
			}
		);
	
	$("#sinNumero").click(
		function() {
			sinNumero();
		}	
	);
	
	if($("#numExteriorAlf").val() != null && $("#numExteriorAlf").val().toLowerCase() == "sn") {
		$("#numExteriorAlf").val($("#numExteriorAlf").val().toUpperCase());
		deshabilitarNumeroExterior(true);
	}

	/*
	 * Se checa si el campo de codigo postal ya tiene valor (ya sea porque hubo
	 * error en la validacion de datos requeridos o porque se esta regresando de
	 * una pantalla posterior), de ser asi, se carga la lista de asentamientos
	 * con el codigo postal
	 */
	var cp = $('form#formCodigoPostal #codigoPostal\\.codigoPostal').val();
	
	if (cp != null && cp != '') {
		getAsentamientoPorCodigo(false);
	}
	
	cveEntidadOriginal = $("form#formMunicipio input#cveEntAsenAux").val();
	cveMunicipioOriginal = $("form#formMunicipio input#cveMuniAsenAux").val();
	
	
	//Se agregan las descripciones cuando se elige un combo
	$("select#domicilioCarretera\\.terminoGeneral\\.clave").change(function() {
		setDescripcionCombo("domicilioCarretera\\.terminoGeneral\\.clave","domicilioCarretera\\.terminoGeneral\\.descripcion");
	});
	
	$("select#domicilioCarretera\\.derechoTransito\\.clave").change(function() {
		setDescripcionCombo("domicilioCarretera\\.derechoTransito\\.clave","domicilioCarretera\\.derechoTransito\\.descripcion");
	});
	
	$("select#domicilioCarretera\\.administracion\\.clave").change(function() {
		setDescripcionCombo("domicilioCarretera\\.administracion\\.clave","domicilioCarretera\\.administracion\\.descripcion");
	});
	
	$("select#domicilioCamino\\.terminoGeneral\\.clave").change(function() {
		setDescripcionCombo("domicilioCamino\\.terminoGeneral\\.clave","domicilioCamino\\.terminoGeneral\\.descripcion");
	});
	
	$("select#domicilioCamino\\.margen\\.clave").change(function() {
		setDescripcionCombo("domicilioCamino\\.margen\\.clave","domicilioCamino\\.margen\\.descripcion");
	});
	
	$("select#vialidadPrimaria\\.tipoVialidad\\.clave").change(function() {
		setDescripcionCombo("vialidadPrimaria\\.tipoVialidad\\.clave","vialidadPrimaria\\.tipoVialidad\\.descripcion\\.hidden");
		setDescripcionCombo("vialidadPrimaria\\.tipoVialidad\\.clave","vialidadPrimariaLbl");
		buscarVialidadNinguno($(this).val());
	});
	
	$("select#asentamiento\\.localidad\\.clave").change(function() {
		setDescripcionCombo("asentamiento\\.localidad\\.clave","asentamiento\\.localidad\\.nombre");
	});
	
	verificarErrores();
	verificarErroresRegreso();
	if($("#formComplemento").length) {
		setEventosChecarError("formComplemento",verificarErroresRegreso,".errors");
	} else {
		setEventosChecarError("formMunicipio",verificarErrores,".errors");
		setEventosChecarError("formCodigoPostal",verificarErrores,".errors");
	}
	$.unblockUI();
});

function sinNumero() {
	var activo = $("#sinNumero").hasClass("active");
	deshabilitarNumeroExterior(!activo);
}

function habiliarBusquedaPorCPoMun() {
	if(!isBlank("tipoBusquedaDomicilio")) {
		var valor = $("#tipoBusquedaDomicilio").val();
		mostrarMunCp(valor);
	} else {
		var entAux = $("form#formMunicipio input#cveEntAsenAux").val();
		var cpAux= $('form#formCodigoPostal #codigoPostal\\.codigoPostal').val();
		
		if(entAux != "") {
			mostrarMunCp('mun');
		} else if(cpAux != null && cpAux != ''){
			mostrarMunCp('cp');
		}
	}
}

function setDescripcionCombo(idCombo,idDescripcion) {
	var texto = $("select#"+idCombo+" option:selected").html();
	$("#"+idDescripcion).val(texto);
}

function mostrarVialidadPrimaria() {
	
	var tipoVialidad = $("#tipoBusquedaVialidad").val();
	habilitarVialidadCarCam(tipoVialidad);
	if(isBlank("tipoBusquedaVialidad") || tipoVialidad == TIPO_BUSQUEDA_VIALIDAD) {
		
		$("#divVialidadPrimaria").show();
		$("#vialidadNoEncontrada").hide();
		$("div#divVialidadPrim").hide();
		$("div#divCarretera").hide();
		$("div#divCamino").hide();
		
		limpiarValoresDiv("divCarretera");
		limpiarValoresDiv("divCamino");
		
	} else if(tipoVialidad == TIPO_BUSQUEDA_VIALIDAD_NL) {
		
		$("#vialidadPrimariaInput").val("NINGUNO");
		$("#vialidadNoEncontrada").show();
		$("#divVialidadPrimaria").hide();
		$("div#divVialidadPrim").show();
		$("div#divCarretera").hide();
		$("div#divCamino").hide();
		
		limpiarValoresDiv("divCarretera");
		limpiarValoresDiv("divCamino");
		
	} else if(tipoVialidad == TIPO_BUSQUEDA_CARRETERA) {
		$("select#domicilioCarretera\\.terminoGeneral\\.clave").addClass("disabled");
		$("#vialidadPrimariaInput").val("");
		$("#vialidadNoEncontrada").show();
		$("#divVialidadPrimaria").hide();
		$("div#divVialidadPrim").hide();
		$("div#divCarretera").show();
		$("div#divCamino").hide();
		$("#divLocalidadHidden").show();
		
		limpiarValoresDiv("divCamino");
		limpiarValoresDiv("divVialidadPrim");
		limpiarValoresDiv("divVialidadPrimaria");
	} else if(tipoVialidad == TIPO_BUSQUEDA_CAMINO) {
		$("#vialidadPrimariaInput").val("");
		$("#vialidadNoEncontrada").show();
		$("#divVialidadPrimaria").hide();
		$("div#divVialidadPrim").hide();
		$("div#divCarretera").hide();
		$("div#divCamino").show();
		$("#divLocalidadHidden").show();
		
		limpiarValoresDiv("divVialidadPrimaria");
		limpiarValoresDiv("divCarretera");
		limpiarValoresDiv("divVialidadPrim");
	}
}

function habilitarVialidadCarCam(opcion) {
	if(typeof $("#nuevoVialidad") !== 'undefined') {
		if(opcion == TIPO_BUSQUEDA_VIALIDAD_NL) {
			$("#nuevoVialidad").addClass('active');
			$("#nuevoCarretera").removeClass('active');
			$("#nuevoCamino").removeClass('active');
			
		} else if(opcion == TIPO_BUSQUEDA_CARRETERA) {
			$("#nuevoVialidad").removeClass('active');
			$("#nuevoCarretera").addClass('active');
			$("#nuevoCamino").removeClass('active');
		} else if(opcion == TIPO_BUSQUEDA_CAMINO) {
			$("#nuevoVialidad").removeClass('active');
			$("#nuevoCarretera").removeClass('active');
			$("#nuevoCamino").addClass('active');
		} else {
			$("#nuevoVialidad").removeClass('active');
			$("#nuevoCarretera").removeClass('active');
			$("#nuevoCamino").removeClass('active');
		}
	}
	
}
function deshabilitarNumeroExterior(deshabilitar) {
	
	if(deshabilitar) {
		$('#sinNumero').html("Con n&uacute;mero");
		$("#sinNumero").addClass("active");
		$("#numExterior1").val("");
		$("#numExteriorAlf").val("SN");
		$("#numExterior1").prop("readOnly","readOnly");
		$("#numExteriorAlf").prop("readOnly","readOnly");
	} else {
		$('#sinNumero').html("Sin n&uacute;mero");
		$("#sinNumero").removeClass("active");
		$("#numExterior1").removeAttr("readOnly");
		$("#numExteriorAlf").removeAttr("readOnly");
		$("#numExterior1").val("");
		$("#numExteriorAlf").val("");
	}
}

function elegirNuevamenteVialidad() {
		$("#tipoBusquedaVialidad").val(TIPO_BUSQUEDA_VIALIDAD);
		habilitarVialidadCarCam(0);
		limpiarElementosVialidades("vialidadPrimaria");
		$("#vialidadPrimariaInput").val(""); 
		$("#vialidadNoEncontrada").hide();
		$("#divVialidadPrimaria").show();
		limpiarValoresDiv("divVialidadPrimaria");
		limpiarValoresDiv("divCarretera");
		limpiarValoresDiv("divCamino");
		limpiarValoresDiv("divVialidadPrim");
		$("div#divVialidadPrim").hide();
		$("div#divCarretera").hide();
		$("div#divCamino").hide();
		$("#divLocalidadHidden").hide();
}


function elegirNuevoTipoVialidad(opcion) {
	$("#tipoBusquedaVialidad").val(opcion);
	if(opcion == TIPO_BUSQUEDA_VIALIDAD_NL) {
		$('#asentamiento\\.localidad\\.clave').val('');
		$("div#divVialidadPrim").show();
		$("div#divCarretera").hide();
		$("div#divCamino").hide();
		
		limpiarValoresDiv("divCarretera");
		limpiarValoresDiv("divCamino");
		$("#divLocalidadHidden").hide();
		habilitarVialidadCarCam(opcion);
	} else if(opcion==TIPO_BUSQUEDA_CAMINO){
		$("div#divVialidadPrim").hide();
		removerOpcionDeCombo("domicilioCamino\\.terminoGeneral\\.clave",1);
		$("div#divCamino").show();
		$("div#divCarretera").hide();
		$('#asentamiento\\.localidad\\.clave').val('');
		$("#divLocalidadHidden").show();
		limpiarValoresDiv("divVialidadPrimaria");
		limpiarValoresDiv("divVialidadPrim");
		limpiarValoresDiv("divCarretera");
		habilitarVialidadCarCam(opcion);
	} else {
		$("select#domicilioCarretera\\.terminoGeneral\\.clave").val(1);
		$("select#domicilioCarretera\\.terminoGeneral\\.clave").attr("disabled","disabled");
		$("select#domicilioCarretera\\.terminoGeneral\\.clave").addClass("disabled");
		$("#divLocalidadHidden").show();
		setDescripcionCombo("domicilioCarretera\\.terminoGeneral\\.clave","domicilioCarretera\\.terminoGeneral\\.descripcion");
		$("div#divVialidadPrim").hide();
		$("div#divCarretera").show();
		$("div#divCamino").hide();
		$('#asentamiento\\.localidad\\.clave').val('');
		limpiarValoresDiv("divCamino");
		limpiarValoresDiv("divVialidadPrim");
		limpiarValoresDiv("divVialidadPrimaria");
		habilitarVialidadCarCam(opcion);
	}
	
}

function removerOpcionDeCombo(idSelect,opcion) {
	$("#"+idSelect+" option").each(
		function(index) {
			if($(this).val() == opcion) {
				$(this).remove();
			}
		});
}

function buscarVialidadNinguno(tipoVialidad) {
	$.blockUI();
	$.ajax({
    	url : '/${mvn.web.app.root}'+'/domicilio/nacional/ubicar/get/vialidad/elegida',
        type: 'post',
        data : {
			cveEnt : $('input#asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val(),
			cveMun : $('input#asentamiento\\.localidad\\.municipio\\.clave').val(),
			nomVialidad : "NINGUNO",
			periodo: 4,
			cveTipoVialidad : tipoVialidad
		},
        dataType: 'json',
        success: function (result) {
        	$('input#vialidadPrimaria\\.clave\\.hidden').val(result.vialidadElegida.clave);
        	$('input#vialidadPrimaria\\.tipoVialidad\\.descripcion\\.hidden').val(result.vialidadElegida.tipoVialidad.descripcion);
        	//$('input#vialidadPrimaria\\.tipoVialidad\\.clave\\.hidden').val(result.vialidadElegida.tipoVialidad.clave);
        	$('#asentamiento\\.localidad\\.clave').val(result.localidadVialidadPrimaria.clave);
        	setDescripcionCombo("asentamiento\\.localidad\\.clave","asentamiento\\.localidad\\.nombre");
        	$.unblockUI();
        }
    });
	
}

function isBlank(idCampo) {
	
	if(typeof $("#" + idCampo).val() !== "undefined") {
		if($("#" + idCampo).val() != null) {
			var sinEspacios = $.trim($("#" + idCampo).val());
			$("#" + idCampo).val(sinEspacios);
			
			if(sinEspacios == "") {
				return true;
			} else {
				return false;
			}
			
		} else {
			return true;
		}
	} else {
		return true;
	}
}

function deshabiliarRadios(nameRadios) {
	
	$("input[name='"+nameRadios+"']").each(
		function(index) {
			$(this).attr("checked",false);
		}
	);
}

function limpiarValoresDiv(idDiv){
	$("#" + idDiv + " input").each(function(index) {
		$(this).val("");
	});
	
	$("#" + idDiv + " select").each(function(index) {
		$(this).val("-1");
	});
}

function setTipoBusquedaDomicilio(opcion) {
	var url = context_path + "/domicilio/nacional/ubicar/setTipoBusqueda";
	$.blockUI();
	$.postJSON(url , {descripcion: opcion} ,function(result) {
		mostrarMunCp(opcion);
		$.unblockUI();
	});
}

function mostrarMunCp(opcion) {
	
	if(opcion == "cp") {
		$("#codigoPostal").show();
		$("#municipio").hide();
		seleccionarOpcion(true);
		setSizeWithinIframe(document);
	} else {
		$("#codigoPostal").hide();
		$("#municipio").show();
		seleccionarOpcion(false);
		setSizeWithinIframe(document);
	}
	
}

function seleccionarOpcion(seleccionarCP) {
	if(seleccionarCP) {
		$("#busquedaCP").addClass('active');
		$("#busquedaMun").removeClass('active');
		pintarErrorGeneral(false);
	} else {
		$("#busquedaCP").removeClass('active');
		$("#busquedaMun").addClass('active');
		pintarErrorGeneral(false);
	}
}
function getAsentamientoPorMunicipio() {

	var cveEnt = $("form#formMunicipio select#localidad\\.municipio\\.entidadFederativa\\.clave").val();
	var cveMun = $("form#formMunicipio select#localidad\\.municipio\\.clave").val();

	if (cveMun != -1 && cveMun != '') {
		var url = context_path
				+ "/domicilio/nacional/ubicar/asentamiento/get/municipio";
		
		$.ajax({
			url : url,
			dataType : 'json',			
			data :  {'cveEnt' : cveEnt,'cveMun' : cveMun},
			beforeSend : function() {
				$('form#formMunicipio img#cveAsentamientoImgCargando').show();
	        },
			success : function(data) {
				setAsentamientos(data.asentamientos,"form#formMunicipio select#clave");
			},
			complete : function() {
				$('form#formMunicipio img#cveAsentamientoImgCargando').hide();
	        }
		});
	}
}

/**
 * Obtiene los asentamientos por codigo postal
 * 
 */
function getAsentamientoPorCodigo(hideErrors) {

	var codigo = $("form#formCodigoPostal input#codigoPostal\\.codigoPostal").val();
	var idUMF = $("#idUMF").val();
	var idDelegacion = $("#idDelegacion").val();
	var tipoTramite = $("#tipoTramite").val();
	var url;
	var otros_parametros;
	
	//tipoTramite es undefined desde el flujo normal
	//alert("tipoTramite: " + tipoTramite); se comenta el alert
	
	if(tipoTramite == 36){
		url = context_path +"/domicilio/nacional/ubicar/delegacion/asentamiento/get/codigoPostal";
		otros_parametros = {'codigo': codigo,'idDelegacion': idDelegacion};
		
//		$.getJSON(url ,{'codigo': codigo,'idDelegacion': idDelegacion}, function(data){
//			setAsentamientos(data.asentamientos, "select#asentamiento\\.clave");
//		}).error(function(data){
//			fnProcesarErrores(data, 'form#formCodigoPostal')
//		});
	}
	else if( tipoTramite == 101 || tipoTramite == 6 ){
		url = context_path +"/domicilio/nacional/ubicar/byUmf/asentamiento/get/codigoPostal";
		otros_parametros = {'codigo' : codigo,  'idUmf': idUMF};
	}
	else{
		url = context_path + "/domicilio/nacional/ubicar/asentamiento/get/codigoPostal";
		otros_parametros = {'codigo' : codigo};
	}
	
	$.ajax({
		url : url,
		dataType : 'json',
		data :  otros_parametros,
		beforeSend : function() {
			$('form#formCodigoPostal img#cveAsentamientoImgCargando').show();
        },
		success : function(data) {
			if(data.asentamientos.length > 0) {
				var claveEstado =  data.asentamientos[0].localidad.municipio.entidadFederativa.clave;
				var claveMunicipio = data.asentamientos[0].localidad.municipio.clave;
				
				$('form#formCodigoPostal input:hidden#asentamiento\\.localidad\\.municipio\\.clave').val(claveMunicipio);
				$('form#formCodigoPostal input:hidden#asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val(claveEstado);

			}
			setAsentamientos(data.asentamientos, "select#asentamiento\\.clave");
		},
		complete : function() {
			$('form#formCodigoPostal img#cveAsentamientoImgCargando').hide();
        },
        error : function (data) {
        	fnProcesarErrores(data, 'form#formCodigoPostal');
    		limpiarAsentamientos();
    		var options = "<option value='-1'>--Selecciona por favor--</option>";
    		$("form#formCodigoPostal select#localidad\\.clave").html(options);
        }
	});

}

function limpiarAsentamientos() {
	$('select#asentamiento\\.clave').html("");
	var optionSeleccione = "<option value='-1'>--Selecciona por favor--</option>";
	$("select#asentamiento\\.clave").append(optionSeleccione);
}

/**
 * 
 * @param asentamientos Lista de los asentamientos
 * @param select Id del select a actualizar
 */
function setAsentamientos(asentamientos, select) {
	
	var cveAsentAux = $('input#cveAsentamientoAux').val();
	
	// Se checa si el otro hidden tiene valor
	if (cveAsentAux == null || cveAsentAux == '' || cveAsentAux == '-1') {
		cveAsentAux	= $('input#cveAsentamientoCPAux').val();
	}
	
	asentamientosUbicados = asentamientos;
	var options = "<option value='-1'>--Selecciona por favor--</option>";
	for ( var i = 0; i < asentamientos.length; i++) {
	
		/*
		 * Se checa si el valor original de la entidad federativa y el municipio es igual al actual,
		 * de ser asi se checa si el input hidden que tiene la clave del asentamiento
		 * tienen un valor, si se cumple significa que hubo error o se viene
		 * de una pantalla posterior y se debe
		 * seleccionar el valor correspondiente en el select
		 */
		var cveEntidadActual = $("form#formMunicipio select#localidad\\.municipio\\.entidadFederativa\\.clave").val();
		var cveMuniAcutal = $("form#formMunicipio select#localidad\\.municipio\\.clave").val();
		if (cveEntidadOriginal == cveEntidadActual
				&& cveMunicipioOriginal == cveMuniAcutal && cveAsentAux != null
				&& cveAsentAux != '' && cveAsentAux == asentamientos[i].clave) {
			options += "<option value='" + asentamientos[i].clave + "' selected='selected'>"
			+ asentamientos[i].nombre + "</option>";
			
		} else {
			options += "<option value='" + asentamientos[i].clave + "'>"
					+ asentamientos[i].nombre + "</option>";
		}
		
	}
	$("" + select).html(options);
}

function getLocalidadPorMunicipio(){
	
	var cveEnt = $('input#asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val();
	var cveMun = $('input#asentamiento\\.localidad\\.municipio\\.clave').val();
	var localidadActual = $('#cveLocalidad').val();
	if(cveMun != -1 && cveMun != ''){
		var url = context_path +"/domicilio/nacional/ubicar/municipio/get/localidad"
		
		$.getJSON( url , {'cveEnt' : cveEnt, 'cveMun':cveMun}, function(data){
			setLocalidades(data.localidades, "#asentamiento\\.localidad\\.clave",localidadActual);
		}).error(function(data){
		});
		
	}
}

function setLocalidades(localidades, select, localidadS){
	localidadesUbicados = localidades;
	var checarLocalidad = localidadS != null && localidadS != "" && localidadS != "-1"; 
	var options = "<option value=''> -- Por favor seleccione -- </option>";
	for(var i = 0 ; i < localidades.length ; i++){
		if(checarLocalidad && localidades[i].clave == localidadS) {
			options += "<option value='" + localidades[i].clave + "' selected='selected'>" + localidades[i].nombre + "</option>";
			$("#asentamiento\\.localidad\\.nombre").val(""+localidades[i].nombre);
		} else {
			options += "<option value='" + localidades[i].clave + "'>" + localidades[i].nombre + "</option>";
		}
	}
	$(""+select).html(options);
}

var verificarErrores = function() {
	var formValidar = "";
	if(!isBlank("tipoBusquedaDomicilio")) {
		var valor = $("#tipoBusquedaDomicilio").val();
		var formValidar = valor == "cp" ? "formCodigoPostal" : "formMunicipio";
	} else {
		var entAux = $("form#formMunicipio input#cveEntAsenAux").val();
		var cpAux= $('form#formCodigoPostal #codigoPostal\\.codigoPostal').val();
		
		if(entAux != "") {
			formValidar = "formMunicipio";
		} else if(cpAux != null && cpAux != ''){
			formValidar = "formCodigoPostal";
		}
	}
	
	if(formValidar != "") {
		marcarCamposConErroresFromSpanErrors(formValidar,".error");
	}
}

var verificarErroresRegreso = function() {
	
	if($("#formComplemento").length) {
		marcarCamposConErroresFromSpanErrors('formComplemento',".error");
	}
}

function pintarErrorGeneral(tieneError){
	var cssColor = tieneError ? "red" : "black";
	var cssDisplay = tieneError ? "block":"none";
	var divErrores = document.getElementById("divErrorCampos");
	var labelGeneral;
	
	if($("#formComplemento").length) {
		labelGeneral = document.getElementById("labelCamposObligatoriosGeneral");
	} else if(!isBlank("tipoBusquedaDomicilio")) {
		var valor = $("#tipoBusquedaDomicilio").val();
		if(valor == "cp"){
			labelGeneral = document.getElementById("labelCamposObligatoriosGeneral");
		} else{
			labelGeneral = document.getElementById("labelCamposObligatoriosGeneralSecundario");
		}
	}
	
	if(labelGeneral != undefined && labelGeneral != null) {
		//labelGeneral.style.color = cssColor;
	}
	
	if(divErrores != undefined && divErrores != null) {
		divErrores.style.display = cssDisplay;
		divErrores.innerHTML = "<strong>&iexcl;Error en el formulario!</strong> No has llenado todos los campos requeridos. Por favor verifica."
	}
}

