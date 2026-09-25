//Prorrogas
PRORROGA_ESTUDIOS = "29";
PRORROGA_ENFERMEDAD = "30";
PRORROGA_INVALIDEZ = "11";
PRORROGA_PERMANENTE ="31";
PRORROGA_TEMPORAL = "32";
PRORROGA_ACUERDOS = "33";
PRORROGA_OBSTETRICOS = "34";
PRORROGA_LAUDO = "35";

$(document).ready(function() {
	
	if($("#tipoTramite").val() == PRORROGA_PERMANENTE){
		$("#tituloVigenciaPermanente").show();
		$("#subMenuLaudo").show();
	}else if($("#tipoTramite").val() == PRORROGA_ACUERDOS){
		$("#tituloAcuerdo").show();
		$("#subMenuAcuerdo").show();
	}else if($("#tipoTramite").val() == PRORROGA_OBSTETRICOS){
		$("#tituloObtetrico").show();		
	}else if($("#tipoTramite").val() == PRORROGA_ENFERMEDAD){
		$("#tituloEnfermedad").show();
		$("#subMenuLaudo").show();
	}else if($("#tipoTramite").val() == PRORROGA_ESTUDIOS){
		$("#tituloEstudios").show();
		$("#subMenuAcuerdo").show();
	}else if($("#tipoTramite").val() == PRORROGA_LAUDO){
		$("#tituloLaudo").show();
		$("#subMenuLaudo").show();
	}else if($("#tipoTramite").val() == PRORROGA_TEMPORAL){
		$("#tituloVigenciaTemporal").show();
	}
	
	
	$('#candidatos').dataTable( {
		bJQueryUI : true,
        bFilter : false,
        bInfo:true,
        bSort: false,
        "bPaginate": true,
        "bAutoWidth" : true,
        "iDeferLoading" : 0
        });
	
	/*
		var derechohabiente = $("input:radio[name=idCandidato]:checked").val();
		
		if(derechohabiente != undefined || derechohabiente != null){
			
			registroProrroga(derechohabiente);
		}	
		else
			errorNoSeleccionado();
	
	*/
	$('#aceptar').click(function() {
		
		var derechohabiente = $("input:radio[name=idCandidato]:checked").val();
		var tipoTramite = $("#tipoTramite").val();
		
		if(derechohabiente != undefined || derechohabiente != null)
			registroProrroga(derechohabiente,tipoTramite);
		else
			errorNoSeleccionado();
	});
	
	$('#cancelar').click(
			function(){	
				cancelar();
			}
	);
	
	 $('#regresar').click(
			 function(){
				 salir();
			 }
		);
	
});


function cancelar() {
	$decision = $('<div></div');
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : 'Selecciona una opci\u00F3n',
		modal : true,
		buttons : {
			"Si" : function() {
				location.href = "" + context_path + "/inicio/grupoFamiliar";
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Seguro que desea salir?');
	$decision.dialog('open');
}

function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}

function errorNoSeleccionado() {
	$noSeleccionado = $('<div></div');

	$noSeleccionado.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : 'Error',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$noSeleccionado.text('Debe seleccionar un derechohabiente');
	$noSeleccionado.dialog('open');
}


