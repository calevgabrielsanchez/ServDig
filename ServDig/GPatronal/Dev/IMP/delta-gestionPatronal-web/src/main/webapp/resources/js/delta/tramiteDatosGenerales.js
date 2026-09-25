var idtramite=-1;
var idSolicitud=-1;
var dialogoAdvertencia="#cancelarSol";

$(document).ready(function() {
	oDialogAdvertencia = $(
			dialogoAdvertencia).dialog( {
		autoOpen : false,
		resizable : false,
		modal : true,
		height : 150,
		width : 350,
		buttons : {
			"Aceptar" : function() {
				cancelar();
			},
			"Cancelar" : function() {
				$(this).dialog('close');
			}
		}
	});
	
});

function abrirAdvertencia(idT, idS){
	idtramite=idT;
	idSolicitud=idS;
	oDialogAdvertencia.dialog('open');
}

function cancelar(){
	var objForm = new Object();
    sSource = context_path +'/sujetoObligado/cierraTramite/'+idSolicitud;
    $.postJSON(sSource, objForm, function(data) {
		/*Actualizamos datos*/
    	oDialogAdvertencia.dialog("close");
    	history.back();
	}).error(function(data) {
	});
}