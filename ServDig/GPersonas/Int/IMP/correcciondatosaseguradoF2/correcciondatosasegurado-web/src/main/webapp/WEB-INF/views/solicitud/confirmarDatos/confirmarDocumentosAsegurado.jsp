<div id="info-paso" style="margin-bottom: 50px;">
	<h3>
		<spring:message
			code="label.solicitud.docuementosProbatoriosAsegurado" />
	</h3>
	<hr class="red" style="margin-bottom: 20px;"/>
	<div class="row">
		<div class="col-md-6 col-sm-5 col-xs-12">
			<div class="col-md-12 col-sm-5 col-xs-12">
				<label for="listadoDocumentos" class="control-label"
					style="text-align: left;"> <spring:message
						code="label.solicitud.placeholder.listadoDocumentos" />
				</label>
			</div>
			<div class="col-md-12 col-sm-5 col-xs-12">
				<table class="table" id='listadoDocumentosGrid' style="">
						<tr></tr>
                                                <c:set var="count" value="0" scope="page" />
						<c:forEach var="documentoProbatorio" varStatus="contador"
							items="${solicitud.tramites[0].asegurado.documentosProbatorios}">
                                                    <c:if test="${documentoProbatorio.documentoPorTipo.tipoDocumentoProbatorio.idTipoDocumentoProbatorio!=11}">
							<tr id="listadoDocumentosGrid${count +1}">
<%-- 								<td><p style="font-size: 1.5em;">${contador.index +1}</p></td> --%>
								<td><p style="font-size: 1.5em;">${count + 1}</p></td>
							<td><p style="font-size: 1.5em;"><a href="${contextpath}/wizard/correccionDatosAsegurado/obtenerDocumento/${solicitud.solicitudId}/${solicitud.noFolioSolicitud}/${documentoProbatorio.nomNombreDocumento}/${documentoProbatorio.bovedaDocId}." target="_blank">${documentoProbatorio.documentoPorTipo.documento.desDocumento}
							</a>
							</p></td>
                                                        </tr>
                                                        <c:set var="count" value="${count + 1}" scope="page"/>
                                                    </c:if>
						</c:forEach>
					</table>
			</div>
		</div>
	</div>

</div>