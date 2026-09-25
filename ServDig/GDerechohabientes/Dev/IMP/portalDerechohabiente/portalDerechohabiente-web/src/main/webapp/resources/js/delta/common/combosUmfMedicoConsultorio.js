UMFS_DISPONIBLES = null;
TURNOS_DISPONIBLES = null;
CONTEXT_PATH_APLICACION = '/${mvn.web.app.root}';

OPCIONES_DEFAULT_UMF_POR_CP = {
	codigoPostal : '',
	idSelect: '',
	umfSeleccionada: '',
	mostrarCFE : false,
	funcionOk: null,
	parametrosOk: null,
	funcionSinDatos: null, 
	funcionError: null
};

OPCIONES_DEFAULT_TURNO_POR_UMF = {
	idUmf: '',
	idSelect: '',
	idTurnoSeleccionado: '',
	funcionOk: null,
	parametrosOk: null,
	funcionSinDatos: null,
	funcionError: null
};

OPCIONES_DEFAULT_MEDICO_ACTIVO = {
	idAsignacionNss: null,
	idParentesco: null,
	idUmfBusqueda: null,
	funcionSeter: null,
	funcionNoEncontrado: null
};

OPCIONES_DEFAULT_UMF_POR_ASENTAMIENTO = {
	asentamiento: null, 
	idSelect: null, 
	umfSeleccionada: null, 
	funcionOk: null, 
	parametrosOk: null, 
	funcionSinDatos: null,
	funcionError: null
};

OPCIONES_DEFAULT_CONSULTORIO_POR_UMF_TURNO = {
	idUmf: '',
	idTurno : '',
	idSelect: '',
	idConsultorioSeleccionado: '',
	mostrarVirtuales: 0,
	funcionOk: null,
	parametrosOk: null,
	funcionSinDatos: null,
	funcionError: null
};

OPCIONES_DEFAULT_CONSULTORIO_MENOR_POBLACION = {
	idUmf: '',
	idTurno: '',
	mostrarVirtuales: 0,
	funcionSeter: null,
	funcionsindatos: null, 
	functionError: null
}; 

OPCIONES_DEFAULT_MEDICO_POR_UMF_TURNO_CONSULTORIO = {
	idUmf: '', 
	idTurno: '',
	idConsultorio: '', 
	funcionSeter: null, 
	funcionSinDatos: null,
	funcionError: null
}

/**
 * Funcion para buscar el medico activo en una UMF
 * @param options.idParentesco - El id del parentesco a cambiar de clinica o registrar
 * @param options.idUmfBusqueda - La umf en donde se buscaran los datos de adscripcion
 * @param options.funcionSeter - funcion que se ejecutara una vez que se obtengan los datos
 * @param options.funcionNoEncontrado - Cuando no se encuentr un integrante en la umf
 */
function getMedicoActivo(options) {
	
	var opciones = $.extend({},OPCIONES_DEFAULT_MEDICO_ACTIVO,options);
	
	var idAsignacionNss = opciones.idAsignacionNss;
	var idParentesco = opciones.idParentesco;
	var idUmfBusqueda = opciones.idUmfBusqueda;
	var funcionSeter = opciones.funcionSeter;
	var funcionNoEncontrado = opciones.funcionNoEncontrado;
	
	if(idUmfBusqueda != null && idUmfBusqueda != "-1") {
		$.blockUI();
		var url = CONTEXT_PATH_APLICACION + "/umf/getMedicoEnTurnoActivo";
		var umf = {
				"parentesco" : {
					"idParentesco" : idParentesco
				},
				"medicoEnTurno": {
					"unidadMedicaFamiliar": {
						"idUMF" : idUmfBusqueda
					}
				},
				"asignacionNSS" : {
					"idAsignacionNSS" : idAsignacionNss
				}
		};
		
		$.postJSON(url,umf,function(result) {
				$.unblockUI();
				if(result.encontrado) {
					var medicoEnTurnoActivo = result.medico;
	
					if(medicoEnTurnoActivo != null){
						funcionSeter(medicoEnTurnoActivo);
					} 
				}else {
					funcionNoEncontrado();
				}
			}	
		);
	}
}

/**
 * Function para obtener las umfs respecto a un aentamiento
 * @param options.asentamiento
 * @param options.idSelect - El id del select que contendra las umfs, debe venir sin #
 * @param options.umfSeleccionadda - El id de la umf que se elegira por default, puede venir nulo
 * @param options.funcionOk - Funcion que se ejecutara en caso de que se encuentren datos, puede ser nula
 * @param options.parametrosOk - Parametros que se le pasaran a la funcion funcionOk
 * @param options.funcionSinDatos - Funcion que se ejecutara en caso de que no se encuentre ninguna umf relacionada al CP
 * @param options.funcionError - Funcion que se ejecutara en caso de que ocurra algun error al consultar las UMFs
 */
function findUmfsByAsentamiento(options) {
	
	var opciones = $.extend({},OPCIONES_DEFAULT_UMF_POR_ASENTAMIENTO,options);
	
	var asentamiento= opciones.asentamiento; 
	var idSelect= opciones.idSelect; 
	var umfSeleccionada= opciones.umfSeleccionada; 
	var funcionOk= opciones.funcionOk; 
	var parametrosOk= opciones.parametrosOk; 
	var funcionSinDatos= opciones.funcionSinDatos; 
	var funcionError= opciones.funcionError; 
	
	//verificamos si la umf seleccionada es nula de ser asi la ponemos en blanco
	umfSeleccionada = umfSeleccionada == null ? "" : umfSeleccionada;
	
	//console.debug("la umf seleccionada es : %s",umfSeleccionada );
	
	//url para consultar las umfs por codigo postal
	var url = CONTEXT_PATH_APLICACION+"/umf/getUmfsByAsentamiento";
	var options = "<option value='-1'>--Selecciona por favor--</option>";
	
	//Validamos que el codigo postal no venga vacio, nulo y que contenga al menos 5 digitos
	if(asentamiento != null && asentamiento.clave != "-1") {
		//Bloqueamos la pantalla
		$.blockUI();
		//Hacemos la llamada json
		$.postJSON(url, asentamiento, function(result) {
			
			//Establecemos las umfs en una variable global para poder obtener despues sus datos
			UMFS_DISPONIBLES = result;
			
			if(UMFS_DISPONIBLES != null && UMFS_DISPONIBLES.length > 0) {
				//recorremos las umfs que encontramos
				for(var i = 0 ; i < UMFS_DISPONIBLES.length ; i++){
					//verificamos si es la umf seleccionada
					if(umfSeleccionada == UMFS_DISPONIBLES[i].idUMF ) {
						options += "<option value='" + UMFS_DISPONIBLES[i].idUMF + "'  selected='selected'>" + UMFS_DISPONIBLES[i].descripcion + "</option>";
					}else{
						options += "<option value='" + UMFS_DISPONIBLES[i].idUMF + "'>" + UMFS_DISPONIBLES[i].descripcion + "</option>";
					}
				}
				//invocamos funcion que sete los datos
				terminarYLlamarFuncion(idSelect, options,funcionOk, parametrosOk);
			} else {
				//En caso de que no haya resultados desbloqueamos la pantalla y seteamos las opciones
				terminarYLlamarFuncion(idSelect,options,funcionSinDatos, null);
			}
			//En caso de ocurrir un error
		}).error(function(data){
			terminarYLlamarFuncion(idSelect,options,funcionError, null);
		});
	}
}

/**
 * Function para obtener las umfs respecto a un codigo postal recibiendo los siguientes parametros
 * @param codigoPostal - codigo postal a 5 digitos del asentamiento
 * @param idSelect - El id del select que contendra las umfs, debe venir sin #
 * @param umfSeleccionadda - El id de la umf que se elegira por default, puede venir nulo
 * @param funcionOk - Funcion que se ejecutara en caso de que se encuentren datos, puede ser nula
 * @param parametrosOk - Parametros que se le pasaran a la funcion funcionOk
 * @param funcionSinDatos - Funcion que se ejecutara en caso de que no se encuentre ninguna umf relacionada al CP
 * @param funcionError - Funcion que se ejecutara en caso de que ocurra algun error al consultar las UMFs
 */
function findUmfsByCodigoPostal(options) {
	
	var opciones =  $.extend({},OPCIONES_DEFAULT_UMF_POR_CP,options);
	
	//verificamos si la umf seleccionada es nula de ser asi la ponemos en blanco
	var umfSeleccionada = opciones.umfSeleccionada == null ? "" : opciones.umfSeleccionada;
	var codigoPostal = opciones.codigoPostal;
	var idSelect = opciones.idSelect;
	var mostrarCFE = opciones.mostrarCFE;
	var funcionOk = opciones.funcionOk;
	var parametrosOk = opciones.parametrosOk;
	var funcionSinDatos = opciones.funcionSinDatos;
	var funcionError = opciones.funcionError;
	
	//console.debug("la umf seleccionada es : %s",umfSeleccionada );
	
	//url para consultar las umfs por codigo postal
	var url = CONTEXT_PATH_APLICACION + (mostrarCFE ? "/umf/getUmfsByCodigoPostalV" :"/umf/getUmfsByCodigoPostal");
	var options = "<option value='-1'>--Selecciona por favor--</option>";
	
	//Validamos que el codigo postal no venga vacio, nulo y que contenga al menos 5 digitos
	if(codigoPostal != "" && $.trim(codigoPostal).length == 5) {
		//Bloqueamos la pantalla
		$.blockUI();
		//Hacemos la llamada json
		$.postJSON(url, {'codigoPostal': codigoPostal}, function(result) {
			
			//Establecemos las umfs en una variable global para poder obtener despues sus datos
			UMFS_DISPONIBLES = result;
			
			if(UMFS_DISPONIBLES != null && UMFS_DISPONIBLES.length > 0) {
				//recorremos las umfs que encontramos
				for(var i = 0 ; i < UMFS_DISPONIBLES.length ; i++){
					//verificamos si es la umf seleccionada
					if(umfSeleccionada == UMFS_DISPONIBLES[i].idUMF ) {
						options += "<option value='" + UMFS_DISPONIBLES[i].idUMF + "'  selected='selected'>" + UMFS_DISPONIBLES[i].descripcion + "</option>";
					}else{
						options += "<option value='" + UMFS_DISPONIBLES[i].idUMF + "'>" + UMFS_DISPONIBLES[i].descripcion + "</option>";
					}
				}
				//invocamos funcion que sete los datos
				terminarYLlamarFuncion(idSelect, options,funcionOk, parametrosOk);
			} else {
				//En caso de que no haya resultados desbloqueamos la pantalla y seteamos las opciones
				terminarYLlamarFuncion(idSelect,options,funcionSinDatos, null);
			}
			//En caso de ocurrir un error
		}).error(function(data){
			terminarYLlamarFuncion(idSelect,options,funcionError, null);
		});
	}
}

/**
 * Function para obtener los turnos disponibles para una Umf
 * @param options.idUmf - id de la umf para buscar los turno en los que tiene medicos
 * @param options.idSelect - El id del select que contendra los turnos, debe venir sin #
 * @param options.idTurnoSeleccionado - El id del turno que se elegira por default, puede venir nulo
 * @param options.funcionOk - Funcion que se ejecutara en caso de que se encuentren datos, puede ser nula
 * @param options.parametrosOk - Parametros que se le pasaran a la funcion funcionOk
 * @param options.funcionSinDatos - Funcion que se ejecutara en caso de que no se encuentre ninguna umf relacionada al CP
 * @param options.funcionError - Funcion que se ejecutara en caso de que ocurra algun error al consultar las UMFs
 */
function findTurnosByUmf(options) {
	
	
	var opciones = $.extend({},OPCIONES_DEFAULT_TURNO_POR_UMF,options);
	
	var idUmf= opciones.idUmf;
	var idSelect= opciones.idSelect;
	var idTurnoSeleccionado= opciones.idTurnoSeleccionado;
	var funcionOk= opciones.funcionOk;
	var parametrosOk= opciones.parametrosOk;
	var funcionSinDatos= opciones.funcionSinDatos;
	var funcionError= opciones.funcionError;
	
	//verificamos si el turno seleccionado es nulo de ser asi la ponemos en blanco
	idTurnoSeleccionado = idTurnoSeleccionado == null ? "" : idTurnoSeleccionado;
	
	//url para consultar las umfs por codigo postal
	var url = CONTEXT_PATH_APLICACION + "/umf/getTurnosByUmf";
	var options = "<option value='-1'>--Selecciona por favor--</option>";
	
	//validamos que venga una Umf
	if(idUmf != "" && idUmf != "-1") {
		//Bloqueamos la pantalla
		$.blockUI();
		
		//Hacemos la llamada json
		$.postJSON(url, {'idUMF': idUmf}, function(result) {
			
			var turnos = result;
			
			if(turnos != null && turnos.length > 0) {
				//recorremos las umfs que encontramos
				for(var i = 0 ; i < turnos.length ; i++){
					//verificamos si es la umf seleccionada
					if(idTurnoSeleccionado == turnos[i].idTurno ) {
						options += "<option value='" + turnos[i].idTurno + "'  selected='selected'>" + turnos[i].descripcion + "</option>";
					}else{
						options += "<option value='" + turnos[i].idTurno + "'>" + turnos[i].descripcion + "</option>";
					}
				}
				
				terminarYLlamarFuncion(idSelect,options,funcionOk, parametrosOk);
			} else {
				//En caso de que no haya resultados desbloqueamos la pantalla y seteamos las opciones
				terminarYLlamarFuncion(idSelect,options,funcionSinDatos, null);
			}
			//En caso de ocurrir un error
		}).error(function(data){
			terminarYLlamarFuncion(idSelect,options,funcionError, null);
		});
	}
}

/**
 * Function para obtener los consultorios disponibles para una umf en un turno especifico
 * @param idUmf - id de la umf para buscar los turno en los que tiene medicos
 * @param idTurno - El id del turno en el que se buscaran consultorios
 * @param idSelect - El id del select que contendra los turnos, debe venir sin #
 * @param idConsultorioSeleccionado - El id del consultorio que se elegira por default, puede venir nulo
 * @param mostrarVirtuales - 1 si quiere que se muestren los consultorios virtuales, 0 si no, null si no
 * @param funcionOk - Funcion que se ejecutara en caso de que se encuentren datos, puede ser nula
 * @param parametrosOk - Parametros que se le pasaran a la funcion funcionOk
 * @param funcionSinDatos - Funcion que se ejecutara en caso de que no se encuentre ninguna umf relacionada al CP
 * @param funcionError - Funcion que se ejecutara en caso de que ocurra algun error al consultar las UMFs
 */
function findConsultoriosByUmfTurno(options) {
	
	var opciones = $.extend({},OPCIONES_DEFAULT_CONSULTORIO_POR_UMF_TURNO,options);
	
	var idUmf= opciones.idUmf;
	var idTurno= opciones.idTurno;
	var idSelect= opciones.idSelect;
	var idConsultorioSeleccionado= opciones.idConsultorioSeleccionado;
	//verificamos si el turno seleccionado es nulo de ser asi la ponemos en blanco
	idConsultorioSeleccionado = idConsultorioSeleccionado == null ? "" : idConsultorioSeleccionado;
	var mostrarVirtuales= opciones.mostrarVirtuales;
	var funcionOk= opciones.funcionOk;
	var parametrosOk= opciones.parametrosOk;
	var funcionSinDatos= opciones.funcionSinDatos;
	var funcionError= opciones.funcionError;
	
	//url para la busqueda de los consultorio
	var url = CONTEXT_PATH_APLICACION + "/umf/getConsultorios";
	var parametros = {
			'unidadMedicaFamiliar': {
				'idUMF': idUmf
			},
			'turno': {
				'idTurno': idTurno
			},
			'consultorio' : {
				'consultorioVirtual' : mostrarVirtuales
			}
	};
	
	//verificamos que los parametros de umf y turno no vengan vacios o con un valor invalido
	if(idUmf != "" && idUmf != "-1" && idTurno != "" && idTurno != "-1") {
		
		$.blockUI();
		//HAcemos la peticion
		$.postJSON(url, parametros, function(result) {
			var options = "<option value='-1'>--Selecciona por favor--</option>";
			
			//Verificamos que la consulta regrese consultorios
			if(result != null && result.length > 0) {
				for(var i = 0 ; i < result.length ; i++){
					if(idConsultorioSeleccionado == result[i].idConsultorio)
						options += "<option value='" + result[i].idConsultorio + "' selected='selected'>" + result[i].descripcion + "</option>";
					else
						options += "<option value='" + result[i].idConsultorio + "'>" + result[i].descripcion + "</option>";
				}
				//en caso de que regrese consultorios ejecutamos la funcion
				terminarYLlamarFuncion(idSelect, options, funcionOk, parametrosOk);
				
			} else {
				//En caso de que no haya resultados desbloqueamos la pantalla y seteamos las opciones
				terminarYLlamarFuncion(idSelect,options,funcionSinDatos, null);
			} 
		}).error(function(data){
			terminarYLlamarFuncion(idSelect,options,funcionError, null);
		});;
	}
	
}

/**
 * Metodo para obtener el consultorio con menod poblacion
 * @param options.idUmf
 * @param options.idTurno
 * @param options.funcionSeter - a esta funcion se la pasará el consultorio obucado
 * @param options.funcionsindatos - que hacer en caso de no encontrar consultorios
 * @param options.functionError - que hacer en caso de que ocurra algun error
 */
function getConsultorioMenorPoblacion(options) {
	
	var opciones = $.extend({},OPCIONES_DEFAULT_CONSULTORIO_MENOR_POBLACION,options);
	var idUmf = opciones.idUmf;
	var idTurno = opciones.idTurno;
	var mostrarVirtuales= opciones.mostrarVirtuales;
	var funcionSeter = opciones.funcionSeter;
	var funcionsindatos = opciones.funcionsindatos;
	var functionError = opciones.functionError;
	
	//url para la busqueda de los consultorio
	var url = CONTEXT_PATH_APLICACION+"/umf/getConsultorioMenorPoblacion";
	var parametros = {
		'unidadMedicaFamiliar': {
			'idUMF': idUmf
		},
		'turno': {
			'idTurno': idTurno
		},
		'consultorio' : {
			'consultorioVirtual' : mostrarVirtuales
		}
	};
	
	//verificamos que los parametros de umf y turno no vengan vacios o con un valor invalido
	if(idUmf != "" && idUmf != "-1" && idTurno != "" && idTurno != "-1") {
		
		//se bloquea la pantall mientras se hace la busqueda del medico
		$.blockUI();
		//Se hace la llamada
		$.postJSON(url, parametros, function(result) {
			//Se verifica que la consulta haya regresado datos
			if(result != null) {
				//Se invoca la funcion seter a la que se enviara el medico encontrado
				terminarYLlamarFuncion(null, null, funcionSeter, result);
			} else {
				terminarYLlamarFuncion(null, null, funcionsindatos, null);
			}
		}). error( function(data) {
			terminarYLlamarFuncion(null, null, funcionError, null)
		})
	}
}

/**
 * Function para obtener los consultorios disponibles para una umf en un turno especifico
 * @param options.idUmf - id de la umf para buscar los turno en los que tiene medicos
 * @param options.idTurno - El id del turno en el que se buscaran consultorios
 * @param options.idSelect - El id del select que contendra los turnos, debe venir sin #
 * @param options.idConsultorio - El id del consultorio que se eligio
 * @param options.funcionSeter - Funcion que se ejecutara en caso de que se encuentren datos y a la que se le pasara un objeto, puede ser nula
 * @param options.funcionSinDatos - Funcion que se ejecutara en caso de que no se encuentre ninguna umf relacionada al CP
 * @param options.funcionError - Funcion que se ejecutara en caso de que ocurra algun error al consultar las UMFs
 */
function getMedicobyUmfTurnoConsultorio(options) {
	
	var opciones = $.extend({},OPCIONES_DEFAULT_MEDICO_POR_UMF_TURNO_CONSULTORIO,options);
	var idUmf=opciones.idUmf;
	var idTurno=opciones.idTurno;
	var idConsultorio=opciones.idConsultorio;
	var funcionSeter=opciones.funcionSeter;
	var funcionSinDatos=opciones.funcionSinDatos;
	var funcionError=opciones.funcionError;
	
	//url de la peticion
	var url = CONTEXT_PATH_APLICACION + "/umf/getMedicosUmfTurno";
	//parametros
	var parametros = {
		'unidadMedicaFamiliar': {
			'idUMF': idUmf
		},
		'turno': {
			'idTurno': idTurno
		},
		'consultorio': {
			'idConsultorio': idConsultorio
		}
	};
	//se bloquea la pantall mientras se hace la busqueda del medico
	$.blockUI();
	//Se hace la llamada
	$.postJSON(url, parametros, function(result) {
		//Se verifica que la consulta haya regresado datos
		if(result != null && result.length > 0) {
			//Se invoca la funcion seter a la que se enviara el medico encontrado
			terminarYLlamarFuncion(null, null, funcionSeter, result[0]);
		} else {
			terminarYLlamarFuncion(null, null, funcionSinDatos, null);
		}
	}). error( function(data) {
		terminarYLlamarFuncion(null, null, funcionError, null)
	});
}

function terminarYLlamarFuncion(idHtml,options,funcion, parametrosFuncion) {
	//Desbloqueamos la pantalla y establecemos los datos
	$.unblockUI();
	
	if(idHtml != null) {
		$("#"+idHtml).html(options);
	}
	//En caso de que la funcion ok no sea nula la invocamos
	if(funcion != null) {
		if(parametrosFuncion != null) {
			funcion(parametrosFuncion);
		} else {
			funcion();
		}
	}
}

/**
 * funcion para limpiar los datos de la umf
 * @param idUmf
 * @param idTurno
 * @param idConsultorio
 * @param consultorioSelect
 */
function limpiarDatosUmf(idUmf, idTurno, idConsultorio, consultorioSelect) {
	UMFS_DISPONIBLES = null;
	var mensaje = "<option value='-1'>--Selecciona por favor--</option>";
	if(existeElemento(idUmf)) {
		$("#"+idUmf).html(mensaje);
	} 
	
	if(existeElemento(idTurno)) {
		$("#"+idTurno).html(mensaje);
	}
	
	if(existeElemento(idConsultorio)) {
		if(consultorioSelect != null && consultorioSelect) {
			$("#"+idConsultorio).html(mensaje);
		} else {
			$("#"+idConsultorio).val("");
		}
	}
}

/**
 * funcion para verificar si existe un elemento dentro de un formulario
 * @param idElemento
 * @returns {Boolean}
 */
function existeElemento(idElemento) {
	if(idElemento != null && $("#"+idElemento).length > 0) {
		return true;
	}
	
	return false;
}