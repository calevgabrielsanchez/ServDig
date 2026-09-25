<!-- JSP Contenido del Portlet de Solicitudes. -->
<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum" %>

<c:set var="edoSolAtendida" value="<%=EstadoSolicitudEnum.ATENDIDA.getCodigo() %>" scope="page" />

<style>
	input.selectFiltroOpcion {
		margin-top: 5px;
	}
	
	.table th {
	    font-size: 10px;
	}
	
	.portlet .contenido {
		padding: 0px;
	}
</style>

<c:choose>
	<c:when test="${not empty filtroSolicitud.idDelegacion && not empty BLOCK_DELEG && not empty filtroSolicitud.idSubdelegacion && not empty BLOCK_SUBDELEG }">
		<c:set var="delegacionReadOnly" value="true" />
		<c:set var="subdelegacionReadOnly" value="true" />
	</c:when>
	<c:when test="${not empty filtroSolicitud.idDelegacion && not empty BLOCK_DELEG}">
		<c:set var="delegacionReadOnly" value="true" />
		<c:set var="subdelegacionReadOnly" value="false" />
	</c:when>
	<c:otherwise>
		<c:set var="delegacionReadOnly" value="false" />
		<c:set var="subdelegacionReadOnly" value="false" />
	</c:otherwise>
</c:choose>

<c:if test="${not empty FROM_REGRESAR }">
	<input type="hidden" id="fromRegresar" value="${FROM_REGRESAR}" />
</c:if>
<c:if test="${not empty FILTROS_SESSION }">
	<input type="hidden" id="isFiltrosSession" value="true" />
</c:if>

<script id="initPortlet">
		
	$('select#selectFiltro').change(function(){
		var idInput = $('select#selectFiltro option:selected').attr('ref-input');
		$('input#' + idInput).show();
		$('input.selectFiltroOpcion:not(input#' + idInput + ')').hide().val('');
	});
	
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
		mDataProp : "origenSolicitud.descripcion",
		sTitle : "Origen"
	},{
		sTitle : "Tr\u00E1mites",
		fnRender : renderTramites,
		sWidth: "300px"
	}, {
		sTitle : "CURP / RFC / NRP",
		fnRender : renderCurpRfcNrp
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
		sTitle : "Delegaci\u00F3n / Subdelegaci\u00F3n",
		fnRender : renderDelegacionSubdelegacion
	}, {
		mDataProp : "estadoSolicitud.descripcion",
		sTitle : "Estado"
	} ];

	var gridSolicitud = $('#tblSolicitudesResumen').dataTable(
			{
				"sPaginationType" : "bootstrap-full",
				"oLanguage": {
					"sZeroRecords": "<center><strong style=\"font-size: small;\">Sin información que mostrar</strong></center>"
				},
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
					$('td:eq(0)', nRow).html('<a onclick="ejecutarConsultaSolicitudPorFolioGrid(&quot;' + aData.noFolioSolicitud + '&quot;)" ' +
			            	' class="link">' +aData.noFolioSolicitud + '</a>');
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
				
		if ($('select#idSubdelegacion').length > 0) {
			$('select#idSubdelegacion option[value!="-1"]').remove();
		} else if ($('select#idSubdelegacionAux:enabled').length > 0) {
			$('select#idSubdelegacionAux:enabled option[value!="-1"]').remove();
		}
		
		$('div#divMsgSinResultados').hide();
		$('input.selectFiltroOpcion').hide();
		<c:if test="${delegacionReadOnly eq true && subdelegacionReadOnly eq false}">
		$('#idSubdelegacion').val('');
		</c:if>
		$.post('/gestionSolicitud-visor-web/portlet/solicitudes/limpiar-sesion');
	});

	<c:if test="${delegacionReadOnly}">
	var comboDeleg = $('select#idDelegacionAux');
	comboDeleg.val($('#idDelegacion').val());
	comboDeleg.attr('disabled', 'disabled');
	</c:if>
	
	<c:if test="${subdelegacionReadOnly}">
	var comboSubdeleg = $('select#idSubdelegacionAux');
	comboSubdeleg.val($('#idSubdelegacion').val());
	comboSubdeleg.attr('disabled', 'disabled');
	</c:if>
	
	<c:if test="${delegacionReadOnly eq true && subdelegacionReadOnly eq false}">
	$('select#idSubdelegacionAux').change(function(){
		$('#idSubdelegacion').val($(this).val());
	});
	</c:if>

	/*
	 * Funcion que convierte en MAYUSCULAS el valor de 
	 * cualquier campo de texto al perder el foco 
	 */
	$('input[type="text"]').blur(function() {
		$(this).val($(this).val().toUpperCase());
	});
	
	var dialogMensajeError = $( "#dgError" ).dialog({
		resizable: false,
		modal: true,
		autoOpen:false,
		buttons: {
			"Aceptar": function() {
				$( this ).dialog( "close" );
			}
		}
	});
</script>

<style>
	.error-custom {
		color: #B94A48 !important;
	}
	
	.portlet .descripcion {
	    padding: 0;
	}
</style>

<div class="encabezado"></div>
<div class="cuerpo">
	
	<div class="titulo" style="display: none;"></div>
		
	<div class="descripcion">
		<div class="alert alert-info alert-block">
			<spring:message code="label.portlet.descripcion.solicitudes" />
		</div>
	</div>
 	
	<div class="contenido">
		<div class="alert alert-danger" id="divMsgErrores"
			style="display: none;">
			<span id="msgError"></span>
		</div>

		<div>
			<form:form modelAttribute="filtroSolicitud"
				id="formBusquedaSolictiudes"
				action="/portlet/solicitudes/consultarSolicitudes"
				cssClass="form-horizontal">

				<form:hidden path="idSolicitud" />
				
				<fieldset class="esquinas-redondeadas" style="margin-bottom: 10px;">
					<legend>Por Folio de Solicitud</legend>
					
					<div class="form-group">
						<label for="folio" class="col-sm-2 control-label">Folio Solicitud</label>
						<div class="col-sm-5">
							<form:input path="folio" maxlength="50"
								cssClass="numericoSinPunto form-control" />
						</div>
						<div class="col-sm-3">
							<button type="button" class="btn btn-primary"
								id="btnBuscarSolicitudPorFolio">BUSCAR</button>
						</div>
					</div>
				</fieldset>
				<fieldset class="esquinas-redondeadas" style="margin-bottom: 35px;">
					<legend>Por Filtros</legend>
					<div>
						<div class="form-group">
							<label for="tipoSolicitud" class="col-sm-2 control-label">
								Filtro </label>
							<div class="col-sm-4">
								<select id="selectFiltro" class="form-control">
									<option value='-1'>--Por favor seleccione--</option>
									<option value="1" ref-input="curp">CURP</option>
									<option value="2" ref-input="rfc">RFC</option>
									<option value="3" ref-input="nss">NSS</option>
									<option value="4" ref-input="rp">NRP</option>
								</select>
								<form:input path="rfc"
									cssClass="selectFiltroOpcion form-control"
									maxlength="13" cssStyle="display:none;" />
								<form:input path="rp"
									cssClass="selectFiltroOpcion form-control"
									maxlength="11" cssStyle="display:none;" />
								<form:input path="curp"
									cssClass="selectFiltroOpcion form-control"
									maxlength="18" cssStyle="display:none;" />
								<form:input path="nss"
									cssClass="numericoSinPunto selectFiltroOpcion form-control"
									maxlength="18" cssStyle="display:none;" />
							</div>
							<label for="idOrigenSolicitud" class="col-sm-2 control-label">
								Origen Solicitud </label>
							<div class="col-sm-4">
								<combo:creaCombo idHtml="idOrigenSolicitud"
									idHtmlContenedor="formBusquedaSolictiudes"
									entidad="mx.gob.imss.ctirss.delta.persistence.DicOrigenSolicitud"
									idHtmlValor="${filtroSolicitud.idOrigenSolicitud }"
									mostrarSoloActivos="true" cssClassname="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="tipoSolicitud" class="col-sm-2 control-label">
								Tr&aacute;mite Asociado </label>
							<div class="col-sm-4">
								<form:select path="tramiteId" cssClass="form-control">
									<form:option value="-1">--Por favor seleccione--</form:option>
									<c:forEach items="${lstTipoTramite}" var="tramite">
										<form:option value="${tramite.idTipoTramite}">${tramite.descripcion}</form:option>
									</c:forEach>
								</form:select>
							</div>
							<label for="estadoSolicitud" class="col-sm-2 control-label">
								Estado Solicitud </label>
							<div class="col-sm-4">
								<combo:creaCombo idHtml="idEstadoSolicitud"
									idHtmlContenedor="formBusquedaSolictiudes"
									entidad="mx.gob.imss.ctirss.delta.persistence.DicEstadoSolicitud"
									idHtmlValor="${filtroSolicitud.idEstadoSolicitud }"
									mostrarSoloActivos="true" cssClassname="form-control" />
							</div>
						</div>
						<div class="row m-t-lg m-b-md">
							<div class="col-sm-6 col-xs-12">
								<label class="text-center" style="display: block;"> FECHA PRESENTACI&Oacute;N </label>
								<div class="form-group">
									<label for="fechaInicioPresentacion" class="col-sm-4 control-label"> Fecha Inicio </label>
									<div class="col-sm-8">
										<form:input path="fechaInicioPresentacion" msgErrorElemntName="Fecha Inicio Presentaci&oacute;n"
											cssStyle="width: auto; margin-right: 10px;" size="10" cssClass="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label for="fechaFinPresentacion" class="col-sm-4 control-label"> Fecha Fin </label>
									<div class="col-sm-8">
										<form:input path="fechaFinPresentacion" msgErrorElemntName="Fecha Fin Presentaci&oacute;n"
											cssStyle="width: auto; margin-right: 10px;" size="10" cssClass="form-control" />
									</div>
								</div>
							</div>
							<div class="col-sm-6 col-xs-12">
								<label class="text-center" style="display: block;"> FECHA CONCLUSI&Oacute;N </label>
								<div class="form-group">
									<label for="fechaInicioConclusion" class="col-sm-4 control-label"> Fecha Inicio </label>
									<div class="col-sm-8">
										<form:input path="fechaInicioConclusion" msgErrorElemntName="Fecha Inicio Conclusi&oacute;n"
											cssStyle="width: auto; margin-right: 10px;" size="10" cssClass="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label for="fechaFinConclusion" class="col-sm-4 control-label"> Fecha Fin </label>
									<div class="col-sm-8">
										<form:input path="fechaFinConclusion" msgErrorElemntName="Fecha Fin Conclusi&oacute;n"
											cssStyle="width: auto; margin-right: 10px;" size="10" cssClass="form-control" />
									</div>
								</div>
							</div>
						</div>

						
						<div class="form-group">
							<label for="idDelegacion" class="col-sm-2 control-label">
								<spring:message code="label.municipio" />
							</label>
							<div class="col-sm-4">
								<c:choose>
									<c:when test="${delegacionReadOnly}">
										<select id="idDelegacionAux" name="idDelegacionAux"
											class="form-control">
											<option value=''>--Por favor seleccione--</option>
											<c:forEach items="${delegacionesAux}" var="delegacionAux">
												<option value='${delegacionAux.id }'>
													${delegacionAux.descripcion}</option>
											</c:forEach>
										</select>
										<form:hidden path="idDelegacion" id="idDelegacion" />
									</c:when>
									<c:otherwise>
										<combo:creaCombo idHtml="idDelegacion"
											idHtmlContenedor="formBusquedaSolictiudes"
											entidad="mx.gob.imss.ctirss.delta.persistence.DicDelegacion"
											idHtmlValor="${filtroSolicitud.idDelegacion }"
											mostrarSoloActivos="true"
											cssClassname="form-control" />
									</c:otherwise>
								</c:choose>
							</div>
							<label for="idSubdelegacion" class="col-sm-2 control-label">
								<spring:message code="label.subdelegacion" />
							</label>
							<div class="col-sm-4">
								<c:choose>
									<c:when test="${delegacionReadOnly}">
										<select id="idSubdelegacionAux" name="idSubdelegacionAux"
											class="form-control">
											<option value=''>--Por favor seleccione--</option>
											<c:forEach items="${subdelegacionesAux}"
												var="subdelegacionAux">
												<option value='${subdelegacionAux.id }'>
													${subdelegacionAux.descripcion}</option>
											</c:forEach>
										</select>
										<form:hidden path="idSubdelegacion" id="idSubdelegacion" />
									</c:when>
									<c:otherwise>
										<combo:creaCombo
											entidad="mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion"
											idHtml="idSubdelegacion"
											entidadPadre="dicDelegacion.cveIdDelegacion"
											idHtmlPadre="idDelegacion"
											idHtmlContenedor="formBusquedaSolictiudes"
											idHtmlValor="${filtroSolicitud.idSubdelegacion }"
											mostrarSoloActivos="true"
											cssClassname="form-control" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
					<div class="row m-t-xl">
						<div class="col-sm-12 text-right">
							<button type="button" class="btn btn-default"
								id="btnLimpiarFormulario">LIMPIAR</button>
							<button type="button" class="btn btn-primary"
								id="btnBuscarSolicitudes">BUSCAR</button>
							
						</div>
					</div>
				</fieldset>
			</form:form>
		</div>
		
		<div class="alert alert-danger" id="divMsgSinResultados"
			style="display: none;">
			La b&uacute;squeda realizada no obtuvo resultados, favor de refinar su b&uacute;squeda.
		</div>
		
		<div class="alert alert-success" id="divNumResultados"
			style="display: none;">
			La b&uacute;squeda realizada obtuvo <strong><span id="numRegistros" ></span></strong> resultados.
		</div>
		
		<div id="tblSolicitudesWrapper" class="table-responsive" style="border: none;">
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

<!-- 
<div class="estado"></div>

<div class="pie">
	<div class="opciones"></div>
</div>
 -->

<!-- Forma auxiliar para consultar el detalle de una solicitud desde el grid -->
<form:form modelAttribute="solicitud" id="formDetalleSolicitud"
	action="/gestionSolicitud-visor-web/portlet/solicitudes/detalle"
	method="post">
	<form:hidden path="noFolioSolicitud" id="noFolioSolicitudDetalle" />
	<input type="hidden" name="subdelegacion.id" id="subdelegacion.id"
		value="${filtroSolicitud.idSubdelegacion}" />
</form:form>

<!-- Forma auxiliar para consultar el detalle de una solicitud desde el grid -->
<form:form modelAttribute="solicitud" id="formDetalleSolicitudGrid"
	action="/gestionSolicitud-visor-web/portlet/solicitudes/detalle"
	method="post">
	<form:hidden path="noFolioSolicitud" id="noFolioSolicitudDetalleGrid" />
</form:form>

<div id="dgError" title="Error inesperado">
	<p style="margin-bottom: 0px;">
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin-right: 7px; margin-top: 3px;"> </span>
			Ha ocurrido un error inesperado.
	</p>
</div>

<script type="text/javascript">
	/*
	 * Se checa si se viene del regresar, si es así se debe ejecutar 
	 * la consulta para mostrar los datos en el grid. Se pone esta parte de código
	 * al final, para que sea lo úlitmo en ejecutarse y de chance a que se carguen los
	 * combos y se tomen en cuenta para volver a realizar la búsqueda
	 */
	var isFromRegresar = $('input#fromRegresar').val();
	var isFiltros = $('input#isFiltrosSession').val();
	
	
	if ((isFromRegresar == 'true' || isFromRegresar == true)
			&& (isFiltros == 'true' || isFiltros == true)) {
		
		<c:if test="${delegacionReadOnly eq true && subdelegacionReadOnly eq false}">
			if ($('#idSubdelegacion').val() != '') {
				$('select#idSubdelegacionAux').val($('#idSubdelegacion').val());
			}
		</c:if>
		
		gridSolicitud.fnDraw();
		
		var selectValue = null;
		
		if ($('input#curp').val() != '') {
			selectValue = $('select#selectFiltro option[ref-input=curp]').val();
		} else if ($('input#rfc').val() != '') {
			selectValue = $('select#selectFiltro option[ref-input=rfc]').val();
		} else if ($('input#rp').val() != '') {
			selectValue = $('select#selectFiltro option[ref-input=rp]').val();
		} else if ($('input#nss').val() != '') {
			selectValue = $('select#selectFiltro option[ref-input=nss]').val();
		}
				
		if (selectValue != null) {
			$('select#selectFiltro').val(selectValue);
			$('select#selectFiltro').trigger('change');
		}
	}
</script>