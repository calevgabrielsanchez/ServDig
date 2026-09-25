<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/mod-40/comunes/wizardCVROalta.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<script type="text/javascript">
   $(function(){
	  var email = parent.$('#correo').val();
	  $('#correoSolicitante').val(email);
	  
	  var nombre = parent.$('#nombre').val();
	  $('#nombreSolicitante').val(nombre);
   });
</script>
<div class="contenedor col-sm-12">
	<c:choose>
		<c:when test="${empty error}">
			<div class="contenido row">
				<div class="introduccion col-sm-4">
					<div class="titulo">
						<span><spring:message code="label.wizard.titulo.altaSeguro.mod40"/></span>
						<hr class="red m-b-md">
					</div>
					<div class="descripcion">
						<p>
							Para el asegurado que fue dado de baja en el r&eacute;gimen obligatorio por un patr&oacute;n y 
							desea continuar cotizando en los seguros de invalidez y vida, 
							as&iacute; como retiro, cesant&iacute;a en edad avanzada y vejez.
						</p>
						
					</div>
					<div class="opciones">
						<button onclick="uid_call('imss.gestion.seguro.voluntario.mod40.alta.btn_iniciarSolicitud','clickin');"
							class="btn btn-primary btn-block" id="btnIniciarSolicitudAlta">
							<span>Iniciar tr&aacute;mite</span>
						</button>
						<button onclick="uid_call('imss.gestion.seguro.voluntario.mod40.alta.btn_cancelarSolicitud','clickout');"
							class="btn btn-default btn-block" id="btnCancelarSolicitudAlta">
							<span>Cancelar</span>
						</button>
					</div>
				</div>
				<div class="instrucciones col-sm-8">
					<%@ include file="../../comunes/mensajeOpcionesIniciarSolicitudCVRO.jsp" %>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="contenido row">
				<div class="alert alert-danger">
					<span>${error}</span>
				</div>
			</div>
			<div class="pie row">
				<div class="opciones col-sm-6">
				</div>
				<div class="controles col-sm-6">
					<div class="pull-right">
						<a id="btnCancelarSolicitudAlta" class="btn btn-default" 
						onclick="uid_call('imss.gestion.seguro.voluntario.mod40.alta.btn_salir','clickout');">Salir</a>
					</div>
				</div>
			</div>
		</c:otherwise>
	</c:choose>
</div>
<%--<form:form id="capturarDatosSolicitudAltaForm" action="${contextPath}/wizard/seguroDomestico/comunes/solicitarTipoPago"--%>
<form:form id="capturarDatosSolicitudAltaForm" action="${contextPath}/wizard/continuacionVoluntaria/comunes/agregarDomicilio"
	method="post">
		<input name="idPersona" type="hidden" value="${solicitante.idPersona}"/>
		<input id="correoSolicitante" name="correoSolicitante" type="hidden" value=""/>
		<input id="nombreSolicitante" name="nombreSolicitante" type="hidden" value=""/>
</form:form>
