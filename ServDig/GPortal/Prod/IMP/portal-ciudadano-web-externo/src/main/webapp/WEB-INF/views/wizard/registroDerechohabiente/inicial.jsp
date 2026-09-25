<%@ include file="../../general/taglibs.jsp" %>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum"%>

<c:set var="OrigenSolicitudEnumVentanilla" value="<%=OrigenSolicitudEnum.VENTANILLA.getId()%>"/>
<c:set var="OrigenSolicitudEnumInternet" value="<%=OrigenSolicitudEnum.INTERNET.getId()%>"/>
<c:set var="OrigenSolicitudEnumCiudadano" value="<%=OrigenSolicitudEnum.PORTAL_CIUDADANO.getId()%>"/>

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
	
	#tablaDatosPersonales input,textarea,.uneditable-input {
		text-transform: uppercase;
	}
</style>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/registroDerechohabiente/inicial.js" htmlEscape="true" />"></script>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<input type="hidden" value="${parentescoARegistrarSession}" id="parentescoARegistrar"/>

<div class="container-fluid"> 
	<div class="wizard row"> 
		<div class="contenedor col-sm-12"> 
			<div class="contenido row">
				<div class="introduccion col-sm-4">
					<div class="titulo separadorseccion">
						<span> 
						<c:if test="${registroAsegurado}">
							Registro de asegurado / pensionado
						</c:if>
						<c:if test="${!registroAsegurado}">
							Registro de beneficiarios 
						</c:if>
						</span>
					</div>
					<div class="descripcion">
						<p>
							A trav&eacute;s de este tr&aacute;mite usted podr&aacute;
						<c:if test="${registroAsegurado}">
							registrarse como derechohabiente y seleccionar su Unidad M&eacute;dica familiar.
						</c:if>
						<c:if test="${!registroAsegurado}">
							registrar a sus beneficiarios.
						</c:if>
						
						</p>
					</div>
					<div class="opciones">
						<c:if test="${empty error}">
							<c:if test="${empty validaciones or validaciones.correcto}">
							
								<button class="btn btn-primary btn-block" id="btnInciaTramite">
									<spring:message code="label.boton.solicitud.iniciar" />
								</button>
							</c:if>
							<c:if test="${not empty validaciones && !validaciones.correcto && validaciones.solicitud.origenSolicitud.idTipoSolicitud == OrigenSolicitudEnumCiudadano}">
								<button class="btn btn-warning btn-block" id="btnIniciocancelarYContinuar">
									<spring:message code="label.boton.solicitud.cancelar.proceso" />
								</button>
							</c:if>
						</c:if>
						<button class="btn btn-default btn-block" id="btnInicioCancelarTramite">
							<spring:message code="label.boton.solicitud.cancelar" />
						</button>
					</div>
				</div>
		
				<div class="instrucciones col-sm-8">
					<h3>Instrucciones:</h3>
					<c:if test="${empty error}">
							<c:if test="${empty validaciones or validaciones.correcto}">
										<div class="alert">
											A continuaci&oacute;n proporcione la informaci&oacute; requerida para el tr&aacute;mite,
											una vez que complete los campos de clic en el bot&oacute;n <strong>"Iniciar Solicitud"</strong>
										</div>
										<div class="alert alert-info">
											1. Seleccion el tipo de registro a realizar:
										</div>
										<form:form modelAttribute="registro" id="registroBeneficiarios">
										<div class="well" style="background-color: white;">
											<div id="listaTramitesBeneficiarios" title="Tr&aacute;mites">
												<ol id="selectable">
													
													<c:if test="${opciones.ind_registro_conyuge}">
													<li class="ui-widget-content" 
															title="Registro de beneficiarios con parentesco Conyuge." 
															id="<%=ParentescoEnum.CONYUGE.getId()%>">Registro de conyuge.</li>
													</c:if>
													<c:if test="${opciones.ind_registro_hijos}">
													<li class="ui-widget-content" 
															title="Registro de beneficiarios con parentesco hijo."
															id="<%=ParentescoEnum.HIJOS.getId()%>">Registro de hijos.</li>
													</c:if>
													<c:if test="${opciones.ind_registro_padres}">
													<li class="ui-widget-content" 
															title="Registro de beneficiarios con parentesco padre o madre." 
															id="<%=ParentescoEnum.PADRES.getId()%>">Registro de padres.</li>
													</c:if>
													<c:if test="${opciones.ind_registro_concubina}">
													<li class="ui-widget-content" 
															title="Registro de beneficiarios con parentesco concubina(rio)."
															id="<%=ParentescoEnum.CONCUBINARIO.getId()%>">Registro de concubina(rio).</li>
													</c:if>
												</ol>
												<span id="parentesco.idParentescoError" class="error hiddenElement"></span>
											</div>
										</div>
										
										<div class="alert alert-info">
											2. Capture la curp del beneficiario a registrar:
										</div>
										<div class="well" style="background-color: white;">
											
												<table id="tablaDatosPersonales" width="100%" >
												<tr>
													<td>
														<label class="control-label" for="curp">
															<span class="required" id="indCurpObligatoria">*</span>&nbsp;CURP :
														</label>
													</td>
													<td>
														<form:hidden path="parentesco.idParentesco"/>
														<form:input path="fisica.curp" cssStyle="width: 300px"  cssClass="form-control" maxlength="18" />
														<span id="fisica.curpError" class="error hiddenElement"></span>
													</td>
												</tr>
												</table>
											
											
										</div>
										</form:form>
							</c:if>
							<c:if test = "${not empty validaciones and !validaciones.correcto }">
								<div class="alert alert-info">
									<button type="button" class="close" data-dismiss="alert">×</button>
									<strong>Importante: </strong>
									<c:if test="${validaciones.solicitud.origenSolicitud.idTipoSolicitud == OrigenSolicitudEnumVentanilla}">
										<spring:message code="SolicitudEnProcesoVentanillaException"  text="default text"/>
									</c:if>
									<c:if test="${validaciones.solicitud.origenSolicitud.idTipoSolicitud == OrigenSolicitudEnumInternet}">
										<spring:message code="SolicitudEnProcesoInternetException" text="default text1" />
									</c:if>
									<c:if test="${validaciones.solicitud.origenSolicitud.idTipoSolicitud == OrigenSolicitudEnumCiudadano}">
										<spring:message code="SolicitudEnProcesoCiudadanoException" text="default text1" />
									</c:if>
								</div>
							</c:if>
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
		<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span> ¿Desea cancelar la
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
<!--  -->