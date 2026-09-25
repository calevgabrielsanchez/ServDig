function generaDocumentos (){
  //var direccion = context_path +'/reportesDocumentos/generarCartillaNacionalSalud';
	var documentosProrroga=context_path +'/documentos/documentosProrroga';
	var page=context_path + "/resources/js/delta/viewPdf.html";
	
//	var total = documentos;
//	if(total=="1"){
//		
//		direccion+="/"+idPersona;
//	
//	}else{
		
		documentosProrroga+='?idTramite='+idTramite+'&idDerechohabiente='+idPersona;
		direccion=documentosProrroga;
		
	//}
	
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");
}

function generaDocumentos (idTramite){
	//var direccion=context_path +'/reportesDocumentos/generarCartillaNacionalSalud';
	var documentosProrroga=context_path +'/documentos/documentosProrroga';
	var page=context_path + "/resources/js/delta/viewPdf.html";
	
//	var total = documentos;
//	if(total=="1"){
//		
//		direccion+="/"+idPersona;
//	
//	}else{
		
		documentosProrroga+='?idTramite='+idTramite+'&idDerechohabiente='+idPersona;
		direccion=documentosProrroga;
		
	//}
	
	window.showModalDialog(page,direccion,"resizable:1;dialogHeight:550px;dialogwidth:985px;scroll:yes;status=no");
}



function salir() {
	
	location.href = "" + context_path + "/inicio/grupoFamiliar";
	
	/*$decision = $('<div></div');
	
	$decision.dialog({
		autoOpen : false,
		resizable: false,
		height: 140,
		title: 'Seleccione una opci\u00F3n',
		modal: true,
		buttons: {
			"Si": function() {
				location.href = "" + context_path + "/inicio/grupoFamiliar";
			},
			"No": function() {
				cierraDialogo($(this));
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$decision.text('\u00BF Seguro que desea salir?');
	$decision.dialog('open');
	*/
}

function convetToDate(fechaC){
	var fecha = new Date(fechaC);
	var dia = fecha.getDate(); 
	if(dia < 10){
		   mes = "0"+dia;
	}

	var mes = fecha.getMonth() + 1;
	if(mes < 10){
		   mes = "0"+mes;
	}

	var anio = fecha.getFullYear();
	
	return ""+dia+"/"+mes+"/"+anio;
}

/**
 * Mensaje de confirmacion para las prorrogas
 * @param mensaje
 */
function prorrogaConfirmacion(mensaje) {

	$decision = $('<div></div');
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 200,
		weight : 250,
		title : 'Confirmaci\u00F3n',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				$decision.dialog('close');
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text(mensaje);
	$decision.dialog('open');
}


