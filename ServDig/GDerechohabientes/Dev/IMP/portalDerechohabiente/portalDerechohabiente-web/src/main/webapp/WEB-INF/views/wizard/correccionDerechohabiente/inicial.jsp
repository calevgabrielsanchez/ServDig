<%@ include file="../../general/taglibs.jsp" %>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/wizard/correccionDerechohabiente/inicial.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript">

$(document).ready(
		function() {
			
			//Configuracion de validaciones y mensajes en el form
			$("#formIniciaTramite").validate({
				rules:{
					curpCap: {
						//required: true,
						minlength: 18,
						maxlength: 18,
						curp: true
					}
				},
				messages:{
					curpCap: {
						minlength: " Debe ser de 18 caracteres",
						maxlength: " Debe ser de 18 caracteres",
						curp: "Formato incorrecto"
					}
				},
// 				submitHandler: function(form){
// 					form.submit();
// 				}
			});
			
			// Se asigna la CURP desde el controller WizardCorreccionDatosDerechohabienteController al campo form#formIniciaTramite #curpCap
			$('form#formIniciaTramite #curpCap').val('${curpFromController}');
		}
	);
</script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<div class="contenedor">

	<div class="contenido">

		<div class="introduccion">

			<div class="titulo">
				<span> ${descripcionTipoSolicitud} </span>
			</div>

			<div class="descripcion">
				<p>A trav&eacute;s de este tr&aacute;mite usted podr&aacute;
					realizar correcci&oacute;n de datos del derechohabiente.</p>
			</div>

			<div class="opciones">


				<div style="max-width: 400px;">
					<c:if test="${empty error}">
						<c:if test="${!otroTipoTramite}">
							<c:choose>
								<c:when test="${!solicitudCreada}">
									<button
										class="ui-button-primary btn-block ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
										role="button" aria-disabled="false" id="btnInciaTramite">
										<span class="ui-button-text"><spring:message
									code="label.boton.solicitud.iniciar" /></span>
									</button>
								</c:when>
								<c:otherwise>
									<button
										class="ui-button-primary btn-block ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
										role="button" aria-disabled="false" id="btnRetomarTramite">
										<span class="ui-button-text"><spring:message
									code="label.boton.solicitud.retomar" /></span>
									</button>
									<button
										class="btn-block ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
										role="button" aria-disabled="false" id="btnCancelarTramite">
										<span class="ui-button-text"><spring:message
									code="label.boton.solicitud.cancelar.proceso" /></span>
									</button>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:if>
					<button
						class="ui-button btn-block ui-widget ui-state-default ui-corner-all ui-button-text-only"
						role="button" aria-disabled="false" id="btnInicioCancelarTramite">
						<span class="ui-button-text"><spring:message
							code="label.boton.solicitud.cancelar" /></span>
					</button>
				</div>

			</div>
		</div>

		<div class="instrucciones" style="width: 65% !important;">
			<c:if test="${empty error}">
				<c:if test="${!otroTipoTramite}">
					<c:choose>
						<c:when test="${!solicitudCreada}">
<!-- 							<form id="curpCaptureForm" action=""> -->
<!-- 								<div class="descripcion"> -->
<!-- 									<p> -->
<!-- 									Favor de verificar la CURP mostrada, en caso de que sea incorrecta favor de corregir: -->
<!-- 									</p> -->
<!-- 								</div>  -->
<%-- 								<input id="curpFromController" value="${curpFromController}"> --%>
<!-- 							</form>	 -->

							<!-- Forma para invocar el inicio del tramite de la correccion de datos del derehohabiente y al
							mismo tiempo el servicio de ICA-->
							<form:form id="formIniciaTramite" method="post" action="${contextPath}/wizard/correccion/iniciarTramite" modelAttribute="datosEntrada">
							</form:form>


							<%@ include file="../general/mensajeOpcionesIniciarSolicitud.jsp" %>
						</c:when>
						<c:otherwise>
							<div class="alert alert-warning">
								Usted ya cuenta con una solicitud de <strong>${tipoTramiteCreado.descripcion}</strong> en proceso.
							</div>
		
							<%@ include file="../general/mensajeOpcionesRetomarSolicitud.jsp" %>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${otroTipoTramite}">
					<div class="alert alert-warning">
								El integrante al que se le quiere aplicar el tr&aacute;mite de <strong>${descripcionTipoSolicitud}</strong>
								ya cuenta con una solicitud de <strong>${tipoTramiteCreado.descripcion}</strong> en proceso. Es necesario
								concluir la solicitud activa para poder aplicar otro tr&aacute;mite.
					</div>
				</c:if>
			</c:if>
			<c:if test="${not empty error }">
				<div class="alert alert-danger">
					<strong>Error</strong>: ${error}
				</div>
			</c:if>
		</div>
	</div>

	<div class="pie">
		<div class="controles"></div>
	</div>
</div>


<!--  -->

<!-- Forma para invocar el retomar o cancelar una solicitud -->
<form:form action="" modelAttribute="solicitudForm" id="solicitudForm"
	method="post">
	<form:hidden path="solicitudId" id="idSolicitudPendiente" />
	<form:hidden path="noFolioSolicitud" id="noFolioSolicitud" />
</form:form>
<!--  -->

<!-- Divs para dialogos de mensajes -->
<div id="dialog-confirm-cancelar"
	title="Confirmar cancelaci&oacute;n de solicitud">
<p><span class="ui-icon ui-icon-alert"
	style="float: left; margin: 0 7px 20px 0;"></span> ¿Desea cancelar la
solicitud pendiente con folio: <strong>${solicitudForm.noFolioSolicitud}</strong>?
</p>
</div>

<div id="dialog-confirm" title="Mensaje confirmaci&oacute;n">
<p><span class="ui-icon ui-icon-alert"
	style="float: left; margin: 0 7px 20px 0;"></span> <label
	id="mensajeDialogo"></label></p>
</div>
<!--  -->


