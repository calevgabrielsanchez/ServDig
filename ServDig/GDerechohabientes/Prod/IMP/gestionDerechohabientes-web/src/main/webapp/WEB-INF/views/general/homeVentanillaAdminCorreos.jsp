<%@ include file="../general/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="/WEB-INF/tag/combo.tld"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum"%>

<meta http-equiv="expires" content="-1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/validations.js" htmlEscape="true" />"></script>
<script type="text/javascript"	src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>	
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/login/login.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/solicitudActualizacionCorreo.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/actualizaCorreo.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.maskedinput.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="/portal-web/static/resources/js/wizard/general/solicitud/detalleSolicitudCorreo.js"></script>

<c:set var="estadoAtendida" value="<%=EstadoSolicitudEnum.ATENDIDA.getDescripcion()%>" />
<c:set var="estadoEnProceso" value="<%=EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getDescripcion()%>" />
<c:set var="estadoCancelada" value="<%=EstadoSolicitudEnum.CANCELADA.getDescripcion()%>" />

<c:set var="idEstadoAtendida" value="<%=EstadoSolicitudEnum.ATENDIDA.getCodigo()%>" />
<c:set var="idEstadoEnProceso" value="<%=EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo()%>" />
<c:set var="idEstadoCancelada" value="<%=EstadoSolicitudEnum.CANCELADA.getCodigo()%>" />


<head>
	
	<link href="<c:url value="/resources/estilos/imss/estilo.css" />" rel="stylesheet"  type="text/css" />			
</head>

<div class="form-comment" align="center">
	<br>
	<br>
	<br>
	<fieldset>
	<c:set var="contextpath" value="<%=request.getContextPath()%>" />
	
	<div style="width: 90%; float: center;" align="justify">
			
			<h2 id="cabecero">Selecciona alg&uacute;n filtro de b&uacute;squeda</h2>
	
	</div>	
			
	<form:form modelAttribute="ActualizaCorreoIn" action="${contextpath}/inicioVentanilla/listar/ActualizaCorreo" method="post" id="filtrosBusquedaForm" >			
		<br>
		<center>		
		<table id="filtroTable">

			<tr>
				<td >
					<label class="control-label" for="fisica.nss" style="width: 150px">
					<spring:message code="label.nss" />:&nbsp;</label>
				</td>
				<td>
					<input id="nss" name="nss" class="entero_20" type="text" value=""> 
				</td>
			</tr>
			
			<tr>
				<td>
					<label class="control-label" for="solicitud.fecha" style="width: 150px">
						<spring:message code="label.fechaCita" />:&nbsp;
					</label>
				</td>
 				<td>
					<input type="text" id="fecha" name="fecha" value="" style="width: 250px"  readonly="readonly" onclick=" $( this ).datepicker();"/>
				</td> 
			</tr>

			<tr>
				<td><label class="control-label" for="fisica.entidad"
					style="width: 150px"> Estado:&nbsp; </label></td>
				<td><select id="estado" name="estado">
						<option value="" selected>-- SELECCIONE UNA OPCI&Oacute;N --</option>
						<option value="${idEstadoAtendida}">${estadoAtendida}</option>
						<option value="${idEstadoEnProceso}">${estadoEnProceso}</option>
						<option value="${idEstadoCancelada}">${estadoCancelada}</option>
				</select></td>
			</tr>

			<tr>
				<td >
					<label class="control-label" for="solicitud.folio" style="width: 150px">
					<spring:message code="label.folio" />:&nbsp;</label>
				</td>
				<td>
					<input id="folio" name="folio" class="entero_20" type="text" value="">  
				</td>
			</tr>
			
			<tr>
				
		  		<td align="center">
					<input type="button" id="regresar" class="mboton"  value="Regresar" 
					onclick="window.location.href = '${contextpath}/tramita'">
					&nbsp;
					<input type="button" id="aceptarVal" class="mboton"  value="Buscar">
					&nbsp;
					<input type="button" id="cleanVal" class="mboton"  value="Limpiar" onclick="limpiar()">
					&nbsp;
				</td>
				
	  		</tr>

		</table>
		</center>
		
	</form:form>
				
	</fieldset>
	
 	<fieldset style="width: 977px" class="titulo"><legend><strong>Solicitudes de actualizaci&oacute;n 
		de correo electr&oacute;nico</strong></legend>
			
			<table style="width: 100%" id="tablaSolicitudActualizacionCorreo">
			<caption><b>Solicitudes</b></caption>	
				
			</table>

					
	</fieldset>	
	<div id="contenedorHomeNormativoCE"></div>
	<div id="mensajes"></div>
</div>
<div id="detalleSolicitudComponent"></div>
