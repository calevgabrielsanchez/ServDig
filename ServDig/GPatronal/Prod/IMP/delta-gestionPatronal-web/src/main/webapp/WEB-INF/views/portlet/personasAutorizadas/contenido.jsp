<!-- JSP Contenido del Portlet de Representados Legales. -->
<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona"%>

<c:choose>
	<c:when test="${not empty personasAutorizadas }">
		<div id="representadosWrapper" style="width: 100%; margin: 0 auto;">
			<table id="tblPersonasAutorizadasResumen" style="width: 100%;"
				class="table table-striped table-bordered" cellpadding="0"
				cellspacing="0" border="0">
				<thead>
					<tr>
						<th>RFC</th>
						<th>Nombre</th>
						<th>Registro Patronal</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${personasAutorizadas}" var="personaAutorizada"
						varStatus="indice">
						<tr>
							<td>${personaAutorizada.fisica.rfc}</td>
							<td>${personaAutorizada.fisica.nombre }
								${personaAutorizada.fisica.primerApellido }
								${personaAutorizada.fisica.segundoApellido }</td>
							<td>
								${personaAutorizada.sujetoObligado.numeroRegistroPatronal}
								${personaAutorizada.sujetoObligado.modalidad.numModalidad}
								${personaAutorizada.sujetoObligado.digVerificador}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:when>
	<c:otherwise>
	
		<div class="container-fluid empty-state">
			<div class="row">
				<!-- Imagen -->
				<div class="col-xs-12 imagen">
					<i class="glyphicon glyphicon-exclamation-sign"></i>
				</div>
			</div>

			<div class="row">
				<!-- Titulo -->
				<div class="col-xs-12 titulo">
					<spring:message code="label.portlet.sin.resultados.personasAutorizadas" />
				</div>
			</div>
		</div>
		
	</c:otherwise>
</c:choose>


<form id="formRepresentado" action="#" method="post">
	<input id="hdnIdRepresentado" type="hidden" value="" name="idPersona">
</form>

<script id="initPortlet">
	$('#tblPersonasAutorizadasResumen').dataTable({
		"bDestroy" : true,
		"bLengthChange" : false,
		"sPaginationType" : "bootstrap"
	});
</script>