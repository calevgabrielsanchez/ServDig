var indexSocioAlta;
var arraySociosAlta = new Array();
var oDialogDetalleSociosAlta;
var dtSociosAlta;
var sIdDialgoAgregarSocioAlta = "#dgAgregarSocioAlta";
var sIdDialgoModificarSocioAlta = "#dgModificarSocioAlta";
var oDialogAgregarSocioAlta;
var oDialogModificarSocioAlta;
var obRowSelectedToModifySocioAlta;

// seccion de inicializacion
$(function() {
	construirDialogoAgregarSocioAlta();
	construirDialogoModificarSocioAlta();
});

var columnasSocio = [ 
              	{			            	   
              	   	"mDataProp" : "idPersona",
              	   	"bVisible": false
                },
                {			            	   
              		"mDataProp" : "idSocio",
              		"bVisible": false
              	},
              	{ 
              		"sTitle" : "RFC",
              		"mDataProp" : "rfc"
              	},
              	{ 
              		"sTitle" : "CURP",
              		"mDataProp" : "curp"
              	},
              	{ 
              		"mDataProp" : "tipoSocio.idTipoPersona",
              		"bVisible": false
              	},
              	{
              		"sTitle" : "Tipo de Socio",
              		"mDataProp" : "tipoSocio.descripcion"
              	},
              	{ 
              		"sTitle" : "Nombre / Raz\u00F3n Social",
              		"mDataProp" : fnRenderSocioNombreRazonSocial
              	},
              	{ 
              		"sTitle" : "Nacionalidad",
              		"mDataProp" : "esNacional",
              		"fnRender": 
              			function ( oObj ) {	
              				return parseIndicadorNacionalidad(	oObj.aData.esNacional);
              			}
              	},
              	{ 
              	"sTitle" : "Residencia",
              	"mDataProp" : "esDomicilioNacional",
              	"fnRender": 
              		function ( oObj ) {	
              			return parseIndicadorResidencia(	oObj.aData.esDomicilioNacional);
              		}
              	
              	},
              	/*{ 
              		"sTitle" : "",					
              		"fnRender": function ( oObj ) {						
              			indexSocio=oObj.aData.idPersona;
              			arrayDatosSocio[indexSocio]=oObj.aData;						
              			return construyeLigaSocio(indexSocio);
              		}
              	},*/
              	{			            	   
              	   	"mDataProp" : "primerApellido",
              	   	"bVisible": false
                },
              	{			            	   
              	   	"mDataProp" : "segundoApellido",
              	   	"bVisible": false
                },
              	{			            	   
              	   	"mDataProp" : "nombres",
              	   	"bVisible": false
                }
              	
];

function fnRenderSocioNombreRazonSocial(oObj){
	if (oObj.nombres != null
			&& oObj.nombres != undefined) {
		return oObj.nombres+" " + oObj.primerApellido + " " + oObj.segundoApellido;
	} else {
		return oObj.nombreRazonSocial;
	}
}

function parseIndicadorNacionalidad( o ) {
	if (o == false){
		return 'Extranjero';
	}else{
		return 'Nacional';
	}
}

function parseIndicadorResidencia( r ) {
	if (r == false){
		return 'Extranjera';
	}else{
		return 'Nacional';
	}
}

function construyeLigaSocio( object ){
	return "<a href='#' onclick='showDetalleSocio("+object+")'>Mostrar Detalle</a>";
}

function construirGridSociosAlta(){
	dtSociosAlta = $('#gridSociosAlta').dataTable({
		bJQueryUI: false,
		bPaginate: true,
		bLengthChange: false,
		iDisplayLength: 5,
		bFilter: false,
		bSort: false,
		bInfo: false,
		bAutoWidth: false,
		//bServerSide : true,			
		aoColumns : columnasSocio,
		sPaginationType: "full_numbers",
		bProcessing : true,
		sAjaxSource : context_path + '/afiliacion/alta/visualizarSocios',
		fnServerData : enviarParametrosSocios
	});
}

function construirDialogoAgregarSocioAlta(){
	oDialogAgregarSocioAlta = 	$( sIdDialgoAgregarSocioAlta).dialog({
		autoOpen:false,
		resizable: false,
		width:920,
		height:650,
		modal: true,
		buttons: {
			"Guardar": function(){
				fnAgregarSocioAlta();
			},
			'Cancelar': function(){
					$( this ).dialog( "close" );
					limpiarFormularioAgregarSocioAlta();
			}
		}
	});
}

function construirDialogoModificarSocioAlta(){
	oDialogModificarSocioAlta = 	$(sIdDialgoModificarSocioAlta).dialog({
		autoOpen:false,
		resizable: false,
		width:920,
		height:650,
		modal: true,
		buttons: {
			"Guardar": function(){
				fnModificarSocioAlta();
			},
			'Cancelar': function(){
					$( this ).dialog( "close" );
			}
		}
	});
}


function enviarParametrosSocios(sSource, aoData, fnCallback) {
	var wrapper = new Object();
	wrapper.aoData = aoData;
	var oForm = new Object();

	wrapper.oForm = oForm;
	
	$.postJSON(sSource, wrapper, function(data){								
		fnCallback(data);
	});
}

function fnAbrirDialogoAgregarSocioAlta(){
	oDialogAgregarSocioAlta.dialog('open');
}

function fnAbrirDialogoEliminarSocioAlta(){
	if(fnValidaRegistroSeleccionado( dtSociosAlta )){
		showPromptDialog("\u00BFDesea eliminar el elemento seleccionado de la lista?", eliminarSocioAlta);
	} else {
		showErrorDialog("Antes debe seleccionar un Socio de la lista para poder eliminarlo.");
	}
}

function fnAbrirDialogoModificarSocioAlta(){
	if(fnValidaRegistroSeleccionado( dtSociosAlta )){
		// recuperamos los datos del elemento seleccionado en el grid para ser visualizados en la pantalla de modificacion
		obRowSelectedToModifySocioAlta = fnGetRowSelected(dtSociosAlta);
	
		//$("div#dgModificarSocioAlta input#representanteLegalAux\\.personaFisica\\.rfc").val(obRowSelectedToModifySocioAlta.personaFisica.rfc);
		// mostramos pantalla de modificacion
		oDialogModificarSocioAlta.dialog('open');
	} else {
		showErrorDialog("Antes debe seleccionar un Socio de la lista para poder modificar sus datos.");
	}
}

function fnAgregarSocioAlta(){
	
	
	// Verificamos que la información requerida esté completa
	/*if (($("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.rfc").val() != undefined && $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.rfc").val() != "") &&
			($("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val() != undefined && $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val() != "") &&
			($("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.nombre").val() != undefined && $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.nombre").val() != "") &&
			($("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.primerApellido").val() != undefined && $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.primerApellido").val() != "") &&
			($("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.segundoApellido").val() != undefined && $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.segundoApellido").val() != "")){*/
	if(true){
		
		// verificamos que no exista un Socio en la lista previamente registrado
		//if (fnExisteSocioenLista($("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.rfc").val(), $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val())){
		if (true){
			showErrorDialog("Ya existe un Socio registrado previamente, verifique su informaci\u00F3n.");
			return;
		} else {
			var sujetoTramiteObj = $("form#altaPatronalForm").toObject(true);
			var socioAux = new Object();
						
			sujetoTramiteObj.socioAux = socioAux;
			/*sujetoTramiteObj.representanteLegalAux.personaFisica.rfc = $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.rfc").val();
			sujetoTramiteObj.representanteLegalAux.personaFisica.curp = $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val();
			sujetoTramiteObj.representanteLegalAux.personaFisica.nombre = $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.nombre").val();
			sujetoTramiteObj.representanteLegalAux.personaFisica.primerApellido = $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.primerApellido").val();
			sujetoTramiteObj.representanteLegalAux.personaFisica.segundoApellido = $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.segundoApellido").val();
			sujetoTramiteObj.representanteLegalAux.indActAdmonDominio = ((document.getElementById("indActAdmonDominioAltaAgregar").checked == true) ? 1 : 0);*/
			
			fnAgregarNuevaFilaSocioAlta(sujetoTramiteObj, limpiarFormularioAgregarSocioAlta);
		}
	} else {
		showErrorDialog("Informaci\u00F3n incompleta, verifique los datos a registrar.");
	}
}

function fnModificarSocioAlta(){
	// Verificamos que la información requerida esté completa
	/*if (($("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.rfc").val() != undefined && $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.rfc").val() != "") &&
			($("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val() != undefined && $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val() != "") &&
			($("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.nombre").val() != undefined && $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.nombre").val() != "") &&
			($("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.primerApellido").val() != undefined && $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.primerApellido").val() != "") &&
			($("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.segundoApellido").val() != undefined && $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.segundoApellido").val() != "")){*/
	if(true){
		
		// verificamos que no exista un Socio en la lista previamente registrado
		//if (fnExisteSocioenListaParaModificacion($("div#dgModificarSocioAlta input#socioAux\\.personaFisica\\.rfc").val(), $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val())){
		if(true){
			showErrorDialog("Ya existe un Socio registrado previamente, verifique su informaci\u00F3n.");
			return;
		} else {
			var sujetoTramiteObj = $("form#altaPatronalForm").toObject(true);
			var socioAux = new Object();
						
			sujetoTramiteObj.socioAux = socioAux;
			/*sujetoTramiteObj.representanteLegalAux.personaFisica.rfc = $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.rfc").val();
			sujetoTramiteObj.representanteLegalAux.personaFisica.curp = $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val();
			sujetoTramiteObj.representanteLegalAux.personaFisica.nombre = $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.nombre").val();
			sujetoTramiteObj.representanteLegalAux.personaFisica.primerApellido = $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.primerApellido").val();
			sujetoTramiteObj.representanteLegalAux.personaFisica.segundoApellido = $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.segundoApellido").val();
			sujetoTramiteObj.representanteLegalAux.indActAdmonDominio = ((document.getElementById("indActAdmonDominioAltaModificar").checked == true) ? 1 : 0);*/
			
			fnModificarFilaSocioAlta(sujetoTramiteObj);
		}
	} else {
		showErrorDialog("Informaci\u00F3n incompleta, verifique los datos a registrar.");
	}
	
}

function eliminarSocioAlta(){
	$(dtSociosAlta.fnSettings().aoData).each(function (){
        if( $(this.nTr).hasClass('row_selected')){
        	dtSociosAlta.fnDeleteRow(this.nTr.sectionRowIndex);
        }
    });
}

function fnAgregarNuevaFilaSocioAlta(newRow, callback){
	var socio = new Object();
	//repLegal.tipoPersonaRepresentada = new Object();
	
	// setting data to Socio
	/*repLegal.cveIdPersona = 1;
	repLegal.personaFisica.idPersona = 1;
	repLegal.personaFisica.rfc = newRow.representanteLegalAux.personaFisica.rfc;
	repLegal.personaFisica.curp = newRow.representanteLegalAux.personaFisica.curp;
	repLegal.personaFisica.nombre = newRow.representanteLegalAux.personaFisica.nombre;
	repLegal.personaFisica.primerApellido = newRow.representanteLegalAux.personaFisica.primerApellido;
	repLegal.personaFisica.segundoApellido = newRow.representanteLegalAux.personaFisica.segundoApellido;
	repLegal.tipoPersonaRepresentada.idTipoPersona = 1;
	repLegal.cveIdRepresentanteLegal = 1;
	repLegal.indActAdmonDominio = newRow.representanteLegalAux.indActAdmonDominio;*/
	
	dtSociosAlta.fnAddData(socio);
	callback();
	oDialogAgregarSocioAlta.dialog('close');
}

function fnModificarFilaSocioAlta(modifiedRow){
	var socio = new Object();
	
	
	// setting data to Representante LEgal
	/*repLegal.cveIdPersona = 1;
	repLegal.personaFisica.idPersona = 1;
	repLegal.personaFisica.rfc = modifiedRow.representanteLegalAux.personaFisica.rfc;
	repLegal.personaFisica.curp = modifiedRow.representanteLegalAux.personaFisica.curp;
	repLegal.personaFisica.nombre = modifiedRow.representanteLegalAux.personaFisica.nombre;
	repLegal.personaFisica.primerApellido = modifiedRow.representanteLegalAux.personaFisica.primerApellido;
	repLegal.personaFisica.segundoApellido = modifiedRow.representanteLegalAux.personaFisica.segundoApellido;
	repLegal.tipoPersonaRepresentada.idTipoPersona = 1;
	repLegal.cveIdRepresentanteLegal = 1;
	repLegal.indActAdmonDominio = modifiedRow.representanteLegalAux.indActAdmonDominio;*/
	
	if (reemplazarSocioPorModificacion(dtSociosAlta, socio)){
		oDialogModificarSocioAlta.dialog('close');
	} else {
		showErrorDialog("Ha ocurrido un error al actualizar la informaci\u00F3n del socio con RFC: ?, notifique al administrador del sistema.");
	}
	
	
}

function fnExisteSocioenLista(rfc, curp){
	var result = false;
	$(dtSociosAlta.fnSettings().aoData).each(function (){
        //if (rfc == this._aData.personaFisica.rfc && curp == this._aData.personaFisica.curp){
		if(false){
			result = true;
		}
    });
	return result;
}

function fnExisteSocioenListaParaModificacion(rfc, curp){
	var result = false;
	var counter = 0;
	$(dtSociosAlta.fnSettings().aoData).each(function (){
        //if (rfc == this._aData.personaFisica.rfc && curp == this._aData.personaFisica.curp){
		if(false){
			counter++;
		}
    });
	
	// asumiendo que este rfc y curp no cambiaron, se concerva la fila
	//if(rfc == obRowSelectedToModifyRLAlta.personaFisica.rfc && curp == obRowSelectedToModifyRLAlta.personaFisica.curp){
	if(false){
		counter--;
	}
	
	if (counter > 0){ 
		result = true;
	}
	
	return result;
}

function limpiarFormularioAgregarSocioAlta(){
	/*$("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.rfc").val("");
	$("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val("");
	$("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.nombre").val("");
	$("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.primerApellido").val("");
	$("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.segundoApellido").val("");
	$("div#dgAgregarRepresentanteLegalAlta #indActAdmonDominioAltaAgregar").removeAttr("checked");*/
}

function callbackGuardarSociosAlta(){
	showMessageDialog("callbackGuardarSociosAlta invocao, se han guardado los Socios");
}

function getSociosAltaAsList() {
	var data;
	var listaSocios = [];
	var socio;
	var personaFisica;
	var dataOrigen = dtSociosAlta.fnSettings().aoData;
	
	for (registro in dataOrigen) {
		data = dataOrigen[registro]._aData;
		socio = new Object();
		personaFisica = new Object();
		//socio
		//personaFisica.nombre = data["personaFisica"]["nombre"];
		
//		socio.personaFisica = personaFisica;
		
		listaSocios.push(socio);
	}
	return listaSocios;
}


function reemplazarSocioPorModificacion(dtSociosAltaLocal, socioLocal){
	
	var obRowSelected = fnGetRowSelected(dtSociosAltaLocal);
	if (obRowSelected == undefined) {
		showErrorDialog("no se pudo recuperar el elemento del grid para su modificaci\u00F3n");
	}else{
		showMessageDialog("reemplazarSocioPorModificacion: pendiente asignación de datos al socio y actualización o reemplazo en el datatable")
		/*obRowSelected.personaFisica.rfc = repLegalLocal.personaFisica.rfc;
		obRowSelected.personaFisica.curp = repLegalLocal.personaFisica.curp;
		obRowSelected.personaFisica.nombre = repLegalLocal.personaFisica.nombre;
		obRowSelected.personaFisica.primerApellido = repLegalLocal.personaFisica.primerApellido;
		obRowSelected.personaFisica.segundoApellido = repLegalLocal.personaFisica.segundoApellido;
		obRowSelected.indActAdmonDominio = repLegalLocal.indActAdmonDominio;
		dtRepresentanteLegalAlta.fnUpdate(obRowSelected, aPos, 0, false, false);*/
	
	}
	return true;
}

