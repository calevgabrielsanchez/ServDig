<%@ include file="../general/taglibs.jsp"%>

<div>
	<div class="row">
		<div class="cell">
			<c:choose>
				<c:when test="${not empty firmaElectronicaFIEL.errorFormGeneral}">
					<div class="alert alert-danger" style="width: 90%; margin: 0 auto;">
						<button type="button" class="close" data-dismiss="alert">�</button>
						<strong>Error: </strong>${firmaElectronicaFIEL.errorFormGeneral}
					</div>
					<br>
					<div style="float: right;">
						<input type="button" value="Aceptar" onclick="cancelar();" class="btn btn-default" />
					</div>
				</c:when>
				<c:otherwise>
					<fieldset>
						<legend>
							<strong>&nbsp;Seleccione el tipo de Firma Digital&nbsp;</strong>
						</legend>
						
						<div class="contenedor">
							<c:if test="${not empty firmaElectronicaFIEL }">
								<div class="row">
									<div class="cell">
										<h2 style="font-size: large;">Firma utilizando su FIEL</h2>
										<p>Si usted cuenta con un certificado FIEL usted la puede utilizar</p>
										<form:form id="firmaFIELForm"
											action="/gestionCobranza-web/firma-digital/firma-FIEL"
											method="post" modelAttribute="firmaElectronicaFIEL">
											<form:hidden path="rfc" />
											<form:hidden path="cadenaOriginal" />
											<form:hidden path="firmarArchivo" />
											<input type="submit" class="btn btn-default"
												value="Firma con FIEL">
										</form:form>
									</div>
								</div>
							</c:if>
							<c:if test="${not empty firmaElectronicaIMSS }">
								<div class="row">
									<div class="cell">
										<h2 style="font-size: large;">Firma utilizando su Certificado IMSS</h2>
										<p>Si usted cuenta con un certificado IMSS usted la puede utilizar</p>
										<form:form id="firmaIMSSForm"
											action="/gestionCobranza-web/firma-digital/firma-IMSS"
											method="post" modelAttribute="firmaElectronicaIMSS">
											<form:hidden path="registroPatronal" />
											<form:hidden path="cadenaOriginal" />
											<form:hidden path="firmarArchivo" />
											<input type="submit" class="btn btn-default"
												value="Firma con IMSS">
										</form:form>
									</div>
								</div>
							</c:if>
						</div>
					</fieldset>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>