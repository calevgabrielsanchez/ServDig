<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/detalleRPAfiliacion.js" htmlEscape="true" />"></script>

<div class="page_holder">
	<div class=" contenedor" style="width: 100% !important">
		<div class="row">
			<div class="cell" >
			<!-- falta aqui el div principal -->
				<c:set var="contextpath" value="<%=request.getContextPath()%>" />
				<form>
					<div class="izquierda">
						<input type="button" class="mboton" name="regresar"
						onclick="history.back();"
						value="<spring:message code="label.regresar" />"></input>
					</div>
				</form>
			</div>
		</div>
		
		<div class="row">
			<div class="cell form-comment" id="captura" style=" float:right; width:600px !important; height: 100% !important; ">
					
				<form:form modelAttribute="sujetoObligado"  action="${contextpath}/afiliacion/algo" id="patronForm">
				
					<form:hidden path="cveIdSujetoObligado"/>
					<form:hidden path="moral.idPersona" id="idPersonaMoral"/>
					<form:hidden path="fisica.idPersona" id="idPersonaFisica"/>
					
					<input type="hidden" id="bFisicaHidden" value="${bFisica}"/>
					
					
					<fieldset style="margin: 20px !important;">
					
						<c:if test="${bFisica}">
							<legend>
								<strong><spring:message code="title.datos.patron" /></strong>
							</legend>
							<jsp:include page="sujetoObligadoPersonaFisica.jsp" />
						</c:if>
						
						<c:if test="${!bFisica}">
							<legend>
								<strong><spring:message code="titulo.razon.social" /></strong>
							</legend>
							<jsp:include page="sujetoObligadoPersonaMoral.jsp" />
						</c:if>
					
					</fieldset>
					
					<fieldset style="margin: 20px !important;">
						<legend>
							<strong>
								<spring:message code="titulo.dom.Fiscal" />
							</strong>
						</legend>
						
						<jsp:include page="afiliacion/domicilioFiscal.jsp" />
					</fieldset>
					
					<fieldset style="margin: 20px !important;">
						<legend>
							<strong>
								<spring:message code="titulo.datos.contacto" />
							</strong>
						</legend>
						
						<div style="display: table-row !important;">
							<fieldset class="fsInterno" >
								<label>
									Buscar:
								</label>
								
								<!--<input id="txtBuscar" name="txtBuscar" maxlength="50"/>-->
							</fieldset>
							
							<table id="gridDatosContacto" style="width: 100%; vertical-align: top;">
								<thead>
								</thead>
								<tbody style="width: 100%;">
								</tbody>
								<tfoot>
								</tfoot>
							</table>
							
							<!--<div class="derecha">
								<input type="button" class="mboton" name="ConsultarRL"
									onclick="validaSeleccionSol()"
									value="<spring:message code="label.ver.detalle" />"/>
							</div>-->
							
						</div>
					</fieldset>
					
				</form:form>
			</div>
		</div>
	</div>
</div>