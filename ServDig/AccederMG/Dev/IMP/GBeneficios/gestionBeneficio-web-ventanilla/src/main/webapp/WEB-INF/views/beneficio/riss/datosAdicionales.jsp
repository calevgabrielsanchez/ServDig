<%@ include file="../../general/taglibs.jsp"%>

<style>
	td.nrpRiss{
		text-align: center;
		font-weight: bold;
	}
</style>

<script src="<spring:url value="/static/resources/js/delta/riss/datosAdicionales.js" htmlEscape="true" />"></script>
	
<div class="contenedor">
	<div>
	
		<div id="info-paso">
			<h3 style="font-size: 1.8em !important"><h3 style="font-size: 1.8em !important">Paso 2: (Continuaci&oacute;n) Informaci&oacute;n b&aacute;sica del beneficiario(s).</h3></h3>			
		</div>
			
	
		<div class="alert alert-info alert-block">
			A continuaci&oacute;n se muestran los patrones que fueron encontrados relacionados 
			al RFC <strong>${datosEntradaRiss.rfc }</strong>.
			Si los datos son correctos de clic en el bot&oacute;n
			&quot;CONTINUAR&quot; para comenzar las validaciones correspondientes.
		</div>

		<fieldset>
			<legend><strong>&nbsp;Informaci&oacute;n localizada</strong></legend>
						
			<c:choose>
				<c:when test="${not empty msgRPSPendientes}">
					<br/><div class="alert alert-success"><strong>${msgRPSPendientes}</strong></div>
				</c:when>
				<c:otherwise></c:otherwise>
			</c:choose>
						
			<form:form modelAttribute="datosEntradaRiss" id="validarRissForm"
				action="${contextpath}/alta/riss/validar/fisica">
				
				<input type="hidden" name="rfcObligatorio" value="${rfcObligatorioValue}"/>
				<input type="hidden" name="iPer" value="${fisica.idPersona }" />
				
				<form:hidden path="rfc"/>
				<form:hidden path="nss"/>				
				<form:hidden path="opcRISS"/>
				<form:hidden path="accion"/>
							
				<table id="tblPatrones" style="width: 100%;"
					class="table table-striped table-bordered" cellpadding="0"
					cellspacing="0" border="0">
					<thead>
						<tr>
							<th>NRP</th>
							<th>Nombre Comercial</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${datosEntradaRiss.patrones}" var="so" varStatus="status">
							<tr>
								<td>${so.numeroRegistroPatronal}${so.modalidad.numModalidad}${so.digVerificador}</td>
								<td>
									<c:choose>
										<c:when test="${empty so.nombreComercial}">
											<span style="font-style: italic; color: #D3D3D3;">SIN
												NOMBRE COMERCIAL</span>
										</c:when>
										<c:otherwise>
											${so.nombreComercial}
										</c:otherwise>
									</c:choose>
									<form:hidden path="patrones[${status.index}].cveIdSujetoObligado"/>
									<form:hidden path="patrones[${status.index}].numeroRegistroPatronal"/>
									<form:hidden path="patrones[${status.index}].modalidad.numModalidad"/>
									<form:hidden path="patrones[${status.index}].digVerificador"/>
																		
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form:form>					
		</fieldset>

			<div style="float: right; margin-top: 10px;">
				<button type="button" id="bntCancelar" class="btn btn-default">REGRESAR</button>
				<button type="button" id="btnValidar" class="btn btn-primary">CONTINUAR</button>
			</div>
			
			<form id="formCancelar"
				action="${contextpath}/alta/riss/iniciar" method="get">
			</form>

	</div>
</div>