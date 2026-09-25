<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<div class="contenedor">

	<!-- Muesta los mensajes de error, este error es cuando encontro mas de un error -->
	<c:if test="${errorDatosExistentes != null }">
		<div style="width: 500px;" align="center">
			<div class="ui-widget">
				<div style="margin-top: 20px; padding: 0 .7em;" class="ui-state-highlight ui-corner-all">
					<p>
						<span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-info"></span>
						${mensaje}.
					</p>
				</div>
			</div>
		</div>
	</c:if>
	
	<jsp:include page="encabezado.jsp">
		<jsp:param name="paso" value="1" />
	</jsp:include>
		
		<div>
			<spring:message code="label.tramite.asignacionNSS.instrucciones.tenermano"/>:<br>
			<ul>
				<li><spring:message code="label.curp"/></li>
				<li><spring:message code="label.tramite.instrucciones.correo"/></li>
			</ul>
			
		</div>
	<!-- Muesta mensaje al usuario informando la confirmacion via correo -->
	<c:if test="${exito != null }">
		<div  align="center">
			<div >
				<div class="alert alert-success">
					<p>
						<span style="float: left; margin-right: .3em;"></span>
						${exito}.
					</p>
				</div>
			</div>
		</div>
	</c:if>
		<br>
		<%--incluimos el jsp de login y seteamos los parametros, no se usa jsp:include com params porque pinta el contenido tal cual y no lo interpreta --%>
		<c:set var="mostrarNSS" value="false" />
		<c:set var="rutaController" value="${contextpath}/asignacionNSS/valida"/>
		<c:set var="clickContinuar" value="onclick=\"uid_call('imss.asegurados.asignacion_nss.inicio.btn_continuar','clickin')\""/>
		<c:set var="onclick" value="onclick=\"uid_call('imss.asegurados.asignacion_nss.inicio.consulta_curp','clickout')\""/>
		<%@ include file="../common/login.jsp"%>
	
		<jsp:include page="../common/pieTramites.jsp">
			<jsp:param name="tipoTramite" value="true" />
		</jsp:include>
		
</div>
<script language="JavaScript1.2" src="${staticResourcesPath}/js/comscore/Form.js"></script>
<!-- Cargar archivo de configuraci�n para el mensaje de advertencia -->
<script src="/src/main/webapp/WEB-INF/config/messages/messages.properties"></script>

