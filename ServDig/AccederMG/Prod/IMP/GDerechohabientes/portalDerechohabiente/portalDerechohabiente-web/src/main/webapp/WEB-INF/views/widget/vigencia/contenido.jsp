<!-- JSP Contenido del Widget de Persona Fisica. -->
<%@ include file="../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum"%>
<c:set var="estadoBaja" value="<%=EstadoDerechohabienteEnum.BAJA.getId()%>" scope="page"></c:set>
<c:set var="estadoConDerecho" value="<%=EstadoDerechohabienteEnum.CON_DERECHO.getId()%>" scope="page"></c:set>
<c:set var="estadoConservacion" value="<%=EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId()%>" scope="page"></c:set>
<c:set var="estadoFallecido" value="<%=EstadoDerechohabienteEnum.FALLECIDO.getId()%>" scope="page"></c:set>
<c:set var="estadoPension" value="<%=EstadoDerechohabienteEnum.PENSION_TRAMITE.getId()%>" scope="page"></c:set>
<c:set var="estadoVigente" value="<%=EstadoDerechohabienteEnum.VIGENTE.getId()%>" scope="page"></c:set>
<c:set var="estadoVigenteProrroga" value="<%=EstadoDerechohabienteEnum.VIGENTE_POR_PRORRGA.getId()%>" scope="page"></c:set>

<c:set var="staticResourcesPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_RESOURCES_PATH")%>' />
<c:set var="staticLogoutPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_LOGOUT_PATH")%>' />
<script type="text/javascript">
var infoDatosVigencia = '<p>En esta secci&oacute;n se muestra la <span style="font-style: italic;">informaci&oacute;n de la vigencia</span> del asegurado de la siguiente forma:</p>' +
	'<address>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span><strong>NSS</strong></span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Situaci&oacute;n</span><br>' +
	'</address>';
$('#idPopoverVigencia').popover({
	animation : true,
	html: true,
	content : infoDatosVigencia,
	trigger: 'hover',
	container : 'body'
});

</script>

	<div class="widget-section">
		<c:if test="${empty error }">
		
		
		<div style="float: right;">
			<a class="btn btn-sm icono-help" id="idPopoverVigencia" data-toggle="popover" title="Datos de vigencia"></a>
		</div>
		<address>
			<span><strong> NSS: </strong></span><br>
			<span> ${asegurado.asignacionNSS.nssStr} </span><br>
			
			<span><strong> Situaci&oacute;n: </strong></span><br>
				<c:choose>
					<c:when test="${asegurado.estadoDerechohabiente.idEstadoDerechohabiente eq estadoConservacion}">
						<c:set var="claseDescEstado" value="label label-warning" />
					</c:when>
					<c:when test="${asegurado.estadoDerechohabiente.idEstadoDerechohabiente eq estadoPension}">
						<c:set var="claseDescEstado" value="label label-info" />
					</c:when>
					<c:when test="${asegurado.estadoDerechohabiente.idEstadoDerechohabiente eq estadoVigente}">
						<c:set var="claseDescEstado" value="label label-success" />
					</c:when>
					<c:when test="${asegurado.estadoDerechohabiente.idEstadoDerechohabiente eq estadoVigenteProrroga}">
						<c:set var="claseDescEstado" value="label label-success" />
					</c:when>
					
					<c:when test="${asegurado.estadoDerechohabiente.idEstadoDerechohabiente eq estadoConDerecho}">
						<c:set var="claseDescEstado" value="label label-success" />
					</c:when>
					<c:when test="${asegurado.estadoDerechohabiente.idEstadoDerechohabiente eq estadoFallecido}">
						<c:set var="claseDescEstado" value="label label-danger" />
					</c:when>
					<c:when test="${asegurado.estadoDerechohabiente.idEstadoDerechohabiente eq estadoBaja}">
						<c:set var="claseDescEstado" value="label label-danger" />
					</c:when>
				</c:choose>
				<span class="${claseDescEstado}"> ${asegurado.estadoDerechohabiente.descripcion}</span><br>
				<c:choose>
					<c:when test="${isPensionadoMod17Convenio}">
						<c:if test="${asegurado.conDerechoSm == 'NO'}">
							<br>
							<span style="float: right"><strong>Sin derecho al servicio m&eacute;dico</strong></span><br>
						</c:if>
					</c:when>
					<c:otherwise>
						<c:if test="${!isMod17Convenio}">
							<c:if test="${asegurado.conDerechoSm == 'NO'}">
								<br>
								<span style="float: right"><strong>Sin derecho al servicio m&eacute;dico</strong></span><br>
							</c:if>
						</c:if>
					</c:otherwise>
				</c:choose>				
			<input type="hidden" id="hdnEstadoCabezaGrupoFamiliar" value="${asegurado.estadoDerechohabiente.idEstadoDerechohabiente}"/>
			<input type="hidden" id="hdnParentescoCabeza" value="${asegurado.parentesco.idParentesco}"/>
		</address>
		
		<div id="divNss" style="display: none;">
			<form id="asegurado" name="asegurado" method="post" action="/portal-web/portal/asegurado/ingresar">
				<input type="hidden" id="asignacionNSS.idPersona" name="asignacionNSS.idPersona" value="${asegurado.derechohabiente.idPersona}">
				<input type="hidden" id="asignacionNSS.idAsignacionNSS" name="asignacionNSS.idAsignacionNSS" value ="${asegurado.asignacionNSS.idAsignacionNSS}">
				<input type="hidden" id="asignacionNSS.nss" name="asignacionNSS.nss" value = "${asegurado.asignacionNSS.nssStr}">
				<input type="hidden" id="asignacionNSS.nombre" name="asignacionNSS.nombre" value = "${asegurado.derechohabiente.nombre}">
				<input type="hidden" id="asignacionNSS.primerApellido" name="asignacionNSS.primerApellido" value = "${asegurado.derechohabiente.primerApellido}">
				<input type="hidden" id="asignacionNSS.segundoApellido" name="asignacionNSS.segundoApellido" value = "${asegurado.derechohabiente.segundoApellido}">
				<input type="hidden" id="asignacionNSS.curp" name="asignacionNSS.curp" value = "${asegurado.derechohabiente.curp}">
				<input type="hidden" id="fechaInicioVigencia" name="fechaInicioVigencia" value = "<fmt:formatDate  pattern="dd/MM/yyyy"  value="${asegurado.fechaInicioVigencia}"/>">
				<input type="hidden" id="fechaFinVigencia" name="fechaFinVigencia" value = "<fmt:formatDate  pattern="dd/MM/yyyy"  value="${asegurado.fechaFinVigencia}"/>">
 				<input type="hidden" id="parentesco.idParentesco" name="parentesco.idParentesco" value = "${asegurado.parentesco.idParentesco}"/>
 				<input type="hidden" id="estadoDerechohabiente.idEstadoDerechohabiente" name="estadoDerechohabiente.idEstadoDerechohabiente" value = "${asegurado.estadoDerechohabiente.idEstadoDerechohabiente}"/>
 			</form>
		</div>
		</c:if>
		<c:if test="${not empty error}">
			${error}
		</c:if>
	</div>
	
	