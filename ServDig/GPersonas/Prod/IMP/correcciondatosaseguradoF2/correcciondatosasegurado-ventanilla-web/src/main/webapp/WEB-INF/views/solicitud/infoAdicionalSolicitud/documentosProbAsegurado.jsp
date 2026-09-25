<script type="text/javascript">
	var contextPath="${contextpath}";
	var claveDocumentoNSS = "${documentoNSSClave}";
	
	$(document).ready(function() {
	
	var infoDocProbatorio = '<div style="font-size:11px"><p >Adjunte los documentos probatorios escaneados necesarios para el tr&aacute;mite.</p>';
        infoDocProbatorio += '<ol>';
        infoDocProbatorio += '<li>Acta de nacimiento.</li>';

        infoDocProbatorio += '<li>Identificaci&oacute;n oficial:';
        infoDocProbatorio +=  '<ol type="a">';
        infoDocProbatorio +=  '<li>Credencial para votar vigente</li>';
        infoDocProbatorio +=  '<li>Pasaporte vigente, mexicano o extranjero</li>';
        infoDocProbatorio +=  '<li>Cartilla del servicio militar nacional</li>';
        infoDocProbatorio +=  '<li>C&eacute;dula profesional</li>';
        infoDocProbatorio +=  '<li>Matr&iacute;cula consular (documento de identidad que expide una oficina consular a favor de un connacional)</li>';
        infoDocProbatorio +=  '<li>Tarjeta/c&eacute;dula/carnet de identidad para extranjeros (en caso de extranjeros)</li>';
        infoDocProbatorio +=  '<li>Documento migratorio vigente que corresponda, emitido por autoridad competente (en su caso pr&oacute;rroga o refrendo migratorio)</li>';
        infoDocProbatorio +=  '</ol>'; 
        infoDocProbatorio += '</li>';
        infoDocProbatorio += '</ol>';
	infoDocProbatorio +='</div>';
	
	$('#ayudaDocProbatorio').popover({
		animation : true,
		html : true,
		title : 'Ayuda',
		content : infoDocProbatorio,
		trigger : 'hover',
		placement : 'top',
		container : 'body'
	});
	
	$('[data-toggle="tooltip"]').tooltip();
	
});
</script>
<div class="row col-md-12">
                     
                            <input id="isDocumentoProbatorioAsegurado" type="checkbox" name="isDocumentoProbatorioAsegurado" value="isDocumentoProbatorioAsegurado"> 
                            <spring:message code="label.docsProbatorios.documentosAsegurado"/>
                     
</div>
<div id="panelDocumentosAsegurado" class="form-group">

		
            <div class="row col-md-6" style="margin-top:20px; margin-right:10px">	
                <div>
                    <label for="documentoProbatorio"
                            class="control-label"
                            style="text-align: left;"> <spring:message
                                    code="label.solicitud.placeholder.documentoProbatorio" />
                    </label>
                    <span id="ayudaDocProbatorio" class="glyphicon glyphicon-question-sign"></span>
                </div>	
                <div>
                    <form:select multiple="single" path="documentoProbatorio.desDocumento" id="registroDocumProbAsegurado" cssClass="form-control" >
                            <form:option value="-1" label="--Por favor seleccione--" disabled="true" selected="true"/>
                            <c:forEach var="documento" varStatus="contadorDocumento" items="${documProbAseguradoVo}" >
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
                    <table class="table" id='listadoDocumentosGridAsegurado' style="">
                            <tr></tr>	
                            <c:forEach var="documentoProbatorio" varStatus="contador"
                                    items="${informacionAdicional.documentoProbatorioAseguradoList}">
                                <tr id="listadoDocumentosGrid${contador.index +1}">
                                        <td width="5%" nowrap><p style="font-size: 1.5em;">${contador.index +1}</p></td>
                                        <td width="5%" nowrap><p style="font-size: 1.5em;">${documentoProbatorio.desDocumento}</p></td>
                                        <td hidden="hidden">${documentoProbatorio.nombre}</td>
                                        <td hidden="hidden">${documentoProbatorio.cveIdDocumento}</td>
                                        <td hidden="hidden">${documentoProbatorio.idDocumentoPorTipo}</td>
                                        <td hidden="hidden">${documentoProbatorio.idDocBoveda}</td>
                                        <td hidden="hidden">${documentoProbatorio.tipoDocumento}</td>
                                        <td align="right" width="90%" height="55"><p style="font-size: 1.5em;"><a href="#"
                                                onclick="return eliminarDocProbAsegurado('listadoDocumentosGrid${contador.index+1}', ${documentoProbatorio.cveIdDocumento}, '${documentoProbatorio.idDocBoveda}');">Eliminar</a></p>
                                        </td>

                                </tr>
                            </c:forEach>
                    </table>
                </div>
                <div class="row">
                    <span style="font-size:18px" id="documentoProbatorioAseguradoListError" class="error hiddenElement"></span>
                        <form:errors path="documentoProbatorioAseguradoList" cssClass="error" />
                </div> 
            </div>
                                                    
            <div style="margin-top:15px" class="row col-md-12">
                <div class="col-md-2" style="display: none;">
                        <input id="fileDataAsegurado" type="file" name="fileDataAsegurado" class="mboton">
                        <form:errors path="documentoProbatorio.nombre" cssClass="error" />
                </div>
            </div>
	   <div class="row col-md-12">
		<hr class="red" style="margin-bottom: 20px;">
		</div>
</div>
