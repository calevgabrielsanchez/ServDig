<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="info-paso" style="margin-bottom: 50px;">
    <h3>
        <spring:message
            code="label.solicitud.numeroSeguridadDocumento" />
    </h3>
    <hr class="red" style="margin-bottom: 20px;">

    <div class="form-group">
        <div class="row col-md-12 form-group">
            <label><spring:message code="label.docsProbatorios.tipoSolicitud"/>*</label>
            <div class="row col-md-12">
                <c:choose> 
                    <c:when test="${datosHistoriaLaboralBeneficiario.tipoSolicitante!='REPRESENTANTE_LEGAL'}">
                        <label style="font-weight: normal"><c:out value="${datosHistoriaLaboralBeneficiario.tipoSolicitante}" /></label>
                    </c:when>
                    <c:when test="${datosHistoriaLaboralBeneficiario.tipoSolicitante=='REPRESENTANTE_LEGAL'}">
                        <label style="font-weight: normal"><spring:message code="label.solicitud.representante"/></label>
                    </c:when>
                </c:choose>
                <input id="tipoSolicitante" value="${datosHistoriaLaboralBeneficiario.tipoSolicitante}" type="hidden" />
                <input id="defuncion" value="${datosHistoriaLaboralBeneficiario.defuncion}" type="hidden" />
            </div>
            <div class="row">
                <form:errors path="tipoSolicitante" cssClass="error" />
            </div>
        </div>
        <c:if test="${datosHistoriaLaboralBeneficiario.tipoSolicitante!='Asegurado' && datosHistoriaLaboralBeneficiario.tipoSolicitante!='REPRESENTANTE_LEGAL'}">
            <div class="row col-md-12 form-group">
                <label><spring:message code="label.docsProbatorios.parentesco"/></label><span id="idBeneficio">*</span><span id="idBeneficioError" class="error hiddenElement" style="font-size:12px !important;"></span>
                <div class="col-md-12">
                    <label class="radio-inline">
                        <input id="conyuge" type="radio" name="tipoBeneficiario" value="CONYUGE" ${datosHistoriaLaboralBeneficiario.tipoBeneficiario == 'CONYUGE' ? 'checked' : ''}> 
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
                        <div class="col-md-12"><input id="concubino" type="radio" name="tipoBeneficiario" value="CONCUBINO" ${datosHistoriaLaboralBeneficiario.tipoBeneficiario == 'CONCUBINO' ? 'checked' : ''}>
                            <spring:message code="label.docsProbatorios.tipoBeneficiario.concubino"/> </div>
                    </label>
                </div>
            </div>
            <div class="row col-md-12 form-group">
                <span style="font-size:18px" id="tipoBeneficiarioError" class="error hiddenElement"></span>
                <form:errors path="tipoBeneficiario" cssClass="error" />
            </div>
        </c:if>
        <div class="row col-md-12 form-group">
            <c:choose> 
                <c:when test="${datosHistoriaLaboralBeneficiario.tipoSolicitante!='ASEGURADO' && datosHistoriaLaboralBeneficiario.tipoSolicitante!='REPRESENTANTE_LEGAL'}">
                    <label><spring:message code="label.solicitud.curpBeneficiario"/></label>
                </c:when>
                <c:when test="${datosHistoriaLaboralBeneficiario.tipoSolicitante=='REPRESENTANTE_LEGAL'}">
                    <label><spring:message code="label.solicitud.curpRepresentante"/></label>
                </c:when>
            </c:choose>
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
                    <label><spring:message code="label.solicitud.label.fecha.nacimiento" /></label>
                </div>
                <div class="col-md-4">
                    <label class="radio-inline" id="lblFechNacimiento">${personaBeneficiario.fechaNacimientoFormateada!=null ? personaBeneficiario.fechaNacimientoFormateada : ""}</label>
                </div>
            </div>
        </div>
        <div class="row col-md-12">
            <div class="row col-md-8" >	
                <div class="row col-md-4">
                    <label><spring:message code="label.solicitud.label.lugar.nacimiento" /></label>
                </div>
                <div class="col-md-4">
                    <label class="radio-inline" id="lblLugarNacimiento">${personaBeneficiario.lugarNacimiento!=null ? personaBeneficiario.lugarNacimiento.nombre!=null?personaBeneficiario.lugarNacimiento.nombre:"" : ""}</label>
                </div>
            </div>
        </div>
        <div class="row col-md-12">
            <div class="row col-md-8" >	
                <div class="row col-md-4">
                    <label><spring:message code="label.solicitud.label.nacionalidad" /></label>
                </div>
                <div class="col-md-4">
                    <label class="radio-inline" id="lblNacionalidad">${personaBeneficiario.pais!=null ? personaBeneficiario.pais.nacionalidad!=null?personaBeneficiario.pais.nacionalidad:"" : ""}</label>
                </div>
            </div>
        </div>
        <div class="row col-md-12">
            <div class="row col-md-8" >	
                <div class="row col-md-4">
                    <label id="lblCurpHistorico"><spring:message code="label.solicitud.label.curp.historica" /></label>
                </div>
                <div class="col-md-4">
                    <label class="radio-inline" id="labelCurpHistoricoText">  

                        <c:if test="${personaBeneficiario.curpsHistoricas!=null}">
                            <c:forEach var="curpsHistoricas" varStatus="curpsHistoricasStatus" items="${personaBeneficiario.curpsHistoricas}" >
                                ${curpsHistoricas}
                            </c:forEach>
                        </c:if>

                    </label>
                </div>
            </div>
        </div>                    
        
        <div class="row col-md-12">
            <h4>
                <label id="lblDocProbatorio" class="control-label"><spring:message
                        code="label.documentos.probatorio" />
                </label>
            </h4>
        </div>

        <div class="row col-md-12">
            <div class="row col-md-8" >
                <div class="row col-md-4">
                    <label class="control-label" id="lblEntidad"> <spring:message
                            code="label.entidad" />
                    </label>
                </div>                    
                <div class="col-md-4">
                    <label class="radio-inline" id="lblEntidadValue">${personaBeneficiario.actaNacimiento.municipio.entidadFederativa.nombre}</label>
                </div> 
            </div>
        </div>
        <div class="row col-md-12">
            <div class="row col-md-8" >
                <div class="row col-md-4">
                    <label class="control-label" id="lblMunicipio"> <spring:message
                            code="label.municipio"></spring:message>
                    </label>
                </div>
                <div class="col-md-4">
                    <label class="radio-inline" id="lblMunicipioValue">${personaBeneficiario.actaNacimiento.municipio.nombre}</label>
                </div>
            </div>
        </div>
        <div class="row col-md-12">
            <div class="row col-md-8" >
                <div class="row col-md-4">
                    <label class="control-label" id="lblAnio"> <spring:message
                            code="label.anio"></spring:message>
                    </label>
                </div>
                <div class="col-md-4">
                    <label class="radio-inline" id="lblAnioValue">${personaBeneficiario.actaNacimiento.anio}</label>
                </div>
            </div>
        </div>
        <div class="row col-md-12">
            <div class="row col-md-8" >
                <div class="row col-md-4">
                    <label class="control-label" id="lblTomo"> <spring:message
                            code="label.tomo"></spring:message>
                    </label>
                </div>
                <div class="col-md-4">
                    <label class="radio-inline" id="lblTomoValue">${personaBeneficiario.actaNacimiento.tomo}</label>
                </div>
            </div>            
        </div>
        <div class="row col-md-12">
            <div class="row col-md-8" >
                <div class="row col-md-4">
                    <label class="control-label" id="lblNoActa"> <spring:message
                            code="label.acta"></spring:message>
                    </label>
                </div>
                <div class="col-md-4">
                    <label class="radio-inline" id="lblNoActaValue">${personaBeneficiario.actaNacimiento.noActa}</label>
                </div>
            </div>            
        </div>
        <div class="row col-md-12">
            <div class="row col-md-8" >
                <div class="row col-md-4">
                    <label class="control-label" id="lblCrip"> <spring:message
                            code="label.crip"></spring:message>
                    </label>
                </div>
                <div class="col-md-4">
                    <label class="radio-inline" id="lblCripValue">${personaBeneficiario.actaNacimiento.crip}</label>
                </div>
            </div>            
        </div>
        <div class="row col-md-12">
            <div class="row col-md-8" >
                <div class="row col-md-4">
                    <label class="control-label" id="lblNoLibro"> <spring:message
                            code="label.libro"></spring:message>
                    </label>
                </div>
                <div class="col-md-4">
                    <label class="radio-inline" id="lblNoLibroValue">${personaBeneficiario.actaNacimiento.noLibro}</label>
                </div>
            </div>            
        </div>
        <div class="row col-md-12">
            <div class="row col-md-8" >
                <div class="row col-md-4">
                    <label class="control-label" id="lblNoFoja"> <spring:message
                            code="label.foja"></spring:message>
                    </label>
                </div>
                <div class="col-md-4">
                    <label class="radio-inline" id="lblNoFojaValue">${personaBeneficiario.actaNacimiento.noFoja}</label>
                </div>
            </div>            
        </div>
                
        <div class="row col-md-6" style="margin-top:20px; margin-right:10px">	
            <div>
                <label for="documentoProbatorio"
                       class="control-label"
                       style="text-align: left;">
                    <c:choose> 
                        <c:when test="${datosHistoriaLaboralBeneficiario.tipoSolicitante!='REPRESENTANTE_LEGAL'}">
                            <spring:message code="label.solicitud.placeholder.documentoProbatorioBeneficiario" />
                        </c:when>
                        <c:when test="${datosHistoriaLaboralBeneficiario.tipoSolicitante=='REPRESENTANTE_LEGAL'}">
                            <spring:message code="label.solicitud.placeholder.documentoProbatorioRepresentante" />
                        </c:when>
                    </c:choose>

                </label>
                <span id="ayudaDocProbatorio" class="glyphicon glyphicon-question-sign"></span>
            </div>	
            <div>
                <form:select multiple="single" path="documentoProbatorio.desDocumento" id="registroDocumentoProbatorio" cssClass="form-control" >
                    <form:option value="-1" label="--Por favor seleccione--" disabled="true" selected="true"/>
                    <c:forEach var="documento" varStatus="contadorDocumento" items="${documentosProbatoriosVo}" >
                        <optgroup id="optgroup${documento.key.idTipoDocumentoProbatorio}" style="color: #101010; font-weight: 700;" label="${documento.key.descripcion} (Obligatorio)" value="${documento.key.idTipoDocumentoProbatorio}"/>
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


