<%@ include file="../../../general/taglibs.jsp"%>

<div id="tramiteSindicato">
<div class="row">
	<div class="cell" >
	<div class="cell form-comment" id="captura" style=" float:right; width:600px !important; height: 100% !important;">
		<c:set var="sol" value="${idSolicitud}" />
		<input type="hidden" id="idSolicitud" name="idSolicitud" value="${sol}"/>
		<fieldset style="margin: 20px !important;">
			<legend style="width:35%">
				<strong><spring:message code="titulo.sindicato" /></strong>
			</legend>
			<form:form modelAttribute="registroSindicato" id="datosActualSindicatoForm">
				<jsp:include page="../registroSindicato.jsp" />
			</form:form>
			<jsp:include page="registroSindicatoEdicion.jsp" />
		</fieldset>
		</div>
</div>
	</div>
</div>
<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>