<%@ include file="../general/taglibs.jsp"%>

<c:if test="${!bFisica}">
	<c:if test="${sujetoObligado.moral.escrituraConstitutiva!=null && sujetoObligado.moral.escrituraConstitutiva.cveEscrituraConstitutiva!=null}">
		<br>
		<b><spring:message code="titulo.escritura.const" /></b>
		<br>
		<table style="width: 100% !important; border: none !important;">
			<tr>
				<td class="label_patrones" style="width: 170px !important;">
					<label> <spring:message code="label.escritura.conts.num.esc" />:</label>
				</td>
				<td class="label_patrones_data" style="width: 280px !important;">
					<label><c:out value="${sujetoObligado.moral.escrituraConstitutiva.numEscritura}" default=""/></label>
								
				</td>				
				<td class="label_patrones" style="width: 200px !important;">
					<label> <spring:message code="label.escritura.conts.notaria" />:</label>
				</td>
				<td class="label_patrones_data">
					<label><c:out value="${sujetoObligado.moral.escrituraConstitutiva.numNotaria}" default=""/></label>
				</td>
			</tr>
			<tr>
				<td class="label_patrones">
						<label><spring:message code="label.entidad.federativa"/></label>
				</td>
				<td class="label_patrones_data">
					<label><c:out value="${sujetoObligado.moral.escrituraConstitutiva.lugarExpedicion.entidadFederativa.nombre}" default=""/></label>
				</td>				
				<td class="label_patrones">
					<label  ><spring:message code="label.escritura.conts.municipio"/>:</label>
				</td>
				<td class="label_patrones_data">
					<label><c:out value="${sujetoObligado.moral.escrituraConstitutiva.lugarExpedicion.nombre}" default=""/></label>
				</td>
			</tr>
			<tr>	
				<td style="border-width:0px 0 0 0px;" class="label_patrones">
					<label> <spring:message code="label.escritura.conts.fecha"/>:</label>
				</td>
				<td class="label_patrones_data">
					<label id="labelfechaEC"><c:out value="${sujetoObligado.moral.escrituraConstitutiva.fechaExpedicion}" default=""/></label>				
				</td>
				<td class="label_patrones">
					<label> <spring:message code="label.escritura.conts.folio" />:</label>
				</td>			
				<td class="label_patrones_data">
					<c:if test="${sujetoObligado.moral.escrituraConstitutiva.folioMercantil!='' && sujetoObligado.moral.escrituraConstitutiva.folioMercantil != null}">
						<c:out value="${sujetoObligado.moral.escrituraConstitutiva.folioMercantil}"/>
					</c:if>
					<c:if test="${sujetoObligado.moral.escrituraConstitutiva.folioMercantil=='' || sujetoObligado.moral.escrituraConstitutiva.folioMercantil == null}">
						<spring:message code="msg.sin.informacion"/>
					</c:if>
				</td>
			</tr>
		</table>
		<table style="width: 100% !important; border: none">
			<tr>
				<td class="label_patrones" style="width: 55px !important;">
					<label> <spring:message code="label.seccion" />:</label>
				</td>
				<td class="label_patrones_data" style="width: 130px !important;">
					<label>
						<c:if test="${sujetoObligado.moral.escrituraConstitutiva.seccion!='' && sujetoObligado.moral.escrituraConstitutiva.seccion != null}">
							<c:out value="${sujetoObligado.moral.escrituraConstitutiva.seccion}"/>
						</c:if>
						<c:if test="${sujetoObligado.moral.escrituraConstitutiva.seccion=='' || sujetoObligado.moral.escrituraConstitutiva.seccion == null}">
							<spring:message code="msg.sin.informacion"/>
						</c:if>
					</label>
				</td>			
				<td class="label_patrones" style="width: 55px !important;">
					<label  > <spring:message code="label.partida" />:</label>
				</td>
				<td class="label_patrones_data" style="width: 130px !important;">
					<label>
						<c:if test="${sujetoObligado.moral.escrituraConstitutiva.partida!='' && sujetoObligado.moral.escrituraConstitutiva.partida != null}">
							<c:out value="${sujetoObligado.moral.escrituraConstitutiva.partida}"/>
						</c:if>
						<c:if test="${sujetoObligado.moral.escrituraConstitutiva.partida=='' || sujetoObligado.moral.escrituraConstitutiva.partida == null}">
							<spring:message code="msg.sin.informacion"/>
						</c:if>
					</label>
				</td>
				<td class="label_patrones" style="width: 55px !important;">
					<label> <spring:message code="label.volumen" />:</label>
				</td>
				<td class="label_patrones_data" style="width: 130px !important;">
					<label>
						<c:if test="${sujetoObligado.moral.escrituraConstitutiva.volumen!='' && sujetoObligado.moral.escrituraConstitutiva.volumen != null}">
							<c:out value="${sujetoObligado.moral.escrituraConstitutiva.volumen}"/>
						</c:if>
						<c:if test="${sujetoObligado.moral.escrituraConstitutiva.volumen=='' ||  sujetoObligado.moral.escrituraConstitutiva.volumen == null}">
							<spring:message code="msg.sin.informacion"/>
						</c:if>
					</label>				
				</td>					
				<td class="label_patrones" style="width: 55px !important;">			
					<label> <spring:message code="label.foja" />:</label>
				</td>
				<td class="label_patrones_data">
					<label>
						<c:if test="${sujetoObligado.moral.escrituraConstitutiva.foja!='' && sujetoObligado.moral.escrituraConstitutiva.foja != null}">
							<c:out value="${sujetoObligado.moral.escrituraConstitutiva.foja}"/>
						</c:if>
						<c:if test="${sujetoObligado.moral.escrituraConstitutiva.foja=='' || sujetoObligado.moral.escrituraConstitutiva.foja == null}">
							<spring:message code="msg.sin.informacion"/>
						</c:if>
					</label>
				</td>
			</tr>			
		</table>
	</c:if>
	<c:if test="${sujetoObligado.moral.registroSindicato!=null && sujetoObligado.moral.registroSindicato.cveRegistroSindicato!=null}">
		<br>
		<b><spring:message code="titulo.sindicato" /></b>
		<br>
		<table style="width: 100%; border: none">
			<tr>
				<td class="label_patrones" style="width: 100px !important;">
					<label><spring:message code="label.sindicato.num.ref" />:</label>			
				</td>
				<td class="label_patrones_data" style="width: 100px !important;">
					<label><c:out value="${sujetoObligado.moral.registroSindicato.numReferenciadocRegistro }" default=""/></label>				
				</td>			
				<td class="label_patrones" style="width: 100px !important;">		
					<label><spring:message code="label.sindicato.fecha" />:</label>
				</td>
				<td class="label_patrones_data" style="width: 100px !important;">
					<label>
						<c:out value="${sujetoObligado.moral.registroSindicato.fechaRegistro }" default=""/>
					</label>					
				</td>
			</tr>
			<tr>
				<td class="label_patrones" style="width: 100px !important;">	
						<label><spring:message code="label.sindicato.autoridad" />:</label>
				</td>
				<td class="label_patrones_data" style="width: 100px !important;">
					<label>
						<c:out value="${sujetoObligado.moral.registroSindicato.autoridadLaboral }" default=""/>
					</label>				
				</td>
			</tr>
		</table>
	</c:if>
</c:if>

