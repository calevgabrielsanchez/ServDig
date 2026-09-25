/**
 * 
 */

var recupera;
$(document).ready(function() {
	
	generaCatalogos();
	
	
	
	
});


function capturaDomicilioOperacion(){
	var context = getAppContextParaJS();
	var func="recuperaDatosDomicilioDOS()";
	openWindowregistraDomicilioInegi(context,'',null,func);		
}

function recuperaDatosDomicilioDOS(){
	$("#calleDerivarSubdel").val(domicilioGuardado.dgVialidadByCveViaPrin.nomVia);
	$("#numExtDerivarSubdel").val(domicilioGuardado.numextnum);
	$("#municipioDerivarSubdel").val(domicilioGuardado.dgCatLocalidad.dgCatMunicipio.nomMun);
	$("#codigoPostalDerivarSubdel").val(domicilioGuardado.domicilioBDTU.codigoPostal.codigoPostal);
	$("#coloniaDerivarSubdel").val(domicilioGuardado.domicilioBDTU.asentamiento.nombre);
	$("#numIntDerivarSubdel").val(domicilioGuardado.domicilioBDTU.numInterior);
	$("#estadoDerivarSubdel").val(domicilioGuardado.dgAsentamiento.dgCatLocalidad.dgCatMunicipio.dgCatEstado.nomEnt);
}



function generaCatalogos(){
	var context = getAppContextParaJS();
	  $.postJSON_Sync(context+"/seguimiento/correccion/getDelegaciones.do", null, function(objData) {			  
			 var options = '<option value="-1" >--Por favor seleccione--</option>';
				for (var i = 0; i < objData.length; i++) {
			        options += '<option value="' + objData[i].cvePk + '">' + objData[i].nomNombre + '</option>';
			      }
				 $("#comboDelegacionDDS").html(options);
		  
		});	
	
	  $.postJSON_Sync(context+"/seguimiento/correccion/motivosCancelaccion.do", null, function(objData) {			  
			 var options = '<option value="-1" >--Por favor seleccione--</option>';
				for (var i = 0; i < objData.length; i++) {
			        options += '<option value="' + objData[i].idMotivocancelacion + '">' + objData[i].motivocancelacion + '</option>';
			      }
				 $("#cboMotivos").html(options);
		  
		});	
	  
	  
	  
}



function recuperaSubdelegacionDDS(valor){
	var context = getAppContextParaJS();
	var sVarSeg = '{"cvePk":"'+valor+'"}';
	var clase = jQuery.parseJSON(sVarSeg);
	  $.postJSON_Sync(context+"/seguimiento/correccion/getSubDelegaciones.do", clase, function(objData) {			  
			 var options = '<option value="-1" >--Por favor seleccione--</option>';
				for (var i = 0; i < objData.length; i++) {
			        options += '<option value="' + objData[i].cvePk + '">' + objData[i].nomNombre + '</option>';
			      }
				 $("#comboSubDelegacionDDS").html(options);		  
		});	
}

function validarSubdelegacion(){
	

	
	var context = getAppContextParaJS();
	var valor=$("#comboSubDelegacionDDS").val();
	var sVarSeg = '{"cvePk":"'+valor+'"}';
	var clase = jQuery.parseJSON(sVarSeg);
	  $.postJSON_Sync(context+"/seguimiento/correccion/validaSubdelegacion.do", clase, function(objData) {		   
		
		  if(objData==false){
			  alert("No se puede derivar a la misma subdelegacion");
			  $("#comboSubDelegacionDDS").val(-1);
		  }
		});	
}


