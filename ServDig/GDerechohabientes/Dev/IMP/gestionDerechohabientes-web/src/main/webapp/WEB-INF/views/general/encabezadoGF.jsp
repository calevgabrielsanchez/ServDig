<%@ include file="../general/taglibs.jsp" %>

<script>
	$(document).ready(function(){
		var muestraDetalleS = ${conDetalleS};
		if(muestraDetalleS == "0"){
			$('#detalleDescr').show();
			$('#detalleLabel').show();
		}else{			
			$('#detalleDescr').hide();
			$('#detalleLabel').hide();
		}
	});
</script>
<fieldset style="width: 977px" class="titulo"><legend><strong><spring:message code="titulo.datosAsegurado" /></strong></legend>
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
						<input id="nssE" readonly="readonly" type="text" 
							style="width: 140px" 
							value="<c:out value="${miGrupoFamiliar.asignacionNSS.nssStr}"/>">
					</td>	
					
			    	<td align="right">
						<spring:message code="label.curp" />:
					</td>
					<td>
						<input id="curpE" readonly="readonly" type="text"
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
						<input id="nombreE" readonly="readonly" type="text"
								style="width: 140px"
								value="<c:out value="${miGrupoFamiliar.derechohabiente.nombre}"/>">
					</td>
				
					<td align="right" >
						<spring:message code="label.primerApe" />:
					</td>
				
					<td>
						<input id="primerApellidoE" readonly="readonly" type="text"
								style="width: 140px"
								value="<c:out value="${miGrupoFamiliar.derechohabiente.primerApellido}"/>">
					</td>
				
					<td align="right" style="width: 150px">
						<spring:message code="label.segundoApe" />: 
					</td>
					<td>
						<input id="segundoApellidoE" readonly="readonly" type="text"
								style="width: 140px"
								value="<c:out value="${miGrupoFamiliar.derechohabiente.segundoApellido}"/>">					
					</td>	
				</tr>	
				<tr>
					<td align="right">
						<spring:message code="label.lugarNac" />:   
					</td>
					<td>
						<input id="lugarNacmientoE" readonly="readonly" type="text"
								style="width: 140px"
								value="<c:out value="${miGrupoFamiliar.derechohabiente.lugarNacimiento.nombre}"/>">
					</td>
					<td align="right">
						<spring:message code="label.fechaNac" />:
					</td>
					<td>
						<input id="fechaNacimientoE" readonly="readonly" type="text"
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
						<input id="vigenciaE" readonly="readonly" type="text"
							style="width: 155px"
							value="<c:out value="${miGrupoFamiliar.estadoDerechohabiente.descripcion}"/>" />
					</td>
					<c:if test="${ (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente != 1)
						&& (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente !=3)}">
						<td align="right">
							<spring:message code="label.subEstadoDer" />:
						</td>
						<td>
							<input type="text" readonly="readonly" value="${miGrupoFamiliar.subEstadoDerechohabiente.descripcion}" style="width: 140px"/>
						</td>
						<td align="center" colspan="2">
						    <c:if test="${patronIMSS}">
							<strong>CCT 74 IMSS</strong>
							</c:if>
						</td>
					</c:if>
						<c:if test="${ (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente == 1)
							|| (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente == 3)}">
						<c:if test="${AsignacionNSS.pensionado}">
							<td align="right">Tipo pension: </td>
							<td>
								<input type="text" readonly="readonly" value="${AsignacionNSS.tipoPension}" style="width: 300px"/>
							</td>
							<td align="center" colspan="2">
							    <c:if test="${patronIMSS}">
								<strong>CCT 74 IMSS</strong>
								</c:if>
							</td>
						</c:if>
						<c:if test="${!AsignacionNSS.pensionado}">
							<td align="center" colspan="4">
							    <c:if test="${patronIMSS}">
								<strong>CCT 74 IMSS</strong>
								</c:if>
							</td>
						</c:if>	
					</c:if>		  
				</tr>	
					<c:if test="${ (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente != 1)
						&& (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente !=3)}">
					<c:if test="${AsignacionNSS.pensionado}">
						<tr>
							<td colspan="2">
							</td>
							<td align="right">Tipo pension: </td>
							<td colspan="3">
								<input type="text" readonly="readonly" value="${AsignacionNSS.tipoPension}" style="width: 300px"/>
							</td>
						</tr>
					</c:if>
				</c:if>		    						  							
			</tbody>											
	</table>
</fieldset>