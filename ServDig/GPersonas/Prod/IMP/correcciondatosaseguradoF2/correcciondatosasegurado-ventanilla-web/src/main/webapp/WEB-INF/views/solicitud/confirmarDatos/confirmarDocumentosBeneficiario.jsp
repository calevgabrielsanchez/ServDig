<div id="info-paso" style="margin-bottom: 50px;">
	<h3>
		<spring:message
			code="label.solicitud.label.info.beneficiario.representante" />
	</h3>
	<hr class="red" style="margin-bottom: 20px;"/>
	
	<label for="listadoDocumentosBeneficiario" class="control-label"
					style="text-align: left; font-weight:900; color:#000000;"> <spring:message
						code="label.solicitud.placeholder.listadoDocumentosBeneficiario" />
				</label>
        <c:if test="${solicitud.tramites[0].beneficiario!=null}">
        <br/>
            <label><spring:message code="label.docsProbatorios.parentesco"/></label>        
            <div class="row col-md-12">
                 <c:choose> 
                        <c:when test="${solicitud.tramites[0].beneficiario.tipoBeneficiario==2}">
                            <label class="radio-inline">
                               <input id="conyuge" type="radio" name="tipoBeneficiario" value="CONYUGE" checked> 
                               <spring:message code="label.docsProbatorios.tipoBeneficiario.coyuge"/>
                            </label>
                        </c:when>
                        <c:when test="${solicitud.tramites[0].beneficiario.tipoBeneficiario==3}">
                            <label class="radio-inline">
                            <input id="descendiente" type="radio" name="tipoBeneficiario" value="DESCENDIENTE" checked> 
                            <spring:message code="label.docsProbatorios.tipoBeneficiario.descendiente"/>
                            </label>
                        </c:when>
                        <c:when test="${solicitud.tramites[0].beneficiario.tipoBeneficiario==4}">
                            <label class="radio-inline">
                               <input id="padres" type="radio" name="tipoBeneficiario" value="PADRES" checked > 
                               <spring:message code="label.docsProbatorios.tipoBeneficiario.padres"/>
                           </label>
                         </c:when>
                         <c:when test="${solicitud.tramites[0].beneficiario.tipoBeneficiario==5}">
                          <label class="radio-inline">
                            <div class="col-md-12"><input id="concubino" type="radio" name="tipoBeneficiario" value="CONCUBINO" checked >
                            <spring:message code="label.docsProbatorios.tipoBeneficiario.concubino"/> </div>
                          </label>
                         </c:when>
                 </c:choose>
            </div>
        </c:if>
        
	<div class="row col-md-12">

                    <div class="row col-md-4">
                        <label><spring:message code="label.solicitud.label.curp" /></label>
                        <br/>
                        <label><spring:message code="label.solicitud.label.primerApellido" /></label>
                        <br/>
                        <label><spring:message code="label.solicitud.label.segundoApellido" /></label>
                        <br/>
                        <label><spring:message code="label.solicitud.label.nombre" /></label>
                        <br/>
                        <label><spring:message code="label.solicitud.label.sexo" /></label>
                        <br/>
                        <label><spring:message code="label.solicitud.label.curp.historica" /></label>
                    </div>
                    <div class="row col-md-3">
                        <label class="radio-inline" id="lblcurp">${solicitud.tramites[0].beneficiario.curp}</label>
                        <br/>
                        <label class="radio-inline" id="lblPrimerApellido">${solicitud.tramites[0].beneficiario.primerApellido}</label>
                        <br/>
                        <label class="radio-inline" id="lblSegundoApellido">${solicitud.tramites[0].beneficiario.segundoApellido}</label>
                        <br/>
                        <label class="radio-inline" id="lblnombre">${solicitud.tramites[0].beneficiario.nombre}</label>
                        <br/>
                        <label class="radio-inline" id="lblSexo">${solicitud.tramites[0].beneficiario.sexo.descripcion}</label>
                        <br/>
                        <label class="radio-inline" id="labelCurpHistorico"></label>
                    </div>
            <div class="row col-md-6">
                <label for="listadoDocumentos" class="control-label"
                        style="text-align: left;"> <spring:message
                                code="label.solicitud.placeholder.listadoDocumentos" />
                </label>
                <br/>
                <table class="table" id='listadoDocumentosGrid' style="">
                    <tr></tr>
                    <c:set var="count" value="0" scope="page" />
                    <c:forEach var="documentoProbatorio" varStatus="contador"
                            items="${solicitud.tramites[0].beneficiario.documentosProbatorios}">
                            <tr id="listadoDocumentosGrid${count +1}">
                                    <td><p style="font-size: 1.5em;">${contador.index +1}</p></td>
                            <td><p style="font-size: 1.5em;"><a href="${contextpath}/wizard/correccionDatosAsegurado/obtenerDocumento/${solicitud.solicitudId}/${solicitud.noFolioSolicitud}/${documentoProbatorio.nomNombreDocumento}/${documentoProbatorio.bovedaDocId}." target="_blank">${documentoProbatorio.documentoPorTipo.documento.desDocumento}
                            </a>
                            </p></td>
                            </tr>
                            <c:set var="count" value="${count + 1}" scope="page"/>
                    </c:forEach>
                </table>
            </div>
	</div>

</div>