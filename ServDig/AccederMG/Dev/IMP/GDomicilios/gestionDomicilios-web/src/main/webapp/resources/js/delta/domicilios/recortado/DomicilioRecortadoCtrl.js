;(function($,window){
	
	var nombrePlugin = 'plugin_domicilioRecortado';
	
	/**
	 * Declaracion del plugin de domicilio recortado para que pueda usarse mediante $("#elemento").domicilioRecortado();
	 */
	$.fn.domicilioRecortado = function(options) {
		//obtenemos el tipo de parametro que se paso
		var tipoOptions = $.type(options);
		//en caso de ser un string, se indica que se llamara un metodo del plugin
		if(tipoOptions == "string") {
			//obtenemos el plugin almacenado en el elemento actual
			var pluginDom = $(this).data(nombrePlugin);
			//verificamos si el elemento contiene realmente el plugin de domicilio
			if(pluginDom) {
				//si la operacion es get obtenemos el domicilio capturado
				if(options == "get") {
					return pluginDom.getDomicilioCapturado();
				} else if(options == "set") {
					//obtenemos el domicilio que se seteara
					var domicilio = Array.prototype.slice.call(arguments, 1)[0];
					pluginDom.setDomicilio(domicilio);
				} else if(options == "block") {
					//verificamos si se bloqueara el formulario o no
					var bloquear = Array.prototype.slice.call(arguments, 1)[0],
					excepcionesBloqueo = Array.prototype.slice.call(arguments, 1)[1];
					if(excepcionesBloqueo) {
						pluginDom.setOption("excepcionesBloqueo",excepcionesBloqueo);
					} else {
						pluginDom.setOption("excepcionesBloqueo",null);
					}
					pluginDom.bloquearFormDomicilio(bloquear);
				} else if(options == "clean") {
					//limpiamos el formulario
					pluginDom.limpiarFormularioDomicilio();
				}
				//una vez que se termina la operacion retornamos
				return;
			} else {
				//si no se ha inicializado el plugin mostramos un error en pantalla
				alert("El plugin de domicilio no ha sido inicializado");
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
			var instanciaDomicilioRecortado = new ComponenteDomicilioRecortado(options,this);
			//ya creada la instancia la guardaremos dentro de nuestro objeto
			//console.log("asociamos el data para el elemento " + elementoActual.attr("id"));
			$elementoActual.data(nombrePlugin,instanciaDomicilioRecortado);
			//iniciamos el plugin
			//console.log("iniciamos el plugin para el elemento " + elementoActual.attr("id"));
			$elementoActual.data(nombrePlugin).iniciar();
		});
	};

	/**
	 * Esta es la url de busqueda de CPs, en caso de que se quisiera hacer algo distinto en la busqueda de asentamientos por codigos postales
	 * se podria modificar la url de busqueda para que se agregen validaciones del lado del controller, en caso de modificar la url, 
	 * la peticion siempre se hace por POST y siempre espera que se retorne un lista de objetos de tipo ASENTAMIENTO en una lista llamada asentamientos
	 * dento de otro objeto, es decir, dataResultado.asentamientos , si la url no retorna este tipo de objetos el plugin no funcionara
	 * 
	 * En caso de que haya mas de un componente de domicilio y se quiera cambiar la ruta de consulta de asentamientos para todos los componentes
	 * de domicilio recortado en la pantalla esta es el parametro que se tiene que moficiar y luego llamar al plugin de manera normal ej. 
	 * 
	 * $.fn.domicilioRecortado.URL_BUSQUEDA_CP = '/contexto/domicilio/buscarPorCpYUmf';
	 * $("#divContenedor").domicilioRecortado({opciones});
	 * 
	 * En caso de que haya mas de un componente y solo se quiera modificar para uno, la forma de hacerlo es al iniciar el plugin 
	 * 
	 * $("#divContenedor").domicilioRecortado({
	 *       urlBusquedaCP: '/contexto/domicilio/buscarPorCpYUmf
	 * })
	 * 
	 * con lo anterior solo se modificaria la ruta para el formulario que esta en divContenedor
	 * Sino se especifica nada la ruta sera la de default
	 */
	$.fn.domicilioRecortado.URL_BUSQUEDA_CP = '/${mvn.web.app.root}/domicilio/nacional/ubicar/asentamiento/get/codigoPostal';
	
	/**
	 * Los elementos por default del plugin de domicilio recortado en cada uno se pone su descripcion
	 */
	$.fn.domicilioRecortado.defaults = {
		contenedor: '', //el div donde se cargara el componente, en caso de que sea llamado como plugin $("#id").domicilioRecortado({}) no se tomara en cuenta el valor de este campo
		divMensajes: '', //el div que se utilizara como dialogo para mostrar los mensajes
		mostrarFormulario: false, // indicador para pintar o no todo el formulario desde el principio sino se indica solo se muestra el codigo postal
		mostrarMensajeCaptura: true, //indicador para saber si en caso de un error de captura se muestra el mensaje de que existen errores de formulario
		setDomicilioDefaultOnInit: false, //indica si el domicilio por default se seteara al cargarse el formulario
		domicilioDefault: null, //domicilio que se utilizara como default y se establecera en caso de que la bandera setDomicilioDefaultOnInit este marcada como true
		formularioDeshabilitado: false,//indica si de inicio el formulario estara deshabilitado
		mostrarTitulosDialogs: false, //indica que los dialog de jquery no tendran titulo
		urlBusquedaCP: null, //url de busqueda de CPS en caso de que no se quiera usar la default, siempre debe devolver lista de objetos de tipo asentamiento
		parametrosBusqueda: {}, //en caso de que se especifique otra url de busqueda,aqui podrian mandarse mas parametros, siempre se enviara un parametro que se llama codigo
		bootstrapHabilitado: true,//inidicador para saber si en la pantalla en donde se usara el plugin tambien esta cargado bootstrap, en caso de que sea false el formulario sera una tabla y no con estilos de boostrap
		mostrarMesajeRequeridos: true,//indicador para saber si se muestra en el componente el mensaje de campos requeridos o no, ya que puede estar en el formulario que contiene al componente
		habilitarTooltips: true,//indicador de tooltips
		//Empiezan los atributos que se usaran en caso de que no se tenga bootstrap, si la bandera bootstrapHabilitado es true no se tomaran en cuenta
		classInputs: '',//cssClass que tendran los select, cajas de texto
		classTabla: '', //cssClas que tendra la tabla
		classButtonAceptar: '', //cssClass del boton aceptar que iniciara la busqueda por CP
		classButtonLimpiar: '', //cssClass del boton limpiar del formulario de domicilio
		//termina la definicion de classes cuando no esta disponible bootstrap
		onReady : null, //funcion que se ejecutara una vez que se haya cargado el formulario
		funcionCambioColonia: null, //funcion que se ejecuta cuando se selecciona una colonia
		funcionOk: null, //funcion que se ejecuta cuando se encuentran asentamientos
		funcionLimpiar: null, //funcion que se ejecutara despues de que se limpie el formulario
		funcionSinDatos: function() {$.fn.domicilioRecortado.muestraMensajeSinAsentamientos()}, //funcion que se ejecuta en caso de que no se encuenten asentamientos, se puede mandar null
		funcionError: function() {$.fn.domicilioRecortado.muestraMensajeSinAsentamientos()}, //funcion que se ejecuta si existe algun error al buscar los asentamientos
		excepcionesBloqueo: null//arreglo[] de campos que no se bloquearan cuando se llame la funcion de bloquear formulario, las opciones son calle, numExt, numInt, cp, colonia
	};
	
	/**
	 * Se establecen variables que pueden ser cambiadas en cualquier momento antes de invovar el plugin p.e
	 * si quisieramos que la opcion por default de los campos dijera otra cosa o tuviuera otro valor se haria de la siguiente manera
	 * 
	 * $.fn.domicilioRecortado.OPTION_DEFAULT = '<option value="0">--Por favor seleccione un valor del combo--</option>';
	 * $("#divDom").domicilioRecortado({opciones});
	 * 
	 * con esto la opcion por defualt ya no diria --Por favor seleccione-- ahora diria --Por favor seleccione un valor del combo--
	 * para todos los domicilios en la pantalla
	 */
	//Se establece el mensaje por default cuando no hay asentamiento para que pueda ser modificado
	$.fn.domicilioRecortado.MENSAJE_SIN_ASENTAMIENTOS = "No se localiz\u00F3 informaci\u00F3n con el c\u00F3digo postal ingresado.";
	$.fn.domicilioRecortado.OPTION_DEFAULT = '<option value="-1">--Selecciona por favor--</option>';
	$.fn.domicilioRecortado.PERIODO_BUSQUEDA = 4;
	$.fn.domicilioRecortado.TIPO_VIALIDAD_CALLE = 5;
	$.fn.domicilioRecortado.TIPO_NINGUNO = 'NINGUNO';
	
	
	//funcion que se ejecutara cuando no se encuentren asentamientos relacionados con el codigo postal
	$.fn.domicilioRecortado.muestraMensajeSinAsentamientos = function() {
		$.fn.domicilioRecortado.mostrarMensajeError($.fn.domicilioRecortado.MENSAJE_SIN_ASENTAMIENTOS);
	};
	
	//funcion para mostrar mensaje de error
	$.fn.domicilioRecortado.mostrarMensajeError = function(mensaje) {
		var divMensajes = $.fn.domicilioRecortado.defaults.divMensajes != '' ? ('#'+$.fn.domicilioRecortado.defaults.divMensajes) : '<div></div>';
		var mensajeError =  $(''+divMensajes);
		mensajeError.html(mensaje);
		mensajeError.dialog({
			autoOpen : false,
			title: 'Error',
			resizable: false,
			closeOnEscape: false,
			modal: true,
			heigth: 'auto',
			width: 'auto',
			buttons: {"Aceptar" : function() { $(this).dialog("close");}}
		}
		)
		mensajeError.dialog('open');
	};
	
	/**
	 * Clase para generar el componente de domicilio recortado
	 * @param options
	 * @param $element
	 * @returns
	 */
	ComponenteDomicilioRecortado = function(opcionesInicio,elementoPantalla) {

		//console.log("El id del contenedor es: " + opcionesInicio.contenedor);		
		//establecemos las opciones
		var options = $.extend({},$.fn.domicilioRecortado.defaults,opcionesInicio),
		_self = this,//una copia del objeto actual
		idFormulario,//variable donde se guardara el id del formulario
		ASENTAMIENTOS_UBICADOS = null,//establecemos las variables que dependen de cada instancia
		bloqueado = false;//variable para saber si esta bloqueado el campo de codigo postal
		var formularioBloqueado = options.formularioDeshabilitado;
		
		//si el elemento del dom se paso como parameto quiere decir que el componente fue iniciado como plugin
		//y en ese caso el id del div o el elemento que vaya a ser contenedor no vendria, por lo que se saca
		//el id del elemento del dom sobre el que hayan ejecutado el plugin
		if(elementoPantalla) {
			options.contenedor = elementoPantalla.id;
		}
		//establecemos el id del formulario
		idFormulario = "#" + options.contenedor +" " + _self.FORMULARIO_DOMICILIO;
		
		if(options.urlBusquedaCP == null) {
			options.urlBusquedaCP = $.fn.domicilioRecortado.URL_BUSQUEDA_CP;
		}
		
		/****************************************************************************************
		 *  Funciones publicas que se podran llamar cuando se obtenga la referencia al objeto  **
		 ****************************************************************************************/
		this.setOption = function(option, valor) {
			//console.log("se modificara la opcion " + option + " con el valor " + valor);
			options[option] = valor;
		}
		/*************************************************************************************************
		 **  Metodo para iniciar el componente de domicilio recortado y setearlo en el div indicado
		 *************************************************************************************************/
		this.iniciar =  function() {
			//bloqueamos la pantalla mientras se carga toda la informacion
			$.blockUI();
			
			//valores iniciales del formulario que se enviaran al servidor
			var formInicial = {
				bloquearFormulario : options.formularioDeshabilitado,
				setDomicilio : options.setDomicilioDefaultOnInit,
				domicilio : options.domicilioDefault,
				bootsTrapHabilitado: options.bootstrapHabilitado,
				classInputs: options.classInputs,
				classTabla: options.classTabla, 
				classButtonAceptar: options.classButtonAceptar, 
				classButtonLimpiar: options.classButtonLimpiar,
				mostrarTitulosDialogs: options.mostrarTitulosDialogs,
				mostrarMesajeRequeridos: options.mostrarMesajeRequeridos
			}
			
			//cargamos la url de domicilios
			//$("#"+_this.options.contenedor).load(_this.URLDOMICILIO,formInicial,_this.setEventos);
			//Cargamos el formulario, lo hacemos mediante ajax para mandar un objeto json
			$.ajax($.extend({},_self.DEFAULTS_PET,{
		    	url : _self.URLDOMICILIO,
		    	async: false,
		        data: JSON.stringify(formInicial),
		        dataType:'html',
		        success: function (html) {
		        	//console.log("seteare los datos en el div" + options.contenedor);
		        	$("#"+options.contenedor).html(html);
		        	//una vez que acabamos de crear el plugin 
		        	setEventos();
		        }
		    }));
		};
		
		/**
		 * Metodo para obtener el JSON del domicili ocapturado llamando al metodo privado
		 * @returns domicilio - El objeto domicilio capturado en pantall
		 */
		this.getDomicilioCapturado = function() {
			return getJSONDomicilioCapturado();
		}
		
		/**
		 * Metodo que obtiene la referencia al formulairo actual donde se encuentra el domicilio
		 * con toda la estructura requerida por jquery que seria algo del estilo
		 * div#nombre-contenero form#nombre-del-formulario
		 * @returns idFormulario - el id del formulario
		 */
		this.getFormularioDomicilio = function() {
			return idFormulario;
		}
		
		/**
		 * funcion que sirve para bloquear el formulario de domicilio llamando a las funciones privadas
		 * especificas dependiendo de la variable
		 */
		this.bloquearFormDomicilio = function(bloquear) {
			//console.log("El estado actual del formulario es bloqueado ? " + formularioBloqueado + " y el formulario se bloqueara? " + bloquear);
			if(bloquear && !formularioBloqueado) {
				bloquearFormulario();
			} else if(!bloquear && formularioBloqueado){
				desbloquearFormulario(idFormulario);
			}
			
			return;
		}
		
		/**
		 * Funcion que seteara un domicilio en el formulario que recibe un parametro de tipo domicilio
		 * @param domicilio - En caso de que sea null o undefined se tratara de setear el domicilio por default,
		 * en caso de que no se pasa nada y no exista el domicilio por default no se hace nada
		 */
		this.setDomicilio = function(domicilio) {
			//console.log("ingreo a insertar el domicilio " + domicilio);
			if(domicilio != undefined && domicilio != null) {
				//console.log("si viene el domicilio");
				setearDomicilio(domicilio)
			} else {
				setearDomicilioDefault();
			}
			
			return;
		}
		
		this.limpiarFormularioDomicilio = function() {
			limpiarFormulario();
		}
		
		/*******************************************************************************************************************
		 ** Empiezan las funciones privadas de la clase que intereatuan con el servidor
		 ** mediante llamadas AJAX
		 *******************************************************************************************************************/
		
		/**
		 * funcion privada para establecer los eventos en el formulario cargado
		 **/
		var setEventos = function() {
			var $formularioDomicilio = $(idFormulario);
			//creamos los tooltips
			if(options.bootstrapHabilitado && options.habilitarTooltips) {
				$formularioDomicilio.find('[data-toggle="tooltip"]').tooltip();
			} else {
				$formularioDomicilio.find('[data-toggle="tooltip"]').hide();
			}
			//seteamos el evento del boton busqueda CP
			$formularioDomicilio.find('#busquedaCp').on('click',function(){ubicarPorCodigoPostal()});
			//seteamos el evento del boton limpiar
			$formularioDomicilio.find('#limpiarForm').on('click',function(){limpiarFormulario()});

			//se quitan los espacios en blanco de los numeros que se generan cuando no se trae los datos de numero y numero alfanumerico
			var $numExteriorAux = $formularioDomicilio.find("#domicilio\\.numExteriorAlf"),
			$numInteriorAux = $formularioDomicilio.find("#domicilio\\.numInteriorAlf");
			$numExteriorAux.val($.trim($numExteriorAux.val()));
			$numInteriorAux.val($.trim($numInteriorAux.val()));
			
			//Verificamos si existe la funcion de cambio de acolonica
			var funcionCambioClinica = options.funcionCambioColonia;
			if($.isFunction(funcionCambioClinica)) {
				$formularioDomicilio.find(_self.ID_COLONIA).on('change',function() {
					var claveAsentamiento = this.value,
					asentamiento = null;
					//console.log("el id sel asentemiento es: " + claveAsentamiento)
					//si la clave del asentamiento es diferente de 
					if(claveAsentamiento != "-1") {
						asentamiento = getAsentamientoElegido(claveAsentamiento);
						asentamiento.codigoPostal = new Object();
						asentamiento.codigoPostal.codigoPostal = $formularioDomicilio.find(_self.ID_CP).val();
					} 
					
					funcionCambioClinica(asentamiento);
				});
			}
			
			//verificamos si de inicio tenemos que mostrar el formulario completo o no
			if(options.mostrarFormulario) {
				mostrarDatosDomicilio(true);
			}
			//una vez que terminamos de setear los eventos desbloqueamos las pantallas
			$.unblockUI();
			
			//evitamos el uso de la tecla back, hacemos el campo numerico y ponemos el foco
			$formularioDomicilio.find(_self.ID_CP).on('keydown',function(e){  
				if( e.which == 8 && (bloqueado) ){
					//console.log("esta bloqueado el campo");
					e.preventDefault();  
					return false;   
				} 
			}).numeric().focus();  
			
			setEventosChecarErrorDom();
			
			if(options.setDomicilioDefaultOnInit && $.trim($formularioDomicilio.find(_self.ID_CP)).length && options.domicilioDefault && options.domicilioDefault.asentamiento){
				busquedaCpYSeleccion(options.domicilioDefault.asentamiento.clave);
			}
			
			if(options.formularioDeshabilitado){
				desbloquearExcepciones();
			}
			
			//ejecutamos la function on ready
			_self.ejecutaFuncion(options.onReady);
		};
		
		var setEventosChecarErrorDom = function() {
			if(verificarExistenciaFuncionErrores()) {
				$(idFormulario+" :text").change(function() {
					verificarPersistenciaDeErrorDom(this, false,marcarCamposErrorFormDom);
				});
				
				$(idFormulario+" select").change(function() {
					verificarPersistenciaDeErrorDom(this, true,marcarCamposErrorFormDom);
				});
			}
		}

		var verificarPersistenciaDeErrorDom = function(element, isSelect, functionErrores) {
		    var elementoError = document.getElementById(element.name+"Error");
		    //verificamos si existe el mensaje de error
		    if(elementoError != undefined) {
		    	//obtenemos la referencia al elemento del error
		    	var $elementoError = $(elementoError);
		    	//verificamos si persiste el error
		    	var errorSolucionado = isSelect ? element.selectedIndex > 0 : $.trim(element.value).length > 0;
		    	//si el elemento es visible y ya no hay error ocultamos el mensaje, y corremos la validacion de errores
		    	if($elementoError.is(":visible") && errorSolucionado) {
		    		$elementoError.html("").removeClass("showElement").addClass("hiddenElement");
		    		//verificamos si ejecutamos la funcion
		    		if(functionErrores != undefined && functionErrores != null && $.isFunction(functionErrores)) {
		    			functionErrores();
					}
		    	}
		    }
		}
		var marcarCamposErrorFormDom = function() {
			if(verificarExistenciaFuncionErrores()) {
				marcarCamposConErrores(idFormulario,".error","div");
			}
		};
		
		var verificarExistenciaFuncionErrores = function() {
			if(typeof marcarCamposConErrores === "undefined") {
				return false;
			} else if($.isFunction(marcarCamposConErrores)) {
				return true;
			} else {
				return false;
			}
		};
		
		/**
		 * funcion para ubicar por codigo postal
		 */
		var ubicarPorCodigoPostal = function() {
			//ocultamos los errores del campo
			fnHideErrores(""+idFormulario);
			marcarCamposErrorFormDom();
			//obtenemos el domicilio
			var $formularioDomicilio = $(idFormulario),
			codigoPostal = $formularioDomicilio.toObject();
			
			$.ajax($.extend({},_self.DEFAULTS_PET,{
		    	url : _self.URL_VALIDACIONES_CP,
		        data: JSON.stringify(codigoPostal),
		        success: function () {
		        	busquedaCpYSeleccion(null);
		        },
				error: function(data) {
					//validamos si mostramos error de captura
					if(options.mostrarMensajeCaptura) {
						$.fn.domicilioRecortado.mostrarMensajeError(_self.MSG_CAPTURA);
					};
					//quitamos los asentamientos ubicados hasta ahora
					ASENTAMIENTOS_UBICADOS = null;
					fnProcesarErrores(data, ""+idFormulario);
					marcarCamposErrorFormDom();
				}
		    }));
		};
		
		var busquedaCpYSeleccion= function(asentamientoElegido) {
			//obtenemos el codigo postal
			var $formularioDomicilio = $(idFormulario),
			codigoPostal = $formularioDomicilio.find(_self.ID_CP).val();
    		//seteamos el codigo postal
    		$formularioDomicilio.find("#codigoPostalSeleccionado").val(codigoPostal);
    		//obtenemos los asentamientos por el codifo postal
    		getAsentamientoPorCP(codigoPostal,asentamientoElegido);
		}
		/**
		 * funcion que busca los asentamientos de acuerdo al codigo postal
		 * @param cpBusqueda
		 */
		var getAsentamientoPorCP = function(cpBusqueda, asentamientoElegido) {
			//realizamos la peticion al servidor
			$.ajax($.extend({},_self.DEFAULTS_PET,{
				type: 'GET',
				url : options.urlBusquedaCP,
				data :  $.extend({},{'codigo' : cpBusqueda},options.parametrosBusqueda),
				success : function(data) {
					
					if(data.asentamientos) {
						var localidad = data.asentamientos[0].localidad;
						var claveEstado =  localidad.municipio.entidadFederativa.clave,//sacamos los datos de los nombres y claves
						claveMunicipio = localidad.municipio.clave,
						nombreEstado =  localidad.municipio.entidadFederativa.nombre.toUpperCase(),
						nombreMunicipio = localidad.municipio.nombre.toUpperCase(),
						$formularioDomicilio = $(idFormulario);//Cacheamos el formulario para que ya solo se busque en el y no en todo el DOM
						//buscamos la calle ninguno que este relacionada al estado y al municipio
						buscarVialidadNinguno(claveEstado, claveMunicipio);
						//seteamos los datos(claves y descripciones) de estado y municipio
						$formularioDomicilio.find(_self.ID_MUN_CVE).val(claveMunicipio);
						$formularioDomicilio.find(_self.ID_ENT_CVE).val(claveEstado);
						$formularioDomicilio.find(_self.ID_MUN_NOM).val(nombreMunicipio);
						$formularioDomicilio.find(_self.ID_ENT_NOM).val(nombreEstado);

						//En caso de que exista la funcion de exito la ejecutamos
						_self.ejecutaFuncion(options.funcionOk);
						//mostramos los datos de estado,municipio, colonia, calle y numeros
						mostrarDatosDomicilio(true);
						//Se setean los asentamientos en el combo
						setAsentamientos(data.asentamientos,asentamientoElegido);
					} else{
						//en caso de que exista la funcion de sin datos la ejecutamos
						_self.ejecutaFuncion(options.funcionSinDatos);
					}
					
					
				},
		        error : function (data) {
		        	bloquearCP(false);
		        	ASENTAMIENTOS_UBICADOS = null;
		        	//pintamos los errores de captura
		        	fnProcesarErrores(data, idFormulario);
		        	//limpiamos la informacion del asentamiento
		        	limpiarAsentamientos();
		    		//en caso de que se haya especificado una funcion de error la ejecutamos
		    		_self.ejecutaFuncion(options.funcionError);
		        }
			}));
		};
		
		/**
		 * Metodo para buscar las vialidas Calle con nombre ninguno
		 * de acuerdo al estado y municipio especificados
		 * @param entidad
		 * @param municipio
		 */
		var buscarVialidadNinguno = function(entidad, municipio) {
			$.ajax($.extend({},_self.DEFAULTS_PET,{
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
		    	url : _self.URL_VIALIDAD_NINGUNO,
		        data : {
					cveEnt : entidad,
					cveMun : municipio,
					nomVialidad : $.fn.domicilioRecortado.TIPO_NINGUNO,
					periodo: $.fn.domicilioRecortado.PERIODO_BUSQUEDA,
					cveTipoVialidad : $.fn.domicilioRecortado.TIPO_VIALIDAD_CALLE
				},
		        success: function (result) {
		        	var $formularioDomicilio = $(idFormulario);
		        	//seteamos los campos de vialidad que coincidan con la calle ninguno
		        	$formularioDomicilio.find(_self.ID_VIAL_PRIM_CVE).val(result.vialidadElegida.clave);
		        	$formularioDomicilio.find(_self.ID_VIAL_PRIM_NOM).val(result.vialidadElegida.nombre);
		        	$formularioDomicilio.find(_self.ID_TIPO_VIAL_CVE).val(result.vialidadElegida.tipoVialidad.clave);
		        	$formularioDomicilio.find(_self.ID_TIPO_VIAL_NOM).val(result.vialidadElegida.tipoVialidad.descripcion);
		        	$formularioDomicilio.find(_self.ID_LOCA_CVE).val(result.localidadVialidadPrimaria.clave);
		        	$formularioDomicilio.find(_self.ID_LOCA_NOM).val(result.localidadVialidadPrimaria.nombre);
		        }
			}));
		};
		
		/*********************************************************************************************************************
		 ** Metodos utilitarios que no hacen consultas al servidor solo setean los datos 
		 ** en los elementos del DOM o en su caso solo interactuan con las variables privadas 
		 ** de la clase
		 ********************************************************************************************************************/
		
		/**
		 * funcion que setea el domicilio por default
		 */
		var setearDomicilioDefault = function() {
			setearDomicilio(options.domicilioDefault);
			return;
		};
		
		/**
		 * function que setea un domicilio
		 * @param domicilio
		 */
		var setearDomicilio = function(domicilio) {
			//console.log("entro al metodo privado que setea el domicilio en el formulario " + domicilio);
			if(domicilio != undefined && domicilio != null) {
				var $formularioDomicilio = $(idFormulario), numeroExterior = "",numeroInterior = "" ;
				
				$formularioDomicilio.find(_self.ID_CP).val(domicilio.codigoPostal.codigoPostal);
				
				if(domicilio.asentamiento.localidad.clave == null || domicilio.asentamiento.clave == null) {
					 $formularioDomicilio.find("#busquedaCp").click();
				} else {

					var cveEstado = domicilio.asentamiento.localidad.municipio.entidadFederativa.clave,
					cveMunicipio = domicilio.asentamiento.localidad.municipio.clave,
					opcionesColonias = $.fn.domicilioRecortado.OPTION_DEFAULT;
					
					//clave de la localidad
					$formularioDomicilio.find(_self.ID_LOCA_CVE).val(domicilio.asentamiento.localidad.clave);
					//datos del estado
					$formularioDomicilio.find(_self.ID_ENT_CVE).val(cveEstado);
					$formularioDomicilio.find(_self.ID_ENT_NOM).val(domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre);
					//datos del municipio
					$formularioDomicilio.find(_self.ID_MUN_CVE).val(cveMunicipio);
					$formularioDomicilio.find(_self.ID_MUN_NOM).val(domicilio.asentamiento.localidad.municipio.nombre);
					//En caso de que venga la clave seteamos los datos
					if(domicilio.vialidadPrimaria && domicilio.vialidadPrimaria != null && domicilio.vialidadPrimaria.clave != null) {
						$formularioDomicilio.find(_self.ID_VIAL_PRIM_NOM).val(domicilio.vialidadPrimaria.nombre);
						$formularioDomicilio.find(_self.ID_VIAL_PRIM_CVE).val(domicilio.vialidadPrimaria.clave);
						$formularioDomicilio.find(_self.ID_TIPO_VIAL_NOM).val(domicilio.vialidadPrimaria.tipoVialidad.descripcion);
						$formularioDomicilio.find(_self.ID_TIPO_VIAL_CVE).val(domicilio.vialidadPrimaria.tipoVialidad.clave);
					} else {
						//Si no viene la clave obtenemos la vialidad ninguno para cumplir con la regla
						buscarVialidadNinguno(cveEstado, cveMunicipio);
					}
					
					opcionesColonias += "<option value="+domicilio.asentamiento.clave+" selected=\"selected\">"+domicilio.asentamiento.nombre+"</option>";
					$formularioDomicilio.find(_self.ID_COLONIA).html(opcionesColonias);
				}				
				//datos de la calle
				$formularioDomicilio.find(_self.ID_CALLE).val(domicilio.calle);
				
				if(domicilio.numExterior1 != undefined) {
					numeroExterior += domicilio.numExterior1 + " ";
				}
				
				if(domicilio.numExteriorAlf != undefined) {
					numeroExterior += domicilio.numExteriorAlf;
				}
				
				if(domicilio.numInterior != undefined) {
					numeroInterior += domicilio.numInterior+ " ";
				}
				
				if(domicilio.numInteriorAlf != undefined) {
					numeroInterior += domicilio.numInteriorAlf;
				}
				
				$formularioDomicilio.find(_self.ID_NUM_EXT).val($.trim(numeroExterior));
				$formularioDomicilio.find(_self.ID_NUM_INT).val($.trim(numeroInterior));
			}
			
			return;
		};
		
		/**
		 * Funcion que obtiene un objeto JSON con la estructura del domicilio capturado
		 */	
		/**
		 * Funcion que obtiene un objeto JSON con la estructura del domicilio capturado
		 */	
		var getJSONDomicilioCapturado =  function() {
			//Seteamos todos los datos en mayusculas
			setearMayuscularFormulario();
			//ocultamos los errores
			fnHideErrores(idFormulario);
			marcarCamposErrorFormDom();
			//obtenemos el domicilio capturado
			var datosCapturados = null,
			domicilioCapturado = null,
			domicilioBloqueado = formularioBloqueado;
			
			if(domicilioBloqueado) {
				desbloquearFormulario();
				datosCapturados = $(idFormulario).toObject();
				bloquearFormulario();
			} else {
				datosCapturados = $(idFormulario).toObject();
			}
			
			$.ajax($.extend({},_self.DEFAULTS_PET,{
		    	url : _self.URL_VALIDA_DOM,
		    	async: false, //se pone en false para que haga la consulta y devuelva el resultado bien
		        data: JSON.stringify( datosCapturados),
		        success: function () {
		        	//no se hace nada ya que solo es para ver si el formulario este completo
		        	$.unblockUI();
		        	domicilioCapturado = datosCapturados.domicilio;
		        },
				error: function(data) {
					$.unblockUI();
					if(options.mostrarMensajeCaptura) {
						$.fn.domicilioRecortado.mostrarMensajeError(_self.MSG_CAPTURA);
					}
					fnProcesarErrores(data,idFormulario);
					marcarCamposErrorFormDom();
				}
			}));
			
			return domicilioCapturado;
		};
		
		/**
		 * Metodo que setea los asentamientos en el combo respectivo
		 * @param asentamientos
		 */
		var setAsentamientos = function(asentamientos,asentamientoElegido) {
			
			//se agrega la opcion por defaul
			var optionsAsentamientos = $.fn.domicilioRecortado.OPTION_DEFAULT;
			
			if(asentamientos != null && asentamientos.length > 0) {
				bloquearCP(true);
				ASENTAMIENTOS_UBICADOS = asentamientos;
				//se recorren los asemtamientos
				for ( var i = 0; i < asentamientos.length; i++) {
					if(asentamientoElegido != null && asentamientoElegido == asentamientos[i].clave) {
						optionsAsentamientos += "<option value='" + asentamientos[i].clave + "' selected='selected'>"+asentamientos[i].nombre+"</option>";
					} else {
						optionsAsentamientos += "<option value='" + asentamientos[i].clave + "'>"+asentamientos[i].nombre+"</option>";
					}
				}
			} else {
				bloquearCP(false);
				ASENTAMIENTOS_UBICADOS = null;
			}
			
			//se setean los asentamientos en el combo
			$(idFormulario).find(_self.ID_COLONIA).html(optionsAsentamientos);
		};
		
		/**
		 * metodo para limpiar el formulario
		 */
		var limpiarFormulario = function() {
			//buscamos y limpiamos todos los elementos de tipo texto en el formulairo de domicilio recortado correspondiente
			$(idFormulario).find('input:text').each(function(){
				$(this).val("");
			});
			//limpiamos los asentemiantos
			limpiarAsentamientos();
			bloquearCP(false);
			//escondemos los errores
			fnHideErrores(""+idFormulario);
			marcarCamposErrorFormDom();
			//si se tiene que hacer algo despues de limpiar el formulario lo hacemos
			_self.ejecutaFuncion(options.funcionLimpiar);
		};
		
		/**
		 * funcion para limpiar los datos del asentamiento
		 */
		var limpiarAsentamientos = function() {
			ASENTAMIENTOS_UBICADOS = null;
			var idComboColonia = _self.ID_COLONIA,
			mostrarDatosComp = options.mostrarFormulario,
			$formularioDomicilio = $(idFormulario);
			
			//ocultamos los campos de 
			if(!mostrarDatosComp) {
				mostrarDatosDomicilio(false);
			}
			
			$formularioDomicilio.find(idComboColonia).html($.fn.domicilioRecortado.OPTION_DEFAULT);
			$formularioDomicilio.find(_self.ID_MUN_CVE).val("");
			$formularioDomicilio.find(_self.ID_ENT_CVE).val("");
			$formularioDomicilio.find(_self.ID_MUN_NOM).val("");
			$formularioDomicilio.find(_self.ID_ENT_NOM).val("");
		};
		
		/**
		 * funcion que bloquea el campo de codigo postal
		 * @param bloquear
		 */
		var bloquearCP = function(bloquear) {
			if(bloquear) {
				bloqueado = true;
				//console.log("Se bloquea campo");
				$(idFormulario).find(_self.ID_CP).attr("readOnly","readOnly");
			} else {
				bloqueado = false;
				//console.log("Se desbloquea campo");
				$(idFormulario).find(_self.ID_CP).removeAttr("readOnly");
			}
		};
		
		/**
		 * funcion que devuelve el asentamiento elegido
		 * @param clave
		 * @returns
		 */
		var getAsentamientoElegido = function(clave) {
			
			if(ASENTAMIENTOS_UBICADOS != null && ASENTAMIENTOS_UBICADOS.length > 0 && clave != null) {
				for(var i= 0; i < ASENTAMIENTOS_UBICADOS.length; i++) {
					if(ASENTAMIENTOS_UBICADOS[i].clave == clave) {
						return ASENTAMIENTOS_UBICADOS[i];
					}
				}
			}
			
			return null;
		};
		
		/**
		 * funcion para mostrar los datos del domicilio en caso de que esten ocultos
		 */
		var mostrarDatosDomicilio =  function(mostrar) {
			var $formularioDomicilio = $(idFormulario);
			//Verificamos si entramos o no el formulario
			if(mostrar) {
				$formularioDomicilio.find('#infoEstadoMun').show();
				$formularioDomicilio.find('#infoDireccion').show();
			} else {
				$formularioDomicilio.find('#infoEstadoMun').hide();
				$formularioDomicilio.find('#infoDireccion').hide();
			}
		};
		
		/**
		 * metodo que setea la descripcion de un combo en el campo que se indique
		 * @param idCombo
		 * @param idDescripcion
		 */
		var setDescripcionCombo = function(idCombo,idDescripcion) {
			$(idDescripcion).val($(idCombo + " option:selected").html().toUpperCase());
		};
		
		/**
		 * funcion para setear los campos en mayusculas
		 */
		var setearMayuscularFormulario = function() {
			var $formularioDomicilio = $(idFormulario);
			var $numeroExterior = $formularioDomicilio.find(_self.ID_NUM_EXT),
			$numeroInterior = $formularioDomicilio.find(_self.ID_NUM_INT),
			$calle = $formularioDomicilio.find(_self.ID_CALLE);
			//seteamos los numeros en mayusculas
			$numeroExterior.val($numeroExterior.val().toUpperCase());
			$numeroInterior.val($numeroInterior.val().toUpperCase());
			//se agrega seteado de calle en mayusculas
			$calle.val($calle.val().toUpperCase());
			//seteamos las descripciones de los combos
			setDescripcionCombo(idFormulario + " " + _self.ID_COLONIA,idFormulario + " " + _self.ID_NOM_COL);
		};
		
		/**
		 * Metodo que bloquea el formulario
		 */
		var bloquearFormulario = function() {
			
			formularioBloqueado = true;
			var $formularioDomicilio = $(idFormulario);
			
			$formularioDomicilio.find(_self.CLASS_OBLI).hide();
			$formularioDomicilio.find(_self.CLASS_HIDE).hide();
			$formularioDomicilio.find(_self.CLASS_BLOQ).attr("disabled","disabled");
			
			desbloquearExcepciones();
		};
		
		var desbloquearExcepciones = function() {
			var $formularioDomicilio = $(idFormulario),
			datosBloqueables = {
				"calle":_self.ID_CALLE,
				"colonia": _self.ID_COLONIA,
				"cp":_self.ID_CP,
				"numExt":_self.ID_NUM_EXT,
				"numInt":_self.ID_NUM_INT
			};
			
			if(options.excepcionesBloqueo != null && options.excepcionesBloqueo.length > 0) {
				$.each(options.excepcionesBloqueo, function(index,value) {
					$formularioDomicilio.find(datosBloqueables[value]).removeAttr("disabled");
				});
			}
		}
		
		/**
		 * Metodo que desbloquea el formulario
		 */
		var desbloquearFormulario = function() {

			formularioBloqueado = false;
			var $formularioDomicilio = $(idFormulario);
			$formularioDomicilio.find(_self.CLASS_OBLI).show();
			$formularioDomicilio.find(_self.CLASS_HIDE).show();
			$formularioDomicilio.find(_self.CLASS_BLOQ).removeAttr("disabled");
		};
		
	};
	
	/**
	 * Declaramos el prototype del componente de domicilio recortado
	 */
	ComponenteDomicilioRecortado.prototype = {
		URLDOMICILIO : '/${mvn.web.app.root}/domicilio/recortado/init',
		URL_VALIDACIONES_CP : '/${mvn.web.app.root}/domicilio/recortado/validacionesCP',
		URL_VIALIDAD_NINGUNO : '/${mvn.web.app.root}/domicilio/nacional/ubicar/get/vialidad/elegida',
		URL_VALIDA_DOM : '/${mvn.web.app.root}/domicilio/recortado/validarDomicilio',
		FORMULARIO_DOMICILIO: '#datosDomicilioRecortadoForm',
		ID_CALLE: '#domicilio\\.calle',
		ID_NUM_EXT:'#domicilio\\.numExteriorAlf',
		ID_NUM_INT: '#domicilio\\.numInteriorAlf',
		ID_COLONIA: '#domicilio\\.asentamiento\\.clave',
		ID_CP: '#domicilio\\.codigoPostal\\.codigoPostal',
		ID_NOM_COL: '#domicilio\\.asentamiento\\.nombre',
		ID_MUN_CVE: '#domicilio\\.asentamiento\\.localidad\\.municipio\\.clave',
		ID_MUN_NOM: '#domicilio\\.asentamiento\\.localidad\\.municipio\\.nombre',
		ID_ENT_CVE:'#domicilio\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.clave',
		ID_ENT_NOM: '#domicilio\\.asentamiento\\.localidad\\.municipio\\.entidadFederativa\\.nombre',
		ID_LOCA_CVE: '#domicilio\\.asentamiento\\.localidad\\.clave',
		ID_LOCA_NOM: '#domicilio\\.asentamiento\\.localidad\\.nombre',
		ID_VIAL_PRIM_CVE: '#domicilio\\.vialidadPrimaria\\.clave',
		ID_VIAL_PRIM_NOM: '#domicilio\\.vialidadPrimaria\\.nombre',
		ID_TIPO_VIAL_CVE: '#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.clave',
		ID_TIPO_VIAL_NOM: '#domicilio\\.vialidadPrimaria\\.tipoVialidad\\.descripcion',
		CLASS_OBLI: '.labelObligatorio',
		CLASS_HIDE: '.divOcultoRecortado',
		CLASS_BLOQ: '.campoBloqueable',
		MSG_CAPTURA: 'Existen errores en la informaci&oacute;n capturada',
		DEFAULTS_PET: {
	        type: 'POST',
	        beforeSend : $.blockUI,
	        contentType: 'application/json',
	        dataType: 'JSON',
	        complete: $.unblockUI
		},
		/**
		 * funcion para ejecutar funciones ;P
		 * @param funcion
		 */
		ejecutaFuncion : function(funcion){
			if(funcion != undefined && funcion != null && $.isFunction(funcion)) {
				funcion();
			} else {
				return;
			}
		}
	};
	
}(jQuery,window));

var DomicilioRecortadoCtrl = {
		instance : null,
		init : function(options) {
			this.instance = new ComponenteDomicilioRecortado(options,null);
			this.instance.iniciar();
		},
		getDomicilio : function() {
			return this.instance.getDomicilioCapturado();
		},
		setDomicilio : function(domicilio) {
			this.instance.setDomicilio(domicilio);
		},
		bloqueoDomicilio: function(bloquear) {
			this.instance.bloquearFormDomicilio(bloquear)
		}, 
		getFormulario: function() {
			return this.instance.getFormularioDomicilio()
		}
}