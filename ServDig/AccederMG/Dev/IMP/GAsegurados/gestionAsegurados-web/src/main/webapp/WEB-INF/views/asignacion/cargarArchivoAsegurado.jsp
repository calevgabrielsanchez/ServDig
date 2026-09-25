<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo"
	uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/asignacion/cargarArchivoAsegurado.js" htmlEscape="true" />"></script>

<c:if test="${not empty FROM_PORTAL}">
	<script type="text/javascript"
		src="<spring:url value="/static/resources/js/delta/asignacion/wizard/estudiantes/contenido.js" htmlEscape="true" />"></script>
	<jsp:include page="wizard/estudiantes/commonFromPortal.jsp"></jsp:include>
	
	<script type="text/javascript">
		var codigoTipoSolicitud = ${codigoTipoSolicitud};
		var descripcionTipoSolicitud = '${descripcionTipoSolicitud}';
		var arrayCodigoTipoTramite = ${codigoTipoTramite};
		var contenidoFirmar = '${contenidoFirmar}';
		
		var datosEntradaFirma = {
			fechaElectronica : '${datosFirmaElectronica.fechaElectronicaFormateada}',
			nombreCompleto : '${datosFirmaElectronica.nombreCompleto}',
			registroPatronal : '${datosFirmaElectronica.registroPatronal}',
			rfc : '${datosFirmaElectronica.rfc}',
			curp : '${datosFirmaElectronica.curp}'
		};
	</script>
</c:if>

<c:choose>
	<c:when test="${not empty FROM_PORTAL}">
		<c:set var="URL"
			value="${contextpath}/asignacion/portal/registro-masivo/cargarArchivo" />
		<c:set var="outerDivCssClass" value="contenedor" />
		<c:set var="innerDivCssClass" value="contenido" />
		<c:choose>
			<c:when test="${empty uploadItem.correosContacto}">
				<c:set var="mostrarAdvertenciaCorreo" value="true" />
			</c:when>
			<c:otherwise>
				<c:set var="mostrarDetalleCorreo" value="true" />
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:set var="URL"
			value="${contextpath}/asignacion/registro-masivo/cargarArchivo" />
		<c:set var="pedirCorreo" value="true" />
		<c:set var="outerDivCssClass" value="page_holder_custom" />
		<c:set var="innerDivCssClass" value="contenedor" />
	</c:otherwise>
</c:choose>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<div class="${outerDivCssClass }">
	<div class="${innerDivCssClass }">
		<div class="alert alert-info">A continuaci&oacute;n debe elegir
			el archivo XML a cargar.</div>

		<form:form modelAttribute="uploadItem" id="uploadFileForm"
			action="${URL}" method="post" enctype="multipart/form-data"
			cssClass="form-horizontal">

			<c:if test="${not empty mostrarAdvertenciaCorreo }">
				<div class="alert alert-warning">No cuenta con correo
					electr&oacute;nico asociado, para poder notificarle de la
					finalizaci&oacute;n del proceso es necesario que agregue, al menos,
					un correo desde este mismo portal.</div>
			</c:if>

			<form:errors path="errorFormGeneral" cssClass="alert alert-danger"
				element="div" />

			<fieldset>
				<legend>
					Ubicaci&oacute;n de Fuente de Informaci&oacute;n
				</legend>
				
				<div class="form-group">
					<form:label path="erpName" cssClass="col-sm-4 control-label">
						Registro Patronal
					</form:label>
					<div class="col-sm-6">
						<p class="form-control-static">${uploadItem.erpName}</p>
					</div>
					<form:hidden path="erpName" />
				</div>
				
				<div class="form-group">
					<form:label path="erpName" cssClass="col-sm-4 control-label">
						Nombre Comercial
					</form:label>
					<div class="col-sm-6">
						<p class="form-control-static">${uploadItem.nombreComercialERP}</p>
					</div>
					<form:hidden path="nombreComercialERP" />
				</div>
				
				<c:choose>
					<c:when test="${not empty mostrarDetalleCorreo}">
						<div class="form-group">
							<form:label path="correosContacto" cssClass="col-sm-4 control-label">
								Correo(s) Electr&oacute;nico(s)
								<form:label path="correosContacto"
									cssStyle="font-size: 9px; font-style: italic; line-height: 9px;">
									(Centro de trabajo)
								</form:label>
							</form:label>
							<div class="col-sm-6">
								<ul>
									<c:forEach items="${uploadItem.correosContacto}" var="correo"
										varStatus="status">
										<li><label style="font-weight: bolder;">${correo.correo}</label></li>
										<form:hidden path="correosContacto[${status.index }].correo" />
									</c:forEach>
								</ul>
							</div>
						</div>
					</c:when>
					<c:when test="${not empty pedirCorreo}">
						<div class="form-group">
							<form:label path="correosContacto" cssClass="col-sm-4 control-label">
								<span class="required">*</span> Correo(s) Electr&oacute;nico(s)
							</form:label>
							<div class="col-sm-6">
								<form:input path="correosContacto[0].correo" maxlength="100"
									cssClass="form-control" />
								<form:errors path="correosContacto[0].correo" cssClass="error" />
							</div>
						</div>
						
						<div class="form-group">
							<form:label path="correosContacto" cssClass="col-sm-4 control-label">
								Correo Electr&oacute;nico (Alternativo)
							</form:label>
							<div class="col-sm-6">
								<form:input path="correosContacto[1].correo"
									cssClass="form-control" maxlength="100" />
								<form:errors path="correosContacto[1].correo" cssClass="error"/>
							</div>
						</div>
					</c:when>
				</c:choose>
				
				<div class="form-group">
					<form:label path="fileData" cssClass="col-sm-4 control-label">
						<span class="required">*</span>Ruta
					</form:label>
					<div class="col-sm-6">
						<input type="file" name="fileData" id="fileData" class="form-control" />
						<form:errors path="fileData" cssClass="error" />
						<c:if test="${not empty FROM_PORTAL}">
							<span class="error" id="fileDataError"></span>
						</c:if>
					</div>
				</div>
			</fieldset>
			<br>
			<c:choose>
				<c:when test="${not empty FROM_PORTAL}">
					<div class="pie" style="margin-bottom: 60px;">
						<div class="opciones">
							<div class="btn-group dropup">
								<a href="#" class="btn btn-primary">Acciones</a> <a href="#"
									data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
									class="caret"></span></a>
								<ul class="dropdown-menu">
									<li><a id="finalizarTramite"><i class="glyphicon glyphicon-ok"></i>
											Finalizar Tr&aacute;mite</a></li>
									<li><a id="cancelarTramite"><i class="icon-trash"></i>
											Cancelar Tr&aacute;mite</a></li>
								</ul>
							</div>
							<button class="btn btn-default" id="cerrarWizard">CERRAR</button>
						</div>
						<div class="controles"></div>
					</div>
				</c:when>
				<c:otherwise>
					<div style="float: right;">
						<button id="cargarArchivo" type="submit" class="btn btn-primary">Cargar
							Archivo</button>
					</div>
				</c:otherwise>
			</c:choose>
		</form:form>
	</div>
</div>

<c:if test="${not empty FROM_PORTAL}">
	<!-- Divs para dialogos de mensajes -->
	<div id="dialog-confirm-cancelar"
		title="Confirmar cancelaci&oacute;n de solicitud">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span> ¿Desea cancelar
			la solicitud?
		</p>
	</div>

	<div id="dialog-confirm-common" title="Mensaje">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0;"></span> <label
				id="mensajeDialogo"></label>
		</p>
	</div>
</c:if>