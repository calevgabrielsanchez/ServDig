var objCtrl = parent.NotificacionCtrl;
var oTable;
var dialogoConfirmar;
var dialogoError;

$(document).ready(function() {
	/*
	 * Initialse DataTables, with no sorting on the 'details' column
	 */
	oTable = $('#tblNotificaciones').dataTable({
		"bFilter" : false,
		"bSort" : false,
		"bAutoWidth" : false,
		"bFilter": false,
		"aoColumnDefs" : [ {
				"bSortable" : false,
				"aTargets" : [ 0 ]
			}, {
				"sClass": "alignCenter",
				"aTargets" : [2,3]
			}, { "bVisible": false, 
				"aTargets": [ 4 ] }],
	});

	/*
	 * Add event listener for opening and closing details Note that the
	 * indicator for showing which row is open is not controlled by DataTables,
	 * rather it is done here
	 */
	$('#tblNotificaciones tbody td img').live('click', function() {
		var nTr = this.parentNode.parentNode;
		
		if (this.src.match('details_close')) {
			/* This row is already open - close it */
			this.src = context_path + "/static/resources/estilos/images/details_open.png";
			$('div.innerDetails', $(nTr).next()[0]).slideUp();
			oTable.fnClose(nTr);
		} else {
			/* Open this row */
			this.src = context_path + "/static/resources/estilos/images/details_close.png";
			var nDetailsRow = oTable.fnOpen(nTr, fnFormatDetails(oTable, nTr), 'details' );
			nDetailsRow.className = nTr.className;
			$('div.innerDetails', nDetailsRow).slideDown();
		}
	});
	
	$('div.detalle-cambio-cerrado').live('click', function() {
		/* Open this row */
		$(this).attr('class','detalle-cambio-abierto');
		$('#divDetalleCambios', $(this).parent()).slideDown();		
	});
	
	$('div.detalle-cambio-abierto').live('click', function() {
		/* Close this row */
		$(this).attr('class','detalle-cambio-cerrado');
		$('#divDetalleCambios', $(this).parent()).slideUp();		
	});
	
	dialogoConfirmar = $( "#expirarNotifConfirmDiv" ).dialog({
		resizable: false,
		height:180,
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				expirarNotificaciones();
		 	},
		 	"CANCELAR": function() {
		 		$( this ).dialog( "close" );
		 	}
		 }
	 });
	
	dialogoError = $( "#msgErrorNotificacionesDiv" ).dialog({
		resizable: false,
		height:100,
		modal: true,
		autoOpen: false,
		buttons: {
			"ACEPTAR": function() {
				$( this ).dialog( "close" );
		 	}
		 }
	 });
	
	$("#btnAceptar").click(function(){
		parent.objDetalleNotifCtrl.cerrar();
	});
	
	$("#btnExpirar").click(function() {
		dialogoConfirmar.dialog( "open" );
	});
	
});

function expirarNotificaciones (){
	var aData = new Array();
	$('.btn-success.active', oTable.fnGetNodes()).each(function(index, value){
		aData.push($(this).val());
	});

	if (aData.length > 0) {
		$.postJSON('/gestionIndividuo-consulta-web/notificaciones/expirar', aData, function(data) {
			// Se recarga la URL del dialogo
			if ($.browser.msie) {
				
				if ($('#isMoral').val()) {
					url = "/gestionIndividuo-consulta-web/notificaciones/persona-moral/obtener-detalle/" + $('#idPersona').val() + "/" + $('#idModulo').val();
				} else {
					url = "/gestionIndividuo-consulta-web/notificaciones/persona/obtener-detalle/" + $('#idPersona').val() + "/" + $('#idModulo').val();
				}
				
				$('#refreshNotifForm').attr('action',url);
				$('#refreshNotifForm').submit();
			} else {
				location.reload(true);
			}
		}).error(function(data){
			
		});
	} else {
		dialogoConfirmar.dialog("close");
		$('#msgErrorLbl').text("No ha elegido notificaciones a expirar");
		dialogoError.dialog("open");
	}
	
	return false;
}

/* Formating function for row details */
function fnFormatDetails(oTable, nTr) {
	var aData = oTable.fnGetData(nTr);
	var sOut = '<div class="innerDetails">' + aData[4] + '</div>';

	return sOut;
}

function changeTextButton(boton){
	
	if($(boton).text() == 'Expirar'){
		$(boton).text('Por Expirar');
	} else {
		$(boton).text('Expirar');
	}
}