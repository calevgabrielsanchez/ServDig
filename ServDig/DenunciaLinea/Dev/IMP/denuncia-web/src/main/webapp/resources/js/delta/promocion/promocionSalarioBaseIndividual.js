var Deteccion = function(domCalle, numCodigopostal, numNroext, numNroint, refColonia){
	this.domCalle = '';
	this.numCodigopostal = '';
	this.numNroext = '';
	this.numNroint = '';
	this.refColonia = '';
}
var deteccion = new Deteccion('','','','','');

$(document).ready(function() {
	
	$("#fecOficio").datepicker({ 
		dateFormat: 'dd/mm/yy',
		onSelect: function(dateText, inst) { 
	       // $('#fecOficio').datepicker('option', 'minDate', dateText); 
	    }
	});
	
	$.postJSON("promocionSBCIndividual/obtenerFechaServidor.do", null,function(data) {
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
//		alert(JSON.stringify(data, null, 4));
		$('#fecOficio').datepicker('option', 'maxDate', data.responseText);
		
	});
	
	$.postJSON("promocionSBCIndividual/obtenerFechaServidorMinima.do", null,function(data) {
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(data){
//		alert(JSON.stringify(data, null, 4));
		$('#fecOficio').datepicker('option', 'minDate', data.responseText);
	});
	
	
	llenaCriterio();
	limpiarDomicilio();
	
	 $("#btnValidar").click(function(event){
		 event.preventDefault();
		 $("#labelRegistroPatronal").html('');
		 $("#labelRazonSocial").html('');
		
		
		 var patron = $("#registroPatronal").val();
		 if(patron != '' && patron.length == 10){							 
			 bloquear();
			$.postJSON("promocionSBCIndividual/validaPatron.do",patron,function(data) { 
				if(data == null){
					$("#labelRegistroPatronal").html('<label class="etiquetaError">El registro patronal no es valido</label>');
					$("#razonSocial").val('');	
					desbloquear();	
				}
				else if(data != null){
					if(data.bandera == 'true'){
						$("#cveFkPatron").val(data.cvePK);
						$("#razonSocial").val(data.razonSocial);
					}else{
						$("#labelRegistroPatronal").html('<label style="color: red;">La subdelegacion del patron no coincide con la subdelegacion del usuario en session</label>');
						$("#razonSocial").val('');
					}
					
				}
			}).error(function(data){ 
				desbloquear();	
				validarSesionExpirada(data);
			}).complete(function(){
				desbloquear();	
			});
	 }else{
		 $("#labelRegistroPatronal").html('<label class="etiquetaError">Capturar un registro patronal valido</label>');
		 $("#razonSocial").val('');	
	 }
	 });
	 
	 $("#btnPromover").click(function(event){
		 event.preventDefault();
		 
		 if(jsValida() == true){
			 jsValidarMunicipio();
		 }
	 });
				
	
});


function llenaCriterio(){
	
	var variable = '{"idOrigen":"1"}';		
	var variableJson = jQuery.parseJSON(variable);
	$.postJSON("promocionSBCIndividual/cboCriteriosSubdelegacion.do", variableJson, function(data) {
		
		var myselect=document.getElementById("selectCriterios");
		myselect.options.length = 1;
		
		for(var i = 0 ; i < data.length ; i++){
			myselect.add(new Option(data[i][3], data[i][0]));
		}
		
	});
}

function jsvalidarAlfaNumerico(e) { 
	
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[1234567890abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ ]/;
    te = String.fromCharCode(tecla);
    
    return patron.test(te);
} 

function jsCortaTextArea(valor){
	if(valor.length > 200){
		valor = valor.substring(0,199);
	}
	
}

function mostarDomGeo(context,fuente){
	var resultado = openWindowregistraDomicilioInegi(context,'catalogo/deteccion/deteccionDomGeografico.do');
	var url = context + '/catalogo/deteccion/obtenerDomicilioSession.do'
	if(fuente == 'registro'){
		refrescar('promocionCargaModel', url);
	}if(fuente == 'buscar'){
		refrescar('promocionCargaModel', url);
	}if(fuente == 'validar'){
		refrescar('promocionCargaModel', url);
	}
}

function refrescar(form, url){
	
	$("#labeldomCalle").html('');
	$("#labelColonia").html('');
	$("#labelCP").html('');
	$("#labelNumExt").html('');
	$.postJSON(url,deteccion,function(data) {
		$("#domCalle").val(data.domCalle);
		$("#refColonia").val(data.refColonia);
		$("#numNroint").val(data.numNroint);
		$("#numNroext").val(data.numNroext);
		$("#numCodigopostal").val(data.numCodigopostal);
		$("#municipio").val(data.estado);
	}).error(function(data){
		validarSesionExpirada(data);
	}).complete(function(){
		
	});
}

function limpiar(){
	
	$("#domCalle").val("");
	$("#refColonia").val("");
	$("#numNroint").val("");
	$("#numNroext").val("");
	$("#numCodigopostal").val("");
	$("#selectCriterios").val("");
	$("#fecOficio").val("");
	$("#nuOficiopro").val("");
	$("#txObservaciones").val("");
	$("#registroPatronal").val("");
	$("#razonSocial").val("");
	$("#cveFkPatron").val("");
	
	$("#labelcriterios").html('');
	$("#labelFecOficio").html('');
	$("#labelNumOficio").html('');
	$("#labelRegistroPatronal").html('');
	$("#labeldomCalle").html('');
	$("#labelColonia").html('');
	$("#labelCP").html('');
	$("#labelNumExt").html('');
	
	limpiarDomicilio();
	
	
}

function jsvalidarNumeros(e) { 
	
    tecla = (document.all) ? e.keyCode : e.which;
    if (tecla==8) return true;
    patron = /[0123456789.]/;
    te = String.fromCharCode(tecla);
    
    return patron.test(te);
} 

function jsValida(){
	
	var resp = true;
	$("#labelcriterios").html('');
	$("#labelFecOficio").html('');
	$("#labelNumOficio").html('');
	$("#labeldomCalle").html('');
	$("#labelColonia").html('');
	$("#labelCP").html('');
	$("#labelNumExt").html('');
	$("#labelRazonSocial").html('');
	$("#bandera").val('')
	
	if($("#selectCriterios").val() == ''){
		$("#labelcriterios").html('<label style="color: red;">Campo Obligatorio</label>');
		resp = false;
	}
	if($("#fecOficio").val() == ''){
		$("#labelFecOficio").html('<label style="color: red;">Campo Obligatorio</label>');
		resp = false;
	}
	if($("#nuOficiopro").val() == ''){
		$("#labelNumOficio").html('<label style="color: red;">Campo Obligatorio</label>');
		resp = false;
	}
	
	if($("#registroPatronal").val() == ''){
		$("#labelRegistroPatronal").html('<label style="color: red;">Campo Obligatorio</label>');
		resp = false;
	}
	
	if($("#razonSocial").val() == ''){
		$("#labelRazonSocial").html('<label style="color: red;">Validar el registro patronal</label>');
		resp = false;
	}
	
	
	// Validar direccion
	
	if($("#domCalle").val() == ''){
		$("#labeldomCalle").html('<label style="color: red;">Campo Obligatorio</label>');
		resp = false;
	}
	if($("#refColonia").val() == ''){
		$("#labelColonia").html('<label style="color: red;">Campo Obligatorio</label>');
		resp = false;
	}
	if($("#numNroext").val() == ''){
		$("#labelNumExt").html('<label style="color: red;">Campo Obligatorio</label>');
		resp = false;
	}
	if($("#numCodigopostal").val() == ''){
		$("#labelCP").html('<label style="color: red;">Campo Obligatorio</label>');
		resp = false;
	}
	

	return resp;
	
}

function limpiarDomicilio(){
	
		var variable = '{' +
	  	'"idOrigen":"0"}';					
		var variableJson = jQuery.parseJSON(variable);
		$.postJSON("promocionSBCIndividual/quitarDomicilioGeo.do", variableJson,function(data) {
			
		}).error(function(data){
			validarSesionExpirada(data);
		}).complete(function(){
			
		});
			
}

function jsValidarMunicipio(){
	
	$("#labelCP").html('');
	var municipio =  $("#municipio").val();
	var variable = '{' +
	  	'"municipio":"'+municipio+'"}';					
	var variableJson = jQuery.parseJSON(variable);
	$.postJSON("promocionSBCIndividual/validaMunicipio.do", variableJson, function(data) {
		
		if(data == false){
			
			$("#labelCP").html('<label class="etiquetaError">El estado seleccionado no coincide con el estado del usuario registrado</label>');
		}else{
			$("#labelCP").html('');
			
			jsAltaPromocionSBC();
		}
		}).error(function(data){ 
			alert("error" + data);
		}).complete(function(data){
			desbloquear();	
		});		
	
}

function jsAltaPromocionSBC(){
	
	var crtPromocion = $("#promocionSBCindividualForm").toObject({mode:'first'});
	bloquear();
		$.postJSON("promocionSBCIndividual/altaPromocionSBC.do", crtPromocion, function(data) {
			alert("El registro patronal se a promovido con el numero de folio : " + data.nuFoliopromocion);
			limpiar();
			desbloquear();	
		});
}
