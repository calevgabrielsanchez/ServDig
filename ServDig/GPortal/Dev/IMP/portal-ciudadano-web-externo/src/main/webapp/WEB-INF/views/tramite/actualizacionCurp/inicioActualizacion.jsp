<%@ include file="../../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript">
  tipoTramite = '${tramite}';

	$(document).ready(function() {
		$("#regresarPantallaInicial").click(redireccionPaginaPrincipal);
	});

	function redireccionPaginaPrincipal() {
		location.href = "/portal-ciudadano-web-externo/home/testTramites";
	}
</script>

<div>

			

			<!-- Forma de la consulta de personas por datos basicos. -->
			<div>
				<div class="row">
					<h2>
						<spring:message code="asegurado.tramite.correccion.title" />
					</h2>
					<hr class="red">
				</div>
				<jsp:include page="../../common/paginadorGenerico.jsp">
					<jsp:param name="pasos" value="1,2,3" />
					<jsp:param name="activo" value="1" />
					<jsp:param name="mensaje" value="Iniciar" />
				</jsp:include>
				<!-- Muesta los mensajes de error, este error es cuando encontro mas de un error -->
				<c:if test="${not empty fisica.errorFormGeneral}">
				<div class="row">
					<div class="alert alert-danger">
						${fisica.errorFormGeneral}
					</div>
				</div>
				</c:if>
				<div class="row m-t-md">
					<div class="col-sm-12">
						<h4>PASO 1: Iniciar</h4>
					</div>
				</div>
				<div>
					Tener a la mano:<br>
					<ul>
						<li>N&uacute;mero de seguridad social (NSS)</li>
						<li>CURP</li>
						<li>Correo el&eacute;ctronico v&aacute;lido, el cual ser&aacute; asociado a tu CURP</li>
					</ul>
					
				</div>
				<div>Favor de ingresar los siguientes datos:</div>

				<br>
				
				<%--incluimos el jsp de login y seteamos los parametros, no se usa jsp:include com params porque pinta el contenido tal cual y no lo 
				interpreta --%>
				<c:set var="mostrarNSS" value="${keyFormConNSS}" />
				<c:set var="rutaController" value="${contextpath}/asegurados/tramite/actualizacion/validar"/>
				<%@ include file="../../common/login.jsp"%>
				
				
				
				<jsp:include page="../../common/pieUmf.jsp">
					<jsp:param name="tipoTramite" value="true" />
				</jsp:include>
			</div>
</div>