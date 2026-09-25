'use strict'
/**
 * 
 */
;(function($, window) {
	var nombrePlugin = 'plugin_busquedaRegistrosPatronales';
	
	$.fn.busquedaRps = function(options) {
		//obtenemos el tipo de parametro que se paso
		var tipoOptions = $.type(options);
		//en caso de ser un string, se indica que se llamara un metodo del plugin
		if(tipoOptions == "string") {
			//obtenemos el plugin almacenado en el elemento actual
			var pluginBusqueda = $(this).data(nombrePlugin);
			//verificamos si el elemento contiene realmente el plugin
			if(pluginBusqueda) {
				//si la operacion es get obtenemos los patrones localizados
				if(options == "get") {
					return pluginBusqueda.getPatrones();
				} else if(options == "setClase") {
					var clase = Array.prototype.slice.call(arguments, 1)[0];
					return pluginBusqueda.setOption("clasePrincipal",clase);
				} 
				//una vez que se termina la operacion retornamos
				return;
			} else {
				//si no se ha inicializado el plugin mostramos un error en pantalla
				return;
			}
		}
		
		//em caso de que el parametro options no sea una operacion se inicializara el plugin
		return this.each(function() {
			//se hace referencia al elemento al que se le aplica el plugin
			var $elementoActual = $(this);
			//si el elemento ya fue inicializado con el plugin retornamos
			if($elementoActual.data(nombrePlugin)) return;
			//si el plugin no ha sido creado para el elemento actual, crearemos una instancia del objeto
			var instanciaComponenteBusqueda = new ComponenteBusquedaRegistrosPatronales(options,this);
			//ya creada la instancia la guardaremos dentro de nuestro objeto
			$elementoActual.data(nombrePlugin,instanciaComponenteBusqueda);
			//iniciamos el plugin
			$elementoActual.data(nombrePlugin).iniciar();
		});
	}
	
	$.fn.busquedaRps.mensajes = {
		patronNoElegible:  "No es posible utilizar este Registro Patronal",
		sujetoYaFusionado : "Ya existe la fusi&oacute;n entre el Registro Patronal capturado y el que realiza el tr&aacute;mite",
		sujetoFusionadoConOtroRP: "El Registro Patronal capturado ya tiene un tr&aacute;mite de fusi&oacute;n con otro Registro Patronal",
		mismaEmpresa:"El registro patronal ingresado no corresponde al RFC del registro patronal que est&aacute; realizando el tr&aacute;mite. Favor de verificar e intentar nuevamente",
		mismoMunicipioIMSS: "El registro patronal de la empresa sustituida y sustituta, deben ser del mismo municipio"
	};
	
	$.fn.busquedaRps.defaults = {
		busquedaMultiple: true,
		contenedor : "",
		validarFusion: false,
		idSujetoObligado: null,
		rfcPatronTramite: null,
		patronesNoElegibles: null,
		patronesDefault: null,
		funcionPatronEncontrado: null, 
		funcionPatronEliminado: null,
		maximo: 1300,
		clasePrincipal: null,
		validarClases: false,
		idMpioIMSSPatronTr: null,
		onInit: null
	}
	
	var ComponenteBusquedaRegistrosPatronales = function(opcionesInicio, elementoPantalla) {
		var options = $.extend({},$.fn.busquedaRps.defaults,opcionesInicio),
		_self = this,//una copia del objeto actual
		RPS_UBICADOS=[];
		//guardamos el id del elemento donde se esta mostranto el componente
		if(elementoPantalla) {
			options.contenedor = elementoPantalla.id;
		}
		//funcion para inicializar el componente
		this.iniciar = function() {
			$.blockUI();
			
			$.ajax($.extend({},_self.DEFAULTS_PET,{
		    	url : _self.URL_COMPONENTE,
		    	async: false,
		        dataType:'html',
		        success: function (html) {
		        	//console.log("seteare los datos en el div" + options.contenedor);
		        	$("#"+options.contenedor).html(html);
		        	//una vez que acabamos de crear el plugin 
		        	setEventos();
		        }
		    }));
		}
		
		this.getPatrones = function() {
			return RPS_UBICADOS;
		}
		
		
		
		/****************************************************************************************
		 *  Funciones publicas que se podran llamar cuando se obtenga la referencia al objeto  **
		 ****************************************************************************************/
		this.setOption = function(option, valor) {
			//console.log("se modificara la opcion " + option + " con el valor " + valor);
			options[option] = valor;
		}
		
		/*************************************************************************************
		 *****
		 *****	Inician las funciones privadas del objeto
		 *****
		 *************************************************************************************/
		
		/**
		 * Funcion para establecer los enventos del formulario de busqueda
		 */
		var setEventos = function() {
			console.log("Entro a establecer los eventos");
			$("#"+options.contenedor).find(_self.ID_BTN_AGREGAR).on("click", function(){
				buscarPatron();
			});
			
			if(options.patronesDefault) {
				setearPatronesDefault();
			}
			
			if(options.onInit != null && typeof options.onInit == 'function') {
				options.onInit(getClasesIguales());
			}
			
			$(".tablaPatron").live('click',eliminarPatron);
		};
		
		/**
		 * Funcion que busca al patron el con nrp capturado en el formulario
		 */
		var buscarPatron = function() {
			let $contenedor = $("#"+options.contenedor),
			$campo_rp = $contenedor.find(_self.ID_CAMPO_RP),
			rp = $campo_rp.val(),
			$divErrores = $(_self.DIV_ERROR), 
			mensajeError = null,
			idPatronSO = (options.validarFusion && options.idSujetoObligado) ? options.idSujetoObligado : null,
			rfcPatronSO = options.rfcPatronTramite,
			mpioPatronSO = options.idMpioIMSSPatronTr; //ponemos aqui el municipio del patron seleccionado en el tramite
			$divErrores.hide();
			
			if(rp=="" || rp.length < 8) {
				mensajeError = "Es necesario capturar al menos 8 posiciones del Registro Patronal";
			}
			
			if(mensajeError == null) {
				if(RPS_UBICADOS != null && RPS_UBICADOS.length >= options.maximo) {
					mensajeError = "El n&uacute;mero m&aacute;ximo de registros patronales que puedes capturar es " + options.maximo;
				}else if(!isRPPermitido(rp)) {
					mensajeError = $.fn.busquedaRps.mensajes.patronNoElegible
				} else if(existePatron(rp)) {
					mensajeError = "Ya capturaste el Registro Patronal <strong>" + rp + "</strong>";
				}
				
				if(codigoTramite == 176){
					if(RPS_UBICADOS != null && RPS_UBICADOS.length >= 1) {
						mensajeError = "Para este tr&aacute;mite solo puede capturar un registro patronal con domicilio anterior";
					}
				}
			}
			
			if(mensajeError != null){
				$divErrores.html(mensajeError).show();
				$campo_rp.val("");
				return;
			}
			
			$.ajax($.extend({},_self.DEFAULTS_PET,{
				url: _self.URL_COMPONENTE+"buscar",
				data:  JSON.stringify({"cveIdSujetoObligado": idPatronSO,"numeroRegistroPatronal" : rp, "fisica": {"rfc":rfcPatronSO},
					"municipioIMSS": {"idMunicipio": mpioPatronSO}
				}),
				success: function(data) {
					
					let patronUbicado = data.sujetoObligado,
					isFusionado = data.isFusionado,
					isFusionadoConOtro = data.isFusionadoConOtro,
					isMismaEmpresa = data.isMismaEmpresa,
					isMismoMunicipio = data.isMismoMunicipio;
					
					let rfcPatronDomAnt = null;
					
					if(patronUbicado) {

						//Se omite a solicitud de usuario normativo
//						if(isMismoMunicipio){
//								console.log("::: Se aplica regla de mismo municipio");
//								mensajeError = $.fn.busquedaRps.mensajes.mismoMunicipioIMSS;
//						}

						//Se omite a solicitud de usuario normativo
//						if(mensajeError == null && rfcPatronSO && data.isMismaEmpresa) {
//								console.log("::: Se aplica regla de misma empresa");
//								mensajeError = $.fn.busquedaRps.mensajes.mismaEmpresa;
//						}

						//Se omite a solicitud de usuario normativo
//						if(mensajeError == null && (idPatronSO && (isFusionado || isFusionadoConOtro))){
//							// esta validacion no aplica para sustitucion por subcontratacion
//							if(codigoTramite != 175){
//								mensajeError = isFusionado ? $.fn.busquedaRps.mensajes.sujetoYaFusionado : $.fn.busquedaRps.mensajes.sujetoFusionadoConOtroRP;
//							}else{
//								console.log("::: El patron ya fue sustituido, pero no se aplica regla por tramite: " + codigoTramite);
//							}
//						}
										
						if(mensajeError == null) {
							console.log("::: Codigo tramite: " + codigoTramite);
							console.log("::: Situacion baja: " + patronUbicado.descSituacionBaja);
							if(codigoTramite == 176){ // si es tramite de Aviso de Cambio de Dom Dif Mpio
								if(patronUbicado.fisica){
									rfcPatronDomAnt = patronUbicado.fisica.rfc;
								}else if(patronUbicado.moral){
									rfcPatronDomAnt = patronUbicado.moral.rfc;
								}							
								console.log("::: rfcPatronTramite: " + rfcPatronSO + ", rfcPatronDomAnt: " + rfcPatronDomAnt);
								if(rfcPatronSO != rfcPatronDomAnt){
									console.log("::: El RFC de ambos patrones es diferente, no se puede agregar patron");
									mensajeError = $.fn.busquedaRps.mensajes.mismaEmpresa;
									console.log("::: Eliminamos de la sesion el patron agregado");
									$.ajax($.extend({},_self.DEFAULTS_PET,{
										url: _self.URL_COMPONENTE+"delete",
										data:  JSON.stringify({"cveIdSujetoObligado" : patronUbicado.cveIdSujetoObligado}),
										success: function(data) {
											$.each(RPS_UBICADOS, function(index, value){
												if(value.cveIdSujetoObligado==idPatronSO) {
													console.log("Se encontro el patron en el indice " + index);
													indicePatron = index;
													return;
												}
											});
																						
											console.log("existen " + RPS_UBICADOS.length + " patrones y son " + RPS_UBICADOS )
											if(options.funcionPatronEliminado != null && typeof options.funcionPatronEliminado == 'function') {
												options.funcionPatronEliminado(getClasesIguales());
											}
											//regresamos el valor de la prima sugerida
											console.log("::: Se deshabilita editar prima por el patron, y se pone en blanco el campo para que vuelva a calcular la prima");
											$("#primaSRTFusionSust").val("");
											$("#primaSRTFusionSust").prop('disabled', true);
											document.getElementById("modPrimaPatronFS").checked  = false;											
										}
									}));
								}
								//Para el tramite Cambio de domicilio de municipio se verifica si el RP por sustituir esta dado de baja
								if(mensajeError == null){
									if(patronUbicado.descSituacionBaja != null){
										console.log("::: El patron esta en baja se valida la fecha del tramite: " + patronUbicado.fechaBaja);
										//Validamos la fecha de la baja															
										$.ajax($.extend({},_self.DEFAULTS_PET,{
											url: _self.URL_COMPONENTE+"validaFechaBaja",
											data:  JSON.stringify({"fechaBaja" : patronUbicado.fechaBaja}),
											success: function(data) {
												console.log("::: Resultado de evaluacion de fecha de baja: " + data.resultado);
												let res = data.resultado;
												if(!res){
													mensajeError = "Estimado patr&oacute;n, la baja del registro patronal del domicilio anterior no debe superar los 6 meses, por lo que el registro patronal del domicilio anterior que pretenden utilizar en el presente tr&aacute;mite no se apega a lo establecido en el tr&aacute;mite Aviso de modificaci&oacute;n por cambio de domicilio ante el IMSS con Homoclave IMSS-02-002 Modalidad B.";
													$divErrores.html(mensajeError).show();
													quitarPatron(patronUbicado.cveIdSujetoObligado, idPatronSO);
												}else{ //el patron paso la validacion de su estado de baja
													console.log("::: El patron paso la validacion de su estado de baja");
													//validamos si ya selecciono la clasificacion
													if (!(isEmpty($("#fraccion").val()) || isEmpty($("#grupo").val())|| isEmpty($("#divison").val()))) {
														console.log("::: Ya se tiene la clasificacion se calcula la prima");
														calculaPrima176();
													}													
												}
											},
											error: function(error) {
												mensajeError = "Error al consultar patr&oacute; con domicilio anterior";
											}
										}));										
									}else{
										console.log("::: El patron no esta en baja, descSituacionBaja: " + patronUbicado.descSituacionBaja);
									}
								}
							}//si es 176																											
						}						
						
						//despues de agregar patron se quita el valor del campo de prima, y se deshabilita/desmarca
						//para obligar a que se calcule de nuevo la prima y se apliquen reglas
						//ya que el agregar un nuevio patron puede influir en el calculo y reglas
						console.log("::: Despues de agregar patron, se limpia el campo de prima para obligar al calculo de la prima");
						$("#primaSRTFusionSust").val("");
						$("#primaSRTFusionSust").prop('disabled', true);
						document.getElementById("modPrimaPatronFS").checked  = false;						
					} else {
						mensajeError ="No se encontr&oacute; el Registro Patronal " + rp;
					}
					
					$campo_rp.val("");
					
					if(mensajeError != null) {
						$divErrores.html(mensajeError).show();
					}else{
						RPS_UBICADOS.push(patronUbicado);
						crearTabla(patronUbicado);
						//Si el componente no es de busqueda multiple ocultamos el formulario de busqueda
						if(!options.busquedaMultiple || RPS_UBICADOS.length >= options.maximo) {
							$contenedor.find(_self.ID_SECCION_BUSQUEDA).hide()
						}
						
						if(options.funcionPatronEncontrado != null && typeof options.funcionPatronEncontrado == 'function') {
							options.funcionPatronEncontrado(getClasesIguales());
						}
					}
				}
			}));
		};
		
function quitarPatron(cveSO, cvePatronSO){
	console.log("::: Eliminamos de la sesion el patron agregado: " + cveSO);
	$.ajax($.extend({},_self.DEFAULTS_PET,{
		url: _self.URL_COMPONENTE+"delete",
		data:  JSON.stringify({"cveIdSujetoObligado" : cveSO}),
		success: function(data) {
			console.log("::: Patron eliminado de la sesion, reset de componentes");
			
/*			var indicePatron = null;
			$.each(RPS_UBICADOS, function(index, value){
				if(value.cveIdSujetoObligado==cvePatronSO) {
					console.log("Se encontro el patron en el indice " + index);
					indicePatron = index;
					return;
				}
			});														
			if(indicePatron!= null) {
				RPS_UBICADOS.splice(indicePatron,1);
			}
*/
			//Se elimina la tabla y se limpia el arreglo de patrones
			$('#'+cveSO).remove(); 
			RPS_UBICADOS=[];

			//regresamos el valor de la prima sugerida
			console.log("::: Se deshabilita editar prima por el patron, y se pone en blanco el campo para que vuelva a calcular la prima");
			$("#primaSRTFusionSust").val("");
			$("#primaSRTFusionSust").prop('disabled', true);
			document.getElementById("modPrimaPatronFS").checked  = false;	
		}
	}));
}
		
function construirDialogo(mensaje, height, width){
	if (height==undefined)
		height=150
	if (width==undefined)
		width=400

	$("#textoMensaje").html(mensaje);
	var dialogo = $("#dialogoMensajes").dialog({
		autoOpen : false,
		resizable : false,
		modal : true,
		height : height,
		width : width,
		title : "Aviso",
		buttons : {
			"Aceptar" : function() {
				$(this).dialog("close");
			}
		}
	});
	dialogo.dialog('open');
}
		
		var getClasesIguales = function() {
			let clasePrincipal = options.clasePrincipal,
			clasesIguales = true;
			console.log("la clasePrincipal es " + clasePrincipal);
			
			if(RPS_UBICADOS.length) {
				$.each(RPS_UBICADOS, function(index, value){
					let patron = value;
					let clasificacion = patron.clasificacion;
					let fraccion = clasificacion.fraccion;
					let grupo = fraccion.grupo;
					let division = grupo.division;
					
					let claseCompleta ="" + division.numDivision + grupo.numGrupo + fraccion.numFraccion;
					
					console.log("La clase del patron a validar es " + claseCompleta);
					
					if(clasePrincipal != claseCompleta) {
						clasesIguales = false;
						return;
					}
				});
			} else {
				clasesIguales = false;
			}
			
			return clasesIguales;
		}

		var existePatron = function(rp) {
			return existeEnArray(rp, RPS_UBICADOS, "numeroRegistroPatronal");
		}
		
		var isRPPermitido = function(rp) {
			return options.patronesNoElegibles ? !existeEnArray(rp, options.patronesNoElegibles, null) : true;
		}
		
		var existeEnArray = function(rp, array, propiedad) {
			var registro = rp.substring(0,8),
			existePatron = false;
			$.each(array, function(index, value){
				var valor = (propiedad != null ? value[propiedad] : value).substring(0,8);
				if(valor == registro) {
					existePatron = true;
					return;
				}
			});
				
			return existePatron;
		}
		
		var setearPatronesDefault = function() {
			var $contenedor = $("#"+options.contenedor);
			RPS_UBICADOS = options.patronesDefault;
			if(RPS_UBICADOS && RPS_UBICADOS.length) {
				if(!options.busquedaMultiple) {
					$contenedor.find(_self.ID_SECCION_BUSQUEDA).hide()
				}
			}
			
			$.ajax($.extend({},_self.DEFAULTS_PET,{
				url: _self.URL_COMPONENTE+"setPatrones",
				data:  JSON.stringify({"sujetosObligados" : RPS_UBICADOS}),
				success: function(data) {
					if(RPS_UBICADOS) {
						RPS_UBICADOS.forEach(function(patron){
							crearTabla(patron);
						});
					}
				}
			}));
		}
		
		var eliminarPatron = function() {
			var $contenedor = $("#"+options.contenedor),
			$tablaEliminar = $(this).closest("table"),
			idPatronSO = $tablaEliminar.attr("id"),
			indicePatron = null;
			
			$.ajax($.extend({},_self.DEFAULTS_PET,{
				url: _self.URL_COMPONENTE+"delete",
				data:  JSON.stringify({"cveIdSujetoObligado" : idPatronSO}),
				success: function(data) {
					$.each(RPS_UBICADOS, function(index, value){
						if(value.cveIdSujetoObligado==idPatronSO) {
							console.log("Se encontro el patron en el indice " + index);
							indicePatron = index;
							return;
						}
					});
					
					if(indicePatron!= null) {
						RPS_UBICADOS.splice(indicePatron,1);
					}
					
					$tablaEliminar.remove();
					
					if(!options.busquedaMultiple) {
						$contenedor.find(_self.ID_SECCION_BUSQUEDA).show()
					} else if(RPS_UBICADOS.length < options.maximo && !$contenedor.find(_self.ID_SECCION_BUSQUEDA).is(":visible")) {
						$contenedor.find(_self.ID_SECCION_BUSQUEDA).show()
					}
					
					console.log("existen " + RPS_UBICADOS.length + " patrones y son " + RPS_UBICADOS )
					if(options.funcionPatronEliminado != null && typeof options.funcionPatronEliminado == 'function') {
						options.funcionPatronEliminado(getClasesIguales());
					}
					
					//regresamos el valor de la prima sugerida
					console.log("::: Se deshabilita editar prima por el patron, y se pone en blanco el campo para que vuelva a calcular la prima");
					$("#primaSRTFusionSust").val("");
					$("#primaSRTFusionSust").prop('disabled', true);
					document.getElementById("modPrimaPatronFS").checked  = false;											
				}
			}));
			
		}
		
		var crearTabla = function(sujetoObligado) {
			
			var $div_rp_ubicados = $("#"+options.contenedor).find("#patronesUbicados"),
			fisica = sujetoObligado.fisica != null,
			etiquetaNombre= fisica ? "Nombre" : "Denominaci&oacute;n o raz&oacute;n social",
			$tablaPatron = $("<table class=\"table table-striped table-bordered\" id=\""+sujetoObligado.cveIdSujetoObligado+"\"></table>"),
			$filaTitulos1 = $("<tr><td style=\"width: 15%\"><strong>Registro Patronal</strong></td>" +
					"<td  style=\"width: 25%\"><strong>"+etiquetaNombre+"</strong></td>" +
					"<td  style=\"width: "+(fisica ? "23": "46")+"%\"><strong>RFC</strong></td>" + 
					(fisica ? "<td  style=\"width: 23%\"><strong>CURP</strong></td>" : "")+
					"<td colspan=\"2\"><strong>Acciones</strong></td></tr>"),
			$filaDescripcion1 = $("<tr></tr>"),
			$celdaRegistroPatronal = $("<td>"+sujetoObligado.numeroRegistroPatronal+"</td>"),
			$celdaNombre =  $("<td>"+(fisica ? sujetoObligado.fisica.nombreCompleto : sujetoObligado.moral.razonSocial)+"</td>"),
			$celdaRFC =  $("<td>"+(fisica ? sujetoObligado.fisica.rfc :  sujetoObligado.moral.rfc)+"</td>"),
			$celdaAcciones = $("<td colspan=\"2\"><button type=\"button\" class=\"btn btn-danger btn-sm tablaPatron\">Eliminar patr&oacute;n</button></td>"),
			$filaTitulos2 = $("<tr><td><strong>Divisi&oacute;n</strong></td><td><strong>Grupo</strong></td>" +
					"<td "+ (fisica ? "colspan=\"2\"" : "")+"><strong>Fracci&oacute;n</strong></td>" +
					"<td><strong>Clase</strong></td><td><strong>Prima SRT</strong></td></tr>"),
			$filaDescripcion2 = $("<tr></tr>"),
			$celdaDivision=$("<td>"+sujetoObligado.clasificacion.fraccion.grupo.division.descripcion+"</td>"),
			$celdaGrupo = $("<td>"+sujetoObligado.clasificacion.fraccion.grupo.descripcion+"</td>"),
			$celdafraccion = $("<td "+ (fisica ? "colspan=\"2\"" : "")+">"+sujetoObligado.clasificacion.fraccion.descripcion+"</td>"),
			$celdaClase = $("<td>"+sujetoObligado.clasificacion.fraccion.clase.descripcion+"</td>"),
			$celdaPrima = $("<td>"+sujetoObligado.clasificacion.primaSRTActual+"</td>");
			
			$filaDescripcion1.append($celdaRegistroPatronal).append($celdaNombre).append($celdaRFC);
			if(fisica) {
				$filaDescripcion1.append("<td>"+sujetoObligado.fisica.curp+"</td>");
				
				
			}
			$filaDescripcion1.append($celdaAcciones);
			$filaDescripcion2.append($celdaDivision).append($celdaGrupo).append($celdafraccion).append($celdaClase).append($celdaPrima);
			$tablaPatron.append($filaTitulos1).append($filaDescripcion1).append($filaTitulos2).append($filaDescripcion2);
			
			$div_rp_ubicados.append($tablaPatron);
			$div_rp_ubicados.append("<br>");
		};
	}
	
	ComponenteBusquedaRegistrosPatronales.prototype = {
		URL_COMPONENTE:  '/${mvn.web.app.root}/componente/busquedaRP/',
		ID_BTN_AGREGAR: '#agregarPatron',
		ID_CAMPO_RP: '#numeroRegistroPatronalBusqueda',
		ID_SECCION_BUSQUEDA: '#formBusquedaPatrones',
		DIV_ERROR: "#errorBusquedaRp",
		DIV_RP_UBICADOS: "#patronesUbicados",
		DEFAULTS_PET: {
	        type: 'POST',
	        beforeSend : $.blockUI,
	        contentType: 'application/json',
	        dataType: 'JSON',
	        complete: $.unblockUI
		}
	}
	
}(jQuery, window));