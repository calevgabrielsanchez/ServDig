<%@ include file="../../layout/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ page
	import="mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum"%>


<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/fisica/busqueda/detalle.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/personas/fisica/busqueda/terminar.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/jquery/jquery.ui.datepicker-es.js" htmlEscape="true" />"></script>

<div class="container">
	<div class="">

		<c:if test="${ warning == true }">
			<div class="row">

				<div class="alert alert-danger">
					<h6>Aviso: ${mensajeException}</h6>
					Los datos proporcionados no pudieron ser validados en la entidad
					externa, si desea podr&aacute; realizar el registro de la persona
					complementando los datos faltantes.
				</div>
			</div>
		</c:if>
		
		<div class="form-comment" style="padding-right: 20px;">
			<c:set var="contextpath" value="<%=request.getContextPath()    %>" />

			<form:form modelAttribute="fisica" id="forma" method="post" 
				action="${contextpath}/persona/fisica/ubicar/validar"
				cssClass="formNotBlock">

				<fieldset>
					<legend>
						<strong>&nbsp;Detalle de la informaci&oacute;n de la
							Persona F&iacute;sica seleccionada</strong>
					</legend>

					<form:hidden path="idPersona" />

					<form:label path="curp" cssClass="wide">CURP</form:label>
					<form:input path="curp" cssStyle="width: 300px;" maxlength="18"
						readonly="true" cssClass="disabled" />
					<c:if test="${not empty datosRespuestSession.datosICA.cambios && datosRespuestSession.datosICA.cambios['curp'].id ne 4}">
						<span class="label label-danger" title="CAMBIO">CAMBIO</span>
					</c:if>
					<br /> <br /> <br />


					<form:label path="rfc" cssClass="wide">RFC</form:label>
					<form:input path="rfc" cssStyle="width: 300px" maxlength="13"
						cssClass="disabled" />
					<c:if test="${not empty datosRespuestSession.datosICA.cambios && datosRespuestSession.datosICA.cambios['rfc'].id ne 4}">
						<span class="label label-danger" title="CAMBIO">CAMBIO</span>
					</c:if>
					<br /> <br /> <br />

					<form:label path="nombre" cssClass="wide">Nombre(s)</form:label>
					<form:input path="nombre" cssStyle="width: 300px" maxlength="30"
						cssClass="disabled" />
					<c:if test="${not empty datosRespuestSession.datosICA.cambios && datosRespuestSession.datosICA.cambios['nombre'].id ne 4}">
						<span class="label label-danger" title="CAMBIO">CAMBIO</span>
					</c:if>	
					<br /> <br /> <br />

					<form:label path="primerApellido" cssClass="wide">Primer Apellido</form:label>
					<form:input path="primerApellido" cssStyle="width: 300px"
						maxlength="30" cssClass="disabled" />
					<c:if test="${not empty datosRespuestSession.datosICA.cambios && datosRespuestSession.datosICA.cambios['primerApellido'].id ne 4}">
						<span class="label label-danger" title="CAMBIO">CAMBIO</span>
					</c:if>
					<br /> <br /> <br />

					<form:label path="segundoApellido" cssClass="wide">Segundo Apellido</form:label>
					<form:input path="segundoApellido" cssStyle="width: 300px"
						maxlength="30" cssClass="disabled" />
					<c:if test="${not empty datosRespuestSession.datosICA.cambios && datosRespuestSession.datosICA.cambios['segundoApellido'].id ne 4}">
						<span class="label label-danger" title="CAMBIO">CAMBIO</span>
					</c:if>
					<br /> <br /> <br />

					<div>
						<form:label path="sexo.idSexo" class="wide">Sexo</form:label>
						<span id="sexo.idSexoError" class=" hiddenElement error"></span>
						<combo:creaCombo idHtml="sexo.idSexo" idHtmlContenedor="forma"
							entidad="mx.gob.imss.ctirss.delta.persistence.DicSexo"
							idHtmlValor="${fisica.sexo.idSexo}" 
							mostrarSoloActivos="true" />
						<c:if test="${not empty datosRespuestSession.datosICA.cambios && datosRespuestSession.datosICA.cambios['sexo'].id ne 4}">
							<span class="label label-danger" title="CAMBIO">CAMBIO</span>
						</c:if>
					</div>
					<br /> <br /> <br />
					
					<div>
						<form:label path="fechaNacimiento" class="wide">Fecha de Nacimiento</form:label>
						<span id="fechaNacimientoError" class=" hiddenElement error"></span>
						<form:input path="fechaNacimiento" id="fechaNacimiento"
							style="width: 70px" maxlength="10" readonly="true"
							cssClass="disabled" />
						<c:if test="${not empty datosRespuestSession.datosICA.cambios && datosRespuestSession.datosICA.cambios['fechaNacimiento'].id ne 4}">
							<span class="label label-danger" title="CAMBIO">CAMBIO</span>
						</c:if>
					</div>
					<br /> <br /> <br />
					
					<div>
						<form:label path="lugarNacimiento.clave" class="wide">Lugar de Nacimiento</form:label>
						<span id="lugarNacimiento.claveError" class=" hiddenElement error"></span>
						<combo:creaCombo idHtml="lugarNacimiento.clave"
							idHtmlContenedor="forma"
							entidad="mx.gob.imss.ctirss.delta.persistence.DgCatEstado"
							idHtmlValor="${fisica.lugarNacimiento.clave}" 
							mostrarSoloActivos="true"/>
						<c:if test="${not empty datosRespuestSession.datosICA.cambios && datosRespuestSession.datosICA.cambios['lugarNacimiento'].id ne 4}">
							<span class="label label-danger" title="CAMBIO">CAMBIO</span>
						</c:if>
					</div>
					<br /> <br /> <br />

				</fieldset>

				<br>
				<br>
				<br>

				<div style="float: right;">

					<input type="button" class="mboton" id="cancelar" value="Cancelar" />
					<input type="button" class="mboton" id="regresar" value="Regresar" />
					<input type="button" class="mboton" id="buscar"
						value="Selecccionar" />

				</div>
				<br />

				<span id="errorNegocioLabel" class="error hiddenElement"></span>

			</form:form>
		</div>
	</div>
</div>