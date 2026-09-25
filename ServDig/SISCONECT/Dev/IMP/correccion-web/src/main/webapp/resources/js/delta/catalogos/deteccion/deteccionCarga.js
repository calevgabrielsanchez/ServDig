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
		//var urlPDF = contexto + "/deteccion/carga/descargaXls.do";
		bloquear();
		
		var URL_ACTION = '/deteccion/carga/descargaWindow.do?unDato=1';
		var resultado = openDownloadFileWindow(contexto,URL_ACTION);
		
		window.location=contexto+"/deteccion/carga/descargaReturn.do";
		
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
			}
		}
	});
	
	

	if($("#detalles")!=null ){
        if($("#detalles").val().length>0){
		var i = 0;
		var textToInsert = "";
		
		errorDialog.html('');
		var sibStr = $("#detalles").val().split('--');
//		alert(sibStr);
		
		textToInsert = textToInsert + '<ol>';
		
		textToInsert = textToInsert +  '<li>Numero de registros insertados: ';
		textToInsert = textToInsert +  removePreTag($.trim(sibStr[0]));
		textToInsert = textToInsert +  '</li>';
		
		textToInsert = textToInsert +  '<li>Numero de registros no insertados: ';
		textToInsert = textToInsert +  removePreTag($.trim(sibStr[1]));
		textToInsert = textToInsert +  '</li>';
		
		if(sibStr.length > 2){
			for(j = 2 ; j < sibStr.length ; j++){
				textToInsert = textToInsert +  '<li>Error: ';
				textToInsert = textToInsert +  $.trim(sibStr[j]);
				textToInsert = textToInsert +  '</li>';
			}
		}
		
		textToInsert = textToInsert +  '</ol>';
		
		 $("#dialog-error").html(textToInsert);
		 $( "#dialog-confirm" ).dialog("close");
		  $("#dialog-error").dialog("open");
        }
	}
	
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
				var userFile = $('form#archivo').val();
				$("detalles").val("");
				$('#deteccionCargaModel').attr("action", urlPDF)
				$('#deteccionCargaModel').attr("method", "post")
				$('#deteccionCargaModel').attr("archivo", userFile)
				$('#deteccionCargaModel').attr( "enctype", "multipart/form-data" )
				$('#deteccionCargaModel').attr( "encoding", "multipart/form-data" )
				$('#deteccionCargaModel').submit();

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