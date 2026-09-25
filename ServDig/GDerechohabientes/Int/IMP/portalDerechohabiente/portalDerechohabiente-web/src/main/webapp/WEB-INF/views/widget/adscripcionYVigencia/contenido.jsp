<!-- JSP Contenido del Widget de Persona Fisica. -->
<%@ include file="../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum"%>
<c:set var="estadoBaja" value="<%=EstadoDerechohabienteEnum.BAJA.getId()%>" scope="page"></c:set>
<c:set var="estadoConDerecho" value="<%=EstadoDerechohabienteEnum.CON_DERECHO.getId()%>" scope="page"></c:set>
<c:set var="estadoConservacion" value="<%=EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId()%>" scope="page"></c:set>
<c:set var="estadoFallecido" value="<%=EstadoDerechohabienteEnum.FALLECIDO.getId()%>" scope="page"></c:set>
<c:set var="estadoPension" value="<%=EstadoDerechohabienteEnum.PENSION_TRAMITE.getId()%>" scope="page"></c:set>
<c:set var="estadoVigente" value="<%=EstadoDerechohabienteEnum.VIGENTE.getId()%>" scope="page"></c:set>
<c:set var="estadoVigenteProrroga" value="<%=EstadoDerechohabienteEnum.VIGENTE_POR_PRORRGA.getId()%>" scope="page"></c:set>



<c:set var="padresId"><%=ParentescoEnum.PADRES.getId()%></c:set>
<c:set var="aseguradosId"><%=ParentescoEnum.ASEGURADO.getId()%></c:set>
<c:set var="pensionadosId"><%=ParentescoEnum.PENSIONADO.getId()%></c:set>
<c:set var="concubinasId"><%=ParentescoEnum.CONCUBINARIO.getId()%></c:set>
<c:set var="idParentesco">${derechohabiente.parentesco.idParentesco}</c:set>

<c:set var="staticResourcesPath"
	value='<%=request.getSession().getServletContext()
					.getInitParameter("STATIC_RESOURCES_PATH")%>' />
<c:set var="staticLogoutPath" value='<%=request.getSession().getServletContext().getInitParameter("STATIC_LOGOUT_PATH")%>' />
<script type="text/javascript">
var infoDatosVigencia = '<p>En esta secci&oacute;n se muestra la <span style="font-style: italic;">informaci&oacute;n de la vigencia</span> del asegurado de la siguiente forma:</p>' +
	'<address>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span><strong>NS</strong></span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Situaci&oacute;n</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Unidad Medica Familiar</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Consultorio</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Turno</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>M&eacute;dico</span><br>' +
	'<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Delegaci&oacute;n</span>' +
	<c:if test="${idParentesco ne padresId && idParentesco ne aseguradosId && idParentesco ne pensionadosId  && idParentesco ne concubinasId }">
	'<br><i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Servicios en circunscripci&oacute;n for&aacute;nea</span>' +	
	</c:if>
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
		<input type="hidden" id="idPersona" value='${derechohabiente.derechohabiente.idPersona}' />
		<input type="hidden" id="idAsignacionNssGrupoFamiliar" value='${derechohabiente.asignacionNSS.idAsignacionNSS}' />
		<input type="hidden" id="nssGrupoFamiliar" value='${derechohabiente.asignacionNSS.nssStr}' />
		<input type="hidden" id="curpPersona" value='${derechohabiente.derechohabiente.curp}' />
		
		
		<div style="float: right;">
			<a class="btn btn-sm icono-help" id="idPopoverVigencia" data-toggle="popover" title="Datos de adcsripcion y vigencia"></a>
		</div>
		<address>
		
			<span><strong> NSS: </strong></span>
			<span> ${derechohabiente.asignacionNSS.nssStr} </span><br>
			
			<span><strong> Situaci&oacute;n: </strong></span>
			<c:choose>
				<c:when test="${derechohabiente.estadoDerechohabiente.idEstadoDerechohabiente eq estadoConservacion}">
					<c:set var="claseDescEstado" value="label label-warning" />
				</c:when>
				<c:when test="${derechohabiente.estadoDerechohabiente.idEstadoDerechohabiente eq estadoPension}">
					<c:set var="claseDescEstado" value="label label-info" />
				</c:when>
				<c:when test="${derechohabiente.estadoDerechohabiente.idEstadoDerechohabiente eq estadoVigente}">
					<c:set var="claseDescEstado" value="label label-success" />
				</c:when>
				
				<c:when test="${derechohabiente.estadoDerechohabiente.idEstadoDerechohabiente eq estadoVigenteProrroga}">
						<c:set var="claseDescEstado" value="label label-success" />
				</c:when>
					
				<c:when test="${derechohabiente.estadoDerechohabiente.idEstadoDerechohabiente eq estadoConDerecho}">
					<c:set var="claseDescEstado" value="label label-success" />
				</c:when>
				
				<c:when test="${derechohabiente.estadoDerechohabiente.idEstadoDerechohabiente eq estadoFallecido}">
					<c:set var="claseDescEstado" value="label label-danger" />
				</c:when>
				<c:when test="${derechohabiente.estadoDerechohabiente.idEstadoDerechohabiente eq estadoBaja}">
					<c:set var="claseDescEstado" value="label label-danger" />
				</c:when>
			</c:choose>
			<span class="${claseDescEstado}"> ${derechohabiente.estadoDerechohabiente.descripcion}</span><br>
			
			<span><strong> UMF: </strong></span>
			<span> ${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.nombreCorto} </span><br>
			
			<span><strong> Consultorio: </strong></span>
			<span> ${derechohabiente.medicoEnTurno.consultorio.descripcion} </span><br>
			
			<span><strong> Turno: </strong></span>
			<span> ${derechohabiente.medicoEnTurno.turno.descripcion} </span><br>
			
			<span><strong> M&eacute;dico: </strong></span>
			<span> ${derechohabiente.medicoEnTurno.medicoFamiliar.nombre} ${derechohabiente.medicoEnTurno.medicoFamiliar.primerApellido} ${derechohabiente.medicoEnTurno.medicoFamiliar.segundoApellido}</span><br>
		
			<span><strong> Delegaci&oacute;n: </strong></span>
			<span> ${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion} </span><br>
			
			<c:if test="${idParentesco ne padresId &&
			    idParentesco ne aseguradosId &&  
			    idParentesco ne pensionadosId  &&
			    idParentesco ne concubinasId }">
				<span><strong> Servicios en circunscripci&oacute;n for&aacute;nea: </strong></span>
				<span> ${derechohabiente.circunscripcionForaneaActiva ? 'SI' : 'NO'} </span><br>
			</c:if>
			
			<c:choose>
				<c:when test="${isPensionadoMod17Convenio}">
					<c:if test="${derechohabiente.conDerechoSm == 'NO'}">
						<br>
						<span style="float: right"><strong>Sin derecho al servicio m&eacute;dico</strong></span><br>
					</c:if>
				</c:when>
				<c:otherwise>
					<c:if test="${!isMod17Convenio}">
						<c:if test="${derechohabiente.conDerechoSm == 'NO'}">
							<br>
							<span style="float: right"><strong>Sin derecho al servicio m&eacute;dico</strong></span><br>
						</c:if>
					</c:if>
				</c:otherwise>
			</c:choose>				
			
		</address>
		</c:if>
		<c:if test="${not empty error}">
			${error}
		</c:if>
	</div>