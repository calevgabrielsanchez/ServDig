$(document).ready(function() {	
	var indiceAdicionalHistoriaLaboral = ($('#datosAdicionalesHistoriaLaboralGrid tr').length-1);

	$('#agregar').click(function() {		
		fnHideErrores("form#informacionHistoriaLaboralForm");
		fnHideErroresInput("form#informacionHistoriaLaboralForm");
		var registroNumeroRegistroPatronal = $("#registroNumeroRegistroPatronal").val();
		var registroActividadEmpresa = $("#registroActividadEmpresa").val();
		var registroDomicilioEmpresa = $("#registroDomicilioEmpresa").val();
		$.blockUI();
										$
												.ajax({
													url : contextPath+'/wizard/correccionDatosAsegurado/validar/datosAdicionales',
													type : 'post',
													async : false,
													dataType : 'json',
													contentType : "application/json; charset=utf-8",
													data : JSON
															.stringify({
																numeroRegistroPatronal : registroNumeroRegistroPatronal,
																actividadEmpresa : registroActividadEmpresa,
																domicilioEmpresa : registroDomicilioEmpresa
															}),
													success : function(response) {
															var arrayCampos =[registroNumeroRegistroPatronal,registroActividadEmpresa,registroDomicilioEmpresa];
															fnCrearRow(arrayCampos,++indiceAdicionalHistoriaLaboral,'datosAdicionalesHistoriaLaboralGrid');
															limpiarCamposAgregados(["registroNumeroRegistroPatronal","registroActividadEmpresa","registroDomicilioEmpresa"]);
														
														$.unblockUI();
													},
													error : function(error) {														
														fnProcesarErrores(
																error,
																"form#informacionHistoriaLaboralForm");
														$.unblockUI();
													}
												});
										$.unblockUI();
	});

	$('#continuarInformacionHistoriaLaboral').click(function() {
		fnCrearCamposHidden('datosAdicionalesHistoriaLaboralGrid','informacionHistoriaLaboralForm','datosAdicionalesList',3);
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
$('#'+idFrom).submit();
};

var limpiarCamposAgregados = function(arrayCamposLimpiar){
	jQuery.each( arrayCamposLimpiar, function( i, val ) {
		$("#"+val).val("");  	
});
};