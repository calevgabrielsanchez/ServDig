UMFS_DISPONIBLES = null;

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
function findUmfsByCodigoPostal(codigoPostal, idSelect, umfSeleccionada, funcionOk, parametrosOk, funcionSinDatos,funcionError) {
	
	//verificamos si la umf seleccionada es nula de ser asi la ponemos en blanco
	umfSeleccionada = umfSeleccionada == null ? "" : umfSeleccionada;
	
	//console.debug("la umf seleccionada es : %s",umfSeleccionada );
	
	//url para consultar las umfs por codigo postal
	var url = context_path + "/umf/getUmfsByCodigoPostalV";
	var options = "<option value='-1'>--Seleccione por favor--</option>";
	
	//Validamos que el codigo postal no venga vacio, nulo y que contenga al menos 5 digitos
	if(codigoPostal != "" && $.trim(codigoPostal).length == 5) {
		//Bloqueamos la pantalla
		$.blockUI();
		//Hacemos la llamada json
		$.postJSON(url, {'codigoPostal': codigoPostal}, function(result) {
			
			//Establecemos las umfs en una variable global para poder obtener despues sus datos
			UMFS_DISPONIBLES = result;
			
			if(UMFS_DISPONIBLES != null && UMFS_DISPONIBLES.length > 0) {
				var numeroUmfs = UMFS_DISPONIBLES.length;
				//recorremos las umfs que encontramos
				for(var i = 0 ; i <  numeroUmfs; i++){
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
 * @param idUmf - id de la umf para buscar los turno en los que tiene medicos
 * @param idSelect - El id del select que contendra los turnos, debe venir sin #
 * @param idTurnoSeleccionado - El id del turno que se elegira por default, puede venir nulo
 * @param funcionOk - Funcion que se ejecutara en caso de que se encuentren datos, puede ser nula
 * @param parametrosOk - Parametros que se le pasaran a la funcion funcionOk
 * @param funcionSinDatos - Funcion que se ejecutara en caso de que no se encuentre ninguna umf relacionada al CP
 * @param funcionError - Funcion que se ejecutara en caso de que ocurra algun error al consultar las UMFs
 */
function findTurnosByUmf(idUmf, idSelect, idTurnoSeleccionado, funcionOk, parametrosOk, funcionSinDatos,funcionError) {
	
	//verificamos si el turno seleccionado es nulo de ser asi la ponemos en blanco
	idTurnoSeleccionado = idTurnoSeleccionado == null ? "" : idTurnoSeleccionado;
	
	//url para consultar las umfs por codigo postal
	var url = context_path + "/umf/getTurnosByUmf";
	var options = "<option value='-1'>--Seleccione por favor--</option>";
	
	//validamos que venga una Umf
	if(idUmf != "" && idUmf != "-1") {
		//Bloqueamos la pantalla
		$.blockUI();
		
		//Hacemos la llamada json
		$.postJSON(url, {'idUMF': idUmf}, function(result) {
			
			var turnos = result;
			TURNOS_DISPONIBLES = turnos;
			
			if(turnos != null && turnos.length > 0) {
				var numeroTurnos = turnos.length;
				//recorremos las umfs que encontramos
				for(var i = 0 ; i <  numeroTurnos; i++){
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
 * @param funcionOk - Funcion que se ejecutara en caso de que se encuentren datos, puede ser nula
 * @param parametrosOk - Parametros que se le pasaran a la funcion funcionOk
 * @param funcionSinDatos - Funcion que se ejecutara en caso de que no se encuentre ninguna umf relacionada al CP
 * @param funcionError - Funcion que se ejecutara en caso de que ocurra algun error al consultar las UMFs
 */
function findConsultoriosByUmfTurno(idUmf, idTurno,idSelect, idConsultorioSeleccionado, funcionOk, parametrosOk, funcionSinDatos,funcionError) {
	
	//verificamos si el turno seleccionado es nulo de ser asi la ponemos en blanco
	idConsultorioSeleccionado = idConsultorioSeleccionado == null ? "" : idConsultorioSeleccionado;
	
	//url para la busqueda de los consultorio
	var url = context_path + "/umf/getConsultorios";
	var parametros = {
			'unidadMedicaFamiliar': {
				'idUMF': idUmf
			},
			'turno': {
				'idTurno': idTurno
			}
	};
	
	//verificamos que los parametros de umf y turno no vengan vacios o con un valor invalido
	if(idUmf != "" && idUmf != "-1" && idTurno != "" && idTurno != "-1") {
		
		$.blockUI();
		//HAcemos la peticion
		$.postJSON(url, parametros, function(result) {
			var options = "<option value='-1'> -- Por favor seleccione -- </option>";
			
			//Verificamos que la consulta regrese consultorios
			if(result != null && result.length > 0) {
				var numeroConsultorios = result.length;
				for(var i = 0 ; i <  numeroConsultorios; i++){
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
 * Function para obtener los consultorios disponibles para una umf en un turno especifico
 * @param idUmf - id de la umf para buscar los turno en los que tiene medicos
 * @param idTurno - El id del turno en el que se buscaran consultorios
 * @param idSelect - El id del select que contendra los turnos, debe venir sin #
 * @param idConsultorio - El id del consultorio que se eligio
 * @param funcionSeter - Funcion que se ejecutara en caso de que se encuentren datos y a la que se le pasara un objeto, puede ser nula
 * @param funcionSinDatos - Funcion que se ejecutara en caso de que no se encuentre ninguna umf relacionada al CP
 * @param funcionError - Funcion que se ejecutara en caso de que ocurra algun error al consultar las UMFs
 */
function getMedicobyUmfTurnoConsultorio(idUmf, idTurno, idConsultorio, funcionSeter, funcionSinDatos,funcionError) {
	//url de la peticion
	var url = context_path + "/umf/getMedicosUmfTurno";
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
	})
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