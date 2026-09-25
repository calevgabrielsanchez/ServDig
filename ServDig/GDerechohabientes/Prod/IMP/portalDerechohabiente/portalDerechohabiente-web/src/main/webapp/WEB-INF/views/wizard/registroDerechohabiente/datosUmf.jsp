<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/common/combosUmfMedicoConsultorio.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/registroDerechohabiente/funcionesComunes.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/registroDerechohabiente/datosUmf.js" htmlEscape="true" />"></script>

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
	<input type="hidden" value="${asignacionNssSession.idAsignacionNSS}" id="idAsignacionNSSSessio"/>
	<input type="hidden" value="${registro.domicilio.codigoPostal.codigoPostal}" id="cpBusquedaUmf"/>
	<input type="hidden" value="${registro.medicoEnTurno.unidadMedicaFamiliar.idUMF}" id="umfSeleccionada"/>
	<input type="hidden" value="${registro.medicoEnTurno.turno.idTurno}" id="turnoSeleccionado"/>
	<input type="hidden" value="${registro.medicoEnTurno.consultorio.idConsultorio}" id="consultorioSeleccionado"/>
	<input type="hidden" value="${solicitudRegistro.solicitudId}" id="idSolicitud"/>
	<input type="hidden" value="${registroConyuge ? 1 : 0}" id="hdnRegistroConyuge"/>
	<input type="hidden" value="${mismoSexo ? 1 : 0}" id="hdnMismoSexo"/>
	<input type="hidden" value="${validacionesDom.setUmfAsegurado?1:0}" id="mismaUmf">
	<input type="hidden" value="${contenidoFirmar}" id="contenidoFirmar"/>
	<input type="hidden" value="${registro.tipoTramite.homoclave}" id="homoclaveTramite"/>
	<input type="hidden" value="${registro.indHijosProcreados}" id="indHijosProcreados"/>
	
	<div class="contenido row">
	<div class="col-sm-12">

		<jsp:include page="encabezadoRegistro.jsp">
			<jsp:param name="paso" value="3" />
		</jsp:include>

		<c:if test="${empty error}">
		<div class="alert alert-info">
	 		<spring:message code="label.wizard.instrucciones.finalizado"/>
		</div>
		<form:form id="formRegistro" method="post" modelAttribute="registro" role="form">
			<div id="seccionUmfRegistro" class="table_form">
				<form:hidden path="paso"/>
				<form:hidden path="parentesco.idParentesco"/>
				<form:hidden path="tipoTramite.idTipoTramite"/>
				<form:hidden path="fisica.curp"/>
				<form:hidden path="tramiteId"/>
				<form:hidden path="indSeleccionMedico"/>
				<form:hidden path="fechaCambioMedico"/>
				
				<div class="separadorseccion">
					<span>
						<spring:message code="label.adscripcion.titulo"/>
					</span>
				</div>
				<div id="mensajeIntegranteUmf" class="alert alert-info" align="center" style="display:none;">
					<p>
						<i class="glyphicon glyphicon-info-sign"></i><span id="mensajeUmfIntegrante"></span>
					</p>
				</div>
				<c:if test="${validacionesDom.setUmfAsegurado}">
					<div class="alert alert-info">
						<i class="glyphicon glyphicon-info-sign"></i>${validacionesDom.mensajeUmf }
					</div>					
				</c:if>
				<table id="datosAdscripcionTable" style="width: 100%" class="table table-striped table-bordered" >
					<tr>
						<td>
							<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.idUMF">
								<spring:message code="label.umf"/><span class="required">*</span>:
							</label>
						</td>
						<td colspan="3">
							<form:hidden path="medicoEnTurno.idMedicoContultorioTurno" />	
							<select id="medicoEnTurno.unidadMedicaFamiliar.idUMF" name="medicoEnTurno.unidadMedicaFamiliar.idUMF" class="form-control ns_">
							  	<option value="-1"><spring:message code="label.opcion.default.select"/></option>
							 </select>						
							 <span id="medicoEnTurno.unidadMedicaFamiliar.idUMFError" class="error hiddenElement"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion">
								<spring:message code="label.adscripcion.delegacion"/>:
							</label>
						</td>
						<td>
							<form:hidden path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.id"/>
							<form:input path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion" cssClass="form-control ns_"/>
						</td>
						<td>
							<label class="control-label" for="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion">
								<spring:message code="label.adscripcion.subdelegacion"/>:
							</label>
						</td>
						<td>
							<form:hidden path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.id"/>
							<form:input path="medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion" cssClass="form-control ns_"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label" for="medicoEnTurno.turno.idTurno">
								<spring:message code="label.adscripcion.turno"/><span class="required">*</span>:
							</label>
						</td>
						<td>
							<select id="medicoEnTurno.turno.idTurno" name="medicoEnTurno.turno.idTurno" class="form-control ns_">
								 <option value="-1"><spring:message code="label.opcion.default.select"/></option>
							</select>
							
							<span id="medicoEnTurno.turno.idTurnoError" class="error hiddenElement"></span>
						</td>
						<td>
							<label class="control-label" for="medicoEnTurno.consultorio.idConsultorio">
								<spring:message code="label.adscripcion.consultorio"/><span class="required">*</span>:
							</label>
						</td>
						<td>
							<select id="medicoEnTurno.consultorio.idConsultorio" name="medicoEnTurno.consultorio.idConsultorio" class="form-control ns_">
								 <option value="-1"><spring:message code="label.opcion.default.select"/></option>
							</select>
							
							<span id="medicoEnTurno.consultorio.idConsultorioError" class="error hiddenElement"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label" for="medicoEnTurno.medicoFamiliar.idMedicoFamiliar">
								<spring:message code="label.adscripcion.medico"/>:
							</label>
						</td>
						<td colspan="3">
							<form:hidden path="medicoEnTurno.medicoFamiliar.idMedicoFamiliar"/>
							<form:input path="medicoEnTurno.medicoFamiliar.nombre" cssClass="form-control ns_"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="control-label" for="medicoEnTurno.medicoFamiliar.noMatricula">
								<spring:message code="label.adscripcion.matricula"/>:
							</label>
						</td>
						<td><form:input path="medicoEnTurno.medicoFamiliar.noMatricula" cssClass="form-control ns_"/></td>
						<td>
							<label class="control-label" for="medicoEnTurno.medicoEspecialidad.descripcion">
								<spring:message code="label.adscripcion.especialidad"/>:
							</label>
						</td>
						<td>
							<form:hidden path="medicoEnTurno.medicoEspecialidad.idMedicoEspacialidad"/>
							<form:input path="medicoEnTurno.medicoEspecialidad.descripcion" cssClass="form-control ns_"/>
						</td>
					</tr>
				</table>
				<c:if test="${requiereDocs}">
					<div class="separadorseccion">
						<span><spring:message code="label.documentos.probatorios.titulo"/></span>
					</div>
					<div class="row">
						<div class="col-sm-12">
						<p>
						<spring:message code="label.documentos.probatorios.mensaje1"/> 
						<a id="capturarDocumentos" class="alert-link"><spring:message code="label.documentos.probatorios.mensaje2"/></a> 
						<spring:message code="label.documentos.probatorios.mensaje3"/><span class="required">*</span>
						</p>
						</div>
					</div>
				</c:if>
			</div>
		</form:form>
		</c:if>
	</div>
	</div>
 	
	<div class="pie row">
		<div class="col-sm-4">
			<div style="float: left; padding: 11px 0px;"><span class="required" id="labelCamposObligatoriosGeneral">*</span><spring:message code="label.camposRequeridos" /></div>
		</div>
		<div class="col-sm-8">
			<div class="pull-right">
			<button id="regresar" class="btn btn-default" type="button" onclick="uid_call('imss.derechohabientes.registro.datosAdscripcion.btn_anterior','clickin')"> <span class="glyphicon glyphicon-step-backward"></span><spring:message code="wizard.button.anterior" /></button>
			
			<c:if test="${empty error}">
				<div class="btn-group dropup">
					<a href="#" class="btn btn-primary"><spring:message code="label.menus.opciones" /></a> 
					<a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span class="caret"></span></a>
					<ul class="dropdown-menu pull-right">
						<li><a id="finalizarTramite" onclick="uid_call('imss.derechohabientes.registro.datosAdscripcion.link_finalizar','clickin')"><i class="glyphicon glyphicon-ok"></i><spring:message code="wizard.button.finalizarTramite" /></a></li>
						<li><a id="guardarTramite" onclick="uid_call('imss.derechohabientes.registro.datosAdscripcion.link_guardar','clickin')"><i class="glyphicon glyphicon-download-alt"></i><spring:message code="wizard.button.guardarTramite" /></a></li>
					<li><a id="guardarCerrarTramite" onclick="uid_call('imss.derechohabientes.registro.datosAdscripcion.link_guardarCerrar','clickin')"><i class="glyphicon glyphicon-remove"></i><spring:message code="wizard.button.guardarCerrar" /></a></li>
					<li><a id="cancelarTramite" onclick="uid_call('imss.derechohabientes.registro.datosAdscripcion.link_cancelar','clickin')"><i class="glyphicon glyphicon-trash"></i><spring:message code="wizard.button.cancelarTramite" /></a></li>
					</ul>
				</div>
			</c:if>
			</div>
		</div>



	</div>
</div>

<jsp:include page="pieRegistro.jsp"/>