<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/doctosProbatorios/administrar/initAdmonDoctosProbatorios.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<style>
	table.table {
	    font-size: 15px !important;
	}
</style>

<div class="col-sm-12">
	<div class=" contenedor">
		<table id="tblAdmonDoctosProbatorios" class="table table-striped">
			<thead>
				<tr>
					<th>Descripci&oacute;n corta</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="doctoProbatorio" items="${doctosProbatorios}"
					varStatus="indice">
					<tr>
						<td>
							<address>
								<strong>${doctoProbatorio.documentoPorTipo.documento.desDocumento}</strong><br>
								<c:choose>
									<c:when test="${doctoProbatorio.documentoPorTipo.documento.cveIdDocumento ==  1 }">
										A&ntilde;o de Registro: ${doctoProbatorio.anio}<br>
										Entidad Federativa de Registro: ${doctoProbatorio.municipio.entidadFederativa.nombre}
									</c:when>
									<c:when test="${doctoProbatorio.documentoPorTipo.documento.cveIdDocumento ==  28 }">
										N&uacute;mero del Registro Nacional de Extranjeros: ${doctoProbatorio.numFolioExtranjero }<br>
										N&uacute;mero de Expediente del Documento Migratorio: ${doctoProbatorio.noActa }
									</c:when>
									<c:when test="${doctoProbatorio.documentoPorTipo.documento.cveIdDocumento ==  27 }">
										A&ntilde;o de Registro: ${doctoProbatorio.anioRegistro }<br>
										Folio de la Carta: ${doctoProbatorio.numFolioExtranjero }
									</c:when>
									<c:when test="${doctoProbatorio.documentoPorTipo.documento.cveIdDocumento == 47 }">
										N&uacute;mero de folio: ${doctoProbatorio.numFolioExtranjero }
									</c:when>
									<c:when test="${doctoProbatorio.documentoPorTipo.documento.cveIdDocumento == 48 }">
										A&ntilde;o de Registro: ${doctoProbatorio.anioRegistro }<br>
										Folio de la Carta: ${doctoProbatorio.numFolioExtranjero }
									</c:when>
									<c:when test="${doctoProbatorio.documentoPorTipo.documento.cveIdDocumento == 49 }">
										N&uacute;mero de Folio: ${doctoProbatorio.numFolioExtranjero }
									</c:when>
									<c:when test="${doctoProbatorio.documentoPorTipo.documento.cveIdDocumento == 50 }">
										N&uacute;mero de Folio: ${doctoProbatorio.numFolioExtranjero }
									</c:when>
								</c:choose>
							</address>
						</td>
						<td>
							<c:choose>
								<c:when test="${doctoProbatorio.estadoAdministracionDocto.clave == 1}">
									<label style="float: left">POR SER AGREGADO</label>
								</c:when>
								<c:when test="${doctoProbatorio.estadoAdministracionDocto.clave == 2}">
									<label style="float: left">POR SER ${doctoProbatorio.estadoAdministracionDocto}</label>
								</c:when>
								<c:when test="${doctoProbatorio.estadoAdministracionDocto.clave == 3  }">
									<label style="float: left">POR SER ${doctoProbatorio.estadoAdministracionDocto}</label>
								</c:when>
							</c:choose>
						</td>
						<td style="vertical-align: top; text-align: right;">
							<c:if test="${doctoProbatorio.estadoAdministracionDocto.clave != 3  }">
								<div role="toolbar" class="btn-toolbar-medio">
									<div class="btn-group btn-group-sm">
										<button aria-label="MODIFICAR" class="btn btn-default" type="button"
											onclick="modificarDoctoProbatorio(${indice.index})">
											<span aria-hidden="true" class="glyphicon glyphicon-pencil"></span>
										</button>
										<button aria-label="ELIMINAR" class="btn btn-default" type="button"
											onclick="eliminarDoctoProbatorio(${indice.index})">
											<span aria-hidden="true" class="glyphicon glyphicon-trash"></span>
										</button>
									</div>
								</div>
							</c:if>
							<c:if test="${doctoProbatorio.estadoAdministracionDocto.clave == 3  }">
								<div role="toolbar" class="btn-toolbar-medio">
									<div class="btn-group btn-group-sm">
										<button aria-label="DESHACER" class="btn btn-default" type="button"
											onclick="deshacerEliminar(${indice.index})">
											<span aria-hidden="true" class="fa fa-undo"></span>
										</button>
									</div>
								</div>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

<div id="dialog-confirm-DoctoProbatorio"
	title="Confirmar eliminaci&oacute;n del documento probatorio">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>¿Desea eliminar el
		documento probatorio?
	</p>
</div>

<!-- FORMA PARA ELIMINAR UN DOCTO PROBATORIO -->
<form
	action="${contextpath}/documentos/probatorios/administrar/eliminar"
	id="deleteDoctoProbatorioForm"></form>