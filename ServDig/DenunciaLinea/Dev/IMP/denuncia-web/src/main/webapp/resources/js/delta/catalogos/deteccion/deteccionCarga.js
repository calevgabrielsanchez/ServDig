var errorDialog;
var confirmDialog;

var hash = { 
		  '.xls'  : 1, 
		  '.xlsx' : 1 
		};

$(document).ready(function() {
	
	$("#cargaArchivoStatus").hide();
	
	$("a#btnDescargar").click(function(event){
		var contexto = $('#idContexto').val();
		var urlPDF = contexto + "/deteccion/carga/descargaXls.do";
		window.open(urlPDF,"PresentacionCorrecion","menubar=1,resizable=1,width=100,height=50");
	});
	
	$("a#btnCargar").click(function(event){
		confirmDialog.dialog("open");
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
				window.location.replace(contexto + "/deteccion/carga.do");
			}
		}
	});
	
	$("#btnCargar").hide();
	
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
				var resp = '';
				var urlPDF = contexto + "/deteccion/carga/cargarXls.do";
				bloquear();
				var userFile = $('form#archivo').val();
				var iframe = $('<iframe name="postframe" id="postframe" class="hidden" src="about:none" />');
				$('div#iframe').append(iframe);
					
				$('#deteccionCargaModel').attr("action", urlPDF)
				$('#deteccionCargaModel').attr("method", "post")
				$('#deteccionCargaModel').attr("archivo", userFile)
				$('#deteccionCargaModel').attr( "enctype", "multipart/form-data" )
				$('#deteccionCargaModel').attr( "encoding", "multipart/form-data" )
				$('#deteccionCargaModel').attr( "target", "postframe" )
				$('#deteccionCargaModel').submit();
					
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
//						alert(iFrameBody.innerHTML);
						resp = iFrameBody.innerHTML;
						
						errorDialog.html('');
						var sibStr = resp.split('--');
//						alert(sibStr);
						
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
								textToInsert[i++] = $.trim(sibStr[j]);
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