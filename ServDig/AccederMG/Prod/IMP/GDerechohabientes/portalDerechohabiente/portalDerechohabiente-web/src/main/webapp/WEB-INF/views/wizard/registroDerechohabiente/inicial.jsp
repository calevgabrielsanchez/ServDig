<%@ include file="../../general/taglibs.jsp" %>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum"%>

<style>
	#selectable .ui-selecting {
		background: #1A79A7;
		color: white;
	}
	
	#selectable .ui-selected {
		background: #428BCA;
		color: white;
	}
	
	#selectable {
		list-style-type: none;
		margin: 0;
		padding: 0;
		width: 100%;
		cursor: pointer
	}
	
	#selectable li {
		margin: 3px;
		padding: 0.4em;
		color: #67666A;
	}

	a:active {
		outline: none;
	}
	
	a:focus {
		-moz-outline-style: none;
	}
</style>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/registroDerechohabiente/inicial.js" htmlEscape="true" />"></script>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<input type="hidden" value="${parentescoARegistrarSession}" id="parentescoARegistrar"/>
<c:set var="tipoSolicitudCapital" value="${fn:toUpperCase(fn:substring(descripcionTipoSolicitud, 0, 1))}${fn:toLowerCase(fn:substring(descripcionTipoSolicitud, 1,fn:length(descripcionTipoSolicitud)))}"></c:set>
<div class="contenedor col-sm-12">

	<div class="contenido row">

		<div class="introduccion col-sm-4">

			<div class="titulo separadorseccion">
				<span> ${tipoSolicitudCapital} </span>
			</div>

			<div class="descripcion">
				<p>
					<c:if test="${parentescoARegistrarSession ne 0 }">
						<spring:message code="label.wizard.registro.descripcion.asegurado"/>
					</c:if>
					<c:if test="${parentescoARegistrarSession eq 0 }">
						<spring:message code="label.wizard.registro.descripcion.beneficiarios"/>
					</c:if>
				</p>
			</div>

			<div class="opciones">


				<div style="max-width: 400px;">
					<c:if test="${empty error}">
							<c:choose>
								<c:when test="${!solicitudCreada}">
									<button class="btn btn-primary btn-block" role="button" aria-disabled="false" id="btnInciaTramite" onclick="uid_call('imss.derechohabientes.registro.inicio.btn_iniciarTramite','clickin')">
										<span class="ui-button-text"><spring:message code="label.boton.solicitud.iniciar" /></span>
									</button>
								</c:when>
								<c:otherwise>
									<c:if test="${mismoOrigen}">
										<button class="btn btn-primary btn-block" role="button" aria-disabled="false" id="btnRetomarTramite"  onclick="uid_call('imss.derechohabientes.registro.inicio.btn_retomar','clickin')">
											<span class="ui-button-text"><spring:message code="label.boton.solicitud.retomar" /></span>
										</button>
										<button class="btn btn-danger btn-block" role="button" aria-disabled="false" id="btnCancelarTramite"  onclick="uid_call('imss.derechohabientes.registro.inicio.btn_cancelar','clickin')">
											<span class="ui-button-text"><spring:message code="label.boton.solicitud.cancelar.proceso" /></span>
										</button>
									</c:if>
								</c:otherwise>
							</c:choose>
					</c:if>
					<button class="btn btn-default btn-block" role="button" aria-disabled="false" id="btnInicioCancelarTramite"  onclick="uid_call('imss.derechohabientes.registro.inicio.btn_salir','clickin')">
						<span class="ui-button-text"><spring:message code="label.boton.solicitud.cancelar" /></span>
					</button>
				</div>

			</div>
		</div>

		<div class="instrucciones col-sm-8">
			<c:if test="${empty error}">
					<c:choose>
						<c:when test="${!solicitudCreada}">
							<c:if test="${parentescoARegistrarSession ne 0 }">
								<%@ include file="../general/mensajeOpcionesIniciarSolicitud.jsp" %>
							</c:if>
							<c:if test="${parentescoARegistrarSession eq 0 }">
								<div class="alert alert-info">
									<spring:message code="label.wizard.registro.instrucciones" />
								</div>
								
								
								<div class="well" style="background-color: white;">
									<div id="listaTramitesBeneficiarios" title="Tr&aacute;mites">
										<input type="hidden" value="${mostrarPadres}">
										<ol id="selectable">
											<li class="ui-widget-content" title="Registro de beneficiarios con parentesco Conyuge."  id="<%=ParentescoEnum.CONYUGE.getId()%>">
												<spring:message code="label.wizard.registro.registroConyuge" />
											</li>
											<li class="ui-widget-content" title="Registro de beneficiarios con parentesco hijo." id="<%=ParentescoEnum.HIJOS.getId()%>">
												<spring:message code="label.wizard.registro.registroHijos"/>
											</li>
											<c:if test="${mostrarPadres}">
											<li class="ui-widget-content" title="Registro de beneficiarios con parentesco padre o madre." id="<%=ParentescoEnum.PADRES.getId()%>">
												<spring:message code="label.wizard.registro.registroPadre"/>
											</li>
											</c:if>
											<li class="ui-widget-content" title="Registro de beneficiarios con parentesco concubina(rio)." id="<%=ParentescoEnum.CONCUBINARIO.getId()%>">
												<spring:message code="label.wizard.registro.registroConcubina"/>
											</li>
										</ol>
									</div>
								</div>
								
								
							</c:if>
							
							<div class="alert alert-info" style="margin-top: 20px; display:none" id="avisoPrivacidad">
								<p><strong>Aviso de privacidad simplificado</strong></p>
								La recolecci&oacute;n de datos personales se lleva a cabo a trav&eacute;s de la p&aacute;gina 
								electr&oacute;nica <a href="${mvn.avisos.contexto}/portal-web/portal"
								target="_blank">${mvn.avisos.contexto}/portal-web/portal</a> cuyo administrador y responsable del tratamiento 
								es la Coordinaci&oacute;n de Clasificaci&oacute;n de Empresas y Vigencia de Derechos del Instituto Mexicano del Seguro Social. 
								Los datos personales que se recaban ser&aacute;n utilizados con la finalidad de llevar a cabo el
								<span id="tituloTramite"></span> como  derechohabiente en el IMSS con Homoclave <span id="homoclaveTramite"></span>.
				 				Si deseas conocer nuestro aviso de privacidad integral, lo podr&aacute;s consultar en el portal: 
								<a href="${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoRegistro.jsp"
								target="_blank">${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoRegistro.jsp</a>
							</div>
						</c:when>
						<c:otherwise>
							<c:if test="${mismoOrigen}">
								<div class="alert alert-info">
									<spring:message code="label.mensaje.solicitud.enProceso" arguments="${tipoTramiteCreado.descripcion}"/>
								</div>
			
								<%@ include file="../general/mensajeOpcionesRetomarSolicitud.jsp" %>
							</c:if>
							<c:if test="${!mismoOrigen }">
								<div class="alert alert-info">
									<spring:message code="label.mensaje.tramite.existente" arguments="${tipoTramiteCreado.descripcion},'VENTANILLA'"></spring:message>
								</div>
							</c:if>
							
							<div class="alert alert-info" style="margin-top: 20px">
								<p><strong>Aviso de privacidad simplificado</strong></p>
								La recolecci&oacute;n de datos personales se lleva a cabo a trav&eacute;s de la p&aacute;gina 
								electr&oacute;nica <a href="${mvn.avisos.contexto}/portal-web/portal"
								target="_blank">${mvn.avisos.contexto}/portal-web/portal</a> cuyo administrador y responsable del tratamiento 
								es la Coordinaci&oacute;n de Clasificaci&oacute;n de Empresas y Vigencia de Derechos del Instituto Mexicano del Seguro Social. 
								Los datos personales que se recaban ser&aacute;n utilizados con la finalidad de llevar a cabo el
								<c:if test="${tipoTramiteCreado.idTipoTramite == 44 || tipoTramiteCreado.idTipoTramite ==45}">
								 Registro del asegurado (a) o pensionado(a) como  derechohabiente en el IMSS con Homoclave IMSS-02-066-M.
								</c:if>
								<c:if test="${tipoTramiteCreado.idTipoTramite == 48}">
									Registro de hijo (a) como derechohabiente en el IMSS con Homoclave IMSS-02-066-J.
								</c:if>
								<c:if test="${tipoTramiteCreado.idTipoTramite == 49}">
									Registro de padre y/o madre como derechohabiente en el IMSS con Homoclave IMSS-02-066-G.
								</c:if>
								<c:if test="${tipoTramiteCreado.idTipoTramite == 46}">	
									Registro de concubina (rio) como derechohabiente en el IMSS con Homoclave IMSS-02-066-D.
								</c:if>
								<c:if test="${tipoTramiteCreado.idTipoTramite == 47}">
									Registro de esposa (o) como derechohabiente en el IMSS con Homoclave IMSS-02-066-A.
								</c:if>
				 				Si deseas conocer nuestro aviso de privacidad integral, lo podr&aacute;s consultar en el portal: 
								<a href="${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoRegistro.jsp"
								target="_blank">${mvn.avisos.contexto}/gestionAsegurados-web-externo/avisoRegistro.jsp</a>
						</div>
						</c:otherwise>
					</c:choose>
					
					
			</c:if>
			<c:if test="${not empty error }">
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">×</button>
					<strong>Error: </strong>${error}
				</div>
			</c:if>
		</div>
	</div>

	<div class="pie row">
		<div class="controles"></div>
	</div>
</div>

<!-- Forma para invocar la baja de derehohabiente -->
<form:form id="formIniciaTramite" method="post"
	action="${contextPath}/wizard/registro/iniciarTramite">
</form:form>
<!--  -->

<!-- Forma para invocar el retomar o cancelar una solicitud -->
<form:form action="" modelAttribute="solicitudForm" id="solicitudForm" method="post">
	<form:hidden path="solicitudId" id="idSolicitudPendiente" />
	<form:hidden path="noFolioSolicitud" id="noFolioSolicitud" />
</form:form>
<!--  -->

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>
		<spring:message code="label.solicitud.mensaje.cancelar" arguments="${solicitudForm.noFolioSolicitud}"/>
	</p>
</div>

<div id="dialog-confirm" title="Mensaje confirmaci&oacute;n">
	<p><span class="ui-icon ui-icon-alert"	style="float: left; margin: 0 7px 20px 0;"></span> 
		<label id="mensajeDialogo"></label>
	</p>
</div>
<!--  -->