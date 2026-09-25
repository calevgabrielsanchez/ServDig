
<script type="text/javascript">
	var contextPath="${contextpath}";
	var claveDocumentoNSS = "${documentoNSSClave}";
	var tipoDocActas = "${documentosActa}";
	var tipoDocId = "${documentosIdentificacion}";
	var personaSolicitante= "${datosHistoriaLaboral.tipoSolicitante}";
	var personaBeneficiario = "${datosHistoriaLaboral.tipoBeneficiario}";
	var arregloTipoDocs = [];
	
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
		
            var infoNSS='<div style="font-size:11px"><p>Debe ingresar el o los NSS que sean necesarios para la regularizaci&oacute;n de sus datos ante el IMSS.</p></div>';
		
            //PENDIENTE CONFIRMACION
            var infoAsegurado = '<div style="font-size:11px"><p >Adjunte los siguientes documentos:</p>';
            infoAsegurado += '<li>Beneficiario</li>'
            infoAsegurado += '- Identificaci&oacute;n oficial.(Aplica para los cuatro)<br>';
            infoAsegurado += '<li>Representante Legal</li>'
            infoAsegurado += '- Acta de nacimiento.<br>';
            infoAsegurado += '- Poder notarial.<br>';
            infoAsegurado +='</div>';
		
		$('#ayudaDocProbatorioBeneficiario').popover({
			animation : true,
			html : true,
			title : 'Ayuda',
			content : infoDocProbatorio,
			trigger : 'hover',
			placement : 'right',
			container : 'body'
		});
		
		$('#ayudaNss').popover({
			animation : true,
			html : true,
			title : 'Ayuda',
			content : infoNSS,
			trigger : 'hover',
			placement : 'right',
			container : 'body'
		});
		
		//PENDIENTE CONFIRMACIÓN
		$('#ayudaDoctos').popover({
			animation : true,
			html : true,
			title : 'Ayuda',
			content : infoAsegurado,
			trigger : 'hover',
			placement : 'right',
			container : 'body'
		});
		
		$('[data-toggle="tooltip"]').tooltip();
		
	});
	
</script>
	<div class="row col-md-12">
         	 <c:choose> 
                    <c:when test="${informacionAdicional.tipoSolicitante=='BENEFICIARIO'}">
					
							 <input id="isDocumentoBeneficiario" type="checkbox" name="isDocumentoBeneficiario" value="isDocumentoBeneficiario"> 
							 <spring:message code="label.docsProbatorios"/>
					
                    </c:when>
                    <c:when test="${informacionAdicional.tipoSolicitante=='REPRESENTANTE_LEGAL'}">
                       
							 <input id="isDocumentoBeneficiario" type="checkbox" name="isDocumentoBeneficiario" value="isDocumentoBeneficiario"> 
							 <spring:message code="label.docsProbatorios"/>
						
                    </c:when>
                </c:choose>
    </div>
<div id="panelDocumentosBeneficiario" class="form-group">
    <div class="row col-md-6" style="margin-top:20px; margin-right:10px">	
        <div>
            <label for="documentoProbatorio"
                    class="control-label"
                    style="text-align: left;">
                <c:choose> 
                    <c:when test="${informacionAdicional.tipoSolicitante=='BENEFICIARIO'}">
                     <spring:message code="label.solicitud.placeholder.documentoProbatorioBeneficiario" />
                    </c:when>
                    <c:when test="${informacionAdicional.tipoSolicitante=='REPRESENTANTE_LEGAL'}">
                         <spring:message code="label.solicitud.placeholder.documentoProbatorioRepresentante" />
                    </c:when>
                </c:choose>

            </label>
            <span id="ayudaDocProbatorioBeneficiario" class="glyphicon glyphicon-question-sign"></span>
        </div>	
        <div>
            <form:select multiple="single" path="documentoProbatorio.desDocumento" id="registroDocumentoProbatorio" cssClass="form-control" >
                    <form:option value="-1" label="--Por favor seleccione--" disabled="true" selected="true"/>
                    <c:forEach var="documento" varStatus="contadorDocumento" items="${documProbBeneficiarioVo}" >
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
                            items="${informacionAdicional.documentoProbatorioBeneficiarioList}">
                        <tr id="listadoDocumentosGrid${contador.index +1}">
                                <td width="5%" nowrap><p style="font-size: 1.5em;">${contador.index +1}</p></td>
                                <td width="5%" nowrap><p style="font-size: 1.5em;">${documentoProbatorio.desDocumento}</p></td>
                                <td hidden="hidden">${documentoProbatorio.nombre}</td>
                                <td hidden="hidden">${documentoProbatorio.cveIdDocumento}</td>
                                <td hidden="hidden">${documentoProbatorio.idDocumentoPorTipo}</td>
                                <td hidden="hidden">${documentoProbatorio.idDocBoveda}</td>
                                <td hidden="hidden">${documentoProbatorio.tipoDocumento}</td>
                                <td align="right" width="90%" height="55"><p style="font-size: 1.5em;"><a href="#"
                                        onclick="return eliminarDocProbBenf('listadoDocumentosGrid${contador.index+1}', ${documentoProbatorio.cveIdDocumento}, '${documentoProbatorio.idDocBoveda}');">Eliminar</a></p>
                                </td>

                        </tr>
                    </c:forEach>
            </table>
        </div>
        <div class="row">
            <span style="font-size:18px" id="documentoProbatorioBeneficiarioListError" class="error hiddenElement"></span>
                <form:errors path="documentoProbatorioBeneficiarioList" cssClass="error" />
        </div> 
    </div>

    <div style="margin-top:15px" class="row col-md-8">
        <div class="col-md-2" style="display: none;">
                <input id="fileData" type="file" name="fileData" class="mboton">
                <form:errors path="documentoProbatorio.nombre" cssClass="error" />
        </div>
    </div>
</div>
