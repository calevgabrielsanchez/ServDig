<%@ include file="../general/taglibs.jsp"%>

<c:if test="${opciones.ind_mod_srt}">
	<li><a id="modificaClasificacion"
		onclick="patronAsociadoPortlet.modificarClasificacion('${registroPatronal}')">Efectuar
			tr&aacute;mite de modificaciones en el SRT</a></li>
</c:if>

<c:if test="${estadoPatron == 1 }">


<c:if test="${opciones.ind_cambio_dom_patron}">
	<li><a id="modificaDomicilioCT"
		onclick="patronAsociadoPortlet.modificarCentroTrabajo('${registroPatronal}','${indClase}', '${idTipoRegPatron}')">
			Efectuar cambio de domicilio del centro de trabajo en el mismo municipio</a></li>
</c:if>

<c:if test="${opciones.ind_cambio_dom_patron_dif_mun}">
	<li><a id="modificaDomicilioDifMun"
		onclick="patronAsociadoPortlet.modificarClasificacion('${registroPatronal}')">Efectuar tr&aacute;mite de modificaci&oacute;n de la clasificaci&oacute;n por cambio de domicilio en diferente municipio</a></li>
</c:if>


<c:if test="${opciones.ind_medios_patron}">
	<li><a id="modificaDomicilioContactoCT"
		onclick="patronAsociadoPortlet.modificarContactoCentroTrabajo('${registroPatronal}','${indClase}')">Efectuar
			cambio de datos de contacto del centro de trabajo</a></li>
</c:if>
<!--  
<c:if test="${opciones.ind_amp_reg_event_mod14}">
	<li>
		<a id="ampliacionRegistroEventualesMod14" onclick="patronAsociadoPortlet.ampliacionParaRegistrosEventualesMod14('${registroPatronal}')">
		Registro de Movimientos Afiliatorios</a>
	</li>
</c:if>
-->
<c:if test="${opciones.ind_mostrar_adeudo}">
	<li><a id="consultaEstadoAdeudo"
		onclick="patronAsociadoPortlet.mostrarEstadoDeAdeudo('${registroPatronal}')">Consultar
			estado de adeudo</a></li>
</c:if>
<c:if test="${opciones.ind_detalle_patron}">
	<li><a id="verDetalleRegistroPatronal"
		onclick="patronAsociadoPortlet.verDetalle('${registroPatronal}','${modalidad}', '${indClase }')">Ver
			detalle</a></li>
</c:if>
<c:if test="${opciones.ind_comprobante_fiscal}">
	<li><a id="obtenerComprobanteFiscal"
		onclick="patronAsociadoPortlet.obtenerComprobanteFiscal('${registroPatronal}')">Obtener
			comprobantes fiscales</a></li>
</c:if>
<c:if test="${opciones.ind_riesgo_trabajo_terminado}">
<li><a href="#" id="PortletRiesgoTrabajo" onclick="patronAsociadoPortlet.riesgoTrabajo('${registroPatronal}')"> <spring:message
			code="label.button.riesgosTrabajo" />
</a></li>
</c:if>

<c:if test="${opciones.ind_riesgo_trabajo_terminado_rfc}">
<li><a href="#" id="PortletRiesgoTrabajoRfc" onclick="patronAsociadoPortlet.riesgoTrabajoRfc('${rfc}')"> <spring:message
			code="label.button.riesgosTrabajoRfc" />
</a></li>
</c:if>

<c:if
	test="${!opciones.ind_mod_srt && !opciones.ind_cambio_dom_patron && !opciones.ind_medios_patron && !opciones.ind_mostrar_adeudo && !opciones.ind_detalle_patron && !opciones.ind_comprobante_fiscal}">
	<%@ include file="sinOpciones.jsp"%>
</c:if>

<c:if test="${opciones.ind_registro_obra}">
	<li><a id="idLnkRegistroObra" onclick="patronAsociadoPortlet.registroObra('${registroPatronal}')">Registro de obra</a></li>
</c:if>
<c:if test="${opciones.ind_escrito_des}">
	<li><a id="idLnkRegistroObra" onclick="patronAsociadoPortlet.escrito('${registroPatronal}')">Presentaci&oacute;n de Escrito de desacuerdo</a></li>
</c:if>

<c:if test="${opciones.ind_consulta_incap_folio}">
	<li><a id="idLnkFolioIncapacidades" onclick="patronAsociadoPortlet.folioIncapacidades('${registroPatronal}')">Consulta Incapacidades - Folio de incapacidades</a></li>
</c:if>

<c:if test="${opciones.ind_rango_fechas}">
	<li><a id="idLnkRangoFechas" onclick="patronAsociadoPortlet.rangoFechas('${registroPatronal}')">Consulta Incapacidades - Rango de fechas</a></li>
</c:if>

<c:if test="${opciones.ind_nss}">
	<li><a id="idLnkNSS" onclick="patronAsociadoPortlet.nss('${registroPatronal}')">Consulta Incapacidades - NSS</a></li>
</c:if>

<c:if test="${opciones.ind_reembolso_subsidios}">
	<li><a id="idLnkReembolsoSubsidios" onclick="patronAsociadoPortlet.reembolsoSubsidios('${registroPatronal}')">Consulta estado de cuenta por reembolso de subsidios (factura convenio)</a></li>
</c:if>

<c:if test="${opciones.ind_nmps_bandeja_incap}">
    <li><a id="idLnkBandejaIncap" onclick="patronAsociadoPortlet.bandeja('${registroPatronal}')">Calificar incapacidades</a></li>
</c:if>

<c:if test="${opciones.ind_nmps_validacion_cta_clabe}">
	<li><a id="idLnkValidaCuenta" onclick="patronAsociadoPortlet.validaCuenta('${registroPatronal}')">Validaci&oacute;n de cuenta CLABE</a></li>
</c:if>

</c:if>
