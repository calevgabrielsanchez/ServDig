<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="combo"
	uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>


<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/recuperacionRP/setNombreComercial.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/procesaErrores.js" htmlEscape="true" />"></script>

<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<div class="container-fluid">
	<div>
		<label class="alert alert-info">El patr&oacute;n localizado no
			cuenta con nombre comercial, si desea agregar el nombre comercial
			introd&uacute;zcalo:</label>
	</div>


	<form id="formNomC" class="form-horizontal" role="form">
		<input type="hidden" id="cveIdSujetoObligado"
			name="cveIdSujetoObligado" value="${cvePatron}">
		<div class="form-group">
			<label for="numeroRegistroPatronalError"
				class="col-sm-3 control-label"> <span class="required">*</span>
				Nombre Comercial:
			</label>
			<div class="col-sm-3">
				<input type="text" id="nombreComercial" name="nombreComercial"
					value="" maxlength="120" class="form-control caracterNotaria" /> 
				<span id="nombreComercial" class="error hiddenElement"></span>
			</div>

		</div>
		<br>
		<div>
			<div>
				<input type="button" class="btn btn-primary" id="aceptarNC"
					value="CONTINUAR" />
			</div>
		</div>

	</form>

</div>