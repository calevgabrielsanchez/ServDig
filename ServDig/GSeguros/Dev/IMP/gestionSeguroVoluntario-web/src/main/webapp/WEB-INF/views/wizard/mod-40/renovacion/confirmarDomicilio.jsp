<%@ include file="../../../general/taglibs.jsp"%>
<%@ taglib prefix="domicilio"
	uri="http://www.serviciosdigitales.imss.gob.mx/tags/domicilio"%>


<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/mod-40/comunes/wizardCVROalta.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<style>
.direccion {
	padding: 15px 25px;
	font-size: 36px;
	text-aling: center;
}
</style>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<div class="titulo">
				<span>Paso 1 de 4: Verificar domicilio</span>
			</div>
			<div class="titulo">
				<span>¿Es este tu domicilio?</span>
				<hr class="red m-b-md">
			</div>
			<div class="alert alert-danger" style="display: none;"
				id="validacion">
				<span id="mensaje-validacion">Selecciona un Domicilio</span>
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
					</c:if>
					<ol id="listDomicilios" class="m-b-lg">
						<c:if
							test="${not empty solicitante.domicilioParticular && empty domicilioOtraUbicacion.codigoPostal}">
							<li id="domicilio" class="direccion  ui-selected"
								idDomicilio="${solicitante.domicilioParticular.idDomicilio}"
								codigoPostal="${solicitante.domicilioParticular.codigoPostal}"
								idEntidad="${solicitante.domicilioParticular.localidad.municipio.entidadFederativa.clave}"
								idMunicipio="${solicitante.domicilioParticular.localidad.municipio.clave}">
								<h5>
									<domicilio:generar-descripcion
										domicilio="${solicitante.domicilioParticular}" />
								</h5>
							</li>
						</c:if>
						<c:if test="${not empty domicilioOtraUbicacion.codigoPostal}">
							<li id="domicilio" class="direccion ui-selected"
								idDomicilio="${domicilioOtraUbicacion.idDomicilio}"
								codigoPostal="${domicilioOtraUbicacion.codigoPostal}"
								idEntidad="${domicilioOtraUbicacion.localidad.municipio.entidadFederativa.clave}"
								idMunicipio="${domicilioOtraUbicacion.localidad.municipio.clave}">
								<h5>
									<domicilio:generar-descripcion
										domicilio="${domicilioOtraUbicacion}" />
								</h5> 
							</li>
						</c:if>
					</ol>
				</c:otherwise>
			</c:choose>

			<form:form id="nextStepForm" modelAttribute="tramiteSeguro"
				action="${contextPath}/wizard/continuacionVoluntaria/renovacion/comunes/datosInscripcion">
				<form:hidden id="idDomSeguro" path="domicilioSeguro.idDomicilio" />
				<form:hidden id="cpDomSeguro" path="domicilioSeguro.codigoPostal" />
			</form:form>

			<form
				action="${contextPath}/wizard/continuacionVoluntaria/renovacion/comunes/otraUbicacion"
				id="otraUbicacionForm"></form>

		</div>
	</div>
	<div class="pie row">
		<div class="opciones col-sm-6"></div>
		<div class="controles col-sm-12">
			<div class="pull-right">
				<a id="agregarDomicilio" class="btn btn-default" href="#" >Otra ubicaci&oacute;n</a>
				<button id="cancelarTramiteEnAgregarDomcilio" class="btn btn-danger">Cancelar</button>
				<a id="siguientePaso" class="btn btn-primary" >Continuar</a>
			</div>
			<div id="dialog-confirm-cancelar"
				title="Confirmar cancelaci&oacute;n de solicitud">
				<p>
					<span class="ui-icon ui-icon-alert" style="float: center;"></span>
					&iquest;Est&aacute;s seguro de cancelar tu solicitud de Reingreso en la
					Continuaci&oacute;n Voluntaria en el R&eacute;gimen Obligatorio?
				</p>
			</div>

			<div id="dialog-cancelar-en-domicilio" title="Mensaje del Sistema">
				<p>
					<span class="ui-icon ui-icon-alert"
						style="float: left; margin: 0 7px 20px 0;"></span>&iquest;Est&aacute;s
					seguro de cancelar tu solicitud de Reingreso en la
					Continuaci&oacute;n Voluntaria en el R&eacute;gimen Obligatorio?
				</p>
			</div>
		</div>
	</div>
</div>
