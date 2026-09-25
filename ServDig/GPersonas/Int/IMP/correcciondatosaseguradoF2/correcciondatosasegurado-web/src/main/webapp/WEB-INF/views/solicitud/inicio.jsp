<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript">
  history.go(1);
  tipoTramite = '${tramite}';
  curp = '${curp}';
</script>

<div class="contenedor">

	<!-- Muesta los mensajes de error, este error es cuando encontro mas de un error -->
	<c:if test="${errorDatosExistentes != null }">
		<div style="width: 500px;" align="center">
			<div class="ui-widget">
				<div style="margin-top: 20px; padding: 0 .7em;" class="ui-state-highlight ui-corner-all">
					<p>
						<span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-info"></span>
						${mensaje}
					</p>
				</div>
			</div>
		</div>
	</c:if>
	
	<jsp:include page="encabezado.jsp">
		<jsp:param name="paso" value="1" />
	</jsp:include>
	
		<div>
			<spring:message code="label.solicitud.instrucciones.tener"/>:<br>
			<ul>
				<li><spring:message code="label.solicitud.curp"/></li>
				<li><spring:message code="label.solicitud.nss"/></li>
				<li><spring:message code="label.solicitud.correoPersonal"/></li>
			</ul>
			
		</div>
		<div><spring:message code="label.solicitud.instrucciones.ingresa"/></div>

		<br>
		<%--incluimos el jsp de login y seteamos los parametros, no se usa jsp:include com params porque pinta el contenido tal cual y no lo interpreta --%>
		<c:set var="rutaController" value="${contextpath}/wizard/correccionDatosAsegurado/consultar"/>
		<%@ include file="../common/login.jsp"%>
		<br>
		<div>
		<h6><spring:message code="label.solicitud.leyenda.ingresa"/></h6></div>
</div>