<div id="info-paso" style="margin-bottom: 50px;">
	<h3>
		<spring:message
			code="label.solicitud.docuementosProbatoriosAsegurado" />
	</h3>
	<hr class="red" style="margin-bottom: 20px;">

	<div class="form-group">
            <div class="row col-md-6" style="margin-top:20px; margin-right:10px">	
                <div>
                    <label for="documentoProbatorio"
                            class="control-label"
                            style="text-align: left;"> <spring:message
                                    code="label.solicitud.placeholder.documentoProbatorioAsegurado" />
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
                                                    
            <div style="margin-top:15px" class="row col-md-12">
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
