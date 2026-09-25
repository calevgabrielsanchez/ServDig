<div id="info-paso" style="margin-bottom: 50px;">
	<h3>
		<spring:message
			code="label.solicitud.numerosSeguridadSocialInvolucrados" />
	</h3>
	<hr class="red" style="margin-bottom: 20px;">

	<div class="form-group">
		<div class="row col-md-8 bottom-buffer">
				<div class="row col-md-12">
					<label for="NSS" class="control-label" style="text-align: left;">
						<spring:message code="label.solicitud.placeholder.NSS" /><span>:</span>
					</label>
				</div>
				<div class="row col-md-12" style="margin-bottom:15px">
					<spring:message code="label.solicitud.placeholder.NSS"
						var="placeHolderNSS" />
					<div>
						<div class="row col-md-11">
							<form:input path="nssvo.NSS" id="registroNSS"
								cssClass="form-control" maxlength="11" style="font-size: 18px;"
								placeholder="${placeHolderNSS}" data-toggle="tooltip" data-placement="top" title="Debe ingresar el o los NSS que sean necesarios para la regularización de sus datos ante el IMSS."/>
						</div>
					</div>
					<div class="col-md-12 col-sm-7 col-xs-12 text-left">
						<span style="font-size:18px" id="NSSError" class="error hiddenElement"></span>
							<form:errors path="nssvo.NSS" cssClass="error" />
					</div>
				</div>
				
				<div class="row col-md-12" style="margin-bottom:15px">
					<button type="button" id="agregarNSS" class="btn btn-primary pull-right">
						<spring:message code="label.solicitud.agregar" />
					</button>
				</div>
			<div class="row col-md-12 bottom-buffer" style="margin-top:15px">		
			<div class="row" >
				<div class="col-md-12 col-sm-5 col-xs-12">
					<label for="listadoNSSInvolucrados" class="control-label"
						style="text-align: left;"> <spring:message
							code="label.solicitud.placeholder.listadoNSSInvolucrados" />
					</label>
				</div>
				<div class="col-md-12 ">
					<table class="table" id='listadoNSSInvolucradosGrid' style="">
						<tr></tr>
						<c:forEach var="nss" varStatus="contadorNSS" items="${datosHistoriaLaboral.NSSList}">
                            <tr id="listadoNSSInvolucradosGrid${contadorNSS.index +1}">
                                <td width="5%" nowrap><p style="font-size: 1.5em;">${contadorNSS.index+1}</p></td>
                                <td width="5%" nowrap><p style="font-size: 1.5em;">${nss.NSS}</p></td>
                                <td align="right" width="90%" height="55"><p style="font-size: 1.5em;"><a href="#"
                                    onclick="return fnEliminarRowNss('listadoNSSInvolucradosGrid${contadorNSS.index+1}');">Eliminar</a></p>
                                </td>
                            </tr>
                        </c:forEach>
					</table>
				</div>
				<div class="col-md-12 col-sm-7 col-xs-12">
					<form:errors path="NSSList" cssClass="error" />
				</div>
			</div>
		</div>
		</div>
		<div class="row col-md-8">	
			<div>
				<label for="documentoProbatorio"
					class="control-label"
					style="text-align: left;"> <spring:message
						code="label.solicitud.placeholder.documentoProbatorio" />
				</label>
			</div>	
				<div class="col-md-11">
					<div >
						<form:select multiple="single" path="documentoProbatorio.desDocumento" id="registroDocumentoProbatorio" cssClass="form-control" >
							<form:option value="-1" label="--Por favor seleccione--" disabled="true" selected="true"/>
							<c:forEach var="documento" varStatus="contadorDocumento" items="${documentosProbatoriosVo}" >
								<optgroup style="color: #101010; font-weight: 700;" label="${documento.key.descripcion} (Obligatorio)" value="${documento.key.idTipoDocumentoProbatorio}"/>
								<c:forEach var="opcion" varStatus="contdocs" items="${documento.value}" >
									<form:option value="${opcion.cveIdDocumento}" label="${opcion.desDocumento}" docportipo="${opcion.idDocumentoPorTipo}" />  
								</c:forEach>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="col-md-1" style="text-align-right" id="contenedorAyudaDoc">
				   <a data-toggle="popover" id="ayudaDocProbatorio" class="btn btn-xs icono-help" data-original-title="" title=""> </a>
				</div>

			<div style="margin-top:15px" class="row col-md-12">
				<div class="col-md-2">
					<form:label path="documentoProbatorio.nombre"
						cssClass="col-md-3 control-label">
						<spring:message code="label.solicitud.archivo" />
					</form:label>
				</div>
				<div class="col-md-2">
					<div id="file_browse_wrapper" style="vertical-align: middle;" class="image-upload">
						<label for="fileData"> <img src="${recurso}/delta/resources/imagenes/btn_buscar.png" />
						</label>
						<input id="fileData" type="file" name="fileData" class="mboton">
						<form:errors path="documentoProbatorio.nombre" cssClass="error" />
					</div>
				</div>
				<div class="text-right">
					<button type="button" id="agregarDocumento" class="btn btn-primary">
						<spring:message code="label.solicitud.adjuntar" />
					</button>
				</div>
			</div>
				
			<div class="row col-md-12 bottom-buffer" style="margin-top:15px">
				<div >
					<label for="listadoDocumentos" class="control-label"
						style="text-align: left;"> <spring:message
							code="label.solicitud.placeholder.listadoDocumentos" />
					</label>
				</div>
				<div >
					<table class="table" id='listadoDocumentosGrid' style="">
						<tr></tr>	
						<c:forEach var="documentoProbatorio" varStatus="contador"
							items="${datosHistoriaLaboral.documentoProbatorioList}">
							<tr id="listadoDocumentosGrid${contador.index +1}">
								<td width="5%" nowrap><p style="font-size: 1.5em;">${contador.index +1}</p></td>
								<td width="5%" nowrap><p style="font-size: 1.5em;">${documentoProbatorio.desDocumento}</p></td>
								<td hidden="hidden">${documentoProbatorio.nombre}</td>
								<td hidden="hidden">${documentoProbatorio.cveIdDocumento}</td>
								<td hidden="hidden">${documentoProbatorio.idDocumentoPorTipo}</td>
								<td hidden="hidden">${documentoProbatorio.idDocBoveda}</td>
								<td hidden="hidden">${documentoProbatorio.tipoDocumento}</td>
								<td align="right" width="90%" height="55"><p style="font-size: 1.5em;"><a href="#"
									onclick="return fnEliminarRow('listadoDocumentosGrid${contador.index+1}', ${documentoProbatorio.tipoDocumento}, '${documentoProbatorio.idDocBoveda}');">Eliminar</a></p>
								</td>
								
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="row">
					<form:errors path="documentoProbatorioList" cssClass="error" />
				</div> 
			</div>
		</div>

	</div>
</div>