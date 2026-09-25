<!-- JSP Contenido del Portlet de Solicitudes. -->
<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/portlet/solicitudesPortlet.js" htmlEscape="true" />"></script>

<script id="initPortlet">
	var columnasSolicitud = [ {
		mDataProp : "solicitudId",
		bVisible : false
	}, {
		mDataProp : "tipoSolicitud.idTipoSolicitud",
		bVisible : false
	}, {
		mDataProp : "noFolioSolicitud",
		sTitle : "Folio"
	}, {
		mDataProp : "tipoSolicitud.descripcion",
		sTitle : "Tipo"
	}, {
		sTitle : "RFC",
		fnRender : renderRFC
	}, {
		sTitle : "Reg Patronal",
		fnRender : renderOwner
	}, {
		sTitle : "Fecha de Presentaci\u00F3n",
		mDataProp : "fechaPresentacionParse"
	}, {
		sTitle : "Fecha de Conclusi\u00F3n",
		mDataProp : "fechaConclusionParse"
	}, {
		sTitle : "Fecha Solicitud",
		mDataProp : "fechaSolicitudParse",
		bVisible : false
	}, {
		sTitle : "Tramites",
		fnRender : renderTramites,
		bVisible : false
	}, {
		sTitle : "Delegaci\u00F3n",
		fnRender : renderDelegacion
	}, {
		sTitle : "Subdelegaci\u00F3n",
		fnRender : renderSubdelegacion
	}, {
		mDataProp : "estadoSolicitud.descripcion",
		sTitle : "Estado"
	} ];

	var gridSolicitud = $('#tblSolicitudesResumen').dataTable(
			{
				"sPaginationType" : "bootstrap",
				"bServerSide" : true,
				"bLengthChange" : false,
				"bFilter" : false,
				"bProcessing" : false,
				"bSort" : false,
				"aoColumns" : columnasSolicitud,
				"sAjaxSource" : context_path
						+ "/portlet/solicitudes/consultarSolicitudes",
				"fnServerData" : cargarGridSolicitudesConsultadas,
				"iDeferLoading" : 0,
				"fnRowCallback": function( nRow, aData, iDisplayIndex ) {
					var obj = JSON.stringify(aData);
		            $('td:eq(0)', nRow).html('<a onclick="ejecutarConsultaSolicitudPorFolio(&quot;' + aData.noFolioSolicitud + '&quot;)" ' +
		            	' class="linkFolio">' +aData.noFolioSolicitud + '</a>');
		            return nRow;
		        }
			});

	crearSolicitudDatePickers();

	$('#btnBuscarSolicitudes').click(function() {
		ejecutarConsultaSolicitudes();
	});
	
	$('#btnBuscarSolicitudPorFolio').click(function() {
		var folio = $('#folio').val();
		
		ejecutarConsultaSolicitudPorFolio(folio);
	});
	
	$('#btnLimpiarFormulario').click(function() {
		ocultarErrorCaptura();
		limpiarFormulario('#formBusquedaSolictiudes');
	});
	
</script>

<style>
.filtros-busqueda {
	font-size: x-small;
	margin-bottom: 35px;
	color: black;
}

.filtros-busqueda .control-label {
	cursor: default;
	display: inline-block;
	margin-right: 10px;
}

.filtros-busqueda .row {
	margin-left: inherit;
	width: 100%;
}

.filtros-busqueda .filtros .etiqueta{
	width: 15%;
	text-align: right;
}

.filtros-busqueda .filtros .filtro{
	width: 35%;
}

.linkFolio{
	cursor: pointer;
	color: #0088CC;
}

.error-custom{
	color: #B94A48 !important;
}
</style>

<div class="encabezado"></div>
<div class="cuerpo">

	<div class="titulo">
		<span><spring:message code="label.portlet.titulo.solicitudes" />
		</span>

		<div class="controles">
			<a class="btn btn-sm widget-tool widget-resize"><i
				class="icon-resize-small"></i></a> <a
				class="btn btn-sm widget-tool widget-move"><i
				class="icon-move handle"></i></a>
		</div>

	</div>

	<div class="descripcion">
		<p>
			<spring:message code="label.portlet.descripcion.solicitudes" />
		</p>
	</div>

	<div class="contenido">
	
		<div class="alert alert-danger" id="divMsgErrores" style="display: none;">
			<span id="msgError"></span>
		</div>
		
		<div class="filtros-busqueda">
			<form:form modelAttribute="filtroSolicitud"
				id="formBusquedaSolictiudes"
				action="/portlet/solicitudes/consultarSolicitudes">

				<form:hidden path="idDelegacion" />
				<form:hidden path="idSubdelegacion" />
				<form:hidden path="idSolicitud" />

				<div class="row filtros" 
					style="border-bottom: 1px solid rgba(0, 0, 0, 0.2); margin-bottom: 10px;">
					<div class="cell etiqueta">
						<label class="control-label" for="folio">Folio
							Solicitud</label>
					</div>
					<div class="cell filtro">
						<form:input path="folio" />
					</div>
					<div class="cell ">
						<button type="button" class="btn btn-primary"
								id="btnBuscarSolicitudPorFolio">BUSCAR</button>
					</div>
				</div>

				<div class="row filtros">
					<div class="cell etiqueta">
						<label class="control-label" for="tipoSolicitud">RFC</label>
					</div>
					<div class="cell filtro">
						<form:input path="rfc" />
					</div>

					<div class="cell etiqueta">
						<label class="control-label" for="tipoSolicitud">Registro
							Patronal</label>
					</div>
					<div class="cell filtro">
						<form:input path="rp" />
					</div>
				</div>
				
				<div class="row filtros">
					<div class="cell etiqueta">
						<label class="control-label" for="tipoSolicitud">Tr&aacute;mite Asociado</label>
					</div>
					<div class="cell filtro">
						<combo:creaCombo idHtml="tipoTramiteInput"
							idHtmlContenedor="formBusquedaSolictiudes"
							entidad="mx.gob.imss.ctirss.delta.persistence.DicTipoTramite" 
							mostrarSoloActivos = "false" />
						<form:hidden path="tramiteId" id="tipoTramiteInputHidden" />
					</div>

					<div class="cell etiqueta">
						<label class="control-label" for="estadoSolicitud">Estado 
							Solicitud</label>
					</div>
					<div class="cell filtro">
						<combo:creaCombo idHtml="estadoSolicitudInput"
							idHtmlContenedor="formBusquedaSolictiudes"
							entidad="mx.gob.imss.ctirss.delta.persistence.DicEstadoSolicitud" 
							mostrarSoloActivos = "false" />
						<form:hidden path="idEstadoSolicitud"
							id="estadoSolicitudInputHidden" />
					</div>
				</div>
				
				<div class="row filtros">
					<div class="cell" style="width: 50%; text-align: center;">
						<label class="control-label" for="fechaSolicitud">FECHA
							PRESENTACI&Oacute;N</label>
					</div>
					<div class="cell" style="width: 50%; text-align: center;">
						<label class="control-label" for="fechaSolicitud">FECHA
							CONCLUSI&Oacute;N</label>
					</div>
				</div>
				
				<div class="row filtros">
					<div class="cell etiqueta">
						<label class="control-label" for="fechaInicioPresentacion">Fecha
							Inicio</label>
					</div>
					<div class="cell filtro">
						<form:input path="fechaInicioPresentacion"
							cssStyle="width: auto; margin-right: 10px;" size="10" />
					</div>
					<div class="cell etiqueta">
						<label class="control-label" for="fechaInicioConclusion">Fecha
							Inicio</label>
					</div>
					<div class="cell filtro">
						<form:input path="fechaInicioConclusion"
							cssStyle="width: auto; margin-right: 10px;" size="10" />
					</div>
				</div>
				
				<div class="row filtros">
					<div class="cell etiqueta">
						<label class="control-label" for="fechaFinPresentacion">Fecha
							Fin</label>
					</div>
					<div class="cell filtro">
						<form:input path="fechaFinPresentacion"
							cssStyle="width: auto; margin-right: 10px;" size="10" />
					</div>
					<div class="cell etiqueta">
						<label class="control-label" for="fechaFinConclusion">Fecha
							Fin</label>
					</div>
					<div class="cell filtro">
						<form:input path="fechaFinConclusion"
							cssStyle="width: auto; margin-right: 10px;" size="10" />
					</div>
				</div>

				<div style="margin-top: 15px;">
					<button type="button" class="btn btn-primary"
						id="btnBuscarSolicitudes">BUSCAR</button>
					<button type="button" class="btn btn-secondary"
						id="btnLimpiarFormulario">LIMPIAR</button>
				</div>
			</form:form>
		</div>

		<div id="tblSolicitudesWrapper" style="width: 100%; margin: 0 auto;">
			<table id="tblSolicitudesResumen" style="width: 100%;"
				class="table table-striped table-bordered" cellpadding="0"
				cellspacing="0" border="0">
				<thead>
				</thead>
				<tbody>

				</tbody>
			</table>
		</div>

		<br> <br>
	</div>
</div>

<div class="estado"></div>

<div class="pie">
	<div class="opciones"></div>
</div>

<!-- Forma auxiliar para consultar el detalle de una solicitud -->
<form:form modelAttribute="solicitud" id="formDetalleSolicitud"
	action="/gestionCobranza-visor-web/portlet/solicitudes/detalle" method="post">
	<form:hidden path="noFolioSolicitud" id="noFolioSolicitudDetalle" />
</form:form>