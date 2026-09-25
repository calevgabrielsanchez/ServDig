<%@ include file="../../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum"%>


<link rel="stylesheet" type="text/css" 	href='<spring:url value="/static/resources/estilos/imss/reset.css" htmlEscape="true" />' />
<link rel="stylesheet" type="text/css" 	href='<spring:url value="/static/resources/estilos/imss/style.css" htmlEscape="true" />' />
<link rel="stylesheet" type="text/css"  href='<spring:url value="/static/resources/estilos/jQueryValidationEngine/validationEngine.jquery.css" htmlEscape="true"/>' />
<link rel="stylesheet" type="text/css"  href='<spring:url value="/static/resources/estilos/jQueryValidationEngine/template.css" htmlEscape="true"/>' />


<!-- seccion de estilo para los mensajes overlay -->
<style>
    #message_dialog, #error_dialog, #accept_cancel_prompt {

    /* overlay is hidden before loading */
    //display:none;

    /* standard decorations */
    width:400px;
    border:10px solid #666;

    /* for modern browsers use semi-transparent color on the border. nice! */
    border:10px solid rgba(82, 82, 82, 0.698);

    /* hot CSS3 features for mozilla and webkit-based browsers (rounded borders) */
    -moz-border-radius:8px;
    -webkit-border-radius:8px;
  }

  #message_dialog div, #error_dialog div, #accept_cancel_prompt div {
    padding:10px;
    border:1px solid #3B5998;
    background-color:#fff;
    font-family:"lucida grande",tahoma,verdana,arial,sans-serif
  }

  #message_dialog h2, #error_dialog h2, #accept_cancel_prompt h2 {
    margin:-11px;
    margin-bottom:0px;
    color:#000000;
    background-color:#F2F2F2; /* title color */
    padding:5px 10px;
    border:1px solid #3B5998;
    font-size:20px;
  }
  </style>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jQueryValidationEngine/js/languages/jquery.validationEngine-es.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jQueryValidationEngine/js/jquery.validationEngine.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/medioContactoCmp.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="../../gestionMediosContacto-web/static/resources/js/delta/mediosContacto/cmpMedioContacto.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/alta/altaCentroTrabajo.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/alta/altaClasificacion.js" htmlEscape="true" />"></script>
<!-- 
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/alta/altaEscrituraConstitutiva.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/alta/altaSindicato.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/alta/altaRepresentante.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/alta/altaSocio.js" htmlEscape="true" />"></script>
 -->
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/alta/altaRegistroPatronal.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script>
	var tipoPersonaFisica="<%=TipoPersonaEnum.FISICA.getId()%>";
	var tipoPersonaMoral="<%=TipoPersonaEnum.MORAL.getId()%>";
	var tipoPersonaFisicaEnum="<%=TipoPersonaEnum.FISICA%>";
	var tipoPersonaMoralEnum="<%=TipoPersonaEnum.MORAL%>";
	var context = "${contextpath}";
	var idSolicitud = "${solicitud.solicitudId}" !=null && "${solicitud.solicitudId}" !="" ? "${solicitud.solicitudId}" : 0;
	folio = "${solicitud.noFolioSolicitud}";
	var tipoPersonaEnTramite = "${solicitud.sujetoObligado.tipoPersonaFiscal}"!=null ?  "${solicitud.sujetoObligado.tipoPersonaFiscal.codigo}" == tipoPersonaFisica ? tipoPersonaFisica : tipoPersonaMoral : 0;
</script>

<jsp:include page="../../common/headerDivs.jsp"/>
		<table id="breadcrumbsTable" style="width: 100%; border: none;">
			<tr>
				<td style="border: none;">
					<div id="breadcrumbPaso1" style="display: none;">
						<img src="<spring:url value="/static/resources/imagenes/alta/altaPaso1AgregarRegistroPatronal.png" htmlEscape="true" />" title="Alta Patronal" />
					</div>
					<div id="breadcrumbPaso2" style="display: none;">
						<img src="<spring:url value="/static/resources/imagenes/alta/altaPaso2AgregarRegistroPatronal.png" htmlEscape="true" />" title="Alta Patronal" />
					</div>
				</td>
			</tr>
		</table>
		<form:form action="${contextpath}/afiliacion/alta/actualizarDatos" modelAttribute="sujetoTramite" id="altaPatronalForm">
			<table style="width: 920px; border: none;" >
				<tr>
					<td style="width: 100px; border: none;">
						<input type="button" id="btnAnterior" class="mboton" value="Anterior" onclick="retrocederPagina();">
					</td>
					<td style="border: none;">
						<input type="button" id="btnSiguiente" class="mboton" value="Siguiente" onclick="avanzarPagina();">
					</td>
				</tr>
			</table>
			
			<table style="display: none; width: 920px; border-width: 0px !important; text-align: center;"  id="infoFolio">
				<tr>
					<td class="label_patrones" style="text-align: right !important;">
						<label>
							<spring:message code="label.numero.folio" />:
						</label>
					</td>
					<td class="label_patrones" style="text-align: left !important;">
						<span id="noFolioActual"></span>
					</td>
				</tr>
			</table>
			<jsp:include page="datosPersonalesActuales.jsp"/>
			<div id="seccionCentroTrabajo" style="display: none;">
				<jsp:include page="datosCentroTrabajo.jsp"/>
			</div>
			<div id="seccionActividadEconomica" style="display: none;">
				<jsp:include page="datosActividadEconomica.jsp"/>
			</div>
			<table style="width: 920px; border: none;" >
				<tr>
					<td style="width: 100px; border: none;">
						<input type="button" id="btnAnterior" class="mboton" value="Anterior" onclick="retrocederPagina();">
					</td>
					<td style="width: 100px; border: none;">
						<input type="button" id="btnSiguiente" class="mboton" value="Siguiente" onclick="avanzarPagina();">
					</td>
					<td style="border: none;" align="right">
						<input type="button" id="btnCancel" class="mboton" value="Cancelar" onclick="presentarSolicitudConfirmacionCancelacion();">
						<input type="button" id="btnGuardar" class="mboton" value="Guardar" onclick="confirmarOperacion(guardarSolicitud);">
					</td>
				</tr>
			</table>		
		</form:form>
<jsp:include page="../../common/footerDivs.jsp"/>

<jsp:include page="../../common/mediosContactoForm.jsp"/>

<jsp:include page="../../common/dialogosGenericos.jsp"/>

<div id="dialogoGrids">
	<form id="formaDialogo"></form>
</div>
<form id="formSupport"></form>

<!-- message dialog -->
<div id="message_dialog" style="display:none;">
  <div>
    <h2>Informaci&oacute;n</h2>
    <p id="detalle">
     ... 
    </p>
    <!-- yes/no buttons -->
    <p>
      <button class="close"> Aceptar </button>
    </p>
  </div>
</div>

<div id="error_dialog" style="display:none;">
  <div>
    <h2>Error</h2>
    <p id="detalle">
      ...
    </p>
    <!-- yes/no buttons -->
    <p>
      <button class="close"> Aceptar </button>
    </p>
  </div>
</div>

<!-- accept/cancel prompt -->
<div id="accept_cancel_prompt" style="display:none;">
	<div>
		<h2>Confirmaci&oacute;n</h2>
		
		<p id="detalle">
			...
		</p>

		<!-- yes/no buttons -->
		<p>
			<button class="close"> Aceptar </button>
			<button class="close"> Cancelar </button>
		</p>
	</div>
</div>