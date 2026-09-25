<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<head>

<script type="text/javascript">	history.go(1); </script>
<script>
	var contextPath = "<%=request.getContextPath()%>";
	var perfilSession='null';
	var patronImss ='null';
	
	try{
		perfilSession='<%=request.getSession().getAttribute("perfilUsuario")%>';
		patronImss =  ${patronIMSS};

		$(document).ready(function(){
			$( "#accordion" ).accordion({
				collapsible: true,					
				active: 0,
				header: 'div'		
			});
		});
		
	}catch(e){
		
	}
	
</script>
<meta http-equiv="expires" content="-1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src=" <spring:url value="/static/resources/js/jquery/dtable/jquery.dataTables_1.9.2.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/grupoFamiliar.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/derechohabiente/js/documentosReportes/cartillaNacionalSalud.js" htmlEscape="true" />"></script>



</head>

<div class="form-comment"><form:form modelAtribute="integrantes"
	id="integrantes" action="#" method="post">

	<h4 align="center"><strong><spring:message
		code="label.infoGrupoFamiliar" /></strong></h4>

<!------------------------------------ Datos del asegurado ---------------------------------------------------->
	<fieldset style="width: 977px" class="titulo"><legend><strong><spring:message
		code="titulo.datosAsegurado" /></strong></legend>


		<table style="width: 100%" >
		  <COLGROUP id="col1" style="border: 1px solid lightgray;" span="6">
		   <tbody style="border: 1px solid lightgray;">		
				<tr>
			        <th colspan="6">
						<spring:message code="label.infoGenreal"/>
					</th>
					
				</tr>
			</tbody>	
			<tbody style="border: 1px solid lightgray;">
			    <tr>
			        <td align="right">
						<spring:message code="label.nss"/>:
					</td>
					<td>
						<input id="nss" readonly="readonly" type="text" 
							style="width: 140px" 
							value="<c:out value="${miGrupoFamiliar.asignacionNSS.nssStr}"/>">
					</td>	
					
			    	<td align="right">
						<spring:message code="label.curp" />:
					</td>
					<td>
						<input id="curp" readonly="readonly" type="text"
								style="width: 140px"
								value="<c:out value="${miGrupoFamiliar.derechohabiente.curp}"/>">
					</td>
					<td align="right">
						<spring:message code="label.sexo" />:
					</td>
					<td>
						<input id="sexo" readonly="readonly" type="text"
								style="width: 140px"
								value="<c:out value="${miGrupoFamiliar.derechohabiente.sexo.descripcion}"/>" />
					</td>
					
			    </tr>
			    
			    
			    
			    <tr>				
					<td align="right" >
						<spring:message code="label.nombre" />:
					</td>
					<td>
						<input id="nombre" readonly="readonly" type="text"
								style="width: 140px"
								value="<c:out value="${miGrupoFamiliar.derechohabiente.nombre}"/>">
					</td>
				
					<td align="right" >
						<spring:message code="label.primerApe" />:
					</td>
				
					<td>
						<input id="primerApellido" readonly="readonly" type="text"
								style="width: 140px"
								value="<c:out value="${miGrupoFamiliar.derechohabiente.primerApellido}"/>">
					</td>
				
					<td align="right" style="width: 150px">
						<spring:message code="label.segundoApe" />: 
					</td>
					<td>
						<input id="segundoApellido" readonly="readonly" type="text"
								style="width: 140px"
								value="<c:out value="${miGrupoFamiliar.derechohabiente.segundoApellido}"/>">					
					</td>	
				</tr>	
				
				<tr>
					<td align="right">
						<spring:message code="label.lugarNac" />:   
					</td>
					<td>
						<input id="lugarNacmiento" readonly="readonly" type="text"
								style="width: 140px"
								value="<c:out value="${miGrupoFamiliar.derechohabiente.lugarNacimiento.nombre}"/>">
					</td>
					<td align="right">
						<spring:message code="label.fechaNac" />:
					</td>
					<td>
						<input id="fechaNacimiento" readonly="readonly" type="text"
								style="width: 140px"
								value="<fmt:formatDate pattern="dd/MM/yyyy" value="${miGrupoFamiliar.derechohabiente.fechaNacimiento}"/>">
					</td>
					<td></td><td></td>	
				</tr>
				<tr>
					<td><br></td><td></td><td></td>
					<td></td><td></td><td></td>
				</tr>
			</tbody>
			<tr>
		        <th colspan="6">
					<spring:message code="label.datosVigencia"/>
				</th>
			</tr>
			<tbody style="border: 1px solid lightgray;">
				<tr>
					<td align="right">
						<spring:message code="label.situacion" />: 
					</td>
					
					<td>
						<input id="vigencia" readonly="readonly" type="text"
							style="width: 155px"
							value="<c:out value="${miGrupoFamiliar.estadoDerechohabiente.descripcion}"/>" />
					</td>
					<c:if test="${ (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente != 1)
						&& (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente !=3)}">
					
						<td align="right">
							<spring:message code="label.subEstadoDer" />
						</td>
						<td colspan="3">
							<input type="text" readonly="readonly" value="${miGrupoFamiliar.subEstadoDerechohabiente.descripcion}" style="width: 160px"/>
						</td>
					</c:if>
					<c:if test="${ (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente == 1)
						|| (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente ==3)}">
					
						<td align="right">
							<spring:message code="label.umf" />:
						</td>
						<td colspan="3">
							<input id="umf" readonly="readonly" type="text"
								style="width: 510px"
								value="<c:out value="${miGrupoFamiliar.medicoEnTurno.unidadMedicaFamiliar.nombreCorto}"/>" />					
						</td>
				  	</c:if>
				</tr>
				<c:if test="${ (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente != 1)
						&& (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente !=3)}">
				<tr>
						<td colspan="2"></td>
						<td align="right">
							<spring:message code="label.umf" />:
						</td>
						<td colspan="3">
							<input id="umf" readonly="readonly" type="text"
								style="width: 510px"
								value="<c:out value="${miGrupoFamiliar.medicoEnTurno.unidadMedicaFamiliar.nombreCorto}"/>" />					
						</td>
				</tr>
				</c:if>${miGrupoFamiliar.conDerechoSm}
				<tr>
						<td colspan="2"></td>
						<td align="right">
							<spring:message code="abel.servicioMedico" />:
						</td>
						<td colspan="3">
							<input id="servicioMedico" readonly="readonly" type="text"
								style="width: 510px"
								value="<c:out value="${miGrupoFamiliar.conDerechoSm}"/>" />					
						</td>
				</tr>
			    <tr>
			    	
					 <td align="right">
						<spring:message code="label.turno" />:
					</td>
					<td>
						<input id="turno" readonly="readonly" type="text"
							style="width: 140px"
							value="<c:out value="${miGrupoFamiliar.medicoEnTurno.turno.descripcion}"/>" />					
					</td>
					
					<td align="right">
						<spring:message code="label.medicoFamiliar" />:
					</td>
					<td colspan="3">
						<input  readonly="readonly" type="text"
							style="width: 510px"
							value="<c:out value="${miGrupoFamiliar.medicoEnTurno.medicoFamiliar.nombre} ${miGrupoFamiliar.medicoEnTurno.medicoFamiliar.primerApellido} ${miGrupoFamiliar.medicoEnTurno.medicoFamiliar.segundoApellido}  "/>" />					
					</td>
			    </tr>
				<tr>
					<td align="right">
						<spring:message code="label.consultorio" />:
					</td>
					<td>
						<input id="consultorio" readonly="readonly" type="text"
							style="width: 140px"
							value="<c:out value="${miGrupoFamiliar.medicoEnTurno.consultorio.descripcion}"/>" />					
					</td>
					<td align="right"><spring:message code="label.delegacion" />: </td>
					<td colspan="3">
						<input type="text" readonly="readonly" type="text"
						style="width: 510px"
						value="${miGrupoFamiliar.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion}" />
					 </td>
				</tr>
				<tr>
				<td colspan="2" align="center">
					 	 <c:if test="${patronIMSS}">
					 	 <br>
					 	 	 <strong>CCT 74 IMSS</strong>
					 	 	 <br>
					 	 </c:if>
					</td>
				<c:if test="${AsignacionNSS.pensionado}">
					<td align="right">Tipo pension: </td>
					<td colspan="3">
						<input type="text" readonly="readonly" value="${AsignacionNSS.tipoPension}" style="width: 510px"/>
					</td>
				</c:if>
				<c:if test="${!AsignacionNSS.pensionado}">
					<td colspan=4></td>
				</c:if>
					
				</tr>
			
			</tbody>
												
		</table>
		
	
	</fieldset>
	
	
	
	
	<br>
	<!------------------------------------ Domicilio grupo familiar ---------------------------------------------------->
	<fieldset class="titulo" id="accordion">
		<div>
		
		<table style="width: 100%">
		    
			<tr>
				<td><a href="#"><strong><spring:message code="label.domicilioGrupo"/></strong></a></td>
			</tr>
				</table>
		</div>
		
		<table style="width: 100%">
			<tr>
			
				<td align="right"><spring:message code="label.codigoPos" />: 
				</td>
				<td><input type="text" readonly="readonly" type="text"
					style="width: 140px"
					value="${miGrupoFamiliar.domicilio.codigoPostal.codigoPostal}" />
				</td>
				<td align="right"><spring:message code="label.asentamiento" />: </td>
				<td><input type="text" readonly="readonly" type="text"
					style="width: 140px"
					value="${miGrupoFamiliar.domicilio.asentamiento.nombre}" />
				</td>
				
				<td align="right"><spring:message code="label.localidad" />:  
				</td>
				<td><input type="text" readonly="readonly" type="text"
					style="width: 140px"
					value="${miGrupoFamiliar.domicilio.asentamiento.localidad.nombre}" />
				</td>
			</tr>
			
			<tr>
				<td align="right"><spring:message code="label.nombreV" />: 
				</td>
				<td><input type="text" readonly="readonly"
					type="text" style="width: 140px"
					value="${miGrupoFamiliar.domicilio.vialidadPrimaria.nombre}" />
				</td>
				<td align="right"><spring:message code="label.delegacion" />: 
				</td>
				<td><input type="text" readonly="readonly" type="text"
					style="width: 140px"
					value="${miGrupoFamiliar.domicilio.asentamiento.localidad.municipio.nombre}" />
				</td>
				<td align="right"><spring:message code="label.entidadF" />: 
				</td>
				<td><input type="text" readonly="readonly" type="text"
					style="width: 140px"
					value="${miGrupoFamiliar.domicilio.asentamiento.localidad.municipio.entidadFederativa.nombre}" />
				</td>
			</tr>
		
			<tr>
				
				<td align="right"><spring:message code="label.numeroLExt" />: 
				</td>
				<td><input type="text" readonly="readonly" type="text"
						style="width: 140px"
						value="${miGrupoFamiliar.domicilio.numExterior1} ${miGrupoFamiliar.domicilio.numExteriorAlf}" />
				</td>
				<td align="right"><spring:message code="label.numeroLInt" />: 
				</td>
				<td><input type="text" readonly="readonly" type="text"
						style="width: 140px"
						value="${miGrupoFamiliar.domicilio.numInterior} ${miGrupoFamiliar.domicilio.numInteriorAlf}" />
				</td>
				<td align="right"><spring:message code="label.numeroSecundario" />: 
				</td>
				<td><input type="text" readonly="readonly" type="text"
						style="width: 140px"
						value="${miGrupoFamiliar.domicilio.numExterior2}" />
				</td>
			</tr>
			<tr>
				<td colspan="6"><br></td>
			</tr>
			
			<tr>
				<td align="right"><spring:message code="label.ref1" /> 
				</td>
				
				<td colspan="5"><input type="text" readonly="readonly"
					type="text" style="width: 450px"
					value="${miGrupoFamiliar.domicilio.vialidadReferenciaPrimaria.tipoVialidad.descripcion} ${miGrupoFamiliar.domicilio.vialidadReferenciaPrimaria.nombre}" />
				</td>
			</tr>
			<tr>
				<td align="right"><spring:message code="label.ref2" /> 
				</td>
				
				<td colspan="5"><input type="text" readonly="readonly"
					type="text" style="width: 450px"
					value="${miGrupoFamiliar.domicilio.vialidadReferenciaSecundaria.tipoVialidad.descripcion} ${miGrupoFamiliar.domicilio.vialidadReferenciaSecundaria.nombre}" />
			    </td>
			</tr>
			<tr>    
			    <td align="right"><spring:message code="label.ref3" /> 
				</td>
			    <td colspan="5"><input type="text" readonly="readonly"
					type="text" style="width: 450px"
					value="${miGrupoFamiliar.domicilio.vialidadReferenciaPosterior.tipoVialidad.descripcion} ${miGrupoFamiliar.domicilio.vialidadReferenciaPosterior.nombre}" />
				</td>
			</tr>
			
			
			<tr>
				<td><br></td><td></td><td></td><td></td><td></td><td></td>
			</tr>
			
		</table>
	</fieldset>	
	<!------------------------------------ FIN Domicilio grupo familiar ---------------------------------------------------->

	
	<input type="hidden" id="conAsegurado" name="conAsegurado" value="<c:out value="${conAsegurado}"/>"/>
</form:form>
<div id="msg00"
	title="<spring:message code="titulo.mensajeConfirmacion"/>"
	style="display:none"> 
	<c:choose>
			<c:when test="${usuarioObj.perfilUsuario.idPerfilUsuario==1}"> 
				<spring:message code="msg00" />
			</c:when>
			<c:otherwise>
				<spring:message code="msgRegistroJefeDepto" />
  			</c:otherwise>
		</c:choose>			
</div>
</div>			


