<%@ include file="../general/taglibs.jsp"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="max-age=100, must-revalidate" />
<meta name="description" content="Instituto Mexicano del Seguro Social Portal DELTA" />
<meta lang="es">
<title><tiles:insertAttribute name="title" ignore="true" /></title>

<link href="<spring:url value='/static/resources/js/delta/main.css'/>" rel="stylesheet">
<script type="text/javascript" src="<spring:url value='/static/resources/js/lib/jquery/1.11.3/jquery.min.js'/>"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/lib/jquery/1.11.1/jquery.validate.js'/>"></script>
<%-- <script type="text/javascript" src="<spring:url value='/static/resources/js/jquery/validation/validator/jquery.validate.js'/>"></script>--%>
<script type="text/javascript" src="<spring:url value='/static/resources/js/main.js'/>"></script>
<script>
	var context_path = '<%= request.getContextPath()%>';	
</script>	
</head>
<body>
  <main class="page">
    <div id="modals"></div>
	    <div id="sessionControl">
	    	<input name="fechaSistema" type="hidden" id="fechaSistema"  value="${info_sesion.fechaSistema}"/>
			<input name="fechaAvisoSession" type="hidden" id="fechaAvisoSession"  value="${info_sesion.fechaAvisoSession}"/>
			<input name="fechaFinSession" type="hidden" id="fechaFinSession"  value="${info_sesion.fechaFinSession}"/>
			<input name="intervaloValidacionSession"  type="hidden" id="intervaloValidacionSession" value ="10000"/>
			<input name="validaAviso"  type="hidden" id="validaAviso" value ="${info_sesion.validaAvisoSession}"/>
			<input name="refreshCtx"  type="hidden" id="refreshCtx" value ="${info_sesion.refreshCtx}"/>
	    </div>
	    <div id="dialog-Aviso-Session"
				title="Cierre de Sesi&oacute;n">
				<p>
					<span class="ui-icon ui-icon-alert"
						style="float: left; margin: 0 7px 20px 0;"></span>
					<label id="mensajeDialogoSession"></label>
				</p>
		</div>
    <div class="container">
      <ol class="breadcrumb">
        <li><a href="#"><i class="icon icon-home"></i></a></li>
        <li><a href="#">Inicio</a></li>
        <li class="active">CDA</li>
      </ol>
      <div id="workingArea"></div>
		</div>
    <tiles:insertAttribute name="contenido" />
  </main>
  <form name="auxForm" target="_blank"><input type="hidden" name="cdainfo" value="TramiteCDA" /></form>
  <script type="text/javascript" src="<spring:url value='/static/resources/js/delta/gobmx.js'/>"></script>
  <script type="text/javascript" src="<spring:url value='/static/resources/js/openam-session.js'/>"></script>
  <%-- <script type="text/javascript" src="<spring:url value='/static/resources/js/jquery/jquery.js'/>"></script> --%>
  <script type="text/javascript" src="<spring:url value='/static/resources/js/lib/jquery/1.11.3/jquery.min.js'/>"></script>
  <script type="text/javascript" src="<spring:url value='/static/resources/js/jquery/jquery-ui.js'/>"></script>
  <script type="text/javascript" src="<spring:url value='/static/resources/js/jquery/jquery.ui.datepicker-es.js'/>"></script>
  <script type="text/javascript" src="<spring:url value='/static/resources/js/jquery/jquery-migrate-1.0.0.js'/>"></script> 
  
</body>
</html>