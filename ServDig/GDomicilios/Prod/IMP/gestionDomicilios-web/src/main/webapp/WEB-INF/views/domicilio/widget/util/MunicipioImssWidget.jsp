<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/domicilios/widget/MunicipioImssWidget.js" htmlEscape="true" />"></script>

<div class="cuerpo">
	<div class="descripcion">
	</div>
	<div class="contenido" style="width: 100%;">
		<form name="form" id="form">
			<c:choose>
				<c:when test="${not empty msgError }">
					<span class="error">${msgError }</span>
				</c:when>
				<c:otherwise>
					<table id="tblUMFs" class="table table-striped table-bordered" cellpadding="0" cellspacing="0" border="0" style="width: 100%;">
						<thead>
							<tr>
								<th style="width: 10% !important;"></th>
								<th style="text-align: center;">Subdelegaci&oacute;n</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${not empty lstMunicipioImss }">
									<c:forEach items="${lstMunicipioImss}" var="municipioImss" varStatus="indice">
										<tr>
											<td style="text-align: center; vertical-align: middle;">
												<input type="radio" name="idMunicipioImssRadio" value="${municipioImss.idMunicipio}" style="width: 100%;" id="idMunicipioImssRadio"
														onclick="javascript:ejecutarCallback();">
											</td>
											<td>
												<address>
													<i class="glyphicon glyphicon-tag" style="margin-right: 15px;"></i><strong>Delegaci&oacute;n :</strong> ${municipioImss.subdelegacion.delegacion.clave} ${municipioImss.subdelegacion.delegacion.descripcion} <br>
													<i class="glyphicon glyphicon-tag" style="margin-right: 15px;"></i><strong>Subdelegaci&oacute;n :</strong> ${municipioImss.subdelegacion.clave} ${municipioImss.subdelegacion.descripcion} <br>
													<i class="glyphicon glyphicon-globe" style="margin-right: 15px;"></i><strong>Municipio :</strong> ${municipioImss.cvecMunicipioSINDO} ${municipioImss.descMunicipio} <br>

													<input type="hidden" name="idMunicipio" id="idMunicipio${municipioImss.idMunicipio}" value="${municipioImss.idMunicipio}" />
													<input type="hidden" name="cvecMunicipioSINDO" id="cvecMunicipioSINDO${municipioImss.idMunicipio}" value="${municipioImss.cvecMunicipioSINDO}" />
													<input type="hidden" name="descMunicipio" id="descMunicipio${municipioImss.idMunicipio}" value="${municipioImss.descMunicipio}" />

													<input type="hidden" name="idDelegacion" id="idDelegacion${municipioImss.idMunicipio}" value="${municipioImss.subdelegacion.delegacion.id}" />
													<input type="hidden" name="cveDelegacion" id="cveDelegacion${municipioImss.idMunicipio}" value="${municipioImss.subdelegacion.delegacion.clave}" />
													<input type="hidden" name="descDelegacion" id="descDelegacion${municipioImss.idMunicipio}" value="${municipioImss.subdelegacion.delegacion.descripcion}" />
													<input type="hidden" name="cveCiz" id="cveCiz${municipioImss.idMunicipio}" value="${municipioImss.subdelegacion.delegacion.ciz}" />
													
													<input type="hidden" name="idSubdelegacion" id="idSubdelegacion${municipioImss.idMunicipio}" value="${municipioImss.subdelegacion.id}" />
													<input type="hidden" name="cveSubdelegacion" id="cveSubdelegacion${municipioImss.idMunicipio}" value="${municipioImss.subdelegacion.clave}" />
													<input type="hidden" name="descSubdelegacion" id="descSubdelegacion${municipioImss.idMunicipio}" value="${municipioImss.subdelegacion.descripcion}" />
												</address>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="2">Sin Subdelegaciones que mostrar</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</form>
	</div>
</div>