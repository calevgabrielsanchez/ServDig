<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ include file="../general/taglibs.jsp"%>

<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/datosGenerales.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>

<script>
	var tramiteDenominacionActivo = ${tramiteDenominacionActivo};
	var tramiteDenominacionRatificado = ${tramiteDenominacionRatificado};
	var context = '<%= request.getContextPath()%>';
	var tipoPersonaFiscal = '${sujetoObligado.tipoPersonaFiscal}';
	var bFisicaVar = '${bFisica}';
	var tipoTramiteDenominacionSocial='<%=TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL%>';
</script>

<c:if test="${bFisica}">
	<c:set var="idPersona" value="${sujetoObligado.fisica.idPersona}"/>
	<c:set var="idTipoPersona" value="<%=TipoPersona.TIPO_PERSONA_FISICA%>"/>
</c:if>

<c:if test="${!bFisica}">
	<c:set var="idPersona" value="${sujetoObligado.moral.cveMoral}"/>
	<c:set var="idTipoPersona" value="<%=TipoPersona.TIPO_PERSONA_MORAL%>"/>
</c:if>


<form:form modelAttribute="sujetoObligado" id="nombreComercialOriginalForm">
	<form:hidden path="cveIdSujetoObligado"/>
	<form:hidden path="tipoPersonaFiscal"/>
	<h3><spring:message code="titulo.informacion.actual" /></h3>
	
	<c:if test="${bFisica}">
		<form:hidden path="fisica.idPersona"/>
		<form:hidden path="fisica.cveFisica"/>
		<form:hidden path="fisica.rfc"/>
		
		<table style="width: 100% !important; border: none !important;">
			<tr>
				<td class="label_patrones">
					<label>
						<spring:message code="label.primer.apellido" />:
					</label>
				</td>
				<td class="label_patrones_data">
					<label>
						<c:out value="${sujetoObligado.fisica.primerApellido}"></c:out>
					</label>
				</td>
				<td class="label_patrones">
					<label>
						<spring:message code="label.segundo.apellido" />:
					</label>
				</td>
				<td class="label_patrones_data">
					<label>
						<c:out value="${sujetoObligado.fisica.segundoApellido}"></c:out>
					</label>
				</td>
			</tr>
			<tr>
				<td class="label_patrones">
					<label>
						<spring:message code="label.nombres" />:
					</label>
				</td>
				<td class="label_patrones_data">
					<label>
						<c:out value="${sujetoObligado.fisica.nombre}"></c:out> 
					</label>
				</td>
				<td class="label_patrones">
					<label>
					<!-- no hay una etiqueta CURP a nivel general, se toma la correspondiente a rep legal-->
						<spring:message code="rep.legal.curp" />
					</label>
				</td>
				<td class="label_patrones_data">
					<label>
						<c:out value="${sujetoObligado.fisica.curp}"></c:out>
					</label>
				</td>
			<tr>
			</tr>
				<td class="label_patrones">
					<label>
						<spring:message code="label.rfc" />:
					</label>
				</td>
				<td class="label_patrones_data">
					<label>
						<c:out value="${sujetoObligado.fisica.rfc}"></c:out>
					</label>
				</td>
			</tr>
			<tr>
				<td class="label_patrones">
					<label>
						Medios de Contacto
					</label>
				</td>
				<td class="label_patrones_data" colspan="3">
					<c:if test="${ sujetoObligado.fisica.mediosContactoFiscales!=null }">
						<c:forEach var="medioContactoF" items="${sujetoObligado.fisica.mediosContactoFiscales}">
							${ medioContactoF.tipoMedioContacto.descripcion }: ${ medioContactoF.desFormaContacto };
						</c:forEach>
					</c:if>
				</td>
			</tr>
		</table>
		<table style="width: 100%; border-width: 0px !important;">
			<tr>
				<td class="label_patrones" style="width: 100px !important;">
					<spring:message code="label.domicilio.fiscal"/>:
				</td>
				<td class="label_patrones_data">
					<c:if test="${sujetoObligado.domicilioFiscal !=null}">
						<c:out value="${sujetoObligado.domicilioFiscal.vialidadPrimaria.nombre}"/> #<c:out value="${sujetoObligado.domicilioFiscal.numExterior1}"/><c:out value="${sujetoObligado.domicilioFiscal.numExteriorAlf}"/>, 
						<spring:message code="label.interior" /> <c:out value="${sujetoObligado.domicilioFiscal.numInterior}"/><c:out value="${sujetoObligado.domicilioFiscal.numInteriorAlf}"/>, 
						<spring:message code="label.colonia" /> <c:out value="${sujetoObligado.domicilioFiscal.asentamiento.nombre}"/>,
						<c:out value="${sujetoObligado.domicilioFiscal.asentamiento.localidad.municipio.nombre}"/>, 
						<c:out value="${sujetoObligado.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}"/>, 
						<spring:message code="label.codigo.postal.abreviado" /> <c:out value="${sujetoObligado.domicilioFiscal.codigoPostal.codigoPostal}"/>.
					</c:if>
					<c:if test="${ sujetoObligado.domicilioFiscal ==null }">
						<div id="mensajeSinDomFiscal" style="color: red;">No existe ning&uacute;n domicilio fiscal registrado</div>
					</c:if>
				</td>
			</tr>
		</table>
	</c:if>
	
	<c:if test="${!bFisica}">
		<form:hidden path="moral.idPersona"/>
		<form:hidden path="moral.rfc"/>
		
		<table style="width: 100% !important; border-width:0 0px 0px 0;">
			<tr>
				<td class="label_patrones" style="width: 100px !important;">
					<label>
						<spring:message code="label.rfc" />:
					</label>
				</td>
				<td class="label_patrones_data" style="width: 150px !important;">
					<label>
						<c:out value="${sujetoObligado.moral.rfc}"></c:out>
					</label>

				</td>
				<td class="label_patrones" style="width: 160px !important;">
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
				<td class="label_patrones" style="width: 100px !important;">
					<label>
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
		<table style="width: 100%; border-width: 0px !important;">
			<tr>
				<td class="label_patrones" style="width: 100px !important;">
					<spring:message code="label.domicilio.fiscal"/>:
				</td>
				<td class="label_patrones_data">
					<c:if test="${sujetoObligado.domicilioFiscal !=null}">
						<c:out value="${sujetoObligado.domicilioFiscal.vialidadPrimaria.nombre}"/> #<c:out value="${sujetoObligado.domicilioFiscal.numExterior1}"/><c:out value="${sujetoObligado.domicilioFiscal.numExteriorAlf}"/>, 
						<spring:message code="label.interior" /> <c:out value="${sujetoObligado.domicilioFiscal.numInterior}"/><c:out value="${sujetoObligado.domicilioFiscal.numInteriorAlf}"/>, 
						<spring:message code="label.colonia" /> <c:out value="${sujetoObligado.domicilioFiscal.asentamiento.nombre}"/>,
						<c:out value="${sujetoObligado.domicilioFiscal.asentamiento.localidad.municipio.nombre}"/>, 
						<c:out value="${sujetoObligado.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre}"/>, 
						<spring:message code="label.codigo.postal.abreviado" /> <c:out value="${sujetoObligado.domicilioFiscal.codigoPostal.codigoPostal}"/>.
					</c:if>
					<c:if test="${ sujetoObligado.domicilioFiscal ==null }">
						<div id="mensajeSinDomFiscal" style="color: red;">No existe ning&uacute;n domicilio fiscal registrado</div>
					</c:if>
				</td>
			</tr>
		</table>
	</c:if>
	
	<table style="width: 100% !important; margin: 0px; width: 100%; border: none !important;" >
		<tr>
			<td style="border: none !important;">
				<input type="button" id="btnGuardar" class="mboton" style="width:250px;" 
					onclick="actualizarNC();" value="Actualizar informaci&oacute;n">
			</td>
			<td style="border: none !important;" align="right" >
				<div id="grupoRatificarDG">
				<!-- 
					<input type="checkbox" name="chkRatifica" id="chkRatifica" onclick="ratificaCheckNC();" />
					<b><spring:message code="label.ratificar"/></b>
				 -->
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2" style="border: none !important;" align="center">
				<div id="mesajeRatificacion" style="display: none;">
					<legend class="legendaConfirmacion">
						La informaci&oacute;n del tr&aacute;mite ha sido ratificada
					</legend>
				</div>
			</td>
		</tr>
	</table>
</form:form>
<form:form id="nombreComercialForm" modelAttribute="<%=TipoTramiteEnum.ACTUALIZACION_DENOMINACION_SOCIAL.name()%>">	
	<div id="divNombreComercial" style="display: none;">
		<h3><spring:message code="titulo.tramite" /></h3>
			
			<c:if test="${bFisica}">
				<form:hidden path="fisica.idPersona"/>
				<form:hidden path="fisica.rfc"/>
				
				<table style="width: 100% !important; border-width:0 0px 0px 0;" >
					<tr>
						<td style="border-width:0px 0 0 0px; width: 100px !important;" class="label_patrones">
							<label>
								<span class="indicador_campo_requerido">*</span><spring:message code="label.primer.apellido" />:
							</label>
						</td>
						<td style="border-width:0px 0 0 0px;">
							<form:input path="fisica.primerApellido" disabled="true" size="25" maxlength="60"/>
						</td>
						<td style="border-width:0px 0 0 0px; width: 130px !important;" class="label_patrones">
							<label>
								<span class="indicador_campo_requerido">*</span><spring:message code="label.segundo.apellido" />:
							</label>
						</td>
						<td style="border-width:0px 0 0 0px;">
							<form:input path="fisica.segundoApellido" disabled="true" size="25" maxlength="60"/>
						</td>
					</tr>
					<tr>
						<td style="border-width:0px 0 0 0px;" class="label_patrones">
							<label>
								<span class="indicador_campo_requerido">*</span><spring:message code="label.nombres" />:
							</label>
						</td>
						<td style="border-width:0px 0 0 0px;">
							<form:input path="fisica.nombre" disabled="true" size="25" maxlength="60"/>
						</td>
						<td style="border-width:0px 0 0 0px;" class="label_patrones">
							<label>
								<span class="indicador_campo_requerido">*</span><spring:message code="rep.legal.curp" />
							</label>
						</td>
						<td style="border-width:0px 0 0 0px;">
							<form:input path="fisica.curp" disabled="true" maxlength="50"/>
						</td>
					</tr>
					<tr>
						<td style="border-width:0px 0 0 0px;" class="label_patrones">
							<label>
								<span class="indicador_campo_requerido">*</span><spring:message code="label.rfc" />:
							</label>
						</td>
						<td style="border-width:0px 0 0 0px;">
							<form:input path="fisica.rfc" disabled="true" size="15" maxlength="13"/>
						</td>
					</tr>
				</table>
				
				<!-- Seccion Datos generales -->
				<form:hidden path="fisica.pais.idPais"/>
				<form:hidden path="fisica.pais.descripcion"/>
				<form:hidden path="fisica.pais.nacionalidad"/>
				<form:hidden path="fisica.sexo.idSexo"/>
				<form:hidden path="fisica.sexo.descripcion"/>
				<form:hidden path="fisica.sexo.genero"/>
				<form:hidden path="fisica.lugarNacimiento.clave"/>
				<form:hidden path="fisica.lugarNacimiento.nombre"/>
				
				
				
				<!-- Sección domicilio -->
				<form:hidden path="fisica.domicilioFiscal.calle"/>
				<form:hidden path="fisica.domicilioFiscal.colonia"/>
				<form:hidden path="fisica.domicilioFiscal.descripcion"/>
				<form:hidden path="fisica.domicilioFiscal.numExterior1"/>
				<form:hidden path="fisica.domicilioFiscal.numExterior2"/>
				<form:hidden path="fisica.domicilioFiscal.numExteriorAlf"/>
				<form:hidden path="fisica.domicilioFiscal.numInterior"/>
				<form:hidden path="fisica.domicilioFiscal.numInteriorAlf"/>
				<form:hidden path="fisica.domicilioFiscal.numExterior1"/>
				<form:hidden path="fisica.domicilioFiscal.vialidadPrimaria.clave"/>
				<form:hidden path="fisica.domicilioFiscal.vialidadPrimaria.nombre"/>
				<form:hidden path="fisica.domicilioFiscal.vialidadPrimaria.tipoVialidad.clave"/>
				<form:hidden path="fisica.domicilioFiscal.vialidadPrimaria.tipoVialidad.descripcion"/>
				<form:hidden path="fisica.domicilioFiscal.asentamiento.clave"/>
				<form:hidden path="fisica.domicilioFiscal.asentamiento.nombre"/>
				<form:hidden path="fisica.domicilioFiscal.asentamiento.codigoPostal.codigoPostal"/>
				<form:hidden path="fisica.domicilioFiscal.asentamiento.localidad.clave"/>
				<form:hidden path="fisica.domicilioFiscal.asentamiento.localidad.nombre"/>
				<form:hidden path="fisica.domicilioFiscal.asentamiento.localidad.municipio.clave"/>
				<form:hidden path="fisica.domicilioFiscal.asentamiento.localidad.municipio.nombre"/>
				<form:hidden path="fisica.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.clave"/>
				<form:hidden path="fisica.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre"/>
				<c:set var="descripcionDomicilio" value="${ACTUALIZACION_DENOMINACION_SOCIAL.fisica.domicilioFiscal.descripcion }"></c:set>
			</c:if>
			
			<c:if test="${!bFisica}">
				<form:hidden path="moral.idPersona"/>
				<form:hidden path="moral.rfc"/>
				
				<!-- Datos de retorno del servicio de personas -->
				<!-- PERSONA -->
				
				<!-- DOMICILIO -->
				
				
				<table style="width: 100% !important; border-width:0 0px 0px 0;">
					<tr>
						<td class="label_patrones" style="width: 100px !important;">
							<label>
								<span class="indicador_campo_requerido">*</span><spring:message code="label.rfc" />
							</label>
						</td>
						<td style=" width: 150px !important; border-width:0px 0 0 0px;">
							<form:input path="moral.rfc" disabled="true" maxlength="13"/>
						</td>
						<td class="label_patrones" style="width: 160px !important;">
							<label>
								<span class="indicador_campo_requerido">*</span><spring:message code="label.razon.social" />
							</label>
						</td>
						<td style="border-width:0px 0 0 0px;">
							<form:input path="moral.razonSocial" disabled="true" size="55" maxlength="50"/>
						</td>
					</tr>
					<tr>
						<td class="label_patrones">
							<label style="width:25%">
								<span class="indicador_campo_requerido">*</span><spring:message code="label.tipo.sociedad" />
							</label>
						</td>
						<td style="border-width:0px 0 0 0px;" colspan="3">
							<combo:creaCombo idHtml="moral.tipoSociedad.idTipoSociedad"  
					idHtmlContenedor="nombreComercialForm"
					entidad="mx.gob.imss.ctirss.delta.persistence.DicTipoSociedad"
					idHtmlValor="${idTipoSociedadVar}"  
					mostrarSoloActivos = "true"/>
					<form:hidden path="moral.tipoSociedad.descripcion"/>
					<form:hidden path="moral.tipoSociedad.descripcionAbreviada"/>
						</td>
					</tr>
				</table>
				
				<form:hidden path="moral.domicilioFiscal.calle"/>
				<form:hidden path="moral.domicilioFiscal.colonia"/>
				<form:hidden path="moral.domicilioFiscal.descripcion"/>
				<form:hidden path="moral.domicilioFiscal.numExterior1"/>
				<form:hidden path="moral.domicilioFiscal.numExterior2"/>
				<form:hidden path="moral.domicilioFiscal.numExteriorAlf"/>
				<form:hidden path="moral.domicilioFiscal.numInterior"/>
				<form:hidden path="moral.domicilioFiscal.numInteriorAlf"/>
				<form:hidden path="moral.domicilioFiscal.numExterior1"/>
				<form:hidden path="moral.domicilioFiscal.codigoPostal.codigoPostal"/>
				<form:hidden path="moral.domicilioFiscal.vialidadPrimaria.clave"/>
				<form:hidden path="moral.domicilioFiscal.vialidadPrimaria.nombre"/>
				<form:hidden path="moral.domicilioFiscal.vialidadPrimaria.tipoVialidad.clave"/>
				<form:hidden path="moral.domicilioFiscal.vialidadPrimaria.tipoVialidad.descripcion"/>
				<form:hidden path="moral.domicilioFiscal.asentamiento.clave"/>
				<form:hidden path="moral.domicilioFiscal.asentamiento.nombre"/>
				<form:hidden path="moral.domicilioFiscal.asentamiento.codigoPostal.codigoPostal"/>
				<form:hidden path="moral.domicilioFiscal.asentamiento.localidad.clave"/>
				<form:hidden path="moral.domicilioFiscal.asentamiento.localidad.nombre"/>
				<form:hidden path="moral.domicilioFiscal.asentamiento.localidad.municipio.clave"/>
				<form:hidden path="moral.domicilioFiscal.asentamiento.localidad.municipio.nombre"/>
				<form:hidden path="moral.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.clave"/>
				<form:hidden path="moral.domicilioFiscal.asentamiento.localidad.municipio.entidadFederativa.nombre"/>
				<c:set var="descripcionDomicilio" value="${ACTUALIZACION_DENOMINACION_SOCIAL.moral.domicilioFiscal.descripcion }"></c:set>
			</c:if>
		
		<table style="width: 100% !important; border:none;">
			<tr>
				<td class="label_patrones" style="width: 150px;">
					<spring:message code="label.domicilio.fiscal"/>
				</td>
				<td class="label_patrones_data">
					<div id="textoDomicilio">
						${descripcionDomicilio }
					</div>
				<td>
			</tr>
		</table>
		
		
		<div id="seccionMediosFiscales" style="display: none;">
			<legend class="separadorseccion" style="width:95%">
				<spring:message code="titulo.medio.contacto"/>
			</legend>
			
			<table id="gridMediosFiscales"
					style="width: 500px; vertical-align: top;">
				<thead>
				</thead>
				<tbody style="width: 500px;">
				</tbody>
			</table>
		</div>
				
		
		<input type="button" id="btnGuardar" class="mboton" style="width:200px;" 
			onclick="validarDG()" value="Guardar">	
		<input type="button" id="btnCancelar" class="mboton" style="width:200px;" 
			onclick="cancelarNC()" value="Cancelar">	
	</div>
</form:form>

<div id="icaFisicaDialog"></div>
<div id="icaMoralDialog"></div>
<div id="mdmFisicaDialog"></div>
<div id="mdmMoralDialog"></div>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<script language="javascript">
	function validarDG(){
		if (validarDatosGenerales()){
			var rfcEnviarValidacion;
			if (tipoPersonaFiscal == "FISICA") {
				rfcEnviarValidacion=$("#fisica\\.rfc").val();
			}else{
				rfcEnviarValidacion=$("#moral\\.rfc").val();
			}
			validaSolicitudTramiteActivo('/afiliacion/validarTramiteActivo?tipoTramite='+denominacionSocial+'&idSolicitud='+idSolicitud+'&rfc='+rfcEnviarValidacion,callbackActualizacionDenominacionTramite);
		}
	}
</script>

