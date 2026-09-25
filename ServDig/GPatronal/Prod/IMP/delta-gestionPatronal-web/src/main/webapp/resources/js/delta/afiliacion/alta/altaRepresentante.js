var indexRepresentanteAlta;
var indexDTMediosContactoRLAlta;
var arrayRepresentantes = new Array();
var oDialogDetalleRepLegalAlta;
var sIdDialgoAgregarRepresentanteLegalAlta = "#dgAgregarRepresentanteLegalAlta";
var sIdDialgoModificarRepresentanteLegalAlta = "#dgModificarRepresentanteLegalAlta";
var sIdDialgoAgregarMedioContactoRLAlta = "#divMedioContactoAlta";
var oDialogAgregarRepresentanteLegalAlta;
var oDialogModificarRepresentanteLegalAlta;
var oDialogAgregarMedioContactoRLAlta;
var objRowSelectedToModifyRLAlta;


// seccion de inicializacion
$(function() {
	construirDialogoAgregarRLAlta();
	construirDialogoModificarRLAlta();
});

var columnasRepresentante = [ 
                {			            	   
                	"mDataProp" : "cveIdPersona",
                	"bVisible": false
                },
				{			            	   
                	
                	"mDataProp" : "personaFisica.idPersona",
                	"bVisible": false
                },
                {			            	   
                	"mDataProp" : "tipoPersonaRepresentada.idTipoPersona",
                	"bVisible": false
                },
				{			            	   
                	"mDataProp" : "cveIdRepresentanteLegal",
                	"bVisible": false
                },
                { 
                	"sTitle" : "RFC",
                	"mDataProp" : "personaFisica.rfc"
                },
                { 
                	"sTitle" : "CURP",
                	"mDataProp" : "personaFisica.curp"
                },	
                { 
                	"sTitle" : "Nombre",
                	"mDataProp" : renderRepresentanteNombre
                },
                { 
                	"sTitle" : "Actos de Administraci&oacute;n o dominio",
                	"mDataProp" : "indActAdmonDominio",
                	"fnRender": function ( oObj ) {						
                		return parseIndicadorActosAdmon(oObj.aData.indActAdmonDominio);
                	}
                }
                
];


var columnasMediosContactoRLAlta = [ 
{
	mDataProp : "tipoMedioContacto.idTipoMedioContacto",
	bVisible : false
},{
	"sTitle" : "Tipo de medio de contacto",
	"mDataProp" : "tipoMedioContacto.descripcion",
	sWidth : "200px"
}, {
	"sTitle" : "detalle",
	"mDataProp" : "desFormaContacto",
	sWidth : "200px"
} ];
                             
                             
function renderRepresentanteNombre(oObj){
	return oObj.personaFisica.nombre+" "+oObj.personaFisica.primerApellido+" "+oObj.personaFisica.segundoApellido;
}

function parseIndicadorActosAdmon( o ) {
	if (o == '1' || o == 1){
		return 'SI';		
		
	}else{
		return 'NO';		
	}
}

function construyeLigaRepresentante( object ){	
	return "<a href='#' onclick='showRepLegal("+object+")'>Mostrar Detalle</a>";
}

function construirGridRepresentateLegal(){
	dtRepresentanteLegalAlta = $('#gridRepresentantesAlta').dataTable({
		bJQueryUI: false,
		bPaginate: true,
		bLengthChange: false,
		iDisplayLength: 5,
		bFilter: false,
		bSort: false,
		bInfo: false,
		bAutoWidth: false,
		//bServerSide : true,			
		aoColumns : columnasRepresentante,
		sPaginationType: "full_numbers",
		bProcessing : true,
		sAjaxSource : context_path + '/afiliacion/alta/visualizarRepresentantes',
		fnServerData : enviarParametrosRepresentante
	});
}

function construirGridMediosContactoRLAltaEnAgregar(){
	dtMediosContactoRLAltaEnAgregar = $('#gridMediosContactoRLAltaEnAgregar').dataTable({
		bJQueryUI: false,
		bPaginate: true,
		bLengthChange: false,
		iDisplayLength: 5,
		bFilter: false,
		bSort: false,
		bInfo: false,
		bAutoWidth: false,
		//bServerSide : true,			
		aoColumns : columnasMediosContactoRLAlta,
		sPaginationType: "full_numbers",
		bProcessing : true,
		//sAjaxSource : context_path + '/afiliacion/alta/visualizarRepresentantes',
		//fnServerData : enviarParametrosRepresentante
	});
}

function construirGridMediosContactoRLAltaEnModificar(){
	dtMediosContactoRLAltaEnModificar = $('#gridMediosContactoRLAltaEnModificar').dataTable({
		bJQueryUI: false,
		bPaginate: true,
		bLengthChange: false,
		iDisplayLength: 5,
		bFilter: false,
		bSort: false,
		bInfo: false,
		bAutoWidth: false,
		//bServerSide : true,			
		aoColumns : columnasMediosContactoRLAlta,
		sPaginationType: "full_numbers",
		bProcessing : true,
		//sAjaxSource : context_path + '/afiliacion/alta/visualizarRepresentantes',
		//fnServerData : enviarParametrosRepresentante
	});
}

function construirDialogoAgregarRLAlta(){
	oDialogAgregarRepresentanteLegalAlta = 	$( sIdDialgoAgregarRepresentanteLegalAlta).dialog({
		autoOpen:false,
		resizable: false,
		width:1000,
		height:650,
		modal: true,
		closeOnEscape: false,
		buttons: {
			"Guardar": function(){
				fnAgregarRLAlta();
			},
			
			'Cancelar': function(){
					$( this ).dialog( "close" );
					limpiarFormularioAgregarRLAlta();
			}
		}
	});
	
	// se incluye la construccion del dialogo de agregar para los datos de contacto
	oDialogAgregarMedioContactoRLAlta = 	$( sIdDialgoAgregarMedioContactoRLAlta).dialog({
		title : "Gesti\u00F3n de Medios de Contacto",
		autoOpen:false,
		resizable: false,
		width:500,
		height:400,
		modal: true,
		closeOnEscape: false,
		buttons: {
			"Guardar": function(){
				ejecutarAccionMedioContactoRLAlta();
			},
			
			'Cancelar': function(){
					$( this ).dialog( "close" );
					limpiarFormularioAgregarMedioContactoRLAlta();
			}
		}
	});
	
}

function construirDialogoModificarRLAlta(){
	oDialogModificarRepresentanteLegalAlta = 	$(sIdDialgoModificarRepresentanteLegalAlta).dialog({
		autoOpen:false,
		resizable: false,
		width:920,
		height:650,
		modal: true,
		closeOnEscape: false,
		buttons: {
			"Guardar": function(){
				fnModificarRLAlta();
			},
			'Cancelar': function(){
					$( this ).dialog( "close" );
			}
		}
	});
}


function enviarParametrosRepresentante(sSource, aoData, fnCallback) {
	var wrapper = new Object();
	wrapper.aoData = aoData;
	var oForm = new Object();

	wrapper.oForm = oForm;
	
	$.postJSON(sSource, wrapper, function(data){								
		fnCallback(data);
	});
}

function fnAbrirDialogoAgregarRLAlta(){
	// limpiamos data tables de los datos de contacto del rep. legal (en agregar y en modificar)
	dtMediosContactoRLAltaEnAgregar.fnClearTable();
	dtMediosContactoRLAltaEnModificar.fnClearTable();

	// se muestra el dialogo para agregar un nuevo representante legal
	oDialogAgregarRepresentanteLegalAlta.dialog('open');
	// verificar si se abrirá el puto componente de personas
	//fnOpenBuscarPersonaFisica();
	
}

function fnAbrirDialogoEliminarRLAlta(){
	if(fnValidaRegistroSeleccionado( dtRepresentanteLegalAlta )){
		showPromptDialog("\u00BFDesea eliminar el elemento seleccionado de la lista?", eliminarRLAlta);
	} else {
		showErrorDialog("Antes debe seleccionar un Representante Legal de la lista para poder eliminarlo.");
	}
}

function fnAbrirDialogoModificarRLAlta(){
	if(fnValidaRegistroSeleccionado( dtRepresentanteLegalAlta )){
		// recuperamos los datos del elemento seleccionado en el grid para ser visualizados en la pantalla de modificacion
	objRowSelectedToModifyRLAlta = fnGetRowSelected(dtRepresentanteLegalAlta);
	
	$("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.rfc").val(objRowSelectedToModifyRLAlta.personaFisica.rfc);
	$("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val(objRowSelectedToModifyRLAlta.personaFisica.curp);
	$("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.nombre").val(objRowSelectedToModifyRLAlta.personaFisica.nombre);
	$("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.primerApellido").val(objRowSelectedToModifyRLAlta.personaFisica.primerApellido);
	$("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.segundoApellido").val(objRowSelectedToModifyRLAlta.personaFisica.segundoApellido);
	if (objRowSelectedToModifyRLAlta.indActAdmonDominio == "SI"){
		$("div#dgModificarRepresentanteLegalAlta input#indActAdmonDominioAltaModificar").attr("checked",  "checked");
	} else {
		$("div#dgModificarRepresentanteLegalAlta input#indActAdmonDominioAltaModificar").removeAttr("checked");
	}
		
		// pasamos los datos de contacto del elemento seleccionado
		dtMediosContactoRLAltaEnModificar.fnClearTable();
		dtMediosContactoRLAltaEnModificar.fnAddData(objRowSelectedToModifyRLAlta.mediosContacto);
		
		// mostramos pantalla de modificacion
		oDialogModificarRepresentanteLegalAlta.dialog('open');
		
	} else {
		showErrorDialog("Antes debe seleccionar un Representante Legal de la lista para poder modificar sus datos.");
	}
}

function fnAbrirDialogoAgregarMedioContactoRLAlta(){
	accionSobreMediosContacto = "agregar";
	ocultarFormulariosMediosContactoAlta();
	oDialogAgregarMedioContactoRLAlta.dialog('open');
}

function fnAbrirDialogoEliminarMedioContactoRLAlta(view){
	if (view == 'fromRLAltaAgregar'){
		if(fnValidaRegistroSeleccionado( dtMediosContactoRLAltaEnAgregar )){
			showPromptDialog("\u00BFDesea eliminar el elemento seleccionado de la lista?", fnEliminarMedioContactoRLAlta);
		} else {
			showErrorDialog("Antes debe seleccionar un dato de contacto de la lista para poder eliminarlo.");
		}
	} else if (view == 'fromRLAltaModificar'){
		if(fnValidaRegistroSeleccionado( dtMediosContactoRLAltaEnModificar )){
			showPromptDialog("\u00BFDesea eliminar el elemento seleccionado de la lista?", fnEliminarMedioContactoRLAlta);
		} else {
			showErrorDialog("Antes debe seleccionar un dato de contacto de la lista para poder eliminarlo.");
		}
	}
}

function fnAbrirDialogoModificarMedioContactoRLAlta(view){
	accionSobreMediosContacto = "modificar";
	if (view == 'fromRLAltaAgregar'){
		if(fnValidaRegistroSeleccionado( dtMediosContactoRLAltaEnAgregar )){
			mostrarFormModificacionMedioContacto(fnGetRowSelected(dtMediosContactoRLAltaEnAgregar));
		} else {
			showErrorDialog("Antes debe seleccionar un dato de contacto de la lista para poder modificarlo.");
		}
	} else if (view == 'fromRLAltaModificar'){
		if(fnValidaRegistroSeleccionado( dtMediosContactoRLAltaEnModificar )){
			mostrarFormModificacionMedioContacto(fnGetRowSelected(dtMediosContactoRLAltaEnModificar));
		} else {
			showErrorDialog("Antes debe seleccionar un dato de contacto de la lista para poder modificarlo.");
		}
	}
}


/*
* Reutilizamos el dialogo definido en este form para mostrar la modificación
*/
function mostrarFormModificacionMedioContacto(medioContactoToModify){
	ocultarFormulariosMediosContactoAlta();
	
	if (medioContactoToModify.tipoMedioContacto.idTipoMedioContacto == 1){
		$('#correoElectronico').show();
		$('#tipoMedioContacto\\.descripcion').val(medioContactoToModify.tipoMedioContacto.descripcion);
		$('#tipoMedioContacto\\.idTipoMedioContacto option:selected').text(medioContactoToModify.tipoMedioContacto.descripcion);
		$('#correoElectronico\\.correo').val(medioContactoToModify.desFormaContacto);
		$('#correoElectronico\\.correo').focus();
	} else if(medioContactoToModify.tipoMedioContacto.idTipoMedioContacto == 2){
		$('#telefonoFijo').show();
		$('#tipoMedioContacto\\.descripcion').val(medioContactoToModify.tipoMedioContacto.descripcion);
		$('#tipoMedioContacto\\.idTipoMedioContacto option:selected').text(medioContactoToModify.tipoMedioContacto.descripcion);
		$('#telefonoFijo\\.numero').val(medioContactoToModify.telefonoFijo.numero);
		$('#telefonoFijo\\.numero').focus();
	} else if(medioContactoToModify.tipoMedioContacto.idTipoMedioContacto == 3){
		$('#telefonoMovil').show();
		$('#tipoMedioContacto\\.descripcion').val(medioContactoToModify.tipoMedioContacto.descripcion);
		$('#tipoMedioContacto\\.idTipoMedioContacto option:selected').text(medioContactoToModify.tipoMedioContacto.descripcion);
		$('#telefonoMovil\\.numero').val(medioContactoToModify.telefonoMovil.numero);
		$('#telefonoMovil\\.numero').focus();
	} else if(medioContactoToModify.tipoMedioContacto.idTipoMedioContacto == 4){
		$('#facebook').show();
		$('#tipoMedioContacto\\.descripcion').val(medioContactoToModify.tipoMedioContacto.descripcion);
		$('#tipoMedioContacto\\.idTipoMedioContacto option:selected').text(medioContactoToModify.tipoMedioContacto.descripcion);
		$('#facebook\\.cuenta').val(medioContactoToModify.facebook.cuenta);
		$('#facebook\\.cuenta').focus();
	} else if(medioContactoToModify.tipoMedioContacto.idTipoMedioContacto == 5){
		$('#twitter').show();
		$('#tipoMedioContacto\\.descripcion').val(medioContactoToModify.tipoMedioContacto.descripcion);
		$('#tipoMedioContacto\\.idTipoMedioContacto option:selected').text(medioContactoToModify.tipoMedioContacto.descripcion);
		$('#twitter\\.cuenta').val(medioContactoToModify.twitter.cuenta);
		$('#twitter\\.cuenta').focus();
	}
	
	// para evitar modificar el tipo de medio de contacto, limitamos a que se modifique el detalle.
	$('#tipoMedioContacto\\.idTipoMedioContacto').prop("disabled", true);
	
	oDialogAgregarMedioContactoRLAlta.dialog('open');
}

function fnAgregarRLAlta(){
	
	
	// Verificamos que la información requerida esté completa
	if (($("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.rfc").val() != undefined && $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.rfc").val() != "") &&
			($("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val() != undefined && $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val() != "") &&
			($("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.nombre").val() != undefined && $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.nombre").val() != "") &&
			($("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.primerApellido").val() != undefined && $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.primerApellido").val() != "") &&
			($("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.segundoApellido").val() != undefined && $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.segundoApellido").val() != "")){
		
		// verificamos que no exista un rep legal en la lista previamente registrado
		if (fnExisteRLenLista($("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.rfc").val(), $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val())){
			showErrorDialog("Ya existe un Representante Legal registrado previamente, verifique su informaci\u00F3n.");
			return;
		} else {
			var sujetoTramiteObj = $("form#altaPatronalForm").toObject(true);
			var representanteLegalAux = new Object();
			var personaFisica = new Object();
			
			representanteLegalAux.personaFisica = personaFisica;
			sujetoTramiteObj.representanteLegalAux = representanteLegalAux;
			sujetoTramiteObj.representanteLegalAux.personaFisica.rfc = $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.rfc").val();
			sujetoTramiteObj.representanteLegalAux.personaFisica.curp = $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val();
			sujetoTramiteObj.representanteLegalAux.personaFisica.nombre = $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.nombre").val();
			sujetoTramiteObj.representanteLegalAux.personaFisica.primerApellido = $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.primerApellido").val();
			sujetoTramiteObj.representanteLegalAux.personaFisica.segundoApellido = $("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.segundoApellido").val();
			sujetoTramiteObj.representanteLegalAux.indActAdmonDominio = ((document.getElementById("indActAdmonDominioAltaAgregar").checked == true) ? 1 : 0);
			
			fnAgregarNuevaFilaRLAlta(sujetoTramiteObj, limpiarFormularioAgregarRLAlta);
		}
	} else {
		showErrorDialog("Informaci\u00F3n incompleta, verifique los datos a registrar.");
	}
}

function ejecutarAccionMedioContactoRLAlta(){

	if (accionSobreMediosContacto == "agregar"){
		fnHideErrores("form#agregarMedioContactoFormAlta");
		var oForm = $("form#agregarMedioContactoFormAlta").toObject();
		var desFormaContacto;
		
		if (oForm != undefined){
			
			desFormaContacto = fnGetDesFormaMedioContacto(oForm);
			
			if (desFormaContacto != undefined){
				oForm.desFormaContacto = desFormaContacto;
			} else {
				showErrorDialog("Informaci\u00F3n incompleta, verifique los datos a registrar.");
				return;
			}
			if(fnValidaMedioContacto(oForm)){
				fnAgregarNuevaFilaMedioContacto(oForm);
				resetMedioContactoOptions();
			}
		
		} else {
			showErrorDialog("Informaci\u00F3n incompleta, verifique los datos a registrar.");
		}
	} else if (accionSobreMediosContacto == "modificar"){
		showMessageDialog("accionSobreMediosContacto: modificar");
	}
}

function fnModificarRLAlta(){
	// Verificamos que la información requerida esté completa
	if (($("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.rfc").val() != undefined && $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.rfc").val() != "") &&
			($("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val() != undefined && $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val() != "") &&
			($("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.nombre").val() != undefined && $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.nombre").val() != "") &&
			($("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.primerApellido").val() != undefined && $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.primerApellido").val() != "") &&
			($("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.segundoApellido").val() != undefined && $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.segundoApellido").val() != "")){
		
		// verificamos que no exista un rep legal en la lista previamente registrado
		if (fnExisteRLenListaParaModificacion($("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.rfc").val(), $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val())){
			showErrorDialog("Ya existe un Representante Legal registrado previamente, verifique su informaci\u00F3n.");
			return;
		} else {
			var sujetoTramiteObj = $("form#altaPatronalForm").toObject(true);
			var representanteLegalAux = new Object();
			var personaFisica = new Object();
			
			representanteLegalAux.personaFisica = personaFisica;
			sujetoTramiteObj.representanteLegalAux = representanteLegalAux;
			sujetoTramiteObj.representanteLegalAux.personaFisica.rfc = $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.rfc").val();
			sujetoTramiteObj.representanteLegalAux.personaFisica.curp = $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val();
			sujetoTramiteObj.representanteLegalAux.personaFisica.nombre = $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.nombre").val();
			sujetoTramiteObj.representanteLegalAux.personaFisica.primerApellido = $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.primerApellido").val();
			sujetoTramiteObj.representanteLegalAux.personaFisica.segundoApellido = $("div#dgModificarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.segundoApellido").val();
			sujetoTramiteObj.representanteLegalAux.indActAdmonDominio = ((document.getElementById("indActAdmonDominioAltaModificar").checked == true) ? 1 : 0);
			
			fnModificarFilaRLAlta(sujetoTramiteObj);
		}
	} else {
		showErrorDialog("Informaci\u00F3n incompleta, verifique los datos a registrar.");
	}
	
}

function eliminarRLAlta(){
	$(dtRepresentanteLegalAlta.fnSettings().aoData).each(function (){
        if( $(this.nTr).hasClass('row_selected')){
        	dtRepresentanteLegalAlta.fnDeleteRow(this.nTr.sectionRowIndex);
        }
    });
}

function fnEliminarMedioContactoRLAlta(){
	// el elemento se remueve de los dos datatables
	// removemos del datatable de la pantalla de agregar RL
	$(dtMediosContactoRLAltaEnAgregar.fnSettings().aoData).each(function (){
        if( $(this.nTr).hasClass('row_selected')){
        	dtMediosContactoRLAltaEnAgregar.fnDeleteRow(this.nTr.sectionRowIndex);
        }
    });
	
	// removemos del datatable de la pantalla de modificar RL
	$(dtMediosContactoRLAltaEnModificar.fnSettings().aoData).each(function (){
        if( $(this.nTr).hasClass('row_selected')){
        	dtMediosContactoRLAltaEnModificar.fnDeleteRow(this.nTr.sectionRowIndex);
        }
    });
}

function fnAgregarNuevaFilaRLAlta(newRow, callback){
	var repLegal = new Object();
	repLegal.personaFisica = new Object();
	repLegal.tipoPersonaRepresentada = new Object();
	
	// setting data to Representante LEgal
	repLegal.cveIdPersona = 1;
	repLegal.personaFisica.idPersona = 1;
	repLegal.personaFisica.rfc = newRow.representanteLegalAux.personaFisica.rfc;
	repLegal.personaFisica.curp = newRow.representanteLegalAux.personaFisica.curp;
	repLegal.personaFisica.nombre = newRow.representanteLegalAux.personaFisica.nombre;
	repLegal.personaFisica.primerApellido = newRow.representanteLegalAux.personaFisica.primerApellido;
	repLegal.personaFisica.segundoApellido = newRow.representanteLegalAux.personaFisica.segundoApellido;
	repLegal.tipoPersonaRepresentada.idTipoPersona = 1;
	repLegal.cveIdRepresentanteLegal = 1;
	repLegal.indActAdmonDominio = newRow.representanteLegalAux.indActAdmonDominio;
	
	// incluimos los datos de contacto de este representante legal
	attachMediosContactoToRLAlta(repLegal, dtMediosContactoRLAltaEnAgregar);
	
	dtRepresentanteLegalAlta.fnAddData(repLegal);
	callback();
	oDialogAgregarRepresentanteLegalAlta.dialog('close');
}

function fnAgregarNuevaFilaMedioContactoRLAlta(medioContacto){
	// pasamos los datos de contacto de un dtatable a otro para modificacion
	//dtMediosContactoRLAltaEnModificar.fnClearTable();
	dtMediosContactoRLAltaEnModificar.fnAddData(medioContacto);
	dtMediosContactoRLAltaEnAgregar.fnAddData(medioContacto);
	oDialogAgregarMedioContactoRLAlta.dialog('close');
}

function fnModificarFilaRLAlta(modifiedRow){
	var repLegal = new Object();
	repLegal.personaFisica = new Object();
	repLegal.tipoPersonaRepresentada = new Object();
	
	// setting data to Representante LEgal
	repLegal.cveIdPersona = 1;
	repLegal.personaFisica.idPersona = 1;
	repLegal.personaFisica.rfc = modifiedRow.representanteLegalAux.personaFisica.rfc;
	repLegal.personaFisica.curp = modifiedRow.representanteLegalAux.personaFisica.curp;
	repLegal.personaFisica.nombre = modifiedRow.representanteLegalAux.personaFisica.nombre;
	repLegal.personaFisica.primerApellido = modifiedRow.representanteLegalAux.personaFisica.primerApellido;
	repLegal.personaFisica.segundoApellido = modifiedRow.representanteLegalAux.personaFisica.segundoApellido;
	repLegal.tipoPersonaRepresentada.idTipoPersona = 1;
	repLegal.cveIdRepresentanteLegal = 1;
	repLegal.indActAdmonDominio = modifiedRow.representanteLegalAux.indActAdmonDominio;
	
	// incluimos los datos de contacto de este representante legal
	attachMediosContactoToRLAlta(repLegal, dtMediosContactoRLAltaEnModificar);
	
	if (reemplazarRLPorModificacion(dtRepresentanteLegalAlta, repLegal)){
		oDialogModificarRepresentanteLegalAlta.dialog('close');
	} else {
		showErrorDialog("Ha ocurrido un error al actualizar la informaci\u00F3n del representante legal con RFC: " + repLegal.personaFisica.rfc + " y CURP: " + repLegal.personaFisica.curp + ", notifique al administrador del sistema.");
	}
	
	
}

function fnExisteRLenLista(rfc, curp){
	var result = false;
	$(dtRepresentanteLegalAlta.fnSettings().aoData).each(function (){
        if (rfc == this._aData.personaFisica.rfc && curp == this._aData.personaFisica.curp){
			result = true;
		}
    });
	return result;
}

function fnExisteRLenListaParaModificacion(rfc, curp){
	var result = false;
	var counter = 0;
	$(dtRepresentanteLegalAlta.fnSettings().aoData).each(function (){
        if (rfc == this._aData.personaFisica.rfc && curp == this._aData.personaFisica.curp){
			counter++;
		}
    });
	
	// asumiendo que este rfc y curp no cambiaron, se concerva la fila
	if(rfc == objRowSelectedToModifyRLAlta.personaFisica.rfc && curp == objRowSelectedToModifyRLAlta.personaFisica.curp){
		counter--;
	}
	
	if (counter > 0){ 
		result = true;
	}
	
	return result;
}

function limpiarFormularioAgregarRLAlta(){
	$("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.rfc").val("");
	$("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.curp").val("");
	$("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.nombre").val("");
	$("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.primerApellido").val("");
	$("div#dgAgregarRepresentanteLegalAlta input#representanteLegalAux\\.personaFisica\\.segundoApellido").val("");
	$("div#dgAgregarRepresentanteLegalAlta #indActAdmonDominioAltaAgregar").removeAttr("checked");
}

function limpiarFormularioAgregarMedioContactoRLAlta(){
	$("div#divMedioContactoAlta input#representanteLegalAux\\.personaFisica\\.busqAprox").val("");
	$("div#divMedioContactoAlta input#representanteLegalAux\\.rupa").val("");
}

function callbackGuardarRepresentantesLegalesAlta(){
	showMessageDialog("callbackGuardarRepresentantesLegalesAlta invocao, se han guardado los reps. legales");
}

function getRepLegalesAltaAsList() {
	var data;
	var listaRL = [];
	var rl;
	var personaFisica;
	var dataOrigen = dtRepresentanteLegalAlta.fnSettings().aoData;
	var indActAdmonDominioVar;
	for (registro in dataOrigen) {
		data = dataOrigen[registro]._aData;
		rl = new Object();
		personaFisica = new Object();
		indActAdmonDominioVar = data["indActAdmonDominio"];
		// checar el <center> que viene en esta variable desde el parser
		rl.indActAdmonDominio = indActAdmonDominioVar == 'SI' ? 1 : 0;
		personaFisica.nombre = data["personaFisica"]["nombre"];
		
		rl.personaFisica = personaFisica;
		
		listaRL.push(rl);
	}
	return listaRL;
}


function reemplazarRLPorModificacion(dtRepresentanteLegalAltaLocal, repLegalLocal){
	
	var obRowSelected = fnGetRowSelected(dtRepresentanteLegalAltaLocal);
	if (obRowSelected == undefined) {
		showErrorDialog("no se pudo recuperar el elemento del grid para su modificaci\u00F3n");
	}else{
		obRowSelected.personaFisica.rfc = repLegalLocal.personaFisica.rfc;
		obRowSelected.personaFisica.curp = repLegalLocal.personaFisica.curp;
		obRowSelected.personaFisica.nombre = repLegalLocal.personaFisica.nombre;
		obRowSelected.personaFisica.primerApellido = repLegalLocal.personaFisica.primerApellido;
		obRowSelected.personaFisica.segundoApellido = repLegalLocal.personaFisica.segundoApellido;
		obRowSelected.indActAdmonDominio = repLegalLocal.indActAdmonDominio;
		obRowSelected.mediosContacto = repLegalLocal.mediosContacto;
		dtRepresentanteLegalAlta.fnUpdate(obRowSelected, aPos, 0, false, false);
	
	}
	return true;
}

function attachMediosContactoToRLAlta(repLegalParam, dtMediosContactoRLAltaParam){
	repLegalParam.mediosContacto = new Object();
	repLegalParam.mediosContacto = obtenerListaMediosContactoFiscales(dtMediosContactoRLAltaParam);
}
