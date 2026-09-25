<%@ include file="../../../general/taglibs.jsp"%>
<%@ taglib prefix="domicilio" uri="http://www.serviciosdigitales.imss.gob.mx/tags/domicilio"%>


<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/mod-40/comunes/wizardCVROalta.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<style>
	.ui-selectable li {
	    padding: 15px 25px;
	    word-wrap: break-word;
	}
</style>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<jsp:include page="encabezadoMod40.jsp">
			<jsp:param name="paso" value="1" />
		</jsp:include>
			<div class="titulo">
				<span>Domicilio</span>
				<hr class="red m-b-md">
			</div>
			<div class="alert alert-danger" style="display: none;" id="validacion">
				<span id="mensaje-validacion">Selecciona un domicilio</span>
			</div>
			<c:choose>
				<c:when test="${empty solicitante.domicilioParticular and empty domicilioOtraUbicacion.codigoPostal}">
					<div class="alert alert-warning">
						<span>No tienes registrado ning&uacute;n domicilio particular en el Instituto,
						para continuar con el tr&aacute;mite selecciona &quot;Otra ubicaci&oacute;n&quot;</span>
					</div>
				</c:when>
				<c:otherwise>
					<c:if test="${not empty solicitante.domicilioParticular}">
						<div class="alert alert-info">A continuaci&oacute;n se muestra el domicilio particular que tienes registrado 
							en el Instituto, si deseas puede elegir la opci&oacute;n de &quot;Otra ubicaci&oacute;n&quot;
						</div>
					</c:if>
					<ol id="listDomicilios" class="m-b-lg">
						<c:if test="${not empty solicitante.domicilioParticular}">
							<li class="ui-state-default domicilioParticular"
								idDomicilio="${solicitante.domicilioParticular.idDomicilio}"
								codigoPostal="${solicitante.domicilioParticular.codigoPostal}"
								idEntidad="${solicitante.domicilioParticular.localidad.municipio.entidadFederativa.clave}"
								idMunicipio="${solicitante.domicilioParticular.localidad.municipio.clave}">
								<h5>
									<domicilio:generar-descripcion domicilio="${solicitante.domicilioParticular}"/>
								</h5>
							</li>
						</c:if>
						<c:if test="${not empty domicilioOtraUbicacion.codigoPostal}">
							<li class="ui-state-default domicilioOtraUbicacion"
								idDomicilio="${domicilioOtraUbicacion.idDomicilio}"
								codigoPostal="${domicilioOtraUbicacion.codigoPostal}"
								idEntidad="${domicilioOtraUbicacion.localidad.municipio.entidadFederativa.clave}"
								idMunicipio="${domicilioOtraUbicacion.localidad.municipio.clave}">
								<h5>
									<domicilio:generar-descripcion domicilio="${domicilioOtraUbicacion}"/>
								</h5>
							</li>
						</c:if>
					</ol>
				</c:otherwise>
			</c:choose>
			
			<form:form id="nextStepForm"
				modelAttribute="tramiteSeguro"
				action="${contextPath}/wizard/continuacionVoluntaria/comunes/datosInscripcion" accept-charset="ISO-8859-1">
				<form:hidden id="idDomSeguro" path="domicilioSeguro.idDomicilio" />
				<form:hidden id="cpDomSeguro" path="domicilioSeguro.codigoPostal"/>
			</form:form>
			
			<form action="${contextPath}/wizard/continuacionVoluntaria/comunes/otraUbicacion" id="otraUbicacionForm" accept-charset="ISO-8859-1"></form>
			
		</div>
	</div>
	<div class="pie row">
		<div class="controles col-sm-12">
			<div class="pull-right">
				<a id="agregarDomicilio" class="btn btn-default" href="#"  onclick="uid_call('imss.gestion.seguro.voluntario.mod40.seleccionDomicilio.btn_otraUbicacion','clickin');">
				Otra ubicaci&oacute;n</a>
				<button id="cancelarTramite" class="btn btn-danger" onclick="uid_call('imss.gestion.seguro.voluntario.mod40.seleccionDomicilio.btn_cancelar','clickin');">
					<c:choose>
						<c:when test="${esVentanilla and tieneSeguros}">Regresar</c:when>
						<c:otherwise>Cancelar</c:otherwise>
					</c:choose>
				</button>
				<a id="siguientePaso" class="btn btn-primary"  onclick="uid_call('imss.gestion.seguro.voluntario.mod40.seleccionDomicilio.btn_continuar','clickin');">
				Continuar</a>
			</div>
		</div>
	</div>
</div>
