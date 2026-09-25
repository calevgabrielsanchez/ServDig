var busquedaEscDesCtrl = {
	context: '/${mvn.web.app.root}',
	tipoBusqueda: 1,
	tipoRespaldo: 1,
	BUSQUEDA_FOLIO : 1, 
	BUSQUEDA_REG_PAT : 2, 
	BUSQUEDA_PERIODO : 3,
	fechaActual: 0,
	folio : 0,
	registroPatronal : 0,
	fechaInicio : 0,
	fechaFin : 0,
	SIN_PERFIL : 0,
	tipoBuscar:"",
	optionDefault: '<option value=\"0\">--Selecciona por favor--</option>',
	init: function() {
		busquedaEscDesCtrl.cargarPeriodos();
		$(".panelEscDes").on("click",busquedaEscDesCtrl.procesaTipoBusqueda);
		$("#buscarHistorial").on("click", busquedaEscDesCtrl.busqueda);
		$(".paramBusqueda").on("click",busquedaEscDesCtrl.consultaTipoBuscar);
		//inicializamos los combos de delegacion y subdelegacion
		combosComunesCtrl.init();
	},
	procesaTipoBusqueda: function() {
		busquedaEscDesCtrl.tipoRespaldo = busquedaEscDesCtrl.tipoBusqueda = this.id.replace('escDes','');
		busquedaEscDesCtrl.limpiarTipoBusqueda();
		
		combosComunesCtrl.limpiarCombos();
		busquedaEscDesCtrl.resetPeriodo();
		busquedaEscDesCtrl.folio=0; 
		busquedaEscDesCtrl.registroPatronal=0; 	
		busquedaEscDesCtrl.fechaInicio=0;
		
		var datosBusqueda = busquedaEscDesCtrl.datosBusqueda[busquedaEscDesCtrl.tipoBusqueda-1];
		
		$("#opcionesFolioRecepcion")[datosBusqueda.showFolio]();
		$("#opcionesRegistroPatronal")[datosBusqueda.showRP]();
		$("#opcionesPeriodo")[datosBusqueda.showPeriodo]();
		$("#delegacionSubdelegacion")[datosBusqueda.showDel]();
		$("#"+datosBusqueda.campo).focus();
		$('div#contenedorEscritoDesacuerdoHistorial').html('');		
	},
	setValorCombo: function(event) {
		var atributo = event.data.attr;
		busquedaEscDesCtrl[atributo] = this.value;
		if(busquedaEscDesCtrl[atributo+"Change"])busquedaEscDesCtrl[atributo+"Change"]();
	},
	resetPeriodo: function() {
		$("#fechaInicio").val('');
		$("#fechaFin").val(busquedaEscDesCtrl.fechaActual);		
	},
	mostrarMensajeError: function(mensaje) {
		$("#error").html(mensaje).show();
		$("#contenedorEscritoDesacuerdoHistorial").html('');
	},
	ocultarError: function() {
		$("#error").html("").hide();
	},
	errores: {
		datos: "Debe ingresar alguno de los datos para la b\u00FAsqueda.",
		MSG011: "La Fecha Fin ingresada no puede ser anterior a la Fecha Inicio.",
		MSG010: "La Fecha Inicio no puede ser mayor a la fecha en que se realiza la consulta.",
		MSG010_1: "La Fecha Fin no puede ser mayor a la fecha en que se realiza la consulta."
		
	},
	limpiarTipoBusqueda: function() {
		$('#registroPatronal').val('');
		$('#folioRecepcion').val('');
		$('#fechaInicio').val('');
		$('#fechaFin').val('');
		busquedaEscDesCtrl.ocultarError();
	},
	busqueda: function() {
		$('div#contenedorEscritoDesacuerdoHistorial').html('');
		busquedaEscDesCtrl.ocultarError();
		if(busquedaEscDesCtrl.validaDatos()){
			if(busquedaEscDesCtrl.tipoBusqueda == 1) {
				busquedaEscDesCtrl.buscarEscritoPorFolio($("#folioRecepcion").val());
			} else {
				busquedaEscDesCtrl.ejecutarConsulta();
			}
		}
	}, 
	datoInvalido: function(dato) {
		return dato === "" || dato === undefined || dato === null;
	},
	cargarPeriodos: function() {
		
		busquedaEscDesCtrl.cargarCombo("/getPeriodos",function(data) {
			busquedaEscDesCtrl.fechaActual = data.actual;
			var defautlsDatePicker = {
				"dateFormat": 'dd/mm/yy',
				"maxDate": data.actual,
				"changeYear": true,
				"changeMonth": true
			};
			$( "#fechaInicio" ).datepicker(defautlsDatePicker);
			$( "#fechaFin" ).datepicker(defautlsDatePicker);
			busquedaEscDesCtrl.resetPeriodo();
		});
	},
	cargarCombo: function(url, funcion) {
		$.ajax({
			url : busquedaEscDesCtrl.context + "/escrito/consultar/comunes"+url,
			data : null,
			cache: false,
			type: 'POST',
			beforeSend : $.blockUI,
			success : funcion,
			complete: $.unblockUI
		});
	},
	validaDatos: function() {
		var tipoBusqueda = busquedaEscDesCtrl.tipoBusqueda,
		datosBusqueda= busquedaEscDesCtrl.datosBusqueda[busquedaEscDesCtrl.tipoBusqueda-1],
		
		fLen = datosBusqueda.campos.length;
		for (i = 0; i < fLen; i++) {
			nombreCampo = datosBusqueda.campos[i];
			valorCampo=$("#"+nombreCampo).val();
			if(busquedaEscDesCtrl.datoInvalido(valorCampo)) {
				if (nombreCampo != 'registroPatronal' && nombreCampo != 'fechaInicio') {
				busquedaEscDesCtrl.mostrarMensajeError(busquedaEscDesCtrl.errores['datos']);
				return false;
				}
			}
		}
		return datosBusqueda.valida();
	},
	datosBusqueda: [{		
		url: '/escrito/consultar/findEscritoDesacuerdoFolio',
		sinDato: '0',
		showFolio: 'show',
		showRP: 'hide',
		showPeriodo: 'hide',
		showDel: 'hide',
		campo: 'folioRecepcion',
		campos: ['folioRecepcion'],
		error: 'Ocurri\u00F3 un error inesperado al cargar los escritos de desacuerdo',//TODO check message
		valida: function() {
			return true;
		}
	},{	
		url: '/escrito/consultar/findEscritoDesacuerdoRegPatron',
		sinDato: '0',
		campo: 'registroPatronal',
		showFolio: 'hide',
		showRP: 'show',
		showPeriodo: 'show',
		showDel: 'hide',
		campos: ['registroPatronal','fechaFin', 'fechaInicio'],
		error: 'Ocurri\u00F3 un error inesperado al cargar los escritos de desacuerdo',//TODO check message
		valida: function() {
			return true;
		}
	},{
		url: '/escrito/consultar/findEscritoDesacuerdoPeriodo',
		sinDato: '0',		
		campo: 'fechaInicio', 
		campos: ['fechaFin', 'fechaInicio'],
		showFolio: 'hide',
		showRP: 'hide',
		showPeriodo: 'show',
		showDel: 'show',
		error: 'Ocurri\u00F3 un error inesperado al consultar por Per&iacute;odo',
		valida: function() {
			return busquedaEscDesCtrl.validaFechas();
		}
	}],
	buscarEscritoPorFolio: function(folio) {
		var busqueda = busquedaEscDesCtrl.datosBusqueda[0];		
		url = busquedaEscDesCtrl.context + busqueda.url ;
		busquedaEscDesCtrl.folio = folio;
		bovedaCtrl = null;
		busquedaEscDesCtrl.consultaAjaxHistorialEscDes(url, busqueda.error, 		
			function(data) {
				busquedaEscDesCtrl.construirDialogo("#mensajes", "Detalle de Folio",data);
				$.unblockUI();
			}, function() {
				console.log("no se cneonctro el folio");
				$("#error").html("No se encontr&oacute; informaci&oacute;n con el folio proporcionado.");
				$("#error").show();
				$.unblockUI();
			}
		);
	
	},
	ejecutarConsulta: function() {
		var busqueda = busquedaEscDesCtrl.datosBusqueda[busquedaEscDesCtrl.tipoBusqueda-1];
		url = busquedaEscDesCtrl.context + busqueda.url ;//+ ( busquedaEscDesCtrl.getSinDato(valorBusqueda,busqueda.sinDato) );
		
		busquedaEscDesCtrl.folio = busquedaEscDesCtrl.getSinDato($('#folioRecepcion').val(), busqueda.sinDato);
		//busquedaEscDesCtrl.registroPatronal = busquedaEscDesCtrl.getSinDato($('#registroPatronal').val(), busqueda.sinDato);
		busquedaEscDesCtrl.registroPatronal = $('#registroPatronal').val();
		busquedaEscDesCtrl.fechaFin = busquedaEscDesCtrl.getSinDato($('#fechaFin').val(), busqueda.sinDato);			
		//busquedaEscDesCtrl.fechaInicio  = busquedaEscDesCtrl.getSinDato($('#fechaInicio').val(), busqueda.sinDato);
		busquedaEscDesCtrl.fechaInicio  = $('#fechaInicio').val();

		busquedaEscDesCtrl.consultaAjaxHistorialEscDes(url, busqueda.error, 		
			function(data) {
				busquedaEscDesCtrl.tipoBusqueda = busquedaEscDesCtrl.tipoRespaldo;  

				var $escritosDesacuerdo = $('#contenedorEscritoDesacuerdoHistorial');
				$escritosDesacuerdo.html(data);
				$escritosDesacuerdo.get(0).scrollIntoView();  

				$('#mensajes').hide();
				$.unblockUI();
			}
		);
	
	},
	consultaAjaxHistorialEscDes: function(url, msjError, ejecutar, funcionError) {
		var error = funcionError || function(data) {
			$('#error').html(msjError);
			$('div#mensajes > div').hide();
			$.unblockUI();
		};
		
		$.ajax({ 
			url : url,
			data : {
				delegacion: combosComunesCtrl.delegacion,
				subdelegacion: combosComunesCtrl.subdelegacion,
				folio: busquedaEscDesCtrl.folio,
				registroPatronal : busquedaEscDesCtrl.registroPatronal,
				fechaInicio: busquedaEscDesCtrl.fechaInicio,
				fechaFin: busquedaEscDesCtrl.fechaFin				
			},
			cache: false,
			type: 'POST',
			beforeSend : function() {
				$('div#mensajes > div').show();
				$.blockUI();
			},
			success : ejecutar,
			error : error
		});
	},	
	getSinDato: function(dato, sinDato) {
		if(busquedaEscDesCtrl.datoInvalido(dato)){
			return sinDato;
		}
		return dato;
	},	
	validaFechas: function(){
		var fechaInicioValor = $("#fechaInicio").datepicker('getDate');
		var fechaFinValor = $("#fechaFin").datepicker('getDate');

		//RN018 periodo valido 
		var hoy = $.datepicker.parseDate('dd/mm/yy', busquedaEscDesCtrl.fechaActual); 
		 
		var difInicio = hoy - fechaInicioValor;
		var diasFechaInicio = difInicio / 1000 / 60 / 60 / 24;
		if( diasFechaInicio <0 ){
			busquedaEscDesCtrl.mostrarMensajeError(busquedaEscDesCtrl.errores['MSG010']);
			return false;
		}
		
		var difFin = hoy - fechaFinValor;
		var diasFechaFin = difFin / 1000 / 60 / 60 / 24;
		if( diasFechaFin <0 ){
			busquedaEscDesCtrl.mostrarMensajeError(busquedaEscDesCtrl.errores['MSG010_1']);
			return false;
		} 
		 
		//RN021 fecha fin 
		var diferencia = fechaFinValor - fechaInicioValor;
		var dias = diferencia / 1000 / 60 / 60 / 24;
		if( dias <0 ){
			busquedaEscDesCtrl.mostrarMensajeError(busquedaEscDesCtrl.errores['MSG011']);
			return false;
		}
		
		return true;	
	},
	
	construirDialogo: function (divId, titulo, mensaje) {
		$("#mensajes").html(mensaje);
		height = 750;
		width = 800;
		bovedaCtrl = null;
		var objDialogo = $(divId).dialog({
			autoOpen : false,
			resizable : false,
			modal : true,
			height : height,
			width : width,
			title : titulo,
			buttons : {
				"Aceptar" : function() {
					$(this).dialog("close");
				}
			}
		});
		objDialogo.dialog('open');
	},
	consultaTipoBuscar: function(){
		busquedaEscDesCtrl.tipoBuscar = this.id;

		if(busquedaEscDesCtrl.tipoBuscar == "folioRecepcion"){
			busquedaEscDesCtrl.tipoRespaldo = busquedaEscDesCtrl.tipoBusqueda = 1;
			} else {
			busquedaEscDesCtrl.tipoRespaldo = busquedaEscDesCtrl.tipoBusqueda = 2;
			}
	}
}

function generarExcelEscritoDesacuerdo() {
	url = busquedaEscDesCtrl.context + '/escrito/consultar/generaReporteExcelEscrtoDesacuerdo';
	window.open(url, '_blank');
}

$(document).ready(busquedaEscDesCtrl.init);





