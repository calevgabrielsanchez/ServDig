<%@ include file="../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.ModuloEnum"%>
<script>
	var bFisicaVarDatosGeneralesEncabezado = '${bFisica}';
	
	$(document).ready(function(){
		$.getScript("/gestionIndividuo-consulta-web/static/resources/js/delta/personas/notificacion/NotificacionesCtrl.js", function(){
			
			var idPersona = null;
			var isMoral = null;
			
			if(bFisicaVarDatosGeneralesEncabezado == 'true') {
				idPersona = $('#idNotifPersonaFisica').val();
				isMoral = false;
			} else {
				idPersona = $('#idNotifPersonaMoral').val();
				isMoral = true;
			}
			
			
			NotificacionesCtrl.datosEntrada.idPersona = idPersona;
			NotificacionesCtrl.datosEntrada.idModulo = <%=ModuloEnum.PATRONES.getCodigo()%>;
			NotificacionesCtrl.datosEntrada.isMoral = isMoral;
			NotificacionesCtrl.datosEntrada.idContenedor = "numNotifDivSujOblig";
			
			NotificacionesCtrl.consultarNotificaciones();
		});	
	});
</script>

<c:if test="${bFisica}">
		<input type="hidden" id="idNotifPersonaFisica" value="${sujetoObligado.fisica.idPersona}"/>
		<div id="numNotifDivSujOblig"></div>
		
		<table style="border: none; width: 100%" >
			<tr>
				<td width="20%" class="label_patrones">
					<label style="width:25%">
						<spring:message code="label.primer.apellido" />:
					</label>
				</td>
				<td width="30%" class="label_patrones_data">
					<label>
						<c:out value="${sujetoObligado.fisica.primerApellido}"></c:out>
					</label>
				</td>
				<td width="20%" class="label_patrones">
					<label style="width:25%">
						<spring:message code="label.rfc" />:
					</label>
				</td>
				<td width="30%" class="label_patrones_data">
					<label>
						<c:out value="${sujetoObligado.fisica.rfc}"></c:out>
					</label>
				</td>
			</tr>
			<tr>
				<td width="20%" class="label_patrones">
					<label style="width:25%">
						<spring:message code="label.segundo.apellido" />:
					</label>
				</td>
				<td width="30%" class="label_patrones_data">
					<label>
						<c:out value="${sujetoObligado.fisica.segundoApellido}"></c:out>
					</label>
				</td>
				<td width="20%" class="label_patrones">
					<label style="width:25%">
					<!-- no hay una etiqueta CURP a nivel general, se toma la correspondiente a rep legal-->
						<spring:message code="rep.legal.curp" />
					</label>
				</td>
				<td width="30%" class="label_patrones_data">
					<label>
						<c:out value="${sujetoObligado.fisica.curp}"></c:out>
					</label>
				</td>
			</tr>
			<tr>
				<td width="20%" class="label_patrones">
					<label style="width:25%">
						<spring:message code="label.nombres" />:
					</label>
				</td>
				<td width="30%" class="label_patrones_data">
					<label>
						<c:out value="${sujetoObligado.fisica.nombre}"></c:out> 
					</label>
				</td>
			</tr>
			<tr>
				<td width="20%" class="label_patrones">
					<label style="width:25%">
						Medios de Contacto Fiscales
					</label>
				</td>
				<td width="30%" class="label_patrones_data" colspan="3">
					<c:if test="${ sujetoObligado.fisica.mediosContactoFiscales!=null }">
						<c:forEach var="medioContactoF" items="${sujetoObligado.fisica.mediosContactoFiscales}">
							${ medioContactoF.tipoMedioContacto.descripcion }: ${ medioContactoF.desFormaContacto };
						</c:forEach>
					</c:if>
				</td>
			</tr>
		</table>
</c:if>

<c:if test="${!bFisica}">
		<input type="hidden" id="idNotifPersonaMoral" value="${sujetoObligado.moral.idPersona}"/>
		<div id="numNotifDivSujOblig"></div>
		
		<table style="width: 100%; border: none">
			<tr>
				<td class="label_patrones" style="width: 150px !important;" >
					<label>
						<spring:message code="label.rfc" />:
					</label>
				</td>
				<td class="label_patrones_data" style="width: 100px !important;">
					<label>
						<c:out value="${sujetoObligado.moral.rfc}"></c:out>
					</label>
				</td>
				<td class="label_patrones" style="width: 200px !important;">
					<label>
						<spring:message code="label.razon.social" />:
					</label>
				</td>
				<td class="label_patrones_data">
					<label>
						<c:out value="${sujetoObligado.moral.razonSocial}"></c:out>
					</label>
				</td>
			</tr>
			<tr>
				<td class="label_patrones">
					<label >
						<spring:message code="label.tipo.sociedad" />:
					</label>
				</td>
				<td class="label_patrones_data" colspan="3">
					<label>
						<c:out value="${sujetoObligado.moral.tipoSociedad.descripcion}"></c:out>
					</label>
				</td>
			</tr>
			<tr>
				<td class="label_patrones">
					<label style="width:25%">
						Medios de Contacto:
					</label>
				</td>
				<td class="label_patrones_data" colspan="3">
					<c:if test="${ sujetoObligado.moral.mediosContactoFiscales!=null }">
						<c:forEach var="medioContactoF" items="${sujetoObligado.moral.mediosContactoFiscales}">
							${ medioContactoF.tipoMedioContacto.descripcion }: ${ medioContactoF.desFormaContacto };
						</c:forEach>
					</c:if>
				</td>
			</tr>
		</table>	
</c:if>
<table style="width: 100%; border-width: 0px !important;">
	<tr>
		<td class="label_patrones" style="width: 150px !important;">
			<spring:message code="label.domicilio.fiscal"/>:
		</td>
		<td class="label_patrones_data">
			<c:out value="${sujetoObligado.domicilioFiscal.vialidadPrimaria.nombre}" default=" "/> #<c:out value="${sujetoObligado.domicilioFiscal.numExterior1}" default=""/><c:out value="${sujetoObligado.domicilioFiscal.numExteriorAlf}" default=""/>, 
			<spring:message code="label.interior" /> <c:out value="${sujetoObligado.domicilioFiscal.numInterior}" default=""/><c:out value="${sujetoObligado.domicilioFiscal.numInteriorAlf}" default=""/>, 
			<spring:message code="label.colonia" /> <c:out value="${sujetoObligado.domicilioFiscal.asentamiento.nombre}" default=""/>, 
			<spring:message code="label.codigo.postal.abreviado" /> <c:out value="${sujetoObligado.domicilioFiscal.codigoPostal.codigoPostal}" default=""/>.
		</td>
	</tr>
</table>
