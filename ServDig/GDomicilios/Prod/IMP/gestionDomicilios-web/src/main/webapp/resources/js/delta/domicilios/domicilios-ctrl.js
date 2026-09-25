/*
 * Seccion de codigo a ejecutar en cuanto el DOM envie la se�ar de que esta
 * listo para procesar de modificaciones al DOM
 */
$(document).ready(function() {
	/**
	 * Funcionalidad para cerrar el dialogo y regresar el objeto 
	 * de domicilio
	 */
	$("#botonControl").click(function(event){
			fnCloseDialogoDomicilio();
        	return false;
		
	});
	
	
	$("#botonControlRegresar").click(function(){
		$("form#formDomicilio").attr("action" , context_path+"/domicilio/nacional/ubicar/regresar/datos/complementarios");
		$("form#formDomicilio").submit();
	});
	
	
	$("#calle").blur(function(){
		$("#calle").val(validaCadenaOnBlur($("#calle").val()));
	});
	
	$("#domicilioCarretera\\.origen").blur(function(){
		$("#domicilioCarretera\\.origen").val(validaCadenaOnBlur($("#domicilioCarretera\\.origen").val()));
	});
	
	$("#domicilioCarretera\\.destino").blur(function(){
		$("#domicilioCarretera\\.destino").val(validaCadenaOnBlur($("#domicilioCarretera\\.destino").val()));
	});
	
	$("#domicilioCarretera\\.codigoCarretera").blur(function(){
		$("#domicilioCarretera\\.codigoCarretera").val(validaCadenaOnBlur($("#domicilioCarretera\\.codigoCarretera").val()));
	});
	
	$("#domicilioCarretera\\.cadenamiento").blur(function(){
		$("#domicilioCarretera\\.cadenamiento").val(validaCadenaOnBlur($("#domicilioCarretera\\.cadenamiento").val()));
	});
	
	$("#domicilioCamino\\.origen").blur(function(){
		$("#domicilioCamino\\.origen").val(validaCadenaOnBlur($("#domicilioCamino\\.origen").val()));
	});
	
	$("#domicilioCamino\\.destino").blur(function(){
		$("#domicilioCamino\\.destino").val(validaCadenaOnBlur($("#domicilioCamino\\.destino").val()));
	});
	
	$("#domicilioCamino\\.cadenamiento").blur(function(){
		$("#domicilioCamino\\.cadenamiento").val(validaCadenaOnBlur($("#domicilioCamino\\.cadenamiento").val()));
	});
	
	$("#numExterior1").blur(function(){
		$("#numExterior1").val(validaCadenaOnBlur($("#numExterior1").val()));
	});
	
	$("#numExteriorAlf").blur(function(){
		$("#numExteriorAlf").val(validaCadenaOnBlur($("#numExteriorAlf").val()));
	});
	
	$("#numInteriorAlf").blur(function(){
		$("#numInteriorAlf").val(validaCadenaOnBlur($("#numInteriorAlf").val()));
	});
	
	$("#numInterior").blur(function(){
		$("#numInterior").val(validaCadenaOnBlur($("#numInterior").val()));
	});
	
	$("#numExterior2").blur(function(){
		$("#numExterior2").val(validaCadenaOnBlur($("#numExterior2").val()));
	});
	
	$("#calle").on('input',function(){
		$("#calle").val(validaCadenaOnChange($("#calle").val()));
	});
	
	$("#domicilioCarretera\\.origen").on('input',function(){
		$("#domicilioCarretera\\.origen").val(validaCadenaOnChange($("#domicilioCarretera\\.origen").val()));
	});
	
	$("#domicilioCarretera\\.destino").on('input',function(){
		$("#domicilioCarretera\\.destino").val(validaCadenaOnChange($("#domicilioCarretera\\.destino").val()));
	});
	
	$("#domicilioCarretera\\.codigoCarretera").on('input',function(){
		$("#domicilioCarretera\\.codigoCarretera").val(validaCadenaOnChange($("#domicilioCarretera\\.codigoCarretera").val()));
	});
	
	$("#domicilioCarretera\\.cadenamiento").on('input',function(){
		$("#domicilioCarretera\\.cadenamiento").val(validaCadenaOnChange($("#domicilioCarretera\\.cadenamiento").val()));
	});
	
	$("#domicilioCamino\\.origen").on('input',function(){
		$("#domicilioCamino\\.origen").val(validaCadenaOnChange($("#domicilioCamino\\.origen").val()));
	});
	
	$("#domicilioCamino\\.destino").on('input',function(){
		$("#domicilioCamino\\.destino").val(validaCadenaOnChange($("#domicilioCamino\\.destino").val()));
	});
	
	$("#domicilioCamino\\.cadenamiento").on('input',function(){
		$("#domicilioCamino\\.cadenamiento").val(validaCadenaOnChange($("#domicilioCamino\\.cadenamiento").val()));
	});
	
	$("#numExterior1").on('input',function(){
		$("#numExterior1").val(validaCadenaOnChange($("#numExterior1").val()));
	});
	
	$("#numExteriorAlf").on('input',function(){
		$("#numExteriorAlf").val(validaCadenaOnChange($("#numExteriorAlf").val()));
	});
	
	$("#numInteriorAlf").on('input',function(){
		$("#numInteriorAlf").val(validaCadenaOnChange($("#numInteriorAlf").val()));
	});
	
	$("#numInterior").on('input',function(){
		$("#numInterior").val(validaCadenaOnChange($("#numInterior").val()));
	});
	
	$("#numExterior2").on('input',function(){
		$("#numExterior2").val(validaCadenaOnChange($("#numExterior2").val()));
	});
});

function validaCadenaOnBlur(valor){
	return valor.trim();
}

function validaCadenaOnChange(valor){	
	var out = '';
	var exp_reg = /[A-Za-z0-9��\s]/;
	for(var i=0; i<valor.length; i++){
		if((exp_reg).exec(valor.charAt(i)))
			out += valor.charAt(i);
	}
	return out;
}

var fnCloseDialogoDomicilio = function(){
	var oForm = $("form#formDomicilio").toObject();
	var ctrl = parent.DomicilioCtrl;
	
	if(ctrl != null){
		oForm = validarVialidades(oForm);
		ctrl.domicilio = oForm;
	}
	ctrl.cerrar();
};

function validarVialidades(domicilio) {
	
	if(domicilio.calle) {
		domicilio.calle = replaceCaracteres(domicilio.calle);
	}
	
	if(domicilio.domicilioCarretera && domicilio.domicilioCarretera.origen) {
		domicilio.domicilioCarretera.origen = replaceCaracteres(domicilio.domicilioCarretera.origen);
		domicilio.domicilioCarretera.destino = replaceCaracteres(domicilio.domicilioCarretera.destino);
	}
	
	if(domicilio.domicilioCamino && domicilio.domicilioCamino.origen) {
		domicilio.domicilioCamino.origen = replaceCaracteres(domicilio.domicilioCamino.origen);
		domicilio.domicilioCamino.destino = replaceCaracteres(domicilio.domicilioCamino.destino);
	}
	
	return domicilio;
}

function replaceCaracteres(cadena) {
	console.log('La cadena antes de caracteres ' + cadena);
	cadena = cadena.replace(/[\u00E0\u00E1]/g,'a');
	cadena = cadena.replace(/[\u00C0\u00C1]/g,'A');
	cadena = cadena.replace(/[\u00E8\u00E9]/g,'e');
	cadena = cadena.replace(/[\u00C8\u00C9​]/g,'E');
	cadena = cadena.replace(/[\u00A0\u1680​]/g,'i');
	cadena = cadena.replace(/[\u00CC\u00CD]/g,'I');
	cadena = cadena.replace(/[\u00F2\u00F3​]/g,'o');
	cadena = cadena.replace(/[\u00D2\u00D3​]/g,'O');
	cadena = cadena.replace(/[\u00F9\u00FA​]/g,'u');
	cadena = cadena.replace(/[\u00D9\u00DA]/g,'U');
	console.log('La cadena despues de caracteres ' + cadena);
	
	return cadena;
}