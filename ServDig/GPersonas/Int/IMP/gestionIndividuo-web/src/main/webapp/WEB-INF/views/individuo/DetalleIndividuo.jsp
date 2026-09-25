<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.PropietarioMedioContactoEnum"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/individuo/fisica/PersonaFisicaDetalle.js" htmlEscape="true" />"></script>

<script type="text/javascript" src="../../gestionMediosContacto-web/static/resources/js/delta/mediosContacto/cmpMedioContacto.js"></script>

<script>
$(function(){
		var modInterfaz = 2;
		<c:if test="${bFisica}">
			var tpPropietario = <%=PropietarioMedioContactoEnum.PERSONA_FISICA.getCodigo()%>;
		</c:if>
		<c:if test="${!bFisica}">
			var tpPropietario = <%=PropietarioMedioContactoEnum.PERSONA_MORAL.getCodigo()%>;
		</c:if>
		var idPropietario = ${idPersona};
		var idSolicitud = 0;
		var idSujetoOblgiado = 0;
		
		var medioDeContactoPersona = new MedioContacto("medioDeContactoPersona", modInterfaz);
		medioDeContactoPersona.init();
	});
</script>
<div class="page_holder" style="margin: 0px !important; width: 650px">
	<div class=" contenedor" style="width: 100% !important">
		<div class="row">
			<div class="cell" >
				<div class="row" id="regPatronal">
					<div class="cell form-comment" id="captura" style=" float:right; width:600px !important; height: 100% !important; ">
						<fieldset style="margin: 20px !important;">				
							<legend>
								<strong><spring:message code="titulo.persona.fisica" /></strong>
							</legend>
							
							<form:form modelAttribute="fisica" id="individuoFisicoForm">
								<fieldset class="fsInterno">
									<label style="width:25%">
										<spring:message code="label.nombre" />
									</label>
									<form:input readonly="true" path="nombre" maxlength="14" cssStyle="width:70%"/>
								</fieldset>
								<fieldset class="fsInterno">
									<label style="width:25%">
										<spring:message code="label.primer.apellido" />
									</label>
									<form:input readonly="true" path="primerApellido" maxlength="14" cssStyle="width:70%"/>
								</fieldset>
								<fieldset class="fsInterno">
									<label style="width:25%">
										<spring:message code="label.segundo.apellido" />
									</label>
									<form:input readonly="true" path="segundoApellido" maxlength="14" cssStyle="width:70%"/>
								</fieldset>
								<fieldset class="fsInterno">
									<label style="width:25%">
										<spring:message code="label.fecha.nacimiento" />
									</label>
									<form:input readonly="true" path="fechaNacimiento" maxlength="14" cssStyle="width:70%"/>
								</fieldset>
								
								<fieldset class="fsInterno">
									<label style="width:25%">
										<spring:message code="label.sexo" />
									</label>
									<form:input readonly="true" path="sexo.descripcion" maxlength="14" cssStyle="width:70%"/>
								</fieldset>
						
								<fieldset class="fsInterno">
									<label style="width:25%">
										<spring:message code="label.rfc" />
									</label>
									<form:input readonly="true" path="rfc" maxlength="14" cssStyle="width:70%"/>
								</fieldset>
						
								<fieldset class="fsInterno">
									<label style="width:25%">
										<spring:message code="label.curp" />
									</label>
									<form:input readonly="true" path="curp" maxlength="14" cssStyle="width:70%"/>
								</fieldset>
							</form:form>
						</fieldset>
						<div style="width:100%;">
							<div id="medioDeContactoPersona"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>