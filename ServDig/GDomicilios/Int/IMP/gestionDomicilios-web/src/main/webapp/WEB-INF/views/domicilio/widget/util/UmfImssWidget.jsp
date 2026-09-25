<%@ include file="../../../general/taglibs.jsp"%>

<div class="cuerpo">
	<div class="descripcion">
		<c:if test="${empty msgError }">
			<p>
				<spring:message code="label.widget.descripcion.lista.umf" />
			</p>
		</c:if>
	</div>
	<div class="contenido" style="width: 100%;">
		<form name="form" id="form">
			<c:choose>
				<c:when test="${not empty msgError }">
					<div class="alert alert-warning" >${msgError}</div>
				</c:when>
				<c:otherwise>
					<table id="tblUMFs" class="table table-striped table-bordered"
						cellpadding="0" cellspacing="0" border="0" style="width: 100%;">
						<thead>
							<tr>
								<th style="width: 10% !important;"></th>
								<th style="text-align: center;">Unidad M&eacute;dico
									Familiar</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${not empty lstUmf }">
									<c:forEach items="${lstUmf}" var="umf" varStatus="indice">
										<tr>
											<td style="text-align: center; vertical-align: middle;">
												<c:if test = "${fn:length(lstUmf) eq 1}">
													<input type="radio" name="idUmfRadio" value="${umf.idUMF}"
													style="width: 100%;" id="idUmfRadio"
													onclick="javascript:ejecutarCallback();"
													checked="checked">
													<input type="hidden" id="umfSelectDefault" value="${umf.idUMF}">
													</c:if>
												<c:if test="${fn:length(lstUmf) gt 1}">
													<input type="radio" name="idUmfRadio" value="${umf.idUMF}"
													style="width: 100%;" id="idUmfRadio"
													onclick="javascript:ejecutarCallback();">
													<input type="hidden" id="umfSelectDefault" value="">
												</c:if>
											</td>
											<td>
												<address>
													<i class="glyphicon glyphicon-tag" style="margin-right: 15px;"></i><strong>Delegaci&oacute;n
														:</strong> ${umf.subdelegacion.delegacion.clave}
													${umf.subdelegacion.delegacion.descripcion} <br> <i
														class="glyphicon glyphicon-tag" style="margin-right: 15px;"></i><strong>Subdelegaci&oacute;n
														:</strong> ${umf.subdelegacion.clave}
													${umf.subdelegacion.descripcion} <br> <i
														class="glyphicon glyphicon-tag" style="margin-right: 15px;"></i><strong>Unidad
														M&eacute;dica Familiar :</strong> ${umf.noEconomico}
													${umf.nombreCorto} <br> <i class="glyphicon glyphicon-globe"
														style="margin-right: 15px;"></i><strong>Direcci&oacute;n
														:</strong> ${umf.desDireccion} <input type="hidden" name="idUmf"
														id="idUmf${umf.idUMF}" value="${umf.idUMF}" /> <input
														type="hidden" name="noEconomicoUMF"
														id="noEconomicoUMF${umf.idUMF}" value="${umf.noEconomico}" />
													<input type="hidden" name="idDelegacion"
														id="idDelegacion${umf.idUMF}"
														value="${umf.subdelegacion.delegacion.id}" /> <input
														type="hidden" name="cveDelegacion"
														id="cveDelegacion${umf.idUMF}"
														value="${umf.subdelegacion.delegacion.clave}" /> <input
														type="hidden" name="idSubdelegacion"
														id="idSubdelegacion${umf.idUMF}"
														value="${umf.subdelegacion.id}" /> <input type="hidden"
														name="cveSubdelegacion" id="cveSubdelegacion${umf.idUMF}"
														value="${umf.subdelegacion.clave}" /> <input type="hidden"
														name="cveCiz" id="cveCiz${umf.idUMF}"
														value="${umf.subdelegacion.delegacion.ciz}" />
												</address>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="2">Sin Unidades M&eacute;dicas que mostrar</td>
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