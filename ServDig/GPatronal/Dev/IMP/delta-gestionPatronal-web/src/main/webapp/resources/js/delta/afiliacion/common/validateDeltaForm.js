 $.extend($.validator.messages, {
        required: "<br>Este campo es requerido",
        email: "<br>El formato del campo email es incorrecto",
        digits: "<br>Solo se permiten digitos",
        number:"<br>Solo se permiten n&uacute;meros"
    });
	
	
function validarFormEscritura(){		
	
	jQuery.validator.addMethod( 
	  "validaSeleccion", 
	  function(value, element) { 	  
	    if (element.value == "-1") 		
			return false; 			
		else return true; 
		}, 
		"Por favor seleccione una opci&oacute;n" 
	);
	
	jQuery.validator.addMethod("validaFechaEC", function(value, element) {		
		var s1=$("#txtFechaRegistroEdicionEC").val();
		var s2=$("#labelfechaEC").html();
		if (s2==undefined || s2==null || s2=="")
			return true;						
		var d1 = new Date(s1.substr(3,2)+"/"+s1.substr(0,2)+"/"+s1.substr(6,4));
		var d2 = new Date(s2.substr(3,2)+"/"+s2.substr(0,2)+"/"+s1.substr(6,4));			
		if (d1<d2){		
			return false; 
		}
		else
			return true;
	}, 
	"La fecha de expedici&oacute;n del tr&aacute;mite debe ser mayor a la fecha de expedici&oacute;n vigente");
	
	
	
	/**
	  * Return true, if the value is a valid date, also making this formal check mm/dd/yyyy.
	  *
	  * @example jQuery.validator.methods.date("01/01/1900")
	  * @result true
	  *
	  * @example jQuery.validator.methods.date("13/01/1990")
	  * @result false
	  *
	  * @example jQuery.validator.methods.date("01.01.1900")
	  * @result false
	  *
	  * @example <input name="pippo" class="dateUS" />
	  * @desc Declares an optional input element whose value must be a valid date.
	  *
	  * @name jQuery.validator.methods.dateUS
	  * @type Boolean
	  * @cat Plugins/Validate/Methods
	  */

	jQuery.validator.addMethod(
		"dateMX",
		function(value, element) {
			var check = false;
			var re = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
			if( re.test(value)){
				var adata = value.split('/');
				var dd = parseInt(adata[0],10);
				var mm = parseInt(adata[1],10);
				var yyyy = parseInt(adata[2],10);
				var xdata = new Date(yyyy,mm-1,dd);
				if ( ( xdata.getFullYear() == yyyy ) && ( xdata.getMonth () == mm - 1 ) && ( xdata.getDate() == dd ) )
					check = true;
				else
					check = false;
			} else
				check = false;
			return this.optional(element) || check;
		},
		"Por favor ingresa una fecha v\u00E1lida con el siguiente formato dd/mm/yyyy"
	);

	
	
	
	$("#escrituraConstitutivaForm").validate({
		rules: {
			"moral.escrituraConstitutiva.numEscritura": {
				required:true,
				number:true,
				maxlength:12
			},
			"moral.escrituraConstitutiva.numNotaria": {
				required:true
			},
			"moral.escrituraConstitutiva.lugarExpedicion.entidadFederativa.clave": {
				validaSeleccion: true 
			},
			"moral.escrituraConstitutiva.lugarExpedicion.clave": {
				validaSeleccion: true 
			},
			"moral.escrituraConstitutiva.folioMercantil": {
				required:function(){														
					if ($("#moral\\.escrituraConstitutiva\\.seccion").val() == "" 
						&& $("#moral\\.escrituraConstitutiva\\.partida").val()=="" && $("#moral\\.escrituraConstitutiva\\.volumen").val()=="" 
						&& $("#moral\\.escrituraConstitutiva\\.foja").val()=="")
						return true;
					else
						return false;
				},
				number:true
			},
			"moral.escrituraConstitutiva.seccion": {
				required:function(){														
					if ($("#moral\\.escrituraConstitutiva\\.folioMercantil").val()=="")
						return true;
					else
						return false;
				}
			},
			"moral.escrituraConstitutiva.partida": {
				required:function(){														
					if ($("#moral\\.escrituraConstitutiva\\.folioMercantil").val()=="")
						return true;
					else
						return false;
				}
			},
			"moral.escrituraConstitutiva.volumen": {
				required:function(){														
					if ($("#moral\\.escrituraConstitutiva\\.folioMercantil").val()=="")
						return true;
					else
						return false;
				}
			},
			"moral.escrituraConstitutiva.foja": {
				required:function(){														
					if ($("#moral\\.escrituraConstitutiva\\.folioMercantil").val()=="")
						return true;
					else
						return false;
				}
			},
			"txtFechaRegistroEdicionEC": {
				required: true,
				validaFechaEC: true,
				dateMX: true
			}
		},
		messages:{			
			"moral.escrituraConstitutiva.folioMercantil":{
				required: "<br>Este campo es requerido cuando no se escriben datos en Secci&oacute;n, Partida,Vol&uacute;men y Foja"				
			}
		}
	});			
	return $("#escrituraConstitutivaForm").valid();	
}


function validarFormRegistroSindicato(){						
	
	$("#registroSindicatoForm").validate({
		rules: {
			"moral.registroSindicato.numReferenciadocRegistro": {
				required:true,
				number:true
			},
			"moral.registroSindicato.autoridadLaboral": {
				required:true
			}
		}
	});	
	var respuesta=$("#registroSindicatoForm").valid();	
	if (document.getElementById("txtFechaRegistroEdicion").value==""){
		document.getElementById("divErrorFecha").innerHTML="<font color=red>Seleccione una fecha</font>";
		respuesta=false;
	}
	else{		
		document.getElementById("divErrorFecha").innerHTML="";
	}
	return respuesta;
}

jQuery.validator.addMethod("tipoSociedadSeleccionada", function(value, element, param) {
	var result = true;

	if (param == value ) {
		result = false;
	}
	return result;
}, "Favor de seleccionar tipo de sociedad");

function validarDatosGenerales(){	
	$("#nombreComercialForm").validate({
		rules: {
			"moral.nombreComercial": {
				required:true,
				maxlength:250
			},
			"moral.rfc": {
				required:true,
				maxlength:20
			},
			"moral.razonSocial": {
				required:true,
				maxlength:250
			},
			"moral.tipoSociedad.idTipoSociedad": {
				tipoSociedadSeleccionada : -1
				
			},
			"fisica.primerApellido": {
				required:true,
				maxlength:250
			},
			"fisica.segundoApellido": {
				required:true,
				maxlength:250
			},
			"fisica.nombre": {
				required:true,
				maxlength:250
			},
			"fisica.nombreComercial": {
				required:true,
				maxlength:250
			},
			"fisica.curp": {
				required:true,
				maxlength:250
			},
			"fisica.rfc": {
				required:true,
				maxlength:250
			}
		}		
	});		
	return $("#nombreComercialForm").valid();	
}

function validaFormaFiltroSolicitud(){
	jQuery.validator.addMethod(
			"dateMX",
			function(value, element) {
				var check = false;
				var re = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
				if( re.test(value)){
					var adata = value.split('/');
					var dd = parseInt(adata[0],10);
					var mm = parseInt(adata[1],10);
					var yyyy = parseInt(adata[2],10);
					var xdata = new Date(yyyy,mm-1,dd);
					if ( ( xdata.getFullYear() == yyyy ) && ( xdata.getMonth () == mm - 1 ) && ( xdata.getDate() == dd ) )
						check = true;
					else
						check = false;
				} else
					check = false;
				return this.optional(element) || check;
			},
			"<br>Por favor ingresa una fecha v\u00E1lida con el siguiente formato dd/mm/yyyy"
		);
	
	
	jQuery.validator.addMethod(
		"fechaInicioPresentacionRequerida",
		function(){														
			if ($("#fechaFinPresentacion").val()!="" && $("#fechaInicioPresentacion").val()=="")
				return false;
			else
				return true;
		},
		"<br>Por favor ingresa la fecha de inicio de captura"
	);
	
	jQuery.validator.addMethod(
		"fechaFinPresentacionRequerida",
		function(){														
			if ($("#fechaInicioPresentacion").val()!="" && $("#fechaFinPresentacion").val()=="")
				return false;
			else
				return true;
		},
		"<br>Por favor ingresa la fecha de fin de captura"
	);	
	
	jQuery.validator.addMethod(
			"fechaInicioConclusionRequerida",
			function(){														
				if ($("#fechaFinConclusion").val()!="" && $("#fechaInicioConclusion").val()=="")
					return false;
				else
					return true;
			},
			"<br>Por favor ingresa la fecha de inicio de conclusi&oacute;n"
		);
		
		jQuery.validator.addMethod(
			"fechaFinConclusionRequerida",
			function(){														
				if ($("#fechaInicioConclusion").val()!="" && $("#fechaFinConclusion").val()=="")
					return false;
				else
					return true;
			},
			"<br>Por favor ingresa la fecha de fin de conclusi&oacute;n"
		);
		
		jQuery.validator.addMethod(
				"formatoNumeroRegistroPatronal",
				function(){
					var valorRP = $("#rp").val();
					if (valorRP!=null && valorRP!=""){
						var tamanoRP = valorRP.length;
						if(tamanoRP==8 || tamanoRP==10 || tamanoRP==11){
							return true;
						}else{
							return false;
						}
					}
					return true;
				},
				"<br>Por favor ingresa un n&uacute;mero de registro patronal v&aacute;lido.<br>Debe contener 8, 10 u 11 caracteres."
			);
		
	$("#filtroSolicitudForm").validate({
		rules: {
			"rp":{
				formatoNumeroRegistroPatronal: true
			},
			"fechaInicioPresentacion": {
				dateMX: true,
				fechaInicioPresentacionRequerida: true
			},
			"fechaFinPresentacion": {
				dateMX: true,
				fechaFinPresentacionRequerida: true
			},
			"fechaInicioConclusion": {
				dateMX: true,
				fechaInicioConclusionRequerida:true
			},
			"fechaFinConclusion": {
				dateMX: true,
				fechaFinConclusionRequerida:true
			}
		},
		errorLabelContainer:"#fechasError"
	});
	return $("#filtroSolicitudForm").valid();
}
