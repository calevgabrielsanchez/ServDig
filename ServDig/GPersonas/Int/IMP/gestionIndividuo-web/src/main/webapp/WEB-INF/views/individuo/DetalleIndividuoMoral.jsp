<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/individuo/moral/PersonaMoralDetalle.js" htmlEscape="true" />"></script>

<div class="page_holder" style="margin: 0px !important; width: 650px">
	<div class=" contenedor" style="width: 100% !important">
		<div class="row">
			<div class="cell" >
				<div class="row" id="regPatronal">
					<div class="cell form-comment" id="captura" style=" float:right; width:600px !important; height: 100% !important; ">
						<fieldset style="margin: 20px !important;">				
							<legend>
								<strong><spring:message code="titulo.persona.moral" /></strong>
							</legend>
							
							<form:form modelAttribute="moral" id="individuoMoralForm">
								
								<fieldset class="fsInterno">
									<label style="width:25%">
										<spring:message code="label.razon.social" />
									</label>
									<form:input readonly="true" path="razonSocial" maxlength="14" cssStyle="width:70%"/>
								</fieldset>
								<fieldset class="fsInterno">
									<label style="width:25%">
										<spring:message code="label.tipo.sociedad" />
									</label>
									<form:input readonly="true" path="tipoSociedad.descripcion" maxlength="14" cssStyle="width:70%"/>
								</fieldset>
							</form:form>
						</fieldset>					
					</div>
				</div>
			</div>
		</div>
	</div>
</div>