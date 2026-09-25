<%@ include file="../../general/taglibs.jsp" %>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>

<c:set var="OrigenSolicitudEnumVentanilla" value="<%=OrigenSolicitudEnum.VENTANILLA.getId()%>"/>
<c:set var="OrigenSolicitudEnumInternet" value="<%=OrigenSolicitudEnum.INTERNET.getId()%>"/>
<c:set var="OrigenSolicitudEnumCiudadano" value="<%=OrigenSolicitudEnum.PORTAL_CIUDADANO.getId()%>"/>

<script type="text/javascript">
			history.go(1);
</script>

<style>
	a:active {
		outline: none;
	}
	
	a:focus {
		-moz-outline-style: none;
	}
	
	#tablaDatosPersonales input,textarea,.uneditable-input {
		text-transform: uppercase;
	}
	
	
span.error-custom {
	float: none !important;
	vertical-align: super;
}

.required {
	color: red;
}

.filtros-busqueda .row {
	margin-bottom: 12px;
}

.filtros-busqueda .filtros .etiqueta {
	width: 25%;
}

input[type="text"] {
	margin-bottom: 0px;
}
	
</style>

<script type="text/javascript" src="<spring:url value="/resources/js/delta/wizard/comprobanteVigencia/inicial.js" htmlEscape="true" />"></script>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />


<div class="container-fluid"> 
	<div class="wizard row"> 
		<div class="contenedor col-sm-12"> 
			<div class="contenido row">
				<div class="introduccion col-sm-4">
					<div class="titulo separadorseccion">
						<span> 
							Reporte de vigencia de derechos
						</span>
					</div>
					<div class="descripcion">
						<p>
							A trav&eacute;s de este tr&aacute;mite usted podr&aacute;
							conocer su situaci&oacute;n de vigencia.
						</p>
					</div>
					<div class="opciones">
						<button class="btn btn-primary btn-block" id="btnObtenerReporte">
							Iniciar Solicitud
						</button>
						<button class="btn btn-default btn-block" id="btnInicioCancelarTramite">
							<spring:message code="label.boton.solicitud.cancelar" />
						</button>
					</div>
				</div>
		
				<div class="instrucciones col-sm-8">
					<h3>Instrucciones:</h3>
					<c:if test="${empty error}">
						<div class="alert">
							A continuaci&oacute;n proporcione la informaci&oacute;n requerida
							para el tr&aacute;mite, una vez que complete los campos de clic
							en el bot&oacute;n <strong>"Iniciar Solicitud"</strong>
						</div>
						<form:form modelAttribute="asignacionNSS" id="asignacionNSS" action="${contextPath}/wizard/comprobante/vigencia/imprimir">

							<div class="alert alert-info">
								1. Capture su NSS por favor:
							</div>
							<div class="well" style="background-color: white;">
								<table id="tablaDatosPersonales" width="100%">
									<tr>
										<td width="30%">
											<label class="control-label" for="nss"> <span
												class="required" id="indCurpObligatoria">*</span>&nbsp;NSS :
											</label>
										</td>
										<td>
											<form:input path="nss" cssClass="form-control" maxlength="11" /> 
											<form:errors path="nss" cssClass="error error-custom"></form:errors>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<div style="float: right;">
												<br>
												
											</div>
										</td>
									</tr>
								</table>
							</div>
						</form:form>
					</c:if>
					<c:if test="${not empty error }">
						<div class="alert alert-info">
							<button type="button" class="close" data-dismiss="alert">�</button>
							<strong>Importante: </strong>${error}
						</div>
					</c:if>
				</div>
			</div>
		
			<div class="pie">
				<div class="controles"></div>
			</div>
		</div>
	</div>
</div>

<div id="dialog-confirm" title="Mensaje confirmaci&oacute;n">
<p><span class="ui-icon ui-icon-alert"
	style="float: left; margin: 0 7px 20px 0;"></span> <label
	id="mensajeDialogo"></label></p>
</div>

<c:if test = "${not empty validaciones and !validaciones.correcto}">
	<c:if test="${validaciones.solicitud.origenSolicitud.idTipoSolicitud == OrigenSolicitudEnumInternet ||
		validaciones.solicitud.origenSolicitud.idTipoSolicitud == OrigenSolicitudEnumCiudadano}">
		<input type="hidden" id="idSolicitudPendiente" value="${validaciones.solicitud.solicitudId}"/>
		
		<!-- Divs para dialogos de mensajes -->
		<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
		<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span> �Desea cancelar la
			solicitud pendiente con folio: <strong>${validaciones.solicitud.noFolioSolicitud}</strong>?
		</p>
		</div>
		
		<div id="dialog-confirm-salir" title="Mensaje del sistema">
		<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
			<spring:message code="CerrarWizardMessage" text="default text1" />
		</p>
		</div>
	</c:if>
</c:if>
