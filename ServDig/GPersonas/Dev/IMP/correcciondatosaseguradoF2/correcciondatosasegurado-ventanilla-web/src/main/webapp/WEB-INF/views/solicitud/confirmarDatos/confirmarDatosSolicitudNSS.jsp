<div id="info-paso" style="margin-bottom: 50px;">
	<div class="col-md-12 col-sm-12 col-xs-12">
		<h3>
			<spring:message
				code="label.solicitud.numerosSeguridadSocialInvolucrados" />
		</h3>
		<hr class="red" style="margin-bottom: 20px;"/>
	</div>
	<div class="row" >
		<div class="col-md-6 col-sm-5 col-xs-12">
			<c:if test="${ not empty solicitud.tramites[0].personas}">	
				<div class="col-md-3">
					<label class="control-label"> 
						<spring:message	code="label.confirmacion.solicitante" />
					</label>
				</div>
				<div class="col-md-6">${solicitud.tramites[0].personas[0].tipoPersona.descripcion}</div>
			</c:if>
		</div>
	</div>
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
			<HR>
			<div class="col-md-12 col-sm-5 col-xs-12">
				<label for="listadoDocumentos" class="control-label"
					style="text-align: left; font-weight:900; color:#000000;"> <spring:message
						code="label.solicitud.placeholder.listadoDocumentosAsegurado" />
				</label>
			</div>
			<div class="col-md-12 col-sm-5 col-xs-12">
				<table class="table" id='listadoDocumentosGrid' style="">
				<%int indiceAsegurado=1;%>
						<tr></tr>	
						<c:forEach var="documentoProbatorio" varStatus="contador" 
							items="${solicitud.tramites[0].documentosProbatorios}">
							<c:if test="${!fn:startsWith (documentoProbatorio.nomNombreDocumento, 'CDA_PI_')}">
							<tr id="listadoDocumentosGrid${contador.index +1}">
								<td><p style="font-size: 1.5em;"><%= indiceAsegurado%></p></td>
							<td><p style="font-size: 1.5em;"><a href="${contextpath}/atencionAutorizador/obtenerDocumento/${solicitud.solicitudId}/${solicitud.noFolioSolicitud}/_/${documentoProbatorio.nomNombreDocumento}/${documentoProbatorio.bovedaDocId}." target="_blank">${documentoProbatorio.documentoPorTipo.tipoDocumentoProbatorio.descripcion}
							</a>
							</p></td>
						</tr>
						<% indiceAsegurado=indiceAsegurado+1;%>
						</c:if>
						</c:forEach>
					</table>
			</div>
			<spring:eval expression="T(mx.gob.imss.cit.cda.web.utils.DeltaUtils).validarDocumentosBeneficiario(solicitud.tramites[0].documentosProbatorios)" var="validarBeneficiario" />
			<c:if test="${validarBeneficiario}">
			<div class="col-md-12 col-sm-5 col-xs-12">
				<label for="listadoDocumentosBeneficiario" class="control-label"
					style="text-align: left; font-weight:900; color:#000000;"> <spring:message
						code="label.solicitud.placeholder.listadoDocumentosBeneficiario" />
				</label>
				</c:if>
				<table class="table" id='listadoDocumentosBeneficiarioGrid' style="">
				<%int indiceBeneficiario=1;%>
						<tr></tr>	
						<c:forEach var="documentoProbatorio" varStatus="contador" 
							items="${solicitud.tramites[0].documentosProbatorios}">
							<c:if test="${fn:startsWith (documentoProbatorio.nomNombreDocumento,'CDA_PI_')}">
							<tr id="listadoDocumentosBeneficiarioGrid${contador.index +1}">
								<td width="27" height="55"><p style="font-size: 1.5em;"><%= indiceBeneficiario%></p></td>
							<td><p style="font-size: 1.5em;"><a href="${contextpath}/atencionAutorizador/obtenerDocumento/${solicitud.solicitudId}/${solicitud.noFolioSolicitud}/_/${documentoProbatorio.nomNombreDocumento}/${documentoProbatorio.bovedaDocId}." target="_blank">${documentoProbatorio.documentoPorTipo.tipoDocumentoProbatorio.descripcion}
							</a>
							</p></td>
						</tr>
						<% indiceBeneficiario=indiceBeneficiario+1;%>
						</c:if>
						</c:forEach>
					</table>
			</div>
		</div>
	</div>

</div>