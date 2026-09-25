<!-- JSP Contenido del Portlet de Clasificacion. -->
<%@ include file="../../../general/taglibs.jsp"%>

<c:set var="objClasificacion" value="${sujetoObligado.clasificacion}" />
<c:set var="fraccion" value="${objClasificacion.fraccion}" />

<input type="hidden" id="hdnCveIdSujetoObligado"
	value="${sujetoObligado.cveIdSujetoObligado}" />
<input type="hidden" id="hdnNumeroRegistroPatronal"
	value="${sujetoObligado.numeroRegistroPatronal}${sujetoObligado.modalidad.numModalidad}${sujetoObligado.digVerificador}" />

<c:choose>
	<c:when test="${objClasificacion != null}">
		<div id="clasificacionWrapper" style="width: 100%; margin: 0 auto;">
			<table id="tblClasificacionDetalle" style="width: 100%;"
				class="table table-bordered table-striped" cellpadding="0"
				cellspacing="0" border="0">
				<thead>
					<tr>
						<th>Giro</th>
						<th>Clave Fracci&oacute;n</th>
						<th>Divisi&oacute;n</th>
						<th>Grupo</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${objClasificacion.giro}</td>
						<td>${fraccion.grupo.division.numDivision}${fraccion.grupo.numGrupo}${fraccion.numFraccion}</td>
						<td>${fraccion.grupo.division.descripcion}</td>
						<td>${fraccion.grupo.descripcion}</td>
					</tr>
				</tbody>
			</table>
			<br />

			<table id="tblClasificacionFraccion" style="width: 100%;"
				class="table table-bordered table-striped" cellpadding="0"
				cellspacing="0" border="0">
				<thead>
					<tr>
						<th>Descripci&oacute;n de la Fracci&oacute;n</th>
						<th>Clase</th>
						<th>Prima SRT</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><strong>${fraccion.descripcion}</strong><br /> <span
							class="no-data">${fraccion.descripcionDetallada}</span></td>
						<td>${fraccion.clase.descripcion}</td>
						<td>${objClasificacion.primaSRTActual}</td>
					</tr>
				</tbody>


			</table>

			<br />
			<div>

				<div class="alert alert-warning alert-block">

					<span style="font-weight: bold;">Importante:</span> La
					informaci&oacute;n aqu&iacute; contenida s&oacute;lo es de
					car&aacute;cter informativo, no es oficial, por lo tanto no
					ser&aacute; v&aacute;lida para ser utilizada en alg&uacute;n tipo
					de proceso legal
				</div>

			</div>

		</div>
	</c:when>
	<c:otherwise>
		<span><spring:message
				code="label.portlet.sin.resultados.patrones.clasificacion" /></span>
	</c:otherwise>
</c:choose>

<script id="initPortlet">
		
</script>