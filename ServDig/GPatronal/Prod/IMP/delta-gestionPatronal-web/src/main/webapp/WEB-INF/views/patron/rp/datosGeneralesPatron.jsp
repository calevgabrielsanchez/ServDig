<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/datosGeneralesPatron.js" htmlEscape="true" />"></script>

<div class="page_holder">
	<div class=" contenedor" style="width: 100% !important">
		<div class="row">
			<div class="cell" >
				<div class="row" id="datoGeneralesPatron" style="width: 1000px;" >
					<c:set var="contextpath" value="<%=request.getContextPath()%>" />
					<form>
						<div class="izquierda">
							<input type="button" class="mboton" name="regresar"
							onclick="history.back();"
							value="<spring:message code="label.regresar" />"></input>
						</div>
					</form>
					<div class="cell form-comment" id="captura"
						style="float: right; width: 600px !important; height: 100% !important;">
						<form:form modelAttribute="sujetoObligado" id="patronVistaFormDG">
							<fieldset style="margin: 20px !important;">
								<legend style="width:35%">
									<strong><spring:message code="titulo.razon.social" /></strong>
								</legend>
								<c:if test="${bFisica}">
									<jsp:include page="sujetoObligadoPersonaFisica.jsp" />
								</c:if>
								<c:if test="${!bFisica}">
									<jsp:include page="sujetoObligadoPersonaMoral.jsp" />
								</c:if>
								<c:if test="${mostrarOpcionActualizacionRazonSocial}">
									<div class="derecha">
										<input type="button" onclick="abrirTramiteDenominacionSocial();"
											class="mboton" name="aDatosGenerales"
											value="<spring:message code="label.modificar"/>"/>
									</div>
								</c:if>
							</fieldset>
						</form:form>
						<form:form modelAttribute="sujetoObligado" id="datosContactoFormDG">
							<fieldset style="margin: 20px !important;">
								<legend>
									<strong><spring:message code="titulo.datos.contacto" /></strong>
								</legend>
								<jsp:include page="datosDeContacto.jsp" />
								<c:if test="${mostrarOpcionActualizacionDatosContacto}">
									<div class="derecha">
										<input type="button" onclick="abrirTramiteDatosContacto();"
											class="mboton" name="aDatosContacto"
											value="<spring:message code="label.modificar" />"/>
									</div>
								</c:if>
							</fieldset>
						</form:form>
						<c:if test="${!bFisica}">
							<form:form id="formActualizarEscrituraConstitutivaDG"
								modelAttribute="escrituraConstitutiva">
								<fieldset style="margin: 20px !important;">
									<legend>
										<strong><spring:message code="titulo.escritura.const" /></strong>
									</legend>
									<jsp:include page="escrituraConstitutiva.jsp" />
									<c:if test="${mostrarOpcionActualizacionEscrituraConstitutiva}">
										<div class="derecha">
											<input type="button" onclick="abrirTramiteEscrituraConstitutiva();"
												class="mboton" name="aEscrituraConstitutiva"
												value="<spring:message code="label.modificar" />"/>
										</div>
									</c:if>
								</fieldset>
							</form:form>

							<form:form id="formActualizarRegistroSindicatoDG"
								modelAttribute="registroSindicato">
								<fieldset style="margin: 20px !important;">
									<legend>
										<strong> <spring:message code="titulo.sindicato" /></strong>
									</legend>
									<form:hidden path="cveRegistroSindicato" />
									<form:hidden path="cveIdPersonaMoral" />
									<form:hidden path="cveIdPatronSujetoObligado"/>							
									<jsp:include page="registroSindicato.jsp" />
									<c:if test="${mostrarOpcionActualizacionRegistroSindicato}">
										<div class="derecha">
											<input type="button" onclick="abrirTramiteSindicato();" class="mboton" name="aRegistrosindicato"
												value="<spring:message code="label.modificar" />"/>
										</div>
									</c:if>
								</fieldset>
							</form:form>
						</c:if>
						<jsp:include page="denominacion/denominacionSocial.jsp" />
						<jsp:include page="contacto/tramiteDatoContacto.jsp" />
						<jsp:include page="acta/tramiteActaConstitutiva.jsp" />
						<jsp:include page="sindicato/tramiteSindicato.jsp" />
						<c:set var="contextpath" value="<%=request.getContextPath()%>" />
						<form id="formaAcuse" name="formaAcuse" action="${contextpath}/sujetoObligado/desplegarAcuse" method="POST" target="_blank"></form>
						<fieldset style="margin: 20px !important;">
							<legend>
								<strong>
									<spring:message code="titulo.dom.Fiscal" />
								</strong>
							</legend>
							<c:if test="${sujetoObligado.domicilioFiscal.clave != null}">
								<script type="text/javascript">
									$.ajax({ 
								        url: '/${mvn.web.app.rootDomicilios}/domicilio/nacional/detalle/${sujetoObligado.domicilioFiscal.clave}', 
								        success: function(data) {
								          $('div#domFiscal').html(data);           
								        }
								      });
								</script>
							</c:if>
							<c:if test="${sujetoObligado.domicilioFiscal.clave == null}">
								<spring:message code="err.domicilio.fiscal.no.localizado" />
							</c:if>
						</fieldset>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>