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

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/comprobanteVigencia/inicial.js" htmlEscape="true" />"></script>
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
							Iniciar solicitud
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
							en el bot&oacute;n <strong>"Iniciar solicitud"</strong>
						</div>
						<form:form modelAttribute="asignacionNSS" id="asignacionNSS" action="${contextPath}/wizard/comprobante/vigencia/imprimir">

							<div class="alert alert-info">
								1. Capture su NSS por favor:
							</div>
							<div class="well" style="background-color: white;">
								<table id="tablaDatosPersonales" width="100%">
									<tr>
										<td width="30%">
											<label class="control-label" for="nss">
												NSS<span class="required" id="indCurpObligatoria">*</span>:
											</label>
										</td>
										<td>
											<form:input path="nss" cssClass="form-control" maxlength="11" /> 
											<form:errors path="nss" cssClass="error error-custom"  spanRequired="indCurpObligatoria" campoRelacionado="nss"></form:errors>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<div style="float: left;">
												<br><br>
												<span class="required"  id="labelCamposObligatoriosGeneral">*</span> Campos obligatorios
												
											</div>
										</td>
									</tr>
								</table>
							</div>
						</form:form>
						
						<div class="alert alert-info" style="margin-top: 20px">
								<p><strong>Aviso de privacidad simplificado</strong></p>
								La recolecci&oacute;n de datos personales se lleva a cabo a trav&eacute;s de la p&aacute;gina 
								electr&oacute;nica <a href="${mvn.avisos.contexto}/portal-ciudadano-web-externo/home"
								target="_blank">${mvn.avisos.contexto}/portal-ciudadano-web-externo/home</a> cuyo administrador y responsable del tratamiento 
								es la Coordinaci&oacute;n de Clasificaci&oacute;n de Empresas y Vigencia de Derechos del Instituto Mexicano del Seguro Social. 
								Los datos personales que se recaban ser&aacute;n utilizados con la finalidad de generar y obtener la Constancia de 
								vigencia de derechos para recibir servicio m&eacute;dico ante el IMSS con Homoclave IMSS-02-020-B. 
				 				Si deseas conocer nuestro aviso de privacidad integral, lo podrás consultar en el portal: 
								<a href="${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoVigencia.jsp"
								target="_blank">${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoVigencia.jsp</a>
						</div>
					</c:if>
					<c:if test="${not empty error }">
						<div class="alert alert-info">
							<button type="button" class="close" data-dismiss="alert">×</button>
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