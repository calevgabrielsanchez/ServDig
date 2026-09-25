$(document).ready(function() {

	var indiceHistoriaLaboral = 0;
	var indiceNSS = 0;
	var indiceDocumentoProbatorio = 0;
	
	$( "#registroFechaInscripcion" ).datepicker();
	$( "#registroFechaInscripcion" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );

	$( "#registroFechaBaja" ).datepicker();
	$( "#registroFechaBaja" ).datepicker( "option", "dateFormat", 'dd/mm/yy' );
	
	$('#agregarHistoriaLaboral').click(function() {
		
		var registroNombrePatron = $("#registroNombrePatron").val();
		var registroEntidadFederativa = $("#registroEntidadFederativa").val();
		var registroFechaInscripcion = $("#registroFechaInscripcion").val();
		var registroFechaBaja = $("#registroFechaBaja").val();
		var arrayCampos =[registroNombrePatron,registroEntidadFederativa,registroFechaInscripcion,registroFechaBaja];
		fnCrearRow(arrayCampos,++indiceHistoriaLaboral,'datosHistoriaLaboralGrid');
		limpiarCamposAgregados(['registroNombrePatron','registroEntidadFederativa','registroFechaInscripcion','registroFechaBaja']);
		});

	$('#agregarNSS').click(function() {		
		var registroNSS = $("#registroNSS").val();
		var arrayCampos =[++indiceNSS,registroNSS];
		fnCrearRow(arrayCampos,indiceNSS,'listadoNSSInvolucradosGrid');
		limpiarCamposAgregados(["registroNSS"]);
		});

	$('#agregarDocumento').click(function(){
		var registroDocumentoProbatorio = $("#registroDocumentoProbatorio").val();
		var arrayCampos =[++indiceDocumentoProbatorio,registroDocumentoProbatorio];		
		if(ajaxFileUpload()){
			fnCrearRow(arrayCampos,indiceDocumentoProbatorio,'listadoDocumentosGrid');
		}

		limpiarCamposAgregados(["registroDocumentoProbatorio","fileData"]);
	});
	
	$('#continuarDatosHistoriaLaboral').click(function() {
		fnCrearCamposHidden('datosHistoriaLaboralGrid','datosHistoriaLaboralForm','historiaLaboralGrid',4);

		fnCrearCamposHiddenNotTH('listadoNSSInvolucradosGrid','datosHistoriaLaboralForm','NSSList','NSS',1);
		$('#datosHistoriaLaboralForm').submit();

	});
});

var fnEliminarRow = function(idRow){
	$('#'+idRow).remove();
	return false;
};

var fnCrearRow = function(arrayCampos,rowCount,tableName){
	var idTr=tableName+rowCount;
	var trHtml='<tr id=\''+idTr+'\'>';
	for(var i=0;i<arrayCampos.length;i++){
		trHtml+='<td>'+arrayCampos[i]+'</td>';	
	}
	trHtml+='<td> <a href="#" onclick="return fnEliminarRow(\''+idTr+'\');" >Eliminar</a> </td></tr>';
	
	$('#'+tableName+' tbody').append(trHtml);
};

var fnCrearCamposHidden = function(idTable,idFrom,parameterList,numeroCampos){
var arrayCampos = [];
$("#"+idTable+" tbody tr th").each(function (index) {
	if(arrayCampos.length<numeroCampos){
	 arrayCampos.push($(this).attr("name"));
	}
});

$("#"+idTable+" tbody tr").each(function (index) 
        {
            $(this).children("td").each(function (index2) 
            {
               if(index2 < numeroCampos ){
               	$('#'+idFrom).append
               	(
               			'<input type="hidden" name="'+parameterList+'['+(index-1)+
               			'].'+arrayCampos[index2]+'" value="'+$(this).text()+'"/>'
               	);
               } 
            });            
        });
};
var fnCrearCamposHiddenNotTH = function(idTable,idFrom,parameterList,nombreCampo,posicionInformacion){
$("#"+idTable+" tbody tr").each(function (index) 
        {
            $(this).children("td").each(function (index2) 
            {
               if(index2 === posicionInformacion ){
               	$('#'+idFrom).append               	
               	(
               			'<input type="hidden" name="'+parameterList+'['+(index-1)+
               			'].'+nombreCampo+'" value="'+$(this).text()+'"/>'
               	);
               } 
            });            
        });
};

var limpiarCamposAgregados = function(arrayCamposLimpiar){
	jQuery.each( arrayCamposLimpiar, function( i, val ) {
		$("#"+val).val("");  	
});
};


function ajaxFileUpload() {
		var ext = $("#fileData").val();
		var n = ext.split("\\");
		var nombreExtension = n[n.length - 1];
		n = nombreExtension.split(".");
		nombreExtension = n[n.length - 1];

		var urlDocumento = "${scheme}://${server}/gestionDerechohabientes-web/fileupload/uploadify";

		//'*.gif; *.jpg; *.png;*.pdf'
		if (nombreExtension == 'gif' || nombreExtension == 'jpg'
				|| nombreExtension == 'png' || nombreExtension == 'pdf') {
			nombreExtension = "";
			$("#loading").ajaxStart(function() {
				$(this).show();
			}).ajaxComplete(function() {
				$(this).hide();
			});

			$.ajaxFileUpload(

			{
				url : urlDocumento,
				secureuri : false,
				fileElementId : 'fileData',
				dataType : 'json',
				data : {
					name : 'logan',
					id : 'id'
				}				
			});
		} else {
			mensageConfirmacion('Tipo de archivo no valido');
			return false;
		}

		return true;

	}

	function mensageConfirmacion(mensaje) {
		
		$ventana = $('<div></div');

		$ventana.append(mensaje);
		$ventana.dialog({
			autoOpen : false,
			title : 'Mensaje',
			show : "blind",
			hide : "explode",
			modal : true,
			height : 150,
			width : 250,
			buttons : {
				"Aceptar" : function() {					
					cierraDialogo($(this));
				}
			}

		});

		$ventana.dialog('open');
	}

	function cierraDialogo($dialogo) {		
		$dialogo.dialog('close');
		$dialogo.dialog('destroy');
		$dialogo.html('');
	}
