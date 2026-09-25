var Deteccion = function(domCalle, numCodigopostal, numNroext, numNroint, refColonia){
	this.domCalle = '';
	this.numCodigopostal = '';
	this.numNroext = '';
	this.numNroint = '';
	this.refColonia = '';
}
var deteccion = new Deteccion('','','','','');

$(document).ready(function() {
	$('#fecOficio').datepicker(menos45dias());
	$("#fecOficio").datepicker('option', 'onSelect', function(dateText, inst) { 
			$('form#promocionSBCindividualForm #labelFecOficio').html('');
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
					$("#labelRegistroPatronal").html('<label class="etiquetaError">El registro patronal no es valido o esta dado de baja</label>');
					$("#razonSocial").val('');	
					$("#registroPatronal").val('');	
					
					desbloquear();	
				}
				else if(data != null){
					
					if(data.error!=null && data.error != "" ){
						alert(data.error);
						$("#razonSocial").val('');	
						$("#registroPatronal").val('');	
						return;
					}
						
					$("#cveFkPatron").val(data.cvePK);
					$("#razonSocial").val(data.razonSocial);					
				}
//				else if(data != null){
//					if(data.bandera == 'true'){
//						$("#cveFkPatron").val(data.cvePK);
//						$("#razonSocial").val(data.razonSocial);
//					}else{
//						$("#labelRegistroPatronal").html('<label style="color: red;">La subdelegacion del patron no coincide con la subdelegacion del usuario en session</label>');
//						$("#razonSocial").val('');
//					}
//					
//				}
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
			
				
				
			 var variable = '{"nuOficiopro":"'+$("#nuOficiopro").val()+'", "regPatron": "'+$("#registroPatronal").val()+'"}';		
				var variableJson = jQuery.parseJSON(variable);
			 $.postJSON("promocionSBCIndividual/verificaExitencia.do", variableJson, function(data) {
				 if(data==null){
					 jsAltaPromocionSBC();
				 }else{
					 //alert('El n\u00famero de oficio: '+$("#nuOficiopro").val()+', ya se encuentra registrado para el patr\u00f3n: ' + $("#registroPatronal").val());
					 alert('El n\u00famero de oficio: '+$("#nuOficiopro").val()+', ya se encuentra registrado ');
				 }
				 
			 });
			 //jsValidarMunicipio();   Se comenta esta validación ya que no estan coincideiendo en BD   EDJ  23/08/2012
			
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
	
	var url = context + '/catalogo/deteccion/obtenerDomicilioSession.do';
	var func;
	if(fuente == 'registro'){
		func="refrescar('promocionCargaModel', '"+url+"')";
	}if(fuente == 'buscar'){
		func="refrescar('promocionCargaModel', '"+url+"')";
	}if(fuente == 'validar'){
		func="refrescar('promocionCargaModel', '"+url+"')";
	}
	var resultado = openWindowregistraDomicilioInegi(context,'catalogo/deteccion/deteccionDomGeografico.do',null,func);
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
			alert("El registro patronal se ha promovido con el numero de folio: " + data.nuFoliopromocion);
			limpiar();
			desbloquear();	
		});
}
