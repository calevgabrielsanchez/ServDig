<!-- JSP Contenido del Widget de Persona Fisica. -->
<%@ include file="../../../general/taglibs.jsp"%>
<c:set var="staticResourcesPath"
	value='<%=request.getSession().getServletContext()
					.getInitParameter("STATIC_RESOURCES_PATH")%>' />
<c:set var="staticLogoutPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_LOGOUT_PATH")%>' />
<script type="text/javascript">
var infoDatosVigencia = '<p>En esta secci&oacute;n se muestra la <span style="font-style: italic;">informaci&oacute;n de la vigencia</span> del asegurado de la siguiente forma:</p>' +
	'<address>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span><strong>Nss</strong></span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Situaci&oacute;n</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Inicio de vigencia</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Fin de vigencia</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Unidad Medica Familiar</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Consultorio</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Turno</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>M&eacute;dico</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Delegaci&oacute;n</span>' +
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
		<input type="hidden" id="idPersona" value='${asegurado.derechohabiente.idPersona}' />
		<input type="hidden" id="idAsignacionNss" value='${asegurado.asignacionNSS.idAsignacionNSS}' />
		<input type="hidden" id="curpPersona" value='${asegurado.derechohabiente.curp}' />
		
		
		<div style="float: right;">
			<a class="btn btn-sm icono-help" id="idPopoverVigencia" data-toggle="popover" title="Datos de Vigencia"></a>
		</div>
		<address>
		
			<span><strong> NSS: </strong></span><br>
			<span> ${asegurado.asignacionNSS.nssStr} </span><br>
			
			<span><strong> Situaci&oacute;n: </strong></span><br>
			<span> ${asegurado.estadoDerechohabiente.descripcion} </span><br>
			<c:if test="${(asegurado.subEstadoDerechohabiente.idSubEstadoDerechohabiente !=1) &&
			 (asegurado.subEstadoDerechohabiente.idSubEstadoDerechohabiente !=3 )}">
			 
				<span><strong> Detalle situaci&oacute;n</strong></span><br>
				<span> ${asegurado.subEstadoDerechohabiente.descripcion}</span><br>
			</c:if>
			
			<span><strong> Inicio Vigencia: </strong></span><br>
			<span> <fmt:formatDate  pattern="dd/MM/yyyy"  value="${asegurado.fechaInicioVigencia}"/></span><br>
			<span><strong> Fin Vigencia: </strong></span><br>
			<span> <fmt:formatDate  pattern="dd/MM/yyyy"  value="${asegurado.fechaFinVigencia}"/></span><br>
			
			<span><strong> UMF: </strong></span><br>
			<span> ${asegurado.medicoEnTurno.unidadMedicaFamiliar.nombreCorto} </span><br>
			
			<span><strong> Consultorio: </strong></span><br>
			<span> ${asegurado.medicoEnTurno.consultorio.descripcion} </span><br>
			
			<span><strong> Turno: </strong></span><br>
			<span> ${asegurado.medicoEnTurno.turno.descripcion} </span><br>
			
			<span><strong> M&eacute;dico: </strong></span><br>
			<span> ${asegurado.medicoEnTurno.medicoFamiliar.nombre} ${asegurado.medicoEnTurno.medicoFamiliar.primerApellido} ${asegurado.medicoEnTurno.medicoFamiliar.segundoApellido}</span><br>
		
			<span><strong> Delegaci&oacute;n: </strong></span><br>
			<span> ${asegurado.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion} </span><br>
		</address>
		</c:if>
		<c:if test="${not empty error}">
			${error}
		</c:if>
	</div>