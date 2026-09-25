<div id="info-paso" style="margin-bottom: 50px;">
	<div class="row col-md-8">
		<h3>
			<spring:message
				code="label.solicitud.numerosSeguridadSocialInvolucrados" />
		</h3>
		<hr class="red" style="margin-bottom: 20px;">
	</div>

	<div class="form-group">
		<div class="row col-md-8">
			<div class="row col-md-12 form-group">	
				<label><spring:message code="label.docsProbatorios.tipoSolicitud"/></label><span class="requied">*</span>
				<div class="col-md-12">
				  <label class="radio-inline">
					<input id="asegurado" type="radio" name="tipoSolicitante" value="ASEGURADO_PENSIONADO"> <spring:message code="label.docsProbatorios.tipoSolicitud.asegurado"/>
				  </label>
				  <label class="radio-inline">
					<input id="def" type="radio" name="tipoSolicitante" value="1"> <spring:message code="label.docsProbatorios.tipoSolicitud.defuncion"/>
				  </label>
				  <label class="radio-inline">
					<input id="representante" type="radio" name="tipoSolicitante" value="REPRESENTANTE_LEGAL"> <spring:message code="label.docsProbatorios.tipoSolicitud.representante"/>
				  </label>
				</div>
				<div class="row col-md-12">
					<form:errors path="tipoSolicitante" cssClass="error" />
				</div>
			</div>
			
			<div style="display:none">
			<label class="radio-inline">
				<input id="defuncion" type="radio" name="defuncion" value="1"> <spring:message code="label.docsProbatorios.tipoSolicitud.defuncion"/>
			  </label>
		  </div>
		</div>
		
		<div class="row col-md-8" id="beneficiario">
			<div class="row col-md-12 form-group">
				<label><spring:message code="label.docsProbatorios.tipoBeneficiario"/></label><span id="idBeneficio">*</span><span id="idBeneficioError" class="error hiddenElement" style="font-size:12px !important;"></span>
				<div class="col-md-12">
				  <label class="radio-inline">
					<input id="conyuge" type="radio" name="tipoBeneficiario" value="CONYUGE"> 
					<spring:message code="label.docsProbatorios.tipoBeneficiario.coyuge"/>
				  </label>
				  <label class="radio-inline">
					<input id="descendiente" type="radio" name="tipoBeneficiario" value="DESCENDIENTE"> 
					<spring:message code="label.docsProbatorios.tipoBeneficiario.descendiente"/>
				  </label>
				</div>
				<div class="col-md-12">
				  <label class="radio-inline">
					<input id="padres" type="radio" name="tipoBeneficiario" value="PADRES"> 
					<spring:message code="label.docsProbatorios.tipoBeneficiario.padres"/>
				  </label>
				  <label class="radio-inline">
					<div class="col-md-12"><input id="concubino" type="radio" name="tipoBeneficiario" value="CONCUBINO">
					<spring:message code="label.docsProbatorios.tipoBeneficiario.concubino"/> </div>
				  </label>
				</div>
			  </div>
		</div>
		<div class="row col-md-8" id="curpSolicitante">
			 <div class="row col-md-12 form-group">
				<div>
					<label for="curp" class="control-label">
						<spring:message code="label.solicitud.curpp"/>:
					</label>
				</div>
				<div>
					<spring:message code="label.placeholder.asegurado.curp" var="placeHolderCURP"/>
					<form:input path="curp" id="registroCurp" cssClass="form-control" maxlength="18" style="font-size: 18px;" placeholder="${placeHolderCURP}"/>
					<form:errors path="curp" cssClass="error" />
				</div>
			  </div>
		</div>
		
		<div class="row col-md-8 bottom-buffer">
			<div >
				<div class="row col-md-12">
					<div >
						<label for="NSS" class="control-label" style="text-align: left;">
							<spring:message code="label.solicitud.placeholder.NSS" />
						</label>
					</div>
				</div>
				<div class="row col-md-12" style="margin-bottom:15px">
						<spring:message code="label.solicitud.placeholder.NSS"
							var="placeHolderNSS" />
						<div>
							<div class="row col-md-11">
								<form:input path="nssvo.NSS" id="registroNSS"
									cssClass="form-control" maxlength="11" style="font-size: 18px;"
									placeholder="${placeHolderNSS}" />
							</div>
							<div class="col-md-1" id="contenedorAyudaNss" style="margin-left:28px;">
								<a data-toggle="popover" id="ayudaNss" class="btn btn-xs icono-help" data-original-title="" title=""> </a>
							</div>
						</div>
						<div class="col-md-12 col-sm-7 col-xs-12 text-left">
							<span id="NSSError" class="error hiddenElement"></span>
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
					<div class="col-md-12">
						<label for="listadoNSSInvolucrados" class="control-label"
							style="text-align: left;"> <spring:message
								code="label.solicitud.placeholder.listadoNSSInvolucrados" />
						</label>
					</div>
					<div class="col-md-12">
						<table class="table" id='listadoNSSInvolucradosGrid' style="">
							<tr></tr>							
                            <c:forEach var="nss" varStatus="contadorNSS"
                                items="${datosHistoriaLaboral.NSSList}">
                                <tr id="listadoNSSInvolucradosGrid${contadorNSS.index +1}">
                                    <td width="5%" nowrap><p style="font-size: 1.5em;">${contadorNSS.index +1}</p></td>
                                    <td width="5%" nowrap><p style="font-size: 1.5em;">${nss.NSS}</p></td>
                                    <td align="right" width="90%" height="55"><p style="font-size: 1.5em;"><a href="#"
                                        onclick="return fnEliminarRow('listadoNSSInvolucradosGrid${contadorNSS.index+1}');">Eliminar</a></p>
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
	   </div>
		<div class="row col-md-8">
			<div>			
				<label for="documentoProbatorio"
					class="control-label"
					style="text-align: left;"> <spring:message
					code="label.solicitud.placeholder.documentoProbatorio" />
				</label>
			</div>
			<div class="row col-md-11" >
				<div >
					<form:select multiple="single" path="documentoProbatorio.desDocumento" id="registroDocumentoProbatorio" cssClass="form-control" >
						<form:option value="NONE" label="--Por favor seleccione--" disabled="true" selected="true"/>
						<c:forEach var="documento" varStatus="contadorDocumento" items="${documentosProbatoriosVo}" >
							<optgroup label="${documento.key.descripcion} (Obligatorio)" value="${documento.key.idTipoDocumentoProbatorio}" />
							<form:options items="${documento.value}" itemValue="cveIdDocumento" itemLabel="desDocumento" />  
						</c:forEach>
					</form:select>
				</div>	
			</div>
			<div class="col-md-1" id="contenedorAyudaDoc" style="text-align-right">
				   <a data-toggle="popover" id="ayudaDocProbatorio" class="btn btn-xs icono-help" data-original-title="" title=""> </a>
			</div>
			<div style="margin-top:15px" class="row col-md-12">
				<div class="col-md-2">
					<form:label path="documentoProbatorio.nombre"
						cssClass="control-label">
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
				<div>
					<label for="listadoDocumentos" class="control-label"
						style="text-align: left;"> <spring:message
							code="label.solicitud.placeholder.listadoDocumentos"/>
					</label>
					<!--PENDIENTE CONFIRMACIÓN  -->
					<a data-toggle="popover" id="ayudaDoctos" class="btn btn-xs icono-help" data-original-title="" title=""> </a>
				</div>
				<div>
				   <h6>Asegurado</h6>
					<table class="table TbDocs" id='listadoDocumentosGrid'>
						<tr></tr>	
						<c:forEach var="documentoProbatorio" varStatus="contador"
							items="${datosHistoriaLaboral.documentoProbatorioList}">
							<c:if test="${!fn:startsWith (documentoProbatorio.nombre, 'CDA_PI')}">
							<tr id="listadoDocumentosGrid${contador.index +1}" class="trDocs">
								<td width="5%" nowrap>${contador.index +1}</p></td>
								<td width="5%" nowrap><p style="font-size: 1.5em;">${documentoProbatorio.desDocumento}</p></td>

								<td hidden="hidden">${documentoProbatorio.nombre}</td>
								<td hidden="hidden">${documentoProbatorio.cveIdDocumento}</td>
								<td hidden="hidden">${documentoProbatorio.idDocumentoPorTipo}</td>
								<td hidden="hidden">${documentoProbatorio.tipoDocumento}</td>
								<td hidden="hidden">${documentoProbatorio.tipoPer}</td>
								<td hidden="hidden">${documentoProbatorio.idDocBoveda}</td>
								<td align="right" width="90%" height="55"><p style="font-size: 1.5em;"><a href="#"
									onclick="return fnEliminarRowDoc('listadoDocumentosGrid${contador.index+1}', ${documentoProbatorio.tipoDocumento},'${documentoProbatorio.tipoPer}',${documentoProbatorio.cveIdDocumento},'${documentoProbatorio.idDocBoveda}');">Eliminar</a></p>
								</td>
							</tr>
							</c:if>
						</c:forEach>
					</table>				
						<hr>
					<div id="doctosBene" style="display:none"><h6>Beneficiario/Representante Legal</h6></div>
					<table class="table TbDocs" id='listadoDocumentosGridB'>
						<tr></tr>	
						<c:forEach var="documentoProbatoriob" varStatus="contadorb"
							items="${datosHistoriaLaboral.documentoProbatorioList}">
							<c:if test="${fn:startsWith (documentoProbatoriob.nombre, 'CDA_PI')}">
							<tr id="listadoDocumentosGridB${contadorb.index +1}" class="trDocs">
								<td width="5%" nowrap>${contadorb.index +1}</p></td>
								<td width="5%" nowrap><p style="font-size: 1.5em;">${documentoProbatoriob.desDocumento}</p></td>
								
								<td hidden="hidden">${documentoProbatoriob.nombre}</td>
								<td hidden="hidden">${documentoProbatoriob.cveIdDocumento}</td>
								<td hidden="hidden">${documentoProbatoriob.idDocumentoPorTipo}</td>
								<td hidden="hidden">${documentoProbatoriob.tipoDocumento}</td>
								<td hidden="hidden">${documentoProbatoriob.tipoPer}</td>
								<td hidden="hidden">${documentoProbatoriob.idDocBoveda}</td>
								<td align="right" width="90%" height="55"><p style="font-size: 1.5em;"><a href="#"
								onclick="return fnEliminarRowDoc('listadoDocumentosGridB${contadorb.index+1}',${documentoProbatoriob.tipoDocumento},'${documentoProbatoriob.tipoPer}', ${documentoProbatoriob.cveIdDocumento},'${documentoProbatoriob.idDocBoveda}');">Eliminar</a></p>
								</td>
							</tr>
							</c:if>
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