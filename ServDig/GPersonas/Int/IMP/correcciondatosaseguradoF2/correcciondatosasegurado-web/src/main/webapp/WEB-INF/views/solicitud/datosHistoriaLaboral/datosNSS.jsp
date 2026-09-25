
<div id="info-paso" style="margin-bottom: 50px;">
	<h3>
		<spring:message
			code="label.solicitud.numerosSeguridadSocialInvolucrados" />
	</h3>
	<hr class="red" style="margin-bottom: 20px;">

	<div class="form-group">
            <div class="row col-md-12">
                <div class="row col-md-6">
                    <label for="NSS" class="control-label" style="text-align: left;">
                            <spring:message code="label.solicitud.placeholder.NSS.short" /><span>:</span>
                    </label>
                </div>
                <div class="row col-md-6">
                    <label for="documentoProbatorio"
                                class="control-label"
                                style="text-align: left;"> <spring:message
                                        code="label.solicitud.placeholder.documentoProbatorioNss" />
                    </label>
                </div>
            </div>
            <div class="row col-md-12">

                <div class="row col-md-6" style="margin-bottom:15px">
                    <spring:message code="label.solicitud.placeholder.NSS.singular"
                            var="placeHolderNSS" />
                    <div>
                        <div style="margin-right:20px;">
                            <form:input path="nssvo.NSS" id="registroNSS"
                                    cssClass="form-control" maxlength="11" style="font-size: 18px;"
                                    placeholder="${placeHolderNSS}" data-toggle="tooltip" data-placement="top" title="Debe ingresar el o los NSS que sean necesarios para la regularización de sus datos ante el IMSS."/>
                        </div>
                    </div>
                    <div class="text-left">
                    <span style="font-size:18px" id="NSSError" class="error hiddenElement"></span>
                            <form:errors path="nssvo.NSS" cssClass="error" />
                    <span style="font-size:18px" id="NSSListError" class="error hiddenElement"></span>
                	<form:errors path="NSSList" cssClass="error" />
                    </div>
                    
                    
                    
                    
                </div>
                <div class="row col-md-6">
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
            <div style="margin-top:15px" class="row col-md-12">
                <div class="col-md-2" style="display: none;">
                        <input id="fileData" type="file" name="fileData" class="mboton">
                        <form:errors path="documentoProbatorio.nombre" cssClass="error" />
                </div>
            </div>
                <div class="row col-md-12">
                    <div class="col-md-5" style="margin-right:60px;"></div>
                    
                    <div class="row col-md-6" style="text-align-right">
                        <label for="listadoDocumentosNSS" class="control-label"
                                style="text-align: left;"> <spring:message
                                        code="label.solicitud.placeholder.listadoDocumentos" />
                        </label>
                    </div>
                </div>
                <div class="row col-md-12">
                    <div class="col-md-5" style="margin-right:60px;"></div>
                    <div class="row col-md-6" style="text-align: left;">
                        <table class="table" id='listadoDocumentosNSSGrid' style="">
                            <tr></tr>	
                            <c:forEach var="documentoProbatorioNSS" varStatus="contador"
                                    items="${datosHistoriaLaboralNSS.documentoProbatorioList}">
                                    <tr id="listadoDocumentosNSSGrid${contador.index +1}">
                                            <td width="5%" nowrap><p style="font-size: 1.5em;">${contador.index +1}</p></td>
                                            <td width="5%" nowrap><p style="font-size: 1.5em;">${documentoProbatorioNSS.desDocumento}</p></td>
                                            <td hidden="hidden">${documentoProbatorioNSS.nombre}</td>
                                            <td hidden="hidden">${documentoProbatorioNSS.cveIdDocumento}</td>
                                            <td hidden="hidden">${documentoProbatorioNSS.idDocumentoPorTipo}</td>
                                            <td hidden="hidden">${documentoProbatorioNSS.idDocBoveda}</td>
                                            <td hidden="hidden">${documentoProbatorioNSS.tipoDocumento}</td>
                                            <td align="right" width="90%" height="55"><p style="font-size: 1.5em;"><a href="#"
                                                    onclick="return fnEliminarRow('listadoDocumentosNSSGrid${contador.index+1}', ${documentoProbatorioNSS.cveIdDocumento}, '${documentoProbatorioNSS.idDocBoveda}');">Eliminar</a></p>
                                            </td>
                                    </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
                <div class="row col-md-12">
                <div class="col-md-5" style="margin-right:60px;">
                </div>
                <div class="row col-md-6" style="text-align: left;">
                	<div class="row">
                        <span style="font-size:18px" id="documentoProbatorioListError" class="error hiddenElement"></span>
                            <form:errors path="documentoProbatorioList" cssClass="error" />
                    </div>
                </div>
                </div>
            <div class="row col-md-12">
                
                        <div class="pull-right" >
				<button type="button" id="limpiarNSSDocumentos"
					class="btn btn-primary">
					<spring:message code="label.limpiar" />
				</button>			
			</div>
			
			<div class="pull-right" style="margin-right:10px;">
				<button type="button" id="agregarNSSDocumentos"
					class="btn btn-primary">
					<spring:message code="label.solicitud.agregar" />
				</button>			
			</div>
	   </div>
           <div class="row col-md-12">
                <label for="listadoNSSInvolucrados" class="control-label"
                        style="text-align: left;"> <spring:message
                                code="label.solicitud.placeholder.documentoProbatorioInvolucradosNss" />
                </label>
               <br/>
            </div>
            <div class="row col-md-12">			
                <div class="row col-md-6">
                    <label for="listadoNSSInvolucrados" class="control-label"
                            style="text-align: left;"> <spring:message
                                    code="label.solicitud.placeholder.listadoNSSInvolucrados" />
                    </label>
                </div>
                <div class="col-md-6">
                    <label for="listadoDocumentos" class="control-label"
                            style="text-align: left;"> <spring:message
                                    code="label.solicitud.placeholder.listadoDocumentos" />
                    </label>
                </div>
            </div>
            <div class="row col-md-12">
                <table class="table" id='listadoNSSInvolucradosGrid' style="">
                    <tr></tr>
                    <c:forEach var="nss" varStatus="contadorNSS" items="${datosHistoriaLaboral.NSSList}">
                        <tr id="listadoNSSInvolucradosGrid${contadorNSS.index +1}">
                            <td width="5%" nowrap><p style="font-size: 1.5em;">${contadorNSS.index+1}</p></td>
                            <td width="5%" nowrap><p style="font-size: 1.5em;">${nss.NSS}</p></td>
                            <td align="right" width="30%" height="55"><p style="font-size: 1.5em;"><a href="#"
                                onclick="return fnEliminarRowNss('${nss.NSS}','listadoNSSInvolucradosGrid${contadorNSS.index+1}');">Eliminar</a></p>
                            </td>
                            <td id="table-${nss.NSS}" align="right" style="padding-top: 0px">
                                <div style="overflow-y: scroll; height: 100px;">
                                    <table class="table" id="listadoDocumentosGrid-${nss.NSS}" style="">
                                        <tr></tr>
                                        <c:forEach var="documentoProbatorio" varStatus="contador" items="${nss.documentoProbatorioList}">
                                                <tr id="listadoDocumentosGrid-${nss.NSS-contador.index +1}">
                                                    <td width="5%" nowrap><p style="font-size: 1.5em;">${contador.index +1}</p></td>
                                                    <td width="5%" nowrap><p style="font-size: 1.5em;">${documentoProbatorio.desDocumento}</p></td>
                                                    <td hidden="hidden">${documentoProbatorio.nombre}</td>
                                                    <td hidden="hidden">${documentoProbatorio.cveIdDocumento}</td>
                                                    <td hidden="hidden">${documentoProbatorio.idDocumentoPorTipo}</td>
                                                    <td hidden="hidden">${documentoProbatorio.idDocBoveda}</td>
                                                </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <div class="col-md-6 col-sm-7 col-xs-6">                
                </div>
            </div>
	</div>
</div>
