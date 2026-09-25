
/**
 * 
 * @param idSelect
 * @param idParentescoSeleccionado
 * @param funcionOk
 * @param parametrosOk
 */
function getParentescosValidos(idSelect, idParentescoSeleccionado, funcionOk, parametrosOk) {
	
	$.blockUI();
	idParentescoSeleccionado = idParentescoSeleccionado != undefined ? idParentescoSeleccionado : "";
	
	//url para consultar las umfs por codigo postal
	var url = "/${mvn.web.app.root}/tramite/registro/combo/parentesco";
	var options = "<option value='-1'>--Seleccione por favor--</option>";
	
	$.postJSON(url,null, function(result) {
		
		var parentescos = result.parentesco;
		if(parentescos != null && parentescos.length > 0) {
			for(var i = 0 ; i < parentescos.length ; i++){
				//verificamos si es el parentesco seleccionado
				if(idParentescoSeleccionado == parentescos[i].idParentesco ) {
					options += "<option value='" + parentescos[i].idParentesco + "'  selected='selected'>" + parentescos[i].descripcion + "</option>";
				}else{
					options += "<option value='" + parentescos[i].idParentesco + "'>" + parentescos[i].descripcion + "</option>";
				}
			}
			
			//invocamos funcion que sete los datos
			terminarYLlamarFuncion(idSelect, options,funcionOk, parametrosOk);
		}
		
		//En caso de ocurrir un error
	}).error(function(data){
		$.unblockUI();
	});
	
}



function getRazonesRegistroPorParentesco(idSelect, idParentesco,idRazonSeleccionada, funcionOk, parametrosOk) {
	
	$.blockUI();
	
	idParentesco = idParentesco == "-1" || idParentesco == "" ? null : idParentesco ;
	idRazonSeleccionada = idRazonSeleccionada == "-1" || idRazonSeleccionada == "" ? null : idRazonSeleccionada;
	var tramite = {
		'parentesco' : {idParentesco: idParentesco},
		'razonRegistro' : {idRazonRegistro : idRazonSeleccionada}
	};
	//url para consultar las umfs por codigo postal
	var url = "/${mvn.web.app.root}/tramite/registro/combo/razonRegistro";
	var options = "<option value='-1'>--Seleccione por favor--</option>";
	
	$.postJSON(url,tramite, function(result) {
		
		var razones = result.razonRegistro;
		if(razones != null && razones.length > 0) {
			for(var i = 0 ; i < razones.length ; i++){
				//verificamos si es el parentesco seleccionado
				if(idRazonSeleccionada == razones[i].idRazonRegistro ) {
					options += "<option value='" + razones[i].idRazonRegistro + "'  selected='selected'>" + razones[i].descripcion + "</option>";
				}else{
					options += "<option value='" + razones[i].idRazonRegistro + "'>" + razones[i].descripcion + "</option>";
				}
			}
			
			//invocamos funcion que sete los datos
			terminarYLlamarFuncion(idSelect, options,funcionOk, parametrosOk);
		}
		
		//En caso de ocurrir un error
	}).error(function(data){
		$.unblockUI();
	});
	
}

/**
 * 
 * @param idHtml
 * @param options
 * @param funcion
 * @param parametrosFuncion
 */
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