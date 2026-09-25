<%@ include file="../../../../../../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum"%>

<script type="text/javascript" 
	src="<spring:url value="/static/resources/js/wizard/modificacion/patron/altaPatronal/inicial.js" htmlEscape="true" />"></script>

<c:set var="origenINTERNET" value="<%=OrigenSolicitudEnum.INTERNET.getId()%>" />
<c:set var="idTipoPersonaFisicaEnum" value="<%=TipoPersonaEnum.FISICA.getId()%>" />

<script>
	var idTipoPersonaFisicaEnum = <%=TipoPersonaEnum.FISICA.getId()%>;
</script>

<input type="hidden" id="hdnIdSolicitud" value="${idSolicitud}" />
<input type="hidden" id="hdnTipoPersona" value="${personaForm.tipoPersona.idTipoPersona}" />

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="introduccion col-sm-4">
			<div class="titulo separadorseccion">			
				<c:choose>
					<c:when test="${personaForm.tipoPersona.idTipoPersona == idTipoPersonaFisicaEnum}">
						<span><spring:message code="wizard.altaPat.titulo.fisica"/></span>
					</c:when>
					<c:otherwise>
						<span><spring:message code="wizard.altaPat.titulo.moral"/></span>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="descripcion">
				<p><spring:message code="wizard.altaPat.descripcion"/></p>
			</div>

			<div class="opciones">
				<div style="max-width: 400px;">
					
					<c:if test="${!existeSolProceso}">
						<c:choose>
							<c:when test="${!existeSolRegistrada}">
								<button class="btn btn-primary btn-block"
									role="button" aria-disabled="false" id="btnInciaTramite" onclick="uid_call('imss.patrones.alta_patronal.inicio.btn_iniciarTramite','clickin')">
									<span class="ui-button-text">
										<spring:message code="label.boton.solicitud.iniciar"/>
									</span>
								</button>
							</c:when>
							<c:otherwise>
								<c:if test="${solicitudMismoOrigen}">
									<button class="btn btn-primary btn-block"
										role="button" aria-disabled="false" id="btnRetomarTramite" onclick="uid_call('imss.patrones.alta_patronal.inicio.btn_retomar','clickin')">
										<span class="ui-button-text">
											<spring:message code="label.boton.solicitud.retomar"/>
										</span>
									</button>
								</c:if>
								<button class="btn btn-danger btn-block"
									role="button" aria-disabled="false" id="btnCancelarTramite" onclick="uid_call('imss.patrones.alta_patronal.inicio.btn_cancelar','clickin')">
									<span class="ui-button-text">
										<spring:message code="label.boton.solicitud.cancelar.solicitud"/>
									</span>
								</button>
							</c:otherwise>
						</c:choose>
					</c:if>
										
					<button
						class="btn btn-default btn-block"
						role="button" aria-disabled="false" id="btnInicioCancelarTramite" onclick="uid_call('imss.patrones.alta_patronal.inicio.btn_salir','clickin')">
						<span class="ui-button-text">
							<spring:message code="label.boton.solicitud.cancelar"/>
						</span>
					</button>
				</div>
			</div>
		</div>

		<div class="instrucciones col-sm-8">
			<c:choose>
				<c:when test="${existeSolRegistrada}">
					<div class="alert alert-info">
						Ya cuentas con una solicitud para <strong>ALTA PATRONAL</strong> en proceso.<br>
						Folio de solicitud: <strong>${folioSolicitud}</strong>.<br>
						Origen de solicitud: <strong>${descripcionOrigenSolicitud}</strong>.<br>
					</div>
				</c:when>
				<c:when test="${existeSolProceso}">
					<div class="alert alert-warning">
						Existe un tr&aacute;mite de <strong>ALTA PATRONAL</strong> en curso, espera a su
						conclusi&oacute;n para iniciar un nuevo tr&aacute;mite.<br>
						Folio de solicitud: <strong>${folioSolicitud}</strong>.<br>
						Origen de solicitud: <strong>${descripcionOrigenSolicitud}</strong>.<br>
					</div>
				</c:when>
			</c:choose>
			<ul>
					<li>
						<c:if test="${personaForm.tipoPersona.idTipoPersona == 1}">
							<p>Alta patronal para persona f&iacute;sica</p>
						</c:if>
						<c:if test="${personaForm.tipoPersona.idTipoPersona == 2}">
							<p>Alta patronal para persona moral</p>
						</c:if>
	
							<p>
								Es el tr&aacute;mite a trav&eacute;s del cual podr&aacute;s registrarte como patr&oacute;n 
								para que despu&eacute;s puedas inscribir a tus trabajadores. 
								El Instituto te asignar&aacute; un registro patronal por centro de trabajo 
								en municipio distinto o uno en la Ciudad de M&eacute;xico.
							</p> 
							<p>
								1.	Podr&aacute;s solicitar de manera no presencial registros patronales con el uso de tu FIEL.<br>
								2.	Inicialmente, s&oacute;lo podr&aacute;s solicitar registros patronales de persona f&iacute;sica. <br>
								3.	En caso de requerir un "Registro Patronal por Clase (RPC)", deber&aacute;s acudir a la Subdelegaci&oacute;n que corresponda a tu domicilio fiscal para realizar el tr&aacute;mite.<br> 
								4.	Si realizaste una modificaci&oacute;n recientemente de tu informaci&oacute;n ante el SAT o RENAPO, esta informaci&oacute;n ser&aacute; utilizada en este tr&aacute;mite.<br>
							</p>
							<p>
								Instrucciones:
							</p>
							<c:if test="${origenApp eq origenINTERNET}">
								<p>
									Para iniciar la solicitud se requiere cuentes con tu RFC y archivos de tu FIEL.
								</p>
							</c:if>
							<p>
								1.	Selecciona la opci&oacute;n iniciar tr&aacute;mite y captura los datos solicitados.<br>
								2.	Los campos marcados con un asterisco (*) son datos obligatorios.<br>
							</p>
	
					</li>
					<li>
						<p>En caso de requerir un "Registro Patronal por Clase (RPC)", 
							deber&aacute;s realizar el tr&aacute;mite en la Subdelegaci&oacute;n que corresponda a tu domicilio fiscal.
						</p>
					</li>
					<li>
						<p>Si realizaste una modificaci&oacute;n recientemente de tu
							informaci&oacute;n en el SAT o RENAPO al realizar el presente tr&aacute;mite
							tu informaci&oacute;n ser&aacute; actualizada.</p>
					</li>
			</ul>				
		</div>
		
	</div>
	<!-- 
		<div class="pie row">
			<div class="opciones"></div>
			<div class="controles"></div>
		</div>
	 -->
</div>

<!-- Forma para invocar al servicio del Modificacion Manual de Datos -->
<form:form	modelAttribute="personaForm" id="personaForm" method="post">	
	<input id="hdnIdPersona" type="hidden" value="${personaForm.idPersona}" name="idPersona">
	<form:hidden path="tipoPersona.idTipoPersona" />
</form:form>

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<spring:message code="label.cancelar.solicitud" arguments="${folioSolicitud}"/>
	</p>
</div>

<div id="dialog-confirm" title="Mensaje confirmaci&oacute;n">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogo"></label>
	</p>
</div>

<div id="dialogoMensajesAP">
	<p>
		<span id="textoMensajeAP"></span>
	</p>
</div>