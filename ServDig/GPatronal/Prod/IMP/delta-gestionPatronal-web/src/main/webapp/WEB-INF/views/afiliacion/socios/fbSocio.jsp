<%@ include file="../../general/taglibs.jsp"%>
<%@ include file="detalleEnTramiteSocios.jsp" %>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.PropietarioMedioContactoEnum"%>

<style>
	#selectable .ui-selecting { background: #FECA40; }
	#selectable .ui-selected { background: #F39814; color: white; }
	#selectable { list-style-type: none; margin: 0; padding: 0; width: 100%; }
	#selectable li { margin: 3px; padding: 0.4em; font-size: 1.0em; height: 18px; }
</style>
		
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/afiliacion/socios.js" htmlEscape="true" />"></script>
	
<script>
var tpPropietarioSocioPersonaFisica;
var idPropietarioSocioPersonaFisica;

tpPropietarioSocioPersonaFisica = <%=PropietarioMedioContactoEnum.SOCIO.getCodigo()%>;
idPropietarioSocioPersonaFisica = 0;

	var tramiteSocioActivo = ${tramiteSocioActivo};
	var tramiteSocioRatificado = ${tramiteSocioRatificado};
	var tipoTramiteSocio = '<%=TipoTramiteEnum.ACTUALIZACION_SOCIO%>';
	
	var codigoOperacionGuardarSocio = 21;
	var codigoOperacionRatificarSocio = 22;
</script>
				
		<!-- JSP principal del modulo de Socios -->
				 
			<input type="hidden" id="tipoPersonaFiscalHiddenSocio" value="${sujetoObligado.tipoPersonaFiscal}">					
			<input type="hidden" id="idSolicitudSocio" name="idSolicitudSocio" value="${idSolicitudSocio}"/>
			<input type="hidden" id="idPersonaMoralSocio" name="idSolicitudSocio" value="${moral.idPersona}"/>
			<input type="hidden" id="idPersonaFisicaSocio" name="idSolicitudSocio" value="${fisica.idPersona}"/>
			<input type="hidden" id="responseSocioHidden" name="responseSocioHidden" value=""/>		
			<input type="hidden" id="tipoSocioModificacion" name="tipoSocioModificacion" value=""/>

			<!-- datos necesarios para deshacer eliminar socio (caso de socios extranjeros con residencia extranjera) -->
			<input type="hidden" id="idPersonaDeshacerSocio" name="idPersonaDeshacerSocio" value=""/>
			<input type="hidden" id="esNacionalStrDeshacerSocio" name="esNacionalStrDeshacerSocio" value=""/>
			<input type="hidden" id="esDomicilioNacionalStrDeshacerSocio" name="esDomicilioNacionalStrDeshacerSocio" value=""/>
			<input type="hidden" id="primerApellidoDeshacerSocio" name="primerApellidoDeshacerSocio" value=""/>
			<input type="hidden" id="segundoApellidoDeshacerSocio" name="segundoApellidoDeshacerSocio" value=""/>
			<input type="hidden" id="nombresDeshacerSocio" name="nombresDeshacerSocio" value=""/>
			<input type="hidden" id="nombreRazonSocialDeshacerSocio" name="nombreRazonSocialDeshacerSocio" value=""/>
			<input type="hidden" id="idTipoPersonaDeshacerSocio" name="idTipoPersonaDeshacerSocio" value=""/>
			<input type="hidden" id="tipoPersonaDescripcionDeshacerSocio" name="tipoPersonaDescripcionDeshacerSocio" value=""/>
		
		<h3><spring:message code="titulo.informacion.actual" /></h3>
		<div id="listaSocio" style="display: table-row !important;">									
			<table id="tbSocio" style="width: 100%; vertical-align: top;">											
					<thead></thead>
					<tbody style="width: 100%;"></tbody>
					<tfoot></tfoot>
			</table>
		</div>				
		<form>
			<table style="width: 100%; vertical-align: top; border: none !important;">
				<tr>
					<td style="border: none !important;">
						<div class="opciones">
							<div id="btnGridSocio" class="opcion">
								<input type="button"
								onclick="fnOpenDialogNuevoSocio();"
								class="mboton" value="Agregar"
								style="font-size: .8em !important;">
							
								<input type="button"
								onclick="fnOpenDialogEliminarSocio();"
								class="mboton" value="Eliminar"
								style="font-size: .8em !important;">
								
								<input type="button"
								onclick="fnOpenDialogModificarSocio();"
								class="mboton" value="Modificar"
								style="font-size: .8em !important;">
							</div>
						</div>
					</td>
					<td style="border: none !important;" align="right">
						<div id="grupoRatificarSocio">
							<!-- 
							<input type="checkbox" name="chkRatificaSocio" id="chkRatificaSocio" onclick="ratificaSocio();" />
							<b><spring:message code="label.ratificar"/></b>
							 -->
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="border: none !important;" align="center">
						<div id="mensajeSocioRatificacion">
							<legend class="legendaConfirmacion">
								La informaci&oacute;n del tr&aacute;mite ha sido ratificada
							</legend>
						</div>
					</td>
				</tr>						
			</table>					
		</form>															
		
																		
		<div id="listaForSessionSocio"  style="width: 100%; display: table-row !important;">
		<h3><spring:message code="titulo.tramite" /></h3>								
			<table id="tbSocioForSession" style="width: 100%; vertical-align: top;">
				<thead></thead>
				<tbody style="width: 100%;"></tbody>				
			</table>				
		</div>
		<form><!-- Solo se agrega para darle estilo correcto al boton -->
			<input type="button"
				onclick="guardarSocio();"
				class="mboton" value="Guardar"
				style="width:200px; font-size: .8em !important;">
		</form>
		
	
		<div id="dgNuevoSocio" title="Agregar elemento" >		
			<jsp:include page="nuevo.jsp"></jsp:include>
		</div>
		<div id="dgModificarSocioFisico" title="Modificar Socio" >		
			<jsp:include page="modificarSocioFisico.jsp"></jsp:include>
		</div>
		<div id="dgModificarSocioMoral" title="Modificar Socio" >		
			<jsp:include page="modificarSocioMoral.jsp"></jsp:include>
		</div>
		<div id="dgModificarSocioFideicomiso" title="Modificar Socio" >		
			<jsp:include page="modificarSocioFideicomiso.jsp"></jsp:include>
		</div>
		<div id="dgEliminarSocio" title="&iquest;Eliminar elemento?" style="display: none;">		
			El elemento seleccionado ser&aacute; eliminado, &iquest;Est&aacute; Ud. seguro?				
		</div>
		<div id="dgEliminarSocioTramite" title="&iquest;Eliminar elemento?" style="display: none;">		
			El elemento seleccionado ser&aacute; eliminado, &iquest;Est&aacute; Ud. seguro?				
		</div>
		<div id="dgDeshacerEliminarSocioTramite" title="&iquest;Cancelar elemento?" style="display: none;">		
			Ser&aacute; cancelado el tr&aacute;mite seleccionado, &iquest;Est&aacute; Ud. seguro?				
		</div>
		<div id="dgErrorSinSeleccionSocio" title="Debe seleccionar un elemento" style="display: none;">	
			No ha seleccionado ning&uacute;n registro para la acci&oacute;n.	
		</div>
		<div id="dgErrorDuplicadoSocio" title="Duplicidad de elemento en tr&aacute;mite" style="display: none;">	
			Ya existe un registro en tr&aacute;mite para el rengl&oacute;n seleccionado.	
		</div>
		<div id="dgErrorSinSeleccionPersonaParaSocio" title="Debe seleccionar un elemento de Personas"  style="display: none;">
			Por favor seleccione la persona a registrar como Socio.
		</div>
		<div id="dgModificarSocio" title="Modificar elemento">
			<jsp:include page="modificar.jsp"></jsp:include>
		</div>
		<div id="dgDetalleSocio" style="display: none;">
			<jsp:include page="detalleSocio.jsp"></jsp:include>
		</div>