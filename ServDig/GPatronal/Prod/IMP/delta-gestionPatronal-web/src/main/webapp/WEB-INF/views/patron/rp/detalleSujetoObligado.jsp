<%@ include file="../../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/integracion/IntegracionIndividuoFisico.js" htmlEscape="true" />"></script>

<script>

$(document).ready(
		function() {
			$.getScript("/gestionIndividuo-web/static/resources/js/delta/individuo/fisica/PersonaFisicaDetalleInvoker.js", function(){
			IndividuoFisicoInvoker.init('personaFisicaDiv');
			IndividuoFisicoInvoker.setOnCloseCallback(fnOnReadyIndividuoFisico);
		});
);

var fnOnReadyIndividuoFisico = function(){
	var objetoIndividuo = this;
	alert(objetoIndividuo.nombre);
}
</script>

<div id="personaFisicaDiv"/>


<div class="page_holder">
	<div class="contenedor" style="width: 100% !important">
		<div class="row">
			<div class="cell">
				<div class="row" id="rowDetalleSujetoObligado" style="width: 1000px;" >
					<c:set var="contextpath" value="<%=request.getContextPath()%>" />
					<form>
						<div class="izquierda">
							<input type="button" class="mboton" name="regresar"
							onclick="history.back();"
							value="<spring:message code="label.regresar" />"></input>
						</div>
					</form>
					<div class="cell form-comment" id="celdaCaptura" style=" float:right;  width:600px !important; height: 100% !important; ">
						<div class="row">
							<h2><spring:message code="title.informacion.general"/></h2>
						</div>
						<div class="row">
							<h3>Sujeto Obligado</h3>
							<p>A continuaci&oacute;n se presenta la informaci&oacute;n general (Persona F&iacute;sica/Moral) y sus registros patronales</p>
						</div>
						<c:set var="contextpath" value="<%=request.getContextPath()%>" />
						<form:form modelAttribute="sujetoObligado" action="${contextpath}/sujetoObligado/detalleRP" id="registroForm">
							<fieldset style="margin: 20px !important;">
								<legend>
									<strong><spring:message code="titulo.sujeto.obligado" /></strong>
								</legend>
								<c:if test="${bFisica}">
									<jsp:include page="sujetoObligadoPersonaFisica.jsp" />
								</c:if>
								<c:if test="${!bFisica}">
									<jsp:include page="sujetoObligadoPersonaMoral.jsp" />
								</c:if>
							</fieldset>
							<fieldset style="margin: 20px !important;">
								<legend>
										<strong><spring:message code="titulo.registros.patronales" /></strong>
								</legend>
								<form:errors path="*" cssClass="error" />
								<c:forEach items="${sujetoObligado.sujetosObligados}" varStatus="gridRow" var="registro" >
										<form:hidden path="sujetosObligados[${gridRow.index}].tipoPersonaFiscal"/>
										<form:hidden path="sujetosObligados[${gridRow.index}].cveIdSujetoObligado"/>
										<form:hidden path="sujetosObligados[${gridRow.index}].tipoPersonaFiscal" />
										<fieldset class="fsInterno">
											<form:radiobutton path="cveIdSujetoObligado" value="${gridRow.index}" cssStyle="width:10%"/>
											<form:input readonly="true" path="sujetosObligados[${gridRow.index}].numeroRegistroPatronal" cssStyle="width:50%"/>
										</fieldset>
								</c:forEach>
								<div class="derecha">
									<input type="submit" class="mboton" name="Detalle RP" value="<spring:message code="label.detalle.rp" />"></input>
								</div>
							</fieldset>
						</form:form>
					</div>
				</div>	
			</div>
		</div>
	</div>
</div>