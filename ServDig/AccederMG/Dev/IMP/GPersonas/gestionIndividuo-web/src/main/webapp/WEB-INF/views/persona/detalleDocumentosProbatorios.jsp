<%@ include file="taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum"%>

<c:set var="tipoActaNacimiento"><%=DocumentoPorTipoEnum.ACTA_NACIMIENTO.getId()%></c:set>
<c:set var="tipoCartaNaturalizacion"><%=DocumentoPorTipoEnum.CARTA_NATURALIZACION.getId()%></c:set>
<c:set var="tipoDocumentoMigratorio"><%=DocumentoPorTipoEnum.DOCUMENTO_MIGRATORIO.getId()%></c:set>
<c:set var="tipoNumeroUnicoExtranjero"><%=DocumentoPorTipoEnum.NUMERO_UNICO_EXTRANJERO.getId()%></c:set>
<c:set var="tipoCertificadoNacionalidad"><%=DocumentoPorTipoEnum.CERTIFICADO_NACIONALIDAD_MEXICANA.getId()%></c:set>
<c:set var="tipoOficioSolicitanteRef"><%=DocumentoPorTipoEnum.OFICIO_SOLICITANTE_REFUGIADO.getId()%></c:set>
<c:set var="tipoFormaMigratoriaTurista"><%=DocumentoPorTipoEnum.FORMA_MIGRATORIA_TURISTA.getId()%></c:set>

<c:forEach items="${fisica.documentosProbatorios}" var="documento" varStatus="index">
	<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoActaNacimiento}">
		<h3>
			<a href='#' class="actualiza_combo_hijo">
				DOCUMENTOS PROBATORIOS (Acta de nacimiento)
			</a>
		</h3>
		<div id="documentosProbatoriosDiv">
			<input id="actaNacimiento.noJuzgado" name="actaNacimiento.noJuzgado" value="0" type="hidden">
			<form:hidden path="actaNacimiento.documentoPorTipo.idDocumentoPorTipo"/>

			<form:label path="actaNacimiento.anio" cssClass="wide">Año de Registro</form:label>
			<form:input path="actaNacimiento.anio" id="anio" style="width: 70px;" maxlength="4" />
			<br/><br/><br/>
			
			<form:label path="actaNacimiento.tomo" cssClass="wide">Tomo</form:label>
			<form:input path="actaNacimiento.tomo" id="tomo" style="width: 70px;" maxlength="3" />
			<br/><br/><br/>
			
			<form:label path="actaNacimiento.crip" cssClass="wide">CRIP</form:label>
			<form:input path="actaNacimiento.crip" id="crip" style="width: 70px;" maxlength="15" />
			<br/><br/><br/>

			<form:label path="actaNacimiento.noFoja" cssClass="wide">Foja</form:label>
			<form:input path="actaNacimiento.noFoja" id="foja" style="width: 70px;" maxlength="5" />
			<br/><br/><br/>	
			
			<form:label path="actaNacimiento.noLibro" cssClass="wide">Libro</form:label>
			<form:input path="actaNacimiento.noLibro" id="libro" style="width: 70px;" maxlength="4" />
			<br/><br/><br/>	

			<form:label path="actaNacimiento.noActa" cssClass="wide">N&uacute;mero de Acta</form:label>
			<form:input path="actaNacimiento.noActa" id="numeroActa" style="width: 70px;" maxlength="5" />
			<br/><br/><br/>	
			
			<!-- Combo papa -->
			<c:if test="${not isBusquedaPersonaFisica}">
				<form:label path="actaNacimiento.municipio.entidadFederativa.clave" cssClass="wide">Entidad de Registro</form:label>
				<combo:creaCombo idHtml				="actaNacimiento.municipio.entidadFederativa.clave" 
								 idHtmlContenedor	="documentosProbatoriosDiv" 
								 entidad			="mx.gob.imss.ctirss.delta.persistence.DgCatEstado" 
								 idHtmlValor		="${fisica.actaNacimiento.municipio.entidadFederativa.clave}" 
								 mostrarSoloActivos="true"/>
				<br/><br/>
				
					
				<!-- Combo hijo -->
				<form:label path="actaNacimiento.municipio.clave" cssClass="wide">Municipio de Registro</form:label>
				<combo:creaCombo 	entidad			="mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio" 
	                   					idHtml			="actaNacimiento.municipio.clave" 
	                   				  	entidadPadre	="dgCatEstado.cveEnt"
	                   				  	idHtmlPadre		="actaNacimiento.municipio.entidadFederativa.clave"
	                   				  	idHtmlContenedor="documentosProbatoriosDiv"
	                   				  	idHtmlValor		="${fisica.actaNacimiento.municipio.clave}" 
	                   				  	mostrarSoloActivos="false"/> 
	                    
				<br/><br/>
			</c:if>
			<c:if test="${isBusquedaPersonaFisica}">
				<form:label path="actaNacimiento.municipio.entidadFederativa.nombre" cssClass="wide">Entidad de registro</form:label>
				<form:input path="actaNacimiento.municipio.entidadFederativa.nombre" id="desEntidadRegistro" style="width: 200px;" maxlength="50" />
				<br/><br/><br/>

				<form:label path="actaNacimiento.municipio.nombre" cssClass="wide">Municipio de registro</form:label>
				<form:input path="actaNacimiento.municipio.nombre" id="desMunicipioRegistro" style="width: 200px;" maxlength="50" />
				<br/><br/><br/>
			</c:if>
		</div>
	</c:if>
	<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoCartaNaturalizacion}">
		<h3>
			<a href='#' class="actualiza_combo_hijo">
				DOCUMENTOS PROBATORIOS (<c:out value="${documento.documentoPorTipo.documento.desDocumento }" />)
			</a>
		</h3>
		<div id="documentosProbatoriosDiv">
			<form:hidden path="cartaNaturalizacion.documentoPorTipo.idDocumentoPorTipo"/>
			<form:hidden path="cartaNaturalizacion.numTipoDocumento" />
			<form:hidden path="cartaNaturalizacion.descripcionTipoDocumento" />
			<form:hidden path="cartaNaturalizacion.curp" />

			<form:label path="cartaNaturalizacion.numFolioExtranjero" cssClass="wide">Folio</form:label>
			<form:input path="cartaNaturalizacion.numFolioExtranjero" id="numFolioExtranjero" style="width: 70px;" />
			<br/><br/><br/>	

			<form:label path="cartaNaturalizacion.anioRegistro" cssClass="wide">A&ntilde;o registro</form:label>
			<form:input path="cartaNaturalizacion.anioRegistro" id="anioRegistro" style="width: 70px;" />
			<br/><br/>
		</div>
	</c:if>
	<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoDocumentoMigratorio}">
		<h3>
			<a href='#' class="actualiza_combo_hijo">
				DOCUMENTOS PROBATORIOS (<c:out value="${documento.documentoPorTipo.documento.desDocumento }" />)
			</a>
		</h3>
		<div id="documentosProbatoriosDiv">
			<form:hidden path="documentoMigratorio.documentoPorTipo.idDocumentoPorTipo"/>
			<form:hidden path="documentoMigratorio.numTipoDocumento" />
			<form:hidden path="documentoMigratorio.descripcionTipoDocumento" />
			<form:hidden path="documentoMigratorio.curp" />

			<form:label path="documentoMigratorio.numFolioExtranjero" cssClass="wide">Folio</form:label>
			<form:input path="documentoMigratorio.numFolioExtranjero" id="numFolioExtranjero" style="width: 70px;" />
			<br/><br/>
		</div>
	</c:if>
	<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoNumeroUnicoExtranjero}">
		<h3>
			<a href='#' class="actualiza_combo_hijo">
				DOCUMENTOS PROBATORIOS (<c:out value="${documento.documentoPorTipo.documento.desDocumento }" />)
			</a>
		</h3>
		<div id="documentosProbatoriosDiv">
			<form:hidden path="numeroUnicoExtranjero.documentoPorTipo.idDocumentoPorTipo"/>
			<form:hidden path="numeroUnicoExtranjero.numTipoDocumento" />
			<form:hidden path="numeroUnicoExtranjero.descripcionTipoDocumento" />
			<form:hidden path="numeroUnicoExtranjero.curp" />

			<form:label path="numeroUnicoExtranjero.numFolioExtranjero" cssClass="wide">Folio</form:label>
			<form:input path="numeroUnicoExtranjero.numFolioExtranjero" id="numFolioExtranjero" style="width: 70px;" />
			<br/><br/>
		</div>
	</c:if>
	<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoCertificadoNacionalidad}">
		<h3>
			<a href='#' class="actualiza_combo_hijo">
				DOCUMENTOS PROBATORIOS (<c:out value="${documento.documentoPorTipo.documento.desDocumento }" />)
			</a>
		</h3>
		<div id="documentosProbatoriosDiv">
			<form:hidden path="certificadoNacionalidadMexicana.documentoPorTipo.idDocumentoPorTipo"/>
			<form:hidden path="certificadoNacionalidadMexicana.numTipoDocumento" />
			<form:hidden path="certificadoNacionalidadMexicana.descripcionTipoDocumento" />
			<form:hidden path="certificadoNacionalidadMexicana.curp" />

			<form:label path="certificadoNacionalidadMexicana.numFolioExtranjero" cssClass="wide">Folio</form:label>
			<form:input path="certificadoNacionalidadMexicana.numFolioExtranjero" id="numFolioExtranjero" style="width: 70px;" />
			<br/><br/><br/>	

			<form:label path="certificadoNacionalidadMexicana.anioRegistro" cssClass="wide">A&ntilde;o registro</form:label>
			<form:input path="certificadoNacionalidadMexicana.anioRegistro" id="anioRegistro" style="width: 70px;" />
			<br/><br/>
		</div>
	</c:if>
	<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoOficioSolicitanteRef}">
		<h3>
			<a href='#' class="actualiza_combo_hijo">
				DOCUMENTOS PROBATORIOS (<c:out value="${documento.documentoPorTipo.documento.desDocumento }" />)
			</a>
		</h3>
		<div id="documentosProbatoriosDiv">
			<form:hidden path="oficioSolicitanteRefugiado.documentoPorTipo.idDocumentoPorTipo"/>
			<form:hidden path="oficioSolicitanteRefugiado.numTipoDocumento" />
			<form:hidden path="oficioSolicitanteRefugiado.descripcionTipoDocumento" />
			<form:hidden path="oficioSolicitanteRefugiado.curp" />

			<form:label path="oficioSolicitanteRefugiado.numFolioExtranjero" cssClass="wide">Folio</form:label>
			<form:input path="oficioSolicitanteRefugiado.numFolioExtranjero" id="numFolioExtranjero" style="width: 70px;" />
			<br/><br/>
		</div>
	</c:if>
	<c:if test="${documento.documentoPorTipo.idDocumentoPorTipo eq tipoFormaMigratoriaTurista}">
		<h3>
			<a href='#' class="actualiza_combo_hijo">
				DOCUMENTOS PROBATORIOS (<c:out value="${documento.documentoPorTipo.documento.desDocumento }" />)
			</a>
		</h3>
		<div id="documentosProbatoriosDiv">
			<form:hidden path="formaMigratoriaTurista.documentoPorTipo.idDocumentoPorTipo"/>
			<form:hidden path="formaMigratoriaTurista.numTipoDocumento" />
			<form:hidden path="formaMigratoriaTurista.descripcionTipoDocumento" />
			<form:hidden path="formaMigratoriaTurista.curp" />

			<form:label path="formaMigratoriaTurista.numFolioExtranjero" cssClass="wide">Folio</form:label>
			<form:input path="formaMigratoriaTurista.numFolioExtranjero" id="numFolioExtranjero" style="width: 70px;" />
			<br/><br/>
		</div>
	</c:if>
</c:forEach>
