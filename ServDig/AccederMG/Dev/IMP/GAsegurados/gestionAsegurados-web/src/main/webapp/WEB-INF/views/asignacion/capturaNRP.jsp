<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/asignacion/cargarArchivoAsegurado.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<div class="contenedor">
	<c:choose>
		<c:when test="${not empty PATRON_SIE}">
			<c:set var="URL" value="${contextpath}/asignacion/confirmar-patron" />
			<c:set var="LABEL_BTN" value="CONTINUAR" />
		</c:when>
		<c:otherwise>
			<c:set var="URL" value="${contextpath}/asignacion/validar-patron" />
			<c:set var="LABEL_BTN" value="BUSCAR PATR&Oacute;N" />
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${not empty PATRON_SIE}">
			<div class="alert alert-info">A continuaci&oacute;n se
				muestra el Nombre Comercial del patr&oacute;n capturado, si esta
				de acuerdo con la informaci&oacute;n mostrada de click en el
				bot&oacute;n CONTINUAR.</div>
		</c:when>
		<c:otherwise>
			<div class="alert alert-info">A continuaci&oacute;n debe
				capturar el registro patronal de la Instituci&oacute;n Educativa
				que corresponde al archivo a cargar.</div>
		</c:otherwise>
	</c:choose>

	<form:form modelAttribute="uploadItem" id="uploadFileForm"
		action="${URL}" method="post" cssClass="form-horizontal">

		<form:errors path="errorFormGeneral" cssClass="alert alert-danger"
			element="div" />

		<div class="form-group">
			<form:label path="erpName" cssClass="col-sm-3 col-sm-offset-1 control-label">
				<c:if test="${ empty PATRON_SIE}">
					<span class="error">*</span>
				</c:if>
				Registro Patronal
			</form:label>
		
			<c:choose>
				<c:when test="${ not empty PATRON_SIE}">
					<div class="col-sm-5">
						<p class="form-control-static">
							${uploadItem.erpName}
						</p>
					</div>
					<form:hidden path="erpName" />
				</c:when>
				<c:otherwise>
					<div class="col-sm-5">
						<form:input path="erpName" id="erpName"
							cssStyle="text-transform: uppercase; margin-bottom: 0px;"
							cssClass="form-control"
							maxlength="11" />
						<form:errors path="erpName" cssClass="error" />
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		<c:if test="${ not empty PATRON_SIE}">
			<div class="form-group">
				<form:label path="nombreComercialERP"
					cssClass="col-sm-3 col-sm-offset-1 control-label">
					Nombre Comercial
				</form:label>
				<div class="col-sm-5">
					<p class="form-control-static">
						${uploadItem.nombreComercialERP}
					</p>
				</div>
				<form:hidden path="nombreComercialERP" />
			</div>
		</c:if>
		<div class="form-group text-right m-t-lg">
			<div class="col-sm-offset-4 col-sm-5">
				<button id="cargarArchivo" type="submit" class="btn btn-primary">${LABEL_BTN}</button>
			</div>
		</div>
	</form:form>
</div>