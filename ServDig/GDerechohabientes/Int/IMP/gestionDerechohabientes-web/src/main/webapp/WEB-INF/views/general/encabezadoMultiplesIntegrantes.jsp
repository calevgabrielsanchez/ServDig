<%@ include file="../general/taglibs.jsp" %>
<fieldset style="width: 967px" class="titulo">
	<legend>
		<strong><spring:message code="titulo.datosAsegurado" /></strong>
	</legend>

	<table style="width: 100%" >
	  <COLGROUP id="col1" style="border: 1px solid lightgray;" span="4">
	   <tbody style="border: 1px solid lightgray;">		
			<tr>
		        <th colspan="4">
					<spring:message code="label.infoGenreal"/>
				</th>
				<th colspan="4">
				<spring:message code="label.datosVigencia"/>
			</th>
				
			</tr>
		</tbody>	
		<tbody style="border: 1px solid lightgray;">
		    <tr>
		        <td align="right">
					<spring:message code="label.nss"/>:
				</td>
				<td>
					<input id="nss" disabled="disabled" type="text" 
						style="width: 130px" 
						value="<c:out value="${miGrupoFamiliar.asignacionNSS.nssStr}"/>">
				</td>	
				
		    	<td align="right" >
					<spring:message code="label.nombre" />:
				</td>
				<td>
					<input id="nombreGrupoFamiliar" disabled="disabled" type="text"
							style="width: 130px"
							value="<c:out value="${miGrupoFamiliar.derechohabiente.nombre}"/>">
				</td>
				
				<td align="right">
					<spring:message code="label.situacion" />: 
				</td>
				
				<td>
					<input id="vigencia" disabled="disabled" type="text"
						style="width: 155px"
						value="<c:out value="${miGrupoFamiliar.estadoDerechohabiente.descripcion}"/>" />
				</td>
				
				<td align="right" id="detalleLabel"> 
				</td>
				<td id="detalleDescr">
				</td>
				
		    </tr>
		    
		    <tr>				
				<td align="right" >
					<spring:message code="label.primerApe" />:
				</td>
			
				<td>
					<input id="primerApellidoGrupoFamiliar" disabled="disabled" type="text"
							style="width: 130px"
							value="<c:out value="${miGrupoFamiliar.derechohabiente.primerApellido}"/>">
				</td>
			
				<td align="right">
					<spring:message code="label.segundoApe" />: 
				</td>
				<td>
					<input id="segundoApellidoGrupoFamiliar" disabled="disabled" type="text"
							style="width: 130px"
							value="<c:out value="${miGrupoFamiliar.derechohabiente.segundoApellido}"/>">					
				</td>	
			
				
				<td><br></td> 
				<td align="left">
				 	 <c:if test="${patronIMSS}">
				 	 	 <strong>CCT 74 IMSS</strong>
				 	 </c:if>
				</td> 
				<td><br></td> 
				<td><br></td>
			</tr>	
			
			
			<tr>
				<td><br></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>					
				<td></td>
			</tr>
		</tbody>

											
	</table>	
</fieldset>
<c:forEach items="${integrantes}" var="integrante">
<fieldset style="width: 967px" class="titulo" >
	<legend>
		<strong>
			<spring:message code="label.prorrogaEstudios.grupoFamiliar"/>
		</strong>
	</legend>
	<table style="width: 100%">
		<COLGROUP id="col1" style="border: 1px solid lightgray;" span="4">
		<tbody style="border: 1px solid lightgray;">		
			<tr>
		        <th colspan="4">
					<spring:message code="label.infoGenreal"/>
				</th>
				<th colspan="4">
				<spring:message code="label.datosVigencia"/>
			</th>
				
			</tr>
		</tbody>
		<tbody style="border: 1px solid lightgray;">
			<tr>
				<td align="right">
					<spring:message code="label.nombre"/>:
				</td>
				<td>
					<input type="text" disabled="disabled" value="${integrante.derechohabiente.nombre}" style="width: 130px"/>
				</td>
				<td align="right">
					<spring:message code="label.parentesco"/>:		
				</td>
				<td>
					<input type="text" disabled="disabled" style="width: 130px" value="${integrante.parentesco.descripcion}"/>
				</td>
				
				<td align="right">
					Vencimiento vigencia:
				</td>
				<td align="right">
					<input type="text" value='<fmt:formatDate pattern="dd/MM/yyyy" value="${integrante.fechaFinVigencia}"/>' 
						disabled="disabled" style="width: 130px"/>
				</td>
				
				<td align="right">
					<spring:message code="label.situacion" />:
				</td>
				<td>
					<input type="text" disabled="disabled"  style="width: 155px" value="${integrante.estadoDerechohabiente.descripcion}" />
				</td>				
			</tr>
			<tr>				
				<td align="right" style="width: 130px">
					<spring:message code="label.primerApe"/>:
				</td>
				<td align="right">
					<input type="text" disabled="disabled" style="width: 130px" value="${integrante.derechohabiente.primerApellido}"/>
				</td>

				<td align="right">
					<spring:message code="label.fechaNac"/>:
				</td >
				<td>
					<input id="fechaNacimientoGrupoFamiliar${integrante.derechohabiente.idPersona}" type="text" disabled="disabled" style="width: 130px"  
						value='<fmt:formatDate pattern="dd/MM/yyyy" value="${integrante.derechohabiente.fechaNacimiento}"/>'/>
				</td>

				<td align="right">
					<spring:message code="label.consultorio"/>:
				</td>
				<td>
					<input type="text" value="${integrante.medicoEnTurno.consultorio.descripcion}" disabled="disabled" style="width: 130px"/>
				</td>
				

				<td align="right" id="detalleLabel1">
				</td>
				<td id="detalleDescr1">
				</td>
			</tr>
			<tr>
				<td align="right">
					<spring:message code="label.segundoApe"/>:
				</td>
				<td>
					<input type="text" disabled="disabled" style="width: 130px" value="${integrante.derechohabiente.segundoApellido}"/>
				</td>

				<td align="right">
					<spring:message code="label.edad"/>:
				</td>
				<td>
					<input id="edad${integrante.derechohabiente.idPersona}" type="text" disabled="disabled" value="${edad}"  style="width: 130px"  />
					<script>
					$(document).ready(
						function() {
							setEdad("edad${integrante.derechohabiente.idPersona}","fechaNacimientoGrupoFamiliar${integrante.derechohabiente.idPersona}");
						}	
					);
					
					</script>
				</td>
				<td align="right">
					<spring:message code="label.umf"/>:
				</td>
				<td colspan="3">
					<input type="text" value="${integrante.medicoEnTurno.unidadMedicaFamiliar.nombreCorto}" 
						disabled="disabled" style="width: 365px"/>
					<input type="hidden" id="idUmf" name="idUmf" value="${integrante.medicoEnTurno.unidadMedicaFamiliar.idUMF}"></input>	
				</td>				
			</tr>
			<tr>
				<td align="right">
					<spring:message code="label.curp"/>:
				</td>
				<td>
					<input type="text" disabled="disabled" style="width: 130px" value="${integrante.derechohabiente.curp}"/>
				</td>
				
				<td align="right">
					<spring:message code="label.sexo" />:
				</td>
				<td>
					<input type="text" disabled="disabled"  class="disabled" style="width: 130px"  value="${integrante.derechohabiente.sexo.descripcion}"/>
				</td>
				
				<td align="right">
					<spring:message code="label.umf.delegacion"/>:
				</td>
				<td colspan="3">
					<input type="text" value="${integrante.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion}" 
						disabled="disabled" style="width: 365px"/>
				</td>								
			</tr>
			<c:if test="${integrante.parentesco.idParentesco == 1}">
				<tr>				
				<td colspan="4"></td>
				<td colspan="2">
					Autorizaci&oacute;n del Consejo Consultivo :
				</td>
				<td colspan="2">
					<input type="text" disabled="disabled"  style="width: 205px" value="${integrante.indAcuerdo == 1 && integrante.numeroAcuerdo != null ? 'Si' : 'No'}" />
				</td>
				
				</tr>
				<tr>
					<td colspan="4"></td>
					<td colspan="2">
						N&uacute;mero del acuerdo :
					</td>					
					<td colspan="2">
						<input type="text" disabled="disabled"  style="width: 205px" value="${integrante.numeroAcuerdo}" />
					</td>
				</tr>
			</c:if>
			<c:if test="${integrante.parentesco.idParentesco != 1}">
			<tr>				
				<td colspan="4"><br></td>
				<td colspan="4"></td>
				</tr>
			</c:if>
		</tbody>			
	</table>
</fieldset>
</c:forEach>
	<script type="text/javascript">
     	function setEdad(idHtmlEdad,idHtmlFechaNacimiento) {
    		var fechaNac = $("#"+idHtmlFechaNacimiento).val();
    	   
    		calcularEdadCambioMultiple(fechaNac,idHtmlEdad);
     	}
     	
     	function calcularEdadCambioMultiple(fechaNacimiento,idCampoEdad) {
			var edad = "";
			
			if(fechaNacimiento != null && fechaNacimiento != "") {
				
				var fecha = fechaNacimiento.toString().split("/");
			    var dia = fecha[0];
			    var mes = fecha[1];
			    var anio = fecha[2];
			    
			    // cogemos los valores actuales
			    var fecha_hoy = new Date();
			    var ahora_anio = fecha_hoy.getYear();
			    var ahora_mes = fecha_hoy.getMonth()+1;
			    var ahora_dia = fecha_hoy.getDate();
			    
			    // realizamos el calculo
			   	edad = (ahora_anio + 1900) - anio;
			    if ( ahora_mes < mes )
			    {
			        edad--;
			    }
			    if ((mes == ahora_mes) && (ahora_dia < dia))
			    {
			        edad--;
			    }
			    if (edad > 1900)
			    {
			        edad -= 1900;
			    }
			
			    // calculamos los meses
			    var meses=0;
			    if(ahora_mes>mes)
			        meses=ahora_mes-mes;
			    if(ahora_mes<mes)
			        meses=12-(mes-ahora_mes);
			}
		    
			$('#'+idCampoEdad).val(edad);
		}	
			
	</script>