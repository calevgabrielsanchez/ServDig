<%@ include file="../../layout/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum"%>

<c:set var="tipoActaNacimiento"><%=DocumentoPorTipoEnum.ACTA_NACIMIENTO.getId()%></c:set>
<c:set var="tipoCartaNaturalizacion"><%=DocumentoPorTipoEnum.CARTA_NATURALIZACION.getId()%></c:set>
<c:set var="tipoDocumentoMigratorio"><%=DocumentoPorTipoEnum.DOCUMENTO_MIGRATORIO.getId()%></c:set>
<c:set var="tipoNumeroUnicoExtranjero"><%=DocumentoPorTipoEnum.NUMERO_UNICO_EXTRANJERO.getId()%></c:set>
<c:set var="tipoCertificadoNacionalidad"><%=DocumentoPorTipoEnum.CERTIFICADO_NACIONALIDAD_MEXICANA.getId()%></c:set>
<c:set var="tipoOficioSolicitanteRef"><%=DocumentoPorTipoEnum.OFICIO_SOLICITANTE_REFUGIADO.getId()%></c:set>
<c:set var="tipoFormaMigratoriaTurista"><%=DocumentoPorTipoEnum.FORMA_MIGRATORIA_TURISTA.getId()%></c:set>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/fisica/busqueda/detalleComplementar.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/fisica/busqueda/validar.js" htmlEscape="true" />"></script>
	<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.ui.datepicker-es.js" htmlEscape="true" />"></script>
	
<div class="container">


	


	
	
      <div class="">
   
        <c:if test="${ warning == true }">
		<div class="row" >

			<div class="alert alert-danger">
				<h6> Aviso: ${mensajeException}</h6>
				Los datos proporcionados no pudieron ser validados en la entidad externa, si desea podr&aacute; realizar el
				registro de la persona complementando los datos faltantes.
			</div>
		</div>
	</c:if>
        <div class="form-comment" style="padding-right: 20px;">
					<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
					<form:form modelAttribute="fisica" id="forma" method="post" action="${contextpath}/persona/fisica/ubicar/validar" cssClass="formNotBlock">	
						
						
	
						
						<fieldset>
							<legend>
								<strong>&nbsp;Detalle de la informaci&oacute;n de la Persona F&iacute;sica seleccionada</strong>
							</legend>

							<form:hidden path="idPersona"/>
							
							<form:label path="curp" cssClass="wide">CURP</form:label>
							<form:input path="curp" id="busquedaCurp" cssStyle="width: 300px;" maxlength="18" readonly="true" cssClass="disabled" />
							<br /><br /><br />
							
							
							<form:label path="rfc" cssClass="wide">RFC</form:label>
							<form:input path="rfc" id="busquedaRfc" cssStyle="width: 300px" maxlength="13" cssClass="disabled"/>
							<br /><br /><br />
							
							<form:label path="nombre" cssClass="wide">Nombre(s)</form:label>
							<form:input path="nombre" id="busquedaNombres" cssStyle="width: 300px" maxlength="30" cssClass="disabled"/>
							<br /><br /><br />
							
							<form:label path="primerApellido" cssClass="wide">Primer Apellido</form:label>
							<form:input path="primerApellido" id="busquedaPrimerApellido" cssStyle="width: 300px" maxlength="30" cssClass="disabled"/>
							<br /><br /><br />
		
							<form:label path="segundoApellido" cssClass="wide">Segundo Apellido</form:label>
							<form:input path="segundoApellido" id="busquedaSegundoApellido"	cssStyle="width: 300px" maxlength="30" cssClass="disabled"/>
							<br /><br /><br />
							<div>
							<form:label path="sexo.idSexo" class="wide">Sexo</form:label>
							<span id="sexo.idSexoError" class=" hiddenElement error"></span>
							<combo:creaCombo idHtml="sexo.idSexo" idHtmlContenedor="forma" 
							entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo" idHtmlValor="${fisica.sexo.idSexo}"
							mostrarSoloActivos="true"/>
							</div>
							<br /><br /><br />
							<div>
							<form:label path="fechaNacimiento" class="wide">Fecha de Nacimiento</form:label>
							<span id="fechaNacimientoError" class=" hiddenElement error"></span>
							<form:input path="fechaNacimiento" id="fechaNacimiento"	style="width: 70px" maxlength="10" />
							</div>
							<br /><br /><br />
							<div>
							<form:label path="lugarNacimiento.clave" class="wide">Lugar de Nacimiento</form:label>
							<span id="lugarNacimiento.claveError" class=" hiddenElement error"></span>
							<combo:creaCombo idHtml="lugarNacimiento.clave" idHtmlContenedor="forma" 
							entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"  idHtmlValor="${fisica.lugarNacimiento.clave}"
							mostrarSoloActivos="true" />
							</div>
							<br /><br />	<br />
														
						</fieldset>
	
	</br>
	</br>
	</br>
						<div style="float: right;">
							
							
<!-- 							<button type="button" class="btn btn-secondary" id="cancelar"> Cancelar </button> -->
							
<!-- 							<button type="submit" class="btn btn-secondary" id="buscar"> Seleccionar </button> -->
							
							
							<input type="button" class="mboton" id="cancelar" value="Cancelar"/>
							<input type="button" class="mboton" id="regresar" value="Regresar"/>
							<input type="submit" class="mboton" id="buscar" value="Selecccionar"/>
							
						</div>	
						<br />
						
						<span id="errorNegocioLabel" class="error hiddenElement"></span>
						
						
						<!-- Datos del documento probatorio -->
						<c:forEach items="${fisica.documentosProbatorios}" var="documento" varStatus="index">
							<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoActaNacimiento}">
								<form:hidden path="actaNacimiento.documentoPorTipo.idDocumentoPorTipo" />
								<form:hidden path="actaNacimiento.anio" />
								<form:hidden path="actaNacimiento.noLibro" />
								<form:hidden path="actaNacimiento.noActa" />
								<form:hidden path="actaNacimiento.tomo" />
								<form:hidden path="actaNacimiento.noFoja" />
								<form:hidden path="actaNacimiento.crip" />

								<input id="actaNacimiento.noJuzgado" name="actaNacimiento.noJuzgado" value="0" type="hidden">
								<form:hidden path="actaNacimiento.municipio.entidadFederativa.clave" />
								<form:hidden path="actaNacimiento.municipio.clave" />
							</c:if>
							<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoCartaNaturalizacion}">
								<form:hidden path="cartaNaturalizacion.documentoPorTipo.idDocumentoPorTipo" />
								<form:hidden path="cartaNaturalizacion.numTipoDocumento" />
								<form:hidden path="cartaNaturalizacion.descripcionTipoDocumento" />
								<form:hidden path="cartaNaturalizacion.curp" />

								<form:hidden path="cartaNaturalizacion.numFolioExtranjero" />
								<form:hidden path="cartaNaturalizacion.anioRegistro" />
							</c:if>
							<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoDocumentoMigratorio}">
								<form:hidden path="documentoMigratorio.documentoPorTipo.idDocumentoPorTipo" />
								<form:hidden path="documentoMigratorio.numTipoDocumento" />
								<form:hidden path="documentoMigratorio.descripcionTipoDocumento" />
								<form:hidden path="documentoMigratorio.curp" />

								<form:hidden path="documentoMigratorio.numFolioExtranjero" />
							</c:if>
							<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoNumeroUnicoExtranjero}">
								<form:hidden path="numeroUnicoExtranjero.documentoPorTipo.idDocumentoPorTipo" />
								<form:hidden path="numeroUnicoExtranjero.numTipoDocumento" />
								<form:hidden path="numeroUnicoExtranjero.descripcionTipoDocumento" />
								<form:hidden path="numeroUnicoExtranjero.curp" />

								<form:hidden path="numeroUnicoExtranjero.numFolioExtranjero" />
							</c:if>
							<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoCertificadoNacionalidad}">
								<form:hidden path="certificadoNacionalidadMexicana.documentoPorTipo.idDocumentoPorTipo" />
								<form:hidden path="certificadoNacionalidadMexicana.numTipoDocumento" />
								<form:hidden path="certificadoNacionalidadMexicana.descripcionTipoDocumento" />
								<form:hidden path="certificadoNacionalidadMexicana.curp" />

								<form:hidden path="certificadoNacionalidadMexicana.numFolioExtranjero" />
								<form:hidden path="certificadoNacionalidadMexicana.anioRegistro" />
							</c:if>
							<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoOficioSolicitanteRef}">
								<form:hidden path="oficioSolicitanteRefugiado.documentoPorTipo.idDocumentoPorTipo" />
								<form:hidden path="oficioSolicitanteRefugiado.numTipoDocumento" />
								<form:hidden path="oficioSolicitanteRefugiado.descripcionTipoDocumento" />
								<form:hidden path="oficioSolicitanteRefugiado.curp" />

								<form:hidden path="oficioSolicitanteRefugiado.numFolioExtranjero" />
							</c:if>
							<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoFormaMigratoriaTurista}">
								<form:hidden path="formaMigratoriaTurista.documentoPorTipo.idDocumentoPorTipo" />
								<form:hidden path="formaMigratoriaTurista.numTipoDocumento" />
								<form:hidden path="formaMigratoriaTurista.descripcionTipoDocumento" />
								<form:hidden path="formaMigratoriaTurista.curp" />

								<form:hidden path="formaMigratoriaTurista.numFolioExtranjero" />
							</c:if>
						</c:forEach>
					</form:form>
						
					
					
				</div>
        
        
      </div>

    

    </div>