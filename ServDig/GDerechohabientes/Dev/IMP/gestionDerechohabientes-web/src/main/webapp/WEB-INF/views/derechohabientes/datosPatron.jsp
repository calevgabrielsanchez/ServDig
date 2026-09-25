 <%@ include file="../general/taglibs.jsp" %>

<script type="text/javascript">
$(document).ready(function(){ 
	

$('#patronesProrroga').dataTable( 
	 	{
	 		sScrollX: "100%",
	 		bJQueryUI : true,
	         bFilter : false,
	         bInfo:true,
	         bSort: false,
	         "bPaginate": true,
	         "bAutoWidth" : true,
	         "iDeferLoading" : 0
	     }
	   );
}  );
</script>
	<!------------------------------------ Datos del patron---------------------------------------------------->		
	<fieldset style="width: 967px" class="titulo" id="prestaciones">
		<legend><strong><spring:message code="titulo.datosPatron" /></strong></legend>
		
	  	<table style="width: 100%" >
			<tr>
		        <td align="right">
						<spring:message code="label.registroPatronal"/>: 
				</td>
				<td >					
					<input id="nrp" readonly="readonly" type="text" style="width: 100px" 
					<c:choose>
						<c:when test="${cabezaGrupoFamiliar.calidadParentesco.idParentesco == 6}">
							<c:if test="${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad != '00'}">
							value="<c:out value="${cabezaGrupoFamiliar.patronSujetoObligado.numeroRegistroPatronal}"/>"
							</c:if>
						</c:when>
						<c:otherwise>
							value="<c:out value="${cabezaGrupoFamiliar.patronSujetoObligado.numeroRegistroPatronal}"/>"
						</c:otherwise>
					</c:choose>
					>
				</td>
				
				<td align="right">
						<spring:message code="label.ultimoMov"/>: 
				</td>
				<td >
					<input id="fechaMovimiento" readonly="readonly" type="text"
							style="width: 275px"            
							<c:choose>
								<c:when test="${cabezaGrupoFamiliar.calidadParentesco.idParentesco == 6}">
									<c:if test="${cabezaGrupoFamiliar.patronSujetoObligado != null}">
										<c:if test="${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad != '00'}">
										value="<fmt:formatDate pattern="dd/MM/yyyy" value="${cabezaGrupoFamiliar.fechaUltimoMovAfiliacion}"/>  ${cabezaGrupoFamiliar.tipoMovtoAsegurado.desTipoMvtoAsegurado}"
										</c:if>
									</c:if>
								</c:when>
								<c:otherwise>
									value="<fmt:formatDate pattern="dd/MM/yyyy" value="${cabezaGrupoFamiliar.fechaUltimoMovAfiliacion}"/>  ${cabezaGrupoFamiliar.tipoMovtoAsegurado.desTipoMvtoAsegurado}"
								</c:otherwise>
							</c:choose>
							>
				</td>	
			
				
			</tr>
			<tr>
				<td align="right">
						<spring:message code="label.modalidad"/>: 
				</td>
				<td colspan="3">					
					<input id="modalidad" readonly="readonly" type="text" style="width: 630px" 
					<c:choose>
						<c:when test="${cabezaGrupoFamiliar.calidadParentesco.idParentesco == 6}">
							<c:if test="${cabezaGrupoFamiliar.patronSujetoObligado != null}">
								<c:if test="${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad != '00'}">
								value="${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad} - ${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.descripcion}"
								</c:if>
							</c:if>
						</c:when>
						<c:otherwise>
							value="${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad} - ${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.descripcion}"
						</c:otherwise>
					</c:choose>
			>
				</td>
			</tr>
			<tr>
				<td align="right">
						Nombre o raz&oacute;n social: 
				</td>
				<td colspan="3">	
					<c:if test="${not empty cabezaGrupoFamiliar.patronSujetoObligado.moral}">
						<c:set var="nombrePatronCGF">${cabezaGrupoFamiliar.patronSujetoObligado.moral.razonSocial}</c:set>
					</c:if>
					<c:if test="${not empty cabezaGrupoFamiliar.patronSujetoObligado.fisica}">
						<c:set var="nombrePatronCGF">${cabezaGrupoFamiliar.patronSujetoObligado.fisica.nombre} ${cabezaGrupoFamiliar.patronSujetoObligado.fisica.primerApellido} ${cabezaGrupoFamiliar.patronSujetoObligado.fisica.segundoApellido}</c:set> 
					</c:if>
										
					<input id="razonSocial" readonly="readonly" type="text" style="width: 630px" 
					<c:choose>
						<c:when test="${cabezaGrupoFamiliar.calidadParentesco.idParentesco == 6}">
							<c:if test="${cabezaGrupoFamiliar.patronSujetoObligado.modalidad.numModalidad != '00'}">
							value="${nombrePatronCGF}"
							</c:if>
						</c:when>
						<c:otherwise>
							value="${nombrePatronCGF}"
						</c:otherwise>
					</c:choose>
					>
				</td>
			</tr>
	  	
	  	</table>
		<br>
		<table width="100%" id="patronesProrroga">
			<caption><strong><spring:message code="label.patronesRelacionados" /></strong></caption>
			<thead>
				<tr>
					<th><spring:message code="label.registroPatronal" /></th>
					<th><spring:message code="label.modalidad" /></th>
					<th>Nombre o raz&oacute;n social</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${patrones}" var="patron">
					<tr>
						<td>${patron.numeroRegistroPatronal}</td>
						<td>${patron.modalidad.numModalidad} - ${patron.modalidad.descripcion}</td>
						<td>
							<c:if test="${not empty patron.moral}">
								${patron.moral.razonSocial}
							</c:if>
							<c:if test="${not empty patron.fisica}">
								${patron.fisica.nombre} ${patron.fisica.primerApellido} ${patron.fisica.segundoApellido} 
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</fieldset>
	
	<!------------------------------------ FIN Datos del patron---------------------------------------------------->
	<br>
			