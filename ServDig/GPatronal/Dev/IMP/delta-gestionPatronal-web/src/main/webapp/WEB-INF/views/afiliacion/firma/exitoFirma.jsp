<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../../general/taglibs.jsp"%>

<c:set var="folio"  value="${solicitudModel.noFolioSolicitud}" ></c:set>
<script type="text/javascript" src="<spring:url value='/static/resources/js/delta/afiliacion/common/commonMethods.js' htmlEscape='true' />"></script>
<script type="text/javascript" src="<spring:url value='/static/resources/js/delta/afiliacion/firmaDigitalAfiliacion.js' htmlEscape='true' />"></script>

<script language="javascript">
	var context="${contextpath}";
</script>

	<input type="hidden" id="cveIdSujetoObligado" name="cveIdSujetoObligado" value=" <c:out value="${idSujetoObligado}"/> "/>
	<input type="hidden" id="tipoPersonaFiscal" name="tipoPersonaFiscal" value="<c:out value="${tipoPersonaFiscal}" />"/>
<c:if test="${!personaFisica}">
	<input type="hidden" id="fisica.idPersona" name="fisica.idPersona" value=" <c:out value="${fisicaIdPersona}" />"/>
	<input type="hidden" id="fisica.rfc" name="fisica.rfc" value="<c:out value="${fisicaRfcPersona}"/> "/>
</c:if>
<c:if test="${!personaFisica}">
	<input type="hidden" id="moral.idPersona" name="moral.idPersona" value=" <c:out value="${moralIdPersona}"/> "/>
	<input type="hidden" id="moral.rfc" name="moral.rfc" value="<c:out value="${moralRfcPersona}"/>"/>
</c:if>
<div class="page_holder">
	<div class="contenedor">
		<div class="row">
			<div class="cell">
				<h2 style="font-size: 14px;padding-top: 25px;">La solicitud fue firmada exitosamente</h2>
				<div style="margin:0 auto 0 auto; width:850px;text-align: center;">
					<label>
						<strong>Folio:</strong>&nbsp;&nbsp;&nbsp;<span>${folio}</span>
					</label>
				</div>
				<br />
				<div style="margin:0 auto 0 auto; width:850px;">
<!-- 					<p style="text-align: justify;">Mauris mauris ante, blandit et, -->
<!-- 					ultrices a, suscipit eget, quam. Integer ut neque. Vivamus nisi -->
<!-- 					metus, molestie vel, gravida in, condimentum sit amet, nunc. Nam a -->
<!-- 					nibh. Donec suscipit eros. Nam mi. Proin viverra leo ut odio. -->
<!-- 					Curabitur malesuada. Vestibulum a velit eu ante scelerisque -->
<!-- 					vulputate.</p> -->
				</div>
				<br />
				<div style="margin:0 auto 0 auto; width:850px;text-align: center;">
				<form id="formExitoFirma" action="">
					<input type="button" class="mboton" name="finalizarProcesoFE" id="finalizarProcesoFE" onclick="ejecutarEnvioDeSolicitudconFirma()" value="Finalizar Proceso">
				</form>
				</div> 
			</div>
		</div>
	</div>
</div>

<div id="dialogoConfirmacion">
	<p><span id="textoConfirmacion"></span></p>
</div>
<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>


<c:set var="idSolicitud"  value="${firmaElectronicaModel.idSolicitud}" ></c:set>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />

