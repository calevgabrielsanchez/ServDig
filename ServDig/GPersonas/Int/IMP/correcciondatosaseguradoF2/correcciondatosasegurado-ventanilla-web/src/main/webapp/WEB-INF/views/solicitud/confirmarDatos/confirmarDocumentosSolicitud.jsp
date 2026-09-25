<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="info-paso" style="margin-bottom: 50px;">
	<h3>
		<spring:message
			code="label.solicitud.placeholder.documentoProbatorioInvolucradosNss" />
	</h3>
	<hr class="red" style="margin-bottom: 20px;"/>
	<div class="row">
		<div class="col-md-12">
				<div class="col-md-6">
					<label for="listadoNSSInvolucrados" class="control-label"
						style="text-align: left;"> <spring:message
							code="label.solicitud.placeholder.listadoNSSInvolucrados" />
					</label>
				</div>
				<div class="col-md-6">
					<label for="listadoNSSInvolucrados" class="control-label"
						style="text-align: left;"> <spring:message
							code="label.solicitud.placeholder.listadoDocumentos" />
					</label>
				</div>
		</div>
				<div class="col-md-12">
					<table class="table" id='listadoNSSInvolucradosGrid' style="">
						<tr></tr>
                                                <c:set var="countnss" value="0" scope="page" />
						<c:forEach var="correcionNss" varStatus="contador" items="${solicitud.tramites[0].listaNssCorreccion}">
                                                        <tr id="listadoNSSInvolucradosGrid${countnss +1}">
                                                            <td width="5%" nowrap><p style="font-size: 1.5em;">${contador.index+1}</p></td>
                                                            <td width="45%" nowrap><p style="font-size: 1.5em;">${correcionNss.nss}</p></td>
                                                            <td width="50%" id="table-${tramite.listaNSS[0]}" align="right" style="padding-top: 0px">
                                                                <c:set var="countnss" value="${countnss + 1}" scope="page"/>
                                                                <div style="overflow-y: scroll; height: 100px;">
                                                                    <table class="table" id="listadoDocumentosGrid-${correcionNss.nss}" style="">
                                                                        <tr></tr>
                                                                        <c:set var="countdoc" value="0" scope="page" />
                                                                        <c:forEach var="documentoProbatorio" varStatus="contadordocu" items="${correcionNss.documentosProbatorios}">
                                                                                
                                                                                    <tr id="listadoDocumentosGrid-${correcionNss.nss-countdoc +1}">
                                                                                        <td width="5%" nowrap><p style="font-size: 1.5em;">${countdoc +1}</p></td>
                                                                                        <td><p style="font-size: 1.5em;"><a href="${contextpath}/wizard/correccionDatosAsegurado/obtenerDocumento/${solicitud.solicitudId}/${solicitud.noFolioSolicitud}/${documentoProbatorio.nomNombreDocumento}/${documentoProbatorio.bovedaDocId}." target="_blank">${documentoProbatorio.documentoPorTipo.documento.desDocumento}
                                                                                        </a>
                                                                                        </p></td>
                                                                                    </tr>
                                                                                    <c:set var="countdoc" value="${countdoc + 1}" scope="page"/>
                                                                        </c:forEach>
                                                                    </table>
                                                                </div>
                                                            </td>
                                                        </tr>
						</c:forEach>
					</table>
				</div>
		
	</div>

</div>