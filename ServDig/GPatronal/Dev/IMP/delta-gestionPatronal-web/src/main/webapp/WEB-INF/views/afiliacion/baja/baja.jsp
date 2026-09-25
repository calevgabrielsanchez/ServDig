<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/baja/baja.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript">
	var context_path="${contextpath}";
	var context="${contextpath}";
</script>

<div class="page_holder">
	<div class="contenedor" style="width: 100% !important">
		<div class="row">
			<div class="cell">
				<div class="row" id="formaBaja" style="width: 1000px;" >
					<form id="formaParaEstilo">
						<p>
							<spring:message code="texto.baja" />
						</p>
						<table style="width: 900px; border: none !important;">
							<tr>
								<td class="label_patrones" style="width: 150px;">
									<label>
										<spring:message code="label.rfc" />
									</label>
								</td>
								<td class="label_patrones_data" style="width: 150px;">
									<input type="text" id="rfc" maxlength="13" size="15"/>	
								</td>
								<td style="border: none !important;">
									<input type="button" id="btnBuscaRegistrosPatronales" class="mboton" onclick="actualizarListaRegistrosPatronales()" value="Mostrar registros patronales" />
								</td>
							</tr>
							<tr>
								<td class="label_patrones">
									<label>
										<spring:message code="label.nrp" />
									</label>
								</td>
								<td class="label_patrones_data">
									<input type="text" id="numRegistroPatronal" maxlength="11" size="15"/>	
								</td>
							</tr>
						</table>
						<table style="width: 900px; border: none !important;">
							<tr>
								<td>
									<div id="listaRegistrosPatronales" style="width: 100%;">
										<table id="gridRegistrosPatronales"
												style="width: 100%; vertical-align: top;">
											<thead>
											</thead>
											<tbody style="width: 100%;">
											</tbody>
										</table>					
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<input type="button" id="btnRegistrarBaja" class="mboton" onclick="iniciarBaja()" value="Registrar Baja de Registro Patronal" />
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div> 
</div>
<jsp:include page="../popUpDetalleRegistroPatronal.jsp"/>
<jsp:include page="../../common/dialogosGenericos.jsp"/>
