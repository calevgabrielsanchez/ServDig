$(document).ready(function() {
	initPantalla();
});

function initPantalla() {
	
	$.blockUI();
	$.postJSON_Sync(getAppContextParaJS()+"/estrados/consultaDiasInhabiles.do", null, function(data) {
		listDiasInhabilDTOs = data;
	}).error(function(data) {
		
	}).complete(function(data) {
		$.unblockUI();
	});
	
	$("#idFechaPublicacion").datepicker({
		dateFormat: 'yy-mm-dd',
		buttonImage: getAppContextParaJS()+"/resources/imagenes/calendarIcon.gif",
		showOn: "button",
		beforeShowDay: inhabiles,
		changeYear: true,
		buttonImageOnly: true,
		maxDate: "-1D",
		onSelect: function(dateText, inst) { 
			if($("#idFechaPublicacion").val()!=""){
			}
	  }
	});
	
	$("#botonBuscar").click(function(){
		var valEstatus = $("#idStatusCombo").val();
		var valFecha = $("#idFechaPublicacion").val();
		$.blockUI();
		$.postJSON_Sync(getAppContextParaJS()+"/estrados/consultaInternaEjecuta.do", {"idCombo":valEstatus,"fechaEjecuta":valFecha}, function(data) {
			$("#idResultado").val(data.numRegistros);
			$("#deLaFecha").val(valFecha);
		}).error(function(data) {
		}).complete(function(data) {
			$.unblockUI();
		});
	});
	
	$("#botonPublicar").click(function(){
		var valEstatus = $("#idStatusCombo").val();
		var valFecha = $("#idFechaPublicacion").val();
		var numRegEjecutados = null;
		$.blockUI();
		$.postJSON_Sync(getAppContextParaJS()+"/estrados/ejecutaTareaNotifica.do", {"idCombo":valEstatus,"fechaEjecuta":valFecha}, function(data) {
			numRegEjecutados = data.numRegEjecutados;
			//$("#idRegistrados").val(numRegEjecutados);
			$("#deLaFecha").val(valFecha);
		}).error(function(data) {
			
		}).complete(function(data) {
			$.unblockUI();
			
			var text1 = "";
			var text2 = "";
			
			if ($("#idStatusCombo").val() != 2) {
				if (numRegEjecutados != 1) {
					text1 = "retiraron";
					text2 = "notificaciones";
				} else {
					text1 = "retir\u00f3";
					text2 = "notificaci\u00f3n";
				}
			} else if ($("#idStatusCombo").val() == 2) {
				if (numRegEjecutados != 1) {
					text1 = "publicaron";
					text2 = "notificaciones";
				} else {
					text1 = "public\u00f3";
					text2 = "notificaci\u00f3n";
				}
			}
			
			$( "#dialogoEjecutados" ).dialog({
				autoOpen: false, 
				modal: true,
				title: "Mensaje del sistema"
				});
			$( "#dialogoEjecutados" ).html("Se " + text1 + " " + numRegEjecutados + " " + text2);
			$( "#dialogoEjecutados" ).dialog("open");
		});
	});
	
	$("#idStatusCombo").change(function (val) {
		if ($("#idStatusCombo").val() == 2) {	
			$("#botonPublicar").text("Publicar manualmente");
			$("#idResultado").val("");
			$("#deLaFecha").val("");
			$("#idFechaPublicacion").datepicker({
				minDate: "-10D", maxDate: "-1D"});
		} else if ($("#idStatusCombo").val() == 3) {
			$("#botonPublicar").text("Retirar manualmente");
			$("#idResultado").val("");
			$("#deLaFecha").val("");
			$("#idFechaPublicacion").datepicker({maxDate: "-11D"});
		}
	});
	
	$("#botonLimpiar").click(function(){
		$("#idResultado").val("");
		$("#deLaFecha").val("");
	});

	function recuperaFechaServidor() {
		var fecha="";
		$.blockUI();
		$.postJSON_Sync(getAppContextParaJS()+"/estrados/recuperaFechaServidor.do", null,function(data) {
	    
		}).error(function(data){
			
		}).complete(function(data){
			$.unblockUI();
			fecha=data.responseText;
		});	
		return fecha;
	}
	
	$("#btnSalir").click(function() {
		$("#redireccionListado").submit();
	});
	
}
