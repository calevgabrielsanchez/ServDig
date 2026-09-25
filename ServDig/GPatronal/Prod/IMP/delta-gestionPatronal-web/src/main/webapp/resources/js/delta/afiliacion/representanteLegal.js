var sIdNameFormNuevoRepresentanteLegal="#representanteLegalFormNuevo";
var sIdDialgoAgregarRepresentanteLegal = "#dgNuevoRepresentanteLegal";
var sIdDialgoAgregarDatoContactoDetalleRL = "#dgNuevoDatoContactoDetalleRL";
var sIdDialgoModificarDatoContactoDetalleRL = "#dgModificarDatoContactoDetalleRL";


var dtRepresentanteLegal;
var dtRepresentanteLegalForSession;
var dtRepresentanteLegalDatosContactoDetalle;
var dtRepresentanteLegalDatosContactoDetalleEliminar;
var sIdNameFormPaginarRepresentanteLegal="#representanteLegalFormPaginar";
var sIdDialogErrorSinSeleccionRepresentanteLegal = "#dgErrorSinSeleccionRepresentanteLegal";
var sIdDialogErrorSinSeleccionPersonaParaRepresentanteLegal = "#dgErrorSinSeleccionPersonaParaRepresentanteLegal";
var sIdDialogEliminarRepresentanteLegal = "#dgEliminarRepresentanteLegal";
var sIdDialogEliminarDatoContactoDetalleRL = "#dgEliminarDatoContactoDetalleRL";
var sIdDialogEliminarRepresentanteLegalForSession = "#dgEliminarRepresentanteLegalTramite";
var sIdDialogDeshacerEliminarRepresentanteLegalForSession="#dgDeshacerEliminarRepresentanteLegalTramite"; 
var sIdDialogDetalleEnTramiteRepresentanteLegal="#dgDetalleEnTramiteRepresentanteLegal";
var sIdDialogErrorDuplicadoRepresentanteLegal="#dgErrorDuplicadoRepresentanteLegal";
var sIdDialogModificarRepresentanteLegal = "#dgModificarRepresentanteLegal";

var arrayDatos = new Array();
var oDialogAgregarRepresentanteLegal;
var oDialogAgregarDatoContactoDetalleRL;
var oDialogModificarDatoContactoDetalleRL;
var oDialogEliminarRepresentanteLegal;
var oDialogEliminarDatoContactoDetalleRL;
var oDialogDeshacerEliminarRepresentanteLegal;
var oDialogDetalleEnTramiteRepresentanteLegal;
var oDialogDetalleEnTramiteRepresentanteLegalEliminar;
var oDialogErrorDuplicadoRepresentanteLegal;
var oDialogModificarRepresentanteLegal;
var mcRepLegal;
var mcRepLegalModificacion;
var mcRepLegalDetalleTramite;
var oDialogDetalleRepLegal;

var personaFisicaIdPersonaForMediosContactoDetalleTramite;
var accionSolicitada;

MedioContacto.prototype.extendValidation = function () {
    var mc = this;

    var _selectTipoFn = function() { return mc.jqSelectTipo; };
    var fnValidarDatos = mc.validarDatos;
    var alterFnValidarDatos = function(arg1, arg2) {
        this.validarDatos = fnValidarDatos;
        var _option = $([_selectTipoFn(), ' > ', 'option:selected'].join(''));
        if (/correo e|facebook|twitter/i.test(_option.text())) {
            var _tmptxt = $(mc.jqTxtFldDesc).val().replace(/^\s+|\s+$/g, '');
            $(mc.jqTxtFldDesc).val(_tmptxt);
            arg2 = _tmptxt
        }
        var retval = this.validarDatos(arg1, arg2);
        this.validarDatos = alterFnValidarDatos;
        return retval;
    };

    $.extend(mc, {validarDatos: alterFnValidarDatos});
}

$(function() {	
	construirGridRepresentateLegal();
	construirGridTramiteRL();
	construirGridTramiteRLDatosContactoDetalle();
	construirDialogosAgregarRL();
	construirDialogosAgregarDatoContactoDetalleRL();
	construirDialogosModificarDatoContactoDetalleRL();
	construirDialogosEliminarRL();
	construirDialogosDeshacerEliminarRL();
	construirDialogoDetalleEnTramiteRepresentanteLegal();
	construirDialogosModificarRL();
	configurarBusquedaPersonaFisica();
	inicializaEstilos();
	inicializaEstilosDatoContactoDetalleRL();
	evaluarBotones();
});	



var columnas = [ 
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
                	"mDataProp" : renderNombre
                },
                { 
                	"sTitle" : "Actos de Administraci&oacute;n o dominio",
                	"mDataProp" : "indActAdmonDominio",
                	"fnRender": function ( oObj ) {						
                		return parseIndicador(oObj.aData.indActAdmonDominio);
                	}
                },
                { 
                	"sTitle" : "",					
                	"fnRender": function ( oObj ) {						
                		index=oObj.aData.personaFisica.idPersona;
                		arrayDatos[index]=oObj.aData;						
                		return construyeLiga(index);
                	}
                }
];


var columnasTramiteRL = [{			            	   
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
					"sTitle" : "RFC",
					"mDataProp" : "personaFisica.rfc",
					sWidth: "100px"
				},
				{ 
					"sTitle" : "CURP",
					"mDataProp" : "personaFisica.curp",
					sWidth: "150px"
				}
				,
				{ 
					"sTitle" : "Nombre",
					"mDataProp" : renderNombre,
					sWidth: "300px"
				}
				,
				{ 
					"sTitle" : "Actos de Administraci&oacute;n o dominio",
					"mDataProp" : "indActAdmonDominio",
					"fnRender": function ( oObj ) {						
						return parseIndicador(oObj.aData.indActAdmonDominio);
					}
				},
				{ 
					"sTitle" : "Acci&oacute;n a Realizar",					
					"fnRender": function ( oObj ) {						
						accion=oObj.aData.accion;														
						return showAfectacion(accion);
					}
					
				},
				{ 
					"sTitle" : "",					
					"fnRender": function ( oObj ) {																									
						index=oObj.aData.personaFisica.idPersona;						
                		return deshacer(index);
					}
					
				},
				{ 
					"sTitle" : "",
					"fnRender": function ( oObj ) {																									
						index=oObj.aData.personaFisica.idPersona;						
                		return mostrarDetalleEnTramiteRepLegal(index);
					}
					
				}
                ];



var columnasRLDatosContactoDetalle = [
	{
		"mDataProp" : "idVista",
		"bVisible" : false
	},
	{
		"mDataProp" : "errorFormGeneral",
		"bVisible" : false
	},
	{ 
		"sTitle" : "Medio de Contacto",
		"mDataProp" : "tipoMedioContacto.descripcion"
	},
	{ 
		"sTitle" : "Descripci&oacute;n",
		"mDataProp" : "desFormaContacto"
	}
];


function renderNombre(oObj){
	var nombre = oObj.personaFisica.nombre!=undefined ? oObj.personaFisica.nombre : "";
	var apellidoPaterno = oObj.personaFisica.primerApellido!=undefined ? oObj.personaFisica.primerApellido :"";
	var apellidoMaterno = oObj.personaFisica.segundoApellido!=undefined ? oObj.personaFisica.segundoApellido : "";
	
	return nombre+" "+apellidoPaterno+" "+apellidoMaterno;
}


/* 
 * Callback de la busqueda de personas fisica. 
 */ 
var fnOnPersonaReturn = function(){ 
	
	var objTramiteFisica = this;
	var p = null;	
	
	if(objTramiteFisica.fisica != null) {
		p = objTramiteFisica.fisica;
	} else if (objTramiteFisica.datosICA != null) {
		p = objTramiteFisica.datosICA.personaFisicaIMSS;
	}
	
	if (p.nombre != null) {				
		
		if(p.idPersona == undefined){
			oDialogAgregarRepresentanteLegal.dialog("close");
			var oDialogo;
			construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", 
					"Se ha encontrado que la persona " + p.nombre + " " + p.primerApellido + " " + p.segundoApellido + " esta registrada ante " +
					"el SAT (Sistema de Administraci&oacute;n Tributaria) y/o RENAPO, " +
					"sin embargo no se ha encontrado registro de la misma dentro del Instituto Mexicano del Seguro Social.<br><br>" +
					"Recuerde que usted puede registrar como representante legal solo aquellas personas que previamente han sido registradas ante el instituto, " +
					"por favor aseg&uacute;rese que la persona que desea registrar como representante legal ha sido acreditada previamente " +
					"ante el Instituto Mexicano del Seguro Social.", true, undefined,undefined,250,800);
			return;
		}
		$("#divMediosContactoRepLegalPersonaFisica").show();
		document.getElementById("representanteLegalAux.personaFisica.idPersona").value=p.idPersona != undefined ? p.idPersona : 0;
		document.getElementById("representanteLegalAux.personaFisica.nombre").value=p.nombre != undefined ? p.nombre : "";
		document.getElementById("representanteLegalAux.personaFisica.primerApellido").value=p.primerApellido != undefined ? p.primerApellido : "";	
		document.getElementById("representanteLegalAux.personaFisica.segundoApellido").value=p.segundoApellido!=undefined ? p.segundoApellido: "";
		document.getElementById("representanteLegalAux.personaFisica.rfc").value=p.rfc!=undefined ? p.rfc : "";
		document.getElementById("representanteLegalAux.personaFisica.curp").value=p.curp!= undefined ? p.curp : "";
		this.nombre=null;
		$("#divMediosContactoRepLegalPersonaFisica").html("");
		$("#indActAdmonDominio1").removeAttr("checked");
		mcRepLegal = new MedioContacto("divMediosContactoRepLegalPersonaFisica", 2, tpPropietarioRepLegal, p.idPersona, idSolicitud, idSujetoObligado);			 			
		mcRepLegal.init();
        mcRepLegal.extendValidation();
		
	} else {
		//Se limpia la forma.
		document.getElementById("representanteLegalAux.personaFisica.idPersona").value=0;
		document.getElementById("representanteLegalAux.personaFisica.nombre").value="";
		document.getElementById("representanteLegalAux.personaFisica.primerApellido").value="";	
		document.getElementById("representanteLegalAux.personaFisica.segundoApellido").value="";
		document.getElementById("representanteLegalAux.personaFisica.rfc").value="";
		document.getElementById("representanteLegalAux.personaFisica.curp").value="";
		$("#indActAdmonDominio1").removeAttr("checked");
		$("#divMediosContactoRepLegalPersonaFisica").hide();
		this.nombre=null;
		
		
		oDialogAgregarRepresentanteLegal.dialog("close");
//		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Notificaci\u00F3n", "No pudieron obtenerse los datos desde el servicio de personas, intente m\u00E1s tarde.", true);
		//alert('No pudieron obtenerse los datos desde el servicio de personas, intente m\u00E1s tarde.');
	}
	//Se inicializa para tener limpia la forma cuando se abre de nuevo el popup
	this.nombre=null;
}


function showAfectacion(accion){	
	return accion;
}

function deshacer(index){
	return "<a style='cursor:pointer;' onclick='deshacerAccion("+index+")'>Deshacer Acci&oacute;n</a>";
}


function deshacerAccion(index){
	document.getElementById("idPersonaDeshacer").value=index;
	oDialogDeshacerEliminarRepresentanteLegal.dialog('open');
	$("#listaForSession").focus();
}

function mostrarDetalleEnTramiteRepLegal(index){
	return "<a style='cursor:pointer;' onclick='mostrarDialogoDeDetalleEnTramiteRepLegal("+index+")'>Mostrar Detalle</a>";
}

function mostrarDialogoDeDetalleEnTramiteRepLegal(index){
	/* 
	 * solicitamos informacion del rep legal en session al controller, procesaRespuestaParaDetalleEnTramiteRepresentante
	 * insertará la info. en el div de detalle
	 */
	var fisica = new Object();
	fisica.idPersona=index;
	
	sendToServer('/representanteLegal/fb/obtenerDetalleEnTramiteRepresentanteLegal', fisica, 
			procesaRespuestaParaDetalleEnTramiteRepresentanteLegal, false);
}

function construirGridRepresentateLegal(){
	dtRepresentanteLegal = $('#tbRepresentanteLegal').dataTable({
		"bJQueryUI": false,
		"bPaginate": true,
		"bLengthChange": false,
		"iDisplayLength": 5,
		"bFilter": false,
		"bSort": false,
		"bInfo": false,
		"bAutoWidth": false,
		"bServerSide" : true,			
		"aoColumns" : columnas,
		"sPaginationType": "full_numbers",
		"bProcessing" : true,
		"sAjaxSource" : '/delta-gestionPatronal-web/representanteLegal/fb/paginar',
		"fnServerData" : enviarTramite
	});	
	
}

function inicializaEstilos(){
	$("#tbRepresentanteLegal tbody").hover(
			function(){
				$(this).css('cursor', 'pointer');
			}
		);
	
	/* Add a click handler to the rows - this could be used as a callback */
	$("#tbRepresentanteLegal tbody").click(function(event) {
		var seleccionar = !$(event.target.parentNode).hasClass('row_selected');
		
		$(dtRepresentanteLegal.fnSettings().aoData).each(function() {
			$(this.nTr).removeClass('row_selected');
		});
		if (seleccionar){
			$(event.target.parentNode).addClass('row_selected');
		}
	});
}

function inicializaEstilosDatoContactoDetalleRL(){
	$("#tbRepresentanteLegalDatosContactoDetalle tbody").hover(
			function(){
				$(this).css('cursor', 'pointer');
			}
		);
	
	/* Add a click handler to the rows - this could be used as a callback */
	$("#tbRepresentanteLegalDatosContactoDetalle tbody").click(function(event) {
		var seleccionar = !$(event.target.parentNode).hasClass('row_selected');
		
		$(dtRepresentanteLegalDatosContactoDetalle.fnSettings().aoData).each(function() {
			$(this.nTr).removeClass('row_selected');
		});
		if (seleccionar){
			$(event.target.parentNode).addClass('row_selected');
		}
	});
}
	
function enviarTramite(sSource, aoData, fnCallback) {
	var wrapper = new Object();
	wrapper.aoData = aoData;
	var oForm = $(sIdNameFormPaginarRepresentanteLegal).serializeObject(true);
	wrapper.oForm = oForm;
	
	$.postJSON(sSource, wrapper, function(data){								
		fnCallback(data);
	});

}


function parseIndicador( o ) {
	if (o == '1' || o == 1){
		return '<center>SI</center>';		
		
	}else{
		return '<center>NO</center>';		
	}
}

function construyeLiga( object ){
	
	return "<a href='#' onclick='showRepLegal("+object+")'>Mostrar Detalle</a>";
}

function showRepLegal(object){

	oDialogDetalleRepLegal =  $('#divDetalleRepLegal').dialog({
			autoOpen:false,
			resizable: false,
			height: 700,
			width: 1000,
			modal: true,
			close: function() {
						$("#divDetalleMediosContactoRepLegal").html("");
					},
			buttons: {
				"Cerrar": function() {
							$( this ).dialog( "close" );		
						}
			}		
		});
	
	$("#divDetalleRepLegal").css("display", "block");
	
	$("#divDetalleRepLegal #detalleRepLegalRFC").text(arrayDatos[object].personaFisica.rfc);
	$("#divDetalleRepLegal #detalleRepLegalCURP").text(arrayDatos[object].personaFisica.curp);
	$("#divDetalleRepLegal #detalleRepLegalPrimerApellido").text(arrayDatos[object].personaFisica.primerApellido);
	$("#divDetalleRepLegal #detalleRepLegalSegundoApellido").text(arrayDatos[object].personaFisica.segundoApellido);
	$("#divDetalleRepLegal #detalleRepLegalNombre").text(arrayDatos[object].personaFisica.nombre);
	
	if (arrayDatos[object].indActAdmonDominio.indexOf("no")!=-1){
		$("#divDetalleRepLegal #indActAdmonDominioDetalle").text("NO");
	} else {
		$("#divDetalleRepLegal #indActAdmonDominioDetalle").text("SI");
	}
	
	var mcRepLegalDetalle = new MedioContacto("divDetalleMediosContactoRepLegal", 1, tpPropietarioRepLegal, arrayDatos[object].cveIdRepresentanteLegal, idSolicitud, idSujetoObligado, false);			 			
	mcRepLegalDetalle.init();
    mcRepLegalDetalle.extendValidation();
	oDialogDetalleRepLegal.dialog('open');
	$("#listaForSession").focus();
}


function construirGridTramiteRL(){
	/* Configuracion del data table de Representante Legal para el objeto de sesion*/
	dtRepresentanteLegalForSession = $('#tbRepresentanteLegalForSession').dataTable({
			bJQueryUI : false,
			bFilter : false,
			bInfo:false,
			bSort: false,
			bPaginate: true,
			"iDisplayLength": 5,
			"bAutoWidth" : false,
			"bServerSide" : true,
			"sPaginationType": "full_numbers",
			"aoColumns" : columnasTramiteRL, 
			"bProcessing" : true,
			"sAjaxSource" : '/delta-gestionPatronal-web/representanteLegal/paginarForSession',
			"fnServerData" : enviarMovimiento
			});

}

function construirGridTramiteRLDatosContactoDetalle(){
	dtRepresentanteLegalDatosContactoDetalle = $('#tbRepresentanteLegalDatosContactoDetalle').dataTable({
			bJQueryUI : false,
			bFilter : false,
			bInfo:false,
			bSort: false,
			bProcessing : true,
			bPaginate: true,
			iDisplayLength: 4,
			iLength: 4,
			sPaginationType: "full_numbers",
			bAutoWidth : false,
			bServerSide : true,
			aoColumns : columnasRLDatosContactoDetalle, 
			sAjaxSource : '/delta-gestionPatronal-web/representanteLegal/fb/obtenerDetalleEnTramiteRepresentanteLegalDatosContacto',
			fnServerData : enviarPersonaFisicaIdPersona,
			"fnDrawCallback": function( oSettings ) {
//			      alert( 'DataTables has redrawn the table' );
			    }
			});
	
	dtRepresentanteLegalDatosContactoDetalleEliminar = $('#tbRepresentanteLegalDatosContactoDetalleEliminar').dataTable({
		bJQueryUI : false,
		bFilter : false,
		bInfo:false,
		bSort: false,
		iDisplayLength: 4,
		bPaginate: true,
		bAutoWidth : false,
		bServerSide : true,
		aoColumns : columnasRLDatosContactoDetalle, 
		bProcessing : true,
		sAjaxSource : '/delta-gestionPatronal-web/representanteLegal/fb/obtenerDetalleEnTramiteRepresentanteLegalDatosContacto',
		fnServerData : enviarPersonaFisicaIdPersona
		});

}

function enviarMovimiento(sSource, aoData, fnCallback){
	aoData.push({
		"name" : "sSearch",
		"value" : ''
	});

	var wrapperMovimiento = new Object();
	wrapperMovimiento.aoData = aoData;	
	var oForm = new Object();
	wrapperMovimiento.oForm = oForm;
	
	wrapperMovimiento.oForm.tipoPersonaRepresentada=new Object();
	if (tipoPersonaFiscal == "FISICA") {
		wrapperMovimiento.oForm.tipoPersonaRepresentada.idTipoPersona = 1;
		wrapperMovimiento.oForm.cveIdPersona=$("#fisica\\.idPersona").val();
	} else {
		wrapperMovimiento.oForm.tipoPersonaRepresentada.idTipoPersona = 2;
		wrapperMovimiento.oForm.cveIdPersona=$("#moral\\.idPersona").val();
		
	}	
	$.postJSON(sSource+"?idSolicitud="+idSolicitud, wrapperMovimiento, function(data) {		
		fnCallback(data);
	});
}

function enviarPersonaFisicaIdPersona(sSource, aoData, fnCallback){
	aoData.push({
		"name" : "sSearch",
		"value" : ''
	});
	
	var wrapperMovimiento = new Object();
	var oForm = new Object();
	
	wrapperMovimiento.aoData = aoData;
	wrapperMovimiento.oForm = oForm;
		
	// asignamos valores
	wrapperMovimiento.oForm.clave = personaFisicaIdPersonaForMediosContactoDetalleTramite;
	
	$.postJSON(sSource, wrapperMovimiento, function(data) {		
		fnCallback(data);
	});
}

function construirDialogosAgregarRL(){

	/*Configuracion del dialogo de agregar nuevo elemento*/
	oDialogAgregarRepresentanteLegal = 	$( sIdDialgoAgregarRepresentanteLegal).dialog({
		autoOpen:false,
		resizable: false,
		width:920,
		height:650,
		modal: true,
		buttons: {
			"Guardar": function(){
				agregarTramiteRL();
			},
			'Cancelar': function(){
					$( this ).dialog( "close" );
			}
		}

	});
}

function construirDialogosAgregarDatoContactoDetalleRL(){
	
	/*Configuracion del dialogo de agregar nuevo elemento*/
	oDialogAgregarDatoContactoDetalleRL = 	$( sIdDialgoAgregarDatoContactoDetalleRL).dialog({
		autoOpen:false,
		resizable: false,
		height : 350,
		width : 550,
		modal: true,
		buttons: {
			"Guardar": function(){
				agregarDatoContactoDetalleRL();
			},
			'Cancelar': function(){
					$( this ).dialog( "close" );
			}
		}

	});
}

function construirDialogosModificarDatoContactoDetalleRL(){

	/*Configuracion del dialogo de modificar elemento*/
	oDialogModificarDatoContactoDetalleRL = 	$( sIdDialgoModificarDatoContactoDetalleRL).dialog({
		autoOpen:false,
		resizable: false,
		height : 350,
		width : 550,
		modal: true,
		buttons: {
			"Guardar": function(){
				modificarDatoContactoDetalleRL();
			},
			'Cancelar': function(){
					$( this ).dialog( "close" );
			}
		}

	});
}

function construirDialogosEliminarRL(){
	
	oDialogEliminarRepresentanteLegal = 	$( sIdDialogEliminarRepresentanteLegal ).dialog({
		autoOpen:false,
		resizable: false,
		height:200,
		modal: true,
		buttons: {
			"Eliminar": eliminarTramiteRL,
			'Cancelar': cancelarRL
		}
	});

	oDialogEliminarRepresentanteLegalForSession = 	$( sIdDialogEliminarRepresentanteLegalForSession ).dialog({
		autoOpen:false,
		resizable: false,
		height:200,
		modal: true,
		buttons: {
			"Eliminar": eliminarTramiteRL,
			'Cancelar': cancelarTramiteRL
		}
	});	
	
	oDialogEliminarDatoContactoDetalleRL = 	$( sIdDialogEliminarDatoContactoDetalleRL ).dialog({
		autoOpen:false,
		resizable: false,
		height:200,
		modal: true,
		buttons: {
			"Eliminar": eliminarDatoContactoDetalleRL,
			'Cancelar': function (){
				$( this ).dialog( "close" );
			}
		}
	});
}

function construirDialogosModificarRL(){	
	
	oDialogModificarRepresentanteLegal =	$( sIdDialogModificarRepresentanteLegal).dialog({
		autoOpen:false,
		resizable: false,
		width:920,
		height:650,
		modal: true,		
		buttons: {
			"Guardar": function(){
				modificarTramiteRL();
			},
			'Cancelar': function(){
					$( this ).dialog( "close" );
			}
		}
	});
}

function construirDialogosDeshacerEliminarRL(){
	oDialogDeshacerEliminarRepresentanteLegal =	$( sIdDialogDeshacerEliminarRepresentanteLegalForSession ).dialog({
		autoOpen:false,
		resizable: false,
		height:200,
		modal: true,
		buttons: {
			"Eliminar": deshacerEliminarTramiteRL,
			'Cancelar': cancelarTramiteRL
		}
	});
	
	oDialogErrorDuplicadoRepresentanteLegal = $( sIdDialogErrorDuplicadoRepresentanteLegal ).dialog({
		autoOpen:false,
		resizable: false,
		height:200,
		modal: true,
		buttons: {
			"Aceptar": function() {
						$( this ).dialog( "close" );			
						}
		}
	});								 
}

function construirDialogoDetalleEnTramiteRepresentanteLegal(){
	oDialogDetalleEnTramiteRepresentanteLegal =	$( sIdDialogDetalleEnTramiteRepresentanteLegal ).dialog({
		autoOpen:false,
		resizable: false,
		modal: true,
		height : 700,
		width : 850,
		buttons: {
			"Aceptar": function() {
				validaCambiosDetalleEnTramite();			
			},
			'Cancelar': function() {
				cancelarEdicionMediosContactoRepLegalEnDetalle();
				$( this ).dialog( "close" );			
			}
		}
	});
	
	oDialogDetalleEnTramiteRepresentanteLegalEliminar =	$("#divDetalleEnTramiteRepresentanteLegalEliminar").dialog({
		autoOpen:false,
		resizable: false,
		modal: true,
		height : 550,
		width : 850,
		buttons: {
			'Cerrar': function() {
				$( this ).dialog( "close" );			
			}
		}
	});
}




function eliminarRL(data){
	
	fnHideErrores(sIdDialogEliminarRepresentanteLegal);
	/*Obtenemos el radio seleccionado*/
	var obRowSelected = fnGetRowSelected(dtRepresentanteLegal);
	var idRepresentanteLegal = obRowSelected.cveIdPersona;
	
	
	var cveIdPatronSujetoObligado = $('#representanteLegalFormPaginar:hidden #cveIdPatronSujetoObligado').val();
    	
	var representanteLegalObj = new Object();
	
	representanteLegalObj.cveIdPatronSujetoObligado=$("#cveIdSujetoObligado").val();
	representanteLegalObj.tipoPersonaRepresentada = new Object();
	representanteLegalObj.cveIdPersona = obRowSelected.cveIdPersona;	
	
	if (tipoPersonaFiscal == "FISICA") {
		representanteLegalObj.tipoPersonaRepresentada.idTipoPersona = 1;
	} else {
		representanteLegalObj.tipoPersonaRepresentada.idTipoPersona = 2;
	}
	
	representanteLegalObj.personaFisica = new Object();
	representanteLegalObj.personaFisica = obRowSelected.personaFisica;

	var sSource = '/representanteLegal/fb/eliminarRepLegal';
	var callback;
	sendToServer(sSource, representanteLegalObj, procesarRespuestaServer, false);	
}

function cancelarRL(){
    fnHideErrores(sIdDialogEliminarRepresentanteLegal);
    $( this ).dialog( "close" );
}

/*Funcion para agregar el elemento nuevo de Representante Legal*/
function fnOpenDialogNuevoRepresentanteLegal(){
	oDialogAgregarRepresentanteLegal.dialog('open');
	fnOpenBuscarPersonaFisica();
}

/*Funcion para agregar el elemento nuevo de Representante Legal, dato de contacto en detalle*/
function fnOpenDialogNuevoDatoContactoDetalleRL(){
	fnOcultaErrores($("#divErrorTipoFormaContacto"));
	fnOcultaErrores($("#divErrorDesFormaContacto"));
	$("#dgNuevoDatoContactoDetalleRL #nuevoDatoContactoDetalleRLDescripcion").val('');
	$("#dgNuevoDatoContactoDetalleRL #tipoContacto").val('-1');

	oDialogAgregarDatoContactoDetalleRL.dialog('open');
}

function fnOpenDialogModificarDatoContactoDetalleRL(){
	if(fnValidaRegistroSeleccionado(dtRepresentanteLegalDatosContactoDetalle)){
		
		var obRowSelected = fnGetRowSelected(dtRepresentanteLegalDatosContactoDetalle);
		fnOcultaErrores($("#divErrorTipoFormaContactoModificar"));
		fnOcultaErrores($("#divErrorDesFormaContactoModificar"));
		$("#dgModificarDatoContactoDetalleRL #idVistaModificar").val(obRowSelected.idVista);
		$("#dgModificarDatoContactoDetalleRL #errorFormGeneralModificar").val(obRowSelected.errorFormGeneral);
		$("#dgModificarDatoContactoDetalleRL #tipoContactoModificar").val(obRowSelected.tipoMedioContacto.idTipoMedioContacto);
		$("#dgModificarDatoContactoDetalleRL #tipoContactoModificar").attr("disabled", "true");
		$("#dgModificarDatoContactoDetalleRL #modificarDatoContactoDetalleRLDescripcion").val(obRowSelected.desFormaContacto);
	
		oDialogModificarDatoContactoDetalleRL.dialog("open");
	} else {
		fnDialogErrorSinSeleccionRepresentanteLegal();
	}
}


function fnOpenDialogEliminarDatoContactoDetalleRL(){
	if(fnValidaRegistroSeleccionado(dtRepresentanteLegalDatosContactoDetalle)){
		oDialogEliminarDatoContactoDetalleRL.dialog('open');
	} else {
		fnDialogErrorSinSeleccionRepresentanteLegal();
	}
}

function agregarTramiteRL(){

	fnHideErrores(sIdNameFormNuevoRepresentanteLegal);	
	
	var tramiteFisica = PersonaFisicaCtrl.getPersona();
	
	var oForm = new Object();
	
	
	if (document.getElementById("representanteLegalAux\.personaFisica\.curp").value != "" && document.getElementById("representanteLegalAux\.personaFisica\.idPersona").value != ""){
		oForm.representanteLegalAux = new Object();
		oForm.representanteLegalAux.personaFisica = new Object();
		
		var sSource = '/delta-gestionPatronal-web/representanteLegal/fb/agregarRepLegal';	

		oForm.representanteLegalAux.personaFisica.idPersona = document.getElementById("representanteLegalAux\.personaFisica\.idPersona").value;
		oForm.representanteLegalAux.personaFisica.rfc = document.getElementById("representanteLegalAux\.personaFisica\.rfc").value;
		oForm.representanteLegalAux.personaFisica.curp = document.getElementById("representanteLegalAux\.personaFisica\.curp").value;
		oForm.representanteLegalAux.personaFisica.primerApellido = document.getElementById("representanteLegalAux\.personaFisica\.primerApellido").value;
		oForm.representanteLegalAux.personaFisica.segundoApellido = document.getElementById("representanteLegalAux\.personaFisica\.segundoApellido").value;
		oForm.representanteLegalAux.personaFisica.nombre = document.getElementById("representanteLegalAux\.personaFisica\.nombre").value;

		
		
		var oFormSend =new Object();
		var wrapper = new Object();
		
		
		oForm.representanteLegalAux.tipoPersonaRepresentada=new Object();
		oForm.representanteLegalAux.tipoPersonaRepresentada.idTipoPersona=new Object();
		oForm.representanteLegalAux.indActAdmonDominio=new Object();
		oForm.representanteLegalAux.cveIdPatronSujetoObligado = $("#cveIdSujetoObligado").val();
		
		if (document.getElementById("indActAdmonDominio1").checked==true)
			oForm.representanteLegalAux.indActAdmonDominio=1;
		else
			oForm.representanteLegalAux.indActAdmonDominio=0;
		
		// verificamos datos de contacto, al menos debe ser capturado telefono (fijo o movil) o correo electronico
		var mediosContactoList = mcRepLegal.obtenerListaMediosContacto();
		var contadorDatosContactoRequeridos = 0;
		var correoPresente = false;
		var telefonoFijoPresente = false;
		var telefonoMovilPresente = false;
		
		if (validaMediosContactoRequeridosDeRFC(mediosContactoList)){
			
			
			
				// asignamos datos de contacto
				oForm.representanteLegalAux.mediosContacto = mcRepLegal.obtenerListaMediosContacto();
				
				if (tipoPersonaFiscal == "FISICA") {
					oForm.representanteLegalAux.tipoPersonaRepresentada.idTipoPersona = 1;
					oForm.representanteLegalAux.cveIdPersona=$("#fisica\\.idPersona").val();
				} else {
					oForm.representanteLegalAux.tipoPersonaRepresentada.idTipoPersona = 2;
					oForm.representanteLegalAux.cveIdPersona=$("#moral\\.idPersona").val();
				}
				
				// Se agrega el objeto TramiteFisica al Representante Legal
				oForm.representanteLegalAux.tramiteFisica = new Object();
				oForm.representanteLegalAux.tramiteFisica = tramiteFisica;
				
				wrapper.oForm = oForm.representanteLegalAux;
				
				document.getElementById("responseHidden").value=1;
				
				$.postJSON(sSource, wrapper, procesaRespuesta);
		}
	}
}	


function agregarDatoContactoDetalleRL(){
	
	var sSource = '/delta-gestionPatronal-web/representanteLegal/fb/agregarDatoContactoDetalleRL';
	var oForm = new Object();
	var tipoMedioContacto = new Object();
	
	oForm.tipoMedioContacto = tipoMedioContacto;
	
	oForm.desFormaContacto = $("#dgNuevoDatoContactoDetalleRL #nuevoDatoContactoDetalleRLDescripcion").val();
	oForm.tipoMedioContacto.idTipoMedioContacto = $("#dgNuevoDatoContactoDetalleRL #tipoContacto").val();
	oForm.tipoMedioContacto.descripcion = jQuery('#dgNuevoDatoContactoDetalleRL #tipoContacto option:selected').text();
	
	// utiliamos temporalmente idVista para gestionar personaFisicaIdPersona
	oForm.idVista = $("#divDetalleEnTramiteRepresentanteLegal #personaFisicaIdPersona").val(); 
	var respuesta = true;
//	document.getElementById("divErrorDesFormaContacto").innerHTML="";
//	document.getElementById("divErrorTipoFormaContacto").innerHTML="";
//	if(oForm.desFormaContacto==undefined || oForm.desFormaContacto==""){
////		document.getElementById("divErrorDesFormaContacto").innerHTML="<font color=red>Este dato es requerido</font>";
//		fnDespliegaError($("#divErrorDesFormaContacto"),"Este dato es requerido");
//		respuesta = false;
//	}
//	
//	if(oForm.tipoMedioContacto.idTipoMedioContacto==undefined || oForm.tipoMedioContacto.idTipoMedioContacto=="" || oForm.tipoMedioContacto.idTipoMedioContacto=="-1"){
////		document.getElementById("divErrorTipoFormaContacto").innerHTML="<font color=red>Este dato es requerido</font>";
//		fnDespliegaError($("#divErrorTipoFormaContacto"),"Este dato es requerido");
//		respuesta = false;
//	}
	
	
	if(validarDatosContacto(
			$("#dgNuevoDatoContactoDetalleRL #tipoContacto"), 
			$("#dgNuevoDatoContactoDetalleRL #nuevoDatoContactoDetalleRLDescripcion"),
			$("#divErrorTipoFormaContacto"),
			$("#divErrorDesFormaContacto"))
	){
		$.postJSON(sSource, oForm, function(data){								
			//fnCallback(data);
			dtRepresentanteLegalDatosContactoDetalle.fnDraw();
			dtRepresentanteLegalDatosContactoDetalle.fnPageChange("last");
			oDialogAgregarDatoContactoDetalleRL.dialog("close");
			
		});
	}
	
}

function modificarDatoContactoDetalleRL(){
	var sSource = '/delta-gestionPatronal-web/representanteLegal/fb/modificarDatoContactoDetalleRL';
	var oForm = new Object();
	var tipoMedioContacto = new Object();
	
	oForm.tipoMedioContacto = tipoMedioContacto;
	
	oForm.desFormaContacto = $("#dgModificarDatoContactoDetalleRL #modificarDatoContactoDetalleRLDescripcion").val();
	oForm.tipoMedioContacto.idTipoMedioContacto = $("#dgModificarDatoContactoDetalleRL #tipoContactoModificar").val();
	oForm.tipoMedioContacto.descripcion = jQuery('#dgModificarDatoContactoDetalleRL #tipoContactoModificar option:selected').text();
	
	oForm.idVista = $("#dgModificarDatoContactoDetalleRL #idVistaModificar").val(); 
	// utilizamos errorFormGeneral para mandar le id persona fisica
	oForm.errorFormGeneral = $("#dgModificarDatoContactoDetalleRL #errorFormGeneralModificar").val();
	
	var respuesta = true;
	document.getElementById("divErrorDesFormaContactoModificar").innerHTML="";
	document.getElementById("divErrorTipoFormaContactoModificar").innerHTML="";
	if(oForm.desFormaContacto==undefined || oForm.desFormaContacto==""){
//		document.getElementById("divErrorDesFormaContactoModificar").innerHTML="<font color=red>Este dato es requerido</font>";
		fnDespliegaError($("#divErrorDesFormaContactoModificar"),"Debe agregar un valor en la descripci\u00F3n del medio de contacto");
		respuesta = false;
	}
	
	if(oForm.tipoMedioContacto.idTipoMedioContacto==undefined || oForm.tipoMedioContacto.idTipoMedioContacto=="" || oForm.tipoMedioContacto.idTipoMedioContacto=="-1"){
//		document.getElementById("divErrorTipoFormaContactoModificar").innerHTML="<font color=red>Este dato es requerido</font>";
		fnDespliegaError($("#divErrorTipoFormaContactoModificar"),"Debe seleccionar un tipo de medio de contacto");
		respuesta = false;
	}
	
	
	if(respuesta){
		if(validarDatosContacto(
				$("#dgModificarDatoContactoDetalleRL #tipoContactoModificar"), 
				$("#dgModificarDatoContactoDetalleRL #modificarDatoContactoDetalleRLDescripcion"),
				$("#divErrorTipoFormaContactoModificar"),
				$("#divErrorDesFormaContactoModificar"))
		){
			$.postJSON(sSource, oForm, function(data){
				dtRepresentanteLegalDatosContactoDetalle.fnDraw();
				oDialogModificarDatoContactoDetalleRL.dialog("close");
			});
		}
	}
}

function modificarTramiteRL(){		
	/*Obtenemos el radio seleccionado*/
	var obRowSelected = fnGetRowSelected(dtRepresentanteLegal);
	var indActAdmonDominio=0;
	if (document.getElementById("modificar.indActAdmonDominio").checked==true)
		indActAdmonDominio=1;
	
	var cveIdPersonaFisicaRepLegal = obRowSelected.personaFisica.idPersona;	
	var sSource = '/representanteLegal/fb/modificarRepLegal';
	document.getElementById("responseHidden").value=3;

	
	var listaMediosContacto = mcRepLegalModificacion.obtenerListaMediosContacto();
	
	if(!validaMediosContactoRequeridosDeRFC(listaMediosContacto)){
		var oDialogo;
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Debe capturar al menos un Tel\u00E9fono fijo o m\u00F3vil y Correo Electr\u00F3nico v\u00E1lidos.", true);
	}else{
	
		var repLegal = new Object();
		repLegal.personaFisica = new Object();
		repLegal.tipoPersonaRepresentada = new Object();
		repLegal.mediosContacto = new Object();
		
		
		// asignamos valores al obj rep legal
		repLegal.personaFisica.idPersona = cveIdPersonaFisicaRepLegal;
		repLegal.indActAdmonDominio = indActAdmonDominio;
		repLegal.tipoPersonaRepresentada.descripcion = tipoPersonaFiscal;
		
		repLegal.mediosContacto = listaMediosContacto;
		sendToServer(sSource, repLegal, procesaRespuesta, false);	
	}
}


function eliminarTramiteRL(){
	fnHideErrores(sIdDialogEliminarRepresentanteLegalForSession);		
	/*Obtenemos el radio seleccionado*/
	var obRowSelected = fnGetRowSelected(dtRepresentanteLegal);
	//var idRepresentanteLegal = obRowSelected.cveIdRepresentanteLegal;	
	var cveIdPersonaRepLegal = obRowSelected.personaFisica.idPersona;
//	var tipoPersonaFiscal = $('#tipoPersonaFiscalHidden').val();	
	var sSource = '/representanteLegal/fb/eliminarRepLegalPowered';
	var datosRequest={ cveIdPersonaRepLegal: cveIdPersonaRepLegal, tipoPersonaFiscal: tipoPersonaFiscal};
	var repLegal = new Object();
	repLegal.cveIdRepresentanteLegal = cveIdPersonaRepLegal;
	repLegal.tipoPersonaRepresentada = new Object();
	if(tipoPersonaFiscal == "FISICA")
		repLegal.tipoPersonaRepresentada.idTipoPersona=1;
	if(tipoPersonaFiscal == "MORAL")
		repLegal.tipoPersonaRepresentada.idTipoPersona=2;
	document.getElementById("responseHidden").value=2;
	//$.getJSON(sSource, datosRequest,procesaRespuesta); 
	sendToServer(sSource, repLegal, procesaRespuesta, false);	
}

function eliminarDatoContactoDetalleRL(){
	var obRowSelected = fnGetRowSelected(dtRepresentanteLegalDatosContactoDetalle);
	var personaFisicaIdPersona = obRowSelected.errorFormGeneral; // utilizamos este atrib. para idPersonaFisica
	var idVista = obRowSelected.idVista;
//	var sSource = '/delta-gestionPatronal-web/representanteLegal/fb/eliminarDatoContactoDetalleRL';
	var sSource = '/representanteLegal/fb/eliminarDatoContactoDetalleRL';
	var datosRequest={ personaFisicaIdPersona: personaFisicaIdPersona, idVista: idVista};
	var repLegal = new Object();
	repLegal.personaFisica = new Object();
	repLegal.idVista = idVista;
	repLegal.personaFisica.cveFisica=personaFisicaIdPersona;
//	$.postJSON(sSource, datosRequest, function(data){
//			dtRepresentanteLegalDatosContactoDetalle.fnDraw();
//			oDialogEliminarDatoContactoDetalleRL.dialog('close');
//		}
		
	sendToServer(sSource, repLegal, function(data){
		dtRepresentanteLegalDatosContactoDetalle.fnDraw();
		oDialogEliminarDatoContactoDetalleRL.dialog('close');
	}, false);	 
	
}

function deshacerEliminarTramiteRL(){		
	/*Obtenemos el radio seleccionado*/	
	var cveIdPersonaRepLegal = document.getElementById("idPersonaDeshacer").value;	
	//var sSource = '/delta-gestionPatronal-web/representanteLegal/fb/deshacerEliminarRepLegal';
	var sSource = '/representanteLegal/fb/deshacerEliminarRepLegalPowered';
	var datosRequest={ cveIdPersonaRepLegal: cveIdPersonaRepLegal,tipoPersonaFiscal:tipoPersonaFiscal};
	var repLegal = new Object();
	repLegal.cveIdRepresentanteLegal = cveIdPersonaRepLegal;
	repLegal.tipoPersonaRepresentada = new Object();
	if(tipoPersonaFiscal == "FISICA")
		repLegal.tipoPersonaRepresentada.idTipoPersona=1;
	if(tipoPersonaFiscal == "MORAL")
		repLegal.tipoPersonaRepresentada.idTipoPersona=2;
	document.getElementById("responseHidden").value=4;
//	$.getJSON(sSource, datosRequest,procesaRespuesta); 	
	sendToServer(sSource, repLegal, procesaRespuesta, false);
}

function procesaRespuestaParaDetalleEnTramiteRepresentanteLegal(data){
	
	if(data.accion=='ELIMINAR'){
		$("#divDetalleEnTramiteRepresentanteLegalEliminar #detalleEnTramiteRepLegalRFCEliminar").text(data.personaFisica.rfc);
		$("#divDetalleEnTramiteRepresentanteLegalEliminar #detalleEnTramiteRepLegalCURPEliminar").text(data.personaFisica.curp);
		$("#divDetalleEnTramiteRepresentanteLegalEliminar #detalleEnTramiteRepLegalPrimerApellidoEliminar").text(data.personaFisica.primerApellido);
		$("#divDetalleEnTramiteRepresentanteLegalEliminar #detalleEnTramiteRepLegalSegundoApellidoEliminar").text(data.personaFisica.segundoApellido);
		$("#divDetalleEnTramiteRepresentanteLegalEliminar #detalleEnTramiteRepLegalNombreEliminar").text(data.personaFisica.nombre);
//		$("#divDetalleEnTramiteRepresentanteLegal #personaFisicaIdPersona").val(data.personaFisica.idPersona);
		if (data.indActAdmonDominio == 1){
			//$("#divDetalleEnTramiteRepresentanteLegal #indActAdmonDominioDetalleEnTramite").checked=true;
			$("#indActAdmonDominioDetalleEliminar").text('SI');
		} else {
			//$("#divDetalleEnTramiteRepresentanteLegal #indActAdmonDominioDetalleEnTramite").checked=false;
			$("#indActAdmonDominioDetalleEliminar").text('NO');
		}
		personaFisicaIdPersonaForMediosContactoDetalleTramite = data.personaFisica.idPersona;
		dtRepresentanteLegalDatosContactoDetalleEliminar.fnDraw();
		oDialogDetalleEnTramiteRepresentanteLegalEliminar.dialog('open');
	}else{
		
		// mostramos la informacion recuperada
		$("#divDetalleEnTramiteRepresentanteLegal #personaFisicaIdPersona").val(data.personaFisica.idPersona);
		$("#divDetalleEnTramiteRepresentanteLegal #detalleEnTramiteRepLegalRFC").text(data.personaFisica.rfc);
		$("#divDetalleEnTramiteRepresentanteLegal #detalleEnTramiteRepLegalCURP").text(data.personaFisica.curp);
		$("#divDetalleEnTramiteRepresentanteLegal #detalleEnTramiteRepLegalPrimerApellido").text(data.personaFisica.primerApellido);
		$("#divDetalleEnTramiteRepresentanteLegal #detalleEnTramiteRepLegalSegundoApellido").text(data.personaFisica.segundoApellido);
		$("#divDetalleEnTramiteRepresentanteLegal #detalleEnTramiteRepLegalNombre").text(data.personaFisica.nombre);
		
		if (data.indActAdmonDominio == 1){
			//$("#divDetalleEnTramiteRepresentanteLegal #indActAdmonDominioDetalleEnTramite").checked=true;
			document.getElementById("indActAdmonDominioDetalleEnTramite").checked=true;
		} else {
			//$("#divDetalleEnTramiteRepresentanteLegal #indActAdmonDominioDetalleEnTramite").checked=false;
			document.getElementById("indActAdmonDominioDetalleEnTramite").checked=false;
		}
		
		/*$("#divDetalleEnTramiteMediosContactoRepLegal").html("");
		mcRepLegalDetalleTramite = new MedioContacto("divDetalleEnTramiteMediosContactoRepLegal", 2, tpPropietarioRepLegal, data.personaFisica.idPersona, idSolicitud, null);
		mcRepLegalDetalleTramite.init();*/
		
		// grid datos de contacto
		
		personaFisicaIdPersonaForMediosContactoDetalleTramite = data.personaFisica.idPersona;
		dtRepresentanteLegalDatosContactoDetalle.fnDraw();
		
		oDialogDetalleEnTramiteRepresentanteLegal.dialog('open');
	}
	
	
}


function procesaRespuesta(data) {
	dato=document.getElementById("responseHidden").value;	
	var oDialog;
	switch (dato){
		case "1":			
			oDialog=oDialogAgregarRepresentanteLegal;
			break;
		case "2":			
			oDialog=oDialogEliminarRepresentanteLegal;
			break;
		case "3":						
			oDialog=oDialogModificarRepresentanteLegal;
			break;
		case "4":
			oDialog=oDialogDeshacerEliminarRepresentanteLegal;
			break;
	}
	if (data==false){		
		oDialogErrorDuplicadoRepresentanteLegal.dialog("open");
	}
	cleanForm();
	dtRepresentanteLegalForSession.fnDraw();	                                          
	oDialog.dialog("close");
}



function cleanForm(){
document.getElementById("representanteLegalAux\.personaFisica\.idPersona").value="";
document.getElementById("representanteLegalAux\.personaFisica\.rfc").value="";
document.getElementById("representanteLegalAux\.personaFisica\.curp").value="";
document.getElementById("representanteLegalAux\.personaFisica\.primerApellido").value="";
document.getElementById("representanteLegalAux\.personaFisica\.segundoApellido").value="";
document.getElementById("representanteLegalAux\.personaFisica\.nombre").value ="";

}

function cancelarTramiteRL(){	
    fnHideErrores(sIdDialogEliminarRepresentanteLegalForSession);
    $( this ).dialog( "close" );
}

function fnOpenDialogEliminarRepresentanteLegal(){
	
	
	//Validamos que exista un elemento seleccionado.
	if(fnValidaRegistroSeleccionado(dtRepresentanteLegal)){
		accionSolicitada='ELIMINAR';
		var obRowSelected = fnGetRowSelected(dtRepresentanteLegal);
		var representanteLegal = new Object();
		representanteLegal.cveIdRepresentanteLegal = obRowSelected.cveIdRepresentanteLegal;
		sendToServer('/representanteLegal/fb/validaMovimientoPrevio',representanteLegal,
				callbackValidaMovimientoPrevio, false);
	}else if (fnValidaRegistroSeleccionado(dtRepresentanteLegalForSession)){
		oDialogEliminarRepresentanteLegalForSession.dialog('open');
	} else {
		//Mostramos mensaje de error
		fnDialogErrorSinSeleccionRepresentanteLegal();
	}
}

function fnOpenDialogModificarRepresentanteLegal(){
	
	//Validamos que exista un elemento seleccionado.
	if(fnValidaRegistroSeleccionado(dtRepresentanteLegal)){
		accionSolicitada='MODIFICAR';
		var obRowSelected = fnGetRowSelected(dtRepresentanteLegal);
		var representanteLegal = new Object();
		representanteLegal.cveIdRepresentanteLegal = obRowSelected.cveIdRepresentanteLegal;
		sendToServer('/representanteLegal/fb/validaMovimientoPrevio',representanteLegal,
				callbackValidaMovimientoPrevio, false);
	}else if (fnValidaRegistroSeleccionado(dtRepresentanteLegalForSession)){		
		oDialogEliminarRepresentanteLegalForSession.dialog('open');
	} else {
		//Mostramos mensaje de error
		fnDialogErrorSinSeleccionRepresentanteLegal();
	}
}


function callbackValidaMovimientoPrevio(response){
	if(!response.existeMovimientoPrevio){
		
		switch(accionSolicitada){
			case 'MODIFICAR':
				var listaDeMediosContactoPersona;
				var obRowSelected = fnGetRowSelected(dtRepresentanteLegal);
				var indice = obRowSelected.personaFisica.idPersona;
				$("#representanteLegalConsultar").css("display", "block");
				if (arrayDatos[indice].indActAdmonDominio.indexOf("no")!=-1){
					document.getElementById("modificar.indActAdmonDominio").checked=false;
				} else {
					document.getElementById("modificar.indActAdmonDominio").checked=true;
				}
				document.getElementById("modificar.nombre").value=arrayDatos[indice].personaFisica.nombre;
				document.getElementById("modificar.primerApellido").value=arrayDatos[indice].personaFisica.primerApellido;	
				document.getElementById("modificar.segundoApellido").value=arrayDatos[indice].personaFisica.segundoApellido;
				document.getElementById("modificar.rfc").value=arrayDatos[indice].personaFisica.rfc;
				document.getElementById("modificar.curp").value=arrayDatos[indice].personaFisica.curp;
				
				$("#divMediosContactoRepLegalPersonaFisicaModificacion").html("");
				var idPropietario=arrayDatos[indice].cveIdRepresentanteLegal;
				
				mcRepLegalModificacion = new MedioContacto("divMediosContactoRepLegalPersonaFisicaModificacion", 2, tpPropietarioRepLegal, idPropietario, idSolicitudActiva, idSujetoObligado);			 			
				mcRepLegalModificacion.init();
                mcRepLegalModificacion.extendValidation();
				
				oDialogModificarRepresentanteLegal.dialog('open');	
				break;
			case 'ELIMINAR':
				oDialogEliminarRepresentanteLegal.dialog('open');
				break;
		}
		
	}else{
		var oDialogo;
		var mensaje = "Ya se tiene un movimiento previo registrado para este representante legal.<br>";
		mensaje +="Actualmente tiene agendado un movimiento para "+response.accionPreviamenteSolicitada+ " al representante legal en cuesti\u00F3n.<br>";
		switch(response.accionPreviamenteSolicitada){
			case 'MODIFICAR':
				mensaje += "Por favor seleccione la opci\u00F3n: <b>'Detalle'</b> en la secci\u00F3n: <b>'Informaci\u00F3n a modificar'</b> para editar la informaci\u00F3n del representante.<br>"; 
				break;
			case 'ELIMINAR':
				mensaje += "Por favor seleccione la opci\u00F3n: <b>'Detalle'</b> en la secci\u00F3n: <b>'Informaci\u00F3n a modificar'</b> para visualizar la informaci\u00F3n del representante.<br>";
				break;
			default: break;
		}
		
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error",
				mensaje, 
				true, 
				undefined, undefined, 250,700);
	}
}


function fnDialogErrorSinSeleccionRepresentanteLegal(){
	oDialogErrorSinSeleccionRepresentanteLegal =  $( sIdDialogErrorSinSeleccionRepresentanteLegal ).dialog({
		autoOpen:false,
		resizable: false,
		height:140,
		modal: true,
		buttons: {
			'Aceptar': function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
	oDialogErrorSinSeleccionRepresentanteLegal.dialog('open');
}

function fnDialogErrorSinSeleccionPersonaParaRepresentanteLegal(){
	oDialogErrorSinSeleccionPersonaParaRepresentanteLegal =  $( sIdDialogErrorSinSeleccionPersonaParaRepresentanteLegal ).dialog({
		autoOpen:false,
		resizable: false,
		height:180,
		modal: true,
		buttons: {
			'Aceptar': function() {
				$( this ).dialog( "close" );
				fnOpenBuscarPersonaFisica();
			}
		}
	});
	
	oDialogErrorSinSeleccionPersonaParaRepresentanteLegal.dialog('open');
}

function validarRepLegal(url, callbackAEjecutar){
	//$.blockUI();
	//inicializarSujetoTramiteParaValidacionDeSolicitudActiva();
	sendToServer(url,null,
			callbackAEjecutar, true);
}

function callbackValidarRepLegal(response){
	if (response != undefined || response != null){
		if (response == 'OK'){
			var rfcEnviarValidacion;
			if (tipoPersonaFiscal == "FISICA") {
				rfcEnviarValidacion=$("#fisica\\.rfc").val().toUpperCase();
			}else{
				rfcEnviarValidacion=$("#moral\\.rfc").val().toUpperCase();
			}
			validaSolicitudTramiteActivo('/afiliacion/validarTramiteActivo?tipoTramite='+representanteLegal+'&idSolicitud='+idSolicitud+'&rfc='+rfcEnviarValidacion,callbackActualizacionRepresentanteLegalTramite);
		} else {
			var oDialogo;
			construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", response, true, undefined, undefined, 150, 500);
			//alert(response);
		}
	} else {
		var oDialogo;
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Hay un problema al guardar el tr\u00E1mite, contacte al administrador del sistema.", true,
				undefined,undefined,150,500);
		//alert('Hay un problema al guardar el tr\u00E1amite, contacte al administrador del sistema.');
	}
	
}

function guardarRepresentante(){
	validarRepLegal('/representanteLegal/fb/realizarValidaciones?tipoPersonaFiscal='+tipoPersonaFiscal+'&operacion='+codigoOperacionGuardar, callbackValidarRepLegal);	
}

function guardarRL(){
	construirDialogoConfirmar('Guardar',guardarTramiteRepresentanteLegal);
}


function callbackActualizacionRepresentanteLegalTramite(response){
	if(isOpRatificacion){
		checkedObject = $('#chkRatificaRL');//Se asigna a la variable global checkedObject el objeto check que se deseleccionara en caso de presionar cancelar en la pantalla de confirmación
		callbackValidacionTramiteActivo(response, ratificaTramiteRepresentante, deseleccionarCheck);
		isOpRatificacion=false;
	}else{
		callbackValidacionTramiteActivo(response, guardarTramiteRepresentanteLegal);
	}	
}

function guardarTramiteRepresentanteLegal(){
	construirObjetoRepresentanteLegal();
	sendToServer('/afiliacion/actualizarTramiteRepresentanteLegal?idSolicitud='+idSolicitud,sujetoObigadoTramite,
			callbackRepresentanteLegalTramite, false);
}


function callbackRepresentanteLegalTramite(response){
	callbackEnviarTramite(response, 150, 750);
	evaluarBotonesSolicitud();
}

function construirObjetoRepresentanteLegal(){
	if (sujetoObigadoTramite == undefined || sujetoObigadoTramite == null) {
		sujetoObigadoTramite = new Object();
	}
	sujetoObigadoTramite.cveIdSujetoObligado=$("#cveIdSujetoObligado").val();
	sujetoObigadoTramite.tipoPersonaFiscal = $("#tipoPersonaFiscal").val().toUpperCase();
	if (tipoPersonaFiscal == "FISICA") {
		sujetoObigadoTramite.fisica = new Object();
		sujetoObigadoTramite.fisica.idPersona=$("#fisica\\.idPersona").val();
	} else {
		sujetoObigadoTramite.moral = new Object();
		sujetoObigadoTramite.moral.idPersona=$("#moral\\.idPersona").val();
	}
}

function configurarBusquedaPersonaFisica(){
	/*
	 * configuracion para buscar a la persona fisica
	 */
	$.getScript("/gestionIndividuo-web/static/resources/js/delta/personas/fisica/PersonaFisica.js", function(){
	});
}

/**
 * Funcion para invocar al proceso de buscar persona fisica
 */
function fnOpenBuscarPersonaFisica() {
	PersonaFisicaCtrl.init('personaFisica', PersonaFisicaCtrl.tipoServicio.COMPLETO, PersonaFisicaCtrl.tipoContexto.INTERNO, PersonaFisicaCtrl.tipoBusqueda.SAT);
	if (PersonaFisicaCtrl.persona != null){
		PersonaFisicaCtrl.persona = null;
		PersonaFisicaCtrl.personaEncontrada = false;
	}
	PersonaFisicaCtrl.setOnCloseCallback(fnOnPersonaReturn);
	PersonaFisicaCtrl.buscar();
}

function ratificaRL(){
	var esRatificado = $("#chkRatificaRL").attr("checked");
	if(esRatificado == undefined){//Esto significa que el elemento no esta checado
		tramiteRepresentanteLegalRatificado=false;
		evaluarBotones();
		
	}else{//Si esta checado
		validarRepLegal('/representanteLegal/fb/realizarValidaciones?tipoPersonaFiscal='+tipoPersonaFiscal+'&operacion='+codigoOperacionRatificar, callbackValidarRepLegalAlRatificar);
	}
	
}

function callbackValidarRepLegalAlRatificar(response){
	if (response != undefined || response != null){
		if (response == 'OK'){
			var rfcEnviarValidacion;
			if (tipoPersonaFiscal == "FISICA") {
				rfcEnviarValidacion=$("#fisica\\.rfc").val();
			}else{
				rfcEnviarValidacion=$("#moral\\.rfc").val();
			}
			validaSolicitudTramiteActivo('/afiliacion/validarTramiteActivo?tipoTramite='+representanteLegal+'&idSolicitud='+idSolicitud+'&rfc='+rfcEnviarValidacion,callbackActualizacionRepresentanteLegalTramite);
			isOpRatificacion=true;
		} else {
			$("#chkRatificaRL").attr("checked", false);
			var oDialogo;
			construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", response, true, undefined, undefined, 150, 500);
			//alert(response);
		}
	} else {
		var oDialogo;
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error", "Hay un problema al guardar el tr\u00E1amite, contacte al administrador del sistema.", true,
				undefined, undefined, 150, 500);
		//alert('Hay un problema al guardar el tr\u00E1amite, contacte al administrador del sistema.');
	}
	
}

function ratificaTramiteRepresentante(){
	construirObjetoRepresentanteLegal();
	sendToServer('/afiliacion/ratificarTramite?tipoTramite='+tipoTramiteRepresentanteLegal+'&idSolicitud='+idSolicitud,sujetoObigadoTramite,
			callbackRatificarRepresentanteLegal, false);
	evaluarBotones();
}

function callbackRatificarRepresentanteLegal(response){
	$.unblockUI();
	callbackEnviarTramite(response, 150, 750);
	if(response.mensajeError!=undefined && response.mensajeError!=null){
		deseleccionarCheck();
		tramiteRepresentanteLegalRatificado=false;
	}else{
		$("#mensajeRepresentanteLegalRatificacion").show();
		$("#listaForSession").hide();
		$("#btnGridRL").hide();
		tramiteRepresentanteLegalRatificado=true;
		tramiteRepresentanteLegalActivo=true;
		evaluarBotonesSolicitud();
	}

}

function evaluarBotones(){
	$("#mensajeRepresentanteLegalRatificacion").hide();
	$("#dgErrorSinSeleccionRepresentanteLegal").hide();
	$("#btnGridRL").show();
	evaluarBotonesTramiteRepresentanteActivo();
	evaluarBotonesTramiteRepresentanteRatificado();
	
	
//	if(!esNuevaSolicitud){
//		if(!isOperadorIMSS){
//			$("#listaForSession").hide();
//			$("#btnGridRL").hide();
//			$("#grupoRatificarRL").hide();
//			if(tramiteRepresentanteLegalRatificado){
//				$("#mensajeRepresentanteLegalRatificacion").show();
//			}else if(!tramiteRepresentanteLegalRatificado){
//				$("#mensajeRepresentanteLegalRatificacion").hide();
//			}
//		}else if(isOperadorIMSS){
//			evaluarBotonesTramiteRepresentanteActivo();
//			evaluarBotonesTramiteRepresentanteRatificado();
//			if(tramiteRepresentanteLegalRatificado){
//				$("#mensajeRepresentanteLegalRatificacion").show();
//				$("#listaForSession").hide();
//				$("#btnGridRL").hide();
//			}
//		}
//	}
}

function evaluarBotonesTramiteRepresentanteActivo(){
	if(tramiteRepresentanteLegalActivo){
		$("#listaForSession").show();
	}else if(!tramiteRepresentanteLegalActivo){
		$("#listaForSession").show();
	}
}

 

function evaluarBotonesTramiteRepresentanteRatificado(){
	if(tramiteRepresentanteLegalRatificado){
		$("#listaForSession").hide();
		$("#btnGridRL").hide();
		$("#mensajeRepresentanteLegalRatificacion").show();
		$("#chkRatificaRL").attr("checked","checked");
	}
}

function cancelarEdicionMediosContactoRepLegalEnDetalle(){
	
	var oForm = new Object();
	oForm.personaFisica = new Object();
	
	var sSource = '/representanteLegal/fb/cancelarEdicionMediosContactoRepLegalEnDetalle';
	
	oForm.personaFisica.idPersona = $("#divDetalleEnTramiteRepresentanteLegal #personaFisicaIdPersona").val();
	oForm.personaFisica.rfc = $("#divDetalleEnTramiteRepresentanteLegal #detalleEnTramiteRepLegalRFC").val();
	oForm.personaFisica.curp = $("#divDetalleEnTramiteRepresentanteLegal #detalleEnTramiteRepLegalCURP").val();
	oForm.personaFisica.primerApellido = $("#divDetalleEnTramiteRepresentanteLegal #detalleEnTramiteRepLegalPrimerApellido").val();
	oForm.personaFisica.segundoApellido = $("#divDetalleEnTramiteRepresentanteLegal #detalleEnTramiteRepLegalSegundoApellido").val();
	oForm.personaFisica.nombre = $("#divDetalleEnTramiteRepresentanteLegal #detalleEnTramiteRepLegalNombre").val();
	
	sendToServer(sSource, oForm, cancelarEdicionMediosContactoRepLegalEnDetalleCallback, false);
}

function cancelarEdicionMediosContactoRepLegalEnDetalleCallback(response){
	// verificar por true o false en response
}

function validaCambiosDetalleEnTramite(){
	validaMediosContactoRequeridosDeRepresentante(guardarCambiosDetalleEnTramiteRepresentanteLegal);
}

function guardarCambiosDetalleEnTramiteRepresentanteLegal(response){
	
	if(!response.datosContactoValidos){
		var oDialogo;
		construirDialogoGenerico("#dialogoMensajes", oDialogo, "Error",
				"Debe capturar al menos un Tel\u00E9fono fijo o m\u00F3vil y Correo Electr\u00F3nico v\u00E1lidos", true,
				undefined, undefined, 150,500);
		return false;
	} else {
		var oForm = new Object();
		oForm.personaFisica = new Object();
		
		var sSource = '/representanteLegal/fb/guardarCambioDetalleEnTramiteRepresentanteLegal';

		oForm.personaFisica.idPersona = $("#divDetalleEnTramiteRepresentanteLegal #personaFisicaIdPersona").val();
		oForm.personaFisica.rfc = $("#divDetalleEnTramiteRepresentanteLegal #detalleEnTramiteRepLegalRFC").val();
		oForm.personaFisica.curp = $("#divDetalleEnTramiteRepresentanteLegal #detalleEnTramiteRepLegalCURP").val();
		oForm.personaFisica.primerApellido = $("#divDetalleEnTramiteRepresentanteLegal #detalleEnTramiteRepLegalPrimerApellido").val();
		oForm.personaFisica.segundoApellido = $("#divDetalleEnTramiteRepresentanteLegal #detalleEnTramiteRepLegalSegundoApellido").val();
		oForm.personaFisica.nombre = $("#divDetalleEnTramiteRepresentanteLegal #detalleEnTramiteRepLegalNombre").val();
		
		if (document.getElementById("indActAdmonDominioDetalleEnTramite").checked==true){
			oForm.indActAdmonDominio=1;
		} else {
			oForm.indActAdmonDominio=0;
		}
		
		sendToServer(sSource, oForm, callbackRefreshListaTramiteRepLegal, false);
		
		oDialogDetalleEnTramiteRepresentanteLegal.dialog("close");
		return true;
		
	}
}

function callbackRefreshListaTramiteRepLegal(response){
	dtRepresentanteLegalForSession.fnDraw();
}

function validaMediosContactoRequeridosDeRepresentante(callback){
	var representante = new Object();
	var sSource = "/representanteLegal/validaMediosContacto";
	representante.personaFisica=new Object();
	representante.personaFisica.idPersona = $("#divDetalleEnTramiteRepresentanteLegal #personaFisicaIdPersona").val();
	sendToServer(sSource, representante , callback, false); 
}
