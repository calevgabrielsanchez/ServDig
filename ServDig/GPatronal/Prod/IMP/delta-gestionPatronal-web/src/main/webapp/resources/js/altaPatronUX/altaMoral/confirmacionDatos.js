/**
 * 
 */

$(document).ready(function() {
	$("#rechazoConfirmacion").on("click", datosIncorrectos);
	$("#aceptarConfirmacion").on("click", datosCorrectos)
});


var setDatosPatron = function() {
	
	$("#rfcConfirmacion").html(personaMoralAP.rfc);
	$("#razonSocialConfirmacion").html(personaMoralAP.razonSocial);
	$("#estatusConfirmacion").html(personaMoralAP.situacionesSAT[0].descripcion);

	$("#fechaInicioConfirmacion").html(personaMoralAP.datosPersonaSAT.fechaInicioOperaciones);
	
	var domicilioFiscal = personaMoralAP.domicilioFiscal;
	$("#codigoPostalConfirmacion").html(domicilioFiscal.codigoPostal.codigoPostal);
	$("#entidadConfirmacion").html(domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre);
	$("#municipioConfirmacion").html(domicilioFiscal.asentamiento.localidad.municipio.nombre);
	$("#localidadConfirmacion").html(domicilioFiscal.asentamiento.localidad.nombre);
	
	$("#coloniaConfirmacion").html(domicilioFiscal.colonia);
	$("#calleConfirmacion").html(domicilioFiscal.calle);
	$("#numeroConfirmacion").html(domicilioFiscal.numExteriorAlf);
	$("#numeroIntConfirmacion").html(domicilioFiscal.numInteriorAlf);
	
	if(personaMoralAP.telefonoFijoFiscal != null) {
		$("#telefonoFiscalConfirmacion").html($.trim(personaMoralAP.telefonoFijoFiscal.numero));
		$("#extensionFiscalConfirmacion").html($.trim(personaMoralAP.telefonoFijoFiscal.extension));
	} else {
		$("#telefonoFiscalConfirmacion").html("&nbsp;");
		$("#extensionFiscalConfirmacion").html("&nbsp;");
	}
	
	if(personaMoralAP.telefonoMovilFiscal != null) {
		$("#movilFiscalConfirmacion").html($.trim(personaMoralAP.telefonoMovilFiscal.numero));
	} else {
		$("#movilFiscalConfirmacion").html("&nbsp;");
	}
	
	if(personaMoralAP.correoElectronicoFiscal != null) {
		$("#correoFiscalConfirmacion").html($.trim(personaMoralAP.correoElectronicoFiscal.correo));
	} else {
		$("#correoFiscalConfirmacion").html("&nbsp;");
	}

};


var convertirFechaFormato = function(fechaGuiones) {
	var fecha = fechaGuiones.split("-");
	var fechaSinGuiones=fecha[2]+"/"+fecha[1]+"/"+fecha[0];
	
	return fechaSinGuiones;
}
var datosCorrectos = function() {
	paginaSiguiente();
};

var datosIncorrectos = function(){
	var $divMensajes = $("<div></div>");
	$divMensajes.dialog({
		resizable: false,
		modal:true,
		heigth: 'auto',
		width: '400px',
		title: "Aviso",
		autoOpen: false,
	    closeOnEscape: false,
	    buttons: {
	    	"Aceptar": function() {
	    		$(this).dialog("close");
	    		window.close();
	    	}
	    },
	    create:function () {
	        $(this).closest(".ui-dialog")
	            .find("button:first") // the first button
	            .addClass("btn btn-sm btn-primary");
	    }
	})
	
	$divMensajes.html("<div class=\"alert alert-danger\">Si tus datos no son correctos, acude al SAT para hacer la aclaraci&oacute;n correspondiente y poder continuar con este tr&aacute;mite.</div>");
	$divMensajes.dialog('open');
}