<%@ include file="taglibs.jsp"%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description"
	content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta lang="es">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<jsp:include page="staticResources.jsp"></jsp:include>

<script>
	var context_path = '<%=request.getContextPath()%>';
	var sessionId = '<%=request.getSession().getId()%>';
	
	$(document).ready(function(){
		$('form:not(.formNotBlock)').submit(function(){
			$.blockUI();
		});	
	});
</script>

<!-- JS del control de mensajes de exito  -->
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/controlmensaje/ctrlMensaje.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/generalPersonas.js" htmlEscape="true" />"></script>
</head>

<body>
	<div class="site_position_center">
		<c:set var="contextpath" value="<%=request.getContextPath()%>" />
		<div class="main_wrap">
			<div id="cuerpo_principal">
				<div id="encabezado">
					<tiles:insertAttribute name="encabezado" />
				</div>
				<div id="subencabezado">
					<tiles:insertAttribute name="subencabezado" />
				</div>
				<br>
				<div id="cuerpo">
					<tiles:insertAttribute name="contenido" />
				</div>
			</div>
		</div>
	</div>

	<div id="dialogoMensajeOperacionExitosa"
		title="Operaci&oacute;n exitosa.">
		<p>
			<span class="ui-icon ui-icon-circle-check"
				style="float: left; margin: 0 7px 50px 0;"></span> <b><strong>
					La operaci&oacute;n fue efectuada exitosamente.</strong></b>
		</p>
	</div>
</body>
</html>