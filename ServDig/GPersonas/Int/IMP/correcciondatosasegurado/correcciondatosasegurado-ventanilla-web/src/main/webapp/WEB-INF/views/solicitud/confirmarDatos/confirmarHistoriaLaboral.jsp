<div id="info-paso" style="margin-bottom: 50px;">
<div class="contenedor">
	<h3>
		<spring:message code="label.solicitud.datosHistoriaLaboral" />
	</h3>
	<hr class="red" style="margin-bottom: 20px;"/>
	</div>
	
	<div class="row">
							
			<div class="form-group">
				<div class="row">
					<div class="col-md-12 col-sm-7 col-xs-12">
					<table class="table table-hover table-bordered table-responsive" id='datosHistoriaLaboralGrid' name="datosHistoriaLaboralGrid" style="font-size: 18px!important;table-layout: fixed; width: 100%"">					
						<tr>
							<th name="nombrePatron" ><spring:message
									code="label.solicitud.placeholder.nombreRazonSocial" /></th>
							<th name="entidadFederativa"><spring:message
									code="label.solicitud.placeholder.entidadFederativa" /></th>
							<th name="fechaInscripcion"><spring:message
									code="label.solicitud.placeholder.fechaInscripcion" /></th>
							<th name="fechaBaja"><spring:message
									code="label.solicitud.placeholder.fechaBaja" /></th>
							<th name="numeroRegistroPatronal"><spring:message
									code="label.solicitud.placeholder.numeroRegistroPatronal" /></th>
							<th name="actividadEmpresa" ><spring:message
									code="label.solicitud.placeholder.actividadEmpresa" /></th>
							<th name="domicilioEmpresa" ><spring:message
									code="label.solicitud.placeholder.domicilioEmpresa" /></th>
						</tr>
						<c:forEach var="historiaLaboral" varStatus="contador"
							items="${informacionHistoriaLaboral.historiaLaboralGrid}">
							<tr id="datosHistoriaLaboralGrid${contador.index +1}">
								<td style="word-wrap: break-word">${historiaLaboral.nombrePatron}</td>
								<td>${historiaLaboral.entidadFederativa}</td>
								<td>${historiaLaboral.fechaInscripcion}</td>
								<td>${historiaLaboral.fechaBaja}</td>
								<td>${historiaLaboral.numeroRegistroPatronal}</td>
								<td style="word-wrap: break-word">${historiaLaboral.actividadEmpresa}</td>
								<td style="word-wrap: break-word">${historiaLaboral.domicilioEmpresa}</td>
								
							</tr>
						</c:forEach>
					</table>			
					</div>
				</div>
			</div>
		</div>


</div>