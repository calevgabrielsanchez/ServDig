var tablaInterna;

$(document).ready(function() {
	recuperaDatosHeader();
	generarTablasNotificaciones();
});

/**
 * Metodo para definir que tabla se va a mostrar en el listado de consulta interna.
 * Esto es definido dependiendo el usuario que loguea, ya sea por normativa o delegaci�n, subdelegaci�n.
 */
function generarTablasNotificaciones() {
	if (ssoVwUsuarioDTO.cveDelegacion != null || ssoVwUsuarioDTO.cveSubdelegacion != null) {
		generaTablaDelSub();
	} else {
		generaTablaNormativa(ssoVwUsuarioDTO.desAreaNorma);
	}
}

/**
 * Metodo para generar la tabla paginada de la consulta interna de las notificaciones
 */
function generaTablaDelSub() {
	
	tablaInterna = $("#idTablaConsultaInterna").dataTable({
		//ocultamos la informacion de la consulta total de registros
		"oLanguage": {"sInfoFiltered": ""},
		// Indicamos el numero de registros que mostrara la tabla
		"iDisplayLength": 10,
		// Muestra el mensaje de processing... en la tabla mientras la llamada Ajax se est� ejecutando.
		"bProcessing": true,
		// Indicamos que se procesara el filtrado del lado del servidor, asi como la paginacion y el ordenamiento
        "bServerSide": true,
		// Indicamos que la fuente de la peticion ajax
        "sAjaxSource": getAppContextParaJS()+"/estrados/consultaInternaPaginada.do",
        // Aqui indicamos el tipo de formato de la paginaci�n, para que aparezcan los numeros de la paginacion, si no, solo aparecera el next o prev
        // "sPaginationType": "full_numbers",
        "sPaginationType": "bootstrap",
        // Establece el m�todo HTTP que se utiliza para realizar la llamada Ajax para el procesamiento del lado del servidor o del Ajax de datos de origen. 
		"sServerMethod": "POST",
		// Aplicaci�n de estilos estandar de JqueryUI.
		"bJQueryUI": true,
		// Indicamos que las columnas podran ser organizadas en ascendente o descendente.
		// Se puede desactivar la opcion para cada columna con la propiedad "bSortable": false
		"bSort": true,
		// Indicamos que se va a mostrar un buscador
		"bFilter" : true,
		"bDestroy": true,
		"aaSorting" : [[3, 'desc']],
		"aoColumns" : [{
//				"sTitle" : "N\u00famero de Registro",
//				"bSortable": false,
//				"sWidth": "100px",
//				"mDataProp" : "cveNotificaciones"
//			},{
				"sTitle" : "",
				"bVisible": false, 
				"bSortable": false,
				"mDataProp" : "razonSocial"
			},{
				"sTitle" : "Sujeto a Notificar",
				"mDataProp" : "razonSocial"
			},{
				"sTitle" : "Documento a Notificar",
				"bSortable": false,
				"mDataProp" : "tipodocumentoDTO.desTipodocumento"
			},{
				"sTitle" : "Fecha de publicaci\u00f3n",
				"sWidth": "170px",
				"mDataProp" : "fecPublicacionCadena"
			},{
				"sTitle" : "Status",
				"bSortable": false,
				"mDataProp" : "statusDTO.desEstatus"
//			},{
//				"sTitle" : "Archivos",
//				"bSortable": false,
//				"sWidth": "130px",
//				"fnRender": function ( objeto, val ) {
//								var res;
//								res = generaColumnaArchivos(objeto, objeto.aData['cveNotificaciones']);
//								return res;
//							}
			},{
				"sTitle" : "Acci\u00f3n",
				"bSortable": false,
				"fnRender": function ( objeto, val ) {
								var res;
								res = generaBotonAccion(objeto.aData['cveNotificaciones'], objeto);
								return res;
					        }
			}]
		}).columnFilter({
			sPlaceHolder: "head:before",
	        "aoColumns": [
	        	null,
	        	null,
	        {
	        	"type": "select",
            	values:  obtenerDescripcionFiltroTipoDocumento()
	        },
	        	null,
	        {
	        	"type": "select",
            	values:  obtenerDescripcionFiltroStatus()
	        },
	        	null
            ]
	    });
}

/**
 * Metodo para generar la tabla paginada de la consulta interna de las notificaciones
 */

function generaTablaNormativa(desAreaNorma) {
	
	tablaInterna=$("#idTablaConsultaInterna").dataTable({
		//ocultamos la informacion de la consulta total de registros
		"oLanguage": {"sInfoFiltered": ""},
		// Indicamos el numero de registros que mostrara la tabla
		"iDisplayLength": 10,
		// Muestra el mensaje de processing... en la tabla mientras la llamada Ajax se est� ejecutando.
		"bProcessing": true,
		// Indicamos que se procesara el filtrado del lado del servidor, asi como la paginacion y el ordenamiento
        "bServerSide": true,
		// Indicamos que la fuente de la peticion ajax
        "sAjaxSource": getAppContextParaJS()+"/estrados/consultaInternaPaginada.do",
        // Aqui indicamos el tipo de formato de la paginaci�n, para que aparezcan los numeros de la paginacion, si no, solo aparecera el next o prev
        // "sPaginationType": "full_numbers",
        "sPaginationType": "bootstrap",
        // Establece el m�todo HTTP que se utiliza para realizar la llamada Ajax para el procesamiento del lado del servidor o del Ajax de datos de origen. 
		"sServerMethod": "POST",
		// Aplicaci�n de estilos estandar de JqueryUI.
		"bJQueryUI": true,
		// Indicamos que las columnas podran ser organizadas en ascendente o descendente.
		// Se puede desactivar la opcion para cada columna con la propiedad "bSortable": false
		"bSort": true,
		// Indicamos que se va a mostrar un buscador
		"bFilter" : true,
		"bDestroy": true,
		"aaSorting" : [[3, 'desc']],
		"aoColumns" : [{
//				"sTitle" : "N\u00famero de Registro",
//				"bSortable": false,
//				"sWidth": "100px",
//				"mDataProp" : "cveNotificaciones"
//			},{
				"sTitle" : "Autoridad que notifica",
				"bSortable": false,
				"sWidth": "170px",
				"fnRender": function ( objeto, val ) {
								var res;
								res = generaAutoridadNotifica(objeto);
								return res;
							}
			},{
				"sTitle" : "Sujeto a Notificar",
				"mDataProp" : "razonSocial"
			},{
				"sTitle" : "Documento a Notificar",
				"bSortable": false,
				"mDataProp" : "tipodocumentoDTO.desTipodocumento"
			},{
				"sTitle" : "Fecha de publicaci\u00f3n",
				"sWidth": "170px",
				"mDataProp" : "fecPublicacionCadena"
			},{
				"sTitle" : "Status",
				"bSortable": false,
				"mDataProp" : "statusDTO.desEstatus"
//			},{
//				"sTitle" : "Archivos",
//				"bSortable": false,
//				"sWidth": "130px",
//				"fnRender": function ( objeto, val ) {
//								var res;
//								res = generaColumnaArchivos(objeto, objeto.aData['cveNotificaciones']);
//								return res;
//							}
			},{
				"sTitle" : "Acci\u00f3n",
				"bSortable": false,
				"fnRender": function ( objeto, val ) {
								var res;
								res = generaBotonAccion(objeto.aData['cveNotificaciones'], objeto);
								return res;
					        }
			}]
		}).columnFilter({
			sPlaceHolder: "head:before",
	        "aoColumns": [
                null,
	        	null,
	        {
	        	"type": "select",
            	values:  obtenerDescripcionFiltroTipoDocumento()
	        },
	        	null,
	        {
	        	"type": "select",
            	values:  obtenerDescripcionFiltroStatus()
	        },
	        	null
            ]
	    });
}

/**
 * Metodo para generar el fnRender de la Autoridad que notifica de la tabla consulta externa
 * @param objeto
 * @returns {String}
 */
function generaAutoridadNotifica(objeto) {
	var autoridadNotifica =	"";
	
	if (objeto.aData['delegacionDTO'].desDeleg == null && objeto.aData['subdelegacionDTO'].desSubdelegacion == null) {
		autoridadNotifica = objeto.aData['ssoVwUsuarioDTO'].desAreaNorma;
	} else if (objeto.aData['delegacionDTO'].desDeleg != null && objeto.aData['subdelegacionDTO'].desSubdelegacion == null) {
		autoridadNotifica = objeto.aData['delegacionDTO'].desDeleg
	} else if (objeto.aData['delegacionDTO'].desDeleg != null && objeto.aData['subdelegacionDTO'].desSubdelegacion != null) {
		autoridadNotifica = objeto.aData['subdelegacionDTO'].desSubdelegacion;
	}
	return autoridadNotifica;
}

///**
// * Metodo para generar el fnRender de los Archivos de la tabla consulta interna
// * @param objeto
// * @returns {String}
// */
//function generaColumnaArchivos(objeto, cveNotificaciones) {
//	var columnaArchivos = '';
//	
//	if (objeto.aData['listDocumentosAdjuntosDTOs'] != null) {
//		for(i = 0; i < objeto.aData['listDocumentosAdjuntosDTOs'].length; i++) {
//			columnaArchivos +=''
//			+'<div class="btn-group">'
//			+'	<button type="button" class="btn btn-primary" onclick="visorArchivoPDF('+objeto.aData['listDocumentosAdjuntosDTOs'][i].cveDoctoAdjunto+', '+cveNotificaciones+')">PDF</button>'
//			+'</div>'
//			+'	<span>&nbsp;&nbsp;&nbsp;'+objeto.aData['listDocumentosAdjuntosDTOs'][i].tipoAdjuntoDTO.desTipoAdjunto+'</span>'
//			if (i < objeto.aData['listDocumentosAdjuntosDTOs'].length-1) {
//			    columnaArchivos += '<br/><br/>';
//		    }
//		}
//	}
//	return columnaArchivos;
//}

/**
 * Metodo para generar el fnRender del boton de Accion de la tabla de consulta interna
 * @param id
 * @param objeto
 * @returns {String}
 */
function generaBotonAccion(cveNotificaciones, objeto){
	
	var opcionEliminar =	"";
//	var opcionActualizar = "";
	var opcionImpresionAcuse = "";
	var opcionDocumentosAdjuntos = "";
	
	if (objeto.aData['statusDTO'].cveStatus == 0 || objeto.aData['statusDTO'].cveStatus == 1) {
		opcionEliminar = ''
			+'		<li>'
			+'			<a onclick="eliminarNotificacion('+cveNotificaciones+')"> '
			+'				ELIMINAR'
			+'			</a>'
			+'		</li>'
	}
	
//	if (objeto.aData['statusDTO'].cveStatus == 0 || objeto.aData['statusDTO'].cveStatus == 1) {
//		opcionActualizar = ''
//			+'		<li>'
//			+'			<a onclick="actualizarNotificacion('+id+')"> '
//			+'				ACTUALIZAR'
//			+'			</a>'
//			+'		</li>'
//	}
	
	if (objeto.aData['statusDTO'].cveStatus == 1 || objeto.aData['statusDTO'].cveStatus == 2 || objeto.aData['statusDTO'].cveStatus == 3) {
		opcionImpresionAcuse = ''
			+'		<li class="dropdown-submenu">'
			+'			<a tabindex="-1">IMPRESI\u00d3N DE ACUSE</a>'
			+'			<ul class="dropdown-menu">'
			+'				<li>'
			+'					<a onclick="visualizaAcuse(\''+objeto.aData['desRefAcuse']+'\')"> '
			+'						REGISTRADA'
			+'					</a>'
			+'				</li>'
			if (objeto.aData['statusDTO'].cveStatus == 2 || objeto.aData['statusDTO'].cveStatus == 3) {
				opcionImpresionAcuse += ''
					+'				<li>'
					+'					<a onclick="visualizaAcuse(\''+objeto.aData['desRefPublicacion']+'\')"> '
					+'						PUBLICADA'
					+'					</a>'
					+'				</li>'
			}
			if (objeto.aData['statusDTO'].cveStatus == 3) {
				opcionImpresionAcuse += ''
					+'				<li>'
					+'					<a onclick="visualizaAcuse(\''+objeto.aData['desRefRetiro']+'\')"> '
					+'						RETIRADA'
					+'					</a>'
					+'				</li>'
			}
			opcionImpresionAcuse += ''
			+'			</ul>'
			+'		</li>'
	}
	if (objeto.aData['listDocumentosAdjuntosDTOs'] != null) {
		opcionDocumentosAdjuntos = ''
			+'				<li class="divider"></li>'
			+'				<li class="dropdown-submenu">'
			+'					<a tabindex="-1">ARCHIVOS</a>'
			+'					<ul class="dropdown-menu">'
								for(i = 0; i < objeto.aData['listDocumentosAdjuntosDTOs'].length; i++) {
									opcionDocumentosAdjuntos +=''
									+'<li>'
									+'	<a onclick="visorArchivoPDF('+objeto.aData['listDocumentosAdjuntosDTOs'][i].cveDoctoAdjunto+', '+cveNotificaciones+')"> '
									+'		'+objeto.aData['listDocumentosAdjuntosDTOs'][i].tipoAdjuntoDTO.desTipoAdjunto+''
									+'	</a>'
									+'</li>'
								}
			opcionDocumentosAdjuntos += ''
			+'					</ul>'
			+'				</li>'
	}
	
	var botonHTML=''
	+'<div class="btn-group">'
	+'	<button type="button" class="btn btn-primary">Acci\u00f3n</button>'
	+'	<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" style="padding: 8px">'
	+'		<span class="caret"></span>'
	+'	</button>'
	+'	<ul class="dropdown-menu" role="menu">'
	+		opcionEliminar
//	+		opcionActualizar
	+		opcionImpresionAcuse
	+		opcionDocumentosAdjuntos
	+'	</ul>'
	+'</div>';
	
	return botonHTML;
}

/**
 * Metodo para obtener la descripcion del catalo de Tipo de documento de una Notificacion
 * @returns {Array}
 */
function obtenerDescripcionFiltroTipoDocumento() {
	var data = obtenerFiltroTipoDocumento();
	var listDescripcion = new Array(data.length);
	for(var i = 0; i < data.length; i++) {
		listDescripcion[i] = data[i].desTipodocumento;
	}
	return listDescripcion;
}

/**
 * Metodo que realiza la consulta ajax sincrona para obtener el catalogo de Tipos de Documentos de una Notificacion
 * @returns data
 */
function obtenerFiltroTipoDocumento() {
	var resultado;
	$.postJSON_Sync(getAppContextParaJS()+"/estrados/obtenerFiltroTipoDocumento.do", null, function(data) {
		resultado = data;
	}).error(function(data) {
		
	}).complete(function(data) {
		
	});
	return resultado;
}

/**
 * Metodo para obtener la descripcion del catalo de Status de una Notificacion
 * @returns {Array}
 */
function obtenerDescripcionFiltroStatus() {
	var data = obtenerFiltroStatus();
	var listDescripcion = new Array(data.length);
	for(var i = 0; i < data.length; i++) {
		listDescripcion[i] = data[i].desEstatus;
	}
	return listDescripcion;
}

/**
 * Metodo que realiza la consulta ajax sincrona para obtener el catalogo de Status de una Notificacion
 * @returns data
 */
function obtenerFiltroStatus() {
	var resultado;
	$.postJSON_Sync(getAppContextParaJS()+"/estrados/obtenerFiltroStatus.do", null, function(data) {
		resultado = data;
	}).error(function(data) {
		
	}).complete(function(data) {
		
	});
	return resultado;
}

/**
 * Metodo que elimina una notificaci�n de la base de datos.
 * @param cveNotificaciones
 * @returns
 */
function eliminarNotificacion(cveNotificaciones) {
	var resultado;
	promtDialogo("\u00bfDesea eliminar el registro seleccionado?", function() {
		$.postJSON_Sync(getAppContextParaJS()+"/estrados/eliminarNotificacion.do", cveNotificaciones, function(data) {
			
		}).error(function(data) {
			
		}).complete(function(data) {
			generaDialogo("La Notificaci\u00f3n fue eliminada exitosamente");
			// Refresaca tabla de consulta interna despues de hacer una eliminaci�n
			tablaInterna.fnDraw();
		});
	});
	return resultado;
}

/**
 * Metodo que genera un mensaje de conclusi�n.
 * @param text
 */
function generaDialogo(text){
	var dialogo=$('#dialogoMensaje').dialog({
        title: "Informativo",
        autoOpen: false,
        width : 400,
        height : 180,
        modal: true,
        resizable: false,
        overlay: {
            opacity: 0.5,
            background: "black"
        },
        buttons: [{
        		text:"Aceptar",
        		click:function(){
        			$(this).dialog("destroy");
        		}
        }]
	});
	dialogo.html(text);
	dialogo.dialog("open");
}

function promtDialogo(texto,callbackOk){
	$('#dialogoMensaje').dialog({
        title: "Eliminaci\u00f3n de Notificaci\u00f3n",
        autoOpen: false,
        width : 400,
        height : 180,
        modal: true,
        resizable: false,
        overlay: {
            opacity: 0.5,
            background: "black"
        },
        buttons: [{ 
        			text: "SI", 
        			click:callbackOk
        		},{ 
	        		text: "NO", 
	        		click:function(){
	        				$(this).dialog("destroy");
	        		}
        			}]
   		});
	$('#dialogoMensaje').html(texto);
	$('#dialogoMensaje').dialog("open");
}

function actualizarNotificacion(id) {
}