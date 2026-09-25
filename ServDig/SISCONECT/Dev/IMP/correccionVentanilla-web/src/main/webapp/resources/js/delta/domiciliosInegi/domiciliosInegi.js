/**
 * JS para el soporte del catalogo de clase.
 */


var idDataDomInegiTable 	= "#dtDomInegi";
var idDgRegistroDomGeo	= "#dgDomGeoRegistro";
var idDgBorrarRegistroDomInegi	= "#dgDomInegiBorrar";

	
// Objeto del DataTable
var oDtDomInegi;
// Dialogos
var oDgRegistroDomGeo;
var oDgBorrarRegistroDomInegi;


/**
 * Iniciamos la configuracion de los componentes visuales de jQuery.
 */
$(document).ready(function() {
	

	/**
	 * Inicializacion del data table
	 */
	
	// Dialog de Elemento Nuevo			
	 oDgRegistroDomGeo = $(idDgRegistroDomGeo).dialog({
		autoOpen: true,
		modal:true,
		resizable:false,
		width: 930,
		closeOnEscape: false,
		beforeClose :function(event,ui){
		    limpiarFormulario("#dgDomGeoRegistro");
		    window.close();
		},
		buttons: {
		
			"Guardar": function() {
				
								
				if(!checaCero($("#domGeoFormRegistro #dgCatLocalidad\\.dgCatAmbito\\.ambito").val(),"\00c1mbito del Domicilio")) return false;
				if(!checaCero($("#domGeoFormRegistro #dgCatLocalidad\\.dgCatMunicipio\\.dgCatEstado\\.cveEnt").val(),"Estado")) return false;
				if(!checaCero($("#domGeoFormRegistro #dgCatLocalidad\\.dgCatMunicipio\\.id\\.cveMun").val(),"Municipio")) return false;
				if(!checaCero($("#domGeoFormRegistro #dgCatLocalidad\\.id\\.cveLoc").val(),"Localidad")) return false;
				if(!checaCero($("#domGeoFormRegistro #dgAsentamiento\\.dgCatTipoAsen\\.cveTipoAsen").val(),"Tipo de Asentamiento")) return false;
				if(!checaCero($("#domGeoFormRegistro #dgAsentamiento\\.id\\.cveAsen").val(),"Asentamiento")) return false;
				if(!checaCero($("#domGeoFormRegistro #dgCodigosPostales\\.id\\.codigo").val(),"C\u00f3digo Postal")) return false;
				if(!checaCero($("#domGeoFormRegistro #dgVialidadByCveViaPrin\\.dgCatVialidad\\.cveTipoVial").val(),"Tipo Vialidad")) return false;
				
				if(checaCero($("#domGeoFormRegistro #dgVialidadByCveViaPrin\\.cveVia").val(),"Vialidad Principal")){
					
					if($('#domGeoFormRegistro #dgVialidadByCveViaPrin\\.cveVia>option:selected').text()!="--Por favor seleccione--"){
						if($("#domGeoFormRegistro #nomvial").val()==""){
							$("#domGeoFormRegistro #nomvial").val($('#domGeoFormRegistro #dgVialidadByCveViaPrin\\.cveVia>option:selected').text());
							
						}
					}
					
				}else return false;
				
				
				if(!tieneDato($("#domGeoFormRegistro #nomvial").val(),"Vialidad Principal")) return false;
				 
				if(!checaCero($("#domGeoFormRegistro #dgCatTipoDom\\.cveTipoDom").val(),"Tipo de Domicilio")) return false;
				
				
				if(!tieneDato($("#domGeoFormRegistro #numextnum").val(),"N\00famero Exterior")) return false;
				
				if(!checaCero($("#domGeoFormRegistro #dgVialidadByCveViaRef1\\.dgCatVialidad\\.cveTipoVial").val(),"Tipo Vial Referencia 1")) return false;
				if(!checaCero($("#domGeoFormRegistro #dgVialidadByCveViaRef1\\.cveVia").val(),"Vialidad Referencia 1")) return false;
				
				if(!checaCero($("#domGeoFormRegistro #dgVialidadByCveViaRef2\\.dgCatVialidad\\.cveTipoVial").val(),"Tipo Vial Referencia 2")) return false;
				if(!checaCero($("#domGeoFormRegistro #dgVialidadByCveViaRef2\\.cveVia").val(),"Vialidad Referencia 2")) return false;
				
				//if(!checaCero($("#domGeoFormRegistro #dgVialidadByCveViaRef3\\.dgCatVialidad\\.cveTipoVial").val(),"Tipo Vial Referencia 3")) return false;
				//if(!checaCero($("#domGeoFormRegistro #dgVialidadByCveViaRef3\\.cveVia").val(),"Vialidad Referencia 3")) return false;
				
				
				 
				$('#domGeoFormRegistro #dgCatLocalidad\\.dgCatAmbito\\.nombre').val(
											$('#domGeoFormRegistro #dgCatLocalidad\\.dgCatAmbito\\.ambito>option:selected').text());
				
				
				$('#domGeoFormRegistro #dgCatLocalidad\\.dgCatMunicipio\\.dgCatEstado\\.nomEnt').val(
											$('#domGeoFormRegistro #dgCatLocalidad\\.dgCatMunicipio\\.dgCatEstado\\.cveEnt>option:selected').text());
				
				$('#domGeoFormRegistro #dgCatLocalidad\\.dgCatMunicipio\\.nomMun').val(
											$('#domGeoFormRegistro #dgCatLocalidad\\.dgCatMunicipio\\.id\\.cveMun>option:selected').text());
				
				$('#domGeoFormRegistro #dgCatLocalidad\\.nomLoc').val(
											$('#domGeoFormRegistro #dgCatLocalidad\\.id\\.cveLoc>option:selected').text());
				
				$('#domGeoFormRegistro #dgAsentamiento\\.dgCatTipoAsen\\.nombre').val(
											$('#domGeoFormRegistro #dgAsentamiento\\.dgCatTipoAsen\\.cveTipoAsen>option:selected').text());
				
				$('#domGeoFormRegistro #dgAsentamiento\\.nomAsen').val(
											$('#domGeoFormRegistro #dgAsentamiento\\.id\\.cveAsen>option:selected').text());
				$('#domGeoFormRegistro #dgVialidadByCveViaPrin\\.dgCatVialidad\\.descripcion').val(
											$('#domGeoFormRegistro #dgVialidadByCveViaPrin\\.dgCatVialidad\\.cveTipoVial>option:selected').text());
				
				$('#domGeoFormRegistro #dgVialidadByCveViaPrin\\.nomVia').val(
											$('#domGeoFormRegistro #dgVialidadByCveViaPrin\\.cveVia>option:selected').text());
				
				$('#domGeoFormRegistro #dgVialidadByCveViaRef1\\.dgCatVialidad\\.descripcion').val(
											$('#domGeoFormRegistro #dgVialidadByCveViaRef1\\.dgCatVialidad\\.cveTipoVial>option:selected').text());
				
				$('#domGeoFormRegistro #dgVialidadByCveViaRef1\\.nomVia').val(
											$('#domGeoFormRegistro #dgVialidadByCveViaRef1\\.cveVia>option:selected').text());
				
				$('#domGeoFormRegistro #dgVialidadByCveViaRef2\\.dgCatVialidad\\.descripcion').val(
											$('#domGeoFormRegistro #dgVialidadByCveViaRef2\\.dgCatVialidad\\.cveTipoVial>option:selected').text());
				
				$('#domGeoFormRegistro #dgVialidadByCveViaRef2\\.nomVia').val(
											$('#domGeoFormRegistro #dgVialidadByCveViaRef2\\.cveVia>option:selected').text());
			
				$('#domGeoFormRegistro #dgVialidadByCveViaRef3\\.dgCatVialidad\\.descripcion').val(
											$('#domGeoFormRegistro #dgVialidadByCveViaRef3\\.dgCatVialidad\\.cveTipoVial>option:selected').text());
				
				$('#domGeoFormRegistro #dgVialidadByCveViaRef3\\.nomVia').val(
											$('#domGeoFormRegistro #dgVialidadByCveViaRef3\\.cveVia>option:selected').text());
				
				$("#dgCatLocalidad\\.dgCatMunicipio\\.dgCatEstado\\.cveEnt").attr("disabled", false);
				
				var domGeograicoInegi = $("#domGeoFormRegistro").toObject({mode:'first'});				
				var formAction = $("#domGeoFormRegistro").attr('action');
				
				
				
				$.postJSON(formAction, domGeograicoInegi, function(data) {
					window.returnValue = true;
					
					if(data==null) alert("Error al procesar el domicilio, intente de nuevo.");
					window.close();
					$(this).dialog("close");
					
				}).error(function(data){ 
					alert("Error fatal al procesar el domicilio, intente de nuevo.");
					window.returnValue = false;
					window.close();
					$(this).dialog("close");
				}).complete(function(){
						
				});
				
				
			}, 
			"Cancelar": function() { 
				$(this).dialog("close");
				window.close();
			} 
		}
	});
	 
	// Dialog de Elemento a Borrar
	 /*
	 oDgBorrarRegistroDomInegi = $(idDgBorrarRegistroDomInegi).dialog({
		autoOpen: false,
		modal:true,
		resizable:false,
		height: 140,
		buttons: {
			"Aceptar": function() { 

				var clase = $("#domInegiFormBorrar").toObject({mode:'first'});
				
				$.postJSON("domiciliosGeograficos/eliminar.do", clase, function(data) {
					alert("Operaci\u00F3n Exitosa");		
					inicializaPosicionPaginador();							
				}).error(function(data){ 
					alert("error" + data);
				}).complete(function(){
					//Instrucciones para el complete			
				});
				$(this).dialog("close"); 
			}, 
			"Cancelar": function() { 
				$(this).dialog("close"); 
			} 
		}
	});	 
	*/
});


function registraDomicilioInegi(hastableKey){
	
	$("form#domGeoFormRegistro #domicilioInegi\\.hastableKeyDG").val(""+hastableKey+"");
	oDgRegistroDomGeo.dialog('open');
}
	
function reinicaSeleccionCmb(ids){
	
	var isJustLoaded = $("form#domGeoFormRegistro #loadValue").val();
	
	$("form#domGeoFormRegistro #loadValue").val((Number(isJustLoaded)+1));
	
	if(BrowserDetect.browser == "Explorer" && Number(BrowserDetect.version)<9){
		if($("form#domGeoFormRegistro #loadValue").val()>5)
			resetCombosDG(ids);
	}else resetCombosDG(ids);
	
}

 

function resetCombosDG(ids){
	if(ids!=""){
		
		var obj;
		
		for(var i=0;i<ids.length;i++){
			obj = document.getElementById(ids[i]);
			obj[0].selected = true;
		}
		
	}
}


