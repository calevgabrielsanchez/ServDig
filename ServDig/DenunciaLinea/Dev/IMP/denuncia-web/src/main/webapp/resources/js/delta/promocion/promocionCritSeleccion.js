var errorDialog;
var confirmDialog;

var hash = { 
		  '.xls'  : 1, 
		  '.xlsx' : 1 
		};

var PromocionModelForm = function(idTipo, idOrigen, idCriterio, fechaNotificacion, numeroOficioID, 
		fechaPromocionID, domCalle, refColonia, numNroext, numNroint, numCodigopostal){
	this.idTipo = 0;
	this.idOrigen = 0;
	this.idCriterio = 0;
	this.fechaNotificacion = '';
	this.numeroOficioID = 0;
	this.fechaPromocionID = '';
	this.domCalle = '';
	this.refColonia = '';
	this.numNroext = '';
	this.numNroint = '';
	this.numCodigopostal = '';
}

var promocionModel = new PromocionModelForm(0, 0, 0, '', 0, '', '', '', '', '', '');

$(document).ready(function() {
	
	$("#cargaArchivoStatus").hide();
	
	$("a#btnDescargar").click(function(event){
		var contexto = $('#idContexto').val();
		var urlPDF = contexto + "/promocion/carga/descargaXls.do";
		window.open(urlPDF,"PresentacionCorrecion","menubar=1,resizable=1,width=500,height=500");
	});
	
	$("a#btnCargar").click(function(event){
		confirmDialog.dialog("open");
	});
	
	$("#idOrigen").change(function() {
		var valTipo = $("#idTipo").val();
		var valOrigen = $(this).val();
		var urlCriterioOrigen = $("#rutaCriterioSeleccion").val();
		if(valTipo != null && 
				valTipo != -1){
			if(valOrigen != null &&
					valOrigen != -1){
//				var modelo = $("#promocionCargaModel").serializeObject(true);
				promocionModel.idTipo = valTipo;
				promocionModel.idOrigen = valOrigen;
				bloquear();
				$.postJSON(urlCriterioOrigen, promocionModel, function(data) {
					$("#idCriterio").html("");
//					$('#idCriterio').append($("<option></option>").attr("value",-1).text('<-Seleccione un Criterio de Seleccion->'));
					jQuery.each(data.criterios, function() { 
						$('#idCriterio').append($("<option></option>").attr("value",this.id).text(this.descripcion));
					});
					
				}).error(function(data){ 
//					alert("error: " + JSON.stringify(data, null, 4));
					errorDialog.html(data.responseText);
					errorDialog.dialog("open");
				}).complete(function(){
					desbloquear(); 
				});
			}else{
				alert("Seleccione un valor para Origen");
			}
		}else{
			alert("Seleccione un Tipo de Promocion");
		}
    });
	
	$("#archivo").change(function() {
		var filename = $(this).val();
		var len = filename.length;
//		alert(len);
//		alert(filename);
		var ext = filename.substring(filename.lastIndexOf('.'), len);
//		alert(ext);
	    if (hash[ext]) {
	    	$("#btnCargar").show();
	    } else {
	    	alert("Archivo Incorrecto, no es un archivo de Excel");
	    	$("#btnCargar").hide();
	    } 
    });
	
	$("#idTipo").change(function() {
		$("#idCriterio").html("");
		$('#idCriterio').append($("<option></option>").attr("value",-1).text('<-Seleccione un Criterio de Seleccion->'));
    });
	
	//Dialogo para mostrar errores
	errorDialog = $("#dialog-error").dialog({
		autoOpen: false,
		modal: true,
		resizable: true,
		width: 930,
		buttons: {
			Ok: function() {
				$(this).dialog("close");
				var contexto = $('#idContexto').val();
				window.location.replace(contexto + "/promocion/carga.do");
			}
		}
	});
	
	$("#btnCargar").hide();
	
	// a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
	$( "#dialog:ui-dialog" ).dialog( "destroy" );

	confirmDialog = $( "#dialog-confirm" ).dialog({
		autoOpen: false,
		resizable: false,
		height:160,
		width: 500,
		modal: true,
		buttons: {
			"Si esta cerrado el archivo": function() {
				$( this ).dialog( "close" );
				var contexto = $('#idContexto').val();
				var urlPDF = contexto + "/promocion/carga/cargarXls.do";
				var valCriterio = $("#idCriterio").val();
				if(valCriterio != null &&
						valCriterio != -1){
					bloquear();
					var userFile = $('form#archivo').val();
					var iframe = $('<iframe name="postframe" id="postframe" class="hidden" src="about:none" />');
					$('div#iframe').append(iframe);
					
					$('#promocionCargaModel').attr("action", urlPDF)
					$('#promocionCargaModel').attr("method", "post")
					$('#promocionCargaModel').attr("archivo", userFile)
					$('#promocionCargaModel').attr( "enctype", "multipart/form-data" )
					$('#promocionCargaModel').attr( "encoding", "multipart/form-data" )
					$('#promocionCargaModel').attr( "target", "postframe" )
					$('#promocionCargaModel').submit();
					
					$("#postframe").load(
						function(){
							var iFrame = $("iframe")[0];
							var i = 0;
							var textToInsert = [];
							if ( iFrame.contentDocument ){ // FF
								iFrameBody = iFrame.contentDocument.getElementsByTagName('body')[0];
							}else if ( iFrame.contentWindow ){ // IE
								iFrameBody = iFrame.contentWindow.document.getElementsByTagName('body')[0];
							}
//							alert(iFrameBody.innerHTML);
							resp = iFrameBody.innerHTML;
							
							errorDialog.html('');
							var sibStr = resp.split('--');
//							alert(sibStr);
							
							textToInsert[i++] = '<ol>';
							
							textToInsert[i++] = '<li>Numero de registros insertados: ';
							textToInsert[i++] = removePreTag($.trim(sibStr[0]));
							textToInsert[i++] = '</li>';
							
							textToInsert[i++] = '<li>Numero de registros no insertados: ';
							textToInsert[i++] = removePreTag($.trim(sibStr[1]));
							textToInsert[i++] = '</li>';
							
							if(sibStr.length > 2){
								for(j = 2 ; j < sibStr.length ; j++){
									textToInsert[i++] = '<li>Error: ';
									textToInsert[i++] = removePreTag($.trim(sibStr[j]));
									textToInsert[i++] = '</li>';
								}
							}
							
							textToInsert[i++] = '</ol>';
							
							var list = $("#dialog-error").append(textToInsert.join(''));
							
							
							//errorDialog.html();
							errorDialog.dialog("open");
							$("#btnCargar").hide();
							desbloquear();
						}
					);
				}else{
					alert("Seleccione un valor para el criterio de seleccion");
				}
			},
			"NO esta cerrado el archivo": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
});

function removePreTag(cadena){
	var s = cadena.toUpperCase();
	if(cadena.indexOf('<PRE>') != -1){
		s = cadena.replace('<PRE>', '');
	}else if(cadena.indexOf('</PRE>') != -1){
		s = cadena.replace('</PRE>', '');
	}
//	alert(s);
	return s;
}

function descarga(contexto){
	var urlPDF = contexto + "/servlet/EnviaArchivoServlet";
	window.open(urlPDF,"PresentacionCorrecion","menubar=1,resizable=1,width=500,height=500");
}