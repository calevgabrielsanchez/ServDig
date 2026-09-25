<div id="info-paso" style="margin-bottom: 50px;">
	<h3>
		<spring:message
			code="label.solicitud.numerosSeguridadSocialInvolucrados" />
	</h3>
	<hr class="red" style="margin-bottom: 20px;"/>
	<div class="row">
		<div class="col-md-6 col-sm-5 col-xs-12">

				<div class="col-md-12 col-sm-5 col-xs-12">
					<label for="listadoNSSInvolucrados" class="control-label"
						style="text-align: left;"> <spring:message
							code="label.solicitud.placeholder.listadoNSSInvolucrados" />
					</label>
				</div>
				<div class="col-md-12 col-sm-5 col-xs-12">
					<table class="table" id='listadoNSSInvolucradosGrid' style="">
						<tr></tr>
						<c:forEach var="nss" varStatus="contador"
							items="${solicitud.tramites[0].listaNSS}">
							<tr id="listadoNSSInvolucradosGrid${contador.index +1}">
								<td><p style="font-size: 1.5em;">${contador.index +1}</p></td>
								<td><p style="font-size: 1.5em;">${nss}</p></td>
							</tr>
						</c:forEach>
							
					</table>
				</div>
				

			</div>
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
						<c:forEach var="documentoProbatorio" varStatus="contador"
							items="${solicitud.tramites[0].documentosProbatorios}">
							<tr id="listadoDocumentosGrid${contador.index +1}">
								<td><p style="font-size: 1.5em;">${contador.index +1}</p></td>
							<td><p style="font-size: 1.5em;"><a href="${contextpath}/wizard/correccionDatosAsegurado/obtenerDocumento/${solicitud.solicitudId}/${solicitud.noFolioSolicitud}/${documentoProbatorio.nomNombreDocumento}/${documentoProbatorio.bovedaDocId}." target="_blank">${documentoProbatorio.documentoPorTipo.tipoDocumentoProbatorio.descripcion}
							</a>
							</p></td>
						</tr>
						</c:forEach>
					</table>
			</div>
		</div>
	</div>

</div>