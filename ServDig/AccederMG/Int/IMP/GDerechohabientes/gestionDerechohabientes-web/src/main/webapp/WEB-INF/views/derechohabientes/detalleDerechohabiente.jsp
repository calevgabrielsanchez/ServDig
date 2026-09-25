<%@ include file="../general/taglibs.jsp"%>
<style>
	DIV#loadera {  border: 1px solid #ccc;  width: 225px;  height: 300px;}
	DIV#loadera.loading {  background: url("<spring:url value='/static/resources/imagenes/loading.gif' htmlEscape='true' />") no-repeat center center;}
</style>
<c:choose>
<c:when test="${empty errores}">
<script type="text/javascript">
$(document).ready(function(){
	cargarImagen();
});

function cargarImagen() {
	var imagen = $("<img>");
	var calidadDer = ${derechohabiente.calidad};
	var url = "";
	var nombre = "/${derechohabiente.derechohabiente.nombre}_${derechohabiente.derechohabiente.primerApellido}_${derechohabiente.derechohabiente.segundoApellido}"; 
	if(calidadDer == 1)
		url = "/portalDerechohabiente-ciudadano/derechohabientesImg/getFotografiaAsegurado/${derechohabiente.calidad}/${derechohabiente.asignacionNSS.nssStr}";
	else
		url = "/portalDerechohabiente-ciudadano/derechohabientesImg/getFotografiaDerechohabiente/${derechohabiente.calidad}/${derechohabiente.asignacionNSS.nssStr}"+nombre;
	imagen.attr("src",url).load(
		function() {
			$('#loadera').removeClass('loading');
			$('#loadera').html("");
			$('#loadera').append(imagen);
		}	
	).error(
		function() {
			$('#loadera').removeClass('loading');
			$('#loadera').hide();
			$('#sinImg').show();
		}	
	).css({
		width:'225px',
	 	height:'300px'
	});
}
</script>
<div class="form-comment">

<form >
	<fieldset>
		<legend><strong><spring:message code="label.datosGrupo"/> </strong></legend>
		<div align="center">
		<table>
			<tr>
				<td align="right"><spring:message code="label.nombreAsegurado" /> : </td>
				<td>
					<input type="text" readonly="readonly" value="${derechohabiente.asignacionNSS.nombre} ${derechohabiente.asignacionNSS.primerApellido} ${derechohabiente.asignacionNSS.segundoApellido}" style="width: 300px"/>
				</td>
				<td align="right"><spring:message code="label.nss" /> : </td>
				<td>
					<input type="text" readonly="readonly" value="${derechohabiente.asignacionNSS.nssStr}" style="width: 160px" />
				</td>
			</tr>
			<tr>
					<td align="right">Tipo pension : </td>
					<td>
						<input type="text" readonly="readonly" value="${AsignacionNSS.tipoPension}" style="width: 300px"/>
					</td>
				<td colspan="2" align="center">
				    <c:if test="${patronIMSS}">
					<strong>CCT 74 IMSS</strong>
					</c:if>
				</td>
			</tr>
		</table>
		</div>
	</fieldset>
	<br>
	<fieldset >
		<legend><strong><spring:message code="titulo.derechohabiente"/> ${derechohabiente.parentesco.descripcion}</strong></legend>
		
			<table>
				<tr>
					<td align="right"><spring:message code="label.parentesco" /> : </td>
					<td>
						<input type="text" readonly="readonly" value="${derechohabiente.parentesco.descripcion}" style="width: 160px" />
					</td>
					
					<td align="right"><spring:message code="label.calidad" /> : </td>
					<td>
						<input type="text" readonly="readonly" value="${derechohabiente.calidad}" style="width: 160px" />
					</td>
					<td align="right"><spring:message code="label.agregadoMedico" /> : </td>
					<td>
						<input type="text" readonly="readonly" value="${derechohabiente.agregadoMedico}" style="width: 160px"/>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="label.nombre"/> : </td>
					<td>
						<input type="text" readonly="readonly" value="${derechohabiente.derechohabiente.nombre}" style="width: 160px"/>
					</td>
					<td align="right"><spring:message code="label.primerApe"/> : </td>
					<td>
					<input type="text" readonly="readonly" value="${derechohabiente.derechohabiente.primerApellido}" style="width: 160px"/>
					</td>
					<td align="right"><spring:message code="label.segundoApe"/> : </td>
					<td>
						<input type="text" readonly="readonly" value="${derechohabiente.derechohabiente.segundoApellido}" style="width: 160px"/>
					</td>				
				</tr>
				<tr>
					<td align="right"><spring:message code="label.fechaNac" /> : </td>
					<td>
						<input type="text" readonly="readonly" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${derechohabiente.derechohabiente.fechaNacimiento}"/>" style="width: 160px"/>
					</td>
					<td align="right"><spring:message code="label.lugarNac" /> : </td>
					<td>
						<input type="text" readonly="readonly" value="${derechohabiente.derechohabiente.lugarNacimiento.nombre}" style="width: 160px"/>
					</td>
					<td align="right"><spring:message code="label.curp" /> : </td>
					<td>
						<input type="text" readonly="readonly" value="${derechohabiente.derechohabiente.curp}" style="width: 160px"/>
					</td>			
				</tr>
				<tr>
					<td align="right"><spring:message code="label.sexo" /> : </td>
					<td>
						<input type="text" readonly="readonly" value="${derechohabiente.derechohabiente.sexo.descripcion}" style="width: 160px"/>
					</td>
					<td align="right"><spring:message code="label.edoCivil" /> : </td>
					<td>
						<input type="text" readonly="readonly" value="${derechohabiente.derechohabiente.estadoCivil.descripcion}" style="width: 160px"/>
					</td>
					<td align="right"><spring:message code="label.estadoDer" /> : </td>
					<td>
						<input type="text" readonly="readonly" value="${derechohabiente.estadoDerechohabiente.descripcion}" style="width: 160px" />
					</td>			
				</tr>
				<tr>
					
					<td align="right"><spring:message code="label.inicioVigencia" /> : </td>
					<td >
						<input type="text" readonly="readonly" value="<fmt:formatDate  pattern="dd/MM/yyyy"  value="${derechohabiente.fechaInicioVigencia}"/>" style="width: 160px"/>
					</td>
					<td align="right"><spring:message code="label.finVigencia" /> : </td>
					<td>
						<input type="text" readonly="readonly" value="<fmt:formatDate  pattern="dd/MM/yyyy"  value="${derechohabiente.fechaFinVigencia}"/>"  style="width: 160px"/>
					</td>
					
					
					<td align="right">
						<c:if test="${(derechohabiente.subEstadoDerechohabiente.idSubEstadoDerechohabiente != 3) &&
						(derechohabiente.subEstadoDerechohabiente.idSubEstadoDerechohabiente !=1)}">
						
							<spring:message code="label.subEstadoDer" /> : 
						</c:if>
					</td>
					<td>
						<c:if test="${(derechohabiente.subEstadoDerechohabiente.idSubEstadoDerechohabiente != 3) &&
							(derechohabiente.subEstadoDerechohabiente.idSubEstadoDerechohabiente !=1)}">
						<input type="text" readonly="readonly" value="${derechohabiente.subEstadoDerechohabiente.descripcion}" style="width: 160px"/>
						</c:if>
					</td>
				</tr>
				<c:if test="${derechohabiente.prorrogaActiva}">
					<tr>
					<td align="right">Tipo Pr&oacute;rroga : </td>
					<td colspan="2">
						<input type="text" readonly="readonly" value="${derechohabiente.tipoProrroga.descripcion}" style="width: 300px"/>
					</td>
					<td align="center" colspan="3">
						<c:if test="${derechohabiente.prorrogaPermanente}">
							<strong>Aplicaci�n del art�culo 93 LLS97</strong>
						</c:if>
					</td>
				</tr>
				</c:if>
				<tr>
					<td align="right"><spring:message code="label.umf" /> : </td>
					<td>
						<input type="text" readonly="readonly" value="${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.descripcion} - ${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.nombreCorto}" style="width: 160px" />
					</td>
					<td align="right"><spring:message code="label.turno" /> : </td>
					<td>
						<input type="text" readonly="readonly" value="${derechohabiente.medicoEnTurno.turno.descripcion}" style="width: 160px"/>
					</td>
					<td align="right"><spring:message code="label.consultorio" /> : </td>
					<td >
						<input type="text" readonly="readonly" value="${derechohabiente.medicoEnTurno.consultorio.descripcion}" style="width: 160px"/>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="label.medicoFamiliar" /> : </td>
					<td>
						<input type="text" readonly="readonly" value="${derechohabiente.medicoEnTurno.medicoFamiliar.nombre} ${derechohabiente.medicoEnTurno.medicoFamiliar.primerApellido} ${derechohabiente.medicoEnTurno.medicoFamiliar.segundoApellido}" style="width: 200px"/>
					</td>
					<td align="right" colspan="3"><spring:message code="label.servicioMedico" /> : </td>
					<td>
						<c:choose>
							<c:when test="${isPensionadoMod17Convenio}">
								<input id="servicioMedico" readonly="readonly" type="text" style="width: 30px"
								value="<c:out value="${derechohabiente.conDerechoSm}"/>" />
							</c:when>
							<c:otherwise>
								<c:if test="${ISMODALIDAD17}">
									<table style="width: 95%; margin-top:5px; margin-left: 15px">
										<tr>
											<td>1er<br>Nivel</td>
											<td>2do<br>Nivel</td>
											<td>3er<br>Nivel</td>
										</tr>
										<tr>
											<td><input type="text" value="NO" disabled="disabled" style="width: 20px"></td>
											<td><input type="text" value="SI" disabled="disabled" style="width: 20px"></td>
											<td><input type="text" value="SI" disabled="disabled" style="width: 20px"></td>
										</tr>
									</table>
								</c:if>
								<c:if test="${!ISMODALIDAD17}">
									<input id="servicioMedico" readonly="readonly" type="text" style="width: 30px"
										value="<c:out value="${derechohabiente.conDerechoSm}"/>" />
								</c:if>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="label.umf.delegacion" /> : </td>
					<td colspan="5">
						<input type="text" readonly="readonly" value="${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion}" style="width: 750px" />
					</td>
				</tr>
				<tr>
					<td align="right"><spring:message code="label.umf.subdelegacion" /> : </td>
					<td colspan="5">
						<input type="text" readonly="readonly" value="${derechohabiente.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.descripcion}" style="width: 750px"/>
					</td>
				</tr>
			</table>
	</fieldset>
	<br>
	<fieldset>
	<legend><strong>Fotografia del integrante ${derechohabiente.parentesco.descripcion}</strong></legend>
	<div align="center">
	<div id="loadera" align="center" class="loading" >
	</div>
	<div id="sinImg" style="display: none" >
		<img height="250px" width="200px" alt="Sin Imagen" src="<spring:url value="/static/resources/imagenes/sinImagen.gif" htmlEscape="true" />" title="Portal IMSS"/>
	</div>
	</div>
	</fieldset>
	<br>
		<fieldset >
		<legend><strong><spring:message code="titulo.datosDomicilio" /> ${derechohabiente.parentesco.descripcion}</strong></legend>
		<table>
		
			<tr>
				<td align="right"><spring:message code="label.entidadF" /> : </td>
				<td><input type="text" readonly="readonly" value="${domicilioParticular.asentamiento.localidad.municipio.entidadFederativa.nombre}" style="width: 160px" /><br></td>
				<td align="right"><spring:message code="label.delegacion" /> : </td>
				<td><input type="text" readonly="readonly" value="${domicilioParticular.asentamiento.localidad.municipio.nombre}" style="width: 160px" /><br></td>
				<td align="right"><spring:message code="label.localidad" /> : </td>
				<td><input type="text" readonly="readonly" value="${domicilioParticular.asentamiento.localidad.nombre}" style="width: 200px" /><br></td>
			</tr>
			<tr>
				<td align="right"><spring:message code="label.codigoPos" /> : <br></td>
				<td><input type="text" readonly="readonly" value="${domicilioParticular.codigoPostal.codigoPostal}" style="width: 160px" /><br></td>
				<td align="right"><spring:message code="label.asentamiento" /> : <br></td>
				<td colspan="3"><input type="text" readonly="readonly" value="${domicilioParticular.asentamiento.nombre}" style="width: 460px" /><br></td>
			</tr>
			<tr>
				<td align="right"><spring:message code="label.tipoVialidad" /> : <br></td>
				<td><input type="text" readonly="readonly" value="${domicilioParticular.vialidadPrimaria.tipoVialidad.descripcion}" style="width: 160px" /><br></td>
				<td align="right"><spring:message code="label.nombreV" /> : <br></td>
				<td colspan="3"><input type="text"readonly="readonly" value="${domicilioParticular.vialidadPrimaria.nombre}" style="width: 460px" /><br></td>
			</tr>
			<tr>
				<td colspan="6">
				
					<table>
						<tr>
							<td align="right"><spring:message code="label.numeroLExt" /> : <br></td>
							<td colspan="2"><input type="text" readonly="readonly" value="${domicilioParticular.numExteriorAlf}" style="width: 50px" /><br></td>
							<td colspan="2">&nbsp;</td>
							<td align="right"><spring:message code="label.numeroLInt" /> : <br></td>
							<td><input type="text" readonly="readonly" value="${domicilioParticular.numInteriorAlf}" style="width: 50px" /><br></td>
						</tr>
					</table>
				
					<br>
				</td>
			</tr>
		</table>
	</fieldset>
	<br>
	<fieldset>
		<legend><strong><spring:message code="label.mediosContacto"/> ${derechohabiente.parentesco.descripcion}</strong></legend>
		<center>
		<table>
			<tr>
				<td align="right"><spring:message code="label.correo" /> : </td>
				<td>
					<input type="text" readonly="readonly" value="${derechohabiente.derechohabiente.correoElectronico.correo}" style="width: 160px"/>
				</td>	
				<td align="right"><spring:message code="label.telefono" /> : </td>
				<td>
					<input type="text" readonly="readonly" value="${derechohabiente.derechohabiente.telefonoFijo.claveLada}" style="width: 160px"/>
				</td>	
				<td></td>
				<td></td>
			</tr>
		</table>
		</center>
	</fieldset>
	<br>
	<fieldset>
		<legend><strong><spring:message code="titulo.datosPatron"/></strong></legend>
		<center>
		<table>
			<tr>
				<td align="right"><spring:message code="label.nrp"/> : </td>
				<td>
					<input type="text" readonly="readonly" id="patronnrp" 
					<c:choose>
						<c:when test="${cabezaGrupoFamiliar.calidadParentesco.idParentesco == 6}">
							<c:if test="${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad != '00'}">
							value="${cabezaGrupoFamiliar.patronSujetoObligado.numeroRegistroPatronal}" 
							</c:if>
						</c:when>
						<c:otherwise>
							value="${cabezaGrupoFamiliar.patronSujetoObligado.numeroRegistroPatronal}" 
						</c:otherwise>
					</c:choose>
					style="width: 160px"/>
				</td>	
				<td align="right"><spring:message code="label.modalidad"/> : </td>
				<td>
					<input type="text" readonly="readonly" id="modalidadPatron" id="modalidadp" 
					<c:choose>
						<c:when test="${cabezaGrupoFamiliar.calidadParentesco.idParentesco == 6}">
							<c:if test="${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad != '00'}">
							value="${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.descripcion}" 
							</c:if>
						</c:when>
						<c:otherwise>
							value="${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.descripcion}" 
						</c:otherwise>
					</c:choose>
					style="width: 500px"/>
				</td>
					
			</tr>
			<tr>
				<td align="right"><spring:message code="label.tipoMov"/> : </td>
				<td>
					<input type="text" readonly="readonly" 
					<c:choose>
						<c:when test="${cabezaGrupoFamiliar.calidadParentesco.idParentesco == 6}">
							<c:if test="${cabezaGrupoFamiliar.patronSujetoObligado != null}">
								<c:if test="${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad != '00'}">
								value="${cabezaGrupoFamiliar.tipoMovtoAsegurado.desTipoMvtoAsegurado}" 
								</c:if>
							</c:if>
						</c:when>
						<c:otherwise>
							value="${cabezaGrupoFamiliar.tipoMovtoAsegurado.desTipoMvtoAsegurado}" 
						</c:otherwise>
					</c:choose>
					style="width: 160px"/>
				</td>	
				<td align="right"><spring:message code="label.fechaMov"/> : </td> 
				<td>
					<input type="text" readonly="readonly"  
					<c:choose>
						<c:when test="${cabezaGrupoFamiliar.calidadParentesco.idParentesco == 6}">
							<c:if test="${cabezaGrupoFamiliar.patronSujetoObligado != null}">
								<c:if test="${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad != '00'}">
								value="<fmt:formatDate pattern="dd/MM/yyyy" value="${cabezaGrupoFamiliar.fechaUltimoMovAfiliacion}"/>" 
								</c:if>
							</c:if>
						</c:when>
						<c:otherwise>
							value="<fmt:formatDate pattern="dd/MM/yyyy" value="${cabezaGrupoFamiliar.fechaUltimoMovAfiliacion}"/>" 
						</c:otherwise>
					</c:choose>
					style="width: 160px"/>
				</td>
			</tr>
		</table>
		</center>
	</fieldset>
</form>
</div>	
</c:when>
<c:otherwise>
	<div class="ui-widget-content ui-corner-all">
	<div class="ui-state-error ui-corner-all" align="center">
	<p class="ui-helper-reset ui-state-error-text"><div class="ui-icon ui-icon-alert"></div><spring:message code="${errores}"></spring:message></p>
	</div>
	</div>
</c:otherwise>
</c:choose>	