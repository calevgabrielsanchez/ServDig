<%@ include file="../general/taglibs.jsp"%>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>


<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/mediosContacto/mediosContacto.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()    %>" />

<form:form action="${contextpath}/medios/contacto/guardar" method="POST"
	modelAttribute="medioContactoFormWrapper" id="form">
	<div class="page_holder">
		<div class=" contenedor">
			<div class="row">
				<div class="cell">

					<br />

					<div class="row">
						<div class="row">
							<h2>Medios de contacto</h2>
							<h3 style="font-size: .9em; color: #666666">Paso 1 / 2</h3>
						</div>
					</div>
					<br />

					<jsp:include page="formMedioContactoCommon.jsp"></jsp:include>

				<div class="row">
						<div id="control" style="float: right;">
							<input type="submit" value="Aceptar" class="mboton" />
						</div>
					</div> 
				</div>
			</div>
		</div>
	</div>
</form:form>
