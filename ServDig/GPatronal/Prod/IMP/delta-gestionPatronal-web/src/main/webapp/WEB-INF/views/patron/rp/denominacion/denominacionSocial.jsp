<%@ include file="../../../general/taglibs.jsp"%>

<div id="tramiteDenominacionSocial">
	<div class="row">
		<div class="cell" >
			<div class="row" id="divSujetoObligado" >
				<div class="cell form-comment" id="captura" style=" float:right; width:600px !important; height: 100% !important;">
					<c:set var="sol" value="${idSolicitud}" />
					<input type="hidden" id="idSolicitud" name="idSolicitud" value="${sol}"/>
					<form:form modelAttribute="sujetoObligado" id="actualPatronForm">
						<fieldset style="margin: 20px !important;">
							<legend style="width:35%">
								<strong><spring:message code="titulo.razon.social" /> ACTUAL</strong>
							</legend>
							<c:if test="${bFisica}">
									<jsp:include page="../sujetoObligadoPersonaFisica.jsp" />
							</c:if>
							<c:if test="${!bFisica}">
									<jsp:include page="../sujetoObligadoPersonaMoral.jsp" />
							</c:if>
						</fieldset>
					</form:form>
					<c:if test="${bFisica}">
						<jsp:include page="../sujetoObligadoPersonaFisicaEdicion.jsp" />
					</c:if>
					<c:if test="${!bFisica}">
						<jsp:include page="../sujetoObligadoPersonaMoralEdicion.jsp" />
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>