var objDatable;
$.getScript("/gestionAsegurados-web-externo/static/resources/js/delta/wizard/busquedaPersona/busquedaPersonaCurpWizard.js");

$(document).ready(function(){	
	 $.ajaxSetup({async:false});	
	 $.blockUI();
	
	 
	 $('#buscaDomicilio').hide();
	 muestraDomicilio();
	 
	 validateForm.allowOnlyRegularExpression( $('.entero_10'),regularExpression.entero_10);
	 validateForm.allowOnlyRegularExpression( $('.alfanumerico_espacios'),regularExpression.alfanumerico_espacios);
	 validateForm.allowOnlyRegularExpression( $('.alfabetico_espacios'),regularExpression.alfabetico_espacios);
	 
	 $('form:not(.formNotBlock)').submit(function(){
         $.blockUI();
	 });  
	 
	 $("#miFechaNacimientoFormateada").mask("99/99/9999");
	 
	 $('#frmRegDerechohabiente').validate({
		rules: {
			'miCorreoElectronico':{email:true },
			'miFechaNacimientoFormateada': {required : true, validDate : true },
			'miCurp': {curp: true }
		},
		messages: {
			'miCorreoElectronico':'El formato de Correo electr&oacute;nico es inv&aacute;lido',
			'miFechaNacimientoFormateada': {required : "Obligatorio", validDate : "Fecha inv\u00e1lida" },
			'miCurp': 'El formato de curp es incorrecto'
		},
		debug: true,
		submitHandler: function(form){
			form.submit();
		}
	});
	 
	var vig = $('#vigencia').val();
	var aseg = $('#conAsegurado').val();
	var proc = $('#proceso').val();	
	var idPersona = $('#tramiteRegistro\\.fisica\\.idPersona').val();	
	
	$("#buscarXCurp").click(function() {
		BusquedaPersonaPorCurpCtrl.init("buscarPersonaPorCurp",1);
		BusquedaPersonaPorCurpCtrl.setOnCloseCallback(fnOnPersonaReturn); 
		BusquedaPersonaPorCurpCtrl.abrir();
	});
	
	 if(proc != "0" && ($('#miParentesco').val() == 5 || $('#miParentesco').val() == 6)){
		 muestraDomicilio();
		 $('#seleccionarDom').show();
	 }	 		 	 
			
	$('#miFechaNacimientoFormateada').change(function() {
		$('#tramiteRegistro\\.fisica\\.fechaNacimientoFormateada').val($('#miFechaNacimientoFormateada').val());
	});
	
	 $("#tramiteRegistro\\.parentesco\\.idParentesco").change(function() {		
		var parentesco = $('#tramiteRegistro\\.parentesco\\.idParentesco').val();		
		var idse = $("#sexoR").val();	
		var idPersona = $("#tramiteRegistro\\.fisica\\.idPersona").val();
		
		if(parentesco == 5 || parentesco == 6) {
			
			$("#buscarXCurp").hide();
			
			if($.trim($("#miFechaNacimientoFormateada").val()) == "") {
				var mesNacAs = $("#tramiteRegistro\\.fisica\\.mesRegistroNac").val();
				var aniosNacAs = $("#tramiteRegistro\\.fisica\\.anioRegistroNac").val();
				var fechaNacimiento = convertirAFechaMesAnio(mesNacAs,aniosNacAs);
				
				var ultimoDia = dias(mesNacAs, aniosNacAs);
				var ultimo = new Date();
				var minimo = new Date();
				
				minimo.setFullYear(fechaNacimiento.getFullYear(),fechaNacimiento.getMonth(),1);
				ultimo.setFullYear(fechaNacimiento.getFullYear(),fechaNacimiento.getMonth(),ultimoDia);
				
				$("#miFechaNacimientoFormateada").datepicker({
					showOn: 'both',
					dateFormat: 'dd/mm/yy',
					minDate:minimo,
		            maxDate:ultimo,
					regional:'es',
					onClose: function(){
						$(this).valid();			
						validaEdad($('#miFechaNacimientoFormateada').val());
					}
				});	
				
				$("#miFechaNacimientoFormateada").removeAttr("disabled","disabled");
				$('#buscaDomicilio').hide();
			}
		} else {
			if($("#razonRegistroTramite").val() == "2") {
				$("#buscarXCurp").hide();
			} else {
				$("#buscarXCurp").show();
			}
		}
		
		validarDomicilioPersonaParentesco(idPersona);
		
		if( parentesco >= 0){
			$("#guia").show();
		}
		if(parentesco != "-1"){
			$("#miParentesco").val(parentesco);
			hijos(parentesco);
			pareja();
		}	
		
	});	
	 
	$("#miFechaNacimientoFormateada").datepicker({
		showOn: 'both',
		dateFormat: 'dd/mm/yy',
		changeYear: true,
		changeMonth: true,
		maxDate: new Date(),
		yearRange : '-112:+0',
		regional:'es',
		onClose: function(){
			$(this).valid();			
			validaEdad($('#miFechaNacimientoFormateada').val());
		}});			
	
	if(vig == "1"){
		$("#msg13").dialog({
			modal: true,
			close: function(event, ui) {location.href = "" + context_path + "/inicio/grupoFamiliar";},
		    buttons : {
		        "Aceptar" : function() {		        	
		        	location.href = "" + context_path + "/inicio/grupoFamiliar";
		        }
		      }
		  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	}else if(aseg == "6" && proc == "0"){
		$("#msg14").dialog({
			modal: true,
			close: function(event, ui) {location.href = "" + context_path + "/inicio/grupoFamiliar";},
		    buttons : {
		        "Aceptar" : function() {
		        	location.href = "" + context_path + "/inicio/grupoFamiliar";
		        }			  	
		      }
		  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	}else if(proc == "0" ){	
		if(aseg == "0" || aseg == "1"){		
			
			var urlDomicilios = "/gestionDomicilios-web/static/resources/js/delta/domicilios/Domicilio.js";
			$.getScript(urlDomicilios).done(
                function(script, textStatus) {

                    DomicilioCtrl.init('domicilioLocaliza');
                    DomicilioCtrl.setOnCloseCallback(fnOnCloseDomicilio);
                    
                }).fail(
          		  function(jqxhr, settings, exception) {
          			$("#msgDomicilio").dialog({			 
  				      buttons : {
  				        "Aceptar" : function() {
  				        	$(this).dialog("close");
  				        }
  				      }
  				  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();			  	                       
            }); 		      
			$('#parentAseg').hide();
	    	$('#parentInteg').show();
			$('#buscaPersonaF').show();		
			if(idPersona != ""){
		    	  setEditable(idPersona);
		      }
		}else{
			var reg = $('#registrado').val();
			var medios = $('#mc').val();
			
			datosAsegurado(aseg,medios);
			
			if(reg == "1"){
				$("#msg09").dialog({
					modal: true,
				      buttons : {
				        "Aceptar" : function() {
				        	$(this).dialog("close");
				        }
				      }
				  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();		
			}
			
			var urlDomicilios = "/gestionDomicilios-web/static/resources/js/delta/domicilios/Domicilio.js";
		      $.getScript(urlDomicilios).done(
	                function(script, textStatus) {
	
	                        DomicilioCtrl.init('domicilioLocaliza');
	                        DomicilioCtrl.setOnCloseCallback(fnOnCloseDomicilio);
	                        
	                }).fail(
	              		  function(jqxhr, settings, exception) {
	              			$("#msgDomicilio").dialog({			 
	      				      buttons : {
	      				        "Aceptar" : function() {
	      				        	$(this).dialog("close");
	      				        }
	      				      }
	      				  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();			  	                       
	                }); 
		      
		}
	}else{
		var medios = $('#mc').val();
		
		var urlDomicilios = "/gestionDomicilios-web/static/resources/js/delta/domicilios/Domicilio.js";
	      $.getScript(urlDomicilios).done(
              function(script, textStatus) {

                      DomicilioCtrl.init('domicilioLocaliza');
                      DomicilioCtrl.setOnCloseCallback(fnOnCloseDomicilio);
                      
              }).fail(
            		  function(jqxhr, settings, exception) {
        			  $("#msgDomicilio").dialog({
        				  modal: true,
      				      buttons : {
      				        "Aceptar" : function() {
      				        	$(this).dialog("close");
      				        }
      				      }
      				  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
              });
	      
	      if(idPersona != ""){
	    	  setEditable(idPersona);
	      }
	      	      
	      if(aseg == "2" || aseg== "3" || aseg == "4" || aseg== "5"){
	    	  $('#parentAseg').show();
	      	  $('#parentInteg').hide();
	    	  $('#buscaPersonaF').hide();
	      }else{
	    	  $('#parentAseg').hide();
	      	  $('#parentInteg').show();
	    	  $('#buscaPersonaF').show();
	      }
	}	
	
	$("#mensajeError").dialog({	
		title: "Error",
        autoOpen: false,
	    closeOnEscape: false,
        width : 400,
        modal: true,
        resizable: false,
	    buttons : {
	    	"Aceptar" : function() {
	    		$(this).html('');
	        	$(this).dialog("close");
	        }
	      }
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	habilitarDesabilitarCamposDatosBasicos(false);
	
	if($("#miNombre").val() == "RECIEN NACIDO") {
		$("#miNombre").attr("disabled","disbled");
	}
	
	
	
	$.unblockUI();
});	

function convertirAFechaMesAnio(mes, anio) {
	var date = new Date();
	  date.setMonth(mes-1); //en javascript los meses van de 0 a 11
	  date.setDate(1);
	  date.setYear(anio);
	  
	  return date;
}

function dias(mes, anno) {
	anno = parseInt(anno);
	
    switch (mes) {
	    case 1 : case 3 : case 5 : case 7 : case 8 : case 10 : case 12 : return 31;
		case 2 : return (anno % 4 == 0) ? 29 : 28;
	}
    
	return 30;
 }


function buscarPersonaPorCurp() {
	
	$.ajax({
		async: true,
    	url : context_path + "/derechohabiente/buscarPersona",
        type: 'post',
        data : {
			'curp' : $("#miCurp").val()
		},
        dataType: 'json',
        success: function (result) {
        	if(result.errorFormGeneral != undefined && result.errorFormGeneral != null) {
        		$("#mensajeError").html(result.errorFormGeneral);
        		$("#mensajeError").dialog('open');
        		limpiarDatosBasicos();
        	} else {
        		setPersonaCommon(result);
        	}
        }
    });
	
}

function muestraDomicilio(){
	$("#domicilio\\.asentamiento\\.nombre").attr('readonly','readonly');
	 $("#domicilio\\.asentamiento\\.localidad\\.nombre").attr('readonly','readonly');
	 $("#domicilio\\.asentamiento\\.localidad\\.municipio\\.nombre").attr('readonly','readonly');
	 $("#domicilio\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre").attr('readonly','readonly');
	 $("#codigoPostal").attr('readonly','readonly');
	 $("#numeroExterior").attr('readonly','readonly');
	 $("#numeroInterior").attr('readonly','readonly');
	 $("#secundario").attr('readonly','readonly');
	 $("#vialidadPrimaria").attr('readonly','readonly');
	 $("#referenciaPrimaria").attr('readonly','readonly');
	 $("#refSecundaria").attr('readonly','readonly');
	 $("#refPosterior").attr('readonly','readonly');
}

function preparaGuia(){
	var perfil = $('#perfil').val();
	var parentesco = $('#tramiteRegistro\\.parentesco\\.idParentesco').val();
	
	if(perfil == "1"){
		showGuiaTramite('44',perfil);
	}else{
		if(parentesco == "1" || parentesco == "4" || parentesco =="7"){
			showGuiaTramite('46',perfil);
		}else{
			showGuiaTramite('47',perfil);
		}
	}	
}
	  
function datosAsegurado(aseg,medios){
	
	if(aseg == "2" || aseg== "3" || aseg == "4" || aseg== "5"){			
		$('#cmbLN').hide();
		$('#cmbSX').hide();
		$('#cmbLNAseg').show();
		$('#cmbSXAseg').show();
		$('#miNombre').attr('disabled','disabled');
    	$('#miCurp').attr('disabled','disabled');
    	$('#miPrimerApellido').attr('disabled','disabled');
    	$('#miSegundoApellido').attr('disabled','disabled');
    	$('#miFechaNacimientoFormateada').attr('disabled','disabled');
    	$('#miFechaNacimientoFormateada').datepicker('destroy');    		
    	$('#lugarNacimiento').attr('disabled','disabled');
    	$('#sexoR').attr('disabled','disabled');
    	$('#buscaPersonaF').hide();
    	$('#parentAseg').show();
    	$('#parentInteg').hide();
    	if($.trim($('#miCorreoElectronico').val()) != "")    		
    		$('#miCorreoElectronico').attr('disabled','disabled');
    	if($.trim($('#miTelefonoFijo').val()) != "")
    		$('#miTelefonoFijo').attr('disabled','disabled');
    	if($.trim($('#miTelefonoMovil').val()) != "")
    		$('#miTelefonoMovil').attr('disabled','disabled');
    	if($.trim($('#miFacebook').val()) != "")
    		$('#miFacebook').attr('disabled','disabled');
    	if($.trim($('#miTwitter').val()) != "")
    		$('#miTwitter').attr('disabled','disabled');    	
	}
}

function iniciaConsultaPersona(){
	//PersonaFisicaCtrl.buscar();
} 

function iniciaConsultaDomicilio(){
	DomicilioCtrl.localizar();
} 

function validaEdad(fechaNacimiento){
	var parentesco = $('#tramiteRegistro\\.parentesco\\.idParentesco').val();
	var perfil = $('#perfil').val();
	var url = context_path + "/derechohabientes/getValidaEdad";
	var parametros = {
		'fechaNacimientoFormateada':fechaNacimiento		
	};
	if(perfil == "1"){
		if(parentesco == "2"){			
			$.postJSON(url, parametros, function(result) {	
				if(result.modelo <= 1){
					if($('#recienNacido').is(":checked")){
						$('#tipoRegistro\\.idRazonRegistro').val("2");
					}else{
						if($('#miNombre').val() == 'RECIEN NACIDO' ){
							$('#miNombre').val('');
						}
						$('#tipoRegistro\\.idRazonRegistro').val("3");
					}
					
					$('#unaRazonRegistro').hide();
					$('#sinRazonRegistro').show();
					$('#conRN').show();
					$('#sinRN').hide();
				}
				
				if(result.modelo > 1 && result.modelo <= 16){					
					if($('#miNombre').val() == 'RECIEN NACIDO' ){
						$('#miNombre').val('');
					}
					$('#tipoRegistro\\.idRazonRegistro').val("3");
					$('#unaRazonRegistro').hide();
					$('#sinRazonRegistro').show();
					
					$('#conRN').hide();
					$('#sinRN').show();
				}
				
				if(result.modelo > 16 && result.modelo <= 25){	
					if($('#miNombre').val() == 'RECIEN NACIDO' ){
						$('#miNombre').val('');
					}
					if($("#sexoR").val() == "2"){
						$('#tipoRegistro\\.idRazonRegistro').val("3");	
					}else{
						$('#tipoRegistro\\.idRazonRegistro').val("6");
					}
					$('#unaRazonRegistro').hide();
					$('#sinRazonRegistro').show();
					$('#conRN').hide();
					$('#sinRN').show();
				}
				
				if(result.modelo > 25){	
					if($('#miNombre').val() == 'RECIEN NACIDO' ){
						$('#miNombre').val('');
					}			
					$('#tipoRegistro\\.idRazonRegistro').val("8");
					$('#unaRazonRegistro').show();
					$('#sinRazonRegistro').hide();
					$('#conRN').hide();
					$('#sinRN').show();
				}
			});			
		}
	}
}
function setEditable(idPersona) {	
	$('#miNombre').attr('disabled','disabled');
	$('#miPrimerApellido').attr('disabled','disabled');
	$('#miSegundoApellido').attr('disabled','disabled');
	$('#lugarNacimiento').attr('disabled','disabled');
	$('#sexoR').attr('disabled','disabled');
	$('#miFechaNacimientoFormateada').attr('disabled','disabled');
	$('#miFechaNacimientoFormateada').datepicker("destroy");	
	if($('#tramiteRegistro\\.parentesco\\.idParentesco').val() == "2" && $('#tramiteRegistro\\.razonRegistro\\.idRazonRegistro').val() == "2"){
		$('#tramiteRegistro\\.parentesco\\.idParentesco')[0].selectedIndex=0;
		$('#tramiteRegistro\\.razonRegistro\\.idRazonRegistro')[0].selectedIndex=0;
	}	
}

var fnOnPersonaReturn = function(){
    
	var p = this;	
	var proc = $('#proceso').val(); 
	var miPersona = $('#tramiteRegistro\\.fisica\\.idPersona').val();	
	
	if(!jQuery.isEmptyObject(p)){
		limpiarDatosBasicos();
		setPersonaCommon(p);
		pareja();
		hijos();
		p=null;		
	}			
} 


function limpiarDatosBasicos() {
	$('#tramiteRegistro\\.fisica\\.idPersona').val("");
	$('#miNombre').val("");
	$('#tramiteRegistro\\.fisica\\.nombre').val("");
	$('#tramiteRegistro\\.fisica\\.curp').val("");
	$('#miPrimerApellido').val("");
	$('#tramiteRegistro\\.fisica\\.primerApellido').val("");
	$('#miSegundoApellido').val("");
	$('#tramiteRegistro\\.fisica\\.segundoApellido').val("");
    $('#miFechaNacimientoFormateada').val("");
    $('#tramiteRegistro\\.fisica\\.fechaNacimientoFormateada').val("");
    $('#tramiteRegistro\\.fisica\\.sexo\\.idSexo').val("");
	$('#sexoR')[0].selectedIndex=0;
	$('#tramiteRegistro\\.fisica\\.lugarNacimiento\\.clave').val("");
	$('#lugarNacimiento')[0].selectedIndex=0;
	$('#calificacion').val("");
}

function setPersonaCommon(persona) {
	if(persona.personaCalificaciones != null && persona.personaCalificaciones.length > 0 || persona.idPersona != undefined){
		iniciar();
	}
	
	if(persona.idPersona == undefined || persona.idPersona == null){ // Persona RENAPO
		$('#tramiteRegistro\\.fisica\\.idPersona').val("");
		limpiarMedios();
		setDomicilioAsegurado();
	}else if(persona.idPersona != undefined ){ // Persona en IMSS con calificacion
		buscarMediosContacto(persona.idPersona);
		$('#tramiteRegistro\\.fisica\\.idPersona').val(persona.idPersona);			
	}
		
	if(persona.nombre != undefined){
		$('#miNombre').val(persona.nombre);
		$('#tramiteRegistro\\.fisica\\.nombre').val(persona.nombre);
	}
	if(persona.curp != undefined){
		$('#miCurp').val(persona.curp);
		$('#tramiteRegistro\\.fisica\\.curp').val(persona.curp);
	}
	
	if(persona.primerApellido != undefined){        
		$('#miPrimerApellido').val(persona.primerApellido);
		$('#tramiteRegistro\\.fisica\\.primerApellido').val(persona.primerApellido);
	}
	
	if(persona.segundoApellido != undefined){
		$('#miSegundoApellido').val(persona.segundoApellido);
		$('#tramiteRegistro\\.fisica\\.segundoApellido').val(persona.segundoApellido);
	}
    
    if(persona.fechaNacimientoFormateada != undefined){
    	$('#miFechaNacimientoFormateada').val(persona.fechaNacimientoFormateada);
    	$('#tramiteRegistro\\.fisica\\.fechaNacimientoFormateada').val(persona.fechaNacimientoFormateada);
    	validaEdad(persona.fechaNacimientoFormateada);
    }
    
    if(persona.sexo != undefined){
    	if(persona.sexo.idSexo != null){  
	    	$('#tramiteRegistro\\.fisica\\.sexo\\.idSexo').val(persona.sexo.idSexo);
	    	$('#sexoR').val(persona.sexo.idSexo);
    	}
    }
    if(persona.lugarNacimiento != undefined){
    	if(persona.lugarNacimiento.clave != null){
	    	$('#tramiteRegistro\\.fisica\\.lugarNacimiento\\.clave').val(persona.lugarNacimiento.clave);
	    	$('#lugarNacimiento').val(persona.lugarNacimiento.clave);
    	}
    }		                	   
	
	if(persona.personaCalificaciones != null && persona.personaCalificaciones.length > 0 || persona.idPersona != undefined){	    	
    	setEditable(persona.idPersona);	    	
    	$('#calificacion').val("0");
    }	
}


function iniciar(){	
	var proc = $('#proceso').val();
	
	$('#tramiteRegistro\\.fisica\\.idPersona').val('');		
	$('#tramiteRegistro\\.fisica\\.nombre').val('');		
	$('#tramiteRegistro\\.fisica\\.primerApellido').val('');		
	$('#tramiteRegistro\\.fisica\\.segundoApellido').val('');		
	$('#tramiteRegistro\\.fisica\\.curp').val('');		
	$('#lugarNacimiento')[0].selectedIndex=0; 		
	$('#sexoR')[0].selectedIndex=0;		
	$('#tramiteRegistro\\.fisica\\.fechaNacimientoFormateada').val('');		
	$('#tramiteRegistro\\.fisica\\.estadoCivil\\.idEstadoCivil')[0].selectedIndex=0;			
		
}

function setDomicilioAsegurado() {
	
	var url = '/${mvn.web.app.root}/derechohabientes/getDomicilioAsegurado';
	$.blockUI();
	
	$.postJSON(url, null, function(domicilio) {
		if(domicilio != null) {
			setDomicilioCommon(domicilio);
			var idParentesco = $('#tramiteRegistro\\.parentesco\\.idParentesco').val();	
			var patronIMSS = $('#imss').val() == "true";
			
			if(idParentesco != "" && idParentesco != "-1") {
				var isAsegurado = idParentesco == "5" || idParentesco == "6";
				var isPadre = idParentesco == "1" || idParentesco == "8";
				var isConcubina = idParentesco == "4" || idParentesco == "7";
				
				var mensaje = "";
				
				if((!isPadre && !isConcubina) || (isPadre && patronIMSS) || isAsegurado) {
					if(!isAsegurado) {
						mensaje = "De clic en el bot&oacute;n ubicar domicilio en caso de que desee modificarlo. <strong>Nota:</strong> El domicilio" +
						" debera estar dentro de la circunscripci&oacute;n de la Unidad Medica Familiar del usuario firmado.";
						
						$("#buscaDomicilio").show();
					} else {
						mensaje = "El domicilio del asegurado/pensionado se pedir&aacute; una vez que se de click en el bot&oacute;n Aceptar. <strong>Nota:</strong> El domicilio" +
						" debera estar dentro de la circunscripci&oacute;n de la Unidad Medica Familiar del usuario firmado.";
						
						$("#buscaDomicilio").show();
					}
				} else {
					mensaje = "El parentesco a registrar no permite que la persona tenga un domicilio distinto al del asegurado / pensionado." +
							" De clic en <strong>Aceptar</strong> para continuar.";	
					$("#buscaDomicilio").hide();
				}

				$("#mensajeRegi").show();
				$("#mensajeRegistro").html(mensaje);
						
			}
			
		}
		
		$.unblockUI();
	});
}

function validarDomicilioPersonaParentesco(idPersona) {
	//console.log("entro a validar dom");
	if(idPersona == "") {
		setDomicilioAsegurado();
	} else {
		var parentesco = $('#tramiteRegistro\\.parentesco\\.idParentesco').val();
		var isAsegurado = parentesco == "5" || parentesco == "6";
		
		var oForm = {
			'fisica': {'idPersona' : idPersona},
			'parentesco' : {'idParentesco' : parentesco}
		};
	
		var url = '/${mvn.web.app.root}/derechohabientes/validacionesDomicilio';
	
		$("#mensajeErrorDomicilio").hide();
		$("#mensajeErr").html("");
		
		$("#mensajeRegi").hide();
		$("#mensajeRegistro").html("");
		//console.log("entro a validar el domicilio")
		$.blockUI();
		$.postJSON(url, oForm, function(valDomicilio) {
			//Verificamos si existe error
			var error = valDomicilio.error;
			//Validamos si no hubo error en las validaciones de domicilio
			if(error) {
				if(valDomicilio.mensajeError != "0") {
					//Si ocurrio un error ocultamos el boton de enviar
					$("#enviar").hide();
					//mostramos el error
					
					setDomicilioCommon(valDomicilio.personaDomicilio.domicilio);
					$("#mensajeErrorDomicilio").show();
					$("#mensajeErr").html(valDomicilio.mensajeError);
				} else {
					//Si ocurrio un error ocultamos el boton de enviar
					$("#enviar").hide();
					//mostramos el error
					$("#mensajeErr").html("Ocurri&oacute; un error al consultar el domicilio de la persona");
					setDomicilioCommon(valDomicilio.personaDomicilio.domicilio);
					$("#mensajeErrorDomicilio").show();
				}
			} else {
				$("#mensajeErrorDomicilio").hide();
				//Validamos si permite ubicar domicilio de la persona
				var permiteUbicarDomicilio = valDomicilio.permiteUbicarDomcilio;
				setDomicilioCommon(valDomicilio.personaDomicilio.domicilio);
				//Si si permite ubicarlo mostramos el boton
				if(permiteUbicarDomicilio) {
					
					if(!isAsegurado) {
						mensajeDom = "De clic en el bot&oacute;n ubicar domicilio en caso de que desee modificarlo. <strong>Nota:</strong> El domicilio" +
						" debera estar dentro de la circunscripci&oacute;n de la Unidad Medica Familiar del usuario firmado.";
						
						$("#buscaDomicilio").show();
					} else {
						mensajeDom = "El domicilio del asegurado/pensionado se pedir&aacute; una vez que se de clic en el bot&oacute;n Aceptar. <strong>Nota:</strong> El domicilio" +
						" debera estar dentro de la circunscripci&oacute;n de la Unidad Medica Familiar del usuario firmado.";
						
						$("#buscaDomicilio").hide();
					}
					
					$("#mensajeRegi").show();
					$("#mensajeRegistro").html(mensajeDom);
					
				} else {
					$("#buscaDomicilio").hide();
					$("#mensajeRegi").show();
					$("#mensajeRegistro").html(valDomicilio.mensajeDomicilio);
				}
				$("#enviar").show();
			}
			
			$.unblockUI();
		});
	}
}

function limpiarMedios() {
	$("#tramiteRegistro\\.fisica\\.telefonoFijo\\.clave").val('');
	$("#miTelefonoFijo").val('');
	$("#tramiteRegistro\\.fisica\\.telefonoFijo\\.claveLada").val('');
	
	$("#tramiteRegistro\\.fisica\\.telefonoMovil\\.clave").val('');
	$("#tramiteRegistro\\.fisica\\.telefonoMovil\\.numero").val('');
	$("#miTelefonoMovil").val('');
	
	$("#tramiteRegistro\\.fisica\\.correoElectronico\\.clave").val('');
	$("#tramiteRegistro\\.fisica\\.correoElectronico\\.correo").val('');
	$("#miCorreoElectronico").val('');
	
	$("#tramiteRegistro\\.fisica\\.facebook\\.clave").val('');
	$("#tramiteRegistro\\.fisica\\.facebook\\.cuenta").val('');
	$("#miFacebook").val('');
	
	$("#tramiteRegistro\\.fisica\\.twitter\\.clave").val('');
	$("#tramiteRegistro\\.fisica\\.twitter\\.cuenta").val('');
	$("#miTwitter").val('');
}

function buscarMediosContacto(idPersona) {
	var oForm = {
		'idPersona' : idPersona
	};
	
	var url = '/${mvn.web.app.root}/derechohabientes/getMediosContacto';
	
	$.blockUI();
	$.postJSON(url, oForm, function(fisica) {
		
		if(fisica.telefonoFijo != undefined && fisica.telefonoFijo != null) {
			$("#tramiteRegistro\\.fisica\\.telefonoFijo\\.clave").val(fisica.telefonoFijo.clave);
			$("#miTelefonoFijo").val(fisica.telefonoFijo.claveLada);
			$("#tramiteRegistro\\.fisica\\.telefonoFijo\\.claveLada").val(fisica.telefonoFijo.claveLada);
		} else {
			$("#tramiteRegistro\\.fisica\\.telefonoFijo\\.clave").val('');
			$("#tramiteRegistro\\.fisica\\.telefonoFijo\\.claveLada").val('');
			$("#miTelefonoFijo").val('');
		}
		
		if(fisica.telefonoMovil != undefined && fisica.telefonoMovil != null) {
			$("#tramiteRegistro\\.fisica\\.telefonoMovil\\.clave").val(fisica.telefonoMovil.clave);
			$("#tramiteRegistro\\.fisica\\.telefonoMovil\\.numero").val(fisica.telefonoMovil.numero);
			$("#miTelefonoMovil").val(fisica.telefonoMovil.numero);
		} else {
			$("#tramiteRegistro\\.fisica\\.telefonoMovil\\.clave").val('');
			$("#tramiteRegistro\\.fisica\\.telefonoMovil\\.numero").val('');
			$("#miTelefonoMovil").val('');
		}
		
		if(fisica.correoElectronico != undefined && fisica.correoElectronico != null) {
			$("#miCorreoElectronico").val(fisica.correoElectronico.correo);
			$("#tramiteRegistro\\.fisica\\.correoElectronico\\.clave").val(fisica.correoElectronico.clave);
			$("#tramiteRegistro\\.fisica\\.correoElectronico\\.correo").val(fisica.correoElectronico.correo);
		} else {
			$("#tramiteRegistro\\.fisica\\.correoElectronico\\.clave").val('');
			$("#tramiteRegistro\\.fisica\\.correoElectronico\\.correo").val('');
			$("#miCorreoElectronico").val('');
		}
		
		if(fisica.facebook != undefined && fisica.facebook != null) {
			$("#miFacebook").val(fisica.facebook.cuenta);
			$("#tramiteRegistro\\.fisica\\.facebook\\.clave").val(fisica.facebook.clave);
			$("#tramiteRegistro\\.fisica\\.facebook\\.cuenta").val(fisica.facebook.cuenta);
		} else {
			$("#tramiteRegistro\\.fisica\\.facebook\\.clave").val('');
			$("#tramiteRegistro\\.fisica\\.facebook\\.cuenta").val('');
			$("#miFacebook").val('');
		}
		
		if(fisica.twitter != undefined && fisica.twitter != null) {
			$("#miTwitter").val(fisica.twitter.clave);
			$("#tramiteRegistro\\.fisica\\.twitter\\.clave").val(fisica.twitter.clave);
			$("#fisica\\.twitter\\.cuenta").val(fisica.twitter.cuenta);
		} else {
			$("#tramiteRegistro\\.fisica\\.twitter\\.clave").val('');
			$("#tramiteRegistro\\.fisica\\.twitter\\.cuenta").val('');
			$("#miTwitter").val('');
		}
		
		$.unblockUI();
		validarDomicilioPersonaParentesco(idPersona);
	}).error(function(data){
		
	});
}

function pareja(){
	var idPersona = $('#tramiteRegistro\\.fisica\\.idPersona').val();
	var idSexoAseg = $('#idSexoAseg').val();
	
	if($('#calificacion').val() != "0"){
		
		if($('#tramiteRegistro\\.parentesco\\.idParentesco').val() == "8" || $('#tramiteRegistro\\.parentesco\\.idParentesco').val() == "1"){				
				
				if($('#tramiteRegistro\\.parentesco\\.idParentesco').val() == "1"){

					$('#sexoR').val("1");
					$('#tramiteRegistro\\.fisica\\.sexo\\.idSexo').val($('#sexoR').val());
				}else if($('#tramiteRegistro\\.parentesco\\.idParentesco').val() == "8"){
					
					$('#sexoR').val("2");
					$('#tramiteRegistro\\.fisica\\.sexo\\.idSexo').val($('#sexoR').val());
				}
				
				habilitarDesabilitarCamposDatosBasicos(false);
		}
	}else{ 
		if($('#sexoR').val() == "1" && $('#tramiteRegistro\\.parentesco\\.idParentesco').val() == "8"){
			$("#msgSexoPadres").dialog({
				  modal: true,
			      buttons : {
		    	  "Aceptar" : function() {	
		    		  	$('#tramiteRegistro\\.parentesco\\.idParentesco')[0].selectedIndex=0;			    		
			        	$(this).dialog("close");
			        }
			      }
			  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
		}
		if($('#sexoR').val() == "2" && $('#tramiteRegistro\\.parentesco\\.idParentesco').val() == "1"){
			$("#msgSexoPadres").dialog({
				  modal: true,
			      buttons : {
		    	  "Aceptar" : function() {	
		    		  	$('#tramiteRegistro\\.parentesco\\.idParentesco')[0].selectedIndex=0;			    		
			        	$(this).dialog("close");
			        }
			      }
			  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
		}

	}
}

function hijos(parentesco){
	var idPersona = $('#tramiteRegistro\\.fisica\\.idPersona').val();	
	var apellido = $('#pApellidoAseg').val();
	var aseg = $('#conAsegurado').val();
	var tamFecha = $('#miFechaNacimientoFormateada').val().length;
	var proc = $('#proceso').val();
	
	if(parentesco == "2"){		
//		if(proc == "0"){
//			$('#recienNacido').removeAttr("checked");
//		}
		if(idPersona != "" && $('#tipoRegistro\\.idRazonRegistro').val()== "2"){
			$('#conRN').show();
			$('#sinRN').hide();			
		}else if(idPersona == ""){
			$('#conRN').show();
			$('#sinRN').hide();
		}		
		
		$('#unaRazonRegistro').hide();
		$('#sinRazonRegistro').show();
		if(tamFecha == 10){
			validaEdad($('#miFechaNacimientoFormateada').val());
		}
		
		if($("#razonRegistroTramite").val() == "2") {
			$("#buscarXCurp").hide();

			habilitarDesabilitarCamposDatosBasicos(true);
			$("#buscaDomicilio").show();
			$('#buscarXCurp').hide();
			$("#miCurp").attr("disabled","disabled");
			$("#miNombre").attr("disabled","disabled");
		} else {
			$("#buscarXCurp").show();
			habilitarDesabilitarCamposDatosBasicos(false);
		}
	}else{		
		if($('#recienNacido').is(":checked")){
			$('#recienNacido').removeAttr("checked");		
			$('#miNombre').val('');
		}
		$('#sinRN').show();	
		$('#conRN').hide();
		$('#unaRazonRegistro').show();
		$('#sinRazonRegistro').hide();
	}	
}

function habilitarDesabilitarCamposDatosBasicos(habilitar) {
	if(habilitar) {
		$("#miNombre").removeAttr("disabled");
		$("#miPrimerApellido").removeAttr("disabled");
		$("#lugarNacimiento").removeAttr("disabled");
		$("#miSegundoApellido").removeAttr("disabled");
		$("#sexoR").removeAttr("disabled");
		$("#miFechaNacimientoFormateada").removeAttr("disabled");
		$( "#miFechaNacimientoFormateada" ).datepicker( "option", "showOn", "both" );
	} else {
		$("#miCurp").attr("disabled","disabled");
		$("#miNombre").attr("disabled","disabled");
		$("#miPrimerApellido").attr("disabled","disabled");
		$("#lugarNacimiento").attr("disabled","disabled");
		$("#miSegundoApellido").attr("disabled","disabled");
		$("#sexoR").attr("disabled","disabled");
		$("#miFechaNacimientoFormateada").attr("disabled","disabled");
		$("#miFechaNacimientoFormateada").datepicker( "option", "showOn", "focus" );
	}
}

function rn(){  
   var idPersona = $('#tramiteRegistro\\.fisica\\.idPersona').val();	
   var apellido = $('#pApellidoAseg').val();

   $('#miNombre').val('RECIEN NACIDO');
   $('#miNombre').attr('disabled','disabled');
   $('#tramiteRegistro\\.fisica\\.nombre').val('RECIEN NACIDO');
}


function fnOnCloseDomicilio(){
	
	var objDomicilio = this;	
	var parentesco = $('#tramiteRegistro\\.parentesco\\.idParentesco').val();
	
	if(objDomicilio != null){
		setDomicilioCommon(objDomicilio);
		
		if(objDomicilio.asentamiento != null){
			if(parentesco == 5 || parentesco == 6){
				$('#frmRegDerechohabiente').submit();
			}			
		}else{
			if(parentesco == 5 || parentesco == 6){
				$("#msgDomicilioOblig").dialog({
					modal: true,
				      buttons : {
				        "Aceptar" : function() {			        	
				        	$(this).dialog("close");
				        }
				      }
				 }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
			}
			
		}
	}		
			
}

function setDomicilioCommon(objDomicilio) {
	
	if(objDomicilio != null){
		
	
	if(objDomicilio.asentamiento != null){			
		$('#domicilio\\.asentamiento\\.clave').val(objDomicilio.asentamiento.clave);
		$('#domicilio\\.asentamiento\\.nombre').val(objDomicilio.asentamiento.nombre);
		
		if(objDomicilio.asentamiento.localidad != null){
			$('#domicilio\\.asentamiento\\.localidad\\.clave').val(objDomicilio.asentamiento.localidad.clave);
			$('#domicilio\\.asentamiento\\.localidad\\.nombre').val(objDomicilio.asentamiento.localidad.nombre);
			
			if(objDomicilio.asentamiento.localidad.municipio != null){
				$('#domicilio\\.asentamiento\\.localidad\\.municipio\\.clave').val(objDomicilio.asentamiento.localidad.municipio.clave);
				$('#domicilio\\.asentamiento\\.localidad\\.municipio\\.nombre').val(objDomicilio.asentamiento.localidad.municipio.nombre);
				
				if(objDomicilio.asentamiento.localidad.municipio.entidadFederativa){
					$('#domicilio\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave').val(objDomicilio.asentamiento.localidad.municipio.entidadFederativa.clave);
					$('#domicilio\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre').val(objDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre);
				}							
			}
		}				
	}
	var numExt = "";
	var numExtAlf = "";
	var numInt = "";
	var numIntAlf = "";
	var numExt2 = "";
	
	if(objDomicilio.numExterior1 != undefined){
		numExt = objDomicilio.numExterior1;
	}
	if(objDomicilio.numExteriorAlf != undefined){
		numExtAlf = objDomicilio.numExteriorAlf;
	}
	if(objDomicilio.numInterior != undefined){
		numInt = objDomicilio.numInterior;
	}
	if(objDomicilio.numInteriorAlf != undefined){
		numIntAlf = objDomicilio.numInteriorAlf;
	}
	if(objDomicilio.numExterior2 != undefined){
		numExt2 = objDomicilio.numExterior2;
	}
	
	$('#numeroExterior').val(numExt+" "+numExtAlf);
	$('#numeroInterior').val(numInt+" "+numIntAlf);
	$('#secundario').val(numExt2);
	
	$('#domicilio\\.numExterior1').val(objDomicilio.numExterior1);
	$('#domicilio\\.numExteriorAlf').val(objDomicilio.numExteriorAlf);
	$('#domicilio\\.numExterior2').val(objDomicilio.numExterior2);
	$('#domicilio\\.numInterior').val(objDomicilio.numInterior);
	$('#domicilio\\.numInteriorAlf').val(objDomicilio.numInteriorAlf);
					
	if(objDomicilio.vialidadPrimaria != undefined){
		$('#domicilio\\.vialidadPrimaria\\.clave').val(objDomicilio.vialidadPrimaria.clave);
		$('#domicilio\\.vialidadPrimaria\\.nombre').val(objDomicilio.vialidadPrimaria.nombre);
		
		if(objDomicilio.vialidadPrimaria.tipoVialidad != undefined){
			$('#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.clave').val(objDomicilio.vialidadPrimaria.tipoVialidad.clave);
			$('#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.descripcion').val(objDomicilio.vialidadPrimaria.tipoVialidad.descripcion);
		} else {
			$('#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.clave').val("");
			$('#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.descripcion').val("");
		}
		
		var nombreVialidadPrimaria = "";
		
		if(objDomicilio.vialidadPrimaria.tipoVialidad != undefined) {
			nombreVialidadPrimaria += "" +objDomicilio.vialidadPrimaria.tipoVialidad.descripcion + " ";
		}
		
		nombreVialidadPrimaria += "" + objDomicilio.vialidadPrimaria.nombre;
		$('#vialidadPrimaria').val(nombreVialidadPrimaria);
	}
	
	if(objDomicilio.vialidadReferenciaPrimaria != undefined){
		$('#domicilio\\.vialidadReferenciaPrimaria\\.clave').val(objDomicilio.vialidadReferenciaPrimaria.clave);
		$('#domicilio\\.vialidadReferenciaPrimaria\\.nombre').val(objDomicilio.vialidadReferenciaPrimaria.nombre);
		if(objDomicilio.vialidadReferenciaPrimaria.tipoVialidad != null){
			$('#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave').val(objDomicilio.vialidadReferenciaPrimaria.tipoVialidad.clave);
			$('#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.descripcion').val(objDomicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion);
		}
		$('#referenciaPrimaria').val(objDomicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion+" "+objDomicilio.vialidadReferenciaPrimaria.nombre);
	} else {
		$('#domicilio\\.vialidadReferenciaPrimaria\\.clave').val("");
		$('#domicilio\\.vialidadReferenciaPrimaria\\.nombre').val("");
		$('#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.clave').val("");
		$('#domicilio\\.vialidadReferenciaPrimaria\\.tipoVialidad\\.descripcion').val("");
		$('#referenciaPrimaria').val("");
	}
	
	if(objDomicilio.vialidadReferenciaSecundaria != undefined){
		$('#domicilio\\.vialidadReferenciaSecundaria\\.clave').val(objDomicilio.vialidadReferenciaSecundaria.clave);
		$('#domicilio\\.vialidadReferenciaSecundaria\\.nombre').val(objDomicilio.vialidadReferenciaSecundaria.nombre);
		if(objDomicilio.vialidadReferenciaSecundaria.tipoVialidad){
			$('#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.clave').val(objDomicilio.vialidadReferenciaSecundaria.tipoVialidad.clave);
			$('#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.descripcion').val(objDomicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion);
		}
		$('#refSecundaria').val(objDomicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion+" "+objDomicilio.vialidadReferenciaSecundaria.nombre);
	}else{
		$('#refSecundaria').val("");
		$('#domicilio\\.vialidadReferenciaSecundaria\\.clave').val("");
		$('#domicilio\\.vialidadReferenciaSecundaria\\.nombre').val("");
		$('#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.clave').val("");
		$('#domicilio\\.vialidadReferenciaSecundaria\\.tipoVialidad\\.descripcion').val("");
		$('#refSecundaria').val("");
		
	}
	
	if(objDomicilio.vialidadReferenciaPosterior != undefined){
		$('#domicilio\\.vialidadReferenciaPosterior\\.clave').val(objDomicilio.vialidadReferenciaPosterior.clave);
		$('#domicilio\\.vialidadReferenciaPosterior\\.nombre').val(objDomicilio.vialidadReferenciaPosterior.nombre);
		if(objDomicilio.vialidadReferenciaPosterior.tipoVialidad != null){
			$('#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave').val(objDomicilio.vialidadReferenciaPosterior.tipoVialidad.clave);
			$('#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.descripcion').val(objDomicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion);
		}
		$('#refPosterior').val(objDomicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion+" "+objDomicilio.vialidadReferenciaPosterior.nombre);
	}else{
		$('#refPosterior').val("");
		$('#domicilio\\.vialidadReferenciaPosterior\\.clave').val("");
		$('#domicilio\\.vialidadReferenciaPosterior\\.nombre').val("");
		$('#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.clave').val("");
		$('#domicilio\\.vialidadReferenciaPosterior\\.tipoVialidad\\.descripcion').val("");
		$('#refPosterior').val("");
	}
	
	$("#domicilio\\.calle").val(objDomicilio.calle);
	$("#domicilio\\.tipoBusquedaVialidad").val(objDomicilio.tipoBusquedaVialidad);
	
	if(objDomicilio.domicilioCarretera != undefined) {
		$("#domicilio\\.domicilioCarretera\\.terminoGeneral\\.descripcion").val(objDomicilio.domicilioCarretera.terminoGeneral.descripcion);
		$("#domicilio\\.domicilioCarretera\\.terminoGeneral\\.clave").val(objDomicilio.domicilioCarretera.terminoGeneral.clave);
		$("#domicilio\\.domicilioCarretera\\.derechoTransito\\.descripcion").val(objDomicilio.domicilioCarretera.derechoTransito.descripcion);
		$("#domicilio\\.domicilioCarretera\\.derechoTransito\\.clave").val(objDomicilio.domicilioCarretera.derechoTransito.clave);
		$("#domicilio\\.domicilioCarretera\\.origen").val(objDomicilio.domicilioCarretera.origen);
		$("#domicilio\\.domicilioCarretera\\.destino").val(objDomicilio.domicilioCarretera.destino);
		$("#domicilio\\.domicilioCarretera\\.administracion\\.descripcion").val(objDomicilio.domicilioCarretera.administracion.descripcion);
		$("#domicilio\\.domicilioCarretera\\.administracion\\.clave").val(objDomicilio.domicilioCarretera.administracion.clave);
		$("#domicilio\\.domicilioCarretera\\.cadenamiento").val(objDomicilio.domicilioCarretera.cadenamiento);
		$("#domicilio\\.domicilioCarretera\\.codigoCarretera").val(objDomicilio.domicilioCarretera.codigoCarretera);
	} else{
		$("#domicilio\\.domicilioCarretera\\.terminoGeneral\\.descripcion").val('');
		$("#domicilio\\.domicilioCarretera\\.terminoGeneral\\.clave").val('');
		$("#domicilio\\.domicilioCarretera\\.derechoTransito\\.descripcion").val('');
		$("#domicilio\\.domicilioCarretera\\.derechoTransito\\.clave").val('');
		$("#domicilio\\.domicilioCarretera\\.origen").val('');
		$("#domicilio\\.domicilioCarretera\\.destino").val('');
		$("#domicilio\\.domicilioCarretera\\.administracion\\.descripcion").val('');
		$("#domicilio\\.domicilioCarretera\\.administracion\\.clave").val('');
		$("#domicilio\\.domicilioCarretera\\.cadenamiento").val('');
		$("#domicilio\\.domicilioCarretera\\.codigoCarretera").val('');
	}
	
	if(objDomicilio.domicilioCamino != undefined) {
		$("#domicilio\\.domicilioCamino\\.terminoGeneral\\.descripcion").val(objDomicilio.domicilioCamino.terminoGeneral.descripcion);
		$("#domicilio\\.domicilioCamino\\.terminoGeneral\\.clave").val(objDomicilio.domicilioCamino.terminoGeneral.clave);
		$("#domicilio\\.domicilioCamino\\.margen\\.descripcion").val(objDomicilio.domicilioCamino.margen.descripcion);
		$("#domicilio\\.domicilioCamino\\.margen\\.clave").val(objDomicilio.domicilioCamino.margen.clave);
		$("#domicilio\\.domicilioCamino\\.origen").val(objDomicilio.domicilioCamino.origen);
		$("#domicilio\\.domicilioCamino\\.destino").val(objDomicilio.domicilioCamino.destino);
		$("#domicilio\\.domicilioCamino\\.cadenamiento").val(objDomicilio.domicilioCamino.cadenamiento);
	} else {
		$("#domicilio\\.domicilioCamino\\.terminoGeneral\\.descripcion").val('');
		$("#domicilio\\.domicilioCamino\\.terminoGeneral\\.clave").val('');
		$("#domicilio\\.domicilioCamino\\.margen\\.descripcion").val('');
		$("#domicilio\\.domicilioCamino\\.margen\\.clave").val('');
		$("#domicilio\\.domicilioCamino\\.origen").val('');
		$("#domicilio\\.domicilioCamino\\.destino").val('');
		$("#domicilio\\.domicilioCamino\\.cadenamiento").val('');
	}
	
	if(objDomicilio.codigoPostal != null){
		$('#codigoPostal').val(objDomicilio.codigoPostal.codigoPostal);
		$('#domicilio\\.codigoPostal\\.codigoPostal').val(objDomicilio.codigoPostal.codigoPostal);
	}
	}
}
function cancelar(){
	$("#msg15").dialog({		
		modal: true,
	      buttons : {
	        "Si" : function() {	        	
	        	location.href = "" + context_path + "/inicio/grupoFamiliar";
	        },
	        "No" : function() {
	          $(this).dialog("close");
	        }
	      }
	  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
}

function valEmail(valor){
    re=/^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,3})$/;
    if(!re.exec(valor))    {
        return false;
    }else{
        return true;
    }
}

function comprobarSiBisisesto(anio){
	if ( ( anio % 100 != 0) && ((anio % 4 == 0) || (anio % 400 == 0))) {
	    return true;
	    }
	else {
	    return false;
	    }
} 

function esFechaValida(){
	var fecha = $("#tramiteRegistro\\.fisica\\.fechaNacimientoFormateada").val();	
	var hoy = new Date();
	
    if (fecha != undefined && fecha != "" ){
        if (!/^\d{2}\/\d{2}\/\d{4}$/.test(fecha)){        	  		    	
            return false;
        }
       var dia  =  parseInt(fecha.substring(0,2),10);
       var mes  =  parseInt(fecha.substring(3,5),10);
       var anio =  parseInt(fecha.substring(6),10);              
       
       var diaH = hoy.getDate();
       var mesH = hoy.getMonth()+1;
       var anioH = hoy.getFullYear();
       
       if(anio > anioH){
    	   return false;
       }else if(anio == anioH && mes > mesH){
    	   return false;
       }else if(anio == anioH && mes == mesH && dia > diaH){
    	   return false;
       }
    	   
    switch(mes){
        case 1:
        case 3:
        case 5:
        case 7:
        case 8: 
        case 10:
        case 12:
            numDias=31;
            break;
        case 4: case 6: case 9: case 11:
            numDias=30;
            break;
        case 2:
            if (comprobarSiBisisesto(anio)){ numDias=29; }else{ numDias=28;}
            break;
        default:        	
            return false;
    }
 
        if (dia>numDias || dia==0){        	
            return false;
        }
                
        
        return true;
    }
} 


function validar(){
	var correcto = true;	
	var proc = $('#proceso').val();
	var idPersona = $('#tramiteRegistro\\.fisica\\.idPersona').val();		
	
	if (document.getElementById('tramiteRegistro.parentesco.idParentesco').selectedIndex==0 && ($('#conAsegurado').val() == '0' || $('#conAsegurado').val() =='1')){
		$("#msgParentesco").dialog({
			modal: true,
		      buttons : {
		        "Aceptar" : function() {
		        	$(this).dialog("close");
		        }
		      }
		  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();			
		correcto = false;
		return 0;
	}
//	if($('#tramiteRegistro\\.parentesco\\.idParentesco').val() != "2"){
//		if (document.getElementById('tramiteRegistro.razonRegistro.idRazonRegistro').selectedIndex==0  && ($('#conAsegurado').val() == '0' || $('#conAsegurado').val() =='1')){
//			$("#msgRazonRegistro").dialog({	
//				modal: true,
//			      buttons : {
//			        "Aceptar" : function() {
//			        	$(this).dialog("close");
//			        }
//			      }
//			  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
//			correcto = false;
//			return 0;
//		}
//	}
	
	if (document.getElementById('miNombre').value.length == 0){
		$("#msgNombre").dialog({	
			modal: true,
		      buttons : {
		        "Aceptar" : function() {
		        	$(this).dialog("close");
		        }
		      }
		  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
		correcto = false;		
		return 0;
	}
	
	var primerApellido = $.trim($('#miPrimerApellido').val()).length == 0 ? '' : $('#miPrimerApellido').val();
	
	$("#miPrimerApellido").val(primerApellido);
	$("#tramiteRegistro\\.fisica\\.primerApellido").val(primerApellido);
	
	var longitudPrimerApellido = $.trim($('#miPrimerApellido').val()).length;
	
	if ( longitudPrimerApellido == 0){
		$("#msgPrimerApellido").html("Debe capturar el primer apellido");
		$("#msgPrimerApellido").dialog({	
			modal: true,
		      buttons : {
		        "Aceptar" : function() {
		        	$(this).dialog("close");
		        }
		      }
		  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
		correcto = false;
		return 0;
	} else {
		if(longitudPrimerApellido < 2) {
			$("#msgPrimerApellido").html("El primer apellido debe contener al menos dos caracteres");
			$("#msgPrimerApellido").dialog({	
				modal: true,
			      buttons : {
			        "Aceptar" : function() {
			        	$(this).dialog("close");
			        }
			      }
			  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
			correcto = false;
			return 0;
		}
	}
	
	var segundoApellido = $.trim($('#miSegundoApellido').val()).length == 0 ? '' : $('#miSegundoApellido').val();
	
	$("#miSegundoApellido").val(segundoApellido);
	$("#tramiteRegistro\\.fisica\\.segundoApellido").val(segundoApellido);
	
	var longitudSegundoApellido = $.trim($('#miSegundoApellido').val()).length;
	if (longitudSegundoApellido > 0){
		if(longitudSegundoApellido < 2) {
			$("#msgSegundoApellido").html("El segundo apellido debe contener al menos dos caracteres");
			$("#msgSegundoApellido").dialog({	
				modal: true,
			      buttons : {
			        "Aceptar" : function() {
			        	$(this).dialog("close");
			        }
			      }
			  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
			correcto = false;
			return 0;
		}
	}
	
	if(!esFechaValida()){
		$("#msgFechaNacimiento").dialog({	
			modal: true,
		      buttons : {
		        "Aceptar" : function() {
		        	$(this).dialog("close");
		        }
		      }
		  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
		correcto = false;
		return 0;
	}
	
	if (document.getElementById('lugarNacimiento').selectedIndex==0 && ($('#conAsegurado').val() == '0' || $('#conAsegurado').val() == '1') && (idPersona == null || idPersona == '')){
		$("#msgLugarNacimiento").dialog({
			modal: true,
		      buttons : {
		        "Aceptar" : function() {
		        	$(this).dialog("close");
		        }
		      }
		  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
		correcto = false;
		return 0;
	}

	
	
	if (document.getElementById('sexoR').selectedIndex==0 && ($('#conAsegurado').val() == '0' || $('#conAsegurado').val() == '1')){
		$("#msgSexo").dialog({
			 modal: true,
		      buttons : {		    
		        "Aceptar" : function() {
		        	$(this).dialog("close");
		        }
		      }
		  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
		correcto = false;
		return 0;
	}
	
	if (document.getElementById('tramiteRegistro.fisica.estadoCivil.idEstadoCivil').selectedIndex==0 && 
			($('#tramiteRegistro\\.parentesco\\.idParentesco').val() == "1" || $('#tramiteRegistro\\.parentesco\\.idParentesco').val() == "5"
				|| $('#tramiteRegistro\\.parentesco\\.idParentesco').val() == "6")){
		$("#msgEstadoCivil").dialog({		
			modal: true,
		      buttons : {
		        "Aceptar" : function() {
		        	$(this).dialog("close"); 
		        }
		      }
		  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();	
		correcto = false;
		return 0;
	}
	
	if(correcto){

		var valor = document.getElementById('tramiteRegistro.parentesco.idParentesco').value;
		
		// ---------------------------------------------------------------------------
		// Se validan otros campos en medios de contacto
		// ---------------------------------------------------------------------------
		if( $('#frmRegDerechohabiente').valid() ){
			
			if(((valor == 5 || valor == 6) && proc == "0") || $('#modificarDom').is(":checked")){
				DomicilioCtrl.localizar();		
			}else{			
				$.blockUI();
				$('#frmRegDerechohabiente').submit();	
			}
		}
		
	}
}

function errorSinRespuestaRenapo(par,proce) {
	var mensajeError = '<div class="ui-widget">' +
	'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
	'<strong>No fue posible validar a la persona en RENAPO por lo cual sera calificado por el IMSS</strong></p></div></div>';

	$razonRechazo = $('<div></div');
	$razonRechazo.html(mensajeError);
	$razonRechazo.dialog({
		autoOpen : false,
		title: '',
		show: "blind",
		hide: "explode",
		resizable: false,
		modal: true,
		width: 500,
		buttons: {
			"Cerrar": function() {
				cierraDialogo($(this));
				if((par == 5 || par == 6) && proce == "0"){
					DomicilioCtrl.localizar();		
				}else{			
					$('#frmRegDerechohabiente').submit();	
				}
			}
		}
	}
	).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$razonRechazo.dialog('open')
}	    

function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}