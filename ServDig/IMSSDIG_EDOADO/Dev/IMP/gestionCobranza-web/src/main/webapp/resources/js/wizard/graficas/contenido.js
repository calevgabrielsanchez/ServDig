/**
 * 
 */
$(document).ready(function() {
	
	$("#operacionesReportes").hide();
	var rp = $("#nrp").val();

	$("#reporteMot").live('click', function() {
		parent.impresionReportesCobranzaObject.reporteMotivo(rp);
	});

	$("#reporteMotRCV").live('click', function() {
		parent.impresionReportesCobranzaObject.reporteMotivoRCV(rp);
	});
	
	$('#cerrarWizard').click(function() {
		cerrarWizard();
	});
	
	var url = '/gestionCobranza-web/consulta/edoAdeudo/getTotales';
		
	
	$.postJSON(url,{'regPatronal': rp} , function(oData){
		
		if(oData.totalesImss != null) {
			$("#operacionesReportes").show();
			new Morris.Donut({
				  // ID of the element in which to draw the chart.
				  element: 'chartimss',
				  // Chart data records -- each entry in this array corresponds to a point on
				  // the chart.
				  data: oData.totalesImss
				});
		} else {
			$("#chartimss").hide();
		}
		
		if(oData.totalesRCV != null) {
			$("#operacionesReportes").show();
			new Morris.Donut({
				  // ID of the element in which to draw the chart.
				  element: 'chartrcv',
				  // Chart data records -- each entry in this array corresponds to a point on
				  // the chart.
				  data: oData.totalesRCV
				});
		} else {
			$("#chartrcv").hide();
		}
		
		$("#totalesImss").html(oData.totalImss);
		$("#totalesRCV").html(oData.totalRcv);
		
		
	});
	  
	
});

function cerrarWizard() {	
	parent.WizardEstadoAdeudoCtrl.cerrar();
}

function mostrarMensajeDiv(nombreDiv,mensaje) {
	var text ='<div class="ui-state-highlight ui-corner-all" style="margin-top: 12px; padding: 0 .5em;">' +
		'<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>' + mensaje +
		'</p></div>';
	
	$("#"+nombreDiv).html(text);
}