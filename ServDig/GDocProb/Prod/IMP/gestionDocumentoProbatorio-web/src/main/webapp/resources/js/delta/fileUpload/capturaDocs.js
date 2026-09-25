/**
 * CEGA IMSS (Instituto Mexicano del Seguro Social) 10/04/2012
 */



	TIPO_DOC_CEDULA_PROFESIONAL = '35' ;
	TIPO_DOC_CARTILLA_MILITAR = '37' ;
	TIPO_DOC_CERTIFICADO_NACIMIENTO = '24' ;
	TIPO_DOC_CONSTANCIA_ESTUDIOS = '19' ;
	TIPO_DOC_PASAPORTE = '38' ;
	TIPO_DOC_CURP = '34' ;
	TIPO_DOC_ACTA_NACIMIENTO = '1' ;
	TIPO_DOC_ACTA_MATRIMONIO = '2' ;
	TIPO_DOC_ACTA_DIVORCIO		='40';
	TIPO_DOC_ACTA_DEFUNCION		='41';
	TIPO_DOC_ACTA_RECONOCIMIENTO='3';
	TIPO_DOC_ACTA_ADOPCION		='4';
	TIPO_DOC_MAT_DICT_DIS		='5';
	
	
	
	TIPO_DOC_CREDENCIAL_ELECTOR = '36' ;
	TIPO_DOC_DICTAMEN_INCAPACITADO = '23' ;
	TIPO_DOC_OBSTETRICO = '20' ;
	TIPO_CERTIFICADO_SIT_CRITICA = '21' ;
	TIPO_DOC_ACUERDO = '17' ;
	TIPO_DOC_LAUDO = '22' ;
	TIPO_DOC_VIGENCIA_TEMPORAL = '18' ;
	TIPO_DOC_PENSION = '42' ;
	TIPO_DOC_ESTADOCUENTABANCARIO='7';

	TIPO_DOC_PAGOPREDIAL='16';
	TIPO_DOC_ADIMSS='46';
	TIPO_DOC_ACTA_PACTO_CIVIL = '65';
	
	TIPO_DOC_ACTA_UNION_CIVIL = '210';
	TIPO_DOC_ACTA_TERMINO_UNION_CIVIL = '211';
	
	// URL para tipos de documento
	URL_CEDULA_PROFESIONAL =      "/documentos/guardarCedulaProfesional";
	URL_CARTILLA_MILITAR =        "/documentos/guardarCartillaMilitar";
	URL_CERTIFICADO_NACIMIENTO =  "/documentos/guardarCertificadoNacimiento";
	URL_CONSTANCIA_ESTUDIOS =     "/documentos/guardarConstanciaEstudios";
	URL_PASAPORTE =               "/documentos/guardarPasaporte";
	URL_CURP =                    "/documentos/guardarCurp";
	URL_ACTA_NACIMIENTO =         "/documentos/guardarActaNacimiento";
	URL_ACTA_COMUN=				  "/documentos/guardarActaComun";	
	URL_CREDENCIAL_ELECTOR =      "/documentos/guardarCredencialElector";
	URL_DICTAMEN_INCAPACITADO =   "/documentos/guardarDictamenIncapacitado";
	URL_OBSTETRICO =              "/documentos/guardarObstetrico";  
	URL_CERTIFICADO_SIT_CRITICA = "/documentos/guardarCertificadoSitCritica";
	URL_ACUERDO =                 "/documentos/guardarAcuerdo"; 
	URL_VIGENCIA_TEMPORAL =       "/documentos/guardarVigenciaTemporal";
	URL_COMPROBANTE_DOMICILIO =   "/documentos/guardarCompDom";
	URL_ACTA_PACTO =   			  "/documentos/guardarActaPacto";
	URL_ADIMSS =   				  "/documentos/guardarAdimss";
	URL_ACTA_UNION_CIVIL =		  "/documentos/guardarActaUnionCivil";
	URL_ACTA_TERMINO_UNION_CIVIL ="/documentos/guardarActaTerminoUnionCivil";
 

	function marcarCamposConErroresYRequired(form,selectorError,selectorPadre) {
		
		$(form).find(""+selectorError).each(function() {
			var existeError = $(this).is(":visible");
			var cssSpan = existeError ? "red" : "black";
			
			var $padre = $(this).parent(""+selectorPadre);
			
			$padre.find(":text").each(function() {
				$('label[for="'+this.name+ '"]').find("span").css("color",cssSpan);
				$('label[for="'+this.id+ '"]').find("span").css("color",cssSpan);
			})
			
			$padre.find("select").each(function() {
				$('label[for="'+this.name+ '"]').find("span").css("color",cssSpan);
				$('label[for="'+this.id+ '"]').find("span").css("color",cssSpan);
			})
			
			$padre.find("textarea").each(function() {
				$('label[for="'+this.name+ '"]').find("span").css("color",cssSpan);
				$('label[for="'+this.id+ '"]').find("span").css("color",cssSpan);
			})
		});
		
	}

function capturaDocs(idDocumento, tipo) {
	$capturaDocs = $('<div id="formulario"></div');
	
	var documento = {"idDocumento" : idDocumento};
	var url = context_path + '/documentos/seleccionFormulario';	
	var url_ajax = '';
	var tipoValidacion = "";
	var validado = true; 
	$capturaDocs.dialog({
		autoOpen : false,
		title: tipo,
		modal: true,
		height: 'auto',
		width: 600,
		resizable: false,
		buttons: {
			"Cerrar": function() {
				cerrarDialogo($(this));
			},
			"Aceptar": function() {
				$('div#formulario form').each(function(index) {
				    	var isDocumentacionValida = $(this).valid();
				    	
				    	if(isDocumentacionValida){
				    		if(idDocumento == TIPO_DOC_CURP) {
								$("#curp").removeAttr("disabled");
							}
				    		var formulario = $(this).serializeObject();
				    		$.postJSON(url_ajax,formulario,function(result) {
				    			$capturaDocs.html(result.modelo);
				    			
				    			$capturaDocs.dialog("option","buttons",{
									"Cerrar": function() {
										cerrarDialogo($(this));
										getLisDoc();
									}
								});
				    			
				    			$capturaDocs.dialog("option","width",350);
				    			$capturaDocs.dialog("option","height",200);

                                $capturaDocs.closest('.ui-dialog').find('.ui-dialog-buttonset').css("text-align","center").css("width","100%");

				    		});			    		
				    	}
				});
				
				
		
			}
		},
		position : {
			my : "top",
			at : "top",
			of : window,
			offset : "0 10"
		},
        open: function(event, ui) {
			var buttonset = $(this).closest('.ui-dialog').find('.ui-dialog-buttonset');
            if($(buttonset).find('button').length == 1){
                $(buttonset).css("text-align","center").css("width","100%");
			}else {
                $(buttonset).css("text-align","").css("width","");
			}
        }
		
	
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	
   
	// Selecciona la URL
	switch (idDocumento) {
		case TIPO_DOC_CEDULA_PROFESIONAL:
			 url_ajax = context_path + URL_CEDULA_PROFESIONAL;		
			 break;
			
		case TIPO_DOC_ACTA_NACIMIENTO:
			url_ajax = context_path + URL_ACTA_NACIMIENTO;
			break;
			
		case TIPO_DOC_ACTA_DIVORCIO:
		case TIPO_DOC_ACTA_DEFUNCION:
		case TIPO_DOC_ACTA_MATRIMONIO:
		case TIPO_DOC_ACTA_RECONOCIMIENTO:
		case TIPO_DOC_ACTA_ADOPCION:
		case TIPO_DOC_MAT_DICT_DIS:
			url_ajax = context_path + URL_ACTA_COMUN;
			break;		
			
		case TIPO_DOC_ACTA_PACTO_CIVIL:
			url_ajax = context_path + URL_ACTA_PACTO;
			break;	
			
		case TIPO_DOC_CARTILLA_MILITAR:
			url_ajax = context_path + URL_CARTILLA_MILITAR;
			break;	
			
		case TIPO_DOC_CERTIFICADO_NACIMIENTO:
			url_ajax = context_path + URL_CERTIFICADO_NACIMIENTO;
			break;	
		case TIPO_DOC_CONSTANCIA_ESTUDIOS:
			url_ajax = context_path + URL_CONSTANCIA_ESTUDIOS;
			break;
		case TIPO_DOC_CREDENCIAL_ELECTOR:
			url_ajax = context_path + URL_CREDENCIAL_ELECTOR;
			break;	
		case TIPO_DOC_CURP:
			url_ajax = context_path + URL_CURP;
			break;	
		case TIPO_DOC_PASAPORTE:
			url_ajax = context_path + URL_PASAPORTE;
			break;
			
		case TIPO_DOC_DICTAMEN_INCAPACITADO:
			url_ajax = context_path + URL_DICTAMEN_INCAPACITADO;
			break;
		case TIPO_DOC_OBSTETRICO:
			url_ajax = context_path + URL_OBSTETRICO;
			break;
		case TIPO_CERTIFICADO_SIT_CRITICA:
			url_ajax = context_path + URL_CERTIFICADO_SIT_CRITICA;
			break;
		case TIPO_DOC_ACUERDO:
			url_ajax = context_path + URL_ACUERDO;
			break;
		case TIPO_DOC_LAUDO:
			url_ajax = context_path + URL_ACUERDO;
			break; 
		case TIPO_DOC_VIGENCIA_TEMPORAL:
		case TIPO_DOC_PENSION:
			url_ajax = context_path + URL_VIGENCIA_TEMPORAL;
			break;	
		case	TIPO_DOC_ESTADOCUENTABANCARIO:
		case 	'8':
		case 	'9':
		case 	'10':
		case 	'11':
		case 	'12':
		case 	'13':
		case 	'14':
		case 	'15':
		case 	TIPO_DOC_PAGOPREDIAL:
			url_ajax = context_path + URL_COMPROBANTE_DOMICILIO;
			break;
		case TIPO_DOC_ADIMSS:
			url_ajax =context_path + URL_ADIMSS;
			break;
		case TIPO_DOC_ACTA_UNION_CIVIL:
			url_ajax =context_path + URL_ACTA_UNION_CIVIL;
			break;
		case TIPO_DOC_ACTA_TERMINO_UNION_CIVIL:
			url_ajax =context_path + URL_ACTA_TERMINO_UNION_CIVIL;
			break;
	}

	$capturaDocs.html('Cargando formulario...');
	$capturaDocs.load(url, {"idDocumento" : idDocumento, "idUmf" : $("#idUmf").val()}, function() {

	});
	$capturaDocs.dialog('open');
	
	
}


function cerrarDialogo($dialogo) {
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}

function validaCurpCorreccion(){
	
	var url = '/gestionDerechohabientes-web' + "/derechohabientesUtil/validaCurpRenapo";
	var fechaArr = $("#fechaNacimiento").val().split('/');
	var aho = fechaArr[2];
	var mes = fechaArr[1];
	var dia = fechaArr[0];
	var fechan= new Date(mes + "/" + dia + "/" + aho + " CST");
	var fisica = {
			'nombre': $("#nombre").val(),
			'primerApellido': $("#primerApellido").val(),
			'segundoApellido': $("#segundoApellido").val(),
			'fechaNacimiento': fechan,
			'curp': $("#curpCap").val(),
			'lugarNacimiento': {
				'clave': $("#lugarNacimiento\\.clave").val()
			},
			'sexo': {
				'idSexo': $("#sexo\\.idSexo").val()
			}
		}
	if($("#curpCap").val().length > 0) {
		$.postJSON(url, fisica, function(data) {
			if(!data.modelo) {
				errorTramite();
				return false;
			} else {
				return true;
			}
		});
	}
}


function validaCurpRegistro(){
	var url = '/gestionDerechohabientes-web' + "/derechohabientesUtil/validaCurpRenapo";
	var fechaArr = fechaNacForm.split('/');
	
	
	if(fechaArr.length > 0){
		var aho = fechaArr[2];
		var mes = fechaArr[1];
		var dia = fechaArr[0];
		var fechan= new Date(mes + "/" + dia + "/" + aho + " CST");
		var fisica = {
				'nombre': nombre,
				'primerApellido': primerApe,
				'segundoApellido': segundoApe,
				'fechaNacimiento': fechan,
				'curp': curp,
				'lugarNacimiento': {
					'clave': idLugarNacimiento
				},
				'sexo': {
					'idSexo': idSexo
				}
			};			
		$.postJSON(url, fisica, function(data) {
			if(!data.modelo) {
				errorTramite();
				return false;
			} else {
				return true;
			}
		});
	}	
}

function errorTramite() {
	var mensajeError = '<div class="ui-widget">' +
	'<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">'+
	'<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>' +
	'<strong>Los datos personales no coinciden con la curp establecida</strong></p></div></div>';

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
