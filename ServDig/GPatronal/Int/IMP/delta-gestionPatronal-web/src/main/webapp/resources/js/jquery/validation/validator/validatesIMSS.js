/**
 * Script con el que se agrega al componente de jquery.valdiate los metodos para validar rfc fisica, rfc moral y la CURP
 * para ser llamado de la siguiente manera 
 * $("algo").validate({
 * 		rules : {
 * 			rfc: {
 * 				required: true,
 * 				rfcFisica: true
 * 			}
 * 		}
 * });
 */

(function() {

	var regexRFCMoral = /^[a-zA-Z\u00E1\u00E9\u00ED\u00F3\u00FA\u00C1\u00C9\u00CD\u00D3\u00DA\u00E4\u00EB\u00EF\u00F6\u00FC\u00C4\u00CB\u00CF\u00D6\u00DC\u0026\u00D1\u00F1\u005F]{3}\d{6}[a-zA-Z0-9]{3}$/;
	var regexRFCFisica = /^[a-zA-Z\u00E1\u00E9\u00ED\u00F3\u00FA\u00C1\u00C9\u00CD\u00D3\u00DA\u00E4\u00EB\u00EF\u00F6\u00FC\u00C4\u00CB\u00CF\u00D6\u00DC\u00D1\u00F1]{4}\d{6}[a-zA-Z\w]{3}$/;
	var regexCURP = /^[A-Za-z]{4}\d{6}((H|M)|(h|m))[A-Za-z]{2}[A-Za-z]{3}\w{1}\d{1}$/;
	
	//agregamos la regla de para rfc fisica
	jQuery.validator.addMethod("rfcFisica", function(value, element) {
		return this.optional(element) || regexRFCFisica.test(value);
	}, "RFC incorrecto");
	
	//agregamos la regla de para rfc de persona moral
	jQuery.validator.addMethod("rfcMoral", function(value, element) {
		return this.optional(element) || regexRFCMoral.test(value);
	}, "RFC incorrecto");
	
	//agregamos la regla de curp
	jQuery.validator.addMethod("curp", function(value, element) {
		return this.optional(element) || regexCURP.test(value);
	}, "CURP incorrecto");
	
	//agregamos para que lo que se capture sea una fecha valida
	jQuery.validator.addMethod("fecha", function(value, element) {
		//primero se valida el formato de la fecha 00/00/0000
		var fechaCorrecta = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/.test(value);
		//si la mascara es correcta se valida que sea una fecha valida que no se pase el numero de dias ni meses
		if(fechaCorrecta) {
			var fechaArr = value.split('/');
			var aho = fechaArr[2];
			var mes = fechaArr[1];
			var dia = fechaArr[0];
	
			var plantilla = new Date(aho, mes - 1, dia);//mes empieza de cero Enero = 0
	
			if(!plantilla || plantilla.getFullYear() == aho && plantilla.getMonth() == mes -1 && plantilla.getDate() == dia){
				return true;
			}else{
				return false;
			}
		} else {
			return false;
		}
	}, "Fecha incorrecta");
	
	//agregamos para que lo que se capture sea una fecha valida
	jQuery.validator.addMethod("fechaMayorQue", function(value, element) {
		//primero se valida el formato de la fecha 00/00/0000
		var fechaCorrecta = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/.test(value);
		//si la mascara es correcta se valida que sea una fecha valida
		if(fechaCorrecta) {
			var fechaArr = value.split('/');
			var aho = fechaArr[2];
			var mes = fechaArr[1];
			var dia = fechaArr[0];
	
			var plantilla = new Date(aho, mes - 1, dia);//mes empieza de cero Enero = 0
	
			if(!plantilla || plantilla.getFullYear() == aho && plantilla.getMonth() == mes -1 && plantilla.getDate() == dia){
				return true;
			}else{
				return false;
			}
		} else {
			return false;
		}
	}, "Fecha incorrecta");
	
	//agregamos la regla para que mientras el campo sea menor a 12 pociiones validar que el rfc sea de una moral
	//mientras que si el campo ya tiene mas de 12 validar que el rfc sea fisica
	jQuery.validator.addMethod("rfcFisOMoral", function(value, element) {
		if(value.length  <= 12) {
			if($("#curpRequired").length) {
				$("#curpRequired").hide();
			}
			return this.optional(element) || regexRFCMoral.test(value);
		} else {
			if($("#curpRequired").length) {
				$("#curpRequired").show();
			}
			return this.optional(element) || regexRFCFisica.test(value);
		}
	}, "RFC incorrecto");
	
})();