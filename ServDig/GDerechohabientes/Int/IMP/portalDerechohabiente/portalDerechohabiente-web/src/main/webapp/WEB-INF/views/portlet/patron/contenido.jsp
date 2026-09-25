<!-- JSP Contenido del Portlet de Representados Legales. -->
<%@ include file="../../general/taglibs.jsp"%>
<style>
	.subtitulo {
		color: #157164;
		font-size: small;
		font-weight: bold;
		line-height: 1em;
		margin-bottom: 0.5em;
		margin-top: 0;
	}
	
	.linkFolio {
		cursor: pointer;
		color: #0088CC;
	}
	
	.desc-campo {
		font-weight: bold !important;
	}
</style>

<c:choose>
	<c:when test="${empty error}">
		<table id="tblPatronesAseguradoResumen" style="width: 100%;" class="table table-striped table-bordered" cellpadding="0"
					cellspacing="0" border="0">
					<caption><strong>Patr&oacute;n del &uacute;ltimo movimiento del asegurado</strong></caption>
					<tbody>
						<tr>
							<td class="desc-campo">Registro patronal:</td>
							<td>
								<c:choose>
									<c:when test="${cabezaGrupoFamiliar.calidadParentesco.idParentesco == 6}">
										<c:if test="${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad != '00'}">
										${cabezaGrupoFamiliar.patronSujetoObligado.numeroRegistroPatronal}${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad}-${cabezaGrupoFamiliar.patronSujetoObligado.digVerificador}
										</c:if>
									</c:when>
									<c:otherwise>
										${cabezaGrupoFamiliar.patronSujetoObligado.numeroRegistroPatronal}${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad}-${cabezaGrupoFamiliar.patronSujetoObligado.digVerificador}
									</c:otherwise>
								</c:choose>
							</td>
							<td class="desc-campo">Fecha y tipo de &uacute;ltimo movimiento:</td>
							<td>
								<c:choose>
									<c:when test="${cabezaGrupoFamiliar.calidadParentesco.idParentesco == 6}">
										<c:if test="${cabezaGrupoFamiliar.patronSujetoObligado != null}">
											<c:if test="${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad != '00'}">
											<fmt:formatDate pattern="dd/MM/yyyy" value="${cabezaGrupoFamiliar.fechaUltimoMovAfiliacion}"/>  ${cabezaGrupoFamiliar.tipoMovtoAsegurado.desTipoMvtoAsegurado}
											</c:if>
										</c:if>
									</c:when>
									<c:otherwise>
										<fmt:formatDate pattern="dd/MM/yyyy" value="${cabezaGrupoFamiliar.fechaUltimoMovAfiliacion}"/>  ${cabezaGrupoFamiliar.tipoMovtoAsegurado.desTipoMvtoAsegurado}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td class="desc-campo">Modalidad:</td>
							<td colspan="3">
								<c:choose>
									<c:when test="${cabezaGrupoFamiliar.calidadParentesco.idParentesco == 6}">
										<c:if test="${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad != '00'}">
										${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad} - ${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.descripcion}
										</c:if>
									</c:when>
									<c:otherwise>
										${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad} - ${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.descripcion}
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td class="desc-campo">Nombre o raz&oacute;n social:</td>
							<td colspan="3">
								<c:choose>
									<c:when test="${cabezaGrupoFamiliar.calidadParentesco.idParentesco == 6}">
										<c:if test="${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad != '00'}">
											<c:if test="${not empty cabezaGrupoFamiliar.patronSujetoObligado.moral}">
											${cabezaGrupoFamiliar.patronSujetoObligado.moral.razonSocial} 
											${cabezaGrupoFamiliar.patronSujetoObligado.moral.tipoSociedad.descripcionAbreviada }
											</c:if>
											<c:if test="${not empty cabezaGrupoFamiliar.patronSujetoObligado.fisica}">
											${cabezaGrupoFamiliar.patronSujetoObligado.fisica.nombre} ${cabezaGrupoFamiliar.patronSujetoObligado.fisica.primerApellido} ${cabezaGrupoFamiliar.patronSujetoObligado.fisica.segundoApellido} 
											</c:if>
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if test="${not empty cabezaGrupoFamiliar.patronSujetoObligado.moral}">
											${cabezaGrupoFamiliar.patronSujetoObligado.moral.razonSocial} 
											${cabezaGrupoFamiliar.patronSujetoObligado.moral.tipoSociedad.descripcionAbreviada }
										</c:if>
										<c:if test="${not empty cabezaGrupoFamiliar.patronSujetoObligado.fisica}">
											${cabezaGrupoFamiliar.patronSujetoObligado.fisica.nombre} ${cabezaGrupoFamiliar.patronSujetoObligado.fisica.primerApellido} ${cabezaGrupoFamiliar.patronSujetoObligado.fisica.segundoApellido} 
										</c:if>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<c:if test="${not empty origenVentanilla}">
							<tr>
								<td class="desc-campo">Domicilio del centro de trabajo:</td>
								<td colspan="3">
									<c:choose>
										<c:when test="${cabezaGrupoFamiliar.calidadParentesco.idParentesco == 6}">
											<c:if test="${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad != '00'}">
											${cabezaGrupoFamiliar.patronSujetoObligado.cntroTrabajo.descripcion}
											</c:if>
										</c:when>
										<c:otherwise>
											${cabezaGrupoFamiliar.patronSujetoObligado.cntroTrabajo.descripcion}
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:if>
					</tbody>
		</table>
		<c:if test="${not empty patrones }">
			<div id="datosPatronesWrapper" style="width: 100%; margin: 0 auto;">
				<table id="tblPatronesAseguradoResumen" style="width: 100%;" class="table table-striped table-bordered" cellpadding="0"
					cellspacing="0" border="0">
					<caption><strong>Patrones relacionados</strong></caption>
					<tbody>
						<c:forEach items="${patrones}" var="patron">
									<tr>
										<td class="desc-campo">Registro patronal:</td>
										<td align="center">${patron.numeroRegistroPatronal}${patron.modalidad.numModalidad}-${patron.digVerificador}</td>
										<td class="desc-campo">Modalidad:</td>
										<td align="center">${patron.modalidad.numModalidad} - ${patron.modalidad.descripcion}</td>
									</tr>
									<tr>
										<td class="desc-campo">Nombre o raz&oacute;n social:</td>
										<td colspan="3">
											<c:if test="${not empty patron.moral}">
												${patron.moral.razonSocial} 
												${patron.moral.tipoSociedad.descripcionAbreviada }
											</c:if>
											<c:if test="${not empty patron.fisica}">
												${patron.fisica.nombre} ${patron.fisica.primerApellido} ${patron.fisica.segundoApellido} 
											</c:if>
										</td>
									</tr>
									<c:if test="${not empty origenVentanilla}">
										<tr>
											<td class="desc-campo">Domicilio del centro de trabajo:</td>
											<td colspan="3">${patron.cntroTrabajo.descripcion}</td>
										</tr>
									</c:if>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
	<c:if test="${not empty origenVentanilla}">
		<c:if test="${not empty ultimosMovimientosBaja }">
			<div id="datosPatronesWrapper" style="width: 100%; margin: 0 auto;">
				<table id="tblPatronesAseguradoResumen" style="width: 100%;" class="table table-striped table-bordered" cellpadding="0"
					cellspacing="0" border="0">
					<caption><strong>Hist&oacute;rico patronal</strong></caption>
					<tbody>
						<c:forEach items="${ultimosMovimientosBaja}" var="movimientos">
									<tr>
										<td class="desc-campo">Registro patronal:</td>
										<td align="center">${movimientos.sujetoObligado.numeroRegistroPatronal}${movimientos.sujetoObligado.modalidad.numModalidad}-${movimientos.sujetoObligado.digVerificador}</td>
										<td class="desc-campo">Modalidad:</td>
										<td align="center">${movimientos.sujetoObligado.modalidad.numModalidad} - ${movimientos.sujetoObligado.modalidad.descripcion}</td>
									</tr>
									<c:if test="${not empty origenVentanilla}">
										<tr>
											<td class="desc-campo">Domicilio del centro de trabajo:</td>
											<td colspan="3">${movimientos.sujetoObligado.cntroTrabajo.descripcion}</td>
										</tr>
									</c:if>
									
									<tr>
										<td class="desc-campo">Fecha alta:</td>
										<td><fmt:formatDate pattern="dd/MM/yyyy" value="${movimientos.periodoMovimientoAfiliatorio.fechaInicioMovimiento}"/> </td>
										<td class="desc-campo">Fecha baja:</td>
										<td><fmt:formatDate pattern="dd/MM/yyyy" value="${movimientos.periodoMovimientoAfiliatorio.fechaFinalMovimiento}"/> </td>
									</tr>
												
								
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
	</c:if>
	</c:when>
	<c:otherwise>
		Ocurri&oacute; un error al consultar a los patrones: ${error}
	</c:otherwise>
</c:choose>

<script id="initPortlet">
	/*
		Se comenta inicializaci�n del datatable, ya que no es necesario
		debido a la cantidad de informaci�n a mostar
		
		$('#tblPatronesAseguradoResumen').dataTable({
			"bDestroy": true,
			"bLengthChange": false,
			"sPaginationType": "bootstrap",
			"aoColumnDefs": [{"sSortDataType": "html", "sType": "html", "aTargets": [0]}]
		});
	*/
</script>