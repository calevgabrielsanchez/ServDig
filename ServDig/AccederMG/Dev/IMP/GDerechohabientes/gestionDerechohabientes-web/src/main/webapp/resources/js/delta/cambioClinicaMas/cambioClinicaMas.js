
var asentamientosUbicados;
var umfUbicados;
var medicosUbicados;
var medicosUbicadosV;
var medicosSelect;
var medicosSelectV;
var asentamientosSelect;
var numASent=0;
$(document).ready(function() {
	
	getUmfPorDelegacion();
	$("#buttonCambioClinicaMas").hide();
	
	$("#buttonCambioClinicaMas").click(dialogConfirmacionCambio);
	
	$("#cancelarCambioClinicaMasivo").click(cancelarCambioClinMas);
	
	$("#umfSelectOrigen").change(limpiarAsentamientos);
	
	$("#umfSelect").change(functionCambioClinicaDestino)
});

var functionCambioClinicaDestino = function() {
	asentamientosUbicados = null;
	var idUMFOrigen = $("#umfSelectOrigen").val();
	var idUMFDestino = $("#umfSelect").val();
	
	if(idUMFOrigen != "-1") {
		if(idUMFDestino == "-1") {
			limpiarAsentamientos();
		} else {
			if(idUMFDestino == idUMFOrigen) {
				dialogoMensajeError("Las UMF origen y destino no pueden ser la misma.");
				limpiarAsentamientos();
			} else {
				setUmf();
			}
		}
		
	} else {
		dialogoMensajeError("Es necesario elegir la UMF origen.")
		$("#umfSelect").val("-1");
	}
}

var limpiarAsentamientos = function() {
	$("#asentamientosField").hide();
	$("#buttonCambioClinicaMas").hide();
	$("#umfSelect").val("-1");
	$("#listaAsentamientosTable").html('');
	asentamientosUbicados = null;
}

function cambioController(){
	var url=context_path + "/cambioClinicaMas/cambioClinica";
	
	var medString="";
	var medStringV="";
	var asenString="";
	for(var i=0;i<medicosSelect.length;i++){
		if(i!=0){
			medString +=",";
			medStringV+=",";
			asenString +=",";
		}
		medString += medicosSelect[i];
		medStringV+= medicosSelectV[i];
		asenString += asentamientosSelect[i];
	}
	//console.debug(medString);
	//console.debug(medStringV);
	//console.debug(asenString);
	
		$.getJSON( url , 
				{
					medicosString:medString,
					medicosStringV:medStringV,
					asentamientosString:asenString,
					idUmfOrigen: $("#umfSelectOrigen").val(),
					idUmfDestino:$("#umfSelect").val()
				}, 
				function(data) { 
					dialogResultado(data.numDerMod,data.numSolMod);
					
				}).error(function(data){
						alert("Ocurri\u00f3 un error");
		});
}




//aprobadas
/**
 * llama al controlador getUmfsPorDelegacion para obtener las UmfCodigoPostal
 * Arma el combo
 */
function getUmfPorDelegacion(){

	
	var url = context_path + "/cambioClinicaMas/getUmfsPorDelegacion"
		$.getJSON( url , {}, function(data){
			setOptionsUmf(data, "#umfSelect");
			setOptionsUmf(data, "#umfSelectOrigen");
		}).error(function(data){
		});
	
}
/**
 * Arma el combo de Umfs
 * @param umf :lista de umfs
 * @param select :	idCombo
 */
function setOptionsUmf(umf, select){
	umfUbicados = umf;
	var options = "<option value='-1'> -- Por favor seleccione -- </option>";
	for(var i = 0 ; i < umf.length ; i++){
		options += "<option value='" + umf[i].idUMF + "'>" + umf[i].nombreCorto + "</option>";
	}
	$(""+select).html(options);
	
}
/**
 * Funcion del onselect del combo umfSelect
 * pinta detalle de la umf seleccionada
 * pinta la lista de Asentamientos
 */
function setUmf(){
	var index = $("#umfSelectOrigen")[0].selectedIndex;
	if(umfUbicados[index-1]!=null){
		var umf=umfUbicados[index-1];
		if(umf!=null){

			$("#asentamientosField").show();
			getAsentamientoPorUmf();
		}
	}else{
		$("#asentamientosField").hide();
		$("#buttonCambioClinicaMas").hide();
	}
	
	
}

function getAsentamientoPorUmf(){
		var url = context_path + "/cambioClinicaMas/getAsentamientosPorUmf"
		$.getJSON( url , {umfOrigen: $("#umfSelectOrigen").val()}, function(data){
			asentamientosUbicados = data;
			muestraMedicosUmf($("#umfSelect").val(),data);
		}).error(function(data){
		});
}


/**
 * llama el listado de medicos de la umf y lo asigna a la variable medicosUbicados
 * Pinta la tabla y opciones
 * @param idUmf
 */
function muestraMedicosUmf(idUmf,asentamientos){
	var url = context_path + "/umf/getConsultorios";
	var umfTurno = {
			'unidadMedicaFamiliar' : {
				'idUMF': idUmf
			} ,
			'turno' : {
				'idTurno' : 1
			}
	}
	$.postJSON( url , umfTurno , function(data){
		medicosUbicados=data;
		umfTurno.turno.idTurno = 2;
		$.postJSON( url , umfTurno , function(result){
			medicosUbicadosV = result;
			var options=pintaMedicosOptions(medicosUbicados);
			var optionsv = pintaMedicosOptions(medicosUbicadosV);
			pintaTablaSelAsentamientosMedicos(asentamientos,options, optionsv);
		});
	}).error(function(data){
	});
}
/**
 * arma la tabla de colonias
 * @param asentamientos 
 * @param options combos de medicos
 */
function pintaTablaSelAsentamientosMedicos(asentamientos,optionsM, optionsV){
	var tableContent = "<thead><tr align='left'><th>Selecci&oacute;n</th><th>Colonia</th><th>Consultorio Turno Matutino</th><th>Consultorio Turno Vespertino</th></tr></thead><tbody>";
	numASent=asentamientos.length;
	for(var i = 0 ; i < numASent ; i++){
		tableContent += "<tr>" +
							"<td><br><input id='asentamientosSelect" + i + "' name='idUmfList' type='checkbox' value='"+asentamientos[i].clave+"'  onClick='validaChecked("+i+");'/></td>" +
							"<td>"+ 
								"<b>Colonia: </b>"+ asentamientos[i].nombre +"<br>" +
								"<b>C.P. : </b>" + asentamientos[i].codigoPostal.codigoPostal +
							"</td>" +
						
						
							"<td><br><b>Consultorio: </b><select id='medicoMSelect" + i + "' name='idMedicoFamiliarList' disabled='disabled' onChange='setMedico(\"M\"," + i + ");'>" + optionsM + "</select><br>" +
								"<b>M&eacute;dico: </b><font id='medicoM" + i + "'>N/A</font><br>" +
								"<b>Poblaci&oacute;n: </b><font id='poblacionM" + i + "'>N/A</font>" +
								"<input type='hidden' id='idMedicoConsultorioM"+i+"' value=''/>" +
							"</td>" +
							
							"<td><br><b>Consultorio: </b><select id='medicoVSelect" + i + "' name='idMedicoFamiliarListV' disabled='disabled' onChange='setMedico(\"V\"," + i + ");'>" + optionsV + "</select><br>" +
								"<b>M&eacute;dico: </b><font id='medicoV" + i + "'>N/A</font><br>" +
								"<b>Poblaci&oacute;n: </b><font id='poblacionV" + i + "'>N/A</font>" +
								"<input type='hidden' id='idMedicoConsultorioV"+i+"' value=''/>"+
							"</td>" +
						"</tr>";
	}
	tableContent += "</tbody>";
	$("#listaAsentamientosTable").html(tableContent);
	
}



/**
 * Regresa la cadena de Options de los medicos
 * @returns {String}
 */
function pintaMedicosOptions(medicos){
	var options = "<option value=''> -- Por favor seleccione -- </option>";
	for(var i = 0 ; i < medicos.length ; i++){
		options += "<option value='" + medicos[i].idConsultorio + "'>" + medicos[i].descripcion + "</option>";
		//options += "<option value='" + medicos[i].idMedicoContultorioTurno + "'>" + medicos[i].consultorio.descripcion +  "</option>";
	}
	return options;
}

function setMedico(turno,i) {
	
	var idTurno = turno == 'M' ? 1 : 2;
	var idConsultorio = $("#medico"+turno+"Select" + i).val();
	
	if(idConsultorio == "") {
		$("#medico"+turno+""+ i).html("N/A");
		$("#poblacion"+turno+""+ i).html("N/A");
		$("#idMedicoConsultorio"+turno+""+i).val("");
		
		validaAsignaArreglos();
		
		return;
	}
	//url de la peticion
	var url = context_path + "/cambioClinicaMas/getMedicoPoblacion";
	//parametros
	var parametros = {
		'unidadMedicaFamiliar': {
			'idUMF': $("#umfSelect").val()
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
		
		var medicoTurno = result[0];
		if(medicoTurno.medicoFamiliar != null && medicoTurno.medicoFamiliar != undefined) {
			$("#medico"+turno+""+ i).html(medicoTurno.medicoFamiliar.nombre + " " + medicoTurno.medicoFamiliar.primerApellido + " " + medicoTurno.medicoFamiliar.segundoApellido);
		} else {
			$("#medico"+turno+""+ i).html("S/N");
		}
		$("#poblacion"+turno+""+ i).html("" + medicoTurno.poblacion);
		$("#idMedicoConsultorio"+turno+""+i).val(medicoTurno.idMedicoContultorioTurno);
		//pinta el boton para aceptar y asigna los valores a los array de peticiones
		validaAsignaArreglos();
		$.unblockUI();
	}). error( function(data) {
		
	});
	
}

function dialogoMensajeError(mensaje){

	$decision = $('<div></div');
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 150,
		title : 'Error',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.html(mensaje);
	$decision.dialog('open');

}

var dialogConfirmacionCambio = function(){

	if(validaAsignaArreglos()){
		dialogoConfirmacionDo();
	}else{
		dialogoMensajeError("Debe seleccionar el asentamiento y la cl\u00ednica");
	}

}

function dialogoConfirmacionDo(){
	$decision = $('<div></div');
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : 'Confirmaci\u00F3n',
		modal : true,
		buttons : {
		
			"Aceptar" : function() {
				cambioController();
				cierraDialogo($(this));
			},
			"Cancelar" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Esta seguro de aplicar el cambio De cl\u00ednica a las colonias seleccionadas?');
	$decision.dialog('open');
}

function dialogResultado(der,sol){

	$decision = $('<div></div');
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 250,
		title : 'Resultado',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				history.go(0);
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('Se han Aplicado los Cambios satisfactoriamente a ' + der + ' derechohabientes y '+ sol+ ' Solicitudes');
	$decision.dialog('open');

}

var cancelarCambioClinMas = function(){

	$decision = $('<div></div');
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : 'Selecciona una opci\u00F3n',
		modal : true,
		buttons : {
			"Si" : function() {
				location.href = "" + context_path + "/welcome/uno/busqueda";
			},
			"No" : function() {
				cierraDialogo($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('\u00BF Est\u00E1 seguro que desea cancelar el cambio de cl\u00ednica?');
	$decision.dialog('open');

}


function cierraDialogo($dialogo){
$dialogo.dialog('close');
$dialogo.dialog('destroy');
$dialogo.html('');
}


function validaChecked(indice) {
	var checado = $("#asentamientosSelect"+indice+"").attr("checked");
	if(checado) {
		$("#medicoMSelect"+indice+"").removeAttr("disabled");
		$("#medicoVSelect"+indice+"").removeAttr("disabled");
	} else {
		$("#medicoMSelect"+indice+"").attr("disabled","disabled");
		$("#medicoVSelect"+indice+"").attr("disabled","disabled");
	}
	validaAsignaArreglos();
}

function validaAsignaArreglos(){
	var ok=false;
	var j=0;
	var tamanoS=getSelectedLenght();
	asentamientosSelect=new Array(tamanoS);
	medicosSelect=new Array(tamanoS);
	medicosSelectV=new Array(tamanoS);
	var validation=new Array(tamanoS);
	var validationV=new Array(tamanoS);
	for(var i=0;i<numASent;i++){
		if($("#asentamientosSelect"+i).attr('checked')!=undefined){
			if((!$("#medicoMSelect"+i).val()=="")){
				validation[j]=true;
			}else{
				validation[j]=false;
			}
			
			if((!$("#medicoVSelect"+i).val()=="")){
				validationV[j]=true;
			}else{
				validationV[j]=false;
			}
			asentamientosSelect[j]=asentamientosUbicados[i].clave;
			medicosSelect[j]=$("#idMedicoConsultorioM"+i).val();
			medicosSelectV[j]=$("#idMedicoConsultorioV"+i).val();
			j=j+1;
		}
	}

	if(validation.length!=0){
		if(validationList(validation) && validationList(validationV)){
			$("#buttonCambioClinicaMas").show();
			ok=true;
		}else{
			$("#buttonCambioClinicaMas").hide();
			ok=false;
		}
	}else{
		$("#buttonCambioClinicaMas").hide();
		ok=false;
	}
	return ok;
}
 
function validationList(validation){
	var resp=true;
	var tam=validation.length;
	for(var i=0;i<tam;i++){
		if(!validation[i]){
			 resp=false;
		}
	}
	return resp;
}

function getSelectedLenght(){
	var cont=0;
	for(var i=0;i<numASent;i++){
		if($("#asentamientosSelect"+i).attr('checked')!=undefined){
			cont=cont + 1;
		}
	}	
	return cont;
}