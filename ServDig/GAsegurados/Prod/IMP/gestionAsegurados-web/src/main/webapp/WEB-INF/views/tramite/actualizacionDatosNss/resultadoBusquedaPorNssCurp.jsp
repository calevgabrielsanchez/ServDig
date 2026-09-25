<!-- Vista para la captura de los datos de la persona -->
<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum"%>

<c:set var="tipoActaNacimiento"><%=DocumentoPorTipoEnum.ACTA_NACIMIENTO.getId()%></c:set>
<c:set var="tipoCartaNaturalizacion"><%=DocumentoPorTipoEnum.CARTA_NATURALIZACION.getId()%></c:set>
<c:set var="tipoDocumentoMigratorio"><%=DocumentoPorTipoEnum.DOCUMENTO_MIGRATORIO.getId()%></c:set>
<c:set var="tipoNumeroUnicoExtranjero"><%=DocumentoPorTipoEnum.NUMERO_UNICO_EXTRANJERO.getId()%></c:set>
<c:set var="tipoCertificadoNacionalidad"><%=DocumentoPorTipoEnum.CERTIFICADO_NACIONALIDAD_MEXICANA
						.getId()%></c:set>
<c:set var="tipoOficioSolicitanteRef"><%=DocumentoPorTipoEnum.OFICIO_SOLICITANTE_REFUGIADO
						.getId()%></c:set>
<c:set var="tipoFormaMigratoriaTurista"><%=DocumentoPorTipoEnum.FORMA_MIGRATORIA_TURISTA.getId()%></c:set>


<script type="text/javascript"
	src="/gestionIndividuo-consulta-web/static/resources/js/delta/personas/fisica/modificacion/manual/modificacion-manual-datos.js"></script>
	<script type="text/javascript"
	src="/gestionIndividuo-consulta-web/static/resources/js/delta/personas/fisica/identificar/cambios-automaticos/identificar-cambios-automaticos.js"></script>
	<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/tramite/actualizacionDatosNss/tramite-resultadoBusquedaNss.js" htmlEscape="true" />"></script>

<script>
	var isICA = ${isICA};
	var isMDM = ${isMDM};
</script>

<div>
	<div id="info-paso">
		<h3 style="font-size: 1.8em !important">Paso 2: Confirmaci&oacute;n de los datos de la Persona</h3>
		<div class="textwidget">
			<p style="font-size: .9em;">Confirme si los datos de la persona son los correctos</p>
		</div>
	</div>

	<div class="alert alert-info">
		<i class="glyphicon glyphicon-exclamation-sign m-r-xs"></i>
		La persona fue localizada en el IMSS
	</div>

	<!-- Forma de la consulta de personas por datos basicos. -->
	<div class="contenedor filtros-busqueda">
		<c:set var="contextpath" value="<%=request.getContextPath()%>" />
		<fieldset>
			<legend> Datos B&aacute;sicos de la Persona </legend>
			<form:form modelAttribute="fisica" id="registroPersonaFisicaForm" action="${contextpath}/tramite/crear">
				<form:hidden path="estatusRenapo" />
				<div id="datosBasicosDiv" class="row">
					<form:hidden path="idPersona" />
					<div class="col-sm-6">
						<div class="form-group">
							<form:label path="curp">CURP</form:label>
							<form:input path="curp" maxlength="18" cssClass="form-control" />
							<span id="curpError" class="error hiddenElement"></span>
						</div>
						<div class="form-group">
							<form:label path="primerApellido">Primer Apellido</form:label>
							<form:input path="primerApellido" id="registroPrimerApellido" maxlength="50" readonly="true"
								cssClass="disabled form-control" />
						</div>
						<div class="form-group">
							<form:label path="sexo.idSexo">Sexo</form:label>
							<form:input path="sexo.descripcion" id="sexo.descripcion" readonly="true" cssClass="disabled form-control" />
							<form:hidden path="sexo.idSexo" id="sexo.idSexo" />
							<span id="sexo.idSexoError" class="error hiddenElement"></span>
						</div>
						<div class="form-group">
							<form:label path="lugarNacimiento.clave">Lugar de Nacimiento</form:label>
							<form:input path="lugarNacimiento.nombre" id="lugarNacimiento.nombre" readonly="true"
								cssClass="disabled form-control" />
							<span id="lugarNacimiento.claveError" class="error hiddenElement"></span>
							<form:hidden path="lugarNacimiento.clave" id="lugarNacimiento.clave" />
						</div>
					</div>
					<div class="col-sm-6">
						<div class="form-group">
							<form:label path="nombre">Nombre(s)</form:label>
							<form:input path="nombre" id="registroNombres" maxlength="50" readonly="true" cssClass="disabled form-control" />
						</div>
						<div class="form-group">
							<form:label path="segundoApellido">Segundo Apellido</form:label>
							<form:input path="segundoApellido" id="registroSegundoApellido" maxlength="50" readonly="true"
								cssClass="disabled form-control" />
						</div>
						<div class="form-group">
							<form:label path="fechaNacimiento">Fecha de Nacimiento</form:label>
							<form:input path="fechaNacimiento" id="registroFechaNacimientoC" maxlength="10" readonly="true"
								cssClass="disabled form-control" />
						</div>
						<c:if test="${not empty fisica.nss}">
							<div class="form-group">
								<form:label path="nss">NSS</form:label>
								<form:input path="nss" readonly="true" cssClass="disabled form-control" />
							</div>
						</c:if>
					</div>
					<span id="errorFormGeneralError" class="error hiddenElement"></span>
				</div>
				<div class="text-right m-t-lg">
					<button type="button" id="regresar" class="btn btn-default">REGRESAR</button>
					<button type="button" id="continuar" class="btn btn-primary">CONTINUAR</button>
				</div>
			</form:form>
		</fieldset>
	</div>
</div>

<div id="comprobanteAsignacion"></div>
<div id="dialogICA"></div>
<div id="dialogMDM"></div>
<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		<label id="mensajeDialogo"></label>
	</p>
</div>