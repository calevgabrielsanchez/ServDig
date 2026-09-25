<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<head>
<meta http-equiv="expires" content="-1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">	history.go(1); </script>
<script>
	var contextPath = "<%=request.getContextPath()%>";
	var perfilSession='null';
	var patronImss ='null';
	
	try{
		perfilSession='<%=request.getSession().getAttribute("perfilUsuario")%>';
		patronImss =  ${patronIMSS};
	}catch(e){
		
	}
</script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/grupoFamiliar.js" htmlEscape="true" />"></script>

<style>
	tr{
		padding-top:1px;
	}
</style>

</head>

<div class="form-comment">

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
					<c:if test="${ (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente != 1)
						&& (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente !=3)}">
				
						<td align="right">
							<spring:message code="label.subEstadoDer" />
						</td>
						<td >
							<input type="text" readonly="readonly" value="${miGrupoFamiliar.subEstadoDerechohabiente.descripcion}" style="width: 160px"/>
						</td>
					</c:if>
					<c:if test="${ (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente == 1)
						|| (miGrupoFamiliar.subEstadoDerechohabiente.idSubEstadoDerechohabiente ==3)}">
					
						<td align="right">
							<spring:message code="label.umf" />:
						</td>
						<td >
							<input id="umf" readonly="readonly" type="text"
								style="width: 140px"
								value="<c:out value="${miGrupoFamiliar.medicoEnTurno.unidadMedicaFamiliar.nombreCorto}"/>" />					
						</td>
				  	</c:if>
				  	
				  	
				  	<td align="right">
						<spring:message code="label.conDerechoSm" />: 
					</td>
					
					<td>
						<input id="vigencia" readonly="readonly" type="text"
							style="width: 140px"
							value="<c:out value="${miGrupoFamiliar.conDerechoSm}"/>" />
					</td>
					
				  	
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
								style="width: 140px"
								value="<c:out value="${miGrupoFamiliar.medicoEnTurno.unidadMedicaFamiliar.nombreCorto}"/>" />					
						</td>
				</tr>
				</c:if>
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
							style="width: 410px"
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
						style="width: 410px"
						value="${miGrupoFamiliar.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion}" />
					 </td>
				</tr>
				
				
				<tr>
					<td align="right">
						<spring:message code="label.conDerechoInc" />: 
					</td>
					
					<td>
						<input id="vigencia" readonly="readonly" type="text"
							style="width: 140px"
							value="<c:out value="${miGrupoFamiliar.conDerechoInc}"/>" />
					</td>
					
				</tr>
				
				<tr>
				
				
				
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
	
</div>			


