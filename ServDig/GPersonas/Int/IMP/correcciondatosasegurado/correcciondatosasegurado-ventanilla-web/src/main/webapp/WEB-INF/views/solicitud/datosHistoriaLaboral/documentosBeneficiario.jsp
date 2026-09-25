<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="info-paso" style="margin-bottom: 50px;">
	<h3>
		<spring:message
			code="label.solicitud.docuementosProbatoriosAsegurado" />
	</h3>
	<hr class="red" style="margin-bottom: 20px;">

	<div class="form-group">
            <div class="row col-md-12 form-group">
                    <label><spring:message code="label.docsProbatorios.tipoSolicitud" /></label>
                    <div class="row col-md-12">
                        <label>
                            <c:out value="${datosHistoriaLaboralBeneficiario.tipoSolicitante}" />
                        </label>
                    </div>
                    <div class="row">
                            <form:errors path="tipoSolicitante" cssClass="error" />
                    </div>
            </div>
            <c:if test="${datosHistoriaLaboralBeneficiario.tipoSolicitante!='Asegurado' && datosHistoriaLaboralBeneficiario.tipoSolicitante!='Representante'}">
            <div class="row col-md-12 form-group">
                    <label><spring:message code="label.docsProbatorios.parentesco"/></label><span id="idBeneficio">*</span><span id="idBeneficioError" class="error hiddenElement" style="font-size:12px !important;"></span>
                    <div class="col-md-12">
                      <label class="radio-inline">
                            <input id="conyugue" type="radio" name="tipoBeneficiario" value="CONYUGUE" ${datosHistoriaLaboralBeneficiario.tipoBeneficiario == 'CONYUGUE' ? 'checked' : ''}> 
                            <spring:message code="label.docsProbatorios.tipoBeneficiario.coyuge"/>
                      </label>
                      <label class="radio-inline">
                            <input id="descendiente" type="radio" name="tipoBeneficiario" value="DESCENDIENTE" ${datosHistoriaLaboralBeneficiario.tipoBeneficiario == 'DESCENDIENTE' ? 'checked' : ''}> 
                            <spring:message code="label.docsProbatorios.tipoBeneficiario.descendiente"/>
                      </label>
                    </div>
                    <div class="col-md-12">
                      <label class="radio-inline">
                            <input id="padres" type="radio" name="tipoBeneficiario" value="PADRES" ${datosHistoriaLaboralBeneficiario.tipoBeneficiario == 'PADRES' ? 'checked' : ''}> 
                            <spring:message code="label.docsProbatorios.tipoBeneficiario.padres"/>
                      </label>
                      <label class="radio-inline">
                            <div class="col-md-12"><input id="concubino" type="radio" name="tipoBeneficiario" value="CONCUBINO" ${datosHistoriaLaboralBeneficiario.tipoBeneficiario == 'PADRES' ? 'checked' : ''}>
                            <spring:message code="label.docsProbatorios.tipoBeneficiario.concubino"/> </div>
                      </label>
                    </div>
              </div>
                <div class="row">
                    <span style="font-size:18px" id="tipoBeneficiarioError" class="error hiddenElement"></span>
                        <form:errors path="tipoBeneficiario" cssClass="error" />
                </div>
            </c:if>
           <div class="row col-md-12 form-group">
                <label><spring:message code="label.solicitud.curpBeneficiario"/></label>
            </div>
            <div class="row col-md-12 form-group">   
                <div class="row col-md-4 col-sm-7 col-xs-12">
                            <spring:message code="label.placeholder.asegurado.curp" var="placeHolderCURP"/>
                            <form:input path="curp" id="registroCurp" cssClass="form-control" maxlength="18" placeholder="${placeHolderCURP}"/>
                                                    <span id="curpError" class="error hiddenElement"></span>
                            <form:errors id="curpError" path="curp" cssClass="error" />
                </div>
                <div class="col-md-3">
                    <button type="button" id="validarCurp" class="btn btn-primary">
                        <spring:message code="label.solicitud.placeholder.validar" />
                    </button>
                </div>
            </div>
            <div class="row col-md-12">
                <div class="row col-md-8" >	
                    <div class="row col-md-4">
                        <label><spring:message code="label.solicitud.label.curp" /></label>
                    </div>
                    <div class="col-md-4">
                        <label class="radio-inline" id="lblcurp">${personaBeneficiario.curp}</label>
                    </div>
                </div>
            </div>
            <div class="row col-md-12">
                <div class="row col-md-8" >	
                    <div class="row col-md-4">
                        <label><spring:message code="label.solicitud.label.primerApellido" /></label>
                    </div>
                    <div class="col-md-4">
                        <label class="radio-inline" id="lblPrimerApellido">${personaBeneficiario.primerApellido!=null ? personaBeneficiario.primerApellido:""}</label>
                    </div>
                </div>
            </div>
            <div class="row col-md-12">
                <div class="row col-md-8" >	
                    <div class="row col-md-4">
                        <label><spring:message code="label.solicitud.label.segundoApellido" /></label>
                    </div>
                    <div class="col-md-4">
                        <label class="radio-inline" id="lblSegundoApellido">${personaBeneficiario.segundoApellido!=null ? personaBeneficiario.segundoApellido:""}</label>
                    </div>
                </div>
            </div>
            
                                                  
            <div class="row col-md-12">
                <div class="row col-md-8" >	
                    <div class="row col-md-4">
                        <label><spring:message code="label.solicitud.label.nombre" /></label>
                    </div>
                    <div class="col-md-4">
                        <label class="radio-inline" id="lblnombre">${personaBeneficiario.nombre!=null ? personaBeneficiario.nombre : ""}</label>
                    </div>
                </div>
            </div>
            <div class="row col-md-12">
                <div class="row col-md-8" >	
                    <div class="row col-md-4">
                        <label><spring:message code="label.solicitud.label.sexo" /></label>
                    </div>
                    <div class="col-md-4">
                        <label class="radio-inline" id="lblSexo">${personaBeneficiario.sexo.descripcion!=null ? personaBeneficiario.sexo.descripcion : ""}</label>
                    </div>
                </div>
            </div>
            <div class="row col-md-12">
                <div class="row col-md-8" >	
                    <div class="row col-md-4">
                        <label><spring:message code="label.solicitud.label.curp.historica" /></label>
                    </div>
                    <div class="col-md-4">
                        <label class="radio-inline" id="labelCurpHistorico"></label>
                    </div>
                </div>
            </div>
                                                    
            <div class="row col-md-6" style="margin-top:20px; margin-right:10px">	
                <div>
                    <label for="documentoProbatorio"
                            class="control-label"
                            style="text-align: left;"> <spring:message
                                    code="label.solicitud.placeholder.documentoProbatorioBeneficiario" />
                    </label>
                    <span id="ayudaDocProbatorio" class="glyphicon glyphicon-question-sign"></span>
                </div>	
                <div>
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
            <div class="row col-md-6 bottom-buffer">
                <br>
                        <label for="listadoDocumentos" class="control-label"
                                style="text-align: left;"> <spring:message
                                        code="label.solicitud.placeholder.listadoDocumentos" />
                        </label>
                <div >
                    <table class="table" id='listadoDocumentosGrid' style="">
                            <tr></tr>	
                            <c:forEach var="documentoProbatorio" varStatus="contador"
                                    items="${datosHistoriaLaboralBeneficiario.documentoProbatorioList}">
                                <tr id="listadoDocumentosGrid${contador.index +1}">
                                        <td width="5%" nowrap><p style="font-size: 1.5em;">${contador.index +1}</p></td>
                                        <td width="5%" nowrap><p style="font-size: 1.5em;">${documentoProbatorio.desDocumento}</p></td>
                                        <td hidden="hidden">${documentoProbatorio.nombre}</td>
                                        <td hidden="hidden">${documentoProbatorio.cveIdDocumento}</td>
                                        <td hidden="hidden">${documentoProbatorio.idDocumentoPorTipo}</td>
                                        <td hidden="hidden">${documentoProbatorio.idDocBoveda}</td>
                                        <td hidden="hidden">${documentoProbatorio.tipoDocumento}</td>
                                        <td align="right" width="90%" height="55"><p style="font-size: 1.5em;"><a href="#"
                                                onclick="return fnEliminarRow('listadoDocumentosGrid${contador.index+1}', ${documentoProbatorio.cveIdDocumento}, '${documentoProbatorio.idDocBoveda}');">Eliminar</a></p>
                                        </td>

                                </tr>
                            </c:forEach>
                    </table>
                </div>
                <div class="row">
                    <span style="font-size:18px" id="documentoProbatorioListError" class="error hiddenElement"></span>
                        <form:errors path="documentoProbatorioList" cssClass="error" />
                </div> 
            </div>
                                                    
            <div style="margin-top:15px" class="row col-md-8">
                <div class="col-md-2" style="display: none;">
                        <input id="fileData" type="file" name="fileData" class="mboton">
                        <form:errors path="documentoProbatorio.nombre" cssClass="error" />
                </div>
            </div>
                        
            <div class="row col-md-12 bottom-buffer" style="margin-top:15px">
                <div class="pull-right" >
                        <button type="button" id="limpiarNSSDocumentos"
                                class="btn btn-primary">
                                <spring:message code="label.limpiar" />
                        </button>			
                </div>
	   </div>
	</div>
</div>
