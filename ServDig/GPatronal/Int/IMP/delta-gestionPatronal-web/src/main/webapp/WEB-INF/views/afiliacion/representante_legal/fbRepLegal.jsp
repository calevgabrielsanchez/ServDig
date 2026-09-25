<%@ include file="../../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.PropietarioMedioContactoEnum"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoAccionAfectacionEnum"%>
<style>
	#selectable .ui-selecting { background: #FECA40; }
	#selectable .ui-selected { background: #F39814; color: white; }
	#selectable { list-style-type: none; margin: 0; padding: 0; width: 100%; }
	#selectable li { margin: 3px; padding: 0.4em; font-size: 1.0em; height: 18px; }
</style>
		
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/afiliacion/representanteLegal.js" htmlEscape="true" />"></script>	

	<script>
		var tramiteRepresentanteLegalActivo = ${tramiteRepresentanteLegalActivo};
		var tramiteRepresentanteLegalRatificado = ${tramiteRepresentanteLegalRatificado};
		var tipoTramiteRepresentanteLegal = '<%=TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL%>';
		var tpPropietarioRepLegal = <%=PropietarioMedioContactoEnum.REPRESENTANTE_LEGAL.getCodigo()%>;
		var codigoOperacionGuardar = 21;
		var codigoOperacionRatificar = 22;
	</script>

		<!-- JSP principal del modulo de Representante Legal -->
		
			<c:set var="cuentaRLConActosDeAdmonDominioVar" value="<%= session.getAttribute( \"cuentaRLConActosDeAdmonDominio\") %>"/>
			<input type="hidden" id="tipoPersonaFiscalHidden" value="${tipoPersonaFiscal}">					
			<input type="hidden" id="idSolicitudRL" name="idSolicitudRL" value="${idSolicituRL}"/>
			<c:if test="${!bFisica}">

				<input type="hidden" id="idPersona" name="idPersona" value="${sujetoObligado.moral.idPersona}"/>

			</c:if>

			<c:if test="${bFisica}">

 				<input type="hidden" id="idPersona" name="idPersona" value="${sujetoObligado.fisica.idPersona}"/>

 			</c:if> 
			<input type="hidden" id="idPersonaDeshacer" name="idPersonaDeshacer" value=""/>
			<input type="hidden" id="responseHidden" name="responseHidden" value=""/>				
		
		<h3><spring:message code="titulo.informacion.actual" /></h3>
		
		<div id="lista" style="display: table-row !important;">									
			<table id="tbRepresentanteLegal" style="width: 100%; vertical-align: top;">											
					<thead></thead>
					<tbody style="width: 100%;"></tbody>
					<tfoot></tfoot>
			</table>
		</div>
					
		<form>
		<table style="width: 100%; vertical-align: top; border: none !important;">
			<tr>
				<td style="border: none !important;" >
					<div class="opciones">
						<div id="btnGridRL" class="opcion">
						
						<c:if test="${cuentaRLConActosDeAdmonDominioVar}">
								<input type="button"
								onclick="fnOpenDialogNuevoRepresentanteLegal();"
								class="mboton" value="Agregar"
								style="font-size: .8em !important;">
						</c:if>
							
								<input type="button"
								onclick="fnOpenDialogEliminarRepresentanteLegal();"
								class="mboton" value="Eliminar"
								style="font-size: .8em !important;">
								
								<input type="button"
								onclick="fnOpenDialogModificarRepresentanteLegal();"
								class="mboton" value="Modificar"
								style="font-size: .8em !important;">
						</div>
					</div>
				</td>
				<td style="border: none !important;" align="right" >
					<div id="grupoRatificarRL">
						<!-- 
						<input type="checkbox" name="chkRatificaRL" id="chkRatificaRL" onclick="ratificaRL();" />
						<b><spring:message code="label.ratificar"/></b>
						 -->
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="border: none !important;" align="center" >
					<div id="mensajeRepresentanteLegalRatificacion">
						<legend class="legendaConfirmacion">
							La informaci&oacute;n del tr&aacute;mite ha sido ratificada
						</legend>
					</div>
				</td>
			</tr>
		</table>
		</form>					
																						
		<div id="listaForSession"  style="display: table-row !important;">								
			<h3><spring:message code="titulo.tramite" /></h3>																	
			
			<table id="tbRepresentanteLegalForSession" style="width: 100%; vertical-align: top;">
				<thead></thead>
				<tbody style="width: 100%;"></tbody>				
			</table>
			<form><!-- Solo se agrega para darle estilo correcto al boton -->
				<input type="button"
					onclick="guardarRepresentante();"
					class="mboton" value="Guardar"
					style="width:200px; font-size: .8em !important;">
			</form>
		</div>
		<div id="dgNuevoRepresentanteLegal" title="Agregar Representante Legal" >		
			<jsp:include page="nuevo.jsp"></jsp:include>
		</div>
		<div id="dgEliminarRepresentanteLegal" title="&iquest;Eliminar elemento?">		
			El elemento seleccionado ser&aacute; eliminado, &iquest;Est&aacute; Ud. seguro?				
		</div>
		<div id="dgEliminarDatoContactoDetalleRL" title="&iquest;Eliminar elemento?">		
			Este dato de contacto ser&aacute; eliminado, &iquest;Est&aacute; Ud. seguro?				
		</div>
		<div id="dgEliminarRepresentanteLegalTramite" title="&iquest;Eliminar elemento?">		
			El elemento seleccionado ser&aacute; eliminado, &iquest;Est&aacute; Ud. seguro?				
		</div>
		<div id="dgDeshacerEliminarRepresentanteLegalTramite" title="&iquest;Cancelar elemento?">		
			Ser&aacute; cancelado el tr&aacute;mite seleccionado, &iquest;Est&aacute; Ud. seguro?				
		</div>
		<div id="dgErrorSinSeleccionRepresentanteLegal" title="Debe seleccionar un elemento">	
			No ha seleccionado ning&uacute;n registro para la acci&oacute;n.	
		</div>
		<div id="dgErrorSinSeleccionPersonaParaRepresentanteLegal" title="Debe seleccionar un elemento de Personas"  style="display: none;">
			Por favor seleccione la persona a registrar como Representante Legal.
		</div>
		<div id="dgErrorDuplicadoRepresentanteLegal" title="Duplicidad de elemento en tr&aacute;mite">	
			Ya existe un registro en tr&aacute;mite para el rengl&oacute;n seleccionado.	
		</div>
		<div id="dgModificarRepresentanteLegal" title="Modificar Representante Legal">
			<jsp:include page="modificar.jsp"></jsp:include>
		</div>
		<div id="dgDetalleEnTramiteRepresentanteLegal" title="Detalle de  Representante Legal en Tr&aacute;mite">
			<jsp:include page="detalleEnTramiteRepresentanteLegal.jsp"></jsp:include>
		</div>

<!-- seccion para detalle del rep legal -->
<div id="divDetalleRepLegal"   style="display:none;">
	<div class="page_holder" style="width: 550px !important;">
		<div class="contenedor">
			<div class="row" >
				<div class="cell" >
					<div class="row" id="rowDetalleSujetoObligado" >
							<legend>
								<strong>Datos de persona</strong>
							</legend>
							<table style="width: 900px !important;">
								<tr>
									<td class="label_patrones" style="width: 150px !important;">
										<label>RFC :</label>
									</td>
									<td>
										<label id="detalleRepLegalRFC"></label>
									</td>
								</tr>
								<tr>
									<td class="label_patrones">
										<label>CURP :</label>
									</td>
									<td>
										<label id="detalleRepLegalCURP"></label>
									</td>
								</tr>
								<tr>
									<td class="label_patrones">
										<label>Primer Apellido :</label>
									</td>
									<td>
										<label id="detalleRepLegalPrimerApellido"></label>
									</td>
								</tr>
								<tr>
									<td class="label_patrones">
										<label>Segundo Apellido :</label>
									</td>
									<td>
										<label id="detalleRepLegalSegundoApellido"></label>
									</td>
								</tr>
								<tr>
									<td class="label_patrones">
										<label>Nombre(s) :</label>
									</td>
									<td>
										<label id="detalleRepLegalNombre"></label>
									</td>
								</tr>
							</table>
							
							</br>
							
							<table style="width: 900px !important;">
								<tr>
									<td align="center" class="label_patrones" colspan="2">
										&iquest;Poder para Actos de administraci&oacute;n o dominio?: &nbsp;<label id="indActAdmonDominioDetalle"></label>
									</td>
								</tr>
							</table>
							
							<div id="divDetalleMediosContactoRepLegal" align="center"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
</div>