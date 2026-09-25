//objeto para guardar la referencia al datTable de solicitudes registradas mediante ventanilla
var solicitudesRegistradasVentanilla = null;
//Objeto para guardar la referencia al dataTable de solicitudes registradas mediente otro medio
var solicitudesRegistradasOtrosOrigenes = null;
//Objeto para guardar la referencia al dataTable de solicitudes registradas mediente otro medio
var solicitudesConcluidas = null;
//ENUM de los tipos de tablas que se usaran o se consultara
var TIPO_TABLA_ENUM = {
	'VENTANILLA': 0,
	'OTROS_ORIGENES': 1,
	'CONCLUIDAS': 2
}
//urls de cargas de tablas de solicitudes
var URLS_DATATABLE = [context_path + '/solicitud/muestraSolicitudesRegistradas',
                      context_path + '/solicitud/muestraSolicitudesOtrosOrigenes',
                      context_path + '/solicitud/muestraSolicitudesConcluidas'];

var IDS_ACCORDION = ["accordionSolicitudRegistrada","accordionSolicitudOtrosMedios","accordionSolicitudConcluidas"];
var IDS_TABLAS = ["solicitudesRegistradasTable","solicitudesRegistradasOtrosMedios","solicitudesConcluidasTable"];

$(window).load(function(){
	//opciones en comun que tienen todas los acordiones de solicitud
	var opcionesComunesAcordion = {
		active:false,
		collapsible: true,
		header : 'div',
		 heightStyle: "content",
         autoHeight: false
	};
	
	$("#accordionSolicitudRegistrada").accordion($.extend({},opcionesComunesAcordion,{
		change: functionAcordionRegistradas
	}));
	
	
	$("#accordionSolicitudOtrosMedios").accordion($.extend({},opcionesComunesAcordion,{
		change: functionAcordionOtrosOrigenes
	}));
	
	$("#accordionSolicitudConcluidas").accordion($.extend({},opcionesComunesAcordion,{
		change: functionAcordionConcluidas
	}));
});

function initSolicitudPenAut(){
	//location.href = context_Path + "/solicitud/initSolicitudesPenAut?idSubDelegacion="+subDelegacion+"&numNSS="+nss+"&refFolio="+folio;
}

var functionAcordionRegistradas = function(event,ui) {
	if(solicitudesRegistradasVentanilla == null) {
		//cargamos la tabla de solicitudes registradas mediante ventanilla
		crearTablaSolicitudes(IDS_TABLAS[TIPO_TABLA_ENUM.VENTANILLA],TIPO_TABLA_ENUM.VENTANILLA);
	}
};

var functionAcordionOtrosOrigenes = function(event, ui) {
	if(solicitudesRegistradasOtrosOrigenes == null) {
		//cargamos la tabla de las solicitudes registradas mediante cualquier medio que no sea ventanilla
		crearTablaSolicitudes(IDS_TABLAS[TIPO_TABLA_ENUM.OTROS_ORIGENES],TIPO_TABLA_ENUM.OTROS_ORIGENES);
	}
};

var functionAcordionConcluidas = function(event, ui) {
	if(solicitudesConcluidas == null) {
		//cargamos la tabla de las solicitudes concluidas
		crearTablaSolicitudes(IDS_TABLAS[TIPO_TABLA_ENUM.CONCLUIDAS],TIPO_TABLA_ENUM.CONCLUIDAS);
	}
};

/**
 * Metodo que se usa para pintar las tablas de solicitudes registradas mediante ventanilla y mediante otro origen
 * distinto a ventanilla como lo son internet, portal ciudadano y movil
 * @param idTabla - El id de la tabla <table> en el que se pintará la informacion
 * @param solicitudesVentanilla - bandera para indicar si se mostraran las solicitudes realizadas en ventanilla en caso de ser true 
 * o las solicitudes registradas mediante otro medio en caso de que la bandera sea false
 */
function crearTablaSolicitudes(idTabla,tipoTabla) {
	//verificamos cual consulta se va a realizar
	var urlConsulta = URLS_DATATABLE[tipoTabla];
	//obtenemos las columas de las tablas de acuerdo al tipo
	var columnasTabla = obtenerColumnasTabla(tipoTabla);
	//creamos la tabla y cargamos los datos mediante el plugin
	var dataTableSolicitudes = $('#'+idTabla).dataTable({
		sScrollX: "100%",
		bJQueryUI : true,
        bFilter : false,
        bInfo:true,
        bSort: false,
        "bDestroy":true,
        "bPaginate": true,
        "bAutoWidth" : false,
        "iDeferLoading" : 0,
        "bServerSide" : true, 
        "bProcessing" : true,
        "aoColumns" : columnasTabla,
        "sAjaxSource" : urlConsulta,
        "fnServerData" : function(sSource, aoData, fnCallback) {
        	aoData.push({
        		"name" : "sSearch",
        		"value" : ''
        	});

        	var wrapper = new Object();
        	wrapper.aoData = aoData;
        	var oForm = {};
        	wrapper.oForm = oForm;

        	$.postJSON(sSource, wrapper, function(data) {
        		fnCallback(data);
        		//redibujamos la tabla
        		//$("#"+IDS_ACCORDION[tipoTabla]).accordion('resize');
        	}).error(function(jqXHR, textStatus, errorThrown) {
        		error(errorThrown+" - "+'No fue posible obtener las solicitudes registradas.');
        	});
        }
	});
	
	//Creamos el evento del botond e mas y menos de la tabla de solicitudes
	$('#'+idTabla+' tbody > tr > td > img').live('click', function () {

		var nTr = $(this).parents('tr')[0];

		if ( dataTableSolicitudes.fnIsOpen(nTr) ){
			this.src =  context_path +"/resources/imagenes/details_open.png";
			dataTableSolicitudes.fnClose( nTr );
		}else {
			this.src = context_path +"/resources/imagenes/details_close.png";
			dataTableSolicitudes.fnOpen( nTr, fnFormatDetails(dataTableSolicitudes, nTr), 'Detalle' );
		}
	});
	//redibujamos la tabla
	dataTableSolicitudes.fnDraw();
	
	if(tipoTabla == TIPO_TABLA_ENUM.VENTANILLA) {
		solicitudesRegistradasVentanilla = dataTableSolicitudes;
	} else if(tipoTabla == TIPO_TABLA_ENUM.OTROS_ORIGENES){
		solicitudesRegistradasOtrosOrigenes = dataTableSolicitudes;
	} else {
		solicitudesConcluidas = dataTableSolicitudes
	}
}

/**
 * Funcion que retorma las columas que se pintaran en la tabla dependiendo del tipo de tabla que se este pintando
 * @param tipoTabla
 * @returns
 */
function obtenerColumnasTabla(tipoTabla) {
	
	/**
	 * Columnas que comparten todas la tablas
	 */
	var columnas = [
		{ 
			"sTitle" : "",
			"sDefaultContent": function(obj) {
				return "<img   src='"+ context_path +"/resources/imagenes/details_open.png'/>";
			}
		},
		{ 
			"sTitle"    : "Folio de la solicitud",
			"mDataProp" : "noFolioSolicitud"
		},
		{ 
			"sTitle" : "Origen",
			"sDefaultContent": "",
			"sClass":"dtJustifyClassColumn",
			fnRender: function(obj) {
				var origen = obj.aData.origenSolicitud;
		
				if(typeof origen !== "undefined" && origen != null) {
					return obj.aData.origenSolicitud.descripcion;
				}
		
				return "";
			}
		},
		{ 
			"sTitle" : "Fecha",
			"sDefaultContent": "",
			"mDataProp" : "fechaSolicitud"
		},
		
		{   "sTitle" : "Solicitante",
			"sDefaultContent": "",
			fnRender: function(obj) {
				if(obj.aData.solicitante != null)
					return ""+ obj.aData.solicitante.usuario+"";
				else
					return "";
			}
		}, 
		{ 
			"sTitle" : "Tipo Solicitud",
			"sClass":"dtJustifyClassColumn",
			"mDataProp" : "tipoSolicitud.descripcion"
		}
	];
	
	//columa que muestra el estado esta solo es para las concluidas
	var columnaEstado = { 
		"sTitle" : "Estado Solicitud",
		"sClass":"dtJustifyClassColumn",
		"mDataProp" : "estadoSolicitud.descripcion"
    };
	
	//columna que muestra el boton
	var columnaBoton =  {   
		"sTitle" : "",
        "sDefaultContent": "",
        fnRender: function(obj) {
        	//botones que se mostraran
        	var BOTONES_ACCION = ["<button type='button' class='mboton' onclick='detalleSolicitud("+obj.aData.solicitudId+");'>Detalle</button>",
        	    "<button type='button' class='mboton' onclick='cancelarSolicitud("+obj.aData.solicitudId+",solicitudesRegistradasOtrosOrigenes);'>Cancelar</button>",
        	    "<button type='button' class='mboton' onclick='ejecutarConsultaSolicitudPorFolio(\""+obj.aData.noFolioSolicitud+"\");'>Detalle</button>"]
        	//se valida cual boton se va a pintar
        	return BOTONES_ACCION[tipoTabla];
        }
	}
	
	//Si la tabla es de las concluidas agregamos la columa de estado
	if(tipoTabla == TIPO_TABLA_ENUM.CONCLUIDAS) {
		columnas.push(columnaEstado);
	}
	
	//al final siempre agregamos la columa que contiene el boton de accion
	columnas.push(columnaBoton)
	
	return columnas;
}

/**
 * Funcion que agrega el detalla a la tabla de solicitudes
 * @param oTable
 * @param nTr
 * @returns {String}
 */
/* Funcion que agrega el detalle a cada fila */
function fnFormatDetails ( oTable, nTr )
{
	
	var aData = oTable.fnGetData( nTr );
	
	
	var sOut = '<div class="innerDetails">';
	
	
	sOut += '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px; background-color:#CCCCCC; width:100%; ">';
	sOut += '	<caption style="background-color:#999999; "><strong><font color="#000000">Tr&aacute;mites</font></strong></caption>';
	
	for(var i=0; i< aData.tramites.length; i++) {
		var tramite = aData.tramites[i];
		sOut += '	<tr>';
		sOut += '	 	<td align="right">Tr&aacute;mite: </td>';
		sOut += '		<td>';
		sOut += '		<input readonly="readonly" type="text"';
		sOut += '		style="width: 200px"';
		sOut += '		value="'+tramite.tipoTramite.descripcion+'"/>';
		sOut += '		</td>';
				
		sOut += '		<td align="right">Persona afectada: </td>';
		sOut += '		<td>';
		sOut += '		<input readonly="readonly" type="text"';
		sOut += '		style="width: 200px" value="';
		if(tramite.persona != null) {
			sOut += '' + tramite.persona.nombre + ' ';
			sOut += '' + tramite.persona.primerApellido != null ? tramite.persona.primerApellido + ' ': '';
			sOut += '' + tramite.persona.segundoApellido != null && tramite.persona.segundoApellido != 'NULL' && tramite.persona.segundoApellido != undefined ? tramite.persona.segundoApellido : '';
		} else {
			sOut += 'NO ENCONTRADO';
		}
		sOut += '"/></td>';
		sOut += '		<td align="right">Estado tr&aacute;mite: </td>';
		sOut += '		<td>';
		sOut += '		<input readonly="readonly" type="text"';
		sOut += '		style="width: 200px" value="';
		sOut += '' + tramite.estadoTramite.descripcion;
		sOut += '"/></td>';
		sOut += '	</tr>';
	}
	
	sOut += '	<tr>';
	sOut += '	 	<td><br></td>';
	sOut += '	</tr>';
	
	
	sOut += '</table>';
	sOut +='</div>';
	
	return sOut;
}

//Funcion general para mostrar el detalle de una solicitud a trav�s de su folio
function ejecutarConsultaSolicitudPorFolio (folio) {
	//console.log("Folio de la solicitud a consultar: %s", folio);
	if (folio == null || folio == '' || typeof folio === 'undefined') {
		return false;
	}
	
	DetalleSolicitudCtrl.init("detalleSolicitudComponent",folio);
	DetalleSolicitudCtrl.abrir();
	
}
