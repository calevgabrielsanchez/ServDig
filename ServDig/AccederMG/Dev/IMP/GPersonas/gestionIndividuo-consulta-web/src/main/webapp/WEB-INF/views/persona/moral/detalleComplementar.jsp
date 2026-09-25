<%@ include file="../../layout/taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/moral/busqueda/detalleComplementar.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/personas/moral/busqueda/validar.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/jquery.ui.datepicker-es.js" htmlEscape="true" />"></script>	
<div class="container">
	<div class="hero-unit">
	<c:if test="${ warning == true }">
		<div class="row" >
			<div class="alert alert-danger">
				<h6> Nota: ${mensajeException}</h6>
				Los datos proporcionados no pudieron ser validados en la entidad externa, si desea podr&aacute; realizar el
				registro de la persona complementando los datos faltantes.
			</div>
		</div>
	</c:if>
		<div class="form-comment" style="padding-right: 20px;">
			<c:set var="contextpath" value="<%=request.getContextPath()%>" />
			<form:form modelAttribute="moral" id="forma" method="post" action="${contextpath}/persona/moral/ubicar/validar" cssClass="formNotBlock">	
				<fieldset>
					<legend>
						<strong>&nbsp;Detalle de la informaci&oacute;n de la Persona F&iacute;sica seleccionada</strong>
					</legend>
					<form:hidden path="idPersona"/>
					<form:label path="rfc" cssClass="wide">RFC</form:label>
					<form:errors path="rfc" cssClass="error"></form:errors>
					<form:input path="rfc" id="busquedaRfc" cssStyle="width: 300px" maxlength="13"/>
					<br/><br/><br/>
												
					<form:label path="razonSocial" cssClass="wide">Raz&oacute;n Social</form:label>
					<form:errors path="razonSocial" cssClass="error"></form:errors>
					<form:input path="razonSocial" id="busquedaRazonSocial" cssStyle="width: 300px" maxlength="18"/>
					<br/><br/><br/>
					
					<form:label path="tipoSociedad.idTipoSociedad" cssClass="wide">Tipo Sociedad</form:label>
					<form:errors path="tipoSociedad.idTipoSociedad" cssClass="error"></form:errors>
					<combo:creaCombo idHtml="tipoSociedad.idTipoSociedad" 
									 idHtmlContenedor="forma" 
									 entidad="mx.gob.imss.ctirss.delta.persistence.DicTipoSociedad" 
									 idHtmlValor="${moral.tipoSociedad.idTipoSociedad}" 
									 mostrarSoloActivos="true"/>
					<br/><br/><br/>
					
					<form:label path="fechaCreacion" cssClass="wide">Fecha de Creaci&oacute;n</form:label>
					<form:errors path="fechaCreacion" cssClass="error"></form:errors>
					<form:input path="fechaCreacion" id="busquedaFechaCreacion" cssStyle="width: 300px" maxlength="18"/>
					<br/><br/><br/>
					
					<form:label path="actaConstitutiva" cssClass="wide">Acta Constitutiva</form:label>
					<form:errors path="actaConstitutiva" cssClass="error"></form:errors>
					<form:input path="actaConstitutiva" id="busquedaActaConstitutiva" cssStyle="width: 300px" maxlength="18"/>
					<br/><br/><br/>	
      			<c:forEach items="${moral.personaCalificaciones}" var="personaCalificacion" varStatus="index">
				<c:if test="${personaCalificacion.calificacion.idCalificacion == 3}">
					<label class="wide">Calificac&oacute;n IMSS</label>
					<input type="checkbox" id="busquedaCalificacionIMSS" value="1" checked="checked"/>
					<br/><br/><br/>	
				</c:if>
				<c:if test="${personaCalificacion.calificacion.idCalificacion == 2}">
					<label class="wide">Calificac&oacute;n SAT</label>
					<input type="checkbox"  id="busquedaCalificacionSAT" value="1" checked="checked"/>
					<br/><br/><br/>	
				</c:if>
      			</c:forEach>
					<br/><br/><br/>	
				</fieldset>
				<div style="float: right;">
					<button type="button" class="btn btn-secondary" id="cancelar"> Cancelar </button>
					<button type="submit" class="btn btn-secondary" id="buscar"> Seleccionar </button>
				</div>	
				<br/>
				<span id="errorNegocioLabel" class="error hiddenElement"></span>
			</form:form>
		</div>
	</div>
</div>
