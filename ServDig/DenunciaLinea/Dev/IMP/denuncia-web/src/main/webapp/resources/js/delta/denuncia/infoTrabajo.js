var checkboxValues = [];


$(document).ready(function() {
	llenaComboPeriodoPago();
	llenaComboTipoComprobante();
//	muestraCheckBoxFormaPago();
	//Resalta campos requeridos
	$('#etiquetaRequired')
		.attr("style", "font-weight:bold")

		//oculta casilla 'otroPeriodo'
	$('.periodoOpcional').hide()
		
		//oculta casilla 'otroComprobante'
	$('.comprobanteOpcional').hide()
		//oculta casilla 'otraForma'
	$('.formaOpcional').hide()
	
	//subir archivo
	$("#uploadbutton").click(function() {
        var filename = $("#file").val();
		//var filename = document.getElementById('file').value;
        $.ajax({ 
        type: "POST",
        contentType: "multipart/form-data; boundary=AaB03x",
        
        url: "datosTrabajo/addFile.do",
           enctype: "multipart/form-data",
           data: {file: filename},
          success: function(){
               alert( "Data Uploaded: ");
            }
        });
		
		
    });
	
	
	
		
});


function otroPeriodo(){

	//var periodo = $('#cveFormaPago option:selected').html();
	var periodo = $("[id='dltFormapagoPP.dlcTiposformapago.cveFormapago'] option:selected").html();
		if(periodo == "OTROS") {
			$('.periodoOpcional').show()
		} else {
			$('.periodoOpcional').hide()
		}
}

function otroComprobante(){

	//var comprobante = $('#tipoComprobante option:selected').html();
	var comprobante = $("[id='dltFormapagoCP.dlcTiposformapago.cveFormapago'] option:selected").html();
		if(comprobante == "OTROS") {
			$('.comprobanteOpcional').show()
		} else {
			$('.comprobanteOpcional').hide()
		}
}

function otraForma(){
	
	$(".otraForma").click(function(event){
	     if($(this).is(":checked")) {
	    	 $('.formaOpcional').show()
		 }else{
			 $('.formaOpcional').hide()
		 }
	   });

	/*

	//var comprobante = $('#tipoComprobante option:selected').html();
	var comprobante = $("[id='dltFormapagoFP.dlcTiposformapago.cveFormapago'] option:selected").html();
		if(comprobante == "OTROS") {
			$('.comprobanteOpcional').show()
		} else {
			$('.comprobanteOpcional').hide()
		}*/
}

function muestraCheckBoxFormaPago(){
	
	$.postJSON("datosTrabajo/consultaFormaPago.do", "", function(data) {
		var boxes = "";
		
		if(data!=null) { 
			for(var i = 0; i < data.length; i++){
				boxes += "<tr><td ><form:input path=\"formaPago\" type=\"checkbox\"	name=\""+data[i].descFormapago+"\" value=\""+data[i].descFormapago+"\" />"+data[i].descFormapago+"</td>";
				if(i+1 != data.length){
					boxes +=  "</tr>"
				}
			}
			boxes += "<td>Especifique <form:input path=\"especifiqueFormaPago\" type=\"text\" id=\"otrosEspecifica\"  /></td></tr>"
		}
		//$('select#formaPago').prop("disabled", false);
		//$('select#formaPago').html(boxes);
/*		 jQuery('#tblTabla tr:last').after('<tr>' +
	                '<td align="left">' + x + '</td>' + 
	                '<td align="left">' + Cantidad + '</td>' +
	                '<td align="left">' + valor + '</td></tr>');
*/
		$('select#formaPago').prop("disabled", true);
		$('#formaPago tr:first').before(boxes);
	}).error(function(data){
		alert("error")
	}).complete(function(){
		
	});
}
	


function submitea(){
	checkBoxSeleccionados();
	// var param = jQuery.parseJSON(checkboxValues);
	//$.postJSON("/denuncia-web/denunciaLinea/datosTrabajo/formaPagosCheckBox.do", checkboxValues, function(data) {
	$.postJSON("/denunciaenlinea/denunciaLinea/datosTrabajo/guardarInfoTrabajo.do", checkboxValues, function(data) {
		
		// $("#infoTrabajoForm").attr("action",getAppContextParaJS() + "/denunciaLinea/datosTrabajo/guardarInfoTrabajo.do" ); 
		  //$("#infoTrabajoForm").submit();
	}).error(function(data){
		alert("error");
	}).complete(function(){
		
	});
	//$("#infoTrabajoForm").submit(); 
}

function checkBoxSeleccionados(){
	checkboxValues = $('[name="cveFormapago"]:checked').map(
			function(){ return $(this).val(); }
			).toArray();
}
