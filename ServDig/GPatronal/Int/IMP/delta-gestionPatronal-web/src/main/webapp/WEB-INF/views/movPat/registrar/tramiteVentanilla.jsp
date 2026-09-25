<%@ include file="../../general/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<script>
	var context="${contextpath}";
	var context_path="${contextpath}";
</script>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/movPat/registrar/tramiteVentanilla.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/movPat/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src=" <spring:url value="/static/resources/js/movPat/delta/atributosPersonaCtrl.js" htmlEscape="true" />"></script>
<script type="text/javascript" src=" <spring:url value="/static/resources/js/movPat/wizard/modificacion/patron/clasificacion/modificacionPatronClasificacionVentanillaWizard.js" htmlEscape="true" />"></script>


<link rel="stylesheet" type="text/css"
	href='<spring:url value="/static/resources/estilos/imss/movPat/menuLateralStyle.css" htmlEscape="true" />' />

<div class="">
	<div class="contenedor" style="width: 100% !important">
		<div id="divRp" >
			<fieldset style="margin: 20px !important;">
				<table>
					<tr>
						<td class="label_patrones"><legend>
								<strong> <spring:message code="label.nrp" />
								</strong>
							</legend></td>
						<td><input type="text" id="numRegistroPatronal"
							maxlength="11" /></td>

					</tr>
					<tr>
						<td><input type="button" id="btnConsultarRegistroPatronal"
							onclick="navegarADetalleDeRP();" class="mboton" name="consultar"
							value="Registrar Tr&aacute;mite" /></td>
						<td><input type="button" id="btnCancelarRp"
							class="mboton" name="cancelar"
							value="Cancelar" /></td>


					</tr>
				</table>

			</fieldset>
		</div>
		


	</div>
</div>



		<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>

<!-- Divs de soporte para abrir los dialogos de las aplicaciones utilitarias -->
<div id="wizardModificacionClasificacionVentanilla"></div>
<div id="wizardModifPatronClasificacionContenido"></div>


<div id="wizardDatosActualizacion"></div>
<div id="wizardRegistroRepresentado"></div>
<div id="wizardAltaPatronal"></div>
<div id="wizardRecuperacionPatron"></div>
<div id="firmaDigitalComponent"></div>
<div id="domiciliosComponent"></div>
<div id="procesandoSolicitudComponent"></div>
<div id="detalleSolicitudComponent"></div>
<div id="doctosRequeridosTramite"></div>
<div id="wizardModificacionCentroTrabajo"></div>
<div id="wizardModificacionContactoCentroTrabajo"></div>
<div id="wizardImpresionReportesCobranza"></div>
<div id="wizardEstadoAdeudoDiv"></div>
<div id="wizardBeneficioRiss"></div>
<div id="reporteFrame"></div>
<div id="wizardRegistroDerechohabiente"></div>
<div id="divCapturaDocs"></div>
<div id="wizardObtencionComprobanteFiscal"></div>
<div id="wizardRegistroObra"></div>
<div id="wizardAltaSeguroVoluntario"></div>
<div id="datosFiscalesComponent"></div>
<div id="wizardRegistroMovimientos"></div>
<div id="wizardNMPSDivAsegurado"></div>
<div id="wizardNMPSDiv"></div>