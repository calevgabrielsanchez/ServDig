$(document).ready(function() {
	generaTabla();
});

/**
 * Metodo para generar la tabla paginada de la consulta externa de las notificaciones
 */
function generaTabla() {
	
	$("#idTablaConsultaExterna").dataTable({
		// Indicamos el numero de registros que mostrara la tabla
		"iDisplayLength": 10,
		// Muestra el mensaje de processing... en la tabla mientras la llamada Ajax se está ejecutando.
		"bProcessing": true,
		// Indicamos que se procesara el filtrado del lado del servidor, asi como la paginacion y el ordenamiento
        "bServerSide": true,
		// Indicamos que la fuente de la peticion ajax
        "sAjaxSource": getAppContextParaJS()+"/estrados/consultaExternaPaginada.do",
        // Aqui indicamos el tipo de formato de la paginación, para que aparezcan los numeros de la paginacion, si no, solo aparecera el next o prev
        // "sPaginationType": "full_numbers",
        "sPaginationType": "bootstrap",
        // Establece el método HTTP que se utiliza para realizar la llamada Ajax para el procesamiento del lado del servidor o del Ajax de datos de origen. 
		"sServerMethod": "POST",
		// Aplicación de estilos estandar de JqueryUI.
		"bJQueryUI": true,
		// Indicamos que las columnas podran ser organizadas en ascendente o descendente.
		// Se puede desactivar la opcion para cada columna con la propiedad "bSortable": false
		"bSort": true,
		// Indicamos que se va a mostrar un buscador
		"bFilter" : true,
		"bDestroy": true,
		"aaSorting" : [[3, 'desc']],
		"aoColumns" : [{
				"sTitle" : "Nombre del Sujeto a notificar",
				"sWidth": "215px",
				"mDataProp" : "razonSocial"
			},{
				"sTitle" : "Autoridad que notifica",
				"bSortable": false,
				"sWidth": "170px",
				"fnRender": function ( objeto, val ) {
								var res;
								res = generaAutoridadNotifica(objeto);
								return res;
							}
			},{
				"sTitle" : "Documento a Notificar",
				"bSortable": false,
				"mDataProp" : "tipodocumentoDTO.desTipodocumento"
			},{
				"sTitle" : "Fecha de publicaci\u00f3n",
				"sWidth": "170px",
				"mDataProp" : "fecPublicacionCadena"
			},{
				"sTitle" : "Archivos",
				"bSortable": false,
				"sWidth": "145px",
				"fnRender": function ( objeto, val ) {
								var res;
								res = generaBotonArchivos(objeto.aData['cveNotificaciones'], objeto);
								return res;
							}
			}]
    });
}

/**
 * Metodo para generar el fnRender de la Autoridad que notifica de la tabla consulta externa
 * @param objeto
 * @returns {String}
 */
function generaAutoridadNotifica(objeto) {
	var autoridadNotifica =	"";
	
	if (objeto.aData['delegacionDTO'].desRIMSSDelegacion == null && objeto.aData['subdelegacionDTO'].desRIMSSSubDelegacion == null) {
		autoridadNotifica = objeto.aData['ssoVwUsuarioDTO'].desAreaNorma;
	} else if (objeto.aData['delegacionDTO'].desRIMSSDelegacion != null && objeto.aData['subdelegacionDTO'].desRIMSSSubDelegacion == null) {
		autoridadNotifica = objeto.aData['delegacionDTO'].desRIMSSDelegacion
	} else if (objeto.aData['delegacionDTO'].desRIMSSDelegacion != null && objeto.aData['subdelegacionDTO'].desRIMSSSubDelegacion != null) {
		autoridadNotifica = objeto.aData['subdelegacionDTO'].desRIMSSSubDelegacion;
	}
	return autoridadNotifica;
}

/**
 * Metodo para generar el fnRender del boton de Accion de la tabla de consulta interna
 * @param id
 * @param objeto
 * @returns {String}
 */
function generaBotonArchivos(cveNotificaciones, objeto) {
	
	var botonHTML=''
	if (objeto.aData['listDocumentosAdjuntosDTOs'] != null) {
		botonHTML += ''
		+'<div class="btn-group">'
		+'	<button type="button" class="btn btn-primary">Archivos</button>'
		+'	<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" style="padding: 8px">'
		+'		<span class="caret"></span>'
		+'	</button>'
		+'	<ul class="dropdown-menu" role="menu">'
			for(i = 0; i < objeto.aData['listDocumentosAdjuntosDTOs'].length; i++) {
				botonHTML +=''
				+'<li>'
				+'	<a onclick="visorArchivoPDF('+objeto.aData['listDocumentosAdjuntosDTOs'][i].cveDoctoAdjunto+', '+cveNotificaciones+')"> '
				+'		'+objeto.aData['listDocumentosAdjuntosDTOs'][i].tipoAdjuntoDTO.desTipoAdjunto+''
				+'	</a>'
				+'</li>'
			}
		+'	</ul>'
		+'</div>';
	}
	return botonHTML;
}

/**
 * Metodo para generar el fnRender de los Archivos de la tabla consulta interna
 * @param objeto
 * @param cveNotificaciones
 * @returns {String}
 */
function generaColumnaArchivos(objeto, cveNotificaciones) {
	var columnaArchivos = '';
	
	if (objeto.aData['listDocumentosAdjuntosDTOs'] != null) {
		for(i = 0; i < objeto.aData['listDocumentosAdjuntosDTOs'].length; i++) {
			columnaArchivos +=''
			+'<div class="btn-group">'
			+'	<button type="button" class="btn btn-primary" onclick="visorArchivoPDF('+objeto.aData['listDocumentosAdjuntosDTOs'][i].cveDoctoAdjunto+', '+cveNotificaciones+')">PDF</button>'
			+'</div>'
			+'	<span>&nbsp;&nbsp;&nbsp;'+objeto.aData['listDocumentosAdjuntosDTOs'][i].tipoAdjuntoDTO.desTipoAdjunto+'</span>'
			if (i < objeto.aData['listDocumentosAdjuntosDTOs'].length-1) {
			    columnaArchivos += '<br/><br/>';
		    }
		}
	}
	return columnaArchivos;
}