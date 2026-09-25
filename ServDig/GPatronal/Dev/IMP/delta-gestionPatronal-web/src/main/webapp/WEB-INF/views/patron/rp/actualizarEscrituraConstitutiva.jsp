<%@ include file="../../general/taglibs.jsp"%>

<div id="modificar" style="float: left; width: 100%; padding-top: 10px;"
	class="page_holder dialogo_holder">
	<h2 style="font-size: 1.0em !important;">Actualizar escritura
		constitutiva</h2>
	<p>
		<span class="info">Modifique los datos requeridos:</span>
	</p>


	<form:form modelAttribute="escrituraConstitutiva"
		id="formActualizarEscrituraConstitutiva">

		<fieldset style="margin: 20px !important;">

			<legend>
				<strong> <spring:message code="titulo.escritura.const" /></strong>
			</legend>

			<fieldset class="fsInterno">
				<label class=""> <spring:message
						code="label.escritura.conts.num.esc" />
				</label>
				<form:input path="numEscritura"
					maxlength="14" size="15" />
			</fieldset>
			<fieldset class="fsInterno">
				<label class=""> <spring:message
						code="label.escritura.conts.notaria" />
				</label>
				<form:input path="numNotaria"
					maxlength="14" size="15" />
			</fieldset>
<!-- 			<fieldset class="fsInterno"> -->
<%-- 				<label class=""> <spring:message --%>
<%-- 						code="label.escritura.conts.fecha" /> --%>
<!-- 				</label> -->
<%-- 				<form:input readonly="true" --%>
<%-- 					path="fechaExpedicion" --%>
<%-- 					maxlength="14" size="15" /> --%>
<!-- 			</fieldset> -->
			<fieldset class="fsInterno">
				<label class=""> <spring:message
						code="label.escritura.conts.folio" />
				</label>
				<form:input
					path="folioMercantil"
					maxlength="14" size="15" />
			</fieldset>
			<fieldset>
			
			<form:hidden path="cveEscrituraConstitutiva" />






<%-- 				<form:hidden path="tipoPersonaFiscal" /> --%>
<%-- 				<form:hidden path="registroPatronal" /> --%>


				<%-- 			<form:hidden path="moral" /> --%>
				<%-- 			<form:hidden path="fisica" /> --%>


			</fieldset>
		</fieldset>
		<!-- 		<input type="submit" value="Actualizar datos" /> -->
	</form:form>
</div>