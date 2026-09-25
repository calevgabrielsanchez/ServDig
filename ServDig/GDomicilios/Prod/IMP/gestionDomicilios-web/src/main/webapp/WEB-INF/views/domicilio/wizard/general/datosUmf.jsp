<%@ include file="../../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum"%>

<script type="text/javascript" src="/portalDerechohabiente-ciudadano/static/resources/js/delta/common/combosUmfMedicoConsultorio.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/domicilios/wizard/general/funcionesComunes.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/domicilios/wizard/general/datosUmf.js" htmlEscape="true" />"></script>

<script>
	var idSolicitud = "${idSolicitud}";
	var tipoSolicitud = <%=TipoSolicitudEnum.REGISTRO_DE_DERECHOHABIENTES.getValor()%>;

	var codigoTipoSolicitud = "${codigoTipoSolicitud}";
	var arrayCodigoTipoTramite = ${codigoTipoTramite};
	var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';

	var datosEntradaFirma = {
		fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
		nombreCompleto : '${datosFirmaElectronica.nombreCompleto}',
		registroPatronal : '${datosFirmaElectronica.registroPatronal}',
		rfc : '${datosFirmaElectronica.rfc}',
		curp : '${datosFirmaElectronica.curp}'
	};
</script>

<style>
.contenedor .pie .opciones {
	float: left;
	width: 80%;
}

select{
	width: 300px;
}

.contenedor .pie .controles {
	float: right;
	width: 20%;
}

label {
	display: inline;
}

.table_form table {
	margin: 15px auto;
}

.table_form table tr td {
	padding: 5px 10px;
}

textarea {
	height: 100%;
}

input,textarea,.uneditable-input {
	width: auto;
	text-transform: uppercase;
	width: 250px;
}
</style>

<div class="contenedor col-sm-12">
	<input type="hidden" value="${requiereDocs ? 1 : 0}" id="requiereDocs"/>
	<input type="hidden" value="${registro.domicilio.codigoPostal.codigoPostal}" id="cpBusquedaUmf"/>
	<input type="hidden" value="${registro.medicoEnTurno.unidadMedicaFamiliar.idUMF}" id="umfSeleccionada"/>
	<input type="hidden" value="${registro.medicoEnTurno.turno.idTurno}" id="turnoSeleccionado"/>
	<input type="hidden" value="${registro.medicoEnTurno.consultorio.idConsultorio}" id="consultorioSeleccionado"/>
	<input type="hidden" value="${solicitudRegistro.solicitudId}" id="idSolicitud"/>
	<input type="hidden" value="${registroConyuge ? 1 : 0}" id="hdnRegistroConyuge"/>
	<input type="hidden" value="${mismoSexo ? 1 : 0}" id="hdnMismoSexo"/>
	<div class="contenido row">
		<div class="col-sm-12">
		<c:if test="${not empty error}">
			<div class="alert alert-error">
				<button type="button" class="close" data-dismiss="alert">×</button>
				<strong>Error: </strong>${error}
			</div>
		</c:if>

		<c:if test="${not empty folioSolicitud}">
			<input type="hidden" id="hdnFolioSolicitud" value="${folioSolicitud}"/>
			<input type="hidden" id="contenidoFirmar" value="${contenidoFirmar}" />
			<div class="alert alert-success">
				<c:choose>
					<c:when test="${!isRetomar}">
						<spring:message code="label.solicitud.iniciada" arguments="${folioSolicitud}"/>
					</c:when>
					<c:otherwise>
						<spring:message code="label.solicitud.retomando" arguments="${folioSolicitud}"/>
					</c:otherwise>
				</c:choose>
			</div>
			
			<div class="titulo" align="center">
				<span> PASO 3/3 Selecci&oacute;n de UMF, Turno y Consultorio</span>
			</div>
		</c:if>

		<c:if test="${empty error}">
		<form:form id="formRegistro" method="post" modelAttribute="registro" role="form">
			<div id="seccionUmfRegistro" style="width: 830px" class="table_form">
				<form:hidden path="paso"/>
				<form:hidden path="parentesco.idParentesco"/>
				<form:hidden path="tipoTramite.idTipoTramite"/>
				<form:hidden path="fisica.curp"/>
				<form:hidden path="tramiteId"/>
				<form:hidden path="indSeleccionMedico"/>
				<form:hidden path="fechaCambioMedico"/>
				
				<div class="separadorseccion">
					<span>
						Datos de adscripci&oacute;n del integrante del grupo familiar
					</span>
				</div>
				<div id="mensajeIntegranteUmf" class="ui-widget" align="center" style="display:none;">
						<div class="ui-state-highlight ui-corner-all" style="margin-top: 12px; padding: 0 .5em;">
							<p>
								<i class="glyphicon glyphicon-info-sign"></i></span>
								<span id="mensajeUmfIntegrante"></span>
							</p>
						</div>
					</div>
				<table width="100%" class="table table-striped table-bordered" >
					<tr>
						<td>
							<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.idUMF">
								<span class="required">*</span>&nbsp;UMF:
							</label>
						</td>
						<td colspan="3">
							<form:hidden path="medicoEnTurno.idMedicoContultorioTurno" />	
							<select id="medicoEnTurno.unidadMedicaFamiliar.idUMF" name="medicoEnTurno.unidadMedicaFamiliar.idUMF" class="form-control">
							  	<option value="-1">--Por favor seleccione--</option>
							 </select>						
							 <span id="medicoEnTurno.unidadMedicaFamiliar.idUMFError" class="error hiddenElement"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion">
								Delegaci&oacute;n :
							</label>
						</td>
						<td>
							<form:hidden path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.id"/>
							<form:input path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion" cssClass="form-control"/>
						</td>
						<td>
							<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion">
								Subdelegaci&oacute;n :
							</label>
						</td>
						<td>
							<form:hidden path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.id"/>
							<form:input path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion" cssClass="form-control"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label" for="medicoEnTurno.turno.idTurno">
							<span class="required">*</span>&nbsp;Turno :
							</label>
						</td>
						<td>
							<select id="medicoEnTurno.turno.idTurno" name="medicoEnTurno.turno.idTurno" class="form-control">
								 <option value="-1">--Por favor seleccione--</option>
							</select>
							
							<span id="medicoEnTurno.turno.idTurnoError" class="error hiddenElement"></span>
						</td>
						<td>
							<label class="control-label" for="medicoEnTurno.consultorio.idConsultorio">
								<span class="required">*</span>&nbsp;Consultorio :
							</label>
						</td>
						<td>
							<select id="medicoEnTurno.consultorio.idConsultorio" name="medicoEnTurno.consultorio.idConsultorio" class="form-control">
								 <option value="-1">--Por favor seleccione--</option>
							</select>
							
							<span id="medicoEnTurno.consultorio.idConsultorioError" class="error hiddenElement"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label" for="medicoEnTurno.medicoFamiliar.idMedicoFamiliar">
								&nbsp;M&eacute;dico :
							</label>
						</td>
						<td colspan="3">
							<form:hidden path="medicoEnTurno.medicoFamiliar.idMedicoFamiliar"/>
							<form:input path="medicoEnTurno.medicoFamiliar.nombre" cssClass="form-control"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label" for="medicoEnTurno.medicoFamiliar.noMatricula">
								Matr&iacute;cula :
							</label>
						</td>
						<td><form:input path="medicoEnTurno.medicoFamiliar.noMatricula" cssClass="form-control"/></td>
						<td>
							<label class="control-label" for="medicoEnTurno.medicoEspecialidad.descripcion">
								Especialidad :
							</label>
						</td>
						<td>
							<form:hidden path="medicoEnTurno.medicoEspecialidad.idMedicoEspacialidad"/>
							<form:input path="medicoEnTurno.medicoEspecialidad.descripcion" cssClass="form-control"/>
						</td>
					</tr>
				</table>
				<br>
				
				<c:if test="${requiereDocs}">
					<div class="separadorseccion">
						<span>
							Documentos probatorios
						</span>
					</div>
					<div class="alert alert-success">
							Este tr&aacute;mite requiere la captura de documentos probatorios, de clic en el bot&oacute;n "Captura de documentos Probatorios" para proceder a la misma, no podr&aacute; 
							finalizar el tr&aacute;mite hasta completarla.<br><br>
							<a id="capturarDocumentos" class="btn btn-default"><i class="glyphicon glyphicon-file"></i> Captura de documentos probatorios</a>
					</div>
				</c:if>
			</div>
		</form:form>
		</c:if>
		
	
 	<p class="alert alert-success" align="center">
 		<b>Para concluir la solicitud, favor de dar clic en el icono de acciones y seleccionar la opci&oacute;n - Finalizar Tr&aacute;mite -.</b>
	</p>
	
	</div>
	</div>
	<div class="pie row">
		<div class="opciones col-sm-6">
			<c:if test="${empty error}">
				<div class="btn-group">
					<a href="#" class="btn btn-primary"><spring:message code="label.menus.opciones" /></a> <a href="#"
						data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a id="finalizarTramite"><i class="glyphicon glyphicon-ok"></i> Finalizar Tr&aacute;mite</a></li>
						<li><a id="guardarTramite"><i class="glyphicon glyphicon-download-alt"></i> Guardar Tr&aacute;mite</a></li>
						<li><a id="guardarCerrarTramite"><i class="glyphicon glyphicon-download-alt"></i> Guardar y Cerrar Tr&aacute;mite</a></li>
						<li><a id="cancelarTramite"><i class="glyphicon glyphicon-trash"></i> Cancelar Tr&aacute;mite</a></li>
					</ul>
				</div>
				
				<button id="regresar" class="btn btn-default pull-right" type="button">
				  <span class="glyphicon glyphicon-step-backward"></span>
				  Anterior
				</button>
			</c:if>
		</div>
		</div>
</div>

<!-- Forma para invocar al servicio del ModificaciÃ³n Manual de Datos -->
<form id="comprobacionIcaForm" method="post">
</form>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			&iquest; Deseas cancelar la solicitud pendiente con folio: <strong>${folioSolicitud}</strong>?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogo"></label>
	</p>
</div>

<div id="dialogoConfirmacion">
	<p><span id="textoConfirmacion"></span></p>
</div>

<div id="dialog-error" title="Error">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> <label
			id="mensajeError"></label>
	</p>
</div>