<!-- JSP Contenido del Widget de Datos Basicos del Centro de Trabajo. -->
<%@ include file="../../../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum"%>

<c:set var="estadoBaja" value="<%=EstadoDerechohabienteEnum.BAJA.getId()%>" scope="page"></c:set>
<c:set var="estadoConDerecho" value="<%=EstadoDerechohabienteEnum.CON_DERECHO.getId()%>" scope="page"></c:set>
<c:set var="estadoConservacion" value="<%=EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId()%>" scope="page"></c:set>
<c:set var="estadoFallecido" value="<%=EstadoDerechohabienteEnum.FALLECIDO.getId()%>" scope="page"></c:set>
<c:set var="estadoPension" value="<%=EstadoDerechohabienteEnum.PENSION_TRAMITE.getId()%>" scope="page"></c:set>
<c:set var="estadoVigente" value="<%=EstadoDerechohabienteEnum.VIGENTE.getId()%>" scope="page"></c:set>


<c:set var="padresId"><%=ParentescoEnum.PADRES.getId()%></c:set>
<c:set var="aseguradosId"><%=ParentescoEnum.ASEGURADO.getId()%></c:set>
<c:set var="pensionadosId"><%=ParentescoEnum.PENSIONADO.getId()%></c:set>
<c:set var="concubinasId"><%=ParentescoEnum.CONCUBINARIO.getId()%></c:set>
<c:set var="idParentesco">${derechohabiente.parentesco.idParentesco}</c:set>


<div class="well">
	<c:if test="${empty error }">
		<address>
			<span><strong> NSS: </strong></span> 
			<span> ${derechohabiente.asignacionNSS.nssStr} </span>
			<br>
			<span><strong>Situaci&oacute;n: </strong></span>
			
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
			
			<span class="${claseDescEstado}"> ${derechohabiente.estadoDerechohabiente.descripcion}</span>
			<br>
			<span><strong>UMF: </strong></span> <span> ${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.nombreCorto} </span>
			<br>
			<span><strong>Consultorio: </strong></span> <span> ${derechohabiente.medicoEnTurno.consultorio.descripcion} </span>
			<br>
			<span><strong>Turno: </strong></span> <span> ${derechohabiente.medicoEnTurno.turno.descripcion} </span>
			<br>
			<span><strong>M&eacute;dico: </strong></span>
			<span> 
				${derechohabiente.medicoEnTurno.medicoFamiliar.nombre}
				${derechohabiente.medicoEnTurno.medicoFamiliar.primerApellido}
				${derechohabiente.medicoEnTurno.medicoFamiliar.segundoApellido}
			</span>
			<br>
			<span><strong>Delegaci&oacute;n: </strong></span> 
			<span>${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion} </span>
			<br>
			<c:if test="${idParentesco ne padresId && idParentesco ne aseguradosId &&  
			    	idParentesco ne pensionadosId  && idParentesco ne concubinasId }">
				<span><strong> Servicios en circunscripci&oacute;n for&aacute;nea: </strong></span>
				<span> ${derechohabiente.circunscripcionForaneaActiva ? 'SI' : 'NO'} </span>
				<br>
			</c:if>
		</address>
	</c:if>
	<c:if test="${not empty error}">
		<div class="alert alert-info" style="margin-bottom: 0px;">${error}</div>
	</c:if>
</div>